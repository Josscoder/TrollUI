package jossc.trollui.command;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
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
  }

  @Override
  public boolean execute(CommandSender sender, String label, String[] args) {
    if (!(sender.isPlayer() || testPermission(sender))) {
      return false;
    }

    API api = TrollUIPlugin.getInstance().getApi();

    if (args.length < 1) {
      api.showSelectPlayerForm((Player) sender);

      return false;
    }

    String targetName = args[0];
    Player target = sender.getServer().getPlayer(targetName);

    if (target == null) {
      sender.sendMessage(TextFormat.RED + "This player is offline!");

      return false;
    }

    api.showTrapListForm((Player) sender, target, false);

    return true;
  }
}
