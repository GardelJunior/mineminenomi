package xyz.pixelatedw.MineMineNoMi3.api.network;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import xyz.pixelatedw.MineMineNoMi3.ID;
import xyz.pixelatedw.MineMineNoMi3.MainMod;
import xyz.pixelatedw.MineMineNoMi3.api.quests.QuestProperties;

public class PacketQuestCompleted implements IMessage {
	@Override
	public void fromBytes(ByteBuf buffer) {}

	@Override
	public void toBytes(ByteBuf buffer) {}

	public static class ClientHandler implements IMessageHandler<PacketQuestCompleted, IMessage> {
		@Override
		@SideOnly(Side.CLIENT)
		public IMessage onMessage(PacketQuestCompleted message, MessageContext ctx) {
			EntityPlayer player = Minecraft.getMinecraft().thePlayer;
			player.worldObj.playSound(player.posX, player.posY, player.posZ, ID.PROJECT_ID + ":quest-completed", 1, 1,false);
			return null;
		}
	}

	public static class ServerHandler implements IMessageHandler<PacketQuestCompleted, IMessage> {
		@Override
		public IMessage onMessage(PacketQuestCompleted message, MessageContext ctx) {
			return new PacketQuestCompleted();
		}
	}
}
