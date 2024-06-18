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
import ru.benos.kotloudron.events.ClientEvents
import ru.benos.kotloudron.events.ScreenEvents
import ru.benos.kotloudron.gui.Config
import ru.benos.kotloudron.registries.KotloudronRegistries

@Mod(Kotloudron.MODID)
class Kotloudron {
    companion object {
        const val MODID: String = "kotloudron"
        val LOGGER: Logger = LogUtils.getLogger()
    }

    init {
        val forgeBus = MinecraftForge.EVENT_BUS
        val modBus = thedarkcolour.kotlinforforge.forge.MOD_BUS

        LOGGER.info("'[Kotloudron]' loading...")

        forgeBus.register(KotloudronRegistries)

        modBus.addListener(::setup)
        if(FMLEnvironment.dist.isClient) {
            LOGGER.info("'[Kotloudron]' start loading client...")

            forgeBus.register(KeyBinds)
            forgeBus.register(ClientEvents)
            forgeBus.register(ScreenEvents)
            forgeBus.register(Config)

            forgeBus.addListener(ScreenEvents::onGuiOpen)
            forgeBus.addListener(ScreenEvents::onNpcToolGuiOpen)
            forgeBus.addListener(ScreenEvents::onNPCCreatorGuiOpen)

            initKeys()

            LOGGER.info("'[Kotloudron]' loading client complete.")
        }
        KotloudronRegistries.init()

        modBus.addListener(::setupComplete)

        forgeBus.register(this)
        LOGGER.info("'[Kotloudron]' loading complete.")
    }

    private fun setup(event: FMLCommonSetupEvent) {
        LOGGER.info("[Kotloudron] Start common setup...")
        Config.init()
        LOGGER.info("[Kotloudron] Common setup completed.")
    }
    private fun setupComplete(event: FMLLoadCompleteEvent) {}

    @SubscribeEvent
    fun setupClient(event: FMLClientSetupEvent?) {
        LOGGER.info("[Kotloudron] Starting client setup...")
        LOGGER.info("[Kotloudron] Client setup completed.")
    }

    @SubscribeEvent
    fun startServer(event: ServerStartingEvent?) {
        LOGGER.info("[Kotloudron] Server is starting.")
    }
}
