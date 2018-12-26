package org.mistex.game.world.content.skill.woodcutting;

import org.mistex.game.MistexConfiguration;
import org.mistex.game.MistexUtility;
import org.mistex.game.world.World;
import org.mistex.game.world.content.skill.Skill;
import org.mistex.game.world.event.CycleEvent;
import org.mistex.game.world.event.CycleEventContainer;
import org.mistex.game.world.event.CycleEventHandler;
import org.mistex.game.world.gameobject.ObjectHandler;
import org.mistex.game.world.player.Client;
import org.mistex.game.world.player.Player;

public class Woodcutting extends Skill {

	public Woodcutting() {
		super(Skills.WOODCUTTING);
	}

	public enum Axe {
		INFERNO_ADZ(13661, 61, 10251),
		DRAGON(6739, 61, 2846),
		RUNE(1359, 41, 867),
		ADAMANT(1357, 31, 869),
		MITHRIL(1355, 21, 871),
		STEEL(1353, 6, 875),
		BLACK(1361, 6, 873),
		IRON(1349, 1, 877),
		BRONZE(1351, 1, 879);

		private int item, level, animation;

		private Axe(int item, int level, int animation) {
			this.item = item;
			this.level = level;
			this.animation = animation;
		}

		public int getAnimation() {
			return animation;
		}

		public int getItem() {
			return item;
		}

		public int getLevel() {
			return level;
		}

		public static Axe getAxe(Client c) {
			Axe unable = null;
			for (Axe data : values()) {
				if (c.getItems().playerHasItem(data.getItem()) || c.getItems().isWearingItem(data.getItem())) {
					if (data.getLevel() > c.playerLevel[Player.playerWoodcutting]) {
						unable = data;
						continue;
					}
					return data;
				}
			}
			if (unable != null)
				c.sendMessage("You need a woodcutting level of " + unable.getLevel() + " to use this axe.");
			else
				c.sendMessage("You need an axe to cut this tree.");
			return null;
		}
	}

	public enum Tree {
		NORMAL(new int[] { 1276, 1277, 1278, 1279, 1280, 1282, 1283, 1284, 1285, 1286, 1287, 1288, 1289, 1290, 1291, 1301, 1303, 1304, 1305, 1318, 1319, 1315, 1316, 1330, 1331, 1332, 1333, 1383, 1384, 2409, 2447, 2448, 3033, 3034, 3035, 3036, 3879, 3881, 3883, 3893, 3885, 3886, 3887, 3888, 3892, 3889, 3890, 3891, 3928, 3967, 3968, 4048, 4049,
				4050, 4051, 4052, 4053, 4054, 4060, 5004, 5005, 5045, 5902, 5903, 5904, 8973, 8974, 10041, 10081, 10082, 10664, 11112, 11510, 12559, 12560, 12732, 12895, 12896, 13412, 13411, 13419, 13843, 13844, 13845, 13847, 13848, 13849, 13850, 14308, 14309, 14513, 14514, 14515, 14521, 14564, 14565, 14566, 14593, 14594, 14595, 14600, 14635,
				14636, 14637, 14642, 14664, 14665, 14666, 14693, 14694, 14695, 14696, 14701, 14738, 14796, 14797, 14798, 14799, 14800, 14801, 14802, 14803, 14804, 14805, 14806, 14807, 15489, 15776, 15777, 16264, 16265, 19165, 19166, 19167, 23381 }, 1511, 1, 25, 10),
		OAK(new int[] { 1281, 3037, 8462, 8463, 8464, 8465, 8466, 8467, 10083, 13413, 13420 }, 1521, 15, 38, 15),
		WILLOW(new int[] { 1308, 5551, 5552, 5553, 8481, 8482, 8483, 8484, 8485, 8486, 8487, 8488, 8496, 8497, 8498, 8499, 8500, 8501, 13414, 13421 }, 1519, 30, 68, 10),
		MAPLE(new int[] { 1307, 4674, 8435, 8436, 8437, 8438, 8439, 8440, 8441, 8442, 8443, 8444, 8454, 8455, 8456, 8457, 8458, 8459, 8460, 8461, 13415, 13423 }, 1517, 40, 100, 15),
		YEW(new int[] { 1309, 8503, 8504, 8505, 8506, 8507, 8508, 8509, 8510, 8511, 8512, 8513, 13416, 13422 }, 1515, 60, 175, 50),
		MAGIC(new int[] { 1306, 8396, 8397, 8398, 8399, 8400, 8401, 8402, 8403, 8404, 8405, 8406, 8407, 8408, 8409, 13417, 13424 }, 1513, 75, 250, 100);

