package net.thecookiemc.cookiecore;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.server.ServerCommandEvent;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class OnCommand implements Listener {

  public static int TICK_COUNT = 0;
  public static long[] TICKS = new long[600];
  public static long LAST_TICK = 0L;

  private void deleteServer() throws IOException {
    String internalid = getInternalID();

    if (internalid != null) {

      String serverurl =
          "https://dev.thecookiemc.net/api/application/servers/" +
              Main.SERVERID;

      URL url = new URL(serverurl);
      HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
      httpConn.setRequestMethod("DELETE");

      httpConn.setRequestProperty("Accept", "application/json");
      httpConn.setRequestProperty("Content-Type", "application/json");
      httpConn.setRequestProperty("Authorization",
          "Bearer 647IF3VVTcEHIsM4WaYwXFNY896otO7DFjdczEuxCeWHrLts");

      InputStream responseStream = httpConn.getResponseCode() / 100 == 2
          ? httpConn.getInputStream()
          : httpConn.getErrorStream();
      Scanner s = new Scanner(responseStream).useDelimiter("\\A");
      String response = s.hasNext() ? s.next() : "";

    }
  }

  private String getInternalID() throws IOException {
    System.out.println(
        "https://dev.thecookiemc.net/api/application/servers/" + Main.SERVERID);
    URL url = new URL(
        "https://dev.thecookiemc.net/api/application/servers/" + Main.SERVERID);
    HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
    httpConn.setRequestMethod("GET");

    httpConn.setRequestProperty("Accept", "application/json");
    httpConn.setRequestProperty("Content-Type", "application/json");
    httpConn.setRequestProperty("Authorization",
        "Bearer 647IF3VVTcEHIsM4WaYwXFNY896otO7DFjdczEuxCeWHrLts");

    InputStream responseStream = httpConn.getResponseCode() / 100 == 2
        ? httpConn.getInputStream()
        : httpConn.getErrorStream();
    Scanner s = new Scanner(responseStream).useDelimiter("\\A");
    String response = s.hasNext() ? s.next() : "";

    System.out.println(response);

    JSONArray array = new JSONArray("[" + response + "]");

    String internalid = null;

    for (int i = 0; i < array.length(); i++) {

      if (internalid == null) {
        JSONObject object = array.getJSONObject(i);
        System.out.println(object);
        try {
          internalid = object.getString("attributes.id");
        } catch (Exception e) {
          e.printStackTrace();
        }

      }

    }

    return internalid;
  }

  @EventHandler(priority = EventPriority.LOWEST)
  public void onCommandPreProcessConsole(ServerCommandEvent event) {
    // System.out.println(event.getCommand());
    if (event.getCommand().startsWith("stop")) {

      Bukkit.broadcastMessage(
          "§8┃ §a❃ §8┃ §3This server is restarting! §7Moving you...");
      Bukkit.broadcastMessage("§8┃ ");

      Main.joinable(false);

      for (Player p : Bukkit.getOnlinePlayers()) {
        p.performCommand("lobby");
      }

      Main.getInstance().getServer().getScheduler()
          .runTaskLater(Main.getInstance(),
              Main::removeServerFromDb, 60);


      try {
        deleteServer();
      } catch (IOException e) {
        System.out.println("aaaaaaaaaaaaaaaaaa");
        e.printStackTrace();
      }

      Bukkit.shutdown();


    }
  }


  @EventHandler(priority = EventPriority.LOWEST)
  public void onCommandPreProcess(PlayerCommandPreprocessEvent event) {

    Player sender = event.getPlayer();

    if (event.getMessage().toLowerCase().startsWith("/tps")) {


      if (event.getPlayer().hasPermission("cookiecore.command.tps")) {
        return;
      }

      event.setCancelled(true);
      sender.sendMessage("§8┃ §c✖ §8┃ §cYou do not have permission for this!");
    }

    if (event.getMessage().toLowerCase().startsWith("/perms")) {
      if (!event.getPlayer().hasPermission("luckperms.*")) {
        sender
            .sendMessage("§8┃ §c✖ §8┃ §cYou do not have permission for this!");
        event.setCancelled(true);
      }
      return;
    }

    if (event.getMessage().toLowerCase().startsWith("/lp") ||
        event.getMessage().toLowerCase().startsWith("/luckperms") ||
        event.getMessage().toLowerCase().startsWith("/permissions") ||
        event.getMessage().toLowerCase().startsWith("/perm")) {
      sender.sendMessage(
          "§8| §7This command does not exist. Use §e/help §7for help");
      event.setCancelled(true);
      return;
    }


    if (event.getMessage().toLowerCase().startsWith("/restart") ||
        event.getMessage().toLowerCase().startsWith("/stop") ||
        event.getMessage().toLowerCase().startsWith("/reboot")) {

      String[] server = event.getMessage().toLowerCase().split(" ");

      if (event.getPlayer().hasPermission("cookiecore.command.restart") ||
          sender.isOp()) {

        try {

          System.out.println("aaaa" + server[1]);

          // TODO - api request
          sender.sendMessage(
              "§8┃ §a❃ §8┃ §7Reinstalling instance with id §f" + server[1] +
                  "§7.");


        } catch (ArrayIndexOutOfBoundsException e) {

          // TODO - api request
          sender.sendMessage(
              "§8┃ §a❃ §8┃ §7Reinstalling your current instance (" +
                  Main.SERVERID + ")");

          Bukkit.broadcastMessage(
              "§8┃ §a❃ §8┃ §3This server is restarting! §7Moving you...");
        }


      } else {
        sender
            .sendMessage("§8┃ §c✖ §8┃ §cYou do not have permission for this!");
      }
      event.setCancelled(true);

    }
  }


}
