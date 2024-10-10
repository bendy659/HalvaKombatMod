package ru.benos.halva_kombat

import com.mojang.logging.LogUtils
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.loading.FMLEnvironment
import org.slf4j.Logger
import ru.benos.halva_kombat.common.registries.Registries
import ru.benos.halva_kombat.events.ScreenEvents
import ru.benos.halva_kombat.events.ScreenEvents.onGuiClose
import ru.benos.halva_kombat.utils.DesingLogging.desingLogging

@Mod(HalvaKombat.MODID)
class HalvaKombat {
  val forgeBus = MinecraftForge.EVENT_BUS
  val modBus = thedarkcolour.kotlinforforge.forge.MOD_BUS

  init {
    LOGGER.info(desingLogging("STARTING INSTALL"))

    forgeBus.register(Registries); Registries.init()

    if (FMLEnvironment.dist.isClient) {
      forgeBus.register(ScreenEvents)
      forgeBus.addListener(::onGuiClose)
    }

    LOGGER.info(desingLogging("INSTALL COMPLETE"))
    forgeBus.register(this)
  }

  companion object {
    const val MODID: String = "halva_kombat"
    val LOGGER: Logger = LogUtils.getLogger()
    val debug = true
  }
}