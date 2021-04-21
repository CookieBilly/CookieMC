package net.thecookiemc.cookiecore.Events.Join;

import net.thecookiemc.cookiecore.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class Bossbar implements Listener {

  @EventHandler
  public void onJoin(PlayerJoinEvent e) {
    BossBar b = Bukkit.createBossBar(
        ChatColor.DARK_PURPLE + "Visit our Website! " + BarColor.YELLOW +
            "...",
        BarColor.PURPLE,
        BarStyle.SOLID);


    Main.getInstance().getServer().getScheduler().runTaskLater(
        Main.getInstance(), () -> {
          b.addPlayer(e.getPlayer());
          b.setVisible(true);
        }, 5);

  }
}
