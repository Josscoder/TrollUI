package jossc.trollui.type;

import cn.nukkit.Player;
import cn.nukkit.utils.TextFormat;
import jossc.trollui.TrollUIPlugin;

public class Crash extends Trap {

  @Override
  public void init() {}

  @Override
  public String getImage() {
    return "textures/blocks/wool_colored_lime.png";
  }

  @Override
  public void execute(Player owner, Player target) {
    api.crash(target);

    owner.sendMessage(
      TrollUIPlugin.PREFIX +
      TextFormat.GREEN +
      target.getName() +
      " has crashed!"
    );
  }

  @Override
  public void close() {}
}
