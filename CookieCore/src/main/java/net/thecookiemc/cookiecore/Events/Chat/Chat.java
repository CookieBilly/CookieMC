package net.thecookiemc.cookiecore.Events.Chat;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class Chat implements Listener {

  @EventHandler
  public void onChat(AsyncPlayerChatEvent e) {
    if (MuteList.isMuted.contains(e.getPlayer())) {
      e.setCancelled(true);
      e.getPlayer().sendMessage("shush be silenced my muted non");
    }
  }
}
