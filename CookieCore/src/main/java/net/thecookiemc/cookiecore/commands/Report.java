package net.thecookiemc.cookiecore.commands;

import com.rethinkdb.net.Cursor;
import net.thecookiemc.cookiecore.Main;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Random;

@SuppressWarnings("unchecked")
public class Report implements CommandExecutor {

  public static String createReportID() {

    Random r = new Random();

    StringBuilder sb = new StringBuilder();
    final String chars =
        "12345678912345678912456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    for (int i = 0; i < 10; i++) {
      sb.append(chars.charAt(r.nextInt(chars.length())));
    }
    long reportIDAlreadyExists =
        Main.r.db("Punishments").table("reports").filter(row -> row.g(
            "id").eq(
            String.valueOf(sb))).count().run(Main.conn);
    if (reportIDAlreadyExists >= 1) {
      createReportID();
    } else {
      return String.valueOf(sb);
    }
    return null;
  }


  @Override
  public boolean onCommand(CommandSender sender, Command cmd,
                           String label, String[] args) {
    if (sender instanceof Player) {

      // Player reporting logic
      if (args.length < 2) {
        sender.sendMessage(
            "§8┃ §9Report §8┃ §cInvalid usage! §7Use §e/report [name] [reason]");
      } else {

        if (args[1].equalsIgnoreCase("chat")) {
          sender.sendMessage("soontm");
        }
        if (args[0].length() > 16) {
          sender.sendMessage(
              "§8┃ §9Report §8┃ §cSorry! §7You have entered an invalid username.");
        } else {
          String suspectUUID;
          String reporterUUID =
              String.valueOf(((Player) sender).getUniqueId());
          String status = "pending";

          long timeOfReport = System.currentTimeMillis();


          if (Bukkit.getPlayer(args[0]) != null) {
            suspectUUID =
                String.valueOf(Bukkit.getPlayer(args[0]).getUniqueId());
          } else {
            suspectUUID =
                String.valueOf(
                    Bukkit.getOfflinePlayer(args[0]).getUniqueId());
          }

          if (suspectUUID != null && reporterUUID != null) {
            System.out.println(args[0] + " " + sender.getName());

            long reporterActiveReportCount =
                Main.r.db("Punishments").table("activereports")
                    .filter(row -> row.g(
                        "reporterUUID").eq(reporterUUID)).count()
                    .run(Main.conn);
            if (reporterActiveReportCount <= 25) {
              if (!args[0].equalsIgnoreCase(sender.getName())) {


                // Check if there is already an active report against the suspect
                long reportIDAlreadyExists =
                    Main.r.db("Punishments").table("activereports")
                        .filter(row -> row.g(
                            "suspectUUID").eq(suspectUUID)).count()
                        .run(Main.conn);
                if (!(reportIDAlreadyExists >= 1)) {
                  // Write to activereports table
                  String reportID = createReportID();
                  Main.r.db("Punishments").table("activereports").insert(
                      Main.r.hashMap
                          ("suspectUUID", suspectUUID)
                          .with("suspectUsername", args[0])
                          .with("reporterUUID", reporterUUID)
                          .with("status", status)
                          .with("timeOfReport", timeOfReport)
                          .with("processedByUUID", null)
                          .with("id", reportID)
                          .with("timeProcessed", null)
                          .with("reason", "hahayes insert report reason")
                          .with("instance", Main.SERVERID)
                  ).run(Main.conn);

                  // Write report to reports table
                  Main.r.db("Punishments").table("reports").insert(
                      Main.r.hashMap
                          ("suspectUUID", suspectUUID)
                          .with("suspectUsername", args[0])
                          .with("reporterUUID", reporterUUID)
                          .with("status", status)
                          .with("timeOfReport", timeOfReport)
                          .with("processedByUUID", null)
                          .with("id", reportID)
                          .with("timeProcessed", null)
                          .with("reason", "hahayes insert report reason")
                          .with("instance", Main.SERVERID)
                  ).run(Main.conn);


                  sender
                      .sendMessage(
                          "§8┃ §9Report §8┃ §7You have opened a report against §f" +
                              args[0] +
                              "\n§8┃ §9Report §8┃ §7Report ID: §f#" + reportID);
                } else {

                  // I have 0 idea what on earth you were trying before lol
                    // neither do i

                  Cursor cursor =
                      Main.r.db("Punishments").table("activereports")
                          .filter(row -> row.g("suspectUUID").eq(suspectUUID))
                          .run(Main.conn);

                  for (Object report : cursor) {

                    sender
                        .sendMessage(
                            "§8┃ §9Report §8┃ §7You have opened a report against §f" +
                                args[0] +
                                "\n§8┃ §9Report §8┃ §7Report ID: §f#" +
                                ((HashMap<String, Boolean>) report).get("id"));

                    return true;
                  }
                }
              } else {
                sender
                    .sendMessage(
                        "§8┃ §dReports §8┃ §cSorry! §7You are unable to report yourself.");
              }
            } else {
              sender.sendMessage(
                  "§8┃ §dReports §8┃ §cSorry! §7You have reached the open report limit.");
            }


          } else {
            sender
                .sendMessage("§8┃ §9Reports §8┃ §cSomething went wrong!");
          }
        }
      }
    }
    return true;
  }
}
