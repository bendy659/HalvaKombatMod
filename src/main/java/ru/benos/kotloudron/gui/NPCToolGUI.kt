package ru.benos.kotloudron.gui

import com.mojang.blaze3d.vertex.PoseStack
import imgui.ImGui
import net.minecraft.client.Minecraft
import ru.benos.kotloudron.gui.Config.getConfig
import ru.benos.kotloudron.utils.HelperPack.lang
import ru.benos.kotloudron.utils.HelperPack.showInfo
import ru.hollowhorizon.hc.client.imgui.ImGuiMethods.centredWindow
import ru.hollowhorizon.hc.client.imgui.ImguiHandler
import ru.hollowhorizon.hc.client.screens.HollowScreen
import ru.hollowhorizon.hc.client.utils.open
import ru.hollowhorizon.hc.client.utils.rl
import ru.hollowhorizon.hc.client.utils.toTexture
import ru.hollowhorizon.hollowengine.client.gui.NPCCreatorGui
import ru.hollowhorizon.hollowengine.client.gui.npcs.ScriptNodeEditor
import ru.hollowhorizon.hollowengine.common.entities.NPCEntity

class NPCToolGUI(val npc: NPCEntity) : HollowScreen() {
  val nodeEditorIconAlt = getConfig("showRealNodeEditorIcon")
  //val nodeEditorSwapIcon =
  //  if(nodeEditorIconAlt is Boolean && nodeEditorIconAlt) "nodes0"
  //  else "nodes"

  override fun render(pPoseStack: PoseStack, pMouseX: Int, pMouseY: Int, pPartialTick: Float) {
    ImguiHandler.drawFrame {
      val window = Minecraft.getInstance().window

      ImGui.setNextWindowSize(window.width * 0.9f, window.height * 0.9f)

      centredWindow {
        ImGui.text(lang("gui.npc_tool.title"))
        ImGui.sameLine()
        showInfo(lang("gui.npc_tool.show_info"))

        ImGui.separator()
        ImGui.newLine()

        if(
          imageButton(
            "wrench",
            lang("gui.npc_tool.npc_editing"),
            ImGui.getWindowWidth() / 8f)
        ) {
          val oldMenuOpen = getConfig("openOldMenu")

          if(oldMenuOpen is Boolean && !oldMenuOpen) HEAddon_NPCCreatorGUI(npc, npc.id, true).open()
          else NPCCreatorGui(npc, npc.id).open()
        }
        sameLine()

        if(
          imageButton(
            if(nodeEditorIconAlt is Boolean && !nodeEditorIconAlt)
              "nodes"
            else
              "nodes0",
            lang("gui.npc_tool.npc_actions"),
            ImGui.getWindowWidth() / 8f)
        ) {
          ScriptNodeEditor(npc).open()
        }
        sameLine()

        if(
          imageButton(
            "pose",
            lang("gui.npc_tool.npc_poses"),
            ImGui.getWindowWidth() / 8f
          )
        ) {}
        ImGui.newLine()
      }
    }
  }

  private fun imageButton(icon: String, desc: String, size: Float): Boolean {
    val config = getConfig("npcToolMenu_newIcons")
    val isClicked =
      if(config is Boolean && !config)  ImGui.imageButton("hollowengine:textures/gui/icons/$icon.png".rl.toTexture().id, size, size)
      else ImGui.imageButton("he_addon:textures/gui/npc_tool/$icon.png".rl.toTexture().id, size, size)

    if (ImGui.isItemHovered()) ImGui.setTooltip(desc)
    return isClicked
  }
}