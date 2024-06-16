package ru.benos.he_addon.gui.theme

import com.mojang.blaze3d.vertex.PoseStack
import imgui.ImGui
import imgui.flag.ImGuiWindowFlags
import imgui.type.ImInt
import ru.benos.he_addon.HEAddon.Companion.MODID
import ru.benos.he_addon.gui.theme.ThemesReaderWriter.themeGuiReading
import ru.benos.he_addon.utils.HelperPack.lang
import ru.hollowhorizon.hc.client.imgui.ImGuiMethods.window
import ru.hollowhorizon.hc.client.imgui.ImguiHandler
import ru.hollowhorizon.hc.client.screens.HollowScreen
import ru.hollowhorizon.hc.client.utils.rl
import ru.hollowhorizon.hc.client.utils.toTexture

object ThemeSelect : HollowScreen() {

  private var menus = themeGuiReading(); private var menu_select = ImInt(0)
  private var packs = arrayOf( "default", "old"  ); private var packs_select = ImInt(0)
  private var colors = arrayOf( "default", "old" ); private var colors_select = ImInt(0)


  /*
   * 0 - NPC Create
  */

  override fun render(pPoseStack: PoseStack, pMouseX: Int, pMouseY: Int, pPartialTick: Float) {
    super.render(pPoseStack, pMouseX, pMouseY, pPartialTick)

    ImguiHandler.drawFrame {
      window(
        "theme_select",
        ImGuiWindowFlags.NoResize or ImGuiWindowFlags.NoMove or ImGuiWindowFlags.NoTitleBar
          or ImGuiWindowFlags.NoCollapse or ImGuiWindowFlags.NoScrollbar or ImGuiWindowFlags.NoScrollWithMouse
      ) {
        centerWindow()

        ImGui.setWindowSize(1024f, 640f)

        ImGui.text(lang("gui.theme_select.title"))
        ImGui.separator()
        ImGui.newLine()

        ImGui.pushItemWidth(256f)
        ImGui.setCursorPosX(16f)
        ImGui.combo("##menu", menu_select, menus); ImGui.sameLine()
        label("menus")
        ImGui.newLine()

        if(menu_select.get() >= 1) {
          ImGui.setCursorPosX(64f)
          ImGui.combo("##packs", packs_select, packs); ImGui.sameLine()
          label("packs")
          ImGui.newLine()

          ImGui.setCursorPosX(64f)
          ImGui.combo("##colors", colors_select, colors); ImGui.sameLine()
          label("colors")
          ImGui.popItemWidth()
        }

        ImGui.setCursorPos(8f,ImGui.getWindowHeight() - 80)
        if(button("create")) {}

        ImGui.setCursorPos(88f,ImGui.getWindowHeight() - 80)
        if(button("edit")) {}

        ImGui.setCursorPos(ImGui.getWindowWidth() - 80, ImGui.getWindowHeight() - 80)
        if(button("delete")) {}

        ImGui.setCursorPos(ImGui.getWindowWidth() - 160, ImGui.getWindowHeight() - 80)
        if(button("reload")) menus = themeGuiReading()

        ImGui.setCursorPos(ImGui.getWindowWidth() - 44, 4f)
        if(button("close", 32f)) onClose()
      }
    }
  }

  private fun label(text: String) {
    ImGui.text(lang("gui.theme_select.$text"))
    if(ImGui.isItemHovered()) ImGui.setTooltip(lang("gui.theme_select.${text}_desc"))
  }
  private fun button(desc: String, size: Float = 64f): Boolean {
    val isClick = ImGui.imageButton("$MODID:textures/gui/theme/$desc.png".rl.toTexture().id, size, size)
    if(ImGui.isItemHovered()) ImGui.setTooltip(lang("gui.theme_select.button.${desc}_desc"))
    return isClick
  }
}