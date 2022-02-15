package jossc.trollui.command;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.defaults.VanillaCommand;
import cn.nukkit.utils.TextFormat;
import com.denzelcode.form.FormAPI;
import com.denzelcode.form.element.ImageType;
import com.denzelcode.form.window.SimpleWindowForm;
import jossc.trollui.API;
import jossc.trollui.TrollUIPlugin;
import jossc.trollui.type.ITrap;

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

    if (args.length < 1) {
      showSelectPlayerForm((Player) sender);

      return false;
    }

    String targetName = args[0];
    Player target = sender.getServer().getPlayer(targetName);

    if (target == null) {
      sender.sendMessage(TextFormat.RED + "This player is offline!");

      return false;
    }

    showTrapListForm((Player) sender, target, false);

    return true;
  }

  private void showSelectPlayerForm(Player player) {
    SimpleWindowForm form = FormAPI.simpleWindowForm(
      null,
      TextFormat.BOLD.toString() + TextFormat.DARK_AQUA + "SELECT A PLAYER",
      "This will be the player you troll"
    );

    player
      .getServer()
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

        Player target = player
          .getServer()
          .getPlayer(event.getButton().getName());

        if (target == null) {
          return;
        }

        showTrapListForm(player, target, true);
      }
    );

    form.sendTo(player);
  }

  private void showTrapListForm(
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

    API api = TrollUIPlugin.getInstance().getApi();

    api
      .getTraps()
      .values()
      .forEach(
        iTrap ->
          form.addButton(
            iTrap.getId(),
            iTrap.getIdToUpperCase(),
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

        ITrap trap = api.getTrap(buttonName);

        if (trap == null) {
          return;
        }

        trap.execute(owner, target);
      }
    );

    form.sendTo(owner);
  }
}
