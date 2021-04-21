package net.thecookiemc.cookiecore.Events.Chat;

import net.thecookiemc.cookiecore.Methods.CheckMuted;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.ArrayList;
import java.util.List;

public class MuteList implements Listener {

  public static List<Player> isMuted = new ArrayList<>();

  @EventHandler
  public void onLogin(PlayerLoginEvent e) {
    if (CheckMuted.CheckPlayerMuted(e.getPlayer().getUniqueId().toString())) {
      isMuted.add(e.getPlayer());
    }
  }

  @EventHandler
  public void onLeave(PlayerQuitEvent e) {
    isMuted.remove(e.getPlayer());
  }
}
