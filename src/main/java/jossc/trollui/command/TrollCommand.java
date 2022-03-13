package jossc.trollui.command;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParamType;
import cn.nukkit.command.data.CommandParameter;
import cn.nukkit.command.defaults.VanillaCommand;
import cn.nukkit.utils.TextFormat;
import jossc.trollui.API;
import jossc.trollui.TrollUIPlugin;

public class TrollCommand extends VanillaCommand {

  public TrollCommand() {
    super(
      "troll",
      "Troll Command",
      "",
      new String[] { "trollgui", "trollui", "tu" }
    );
    setPermission("command.troll.ui");
    setPermissionMessage(TextFormat.RED + "You do not have permissions!");

    this.commandParameters.clear();
    this.commandParameters.put(
        "default",
        new CommandParameter[] {
          new CommandParameter("player", CommandParamType.TARGET, false)
        }
      );
  }

  @Override
  public boolean execute(CommandSender sender, String label, String[] args) {
    if (!sender.isPlayer() || !testPermission(sender)) {
      return false;
    }

    API api = TrollUIPlugin.getInstance().getApi();

    if (args.length < 1) {
      api.showSelectPlayerForm((Player) sender);

      return false;
    }

    String targetName =
      args[0].replace("@s", sender.getName())
        .replace("@r", api.getRandomPlayer().getName());

    Player target = sender.getServer().getPlayer(targetName);

    if (target == null) {
      sender.sendMessage(TextFormat.RED + "This player is offline!");

      return false;
    }

    api.showTrapListForm((Player) sender, target, false);

    return true;
  }
}
