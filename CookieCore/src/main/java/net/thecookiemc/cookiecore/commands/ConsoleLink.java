package net.thecookiemc.cookiecore.commands;

import net.thecookiemc.cookiecore.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public class ConsoleLink implements Listener, CommandExecutor {
  @Override
  public boolean onCommand(CommandSender sender, Command cmd,
                           String label, String[] args) {

    if (sender instanceof Player) {
      if (cmd.getName().equalsIgnoreCase("console")) {
        if (sender.hasPermission("cookiecore.command.console")) {

          if (!Main.SERVERID.equals("Development")) {

            sender.sendMessage(
                "§8┃ §cAdmin §8┃ §bhttps://dev.thecookiemc.net/server/" +
                    Main.SERVERID);
          } else {
            sender.sendMessage(
                "§8┃ §c✖ §8┃ §cThis command only works on live servers");
          }

        } else {
          sender.sendMessage(
              "§8┃ §c✖ §8┃ §cYou do not have permission for this!");
        }
        return true;
      }
    }
    return true;
  }
}
