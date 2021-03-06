package jossc.trollui.type;

import cn.nukkit.Player;
import cn.nukkit.utils.TextFormat;
import jossc.trollui.TrollUIPlugin;

public class Burn extends Trap {

  @Override
  public void init() {}

  @Override
  public String getImage() {
    return "textures/blocks/fire_1_placeholder.png";
  }

  @Override
  public void execute(Player owner, Player target) {
    target.setOnFire(10);

    owner.sendMessage(
      TrollUIPlugin.PREFIX +
      TextFormat.GREEN +
      target.getName() +
      " is now in fire for 10 seconds!"
    );
  }

  @Override
  public void close() {}
}
