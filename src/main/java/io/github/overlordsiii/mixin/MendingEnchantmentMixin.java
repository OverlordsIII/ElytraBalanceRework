package io.github.overlordsiii.mixin;

import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.enchantment.MendingEnchantment;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

@Mixin(MendingEnchantment.class)
public abstract class MendingEnchantmentMixin extends Enchantment {
	protected MendingEnchantmentMixin(Rarity rarity, EnchantmentTarget target, EquipmentSlot[] slotTypes) {
		super(rarity, target, slotTypes);
	}

	@Override
	public boolean isAcceptableItem(ItemStack stack) {
		return !stack.getItem().equals(Items.ELYTRA);
	}
}
