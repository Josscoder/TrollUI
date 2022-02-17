package jossc.trollui.type;

import cn.nukkit.Player;
import cn.nukkit.utils.TextFormat;
import jossc.trollui.TrollUIPlugin;

public class ChangeTime extends Trap {

  @Override
  public void init() {}

  @Override
  public String getId() {
    return "Change Time";
  }

  @Override
  public String getImage() {
    return "textures/ui/time_4sunset.png";
  }

  @Override
  public void execute(Player owner, Player target) {
    api.changeTimeSeveralTimes(target);

    owner.sendMessage(
      TrollUIPlugin.PREFIX +
      TextFormat.GREEN +
      target.getName() +
      " time cycle suddenly changes!"
    );
  }

  @Override
  public void close() {}
}
