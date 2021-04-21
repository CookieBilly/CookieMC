package net.thecookiemc.cookiegamecore.teams;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;


public class CreateTeamsCommand implements CommandExecutor {
  @Override
  public boolean onCommand(@NotNull CommandSender sender,
                           @NotNull Command cmd, @NotNull String label,
                           @NotNull String[] args) {
    if (sender instanceof Player) {
      if (cmd.getName().equalsIgnoreCase("createteams")) {
        if (sender.isOp()) {
          Bukkit.broadcastMessage(String.valueOf(CreateTeams.splitRandomly(
              CreateTeams.convertToList(Bukkit.getServer().getOnlinePlayers()),
              4)));
        } else {
          sender.sendMessage("Unknown command.");
        }
      }
    }
    return true;
  }
}
