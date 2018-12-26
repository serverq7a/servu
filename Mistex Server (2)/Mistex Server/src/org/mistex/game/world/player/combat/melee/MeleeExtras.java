package org.mistex.game.world.player.combat.melee;

import org.mistex.game.MistexUtility;
import org.mistex.game.world.World;
import org.mistex.game.world.event.CycleEvent;
import org.mistex.game.world.event.CycleEventContainer;
import org.mistex.game.world.event.CycleEventHandler;
import org.mistex.game.world.npc.NPCHandler;
import org.mistex.game.world.player.Client;
import org.mistex.game.world.player.Player;


public class MeleeExtras {

	public static void applySmite(Client c, int index, int damage) {
		if (!c.prayerActive[23])
			return;
		if (damage <= 0)
			return;
		if (World.players[index] != null) {
			Client c2 = (Client) World.players[index];
			c2.playerLevel[5] -= (int) (damage / 4);
			if (c2.playerLevel[5] <= 0) {
				c2.playerLevel[5] = 0;
				c2.getCombat().resetPrayers();
			}
			c2.getPA().refreshSkill(5);
		}
	}

	@SuppressWarnings("unused")
	public static void handleDragonFireShield(final Client c) {
		if (World.players[c.followId].playerLevel[3] <= 0) {
			return;
		}
		if (c.playerEquipment[c.playerShield] != 11283 | c.playerEquipment[c.playerShield] != 11283) {
			return;
		}		
		if (System.currentTimeMillis() - c.dfsDelay < 60000) {
			c.sendMessage("Your shield hasen't cooled off yet!");
			return;
		}
		final int pX = c.getX();
		final int pY = c.getY();
		final int oX = World.players[c.followId].getX();
		final int oY = World.players[c.followId].getY();
		final int offX = (pY - oY) * -1;
		final int offY = (pX - oX) * -1;
		final int damage = MistexUtility.random(25) + 5;
		// c.dfsCount = 0;
		c.startAnimation(6696);
		c.gfx0(1165);
		c.attackTimer += 3;
		c.dfsDelay = System.currentTimeMillis();
		CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {
			@Override
			public void execute(CycleEventContainer container) {
				c.getPA().createPlayersProjectile(pX, pY, offX, offY, 50, 10, 1166, 25, 27, c.followId - 1, 0);
				container.stop();
			}

			@Override
			public void stop() {

			}
		}, 3);
		CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {
			@Override
			public void execute(CycleEventContainer container) {
//				World.players[c.followId].gfx100(1167);
//				World.players[c.followId].playerLevel[3] -= damage;
//				if (!World.players[c.followId].getHitUpdateRequired()) {
//					World.players[c.followId].setHitDiff(damage);
//					World.players[c.followId].setHitUpdateRequired(true);
//				} else if (!World.players[c.followId].getHitUpdateRequired2()) {
//					World.players[c.followId].setHitDiff2(damage);
//					World.players[c.followId].setHitUpdateRequired2(true);
//				}
//				World.players[c.followId].updateRequired = true;
				container.stop();
			}

			@Override
			public void stop() {

			}
		}, 3);
	}

	public static void handleDragonFireShieldNPC(final Client c) {
		if (NPCHandler.npcs[c.npcIndex].HP <= 0) {
			return;
		}
		if (c.playerEquipment[c.playerShield] != 11283 | c.playerEquipment[c.playerShield] != 11283) {
			return;
		}
		if (System.currentTimeMillis() - c.dfsDelay < 60000) {
			c.sendMessage("Your shield hasen't cooled off yet!");
			return;
		}
			if (NPCHandler.npcs[c.npcIndex].HP <= 1) {
				return;
			}
			final int pX = c.getX();
			final int pY = c.getY();
			final int nX = NPCHandler.npcs[c.npcIndex].getX();
			final int nY = NPCHandler.npcs[c.npcIndex].getY();
			final int offX = (pY - nY) * -1;
			final int offY = (pX - nX) * -1;
			final int damage = MistexUtility.random(25) + 5;
			c.dfsCount = 0;
			c.startAnimation(6696);
			c.dfsDelay = System.currentTimeMillis();
			c.gfx0(1165);
			c.attackTimer += 3;
			CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {
				@Override
				public void execute(CycleEventContainer container) {
					c.getPA().createPlayersProjectile(pX, pY, offX, offY, 50, 10, 1166, 25, 27, c.npcIndex + 1, 0);
					container.stop();
				}

				@Override
				public void stop() {

				}
			}, 3);
			CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {
				@Override
				public void execute(CycleEventContainer container) {
					NPCHandler.npcs[c.npcIndex].gfx100(1167);
					NPCHandler.npcs[c.npcIndex].handleHitMask(damage);
					NPCHandler.npcs[c.npcIndex].HP -= damage;
					container.stop();
				}

				@Override
				public void stop() {

				}
			}, 3);
	}

	public static void addCharge(Client c) {
		/*if (c.playerEquipment[c.playerShield] == 11283 || c.playerEquipment[c.playerShield] == 11284) {
			c.dfsCount += 1;
			c.startAnimation(6695);
			c.gfx0(1164);
			c.sendMessage("Your shield stores up the dragonfire.");
		if (c.dfsCount >= 40) {
			c.dfsCount = 40;
		}
		if (c.dfsCount == 39) {
			c.sendMessage("Your dragon fireshield has finished charging!");
		}
		} else {
			c.sendMessage("block");		
		}*/
	}

	public static void appendVengeanceNPC(Client c, int otherPlayer, int damage) {
		if (damage <= 0)
			return;
		if (c.npcIndex > 0 && NPCHandler.npcs[c.npcIndex] != null) {
			c.forcedText = "Taste vengeance!";
			c.forcedChatUpdateRequired = true;
			c.updateRequired = true;
			c.vengOn = false;
			if ((NPCHandler.npcs[c.npcIndex].HP - damage) > 0) {
				damage = (int) (damage * 0.75);
				if (damage > NPCHandler.npcs[c.npcIndex].HP) {
					damage = NPCHandler.npcs[c.npcIndex].HP;
				}
				NPCHandler.npcs[c.npcIndex].HP -= damage;
				NPCHandler.npcs[c.npcIndex].handleHitMask(damage);
			}
		}
		c.updateRequired = true;
	}

	public static void appendVengeance(Client c, int otherPlayer, int damage) {
		if (damage <= 0)
			return;
		Player o = World.players[otherPlayer];
		o.forcedText = "Taste vengeance!";
		o.forcedChatUpdateRequired = true;
		o.updateRequired = true;
		o.vengOn = false;
		if ((o.playerLevel[3] - damage) > 0) {
			damage = (int) (damage * 0.75);
			if (damage > c.playerLevel[3]) {
				damage = c.playerLevel[3];
			}
			if (!c.getHitUpdateRequired()) {
				c.setHitDiff(damage);
				c.setHitUpdateRequired(true);
			} else if (!c.getHitUpdateRequired2()) {
				c.setHitDiff2(damage);
				c.setHitUpdateRequired2(true);
			}
			c.playerLevel[3] -= damage;
			c.getPA().refreshSkill(3);
		}
		c.updateRequired = true;
	}

	public static void applyRecoilNPC(Client c, int damage, int i) {
		if (damage > 0 && c.playerEquipment[c.playerRing] == 2550) {
			int recDamage = damage / 10 + 1;
			NPCHandler.npcs[c.npcIndex].HP -= recDamage;
			NPCHandler.npcs[c.npcIndex].handleHitMask(recDamage);
			removeRecoil(c);
			c.recoilHits += damage;
		}
	}

	public static void applyRecoil(Client c, int damage, int i) {
		if (damage > 0 && World.players[i].playerEquipment[c.playerRing] == 2550) {
			int recDamage = damage / 10 + 1;
			if (!c.getHitUpdateRequired()) {
				c.setHitDiff(recDamage);
				c.setHitUpdateRequired(true);
			} else if (!c.getHitUpdateRequired2()) {
				c.setHitDiff2(recDamage);
				c.setHitUpdateRequired2(true);
			}
			c.dealDamage(recDamage);
			c.updateRequired = true;
			removeRecoil(c);
			c.recoilHits += damage;
		}
	}

	public static void removeRecoil(Client c) {
		if (c.recoilHits >= 400) {
			c.getItems().removeItem(2550, c.playerRing);
			c.getItems().deleteItem(2550, c.getItems().getItemSlot(2550), 1);
			c.sendMessage("Your ring of recoil shaters!");
			c.recoilHits = 0;
		} else {
			c.recoilHits++;
		}
	}

	public static void graniteMaulSpecial(Client c) {
		if (c.playerIndex > 0) {
			Client o = (Client) World.players[c.playerIndex];
			if (c.goodDistance(c.getX(), c.getY(), o.getX(), o.getY(), c.getCombat().getRequiredDistance())) {
				if (c.getCombat().checkReqs()) {
					if (c.getCombat().checkSpecAmount(4153)) {
						boolean hit = MistexUtility.random(c.getCombat().calculateMeleeAttack()) > MistexUtility.random(o.getCombat().calculateMeleeDefence());
						int damage = 0;
						if (hit)
							damage = MistexUtility.random(c.getCombat().calculateMeleeMaxHit());
						if (o.prayerActive[18] && System.currentTimeMillis() - o.protMeleeDelay > 1500)
							damage *= .6;
						if (o.playerLevel[3] - damage <= 0) {
							damage = o.playerLevel[3];
						}
						if (o.playerLevel[3] > 0) {
							o.handleHitMask(damage);
							c.startAnimation(1667);
							o.gfx100(337);
							o.dealDamage(damage);
						}
					}
				}
			}
		} else if (c.npcIndex > 0) {
			int x = NPCHandler.npcs[c.npcIndex].absX;
			int y = NPCHandler.npcs[c.npcIndex].absY;
			if (c.goodDistance(c.getX(), c.getY(), x, y, 2)) {
				if (c.getCombat().checkReqs()) {
					if (c.getCombat().checkSpecAmount(4153)) {
						int damage = MistexUtility.random(c.getCombat().calculateMeleeMaxHit());
						if (NPCHandler.npcs[c.npcIndex].HP - damage < 0) {
							damage = NPCHandler.npcs[c.npcIndex].HP;
						}
						if (NPCHandler.npcs[c.npcIndex].HP > 0) {
							NPCHandler.npcs[c.npcIndex].HP -= damage;
							NPCHandler.npcs[c.npcIndex].handleHitMask(damage);
							c.startAnimation(1667);
							c.gfx100(337);
						}
					}
				}
			}
		}
	}
}