package org.mistex.game.world.content.skill.thieving;

import org.mistex.game.MistexUtility;
import org.mistex.game.world.content.Boxing;
import org.mistex.game.world.content.skill.SkillHandler;
import org.mistex.game.world.content.skill.Skill.Skills;
import org.mistex.game.world.event.CycleEvent;
import org.mistex.game.world.event.CycleEventContainer;
import org.mistex.game.world.event.CycleEventHandler;
import org.mistex.game.world.gameobject.ObjectHandler;
import org.mistex.game.world.npc.NPCHandler;
import org.mistex.game.world.player.Client;
import org.mistex.game.world.player.Player;
import org.mistex.game.world.player.PlayerConfiguration;

public class Thieving extends SkillHandler {
	
	public static void antiAutoClick(Client c) {	
		int random = MistexUtility.random(50);
			if (random == 1) {
				if (c.inDonatorZone() && c.getRights().isDonator()) {
					c.getPA().movePlayer(PlayerConfiguration.DONATORZONE_X, PlayerConfiguration.DONATORZONE_Y, 0);
				} else {
					c.getPA().movePlayer(PlayerConfiguration.RESPAWN_X, PlayerConfiguration.RESPAWN_Y, 0);
				}
				c.sendMessage("You have randomly spawned home!");
				c.handleHitMask(5);
				c.dealDamage(5);
				c.getPA().sendFrame107();
				c.startAnimation(4367);
			}
	}
	
	public static boolean canThieve(Client c) {
		if (System.currentTimeMillis() - c.lastThieve < 2500) {
			return false;
		}
		if (c.underAttackBy > 0 || c.underAttackBy2 > 0) {
			c.sendMessage("You can't steal from a stall while in combat!");
			return false;
		}
		if (Boxing.inArena(c)) {
			c.sendMessage("You can't steal while in the boxing arena!");
			return false;
		}
		return true;
	}
	
