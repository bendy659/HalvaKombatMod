package ru.benos.kotloudron.registries

import net.minecraftforge.registries.DeferredRegister
import net.minecraftforge.registries.ForgeRegistries
import ru.benos.kotloudron.registries.blocks.BlockKotloudron
import ru.benos.kotloudron.registries.items.ItemKotloudron
import thedarkcolour.kotlinforforge.forge.MOD_BUS

object KotloudronRegistries {
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