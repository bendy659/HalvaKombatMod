package ru.benos.halva_kombat.client.guis

import com.mojang.blaze3d.vertex.PoseStack
import imgui.ImGui
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.screens.Screen
import net.minecraft.network.chat.Component
import ru.benos.halva_kombat.HalvaKombat.Companion.MODID
import ru.hollowhorizon.hc.client.imgui.ImguiHandler
import ru.hollowhorizon.hc.client.utils.rl
import ru.hollowhorizon.hc.client.utils.toTexture

object PreviewGui: Screen(Component.empty()) {


  override fun render(pPoseStack: PoseStack, pMouseX: Int, pMouseY: Int, pPartialTick: Float) {
    ImguiHandler.drawFrame {
      val window = Minecraft.getInstance().window
      val assets = "$MODID:textures/gui/preview"

      ImGui.begin("preview-gui")

      ImGui.setWindowSize(540f, 480f)

      ImGui.text("Example text")

      ImGui.image("$assets/example-image.png".rl.toTexture().id, 256f, 256f)

      ImGui.button("Example button")

      ImGui.end()
    }
  }
}