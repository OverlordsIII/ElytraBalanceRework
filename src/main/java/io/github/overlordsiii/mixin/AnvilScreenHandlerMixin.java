package io.github.overlordsiii.mixin;

import java.util.Random;

import com.llamalad7.mixinextras.sugar.Local;
import io.github.overlordsiii.ElytraBalanceRework;
import io.github.overlordsiii.config.ElytraBalanceReworkConfig;
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

	private final Random random = new Random();

	public AnvilScreenHandlerMixin(@Nullable ScreenHandlerType<?> type, int syncId, PlayerInventory playerInventory, ScreenHandlerContext context) {
		super(type, syncId, playerInventory, context);
	}

	@ModifyArg(method = "updateResult", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;setDamage(I)V", ordinal = 0))
	private int modifyElytraDamage(int damage, @Local(ordinal = 3) int l) {
		if (this.input.getStack(1).getItem().equals(Items.PHANTOM_MEMBRANE)) {
			damage += l; // cancel out default phantom membrane fix
			double percent = random.nextDouble(0, ElytraBalanceRework.CONFIG.phantomMembraneRepair);
			double damageReduction = (ElytraBalanceRework.CONFIG.elytraDurability * (percent/100));
			int newDamage = (int) (damage - damageReduction);
			return Math.max(0, newDamage);
		}

		return damage;
	}
}
