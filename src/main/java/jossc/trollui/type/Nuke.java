package jossc.trollui.type;

import cn.nukkit.Player;
import cn.nukkit.utils.TextFormat;
import jossc.trollui.TrollUIPlugin;

public class Nuke extends Trap {

  @Override
  public void init() {}

  @Override
  public String getImage() {
    return "";
  }

  @Override
  public void execute(Player owner, Player target) {
    api.spawnNuke(target);

    owner.sendMessage(
      TrollUIPlugin.PREFIX +
      TextFormat.GREEN +
      "A nuclear weapon is about to explode near " +
      target.getName() +
      "!"
    );
  }

  @Override
  public void close() {}
}
