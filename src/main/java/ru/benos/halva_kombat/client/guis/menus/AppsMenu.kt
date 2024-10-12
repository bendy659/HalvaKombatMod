package ru.benos.halva_kombat.client.guis.menus

import imgui.ImGui
import imgui.flag.ImGuiWindowFlags
import imgui.type.ImInt
import ru.benos.halva_kombat.HalvaKombat.Companion.MODID
import ru.hollowhorizon.hc.client.utils.rl
import ru.hollowhorizon.hc.client.utils.toTexture

object AppsMenu {

  var appID = ImInt(0)

  fun onAppsMenu(): ImInt {
    ImGui.beginChild("apps-screen-menu", ImGui.getWindowWidth(), ImGui.getWindowHeight(), false, ImGuiWindowFlags.NoBackground)

    val apps = "$MODID:textures/gui/apps/"
    val appSize = ImGui.getWindowHeight() / 8f
    val dX = ImGui.getWindowWidth() / 12f + appSize
    val px = ImGui.getWindowWidth() / 10f
    val py = ImGui.getWindowHeight() / 10f

    ImGui.setCursorPos(px, py)
    if(ImGui.imageButton("$apps/gemtap.png".rl.toTexture().id, appSize, appSize)) appID.set(1)
    ImGui.setCursorPos(px + dX, py)
    if(ImGui.imageButton("$apps/bank.png".rl.toTexture().id, appSize, appSize)) appID.set(2)
    ImGui.setCursorPos(px + dX * 2, py)
    if(ImGui.imageButton("$apps/settings.png".rl.toTexture().id, appSize, appSize)) appID.set(3)

    ImGui.endChild()

    return appID
  }
}