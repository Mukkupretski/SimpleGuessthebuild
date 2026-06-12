package com.example.myplugin;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import com.example.myplugin.Commands.Aihe;
import com.example.myplugin.Commands.End;
import com.example.myplugin.Commands.Join;
import com.example.myplugin.Commands.Leave;
import com.example.myplugin.Commands.SetArea;
import com.example.myplugin.Commands.Start;
import com.example.myplugin.Listeners.MenuListener;
import com.example.myplugin.Listeners.MessageListener;

public class MyPlugin extends JavaPlugin {

  private static MyPlugin instance;

  public static MyPlugin getInstance() {
    return instance;
  }

  // TODO: ADD LISTENERS HERE
  private void AddEvents() {
    getServer().getPluginManager().registerEvents(
        new MenuListener(),
        this);
    getServer().getPluginManager().registerEvents(
        new MessageListener(),
        this);
  }

  @Override
  public void onEnable() {
    System.out.println("Kemistin Guess the build aktivoitu🧪");
    instance = this;
    saveDefaultConfig();
    AddEvents();
  }

  @Override
  public boolean onCommand(CommandSender sender, Command command,
      String label, String[] args) {

    SetArea setareaCommand = new SetArea();
    Aihe aiheCommand = new Aihe();
    Join joinCommand = new Join();
    Leave leaveCommand = new Leave();
    Start startCommand = new Start();
    End endCommand = new End();
    if (command.getName().equalsIgnoreCase("setarea")) {
      setareaCommand.onCommand(sender, args);
    }
    if (command.getName().equalsIgnoreCase("aihe")) {
      aiheCommand.onCommand(sender, args);
    }
    if (command.getName().equalsIgnoreCase("liity")) {
      joinCommand.onCommand(sender, args);
    }
    if (command.getName().equalsIgnoreCase("poistu")) {
      leaveCommand.onCommand(sender, args);
    }
    if (command.getName().equalsIgnoreCase("aloita")) {
      startCommand.onCommand(sender, args);
    }
    if (command.getName().equalsIgnoreCase("lopeta")) {
      endCommand.onCommand(sender, args);
    }

    return false;
  }
}
