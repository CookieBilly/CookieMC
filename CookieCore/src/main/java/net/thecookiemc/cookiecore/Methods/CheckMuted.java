package net.thecookiemc.cookiecore.Methods;

import net.thecookiemc.cookiecore.Main;
import org.bukkit.Bukkit;

public class CheckMuted {

  public static boolean CheckPlayerMuted(String playerUUID) {
    long CheckMuteCount =
        Main.r.db("Punishments").table("punishments")
            .filter(row -> row.g(
                "UUID").eq(playerUUID)).count().run(Main.conn);

    if (CheckMuteCount >= 1) {
      long getAllEntries =
          Main.r.db("Punishments").table("punishments")
              .filter(row -> row.g(
                  "duration").eq(playerUUID)).count().run(Main.conn);

      if (getAllEntries > 1) {
        Bukkit.broadcastMessage("lol you have more than 1 entry; TODO - make " +
            "this work");
      } else {
        return true;
//        long expiryTime = Main.r.db("Punishments").table("punishments")
//            .filter(row -> row.g(
//                "duration").eq(playerUUID)).run(Main.conn);
//        if ((System.currentTimeMillis() - expiryTime) > 0) {
//          return true;
      }
    } else {
      return false;
    }
    return false;
  }
}

