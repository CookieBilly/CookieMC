package net.thecookiemc.cookiecore.Methods;

import sun.security.util.Length;

public class PunishMSG {

  public static String PunishMSG(String reason, String type, Long length, String id) {

    String message = "§6● §e§lThe§6§lCookie§e§lMC §6●\n\n§cYou are currently ";

    if(type.equals("ban")) {
      message+="banned from the server!";
    }

    if(type.equals("kick")) {
      message+="kicked from the server!";
    }

    Long expires = length - System.currentTimeMillis();
    String remainingstring = "";


    double remaining = expires;

    if(length != -1) {
      double days = Math.floor(remaining / 86400000);
      remaining = remaining - (days * 86400000);

      double hours = Math.floor(remaining / 3600000);
      remaining = remaining - (hours * 3600000);

      double minutes = Math.floor(remaining / 60000);
      remaining = remaining - (minutes * 60000);

      remainingstring+="§7This will expire in §f";

      if(days > 0) {
        remainingstring+= (int) days + " days, ";
      }

      if(hours > 0) {
        remainingstring+=(int) hours+" hours, ";
      }

      if(minutes > 0) {
        remainingstring+=(int) minutes+" minutes ";
      } else {
        remainingstring+=(int) remaining / 1000 +" seconds";
      }

    } else {

      remainingstring+="§7This punishment will not expire (Permanent)";

    }


    String appealmsg = "\n\n§7If you think this punishment is unfair:";
    if(reason.equalsIgnoreCase("Possible Compromised Account")) {
      appealmsg = "\n\n§7Please secure your account, then";
    }
    if(reason.equalsIgnoreCase("Inappropriate Name")) {
      appealmsg = "\n\n§7Please change your name, then";
    }
    if(reason.equalsIgnoreCase("Inappropriate Cosmetics")) {
      appealmsg = "\n\n§7Please change your cosmetics, then";
    }
    if(reason.equalsIgnoreCase("Inappropriate Skin")) {
      appealmsg = "\n\n§7Please change your skin, then";
    }


    message+="\n§7For: §f"+reason+"\n\n"+remainingstring+appealmsg+"\n" +
        "Appeal at §b§nhttp://thecookiemc.net/appeal/"+id;

    return message;
  }
}
