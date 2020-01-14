package xyz.pixelatedw.MineMineNoMi3.quests.objectives;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.biome.BiomeGenBase;

public interface IBiomeQuestObjective {
	public void onChangeBiome(EntityPlayer player, BiomeGenBase biome);
}
