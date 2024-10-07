package ru.benos.halva_kombat.client.guis.halva_kombat

import imgui.type.ImInt
import net.minecraft.world.entity.player.Player

data object HalvaKombatData {
  var score: ImInt = ImInt(0)
  var nextScore: ImInt = ImInt(2500)
  var pointsUpgrade: ImInt = ImInt(0)
  var perClick: ImInt = ImInt(1)
  var clickUpgradeCost: ImInt = ImInt(900)
}

class HalvaKombatDataWork(pPlayer: Player) {
  fun saveData() {

  }

  fun readData(): Array<Unit> {

  }
}