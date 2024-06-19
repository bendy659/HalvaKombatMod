package ru.benos.kotloudron.gui.theme

import com.google.gson.Gson
import com.google.gson.JsonObject
import net.minecraftforge.eventbus.api.SubscribeEvent
import org.jetbrains.kotlin.konan.file.File
import ru.benos.kotloudron.Kotloudron
import ru.benos.kotloudron.Kotloudron.Companion.MODID
import ru.benos.kotloudron.utils.DesingLogging.desingLogging
import ru.hollowhorizon.hollowengine.common.files.DirectoryManager.HOLLOW_ENGINE
import java.io.FileReader

object ThemeData {
  val THEME_DIR = HOLLOW_ENGINE.resolve("themes")
  val THEME_GUI = THEME_DIR.resolve("gui")
  val THEME_COLORS = THEME_DIR.resolve("colors")

  fun init() {
    if(!THEME_DIR.isDirectory) {
      val f = File(THEME_DIR.path, "theme-dir-init.create")
      val g = File(THEME_GUI.path, "theme-dir-init.create")
      val h = File(THEME_COLORS.path, "theme-dir-init.create")
      var attemptCount = 0

      while(true) {
        f.mkdirs(); g.mkdirs(); h.mkdirs()
        if(f.exists && g.exists && h.exists) {
          f.delete(); g.delete(); h.delete(); break
        } else {
          Kotloudron.LOGGER.info(desingLogging("ATTEMPT GENERATE A THEME DIR [${attemptCount + 1}]"))
          attemptCount++
        }

        if(attemptCount >= 120000) throw RuntimeException(desingLogging("EXCEEDED THE NUMBER OF ATTEMPT TO GENERATE THEME DIR. MAX ATTEMPT COUNT: 120000"))
      }
      Kotloudron.LOGGER.info(desingLogging("THEME DIR GENERATED."))
    }
  }

  data class ThemeGuiData(val guiName: String, val folderName: String, val resourceLocation: Boolean = false)

  fun themeGuiReading(): MutableList<ThemeGuiData> {
    val guiArray = mutableListOf(
      ThemeGuiData("None", "none"),
      ThemeGuiData("NPC Creator", "$MODID:textures/gui/npc_creator")
    )

    File(THEME_GUI.path).listFiles.forEach { folder ->
      if (folder.isDirectory) {
        val guiInit = File(folder, "gui-init.txt")

        if (guiInit.exists) {
          val initGuiName = guiInit.readStrings() // Читаем содержимое файла и убираем лишние пробелы

          guiArray.add(ThemeGuiData(initGuiName.toString(), folder.name))
          Kotloudron.LOGGER.debug("[THEME] GUI Loaded: {}", guiArray.joinToString(", "))
        }
      }
    }
    return guiArray
  }

  fun themePackReading(guiSelectID: String, selectGuiID: Int): Array<String> {
    var packsArray = arrayOf("Default")

    if(guiSelectID != "none" && selectGuiID >= 1) {
      File(THEME_GUI.resolve("$guiSelectID/packs").path).listFiles.forEach { pack ->
        if (pack.isDirectory) {
          val pack_init = File(pack, "pack-init.txt")

          if (pack_init.exists) {
            val init_pack_name = pack_init.readStrings()

            packsArray += init_pack_name
          }
        }
      }
    } else packsArray += "Packs not found"
    Kotloudron.LOGGER.debug("[THEME] Packs Loaded: {}", packsArray.joinToString(", "))
    return packsArray.filter { it.isNotEmpty() }.toTypedArray()
  }

  fun themeColorsReading(guiSelectID: String, selectGuiID: Int): Array<String> {
    var colorsArray = arrayOf("Default")

    if(guiSelectID == "none" && selectGuiID >= 1) {
      File(THEME_COLORS.path).listFiles.filter { it.name.endsWith(".json") }.forEach { file ->
        if(file.exists && file.isDirectory) {
          val gson = Gson()
          val read = FileReader(file.name)
          val result = gson.fromJson(read, JsonObject::class.java).getAsJsonObject("Name").asString
          colorsArray += result.toList().toTypedArray().toString()
        }
      }
    } else colorsArray += "Colors not found"
    Kotloudron.LOGGER.debug("[THEME] Colors Loaded: {}", colorsArray.joinToString(", "))
    return colorsArray.filter { it.isNotEmpty() }.toTypedArray()
  }
}