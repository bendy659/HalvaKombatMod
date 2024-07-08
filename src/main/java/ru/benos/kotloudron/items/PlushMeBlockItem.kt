package ru.benos.kotloudron.items

import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.Item
import net.minecraft.world.item.Rarity
import net.minecraft.world.level.block.Block
import ru.benos.kotloudron.KOTLOUDRON_TAB
import ru.benos.kotloudron.registries.KotloudronRegistries

class PlushMeBlockItem: BlockItem(KotloudronRegistries.plushMeBlock.get(),
  Properties()
    .rarity(Rarity.COMMON)
    .stacksTo(64)
    .tab(KOTLOUDRON_TAB)
)