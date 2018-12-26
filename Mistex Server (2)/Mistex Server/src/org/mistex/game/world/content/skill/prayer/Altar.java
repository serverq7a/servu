package org.mistex.game.world.content.skill.prayer;

import org.mistex.game.world.event.CycleEvent;
import org.mistex.game.world.event.CycleEventContainer;
import org.mistex.game.world.event.CycleEventHandler;
import org.mistex.game.world.player.Client;

@SuppressWarnings("static-access")
public class Altar {

	public static void bonesOnAltar(final Client c, final int item) {
		if (!c.getItems().playerHasItem(item)) {
			return;
		}
		c.boneId = item;
		c.getPA().sendFrame164(4429);
		c.getPA().sendFrame246(1746, 140, c.boneId);
		c.getPA().sendFrame126("How many would you like to sacrifice?", 2800);
		c.getPA().sendFrame126(getLine(c) + "" + c.getItems().getItemName(c.boneId)+ "", 2799);
	}

	private static String getLine(Client c) {
		return c.below459 ? ("\\n\\n\\n\\n") : ("\\n\\n\\n\\n\\n");
	}

	public static void sacrificeBone(final Client c, final int amount) {
		if (!c.getItems().playerHasItem(c.boneId)) {
			return;
		}
		if (c.trainingPrayer == true) {
			c.getPA().removeAllWindows();
			return;
		}
		c.getPA().removeAllWindows();
		c.doAmount = amount;
		c.trainingPrayer = true;
		c.startAnimation(645);
		c.getPA().createPlayersStillGfx(624, c.objectX, c.objectY, 0, 0);
		for (int i = 0; i < Constants.BONES.length; i++) {
			if (c.boneId == Constants.BONES[i][0]) {
				c.getPA().addSkillXP((Constants.BONES[i][1] * 3) * Constants.PRAYER_XP,Constants.PRAYER);
			}
		}
		c.getItems().deleteItem(c.boneId, 1);
		c.doAmount--;
		c.sendMessage("The gods are pleased with your offerings.");
		CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {
			@Override
			public void execute(CycleEventContainer container) {
				if (c.doAmount <= 0) {
					container.stop();
					return;
				}
				if (c.trainingPrayer == false) {
					container.stop();
					return;
				}
				if (!c.getItems().playerHasItem(c.boneId)) {
					container.stop();
					return;
				}
				c.startAnimation(645);
				c.getPA().createPlayersStillGfx(624, c.objectX, c.objectY, 0, 0);
				for (int i = 0; i < Constants.BONES.length; i++) {
					if (c.boneId == Constants.BONES[i][0]) {
						c.getPA().addSkillXP((Constants.BONES[i][1] * 3)* Constants.PRAYER_XP,Constants.PRAYER);
					}
				}
				c.getItems().deleteItem(c.boneId, 1);
				c.doAmount--;
				c.sendMessage("The gods are pleased with your offerings.");
			}

			@Override
			public void stop() {
				c.getPA().closeAllWindows();
				c.doAmount = 0;
				c.trainingPrayer = false;
				c.startAnimation(c.playerStandIndex);
			}
		}, 4);
	}

}
