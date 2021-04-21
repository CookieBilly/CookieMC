package net.thecookiemc.cookiehub.Events;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class DamageEvent implements Listener {

  @EventHandler
  public void OnDamage(
      EntityDamageEvent e
  ) {
    e.setCancelled(true);
    if (e.getEntityType() == EntityType.PLAYER) {
      if (e.getEntity().getLocation().getY() <= 0) {

        // 0.5, 64, 0.5
        Location loc = new Location(Bukkit.getWorld("Lobby"), 1, 64, 1, 0, 0);
        e.getEntity().teleport(loc);
      }
    }
  }

}
