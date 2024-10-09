package ru.benos.halva_kombat.client.guis.halva_kombat.menus

import imgui.ImGui
import imgui.flag.ImGuiCol
import imgui.flag.ImGuiWindowFlags
import ru.benos.halva_kombat.HalvaKombat.Companion.MODID
import ru.benos.halva_kombat.client.guis.Utils.getRealTime24hFormat
import ru.hollowhorizon.hc.client.imgui.ImguiHandler.FONTS
import ru.hollowhorizon.hc.client.utils.rl
import ru.hollowhorizon.hc.client.utils.toTexture

object BlockScreen {
  fun onLockScreen(): Boolean {
    ImGui.beginChild("lock-screen-menu", ImGui.getWindowWidth(), ImGui.getWindowHeight(), false, ImGuiWindowFlags.NoBackground)

    val realTime = getRealTime24hFormat()
    val hourTime = if(realTime.first < 10) "0${realTime.first}" else realTime.first.toString()
    val minuteTime = if(realTime.second < 10) "0${realTime.second}" else realTime.second.toString()

    val (width, height) = ImGui.getWindowWidth() to ImGui.getWindowHeight()

    ImGui.pushFont(FONTS[6])
    ImGui.setCursorPos(width / 2 - ImGui.calcTextSize(realTime.toString()).x / 2, height / 8)
    ImGui.text("$hourTime : $minuteTime")
    ImGui.popFont()

    ImGui.pushStyleColor(ImGuiCol.Button, 255, 255, 255, 0)
    ImGui.pushStyleColor(ImGuiCol.ButtonActive, 255, 255, 255, 0)
    ImGui.pushStyleColor(ImGuiCol.ButtonHovered, 255, 255, 255, 0)

    val lockIconSize = width / 2

    ImGui.setCursorPos((width / 2 - lockIconSize/ 2) + 16, (height / 2 - lockIconSize / 16))
    val unlockButton = ImGui.imageButton("$MODID:textures/gui/icons.png".rl.toTexture().id, lockIconSize, lockIconSize, 0.75f, 0f, 1f, 0.25f)
    ImGui.popStyleColor(3)

    ImGui.endChild()

    return unlockButton
  }
}