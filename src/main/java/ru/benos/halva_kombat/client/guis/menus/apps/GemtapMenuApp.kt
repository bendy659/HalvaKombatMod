package ru.benos.halva_kombat.client.guis.menus.apps

import imgui.ImGui
import imgui.flag.ImGuiCol
import imgui.flag.ImGuiStyleVar
import imgui.flag.ImGuiWindowFlags
import imgui.type.ImBoolean
import imgui.type.ImFloat
import imgui.type.ImInt
import net.minecraft.sounds.SoundSource
import net.minecraft.world.entity.player.Player
import ru.benos.halva_kombat.HalvaKombat.Companion.LOGGER
import ru.benos.halva_kombat.HalvaKombat.Companion.MODID
import ru.benos.halva_kombat.HalvaKombat.Companion.debug
import ru.benos.halva_kombat.client.guis.Utils.phoneBg
import ru.benos.halva_kombat.common.items.PhoneItemData
import ru.benos.halva_kombat.common.registries.Registries.CLICK_COIN
import ru.hollowhorizon.hc.client.utils.get
import ru.hollowhorizon.hc.client.utils.rl
import ru.hollowhorizon.hc.client.utils.toTexture
import ru.hollowhorizon.hc.common.capabilities.CapabilityInstance
import ru.hollowhorizon.hc.common.capabilities.HollowCapabilityV2

object GemtapMenuApp {
  private val pPlayer = PhoneItemData.getPlayer()
  private val pLevel = PhoneItemData.getLevel()
  private val pData = pPlayer[GemtapData::class]

  var balance = ImInt(pData.dataBalance)
  var nextLvlUp = ImInt(pData.dataNextLvlUp)
  var upgradePoints = ImInt(pData.dataUpgradePoints)
  var clickAdd = ImInt(pData.dataClickAdd)
  var upgradeClickAdd = ImInt(pData.dataUpgradeClickAdd)

  var loadBar = ImFloat(0f)
  var progress = ImInt(0)
  var isGameLoad = ImBoolean(false)

  fun onGemtapMenu() {
    ImGui.beginChild("gemtap-screen-menu", ImGui.getWindowWidth(), ImGui.getWindowHeight(), false, ImGuiWindowFlags.NoBackground)
    if(!isGameLoad.get())
      gameLoad()
    else {
      //phoneBg("gemtap/bg.png")

      ImGui.setCursorPos(64f, 100f)
      showData("coin", balance); ImGui.sameLine(); ImGui.text(" / $nextLvlUp"); ImGui.newLine()
      ImGui.setCursorPos(64f, 140f)
      showData("upgrade", upgradePoints); ImGui.newLine()
      ImGui.setCursorPos(64f, 180f)
      showData("click", clickAdd); ImGui.newLine()

      clickButton()

      //phoneBg("gemtap/bg.png")
    }
    ImGui.endChild()
  }

  private fun gameLoad(): Boolean {
    val width = ImGui.getWindowWidth()
    val height = ImGui.getWindowHeight()

    val maxBar = width * 0.9f

    if(progress.get() < 100) {
      phoneBg("gemtap/bg.png", arrayOf(0.35f, 0.35f, 0.35f, 1f))
      ImGui.setCursorPos(width / 2 - (width * 0.8f) / 2, height / 2.5f)
      ImGui.beginChild("load-game-bar-bg", width * 0.95f, height * 0.16f, false, ImGuiWindowFlags.NoBackground or ImGuiWindowFlags.NoScrollbar or ImGuiWindowFlags.NoScrollWithMouse)
      ImGui.image("$MODID:textures/gui/gemtap/game_load_bar.png".rl.toTexture().id, width * 0.8f, height * 0.1f, 0f, 0f, 1f, 0.5f)
      ImGui.endChild()
      ImGui.setCursorPos(width / 2 - (width * 0.8f) / 2, height / 2.5f)
      ImGui.beginChild("load-game-bar", loadBar.get(), height * 0.16f, false, ImGuiWindowFlags.NoBackground or ImGuiWindowFlags.NoScrollbar or ImGuiWindowFlags.NoScrollWithMouse)
      ImGui.image("$MODID:textures/gui/gemtap/game_load_bar.png".rl.toTexture().id, width * 0.8f, height * 0.1f, 0f, 0.5f, 1f, 1f)
      ImGui.endChild()
      ImGui.newLine()
      val textWidth = ImGui.calcTextSize("$progress%").x
      ImGui.setCursorPosX(width / 2 - textWidth / 2)
      ImGui.text("$progress%")
    }

    if(loadBar.get() < maxBar) {
      loadBar.set(loadBar.get() + (width * 0.9f) * 0.04f)
      isGameLoad.set(false)
    } else {
      isGameLoad.set(true)
    }
    progress.set(( (loadBar.get() / maxBar) * 100).toInt() )
    if(debug) LOGGER.debug("Gemtap load: Progress {}%, Bar: {}, MaxBar: {}", progress, loadBar.get(), maxBar)
    return isGameLoad.get()
  }

