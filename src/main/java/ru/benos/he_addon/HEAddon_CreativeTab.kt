package ru.benos.he_addon

import net.minecraft.client.Minecraft
import net.minecraft.world.item.CreativeModeTab
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Items
import ru.hollowhorizon.hollowengine.common.registry.ModItems

val HEADDON_TAB = object: CreativeModeTab("he_addon") {
  override fun makeIcon(): ItemStack {
    return ItemStack(Items.STICK)
  }

}