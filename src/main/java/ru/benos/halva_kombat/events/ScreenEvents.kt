package ru.benos.halva_kombat.events

import net.minecraft.commands.arguments.EntityArgument.getPlayer
import net.minecraftforge.client.event.ScreenEvent
import net.minecraftforge.eventbus.api.SubscribeEvent
import ru.benos.halva_kombat.client.guis.halva_kombat.Menus
import ru.benos.halva_kombat.client.guis.halva_kombat.PhoneMenu
import ru.benos.halva_kombat.client.guis.halva_kombat.menuSelected

object ScreenEvents {

  @SubscribeEvent
  fun onGuiClose(e: ScreenEvent.Closing) {
    val screen = e.screen is PhoneMenu

    if(screen && (menuSelected != Menus.LOCK_SCREEN || menuSelected != Menus.APPS))
      e.isCanceled = false
    else
      e.isCanceled = true
  }
}