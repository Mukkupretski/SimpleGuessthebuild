package com.example.myplugin.Util;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.example.myplugin.Managers.GameManager;

public class Broadcast {

  public static void BroadcastMessage(String message) {
    ArrayList<UUID> players = GameManager.getInstance().getPlayers();
    for (UUID uuid : players) {
      Player player = Bukkit.getPlayer(uuid);
      player.sendMessage(message);
    }
  }
}
