package ru.benos.kotloudron.guis

import com.mojang.blaze3d.vertex.PoseStack
import imgui.ImGui
import imgui.ImVec2
import imgui.flag.ImGuiCol
import imgui.flag.ImGuiInputTextFlags
import imgui.flag.ImGuiWindowFlags
import imgui.type.ImString
import net.minecraft.client.Minecraft
import net.minecraft.locale.Language
import org.apache.commons.codec.language.bm.Lang
import ru.benos.kotloudron.Kotloudron
import ru.benos.kotloudron.Kotloudron.Companion.MODID
import ru.hollowhorizon.hc.client.imgui.ImGuiMethods.centerWindow
import ru.hollowhorizon.hc.client.imgui.ImguiHandler
import ru.hollowhorizon.hc.client.models.gltf.manager.AnimatedEntityCapability
import ru.hollowhorizon.hc.client.screens.HollowScreen
import ru.hollowhorizon.hc.client.utils.get
import ru.hollowhorizon.hc.client.utils.rl
import ru.hollowhorizon.hc.client.utils.toTexture
import ru.hollowhorizon.hollowengine.client.gui.NPCCreatorGui
import ru.hollowhorizon.hollowengine.common.entities.NPCEntity
import kotlin.random.Random

class NpcCreateGui(val npc: NPCEntity, val npcID: Int, val isNew: Boolean = false): HollowScreen() {
  private val npcName = ImString().apply {
    if (npc.hasCustomName())
      set(npc.customName?.string ?: "")
    else {
      val r = Random.nextInt(0, 101)

      if (r >= 99)
        set( String(NPCCreatorGui::class.java.getResourceAsStream("/npc_names")!!.readAllBytes()).split("\r\n", "\n").random() )
      else {
        set( String(NPCCreatorGui::class.java.getResourceAsStream("/internal/npc.names")!!.readAllBytes()).split("\r\n", "\n").random() )
        Kotloudron.LOGGER.debug("RTTBHS: $r")
      }
    }
  }
  private val npcModel = ImString().apply {
        set(npc[AnimatedEntityCapability::class].model)
    }

  override fun isPauseScreen() = false

