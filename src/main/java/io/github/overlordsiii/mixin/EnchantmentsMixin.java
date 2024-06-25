package io.github.overlordsiii.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.entry.RegistryEntryList;

// Currently just doesn't apply? Not sure why. I can't mixin into the Enchantments class at all, across any project
@Mixin(Enchantments.class)
public class EnchantmentsMixin {

	@ModifyArg(method = "bootstrap", allow = 1,
		at = @At(value = "INVOKE",
			target = "Lnet/minecraft/enchantment/Enchantment;definition(Lnet/minecraft/registry/entry/RegistryEntryList;IILnet/minecraft/enchantment/Enchantment$Cost;Lnet/minecraft/enchantment/Enchantment$Cost;I[Lnet/minecraft/component/type/AttributeModifierSlot;)Lnet/minecraft/enchantment/Enchantment$Definition;",
			ordinal = 0),
		slice = @Slice(from = @At(value = "NEW",
			target = "(ZLjava/util/Optional;Ljava/util/Optional;Ljava/util/Optional;Lnet/minecraft/util/math/Vec3d;Lnet/minecraft/enchantment/EnchantmentLevelBasedValue;ZLnet/minecraft/world/World$ExplosionSourceType;Lnet/minecraft/particle/ParticleEffect;Lnet/minecraft/particle/ParticleEffect;Lnet/minecraft/registry/entry/RegistryEntry;)Lnet/minecraft/enchantment/effect/entity/ExplodeEnchantmentEffect;")),
		index = 0)
	private static RegistryEntryList<Item> bootstrap(RegistryEntryList<Item> supportedItems) {
		supportedItems.stream().forEach(System.out::println);

		return supportedItems;
	}
}
