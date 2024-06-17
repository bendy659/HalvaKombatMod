package ru.benos.he_addon.gui.theme

import com.mojang.blaze3d.vertex.PoseStack
import imgui.ImGui
import imgui.flag.ImGuiWindowFlags
import imgui.type.ImInt
import net.minecraft.client.Minecraft
import org.jetbrains.kotlin.util.removeSuffixIfPresent
import ru.benos.he_addon.HEAddon.Companion.MODID
import ru.benos.he_addon.gui.theme.ThemeData.themeColorsReading
import ru.benos.he_addon.gui.theme.ThemeData.themeGuiReading
import ru.benos.he_addon.gui.theme.ThemeData.themePackReading
import ru.benos.he_addon.utils.HelperPack.lang
import ru.hollowhorizon.hc.client.imgui.ImGuiMethods.window
import ru.hollowhorizon.hc.client.imgui.ImguiHandler
import ru.hollowhorizon.hc.client.screens.HollowScreen
import ru.hollowhorizon.hc.client.utils.rl
import ru.hollowhorizon.hc.client.utils.toTexture

object ThemeSelect : HollowScreen() {

  private var menus = themeGuiReading(); private var menu_select = ImInt(0)
  private var packs = themePackReading(menus.map { it.folderName }[menu_select.get()], menu_select.get()); private var packs_select = ImInt(0)
  private var colors = themeColorsReading(menus.map { it.folderName }[menu_select.get()], menu_select.get()); private var colors_select = ImInt(0)

  override fun render(pPoseStack: PoseStack, pMouseX: Int, pMouseY: Int, pPartialTick: Float) {
    super.render(pPoseStack, pMouseX, pMouseY, pPartialTick)

    ImguiHandler.drawFrame {
      window(
        "theme_select",
        ImGuiWindowFlags.NoResize or ImGuiWindowFlags.NoMove or ImGuiWindowFlags.NoTitleBar
          or ImGuiWindowFlags.NoCollapse or ImGuiWindowFlags.NoScrollbar or ImGuiWindowFlags.NoScrollWithMouse
      ) {
        centerWindow()

        val window = Minecraft.getInstance().window

        ImGui.setWindowSize(window.width * 0.45f, window.height * 0.5f)

        ImGui.text(lang("gui.theme_select.title"))
        ImGui.separator()
        ImGui.newLine()

        ImGui.pushItemWidth(584f)
        ImGui.setCursorPosX(16f)
        if(ImGui.combo("##menu", menu_select, menus.map { it.guiName }.toTypedArray()))
          packs =  themePackReading(menus.map { it.folderName }[menu_select.get()], menu_select.get())
        ImGui.sameLine()
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
        if(button("reload")) {
          menus = themeGuiReading()
          packs = themePackReading(menus.map { it.folderName }[menu_select.get()], menu_select.get())
        }

        ImGui.setCursorPos(ImGui.getWindowWidth() - 36, 4f)
        if(button("close", 24f)) onClose()
      }
    }
  }
  override fun isPauseScreen() = false

  private fun label(text: String) {
    ImGui.text(lang("gui.theme_select.$text"))
    if(ImGui.isItemHovered()) ImGui.setTooltip(lang("gui.theme_select.${text}_desc"))
  }
  private fun button(desc: String, size: Float = 64f): Boolean {
    val imageHoverColor = arrayOf(0.25f, 1f)
    val isClick = ImGui.imageButton("$MODID:textures/gui/theme/$desc.png".rl.toTexture().id, size, size)

    if(ImGui.isItemHovered()) {
      ImGui.setTooltip(lang("gui.theme_select.button.${desc}_desc"))


    }
    return isClick
  }
}