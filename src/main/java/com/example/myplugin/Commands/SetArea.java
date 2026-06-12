package com.example.myplugin.Commands;

import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.example.myplugin.MyPlugin;

import net.md_5.bungee.api.ChatColor;

public class SetArea extends Command {
  public void onCommand(CommandSender sender, String[] args) {
    String idx = args[0];
    if (!(sender instanceof Player player)) {
      System.out.println("This command can only be used by a player");
      return;
    }
    if (!idx.equals("1") && !idx.equals("2")) {
      player.sendMessage(ChatColor.RED + "Usage: /setarea <1/2>");
      return;
    }
    Location loc = player.getLocation();
    sender.sendMessage(
        "Set location " + idx + " to (" + loc.getBlockX() + ", " + loc.getBlockY() + ", " + loc.getBlockZ() + ")");
    MyPlugin.getInstance().getConfig().set(idx, loc);
    MyPlugin.getInstance().saveConfig();

  }
}
