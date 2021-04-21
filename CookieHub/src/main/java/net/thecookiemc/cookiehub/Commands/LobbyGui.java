package net.thecookiemc.cookiehub.Commands;

import com.rethinkdb.net.Cursor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LobbyGui implements Listener {

  @EventHandler
  public void onClick(PlayerInteractEvent e) {
    if ((e.getAction() == Action.RIGHT_CLICK_AIR ||
        e.getAction() == Action.RIGHT_CLICK_BLOCK) &&
        e.getPlayer().getInventory().getHeldItemSlot() == 2) {


      net.thecookiemc.cookiecore.Main lobbyfetch =
          (net.thecookiemc.cookiecore.Main) Bukkit.getPluginManager()
              .getPlugin("CookieCore");

      Player p = e.getPlayer();
      Inventory LobbyGui;

      LobbyGui = Bukkit.getServer().createInventory(p, 45,
          "§eLobby Jumper");

      final ItemStack greyGlassPane =
          new ItemStack(Material.STAINED_GLASS_PANE, 1,
              (short) 7);

      int glassPanes = 0;

      while (glassPanes < 45) {
        LobbyGui.setItem(glassPanes, greyGlassPane);
        glassPanes++;
        if (glassPanes >= 46) {
          glassPanes = 0;
        }
      }

      net.thecookiemc.cookiecore.Main api =
          (net.thecookiemc.cookiecore.Main) Bukkit.getPluginManager()
              .getPlugin("CookieCore");

      Cursor cursor = api.FetchServers();

      int Lobbycount = 0;
      int position = 9;

      for (Object doc : cursor) {


        if (((HashMap<String, String>) doc).get("game").equals("lobby")) {
          if (((HashMap<String, Boolean>) doc).get("joinable").equals(true)) {


            Lobbycount++;
            System.out.println(Lobbycount);

            ItemStack Lobby =
                new ItemStack(Material.INK_SACK, Lobbycount, (byte) 10);

            if (lobbyfetch.SERVERID
                .equals(((HashMap<String, String>) doc).get("id"))) {
              Lobby = new ItemStack(Material.INK_SACK, Lobbycount, (byte) 12);
            }

            ItemMeta gameSettingsItemMeta = Lobby.getItemMeta();
            List<String> gameSettingsLore = new ArrayList<>();

            gameSettingsLore.add(
                "§a" + ((HashMap<String, Integer>) doc).get("playercount") +
                    "/64 players");
            //if(Integer.parseInt(((HashMap<String, Integer>) doc).get("playercount").toString()) > 45) {
            //    gameSettingsLore.add("§aThis lobby is Donator only (full)!");
            //    ItemMeta meta = Lobby.getItemMeta();
            //    meta.addEnchant(Enchantment.DAMAGE_ALL, 1, true);
            //    Lobby.setItemMeta(meta);

            //}

            gameSettingsLore.add("§a");
            gameSettingsLore.add("§7A lobby will allow you to chat");
            gameSettingsLore.add("§7to fellow players, join games, view");
            gameSettingsLore.add("§7your statistics and show off your");
            gameSettingsLore.add("§7latest achievements!");
            gameSettingsLore.add("§a");
            if (lobbyfetch.SERVERID
                .equals(((HashMap<String, String>) doc).get("id"))) {
              gameSettingsLore.add("§b§lYou are in this lobby!");
            } else {
              gameSettingsLore.add("§b§lClick to swap to this Lobby!");
            }

            gameSettingsItemMeta.setLore(gameSettingsLore);
            gameSettingsItemMeta.setDisplayName(
                "§fLobby #" + Lobbycount + " §7(" +
                    ((HashMap<String, String>) doc).get("id") + ")");
            Lobby.setItemMeta(gameSettingsItemMeta);

            position = position + 1;

            if (position == 16 || position == 25 || position == 34) {
              position = position + 3;
            }

            LobbyGui.setItem(position, Lobby);


          }
        }


      }

      while (Lobbycount < 21) {

        Lobbycount++;

        ItemStack Lobby =
            new ItemStack(Material.INK_SACK, Lobbycount, (byte) 8);

        ItemMeta gameSettingsItemMeta = Lobby.getItemMeta();
        List<String> gameSettingsLore = new ArrayList<>();

        gameSettingsLore.add("§7This server is sleeping!");
        gameSettingsLore.add("§a");
        gameSettingsLore.add("§7This server is currently asleep!");
        gameSettingsLore.add("§7It will wake up when it's needed.");
        gameSettingsLore.add("§a");
        gameSettingsLore.add("§c§lServer offline!");
        gameSettingsItemMeta.setLore(gameSettingsLore);
        gameSettingsItemMeta.setDisplayName("§fLobby #" + Lobbycount);
        Lobby.setItemMeta(gameSettingsItemMeta);


        if (position == 16 || position == 25 || position == 34) {
          position = position + 3;
        } else {
          position++;
        }

        LobbyGui.setItem(position, Lobby);


      }

      p.openInventory(LobbyGui);


    }
  }

}
