package ru.benos.he_addon.gui

import com.mojang.blaze3d.vertex.PoseStack
import imgui.ImGui
import imgui.flag.ImGuiWindowFlags
import imgui.type.ImBoolean
import imgui.type.ImFloat
import imgui.type.ImInt
import imgui.type.ImString
import kotlinx.serialization.Serializable
import net.minecraft.client.Minecraft
import net.minecraft.world.entity.player.Player
import ru.benos.he_addon.HEAddon.Companion.MODID
import ru.benos.he_addon.utils.HelperPack
import ru.benos.he_addon.utils.HelperPack.lang
import ru.hollowhorizon.hc.client.handlers.TickHandler
import ru.hollowhorizon.hc.client.imgui.ImGuiMethods
import ru.hollowhorizon.hc.client.imgui.ImguiHandler
import ru.hollowhorizon.hc.client.models.gltf.Transform
import ru.hollowhorizon.hc.client.models.gltf.animations.AnimationType
import ru.hollowhorizon.hc.client.models.gltf.manager.AnimatedEntityCapability
import ru.hollowhorizon.hc.client.models.gltf.manager.GltfManager
import ru.hollowhorizon.hc.client.screens.HollowScreen
import ru.hollowhorizon.hc.client.utils.*
import ru.hollowhorizon.hc.common.network.HollowPacketV2
import ru.hollowhorizon.hc.common.network.HollowPacketV3
import ru.hollowhorizon.hollowengine.common.entities.NPCEntity
import ru.hollowhorizon.hollowengine.common.npcs.HitboxMode
import ru.hollowhorizon.hollowengine.common.npcs.NPCCapability