	public static void stealFromStall(final Client c, int id, int amount, int xp, int level) {
		if (!canThieve(c)) {
			return;
		}
		if (c.playerLevel[Player.playerThieving] >= level) {
			if (c.getItems().addItem(id,amount)) {
				c.startAnimation(832);
				if (c.doingDungeoneering == false)
					antiAutoClick(c);
				c.getPA().addSkillXP(xp, Player.playerThieving);
				c.lastThieve = System.currentTimeMillis();
				if (c.inDonatorZone() && c.getRights().isDonator()) {
					c.sendMessage("You steal some coins. You get extra since you are a donator.");
				} else {
					c.sendMessage("You steal some coins.");
				}
				CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {
					@Override
					public void execute(CycleEventContainer p) {
						p.stop();
					}

					@Override
					public void stop() {
					}
				}, 1);
			}
		} else {
			c.getPA().sendStatement("You must have a thieving level of " + level + " to thieve from this stall.");
		}
	}
	
	public static void getActionButtons(Client c, int objectId) {
		switch(objectId) {
		case 4874:
			if (c.doingDungeoneering) {
				int random = MistexUtility.random(2);
				switch (random) {
				case 0:
				case 1:
					Thieving.stealFromStall(c, 995, 1, 15, 1);	
					c.sendMessage("You manage to get a gold coin piece.");	
					break;
				case 2:
					c.sendMessage("You thieve nothing.");	
					break;
				}			
				return;
			}
			
			if (c.inDonatorZone() && c.getRights().isDonator()) {
				Thieving.stealFromStall(c, 995, 1500 + MistexUtility.random(750), 750, 1);		
			} else {
				Thieving.stealFromStall(c, 995, 1000 + MistexUtility.random(500), 750, 1);				
			}
			break;
		case 4875:
			if (c.inDonatorZone() && c.getRights().isDonator()) {
				Thieving.stealFromStall(c, 995, 2500 + MistexUtility.random(1000), 4500, 25);		
			} else {
				Thieving.stealFromStall(c, 995, 2000 + MistexUtility.random(500), 2750, 25);
			}
			break;		
		case 4876:
			if (c.inDonatorZone() && c.getRights().isDonator()) {
				Thieving.stealFromStall(c, 995, 5400 + MistexUtility.random(1500), 6500, 50);		
			} else {
				Thieving.stealFromStall(c, 995, 5000 + MistexUtility.random(500), 4500, 50);
			}
			break;	
		case 4877:
			if (c.inDonatorZone() && c.getRights().isDonator()) {
				Thieving.stealFromStall(c, 995, 10000 + MistexUtility.random(2000), 9000, 75);		
			} else {
				Thieving.stealFromStall(c, 995, 8000 + MistexUtility.random(3000), 7500, 75);
			}
			break;
		case 4878:
			if (c.inDonatorZone() && c.getRights().isDonator()) {
				Thieving.stealFromStall(c, 995, 15000 + MistexUtility.random(15000), 12500, 85);		
			} else {
				Thieving.stealFromStall(c, 995, 10000 + MistexUtility.random(10000), 10000, 85);
			}
			break;
		}
	}
	

	public static void stealFromBigStall(final Client c, final int id, int amount,int xp, int level, final int i, final int x, final int y,final int face) {
		if (System.currentTimeMillis() - c.lastThieve < 2500)
			return;
		if (c.underAttackBy > 0 || c.underAttackBy2 > 0) {
			c.sendMessage("You can't steal from a stall while in combat!");
			return;
		}
		if (c.playerLevel[Player.playerThieving] >= level) {
			if (c.getItems().addItem(id, amount)) {
				c.startAnimation(832);
				c.getPA().addSkillXP(xp * Skills.THIEVING.getMultiplier(), Player.playerThieving);
				c.lastThieve = System.currentTimeMillis();
				c.sendMessage("You steal some coins.");
				ObjectHandler.createAnObject(c, 634, x, y, face);
				CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {
					@Override
					public void execute(CycleEventContainer container) {
						ObjectHandler.createAnObject(c, i, x, y, face);
						container.stop();
					}

					@Override
					public void stop() {

					}
				}, getRespawnTime(c, i));
			}
		} else {
			c.sendMessage("You must have a thieving level of " + level+ " to thieve from this stall.");
		}
	}

	private static int getRespawnTime(Client c, int i) {
		switch (i) {
		case 2561:
			return 4; // BAKER
		case 2560:
			return 12; // SILK
		case 7053:
			return 12; // SEED
		case 2563:
			return 15; // FUR
		case 2565:
			return 20; // SILVER
		case 2564:
			return 25; // SPICE
		case 2562:
			return 30; // GEM
		case 14011:
			return 5; // JUG
		default:
			return 5;
		}
	}

	private static int[][] pickpocket = { { 1, 1, 8, 1532, 3, 10, 20, 30 },
			{ 2, 1, 8, 1532, 3, 10, 20, 30 }, { 3, 1, 8, 1532, 3, 10, 20, 30 },
			{ 4, 1, 8, 1532, 3, 10, 20, 30 }, { 5, 1, 8, 1532, 3, 10, 20, 30 },
			{ 6, 1, 8, 1532, 3, 10, 20, 30 },
			{ 3223, 1, 8, 1532, 3, 10, 20, 30 },
			{ 3224, 1, 8, 1532, 3, 10, 20, 30 },
			{ 3226, 1, 8, 1532, 3, 10, 20, 30 },
			{ 3227, 1, 8, 1532, 3, 10, 20, 30 },
			{ 3915, 1, 8, 1532, 3, 10, 20, 30 },
			{ 7, 10, 15, 2000, 3, 20, 30, 40 },
			{ 9, 40, 47, 2355, 3, 50, 60, 70 },
			{ 15, 25, 26, 2164, 3, 35, 45, 55 },
			{ 20, 70, 151, 4241, 8, 80, 90, 99 },
			{ 2256, 70, 151, 4241, 8, 80, 90, 99 },
			{ 21, 80, 275, 6643, 8, 90, 95, 99 },
			{ 23, 55, 84, 3174, 7, 65, 75, 85 },
			{ 26, 55, 84, 3174, 7, 65, 75, 85 },
			{ 18, 25, 26, 2164, 3, 35, 45, 55 },
			{ 3225, 10, 15, 2000, 3, 20, 30, 40 },
			{ 2234, 38, 43, 2532, 7, 48, 58, 68 },
			{ 2235, 38, 43, 2532, 7, 48, 58, 68 },
			{ 32, 40, 47, 2753, 3, 50, 60, 70 },
			{ 296, 40, 47, 2753, 3, 50, 60, 70 },
			{ 297, 40, 47, 2753, 3, 50, 60, 70 },
			{ 298, 40, 47, 2753, 3, 50, 60, 70 },
			{ 299, 40, 47, 2753, 3, 50, 60, 70 },
			{ 2699, 40, 47, 2753, 3, 50, 60, 70 },
			{ 2701, 40, 47, 2753, 3, 50, 60, 70 },
			{ 2702, 40, 47, 2753, 3, 50, 60, 70 },
			{ 2703, 40, 47, 2753, 3, 50, 60, 70 },
			{ 3228, 40, 47, 2753, 3, 50, 60, 70 },
			{ 3229, 40, 47, 2753, 3, 50, 60, 70 },
			{ 3230, 40, 47, 2753, 3, 50, 60, 70 },
			{ 3231, 40, 47, 2753, 3, 50, 60, 70 },
			{ 3232, 40, 47, 2753, 3, 50, 60, 70 },
			{ 3233, 40, 47, 2753, 3, 50, 60, 70 },
			{ 3241, 40, 47, 2753, 3, 50, 60, 70 },
			{ 4307, 40, 47, 2753, 3, 50, 60, 70 },
			{ 4308, 40, 47, 2753, 3, 50, 60, 70 },
			{ 4309, 40, 47, 2753, 3, 50, 60, 70 },
			{ 4310, 40, 47, 2753, 3, 50, 60, 70 },
			{ 4311, 40, 47, 2753, 3, 50, 60, 70 },
			{ 5919, 40, 47, 2753, 3, 50, 60, 70 },
			{ 5920, 40, 47, 2753, 3, 50, 60, 70 }, };

	public static boolean pickpocketNPC(Client c, int npc) {
		for (int i = 0; i < pickpocket.length; i++) {
			if (npc == pickpocket[i][0]) {
				return true;
			}
		}
		return false;
	}
	

	private static void failThieve(final Client c, final int i, final int npc) {
		CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {
			@Override
			public void execute(CycleEventContainer container) {
				c.setHitDiff(pickpocket[i][4]);
				c.setHitUpdateRequired(true);
				c.playerLevel[3] -= pickpocket[i][4];
				c.getPA().refreshSkill(3);

				c.gfx100(80);
				c.startAnimation(404);

				c.lastThieve = System.currentTimeMillis() + 7500;
				c.sendMessage("...you fail to pickpocket the "+ NPCHandler.getNpcListName(pickpocket[i][0]).replace("_", " ") + "!");
				c.playerStun = true;
				container.stop();
			}

			@Override
			public void stop() {

			}
		}, 2);
		CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {
			@Override
			public void execute(CycleEventContainer container) {
				c.playerStun = false;
				container.stop();
			}

			@Override
			public void stop() {

			}
		}, 13);
	}

	private static void completeThieve(final Client c, final int i) {
		int loot = pickpocket[i][3];
		int chance = MistexUtility.random(20);
		String message = "...You successfully pickpocket the "
				+ NPCHandler.getNpcListName(pickpocket[i][0]) + ".";
		if (chance == 1) {
			if (c.playerLevel[Player.playerAgility] >= pickpocket[i][5]) {
				loot = pickpocket[i][3] * 2;
				message = "You steal a double loot from the the "
						+ NPCHandler.getNpcListName(pickpocket[i][0]) + "!";
			}
		} else if (chance == 5) {
			if (c.playerLevel[Player.playerAgility] >= pickpocket[i][6]) {
				loot = pickpocket[i][3] * 3;
				message = "You steal a triple loot from the "
						+ NPCHandler.getNpcListName(pickpocket[i][0]) + "!";
			}
		} else if (chance == 10) {
			if (c.playerLevel[Player.playerAgility] >= pickpocket[i][7]) {
				loot = pickpocket[i][3] * 4;
				message = "You steal a trebble loot from the "
						+ NPCHandler.getNpcListName(pickpocket[i][0]) + "!";
			}
		}
		final int loot2 = loot;
		final String message2 = message;
		CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {
			@Override
			public void execute(CycleEventContainer container) {
				c.sendMessage(message2);
				c.getPA().addSkillXP(pickpocket[i][2] * Skills.THIEVING.getMultiplier(), 17);
				c.getItems().addItem(995, loot2);
				container.stop();
			}

			@Override
			public void stop() {

			}
		}, 2);
	}

	public static void attemptToPickpocket(final Client c, int npcId) {
		if (System.currentTimeMillis() - c.lastThieve < 2000 || c.playerStun) {
			return;
		}
		if (c.underAttackBy > 0 || c.underAttackBy2 > 0) {
			c.sendMessage("You can't pickpocket while in combat!");
			return;
		}
		for (int i = 0; i < pickpocket.length; i++) {
			if (npcId == pickpocket[i][0]) {
				if (!hasRequiredLevel(c, 17, pickpocket[i][1], "thieving",
						"pickpocket this")) {
					return;
				}
				c.sendMessage("You attempt to pickpocket the "+ NPCHandler.getNpcListName(pickpocket[i][0]) + "...");
				c.startAnimation(881);
				if (MistexUtility.random(c.playerLevel[17] + 5) < MistexUtility.random(pickpocket[i][1])) {
					failThieve(c, i, npcId);
				} else {
					completeThieve(c, i);
				}
				c.lastThieve = System.currentTimeMillis();
			}
		}
	}
}