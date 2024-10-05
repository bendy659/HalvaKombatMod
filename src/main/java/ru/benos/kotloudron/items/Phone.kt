package ru.benos.kotloudron.items

import net.minecraft.client.Minecraft
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResultHolder
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Rarity
import net.minecraft.world.level.Level
import ru.benos.kotloudron.KOTLOUDRON_TAB
import ru.benos.kotloudron.guis.halva_kombat.HalvaKombatMenu

class Phone: Item(
  Item.Properties()
    .tab(KOTLOUDRON_TAB)
    .stacksTo(1)
    .rarity(Rarity.EPIC)
    .fireResistant()
) {
  override fun use(pLevel: Level, pPlayer: Player, pUsedHand: InteractionHand): InteractionResultHolder<ItemStack> {
    if(pLevel.isClientSide()) {
      Minecraft.getInstance().setScreen(HalvaKombatMenu())
    }

    return super.use(pLevel, pPlayer, pUsedHand)
  }
}