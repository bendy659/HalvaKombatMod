package ru.benos.halva_kombat.events

import net.minecraftforge.client.event.ScreenEvent
import net.minecraftforge.eventbus.api.SubscribeEvent
import ru.benos.halva_kombat.client.guis.PhoneMenu
import ru.benos.halva_kombat.client.guis.menus.AppsMenu.appID

object ScreenEvents {

  // Is not working... :(
  @SubscribeEvent
  fun onGuiClose(e: ScreenEvent.Closing) {
    val screen = e.screen is PhoneMenu

    if(screen && appID.get() != 0) {
      e.isCanceled = false
      appID.set(0)
    } else e.isCanceled = true
  }
}