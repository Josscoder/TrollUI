package jossc.trollui.type;

import cn.nukkit.Player;
import cn.nukkit.block.BlockBedrock;
import cn.nukkit.block.BlockTNT;
import cn.nukkit.utils.TextFormat;
import jossc.trollui.TrollUIPlugin;

public class TNTCage extends Trap {

  @Override
  public void init() {}

  @Override
  public String getId() {
    return "TNT Cage";
  }

  @Override
  public String getImage() {
    return "";
  }

  @Override
  public void execute(Player owner, Player target) {
    api.buildCage(target, new BlockTNT());
    api.spawnLightning(target.add(0, 1));
    api.burn(target.add(0, 1));

    owner.sendMessage(
      TrollUIPlugin.PREFIX +
      TextFormat.GREEN +
      target.getName() +
      " is in a tnt cage now, tik tak boom!"
    );
  }

  @Override
  public void close() {}
}
