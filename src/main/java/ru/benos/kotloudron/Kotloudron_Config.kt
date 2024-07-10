package ru.benos.kotloudron

import com.mojang.blaze3d.vertex.PoseStack
import imgui.ImGui
import ru.hollowhorizon.hc.client.imgui.ImguiHandler
import ru.hollowhorizon.hc.client.screens.HollowScreen

// Custom config
object Kotloudron_Config: HollowScreen() {
  override fun render(pPoseStack: PoseStack, pMouseX: Int, pMouseY: Int, pPartialTick: Float) {
    ImguiHandler.drawFrame {
      ImGui.begin("#config")



      ImGui.end()
    }
  }
}