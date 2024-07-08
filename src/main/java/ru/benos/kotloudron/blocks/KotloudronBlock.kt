package ru.benos.kotloudron.blocks

import net.minecraft.core.BlockPos
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.material.Material
import net.minecraft.world.phys.shapes.BooleanOp
import net.minecraft.world.phys.shapes.CollisionContext
import net.minecraft.world.phys.shapes.Shapes
import net.minecraft.world.phys.shapes.VoxelShape

class KotloudronBlock: Block(
  Properties.of(Material.METAL)
    .destroyTime(50f)
    .noOcclusion()
) {

  override fun getShape(pState: BlockState, pLevel: BlockGetter, pPos: BlockPos, pContext: CollisionContext): VoxelShape {
    return Shapes.join(
      box(0.0, 0.0, 0.0, 16.0, 16.0, 16.0),
      Shapes.join(
        box(2.0, 4.0, 2.0, 14.0, 16.0, 14.0),
        Shapes.join(
          box(2.0, 0.0, 2.0, 14.0, 3.0, 14.0),
          Shapes.join(
            box(14.0, 0.0, 4.0, 16.0, 3.0, 12.0),
            Shapes.join(
              box(4.0, 0.0, 14.0, 12.0, 3.0, 16.0),
              Shapes.join(
                box(0.0, 0.0, 4.0, 2.0, 3.0, 12.0),
                box(4.0, 0.0, 0.0, 12.0, 3.0, 2.0),
                BooleanOp.ONLY_FIRST
              ),
              BooleanOp.ONLY_FIRST
            ),
            BooleanOp.ONLY_FIRST
          ),
          BooleanOp.ONLY_FIRST
        ),
        BooleanOp.ONLY_FIRST
      ),
      BooleanOp.OR
    )
  }
}