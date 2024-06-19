package ru.benos.kotloudron.registries.blocks

import net.minecraft.core.BlockPos
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.material.Material
import net.minecraft.world.phys.Vec3
import net.minecraft.world.phys.shapes.BooleanOp
import net.minecraft.world.phys.shapes.CollisionContext
import net.minecraft.world.phys.shapes.Shapes
import net.minecraft.world.phys.shapes.VoxelShape
import ru.benos.kotloudron.Kotloudron
import ru.benos.kotloudron.registries.KOTLOUDRON_TAB
import ru.benos.kotloudron.utils.DesingLogging.desingLogging

class BlockKotloudron:Block(
  Properties.of(Material.METAL)
    .destroyTime(40f)
    .noOcclusion()
) {

  fun init() {
    Kotloudron.LOGGER.debug(desingLogging("INIT BLOCK: KOTLOUDRON_BLOCK"))
  }

  // Block properties
  val BASIC: VoxelShape = box(0.0, 0.0, 0.0, 16.0, 16.0, 16.0)
  val EMPTY_CASE: VoxelShape = box(2.0, 4.0, 2.0, 14.0, 16.0, 14.0)
  val SHAPE: VoxelShape = Shapes.join(Shapes.block(), Shapes.or(BASIC, EMPTY_CASE), BooleanOp.ONLY_FIRST)

  @Deprecated("Deprecated in Java", ReplaceWith("SHAPE"))
  override fun getShape(pState: BlockState, pLevel: BlockGetter, pPos: BlockPos, pContext: CollisionContext): VoxelShape {
      return SHAPE;
   }
}