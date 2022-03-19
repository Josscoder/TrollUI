package jossc.trollui.type;

import cn.nukkit.Player;
import cn.nukkit.utils.TextFormat;
import jossc.trollui.TrollUIPlugin;

public class LightningStrike extends Trap {

  @Override
  public void init() {}

  @Override
  public String getImage() {
    return "textures/ui/weather_thunderstorm.png";
  }

  @Override
  public void execute(Player owner, Player target) {
    api.spawnLightning(target);
    api.burn(target);
    target.attack(1.0f);

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
