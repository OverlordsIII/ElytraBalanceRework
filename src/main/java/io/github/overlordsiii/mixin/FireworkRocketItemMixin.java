package io.github.overlordsiii.mixin;

import io.github.overlordsiii.ElytraBalanceRework;
import io.github.overlordsiii.config.ElytraBalanceReworkConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.FireworkRocketItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

@Mixin(FireworkRocketItem.class)
public class FireworkRocketItemMixin {
	@Inject(method = "use", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;getStackInHand(Lnet/minecraft/util/Hand;)Lnet/minecraft/item/ItemStack;", shift = At.Shift.BEFORE))
	public void damageElytra(World world, PlayerEntity user, Hand hand, CallbackInfoReturnable<TypedActionResult<ItemStack>> cir) {
		if (world.isClient) {
			return;
		}
		user.getArmorItems().forEach(itemStack -> {
			if (itemStack.getItem().equals(Items.ELYTRA)) {
				itemStack.damage(ElytraBalanceRework.CONFIG.initialRocketUseDamage, user, user.getPreferredEquipmentSlot(itemStack));
			}
		});
	}
}
