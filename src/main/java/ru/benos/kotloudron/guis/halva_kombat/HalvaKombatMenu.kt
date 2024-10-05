package ru.benos.kotloudron.guis.halva_kombat

import com.mojang.blaze3d.vertex.PoseStack
import imgui.ImGui
import imgui.flag.ImGuiWindowFlags
import imgui.type.ImFloat
import imgui.type.ImInt
import net.minecraft.client.Minecraft
import ru.hollowhorizon.hc.client.imgui.ImGuiMethods.centerWindow
import ru.hollowhorizon.hc.client.imgui.ImguiHandler
import ru.hollowhorizon.hc.client.screens.HollowScreen

class HalvaKombatMenu: HollowScreen() {

  /* Variables */

  var score = ImInt(0)
  var nextScore = ImInt(100)
  var scoreBar = ImFloat(nextScore.get().toFloat() / score.get().toFloat())

  override fun render(pPoseStack: PoseStack, pMouseX: Int, pMouseY: Int, pPartialTick: Float) {
    ImguiHandler.drawFrame {

      /* Styles setup */

      ImGui.begin(
        "halva-kombat-menu-main",
        ImGuiWindowFlags.NoMove or ImGuiWindowFlags.NoResize or ImGuiWindowFlags.NoTitleBar or ImGuiWindowFlags.NoScrollbar
        or ImGuiWindowFlags.NoScrollWithMouse
      )

      ImGui.setWindowSize(
        Minecraft.getInstance().window.width.toFloat() / 2 - ImGui.getWindowWidth() / 2,
        Minecraft.getInstance().window.height.toFloat() / 2 - ImGui.getWindowHeight() / 2
      )

      ImGui.end()
    }
  }
}