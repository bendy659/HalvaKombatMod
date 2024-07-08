package ru.benos.kotloudron.mixins;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AbstractCauldronBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import ru.benos.kotloudron.Kotloudron;
import ru.benos.kotloudron.registries.KotloudronRegistries;
import ru.hollowhorizon.hollowengine.common.registry.ModItems;

@Mixin(AbstractCauldronBlock.class)
public abstract class AbstractCauldronBlockMixin {

  @Inject(method = "use", at = @At("HEAD"), cancellable = true)
  private void use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit, CallbackInfoReturnable<InteractionResult> cir) {
    ItemStack item = pPlayer.getItemInHand(pHand);

    boolean clientSide = pLevel.isClientSide;
    boolean itemCorrect = item.getItem() == ModItems.INSTANCE.getNPC_TOOL().get();

    if(!clientSide && itemCorrect) {
      pLevel.setBlock(pPos, KotloudronRegistries.INSTANCE.getKotloudronBlock().get().defaultBlockState(), 3);

      if(Kotloudron.Companion.getDebug()) {
        Kotloudron.Companion.getLOGGER().debug("Block \"Cauldron\" replaced to \"Kotloudron\"");
      }
      cir.setReturnValue(InteractionResult.SUCCESS);
    } else {
      if(Kotloudron.Companion.getDebug()) {
        Kotloudron.Companion.getLOGGER().debug("Failed replace block \"Cauldron\" to \"Kotloudron\" | isClientSide: {}, itemCorrect: {}", clientSide, itemCorrect);
      }
      cir.setReturnValue(InteractionResult.FAIL);
    }
  }
}