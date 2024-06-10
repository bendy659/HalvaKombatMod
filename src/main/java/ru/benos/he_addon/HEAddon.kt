package ru.benos.he_addon

import com.mojang.logging.LogUtils
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.event.server.ServerStartingEvent
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent
import net.minecraftforge.registries.ForgeDeferredRegistriesSetup.setup
import org.slf4j.Logger

@Mod(HEAddon.MODID)
class HEAddon {
    init {
        val forgeBus = MinecraftForge.EVENT_BUS
        forgeBus.addListener(::commonSetup)
        forgeBus.register(this)
    }

    private fun commonSetup(event: FMLCommonSetupEvent) {
        LOGGER.info("Common setup completed.")
    }

    @SubscribeEvent
    fun onServerStarting(event: ServerStartingEvent?) {
        LOGGER.info("Server is starting.")
    }

    @SubscribeEvent
    fun onClientSetup(event: FMLClientSetupEvent?) {
        LOGGER.info("Client setup completed.")
    }

    companion object {
        const val MODID: String = "he_addon"
        private val LOGGER: Logger = LogUtils.getLogger()
    }
}
