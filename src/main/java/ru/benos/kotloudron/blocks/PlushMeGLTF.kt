package ru.benos.kotloudron.blocks

import net.minecraft.core.BlockPos
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState
import ru.benos.kotloudron.Kotloudron.Companion.MODID
import ru.hollowhorizon.hc.client.models.gltf.animations.AnimationType
import ru.hollowhorizon.hc.client.models.gltf.manager.AnimatedEntityCapability
import ru.hollowhorizon.hc.client.models.gltf.manager.IAnimated
import ru.hollowhorizon.hc.client.utils.get

class PlushMeGLTF(pType: BlockEntityType<*>, pPos: BlockPos, pBlockState: BlockState) : BlockEntity(pType, pPos, pBlockState), IAnimated {
  init {
    this[AnimatedEntityCapability::class].apply {
      model = "$MODID:models/block/plus_bendy659_.gltf"
      animations[AnimationType.IDLE] = "idle"
      animations[AnimationType.HURT] = "hit"
    }
  }
}