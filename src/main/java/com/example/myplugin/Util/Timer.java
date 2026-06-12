package com.example.myplugin.Util;

import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;

import com.example.myplugin.MyPlugin;
import com.example.myplugin.Managers.GameManager;

import net.md_5.bungee.api.ChatColor;

public class Timer {
  private static Timer instance = new Timer();
  private Set<Integer> importantTimes = Set.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 15, 30, 60, 90);

  private int timeLeft = 0;
  private BukkitTask task;

  private Timer() {
  }

  public static Timer getInstance() {
    return Timer.instance;
  }

  public void RemoveTime(int seconds) {
    timeLeft = Math.max(timeLeft - seconds, 0);
  }

  public void SetTime(int seconds) {
    timeLeft = seconds;
  }

  public void Start(int time) {
    timeLeft = time;

    task = Bukkit.getScheduler().runTaskTimer(MyPlugin.getInstance(), () -> {

      // Update scoreboard here
      GameManager.getInstance().SetTime(timeLeft);

      // Check if timer ended
      if (importantTimes.contains(time))
        Broadcast
            .BroadcastMessage(ChatColor.RED + Integer.toString(timeLeft) + ChatColor.YELLOW + " sekuntia jäljellä");
      if (timeLeft <= 0) {

        GameManager.getInstance().TimerEnd();

        task.cancel();
        return;
      }

      timeLeft--;

    }, 0L, 20L); // 20 ticks = 1 second
  }

}
