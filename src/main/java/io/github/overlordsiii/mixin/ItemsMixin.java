package io.github.overlordsiii.mixin;

import io.github.overlordsiii.ElytraBalanceRework;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.Slice;

import net.minecraft.item.Items;

@Mixin(Items.class)
public class ItemsMixin {

	@ModifyConstant(
		method = "<clinit>",
		require = 1,
		allow = 1,
		constant = @Constant(intValue = 432),
		slice = @Slice(
			from = @At(value = "CONSTANT", args = "stringValue=elytra", ordinal = 0)
		)
	)
	private static int replaceElytraMaxDamage(int maxDamage)
	{
		return ElytraBalanceRework.CONFIG.elytraDurability;
	}
}
