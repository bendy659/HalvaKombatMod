package ru.benos.halva_kombat.client.guis

import com.mojang.blaze3d.vertex.PoseStack
import imgui.ImGui
import imgui.flag.ImGuiCol
import imgui.flag.ImGuiStyleVar
import imgui.type.ImBoolean
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.screens.Screen
import net.minecraft.network.chat.Component
import ru.benos.halva_kombat.HalvaKombat.Companion.MODID
import ru.hollowhorizon.hc.client.imgui.ImguiHandler
import ru.hollowhorizon.hc.client.utils.rl
import ru.hollowhorizon.hc.client.utils.toTexture

object PreviewGui: Screen(Component.empty()) {
  val isChecked = ImBoolean(false)

  override fun render(pPoseStack: PoseStack, pMouseX: Int, pMouseY: Int, pPartialTick: Float) {
    ImguiHandler.drawFrame {
      val window = Minecraft.getInstance().window
      val assets = "$MODID:textures/gui/preview"

      ImGui.pushStyleVar(ImGuiStyleVar.WindowRounding, 16f)
      ImGui.pushStyleVar(ImGuiStyleVar.WindowBorderSize, 4f)
      ImGui.pushStyleColor(ImGuiCol.WindowBg, 0.32f, 0f, 0f, 0.5f)
      ImGui.pushStyleColor(ImGuiCol.Button, 0f, 0.65f, 0f, 0.75f)
      ImGui.pushStyleColor(ImGuiCol.Border, 1f, 1f, 0f, 1f)

      ImGui.begin("preview-gui")

      ImGui.setWindowSize(540f, 480f)
      ImGui.text("Example text")
      ImGui.image("$assets/example-image.png".rl.toTexture().id, 256f, 256f)
      ImGui.button("Example button")
      ImGui.checkbox("Example checkbox", isChecked)

      ImGui.end()

      ImGui.popStyleColor(3)
      ImGui.popStyleVar(2)
    }
  }
}