package ru.benos.kotloudron.items

import net.minecraft.world.item.Items
import net.minecraft.world.item.Tier
import net.minecraft.world.item.crafting.Ingredient
import kotlin.reflect.jvm.internal.impl.descriptors.Visibilities.Private

enum class Tiers (
  private val level: Int,
  private val uses: Int,
  private val speed: Float,
  private val damage: Float,
  private val enchantmentValue: Int,
  private val repairIngredient: () -> Ingredient
): Tier {
  KOTLOUDRONIUM(0, 0, Float.MAX_VALUE, Float.MAX_VALUE, Int.MAX_VALUE, { Ingredient.of(Items.BEDROCK) });

  override fun getLevel(): Int = level
  override fun getUses(): Int = uses
  override fun getSpeed(): Float = speed
  override fun getAttackDamageBonus(): Float = damage
  override fun getEnchantmentValue(): Int = enchantmentValue
  override fun getRepairIngredient(): Ingredient = repairIngredient.invoke()
}