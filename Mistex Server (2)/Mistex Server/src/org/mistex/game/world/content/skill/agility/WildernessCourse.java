package org.mistex.game.world.content.skill.agility;

import org.mistex.game.world.event.CycleEvent;
import org.mistex.game.world.event.CycleEventContainer;
import org.mistex.game.world.event.CycleEventHandler;
import org.mistex.game.world.player.Client;
import org.mistex.game.world.player.Player;

public class WildernessCourse {
	
	public static void handleObject(int objectId, Client p) {
		if (!isObstacle(objectId))
			return;
		if (p.playerRights != 3) {
			p.sendMessage("Currently unavailable.");
			return;
		}
		switch (objectId) {
		
		case 2288:
			handlePipe(p);
		break;
		
		case 2283:
			handleRope(p);
			break;
			
		case 14758:
			handleStone(p);
			break;
			
		case 2297:
			handleLog(p);
			break;
			
		case 2328:
			handleRocks(p);
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
		case 2288: //pipe
		case 2283: //rope
		case 14758: //stone
		case 2297: //log
		case 2328: //rocks
			return true;
		}
		return false;
	}

	/**
	 * Checks if the player has passed all obstacles
	 */
	
	public static boolean isFinished(Client p) {
		if (p.finishedWildPipe && p.finishedWildRope && p.finishedWildStone && p.finishedWildLog && p.finishedWildRocks) {
			return true;
		}
		return false;
	}
	
	/**
	 * Obstacle methods
	 */
	
	public static void handlePipe(final Client p) {
		if (p.playerLevel[Player.playerAgility] <= 38) {
			p.sendMessage("You need 49 Agility to do this course.");
			return;
		}
			p.doingAgility = true;
			specialMove(p, 844, 0, 13);
			CycleEventHandler.getSingleton().addEvent(p, new CycleEvent() {
				@Override
				public void execute(CycleEventContainer container) {
					if (p.absY == 3950) {
						container.stop();
					}
				}

				@Override
				public void stop() {
					p.startAnimation(844);
					resetAgilityWalk(p);
					p.finishedWildPipe = true;
					new BarbarianCourse().addExp(p, 15);
				}
			}, 1);	
	}
	
	public static void handleRope(final Client p) {
		if (p.playerLevel[Player.playerAgility] <= 38) {
			p.sendMessage("You need 49 Agility to do this course.");
			return;
		}
	if (p.absY >= 3953) {
		p.doingAgility = true;
		p.getPA().startTeleport(3005, 3958, 0, "agility");
		resetAgilityWalk(p);
		new BarbarianCourse().addExp(p, 15);
		p.finishedWildRope = true;
		p.doingAgility = false;
		}
	}
	
	public static void handleStone(final Client p) {
		if (p.playerLevel[Player.playerAgility] <= 38) {
			p.sendMessage("You need 49 Agility to do this course.");
			return;
		}
		p.startAnimation(828);
		p.getPA().movePlayer(2995, 3960, 0);
		new BarbarianCourse().addExp(p, 15);
		p.finishedWildStone = true;
	}
	
	public static void handleLog(final Client p) {
		if (p.playerLevel[Player.playerAgility] <= 38) {
			p.sendMessage("You need 49 Agility to do this course.");
			return;
		}
		p.doingAgility = true;
			specialMove(p, 762, -7 , 0);
			CycleEventHandler.getSingleton().addEvent(p, new CycleEvent() {
				@Override
				public void execute(CycleEventContainer container) {
					if (p.absX == 2995) {
						container.stop();
					}
				}

				@Override
				public void stop() {
					resetAgilityWalk(p);
					new BarbarianCourse().addExp(p, 20);
					p.finishedWildLog = true;
					p.doingAgility = false;
				}
			}, 1);
	}
	
	public static void handleRocks(final Client p) {
		if (p.playerLevel[Player.playerAgility] <= 38) {
			p.sendMessage("You need 49 Agility to do this course.");
			return;
		}
		if (p.absY >= 3937) {
		p.getPA().movePlayer(2994, 3932, 0);
		p.finishedWildRocks = true;
		if (isFinished(p)) {
			p.getPA().addSkillXP(20000,Player.playerAgility);
			p.sendMessage("You have completed the full barbarian agility course.");
			p.sendMessage("You have been rewarded with 20k Agility XP!");
		} else {
			new BarbarianCourse().addExp(p, 15);
		}
		p.finishedWildPipe = false;
		p.finishedWildRope = false;
		p.finishedWildStone = false;
		p.finishedWildLog = false;
		p.finishedWildRocks = false;
	}
	}
	
	
}
