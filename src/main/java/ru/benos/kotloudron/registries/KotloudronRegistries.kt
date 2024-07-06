package ru.benos.kotloudron.registries

import net.minecraftforge.registries.DeferredRegister
import net.minecraftforge.registries.ForgeRegistries
import ru.benos.kotloudron.Kotloudron.Companion.MODID
import ru.benos.kotloudron.utils.DesingLogging.desingLogging
import thedarkcolour.kotlinforforge.forge.MOD_BUS

object KotloudronRegistries {
  val ITEMS = DeferredRegister.create(
    ForgeRegistries.ITEMS,
    MODID
  )

  val BLOCKS = DeferredRegister.create(
    ForgeRegistries.BLOCKS,
    MODID
  )

  fun init() {
    ITEMS.register(MOD_BUS)
    BLOCKS.register(MOD_BUS)

    desingLogging("REGISTRIES FINISH. REGISTERED: $ITEMS, $BLOCKS")
  }
}