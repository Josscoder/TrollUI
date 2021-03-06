package jossc.trollui.type;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.block.BlockBreakEvent;
import cn.nukkit.event.block.BlockPlaceEvent;
import cn.nukkit.event.player.PlayerQuitEvent;
import cn.nukkit.level.Explosion;
import cn.nukkit.level.Position;
import cn.nukkit.utils.TextFormat;
import java.util.ArrayList;
import java.util.List;
import jossc.trollui.TrollUIPlugin;

public class NextBlockExplodes extends Trap implements Listener {

  private final List<Player> storage = new ArrayList<>();

  @Override
  public void init() {
    api.registerListener(this);
  }

  @Override
  public String getImage() {
    return "https://i.imgur.com/u85YEt6.png";
  }

  @EventHandler
  public void onQuit(PlayerQuitEvent event) {
    Player player = event.getPlayer();

    storage.remove(player);
  }

  @EventHandler
  public void onBlockBreak(BlockBreakEvent event) {
    handle(event.getPlayer(), event.getBlock());
  }

  @EventHandler
  public void onBlockPlace(BlockPlaceEvent event) {
    handle(event.getPlayer(), event.getBlock());
  }

  private void handle(Player player, Position position) {
    if (!storage.contains(player)) {
      return;
    }

    storage.remove(player);

    new Explosion(position, 10, null).explode();
  }

  @Override
  public void execute(Player owner, Player target) {
    if (!storage.contains(target)) {
      storage.add(target);
    }

    owner.sendMessage(
      TrollUIPlugin.PREFIX +
      TextFormat.GREEN +
      " The next block that " +
      target.getName() +
      " touches will explode!"
    );
  }

  @Override
  public void close() {}
}
