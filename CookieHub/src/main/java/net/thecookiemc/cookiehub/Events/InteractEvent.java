package net.thecookiemc.cookiehub.Events;

import net.thecookiemc.cookiehub.Main;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InteractEvent implements Listener {

  private static final Map<String, Vector> PLAYERVECTOR = new HashMap<>();
  private static final Map<String, String> PLAYERANDISLANDTOTP =
      new HashMap<>();
  public static Map<String, String> PLAYERANDISLAND = new HashMap<>();
  public static List<String> isOnPearl = new ArrayList<>();

  @EventHandler
  public void onClick(PlayerInteractAtEntityEvent e) {

    if (e.getRightClicked().getType() == EntityType.ITEM_FRAME) {
      e.setCancelled(true);
    }


    if (e.getRightClicked().getType() == EntityType.ARMOR_STAND) {
      e.setCancelled(true);

      isOnPearl.add(e.getPlayer().getName());
      if (e.getRightClicked().getMetadata(
          "WhichNPC").get(0).value().equals("Trapped")) {
        PLAYERVECTOR.put(e.getPlayer().getName(), new Vector(-113.5, 56,
            107.5));
        PLAYERANDISLAND.put(e.getPlayer().getName(), "Trapped");
        PLAYERANDISLANDTOTP.put(e.getPlayer().getName(), "TRAPPED");

      } else if (e.getRightClicked().getMetadata(
          "WhichNPC").get(0).value().equals("Nether Raid")) {
        PLAYERVECTOR.put(e.getPlayer().getName(), new Vector(-175, 52, 22));
        // -150 51 23
        PLAYERVECTOR.put(e.getPlayer().getName(), new Vector(-170, 85, 22));
        PLAYERANDISLAND.put(e.getPlayer().getName(), "Nether Raid");
        PLAYERANDISLANDTOTP.put(e.getPlayer().getName(), "NETHER_RAID");

      } else if (e.getRightClicked().getMetadata(
          "WhichNPC").get(0).value().equals("Beacon Battle")) {
        PLAYERVECTOR.put(e.getPlayer().getName(), new Vector(-14, 51, 131));
        PLAYERANDISLAND.put(e.getPlayer().getName(), "Beacon Battle");
        PLAYERANDISLANDTOTP.put(e.getPlayer().getName(), "BEACON_BATTLE");

      } else {
        e.getPlayer().sendMessage(ChatColor.RED + "Something isn't right... " +
            "Please try again or report this as a bug urgently!");
      }


//      ComponentBuilder message =
//          new ComponentBuilder("§8┃ §b§lClick to instantly teleport");
//      BaseComponent[] instantTeleport =
//          message.event(new ClickEvent(e.getPlayer().teleport(OnClick.BEACON_BATTLE))).create();

      final Player p = e.getPlayer();
      p.sendMessage("§8┃");
      p.sendMessage("§8┃ §d§lFlying over to the §6" +
          PLAYERANDISLAND.get(e.getPlayer().getName()) +
          " " +
          "§d§lisland.." +
          ".");
      p.sendMessage(
          "§8┃ §7We hope you enjoy the view from up here, it's amazing!");
      p.sendMessage(
          "§8┃ §7Once you've played a game, you will automatically spawn");
      p.sendMessage(
          "§8┃ §7back at this island, to save you some time next time!");
      p.sendMessage("§8┃");
//       p.spigot().sendMessage(instantTeleport);
      p.sendMessage("§8┃");
      final Location loc = new Location(e.getPlayer().getWorld(), -74.5, 50,
          61.5);


      // kill me - cookietom


      Entity enderPearl = e.getPlayer()
          .launchProjectile(EnderPearl.class,
              e.getPlayer().getLocation().getDirection());
      enderPearl.setPassenger(p);

      Main.getInstance().getServer().getScheduler().runTaskLater(
          Main.getInstance(), () -> {


            if (PLAYERANDISLAND.get(e.getPlayer().getName())
                .equals("Trapped")) {
              Vector from = enderPearl.getLocation().toVector();
              Vector velocity =
                  PLAYERVECTOR.get(e.getPlayer().getName()).subtract(from)
                      .setY(20)
                      .normalize().multiply(1.5);
              enderPearl.setVelocity(velocity);
            } else {
              Vector from = enderPearl.getLocation().toVector();
              Vector velocity =
                  PLAYERVECTOR.get(e.getPlayer().getName()).subtract(from)
                      .setY(10)
                      .normalize().multiply(1.5);
              enderPearl.setVelocity(velocity);
            }
          }, 1);

      Main.getInstance().getServer().getScheduler().runTaskLater(
          Main.getInstance(), () -> isOnPearl.remove(e.getPlayer().getName()),
          20);

    }
  }
}
