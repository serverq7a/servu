package org.mistex.game.world.content.skill.hunter;

import org.mistex.game.Mistex;
import org.mistex.game.world.World;
import org.mistex.game.world.event.CycleEvent;
import org.mistex.game.world.event.CycleEventContainer;
import org.mistex.game.world.event.CycleEventHandler;
import org.mistex.game.world.gameobject.ObjectHandler;
import org.mistex.game.world.gameobject.Objects;
import org.mistex.game.world.npc.NPC;
import org.mistex.game.world.npc.NPCHandler;
import org.mistex.game.world.player.Client;

@SuppressWarnings("static-access")
public class TrapHunting {

	private static final int[][] trapData = {
			{ 5117, 19651, 19675, 5262, -1, -1, 700, 10149, 21, 1939, 19652,19662 },
			{ 5087, 19206, 19216, 5272, -1, -1, 500, 9953, 20, 1739, 19205 },
			{ 5073, 19175, 19180, 6779, 10006, 5208, 800, 10088, 1 },
			{ 5072, 19175, 19178, 6779, 10006, 5208, 790, 10087, 1 },
			{ 5074, 19175, 19182, 6779, 10006, 5208, 900, 10089, 1 },
			{ 5079, 19187, 19192, 5184, 10008, 5208, 10000, 10033, 53, 1982 },
			{ 5080, 19187, 19192, 5184, 10008, 5208, 15000, 10034, 64, 1982 } };

	private static int getData(final int[][] from, final int data,final int inputData, final int returningData) {
		for (int[] aFrom : from) {
			if (aFrom[inputData] == data) {
				return aFrom[returningData];
			}
		}
		return -1;
	}

	public static boolean canTrap(final Client c) {
		int amt = 0;
		Objects objects[] = new Objects[6];
		for (Objects o : ObjectHandler.globalObjects) {
			if (o.owner == c.playerId) {
				boolean same = false;
				for (Objects o2 : objects) {
					if (o2 != null && o.objectX == o.objectX + 0
							&& o.objectY == o.objectY - 0) {
						same = true;
					}
				}
				if (!same)
					objects[amt++] = o;

			}
			if (o.objectId == c.objectId && o.getObjectX() == c.getAbsX() && o.getObjectY() == c.getAbsY()) {
				c.sendMessage("You cannot lay a trap on another trap.");
				return false;
			}
		}
		if (amt > maxTraps(c.getPA().getLevelForXP(c.playerXP[Constants.HUNTER]))) {
			c.sendMessage("You cannot lay that many traps at your level.");
			return false;
		}
		return true;
	}

	/**
	 * Most traps
	 */

	private static int maxTraps(int levelForXP) {
		return levelForXP / 20 + 1;
	}

	/**
	 * Handles Laying the trap
	 * 
	 * @param c
	 *            Client
	 * @param id
	 *            Id of Trap
	 * @return Returns if can lay
	 */

	public static boolean layTrap(final Client c, final int id) {
		if (getData(trapData, id, 4, 1) <= -1) {
			return false;
		}
		if (!canTrap(c)) {
			return false;
		}
		c.startAnimation(5208);
		c.getItems().deleteItem(id, 1);
		final org.mistex.game.world.gameobject.Objects o = new org.mistex.game.world.gameobject.Objects(getData(trapData,id, 4, 1), c.getAbsX(), c.getAbsY(), 0, 0, 10, 0);
		o.owner = c.playerId;
		Mistex.objectHandler.addObject(o);
		Mistex.objectHandler.placeObject(o);
		CycleEventHandler.getSingleton().addEvent(id, new CycleEvent() {
			@Override
			public void execute(CycleEventContainer container) {
				if (o.owner == -1) {
					return;
				}
				if (c.getOutStream() != null) {
					Mistex.itemHandler.createGroundItem(c,getData(trapData, o.objectId,Constants.OBJECTID, Constants.ITEMID),o.objectX, o.objectY, 1, c.getId());
				}
				org.mistex.game.world.gameobject.Objects empty = new org.mistex.game.world.gameobject.Objects(100,o.objectX, o.objectY, 0, 0, 10, 0);
				Mistex.objectHandler.placeObject(empty);
				Mistex.objectHandler.removeObject(o);
				o.owner = -1;
				o.objectX = 0;
				o.objectY = 0;
				c.sendMessage("Your trap has dismantled");
				container.stop();
			}

			@Override
			public void stop() {

			}
		}, 40 * 1);
		return true;
	}

