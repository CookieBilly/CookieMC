package net.thecookiemc.cookiehub.Events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.block.BlockSpreadEvent;

public class BlockGravity implements Listener {
  @EventHandler
  public void BlockChange1(
      BlockFromToEvent e
  ) {
    e.setCancelled(true);
  }

  @EventHandler
  public void BlockChange2(
      BlockSpreadEvent e
  ) {
    e.setCancelled(true);
  }

}
