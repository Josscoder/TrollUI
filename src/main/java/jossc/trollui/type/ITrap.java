package jossc.trollui.type;

import cn.nukkit.Player;

public interface ITrap {
  String getId();
  String getIdToLowerCase();
  String getIdToUpperCase();

  void init();

  String getImage();

  void execute(Player owner, Player target);

  void close();
}
