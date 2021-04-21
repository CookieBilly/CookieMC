package net.thecookiemc.cookiegamecore.construction;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import java.util.Objects;

public class VotingNPC {

  private static ItemStack getColouredLeatherArmor(Material leatherPiece,
                                                   int red,
                                                   int green, int blue) {
    ItemStack is = new ItemStack(leatherPiece);
    LeatherArmorMeta im = (LeatherArmorMeta) is.getItemMeta();
    im.setColor(Color.fromRGB(red, green, blue));
    is.setItemMeta(im);
    return is;
  }

  // This method MUST be called within onEnable() (or not, your choice
  // whether to break stuff 🥰)
  public static void createVotingStands(Location loc1,
                                        Location loc2,
                                        Location loc3) {

    // Logic for first armour stand spawning eeeee
    ArmorStand firstStand = (ArmorStand) loc1.getWorld().spawnEntity(loc1,
        EntityType.ARMOR_STAND);
    firstStand.setCustomName("Example 1");
    firstStand.setCustomNameVisible(true);
    Objects.requireNonNull(firstStand.getEquipment())
        .setHelmet(getColouredLeatherArmor(Material.LEATHER_HELMET, 255, 0, 0));
    firstStand.getEquipment().setChestplate(
        getColouredLeatherArmor(Material.LEATHER_CHESTPLATE, 255, 0, 0));
    firstStand.getEquipment()
        .setLeggings(getColouredLeatherArmor(Material.LEATHER_LEGGINGS
            , 255, 0, 0));
    firstStand.getEquipment()
        .setBoots(getColouredLeatherArmor(Material.LEATHER_BOOTS, 255, 0, 0));


    // Logic for second armour stand spawning
    ArmorStand secondStand = (ArmorStand) loc2.getWorld().spawnEntity(loc2,
        EntityType.ARMOR_STAND);
    secondStand.setCustomName("Example 2");
    secondStand.setCustomNameVisible(true);
    Objects.requireNonNull(secondStand.getEquipment())
        .setHelmet(getColouredLeatherArmor(Material.LEATHER_HELMET, 0, 255, 0));
    secondStand.getEquipment().setChestplate(
        getColouredLeatherArmor(Material.LEATHER_CHESTPLATE, 0, 255, 0));
    secondStand.getEquipment()
        .setLeggings(getColouredLeatherArmor(Material.LEATHER_LEGGINGS
            , 0, 0, 255));
    secondStand.getEquipment()
        .setBoots(getColouredLeatherArmor(Material.LEATHER_BOOTS, 0, 255, 0));


    // Logic for third armour stand spawning
    ArmorStand thirdStand = (ArmorStand) loc3.getWorld().spawnEntity(loc3,
        EntityType.ARMOR_STAND);
    thirdStand.setCustomName("Example 3");
    thirdStand.setCustomNameVisible(true);
    Objects.requireNonNull(thirdStand.getEquipment())
        .setHelmet(getColouredLeatherArmor(Material.LEATHER_HELMET, 0, 0, 255));
    thirdStand.getEquipment().setChestplate(
        getColouredLeatherArmor(Material.LEATHER_CHESTPLATE, 0, 0, 255));
    thirdStand.getEquipment()
        .setLeggings(getColouredLeatherArmor(Material.LEATHER_LEGGINGS
            , 0, 0, 255));
    thirdStand.getEquipment()
        .setBoots(getColouredLeatherArmor(Material.LEATHER_BOOTS, 0, 0, 255));
  }
}
