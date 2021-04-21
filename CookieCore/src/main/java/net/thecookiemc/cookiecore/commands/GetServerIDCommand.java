package net.thecookiemc.cookiecore.commands;

import net.thecookiemc.cookiecore.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public class GetServerIDCommand implements Listener, CommandExecutor {
  @Override
  public boolean onCommand(CommandSender sender, Command cmd,
                           String label, String[] args) {

    if (sender instanceof Player) {
      if (cmd.getName().equalsIgnoreCase("findme")) {
        sender.sendMessage("§8┃ §dJumper §8┃ §7You are currently connected to §f" +
            Main.SERVERID);
      }
    }
    return true;
  }
}
