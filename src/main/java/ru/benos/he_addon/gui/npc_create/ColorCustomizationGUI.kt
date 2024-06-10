package ru.benos.he_addon.gui.npc_create

import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import com.mojang.blaze3d.vertex.PoseStack
import imgui.ImGui
import imgui.flag.ImGuiWindowFlags
import imgui.type.ImBoolean
import net.minecraft.client.Minecraft
import ru.hollowhorizon.hc.HollowCore
import ru.hollowhorizon.hc.client.imgui.ImGuiMethods.centerWindow
import ru.hollowhorizon.hc.client.imgui.ImguiHandler
import ru.hollowhorizon.hc.client.screens.HollowScreen
import ru.hollowhorizon.hc.client.utils.rl
import ru.hollowhorizon.hc.client.utils.toTexture
import ru.hollowhorizon.hollowengine.common.files.DirectoryManager.HOLLOW_ENGINE
import java.io.File
import java.io.FileReader
import java.io.FileWriter

object ColorCustomizationGUI: HollowScreen() {
  private val THEMEDIR = HOLLOW_ENGINE.resolve("themes").apply { if (!exists()) mkdirs() }
  val file = File(THEMEDIR, "default.json")

  private val themeData = loadTheme(getSelectTheme())

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
         ImGui.colorEdit4(" bg_bg", themeData[0])
         ImGui.setNextItemWidth(512f)
         ImGui.colorEdit4(" bg_border", themeData[1])
         ImGui.image(
           "hollowengine:textures/gui/npc_create/bg/bg0.png".rl.toTexture().id,
           1024f, 512f,
           0f, 0f, 1f, 1f,
           themeData[0][0], themeData[0][1], themeData[0][2], themeData[0][3]
         ); ImGui.sameLine(); ImGui.setCursorPosX(8f)
         ImGui.image(
           "hollowengine:textures/gui/npc_create/bg/bg1.png".rl.toTexture().id,
           1024f, 512f,
           0f, 0f, 1f, 1f,
           themeData[1][0], themeData[1][1], themeData[1][2], themeData[1][3]
         )

         ImGui.newLine()
         ImGui.separator()
         ImGui.newLine()

        ImGui.endChild()

        ImGui.setCursorPos(ImGui.getWindowWidth() * 0.8f, ImGui.getWindowHeight() * 0.95f)
        if(ImGui.button("Save colors")) saveTheme()

