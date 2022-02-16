package jossc.trollui.type;

import cn.nukkit.Player;
import cn.nukkit.inventory.PlayerInventory;
import cn.nukkit.item.Item;
import cn.nukkit.utils.TextFormat;
import java.util.*;
import jossc.trollui.TrollUIPlugin;

public class ShuffleInventory extends Trap {

  @Override
  public void init() {}

  @Override
  public String getId() {
    return "Shuffle Inventory";
  }

  @Override
  public String getImage() {
    return "";
  }

  @Override
  public void execute(Player owner, Player target) {
    PlayerInventory inventory = target.getInventory();

    List<Item> list = new ArrayList<>(inventory.getContents().values());
    Collections.shuffle(list);

    Map<Integer, Item> contents = new HashMap<>();

    int i = 0;
    for (Item item : list) {
      contents.put(i, item);
      i++;
    }

    inventory.setContents(contents);

    api.updateInventory(target);

    owner.sendMessage(
      TrollUIPlugin.PREFIX +
      TextFormat.GREEN +
      "You shuffled " +
      target.getName() +
      "'s inventory!"
    );
  }

  @Override
  public void close() {}
}
