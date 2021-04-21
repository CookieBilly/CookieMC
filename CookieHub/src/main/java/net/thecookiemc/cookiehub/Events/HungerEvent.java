package net.thecookiemc.cookiehub.Events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class HungerEvent implements Listener {

  @EventHandler
  public void onHunger(FoodLevelChangeEvent e) {
    e.setCancelled(true);
  }

}
