package jossc.trollui.type;

import cn.nukkit.Player;
import cn.nukkit.level.Location;
import cn.nukkit.utils.TextFormat;
import jossc.trollui.TrollUIPlugin;
import jossc.trollui.utils.MathUtils;

public class LookRandom extends Trap {

  @Override
  public void init() {}

  @Override
  public String getId() {
    return "Look Random";
  }

  @Override
  public String getImage() {
    return "";
  }

  @Override
  public void execute(Player owner, Player target) {
    target.teleport(
      new Location(
        target.x,
        target.y,
        target.z,
        MathUtils.rand(0, 180),
        MathUtils.rand(0, 180)
      )
    );

    owner.sendMessage(
      TrollUIPlugin.PREFIX +
      TextFormat.GREEN +
      target.getName() +
      " snapped her head!"
    );
  }

  @Override
  public void close() {}
}