  override fun render(pPoseStack: PoseStack, pMouseX: Int, pMouseY: Int, pPartialTick: Float) {

    ImguiHandler.drawFrame {
      val window = Minecraft.getInstance().window
      val oldCursorPos = ImGui.getCursorPos()

      ImGui.begin(
        "#npc_creator", ImGuiWindowFlags.NoMove or /*ImGuiWindowFlags.NoResize or*/ ImGuiWindowFlags.NoScrollbar
        or ImGuiWindowFlags.NoScrollWithMouse or ImGuiWindowFlags.NoTitleBar or ImGuiWindowFlags.NoBackground
      )
      centerWindow()

      val wd = ImGui.getWindowWidth() * 0.98f
      val hd = ImGui.getWindowHeight() * 1f

      /* Setting setup */

      ImGui.setCursorPosY(64f)
      ImGui.setWindowSize(window.width * 0.9f, window.height * 0.85f)
      ImGui.getStyle().windowBorderSize = 4f
      ImGui.getStyle().windowRounding = 8f
      ImGui.getStyle().setWindowPadding(32f, 32f)
      ImGui.getStyle().setColor(ImGuiCol.Text, 255, 255, 255, 255)

      /* Background setup */

      // Top
      ImGui.setCursorPos(16f, 0f)
      ImGui.image("$MODID:textures/gui/npc_create/bg.png".rl.toTexture().id, 64f, 64f, 0f, 0f, 0.25f, 0.25f); ImGui.sameLine()
      ImGui.setCursorPosX(80f)
      ImGui.image("$MODID:textures/gui/npc_create/bg.png".rl.toTexture().id, wd - 128, 64f, 0.25f, 0f, 0.5f, 0.25f); ImGui.sameLine()
      ImGui.setCursorPosX(wd - 48)
      ImGui.image("$MODID:textures/gui/npc_create/bg.png".rl.toTexture().id, 64f, 64f, 0.5f, 0f, 0.75f, 0.25f)

      // Center
      ImGui.setCursorPos(16f, 64f)
      ImGui.image("$MODID:textures/gui/npc_create/bg.png".rl.toTexture().id, 64f, hd - 128, 0f, 0.25f, 0.25f, 0.5f); ImGui.sameLine()
      ImGui.setCursorPosX(64f)
      ImGui.image("$MODID:textures/gui/npc_create/bg.png".rl.toTexture().id, wd - 112, hd - 128, 0.25f, 0.25f, 0.5f, 0.5f); ImGui.sameLine()
      ImGui.setCursorPosX(wd - 48)
      ImGui.image("$MODID:textures/gui/npc_create/bg.png".rl.toTexture().id, 64f, hd - 128, 0.5f, 0.25f, 0.75f, 0.5f)

      // Bottom
      ImGui.setCursorPos(16f, hd - 64)
      ImGui.image("$MODID:textures/gui/npc_create/bg.png".rl.toTexture().id, 64f, 64f, 0f, 0.5f, 0.25f, 0.75f); ImGui.sameLine()
      ImGui.setCursorPosX(80f)
      ImGui.image("$MODID:textures/gui/npc_create/bg.png".rl.toTexture().id, wd - 128, 64f, 0.25f, 0.5f, 0.5f, 0.75f); ImGui.sameLine()
      ImGui.setCursorPosX(wd - 48)
      ImGui.image("$MODID:textures/gui/npc_create/bg.png".rl.toTexture().id, 64f, 64f, 0.5f, 0.5f, 0.75f, 0.75f)

      ImGui.setCursorPos(oldCursorPos.x, oldCursorPos.y)

      /* Main */

      ImGui.setCursorPos(32f, 16f)
      ImGui.beginChild("#main", ImGui.getWindowWidth() * 0.75f, ImGui.getWindowHeight() * 0.9625f, false, ImGuiWindowFlags.NoBackground)

      input("npc_name", npcName, "npcName", 0f, 0f, oldCursorPos)
      input("npc_model", npcModel, "npcModel", 0f, 128f, oldCursorPos)

      ImGui.endChild()

      /* Entity viewport */

      ImGui.setCursorPos(ImGui.getWindowWidth() - 576, 32f)
      ImGui.beginChild(
        "#npc_entity", width * 0.825f, height * 2.025f, false,
        ImGuiWindowFlags.NoBackground or ImGuiWindowFlags.NoScrollbar or ImGuiWindowFlags.NoScrollWithMouse
      )

      entity(npc, oldCursorPos)

      ImGui.endChild()
      ImGui.end()
    }
  }

