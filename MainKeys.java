package MineMineNoMi3;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;

import org.lwjgl.input.Keyboard;

import MineMineNoMi3.Packets.PacketPlayer;
import MineMineNoMi3.Packets.PacketSync;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent.KeyInputEvent;

public class MainKeys 
{

	public static KeyBinding guiPlayer;
	    
	public static void init() 
	{
		guiPlayer = new KeyBinding("Main GUI", Keyboard.KEY_F, "Mine Mine no Mi Keys");
		ClientRegistry.registerKeyBinding(guiPlayer);
	}
	    
	public static boolean isShiftKeyDown()
	{
		return Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT);		
	}
	
	@SubscribeEvent
	public void onKeyInput(KeyInputEvent event) 
	{
		if(guiPlayer.isPressed())
		{
        	Minecraft minecraft = Minecraft.getMinecraft();
        	EntityPlayer player = minecraft.thePlayer;
        	WorldClient world = minecraft.theWorld;  
        	player.openGui(Main.instance, 1, world, (int)player.posX, (int)player.posY, (int)player.posZ);  
        	Main.network.sendToServer(new PacketPlayer("forcesync"));      	
		}
	}
	
}
