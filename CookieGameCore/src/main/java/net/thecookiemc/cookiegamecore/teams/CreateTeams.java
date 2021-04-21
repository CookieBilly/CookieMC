package net.thecookiemc.cookiegamecore.teams;

import java.util.*;

public final class CreateTeams {

  public static String getRandomElement(List<String> list) {
    Random rand = new Random();
    String randomElement = list.get(rand.nextInt(list.size()));
    list.remove(randomElement);
    return list.get(rand.nextInt(list.size()));
  }

  static List<String> convertToList(Collection collection) {
    return new ArrayList<>(collection);
  }

  static List<List<String>> splitRandomly(List<String> input,
                                          int numberOfSplits) {
    if (input.size() % numberOfSplits != 0) {
      throw new IllegalArgumentException("Not enough players... start " +
          "cancelled");
    }

    List<String> copy = new ArrayList<>(input);
    Collections.shuffle(copy);
    int chunkSize = input.size() / numberOfSplits;

    List<List<String>> results = new ArrayList<>();
    for (int i = 0; i < numberOfSplits; i++) {
      List<String> subList = copy.subList(i * chunkSize, (i + 1) * chunkSize);
      results.add(subList);
    }
    return results;
  }

//  public static Map<String, List> createNewTeam() {
//    Object[] allPlayer =
//        Bukkit.getServer().getOnlinePlayers().toArray();
//    for (int i = 0; allPlayer.length == 0; i++) {
//      Object e = allPlayer[i];
//    }
//    return null;
//  }
}
