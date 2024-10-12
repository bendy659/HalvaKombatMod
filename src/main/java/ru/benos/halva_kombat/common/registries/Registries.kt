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

  val CLICK_COIN = SOUNDS.register("coin_click") { SoundEvent(ResourceLocation(MOD_ID, "coin_click")) }
  val UPGRADE_POINT = SOUNDS.register("upgrade_point") { SoundEvent(ResourceLocation(MOD_ID, "upgrade_point")) }
  val MONEYS_500K = SOUNDS.register("500k_money") { SoundEvent(ResourceLocation(MOD_ID, "500k_money")) }
  val MONEYS_2M = SOUNDS.register("2m_money") { SoundEvent(ResourceLocation(MOD_ID, "2m_money")) }
  val DROP_COIN = SOUNDS.register("coin_drop") { SoundEvent(ResourceLocation(MOD_ID, "coin_drop")) }
  val BUY = SOUNDS.register("buy") { SoundEvent(ResourceLocation(MOD_ID, "buy")) }
  val PHONE_OFF = SOUNDS.register("phone_off") { SoundEvent(ResourceLocation(MOD_ID, "phone_off")) }
  val PHONE_ON = SOUNDS.register("phone_on") { SoundEvent(ResourceLocation(MOD_ID, "phone_on")) }

  fun init() {
    ITEMS.register(MOD_BUS)
    BLOCKS.register(MOD_BUS)
    BLOCK_ENTITIES.register(MOD_BUS)
    SOUNDS.register(MOD_BUS)
  }
}