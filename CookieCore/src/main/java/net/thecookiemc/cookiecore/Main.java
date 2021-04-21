package net.thecookiemc.cookiecore;

import com.rethinkdb.RethinkDB;
import com.rethinkdb.net.Connection;
import com.rethinkdb.net.Cursor;
import net.thecookiemc.cookiecore.Events.Chat.Chat;
import net.thecookiemc.cookiecore.Events.Join.Bossbar;
import net.thecookiemc.cookiecore.Events.Chat.MuteList;
import net.thecookiemc.cookiecore.Events.Join.Scoreboard;
import net.thecookiemc.cookiecore.Events.Join.Security;
import net.thecookiemc.cookiecore.Events.Login.CheckBan;
import net.thecookiemc.cookiecore.Methods.PunishMSG;
import net.thecookiemc.cookiecore.commands.*;
import net.thecookiemc.hidden.obfuscated.Returns;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;

@SuppressWarnings("unchecked")
public class Main extends JavaPlugin implements Listener {

  public static final RethinkDB r = RethinkDB.r;
  public static Connection conn =
      r.connection().hostname(Returns.getDataBaseIP())
          .port(Returns.getDataBasePort())
          .user(Returns.getDataBaseUsername(), Returns.getDataBasePassword())
          .connect();
  public static String SERVERID;
  private static Long lastBanCount;
  private static Main instance;

  public static Cursor FetchServers() {

    return r.db("InstanceManager").table("servers").run(conn);

  }

  public static Cursor FetchSmallLobby() {

    return r.db("InstanceManager").table("servers").orderBy()
        .optArg("index", r.desc("playercount"))
        .filter(doc -> doc.g("game").match("lobby")).run(conn);

  }

