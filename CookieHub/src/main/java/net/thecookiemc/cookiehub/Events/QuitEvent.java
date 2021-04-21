package net.thecookiemc.cookiehub.Events;

import net.thecookiemc.cookiecore.Main;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;


public class QuitEvent implements Listener {

    @EventHandler
    public void OnQuit(
            PlayerQuitEvent e
    ) {

        e.setQuitMessage(null);
        net.thecookiemc.cookiecore.Main api = (net.thecookiemc.cookiecore.Main) Bukkit.getPluginManager().getPlugin("CookieCore");

        Main.getInstance().getServer().getScheduler().runTaskLater(Main.getInstance(), () -> {
            Main.updatePlayerCount();
        }, 2);



    }

}
