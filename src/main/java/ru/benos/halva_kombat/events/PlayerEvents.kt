package ru.benos.halva_kombat.events

import net.minecraftforge.event.entity.player.PlayerEvent
import net.minecraftforge.eventbus.api.SubscribeEvent
import ru.benos.halva_kombat.HalvaKombat.Companion.LOGGER
import ru.benos.halva_kombat.client.guis.menus.apps.GemtapMenuApp

object PlayerEvents {
  @SubscribeEvent
  fun onPlayerLoggenIn(e: PlayerEvent.PlayerLoggedInEvent) {
    LOGGER.debug("Player {} Logged in. Reading datas from \"GemtapApp.data.latest\"...", e.entity)
    //GemtapMenuApp.readData()
  }

  @SubscribeEvent
  fun onPlayerLoggenOut(e: PlayerEvent.PlayerLoggedOutEvent) {
    LOGGER.debug("Player {} Logged out. Save datas to \"GemtapApp.data.latest\"...", e.entity)
    //GemtapMenuApp.saveData()
  }
}