  private fun showData(dataName: String, value: ImInt) {
    ImGui.image("$MODID:textures/gui/gemtap/$dataName.png".rl.toTexture().id, 32f, 32f); ImGui.sameLine(); ImGui.text(value.get().toString())
  }

  private fun clickButton() {
    ImGui.pushID("clicker-button")
    ImGui.setCursorPos(ImGui.getWindowWidth() / 2 - 256f / 2, (ImGui.getWindowHeight() / 2 - 256f / 2) / 1.25f)
    ImGui.pushStyleVar(ImGuiStyleVar.FrameRounding, 32f)
    ImGui.pushStyleColor(ImGuiCol.Button, 255, 255, 255, 0)
    ImGui.pushStyleColor(ImGuiCol.ButtonHovered, 255, 255, 255, 0)
    ImGui.pushStyleColor(ImGuiCol.ButtonActive, 255, 255, 255, 0)
    val clicker = ImGui.imageButton("$MODID:textures/gui/gemtap/coin.png".rl.toTexture().id, 256f, 256f)
    ImGui.popStyleColor(3)
    ImGui.popStyleVar()

    if(clicker) {
      pLevel.playSound(pPlayer, pPlayer.blockPosition(), CLICK_COIN.get(), SoundSource.PLAYERS, 0.5f, 1f)
      balance.set(balance.get() + clickAdd.get())
      if(balance.get() >= nextLvlUp.get()) {
        balance.set(0)
        nextLvlUp.set((nextLvlUp.get() * 1.65).toInt())
        upgradePoints.set(upgradeClickAdd.get() + 1)
      }
      if (debug) LOGGER.debug("Clicked {} count.", balance)
    }
    ImGui.popID()
  }

  /* ---- */

  fun readDatas() {
    try {
      balance.set(GemtapData().dataBalance)
      nextLvlUp.set(GemtapData().dataNextLvlUp)
      upgradePoints.set(GemtapData().dataUpgradePoints)
      clickAdd.set(GemtapData().dataClickAdd)
      upgradePoints.set(GemtapData().dataUpgradeClickAdd)
    } catch (_: Exception) {
      if (debug) LOGGER.error("Error read datas \"GemtapApp.data.latest\". Loaded default value!")

      balance.set(GemtapDataDefault().dataBalance)
      nextLvlUp.set(GemtapDataDefault().dataNextLvlUp)
      upgradePoints.set(GemtapDataDefault().dataUpgradePoints)
      clickAdd.set(GemtapDataDefault().dataClickAdd)
      upgradePoints.set(GemtapDataDefault().dataUpgradeClickAdd)
    }
  }

  fun saveDatas() {
    GemtapData().dataBalance = this.balance.get()
    GemtapData().dataNextLvlUp = this.nextLvlUp.get()
    GemtapData().dataUpgradePoints = this.upgradePoints.get()
    GemtapData().dataClickAdd = this.clickAdd.get()
    GemtapData().dataUpgradeClickAdd = this.clickAdd.get()
  }
}

@HollowCapabilityV2(Player::class)
class GemtapData: CapabilityInstance() {
  var dataBalance by syncable(0)
  var dataNextLvlUp by syncable(2500)
  var dataUpgradePoints by syncable(0)
  var dataClickAdd by syncable(1)
  var dataUpgradeClickAdd by syncable(0)
}

class GemtapDataDefault {
  val dataBalance = 0
  val dataNextLvlUp = 2500
  val dataUpgradePoints = 0
  val dataClickAdd = 1
  val dataUpgradeClickAdd = 0
}