package xyz.pixelatedw.MineMineNoMi3.api.network;

import java.util.Optional;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import xyz.pixelatedw.MineMineNoMi3.ID;
import xyz.pixelatedw.MineMineNoMi3.MainMod;
import xyz.pixelatedw.MineMineNoMi3.api.quests.QuestProperties;

public class PacketStartQuest implements IMessage {
	
	private String questID;
	
	public PacketStartQuest() {}
	
	public PacketStartQuest(String questID) {
		this.questID = questID;
	}

	
	@Override
	public void fromBytes(ByteBuf buffer) {
		int size = buffer.readInt();
		this.questID = new String(buffer.array(),Integer.BYTES+1,size);
		System.out.println(this.questID);
	}

	@Override
	public void toBytes(ByteBuf buffer) {
		buffer.writeInt(questID.length());
		buffer.writeBytes(questID.getBytes());
	}

	public static class ServerHandler implements IMessageHandler<PacketStartQuest, IMessage> {
		@Override
		public IMessage onMessage(PacketStartQuest message, MessageContext ctx) {
			Optional.ofNullable(QuestProperties.get(MainMod.proxy.getPlayerEntity(ctx))).ifPresent(props -> {
				if(message.questID != null) props.setCurrentQuest(message.questID);
			});
			return null;
		}
	}
	
	public static class ClientHandler implements IMessageHandler<PacketStartQuest, IMessage> {
		@Override
		@SideOnly(Side.CLIENT)
		public IMessage onMessage(PacketStartQuest message, MessageContext ctx) {
			return null;
		}
	}
}
