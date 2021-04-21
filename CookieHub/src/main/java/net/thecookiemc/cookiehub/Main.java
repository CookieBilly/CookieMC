package net.thecookiemc.cookiehub;

import net.luckperms.api.LuckPerms;
import net.thecookiemc.cookiehub.Commands.LobbyGui;
import net.thecookiemc.cookiehub.Commands.Settings;
import net.thecookiemc.cookiehub.Commands.Spawn;
import net.thecookiemc.cookiehub.Commands.hicommand;
import net.thecookiemc.cookiehub.Events.*;
import net.thecookiemc.cookiehub.announce.Announcer;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import static net.thecookiemc.cookiehub.Events.SpinnyBoiArmourStand.*;

public final class Main extends JavaPlugin implements Listener {

  private static Main plugin;
  private static Main instance;

  public static Main getPlugin() {
    return plugin;
  }

  public static void setPlugin(Main plugin) {
    Main.plugin = plugin;
  }

  public static Main getInstance() {
    return instance;
  }

  private LuckPerms luckPerms;


  @Override
  public void onEnable() {



    setPlugin(this);


    createArmourStand();

    this.getServer().getScheduler().scheduleSyncRepeatingTask(this,
        () -> {
          trappedIslandArmourStand
              .setHeadPose(trappedIslandArmourStand.getHeadPose().add(0, 0.08,
                  0));
          netherRaidIslandArmourStand
              .setHeadPose(trappedIslandArmourStand.getHeadPose().add(0, 0.08,
                  0));
          beaconBattleIslandArmourStand
              .setHeadPose(trappedIslandArmourStand.getHeadPose().add(0, 0.08
                  , 0));
        }, 0, 1);

    this.getServer().getScheduler().scheduleSyncRepeatingTask(this,
        new Runnable() {
          @Override
          public void run() {
            Announcer.Announce();
          }
        }, 0, 4800);

    PluginManager pm = Bukkit.getServer().getPluginManager();


    this.luckPerms = getServer().getServicesManager().load(LuckPerms.class);

    // events
    pm.registerEvents(new JoinEvent(luckPerms), this);
    pm.registerEvents(new QuitEvent(), this);
    pm.registerEvents(new BlockBreak(), this);
    pm.registerEvents(new BlockPlace(), this);
    pm.registerEvents(new DamageEvent(), this);
    pm.registerEvents(new HungerEvent(), this);
    pm.registerEvents(new InteractEvent(), this);
    pm.registerEvents(new WeatherChange(), this);
    pm.registerEvents(new ClickNPC(), this);
    pm.registerEvents(new RightClickEvent(), this);
    pm.registerEvents(new OnClick(), this);
    pm.registerEvents(new DropAndPickUp(), this);
    pm.registerEvents(new BlockGravity(), this);
    pm.registerEvents(new LobbyGui(), this);
    pm.registerEvents(new ChatEvent(luckPerms), this);
    pm.registerEvents(new ChunkLoad(), this);


    instance = this;

    //commands
    Bukkit.getPluginCommand("hi").setExecutor(new hicommand());
    Bukkit.getPluginCommand("settings").setExecutor(new Settings());
    Bukkit.getPluginCommand("spawn").setExecutor(new Spawn());
  }

  @Override
  public void onDisable() {
    // Plugin shutdown logic
  }
}
