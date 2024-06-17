package ru.benos.he_addon.gui

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.mojang.blaze3d.vertex.PoseStack
import imgui.ImGui
import imgui.flag.ImGuiStyleVar
import imgui.flag.ImGuiWindowFlags
import imgui.type.ImBoolean
import imgui.type.ImInt
import net.minecraftforge.fml.loading.FMLPaths
import ru.benos.he_addon.HEAddon
import ru.benos.he_addon.HEAddon.Companion.MODID
import ru.benos.he_addon.utils.HelperPack.lang
import ru.hollowhorizon.hc.client.imgui.ImGuiMethods.window
import ru.hollowhorizon.hc.client.imgui.ImguiHandler
import ru.hollowhorizon.hc.client.screens.HollowScreen
import java.io.File
import java.io.FileReader
import java.io.FileWriter
import java.time.Instant

// Зачем нам обычный конфиг, когда можно создать свой)))
object Config : HollowScreen() {
  val CONFIGDIR = FMLPaths.GAMEDIR.get().resolve("config").toFile()
  val fileConfig = File(CONFIGDIR, "$MODID.json")
  private var openOldMenu = ImBoolean().apply {
    val check = getConfig("openOldGUI")

    if (check is Boolean) set(check)
    else set(false)
  }
  private var debugMode = ImBoolean().apply {
    val check = getConfig("debugMode")

    if(check is Boolean) set(check)
    else set(false)
  }
  private var npcToolMenu_newIcons = ImBoolean().apply {
    val check = getConfig("npcToolMenu_newIcons")

    if(check is Boolean) set(check)
    else set(false)
  }
  private var showRealNodeEditorIcon = ImBoolean().apply {
    val check = getConfig("showRealNodeEditorIcon")

    if(check is Boolean) set(check)
    else set(false)
  }

  private var categories = arrayOf(""); private var categories_select = ImInt(0)

  public override fun init() {
    if(!fileConfig.exists()) generateConfig()

    categories = arrayOf(
    lang("gui.config.category.none"),
    lang("gui.config.category.client"),
    lang("gui.config.category.common"),
    lang("gui.config.category.jokes"),
    lang("gui.config.category.other"),
  )
  }

  private fun generateConfig() {
    val confBool = LinkedHashMap<String, Boolean>()

    val gsonBuild = GsonBuilder().setPrettyPrinting().create()
    confBool["debugMode"] = false
    confBool["openOldMenu"] = false
    confBool["npcToolMenu_newIcons"] = false
    confBool["showRealNodeEditorIcon"] = false

    val result = gsonBuild.toJson(confBool)
    FileWriter(fileConfig).use { writed -> writed.write(result) }
    HEAddon.LOGGER.warn("Config file not found. Generated now!")
  }

  private fun setConfig(config: String, value: Any) {
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

    if(jsonElement == null) return null
    else return when {
      jsonElement.isJsonPrimitive -> {
        val jsonPrimitive = jsonElement.asJsonPrimitive

        when {
          jsonPrimitive.isBoolean -> jsonPrimitive.asBoolean
          jsonPrimitive.isString -> jsonPrimitive.asString
          jsonPrimitive.isNumber -> jsonPrimitive.asNumber
          else -> null
        }
      }
      else -> null
    }
  }

  override fun render(pPoseStack: PoseStack, pMouseX: Int, pMouseY: Int, pPartialTick: Float) {
    super.render(pPoseStack, pMouseX, pMouseY, pPartialTick)

    ImguiHandler.drawFrame {
      window(lang("gui.config.title"), ImGuiWindowFlags.NoResize or ImGuiWindowFlags.NoMove or ImGuiWindowFlags.NoCollapse) {
        centerWindow()

        ImGui.setWindowSize(1280f, 720f)
        ImGui.getStyle().setWindowPadding(4f, 4f)

        ImGui.pushStyleVar(ImGuiStyleVar.ChildBorderSize, 2f)
        ImGui.setCursorPosX(116f)
        ImGui.beginChild("Main window", 1024f, 580f)

        ImGui.newLine()

        ImGui.pushItemWidth(256f)
        ImGui.combo("##categories", categories_select, categories); ImGui.sameLine(); ImGui.text(" ${lang("gui.config.categories")}")
        ImGui.popItemWidth()

        ImGui.newLine()

        if(categories_select.get() == 1) {
          if( checkbox("openOldMenu", openOldMenu) ) setConfig("openOldMenu", openOldMenu.get())

          ImGui.newLine()

          if( checkbox("npcToolMenu_newIcons", npcToolMenu_newIcons) ) setConfig("npcToolMenu_newIcons", npcToolMenu_newIcons.get())
        }
        if(categories_select.get() == 2) {}
        if(categories_select.get() == 3) {
          if(checkbox("showRealNodeEditorIcon", showRealNodeEditorIcon)) setConfig("showRealNodeEditorIcon", showRealNodeEditorIcon.get())
        }
        if(categories_select.get() == 4) {
          if( checkbox("debugMode", debugMode) ) setConfig("debugMode", debugMode.get())
        }

          ImGui.newLine()

        ImGui.endChild()
        ImGui.popStyleVar()

        if(debugMode.get())
          if ( button("regen_config", 35f, 650f) ) generateConfig()

        if( button("close", 1128f, 650f) ) onClose()
      }
    }
  }

  private fun checkbox(text: String, valueChange: ImBoolean): Boolean {
    val isCheck = ImGui.checkbox(
      " ${lang("gui.config.$text")}",
      valueChange
    )

    if(ImGui.isItemHovered()) ImGui.setTooltip(lang("gui.config.${text}_desc"))
    return isCheck
  }
  private fun button(text: String, posX: Float, posY: Float): Boolean {
    ImGui.setCursorPos(posX, posY)
    val isClick = ImGui.button(lang("gui.config.button.$text"))

    if(ImGui.isItemHovered()) ImGui.setTooltip(lang("gui.config.button.${text}_desc"))
    return isClick
  }
}