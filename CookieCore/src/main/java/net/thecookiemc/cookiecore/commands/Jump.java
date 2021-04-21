package net.thecookiemc.cookiecore.commands;

import net.thecookiemc.cookiecore.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;


public class Jump implements Listener, CommandExecutor {

  // eeeeee
  public boolean onCommand(final CommandSender sender, final Command command,
                           final String label, final String[] args) {

    if (sender instanceof Player) {
      if (command.getName().equalsIgnoreCase("jump")) {

        Player p = (Player) sender;


        if (args.length == 0) {
          p.sendMessage(
              "§8┃ §dJumper §8┃ §cInvalid usage! §7Use §e/jump [server]");
        } else {

          if (args[0].equalsIgnoreCase(Main.SERVERID)) {
            p.sendMessage(
                    "§8┃ §dJumper §8┃ §7You are already connected to §f" + args[0]);
            return true;
          }


          p.sendMessage(
              "§8┃ §dJumper §8┃ §7Trying to send you to §f" + args[0] +
                  "§7!");
          final ByteArrayOutputStream b = new ByteArrayOutputStream();
          final DataOutputStream out = new DataOutputStream(b);
          try {
            out.writeUTF("Connect");
            out.writeUTF(args[0]);
          } catch (IOException ignored) {
          }
          p.sendPluginMessage(Main.getInstance(), "BungeeCord",
              b.toByteArray());
        }
        return true;
      }
    } else {
      sender.sendMessage("This command cannot be run in console");
    }
    return true;
  }
}