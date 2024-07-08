package ru.benos.kotloudron

import net.minecraft.world.item.CreativeModeTab
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Items
import ru.benos.kotloudron.registries.KotloudronRegistries

val KOTLOUDRON_TAB = object: CreativeModeTab("kotloudron_tab") {
  override fun makeIcon(): ItemStack {
    return ItemStack(KotloudronRegistries.kotloudronBlock.get())
  }

}