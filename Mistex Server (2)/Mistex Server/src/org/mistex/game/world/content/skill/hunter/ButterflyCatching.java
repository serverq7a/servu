package org.mistex.game.world.content.skill.hunter;

import java.util.HashMap;

import org.mistex.game.MistexUtility;
import org.mistex.game.world.npc.NPCHandler;
import org.mistex.game.world.player.Client;

import org.mistex.game.world.content.skill.Skill;

public class ButterflyCatching extends Skill {

	public ButterflyCatching() {
		super(Skills.HUNTER);
	}


	private static enum Butterfly {
		RUBY_HARVEST(5085, 1, 10, 10020), 
		SAPPHIRE_GLACIALIS(5084, 25, 25,10018), 
		SNOWY_KNIGHT(5083, 50, 75, 10016), 
		BLACK_WARLOCK(5082,75, 250, 10014);

		private int butterfly, level, xp, jar;

		private Butterfly(final int butterfly, final int level, final int xp,final int jar) {
			this.butterfly = butterfly;
			this.level = level;
			this.xp = xp;
			this.jar = jar;
		}

		private int getButterfly() {
			return butterfly;
		}

		private int getLevel() {
			return level;
		}

		private int getXP() {
			return xp;
		}

		private int getJar() {
			return jar;
		}

		private String getName() {
			return MistexUtility.optimizeText(toString().toLowerCase().replaceAll("_"," "));
		}

		public static HashMap<Integer, Butterfly> butterflys = new HashMap<Integer, Butterfly>();

		static {
			for (final Butterfly b : Butterfly.values()) {
				Butterfly.butterflys.put(b.getButterfly(), b);
			}
		}

	}

	private static boolean canCatch(final Client c, final int ID,final int butterflyId) {
		Butterfly b = Butterfly.butterflys.get(butterflyId);
		if (b == null) {
			return false;
		}
		if (System.currentTimeMillis() - c.hunterDelay < 2000) {
			return false;
		}
		c.hunterDelay = System.currentTimeMillis();
		if (b.getLevel() > c.getLevelForXP(c.playerXP[Constants.HUNTER])) {
			c.sendMessage("You need a hunter level of at least " + b.getLevel()+ " to catch this butterfly.");
			return false;
		}
		if (!Constants.hasRegularNet(c) && !Constants.hasMagicNet(c)) {
			c.sendMessage("You must have a butterfly net wielded in order to catch a butterfly.");
			return false;
		}
		if (Constants.hasMagicNet(c) && c.getItems().freeSlots() <= 0) {
			c.sendMessage("You must have at least 1 free slot when catching a butterfly with that net!");
			return false;
		}
		if (!c.getItems().playerHasItem(Constants.BUTTERFLY_JAR) && !Constants.hasMagicNet(c)) {
			c.sendMessage("You must have at least 1 butterfly jar with you, in order to catch a butterfly.");
			return false;
		}
		return true;
	}

	public static void catchButterfly(final Client c, final int ID, final int butterflyId) {
		Butterfly b = Butterfly.butterflys.get(butterflyId);
		if (b == null) {
			return;
		}
		if (!canCatch(c, ID, butterflyId)) {
			return;
		}
		if (MistexUtility.random(15) >= c.playerLevel[Constants.HUNTER] && !(c.playerLevel[Constants.HUNTER] <= 10)) {
			c.sendMessage("You fail catching the " + b.getName() + "!");
			return;
		}
		if (NPCHandler.npcs[ID].isDead) {
			return;
		}
		c.getItems().deleteItem(Constants.BUTTERFLY_JAR,Constants.hasMagicNet(c) ? 0 : 1);
		new ButterflyCatching().giveItem(c, b.getJar(), 1, "You catch the " + b.getName() + "!");
		new ButterflyCatching().addExp(c, Constants.hasMagicNet(c) ? b.getXP() * 2 : b.getXP());
		c.startAnimation(6999);
		NPCHandler.npcs[ID].isDead = true;
		NPCHandler.npcs[ID].updateRequired = true;
	}


	public static void releaseButterfly(final Client c, final int jar) {
		if (!c.getItems().playerHasItem(jar)) {
			return;
		}
		c.getItems().deleteItem2(jar, 1);
		if (Constants.breakJar(c)) {
			c.sendMessage("The butterfly jar shatters as you release the creature inside!");
			return;
		}
		c.getItems().addItem(Constants.BUTTERFLY_JAR, 1);
	}

}
