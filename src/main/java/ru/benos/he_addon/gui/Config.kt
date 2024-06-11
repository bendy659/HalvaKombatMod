package ru.benos.he_addon.gui

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.mojang.blaze3d.vertex.PoseStack
import imgui.ImGui
import imgui.ImVec2
import imgui.flag.ImGuiStyleVar
import imgui.flag.ImGuiWindowFlags
import imgui.type.ImBoolean
import net.minecraftforge.fml.loading.FMLPaths
import org.jetbrains.kotlin.backend.jvm.intrinsics.IrIllegalArgumentException
import ru.benos.he_addon.HEAddon
import ru.benos.he_addon.HEAddon.Companion.MODID
import ru.benos.he_addon.utils.HelperPack
import ru.benos.he_addon.utils.HelperPack.lang
import ru.hollowhorizon.hc.client.imgui.ImGuiMethods.window
import ru.hollowhorizon.hc.client.imgui.ImguiHandler
import ru.hollowhorizon.hc.client.screens.HollowScreen
import java.io.File
import java.io.FileNotFoundException
import java.io.FileReader
import java.io.FileWriter

// Зачем нам обычный конфиг, когда можно создать свой)))
object Config : HollowScreen() {
  val CONFIGDIR = FMLPaths.GAMEDIR.get().resolve("config").toFile()
  val fileConfig = File(CONFIGDIR, "$MODID.json")

  val confBool = LinkedHashMap<String, Boolean>()

  var openOldGUI = ImBoolean().apply {
    val check0 = getConfig("openOldGUI")

    if(check0 is Boolean) set(check0)
    else set(false)
  }

  override fun init() {
    if(!fileConfig.exists()) generateConfig()
    else HEAddon.LOGGER.debug("Config file '${fileConfig.name}' found.")
  }

  private fun generateConfig() {
    val gsonBuild = GsonBuilder().setPrettyPrinting().create()
    confBool["openOldMenu"] = false

    val result = gsonBuild.toJson(confBool)
    FileWriter(fileConfig).use { writed -> writed.write(result) }
    HEAddon.LOGGER.warn("Config file not found. Generated now!")
  }
  private fun updateConfig(config: String, value: Any) {
    val jsonObj = JsonParser.parseReader(FileReader(fileConfig)).asJsonObject

    when(value) {
      is String -> jsonObj.addProperty(config, value)
      is Int -> jsonObj.addProperty(config, value)
      is Boolean -> jsonObj.addProperty(config, value)
      else -> throw IllegalArgumentException("Unknown value type: ${value.javaClass.name}")
    }
    val result = GsonBuilder().setPrettyPrinting().create().toJson(jsonObj)
    FileWriter(fileConfig).use { writer -> writer.write(result) }
  }
  fun getConfig(config: String): Any? {
    val jsonElement = Gson().fromJson(FileReader(fileConfig), JsonObject::class.java).get(config)

    return when {
        jsonElement.isJsonPrimitive -> {
            val jsonPrimitive = jsonElement.asJsonPrimitive
            when {
                jsonPrimitive.isBoolean -> jsonPrimitive.asBoolean
                jsonPrimitive.isString -> jsonPrimitive.asString
                jsonPrimitive.isNumber -> jsonPrimitive.asNumber
                else -> null
            }
        }
        jsonElement.isJsonObject -> jsonElement.asJsonObject
        jsonElement.isJsonArray -> jsonElement.asJsonArray
        jsonElement.isJsonNull -> null
        else -> null
    }
  }

  override fun render(pPoseStack: PoseStack, pMouseX: Int, pMouseY: Int, pPartialTick: Float) {
    super.render(pPoseStack, pMouseX, pMouseY, pPartialTick)

    ImguiHandler.drawFrame {
      window(lang("gui.title"), ImGuiWindowFlags.NoResize or ImGuiWindowFlags.NoMove or ImGuiWindowFlags.NoCollapse) {
        centerWindow()

        ImGui.setWindowSize(1280f, 720f)
        ImGui.getStyle().setWindowPadding(4f, 4f)

        ImGui.pushStyleVar(ImGuiStyleVar.ChildBorderSize, 2f)
        ImGui.setCursorPosX(116f)
        ImGui.beginChild("Main window", 1024f, 640f)

          ImGui.newLine()
          if(checkbox(" ${lang("gui.config.openOldMenu")}", lang("gui.config.openOldMenu_desc"), openOldGUI))
            updateConfig("openOldGUI", openOldGUI.get())

          ImGui.newLine()
          ImGui.separator()
          ImGui.newLine()

        ImGui.endChild()
        ImGui.popStyleVar()
      }
    }
  }

  private fun checkbox(text: String, desc: String, checker: ImBoolean): Boolean {
    val isCHeck = ImGui.checkbox(text, checker)
    if(ImGui.isItemHovered()) ImGui.setTooltip(desc)
    return isCHeck
  }
}