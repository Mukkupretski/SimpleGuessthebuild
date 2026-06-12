package com.example.myplugin.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Criteria;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import com.example.myplugin.Managers.GameManager;

import net.kyori.adventure.text.Component;
import net.md_5.bungee.api.ChatColor;

public class ScoreboardUtil {
  private static ScoreboardUtil instance = new ScoreboardUtil();

  private ScoreboardUtil() {

  }

  public static ScoreboardUtil getInstance() {
    return ScoreboardUtil.instance;
  }

  public void UpdateScoreboard() {
    Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();

    Objective obj = board.registerNewObjective(
        "game",
        Criteria.DUMMY,
        Component.text("Guess the Build"));

    obj.setDisplaySlot(DisplaySlot.SIDEBAR);

    int score = GameManager.getInstance().GetScores().size() + 3;
    String eventScore = ChatColor.YELLOW + GameManager.getInstance().GetEvent();
    String timeScore = ChatColor.YELLOW + "Aika: " + ChatColor.GREEN + GameManager.getInstance().GetTime();
    String empty = "";
    obj.getScore(eventScore).setScore(score--);
    obj.getScore(timeScore).setScore(score--);
    obj.getScore(empty).setScore(score--);
    List<Map.Entry<String, Integer>> sorted = new ArrayList<>(GameManager.getInstance().GetScores().entrySet());

    sorted.sort((a, b) -> Integer.compare(b.getValue(), a.getValue()));
    for (Map.Entry<String, Integer> line : sorted) {
      String finalLine = line.getKey() + ChatColor.GREEN + line.getValue().toString();
      obj.getScore(finalLine).setScore(score--);
    }

    for (UUID uuid : GameManager.getInstance().getPlayers()) {
      Player player = Bukkit.getPlayer(uuid);
      if (player == null)
        return;
      player.setScoreboard(board);
    }
  }
}
