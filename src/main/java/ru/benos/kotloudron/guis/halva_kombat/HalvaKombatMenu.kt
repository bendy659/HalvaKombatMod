package ru.benos.kotloudron.guis.halva_kombat

import com.mojang.blaze3d.vertex.PoseStack
import imgui.ImGui
import imgui.flag.ImGuiCol
import imgui.flag.ImGuiStyleVar
import imgui.flag.ImGuiWindowFlags
import imgui.type.ImFloat
import imgui.type.ImInt
import net.minecraft.client.Minecraft
import ru.hollowhorizon.hc.client.imgui.ImguiHandler
import ru.hollowhorizon.hc.client.screens.HollowScreen
import ru.hollowhorizon.hc.client.utils.rl
import ru.hollowhorizon.hc.client.utils.toTexture
import kotlin.math.round

class HalvaKombatMenu: HollowScreen() {

  /* Variables */

  var score = ImInt(0)
  var nextScore = ImInt(100)

  var perClick = ImInt(1)

  var pointUpgrades = ImInt(0)

  var progressLoading = ImFloat(0f)

  var menuSelected = Menus.MAIN

  override fun render(pPoseStack: PoseStack, pMouseX: Int, pMouseY: Int, pPartialTick: Float) {
    ImguiHandler.drawFrame {

      /* Variables */

      val screen = Minecraft.getInstance().window

      val frameWidth = screen.height.toFloat() * 0.6f
      val frameHeight = screen.height.toFloat() * 0.95f

      val menuWidth = frameWidth - 50f
      val menuHeight = frameHeight - 150f

      /* Styles setup */



      /* Set main Window */

      ImGui.begin(
        "halva-kombat-frame",
        ImGuiWindowFlags.NoMove or ImGuiWindowFlags.NoResize or ImGuiWindowFlags.NoTitleBar or ImGuiWindowFlags.NoScrollbar
        or ImGuiWindowFlags.NoScrollWithMouse or ImGuiWindowFlags.NoBackground
      )

      ImGui.setWindowPos(screen.width / 2 - ImGui.getWindowWidth() / 2, screen.height / 2 - ImGui.getWindowHeight() / 2)

      /* Draw menu */

      ImGui.setCursorPos(25f, 50f)
      ImGui.beginChild(
        "halva-kombat-menu",
        menuWidth, menuHeight,
        false,
        ImGuiWindowFlags.NoMove or ImGuiWindowFlags.NoResize or ImGuiWindowFlags.NoTitleBar or ImGuiWindowFlags.NoScrollbar
        or ImGuiWindowFlags.NoScrollWithMouse
      )

      /* Draw background */

      ImGui.setCursorPos(0f, 0f)
      ImGui.image("kotloudron:textures/gui/halva_kombat/bg.png".rl.toTexture().id, menuWidth, menuHeight)

      ImGui.setCursorPos(0f, 0f)
      if(loadGame()) {
        if(menuSelected == Menus.MAIN) mainMenu()
        if(menuSelected == Menus.UPGRADES) upgradesMenu()
      }

      ImGui.endChild()

      /* Draw face */

      ImGui.setCursorPos(0f, 0f)
      ImGui.beginChild(
        "halva-kombat-menu-main-front",
        frameWidth, frameHeight,
        true,
        ImGuiWindowFlags.NoMove or ImGuiWindowFlags.NoResize or ImGuiWindowFlags.NoTitleBar or ImGuiWindowFlags.NoScrollbar
        or ImGuiWindowFlags.NoScrollWithMouse or ImGuiWindowFlags.NoBackground or ImGuiWindowFlags.NoNavFocus or ImGuiWindowFlags.NoInputs
      )

      /* Draw image */
      ImGui.setCursorPos(0f, 0f)
      ImGui.image("kotloudron:textures/gui/halva_kombat/face.png".rl.toTexture().id, frameWidth, frameHeight)

      ImGui.endChild()

      ImGui.end()
    }
  }

  override fun isPauseScreen() = false

  /* Private functions */

  private fun loadGame(): Boolean {
    ImGui.setCursorPos(ImGui.getWindowWidth() / 2 - (ImGui.getWindowWidth() - 64f) / 2, ImGui.getWindowHeight() / 2 - 128f / 2)

    if(progressLoading.get() <= 100f) {
      ImGui.progressBar(progressLoading.get(), ImGui.getWindowWidth() - 64f, 128f, "Загрузка... | $progressLoading%")

      progressLoading.set(progressLoading.get() + 1f)

      return false
    } else {
      return true
    }
  }

  private fun mainMenu() {
    ImGui.setCursorPos(32f, 32f)
    ImGui.pushStyleColor(ImGuiCol.MenuBarBg, 25, 49, 156, 255)
    val barProgress = ImFloat( (score.get() / nextScore.get()).toFloat() )
    ImGui.progressBar(barProgress.get(), ImGui.getWindowWidth(), 32f)
    ImGui.popStyleColor()

    ImGui.setCursorPos(32f, 64f)
    ImGui.text("Очки: $score / $nextScore")
    ImGui.setCursorPosX(32f)
    ImGui.text("Улучшений: $pointUpgrades")

    ImGui.setCursorPos(ImGui.getWindowWidth() / 2 - 256f / 2, 256f / 2)
    clickButton()
    nextScoreUpd()
  }

  private fun upgradesMenu() {

  }

  /* Private utilities */

  private fun clickButton() {
    val textureUV =
      if(ImGui.isItemClicked()) arrayOf(0f, 0f, 0.5f, 1f)
      else arrayOf(0.5f, 0f, 1f, 1f)

    val clickButton =
      ImGui.imageButton(
        "kotloudron:textures/gui/halva_kombat/click_button.png".rl.toTexture().id,
        256f, 256f,
        textureUV[0], textureUV[1], textureUV[2], textureUV[3]
      )

    if(clickButton) {
      score.set(score.get() + perClick.get())
    }
  }

  private fun nextScoreUpd() {
    if(score.get() >= nextScore.get()) {
      val newNextScore = ImInt(round(nextScore.get()  * 1.15).toInt())

      nextScore.set(newNextScore)

      score.set(0)

      pointUpgrades.set(pointUpgrades.get() + 1)
    }
  }
}

enum class Menus {
  MAIN,
  UPGRADES
}