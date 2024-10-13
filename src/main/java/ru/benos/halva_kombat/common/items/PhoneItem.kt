package ru.benos.halva_kombat.common.items

import net.minecraft.client.Minecraft
import net.minecraft.sounds.SoundSource
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResultHolder
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Rarity
import net.minecraft.world.level.Level
import ru.benos.halva_kombat.HalvaKombat.Companion.LOGGER
import ru.benos.halva_kombat.HalvaKombat.Companion.debug
import ru.benos.halva_kombat.client.guis.PhoneMenu
import ru.benos.halva_kombat.common.registries.KOTLOUDRON_TAB
import ru.benos.halva_kombat.common.registries.Registries.PHONE_ON

class PhoneItem : Item(
  Properties()
    .tab(KOTLOUDRON_TAB)
    .stacksTo(1)
    .rarity(Rarity.EPIC)
    .fireResistant()
) {
  override fun use(pLevel: Level, pPlayer: Player, pUsedHand: InteractionHand): InteractionResultHolder<ItemStack> {
    if(pLevel.isClientSide()) {
      pLevel.playSound(pPlayer, pPlayer.blockPosition(), PHONE_ON.get(), SoundSource.PLAYERS, 1f, 1f)
      if(debug) LOGGER.debug("Phone on")
      PhoneItemData.setPlayerAndLevel(pPlayer, pLevel)
      Minecraft.getInstance().setScreen(PhoneMenu)
    }
    return super.use(pLevel, pPlayer, pUsedHand)
  }
}

object PhoneItemData {
  lateinit var pPlayer: Player
  lateinit var pLevel: Level

  fun setPlayerAndLevel(pPlayer: Player, pLevel: Level) {
    this.pPlayer = pPlayer
    this.pLevel = pLevel
  }

  fun getPlayer(): Player = pPlayer
  fun getLevel(): Level = pLevel
}