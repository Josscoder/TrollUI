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

public class HideAllPlayers extends Trap implements Listener {

  private final List<Player> storage = new ArrayList<>();

  @Override
  public void init() {
    api.registerListener(this);
  }

  @Override
  public String getImage() {
    return "textures/ui/dressing_room_skins.png";
  }

  @EventHandler(priority = EventPriority.HIGHEST)
  public void onJoin(PlayerJoinEvent event) {
    Player player = event.getPlayer();
    if (!storage.contains(player)) {
      return;
    }

    api.hideOnlinePlayersToPlayer(player);
  }

  @Override
  public void execute(Player owner, Player target) {
    String subfix;

    if (storage.contains(target)) {
      api.showOnlinePlayersToPlayer(target);
      storage.remove(target);
      subfix = "showed";
    } else {
      api.hideOnlinePlayersToPlayer(target);
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
