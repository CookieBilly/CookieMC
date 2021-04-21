package net.thecookiemc.cookiehub.Commands;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.List;

public class Settings implements Listener, CommandExecutor {
  @Override
  public boolean onCommand(CommandSender sender, Command cmd,
                           String label, String[] args) {
    if (sender instanceof Player) {
      if (cmd.getName().equalsIgnoreCase("settings")) {
        Player p = (Player) sender;
        Inventory settingsGUI;

        settingsGUI = Bukkit.getServer().createInventory(p, 27,
            "§bSettings & Stats");


        final ItemStack playerCustomSkull =
            new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        final SkullMeta playerCustomSkullMeta =
            (SkullMeta) playerCustomSkull.getItemMeta();
        playerCustomSkullMeta.setOwner(sender.getName());
        playerCustomSkull.setItemMeta(playerCustomSkullMeta);


        final ItemStack greyGlassPane =
            new ItemStack(Material.STAINED_GLASS_PANE, 1,
                (short) 7);

        int glassPanes = 0;

        while (glassPanes < 27) {
          settingsGUI.setItem(glassPanes, greyGlassPane);
          glassPanes++;
          if (glassPanes >= 28) {
            glassPanes = 0;
          }
        }

        ItemStack gameSettings = new ItemStack(Material.DIODE);
        ItemMeta gameSettingsItemMeta = gameSettings.getItemMeta();
        List<String> gameSettingsLore = new ArrayList<>();
        gameSettingsLore.add("§7");
        gameSettingsItemMeta.setLore(gameSettingsLore);
        gameSettingsItemMeta.setDisplayName("§eYour Game Settings");
        gameSettings.setItemMeta(gameSettingsItemMeta);
        settingsGUI.setItem(10, gameSettings);

        ItemMeta profileItemMeta = playerCustomSkull.getItemMeta();
        List<String> profileLore = new ArrayList<>();
        profileLore.add("§7");
        profileItemMeta.setLore(profileLore);
        profileItemMeta.setDisplayName("§eYour Stats");
        playerCustomSkull.setItemMeta(profileItemMeta);
        settingsGUI.setItem(12, playerCustomSkull);

        ItemStack achievements = new ItemStack(Material.EMERALD);
        ItemMeta achievementsItemMeta = achievements.getItemMeta();
        List<String> achievementsLore = new ArrayList<>();
        achievementsLore.add("§7");
        achievementsItemMeta.setLore(achievementsLore);
        achievementsItemMeta.setDisplayName("§eYour Achievements");
        achievements.setItemMeta(achievementsItemMeta);
        settingsGUI.setItem(14, achievements);

        ItemStack privacySettings = new ItemStack(Material.BOOK_AND_QUILL);
        ItemMeta privacySettingsItemMeta = privacySettings.getItemMeta();
        List<String> privacyLore = new ArrayList<>();
        privacyLore.add("§7");
        privacySettingsItemMeta.setLore(privacyLore);
        privacySettingsItemMeta.setDisplayName("§eYour Privacy Settings");
        privacySettings.setItemMeta(privacySettingsItemMeta);
        settingsGUI.setItem(16, privacySettings);


        p.openInventory(settingsGUI);
      }
    }
    return true;
  }
}
