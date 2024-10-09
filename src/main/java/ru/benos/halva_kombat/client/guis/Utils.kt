package ru.benos.halva_kombat.client.guis

import imgui.ImGui
import net.minecraft.server.level.ServerLevel
import net.minecraftforge.server.ServerLifecycleHooks
import ru.benos.halva_kombat.HalvaKombat.Companion.MODID
import ru.hollowhorizon.hc.client.utils.rl
import ru.hollowhorizon.hc.client.utils.toTexture

object Utils {
  fun button(
    label: String?,
    size: Array<Float> = arrayOf(256f, 256f),
    basicColor: Array<Int> = arrayOf(24, 18, 207),
    hoverColor: Array<Int> = arrayOf(255, 255, 255),
    clickColor: Array<Int> = arrayOf(255, 255, 255)
  ): Boolean {
    var result = false

    val oldCursorPos = ImGui.getCursorPos()

    ImGui.pushID("button-$label")
    ImGui.beginChild("button-$label-widget", size[0], size[1])

    // Color swap logic
    val color =
      if(ImGui.isItemHovered())
        arrayOf((hoverColor[0] / 255).toFloat(), (hoverColor[1] / 255).toFloat(), (hoverColor[2] / 255).toFloat())
      else if(ImGui.isItemHovered())
        arrayOf((clickColor[0] / 255).toFloat(), (clickColor[1] / 255).toFloat(), (clickColor[2] / 255).toFloat())
      else
        arrayOf((basicColor[0] / 255).toFloat(), (basicColor[1] / 255).toFloat(), (basicColor[2] / 255).toFloat())

    // Top
    ImGui.setCursorPos(8f, 8f) // Left
    ImGui.image("$MODID:textures/gui/halva_kombat/button.png".rl.toTexture().id, size[0] / 3, size[1] / 3, 0f, 0f, 0.25f, 0.25f, color[0] / 255, color[0] / 255, color[0] / 255, 1f)
    ImGui.setCursorPos(size[0] / 3 + 8f, 0f) // Center
    ImGui.image("$MODID:textures/gui/halva_kombat/button.png".rl.toTexture().id, size[0] / 3, size[1] / 3, 0.25f, 0f, 0.56f, 0.25f, color[0] / 255, color[0] / 255, color[0] / 255, 1f)
    ImGui.setCursorPos(size[0] / 3 + 8 + size[0] / 3, 8f) // Right
    ImGui.image("$MODID:textures/gui/halva_kombat/button.png".rl.toTexture().id, size[0] / 3, size[1] / 3, 0.5f, 0f, 0.75f, 0.25f, color[0] / 255, color[0] / 255, color[0] / 255, 1f)

    // Center
    ImGui.setCursorPos(8f, size[1] / 3 + 8f) // Left
    ImGui.image("$MODID:textures/gui/halva_kombat/button.png".rl.toTexture().id, size[0] / 3, size[1] / 3, 0f, 0.25f, 0.25f, 0.5f, color[0] / 255, color[0] / 255, color[0] / 255, 1f)
    ImGui.setCursorPos(size[0] / 3 + 8f, size[1] / 3 + 8f) // Center
    ImGui.image("$MODID:textures/gui/halva_kombat/button.png".rl.toTexture().id, size[0] / 3, size[1] / 3, 0.25f, 0.25f, 0.56f, 0.5f, color[0] / 255, color[0] / 255, color[0] / 255, 1f)
    ImGui.setCursorPos(size[0] / 3 + 8 + size[0] / 3, size[1] / 3 + 8f) // Right
    ImGui.image("$MODID:textures/gui/halva_kombat/button.png".rl.toTexture().id, size[0] / 3, size[1] / 3, 0.5f, 0.25f, 0.75f, 0.5f, color[0] / 255, color[0] / 255, color[0] / 255, 1f)

    // Bottom
    ImGui.setCursorPos(8f, size[1] / 3 + 8f + size[3] / 3) // Left
    ImGui.image("$MODID:textures/gui/halva_kombat/button.png".rl.toTexture().id, size[0] / 3, size[1] / 3, 0f, 0.5f, 0.25f, 0.75f, color[0] / 255, color[0] / 255, color[0] / 255, 1f)
    ImGui.setCursorPos(size[0] / 3 + 8f, size[1] / 3 + 8f + size[3] / 3) // Center
    ImGui.image("$MODID:textures/gui/halva_kombat/button.png".rl.toTexture().id, size[0] / 3, size[1] / 3, 0.25f, 0.5f, 0.56f, 0.75f, color[0] / 255, color[0] / 255, color[0] / 255, 1f)
    ImGui.setCursorPos(size[0] / 3 + 8 + size[0] / 3, size[1] / 3 + 8f + size[3] / 3) // Right
    ImGui.image("$MODID:textures/gui/halva_kombat/button.png".rl.toTexture().id, size[0] / 3, size[1] / 3, 0.5f, 0.5f, 0.75f, 0.75f, color[0] / 255, color[0] / 255, color[0] / 255, 1f)

    ImGui.setCursorPos(oldCursorPos.x, oldCursorPos.y)

    // button logic
    result = ImGui.isItemClicked()

    ImGui.endChild()
    ImGui.popID()

    return result
  }

  fun getRealTime24hFormat(): Pair<Long, Long> {
    val tickTime = ServerLifecycleHooks.getCurrentServer().worldData.overworldData().dayTime % 24000
    val hours = (tickTime / 1000) % 24
    val minutes = (tickTime % 1000) * 60 / 1000

    return hours to minutes
  }
}