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

public class PacketQuestHint implements IMessage {
	@Override
	public void fromBytes(ByteBuf buffer) {}

	@Override
	public void toBytes(ByteBuf buffer) {}

	public static class ClientHandler implements IMessageHandler<PacketQuestHint, IMessage> {
		@Override
		@SideOnly(Side.CLIENT)
		public IMessage onMessage(PacketQuestHint message, MessageContext ctx) {
			EntityPlayer player = Minecraft.getMinecraft().thePlayer;
			player.worldObj.playSound(player.posX, player.posY, player.posZ, ID.PROJECT_ID + ":quest-log", 1, 1,false);
			return null;
		}
	}

	public static class ServerHandler implements IMessageHandler<PacketQuestHint, IMessage> {
		@Override
		public IMessage onMessage(PacketQuestHint message, MessageContext ctx) {
			return new PacketQuestHint();
		}
	}
}
