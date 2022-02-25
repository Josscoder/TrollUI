package jossc.trollui.type;

import cn.nukkit.Player;
import cn.nukkit.entity.mob.EntityCreeper;
import cn.nukkit.utils.TextFormat;
import java.util.List;
import jossc.trollui.TrollUIPlugin;

public class Creepers extends Trap {

  @Override
  public void init() {}

  @Override
  public String getImage() {
    return "textures/items/egg_creeper.png";
  }

  @Override
  public void execute(Player owner, Player target) {
    List<EntityCreeper> creepers = api.spawnCreepers(target, 6);

    creepers.forEach(entityCreeper -> entityCreeper.setPowered(true));

    owner.sendMessage(
      TrollUIPlugin.PREFIX +
      TextFormat.GREEN +
      "You have spawned creepers around " +
      target.getName()
    );
  }

  @Override
  public void close() {}
}
