package ru.benos.he_addon.events

import net.minecraft.client.Minecraft
import net.minecraft.client.gui.screens.ChatScreen
import net.minecraft.client.gui.screens.controls.KeyBindsScreen
import net.minecraftforge.client.event.ScreenEvent
import net.minecraftforge.event.TickEvent
import net.minecraftforge.eventbus.api.SubscribeEvent
import ru.benos.he_addon.HEAddon
import ru.benos.he_addon.gui.Config.getConfig
import ru.benos.he_addon.gui.NPCToolGUI
import ru.benos.he_addon.gui.HEAddon_NPCCreatorGUI
import ru.hollowhorizon.hollowengine.client.gui.NPCCreatorGui
import ru.hollowhorizon.hollowengine.client.gui.NPCToolGui

object ScreenEvents {

  var screenOpenType = 0
  /*
   * 0 = Not opened. default
   * 1 - Control menu
   * 2 - Chat menu
  */

  var npcCreatorGuiOpenThisNpcToolGui = false

  @SubscribeEvent
  fun onGuiOpen(e: ScreenEvent.Opening) {
    val screen = e.screen

    if(screen is KeyBindsScreen) screenOpenType = 1 else screenOpenType = 0
    if(screen is ChatScreen) screenOpenType = 2 else screenOpenType = 0
  }

  @SubscribeEvent
  fun onNpcToolGuiOpen(e: TickEvent.ClientTickEvent) {
    if(e.phase == TickEvent.Phase.END) {
      val screen = Minecraft.getInstance().screen

      if (screen is NPCToolGui) {
        HEAddon.LOGGER.debug("Ths NPCToolGui open")
        Minecraft.getInstance().setScreen(NPCToolGUI(screen.npc))
        this.npcCreatorGuiOpenThisNpcToolGui = true
      } else npcCreatorGuiOpenThisNpcToolGui = false
    }
  }

  @SubscribeEvent
  fun onNPCCreatorGuiOpen(e: TickEvent.ClientTickEvent) {
      val screen = Minecraft.getInstance().screen
      val config = getConfig("openOldMenu")

      if(screen is NPCCreatorGui) {
        if(!npcCreatorGuiOpenThisNpcToolGui && config is Boolean && !config)
          Minecraft.getInstance().setScreen(HEAddon_NPCCreatorGUI(screen.npc, screen.npc.id, false))

        HEAddon.LOGGER.debug("Open old 'NPCCreator' menu: $config")
      }
  }
}