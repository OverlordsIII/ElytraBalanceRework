package io.github.overlordsiii.command;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

import java.lang.reflect.Field;

import com.google.common.base.Throwables;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import io.github.overlordsiii.ElytraBalanceRework;
import io.github.overlordsiii.config.ElytraBalanceReworkConfig;

import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

public class ElytraBalanceCommand {
	public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
		dispatcher.register(literal("elytra_balance_rework")
			.then(literal("set")
				.then(literal("phantomMembraneRepair")
					.then(argument("amount", IntegerArgumentType.integer(0, 800))
						.executes(context -> executeSet("phantomMembraneRepair", IntegerArgumentType.getInteger(context, "amount"), context))))
				.then(literal("noElytraSpawnPercentage")
					.then(argument("percentage", IntegerArgumentType.integer(0, 100))
						.executes(context -> executeSet("noElytraSpawnPercentage", IntegerArgumentType.getInteger(context, "percentage"), context))))
				.then(literal("brokenElytraSpawnPercentage")
					.then(argument("percentage", IntegerArgumentType.integer(0, 100))
						.executes(context -> executeSet("brokenElytraSpawnPercentage", IntegerArgumentType.getInteger(context, "percentage"), context))))
				.then(literal("partiallyDamagedSpawnPercentage")
					.then(argument("percentage", IntegerArgumentType.integer(0, 100))
						.executes(context -> executeSet("partiallyDamagedSpawnPercentage", IntegerArgumentType.getInteger(context, "percentage"), context))))
				.then(literal("initialRocketUseDamage")
					.then(argument("damage", IntegerArgumentType.integer(0))
						.executes(context -> executeSet("initialRocketUseDamage", IntegerArgumentType.getInteger(context, "damage"), context))))
				.then(literal("elytraDurability")
					.then(argument("durability", IntegerArgumentType.integer(0))
						.executes(context -> executeSet("elytraDurability", IntegerArgumentType.getInteger(context, "durability"), context))))
				.then(literal("elytraDamageAbsorbedPercentage")
					.then(argument("percentage", IntegerArgumentType.integer(0, 100))
						.executes(context -> executeSet("elytraDamageAbsorbedPercentage", IntegerArgumentType.getInteger(context, "percentage"), context)))))
			.then(literal("toggle")
				.then(literal("rebalanceElytraSpawns")
					.executes(context -> executeToggle("rebalanceElytraSpawns", context)))
				.then(literal("stopElytraFlyDamage")
					.executes(context -> executeToggle("stopElytraFlyDamage", context)))));
	}

	private static int executeSet(String fieldName, int amount, CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
		try {
 			Field field = ElytraBalanceReworkConfig.class.getField(fieldName);
			field.setDouble(ElytraBalanceRework.CONFIG, amount);
		} catch (NoSuchFieldException | IllegalAccessException e) {
			logError(context, e);
			return -1;
		}

		context.getSource().sendFeedback(() -> Text.literal("Set '" + fieldName + "' to " + amount), true);
		ElytraBalanceRework.CONFIG_MANAGER.save();
		return 1;
	}

	private static int executeToggle(String fieldName, CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
		String onOrOff;
		try {
			Field field = ElytraBalanceReworkConfig.class.getField(fieldName);
			boolean newValue = !field.getBoolean(ElytraBalanceRework.CONFIG);
			field.setBoolean(ElytraBalanceRework.CONFIG, newValue);
			onOrOff = parseBoolean(newValue);
		} catch (NoSuchFieldException | IllegalAccessException e) {
			logError(context, e);
			return -1;
		}

		context.getSource().sendFeedback(() -> Text.literal("Set '" + fieldName + "' to " + onOrOff), true);
		ElytraBalanceRework.CONFIG_MANAGER.save();
		return 1;
	}

	private static String parseBoolean(boolean rule) {
		if (rule) {
			return "on";
		}
		return "off";
	}

	private static void logError(CommandContext<ServerCommandSource> ctx, Exception e) throws CommandSyntaxException {
		if (ctx.getSource().getPlayer() != null) {
			ctx.getSource().getPlayer().sendMessage(Text.literal("Exception Thrown! Exception: " + Throwables.getRootCause(e)), false);
		}
		e.printStackTrace();
	}

}