/*
 * New GUI crated for HollowEngine by: _BENDY659_ | RU.
 * All textures drawing by: _BENDY659_ | RU.
*/
class HEAddon_NPCCreatorGUI(
  val npc: NPCEntity,
  private val npcID: Int,
  private var isEditor: Boolean = false
): HollowScreen() {

  // NPC Data values | Если что-то не правильно (есть точно) измени
  private val npcName = ImString().apply {
    if (npc.hasCustomName()) set(npc.customName?.string ?: "")
    else set(
      String(HEAddon_NPCCreatorGUI::class.java.getResourceAsStream("/assets/${MODID}/npc_names.txt")!!.readAllBytes()).split(
        "\r\n",
        "\n"
      ).random()
    )

  }
  private val npcModel = ImString().apply {
    set(npc[AnimatedEntityCapability::class].model)
  }

  private var showName = ImBoolean().apply { set(npc.isCustomNameVisible) }
  private var switchHeadRot = ImBoolean().apply { set(npc[AnimatedEntityCapability::class].switchHeadRot) }
  private var invulnerable = ImBoolean().apply { npc.isInvulnerable }

  private val hitboxWidth = ImFloat(npc.entityData[NPCEntity.sizeX])
  private val hitboxHeight = ImFloat(npc.entityData[NPCEntity.sizeY])
  private val hitboxMode = ImInt().apply { set(npc[NPCCapability::class].hitboxMode.ordinal) }

  private var textures = npc[AnimatedEntityCapability::class].textures.toMutableMap()
  private var animations = HashMap<String, String>()
  private val attributes = HashMap<String, Float>()

  private val transformPos = arrayOf(ImFloat(0f), ImFloat(0f), ImFloat(0f)) // X Y Z
  private val transformRot = arrayOf(ImFloat(0f), ImFloat(0f), ImFloat(0f)) // X Y Z
  private val transformScl = arrayOf(ImFloat(1f), ImFloat(1f), ImFloat(1f)) // X Y Z

  private val showHitbox = ImBoolean().apply { set(false) }

  private var model = GltfManager.getOrCreate(npcModel.get().rl)

  private var tabType = ImInt()

  // Theme set
  //val themeSelect = ThemeSelect.getSelectTheme()
  //val bg0 = loadTheme(themeSelect, "bg0")
  //val bg1 = loadTheme(themeSelect, "bg1")

  @OptIn(ExperimentalStdlibApi::class)
  override fun render(pPoseStack: PoseStack, pMouseX: Int, pMouseY: Int, pPartialTick: Float) {
    super.render(pPoseStack, pMouseX, pMouseY, pPartialTick)

    if (!npc.isAddedToWorld) npc.tickCount = TickHandler.currentTicks

    ImguiHandler.drawFrame() {

      val width = Minecraft.getInstance().window.width.toFloat()
      val height = Minecraft.getInstance().window.height.toFloat()

      ImGui.begin(
        "NPC Editor 4001", ImBoolean(false),
        ImGuiWindowFlags.NoTitleBar or ImGuiWindowFlags.NoBackground or ImGuiWindowFlags.NoScrollbar
        or ImGuiWindowFlags.NoScrollWithMouse or ImGuiWindowFlags.NoResize or ImGuiWindowFlags.NoMove or ImGuiWindowFlags.NoCollapse
        or ImGuiWindowFlags.NoNav
      )

      // Main
      ImGui.pushID("window.main")
      ImGui.setWindowPos(width / 64 - 30, height / 64 - 16)
      ImGui.setWindowSize(width, height)

      ImGui.setCursorPos(ImGui.getWindowWidth() / 64f, ImGui.getWindowHeight() / 64f)
      ImGui.image(
        "hollowengine:textures/gui/npc_create/bg/bg0.png".rl.toTexture().id,
        ImGui.getWindowWidth() - 65,
        ImGui.getWindowHeight() - 32,
        0f, 0f, 1f, 1f,
        1f, 1f, 1f, 0.85f,
        //bg0[0], bg0[1], bg0[2], bg0[3]
      )
      ImGui.setCursorPos(ImGui.getWindowWidth() / 64f, ImGui.getWindowHeight() / 64f)
      ImGui.image(
        "hollowengine:textures/gui/npc_create/bg/bg1.png".rl.toTexture().id,
        ImGui.getWindowWidth() - 65,
        ImGui.getWindowHeight() - 32,
        0f, 0f, 1f, 1f,
        1f, 1f, 1f, 0.85f,
        //bg1[0], bg1[1], bg1[2], bg1[3]
      )
      ImGui.popID()

      ImGui.setCursorPos(80f, 120f)
      HelperPack.separator(ImGui.getWindowWidth() / 1.5f)

      // Tabs
      val tab0 = 95f
      val tab1 = tab0 + 185f
      val tab2 = tab1 + 250f
      val tab3 = tab2 + 250f
      val tab4 = tab3 + 320f

      if( // Basic
        HelperPack.imageButton(
          HelperPack.lang("gui.npc_create.tabs.base"),
          "hollowengine:textures/gui/npc_create/tab.png",
          145f, 60f,
          tab0, 60f,
          4,
          ImBoolean().apply { if(tabType.get() == 0) set(true) },
          ImBoolean().apply { set(true) }
        )
      ) tabType.apply { set(0) }
      if( // Visual
        HelperPack.imageButton(
          HelperPack.lang("gui.npc_create.tabs.visual"),
          "hollowengine:textures/gui/npc_create/tab.png",
          210f, 60f,
          tab1, 60f,
          4,
          ImBoolean().apply { if(tabType.get() == 1) set(true) },
          ImBoolean().apply { set(true) }
        )
      ) tabType.apply { set(1) }
      if( // Animations
        HelperPack.imageButton(
          HelperPack.lang("gui.npc_create.tabs.animations"),
          "hollowengine:textures/gui/npc_create/tab.png",
          210f, 60f,
          tab2, 60f,
          4,
          ImBoolean().apply { if(tabType.get() == 2) set(true) },
          ImBoolean().apply { set(true) }
        )
      ) tabType.apply { set(2) }
      if( // Transformations
        HelperPack.imageButton(
          HelperPack.lang("gui.npc_create.tabs.transformations"),
          "hollowengine:textures/gui/npc_create/tab.png",
          280f, 60f,
          tab3, 60f,
          4,
          ImBoolean().apply { if(tabType.get() == 3) set(true) },
          ImBoolean().apply { set(true) }
        )
      ) tabType.apply { set(3) }
      if( // Attributes
        HelperPack.imageButton(
          HelperPack.lang("gui.npc_create.tabs.attributes"),
          "hollowengine:textures/gui/npc_create/tab.png",
          210f, 60f,
          tab4, 60f,
          4,
          ImBoolean().apply { if(tabType.get() == 4) set(true) },
          ImBoolean().apply { set(true) }
        )
      ) tabType.apply { set(4) }

      // Main
      ImGui.setCursorPos(90f, 140f)
      ImGui.beginChild("Main", ImGui.getWindowWidth() / 1.55f, ImGui.getWindowHeight() / 1.3f)

        if(tabType.get() == 0) base()
        if(tabType.get() == 1) visual()
        if(tabType.get() == 2) animations()
        if(tabType.get() == 3) transformation()
        if(tabType.get() == 4) attrubites()

      ImGui.endChild()

      ImGui.setCursorPos(1330f, 0f)
      ImGui.beginChild("gui.npc_create.entity.bg", 600f, 1250f)
      ImGui.setCursorPos(0f, 120f)
      ImGui.image("hollowengine:textures/gui/npc_create/entity_bg.png".rl.toTexture().id, 500f, 750f, 0f, 0f, 1f, 1f, 1f, 1f, 1f, 0.85f)
        ImGui.setCursorPos(-50f, -530f)
        ImGui.beginChild("gui.npc_create.entity", 600f, 1250f, false, ImGuiWindowFlags.NoScrollbar or ImGuiWindowFlags.NoScrollWithMouse)
          val disp = Minecraft.getInstance().entityRenderDispatcher
          val last = disp.shouldRenderHitBoxes()
          disp.setRenderHitBoxes(showHitbox.get())
          ImGui.setCursorPos(0f, 240f)
          ImGuiMethods.entity(npc, 600f, 1250f, 0f, -72f, 0.5f)
          disp.setRenderHitBoxes(last)
        ImGui.endChild()
          ImGui.setCursorPos(100f, 775f)
          HelperPack.checkbox(
            "hollowengine:textures/gui/npc_create/checkbox.png",
            HelperPack.lang("gui.npc_create.data.hitbox.show"),
            showHitbox,
            48f, 48f
          )
      ImGui.endChild()

      ImGui.setCursorPos(1225f, 875f)
      ImGui.beginChild("buttons", 600f, 140f)
      if (
        HelperPack.imageButton(
          HelperPack.lang(if(!isEditor) "gui.npc_create.buttons.create" else "gui.npc_create.buttons.save"),
          "hollowengine:textures/gui/npc_create/button.png",
          256f, 116f,
          20f, 0f,
          fontSize = 5
        )
      ) {
        val tPosXYZ = arrayOf(transformPos[0].get(), transformPos[1].get(), transformPos[2].get())
        val tRotXYZ = arrayOf(transformRot[0].get(), transformRot[1].get(), transformRot[2].get())
        val tSclXYZ = arrayOf(transformScl[0].get(), transformScl[1].get(), transformScl[2].get())

        NPCData(
          npcID,
          npcName.get(),
          npcModel.get(),
          showName.get(),
          switchHeadRot.get(),
          invulnerable.get(),
          hitboxWidth.get(),
          hitboxHeight.get(),
          HitboxMode.entries[hitboxMode.get()],
          animations.map { AnimationType.valueOf(it.key) to it.value }.toMap(),
          textures.filter { it.value.isNotEmpty() },
          tPosXYZ,
          tRotXYZ,
          tSclXYZ
        ).send()

        onClose()
      }
      if(
        HelperPack.imageButton(
          HelperPack.lang("gui.npc_create.buttons.cancel"),
          "hollowengine:textures/gui/npc_create/button.png",
          256f, 116f,
          320f, 0f,

          fontSize = 5
        )
      ) if(!isEditor) {
        //npc.kill()
        onClose()
      } else onClose()
      ImGui.endChild()

      ImGui.end()
    }
  }
  override fun isPauseScreen() = false

  /**
   * Base parameters NPC
   */
  private fun base() {
    ImGui.newLine()

    // Data - NPC name
    ImGui.pushID("data.name")
    ImGui.text(HelperPack.lang("gui.npc_create.data.npc_name"))
    ImGui.pushItemWidth(512f)
    ImGui.inputText("##data.name_set", npcName)
    ImGui.popItemWidth()
    ImGui.popID()

    ImGui.newLine()
    HelperPack.separator(ImGui.getWindowWidth())
    ImGui.newLine()

    ImGui.text(HelperPack.lang("gui.npc_create.data.options"))
    if(
      HelperPack.checkbox(
        "hollowengine:textures/gui/npc_create/checkbox.png",
        HelperPack.lang("gui.npc_create.data.options.show_name"),
        showName,
        48f,
        48f
      )
    ) npc.isCustomNameVisible = showName.get()
    if(
      HelperPack.checkbox(
        "hollowengine:textures/gui/npc_create/checkbox.png",
        HelperPack.lang("gui.npc_create.data.options.switch_head_rot"),
        switchHeadRot,
        48f,
        48f
      )
    ) {}
    if(
      HelperPack.checkbox(
      "hollowengine:textures/gui/npc_create/checkbox.png",
      HelperPack.lang("gui.npc_create.data.options.invulnerable"),
      invulnerable,
      48f,
      48f
      )
    ) npc.isInvulnerable = invulnerable.get()

    ImGui.newLine()
    HelperPack.separator(ImGui.getWindowWidth())
    ImGui.newLine()

    // Data - NPC Hitbox size
    ImGui.pushID("data.hitbox")
    ImGui.text(HelperPack.lang("gui.npc_create.data.hitbox"))
    ImGui.pushItemWidth(256f)
    var hitboxSize =
      ImGui.inputFloat(" ${HelperPack.lang("gui.npc_create.data.hitbox.width")}", hitboxWidth, 0.01f, 0.1f, "%.3f")
    hitboxSize =
      hitboxSize or ImGui.inputFloat(" ${HelperPack.lang("gui.npc_create.data.hitbox.height")}", hitboxHeight, 0.01f, 0.1f, "%.3f")
    if(hitboxSize) {
      npc.setDimensions(hitboxWidth.get() to hitboxHeight.get())
      npc.refreshDimensions()
    }
    ImGui.popItemWidth()
    ImGui.popID()

    ImGui.newLine()

    ImGui.text(HelperPack.lang("gui.npc_create.data.hitbox.mode"))
    ImGui.pushItemWidth(512f)
    ImGui.combo(
      "##data.hitbox_mode", hitboxMode,
      arrayOf(
        HelperPack.lang("gui.npc_create.data.hitbox.mode.blocked"),
        HelperPack.lang("gui.npc_create.data.hitbox.mode.pushing"),
        HelperPack.lang("gui.npc_create.data.hitbox.mode.empty")
      )
    )
    ImGui.popItemWidth()
  }

  /**
   * Visual parameters NPC
   */
  private fun visual() {
    ImGui.newLine()

    // Data - NPC model
    ImGui.pushID("data.model")
    ImGui.text(HelperPack.lang("gui.npc_create.data.model"))
    ImGui.pushItemWidth(1024f)

    if (ImGui.inputText("##data.model_set", npcModel)) {
      val npcModel = npcModel.get().rl

      if (npcModel.exists() && (npcModel.path.endsWith(".gltf") || npcModel.path.endsWith(".glb"))) {
        model = GltfManager.getOrCreate(npcModel)
        npc[AnimatedEntityCapability::class].model = npcModel.toString()
      }
    }
    ImGui.popID()

    ImGui.newLine()
    HelperPack.separator(ImGui.getWindowWidth())
    ImGui.newLine()

    // Data - NPC Textures
    ImGui.pushID("data.textures")
    ImGui.beginChild("data.textures.child", 1190f, 560f)

    val textureLayers = model.modelTree.materials.map { it.texture.path.toString() }

    for (layer in textureLayers) {
      val text = ImString(); text.set(textures.computeIfAbsent(layer) { "" })

      ImGui.text("${HelperPack.lang("gui.npc_create.data.texture_layer")}: ${layer.toString()}")

      if (ImGui.inputText("##${layer.toString()}", text)) {
        val new = text.get()

        textures[layer] = new

        if (new.isEmpty()) npc[AnimatedEntityCapability::class].textures.remove(layer)
        else npc[AnimatedEntityCapability::class].textures[layer] = new
        ImGui.newLine()
      }
    }
    ImGui.endChild()
    ImGui.popItemWidth()
    ImGui.popID()
  }

  /**
   * Default animation set NPC
   */
  @OptIn(ExperimentalStdlibApi::class)
  private fun animations() {
    ImGui.newLine()

    // Data - NPC standard animations switch
    ImGui.pushID("data.animations")

    val animNames = model.animationPlayer.nameToAnimationMap.keys.toTypedArray()
    val animTypes = model.animationPlayer.typeToAnimationMap.map { it.key.name to it.value.name }.toMap() + animations
    var animChange: Pair<String, String>? = null

    for (type in AnimationType.entries) {

      val index = ImInt(animNames.indexOf(animTypes[type.name] ?: ""))

      ImGui.pushItemWidth(512f)
      if (ImGui.combo("##$type", index, animNames)) animChange = type.name to animNames[index.get()]
      ImGui.sameLine()
      ImGui.text(" $type")
      ImGui.popItemWidth()
    }

    animChange?.let {
      animations[it.first] = it.second
      npc[AnimatedEntityCapability::class].animations.put(AnimationType.valueOf(it.first), it.second)
    }
    ImGui.popID()
  }

  /**
   * Transformation set NPC
   */
  private fun transformation() {
    var isChange = false

    ImGui.newLine()

    ImGui.pushID("transformations: X Y Z ")
    ImGui.pushItemWidth(256f)
    ImGui.newLine()

    ImGui.text(HelperPack.lang("gui.npc_create.data.transformations.position"))
    isChange = isChange or ImGui.inputFloat("##posX", transformPos[0], 0.1f, 1f, "%.3f"); ImGui.sameLine()
    isChange = isChange or ImGui.inputFloat("##posY", transformPos[1], 0.1f, 1f, "%.3f"); ImGui.sameLine()
    isChange = isChange or ImGui.inputFloat("##posZ", transformPos[2], 0.1f, 1f, "%.3f")

    ImGui.newLine()

    ImGui.text(HelperPack.lang("gui.npc_create.data.transformations.rotation"))
    isChange = isChange or ImGui.inputFloat("##rotX", transformRot[0], 0.1f, 1f, "%.3f"); ImGui.sameLine()
    isChange = isChange or ImGui.inputFloat("##rotY", transformRot[1], 0.1f, 1f, "%.3f"); ImGui.sameLine()
    isChange = isChange or ImGui.inputFloat("##rotZ", transformRot[2], 0.1f, 1f, "%.3f")

    ImGui.newLine()

    ImGui.text(HelperPack.lang("gui.npc_create.data.transformations.scale"))
    isChange = isChange or ImGui.inputFloat("##sclX", transformScl[0], 0.01f, 0.1f, "%.3f"); ImGui.sameLine()
    isChange = isChange or ImGui.inputFloat("##sclY", transformScl[1], 0.01f, 0.1f, "%.3f"); ImGui.sameLine()
    isChange = isChange or ImGui.inputFloat("##sclZ", transformScl[2], 0.01f, 0.1f, "%.3f")

    ImGui.popItemWidth()
    ImGui.popID()

    if (isChange) npc[AnimatedEntityCapability::class].transform = Transform(
      transformPos[0].get(), transformPos[1].get(), transformPos[2].get(), // Position: X Y Z
      transformRot[0].get(), transformRot[1].get(), transformRot[2].get(), // Rotation: X Y Z
      transformScl[0].get(), transformScl[1].get(), transformScl[2].get() // Scale: X Y Z
    )
  }

  /**
   * Attributes set NPC
   */
  private fun attrubites() {
    ImGui.text("Work In Progress..."); ImGui.sameLine(); ImGui.text(" [#---------] | 3% | Thinking about this...")
  }
}

