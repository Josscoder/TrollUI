package jossc.trollui.utils;

import java.util.concurrent.ThreadLocalRandom;

public class MathUtils {

  public static int rand(int min, int max) {
    return ThreadLocalRandom.current().nextInt(min, max);
  }
}
