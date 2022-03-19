package jossc.trollui.type;

import cn.nukkit.Player;
import cn.nukkit.utils.TextFormat;
import jossc.trollui.TrollUIPlugin;

public class ShuffleInventory extends Trap {

  @Override
  public void init() {}

  @Override
  public String getImage() {
    return "textures/ui/inventory_icon.png";
  }

  @Override
  public void execute(Player owner, Player target) {
    api.shuffleInventory(target);

    owner.sendMessage(
      TrollUIPlugin.PREFIX +
      TextFormat.GREEN +
      "You shuffled " +
      target.getName() +
      "'s inventory!"
    );
  }

  @Override
  public void close() {}
}
