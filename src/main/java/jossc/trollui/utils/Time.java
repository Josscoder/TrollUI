package jossc.trollui.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Time {
  TIME_DAY(0),
  TIME_NOON(6000),
  TIME_SUNSET(12000),
  TIME_NIGHT(14000),
  TIME_MIDNIGHT(18000),
  TIME_SUNRISE(23000),
  TIME_FULL(24000);

  private final int time;
}
