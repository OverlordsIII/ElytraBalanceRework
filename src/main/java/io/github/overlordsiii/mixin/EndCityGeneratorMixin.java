package io.github.overlordsiii.mixin;

import io.github.overlordsiii.ElytraBalanceRework;
import io.github.overlordsiii.api.ElytraGenerationState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.FacingBlock;
import net.minecraft.inventory.LootableInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.structure.EndCityGenerator;
import net.minecraft.structure.SimpleStructurePiece;
import net.minecraft.structure.StructurePieceType;
import net.minecraft.structure.StructurePlacementData;
import net.minecraft.structure.StructureTemplateManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.ServerWorldAccess;

@Mixin(EndCityGenerator.Piece.class)
public abstract class EndCityGeneratorMixin extends SimpleStructurePiece {

	@Unique
	private ElytraGenerationState state = null;

	@Unique
	private static final java.util.Random random = new java.util.Random();

	public EndCityGeneratorMixin(StructurePieceType type, int length, StructureTemplateManager structureTemplateManager, Identifier id, String template, StructurePlacementData placementData, BlockPos pos) {
		super(type, length, structureTemplateManager, id, template, placementData, pos);
	}

	@Inject(method = "handleMetadata", at = @At(value = "NEW", target = "(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/Direction;)Lnet/minecraft/entity/decoration/ItemFrameEntity;"), cancellable = true)
	private void turnOffElytraSpawns(String metadata, BlockPos pos, ServerWorldAccess world, Random random, BlockBox boundingBox, CallbackInfo ci) {
		checkElytraStatus(random);

		if (!ElytraBalanceRework.CONFIG.rebalanceElytraSpawns) {
			return;
		}

		if (state == ElytraGenerationState.BROKEN_ELYTRA || state == ElytraGenerationState.NO_ELYTRA) {

			world.setBlockState(pos.down(), Blocks.SHULKER_BOX.getDefaultState().with(FacingBlock.FACING, this.placementData.getRotation().rotate(Direction.SOUTH)), Block.NOTIFY_ALL);

			LootableInventory.setLootTable(world, random, pos.down(), ElytraBalanceRework.EXTRA_END_CITY_LOOT_REGISTRY);
		}

		if (state == ElytraGenerationState.NO_ELYTRA) {
			ci.cancel();
		}
	}

	@ModifyArg(method = "handleMetadata", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/decoration/ItemFrameEntity;setHeldItemStack(Lnet/minecraft/item/ItemStack;Z)V"), index = 0)
	private ItemStack modifyElytraSpawn(ItemStack stack) {
		if (!ElytraBalanceRework.CONFIG.rebalanceElytraSpawns) {
			return stack;
		}

		if (state == ElytraGenerationState.BROKEN_ELYTRA) {
			stack.setDamage(stack.getMaxDamage());
		} else if (state == ElytraGenerationState.PARTIAL_ELYTRA) {
			stack.setDamage(random.nextInt(stack.getMaxDamage()));
		} else {
			stack.setDamage(0);
		}

		state = null;

		return stack;
	}

	@Unique
	private void checkElytraStatus(Random random) {
		if (state != null) {
			return;
		}

		double rand = random.nextDouble();

		double chanceElytraGone = ElytraBalanceRework.CONFIG.noElytraSpawnPercentage / 100;

		if (rand <= chanceElytraGone) {
			state = ElytraGenerationState.NO_ELYTRA;
			return;
		}

		double chanceStackFullDamaged = ElytraBalanceRework.CONFIG.brokenElytraSpawnPercentage / 100;
		double chanceStackPartDamaged = ElytraBalanceRework.CONFIG.partiallyDamagedElytraSpawnPercentage / 100;

		if (rand <= chanceStackFullDamaged + chanceElytraGone) {
			state = ElytraGenerationState.BROKEN_ELYTRA;
		} else if (rand <= chanceStackFullDamaged + chanceStackPartDamaged + chanceElytraGone) {
			state = ElytraGenerationState.PARTIAL_ELYTRA;
		} else {
			state = ElytraGenerationState.FULL_ELYTRA;
		}
	}

}