      ImGui.end()
    }
  }
  private fun saveTheme() {
    val data = LinkedHashMap<String, ColorPalette>()

    // Create ALL DATA
    data["bg0"] = ColorPalette(themeData[0][0], themeData[0][1], themeData[0][2], themeData[0][3])
    data["bg0"] = ColorPalette(themeData[1][0], themeData[1][1], themeData[1][2], themeData[1][3])

    // Write
    try {
      val gson = GsonBuilder().setPrettyPrinting().create()
      val write = gson.toJson(data)
      FileWriter(file).use { writer -> writer.write(write) }
      HollowCore.LOGGER.info("[THEME] Theme saved.")

    } catch (e: Exception) {
      HollowCore.LOGGER.error("[THEME] Error save theme.")
      e.printStackTrace()
    }
  }

  fun loadTheme(theme: String): Array<FloatArray> {
    val read = FileReader("$THEMEDIR/$theme.json")
    val jsonElement = JsonParser.parseReader(read)
    val obj = jsonElement.asJsonObject

    val bg0 = obj.getAsJsonObject("bg0")
    val bg1 = obj.getAsJsonObject("bg1")
    val entity_bg0 = obj.getAsJsonObject("entity_bg0")
    val entity_bg1 = obj.getAsJsonObject("entity_bg1")
    val button0 = obj.getAsJsonObject("button0")
    val button1 = obj.getAsJsonObject("button1")
    val tab0 = obj.getAsJsonObject("tab0")
    val tab0_hover = obj.getAsJsonObject("tab0_hover")
    val tab1 = obj.getAsJsonObject("tab1")
    val tab1_hover = obj.getAsJsonObject("tab1_hover")
    val checkbox0 = obj.getAsJsonObject("checkbox0")
    val checkbox1 = obj.getAsJsonObject("checkbox1")
    val separator = obj.getAsJsonObject("separator")

    return arrayOf(
      floatArrayOf(
        bg0.get("R").asFloat,
        bg0.get("G").asFloat,
        bg0.get("B").asFloat,
        bg0.get("A").asFloat
      ),
      floatArrayOf(
        bg1.get("R").asFloat,
        bg1.get("G").asFloat,
        bg1.get("B").asFloat,
        bg1.get("A").asFloat
      ),
      floatArrayOf(
        entity_bg0.get("R").asFloat,
        entity_bg0.get("G").asFloat,
        entity_bg0.get("B").asFloat,
        entity_bg0.get("A").asFloat
      ),
      floatArrayOf(
        entity_bg1.get("R").asFloat,
        entity_bg1.get("G").asFloat,
        entity_bg1.get("B").asFloat,
        entity_bg1.get("A").asFloat
      ),
      floatArrayOf(
        button0.get("R").asFloat,
        button0.get("G").asFloat,
        button0.get("B").asFloat,
        button0.get("A").asFloat
      ),
      floatArrayOf(
        button1.get("R").asFloat,
        button1.get("G").asFloat,
        button1.get("B").asFloat,
        button1.get("A").asFloat
      ),
      floatArrayOf(
        tab0.get("R").asFloat,
        tab0.get("G").asFloat,
        tab0.get("B").asFloat,
        tab0.get("A").asFloat
      ),
      floatArrayOf(
        tab0_hover.get("R").asFloat,
        tab0_hover.get("G").asFloat,
        tab0_hover.get("B").asFloat,
        tab0_hover.get("A").asFloat
      ),
      floatArrayOf(
        tab1.get("R").asFloat,
        tab1.get("G").asFloat,
        tab1.get("B").asFloat,
        tab1.get("A").asFloat
      ),
      floatArrayOf(
        tab1_hover.get("R").asFloat,
        tab1_hover.get("G").asFloat,
        tab1_hover.get("B").asFloat,
        tab1_hover.get("A").asFloat
      ),
      floatArrayOf(
        checkbox0.get("R").asFloat,
        checkbox0.get("G").asFloat,
        checkbox0.get("B").asFloat,
        checkbox0.get("A").asFloat
      ),
      floatArrayOf(
        checkbox1.get("R").asFloat,
        checkbox1.get("G").asFloat,
        checkbox1.get("B").asFloat,
        checkbox1.get("A").asFloat
      ),
      floatArrayOf(
        separator.get("R").asFloat,
        separator.get("G").asFloat,
        separator.get("B").asFloat,
        separator.get("A").asFloat
      )
    )
  }

  fun getSelectTheme(): String {
    val select = FileReader("$THEMEDIR/theme.select").readText()
    HollowCore.LOGGER.info("[THEME] Theme select: $select")
    return select
  }
}

class ColorPalette(
  val R: Float,
  val G: Float,
  val B: Float,
  val A: Float
)

// @Serializable
// class ColorPalette(
//   val chanelRED: Float,
//   val chanelGREEN: Float,
//   val chanelBLUE: Float,
//   val chanelALPHA: Float
// )
//
// @Serializable
// class ThemeTemplate(
//   var bg0: ColorPalette,
//   var bg1: ColorPalette,
//
//   var entity_bg0: ColorPalette,
//   var entity_bg1: ColorPalette,
//
//   var button0: ColorPalette,
//   var button1: ColorPalette,
//
//   var tab0: ColorPalette,
//   var tab0_hover: ColorPalette,
//   var tab1: ColorPalette,
//   var tab1_hover: ColorPalette,
//
//   var checkbox0: ColorPalette,
//   var checkbox1: ColorPalette,
//
//   var separator: ColorPalette
// )