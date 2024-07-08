package ru.benos.kotloudron.blocks

import net.minecraft.core.BlockPos
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState
import ru.benos.kotloudron.Kotloudron.Companion.MODID
import ru.benos.kotloudron.registries.KotloudronRegistries
import ru.hollowhorizon.hc.client.models.gltf.Transform
import ru.hollowhorizon.hc.client.models.gltf.animations.AnimationType
import ru.hollowhorizon.hc.client.models.gltf.manager.AnimatedEntityCapability
import ru.hollowhorizon.hc.client.models.gltf.manager.IAnimated
import ru.hollowhorizon.hc.client.utils.get

class PlushMeBlockEntity(pPos: BlockPos, pBlockState: BlockState) : BlockEntity(KotloudronRegistries.plusMeBlockEntity.get(), pPos, pBlockState), IAnimated {
  init {
    this[AnimatedEntityCapability::class].apply {
      model = "$MODID:models/block/plush_bendy659_.gltf"
      animations[AnimationType.IDLE] = "idle"
      animations[AnimationType.HURT] = "hit"
      transform = Transform.create{}
    }
  }
}