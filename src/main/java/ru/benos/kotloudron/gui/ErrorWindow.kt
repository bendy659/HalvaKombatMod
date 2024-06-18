package ru.benos.kotloudron.gui

import com.mojang.blaze3d.vertex.PoseStack
import imgui.ImGui
import imgui.flag.ImGuiWindowFlags
import ru.hollowhorizon.hc.client.imgui.ImGuiMethods.window
import ru.hollowhorizon.hc.client.imgui.ImguiHandler
import ru.hollowhorizon.hc.client.screens.HollowScreen

class ErrorWindow(val errorTitle: String, val errorMessage: String): HollowScreen() {

  override fun render(pPoseStack: PoseStack, pMouseX: Int, pMouseY: Int, pPartialTick: Float) {
    super.render(pPoseStack, pMouseX, pMouseY, pPartialTick)

    ImguiHandler.drawFrame {

      window(
        errorTitle,
        ImGuiWindowFlags.AlwaysAutoResize or ImGuiWindowFlags.NoResize or ImGuiWindowFlags.NoCollapse
            or ImGuiWindowFlags.NoMove
      ) {
        centerWindow()

        ImGui.text(errorMessage)
      }
    }
  }
}