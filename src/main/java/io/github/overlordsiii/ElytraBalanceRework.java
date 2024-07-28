package io.github.overlordsiii;

import io.github.overlordsiii.command.ElytraBalanceCommand;
import io.github.overlordsiii.config.ElytraBalanceReworkConfig;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigManager;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.loot.LootTable;
import net.minecraft.loot.LootTables;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;

public class ElytraBalanceRework implements ModInitializer {

	public static final Logger LOGGER = LogManager.getLogger("Elytra Balance Rework");

	public static final ElytraBalanceReworkConfig CONFIG;

	public static final ConfigManager<ElytraBalanceReworkConfig> CONFIG_MANAGER;

	public static final RegistryKey<LootTable> EXTRA_END_CITY_LOOT_REGISTRY = RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.of("elytra_balance_rework", "chests/extra_end_city_loot"));

	static {
		CONFIG_MANAGER = (ConfigManager<ElytraBalanceReworkConfig>) AutoConfig.register(ElytraBalanceReworkConfig.class, JanksonConfigSerializer::new);
		CONFIG = AutoConfig.getConfigHolder(ElytraBalanceReworkConfig.class).getConfig();
	}

	/**
	 * Runs the mod initializer.
	 */
	@Override
	public void onInitialize() {
		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
			ElytraBalanceCommand.register(dispatcher);
		});
	}
}
