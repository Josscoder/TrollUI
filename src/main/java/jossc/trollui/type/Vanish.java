package jossc.trollui.type;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.EventPriority;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerJoinEvent;
import cn.nukkit.utils.TextFormat;
import java.util.ArrayList;
import java.util.List;
import jossc.trollui.TrollUIPlugin;

public class Vanish extends Trap implements Listener {

  private final List<Player> storage = new ArrayList<>();

  @Override
  public void init() {
    api.registerListener(this);
  }

  @Override
  public String getImage() {
    return "textures/ui/user_icon.png";
  }

  @EventHandler(priority = EventPriority.HIGHEST)
  public void onJoin(PlayerJoinEvent event) {
    Player player = event.getPlayer();
    storage.forEach(player::hidePlayer);
  }

  @Override
  public void execute(Player owner, Player target) {
    String subfix;
    String connector;

    if (storage.contains(target)) {
      api.showPlayerToOnlinePlayers(target);
      storage.remove(target);
      subfix = "showed";
      connector = "to";
    } else {
      api.hidePlayerFromOnlinePlayers(target);
      storage.add(target);
      subfix = "hid";
      connector = "from";
    }

    owner.sendMessage(
      TrollUIPlugin.PREFIX +
      TextFormat.GREEN +
      "You " +
      subfix +
      " " +
      target.getName() +
      " " +
      connector +
      " the players!"
    );
  }

  @Override
  public void close() {}
}
