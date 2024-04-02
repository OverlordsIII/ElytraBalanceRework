package io.github.overlordsiii.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.item.ItemStack;
import net.minecraft.structure.EndCityGenerator;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.ServerWorldAccess;

@Mixin(EndCityGenerator.Piece.class)
public class EndCityGeneratorMixin {

	@Inject(method = "handleMetadata", at = @At(value = "NEW", target = "(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/Direction;)Lnet/minecraft/entity/decoration/ItemFrameEntity;"), cancellable = true)
	private void turnOffElytraSpawns(String metadata, BlockPos pos, ServerWorldAccess world, Random random, BlockBox boundingBox, CallbackInfo ci) {
		System.out.println(metadata);
		System.out.println(pos);
		double rand = random.nextDouble();

		if (rand <= 0.5) {
			ci.cancel();
		}
	}

	@ModifyArg(method = "handleMetadata", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/decoration/ItemFrameEntity;setHeldItemStack(Lnet/minecraft/item/ItemStack;Z)V"), index = 0)
	private ItemStack modifyElytraSpawn(ItemStack stack) {
		java.util.Random random = new java.util.Random();

		double rand = random.nextDouble();

		double chanceStackFullDamaged = 0.85;
		double chanceStackPartDamaged = 0.1;

		if (rand <= chanceStackFullDamaged) {
			stack.setDamage(stack.getMaxDamage());
		} else if (rand <= chanceStackFullDamaged + chanceStackPartDamaged) {
			stack.setDamage(random.nextInt(stack.getMaxDamage()));
		} else {
			stack.setDamage(0);
		}
		return stack;
	}

}
