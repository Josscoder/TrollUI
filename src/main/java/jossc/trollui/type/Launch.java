package jossc.trollui.type;

import cn.nukkit.Player;
import cn.nukkit.math.Vector3;
import cn.nukkit.utils.TextFormat;
import jossc.trollui.TrollUIPlugin;

public class Launch extends Trap {

  @Override
  public void init() {}

  @Override
  public String getImage() {
    return "";
  }

  @Override
  public void execute(Player owner, Player target) {
    target.setMotion(new Vector3(0, 3));

    owner.sendMessage(
      TrollUIPlugin.PREFIX +
      TextFormat.GREEN +
      target.getName() +
      " tta was thrown into the sky!"
    );
  }

  @Override
  public void close() {}
}
