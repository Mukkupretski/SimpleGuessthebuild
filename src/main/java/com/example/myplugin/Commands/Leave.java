package com.example.myplugin.Commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.example.myplugin.Managers.GameManager;

public class Leave extends Command {

  public void onCommand(CommandSender sender, String[] args) {
    if (!(sender instanceof Player player)) {
      System.out.println("Must be run as a player");
      return;
    }
    player.sendMessage("Poistuit pelistä");
    GameManager.getInstance().RemovePlayer(player);
  }
}
