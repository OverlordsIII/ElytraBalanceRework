package io.github.overlordsiii.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Identifier;

@Mixin(Enchantment.class)
public abstract class EnchantmentMixin {

	@ModifyReturnValue(method = "isAcceptableItem", at = @At("RETURN"))
	private boolean isAcceptableItem(boolean original, @Local(argsOnly = true) ItemStack itemStack) {
		if (itemStack.isOf(Items.ELYTRA)) {
			return false;
		}

		return original;
	}
}
