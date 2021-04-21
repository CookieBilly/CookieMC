package net.thecookiemc.cookiecore.Methods;

import org.bukkit.entity.Player;

public class Execute {

  public static void Ban(Player punished, String reason, boolean Permanent,
                         String time) {
    String topLine;
    if (Permanent || time.equalsIgnoreCase("perm")) {
      topLine = "§cYou have been banned from TheCookieMC network " +
          "indefinitely!";
    } else {
      topLine = "§cYou have been banned from TheCookieMC network!";
    }
    punished.kickPlayer(topLine + "\n \n§7Reason: §f" + reason + "\n §7Ban " +
        "Code: §fEHSDFBEEEE\n \n§cIf you would like to appeal this " +
        "punishment, please create an appeal @ §b§nappeals.thecookiemc.net");
  }

  public static void CompromisedBan(Player compromisedPlayer) {
    compromisedPlayer.kickPlayer("§cYour account has been blocked from " +
        "TheCookieMC!\n \n§fWe think your account may have been compromised " +
        "therefore you are not allowed to play on our network at this time!\n" +
        " \n§cPlease change your password and create an appeal @ §b§nappeals" +
        ".thecookiemc.net");
  }
}