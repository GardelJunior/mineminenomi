package xyz.pixelatedw.MineMineNoMi3.helpers;

import net.minecraft.client.Minecraft;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.EnumChatFormatting;
import xyz.pixelatedw.MineMineNoMi3.quests.Quest;

public class QuestHelper {
	public static ItemStack generateQuestItem(Item item, String questID, String title, String description,Class<? extends Quest> questClass) {
		ItemStack stack = new ItemStack(item);
		
		NBTTagCompound quest = new NBTTagCompound();
		quest.setString("id", questID);
		quest.setString("class", questClass.getName());
		
		NBTTagList lore = new NBTTagList();
		final String[] descrParts = description.split("\n");
		for(final String part : descrParts) lore.appendTag(new NBTTagString(part));
		lore.appendTag(new NBTTagString());
		lore.appendTag(new NBTTagString(EnumChatFormatting.BLUE + "Right Click To Add This Quest"));
		quest.setTag("lore",lore);
		
		NBTTagCompound tag = new NBTTagCompound();
		tag.setTag("quest", quest);
		tag.setTag("ench", new NBTTagList());
		
		stack.setTagCompound(tag);
		stack.setStackDisplayName(EnumChatFormatting.GOLD + title + EnumChatFormatting.RESET);
		
		return stack;
	}
}
