package pl.rodac.me.checks.combat;

import java.util.HashMap;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;

import pl.rodac.me.Utils.Messages;
import pl.rodac.me.checks.CheckTypes;

public class FastBow implements Listener{
	
	static HashMap<Player, Long> lastbow = new HashMap<>();
	
	@EventHandler
	public void onShot(EntityShootBowEvent e){
		
	
		if(!(e.getEntity() instanceof Player)){
			return;
		}
		
		Player p = (Player) e.getEntity();
		if(!lastbow.containsKey(p)){
			lastbow.put(p, 0L);
		}
		if(e.getForce() !=1.0D){
			return;
		}
		if(lastbow.get(p) == 0L){
			lastbow.put(p, System.currentTimeMillis());
			return;
		}
		if(System.currentTimeMillis() - lastbow.get(p) < 500L){
			e.getProjectile().remove();
			e.setCancelled(true);
			p.sendMessage(Messages.PREFIX + "You tried to move wrongly, §e§lCheckType§7 » §a§l" + CheckTypes.FASTBOW);
		}
	}

}
