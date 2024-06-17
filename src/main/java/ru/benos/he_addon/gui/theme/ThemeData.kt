package ru.benos.he_addon.gui.theme

import org.jetbrains.kotlin.konan.file.File
import org.jetbrains.kotlin.util.removeSuffixIfPresent
import ru.benos.he_addon.HEAddon
import ru.hollowhorizon.hollowengine.common.files.DirectoryManager.HOLLOW_ENGINE

object ThemeData {
  val THEME_DIR = HOLLOW_ENGINE.resolve("themes")
  val THEME_GUI = THEME_DIR.resolve("gui")
  val THEME_COLORS = THEME_DIR.resolve("colors")

  data class ThemeGuiData(val guiName: String, val folderName: String)

  fun themeGuiReading(): MutableList<ThemeGuiData> {
    val guiArray = mutableListOf(ThemeGuiData("None", "none"))

    File(THEME_GUI.path).listFiles.forEach { folder ->
      if (folder.isDirectory) {
        val guiInit = File(folder, "gui-init.txt")

        if (guiInit.exists) {
          val initGuiName = guiInit.readStrings() // Читаем содержимое файла и убираем лишние пробелы

          guiArray.add(ThemeGuiData(initGuiName.toString(), folder.name))
          HEAddon.LOGGER.debug("[THEME] GUI Loaded: {}", guiArray.joinToString(", "))
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
            HEAddon.LOGGER.debug("[THEME] Packs Loaded: {}", packsArray.joinToString(", "))
          }
        }
      }
    } else packsArray += "Packs not found"
    return packsArray.filter { it.isNotEmpty() }.toTypedArray()
  }

  fun themeColorsReading(guiSelectID: String, selectGuiID: Int): Array<String> {
    var colorsArray = arrayOf("Default")

    if(guiSelectID == "none" && selectGuiID >= 1) {
      val colorDir = File(THEME_COLORS.path)

      if(colorDir.exists && colorDir.isDirectory) {
        colorsArray = colorDir.listFiles
      }
    } else colorsArray += "Colors not found"
    return colorsArray.filter { it.isNotEmpty() }.toTypedArray()
  }
}