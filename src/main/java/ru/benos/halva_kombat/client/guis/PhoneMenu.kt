package ru.benos.halva_kombat.client.guis

import com.mojang.blaze3d.vertex.PoseStack
import imgui.ImGui
import imgui.flag.ImGuiWindowFlags
import net.minecraft.client.Minecraft
import net.minecraft.world.entity.player.Player
import org.lwjgl.glfw.GLFW
import ru.benos.halva_kombat.HalvaKombat.Companion.MODID
import ru.benos.halva_kombat.client.guis.Utils.phoneBg
import ru.benos.halva_kombat.client.guis.menus.AppsMenu.appID
import ru.benos.halva_kombat.client.guis.menus.AppsMenu.onAppsMenu
import ru.benos.halva_kombat.client.guis.halva_kombat.menus.BankMenuApp.onBankMenu
import ru.benos.halva_kombat.client.guis.menus.LockScreen.onLockScreen
import ru.benos.halva_kombat.client.guis.menus.apps.GemtapMenuApp.isGameLoad
import ru.benos.halva_kombat.client.guis.menus.apps.GemtapMenuApp.loadBar
import ru.benos.halva_kombat.client.guis.menus.apps.GemtapMenuApp.onGemtapMenu
import ru.benos.halva_kombat.client.guis.menus.apps.SettingsMenuApp.onSettingsMenu
import ru.hollowhorizon.hc.client.imgui.ImguiHandler
import ru.hollowhorizon.hc.client.screens.HollowScreen
import ru.hollowhorizon.hc.client.utils.rl
import ru.hollowhorizon.hc.client.utils.toTexture

var menuSelected = Menus.LOCK_SCREEN

object PhoneMenu: HollowScreen() {
  var pPlayer: Player? = null

  override fun render(pPoseStack: PoseStack, pMouseX: Int, pMouseY: Int, pPartialTick: Float) {
    ImguiHandler.drawFrame {
      val screen = Minecraft.getInstance().window

      ImGui.setNextWindowSize(screen.height * 0.6f, screen.height * 0.98f)
      ImGui.begin(
        "phone-menu",
        ImGuiWindowFlags.NoMove or ImGuiWindowFlags.NoResize or ImGuiWindowFlags.NoTitleBar or ImGuiWindowFlags.NoScrollbar
            or ImGuiWindowFlags.NoScrollWithMouse or ImGuiWindowFlags.NoBackground
      )
      ImGui.setWindowPos(screen.width / 2 - ImGui.getWindowWidth() / 2, screen.height / 2 - ImGui.getWindowHeight() / 2)

      /* Draw background */
      phoneBg("bg.png")

      /* Draw menu */
      ImGui.setCursorPos(-8f, 0f)
      ImGui.beginChild("phone-screen", ImGui.getWindowWidth(), ImGui.getWindowHeight(), true, ImGuiWindowFlags.NoBackground or ImGuiWindowFlags.NoScrollbar or ImGuiWindowFlags.NoScrollWithMouse)
      if(menuSelected == Menus.LOCK_SCREEN)
        if(onLockScreen()) menuSelected = Menus.APPS
      if(menuSelected == Menus.APPS && appID.get() == 0) {
        val appSelect = onAppsMenu().get()
        if (appSelect == 1) menuSelected = Menus.GEM_TAP
        if (appSelect == 2) menuSelected = Menus.BANK
        if (appSelect == 3) menuSelected = Menus.SETTINGS
      }
      if(menuSelected == Menus.GEM_TAP) onGemtapMenu()
      if(menuSelected == Menus.BANK) onBankMenu()
      if(menuSelected == Menus.SETTINGS) onSettingsMenu()
      ImGui.endChild()

      /* Draw face */
      ImGui.setCursorPos(0f, 0f)
      ImGui.beginChild(
        "phone-menu-face",
        ImGui.getWindowWidth(), ImGui.getWindowHeight(),
        false,
        ImGuiWindowFlags.NoNavFocus or ImGuiWindowFlags.NoInputs or ImGuiWindowFlags.NoResize or ImGuiWindowFlags.NoBackground
      )
      ImGui.image("$MODID:textures/gui/face.png".rl.toTexture().id, ImGui.getWindowWidth(), ImGui.getWindowHeight())
      //ImGui.image("$MODID:textures/gui/face.png".rl.toTexture().id, ImGui.getWindowWidth(), ImGui.getWindowHeight(), 0f, 0f, 1f, 1f, 1f, 1f, 1f, 0.25f)
      ImGui.endChild()

      ImGui.end()
    }
  }

  override fun isPauseScreen() = false

  override fun keyPressed(pKeyCode: Int, pScanCode: Int, pModifiers: Int): Boolean {
    if (pKeyCode == GLFW.GLFW_KEY_ESCAPE) {
      menuSelected = Menus.LOCK_SCREEN
      isGameLoad.set(false)
      loadBar.set(0f)
      appID.set(0)
    }
    if(pKeyCode == GLFW.GLFW_KEY_F12) Minecraft.getInstance().setScreen(PreviewGui)

    return super.keyPressed(pKeyCode, pScanCode, pModifiers)
  }
}

enum class Menus {
  LOCK_SCREEN,
  APPS,
  GEM_TAP,
  BANK,
  SETTINGS
}