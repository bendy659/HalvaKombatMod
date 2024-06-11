package ru.benos.he_addon.events

import net.minecraft.client.gui.screens.controls.ControlsScreen
import net.minecraftforge.client.event.ScreenEvent
import net.minecraftforge.eventbus.api.SubscribeEvent
import ru.benos.he_addon.HEAddon
import ru.benos.he_addon.gui.Config.getConfig
import ru.benos.he_addon.gui.NPCToolGUI
import ru.hollowhorizon.hc.client.utils.open
import ru.hollowhorizon.hollowengine.client.gui.NPCToolGui

object ScreenEvents {

  var screenOpenType = 0
  /*
   * 0 = Not opened. default
   * 1 - Control menu
   * 2 - NPC tool menu
  */

  @SubscribeEvent
  fun onGuiOpen(e: ScreenEvent.Opening) {
    val screen = e.screen

    if(screen is ControlsScreen) screenOpenType = 1
    else screenOpenType = 0

    if(screen is NPCToolGui) {
      val isOldMenu = getConfig("openOldMenu")

      if(isOldMenu is Boolean && isOldMenu) {
        val npcData = (screen as NPCToolGui).npc
        NPCToolGUI(npcData).open()
      }
    }
  }

  fun isControlScreenOpen(): Boolean {
    if(screenOpenType == 1) {
      HEAddon.LOGGER.debug("Control screen is open")
      return true
    } else return false
  }
}