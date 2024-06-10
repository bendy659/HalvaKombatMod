package ru.benos.he_addon.gui.themes

import com.mojang.blaze3d.vertex.PoseStack
import imgui.ImGui
import ru.hollowhorizon.hc.HollowCore
import ru.hollowhorizon.hc.client.imgui.ImGuiMethods.window
import ru.hollowhorizon.hc.client.imgui.ImguiHandler
import ru.hollowhorizon.hc.client.screens.HollowScreen
import ru.hollowhorizon.hollowengine.common.files.DirectoryManager.HOLLOW_ENGINE
import java.io.FileReader

object ThemeSelect : HollowScreen() {
  val THEMEDIR = HOLLOW_ENGINE.resolve("themes").apply { if (!exists()) mkdirs() }

  fun getSelectTheme(): String {
    val select = FileReader("$THEMEDIR/theme.select").readText()
    HollowCore.LOGGER.info("[THEME] Theme select: $select")
    return select
  }

  override fun render(pPoseStack: PoseStack, pMouseX: Int, pMouseY: Int, pPartialTick: Float) {
    super.render(pPoseStack, pMouseX, pMouseY, pPartialTick)

    ImguiHandler.drawFrame {
      window("Theme selector") {
        centerWindow()

        ImGui.setWindowSize(1280f, 720f)
      }
    }
  }
}