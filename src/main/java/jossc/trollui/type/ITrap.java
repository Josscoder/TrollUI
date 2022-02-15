package jossc.trollui.type;

import cn.nukkit.Player;

public interface ITrap {
  String getId();
  String getIdToLowerCase();
  String getIdToUpperCase();
  String getImage();

  void init();
  void close();

  void execute(Player owner, Player target);
}
