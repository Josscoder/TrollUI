package jossc.trollui.type;

import jossc.trollui.API;
import jossc.trollui.TrollUIPlugin;

public abstract class Trap implements ITrap {

  protected final API api;

  public Trap() {
    api = TrollUIPlugin.getInstance().getApi();
    init();
  }

  @Override
  public String getId() {
    return getClass().getSimpleName();
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
