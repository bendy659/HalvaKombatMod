package ru.benos.he_addon.registries.items

import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.Item
import org.jetbrains.kotlin.konan.properties.Properties
import ru.benos.he_addon.registries.HEAddonRegistries
import ru.benos.he_addon.registries.HE_ADDON_TAB

/*
 * Thx AlgorithmLX
*/
class ItemKotloudron: BlockItem(HEAddonRegistries.kotloudronBlock.get(),
  Properties()
    .tab(HE_ADDON_TAB)
    .stacksTo(1)
)