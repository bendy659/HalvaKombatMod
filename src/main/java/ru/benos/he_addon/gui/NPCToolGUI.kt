package ru.benos.he_addon.gui

import com.mojang.blaze3d.vertex.PoseStack
import imgui.ImGui
import imgui.type.ImBoolean
import net.minecraft.client.Minecraft
import ru.benos.he_addon.gui.npc_create.NewNPCCreatorGUI
import ru.benos.he_addon.utils.HelperPack
import ru.hollowhorizon.hc.client.imgui.ImGuiMethods.centredWindow
import ru.hollowhorizon.hc.client.imgui.ImguiHandler
import ru.hollowhorizon.hc.client.screens.HollowScreen
import ru.hollowhorizon.hc.client.utils.open
import ru.hollowhorizon.hc.client.utils.rl
import ru.hollowhorizon.hc.client.utils.toTexture
import ru.hollowhorizon.hollowengine.client.gui.NPCCreatorGui
import ru.hollowhorizon.hollowengine.client.gui.npcs.ScriptNodeEditor
import ru.hollowhorizon.hollowengine.common.entities.NPCEntity
import ru.hollowhorizon.hollowengine.common.files.DirectoryManager.HOLLOW_ENGINE
import java.io.File

class NPCToolGUI(val npc: NPCEntity) : HollowScreen() {

  override fun render(pPoseStack: PoseStack, pMouseX: Int, pMouseY: Int, pPartialTick: Float) {
    ImguiHandler.drawFrame {
      val window = Minecraft.getInstance().window

      ImGui.setNextWindowSize(window.width * 0.9f, window.height * 0.9f)

      centredWindow {
        if (imageButton("wrench", HelperPack.lang("gui.npc_tool.npc_setting"))) {
          NewNPCCreatorGUI(npc, npc.id, true).open()
        }
        sameLine()

        if (imageButton("nodes", HelperPack.lang("gui.npc_tool.npc_actions"))) {
          ScriptNodeEditor(npc).open()
        }
        sameLine()

        imageButton("pose", HelperPack.lang("gui.npc_tool.npc_poses"))
        ImGui.newLine()
      }
    }
  }

  fun imageButton(image: String, desc: String): Boolean {
    val isClicked = ImGui.imageButton("hollowengine:textures/gui/icons/$image.png".rl.toTexture().id, 256f, 256f)
    if (ImGui.isItemHovered()) ImGui.setTooltip(desc)
    return isClicked
  }
}