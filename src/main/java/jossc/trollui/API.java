package jossc.trollui;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.block.Block;
import cn.nukkit.block.BlockFire;
import cn.nukkit.command.Command;
import cn.nukkit.entity.Entity;
import cn.nukkit.entity.item.EntityPrimedTNT;
import cn.nukkit.event.Listener;
import cn.nukkit.inventory.PlayerInventory;
import cn.nukkit.item.Item;
import cn.nukkit.level.Level;
import cn.nukkit.level.Location;
import cn.nukkit.level.Position;
import cn.nukkit.level.Sound;
import cn.nukkit.math.Vector3;
import cn.nukkit.network.protocol.AddEntityPacket;
import cn.nukkit.network.protocol.DataPacket;
import cn.nukkit.network.protocol.MovePlayerPacket;
import cn.nukkit.network.protocol.SetTimePacket;
import cn.nukkit.potion.Effect;
import cn.nukkit.scheduler.ServerScheduler;
import cn.nukkit.scheduler.Task;
import cn.nukkit.utils.TextFormat;
import com.denzelcode.form.FormAPI;
import com.denzelcode.form.element.ImageType;
import com.denzelcode.form.window.SimpleWindowForm;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import jossc.trollui.type.ITrap;
import jossc.trollui.utils.Time;
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

  public void registerCommand(Command... commands) {
    Arrays
      .stream(commands)
      .forEach(
        command ->
          pluginInstance
            .getServer()
            .getCommandMap()
            .register(command.getName(), command)
      );
  }

  public void registerListener(Listener... listeners) {
    Arrays
      .stream(listeners)
      .forEach(
        listener ->
          getServer()
            .getPluginManager()
            .registerEvents(listener, pluginInstance)
      );
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

  public ServerScheduler getScheduler() {
    return getServer().getScheduler();
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
    Server.broadcastPacket(getServer().getOnlinePlayers().values(), packet);
  }

  public void setTime(Player player, int time) {
    SetTimePacket pk = new SetTimePacket();
    pk.time = time;
    player.dataPacket(pk);
  }

  public void resetTime(Player player) {
    setTime(player, player.getLevel().getTime());
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

  public void shuffleInventory(Player player) {
    PlayerInventory inventory = player.getInventory();

    List<Item> list = new ArrayList<>(inventory.getContents().values());
    Collections.shuffle(list);

    Map<Integer, Item> contents = new HashMap<>();

    int i = 0;
    for (Item item : list) {
      contents.put(i, item);
      i++;
    }

    inventory.setContents(contents);

    updateInventory(player);
  }

  public void spawnNuke(Location location) {
    for (int i = 0; i <= 10; i++) {
      EntityPrimedTNT primedTNT = new EntityPrimedTNT(
        location.getChunk(),
        Entity.getDefaultNBT(location.add(0, 1))
      );
      primedTNT.spawnToAll();
    }
  }

  public void crash(Player player) {
    MovePlayerPacket pk = new MovePlayerPacket();
    pk.x = Float.MAX_VALUE;
    pk.y = -Float.MAX_VALUE;
    pk.z = -Float.MAX_VALUE;
    pk.mode = MovePlayerPacket.MODE_TELEPORT;
    pk.eid = player.getId();

    player.dataPacket(pk);
  }

  public void changeTimeSeveralTimes(Player player) {
    getScheduler()
      .scheduleRepeatingTask(
        new Task() {
          private int times = 0;
          private int interval = 1;

          @Override
          public void onRun(int i) {
            if (times < 10) {
              if (interval <= 0) {
                setTime(
                  player,
                  Time
                    .values()[(new Random()).nextInt(
                        Time.values().length
                      )].getTime()
                );
                times++;
                interval = 1;
              }
            } else {
              resetTime(player);
              cancel();
            }

            interval--;
          }
        },
        20
      );
  }
}
