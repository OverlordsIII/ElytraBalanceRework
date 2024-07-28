package io.github.overlordsiii.mixin;

import io.github.overlordsiii.ElytraBalanceRework;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.world.World;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {

	@Shadow public abstract Iterable<ItemStack> getArmorItems();

	@Shadow public abstract EquipmentSlot getPreferredEquipmentSlot(ItemStack stack);

	public LivingEntityMixin(EntityType<?> type, World world) {
		super(type, world);
	}

	@Inject(method = "damage", at = @At("HEAD"))
	private void incrementElytraDamage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
		for (ItemStack itemStack : this.getArmorItems()) {
			if (itemStack.isOf(Items.ELYTRA)) {
				int itemStackDamage;
				if (source.isOf(DamageTypes.ON_FIRE) || source.isOf(DamageTypes.IN_FIRE)) {
					itemStackDamage = Math.round(amount);
				} else {
					float tenPercent = (float) (((ElytraBalanceRework.CONFIG.elytraDamageAbsorbedPercentage) / 100) * amount);
					itemStackDamage = Math.max(1, Math.round(tenPercent));
				}
				itemStack.damage(itemStackDamage, ((LivingEntity) (Object) this), this.getPreferredEquipmentSlot(itemStack));
			}
		}
	}

	@ModifyArg(method = "tickFallFlying", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;damage(ILnet/minecraft/entity/LivingEntity;Lnet/minecraft/entity/EquipmentSlot;)V"), index = 0)
	public int modifyElytraDamage(int amount) {
		if (ElytraBalanceRework.CONFIG.stopElytraFlyDamage) {
			return 0;
		}

		return amount;
	}
}
