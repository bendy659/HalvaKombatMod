package ru.benos.kotloudron

import com.mojang.blaze3d.platform.InputConstants
import net.minecraft.client.KeyMapping
import net.minecraftforge.client.event.InputEvent
import net.minecraftforge.client.event.RegisterKeyMappingsEvent
import net.minecraftforge.eventbus.api.SubscribeEvent
import org.lwjgl.glfw.GLFW
import ru.benos.kotloudron.Kotloudron.Companion.MODID
import ru.benos.kotloudron.events.ScreenEvents.screenOpenType
import thedarkcolour.kotlinforforge.forge.MOD_BUS

object KeyBinds {
  const val KOTLOUDRON_CATEGORY = "key.categories.$MODID.keys"
  
  val KEYBIND_CONFIG = KeyMapping("key.$MODID.open_config", GLFW.GLFW_KEY_F12, KOTLOUDRON_CATEGORY)
  val KEYBIND_THEME_SELECT = KeyMapping("key.$MODID.open_theme_select", GLFW.GLFW_KEY_RIGHT_BRACKET, KOTLOUDRON_CATEGORY)

  fun initKeys() {
    MOD_BUS.addListener { e: RegisterKeyMappingsEvent ->
      e.register(KEYBIND_CONFIG)
      e.register(KEYBIND_THEME_SELECT)
    }
  }

  @SubscribeEvent
  fun onKeyPressed(event: InputEvent.Key) {
    val key = InputConstants.getKey(
      event.key,
      event.scanCode
    )

    if(KEYBIND_CONFIG.isActiveAndMatches(key) && screenOpenType == 0) {}
    if(KEYBIND_THEME_SELECT.isActiveAndMatches(key) && screenOpenType == 0) {}
  }
}