package jossc.trollui.type;

import cn.nukkit.Player;
import cn.nukkit.inventory.PlayerInventory;
import cn.nukkit.utils.TextFormat;
import jossc.trollui.TrollUIPlugin;

public class DropInventory extends Trap {

  @Override
  public void init() {}

  @Override
  public String getId() {
    return "Drop Inventory";
  }

  @Override
  public String getImage() {
    return "";
  }

  @Override
  public void execute(Player owner, Player target) {
    PlayerInventory inventory = target.getInventory();

    inventory.getContents().values().forEach(target::dropItem);
    inventory.clearAll();
    api.updateInventory(target);

    owner.sendMessage(
      TrollUIPlugin.PREFIX +
      TextFormat.GREEN +
      target.getName() +
      " dropped his entire inventory!"
    );
  }

  @Override
  public void close() {}
}
