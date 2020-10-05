package xyz.pixelatedw.MineMineNoMi3.events;

import java.lang.reflect.Array;

import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import cpw.mods.fml.common.network.FMLNetworkEvent.ClientConnectedToServerEvent;
import cpw.mods.fml.common.network.FMLNetworkEvent.ClientDisconnectionFromServerEvent;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.event.entity.player.PlayerDestroyItemEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerUseItemEvent;
import xyz.pixelatedw.MineMineNoMi3.MainConfig;
import xyz.pixelatedw.MineMineNoMi3.api.WyHelper;
import xyz.pixelatedw.MineMineNoMi3.api.abilities.Ability;
import xyz.pixelatedw.MineMineNoMi3.api.abilities.extra.AbilityProperties;
import xyz.pixelatedw.MineMineNoMi3.api.debug.WyDebug;
import xyz.pixelatedw.MineMineNoMi3.api.math.WyMathHelper;
import xyz.pixelatedw.MineMineNoMi3.api.network.PacketAbilitySync;
import xyz.pixelatedw.MineMineNoMi3.api.network.WyNetworkHelper;
import xyz.pixelatedw.MineMineNoMi3.api.quests.QuestProperties;
import xyz.pixelatedw.MineMineNoMi3.api.telemetry.WyTelemetry;
import xyz.pixelatedw.MineMineNoMi3.data.ExtendedEntityData;
import xyz.pixelatedw.MineMineNoMi3.data.ExtendedWorldData;
import xyz.pixelatedw.MineMineNoMi3.data.HistoryProperties;
import xyz.pixelatedw.MineMineNoMi3.packets.PacketSync;
import xyz.pixelatedw.MineMineNoMi3.packets.PacketSyncInfo;

public class EventsCore
{
	
	// Registering the extended properties
	@SubscribeEvent
	public void onEntityConstructing(EntityConstructing event) 
	{
		if (event.entity instanceof EntityLivingBase && ExtendedEntityData.get((EntityLivingBase) event.entity) == null)
			ExtendedEntityData.register((EntityLivingBase) event.entity);

		if (event.entity instanceof EntityPlayer)
		{
			if(QuestProperties.get((EntityPlayer) event.entity) == null)
				QuestProperties.register((EntityPlayer) event.entity);
			if(AbilityProperties.get((EntityPlayer) event.entity) == null)
				AbilityProperties.register((EntityPlayer) event.entity);
			if(HistoryProperties.get((EntityPlayer) event.entity) == null)
				HistoryProperties.register((EntityPlayer) event.entity);
		}
	}
	
