package net.thecookiemc.events;

import com.rethinkdb.gen.ast.Insert;
import com.rethinkdb.net.Cursor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.event.PreLoginEvent;
import net.md_5.bungee.api.event.ServerConnectEvent;
import net.md_5.bungee.api.event.ServerKickEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.thecookiemc.Main;

import java.util.HashMap;

public class LoginEvent implements Listener {

    @EventHandler
    public void Kicked(ServerKickEvent e) {
        if(BaseComponent.toLegacyText(e.getKickReasonComponent()).contains("restarting")) {

            Cursor cursor = Main.r.db("InstanceManager").table("servers").orderBy().optArg("index", Main.r.desc("playercount")).filter(doc -> doc.g("game").match("lobby")).run(Main.conn);

            String serverid = null;

            for(Object server : cursor) {

                if (((HashMap<String, Boolean>) server).get("joinable")) {
                    serverid = ((HashMap<String, String>) server).get("id");
                }

            }

            try {
                ServerInfo alternativeLobby = Main.GetInstance().getProxy().getServerInfo(serverid);
                e.setCancelled(true);
                e.getPlayer().sendMessage("§8┃ §a❃ §8┃ §aYour previous server crashed, yikes!");
                e.getPlayer().sendMessage("§8┃ §a❃ §8┃ §7You were moved to the Lobby.!");
                e.getPlayer().sendMessage("§8┃ ");
                e.setCancelServer(alternativeLobby);

            } catch (NullPointerException error) {
                System.out.println(error);
            }


        } else {
            System.out.println(BaseComponent.toLegacyText(e.getKickReasonComponent()));
        }
    }


    @EventHandler
    public void Balancing(ServerConnectEvent e) {
        if(e.getReason() == ServerConnectEvent.Reason.JOIN_PROXY || e.getReason() == ServerConnectEvent.Reason.LOBBY_FALLBACK || e.getReason() == ServerConnectEvent.Reason.SERVER_DOWN_REDIRECT || e.getReason() == ServerConnectEvent.Reason.KICK_REDIRECT) {

            Cursor cursor = Main.r.db("InstanceManager").table("servers").orderBy().optArg("index", Main.r.desc("playercount")).filter(doc -> doc.g("game").match("lobby")).run(Main.conn);

            String serverid = null;

            for(Object server : cursor) {

                if (((HashMap<String, Boolean>) server).get("joinable")) {
                    serverid = ((HashMap<String, String>) server).get("id");
                }

            }

            try {
                ServerInfo alternativeLobby = Main.GetInstance().getProxy().getServerInfo(serverid);
                e.setTarget(alternativeLobby);

            } catch (NullPointerException error) {
                e.getPlayer().disconnect(new TextComponent("§6● §e§lThe§6§lCookie§e§lMC §6●\n\n§c§cUnable to find a Lobby server to connect you to!\n§7Please contact a staff member if this issue continues.\n\n§7Error code: §fBalancing-19"));
            }

            return;

        } else {
            return;
        }



    }

    @EventHandler
    public void onQuit(PlayerDisconnectEvent e) {

        Main.r.db("InstanceManager").table("sessions").filter(Main.r.hashMap("id", e.getPlayer().getUniqueId().toString())).delete().run(Main.conn);


    }


    @EventHandler
    public void onLogin(net.md_5.bungee.api.event.LoginEvent e) {


        Main.r.db("InstanceManager").table("sessions").insert(
                Main.r.hashMap("id", e.getConnection().getUniqueId().toString())
                        .with("username", e.getConnection().getName())
                        .with("ip", e.getConnection().getAddress().toString().replace("/","").split(":")[0])
                        .with("server", "null")
        ).run(Main.conn);

        System.out.println(e.getConnection().getAddress().toString().replace("\\",""));
        //System.out.println(insert);

    }

}
