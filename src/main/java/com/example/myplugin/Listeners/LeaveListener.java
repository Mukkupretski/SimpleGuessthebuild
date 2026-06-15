package com.example.myplugin.Listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import com.example.myplugin.Managers.GameManager;

public class LeaveListener implements Listener {

  @EventHandler
  public void onLeave(PlayerQuitEvent e) {
    GameManager.getInstance().RemovePlayer(e.getPlayer());
  }
}
