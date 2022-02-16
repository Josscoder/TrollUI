package jossc.trollui;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.block.Block;
import cn.nukkit.block.BlockFire;
import cn.nukkit.command.Command;
import cn.nukkit.entity.Entity;
import cn.nukkit.inventory.PlayerInventory;
import cn.nukkit.level.Level;
import cn.nukkit.level.Position;
import cn.nukkit.level.Sound;
import cn.nukkit.math.Vector3;
import cn.nukkit.network.protocol.AddEntityPacket;
import cn.nukkit.network.protocol.DataPacket;
import cn.nukkit.potion.Effect;
import cn.nukkit.utils.TextFormat;
import com.denzelcode.form.FormAPI;
import com.denzelcode.form.element.ImageType;
import com.denzelcode.form.window.SimpleWindowForm;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
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

  public Server getServer() {
    return Server.getInstance();
  }

  public void showSelectPlayerForm(Player player) {
    SimpleWindowForm form = FormAPI.simpleWindowForm(
      null,
      TextFormat.BOLD.toString() + TextFormat.DARK_AQUA + "SELECT A PLAYER",
      "This will be the player you troll"
    );

    getServer()
      .getOnlinePlayers()
      .values()
      .forEach(
        onlinePlayer ->
          form.addButton(onlinePlayer.getName(), onlinePlayer.getName())
      );

    form.addHandler(
      event -> {
        if (event.isClosed()) {
          return;
        }

        Player target = getServer().getPlayer(event.getButton().getName());

        if (target == null) {
          return;
        }

        showTrapListForm(player, target, true);
      }
    );

    form.sendTo(player);
  }

  public void showTrapListForm(
    Player owner,
    Player target,
    boolean haveOldForm
  ) {
    SimpleWindowForm form = FormAPI.simpleWindowForm(
      TextFormat.BOLD.toString() +
      TextFormat.DARK_AQUA +
      "TRAPS LIST" +
      TextFormat.RESET +
      TextFormat.GRAY +
      " | " +
      target.getName()
    );

    if (haveOldForm) {
      form.addButton(
        "back",
        "Back",
        ImageType.PATH,
        "textures/ui/generic_select_button.png"
      );
    }

    traps
      .values()
      .forEach(
        iTrap ->
          form.addButton(
            iTrap.getId(),
            iTrap.getId(),
            (
              iTrap.getImage().startsWith("http")
                ? ImageType.URL
                : ImageType.PATH
            ),
            iTrap.getImage()
          )
      );

    if (haveOldForm && form.getButtons().size() >= 10) {
      form.addButton(
        "back",
        "Back",
        ImageType.PATH,
        "textures/ui/generic_select_button.png"
      );
    }

    form.addHandler(
      event -> {
        if (event.isClosed()) {
          return;
        }

        String buttonName = event.getButton().getName();

        if (buttonName.equals("back") && haveOldForm) {
          showSelectPlayerForm(owner);

          return;
        }

        if (!target.isOnline()) {
          return;
        }

        ITrap trap = getTrap(buttonName);

        if (trap == null) {
          return;
        }

        if (
          trap.hasPermission() && !owner.hasPermission(trap.getPermission())
        ) {
          owner.sendMessage(TextFormat.RED + "You do not have permissions!");

          return;
        }

        trap.execute(owner, target);
      }
    );

    form.sendTo(owner);
  }

  public void close() {
    traps.values().forEach(ITrap::close);
  }

  public void buildCage(Position position, Block block) {
    if (!position.isValid()) {
      return;
    }

    Level level = position.getLevel();

    List<Vector3> positions = new ArrayList<Vector3>() {
      {
        add(new Vector3(position.x, position.y - 1, position.z));

        add(new Vector3(position.x + 1, position.y, position.z));
        add(new Vector3(position.x - 1, position.y, position.z));

        add(new Vector3(position.x, position.y, position.z + 1));
        add(new Vector3(position.x, position.y, position.z - 1));

        add(new Vector3(position.x + 1, position.y + 1, position.z));
        add(new Vector3(position.x - 1, position.y + 1, position.z));

        add(new Vector3(position.x, position.y + 1, position.z + 1));
        add(new Vector3(position.x, position.y + 1, position.z - 1));

        add(new Vector3(position.x, position.y + 2, position.z));
      }
    };

    for (Vector3 value : positions) {
      level.setBlock(value, block, false, true);
    }
  }

  public void broadcastPacket(DataPacket packet) {
    getServer()
      .getOnlinePlayers()
      .values()
      .forEach(player -> player.dataPacket(packet));
  }

  public void spawnLightning(Position position) {
    if (!position.isValid()) {
      return;
    }

    long entityId = Entity.entityCount++;

    AddEntityPacket addEntityPacket = new AddEntityPacket();
    addEntityPacket.entityRuntimeId = entityId;
    addEntityPacket.type = 93; // minecraft:lightning_bolt
    addEntityPacket.x = (float) position.getFloorX();
    addEntityPacket.y = (float) position.getFloorY();
    addEntityPacket.z = (float) position.getFloorZ();

    broadcastPacket(addEntityPacket);

    position
      .getLevel()
      .addSound(position, Sound.AMBIENT_WEATHER_LIGHTNING_IMPACT, 1F, 1F);
  }

  public void burn(Position position) {
    if (!position.isValid()) {
      return;
    }

    Level level = position.getLevel();

    BlockFire fire = new BlockFire();
    fire.x = position.x;
    fire.y = position.y;
    fire.z = position.z;
    fire.level = level;

    level.setBlock(fire, fire, true);
    level.scheduleUpdate(
      fire,
      fire.tickRate() + ThreadLocalRandom.current().nextInt(10)
    );
  }

  public void applyAllEffects(Player player, int seconds) {
    for (int i = 1; i <= 29; i++) {
      Effect effect = Effect.getEffect(i);

      if (effect != null) {
        effect.setDuration(20 * seconds);
        effect.setAmplifier(2);
        effect.setVisible(false);

        player.addEffect(effect);
        player.sendPotionEffects(player);
      }
    }
  }
}
