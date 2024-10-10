package ru.benos.halva_kombat.client.guis.halva_kombat

import com.mojang.blaze3d.vertex.PoseStack
import imgui.ImGui
import imgui.flag.ImGuiWindowFlags
import imgui.type.ImInt
import net.minecraft.client.Minecraft
import net.minecraft.world.entity.player.Player
import org.lwjgl.glfw.GLFW
import ru.benos.halva_kombat.HalvaKombat.Companion.MODID
import ru.benos.halva_kombat.client.guis.halva_kombat.menus.AppsMenu.onAppsMenu
import ru.benos.halva_kombat.client.guis.halva_kombat.menus.BankMenuApp.onBankMenu
import ru.benos.halva_kombat.client.guis.halva_kombat.menus.LockScreen.onLockScreen
import ru.benos.halva_kombat.client.guis.halva_kombat.menus.apps.GemtapMenuApp.onGemtapMenu
import ru.benos.halva_kombat.client.guis.halva_kombat.menus.apps.SettingsMenuApp.onSettingsMenu
import ru.hollowhorizon.hc.client.imgui.ImguiHandler
import ru.hollowhorizon.hc.client.screens.HollowScreen
import ru.hollowhorizon.hc.client.utils.get
import ru.hollowhorizon.hc.client.utils.rl
import ru.hollowhorizon.hc.client.utils.toTexture
import ru.hollowhorizon.hc.common.capabilities.CapabilityInstance
import ru.hollowhorizon.hc.common.capabilities.HollowCapabilityV2

var menuSelected = Menus.LOCK_SCREEN

class PhoneMenu(pPlayer: Player) : HollowScreen() {

  /* Variables */

  private val pData = pPlayer[HalvaKombatData::class]

  private var balance = ImInt(pData.dataBalance)
  private var nextLvlUp = ImInt(pData.dataNextLvlUp)
  private var upgradePoints = ImInt(pData.dataUpgradePoints)
  private var clickPoint = ImInt(pData.dataClickAdd)
  private var upgradeClickAdd = ImInt(pData.dataUpgradeClickAdd)

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
        if(onAppsMenu().get() == 1) onGemtapMenu()
        if(onAppsMenu().get() == 2) onBankMenu()
        if(onAppsMenu().get() == 3) onSettingsMenu()
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
      pData.dataBalance = balance.get()
      pData.dataNextLvlUp = nextLvlUp.get()
      pData.dataUpgradePoints = upgradePoints.get()
      pData.dataClickAdd = clickPoint.get()
      pData.dataUpgradeClickAdd = upgradeClickAdd.get()

      menuSelected = Menus.LOCK_SCREEN
    }

    return super.keyPressed(pKeyCode, pScanCode, pModifiers)
  }
}

enum class Menus {
  LOCK_SCREEN,
  APPS,
  GAME,
  BANK,
  SETTINGS
}

@HollowCapabilityV2(Player::class)
class HalvaKombatData: CapabilityInstance() {
  var dataBalance by syncable(0)
  var dataNextLvlUp by syncable(2500)
  var dataUpgradePoints by syncable(0)
  var dataClickAdd by syncable(1)
  var dataUpgradeClickAdd by syncable(0)
}