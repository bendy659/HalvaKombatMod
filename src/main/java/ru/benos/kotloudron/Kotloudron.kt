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
    val forgeBus = MinecraftForge.EVENT_BUS
    val modBus = thedarkcolour.kotlinforforge.forge.MOD_BUS

    init {
        forgeBus.register(this)

        LOGGER.info(
            """
            / =========================== \
            |         [KOTLOUDRON]         |
            | ---------------------------- |
            |         INSTALLING...        |
            \ =========================== /
            """.trimIndent()
        )

        forgeBus.register(KotloudronRegistries)

        modBus.addListener(::setup)
        if(FMLEnvironment.dist.isClient) {
            forgeBus.addListener(::setupClient)
        }
        KotloudronRegistries.init()

        modBus.addListener(::setupComplete)

        LOGGER.info(
            """
            / ============================== \
            |          [KOTLOUDRON]          |
            | ------------------------------ |
            |      INSTALLING COMPLETE       |
            \ ============================== /
            """.trimIndent()
        )
    }

    private fun setup(event: FMLCommonSetupEvent) {
        LOGGER.info(
            """
            / ============================== \
            |          [KOTLOUDRON]          |
            | ------------------------------ |
            |           SETUP START          |
            \ ============================== /
            """.trimIndent()
        )
        LOGGER.info(
            """
            / ============================== \
            |          [KOTLOUDRON]          |
            | ------------------------------ |
            |         SETUP COMPLETE         |
            \ ============================== /
            """.trimIndent()
        )
    }
    private fun setupComplete(event: FMLLoadCompleteEvent) {}

    @SubscribeEvent
    fun setupClient(event: FMLClientSetupEvent?) {
        LOGGER.info(
                """
                / ============================== \
                |          [KOTLOUDRON]          |
                | ------------------------------ |
                |      START CLIENT SETUP...     |
                \ ============================== /
                """.trimIndent()
            )

            forgeBus.register(KeyBinds)
            forgeBus.register(ClientEvents)
            forgeBus.register(ScreenEvents)

            forgeBus.addListener(ScreenEvents::onGuiOpen)
            forgeBus.addListener(ScreenEvents::onNpcToolGuiOpen)
            forgeBus.addListener(ScreenEvents::onNPCCreatorGuiOpen)

            initKeys()

            LOGGER.info(
                """
                / ============================== \
                |          [KOTLOUDRON]          |
                | ------------------------------ |
                |      CLIENT SETUP COMPLETE     |
                \ ============================== /
                """.trimIndent()
            )
    }

    @SubscribeEvent
    fun startServer(event: ServerStartingEvent?) {
        LOGGER.info(
                """
                / ============================== \
                |          [KOTLOUDRON]          |
                | ------------------------------ |
                |         SERVER STARTING        |
                \ ============================== /
                """.trimIndent()
        )
    }

    companion object {
        const val MODID: String = "kotloudron"
        val LOGGER: Logger = LogUtils.getLogger()
    }
}
