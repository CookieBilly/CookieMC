package net.thecookiemc.cookiehub.Events;

import net.thecookiemc.cookiehub.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.metadata.FixedMetadataValue;

public class SpinnyBoiArmourStand {


  public static ArmorStand trappedIslandArmourStand;
  public static ArmorStand netherRaidIslandArmourStand;
  public static ArmorStand beaconBattleIslandArmourStand;


  public static void createArmourStand() {

    for (Entity all :
        Bukkit.getWorld("lobby").getEntities()) {
      if (all.getType() == EntityType.ARMOR_STAND) {
        all.remove();
      }
    }


    // Final variables
    final ItemStack playerCustomSkull =
        new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
    final SkullMeta meta = (SkullMeta) playerCustomSkull.getItemMeta();
    meta.setOwner("Cypiea");
    playerCustomSkull.setItemMeta(meta);

    final World serverWorld = Bukkit.getWorld("lobby");

    final Location trappedIsland =
        new Location(serverWorld, -72.5, 51, 59.5);

    final Location netherRaidIsland =
        new Location(serverWorld, -100.5, 51, 25.5);

    final Location beaconBattleIsland =
        new Location(serverWorld, -22.5, 53, 73.5);

    // Trapped island armour stand
    trappedIslandArmourStand =
        (ArmorStand) serverWorld.spawnEntity(trappedIsland,
            EntityType.ARMOR_STAND);
    trappedIslandArmourStand.getEquipment().setHelmet(playerCustomSkull);
    trappedIslandArmourStand.setVisible(false);
    trappedIslandArmourStand.setCustomName("§d§lClick to teleport!");
    trappedIslandArmourStand.setCustomNameVisible(true);
    trappedIslandArmourStand.setMetadata("WhichNPC",
        new FixedMetadataValue(Main.getPlugin(), "Trapped"));

    // Nether island armour stand
    netherRaidIslandArmourStand =
        (ArmorStand) serverWorld.spawnEntity(netherRaidIsland,
            EntityType.ARMOR_STAND);
    netherRaidIslandArmourStand.getEquipment().setHelmet(playerCustomSkull);
    netherRaidIslandArmourStand.setVisible(false);
    netherRaidIslandArmourStand.setCustomName("§d§lClick to teleport!");
    netherRaidIslandArmourStand.setCustomNameVisible(true);
    netherRaidIslandArmourStand.setMetadata("WhichNPC",
        new FixedMetadataValue(Main.getPlugin(), "Nether Raid"));

    // Beacon battles armour stand
    beaconBattleIslandArmourStand = (ArmorStand) serverWorld
        .spawnEntity(beaconBattleIsland, EntityType.ARMOR_STAND);
    beaconBattleIslandArmourStand.getEquipment().setHelmet(playerCustomSkull);
    beaconBattleIslandArmourStand.setVisible(false);
    beaconBattleIslandArmourStand.setCustomName("§d§lClick to teleport!");
    beaconBattleIslandArmourStand.setCustomNameVisible(true);
    beaconBattleIslandArmourStand.setMetadata("WhichNPC",
        new FixedMetadataValue(Main.getPlugin(), "Beacon Battle"));


  }
}
