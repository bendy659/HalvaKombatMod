package ru.benos.halva_kombat.common.items

import net.minecraft.client.Minecraft
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResultHolder
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Rarity
import net.minecraft.world.level.Level
import ru.benos.halva_kombat.client.guis.halva_kombat.PhoneMenu
import ru.benos.halva_kombat.common.registries.KOTLOUDRON_TAB

class PhoneItem : Item(
  Properties()
    .tab(KOTLOUDRON_TAB)
    .stacksTo(1)
    .rarity(Rarity.EPIC)
    .fireResistant()
) {
  override fun use(pLevel: Level, pPlayer: Player, pUsedHand: InteractionHand): InteractionResultHolder<ItemStack> {
    if(pLevel.isClientSide()) Minecraft.getInstance().setScreen(PhoneMenu(pPlayer))

    return super.use(pLevel, pPlayer, pUsedHand)
  }
}