package io.github.overlordsiii.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;

@Config(name = "elytra_balance_rework")
@Config.Gui.Background("minecraft:textures/block/purpur_pillar.png")
public class ElytraBalanceReworkConfig implements ConfigData {

	@Comment("Determines the amount of elytra damage that can be repaired by a phantom membrane. Vanilla default is 200.")
	public double phantomMembraneRepair = 400;

	@Comment("Changes the spawn rate of elytras in end-cities")
	public boolean rebalanceElytraSpawns = true;

	@Comment("Marks the probability (in percent) that there is no elytra in an end city")
	public double noElytraSpawnPercentage = 35;

	@Comment("Marks the probability (in percent) that there is a broken elytra in an end city")
	public double brokenElytraSpawnPercentage = 25;

	@Comment("Marks the probability (in percent) that there is a partially damaged elytra in an end city")
	public double partiallyDamagedElytraSpawnPercentage = 15;

	@Comment("Amount of damage rockets do to elytras on immediate use. Vanilla default is 0")
	public double initialRocketUseDamage = 10;

	@Comment("Elytra Durability. Vanilla default is 432")
	public double elytraDurability = 800;

	@Comment("Percentage of damage that is absorbed by elytra when flying")
	public double elytraDamageAbsorbedPercentage = 10;

	@Comment("Stops elytra taking durability damage when flying")
	public boolean stopElytraFlyDamage = true;

}
