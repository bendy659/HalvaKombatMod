package ru.benos.kotloudron.registries

import net.minecraft.world.item.CreativeModeTab
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Items

val KOTLOUDRON_TAB = object: CreativeModeTab("kotloudron_tab") {
  override fun makeIcon(): ItemStack {
    return ItemStack(Items.AIR)
  }

}