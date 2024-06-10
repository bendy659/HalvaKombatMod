package ru.benos.he_addon.mixins;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import ru.benos.he_addon.gui.NPCToolGUI;
import ru.hollowhorizon.hollowengine.client.gui.NPCToolGui;
import ru.hollowhorizon.hollowengine.common.entities.NPCEntity;
import ru.hollowhorizon.hollowengine.common.items.NpcTool;

import java.nio.channels.AsynchronousFileChannel;

@Mixin(NpcTool.class)
interface NPCToolMixin {


}
