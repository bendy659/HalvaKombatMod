package ru.benos.kotloudron.registries.blocks

import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.world.item.Item
import net.minecraft.world.item.context.BlockPlaceContext
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockBehaviour
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.level.block.state.properties.BlockStateProperties
import net.minecraft.world.level.block.state.properties.DirectionProperty
import net.minecraft.world.level.material.Material
import net.minecraft.world.phys.shapes.BooleanOp
import net.minecraft.world.phys.shapes.CollisionContext
import net.minecraft.world.phys.shapes.Shapes
import net.minecraft.world.phys.shapes.VoxelShape

class BlockKotloudron: Block(
	Properties.of(Material.METAL)
		.destroyTime(40f)
		.noOcclusion()
) {
	companion object {
		val BLOCK_FACING: DirectionProperty = BlockStateProperties.HORIZONTAL_FACING
	}

	fun init() {
		registerDefaultState(this.stateDefinition.any().setValue(BLOCK_FACING, Direction.NORTH))
	}

  @Deprecated("Deprecated in Java", ReplaceWith("SHAPE"))
  override fun getShape(pState: BlockState, pLevel: BlockGetter, pPos: BlockPos, pContext: CollisionContext): VoxelShape {
    var shape: VoxelShape = Shapes.empty()

    shape = Shapes.join(shape, Shapes.box(0.0, 0.125, 0.0, 0.125, 1.0, 1.0), BooleanOp.OR)
		shape = Shapes.join(shape, Shapes.box(0.875, 0.125, 0.0, 1.0, 1.0, 1.0), BooleanOp.OR)
		shape = Shapes.join(shape, Shapes.box(0.125, 0.125, 0.0, 0.875, 1.0, 0.125), BooleanOp.OR)
		shape = Shapes.join(shape, Shapes.box(0.125, 0.125, 0.875, 0.875, 1.0, 1.0), BooleanOp.OR)
		shape = Shapes.join(shape, Shapes.box(0.0, 0.125, 0.0, 1.0, 0.25, 1.0), BooleanOp.OR)
		shape = Shapes.join(shape, Shapes.box(0.6875, 0.0, 0.0, 1.0, 0.125, 0.3125), BooleanOp.OR)
		shape = Shapes.join(shape, Shapes.box(0.0, 0.0, 0.0, 0.3125, 0.125, 0.3125), BooleanOp.OR)
		shape = Shapes.join(shape, Shapes.box(0.0, 0.0, 0.6875, 0.3125, 0.125, 1.0), BooleanOp.OR)
		shape = Shapes.join(shape, Shapes.box(0.6875, 0.0, 0.6875, 1.0, 0.125, 1.0), BooleanOp.OR)
		shape = Shapes.join(shape, Shapes.box(0.0, 0.125, 0.875, 0.875, 1.0, 1.0), BooleanOp.OR)
		shape = Shapes.join(shape, Shapes.box(0.125, 0.125, 0.125, 0.875, 0.25, 0.875), BooleanOp.OR)
		shape = Shapes.join(shape, Shapes.box(0.0, 0.125, 0.0, 0.125, 1.0, 0.875), BooleanOp.OR)
		shape = Shapes.join(shape, Shapes.box(0.125, 0.125, 0.0, 1.0, 1.0, 0.125), BooleanOp.OR)
		shape = Shapes.join(shape, Shapes.box(0.875, 0.125, 0.125, 1.0, 1.0, 1.0), BooleanOp.OR)
		shape = Shapes.join(shape, Shapes.box(0.8125, 0.0, 0.6875, 1.0, 0.125, 1.0), BooleanOp.OR)
		shape = Shapes.join(shape, Shapes.box(0.0, 0.0, 0.8125, 0.3125, 0.125, 1.0), BooleanOp.OR)
		shape = Shapes.join(shape, Shapes.box(0.0, 0.0, 0.0, 0.1875, 0.125, 0.3125), BooleanOp.OR)
		shape = Shapes.join(shape, Shapes.box(0.6875, 0.0, 0.0, 1.0, 0.125, 0.1875), BooleanOp.OR)
		shape = Shapes.join(shape, Shapes.box(0.6875, 0.0, 0.8125, 0.875, 0.125, 1.0), BooleanOp.OR)
		shape = Shapes.join(shape, Shapes.box(0.0, 0.0, 0.6875, 0.1875, 0.125, 0.875), BooleanOp.OR)
		shape = Shapes.join(shape, Shapes.box(0.8125, 0.0, 0.125, 1.0, 0.125, 0.3125), BooleanOp.OR)
		shape = Shapes.join(shape, Shapes.box(0.125, 0.0, 0.0, 0.3125, 0.125, 0.1875), BooleanOp.OR)

	  return shape
  }

	override fun createBlockStateDefinition(builder: StateDefinition.Builder<Block, BlockState>) {
		builder.add(BLOCK_FACING)
	}

	override fun getStateForPlacement(context: BlockPlaceContext): BlockState {
		return this.defaultBlockState().setValue(BLOCK_FACING, context.horizontalDirection.opposite)
	}
}