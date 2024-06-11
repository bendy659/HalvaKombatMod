package ru.benos.he_addon

import com.mojang.logging.LogUtils
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.event.server.ServerStartingEvent
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent
import net.minecraftforge.fml.loading.FMLEnvironment
import org.slf4j.Logger
import ru.benos.he_addon.KeyBinds.initKeys
import ru.benos.he_addon.events.ClientEvents
import ru.benos.he_addon.events.ScreenEvents

@Mod(HEAddon.MODID)
class HEAddon {
    init {
        val forgeBus = MinecraftForge.EVENT_BUS
        forgeBus.addListener(::commonSetup)

        if(FMLEnvironment.dist.isClient) {
            forgeBus.register(KeyBinds)
            forgeBus.register(ClientEvents)
            forgeBus.register(ScreenEvents)

            forgeBus.addListener(ScreenEvents::onGuiOpen)
            forgeBus.addListener(::onClientSetup)

            initKeys()
        }
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
        val LOGGER: Logger = LogUtils.getLogger()
    }
}
