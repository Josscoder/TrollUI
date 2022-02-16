package jossc.trollui.type;

import cn.nukkit.Player;

public interface ITrap {
  String getId();
  String getIdToLowerCase();
  String getIdToUpperCase();
  String getPermission();

  boolean hasPermission();

  void init();

  String getImage();

  void execute(Player owner, Player target);

  void close();
}
