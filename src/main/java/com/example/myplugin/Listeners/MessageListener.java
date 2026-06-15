package com.example.myplugin.Listeners;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.example.myplugin.MyPlugin;
import com.example.myplugin.Managers.GameManager;

import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;

public class MessageListener implements Listener {

  @EventHandler
  public void onMessage(AsyncChatEvent event) {
    String guess = PlainTextComponentSerializer.plainText().serialize(event.message());

    if (!GameManager.getInstance().ValidateGuess(event.getPlayer(), guess)) {
      return;
    }
    event.setCancelled(true);
    Bukkit.getScheduler().runTask(MyPlugin.getInstance(), () -> {
      GameManager.getInstance().CorrectGuess(event.getPlayer(), guess);
    });
  }
}
