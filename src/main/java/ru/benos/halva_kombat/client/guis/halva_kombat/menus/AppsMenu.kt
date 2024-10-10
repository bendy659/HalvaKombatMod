package ru.benos.halva_kombat.client.guis.halva_kombat.menus

import imgui.ImGui
import imgui.flag.ImGuiCol
import imgui.flag.ImGuiTableFlags
import imgui.flag.ImGuiWindowFlags
import imgui.type.ImInt
import ru.benos.halva_kombat.HalvaKombat.Companion.MODID
import ru.hollowhorizon.hc.client.utils.rl
import ru.hollowhorizon.hc.client.utils.toTexture

object AppsMenu {

  val appID = ImInt(0)

  fun onAppsMenu(): ImInt {
    ImGui.beginChild("apps-screen-menu", ImGui.getWindowWidth(), ImGui.getWindowHeight(), false, ImGuiWindowFlags.NoBackground)

    val appsAtlas = "$MODID:textures/gui/apps.png".rl.toTexture().id
    val appSize = ImGui.getWindowHeight() / 8f
    val dX = ImGui.getWindowWidth() / 12f + appSize
    val px = ImGui.getWindowWidth() / 10f
    val py = ImGui.getWindowHeight() / 10f

    ImGui.pushStyleColor(ImGuiCol.Button, 1f, 1f, 1f, 0f)
    ImGui.pushStyleColor(ImGuiCol.ButtonActive, 1f, 1f, 1f, 0f)
    ImGui.pushStyleColor(ImGuiCol.ButtonHovered, 1f, 1f, 1f, 0f)
    ImGui.setCursorPos(px, py)
    val gemtabApp = ImGui.imageButton(appsAtlas, appSize, appSize, 0f, 0f, 0.25f, 0.25f)
    ImGui.setCursorPos(px + dX, py)
    val bankApp = ImGui.imageButton(appsAtlas, appSize, appSize, 0.25f, 0f, 0.5f, 0.25f)
    ImGui.setCursorPos(px + dX * 2, py)
    val settingsApp = ImGui.imageButton(appsAtlas, appSize, appSize, 0.5f, 0f, 0.75f, 0.25f)
    ImGui.popStyleColor(3)

    if(gemtabApp)
      appID.set(1)
    else if(bankApp)
      appID.set(2)
    else if(settingsApp)
      appID.set(3)
    else
      appID.set(0)

    ImGui.endChild()

    return appID
  }
}