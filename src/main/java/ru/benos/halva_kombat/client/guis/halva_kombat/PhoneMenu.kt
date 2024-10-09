package ru.benos.halva_kombat.client.guis.halva_kombat

import com.mojang.blaze3d.vertex.PoseStack
import imgui.ImGui
import imgui.flag.ImGuiWindowFlags
import imgui.type.ImInt
import net.minecraft.client.Minecraft
import net.minecraft.network.chat.Component
import net.minecraft.world.entity.player.Player
import net.minecraftforge.client.event.ScreenEvent
import net.minecraftforge.eventbus.api.SubscribeEvent
import org.lwjgl.glfw.GLFW
import ru.benos.halva_kombat.HalvaKombat.Companion.MODID
import ru.benos.halva_kombat.HalvaKombat.Companion.debug
import ru.benos.halva_kombat.client.guis.halva_kombat.menus.AppsMenu.onAppsMenu
import ru.benos.halva_kombat.client.guis.halva_kombat.menus.BankMenuApp.onBankMenu
import ru.benos.halva_kombat.client.guis.halva_kombat.menus.BlockScreen.onLockScreen
import ru.benos.halva_kombat.client.guis.halva_kombat.menus.apps.GemtapMenuApp.onGemtapMenu
import ru.benos.halva_kombat.client.guis.halva_kombat.menus.apps.SettingsMenuApp.onSettingsMenu
import ru.hollowhorizon.hc.client.imgui.ImguiHandler
import ru.hollowhorizon.hc.client.screens.HollowScreen
import ru.hollowhorizon.hc.client.utils.rl
import ru.hollowhorizon.hc.client.utils.toTexture

class PhoneMenu(val pPlayer: Player) : HollowScreen() {

  /* Variables */

  private var balance = HalvaKombatData.balance
  private var nextLvlUp = HalvaKombatData.nextLvlUp
  private var upgradePoints = HalvaKombatData.upgradePoints
  private var clickPoint = HalvaKombatData.clickPoint
  private var upgradeClickAdd = HalvaKombatData.upgradeClickAdd

  var menuSelected = Menus.LOCK_SCREEN

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

      /* Draw menu */
      ImGui.setCursorPos(-8f, 0f)
      ImGui.beginChild("phone-screen", ImGui.getWindowWidth(), ImGui.getWindowHeight(), true, ImGuiWindowFlags.NoBackground or ImGuiWindowFlags.NoScrollbar or ImGuiWindowFlags.NoScrollWithMouse)
      if(menuSelected == Menus.LOCK_SCREEN)
        if(onLockScreen()) menuSelected = Menus.APPS
      if(menuSelected == Menus.APPS) {
        if(onAppsMenu() == 1) onGemtapMenu()
        if(onAppsMenu() == 2) onBankMenu()
        if(onAppsMenu() == 3) onSettingsMenu()
      }
      ImGui.endChild()

      /* Draw background */
      ImGui.setCursorPos(16f, 32f)
      ImGui.image("$MODID:textures/gui/bg.png".rl.toTexture().id, ImGui.getWindowWidth() - 16f, ImGui.getWindowHeight() - 32f)

      /* Draw face */
      ImGui.setCursorPos(0f, 0f)
      ImGui.beginChild(
        "phone-menu-face",
        ImGui.getWindowWidth(), ImGui.getWindowHeight(),
        false,
        ImGuiWindowFlags.NoNavFocus or ImGuiWindowFlags.NoInputs or ImGuiWindowFlags.NoResize or ImGuiWindowFlags.NoBackground
      )
      ImGui.image("$MODID:textures/gui/face.png".rl.toTexture().id, ImGui.getWindowWidth(), ImGui.getWindowHeight())
      ImGui.endChild()

      ImGui.end()
    }
  }

  override fun isPauseScreen() = false

  override fun keyPressed(pKeyCode: Int, pScanCode: Int, pModifiers: Int): Boolean {
    if (pKeyCode == GLFW.GLFW_KEY_ESCAPE) {
      HalvaKombatData.balance = balance
      HalvaKombatData.nextLvlUp = nextLvlUp
      HalvaKombatData.upgradePoints = upgradePoints
      HalvaKombatData.clickPoint = clickPoint
      HalvaKombatData.upgradeClickAdd = upgradeClickAdd

      menuSelected = Menus.LOCK_SCREEN
    }

    return super.keyPressed(pKeyCode, pScanCode, pModifiers)
  }

  @SubscribeEvent
  fun onGuiClose(e: ScreenEvent.Closing) {
    if(menuSelected == Menus.LOCK_SCREEN || menuSelected == Menus.APPS)
      e.isCanceled = false
    else
      e.isCanceled = true

    if(debug) pPlayer.sendSystemMessage(Component.literal("Телефон выключен..."))
  }
}

enum class Menus {
  LOCK_SCREEN,
  APPS,
  GAME,
  BANK,
  SETTINGS
}

data object HalvaKombatData {
  var balance: ImInt = ImInt(0)
  var nextLvlUp: ImInt = ImInt(2500)
  var upgradePoints: ImInt = ImInt(0)
  var clickPoint: ImInt = ImInt(1)
  var upgradeClickAdd: ImInt = ImInt(900)
}