package net.thecookiemc.cookiecore.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;


public class Nothing implements Listener, CommandExecutor {
  public boolean onCommand(final CommandSender sender, final Command command,
                           final String label, final String[] args) {

    Player p = (Player) sender;
    p.sendMessage("§8┃ §3This command does nothing...");
    //sender.sendMessage(Ban.createBanID());
    return true;
  }
}