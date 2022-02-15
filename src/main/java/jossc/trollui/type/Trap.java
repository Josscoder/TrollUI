package jossc.trollui.type;

public abstract class Trap implements ITrap {

  public Trap() {
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
