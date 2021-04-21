package net.thecookiemc.cookiegamecore;

import net.thecookiemc.cookiegamecore.commands.StartVoting;
import net.thecookiemc.cookiegamecore.commands.Vote;
import net.thecookiemc.cookiegamecore.construction.VotingNPC;
import net.thecookiemc.cookiegamecore.teams.CreateTeamsCommand;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

  private static Main instance;

  public static Main getInstance() {
    return instance;
  }

  @Override
  public void onEnable() {
    instance = this;

    PluginManager pm = Bukkit.getServer().getPluginManager();

    Bukkit.getPluginCommand("vote").setExecutor(new Vote());
    Bukkit.getPluginCommand("startvoting").setExecutor(new StartVoting());
    Bukkit.getPluginCommand("createteams")
        .setExecutor(new CreateTeamsCommand());

    VotingNPC.createVotingStands((new Location(Bukkit.getWorld("lobby"),
        -169, 52, 16.5)), (new Location(Bukkit.getWorld("lobby"),
        -169, 52, 18.5)), (new Location(Bukkit.getWorld("lobby"),
        -169, 52, 20.5)));

  }

  @Override
  public void onDisable() {
    // Plugin shutdown logic
  }
}
