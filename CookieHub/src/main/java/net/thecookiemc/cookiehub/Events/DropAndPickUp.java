package net.thecookiemc.cookiehub.Events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryPickupItemEvent;
import org.bukkit.event.player.PlayerDropItemEvent;

public class DropAndPickUp implements Listener {

  @EventHandler
  public void onDrop(PlayerDropItemEvent e) {
    e.setCancelled(true);
  }

  @EventHandler
  public void onPickup(InventoryPickupItemEvent e) {
    e.setCancelled(true);
  }
}