	public static void checkTrap(final NPC n, final Client c) {
		if (getData(trapData, n.npcType, 0, 1) > -1) {
			final Objects o = checkClosestObjects(n,getData(trapData, n.npcType, 0, 1), n.npcId);
			if ((o != null && !n.isDead && distSquare(n.getX(), n.getY(),o.objectX, o.objectY) > 0)&& (((int) (Math.random() * (getData(trapData, n.npcType,Constants.NPCID, Constants.REQ)* (o.bait ? 10 : 20) + 50)) < (World.players[o.owner] == null ? 0 : World.players[o.owner].playerLevel[22])&& o.delay < System.currentTimeMillis() && n.delay < System.currentTimeMillis()) || n.npcId == o.target)) {
				if (World.players[o.owner] != null && World.players[o.owner].playerLevel[22] < getData(trapData, n.npcType, Constants.NPCID, Constants.REQ)) {
					return;
				}
				if (o.target != n.npcId)
					o.target = n.npcId;
				n.moveX = NPCHandler.GetMove(n.getX(), o.getObjectX());
				n.moveY = NPCHandler.GetMove(n.getY(), o.getObjectY());
				n.getNextNPCMovement(n.npcId);
				o.delay = System.currentTimeMillis() + 4000;
				n.delay = System.currentTimeMillis() + 2000;
			} else if (o != null && distSquare(n.getX(), n.getY(), o.objectX, o.objectY) <= 0 && o.oDelay < System.currentTimeMillis() && !n.isDead) {
				if (World.players[o.owner] != null && World.players[o.owner].playerLevel[22] < getData( trapData, n.npcType, Constants.NPCID, Constants.REQ)) {
					return;
				}
				n.noDeathEmote = true;
				n.forceAnim(getData(trapData, n.npcType, Constants.NPCID, Constants.ANIM));
				o.oDelay = System.currentTimeMillis() + 2000;
				final Objects catchObject = new Objects(getData(trapData, n.npcType, Constants.NPCID, Constants.CATCHID), o.objectX, o.objectY, 0, 0, 10, 0);
				catchObject.owner = o.owner;
				if (getSize(trapData, o.objectId, Constants.OBJECTID) < 12) {
					Mistex.objectHandler.addObject(catchObject);
					Mistex.objectHandler.placeObject(catchObject);
					Mistex.objectHandler.removeObject(o);
					o.owner = -1;
				} else {
					o.objectY -= 1;
					Objects empty = new Objects(100, o.objectX, o.objectY, 0, 0, 10, 0);
					Mistex.objectHandler.addObject(empty);
					Mistex.objectHandler.placeObject(empty);
					Mistex.objectHandler.removeObject(empty);
					empty.owner = -1;
					o.owner = -1;
				}
				Mistex.objectHandler.addObject(catchObject);
				Mistex.objectHandler.placeObject(catchObject);
				Mistex.objectHandler.removeObject(o);
				o.owner = -1;
				o.objectX = 0;
				o.objectY = 0;
				catchObject.item = getData(trapData, n.npcType, Constants.NPCID, Constants.LOOT);
				catchObject.xp = getData(trapData, n.npcType, Constants.NPCID, Constants.XP);
				CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {
					@Override
					public void execute(CycleEventContainer container) {
						if (catchObject.owner == -1) {
							return;
						}
						if (World.players[catchObject.owner] != null) {
							if (((Client) World.players[catchObject.owner]).getOutStream() != null) {
								Mistex.itemHandler.createGroundItem((Client)(World.players[catchObject.owner]),getData(trapData, n.npcType, Constants.NPCID, Constants.ITEMID),o.objectX,o.objectY,1,World.players[catchObject.owner].getId());
							}
						}
						Mistex.objectHandler.removeObject(catchObject);
						Objects empty = new Objects(100, o.objectX,o.objectY, 0, 0, 10, 0);
						Mistex.objectHandler.placeObject(empty);
						((Client) (World.players[catchObject.owner])).sendMessage("Your trap has dismantled.");
						catchObject.owner = -1;
						o.objectX = 0;
						o.objectY = 0;
						container.stop();
					}

					@Override
					public void stop() {

					}
				}, 20 * 1);
				n.animUpdateRequired = true;
				n.isDead = true;
				n.updateRequired = true;
				n.delay = System.currentTimeMillis() + 2000;
			} else if (o != null && n.npcId == o.target) {

			}
		}
	}

	private static int getSize(int[][] from, int data, int inputData) {
		for (int[] aFrom : from) {
			if (aFrom[inputData] == data) {
				return aFrom.length;
			}
		}
		return -1;
	}

	private static int distSquare(int x, int y, int tx, int ty) {
		return (int) Math.sqrt((Math.abs(x - tx) + Math.abs(y - ty)));
	}

	private static Objects checkClosestObjects(NPC n, int objectId, int npcId) {
		Objects closest = null;
		for (Objects o : ObjectHandler.globalObjects) {
			if (distSquare(n.getX(), n.getY(), o.objectX,
					o.objectY) < 5 && o.objectId == objectId) {
				if (closest == null) {
					closest = o;
				} else if (npcId == o.target) {
					return o;
				} else if (distSquare(n.getX(), n.getY(),
						o.objectX, o.objectY) > distSquare(
						n.getX(), n.getY(), o.objectX,
						o.objectY)) {
					closest = o;
				}
			}
		}
		return closest;
	}

}
