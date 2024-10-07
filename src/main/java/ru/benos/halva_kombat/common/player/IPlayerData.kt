package ru.benos.halva_kombat.common.player

import imgui.type.ImInt
import net.minecraft.nbt.CompoundTag

interface IPlayerData {
  var score: ImInt
  var nextScore: ImInt
  var pointsUpgrade: ImInt
  var perClick: ImInt
  var clickUpgradeCost: ImInt

  fun saveNbtData(compound: CompoundTag)
  fun loadNbtData(compound: CompoundTag)
}