  private fun input(inputName: String, text: ImString, inputID: String, posX: Float, posY: Float, oldCursorPos: ImVec2) {
    val width = ImGui.getWindowWidth() * 0.9f
    val w = 56f
    val h = 56f

    ImGui.setCursorPos(oldCursorPos.x, oldCursorPos.y)

    ImGui.pushID("${inputID}_g")
    ImGui.setCursorPos(posX, posY)
    ImGui.beginChild("${inputID}_g_c", width, 128f, true, ImGuiWindowFlags.NoBackground or ImGuiWindowFlags.NoScrollbar or ImGuiWindowFlags.NoScrollWithMouse)
    val t =
      if(ImGui.isWindowFocused())
        "_a"
      else
        ""

    ImGui.pushStyleColor(ImGuiCol.FrameBg, 0, 0, 0, 0)

    /* Input name */

    ImGui.pushFont(ImguiHandler.FONTS[4])
    ImGui.pushStyleColor(ImGuiCol.Text, 64, 64, 64, 255)
    ImGui.setCursorPos(16f, 8f)
    ImGui.text(Language.getInstance().getOrDefault("gui.$MODID.npc_create.$inputName"))
    ImGui.popStyleColor()
    ImGui.popFont()
    ImGui.newLine()

    //Start input
    ImGui.setCursorPos(16f, 48f)
    ImGui.image("$MODID:textures/gui/npc_create/input$t.png".rl.toTexture().id, w, h, 0f, 0f, 0.25f, 1f); ImGui.sameLine()

    // Center input
    ImGui.setCursorPosX(56f)
    ImGui.image("$MODID:textures/gui/npc_create/input$t.png".rl.toTexture().id, width - 160, h, 0.25f, 0f, 0.5f, 1f); ImGui.sameLine()

    // End input
    ImGui.setCursorPosX(width - 120)
    ImGui.image("$MODID:textures/gui/npc_create/input$t.png".rl.toTexture().id, w, h, 0.5f, 0f, 0.75f, 1f)

    // Input zone
    ImGui.pushFont(ImguiHandler.FONTS[3])
    ImGui.setCursorPos(ImGui.getCursorPosX() - 4, ImGui.getCursorPosY() - 80)
    ImGui.pushItemWidth(width - 130f)
    ImGui.inputText("##$inputID", text)
    ImGui.popItemWidth()
    ImGui.popFont()

    ImGui.popStyleColor()
    ImGui.endChild()
    ImGui.popID()

    ImGui.setCursorPos(oldCursorPos.x, oldCursorPos.y)
  }

  private fun entity(npcEntity: NPCEntity, oldCursorPos: ImVec2) {
    val w = 40f
    val h = 40f
    val wn = ImGui.getWindowWidth()
    val hn = ImGui.getWindowHeight() - 88

    ImGui.setCursorPos(oldCursorPos.x, oldCursorPos.y)

    ImGui.pushID("#npc")

    // Top
    ImGui.setCursorPos(8f, 8f)
    ImGui.image("$MODID:textures/gui/npc_create/entity_bg.png".rl.toTexture().id, w, h, 0f, 0f, 0.25f, 0.25f); ImGui.sameLine()
    ImGui.setCursorPosX(40f)
    ImGui.image("$MODID:textures/gui/npc_create/entity_bg.png".rl.toTexture().id, wn - 88, h, 0.25f, 0f, 0.5f, 0.25f); ImGui.sameLine()
    ImGui.setCursorPosX(wn - 48)
    ImGui.image("$MODID:textures/gui/npc_create/entity_bg.png".rl.toTexture().id, w, h, 0.5f, 0f, 0.75f, 0.25f)

    // Center
    ImGui.setCursorPos(8f, 48f)
    ImGui.image("$MODID:textures/gui/npc_create/entity_bg.png".rl.toTexture().id, w, hn, 0f, 0.25f, 0.25f, 0.5f); ImGui.sameLine()
    ImGui.setCursorPosX(40f)
    ImGui.image("$MODID:textures/gui/npc_create/entity_bg.png".rl.toTexture().id, wn - 88, hn, 0.25f, 0.25f, 0.5f, 0.5f); ImGui.sameLine()
    ImGui.setCursorPosX(wn - 48)
    ImGui.image("$MODID:textures/gui/npc_create/entity_bg.png".rl.toTexture().id, w, hn, 0.5f, 0.25f, 0.75f, 0.5f)

    // Bottom
    ImGui.setCursorPos(8f, hn + 16)
    ImGui.image("$MODID:textures/gui/npc_create/entity_bg.png".rl.toTexture().id, w, h, 0f, 0.5f, 0.25f, 0.75f); ImGui.sameLine()
    ImGui.setCursorPosX(40f)
    ImGui.image("$MODID:textures/gui/npc_create/entity_bg.png".rl.toTexture().id, wn - 88, h, 0.25f, 0.5f, 0.5f, 0.75f); ImGui.sameLine()
    ImGui.setCursorPosX(wn - 48)
    ImGui.image("$MODID:textures/gui/npc_create/entity_bg.png".rl.toTexture().id, w, h, 0.5f, 0.5f, 0.75f, 0.75f)

    ImGui.popID()
  }
}