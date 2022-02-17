package jossc.trollui;

import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.TextFormat;
import com.denzelcode.form.FormAPI;
import jossc.trollui.command.TrollCommand;
import jossc.trollui.type.*;
import lombok.Getter;

public class TrollUIPlugin extends PluginBase {

  @Getter
  private API api;

  @Getter
  private static TrollUIPlugin instance;

  public static String PREFIX = TextFormat.BLUE + "Troll> ";

  @Override
  public void onLoad() {
    api = new API(this);
    instance = this;
  }

  @Override
  public void onEnable() {
    saveDefaultConfig();

    FormAPI.init(this);

    api.registerTrap(
      new Burn(),
      new DropItemInHand(),
      new BedrockCage(),
      new TNTCage(),
      new LightningStrike(),
      new DropInventory(),
      new LookRandom(),
      new Launch(),
      new ShuffleInventory(),
      new Nuke(),
      new AllEffects(),
      new Crash(),
      new NextBlockExplodes(),
      new ChangeTime()
    );
    api.registerCommand(new TrollCommand());

    getLogger().info(TextFormat.GREEN + "This plugin has been enabled!");
  }

  @Override
  public void onDisable() {
    api.close();
    getLogger().info(TextFormat.RED + "This plugin has been disabled!");
  }
}
