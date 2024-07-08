package ru.benos.kotloudron.events

import net.minecraft.sounds.SoundEvents
import net.minecraft.sounds.SoundSource
import net.minecraft.world.level.block.Blocks
import net.minecraftforge.event.entity.player.PlayerInteractEvent
import net.minecraftforge.eventbus.api.EventPriority
import net.minecraftforge.eventbus.api.SubscribeEvent
import ru.benos.kotloudron.registries.KotloudronRegistries
import ru.hollowhorizon.hollowengine.common.registry.ModItems
import java.util.EventListenerProxy

object BlockEvents {

  @SubscribeEvent(priority = EventPriority.HIGHEST)
  fun blockReplaceCauldronToKotloudronAndBack(e: PlayerInteractEvent.RightClickBlock) {
    val pPlayer = e.entity
    val pItemStack = pPlayer.getItemInHand(e.hand)
    val pLevel = e.level
    val pHit = e.hitVec

    if(pItemStack.item == ModItems.NPC_TOOL.get()) {
      val block = pLevel.getBlockState(pHit.blockPos)

      if(block.block == Blocks.CAULDRON) {
        pPlayer.swing(e.hand, true)
        pLevel.setBlock(pHit.blockPos, KotloudronRegistries.kotloudronBlock.get().defaultBlockState(), 3)
        pLevel.playSound(pPlayer, pHit.blockPos, SoundEvents.ANVIL_USE, SoundSource.BLOCKS, 0.5f, 1f)

      } else if(block.block == KotloudronRegistries.kotloudronBlock.get()) {
        pPlayer.swing(e.hand, true)
        pLevel.setBlock(pHit.blockPos, Blocks.CAULDRON.defaultBlockState(), 3)
        pLevel.playSound(pPlayer, pHit.blockPos, SoundEvents.ANVIL_USE, SoundSource.BLOCKS, 0.5f, 1f)

      }
    }
  }
}