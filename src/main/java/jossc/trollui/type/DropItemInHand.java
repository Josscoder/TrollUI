package jossc.trollui.type;

import cn.nukkit.Player;
import cn.nukkit.block.BlockAir;
import cn.nukkit.utils.TextFormat;
import jossc.trollui.TrollUIPlugin;

public class DropItemInHand extends Trap {

  @Override
  public void init() {}

  @Override
  public String getImage() {
    return "https://i.imgur.com/piQOP6U.png";
  }

  @Override
  public void execute(Player owner, Player target) {
    target.dropItem(target.getInventory().getItemInHand());
    target.getInventory().setItemInHand(new BlockAir().toItem());
    api.updateInventory(target);

    owner.sendMessage(
      TrollUIPlugin.PREFIX +
      TextFormat.GREEN +
      target.getName() +
      " has dropped the item in hand!"
    );
  }

  @Override
  public void close() {}
}
