package ru.benos.halva_kombat.events

import net.minecraft.client.Minecraft
import net.minecraftforge.client.event.ScreenEvent
import net.minecraftforge.eventbus.api.SubscribeEvent
import ru.benos.halva_kombat.client.guis.NpcToolGui
import ru.hollowhorizon.hollowengine.client.gui.NPCToolGui

object ScreenEvents {

  @SubscribeEvent
  fun onGuiOpen(e: ScreenEvent.Opening) {
    val screen = e.screen

    if (screen is NPCToolGui) Minecraft.getInstance().setScreen(NpcToolGui(screen.npc))
  }
}