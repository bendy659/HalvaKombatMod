package ru.benos.halva_kombat.common.registries

import net.minecraft.world.item.CreativeModeTab
import net.minecraft.world.item.ItemStack
import ru.benos.halva_kombat.HalvaKombat.Companion.MODID
import ru.benos.halva_kombat.common.registries.Registries.PHONE_ITEM

val KOTLOUDRON_TAB = object: CreativeModeTab("${MODID}_tab") {
  override fun makeIcon(): ItemStack {
    return ItemStack(PHONE_ITEM.get())
  }

}