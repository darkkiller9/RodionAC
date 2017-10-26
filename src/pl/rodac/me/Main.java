package pl.rodac.me;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import pl.rodac.me.checks.combat.CPS;
import pl.rodac.me.checks.movement.Flight;
import pl.rodac.me.checks.movement.Glide;
import pl.rodac.me.checks.movement.NoFall;
import pl.rodac.me.checks.movement.SpeedA;
import pl.rodac.me.checks.movement.SpeedB;




public class Main extends JavaPlugin implements Listener{
	private static Main instance;

	public static FileConfiguration config;

	public static File conf;
	
	
	public void onEnable(){
		
		instance = this;
		config = getConfig();
		config.options().copyDefaults(true);
		conf = new  File(getDataFolder(), "config.yml");
		Bukkit.getServer().getPluginManager().registerEvents(this, this);
		saveConfig();
		saveDefaultConfig();
		
		//register commands here:
		
		
		
		//register events here:
		Bukkit.getServer().getPluginManager().registerEvents(new Glide(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new Flight(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new SpeedA(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new SpeedB(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new NoFall(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new CPS(), this);
	}
	public static Main getInstance(){
    	return instance;
	
		
		
	}

}