		private int[] objects;
		private int product, levelReq, exp, respawn;

		private Tree(int[] objects, int product, int levelReq, int exp, int respawn) {
			this.objects = objects;
			this.product = product;
			this.levelReq = levelReq;
			this.exp = exp;
			this.respawn = respawn;
		}

		public int getExp() {
			return exp;
		}

		public int getLevelReq() {
			return levelReq;
		}

		public int[] getObjects() {
			return objects;
		}

		public int getProduct() {
			return product;
		}

		public int getRespawn() {
			return respawn;
		}

		public static Tree forId(int object) {
			for (Tree data : values())
				for (int tree : data.getObjects())
					if (tree == object)
						return data;
			return null;
		}
	}

	public static boolean hasAxe(Client c) {
		return Axe.getAxe(c) != null;
	}

	public static void attemptData(final Client c, final int obj, final int obX, final int obY) {
		final Axe axe = Axe.getAxe(c);
		final Tree tree = Tree.forId(obj);

		if (axe == null || tree == null || Skill.unavailableObjects.contains((Integer) (obX << 16 | obY << 8 | obj)))
			return;

		c.sendMessage("You swing your axe at the tree.");
		
		if (c.isSkilling || c.playerIsWoodcutting) {
			return;
		}
		
		if (!noInventorySpace(c, "woodcutting")) {
			resetWoodcutting(c);
			return;
		}

		c.isSkilling = true;
		c.playerIsWoodcutting = true;
		c.woodcuttingTree = obj;

		if (!hasRequiredLevel(c, 8, tree.getLevelReq(), "woodcutting", "cut this tree")) {
			resetWoodcutting(c);
			return;
		}

		switch (tree) {
		case NORMAL:
			c.doAmount = 1;
			break;
		default:
			c.doAmount = 1 + MistexUtility.random(25);
		}

		c.startAnimation(axe.getAnimation());
		c.turnPlayerTo(obX, obY);

		CycleEventHandler.getSingleton().addEvent(c, new ChopEvent(c, tree, axe, obX, obY, obj), 4);

		CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {
			@Override
			public void execute(CycleEventContainer container) {
				if (!c.isSkilling || !c.playerIsWoodcutting) {
					container.stop();
					return;
				}
				c.startAnimation(axe.getAnimation());
			}

			@Override
			public void stop() {
				resetWoodcutting(c);
			}
		}, 4);
	}

	private static void treeLocated(Client c, int obX, int obY) {
		for (int i = 0; i < MistexConfiguration.MAX_PLAYERS; i++) {
			if (World.players[i] != null) {
				Client person = (Client) World.players[i];
				if (person != null) {
					Client p = person;
					if (p.distanceToPoint(c.absX, c.absY) <= 10) {
						if (c.woodcuttingTree == p.woodcuttingTree) {
							resetWoodcutting(p);
							resetWoodcutting(c);
						}
					}
				}
			}
		}
	}

	public static void createStump(final Client c, final Tree tree, final int type, final int obX, final int obY) {
		Skill.unavailableObjects.add((Integer) (obX << 16 | obY << 8 | type));
		ObjectHandler.createAnObject(c, 1341, obX, obY);
		treeLocated(c, obX, obY);
		CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {
			@Override
			public void execute(CycleEventContainer container) {
				Skill.unavailableObjects.remove((Integer) (obX << 16 | obY << 8 | type));
				ObjectHandler.createAnObject(c, type, obX, obY);
				container.stop();
			}

			@Override
			public void stop() {
			}
		}, tree.getRespawn());
	}

	public static void resetWoodcutting(Client c) {
		c.startAnimation(65535);
		c.woodcuttingTree = -1;
		c.playerIsWoodcutting = false;
		c.isSkilling = false;
		c.doAmount = 0;
	}
}