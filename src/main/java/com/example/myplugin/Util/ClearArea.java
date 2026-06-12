package com.example.myplugin.Util;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;

import com.example.myplugin.MyPlugin;

public class ClearArea {
  public static void clearArea() {
    Location loc1 = MyPlugin.getInstance().getConfig().getLocation("1");
    Location loc2 = MyPlugin.getInstance().getConfig().getLocation("2");
    if (loc1 == null || loc2 == null) {
      System.out.println("Locations not set!");
      return;
    }

    int minX = Math.min(loc1.getBlockX(), loc2.getBlockX());
    int minY = Math.min(loc1.getBlockY(), loc2.getBlockY());
    int minZ = Math.min(loc1.getBlockZ(), loc2.getBlockZ());

    int maxX = Math.max(loc1.getBlockX(), loc2.getBlockX());
    int maxY = Math.max(loc1.getBlockY(), loc2.getBlockY());
    int maxZ = Math.max(loc1.getBlockZ(), loc2.getBlockZ());

    World world = loc1.getWorld();

    for (int x = minX; x <= maxX; x++) {
      for (int y = minY; y <= maxY; y++) {
        for (int z = minZ; z <= maxZ; z++) {

          world.getBlockAt(x, y, z).setType(Material.AIR);

        }
      }
    }
  }
}