  public static String getIP() {
    try {
      return new BufferedReader(new InputStreamReader(
          new URL("http://checkip.amazonaws.com").openStream())).readLine();
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }

  public static Boolean clearplayercount() {
    Integer playercount = getPlayerCount();


    r.db("InstanceManager").table("servers").filter(
        row -> row.g("id").eq(SERVERID)
    ).update(r.hashMap("playercount", 0)).run(conn);

    return true;
  }

  public static Boolean updatePlayerCount() {
    Integer playercount = getPlayerCount();


    r.db("InstanceManager").table("servers").filter(
        row -> row.g("id").eq(SERVERID)
    ).update(r.hashMap("playercount", playercount)).run(conn);

    return true;
  }

  public static Integer getPlayerCount() {
    return Bukkit.getServer().getOnlinePlayers().size();

  }

  public static String getServerID() {


    Cursor cursor = r.db("InstanceManager").table("servers").filter(
        doc -> doc.g("ip")
            .match((getIP() + ":" + Bukkit.getServer().getPort()))).run(conn);

    for (Object doc : cursor) {

      return ((HashMap<String, String>) doc).get("id");

    }

    return "Development";

  }

  public static void joinable(boolean state) {

    r.db("InstanceManager").table("servers").filter(
        row -> row.g("id").eq(SERVERID)
    ).update(r.hashMap("joinable", state)).run(conn);

  }

  public static void removeServerFromDb() {
    //r.db("InstanceManager").table("servers")
        //.filter(row -> row.g("id").eq(SERVERID)).delete().run(conn);
  }

  public static Main getInstance() {
    return instance;
  }


  @Override
  public void onDisable() {

    clearplayercount();
    joinable(false);

  }

  @Override
  public void onEnable() {

    lastBanCount = r.db("Punishments").table("punishments").count().run(conn);

    this.getServer().getScheduler().scheduleSyncRepeatingTask(this,
        () -> {


          Long banCheck =
              r.db("Punishments").table("punishments").count().run(conn);


          if (!lastBanCount.equals(banCheck)) {
            int newBanCount = (int) (banCheck - lastBanCount);

            if (newBanCount > 0) {

              Cursor cursor =
                  Main.r.db("Punishments").table("punishments").orderBy()
                      .optArg("index", Main.r.desc("occurred"))
                      .limit(newBanCount).run(Main.conn);

              for (Object ban : cursor) {

                String type = null;

                if (((HashMap<String, String>) ban).get("type")
                    .equalsIgnoreCase("ban")) {

                  type = "bann";

                  Player p = Bukkit.getPlayer(
                      ((HashMap<String, String>) ban).get("username"));

                  if (p != null) {
                    p.kickPlayer(PunishMSG.PunishMSG(
                        ((HashMap<String, String>) ban).get("reason"),
                        ((HashMap<String, String>) ban).get("type"),
                        ((HashMap<String, Long>) ban).get("expires"),
                        ((HashMap<String, String>) ban).get("id")));
                  }

                }

                if (((HashMap<String, String>) ban).get("type")
                        .equalsIgnoreCase("mute")) {

                  type = "mute";

                  Player p = Bukkit.getPlayer(
                      ((HashMap<String, String>) ban).get("username"));

                  if (p != null) {
                    p.kickPlayer(PunishMSG.PunishMSG(
                        ((HashMap<String, String>) ban).get("reason"),
                        ((HashMap<String, String>) ban).get("type"),
                        ((HashMap<String, Long>) ban).get("expires"),
                        ((HashMap<String, String>) ban).get("id")));
                  }

                }


                Bukkit.broadcast("§8┃ §bStaff §8┃ §e" +
                        ((HashMap<String, String>) ban).get("username") + " §7" +
                        type + "ed for §f" +
                        ((HashMap<String, String>) ban).get("reason"),
                    "cookiecore.alert.punishment");

               if(((HashMap<String, String>) ban).get("report") != "null") {

                  Bukkit.broadcast("§8┃ §bStaff §8┃ §7ID: §f#" +
                          ((HashMap<String, String>) ban).get("id") +
                          " §8- §7Server: §f" +
                          ((HashMap<String, String>) ban).get("instance"),
                      "cookiecore.alert.punishment");

                } else {

                  Bukkit.broadcast("§8┃ §bStaff §8┃ §7ID: §f#" +
                          ((HashMap<String, String>) ban).get("id") +
                          " §8- §7Server: §f" +
                          ((HashMap<String, String>) ban).get("instance") +
                          " §8- §7Report: §f#" +
                          ((HashMap<String, String>) ban).get("report"),
                      "cookiecore.alert.punishment");

                }


              }

            }
          }

          // update ban count
          lastBanCount = banCheck;
        }, 0, 100);


    instance = this;
    Bukkit.getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");

    PluginManager pm = this.getServer().getPluginManager();

    pm.registerEvents(new Scoreboard(), this);
    pm.registerEvents(new OnCommand(), this);
    pm.registerEvents(new Bossbar(), this);
    pm.registerEvents(new Ban(), this);
    pm.registerEvents(new Security(), this);
    pm.registerEvents(new Chat(), this);
    pm.registerEvents(new MuteList(), this);
    pm.registerEvents(new CheckBan(), this);

    Bukkit.getPluginCommand("jump").setExecutor(new Jump());
    Bukkit.getPluginCommand("swap").setExecutor(new Swap());
    Bukkit.getPluginCommand("nothing").setExecutor(new Nothing());
    Bukkit.getPluginCommand("findme").setExecutor(new GetServerIDCommand());
    Bukkit.getPluginCommand("console").setExecutor(new ConsoleLink());
    Bukkit.getPluginCommand("ban").setExecutor(new Ban());
    Bukkit.getPluginCommand("queue").setExecutor(new Queue());
    Bukkit.getPluginCommand("lobby").setExecutor(new Lobby());
    Bukkit.getPluginCommand("session").setExecutor(new Session());
    Bukkit.getPluginCommand("find").setExecutor(new Find());
    Bukkit.getPluginCommand("history").setExecutor(new History());

    // TODO - rewrite the entire report system
    Bukkit.getPluginCommand("report").setExecutor(new Report());

    SERVERID = getServerID();

    System.out.println("==================");
    System.out.println("  PTERODACTYL ID  ");
    System.out.println("  " + SERVERID);
    System.out.println("==================");

    joinable(true);
  }


}
