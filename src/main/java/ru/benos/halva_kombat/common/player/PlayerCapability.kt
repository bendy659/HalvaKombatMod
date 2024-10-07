package ru.benos.halva_kombat.common.player

import net.minecraftforge.common.capabilities.Capability
import net.minecraftforge.common.capabilities.CapabilityManager
import net.minecraftforge.common.capabilities.CapabilityToken
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent

object PlayerCapability {
  val PLAYER_DATA_CAPABILITY: Capability<IPlayerData> =
    CapabilityManager.get(object : CapabilityToken<IPlayerData>() {})

  fun register(e: RegisterCapabilitiesEvent) {
    e.register(IPlayerData::class.java)
  }
}