package pl.rodac.me.checks.combat;

import java.util.HashMap;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;

import pl.rodac.me.Main;
import pl.rodac.me.Utils.CheckDoubles;
import pl.rodac.me.Utils.Messages;
import pl.rodac.me.checks.CheckTypes;

public class CPS implements Listener{
	
	public HashMap<Player, Integer> hitsPerSecond;
    public BukkitRunnable seconds;
    private Main plugin;
    
    @EventHandler
    public void onClick(PlayerInteractEvent e) {
        if (hitsPerSecond.containsKey(e.getPlayer())) {
            hitsPerSecond.put(e.getPlayer(), 0);
        } else {
            int hits = hitsPerSecond.get(e.getPlayer());
            hitsPerSecond.put(e.getPlayer(), hits++);
        }
        if (seconds == null) {
            seconds = new BukkitRunnable() {
                @Override
                public void run() {
                    for (Player p : hitsPerSecond.keySet()) {
                        if (hitsPerSecond.get(p) > CheckDoubles.MAX_CPS) {
                            p.kickPlayer(Messages.PREFIX + "Unfair Advantage, §e§lCheckType§7 » §a§l" + CheckTypes.HIGH_CPS);
                        } else {
                            hitsPerSecond.put(e.getPlayer(), 0);
                        }
                    }

                }
            };
            seconds.runTaskTimer(plugin, 0, 20);
        }
    }

    

}
