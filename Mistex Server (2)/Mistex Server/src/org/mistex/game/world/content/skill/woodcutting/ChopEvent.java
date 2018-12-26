package org.mistex.game.world.content.skill.woodcutting;

import org.mistex.game.MistexUtility;
import org.mistex.game.world.content.DoubleExpHandler;
import org.mistex.game.world.content.skill.SkillHandler;
import org.mistex.game.world.content.skill.firemaking.Firemaking.LogData;
import org.mistex.game.world.content.skill.woodcutting.Woodcutting.Axe;
import org.mistex.game.world.content.skill.woodcutting.Woodcutting.Tree;
import org.mistex.game.world.event.CycleEvent;
import org.mistex.game.world.event.CycleEventContainer;
import org.mistex.game.world.player.Client;
import org.mistex.game.world.player.Player;

public class ChopEvent extends CycleEvent {
	private Client c;
	private int obX, obY, obj;
	private Tree tree;
	private Axe axe;

	public ChopEvent(Client c, Tree tree, Axe axe, int obX, int obY, int obj) {
		this.c = c;
		this.obX = obX;
		this.obY = obY;
		this.obj = obj;
		this.tree = tree;
		this.axe = axe;
	}

	@Override
	public void execute(CycleEventContainer container) {
		if (!c.playerIsWoodcutting) {
			container.stop();
			return;
		}

		c.turnPlayerTo(obX, obY);

		if (!Woodcutting.hasAxe(c)) {
			c.sendMessage("You need a Woodcutting axe which you need a Woodcutting level to use.");
			container.stop();
			return;
		}
		if (!SkillHandler.noInventorySpace(c, "woodcutting")) {
			container.stop();
			return;
		}
		if (Woodcutting.skillCheck(c.playerLevel[Player.playerWoodcutting], tree.getLevelReq(), (int) ((axe.getLevel() / 10D) * 20))) {
			SkillHandler.deleteTime(c);
			double bonus = 1;
			if (c.getItems().isWearingItem(10933))
				bonus+=0.03;
			if (c.getItems().isWearingItem(10939))
				bonus+=0.03;
			if (c.getItems().isWearingItem(10940))
				bonus+=0.03;
			if (c.getItems().isWearingItem(10941))
				bonus+=0.03;
			new Woodcutting().addExp(c, tree.getExp() * bonus);
			if (axe.equals(Axe.INFERNO_ADZ) && Math.random() > 1 - 1 / 3.0) {
				c.getPA().addSkillXP(DoubleExpHandler.weekendModifier() * LogData.forId(tree.getProduct()).getExp(), 11);
				c.sendMessage("The adze's heat instantly incinerates the log, granting " + MistexUtility.format(DoubleExpHandler.weekendModifier() * LogData.forId(tree.getProduct()).getExp()) + " firemaking EXP");
			} else {
				new Woodcutting().giveItem(c, tree.getProduct(), 1, "You get some logs.");
			}
	
			BirdsNests.dropNest(c);
	
			if (!SkillHandler.noInventorySpace(c, "woodcutting")) {
				container.stop();
				return;
			}
	
			if (c.doAmount <= 0 && c.playerIsWoodcutting) {
			c.playerIsWoodcutting = false;
			Woodcutting.createStump(c, tree, obj, obX, obY);
			container.stop();
		}
		}
	}

	@Override
	public void stop() {
		Woodcutting.resetWoodcutting(c);
	}
}