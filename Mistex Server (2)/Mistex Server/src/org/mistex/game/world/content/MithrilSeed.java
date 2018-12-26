package org.mistex.game.world.content;

import org.mistex.game.world.clip.region.Region;
import org.mistex.game.world.gameobject.ObjectHandler;
import org.mistex.game.world.player.Client;

public class MithrilSeed {
	
	public MithrilSeed() {

	}

	public static void plantSeed(Client c) {
		if (c.getItems().playerHasItem(299, 1)) {
			c.getItems().deleteItem2(299, 1);
			c.getPA().addSkillXP(2500, 19);
			c.startAnimation(827);
			ObjectHandler.createAnObject(c, 0, c.absX, c.absY, 10, 0, 10);
			c.getDH().sendDialogues(22, -1);
		}

		if (Region.getClipping(c.getX() - 1, c.getY(), c.heightLevel, -1, 0)) {
			c.getPA().walkTo(-1, 0);
		} else if (Region.getClipping(c.getX() + 1, c.getY(), c.heightLevel, 1, 0)) {
			c.getPA().walkTo(1, 0);
		} else if (Region.getClipping(c.getX(), c.getY() - 1, c.heightLevel, 0, -1)) {
			c.getPA().walkTo(0, -1);
		} else if (Region.getClipping(c.getX(), c.getY() + 1, c.heightLevel, 0, 1)) {
			c.getPA().walkTo(0, 1);
		}
	}
}