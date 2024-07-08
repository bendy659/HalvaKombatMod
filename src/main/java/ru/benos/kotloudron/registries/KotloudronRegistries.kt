package ru.benos.kotloudron.registries

import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraftforge.registries.DeferredRegister
import net.minecraftforge.registries.ForgeRegistries
import ru.benos.kotloudron.Kotloudron.Companion.MODID
import ru.benos.kotloudron.blocks.KotloudronBlock
import ru.benos.kotloudron.blocks.PlushMeBlock
import ru.benos.kotloudron.blocks.PlushMeBlockEntity
import ru.benos.kotloudron.items.KotloudroniumSword
import ru.benos.kotloudron.items.PlushMeBlockItem
import ru.benos.kotloudron.utils.DesingLogging.desingLogging
import thedarkcolour.kotlinforforge.forge.MOD_BUS

object KotloudronRegistries {
  val ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID)
  val BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID)
  val BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, MODID)

  val kotloudronium_sword = ITEMS.register("kotloudronium_sword", ::KotloudroniumSword)

  val kotloudronBlock = BLOCKS.register("kotloudron", ::KotloudronBlock)

  val plushMeItem = ITEMS.register("plush_bendy659_", ::PlushMeBlockItem)
  val plushMeBlock = BLOCKS.register("plush_bendy659_", ::PlushMeBlock)
  val plusMeBlockEntity = BLOCK_ENTITIES.register("plush_bendy659_") {
    BlockEntityType.Builder.of(::PlushMeBlockEntity, plushMeBlock.get()).build(null)
  }

  fun init() {
    ITEMS.register(MOD_BUS)
    BLOCKS.register(MOD_BUS)
    BLOCK_ENTITIES.register(MOD_BUS)

    desingLogging("REGISTRIES FINISH. REGISTERED: $ITEMS, $BLOCKS, $BLOCK_ENTITIES")
  }
}