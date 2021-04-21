package net.thecookiemc.cookiehub.Events;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;

public class ClickNPC implements Listener {


  @EventHandler
  public void onInteract(PlayerInteractAtEntityEvent e) {
    final Location BeaconBattlesLocation =
        new Location(e.getPlayer().getWorld(), -14, 51, 143);
    if (e.getRightClicked().getType() == EntityType.ZOMBIE
        && e.getRightClicked().getLocation().equals(BeaconBattlesLocation)
    ) {
      System.out.println(e.getPlayer().getName() + " opened the beacon " +
          "battles GUI");
    }
  }
}