@Serializable
@HollowPacketV2
class NPCData(
  private val npcID: Int,

  private val npcName: String,
  private val npcModel: String,

  private val showName: Boolean,
  private val switchHeadRot: Boolean,
  private val invulnerable: Boolean,

  private val hitboxWidth: Float,
  private val hitboxHeight: Float,
  private val hitboxMode: HitboxMode,

  private val animations: Map<AnimationType, String>,
  private val textures: Map<String, String>,

  private val transformPosXYZ: Array<Float>,
  private val transformRotXYZ: Array<Float>,
  private val transformSclXYZ: Array<Float>,

  //private var attributes: Map<String, Float>

): HollowPacketV3<NPCData> {

  override fun handle(player: Player, data: NPCData) {
    val entity = player.level.getEntity(npcID) as? NPCEntity

    if (entity == null) {
      player.sendSystemMessage(lang("gui.npc_create.data.error").mcTranslate())
      return
    }

    entity[AnimatedEntityCapability::class].apply {
      model = this@NPCData.npcModel
        animations.putAll(this@NPCData.animations)
        textures.putAll(this@NPCData.textures)
        transform = Transform(
          transformPosXYZ[0], transformPosXYZ[1], transformPosXYZ[2],
          transformRotXYZ[0], transformRotXYZ[1], transformRotXYZ[2],
          transformSclXYZ[0], transformSclXYZ[1], transformSclXYZ[2]
        )
        switchHeadRot = this@NPCData.switchHeadRot
    }
    entity[NPCCapability::class].hitboxMode = hitboxMode

    entity.isInvulnerable = invulnerable
    entity.isCustomNameVisible = showName && this@NPCData.npcName.isNotEmpty()
    entity.customName = npcName.mcText
    entity.setDimensions(hitboxWidth to hitboxHeight)
    entity.refreshDimensions()
  }
}