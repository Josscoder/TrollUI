package jossc.trollui;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.inventory.PlayerInventory;
import java.util.*;
import jossc.trollui.type.ITrap;
import lombok.Getter;

public class API {

  @Getter
  private final TrollUIPlugin pluginInstance;

  @Getter
  private final Map<String, ITrap> traps = new LinkedHashMap<>();

  @Getter
  private final List<String> blockedTraps = new LinkedList<>();

  public API(TrollUIPlugin pluginInstance) {
    this.pluginInstance = pluginInstance;
  }

  public ITrap getTrap(String id) {
    return traps.get(id);
  }

  public boolean existTrap(String id) {
    return traps.containsKey(id);
  }

  public void registerTrap(ITrap... trap) {
    Arrays.stream(trap).forEach(iTrap -> traps.put(iTrap.getId(), iTrap));
  }

  public void blockTrap(String id) {
    ITrap trap = traps.get(id);

    if (trap == null) {
      return;
    }

    trap.close();

    blockedTraps.add(id);
    traps.remove(id);
  }

  public boolean trapIsBlocked(String id) {
    return blockedTraps.contains(id);
  }

  public void registerCommand(Command command) {
    pluginInstance
      .getServer()
      .getCommandMap()
      .register(command.getName(), command);
  }

  public void updateArmorContents(Player player) {
    PlayerInventory inventory = player.getInventory();
    inventory.sendArmorContents(player);
  }

  public void updateInventory(Player player) {
    PlayerInventory inventory = player.getInventory();
    updateArmorContents(player);
    inventory.sendContents(player);
    inventory.sendHeldItem(player);
  }

  public void close() {
    traps.values().forEach(ITrap::close);
  }
}
