package net.thecookiemc.cookiecore.commands;

import net.thecookiemc.cookiecore.Methods.Execute;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TestBan implements CommandExecutor {
  @Override
  public boolean onCommand(CommandSender sender, Command cmd,
                           String label, String[] args) {
    if (sender instanceof Player) {
      if (cmd.getName().equalsIgnoreCase("testban")) {
        if (sender.isOp()) {
          if (args.length <= 1) {
            sender.sendMessage("idot");
          } else {
            if (args[1].equalsIgnoreCase("CA")) {
              Execute.CompromisedBan(Bukkit.getPlayer(args[0]));
            } else {
              Execute.Ban((Player) sender, "Hack!", false, "1d");
            }
          }
        }
      }
    }
    return true;
  }
}
