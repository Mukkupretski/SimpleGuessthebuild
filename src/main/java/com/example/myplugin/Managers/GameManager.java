package com.example.myplugin.Managers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.World;
import org.bukkit.entity.Player;

import com.example.myplugin.MyPlugin;
import com.example.myplugin.Util.Broadcast;
import com.example.myplugin.Util.ScoreboardUtil;
import com.example.myplugin.Util.SelectTheme;
import com.example.myplugin.Util.Timer;

import net.md_5.bungee.api.ChatColor;

enum States {
  BUILDING,
  ODOTUS,
  POIS,
}

public class GameManager {
  private ArrayList<UUID> players = new ArrayList<UUID>();
  private static final GameManager instance = new GameManager();
  private HashSet<UUID> guessedCorrectly = new HashSet<UUID>();
  private HashSet<UUID> playersLeft = new HashSet<UUID>();
  String sana = "";
  HashMap<String, Integer> scores = new HashMap<String, Integer>();
  int time = 0;
  String event = "";
  boolean Guesstime = false;
  UUID playerInTurn = null;
  States pelinTila = States.POIS;

  private GameManager() {
  }

  public String GetEvent() {
    return event;
  }

  public int GetTime() {
    return time;
  }

  public HashMap<String, Integer> GetScores() {
    return scores;
  }

  public static GameManager getInstance() {
    return instance;
  }

  public void StartGame() {
    if (pelinTila != States.POIS)
      return;
    guessedCorrectly = new HashSet<>();

    for (UUID uuid : players) {
      Player player = Bukkit.getPlayer(uuid);
      player.stopAllSounds();
      playersLeft.add(uuid);
      SetScore(uuid, 0);
    }
    NextPlayerTurn();
  }

  // remove all players
  // reset everything
  // FIXME: ANNOUNCE WINNER
  public void EndGame() {
    Broadcast.BroadcastMessage("Peli päättyi");
    for (UUID uuid : players) {
      Player player = Bukkit.getPlayer(uuid);
      player.playSound(player.getLocation(), Sound.MUSIC_DISC_TEARS, SoundCategory.MUSIC, 1f, 1f);

    }
  }

  // add here to lists
  // and show scoreboard
  public void AddPlayer(Player player) {
    Broadcast.BroadcastMessage("Pelaaja " + ChatColor.GREEN + player.getName() + ChatColor.WHITE + " liittyi");
    players.add(player.getUniqueId());
    SetScore(player.getName(), 0);
    playersLeft.add(player.getUniqueId());

  }

  void SetScore(String name, int score) {
    scores.put(name, score);
    ScoreboardUtil.getInstance().UpdateScoreboard();
  }

  void SetScore(UUID uuid, int score) {
    Player player = Bukkit.getPlayer(uuid);
    SetScore(player.getName(), score);
  }

  int GetScore(String name) {
    return scores.get(name);
  }

  int GetScore(UUID uuid) {
    return scores.get(Bukkit.getPlayer(uuid).getName());
  }

  void SetEvent(String event) {
    this.event = event;
    ScoreboardUtil.getInstance().UpdateScoreboard();
  }

  public void SetTime(int time) {
    this.time = time;
    ScoreboardUtil.getInstance().UpdateScoreboard();
  }

  // remove from here
  // and hide scoreboard and stuff
  public void RemovePlayer(Player player) {
    Broadcast.BroadcastMessage("Pelaaja " + ChatColor.RED + player.getName() + ChatColor.WHITE + " lähti");
    players.remove(player.getUniqueId());
    player.setScoreboard(
        Bukkit.getScoreboardManager().getMainScoreboard());
    if (playerInTurn == player.getUniqueId()) {
      Timer.getInstance().SetTime(0);
      player.stopAllSounds();
    }
  }

  public void TimerEnd() {
    switch (pelinTila) {
      case States.ODOTUS:
        SetState(States.BUILDING);
        break;
      case States.BUILDING:
        if (playersLeft.isEmpty()) {
          SetState(States.POIS);
        } else {
          SetState(States.ODOTUS);
        }
      case States.POIS:
        break;
    }
  }

  public boolean IsInGame(UUID uuid) {
    return players.contains(uuid);
  }

  public ArrayList<UUID> getPlayers() {
    return players;
  }

  public void ClearArea() {
    Location l1 = MyPlugin.getInstance().getConfig().getLocation("1");
    Location l2 = MyPlugin.getInstance().getConfig().getLocation("2");

    if (l1 == null || l2 == null) {
      return;
    }

    int minX = Math.min(l1.getBlockX(), l2.getBlockX());
    int maxX = Math.max(l1.getBlockX(), l2.getBlockX());

    int minY = Math.min(l1.getBlockY(), l2.getBlockY());
    int maxY = Math.max(l1.getBlockY(), l2.getBlockY());

    int minZ = Math.min(l1.getBlockZ(), l2.getBlockZ());
    int maxZ = Math.max(l1.getBlockZ(), l2.getBlockZ());

    World world = l1.getWorld();

    for (int x = minX; x <= maxX; x++) {
      for (int y = minY; y <= maxY; y++) {
        for (int z = minZ; z <= maxZ; z++) {
          world.getBlockAt(x, y, z).setType(Material.AIR);
        }
      }
    }
  }

  public void RevealWord() {
    String vanhaSana = sana;
    SetSana("");
    Broadcast.BroadcastMessage(ChatColor.YELLOW + "Sana oli " + ChatColor.GOLD + vanhaSana);
  }

  public void NextPlayerTurn() {
    ClearArea();
    UUID randomPlayer = playersLeft.stream()
        .skip(ThreadLocalRandom.current().nextInt(playersLeft.size()))
        .findFirst()
        .orElse(null);
    playerInTurn = randomPlayer;
    playersLeft.remove(randomPlayer);
    Player player = Bukkit.getPlayer(randomPlayer);
    SelectTheme.openMenu(player);
  }

  void SetState(States state) {
    switch (state) {
      case States.ODOTUS:
        SetEvent("Väliaika");
        Timer.getInstance().Start(5);
        break;
      case States.BUILDING:
        Timer.getInstance().Start(120);
        NextPlayerTurn();
        SetEvent(ChatColor.GREEN + Bukkit.getPlayer(playerInTurn).getName() + ChatColor.WHITE + " rakentaa");
        break;
      case States.POIS:
        SetEvent("Peli päättyi");
        EndGame();
        break;
    }
    pelinTila = state;

  }

  public void SetSana(String sana) {
    this.sana = sana;
    if (sana == "") {
      guessedCorrectly = new HashSet<UUID>();
      Guesstime = false;

    } else {
      Guesstime = true;
      Broadcast.BroadcastMessage("Alkakaa arvailemaan");
    }
  }

  public void SendGuess(Player player, String guess) {
    if (!Guesstime || sana == "")
      return;
    UUID uuid = player.getUniqueId();
    if (!players.contains(uuid))
      return;
    if (guessedCorrectly.contains(uuid) || playerInTurn == uuid)
      return;
    if (!guess.equalsIgnoreCase(sana))
      return;
    if (guessedCorrectly.isEmpty()) {
      SetScore(playerInTurn, GetScore(playerInTurn) + 3);
    }
    int scoreToAdd = Math.max(1, guessedCorrectly.size());
    SetScore(player.getUniqueId(), GetScore(playerInTurn) + scoreToAdd);
    player.sendMessage("Sana oli " + ChatColor.GOLD + sana);
    guessedCorrectly.add(player.getUniqueId());

  }
}
