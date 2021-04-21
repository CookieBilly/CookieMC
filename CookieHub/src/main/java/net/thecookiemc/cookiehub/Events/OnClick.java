package net.thecookiemc.cookiehub.Events;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class OnClick implements Listener {

  protected static Location BEACON_BATTLE;
  protected static Location TRAPPED;
  protected static Location NETHER_RAID;
  protected static Location SPAWN;

  @EventHandler
  public void onClick(InventoryClickEvent e) {
    Player p = (Player) e.getWhoClicked();
    e.setCancelled(true);
    if (e.getInventory().getName().equals("§dGame Selector")) {


      BEACON_BATTLE = new Location(e.getWhoClicked().getWorld(),
              -14.5, 51, 131.5);
      TRAPPED = new Location(e.getWhoClicked().getWorld(), -113.5,
              54, 107.5);
      NETHER_RAID = new Location(e.getWhoClicked().getWorld(),
              -174.5, 52, 22.5);
      SPAWN = new Location(e.getWhoClicked().getWorld(), 0.5,
              64, 0.5);


      if (e.getSlot() == 10) {
        e.getWhoClicked().teleport(BEACON_BATTLE);
        e.getView().close();
      } else if (e.getSlot() == 12) {
        e.getWhoClicked().teleport(TRAPPED);
        e.getView().close();
      } else if (e.getSlot() == 14) {
        e.getWhoClicked().teleport(NETHER_RAID);
        e.getView().close();
      } else if (e.getSlot() == 16) {
        e.getWhoClicked().teleport(SPAWN);
        e.getView().close();
      }
    } else if (e.getInventory().getName().equals("§bSettings & Stats")) {
      e.setCancelled(true);
      if (e.getSlot() == 10) {
        // INSERT GAME SETTINGS CODE
        e.getView().close();
      } else if (e.getSlot() == 12) {
        // INSERT PLAYER STATS CODE
        e.getView().close();
      } else if (e.getSlot() == 14) {
        // INSERT ACHIEVEMENTS CODE
        e.getView().close();
      } else if (e.getSlot() == 16) {
        // INSERT PLAYER PRIVACY SETTINGS CODE
        e.getView().close();
      }
    } else if (e.getInventory().getName().equals("§eLobby Jumper")) {
      e.setCancelled(true);
      int lobbyGoingTo = 0;
      if (e.getCurrentItem().getType() != Material.STAINED_GLASS_PANE) {
        ((Player) e.getWhoClicked()).performCommand("swap " + e.getCurrentItem().getAmount());
      }
//      if (e.getSlot() >= 16 && e.getSlot() <= 10) {
//        lobbyGoingTo = e.getSlot() - 9;
//        e.getCurrentItem().
//      } else if (e.getSlot() >= 19 && e.getSlot() <= 25) {
//        lobbyGoingTo = e.getSlot() - 11;
//      } else if (e.getSlot() >= 28 && e.getSlot() <= 34) {
//        lobbyGoingTo = e.getSlot() - 13;
//      }
//      Bukkit.broadcastMessage("swap" + String.valueOf(lobbyGoingTo));
//      ((Player) e.getWhoClicked()).performCommand("swap " + String.valueOf(lobbyGoingTo));
//    }
    }
  }
}
