package net.thecookiemc.cookiecore.Events.Join;

import net.thecookiemc.cookiecore.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;

import java.util.Collections;

public class Scoreboard implements Listener {

  private final int MAX_CHARS = 24;


  private String serializeToScoreboard(String string) {
    int spacesToAdd = (MAX_CHARS - string.length()) / 2;
    String spaces = String.join("", Collections.nCopies(spacesToAdd, " "));
    return spaces + string + spaces;
  }


  public void setScoreBoard(Player player) {
    final Player p = player;

    final org.bukkit.scoreboard.Scoreboard board =
        Bukkit.getScoreboardManager().getNewScoreboard();
    final Objective objective =
        board.registerNewObjective("test", "dummy");
    objective.setDisplaySlot(DisplaySlot.SIDEBAR);
    objective.setDisplayName(serializeToScoreboard("§e§lThe§6§lCookie§e" +
        "§lMC"));
    Score score =
        objective.getScore(serializeToScoreboard("§7[" + Main.getServerID() +
            "§7]"));
    score.setScore(10);
    Score score1 = objective.getScore("§1");
    score1.setScore(9);
    Score score2 = objective.getScore("§2");
    score2.setScore(8);
    Score score3 = objective.getScore("§3");
    score3.setScore(7);
    Score score4 = objective.getScore("§4");
    score4.setScore(6);
    Score score5 = objective.getScore("§5");
    score5.setScore(5);
    Score score6 = objective.getScore("§6");
    score6.setScore(4);
    Score score7 = objective.getScore("§7");
    score7.setScore(3);
    Score score8 = objective.getScore("§8");
    score8.setScore(2);
    Score score9 = objective.getScore(serializeToScoreboard(
        "§ethecookiemc.net"));
    score9.setScore(1);

    Main.getInstance().getServer().getScheduler()
        .runTaskLater(Main.getInstance(),
            () -> p.setScoreboard(board), 1);

  }
//  @EventHandler
//  public void PlayerJoin(PlayerJoinEvent e) {
//
//    // Checks if the server is desynchronized with the database
//    if (Main.SERVERID.equals("Development")) {
//      e.getPlayer().sendMessage("§8┃");
//      e.getPlayer().sendMessage(
//          "§8┃ §c✖ §8┃ §cStatistics on this server are disabled §7[Dev server]");
//      e.getPlayer().sendMessage(
//          "§8┃ §c✖ §8┃ §7If this is a mistake, please report instantly!");
//      e.getPlayer().sendMessage("§8┃");
//    }
//
//    // Creates the player a scoreboard
//    final Player p = e.getPlayer();
//
//    final org.bukkit.scoreboard.Scoreboard board =
//        Bukkit.getScoreboardManager().getNewScoreboard();
//    final Objective objective =
//        board.registerNewObjective("test", "dummy");
//    objective.setDisplaySlot(DisplaySlot.SIDEBAR);
//    objective.setDisplayName(serializeToScoreboard("§e§lThe§6§lCookie§e" +
//        "§lMC"));
//    Score score =
//        objective.getScore(serializeToScoreboard("§7[" + Main.getServerID() +
//            "§7]"));
//    score.setScore(10);
//    Score score1 = objective.getScore("§1");
//    score1.setScore(9);
//    Score score2 = objective.getScore("§2");
//    score2.setScore(8);
//    Score score3 = objective.getScore("§3");
//    score3.setScore(7);
//    Score score4 = objective.getScore("§4");
//    score4.setScore(6);
//    Score score5 = objective.getScore("§5");
//    score5.setScore(5);
//    Score score6 = objective.getScore("§6");
//    score6.setScore(4);
//    Score score7 = objective.getScore("§7");
//    score7.setScore(3);
//    Score score8 = objective.getScore("§8");
//    score8.setScore(2);
//    Score score9 = objective.getScore(serializeToScoreboard(
//        "§ethecookiemc.net"));
//    score9.setScore(1);
//
//
//    // Creates the player a bossbar
//
//    Main.getInstance().getServer().getScheduler()
//        .runTaskLater(Main.getInstance(),
//            () -> p.setScoreboard(board), 1);
//
//
//  }
}
