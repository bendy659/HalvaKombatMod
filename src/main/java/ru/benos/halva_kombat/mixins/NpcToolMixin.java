package ru.benos.halva_kombat.mixins;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import ru.benos.halva_kombat.common.registries.Registries;
import ru.hollowhorizon.hollowengine.common.items.NpcTool;

@Mixin(NpcTool.class)
public class NpcToolMixin {
  @Inject(method = "use", at = @At("HEAD"), cancellable = true)
  private void use(Level pLevel, Player pPlayer, InteractionHand pUsedHand, CallbackInfoReturnable<InteractionResult> cir) {
    if (!pLevel.isClientSide && pUsedHand == InteractionHand.MAIN_HAND) {
      HitResult pResult = pPlayer.pick(pPlayer.getReachDistance(), 0f, false);

      if(pResult.getType() == HitResult.Type.BLOCK) {
        BlockHitResult blockHitResult = (BlockHitResult) pResult;
        BlockPos pPos = blockHitResult.getBlockPos();
        BlockState blockState = pLevel.getBlockState(pPos);

        if(blockState.getBlock() == Blocks.CAULDRON) {
          BlockState newBlockState = Registries.INSTANCE.getKotloudronBlock().get().defaultBlockState();
          pLevel.setBlock(pPos, newBlockState, 3);
          cir.setReturnValue(InteractionResult.SUCCESS);
        } else {
          cir.setReturnValue(InteractionResult.FAIL);
        }
        if(blockState.getBlock() == Registries.INSTANCE.getKotloudronBlock().get()) {
          BlockState newBlockState = Blocks.CAULDRON.defaultBlockState();
          pLevel.setBlock(pPos, newBlockState, 3);
          cir.setReturnValue(InteractionResult.SUCCESS);
        } else {
          cir.setReturnValue(InteractionResult.FAIL);
        }
      }
    }
  }
}