package com.example.myplugin.Listeners;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import com.example.myplugin.Managers.GameManager;

import net.md_5.bungee.api.ChatColor;

public class MenuListener implements Listener {

  @EventHandler
  public void onInventoryClick(InventoryClickEvent event) {
    if (!event.getView().getTitle().equals("Valitse aihe")) {
      return;
    }

    event.setCancelled(true); // Prevent taking items

    ItemStack item = event.getCurrentItem();
    if (item == null || item.getType() == Material.AIR) {
      return;
    }

    Player player = (Player) event.getWhoClicked();

    if (item.getType() == Material.PAPER) {
      String valittuAihe = item.getItemMeta().getDisplayName();
      player.sendMessage("Valitsit aiheen " + ChatColor.GOLD + valittuAihe);
      GameManager.getInstance().SetSana(valittuAihe);
      event.getView().close();
    }
  }
}
