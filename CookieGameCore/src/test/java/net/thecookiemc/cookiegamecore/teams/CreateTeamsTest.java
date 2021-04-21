package net.thecookiemc.cookiegamecore.teams;

import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

public class CreateTeamsTest {

  @Test
  public void getRandomElement() {
  }

  @Test
  public void splitRandomlyThreeElements_fail() {
    List<String> input = Lists.newArrayList("foo", "bar", "baz");
    IllegalArgumentException exception =
        assertThrows(IllegalArgumentException.class,
            () -> CreateTeams.splitRandomly(input,
                4));
    // assertTrue(exception.getMessage().contains("does not split equally"));
  }

  @Test
  public void splitRandomlyFourElements_succeed() {
    List<String> input = Lists.newArrayList("foo", "bar", "baz", "qux");
    List<List<String>> result = CreateTeams.splitRandomly(input, 4);
    assertTrue(result.size() == 4);
  }

  @Test
  public void splitRandomlyFourElementsSplitIntoTwo_succeed() {
    List<String> input = Lists.newArrayList("foo", "bar", "baz", "qux");
    List<List<String>> result = CreateTeams.splitRandomly(input, 2);
    assertTrue(result.size() == 2);
    System.out.println(result);
  }

  @Test
  public void splitRandomlyFourElementsSplitIntoOne_succeed() {
    List<String> input = Lists.newArrayList("foo", "bar", "baz", "qux");
    List<List<String>> result = CreateTeams.splitRandomly(input, 1);
    assertTrue(result.size() == 1);
  }

  @Test
  public void createNewTeam() {
  }
}