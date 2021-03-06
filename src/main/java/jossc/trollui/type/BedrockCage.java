package jossc.trollui.type;

import cn.nukkit.Player;
import cn.nukkit.block.BlockBedrock;
import cn.nukkit.utils.TextFormat;
import jossc.trollui.TrollUIPlugin;

public class BedrockCage extends Trap {

  @Override
  public void init() {}

  @Override
  public String getImage() {
    return "textures/blocks/bedrock.png";
  }

  @Override
  public void execute(Player owner, Player target) {
    api.buildCage(target, new BlockBedrock());

    owner.sendMessage(
      TrollUIPlugin.PREFIX +
      TextFormat.GREEN +
      target.getName() +
      " is in a jail now!"
    );
  }

  @Override
  public void close() {}
}
