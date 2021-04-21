package net.thecookiemc.cookiegamecore.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import static net.thecookiemc.cookiegamecore.commands.StartVoting.isVoting;

public class Vote implements Listener, CommandExecutor {

  public static final List<String> ALLVOTES = new ArrayList<>();
  public static final List<String> HASVOTED = new ArrayList<>();

  @Override
  public boolean onCommand(@NotNull CommandSender sender,
                           @NotNull Command cmd, @NotNull String label,
                           @NotNull String[] args) {
    if (sender instanceof Player) {
      if (cmd.getName().equalsIgnoreCase("vote")) {
        if (!isVoting) {
          sender.sendMessage("§8┃ §b✦ §8┃ §7Sorry, but you cannot vote right " +
              "now.");
        } else {
          if (args.length == 0) {
            sender.sendMessage("§8┃ §b✦ §8┃ §7That's not a valid map number! " +
                "Please select: 1, 2 or 3.");
          } else if (args.length >= 2) {
            sender
                .sendMessage("§8┃ §b✦ §8┃ §7You can only vote for one map at " +
                    "once" +
                    "." +
                    "...");
          } else {
            if (!HASVOTED.contains(sender.getName())) {
              if (args[0].equals("1") || args[0].equals("2") ||
                  args[0].equals("3")) {

                for (Player all : Bukkit.getServer().getOnlinePlayers()) {
                  if (all.getName().equals(sender.getName())) {
                    sender.sendMessage("§8┃ §b✦ §8┃ §7You successfully voted for" +
                        " map number " + args[0]);
                  } else {
                    all.sendMessage("§8┃ §b✦ §8┃ §7" + sender.getName() +
                        " voted " +
                        "for" +
                        " map number " + args[0]);
                  }
                }






//                sender.sendMessage("§8┃ §b✦ §8┃ §7You successfully voted for" +
//                    " map number " + args[0]);
                HASVOTED.add(sender.getName());
                switch (args[0]) {
                  case "1":
                    ALLVOTES.add("1");
                  case "2":
                    ALLVOTES.add("2");
                  case "3":
                    ALLVOTES.add("3");
                }
              }
            } else {
              sender.sendMessage("§8┃ §b✦ §8┃ §7You've already voted! You can" +
                  " only vote once.");
            }
          }
        }
      }
    } else {
      sender.sendMessage("You can't use that here!");
    }
    return true;
  }
}
