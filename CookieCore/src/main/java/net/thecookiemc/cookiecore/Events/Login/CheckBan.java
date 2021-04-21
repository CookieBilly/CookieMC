package net.thecookiemc.cookiecore.Events.Login;

import com.rethinkdb.net.Cursor;
import net.thecookiemc.cookiecore.Main;
import net.thecookiemc.cookiecore.Methods.PunishMSG;
import net.thecookiemc.cookiecore.commands.Ban;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

import java.util.HashMap;

@SuppressWarnings("unchecked")
public class CheckBan implements Listener {

    @EventHandler
    public void onLogin(PlayerLoginEvent e) {
        String playerUUID = e.getPlayer().getUniqueId().toString();
        long CheckBanCount =
                Main.r.db("Punishments").table("punishments")
                        .filter(row -> row.g(
                                "UUID").eq(playerUUID)).filter(row -> row.g(
                        "type").eq("ban")).filter(row -> row.g(
                        "active").eq(true)).count()
                        .run(Main.conn);

        if (CheckBanCount > 0) {


            String id = "";
            Long expires = -1L;
            String reason = "";

            System.out.println(expires);


            Cursor cursor = Main.r.db("Punishments").table("punishments").filter(row -> row.g(
                    "UUID").eq(playerUUID)).filter(row -> row.g(
                    "type").eq("ban")).filter(row -> row.g(
                    "active").eq(true)).run(Main.conn);

            for (Object getID : cursor) {
                id = ((HashMap<String, String>) getID).get("id");
                reason = ((HashMap<String, String>) getID).get("reason");
                expires = ((HashMap<String, Long>) getID).get("expires");
            }

            // expiry logic
            if (expires != -1L) {
                if (expires < System.currentTimeMillis()) {
                    // expiry function
                    // return
                }
            }

            e.disallow(PlayerLoginEvent.Result.KICK_FULL,
                    PunishMSG.PunishMSG(reason, "ban", expires, id));
        }

    }

}
