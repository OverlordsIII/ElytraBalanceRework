package io.github.overlordsiii.mixin;

import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.AnvilScreenHandler;
import net.minecraft.screen.ForgingScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.ScreenHandlerType;

@Mixin(AnvilScreenHandler.class)
public abstract class AnvilScreenHandlerMixin extends ForgingScreenHandler {
	public AnvilScreenHandlerMixin(@Nullable ScreenHandlerType<?> type, int syncId, PlayerInventory playerInventory, ScreenHandlerContext context) {
		super(type, syncId, playerInventory, context);
	}

	@ModifyArg(method = "updateResult", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;setDamage(I)V", ordinal = 0))
	private int modifyElytraDamage(int damage) {
		if (this.input.getStack(0).getItem().equals(Items.PHANTOM_MEMBRANE)) {
			return damage - 200;
		}

		return damage;
	}
}
