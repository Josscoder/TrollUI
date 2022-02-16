package jossc.trollui.type;

import cn.nukkit.Player;
import cn.nukkit.entity.Entity;
import cn.nukkit.entity.item.EntityPrimedTNT;
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
    for (int i = 0; i <= 4; i++) {
      EntityPrimedTNT primedTNT = new EntityPrimedTNT(
        target.getChunk(),
        Entity.getDefaultNBT(target.add(0, 1))
      );
      primedTNT.spawnToAll();
    }

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
