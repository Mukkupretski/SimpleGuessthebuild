package com.example.myplugin.Util;

import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.example.myplugin.MyPlugin;

public class SelectTheme {
  private static ItemStack GetRandomTopic() {
    List<String> aiheet = MyPlugin.getInstance().getConfig().getStringList("aiheet");
    ItemStack diamond = new ItemStack(Material.PAPER);
    ItemMeta meta = diamond.getItemMeta();
    Random random = new Random();
    meta.setDisplayName(aiheet.get(random.nextInt(aiheet.size())));
    diamond.setItemMeta(meta);
    return diamond;
  }

  public static void openMenu(Player player) {
    Inventory inv = Bukkit.createInventory(null, 27, "Valitse aihe");

    inv.setItem(11, GetRandomTopic());
    inv.setItem(13, GetRandomTopic());
    inv.setItem(15, GetRandomTopic());

    player.openInventory(inv);
  }
}
