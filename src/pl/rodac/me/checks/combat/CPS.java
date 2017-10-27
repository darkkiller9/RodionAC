package pl.rodac.me.checks.combat;

import java.util.HashMap;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;

import pl.rodac.me.Main;
import pl.rodac.me.Utils.Messages;
import pl.rodac.me.checks.CheckTypes;

public class CPS implements Listener{
	
	public HashMap<Player, Integer> hitsPerSecond;
    public BukkitRunnable seconds;
    public Main plugin; // this is your main

    public CPS(Main plugin) {
        this.plugin = plugin;
        hitsPerSecond = new HashMap<>();
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onClick(PlayerInteractEvent e) {
        if (!hitsPerSecond.containsKey(e.getPlayer())) { //i've missed the '!'
            hitsPerSecond.put(e.getPlayer(), 0);
        } else {
            int hits = hitsPerSecond.get(e.getPlayer());
            hitsPerSecond.put(e.getPlayer(), (hits+1)); //Change hits++ to (hits+1)
        }
        if (seconds == null) {
            seconds = new BukkitRunnable() {
                @Override
                public void run() {
                    for (Player p : hitsPerSecond.keySet()) {
                        if (hitsPerSecond.get(p) > 16) {
                            p.sendMessage(Messages.PREFIX + "You tried to move wrongly, §e§lCheckType§7 » §a§l" + CheckTypes.HIGH_CPS);
                            hitsPerSecond.put(e.getPlayer(), 0); // set to 0 again
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
