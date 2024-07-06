package ru.benos.kotloudron

import com.mojang.logging.LogUtils
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.event.server.ServerStartingEvent
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent
import net.minecraftforge.fml.loading.FMLEnvironment
import org.slf4j.Logger
import ru.benos.kotloudron.KeyBinds.initKeys
import ru.benos.kotloudron.events.ScreenEvents
import ru.benos.kotloudron.utils.DesingLogging.desingLogging

@Mod(Kotloudron.MODID)
class Kotloudron {
    val forgeBus = MinecraftForge.EVENT_BUS
    val modBus = thedarkcolour.kotlinforforge.forge.MOD_BUS

    init {
      LOGGER.info(desingLogging("STARTING INSTALL"))

      modBus.addListener(::setup)
      modBus.addListener(::setupComplete)

      if(FMLEnvironment.dist.isClient) {
        LOGGER.info(desingLogging("CLIENT SETUP STARTING"))

        forgeBus.register(KeyBinds)
        forgeBus.register(ScreenEvents)

        forgeBus.addListener(ScreenEvents::onGuiOpen)
        forgeBus.addListener(ScreenEvents::onNpcToolGuiOpen)
        forgeBus.addListener(ScreenEvents::onNPCCreatorGuiOpen)

        initKeys()

        LOGGER.info(desingLogging("CLIENT SETUP COMPLETE"))
      }

      LOGGER.info(desingLogging("INSTALL COMPLETE"))
      forgeBus.register(this)
    }

    private fun setup(event: FMLCommonSetupEvent) {
      LOGGER.info(desingLogging("SETUP STARTING"))
      LOGGER.info(desingLogging("SETUP COMPLETE"))
    }
    private fun setupComplete(event: FMLLoadCompleteEvent) {}

    @SubscribeEvent
    fun setupClient(event: FMLClientSetupEvent?) {}

    @SubscribeEvent
    fun startServer(event: ServerStartingEvent?) {
        LOGGER.info(desingLogging("SERVER IS STARTING"))
    }

    companion object {
        const val MODID: String = "kotloudron"
        val LOGGER: Logger = LogUtils.getLogger()
    }
}
