package net.thecookiemc.events;

import com.rethinkdb.net.Cursor;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ServerPing;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.event.ProxyPingEvent;
import net.md_5.bungee.api.plugin.Cancellable;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.thecookiemc.Main;

import java.util.HashMap;

public class Ping implements Listener {

    public static String currentmotd = GetMOTD();

    public static String GetMOTD() {

        Cursor cursor = Main.r.db("InstanceManager").table("announcements").orderBy().optArg("index", Main.r.desc("id")).filter(doc -> doc.g("motd").ne("n/a")).run(Main.conn);

        for(Object announcement : cursor) {
            currentmotd = ((HashMap<String, String>) announcement).get("motd");

            currentmotd = ChatColor.translateAlternateColorCodes('&', currentmotd);

            String getsize = currentmotd.replaceAll("§", "");

            int spacing = (53 - getsize.length()) / 2;
            String space = "";

            for(int i = 0; i < spacing; ++i) {
                space += " ";
            }

            System.out.println("[CookieBungee] Updated the server MOTD");
            System.out.println(space + currentmotd);
            currentmotd = "§7" + space + "§7" + currentmotd;
            return currentmotd;
        }



        return "TheCookieMC";


    }

    @EventHandler
    public void onPing(ProxyPingEvent e) {

        final ServerPing response = e.getResponse();

        if (response == null || e instanceof Cancellable && ((Cancellable) e).isCancelled()) {
            return;
        }

        final ServerPing.Players players = response.getPlayers();
        int onlinePlayers = players.getOnline();
        players.setMax(onlinePlayers + 1);

        response.setDescriptionComponent(new TextComponent("§7            §6§m-●-§r §e§lThe§6§lCookie§e§lMC §r§7(1.8.9+) §6§m-●-\n§r"+currentmotd));

        response.getVersion().setName("CookieCord latest");

        ServerPing newPing = new ServerPing();
        e.setResponse(response);
    }

}
