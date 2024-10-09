package ru.benos.halva_kombat.common.registries

import net.minecraft.resources.ResourceLocation
import net.minecraft.sounds.SoundEvent
import net.minecraftforge.registries.DeferredRegister
import net.minecraftforge.registries.ForgeRegistries
import net.minecraftforge.versions.forge.ForgeVersion.MOD_ID
import ru.benos.halva_kombat.HalvaKombat.Companion.MODID
import ru.benos.halva_kombat.common.items.PhoneItem
import thedarkcolour.kotlinforforge.forge.MOD_BUS

object Registries {
  val ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID)
  val BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID)
  val BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, MODID)
  val SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, MODID)

  val PHONE_ITEM = ITEMS.register("phone", ::PhoneItem)

  val CLICK_COIN = SOUNDS.register("click_coin") { SoundEvent(ResourceLocation(MOD_ID, "gui.coin_click")) }
  val UPGRADE_POINT = SOUNDS.register("upgrade_point") { SoundEvent(ResourceLocation(MOD_ID, "gui.upgrade_point")) }
  val MONEYS_500K = SOUNDS.register("500k_money") { SoundEvent(ResourceLocation(MOD_ID, "gui.500k_money")) }
  val MONEYS_2M = SOUNDS.register("2m_money") { SoundEvent(ResourceLocation(MOD_ID, "gui.2m_money")) }
  val DROP_COIN = SOUNDS.register("drop_coin") { SoundEvent(ResourceLocation(MOD_ID, "gui.coin_drop")) }
  val BUY = SOUNDS.register(("buy")) { SoundEvent(ResourceLocation(MOD_ID, "gui.buy")) }

  fun init() {
    ITEMS.register(MOD_BUS)
    BLOCKS.register(MOD_BUS)
    BLOCK_ENTITIES.register(MOD_BUS)
  }
}