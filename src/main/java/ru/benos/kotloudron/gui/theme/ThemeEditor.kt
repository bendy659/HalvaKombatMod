package ru.benos.kotloudron.gui.theme

import com.mojang.blaze3d.vertex.PoseStack
import imgui.ImGui
import imgui.flag.ImGuiWindowFlags
import imgui.type.ImBoolean
import net.minecraft.client.Minecraft
import ru.hollowhorizon.hc.client.imgui.ImGuiMethods.centerWindow
import ru.hollowhorizon.hc.client.imgui.ImguiHandler
import ru.hollowhorizon.hc.client.screens.HollowScreen
import ru.hollowhorizon.hc.client.utils.rl
import ru.hollowhorizon.hc.client.utils.toTexture

object ThemeEditor: HollowScreen() {
  //val file = File(THEMEDIR, "default.json")

  private val bg0 = floatArrayOf(1f, 1f, 1f, 1f)
  private val bg1 = floatArrayOf(1f, 1f, 1f, 1f)

  override fun render(pPoseStack: PoseStack, pMouseX: Int, pMouseY: Int, pPartialTick: Float) {
    super.render(pPoseStack, pMouseX, pMouseY, pPartialTick)

    ImguiHandler.drawFrame {
      ImGui.begin(
        "NPC Create GUI | Color setting", ImBoolean(true),
        ImGuiWindowFlags.NoResize or ImGuiWindowFlags.NoCollapse or ImGuiWindowFlags.NoMove
      )
      centerWindow()
      ImGui.setNextWindowFocus()

      ImGui.setWindowSize(
        Minecraft.getInstance().window.width * 0.75f,
        Minecraft.getInstance().window.height * 0.75f
      )

      // Main
      ImGui.setCursorPosX(86f)
        ImGui.beginChild("Main menu", ImGui.getWindowWidth() * 0.9f, ImGui.getWindowHeight() * 0.9f, true, ImGuiWindowFlags.AlwaysAutoResize)
         ImGui.text("Background")
         ImGui.setNextItemWidth(512f)
         ImGui.colorEdit4(" bg_bg", bg0)
         ImGui.setNextItemWidth(512f)
         ImGui.colorEdit4(" bg_border", bg1)
         ImGui.image(
           "hollowengine:textures/gui/npc_create/bg/bg0.png".rl.toTexture().id,
           1024f, 512f,
           0f, 0f, 1f, 1f,
           bg0[0], bg0[1], bg0[2], bg0[0]
         ); ImGui.sameLine(); ImGui.setCursorPosX(8f)
         ImGui.image(
           "hollowengine:textures/gui/npc_create/bg/bg1.png".rl.toTexture().id,
           1024f, 512f,
           0f, 0f, 1f, 1f,
           bg1[0], bg1[1], bg1[2], bg1[3]
         )

         ImGui.newLine()
         ImGui.separator()
         ImGui.newLine()

        ImGui.endChild()

        ImGui.setCursorPos(ImGui.getWindowWidth() * 0.8f, ImGui.getWindowHeight() * 0.95f)
        //if(ImGui.button("Save colors")) saveTheme()

      ImGui.end()
    }
  }
  //private fun saveTheme() {
  //  val data = LinkedHashMap<String, ColorPalette>()
  //
  //  // Create ALL DATA
  //  data["bg0"] = ColorPalette(bg0[0], bg0[1], bg0[2], bg0[3])
  //  data["bg0"] = ColorPalette(bg1[0], bg1[1], bg1[2], bg1[3])
  //
  //  // Write
  //  try {
  //    val gson = GsonBuilder().setPrettyPrinting().create()
  //    val write = gson.toJson(data)
  //    FileWriter(file).use { writer -> writer.write(write) }
  //    HollowCore.LOGGER.info("[THEME] Theme saved.")
  //
  //  } catch (e: Exception) {
  //    HollowCore.LOGGER.error("[THEME] Error save theme.")
  //    e.printStackTrace()
  //  }
  //}

  //fun loadTheme(theme: String, target: String): FloatArray {
  //  val read = FileReader("$THEMEDIR/$theme.json")
  //  val jsonElement = JsonParser.parseReader(read)
  //  val obj = jsonElement.asJsonObject
  //
  //  if(!obj.has(target)) {
  //    HollowCore.LOGGER.warn("[THEME] Target '$target' on theme '$theme' not found!")
  //    return floatArrayOf(0f, 0f, 0f, 0f)
  //
  //  } else {
  //    val trg = obj.getAsJsonObject(target)
  //
  //    return floatArrayOf(
  //      trg.get("R").asFloat,
  //      trg.get("G").asFloat,
  //      trg.get("B").asFloat,
  //      trg.get("A").asFloat
  //    )
  //  }
  //}
}

class ColorPalette(
  val R: Float,
  val G: Float,
  val B: Float,
  val A: Float
)