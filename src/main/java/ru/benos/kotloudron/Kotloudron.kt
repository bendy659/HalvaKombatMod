package ru.benos.kotloudron

import com.mojang.logging.LogUtils
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.loading.FMLEnvironment
import org.slf4j.Logger
import ru.benos.kotloudron.KeyBinds.initKeys
import ru.benos.kotloudron.blocks.PlushMeBlockEntity
import ru.benos.kotloudron.events.BlockEvents
import ru.benos.kotloudron.events.ScreenEvents
import ru.benos.kotloudron.registries.KotloudronRegistries
import ru.benos.kotloudron.utils.DesingLogging.desingLogging

@Mod(Kotloudron.MODID)
class Kotloudron {
    val forgeBus = MinecraftForge.EVENT_BUS
    val modBus = thedarkcolour.kotlinforforge.forge.MOD_BUS

    init {
      LOGGER.info(desingLogging("STARTING INSTALL"))

      forgeBus.register(KotloudronRegistries)
      KotloudronRegistries.init()

      forgeBus.addListener(BlockEvents::blockReplaceCauldronToKotloudronAndBack)

      if(FMLEnvironment.dist.isClient) {
        forgeBus.register(KeyBinds)
        forgeBus.register(ScreenEvents)

        forgeBus.addListener(ScreenEvents::onGuiOpen)
        forgeBus.addListener(ScreenEvents::onNpcToolGuiOpen)
        forgeBus.addListener(ScreenEvents::onNPCCreatorGuiOpen)

        initKeys()
      }

      LOGGER.info(desingLogging("INSTALL COMPLETE"))
      forgeBus.register(this)
    }

    companion object {
      const val MODID: String = "kotloudron"
      val LOGGER: Logger = LogUtils.getLogger()
      val debug = true
    }
}
