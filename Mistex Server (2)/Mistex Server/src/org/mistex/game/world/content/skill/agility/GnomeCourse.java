package org.mistex.game.world.content.skill.agility;

import org.mistex.game.world.event.CycleEvent;
import org.mistex.game.world.event.CycleEventContainer;
import org.mistex.game.world.event.CycleEventHandler;
import org.mistex.game.world.player.Client;

public class GnomeCourse {

	public static void handleObject(int objectId, Client p) {
		if (!isObstacle(objectId))
			return;
		if (p.doingAgility) {
			return;
		}
		switch (objectId) {

		case 2295:
			handleLog(p);
			break;

		case 2285:
			handleNet1(p);
			break;

		case 2313:
			handleBranch1(p);
			break;

		case 2312:
			handleRope(p);
			break;

		case 2314:
		case 2315:
			handleBranch2(p);
			break;

		case 2286:
			handleNet2(p);
			break;

		case 154:
		case 4058:
			handlePipe(p);
			break;

		}
	}

	/**
	 * Restores the player details after doing the obstacle
	 */

	public static void resetAgilityWalk(final Client c) {
		c.isRunning2 = true;
		c.getPA().sendFrame36(173, 1);
		c.playerWalkIndex = 0x333;
		c.getPA().requestUpdates();
	}

	/**
	 * Moves the player to a coordinate with a asigned animation
	 */

	private static void specialMove(final Client c, final int walkAnimation, final int x, final int y) {
		c.isRunning2 = false;
		c.getPA().sendFrame36(173, 0);
		c.playerWalkIndex = walkAnimation;
		c.getPA().requestUpdates();
		c.getPA().walkTo(x, y);
	}

	/**
	 * Checks if its a obstacle
	 */

	public static boolean isObstacle(int i) {
		switch (i) {
		case 2295: // log
		case 2285: // net1
		case 2313: // branch1
		case 2312: // rope
		case 2314: // branch2
		case 2315: // branch2
		case 2286: // net2
		case 154: // pipe left
		case 4058: // pipe right
			return true;
		}
		return false;
	}

	/**
	 * Checks if the player has passed all obstacles
	 */

	public static boolean isFinished(Client p) {
		return p.finishedLog && p.finishedNet1 && p.finishedBranch1 && p.finishedRope && p.finishedBranch2 && p.finishedNet2 && p.finishedPipe;
	}

	/**
	 * Obstacle methods
	 */

	public static void handleLog(final Client p) {
		p.doingAgility = true;
		specialMove(p, 762, 0, -7);
		CycleEventHandler.getSingleton().addEvent(p, new CycleEvent() {
			@Override
			public void execute(CycleEventContainer container) {
				if (p.absY == 3429) {
					container.stop();
				}
			}

			@Override
			public void stop() {
				resetAgilityWalk(p);
				if (p.doingAgility)
					new BarbarianCourse().addExp(p, 40);
				p.finishedLog = true;
				p.doingAgility = false;
			}
		}, 1);
	}

	public static void handleNet1(final Client p) {
		if (p.clickedAgility == 1)
			return;
		p.clickedAgility = 1;
		p.startAnimation(828);
		p.getPA().movePlayer(p.absX, 3424, 1);
		if (p.doingAgility)
			new BarbarianCourse().addExp(p, 10);
		p.finishedNet1 = true;
	}

	public static void handleBranch1(final Client p) {
		if (p.clickedAgility == 2)
			return;
		p.clickedAgility = 2;
		p.startAnimation(828);
		p.getPA().movePlayer(2473, 3420, 2);
		if (p.doingAgility)
			new BarbarianCourse().addExp(p, 40);
		p.finishedBranch1 = true;
	}

	public static void handleRope(final Client p) {
		p.doingAgility = true;
		specialMove(p, 762, 6, 0);
		CycleEventHandler.getSingleton().addEvent(p, new CycleEvent() {
			@Override
			public void execute(CycleEventContainer container) {
				if (p.absX == 2483) {
					container.stop();
				}
			}

			@Override
			public void stop() {
				resetAgilityWalk(p);
				if (p.doingAgility)
					new BarbarianCourse().addExp(p, 40);
				p.finishedRope = true;
				p.doingAgility = false;
			}
		}, 1);
	}

	public static void handleBranch2(final Client p) {
		if (p.clickedAgility == 3)
			return;
		p.clickedAgility = 3;
		p.startAnimation(828);
		p.getPA().movePlayer(p.absX, p.absY, 0);
		if (p.doingAgility)
			new BarbarianCourse().addExp(p, 40);
		p.finishedBranch2 = true;
	}

	public static void handleNet2(final Client p) {
		if (p.clickedAgility == 4)
			return;
		p.clickedAgility = 4;
		p.doingAgility = true;
		p.startAnimation(828);
		CycleEventHandler.getSingleton().addEvent(p, new CycleEvent() {
			@Override
			public void execute(CycleEventContainer container) {
				p.getPA().movePlayer(p.absX, 3427, 0);
				container.stop();
			}

			@Override
			public void stop() {
				p.turnPlayerTo(p.absX, 3426);
				if (p.doingAgility)
					new BarbarianCourse().addExp(p, 40);
				p.finishedNet2 = true;
				p.doingAgility = false;
			}
		}, 1);
	}

	public static void handlePipe(final Client p) {
		if (p.absX != 2484 && p.absY != p.objectY - 1) {
			p.getPA().walkTo(2484 - p.absX, (p.objectY - 1) - p.absY);
			return;
		}
		if (p.absX == 2484 && p.absY == 3430) {
			p.doingAgility = true;
			specialMove(p, 844, 0, 7);
			CycleEventHandler.getSingleton().addEvent(p, new CycleEvent() {
				@Override
				public void execute(CycleEventContainer container) {
					if (p.absY == 3437) {
						container.stop();
					}
				}

				@Override
				public void stop() {
					p.startAnimation(844);
					resetAgilityWalk(p);
					p.finishedPipe = true;
					if (isFinished(p)) {
						if (p.doingAgility)
							new BarbarianCourse().addExp(p, 420);
						p.sendMessage("You have completed the full gnome agility course.");
						p.sendMessage("You have been rewarded with 15k Agility XP!");
					} else {
						if (p.doingAgility)
							new BarbarianCourse().addExp(p, 50);
						p.sendMessage("You have not completed the full gnome agility course resulting in no bonus.");
					}
					p.clickedAgility = 0;
					p.finishedLog = false;
					p.finishedNet1 = false;
					p.finishedBranch1 = false;
					p.finishedRope = false;
					p.finishedBranch2 = false;
					p.finishedNet2 = false;
					p.finishedPipe = false;
				}
			}, 1);
		}
	}
}
