package com.example.myplugin;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import com.example.myplugin.JoinListener;

public class MyPlugin extends JavaPlugin {

  
    @Override
    public void onEnable() {
        getLogger().info("Plugin enabled!");
    getServer().getPluginManager().registerEvents(
        new JoinListener(),
        
        this
    );
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command,
                             String label, String[] args) {

        if (command.getName().equalsIgnoreCase("hello")) {
            sender.sendMessage("Hello!");
            return true;
        }

        return false;
    }
}
