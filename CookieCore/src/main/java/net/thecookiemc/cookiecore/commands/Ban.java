package net.thecookiemc.cookiecore.commands;

import com.rethinkdb.net.Cursor;
import net.thecookiemc.cookiecore.Main;
import net.thecookiemc.cookiecore.Methods.PunishMSG;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.util.HashMap;
import java.util.Random;

@SuppressWarnings("unchecked")
public class Ban implements Listener, CommandExecutor {


    public static String createBanID() {

        Random r = new Random();

        StringBuilder sb = new StringBuilder();
        final String chars =
                "12345678912345678912456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        for (int i = 0; i < 8; i++) {
            sb.append(chars.charAt(r.nextInt(chars.length())));
        }
        long banIDPreexists =
                Main.r.db("Punishments").table("punishments").filter(row -> row.g(
                        "id").eq(
                        String.valueOf(sb))).count().run(Main.conn);
        if (banIDPreexists >= 1) {
            createBanID();
        } else {
            return String.valueOf(sb);
        }
        return null;
    }


    public static String reason(String code) {
        Cursor cursor = Main.r.db("Punishments").table("reasons")
                .filter(doc -> doc.g("id").match(code)).run(Main.conn);

        for (Object ban : cursor) {
            return ((HashMap<String, String>) ban).get("mask");
        }
        return "null";
    }

    public static String type(String code) {
        Cursor cursor = Main.r.db("Punishments").table("reasons")
                .filter(doc -> doc.g("id").match(code)).run(Main.conn);

        for (Object ban : cursor) {
            return ((HashMap<String, String>) ban).get("type");
        }
        return "null";
    }

    public static Long getlength(String code) {
        Cursor cursor = Main.r.db("Punishments").table("reasons")
                .filter(doc -> doc.g("id").match(code)).run(Main.conn);

        for (Object ban : cursor) {
            return ((HashMap<String, Long>) ban).get("length");
        }
        return null;
    }

    public static String getNotes(String code) {
        Cursor cursor = Main.r.db("Punishments").table("reasons")
                .filter(doc -> doc.g("id").match(code)).run(Main.conn);

        for (Object ban : cursor) {
            return ((HashMap<String, String>) ban).get("note");
        }
        return null;
    }


    public static String action(String code) {
        Cursor cursor = Main.r.db("Punishments").table("reasons")
                .filter(doc -> doc.g("id").match(code)).run(Main.conn);

        for (Object ban : cursor) {
            return ((HashMap<String, String>) ban).get("action");
        }

        return "null";
    }


    @Override
    public boolean onCommand(CommandSender sender, Command cmd,
                             String label, String[] args) {


        if (sender instanceof Player) {
            if (cmd.getName().equalsIgnoreCase("ban")) {

                if (sender.hasPermission("cookiecore.command.ban")) {


                    // Check for both arguments
                    if (args.length < 2) {
                        sender.sendMessage(
                                "§8┃ §bStaff §8┃ §cInvalid usage!" +
                                        " §7Use §e/" + cmd.getName() +
                                        " [user] [code]");

                    } else {

                        String reason = reason(args[1]);
                        Long length = getlength(args[1]);

                        if (reason.equals("null")) {
                            sender.sendMessage(
                                    "§8┃ §bStaff §8┃ §cInvalid punishment code, see guidelines for info.");
                            return true;
                        }

                        String action = action(args[1]);
                        if (action.equals("null")) {
                            sender.sendMessage(
                                    "§8┃ §bStaff §8┃ §cInvalid punishment code, see guidelines for info.");
                            return true;
                        }

                        // Get player UUID (in string form)
                        String punishedUUID;
                        if (Bukkit.getPlayer(args[0]) == null) {
                            punishedUUID =
                                    String.valueOf(
                                            Bukkit.getOfflinePlayer(args[0]).getUniqueId());
                        } else {
                            punishedUUID =
                                    String.valueOf(Bukkit.getPlayer(args[0]).getUniqueId());
                        }

                        // Get the time the player was punished at (milliseconds)
                        long timePunished =
                                System.currentTimeMillis();

                        // Get the username of the player who punished the user
                        String whoPunished =
                                String.valueOf(((Player) sender).getUniqueId());

                        if (punishedUUID == null) {
                            sender.sendMessage(
                                    "§8┃ §bStaff §8┃ §7Unable to find player §f" + args[0]);
                        } else {

                            // TODO - Auto gen ID, gen expires

                            String id = createBanID();


                            if(length != -1) {
                                length = System.currentTimeMillis() + length;
                            }


                            Main.r.db("Punishments").table("punishments").insert(
                                    Main.r.hashMap
                                            ("UUID", punishedUUID).with("username", args[0])
                                            .with("punisher", whoPunished)
                                            .with("type", action)
                                            .with("category", type(args[1]))
                                            .with("occurred", timePunished)
                                            .with("expires", length)
                                            .with("active", true)
                                            .with("note", getNotes(args[1]))
                                            .with("id", id)
                                            .with("reason", reason)
                                            .with("instance", Main.SERVERID)
                            ).run(Main.conn);
                            sender.sendMessage(
                                    "§8┃ §bStaff §8┃ §aPunishment against §e" + args[0] +
                                            "§a has been applied!");

                            System.out.println(action);

                            if (action.equals("ban")) {

                                if (Bukkit.getPlayer(args[0]) != null) {
                                    Bukkit.getPlayer(args[0])
                                            .kickPlayer(
                                                    PunishMSG.PunishMSG(reason, action, length, id));
                                }
                            } else if (action.equals("kick")) {
                                if (Bukkit.getPlayer(args[0]) != null) {
                                    Bukkit.getPlayer(args[0])
                                            .kickPlayer(
                                                    PunishMSG.PunishMSG(reason, action, length, id));
                                }
                            }

                        }

                    }
                } else {

                    sender.sendMessage(
                            "§8┃ §c✖ §8┃ §cYou do not have permission for this!");
                    return true;
                }


            }
        }
        return true;
    }
}