	// Cloning the player data to the new entity based on the config option
	@SubscribeEvent
	public void onClonePlayer(PlayerEvent.Clone e) 
	{
		if(e.wasDeath) 
		{		
	    	ExtendedWorldData worldProps = ExtendedWorldData.get(e.original.worldObj);

			ExtendedEntityData oldPlayerProps = ExtendedEntityData.get(e.original);	
			ExtendedEntityData newPlayerProps = ExtendedEntityData.get(e.entityPlayer);

			//WyNetworkHelper.sendTo(new PacketNewAABB(0.6F, 1.8F), (EntityPlayerMP) e.entityPlayer);
			
			if(MainConfig.enableKeepIEEPAfterDeath.equals("full"))
			{
				NBTTagCompound compound = new NBTTagCompound();
				
				ExtendedEntityData oldProps = ExtendedEntityData.get(e.original);
				oldProps.saveNBTData(compound);
				oldProps.triggerActiveHaki(false);
				oldProps.triggerBusoHaki(false);
				oldProps.triggerKenHaki(false);
				oldProps.setGear(1);
				oldProps.setZoanPoint("n/a");
				ExtendedEntityData props = ExtendedEntityData.get(e.entityPlayer);
				props.loadNBTData(compound);				
				
				compound = new NBTTagCompound();
				AbilityProperties.get(e.original).saveNBTData(compound);
				AbilityProperties abilityProps = AbilityProperties.get(e.entityPlayer);				
				AbilityProperties oldAbilityProps = AbilityProperties.get(e.original);
				abilityProps.loadNBTData(compound);
				
				Ability[] abilities = oldAbilityProps.getAbilitiesInHotbar();
				for(int i = 0 ; i < abilities.length ; i++) {
					Ability a = abilities[i];
					if(a != null) {
						boolean isRacial = abilityProps.hasRacialAbility(a);
						boolean isAkuma = abilityProps.hasDevilFruitAbility(a);
						boolean isHaki = abilityProps.hasHakiAbility(a);
						
						if(isRacial || isAkuma || isHaki) {
							abilityProps.setAbilityInSlot(i, a);
						}
					}
				}
				
				if(e.entityPlayer != null && MainConfig.enableExtraHearts)		
				{
					IAttributeInstance maxHp = e.entityPlayer.getEntityAttribute(SharedMonsterAttributes.maxHealth);
								
					if(props.getDoriki() / 100 <= 20)
						e.entityPlayer.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(20);
					else
						e.entityPlayer.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(props.getDoriki() / 100);
				}
			}
			else if(MainConfig.enableKeepIEEPAfterDeath.equals("auto"))
			{
				ExtendedEntityData oldProps = ExtendedEntityData.get(e.original);
				
				String faction = oldProps.getFaction();
				String race = oldProps.getRace();
				String fightStyle = oldProps.getFightStyle();
				String crew = oldProps.getCrew();
				int doriki = MathHelper.ceiling_double_int(WyMathHelper.percentage(MainConfig.dorikiKeepPercentage, oldProps.getDoriki()));
				int bounty = MathHelper.ceiling_double_int(WyMathHelper.percentage(MainConfig.bountyKeepPercentage, oldProps.getBounty()));
				int belly = MathHelper.ceiling_double_int(WyMathHelper.percentage(MainConfig.bellyKeepPercentage, oldProps.getBelly()));

				worldProps.removeDevilFruitFromWorld(oldProps.getUsedFruit());
				
				ExtendedEntityData props = ExtendedEntityData.get(e.entityPlayer);
				props.setFaction(faction);
				props.setRace(race);
				props.setFightStyle(fightStyle);
				props.setCrew(crew);			
				props.setMaxCola(100);
				props.setCola(oldProps.getMaxCola());
				props.setDoriki(doriki);
				props.setBounty(bounty);
				props.setBelly(belly);
				
				AbilityProperties oldAbilityProps = AbilityProperties.get(e.original);
				AbilityProperties abilityProps = AbilityProperties.get(e.entityPlayer);
				
				Ability[] abilities = oldAbilityProps.getAbilitiesInHotbar();
				for(int i = 0 ; i < abilities.length ; i++) {
					Ability a = abilities[i];
					if(a != null) {
						boolean isRacial = abilityProps.hasRacialAbility(a);
						boolean isAkuma = abilityProps.hasDevilFruitAbility(a);
						boolean isHaki = abilityProps.hasHakiAbility(a);
						
						if(isRacial || isAkuma || isHaki) {
							abilityProps.setAbilityInSlot(i, a);
						}
					}
				}
				
			}
			else if(MainConfig.enableKeepIEEPAfterDeath.equals("custom"))
			{
				ExtendedEntityData oldProps = ExtendedEntityData.get(e.original);
				ExtendedEntityData props = ExtendedEntityData.get(e.entityPlayer);

				for(String stat : MainConfig.statsToKeep)
				{
					switch(WyHelper.getFancyName(stat))
					{
						case "doriki":
							int doriki = MathHelper.ceiling_double_int(WyMathHelper.percentage(MainConfig.dorikiKeepPercentage, oldProps.getDoriki()));
							props.setDoriki(doriki); 
							break;
						case "bounty":
							int bounty = MathHelper.ceiling_double_int(WyMathHelper.percentage(MainConfig.bountyKeepPercentage, oldProps.getBounty()));
							props.setBounty(bounty); 
							break;
						case "belly":
							int belly = MathHelper.ceiling_double_int(WyMathHelper.percentage(MainConfig.bellyKeepPercentage, oldProps.getBelly()));
							props.setBelly(belly); 
							break;
						case "race":
							props.setRace(oldProps.getRace()); break;
						case "faction":
							props.setFaction(oldProps.getFaction()); break;
						case "fightingstyle":
							props.setFightStyle(oldProps.getFightStyle()); break;
						case "devilfruit":
							props.setUsedFruit(oldProps.getUsedFruit()); break;
					}
				}
				
				if(WyHelper.isNullOrEmpty(props.getUsedFruit()))
					worldProps.removeDevilFruitFromWorld(oldProps.getUsedFruit());
			}
			
			NBTTagCompound compound = new NBTTagCompound();
			QuestProperties.get(e.original).saveNBTData(compound);
			QuestProperties questProps = QuestProperties.get(e.entityPlayer);
			questProps.loadNBTData(compound);
		}
	}
	
	@SubscribeEvent
	public void onPlayerLoggedIn(ClientConnectedToServerEvent event)
	{
		if(!WyDebug.isDebug())
		{
			WyTelemetry.addMiscStat("onlinePlayers", "Online Players", 1);
			WyTelemetry.sendAllData();
		}
	}
	
	@SubscribeEvent
	public void onPlayerLoggedOut(ClientDisconnectionFromServerEvent event)
	{
		if(!WyDebug.isDebug())
		{
			WyTelemetry.addMiscStat("onlinePlayers", "Online Players", -1);
			WyTelemetry.sendAllDataSync();
		}
	}
	
	@SubscribeEvent
	public void onPlayerTick(TickEvent.PlayerTickEvent event)
	{
		if(event.phase == Phase.END && event.side == Side.SERVER)
		{
			if(event.player.worldObj.getWorldTime() % 1200 == 0)
			{
				WyTelemetry.sendAllData();
			}
		}
	}
	
	@SubscribeEvent
	public void onEatSomething(PlayerUseItemEvent.Finish e) {
		if(e.item.getItem() == Items.milk_bucket && !e.entityPlayer.worldObj.isRemote) {
				EntityPlayer player = e.entityPlayer;
				
				ExtendedEntityData props = ExtendedEntityData.get(player);
				AbilityProperties abilityProps = AbilityProperties.get(player);
				ExtendedWorldData worldProps = ExtendedWorldData.get(player.worldObj);

				worldProps.removeDevilFruitFromWorld(props.getUsedFruit());
				
				props.setUsedFruit("N/A");
				props.setYamiPower(false);
				props.setIsLogia(false);
				props.triggerActiveHaki(false);
				props.triggerBusoHaki(false);
				props.triggerKenHaki(false);
				
				abilityProps.clearHotbar();
				abilityProps.clearDevilFruitAbilities();
				player.clearActivePotions();

				props.setZoanPoint("n/a");
				
				player.clearActivePotions();
				
				WyNetworkHelper.sendTo(new PacketSync(props), (EntityPlayerMP)player);	
				WyNetworkHelper.sendToAll(new PacketSyncInfo(player.getDisplayName(), props));	
				WyNetworkHelper.sendTo(new PacketAbilitySync(abilityProps), (EntityPlayerMP)player);	
		}
	}
}
