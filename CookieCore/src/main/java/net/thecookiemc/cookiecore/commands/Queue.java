package net.thecookiemc.cookiecore.commands;

import com.rethinkdb.net.Cursor;
import net.thecookiemc.cookiecore.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.util.HashMap;

public class Queue implements Listener, CommandExecutor {

  @SuppressWarnings("unchecked")
  @Override
  public boolean onCommand(CommandSender sender, Command cmd,
                           String label, String[] args) {

    if (sender instanceof Player) {


      if (cmd.getName().equalsIgnoreCase("queue")) {

        String serverid = null;

        if (args.length != 0) {

          if (args[0].equalsIgnoreCase("lobby")) {

            Cursor cursor = Main.FetchSmallLobby();

            for (Object server : cursor) {

              if (((HashMap<String, Boolean>) server).get("joinable")) {
                serverid = ((HashMap<String, String>) server).get("id");
              }

            }

            if (serverid != null) {
              ((Player) sender).performCommand("jump " + serverid);
              return true;
            } else {
              sender.sendMessage(
                  "§8┃ §dJumper §8┃ §cThere are no available Lobby servers!");
              return true;
            }

          }

          if (args[0].equalsIgnoreCase("beacon_battle")) {

            // insert queue code here

          }

        } else {

          sender.sendMessage(
              "§8┃ §dJumper §8┃ §cInvalid usage! §7Use §e/" + cmd.getName() +
                  " [game]");
          return true;
        }

      }
    }
    return true;
  }
}
