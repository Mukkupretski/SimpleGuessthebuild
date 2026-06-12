package com.example.myplugin.Listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.example.myplugin.Managers.GameManager;

import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;

public class MessageListener implements Listener {

  @EventHandler
  public void onMessage(AsyncChatEvent event) {
    Player player = event.getPlayer();
    String guess = PlainTextComponentSerializer.plainText().serialize(event.message());
    GameManager.getInstance().SendGuess(player, guess);
  }
}
