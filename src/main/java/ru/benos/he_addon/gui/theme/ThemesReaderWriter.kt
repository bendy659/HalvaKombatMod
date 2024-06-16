package ru.benos.he_addon.gui.theme

import com.sun.jna.StringArray
import net.minecraft.client.Minecraft
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.packs.resources.ResourceManager
import net.minecraft.world.entity.vehicle.Minecart
import org.jetbrains.kotlin.codegen.intrinsics.ArrayOf
import org.jetbrains.kotlin.konan.file.File
import ru.benos.he_addon.HEAddon
import ru.hollowhorizon.hc.client.utils.stream
import ru.hollowhorizon.hc.common.ui.gui
import ru.hollowhorizon.hollowengine.common.files.DirectoryManager.HOLLOW_ENGINE
import ru.hollowhorizon.repack.gnu.trove.THashMap
import java.io.IOException

object ThemesReaderWriter {
  val THEME_DIR = HOLLOW_ENGINE.resolve("themes")
  val THEME_GUI = THEME_DIR.resolve("gui")

  fun themeGuiReading(): Array<String> {
    var guiArray = arrayOf("NONE")

    File(THEME_GUI.path).listFiles.forEach { folder ->
      if(folder.isDirectory) {
        val gui_init = File(folder, "gui-init.txt")

        if(gui_init.exists) {
          val init_gui_name = gui_init.readStrings()

          guiArray += init_gui_name
          HEAddon.LOGGER.debug("[THEME] GUI Loaded: {}", guiArray.joinToString(", "))
        }
      }
    }
    return guiArray
  }

  //fun themesPacksReading(): Array<String> {
  //
  //}

  //fun themeColorsReading(): Array<String> {
  //
  //}
}