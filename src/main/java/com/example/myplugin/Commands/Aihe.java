package com.example.myplugin.Commands;

import java.util.Arrays;
import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.example.myplugin.MyPlugin;
import com.example.myplugin.Managers.GameManager;

import net.md_5.bungee.api.ChatColor;

public class Aihe extends Command {
  public void onCommand(CommandSender sender, String[] args) {
    if (!(sender instanceof Player player)) {
      System.out.println("This command can only be used by a player");
      return;
    }
    GameManager.getInstance().AddPlayer(player);
    if (args.length < 1) {

      player.sendMessage(ChatColor.RED + "Käyttö: /aihe <add/remove/list> <aihe>");
      return;
    }
    String type = args[0];
    List<String> aiheet = MyPlugin.getInstance().getConfig().getStringList("aiheet");
    switch (type) {
      case "add":
        String uusiaihe = String.join(" ", Arrays.copyOfRange(args, 1, args.length));
        if (aiheet.contains(uusiaihe)) {
          player.sendMessage(ChatColor.RED + "Aihe " + ChatColor.GOLD + uusiaihe + ChatColor.RED + " lisätty");
          return;
        }
        aiheet.add(uusiaihe);
        MyPlugin.getInstance().getConfig().set("aiheet", aiheet);
        player.sendMessage("Aihe " + ChatColor.GOLD + uusiaihe + ChatColor.WHITE + " lisätty");
        break;

      case "list":
        for (String aihe : aiheet) {
          player.sendMessage(ChatColor.GOLD + aihe);
        }
        break;
      case "remove":
        String poistettavaAihe = String.join(" ", Arrays.copyOfRange(args, 1, args.length));
        if (aiheet.remove(poistettavaAihe)) {
          MyPlugin.getInstance().getConfig().set("aiheet", aiheet);
          MyPlugin.getInstance().saveConfig();
          player.sendMessage("Aihe " + ChatColor.GOLD + poistettavaAihe + ChatColor.WHITE + " poistettu");
        } else {
          player.sendMessage(
              ChatColor.RED + "Aihetta " + ChatColor.GOLD + poistettavaAihe + ChatColor.RED + " ei ole olemassa");
        }
        break;
      default:
        player.sendMessage(ChatColor.RED + "Käyttö: /aihe <add/remove/list> <aihe>");
        break;
    }

  }
}
