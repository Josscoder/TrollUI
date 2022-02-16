package jossc.trollui.type;

import cn.nukkit.Player;
import cn.nukkit.utils.TextFormat;
import jossc.trollui.TrollUIPlugin;

public class LightningStrike extends Trap {

  @Override
  public void init() {}

  @Override
  public String getId() {
    return "Lightning Strike";
  }

  @Override
  public String getImage() {
    return "";
  }

  @Override
  public void execute(Player owner, Player target) {
    api.spawnLightning(target);
    api.burn(target);

    owner.sendMessage(
      TrollUIPlugin.PREFIX +
      TextFormat.GREEN +
      "You struck lightning in " +
      target.getName() +
      "'s face!"
    );
  }

  @Override
  public void close() {}
}
