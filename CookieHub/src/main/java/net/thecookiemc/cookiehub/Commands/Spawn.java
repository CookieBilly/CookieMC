package net.thecookiemc.cookiehub.Commands;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public class Spawn implements Listener, CommandExecutor {
  @Override
  public boolean onCommand(CommandSender sender, Command cmd,
                           String label, String[] args) {
    if (sender instanceof Player) {
      if (cmd.getName().equalsIgnoreCase("spawn")) {
        final Location BEACON_BATTLE =
            new Location(((Player) sender).getWorld(),
                -14.5, 51, 131.5);
        final Location TRAPPED =
            new Location(((Player) sender).getWorld(), -113.5,
                54, 107.5);
        final Location NETHER_RAID = new Location(((Player) sender).getWorld(),
            -174.5, 52, 22.5);
        final Location SPAWN = new Location(((Player) sender).getWorld(), 0.5,
            64, 0.5);

        if (args.length == 0) {
          ((Player) sender).teleport(SPAWN);
        } else {
          if (args[0].equalsIgnoreCase("nether_raid")) {
            ((Player) sender).teleport(NETHER_RAID);
          } else if (args[0].equalsIgnoreCase("trapped")) {
            ((Player) sender).teleport(TRAPPED);
          } else if (args[0].equalsIgnoreCase("beacon_battle")) {
            ((Player) sender).teleport(BEACON_BATTLE);
          } else if (args[0].equalsIgnoreCase("spawn")) {
            ((Player) sender).teleport(SPAWN);
          } else {
            sender.sendMessage("§8┃ §cThe spawnpoint given cannot be found.");
          }
        }
      }
    }
    return true;
  }
}