package pl.rodac.me.checks.movement;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import pl.rodac.me.Utils.Messages;
import pl.rodac.me.checks.CheckTypes;

public class Glide implements Listener {
	

	@EventHandler
	public void onMove(PlayerMoveEvent e){
		if((e.getTo().getY() - e.getFrom().getY() == -0.125D) 
			&& e.getTo().clone().subtract(0.0D, 1.0D, 0.0D).getBlock().getType().equals(Material.AIR)){
			e.setCancelled(true);
			e.getPlayer().teleport(e.getPlayer());
			e.getPlayer().sendMessage(Messages.PREFIX + "You tried to move wrongly, §e§lCheckType§7 » §a§l" + CheckTypes.GLIDE);
		}
	}
}
