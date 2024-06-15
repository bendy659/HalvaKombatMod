package ru.benos.he_addon.gui.theme

import com.sun.jna.StringArray
import net.minecraft.client.Minecraft
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.packs.resources.ResourceManager
import net.minecraft.world.entity.vehicle.Minecart
import org.jetbrains.kotlin.codegen.intrinsics.ArrayOf
import ru.benos.he_addon.HEAddon
import java.io.File

object ThemesReaderWriter {

  fun themeGuiReading(): Array<String> {
    val folders = ResourceLocation("he_addon", "theme/gui").path
    val resource = Minecraft.getInstance().resourceManager.listResources(folders) { true }
    var guiArray = arrayOf("NONE")

    try {
      for(folder in resource) {
        val folderName = folder.key.path
        if(folderName.isNotEmpty()) guiArray += folderName

      }
    } catch(e: Exception) {
      e.printStackTrace()
    }
    HEAddon.LOGGER.debug(guiArray.joinToString())
    return guiArray
  }

  //fun themesPacksReading(): Array<String> {
  //
  //}

  //fun themeColorsReading(): Array<String> {
  //
  //}
}