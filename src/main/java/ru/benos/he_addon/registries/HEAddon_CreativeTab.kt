package ru.benos.he_addon.registries

import net.minecraft.world.item.CreativeModeTab
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Items

val HE_ADDON_TAB = object: CreativeModeTab("he_addon_tab") {
  override fun makeIcon(): ItemStack {
    return ItemStack(HEAddonRegistries.kotloudronItem.get())
  }

}