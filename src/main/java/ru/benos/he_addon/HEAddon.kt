package ru.benos.he_addon

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
import ru.benos.he_addon.KeyBinds.initKeys
import ru.benos.he_addon.events.ClientEvents
import ru.benos.he_addon.events.ScreenEvents
import ru.benos.he_addon.gui.Config

@Mod(HEAddon.MODID)
class HEAddon {
    companion object {
        const val MODID: String = "he_addon"
        val LOGGER: Logger = LogUtils.getLogger()
    }

    init {
        val forgeBus = MinecraftForge.EVENT_BUS
        val modBus = thedarkcolour.kotlinforforge.forge.MOD_BUS

        forgeBus.register(this)
        LOGGER.info("'HollowEngine | ADDON' - loading...")

        modBus.addListener(::setup)
        modBus.addListener(::setupComplete)

        if(FMLEnvironment.dist.isClient) {
            LOGGER.info("'HollowEngine | ADDON' - start loading client...")

            forgeBus.register(KeyBinds)
            forgeBus.register(ClientEvents)
            forgeBus.register(ScreenEvents)

            forgeBus.addListener(ScreenEvents::onGuiOpen)
            forgeBus.addListener(ScreenEvents::onNpcToolGuiOpen)
            forgeBus.addListener(ScreenEvents::onNPCCreatorGuiOpen)

            initKeys()

            LOGGER.info("'HollowEngine | ADDON' - loading client complete.")
        }
        LOGGER.info("'HollowEngine | ADDON' - loading complete.")
    }

    private fun setup(event: FMLCommonSetupEvent) {
        LOGGER.info("[HEAddon] Start common setup...")
        Config.init()
        LOGGER.info("[HEAddon] Common setup completed.")
    }
    private fun setupComplete(event: FMLLoadCompleteEvent) {}

    @SubscribeEvent
    fun setupClient(event: FMLClientSetupEvent?) {
        LOGGER.info("[HEAddon] Starting client setup...")
        LOGGER.info("[HEAddon] Client setup completed.")
    }

    @SubscribeEvent
    fun startServer(event: ServerStartingEvent?) {
        LOGGER.info("[HEAddon] Server is starting.")
    }
}
