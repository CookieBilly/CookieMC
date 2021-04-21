package net.thecookiemc;

import com.rethinkdb.RethinkDB;
import com.rethinkdb.net.Connection;
import com.rethinkdb.net.Cursor;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.PluginManager;
import net.thecookiemc.events.LoginEvent;
import net.thecookiemc.events.Ping;
import sun.security.jca.GetInstance;

import java.util.concurrent.TimeUnit;

public class Main extends Plugin {

    private static Main instance;

    public static Main GetInstance() {
        return instance;
    }

    public static final RethinkDB r = RethinkDB.r;

    public static Connection conn = r.connection().hostname("ip").user("username", "pass").port(28015).connect();

    @Override
    public void onEnable() {


        PluginManager pm = getProxy().getPluginManager();
        pm.registerListener(this, new LoginEvent());
        pm.registerListener(this, new Ping());
        getProxy().getScheduler().schedule(this, () -> Ping.GetMOTD(), 1, 2, TimeUnit.MINUTES);
        instance = this;

    }

}
