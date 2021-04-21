package net.thecookiemc.cookiehub.Events;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.cacheddata.CachedMetaData;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatEvent implements Listener {

  private final LuckPerms luckPerms;

  public ChatEvent(LuckPerms luckPerms) {
    this.luckPerms = luckPerms;
  }

  @EventHandler
  public void OnPlace(
      AsyncPlayerChatEvent e
  ) {
    e.setCancelled(true);


    // TODO - get colour of player

    Player player = e.getPlayer();

    ;        // Get a LuckPerms cached metadata for the player.
    CachedMetaData metaData =
        this.luckPerms.getPlayerAdapter(Player.class).getMetaData(player);

    // Get their prefix.
    String prefix = metaData.getPrefix();


    if (prefix != null) {


      prefix = ChatColor.translateAlternateColorCodes('&', prefix);

      String[] colour = prefix.split("");

      // chat format
      Bukkit.broadcastMessage("§8┃ " + prefix + " §8┃ " + "§" + colour[1] +
          e.getPlayer().getName() + " §8» §f" + e.getMessage());

    } else {

      // nons
      Bukkit.broadcastMessage(
          "§8┃ §7" + e.getPlayer().getName() + " §8» §f" + e.getMessage());
    }


  }
}
