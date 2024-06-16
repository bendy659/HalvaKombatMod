package ru.benos.he_addon.gui

import com.mojang.blaze3d.vertex.PoseStack
import imgui.ImGui
import net.minecraft.client.Minecraft
import ru.benos.he_addon.gui.Config.getConfig
import ru.benos.he_addon.utils.HelperPack.lang
import ru.benos.he_addon.utils.HelperPack.showInfo
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

        if (imageButton("wrench", lang("gui.npc_tool.npc_editing"))) {
          val oldMenuOpen = getConfig("openOldMenu")

          if(oldMenuOpen is Boolean && !oldMenuOpen) HEAddon_NPCCreatorGUI(npc, npc.id, true).open()
          else NPCCreatorGui(npc, npc.id).open()
        }
        sameLine()

        if (imageButton(if(nodeEditorIconAlt is Boolean && nodeEditorIconAlt) "nodes" else "nodes0", lang("gui.npc_tool.npc_actions"))) {
          ScriptNodeEditor(npc).open()
        }
        sameLine()

        imageButton("pose", lang("gui.npc_tool.npc_poses"))
        ImGui.newLine()
      }
    }
  }

  private fun imageButton(icon: String, desc: String): Boolean {
    val config = getConfig("npcToolMenu_newIcons")
    val isClicked =
      if(config is Boolean && !config) ImGui.imageButton("hollowengine:textures/gui/icons/$icon.png".rl.toTexture().id, 256f, 256f)
      else ImGui.imageButton("he_addon:textures/gui/npc_tool/$icon.png".rl.toTexture().id, 256f, 256f)

    if (ImGui.isItemHovered()) ImGui.setTooltip(desc)
    return isClicked
  }
}