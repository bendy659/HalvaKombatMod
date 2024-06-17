package ru.benos.he_addon.registries

import net.minecraftforge.registries.DeferredRegister
import net.minecraftforge.registries.ForgeRegistries
import ru.benos.he_addon.registries.blocks.BlockKotloudron
import ru.benos.he_addon.registries.items.ItemKotloudron
import thedarkcolour.kotlinforforge.forge.MOD_BUS

object HEAddonRegistries {
  val ITEMS = DeferredRegister.create(
    ForgeRegistries.ITEMS,
    "he_addon"
  )

  val BLOCKB = DeferredRegister.create(
    ForgeRegistries.BLOCKS,
    "he_addon"
  )

  val kotloudronItem = ITEMS.register("kotloudron", ::ItemKotloudron)

  val kotloudronBlock = BLOCKB.register("kotloudron", ::BlockKotloudron)

  fun init() {
    ITEMS.register(MOD_BUS)
    BLOCKB.register(MOD_BUS)
  }
}