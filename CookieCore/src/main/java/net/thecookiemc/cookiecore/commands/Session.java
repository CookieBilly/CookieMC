package net.thecookiemc.cookiecore.commands;

import com.rethinkdb.net.Cursor;
import net.thecookiemc.cookiecore.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.util.HashMap;

public class Session implements Listener, CommandExecutor {
  @SuppressWarnings("unchecked")
  @Override
  public boolean onCommand(CommandSender sender, Command cmd,
                           String label, String[] args) {

    if (sender instanceof Player) {
      if (cmd.getName().equalsIgnoreCase("session")) {

        if (sender.hasPermission("cookiecore.command.session")) {

          if (args.length != 0) {

            Cursor cursor = Main.r.db("InstanceManager").table("sessions")
                .filter(doc -> doc.g("username").match(args[0])).run(Main.conn);

            for (Object player : cursor) {

              // TODO - clickable
              sender.sendMessage("§8┃");
              sender.sendMessage(
                  "§8┃ §cAdmin §8┃ §7Session information for §e" + args[0] +
                      ":");
              sender.sendMessage("§8┃ §cAdmin §8┃ §7Server: §f" +
                  ((HashMap<String, String>) player).get("server"));
              sender.sendMessage("§8┃ §cAdmin §8┃ §7IP Address: §f" +
                  ((HashMap<String, String>) player).get("ip"));
              sender.sendMessage("§8┃ §cAdmin §8┃ §7UUID: §f" +
                  ((HashMap<String, String>) player).get("id"));
              //sender.sendMessage("§8┃ §cAdmin §8┃ §7Bungee: "+((HashMap<String, String>) player).get("proxy"));
              if (((HashMap<String, Boolean>) player).get("vanish") != null) {

                if (((HashMap<String, Boolean>) player).get("vanish")) {
                  sender.sendMessage("§8┃ §cAdmin §8┃ §7Vanish: §aTrue");
                } else {
                  sender.sendMessage("§8┃ §cAdmin §8┃ §7Vanish: §cFalse");
                }


              } else {
                sender.sendMessage("§8┃ §cAdmin §8┃ §7Vanish: §cFalse");
              }
              sender.sendMessage("§8┃ §cAdmin §8┃ §7Nickname: §f" +
                  ((HashMap<String, String>) player).get("nickname"));
              sender.sendMessage("§8┃");

              return true;

            }

            sender.sendMessage(
                "§8┃ §bStaff §8┃ §e" + args[0] + " §cis not currently online!");

            return true;

          } else {

            sender.sendMessage(
                "§8┃ §bStaff §8┃ §cInvalid usage! §7Use §e/" + cmd.getName() +
                    " [user]");

          }

        } else {
          sender.sendMessage(
              "§8┃ §c✖ §8┃ §cYou do not have permission for this!");
          return true;
        }

      }
    }
    return true;
  }
}
