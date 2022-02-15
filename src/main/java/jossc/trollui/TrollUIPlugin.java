package jossc.trollui;

import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.TextFormat;
import jossc.trollui.command.TrollCommand;
import lombok.Getter;

public class TrollUIPlugin extends PluginBase {

  @Getter
  private API api;

  @Getter
  private static TrollUIPlugin instance;

  @Override
  public void onLoad() {
    api = new API(this);
    instance = this;
  }

  @Override
  public void onEnable() {
    saveDefaultConfig();

    api.registerTrap();

    api.registerCommand(new TrollCommand());

    getLogger().info(TextFormat.GREEN + "This plugin has been enabled!");
  }

  @Override
  public void onDisable() {
    api.close();
    getLogger().info(TextFormat.RED + "This plugin has been disabled!");
  }
}
