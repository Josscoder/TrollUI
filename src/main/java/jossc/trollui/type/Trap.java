package jossc.trollui.type;

import jossc.trollui.API;
import jossc.trollui.TrollUIPlugin;

public abstract class Trap implements ITrap {

  protected final API api;

  public Trap() {
    api = TrollUIPlugin.getInstance().getApi();
    init();
  }

  private String amendSentences(String oldString) {
    StringBuilder builder = new StringBuilder();

    char[] oldStringToChar = oldString.toCharArray();

    for (int i = 0; i < oldStringToChar.length; i++) {
      if (oldStringToChar[i] >= 'A' && oldStringToChar[i] <= 'Z') {
        oldStringToChar[i] = (char) (oldStringToChar[i] + 32);

        if (i != 0) {
          builder.append(" ");
        }

        builder.append(oldStringToChar[i]);
      } else {
        builder.append(oldStringToChar[i]);
      }
    }

    return builder.toString();
  }

  @Override
  public String getId() {
    return amendSentences(getClass().getSimpleName());
  }

  @Override
  public String getIdToLowerCase() {
    return getId().toLowerCase();
  }

  @Override
  public String getIdToUpperCase() {
    return getId().toUpperCase();
  }
}
