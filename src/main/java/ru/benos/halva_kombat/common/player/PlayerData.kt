package ru.benos.halva_kombat.common.player

import imgui.type.ImInt
import net.minecraft.nbt.CompoundTag

class PlayerData : IPlayerData {
  override var score: ImInt = ImInt(0)
  override var nextScore: ImInt = ImInt(2500)
  override var pointsUpgrade: ImInt = ImInt(0)
  override var perClick: ImInt = ImInt(1)
  override var clickUpgradeCost: ImInt = ImInt(900)

  override fun saveNbtData(compound: CompoundTag) {
    compound.putInt("halva_kombat.score", score.get())
    compound.putInt("halva_kombat.nextScore", nextScore.get())
    compound.putInt("halva_kombat.pointsUpgrade", pointsUpgrade.get())
    compound.putInt("halva_kombat.perClick", perClick.get())
    compound.putInt("halva_kombat.clickUpgradeCost", clickUpgradeCost.get())
  }

  override fun loadNbtData(compound: CompoundTag) {
    score.set(compound.getInt("halva_kombat.score"))
    nextScore.set(compound.getInt("halva_kombat.nextScore"))
    pointsUpgrade.set(compound.getInt("halva_kombat.pointsUpgrade"))
    perClick.set(compound.getInt("halva_kombat.perClick"))
    clickUpgradeCost.set(compound.getInt("halva_kombat.clickUpgradeCost"))
  }
}