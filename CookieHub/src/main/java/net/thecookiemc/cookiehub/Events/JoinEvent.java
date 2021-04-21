package net.thecookiemc.cookiehub.Events;

import net.thecookiemc.cookiehub.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import net.thecookiemc.cookiecore.*;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.cacheddata.CachedMetaData;


public class JoinEvent implements Listener {

    private final LuckPerms luckPerms;

    public JoinEvent(LuckPerms luckPerms) {
        this.luckPerms = luckPerms;
    }

    @EventHandler
    public void OnLogin(PlayerJoinEvent e) {


        final ItemStack playerCustomSkull =
                new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        final SkullMeta playerCustomSkullMeta =
                (SkullMeta) playerCustomSkull.getItemMeta();
        playerCustomSkullMeta.setDisplayName(ChatColor.AQUA + "Settings & " +
                "Stats");
        playerCustomSkullMeta.setOwner(e.getPlayer().getName());
        playerCustomSkull.setItemMeta(playerCustomSkullMeta);

        final ItemStack enderPearlHead =
                new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        final SkullMeta enderPearlHeadMeta =
                (SkullMeta) enderPearlHead.getItemMeta();
        enderPearlHeadMeta.setDisplayName("§dGame Selector");
        enderPearlHeadMeta.setOwner("Cypiea");
        enderPearlHead.setItemMeta(enderPearlHeadMeta);

        final ItemStack lobbySelector =
                new ItemStack(Material.SKULL_ITEM, 1, (short) 3);

        final SkullMeta lobbySelectorMeta =
                (SkullMeta) lobbySelector.getItemMeta();
        lobbySelectorMeta.setDisplayName(ChatColor.YELLOW + "Lobby Jumper");
        lobbySelectorMeta.setOwner("MHF_arrowleft");

        //             ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        //            SkullMeta meta = (SkullMeta) skull.getItemMeta();
        //            meta.setOwner(playerName);
        //            skull.setItemMeta(meta);
        //            event.getDrops().add(skull);


        lobbySelector.setItemMeta(lobbySelectorMeta);


        net.thecookiemc.cookiecore.Main api = (net.thecookiemc.cookiecore.Main) Bukkit.getPluginManager().getPlugin("CookieCore");

        net.thecookiemc.cookiecore.Main.updatePlayerCount();

        // TODO - set player's tab colour

        e.getPlayer().sendMessage("§8┃ §dJumper §8┃ §7You have connected to §fLobby §7[" + api.SERVERID + "]");

        // check player's rank before announcing join
        Player player = e.getPlayer();

        ;        // Get a LuckPerms cached metadata for the player.
        CachedMetaData metaData = this.luckPerms.getPlayerAdapter(Player.class).getMetaData(player);

        // Get their prefix.
        String prefix = metaData.getPrefix();


        if (prefix != null) {


            prefix = ChatColor.translateAlternateColorCodes('&', prefix);

            String[] colour = prefix.split("");

            e.getPlayer().setPlayerListName("§"+colour[1] + e.getPlayer().getName());
            e.getPlayer().setDisplayName("§"+colour[1] + e.getPlayer().getName());

            // chat format
            Bukkit.broadcastMessage("§8┃ §b✦ §8┃ " + prefix + " §8┃ " + "§" + colour[1] + e.getPlayer().getName() + " §7has entered the Lobby!");

        } else {
            e.getPlayer().setPlayerListName("§7" + e.getPlayer().getName());
            e.getPlayer().setDisplayName("§7" + e.getPlayer().getName());
        }

        e.setJoinMessage(null);

        Main.getInstance().getServer().getScheduler()
                .runTaskLater(Main.getInstance(),
                        () -> {
                            e.getPlayer().setGameMode(GameMode.ADVENTURE);
                            e.getPlayer().getInventory().clear();
                            e.getPlayer().getInventory().setItem(6, playerCustomSkull);
                            e.getPlayer().getInventory().setItem(4, enderPearlHead);
                            e.getPlayer().getInventory().setItem(2, lobbySelector);
                        }, 1);

    }

}
