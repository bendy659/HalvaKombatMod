package ru.benos.kotloudron.registries.items

import net.minecraft.world.item.BlockItem
import ru.benos.kotloudron.registries.KotloudronRegistries
import ru.benos.kotloudron.registries.KOTLOUDRON_TAB

/*
 * Thx AlgorithmLX
*/
class ItemKotloudron: BlockItem(KotloudronRegistries.kotloudronBlock.get(),
  Properties()
    .tab(KOTLOUDRON_TAB)
    .stacksTo(1)
)