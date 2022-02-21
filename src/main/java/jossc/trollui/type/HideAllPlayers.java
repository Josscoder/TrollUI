package jossc.trollui.type;

import cn.nukkit.Player;
import cn.nukkit.utils.TextFormat;
import java.util.ArrayList;
import java.util.List;
import jossc.trollui.TrollUIPlugin;

public class HideAllPlayers extends Trap {

  private final List<Player> storage = new ArrayList<>();

  @Override
  public void init() {}

  @Override
  public String getId() {
    return "Hide All Players";
  }

  @Override
  public void execute(Player owner, Player target) {
    String subfix;

    if (storage.contains(target)) {
      api.showAllPlayers(target);
      storage.remove(target);
      subfix = "showed";
    } else {
      api.hiddeAllPlayers(target);
      storage.add(target);
      subfix = "hided";
    }

    owner.sendMessage(
      TrollUIPlugin.PREFIX +
      TextFormat.GREEN +
      "You " +
      subfix +
      " all the players to " +
      target.getName()
    );
  }

  @Override
  public void close() {}
}
