package pl.rodac.me.checks.movement;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

import pl.rodac.me.Utils.CheckDoubles;
import pl.rodac.me.Utils.Messages;
import pl.rodac.me.checks.CheckTypes;


public class WaterWalk implements Listener
{
	
	@EventHandler
	public void onMove(PlayerMoveEvent e)
	{
		Player p = e.getPlayer();
		Location to = e.getTo();
		Location from = e.getFrom();
		Vector vec = to.toVector();
		double i = vec.distance(from.toVector());
		if(p.getGameMode().equals(GameMode.CREATIVE))
		{
			return;
		}
		if(p.getEntityId()==
				100){
			
			return;
		}
		if(p.getVehicle() !=null){
			return;
		}
		if(p.getAllowFlight() == true){
			return;
		}
		if((i > CheckDoubles.MIN_WATER_WALK) && (i < 
				CheckDoubles.MAX_WATER_WALK)){
			if(p.getLocation().getBlock().getRelative(BlockFace.DOWN).getType() == 
					Material.WATER){
				return;
			}
			if(p.getLocation().getBlock().getRelative(BlockFace.DOWN).isLiquid())
			{
				p.setHealth(0);
				e.getPlayer().sendMessage(Messages.PREFIX + "You tried to move wrongly, §e§lCheckType§7 » §a§l" + CheckTypes.WATERWALK);
			}
		}
	}

}
