package org.mistex.game.world.content.skill.prayer;

import org.mistex.game.world.event.CycleEvent;
import org.mistex.game.world.event.CycleEventContainer;
import org.mistex.game.world.event.CycleEventHandler;
import org.mistex.game.world.player.Client;

public class Bury {

	public static void buryBones(final Client c, final int i, final int slot) {
		if (c.isSkilling)
			return;
		for (final int[] element : Constants.BONES) {
			if (i == element[0]) {
				c.isSkilling = true;
				c.startAnimation(827);
				c.sendMessage("You bury the bones.");
				CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {
					@Override
					public void stop() {
						if (c.getItems().playerHasItem(element[0], 1, slot)) {
							c.getItems().deleteItem(element[0], slot, 1);
							c.getPA().addSkillXP((element[1]) * Constants.PRAYER_XP, Constants.PRAYER);
						}
						c.isSkilling = false;
					}

					@Override
					public void execute(CycleEventContainer container) {
						container.stop();
					}
				}, 2);
			}
		}
	}
}
