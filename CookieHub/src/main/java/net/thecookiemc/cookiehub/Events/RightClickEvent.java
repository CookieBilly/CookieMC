package net.thecookiemc.cookiehub.Events;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class RightClickEvent implements Listener {

  @EventHandler
  public void onInteract(PlayerInteractEvent e) {


      if (e.getAction() == Action.RIGHT_CLICK_AIR ||
        e.getAction() == Action.RIGHT_CLICK_BLOCK) {


      if (e.getPlayer().getInventory().getHeldItemSlot() == 4) {


        Player p = e.getPlayer();
        Inventory gamesGUI;

        gamesGUI = Bukkit.getServer().createInventory(p, 27,
            "§dGame Selector");

        final ItemStack greyGlassPane =
            new ItemStack(Material.STAINED_GLASS_PANE, 1,
                (short) 7);

        int glassPanes = 0;

        while (glassPanes < 27) {
          gamesGUI.setItem(glassPanes, greyGlassPane);
          glassPanes++;
          if (glassPanes >= 28) {
            glassPanes = 0;
          }
        }
        ItemStack beaconBattles = new ItemStack(Material.BEACON);
        ItemMeta beaconBattlesItemMeta = beaconBattles.getItemMeta();
        List<String> beaconBattlesLore = new ArrayList<>();
        beaconBattlesLore.add("§7");
        beaconBattlesLore.add("§7Claim beacons on floating islands");
        beaconBattlesLore.add("§7stop other teams from respawning");
        beaconBattlesLore.add("§7and be the last team standing to win!");
        beaconBattlesLore.add("§7");
        beaconBattlesLore.add("§b§lClick to teleport to gamemode");

        // add dynamic playercount here
        beaconBattlesLore.add("§6§l➤ §e0 players playing!");

        beaconBattlesItemMeta.setLore(beaconBattlesLore);
        beaconBattlesItemMeta
            .setDisplayName("§3§lBeacon Battle §7(Version 1.8.9+)");
        beaconBattles.setItemMeta(beaconBattlesItemMeta);
        gamesGUI.setItem(10, beaconBattles);

        ItemStack trapped = new ItemStack(Material.LADDER);
        ItemMeta trappedItemMeta = trapped.getItemMeta();
        List<String> trappedLore = new ArrayList<>();
        trappedLore.add("§7");
        trappedLore.add("§7On each floor you're given tasks");
        trappedLore.add("§7to complete as a team, but beware");
        trappedLore.add("§7someone has been chosen as the");
        trappedLore.add("§7saboteur and are working against");
        trappedLore.add("§7you! §7Get to the bottom and don't");
        trappedLore.add("§7get trapped in the tower!");
        trappedLore.add("§7");
        trappedLore.add("§b§lClick to teleport to gamemode");

        // add dynamic playercount here
        trappedLore.add("§6§l➤ §e0 players playing!");

        assert trappedItemMeta != null;
        trappedItemMeta.setLore(trappedLore);
        trappedItemMeta.setDisplayName("§6§lTrapped §7(Version 1.8.9+)");
        trapped.setItemMeta(trappedItemMeta);
        gamesGUI.setItem(12, trapped);

        ItemStack netherRaid = new ItemStack(Material.GOLD_SWORD);
        ItemMeta netherRaidItemMeta = netherRaid.getItemMeta();
        List<String> netherLore = new ArrayList<>();
        netherLore.add("§7");
        netherLore.add("§7Defend the stronghold, or raid it!");
        netherLore.add("§7Play as a §9defender§7 and stop the!");
        netherLore.add("§cMagma Boss§7 from being murdered!");
        netherLore.add("§7or play as an §cattacker§7 and kill!");
        netherLore.add("§7the magma boss before backup arrives!");
        netherLore.add("§7");
        netherLore.add("§b§lClick to teleport to gamemode");

        // add dynamic playercount here
        netherLore.add("§6§l➤ §e0 players playing!");

        netherRaidItemMeta.setLore(netherLore);
        netherRaidItemMeta.setDisplayName("§c§lNether Raid §7(Version 1.8.9+)");
        netherRaid.setItemMeta(netherRaidItemMeta);
        gamesGUI.setItem(14, netherRaid);

        ItemStack spawn = new ItemStack(Material.BED);
        ItemMeta spawnItemMeta = spawn.getItemMeta();
        List<String> spawnLore = new ArrayList<>();
        spawnLore.add("§7");
        spawnLore.add("§7Return to the main spawnpoint of");
        spawnLore.add("§7your current Lobby server");
        spawnLore.add("§7");
        spawnLore.add("§b§lClick to teleport to gamemode");
        spawnLore.add("§6§l➤ §e1 players playing!");
        spawnItemMeta.setLore(spawnLore);
        spawnItemMeta.setDisplayName("§a§lSpawn");
        spawn.setItemMeta(spawnItemMeta);
        gamesGUI.setItem(16, spawn);

        p.openInventory(gamesGUI);


      } else if (e.getPlayer().getInventory().getHeldItemSlot() == 6) {
        e.getPlayer().performCommand("settings");
      }
    }
  }

}

