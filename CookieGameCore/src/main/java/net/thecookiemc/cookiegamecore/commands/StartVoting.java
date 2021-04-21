package net.thecookiemc.cookiegamecore.commands;

import net.thecookiemc.cookiegamecore.Main;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class StartVoting implements CommandExecutor {

  public static boolean isVoting = false;

  public int randomSelection() {
    List<Integer> givenList = Arrays.asList(1, 2, 3);
    Random rand = new Random();
    return givenList.get(rand.nextInt(givenList.size()));
  }


  public static double getRandomIntegerBetweenRange(int min, int max){
    return (int)(Math.random()*((max-min)+1))+min;
  }


  public static <T> T mostCommon(List<T> list) {
    Map<T, Integer> map = new HashMap<>();

    for (T t : list) {
      Integer val = map.get(t);
      map.put(t, val == null ? 1 : val + 1);
    }

    Map.Entry<T, Integer> max = null;

    for (Map.Entry<T, Integer> e : map.entrySet()) {
      if (max == null || e.getValue() > max.getValue())
        max = e;
    }

    assert max != null;
    return max.getKey();
  }

  @Override
  public boolean onCommand(@NotNull CommandSender sender,
                           @NotNull Command cmd, @NotNull String label,
                           @NotNull String[] args) {
    if (cmd.getName().equalsIgnoreCase("startvoting")) {
      if (sender.isOp()) {
        // alr
        Bukkit.broadcastMessage("§8┃ §b✦ §8┃ §7Map voting has started. Feel " +
            "free to use /vote!");
        Vote.HASVOTED.clear();
        isVoting = true;
        Main.getInstance().getServer().getScheduler().runTaskLater(
            Main.getInstance(), () -> {
              String winningMap;
              if (Vote.ALLVOTES.isEmpty()) {
                Bukkit.broadcastMessage("§8┃ §b✦ §8┃ §7No one voted! :( " +
                    "Random map selection coming up!");
                winningMap = String.valueOf(randomSelection());
              } else {
                winningMap = mostCommon(Vote.ALLVOTES);
              }
              Bukkit.broadcastMessage("§8┃ §b✦ §8┃ §7Map voting ended. " +
                  "Map number " + winningMap + " won the vote!");
              Vote.ALLVOTES.clear();
              Vote.HASVOTED.clear();
              isVoting = false;
            }, 600);
      } else {
        sender.sendMessage("§8┃ §b✦ §8┃ §7Sorry! You cannot use this command!");
      }
    }
    return true;
  }
}
