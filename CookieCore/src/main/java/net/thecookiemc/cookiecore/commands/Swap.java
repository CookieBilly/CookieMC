package net.thecookiemc.cookiecore.commands;

import com.rethinkdb.net.Cursor;
import net.thecookiemc.cookiecore.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;


public class Swap implements Listener, CommandExecutor {

  public boolean onCommand(final CommandSender sender, final Command command,
                           final String label, final String[] args) {

    if (sender instanceof Player) {
      if (command.getName().equalsIgnoreCase("swap") || command.getName().equalsIgnoreCase("server")) {

        Player p = (Player) sender;

        if (args.length == 0) {
          p.sendMessage(
              "§8┃ §dJumper §8┃ §cInvalid usage! §7Use §e/swap [lobby]");
        } else {

          // fetch lobby instance id

          // do bungee send
          final ByteArrayOutputStream b = new ByteArrayOutputStream();
          final DataOutputStream out = new DataOutputStream(b);
          try {
            out.writeUTF("Connect");

            Cursor cursor = Main.FetchServers();
            int lobbycount = 0;
            String targetserver = "null";
            for (Object doc : cursor) {
              if (((HashMap<String, String>) doc).get("game").equals("lobby")) {
                if (((HashMap<String, Boolean>) doc).get("joinable")
                    .equals(true)) {
                  lobbycount++;
                  if (lobbycount == Integer.parseInt(args[0])) {
                    targetserver = ((HashMap<String, String>) doc).get("id");
                  }
                }
              }
            }


            if (!targetserver.equals("null")) {

              if(!targetserver.equalsIgnoreCase(Main.SERVERID)) {
                p.sendMessage(
                        "§8┃ §dJumper §8┃ §7Trying to send you to §fLobby " + args[0] +
                                "§7!");

                // send to lobby instance id
                out.writeUTF(targetserver);
              } else {
                p.sendMessage(
                        "§8┃ §dJumper §8┃ §7You are already connected to §fLobby " + args[0]);
                return true;
              }


            } else {
              p.sendMessage(
                  "§8┃ §dJumper §8┃ §cThis lobby cannot be found, try again in a minute! :(");
              return true;
            }
          } catch (IOException | NumberFormatException ignored){}
          p.sendPluginMessage(Main.getInstance(), "BungeeCord",
              b.toByteArray());
        }
        return true;
      }
      return true;
    } else {
      sender.sendMessage("This command cannot be run in console");
    }
    return true;
  }
}