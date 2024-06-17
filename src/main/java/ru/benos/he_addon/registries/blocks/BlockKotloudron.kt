package ru.benos.he_addon.registries.blocks

import net.minecraft.world.level.block.Block
import net.minecraft.world.level.material.Material

class BlockKotloudron: Block(
  Properties.of(Material.METAL)
    .destroyTime(40f)
    .noOcclusion()
)