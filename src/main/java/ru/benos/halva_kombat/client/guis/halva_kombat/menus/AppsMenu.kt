package ru.benos.halva_kombat.client.guis.halva_kombat.menus

import imgui.ImGui
import imgui.flag.ImGuiTableFlags
import ru.benos.halva_kombat.HalvaKombat.Companion.MODID
import ru.hollowhorizon.hc.client.utils.rl
import ru.hollowhorizon.hc.client.utils.toTexture

object AppsMenu {
  val appsAtlas = "$MODID:textures/gui/apps.png".rl.toTexture().id

  fun onAppsMenu(): Int {
    ImGui.setCursorPos(8f, 8f)
    val gemtabApp = ImGui.imageButton(appsAtlas, 256f, 256f, 0f, 0f, 0.25f, 0.25f)
    ImGui.setCursorPos(8f + 256, 8f)
    val bankApp = ImGui.imageButton(appsAtlas, 256f, 256f, 0.25f, 0f, 0.5f, 0.25f)
    ImGui.setCursorPos(8f + 256 * 2, 8f)
    val settingsApp = ImGui.imageButton(appsAtlas, 256f, 256f, 0.5f, 0f, 0.075f, 0.25f)

    if(gemtabApp) return 1
    if(bankApp) return 2
    if(settingsApp) return 3
    else return 0
  }

}