package ru.benos.he_addon

import com.mojang.blaze3d.platform.InputConstants
import net.minecraft.client.KeyMapping
import net.minecraftforge.client.event.InputEvent
import net.minecraftforge.client.event.RegisterKeyMappingsEvent
import net.minecraftforge.eventbus.api.SubscribeEvent
import org.lwjgl.glfw.GLFW
import ru.benos.he_addon.HEAddon.Companion.MODID
import ru.benos.he_addon.events.ScreenEvents.isControlScreenOpen
import ru.benos.he_addon.gui.Config
import ru.hollowhorizon.hc.client.utils.open
import thedarkcolour.kotlinforforge.forge.MOD_BUS

object KeyBinds {
  const val HEADDON_CATEGORY = "key.categories.$MODID.keys"

  val OPEN_CONFIG = KeyMapping("key.$MODID.open_config", GLFW.GLFW_KEY_F12, HEADDON_CATEGORY)

  fun initKeys() {
    MOD_BUS.addListener { e: RegisterKeyMappingsEvent ->
      e.register(OPEN_CONFIG)
    }
  }

  @SubscribeEvent
  fun onKeyPressed(event: InputEvent.Key) {
    val key = InputConstants.getKey(
      event.key,
      event.scanCode
    )

    if(OPEN_CONFIG.isActiveAndMatches(key) && !isControlScreenOpen()) Config.open()
  }
}