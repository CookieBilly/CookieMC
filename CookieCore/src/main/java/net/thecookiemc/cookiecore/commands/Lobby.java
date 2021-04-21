package net.thecookiemc.cookiecore.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public class Lobby implements Listener, CommandExecutor {
  @Override
  public boolean onCommand(CommandSender sender, Command cmd,
                           String label, String[] args) {

    if (sender instanceof Player) {
      if (cmd.getName().equalsIgnoreCase("lobby")) {
        ((Player) sender).performCommand("queue lobby");
        System.out.println("ee");
        return true;
      }
    }
    return true;
  }
}