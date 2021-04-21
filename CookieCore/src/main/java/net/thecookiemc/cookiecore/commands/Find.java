package net.thecookiemc.cookiecore.commands;

import com.rethinkdb.net.Cursor;
import net.thecookiemc.cookiecore.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.util.HashMap;

public class Find implements Listener, CommandExecutor {

  @SuppressWarnings("unchecked")
  @Override
  public boolean onCommand(CommandSender sender, Command cmd,
                           String label, String[] args) {

    if (sender instanceof Player) {
      if (cmd.getName().equalsIgnoreCase("find")) {

        if (sender.hasPermission("cookiecore.command.find")) {

          if (args.length != 0) {

            Cursor cursor = Main.r.db("InstanceManager").table("sessions")
                .filter(doc -> doc.g("username").match(args[0])).run(Main.conn);

            for (Object player : cursor) {

              // TODO - clickable
              sender.sendMessage(
                  "§8┃ §bStaff §8┃ §e" + args[0] + " §7is on server §e" +
                      ((HashMap<String, String>) player).get("server") +
                      "§b (Teleport)");
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
