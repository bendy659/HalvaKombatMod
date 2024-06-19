package ru.benos.kotloudron.registries.items

import net.minecraft.network.chat.Component
import net.minecraft.world.damagesource.DamageSource
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.*
import net.minecraft.world.level.Level
import ru.benos.kotloudron.Kotloudron.Companion.MODID
import ru.benos.kotloudron.registries.KOTLOUDRON_TAB

class ItemKotloudroniumSword: SwordItem(
  Tiers.NETHERITE,
  999999999,
  1.0E38f,
  Properties()
    .tab(KOTLOUDRON_TAB)
    .stacksTo(1)
    .rarity(Rarity.EPIC)
    .fireResistant()
) {
  override fun hurtEnemy(pStack: ItemStack, pTarget: LivingEntity, pAttacker: LivingEntity): Boolean {
    pTarget.hurt(DamageSource.playerAttack(pAttacker as Player), 1000000f)
    return super.hurtEnemy(pStack, pTarget, pAttacker)
  }

  override fun appendHoverText(pStack: ItemStack, pLevel: Level?, pTooltipComponents: MutableList<Component>, pIsAdvanced: TooltipFlag) {
    super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced)
    pTooltipComponents.add(Component.translatable("item.$MODID.kotloudronium_sword_lore"))
  }
}