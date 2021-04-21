package net.thecookiemc.cookiehub.announce;

import com.rethinkdb.RethinkDB;
import com.rethinkdb.net.Connection;
import com.rethinkdb.net.Cursor;
import net.thecookiemc.hidden.obfuscated.Returns;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

import java.util.HashMap;
import java.util.Random;

public class Announcer implements Listener {

  public static final RethinkDB r = RethinkDB.r;
  private static int currentannounce = 0;

  public static Cursor fetchannouncement() {

    Connection conn =
        r.connection().hostname(Returns.getDataBaseIP())
            .port(Returns.getDataBasePort())
            .user(Returns.getDataBaseUsername(), Returns.getDataBasePassword())
            .connect();

    // TODO - Sort by newest
    Cursor cursor = r.db("InstanceManager").table("announcements").orderBy()
        .optArg("index", r.desc("id")).filter(doc -> doc.g("ingame").eq(true))
        .limit(6).run(conn);
    conn.close();

    return cursor;

  }

  public static void Announce() {


    Cursor cursor = fetchannouncement();

    int currentloops = 0;

    for (Object doc : cursor) {

      if (currentannounce == currentloops) {

        Bukkit.broadcastMessage("§8┃");
        String[] colours = {"a", "b", "c", "d", "e", "3", "6", "9"};
        int rnd = new Random().nextInt(colours.length);
        String colourcode = colours[rnd];

        Bukkit.broadcastMessage("§8┃ §" + colourcode + "§l" +
            ((HashMap<String, String>) doc).get("title"));
        // loop through description, max 5 lines /
        String[] splitted =
            ((HashMap<String, String>) doc).get("description").split(" ");

        int line = 0;
        StringBuilder text = new StringBuilder("§8┃ §7");
        int size = 0;
        String currentline = "§8┃ §7";

        for (String word : splitted) {

          if (line < 5) {

            if (size <= 50) {

              text.append(word).append(" ");
              currentline = currentline + word + " ";
              size = currentline.length();
            } else {
              currentline = "§8┃ §7";
              size = 0;
              line++;
              if (line < 5) {
                text.append("\n§8┃ §7").append(word).append(" ");
              }
            }
          }
        }

        Bukkit.broadcastMessage(text.toString());
        Bukkit.broadcastMessage("§8┃");
        Bukkit.broadcastMessage("§8┃ §6➤ §eClick to read more information!");
        Bukkit.broadcastMessage("§8┃");


      }
      currentloops++;


    }

    if (currentannounce == currentloops) {
      currentannounce = 0;

    }

    currentannounce++;

  }

}
