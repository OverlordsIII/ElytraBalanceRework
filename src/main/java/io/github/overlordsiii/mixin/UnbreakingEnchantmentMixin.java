package io.github.overlordsiii.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.enchantment.UnbreakingEnchantment;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

@Mixin(UnbreakingEnchantment.class)
public class UnbreakingEnchantmentMixin {
	@Inject(method = "isAcceptableItem", at = @At("HEAD"), cancellable = true)
	private void invalidateElytra(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
		if (stack.getItem().equals(Items.ELYTRA)) {
			cir.setReturnValue(false);
		}
	}
}
