package pl.rodac.me.checks.combat.npc;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import net.minecraft.server.v1_8_R3.Packet;
import pl.rodac.me.Main;
import pl.rodac.me.Utils.Messages;
import pl.rodac.me.checks.CheckTypes;
import pl.rodac.me.checks.combat.KillAura;

public class PacketReader {
	 
    Player player;
    Channel channel;
   
    public PacketReader(Player player) {
            this.player = player;
    }
   
    public void inject(){
            CraftPlayer cPlayer = (CraftPlayer)this.player;
            channel = cPlayer.getHandle().playerConnection.networkManager.channel;
            channel.pipeline().addAfter("decoder", "PacketInjector", new MessageToMessageDecoder<Packet<?>>() {@Override protected void decode(ChannelHandlerContext arg0, Packet<?> packet,List<Object> arg2) throws Exception {arg2.add(packet);readPacket(packet);}});
    }
   
    public void uninject(){
            if(channel.pipeline().get("PacketInjector") != null){
                    channel.pipeline().remove("PacketInjector");
            }
    }

    static ArrayList<Player> dedected = new ArrayList<>();
   
    public void readPacket(Packet<?> packet){
        if(packet.getClass().getSimpleName().equalsIgnoreCase("PacketPlayInUseEntity")){
                int id = (Integer)getValue(packet, "a");
                if(!(dedected.contains(player))){
                	if(KillAura.check.containsKey(player)){
                		NPC npc = KillAura.check.get(player);
                		if(id == npc.getEntityID()){
                			if(getValue(packet, "action").toString().equalsIgnoreCase("ATTACK")){
                				npc.animation(player, 1);
                				dedected.add(player);
                				Bukkit.getScheduler().runTaskLater(Main.getInstance(), new Runnable(){
                					
                					@Override
                					public void run(){
                						player.kickPlayer(Messages.PREFIX + "Unfair Advantage, §e§lCheckType§7 » §a§l" + CheckTypes.KILLAURA);   
                						dedected.remove(player);
                					
                				
                					}
                				},10);
                			}
                		}
                	}
                }
        }
               
    }
   

    public void setValue(Object obj,String name,Object value){
            try{
            Field field = obj.getClass().getDeclaredField(name);
            field.setAccessible(true);
            field.set(obj, value);
            }catch(Exception e){}
    }
   
    public Object getValue(Object obj,String name){
            try{
            Field field = obj.getClass().getDeclaredField(name);
            field.setAccessible(true);
            return field.get(obj);
            }catch(Exception e){}
            return null;
    }
   
}
