package ru.benos.he_addon.utils

import imgui.ImGui
import imgui.flag.*
import imgui.type.ImBoolean
import imgui.type.ImInt
import net.minecraft.client.Minecraft
import net.minecraft.client.resources.sounds.SimpleSoundInstance
import net.minecraft.client.resources.sounds.SoundInstance
import net.minecraft.locale.Language
import net.minecraft.sounds.SoundSource
import ru.hollowhorizon.hc.client.imgui.ImguiHandler
import ru.hollowhorizon.hc.client.utils.rl
import ru.hollowhorizon.hc.client.utils.toTexture

/**
 * Special helper class
 */
object HelperPack {

  /**
   * Upgrade version ImGui.checkbox()
   */
  fun checkbox(
    texturePath: String = "hollowengine:textures/gui/npc_create/checkbox.png",
    text: String = "##",
    check: ImBoolean = ImBoolean(false),
    sizeX: Float = 1f,
    sizeY: Float = 1f
  ): Boolean {

    // Isolation
    ImGui.pushID("gui.create_npc.$text")

    // Set texture sprite
    if (!check.get()) ImGui.image(texturePath.rl.toTexture().id, sizeX, sizeY, 0f, 0f, 0.5f, 1f)
    else ImGui.image(texturePath.rl.toTexture().id, sizeX, sizeY, 0.5f, 0f, 1f, 1f)

    // Set checked
    if (ImGui.isItemClicked()) {
      check.set(!check.get())

      clickSound()
    }

    ImGui.sameLine()
    ImGui.text(" $text")

    ImGui.popID()

    return check.get()
  }

  /**
   * Upgrade button image & text on button center support
   */
  fun imageButton(
    text: String,
    texturePath: String = "hollowengine:textures/gui/npc_create/button.png",
    sizeX: Float, sizeY: Float,
    posX: Float, posY: Float,
    fontSize: Int,
    isActive: ImBoolean = ImBoolean(false),
    isTabButton: ImBoolean = ImBoolean(false)
  ): Boolean {
    val oldCursorPos = ImGui.getCursorPos()
    val type = arrayOf(
      0f, 0f, 1f, 0.5f, // Is NOT Active
      0f, 0.5f, 1f, 1f // Is Active
    )
    val id = ImInt().apply { set(-1) }

    if(!isActive.get()) id.apply { set(-1) }
    else id.apply { set(3) }

    ImGui.setCursorPos(posX - 15, posY)
    ImGui.image(
      texturePath.rl.toTexture().id,
      sizeX + 30, sizeY,
      type[id.get() + 1], type[id.get() + 2], type[id.get() + 3], type[id.get() + 4]
    )

    ImGui.pushID("tabs.tab-$text")
    ImGui.pushStyleColor(ImGuiCol.Button, 0, 0, 0, 0)
    ImGui.pushStyleColor(ImGuiCol.ButtonHovered, 0, 0, 0, 0)
    ImGui.pushStyleColor(ImGuiCol.ButtonActive, 0, 0, 0, 0)
    if(isTabButton.get()) ImGui.pushStyleVar(ImGuiStyleVar.ButtonTextAlign, sizeX / 512, if(isActive.get()) 0.5f else 1f)

    ImGui.pushFont(ImguiHandler.FONTS[fontSize])
    ImGui.setCursorPos(posX, posY)
    if(ImGui.button(text, sizeX, sizeY)) {
      clickSound()
      isActive.apply { set(true) }
    }
    ImGui.popFont()
    if(isTabButton.get()) ImGui.popStyleVar()
    ImGui.popStyleColor(3)
    ImGui.popID()

    ImGui.setCursorPos(oldCursorPos.x, oldCursorPos.y)

    return isActive.get()
  }

  /**
   * Sound: *click*
   */
  private fun clickSound(volume: Float = 0.16f) {
    Minecraft.getInstance().soundManager.play(
      SimpleSoundInstance(
        "hc:sfx/choice_button".rl, SoundSource.PLAYERS, volume, 1f, Minecraft.getInstance().player!!.random,
        false, 0, SoundInstance.Attenuation.NONE, 0.0, 0.0, 0.0, true
      )
    )
  }

  /**
   * Line separator
   */
  fun separator(sizeX: Float) {
    ImGui.image("hollowengine:textures/gui/npc_create/line.png".rl.toTexture().id, sizeX, 8f)
  }

  /**
   * Helper translation
   */
  public fun lang(translationID: String = "empty"): String {
    return Language.getInstance().getOrDefault("hollowengine." + translationID)
  }
}