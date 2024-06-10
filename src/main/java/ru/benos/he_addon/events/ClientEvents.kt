package ru.benos.he_addon.events

import net.minecraft.client.gui.Gui
import net.minecraft.client.gui.screens.controls.ControlsScreen
import net.minecraftforge.client.event.ScreenEvent
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.Mod.EventBusSubscriber
import ru.hollowhorizon.hollowengine.client.gui.NPCToolGui

@EventBusSubscriber
object ClientEvents {

  var screenOpenType = 0
  /*
   * 0 = Not opened. default
   * 1 - Control screen
   * 2 - NPC tool menu
  */

  @SubscribeEvent
  fun onGuiOpen(e: ScreenEvent.Opening) {
    val screen = e.screen

    if(screen is ControlsScreen) screenOpenType = 1
    else if(screen is NPCToolGui) screenOpenType = 2
    else screenOpenType = 0
  }

  fun isControlScreenOpen() = screenOpenType == 1
  fun isNpcToolGuiScreenOpen() = screenOpenType == 2
}