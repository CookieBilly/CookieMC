package net.thecookiemc.cookiecore.commands;

import com.rethinkdb.net.Cursor;
import com.sun.org.apache.xpath.internal.operations.Bool;
import net.thecookiemc.cookiecore.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class History implements Listener, CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd,
                             String label, String[] args) {

        if (sender instanceof Player) {
            if (cmd.getName().equalsIgnoreCase("history")) {
                if (sender.hasPermission("cookiecore.command.history")) {

                    if (args.length == 0) {
                        sender.sendMessage("no args provided");
                        return true;
                    }

                    Player p = (Player) sender;

                    OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
                    Inventory HistoryGUI;

                    HistoryGUI = Bukkit.getServer().createInventory(p, 45, "History for " + args[0]);

                    final ItemStack greyGlassPane =
                            new ItemStack(Material.STAINED_GLASS_PANE, 1,
                                    (short) 7);

                    int glassPanes = 0;

                    while (glassPanes < 45) {
                        HistoryGUI.setItem(glassPanes, greyGlassPane);
                        glassPanes++;
                        if (glassPanes >= 46) {
                            glassPanes = 0;
                        }
                    }

                    Cursor cursor = Main.r.db("Punishments").table("punishments").filter(
                            row -> row.g("UUID").eq(target.getUniqueId().toString())).run(Main.conn);

                    int position = 9;

                    for (Object doc : cursor) {


                        ItemStack Punishment = new ItemStack(Material.PAPER, 1);

                        // check if it's a kick
                        if (((HashMap<String, String>) doc).get("type").equalsIgnoreCase("kick")) {
                            Punishment = new ItemStack(Material.DIAMOND_BOOTS, 1);
                        }

                        // check if it's a ban
                        if (((HashMap<String, String>) doc).get("type").equalsIgnoreCase("ban")) {
                            Punishment = new ItemStack(Material.REDSTONE_BLOCK, 1);
                        }

                        // check if it's a mute
                        if (((HashMap<String, String>) doc).get("type").equalsIgnoreCase("mute")) {
                            Punishment = new ItemStack(Material.BOOK_AND_QUILL, 1);
                        }

                        ItemMeta PunishLore = Punishment.getItemMeta();
                        List<String> PunishmentLore = new ArrayList<>();
                        //if(Integer.parseInt(((HashMap<String, Integer>) doc).get("playercount").toString()) > 45) {
                        //    gameSettingsLore.add("§aThis lobby is Donator only (full)!");
                        //    ItemMeta meta = Lobby.getItemMeta();
                        //    meta.addEnchant(Enchantment.DAMAGE_ALL, 1, true);
                        //    Lobby.setItemMeta(meta);
                        //}
                        if(((HashMap<String, Integer>) doc).get("note") != null) {
                            PunishmentLore.add("§7" + ((HashMap<String, Integer>) doc).get("note"));
                        } else {
                            PunishmentLore.add("§7No notes added");
                        }
                        PunishmentLore.add("§a");
                        PunishmentLore.add("§eReason:");
                        PunishmentLore.add("§e● §7" + ((HashMap<String, String>) doc).get("reason"));
                        PunishmentLore.add("§a");
                        PunishmentLore.add("§ePunisher:");
                        // Bukkit.getPlayer(((HashMap<String, String>) doc).get("punisher"))
                        PunishmentLore.add("§e● §7" + "CookieBilly");
                        PunishmentLore.add("§e● §c" + "Moderator");
                        PunishmentLore.add("§a");
                        if(((HashMap<String, Boolean>) doc).get("active")) {
                            PunishmentLore.add("§aThis punishment is active.");
                            PunishmentLore.add("§cYou cannot revert this");
                        } else {
                            PunishmentLore.add("§bThis punishment was removed:");
                            PunishmentLore.add("§e● §c" + ((HashMap<String, Boolean>) doc).get("unpunishmoderator"));
                            PunishmentLore.add("§b● §7" + ((HashMap<String, Boolean>) doc).get("unpunishreason"));

                        }

                        PunishLore.setDisplayName("§e" +((HashMap<String, String>) doc).get("type").substring(0, 1).toUpperCase() + ((HashMap<String, String>) doc).get("type").substring(1) + " (" + ((HashMap<String, String>) doc).get("category") + ") ");
                        PunishLore.setLore(PunishmentLore);
                        Punishment.setItemMeta(PunishLore);

                        position = position + 1;

                        if (position == 16 || position == 25 || position == 34) {
                            position = position + 3;
                        }

                        HistoryGUI.setItem(position, Punishment);


                    }


                    p.openInventory(HistoryGUI);

                }
                return true;
            }
            return true;
        }
        return true;
    }


}

