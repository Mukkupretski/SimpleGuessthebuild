package com.example.myplugin.Commands;

import org.bukkit.command.CommandSender;

import com.example.myplugin.Managers.GameManager;

public class End extends Command {

  public void onCommand(CommandSender sender, String[] args) {
    GameManager.getInstance().EndGame();
  }
}
