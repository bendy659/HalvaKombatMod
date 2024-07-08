package ru.benos.kotloudron.blocks

import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.world.item.context.BlockPlaceContext
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.EntityBlock
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.level.block.state.properties.BlockStateProperties
import net.minecraft.world.level.block.state.properties.DirectionProperty
import net.minecraft.world.level.material.Material
import net.minecraft.world.phys.shapes.CollisionContext
import net.minecraft.world.phys.shapes.VoxelShape

class PlushMeBlock: Block(
  Properties.of(Material.WOOL)
    .destroyTime(10f)
    .noOcclusion()
    .noCollission()
), EntityBlock {

  //fun init() {
	//	registerDefaultState(this.stateDefinition.any().setValue(BLOCK_FACING, Direction.NORTH))
	//}

  //companion object {
	//	val BLOCK_FACING: DirectionProperty = BlockStateProperties.HORIZONTAL_FACING
	//}

  override fun getShape(pState: BlockState, pLevel: BlockGetter, pPos: BlockPos, pContext: CollisionContext): VoxelShape {
    return box(1.5, 0.0, 1.75, 14.5, 17.5, 14.25)
  }

  //override fun createBlockStateDefinition(builder: StateDefinition.Builder<Block, BlockState>) {
	//	builder.add(BLOCK_FACING)
	//}

	//override fun getStateForPlacement(context: BlockPlaceContext): BlockState {
	//	return this.defaultBlockState().setValue(BLOCK_FACING, context.horizontalDirection.opposite)
	//}

  override fun newBlockEntity(pPos: BlockPos, pState: BlockState): BlockEntity {
    return PlushMeBlockEntity(pPos, pState)
  }
}