package jossc.trollui.type;

import cn.nukkit.Player;
import cn.nukkit.utils.TextFormat;
import jossc.trollui.TrollUIPlugin;

public class AllEffects extends Trap {

  @Override
  public void init() {}

  @Override
  public String getId() {
    return "All Effects";
  }

  @Override
  public String getImage() {
    return "";
  }

  @Override
  public void execute(Player owner, Player target) {
    api.applyAllEffects(target, 10);

    owner.sendMessage(
      TrollUIPlugin.PREFIX +
      TextFormat.GREEN +
      target.getName() +
      " now has all the effects!"
    );
  }

  @Override
  public void close() {}
}
