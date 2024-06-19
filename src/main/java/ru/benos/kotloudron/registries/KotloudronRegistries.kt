package ru.benos.kotloudron.registries

import net.minecraft.world.level.block.Block
import net.minecraftforge.eventbus.EventBus
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.registries.DeferredRegister
import net.minecraftforge.registries.ForgeRegistries
import net.minecraftforge.registries.ForgeRegistry
import net.minecraftforge.registries.RegisterEvent
import net.minecraftforge.registries.RegistryObject
import ru.benos.kotloudron.Kotloudron.Companion.MODID
import ru.benos.kotloudron.registries.blocks.BlockKotloudron
import ru.benos.kotloudron.registries.items.ItemKotloudron
import ru.benos.kotloudron.registries.items.ItemKotloudroniumSword
import ru.benos.kotloudron.utils.DesingLogging.desingLogging
import ru.hollowhorizon.hollowengine.common.scripting.story.ForgeEvent
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

  val kotloudronItem = ITEMS.register("kotloudron", ::ItemKotloudron)
  val kotloudronium_sword = ITEMS.register("kotloudronium_sword", ::ItemKotloudroniumSword)

  val kotloudronBlock = BLOCKS.register("kotloudron", ::BlockKotloudron)

  fun init() {
    ITEMS.register(MOD_BUS)
    BLOCKS.register(MOD_BUS)

    desingLogging("REGISTRIES FINISH. REGISTERED: $ITEMS, $BLOCKS")
  }
}