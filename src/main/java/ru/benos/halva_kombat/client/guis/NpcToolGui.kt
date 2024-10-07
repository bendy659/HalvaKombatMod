package ru.benos.halva_kombat.client.guis

import com.mojang.blaze3d.vertex.PoseStack
import imgui.ImGui
import imgui.flag.ImGuiCol
import imgui.flag.ImGuiWindowFlags
import net.minecraft.client.Minecraft
import net.minecraft.locale.Language
import ru.benos.halva_kombat.HalvaKombat.Companion.MODID
import ru.benos.halva_kombat.Kotloudron_Config
import ru.hollowhorizon.hc.client.imgui.ImGuiMethods.centerWindow
import ru.hollowhorizon.hc.client.imgui.ImguiHandler
import ru.hollowhorizon.hc.client.screens.HollowScreen
import ru.hollowhorizon.hc.client.utils.*
import ru.hollowhorizon.hollowengine.common.entities.NPCEntity
import ru.hollowhorizon.hollowengine.common.scripting.bold
import ru.hollowhorizon.hollowengine.common.scripting.italic

class NpcToolGui(val npc: NPCEntity): HollowScreen() {

  override fun isPauseScreen() = false

  override fun render(pPoseStack: PoseStack, pMouseX: Int, pMouseY: Int, pPartialTick: Float) {

    ImguiHandler.drawFrame {
      val window = Minecraft.getInstance().window

      ImGui.begin(
        "#NpcToolGui",
        ImGuiWindowFlags.NoMove or ImGuiWindowFlags.NoResize or ImGuiWindowFlags.NoScrollbar or ImGuiWindowFlags.NoScrollWithMouse
            or ImGuiWindowFlags.NoTitleBar
      )
      centerWindow()

      /* Setting setup */

      ImGui.setWindowSize(window.width * 0.90f, window.height * 0.90f)
      ImGui.getStyle().windowBorderSize = 4f
      ImGui.getStyle().windowRounding = 16f
      ImGui.getStyle().setColor(ImGuiCol.WindowBg, 0, 0, 0, 164)
      ImGui.getStyle().setColor(ImGuiCol.Border, 255, 255, 255, 255)
      ImGui.getStyle().setColor(ImGuiCol.Separator, 255, 255, 255, 255)
      ImGui.getStyle().setWindowPadding(32f, 32f)

      /* Title */
      ImGui.pushFont(ImguiHandler.FONTS[4])
      ImGui.text(Language.getInstance().getOrDefault("gui.$MODID.npc_tool.title"))
      ImGui.popFont()

      ImGui.newLine()
      ImGui.separator()
      ImGui.newLine()

      /* Buttons */

      if( button("wrench", "npc_edit") ) NpcCreateGui(npc, npc.id).open()
      ImGui.sameLine()
      if( button("nodes", "npc_nodes") ) {}
      ImGui.sameLine()
      if( button("poses", "npc_poses") ) {}
      ImGui.sameLine()
      if( button("config", "config") ) {Kotloudron_Config.open()}

      ImGui.end()
    }
  }

  private fun button(textureName: String, button: String): Boolean {
    val size = ImGui.getWindowWidth() / 8

    ImGui.pushID("#$button")
    ImGui.beginChild("#${button}n_c", size, size)

    /* Set style for button image */
    ImGui.getStyle().setFramePadding(32f, 32f)
    ImGui.getStyle().childRounding = 12f
    ImGui.getStyle().childBorderSize = 4f

    ImGui.getStyle().setColor(ImGuiCol.Button, 64, 64, 64, 128)
    ImGui.getStyle().setColor(ImGuiCol.ButtonHovered, 128, 128, 128, 255)
    ImGui.getStyle().setColor(ImGuiCol.ButtonActive, 255, 255, 255, 255)

    val type =
      if(ImGui.isWindowHovered())
        arrayOf(0f, 0.5f, 1f, 1f, 1f, 32f)
    else
        arrayOf(0f, 0f, 1f, 0.5f, 0.4f, 8f)

    ImGui.getStyle().setColor(ImGuiCol.ChildBg, type[5].toInt(), type[5].toInt(), type[5].toInt(), 255)
    ImGui.image(
      "$MODID:textures/gui/npc_tool/$textureName.png".rl.toTexture().id,
      size, size,
      type[0], type[1], type[2], type[3],
      type[4], type[4], type[4], 1f
    )

    /* ToolTip set */

    if(ImGui.isItemHovered())
      ImGui.setTooltip(
        """
        ${Language.getInstance().getOrDefault("gui.$MODID.npc_tool.$button.name").bold}
        ----------------
        ${Language.getInstance().getOrDefault("gui.$MODID.npc_tool.$button.desc").italic}
        """.trimIndent()
      )

    ImGui.endChild()
    ImGui.popID()

    return ImGui.isItemClicked()
  }
}