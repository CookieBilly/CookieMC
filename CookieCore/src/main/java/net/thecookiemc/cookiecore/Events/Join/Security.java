package net.thecookiemc.cookiecore.Events.Join;

import com.rethinkdb.net.Cursor;
import net.thecookiemc.cookiecore.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

import java.util.HashMap;

public class Security implements Listener {

  @SuppressWarnings("unchecked")
  @EventHandler
  public void Security(AsyncPlayerPreLoginEvent e) {

    Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> {
      Cursor cursor = Main.r.db("InstanceManager").table("sessions")
          .filter(doc -> doc.g("id").match(e.getUniqueId().toString()))
          .run(Main.conn);

      for (Object player : cursor) {

        String compare1 = ((HashMap<String, String>) player).get("ip");
        String compare2 =
            e.getAddress().toString().replace("/", "").split(":")[0];

        if (compare1.equals(compare2)) {
          Main.r.db("InstanceManager").table("sessions")
              .filter(doc -> doc.g("id").match(e.getUniqueId().toString()))
              .update(Main.r.hashMap("server", Main.SERVERID)).run(Main.conn);
          return;
        }

      }

      if (!Main.SERVERID.equals("Development")) {

        Player p = Bukkit.getPlayer(e.getUniqueId());
        p.kickPlayer("§cUnable to validate your bungee session");

      }

    }, 5);

    // Main.getInstance().getServer().getScheduler()


  }
}


