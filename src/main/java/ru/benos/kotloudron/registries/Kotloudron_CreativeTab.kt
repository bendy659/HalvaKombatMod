package ru.benos.kotloudron.registries

import net.minecraft.world.item.CreativeModeTab
import net.minecraft.world.item.ItemStack

val KOTLOUDRON_TAB = object: CreativeModeTab("kotloudron_tab") {
  override fun makeIcon(): ItemStack {
    return ItemStack(KotloudronRegistries.kotloudronItem.get())
  }

}