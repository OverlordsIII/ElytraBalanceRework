package io.github.overlordsiii.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {

	@Shadow public abstract Iterable<ItemStack> getArmorItems();

	public LivingEntityMixin(EntityType<?> type, World world) {
		super(type, world);
	}

	@ModifyArg(method = "travel", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;damage(Lnet/minecraft/entity/damage/DamageSource;F)Z"), index = 1)
	public float modifyDamage(float amount) {
		for (ItemStack itemStack : this.getArmorItems()) {
			if (itemStack.isOf(Items.ELYTRA)) {
				float tenPercent = 0.1f * amount;
				amount -= tenPercent;
				itemStack.damage(Math.max(1, Math.round(tenPercent)), ((LivingEntity) (Object) this), playerEntity -> playerEntity.playSound(SoundEvents.ITEM_ELYTRA_FLYING, 1, 1));
				return amount;
			}
		}
		return amount;
	}
}
