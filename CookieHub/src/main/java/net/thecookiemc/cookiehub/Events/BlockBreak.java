package net.thecookiemc.cookiehub.Events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BlockBreak implements Listener {

  @EventHandler
  public void OnBreak(
      BlockBreakEvent e
  ) {
    e.setCancelled(true);
  }
}
