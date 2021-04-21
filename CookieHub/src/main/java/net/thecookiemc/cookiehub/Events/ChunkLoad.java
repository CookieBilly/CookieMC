package net.thecookiemc.cookiehub.Events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class ChunkLoad implements Listener {

  @EventHandler
  public void onMove(PlayerMoveEvent e) {

    if (e.getFrom().distance(e.getTo()) > 20) {
      e.getPlayer().kickPlayer("You are sending too many packets!");
      System.out.println(e.getPlayer().getName() + " (" + e.getPlayer().getUniqueId() + ") attempted to crash the server!");
    }
  }

}
