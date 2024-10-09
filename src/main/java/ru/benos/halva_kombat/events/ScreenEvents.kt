package ru.benos.halva_kombat.events

import net.minecraftforge.client.event.ScreenEvent
import net.minecraftforge.eventbus.api.SubscribeEvent

object ScreenEvents {

  @SubscribeEvent
  fun onGuiOpen(e: ScreenEvent.Opening) {
    val screen = e.screen


  }
}