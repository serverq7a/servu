package org.mistex.game.world.player.combat;

import org.mistex.game.Mistex;
import org.mistex.game.MistexConfiguration;
import org.mistex.game.MistexUtility;
import org.mistex.game.world.World;
import org.mistex.game.world.event.CycleEvent;
import org.mistex.game.world.event.CycleEventContainer;
import org.mistex.game.world.event.CycleEventHandler;
import org.mistex.game.world.player.Client;
import org.mistex.game.world.player.PlayerConfiguration;
import org.mistex.game.world.player.combat.range.RangeData;

@SuppressWarnings("static-access")
public class AttackPlayer {

	public static void applyPlayerHit(final Client c, final int i) {
		c.stopPlayerSkill = false;
		c.usingClaws = false;
		// where is spec handled?
		if (!c.usingClaws) { /* !usingClaws */
			c.getCombat().applyPlayerMeleeDamage(i, 1, MistexUtility.random(c.getCombat().calculateMeleeMaxHit()));
			if (c.doubleHit) {
				if (!c.oldSpec) {
					c.getCombat().applyPlayerMeleeDamage(i, 2, MistexUtility.random(c.getCombat().calculateMeleeMaxHit()));
				} else {
					CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {
						@Override
						public void execute(CycleEventContainer container) {
							c.getCombat().applyPlayerMeleeDamage(i, 2, MistexUtility.random(c.getCombat().calculateMeleeMaxHit()));
							container.stop();
						}

						@Override
						public void stop() {

						}
					}, 1);
				}
			}
		} /* !usingClaws */
	}

	public static void applyPlayerMeleeDamage(Client c, int i, int damageMask, int damage) {
		c.previousDamage = damage;

		Client o = (Client) World.players[i];
		if (o == null) {
			return;
		}
		boolean veracsEffect = false;
		boolean guthansEffect = false;
		if (c.getPA().fullVeracs()) {
			if (MistexUtility.random(4) == 1) {
				veracsEffect = true;
			}
		}
		if (c.getPA().fullGuthans()) {
			if (MistexUtility.random(4) == 1) {
				guthansEffect = true;
			}
		}
		if (!c.usingClaws) {
			if (damageMask == 1) {
				damage = c.delayedDamage;
				c.delayedDamage = 0;
			} else {
				damage = c.delayedDamage2;
				c.delayedDamage2 = 0;
			}
		}
		if (MistexUtility.random(o.getCombat().calculateMeleeDefence()) > MistexUtility.random(c.getCombat().calculateMeleeAttack()) && !veracsEffect) {
			damage = 0;
			c.bonusAttack = 0;
		} else if (c.playerEquipment[c.playerWeapon] == 5698 && o.poisonDamage <= 0 && MistexUtility.random(3) == 1) {
			o.getPA().appendPoison(13);
			c.bonusAttack += damage / 3;
		} else {
			c.bonusAttack += damage / 3;
		}
		if (o.curseActive[9]) {
			o.gfx0(2230);
			o.startAnimation(12573);
			if (damage > 0) {
				c.handleHitMask(1);
				c.dealDamage(1);
			}
		}
		if (o.prayerActive[18] || o.curseActive[9] && System.currentTimeMillis() - o.protMeleeDelay > 1500 && !veracsEffect) { // if
																																// prayer
																																// active
																																// reduce
																																// damage
																																// by
																																// 40%
			damage = (int) damage * 60 / 100;
		}
		if (c.maxNextHit && !c.usingClaws) {
			damage = c.getCombat().calculateMeleeMaxHit();
		}
		if (c.doubleHit && c.usingClaws) {
			c.getCombat().applyPlayerMeleeDamage(i, 2, c.previousDamage / 2);
		}
		if (damage > 0 && guthansEffect) {
			c.playerLevel[3] += damage;
			if (c.playerLevel[3] > c.getLevelForXP(c.playerXP[3]))
				c.playerLevel[3] = c.getLevelForXP(c.playerXP[3]);
			c.getPA().refreshSkill(3);
			o.gfx0(398);
		}
		if (c.ssSpec && damageMask == 2) {
			damage = 5 + MistexUtility.random(11);
			c.ssSpec = false;
		}
		if (World.players[i].playerLevel[3] - damage < 0) {
			damage = World.players[i].playerLevel[3];
		}
		if (o.vengOn && damage > 0)
			c.getCombat().appendVengeance(i, damage);
		if (damage > 0)
			c.getCombat().applyRecoil(damage, i);
		switch (c.specEffect) {
		case 5:
			c.clawDelay = 2;
			break;
		case 1: // dragon scimmy special
			if (damage > 0) {
				if (o.prayerActive[16] || o.prayerActive[17] || o.prayerActive[18]) {
					o.headIcon = -1;
					o.getPA().sendFrame36(c.PRAYER_GLOW[16], 0);
					o.getPA().sendFrame36(c.PRAYER_GLOW[17], 0);
					o.getPA().sendFrame36(c.PRAYER_GLOW[18], 0);
				}
				o.sendMessage("You have been injured!");
				o.stopPrayerDelay = System.currentTimeMillis();
				o.prayerActive[16] = false;
				o.prayerActive[17] = false;
				o.prayerActive[18] = false;
				o.getPA().requestUpdates();
			}
			break;
		case 2:
			if (damage > 0) {
				if (o.freezeTimer <= 0)
					o.freezeTimer = 30;
				o.gfx0(369);
				o.sendMessage("You have been frozen.");
				o.frozenBy = c.playerId;
				o.stopMovement();
				c.sendMessage("You freeze your enemy.");
			}
			break;
		case 3:
			if (damage > 0) {
				o.playerLevel[1] -= damage;
				o.sendMessage("You feel weak.");
				if (o.playerLevel[1] < 1)
					o.playerLevel[1] = 1;
				o.getPA().refreshSkill(1);
			}
			break;
		case 4:
			if (damage > 0) {
				if (c.playerLevel[3] + damage > c.getLevelForXP(c.playerXP[3]))
					if (c.playerLevel[3] > c.getLevelForXP(c.playerXP[3]))
						;
					else
						c.playerLevel[3] = c.getLevelForXP(c.playerXP[3]);
				else
					c.playerLevel[3] += damage;
				c.getPA().refreshSkill(3);
			}
			break;
		}
		c.specEffect = 0;
		if (c.fightMode == 3) { // check
			c.getPA().addSkillXP((damage * PlayerConfiguration.MELEE_EXP_RATE / 3), 0);
			c.getPA().addSkillXP((damage * PlayerConfiguration.MELEE_EXP_RATE / 3), 1);
			c.getPA().addSkillXP((damage * PlayerConfiguration.MELEE_EXP_RATE / 3), 2);
			c.getPA().addSkillXP((damage * PlayerConfiguration.MELEE_EXP_RATE / 3), 3);
			c.getPA().refreshSkill(0);
			c.getPA().refreshSkill(1);
			c.getPA().refreshSkill(2);
			c.getPA().refreshSkill(3);
		} else {
			c.getPA().addSkillXP((damage * PlayerConfiguration.MELEE_EXP_RATE), c.fightMode);
			c.getPA().addSkillXP((damage * PlayerConfiguration.MELEE_EXP_RATE / 3), 3);
			c.getPA().refreshSkill(c.fightMode);
			c.getPA().refreshSkill(3);
		}
		World.players[i].logoutDelay = System.currentTimeMillis();
		World.players[i].underAttackBy = c.playerId;
		World.players[i].killerId = c.playerId;
		World.players[i].singleCombatDelay = System.currentTimeMillis();
		if (c.killedBy != World.players[i].playerId)
			c.totalPlayerDamageDealt = 0;
		c.killedBy = World.players[i].playerId;
		c.getCombat().applySmite(i, damage);
		if (c.soulSplitDelay <= 1) {
			c.getCombat().applySoulSplit(i, damage);
		}
		switch (damageMask) {
		case 1:
			World.players[i].dealDamage(damage);
			World.players[i].damageTaken[c.playerId] += damage;
			c.totalPlayerDamageDealt += damage;
			World.players[i].updateRequired = true;
			o.getPA().refreshSkill(3);
			break;

		case 2:
			World.players[i].dealDamage(damage);
			World.players[i].damageTaken[c.playerId] += damage;
			c.totalPlayerDamageDealt += damage;
			World.players[i].updateRequired = true;
			c.doubleHit = false;
			o.getPA().refreshSkill(3);
			break;
		}
		World.players[i].handleHitMask(damage);
	}

	public static void playerDelayedHit(final Client c, final int i) {
		if (World.players[i] != null) {
			if (World.players[i].isDead || c.isDead || World.players[i].playerLevel[3] <= 0 || c.playerLevel[3] <= 0) {
				c.playerIndex = 0;
				return;
			}
			if (World.players[i].respawnTimer > 0) {
				c.faceUpdate(0);
				c.playerIndex = 0;
				return;
			}
			Client o = (Client) World.players[i];
			o.getPA().removeAllWindows();
			if (o.playerIndex <= 0 && o.npcIndex <= 0) {
				if (o.autoRet == 1) {
					o.playerIndex = c.playerId;
				}
			}

			if (o.attackTimer <= 3 || o.attackTimer == 0 && o.playerIndex == 0 && !c.castingMagic) { // block
																										// animation
				o.startAnimation(o.getCombat().getBlockEmote());
			}
			if (o.inTrade) {
				o.getTrade().declineTrade();
			}
			if (c.projectileStage == 0 && !c.usingMagic) { // melee hit damage
				c.getCombat().applyPlayerHit(c, i);
			}
			c.getCombat().addCharge();
			if (!c.castingMagic && c.projectileStage > 0) { // range hit damage
				int damage = MistexUtility.random(c.getCombat().rangeMaxHit());
				int damage2 = -1;
				if (c.lastWeaponUsed == 11235 || c.bowSpecShot == 1) {
					damage2 = MistexUtility.random(c.getCombat().rangeMaxHit());
				}
				c.rangeEndGFX = RangeData.getRangeEndGFX(c);

				if (MistexUtility.random(10 + o.getCombat().calculateRangeDefence()) > MistexUtility.random(10 + c.getCombat().calculateRangeAttack()) && !c.ignoreDefence) {
					damage = 0;
				}
				if (c.playerEquipment[3] == 9185) {
					if (MistexUtility.random(10) == 1) {
						if (damage > 0) {
							c.boltDamage = damage;
							c.getCombat().crossbowSpecial(c, i);
							damage *= c.crossbowDamage;
						}
					}
				}
				if (c.lastWeaponUsed == 11235 || c.bowSpecShot == 1) {
					if (MistexUtility.random(10 + o.getCombat().calculateRangeDefence()) > MistexUtility.random(10 + c.getCombat().calculateRangeAttack()))
						damage2 = 0;
				}

				if (c.dbowSpec) {
					o.gfx100(c.lastArrowUsed == 11212 ? 1100 : 1103);
					if (damage < 8)
						damage = 8;
					if (damage2 < 8)
						damage2 = 8;
					c.dbowSpec = false;
				}
				if (o.curseActive[8]) {
					o.gfx0(2229);
					o.startAnimation(12573);
					if (damage > 0) {
						c.handleHitMask(1);
						c.dealDamage(1);
					}
				}
				if (o.prayerActive[17] || o.curseActive[8] && System.currentTimeMillis() - o.protRangeDelay > 1500) {
					damage = (int) damage * 60 / 100;
					if (c.lastWeaponUsed == 11235 || c.bowSpecShot == 1)
						damage2 = (int) damage2 * 60 / 100;
				}
				if (World.players[i].playerLevel[3] - damage < 0) {
					damage = World.players[i].playerLevel[3];
				}
				if (World.players[i].playerLevel[3] - damage - damage2 < 0) {
					damage2 = World.players[i].playerLevel[3] - damage;
				}
				if (damage < 0)
					damage = 0;
				if (damage2 < 0 && damage2 != -1)
					damage2 = 0;
				if (o.vengOn) {
					c.getCombat().appendVengeance(i, damage);
					c.getCombat().appendVengeance(i, damage2);
				}
				if (damage > 0)
					c.getCombat().applyRecoil(damage, i);
				if (damage2 > 0)
					c.getCombat().applyRecoil(damage2, i);
				if (c.fightMode == 3) {
					c.getPA().addSkillXP((damage * PlayerConfiguration.RANGE_EXP_RATE / 3), 4);
					c.getPA().addSkillXP((damage * PlayerConfiguration.RANGE_EXP_RATE / 3), 1);
					c.getPA().addSkillXP((damage * PlayerConfiguration.RANGE_EXP_RATE / 3), 3);
					c.getPA().refreshSkill(1);
					c.getPA().refreshSkill(3);
					c.getPA().refreshSkill(4);
				} else {
					c.getPA().addSkillXP((damage * PlayerConfiguration.RANGE_EXP_RATE), 4);
					c.getPA().addSkillXP((damage * PlayerConfiguration.RANGE_EXP_RATE / 3), 3);
					c.getPA().refreshSkill(3);
					c.getPA().refreshSkill(4);
				}
				boolean dropArrows = true;

				for (int noArrowId : c.NO_ARROW_DROP) {
					if (c.lastWeaponUsed == noArrowId) {
						dropArrows = false;
						break;
					}
				}
				if (dropArrows) {

					c.getItems().dropArrowPlayer();

				}
				if (c.rangeEndGFX > 0 && !c.getCombat().usingBolts(c.lastArrowUsed)) {
					if (c.rangeEndGFXHeight) {
						o.gfx100(c.rangeEndGFX);
					} else {
						o.gfx0(c.rangeEndGFX);
					}
				}
				World.players[i].underAttackBy = c.playerId;
				World.players[i].logoutDelay = System.currentTimeMillis();
				World.players[i].singleCombatDelay = System.currentTimeMillis();
				World.players[i].killerId = c.playerId;
				World.players[i].dealDamage(damage);
				World.players[i].damageTaken[c.playerId] += damage;
				c.killedBy = World.players[i].playerId;
				World.players[i].handleHitMask(damage);
				c.ignoreDefence = false;
				if (damage2 != -1) {
					World.players[i].dealDamage(damage2);
					World.players[i].damageTaken[c.playerId] += damage2;
					World.players[i].handleHitMask(damage2);
				}
				o.getPA().refreshSkill(3);
				World.players[i].updateRequired = true;
				c.getCombat().applySmite(i, damage);
				if (c.soulSplitDelay <= 1) {
					c.getCombat().applySoulSplit(i, damage);// makes it like
															// smite
				}
				if (damage2 != -1)
					c.getCombat().applySmite(i, damage2);

			} else if (c.projectileStage > 0) { // magic hit damageno0b
				int damage = 0;
				if (c.spellSwap) {
					c.spellSwap = false;
					c.setSidebarInterface(6, 16640);
					c.playerMagicBook = 2;
					c.gfx0(-1);
				}
				if (c.fullVoidMage() && c.playerEquipment[c.playerWeapon] == 8841) {
					damage = MistexUtility.random(c.getCombat().magicMaxHit() + 13);
				} else {
					damage = MistexUtility.random(c.getCombat().magicMaxHit());
				}
				if (c.getCombat().godSpells()) {
					if (System.currentTimeMillis() - c.godSpellDelay < PlayerConfiguration.GOD_SPELL_CHARGE) {
						damage += 10;
					}
				}
				if (c.magicFailed)
					damage = 0;
				if (o.curseActive[7]) {
					o.gfx0(2228);
					o.startAnimation(12573);
					if (damage > 0) {
						c.handleHitMask(1);
						c.dealDamage(1);
					}
				}
				if (o.prayerActive[16] || o.curseActive[7] && System.currentTimeMillis() - o.protMageDelay > 1500) {
					damage = (int) damage * 60 / 100;
				}
				if (World.players[i].playerLevel[3] - damage < 0) {
					damage = World.players[i].playerLevel[3];
				}
				if (o.vengOn)
					c.getCombat().appendVengeance(i, damage);
				if (damage > 0)
					c.getCombat().applyRecoil(damage, i);
				if (c.magicDef) {
					c.getPA().addSkillXP((damage * PlayerConfiguration.MELEE_EXP_RATE / 3), 1);
					c.getPA().refreshSkill(1);
				}
				c.getPA().addSkillXP((c.MAGIC_SPELLS[c.oldSpellId][7] + damage * PlayerConfiguration.MAGIC_EXP_RATE), 6);
				c.getPA().addSkillXP((c.MAGIC_SPELLS[c.oldSpellId][7] + damage * PlayerConfiguration.MAGIC_EXP_RATE / 3), 3);
				c.getPA().refreshSkill(3);
				c.getPA().refreshSkill(6);

				if (c.getCombat().getEndGfxHeight() == 100 && !c.magicFailed) {
					World.players[i].gfx100(c.MAGIC_SPELLS[c.oldSpellId][5]);
				} else if (!c.magicFailed) {
					World.players[i].gfx0(c.MAGIC_SPELLS[c.oldSpellId][5]);
				} else if (c.magicFailed) {
					World.players[i].gfx100(85);
				}

				if (!c.magicFailed) {
					if (System.currentTimeMillis() - World.players[i].reduceStat > 35000) {
						World.players[i].reduceStat = System.currentTimeMillis();
						switch (c.MAGIC_SPELLS[c.oldSpellId][0]) {
						case 12987:
						case 13011:
						case 12999:
						case 13023:
							World.players[i].playerLevel[0] -= ((o.getPA().getLevelForXP(World.players[i].playerXP[0]) * 10) / 100);
							break;
						}
					}

					switch (c.MAGIC_SPELLS[c.oldSpellId][0]) {
					case 12445: // teleblock
						if (System.currentTimeMillis() - o.teleBlockDelay > o.teleBlockLength) {
							o.teleBlockDelay = System.currentTimeMillis();
							o.sendMessage("You have been teleblocked.");
							if (o.prayerActive[16] && System.currentTimeMillis() - o.protMageDelay > 1500)
								o.teleBlockLength = 150000;
							else
								o.teleBlockLength = 300000;
						}
						break;

					case 12901:
					case 12919: // blood spells
					case 12911:
					case 12929:
						int heal = (int) (damage / 4);
						if (c.playerLevel[3] + heal > c.getPA().getLevelForXP(c.playerXP[3])) {
							c.playerLevel[3] = c.getPA().getLevelForXP(c.playerXP[3]);
						} else {
							c.playerLevel[3] += heal;
						}
						c.getPA().refreshSkill(3);
						break;

					case 1153:
						World.players[i].playerLevel[0] -= ((o.getPA().getLevelForXP(World.players[i].playerXP[0]) * 5) / 100);
						o.sendMessage("Your attack level has been reduced!");
						World.players[i].reduceSpellDelay[c.reduceSpellId] = System.currentTimeMillis();
						o.getPA().refreshSkill(0);
						break;

					case 1157:
						World.players[i].playerLevel[2] -= ((o.getPA().getLevelForXP(World.players[i].playerXP[2]) * 5) / 100);
						o.sendMessage("Your strength level has been reduced!");
						World.players[i].reduceSpellDelay[c.reduceSpellId] = System.currentTimeMillis();
						o.getPA().refreshSkill(2);
						break;

					case 1161:
						World.players[i].playerLevel[1] -= ((o.getPA().getLevelForXP(World.players[i].playerXP[1]) * 5) / 100);
						o.sendMessage("Your defence level has been reduced!");
						World.players[i].reduceSpellDelay[c.reduceSpellId] = System.currentTimeMillis();
						o.getPA().refreshSkill(1);
						break;

					case 1542:
						World.players[i].playerLevel[1] -= ((o.getPA().getLevelForXP(World.players[i].playerXP[1]) * 10) / 100);
						o.sendMessage("Your defence level has been reduced!");
						World.players[i].reduceSpellDelay[c.reduceSpellId] = System.currentTimeMillis();
						o.getPA().refreshSkill(1);
						break;

					case 1543:
						World.players[i].playerLevel[2] -= ((o.getPA().getLevelForXP(World.players[i].playerXP[2]) * 10) / 100);
						o.sendMessage("Your strength level has been reduced!");
						World.players[i].reduceSpellDelay[c.reduceSpellId] = System.currentTimeMillis();
						o.getPA().refreshSkill(2);
						break;

					case 1562:
						World.players[i].playerLevel[0] -= ((o.getPA().getLevelForXP(World.players[i].playerXP[0]) * 10) / 100);
						o.sendMessage("Your attack level has been reduced!");
						World.players[i].reduceSpellDelay[c.reduceSpellId] = System.currentTimeMillis();
						o.getPA().refreshSkill(0);
						break;
					}
				}

				World.players[i].logoutDelay = System.currentTimeMillis();
				World.players[i].underAttackBy = c.playerId;
				World.players[i].killerId = c.playerId;
				World.players[i].singleCombatDelay = System.currentTimeMillis();
				if (c.getCombat().magicMaxHit() != 0) {
					World.players[i].dealDamage(damage);
					World.players[i].damageTaken[c.playerId] += damage;
					c.totalPlayerDamageDealt += damage;
					if (!c.magicFailed) {
						World.players[i].handleHitMask(damage);
					}
				}
				c.getCombat().applySmite(i, damage);
				if (c.soulSplitDelay <= 1) {
					c.getCombat().applySoulSplit(i, damage);
				}
				c.killedBy = World.players[i].playerId;
				o.getPA().refreshSkill(3);
				World.players[i].updateRequired = true;
				c.usingMagic = false;
				c.castingMagic = false;
				if (o.inMulti() && c.getCombat().multis()) {
					c.barrageCount = 0;
					for (int j = 0; j < World.players.length; j++) {
						if (World.players[j] != null) {
							if (j == o.playerId)
								continue;
							if (c.barrageCount >= 9)
								break;
							if (o.goodDistance(o.getX(), o.getY(), World.players[j].getX(), World.players[j].getY(), 1))
								c.getCombat().appendMultiBarrage(j, c.magicFailed);
						}
					}
				}
				c.getPA().refreshSkill(3);
				c.getPA().refreshSkill(6);
				c.oldSpellId = 0;
			}
		}
		c.getPA().requestUpdates();
		if (c.bowSpecShot <= 0) {
			c.oldPlayerIndex = 0;
			c.projectileStage = 0;
			c.lastWeaponUsed = 0;
			c.doubleHit = false;
			c.bowSpecShot = 0;
		}
		if (c.bowSpecShot != 0) {
			c.bowSpecShot = 0;
		}
	}

	public static void attackPlayer(Client c, int i) {
		if (World.players[i] != null) {
			if (World.players[i].isDead) {
				c.getCombat().resetPlayerAttack();
				return;
			}
			if (c.respawnTimer > 0 || World.players[i].respawnTimer > 0) {
				c.getCombat().resetPlayerAttack();
				return;
			}
			if (!c.getCombat().checkReqs()) {
				return;
			}
			boolean sameSpot = c.absX == World.players[i].getX() && c.absY == World.players[i].getY();
			if (!c.goodDistance(World.players[i].getX(), World.players[i].getY(), c.getX(), c.getY(), 25) && !sameSpot) {
				c.getCombat().resetPlayerAttack();
				return;
			}
			if (World.players[i].respawnTimer > 0) {
				World.players[i].playerIndex = 0;
				c.getCombat().resetPlayerAttack();
				return;
			}

			if (World.players[i].heightLevel != c.heightLevel) {
				c.getCombat().resetPlayerAttack();
				return;
			}
			c.followId = i;
			c.followId2 = 0;
			if (c.attackTimer <= 0) {
				c.usingBow = false;
				c.specEffect = 0;
				c.usingRangeWeapon = false;
				c.rangeItemUsed = 0;
				c.usingBow = false;
				c.usingArrows = false;
				c.usingOtherRangeWeapons = false;
				c.usingCross = c.playerEquipment[c.playerWeapon] == 9185 || c.playerEquipment[c.playerWeapon] == 18357;
				c.projectileStage = 0;

				if (c.absX == World.players[i].absX && c.absY == World.players[i].absY) {
					if (c.freezeTimer > 0) {
						c.getCombat().resetPlayerAttack();
						return;
					}
					c.followId = i;
					c.attackTimer = 0;
					return;
				}
				// c.sendMessage("Made it here1.");
				if (!c.usingMagic) {
					for (int bowId : c.BOWS) {
						if (c.playerEquipment[c.playerWeapon] == bowId) {
							c.usingBow = true;
							for (int arrowId : c.ARROWS) {
								if (c.playerEquipment[c.playerArrows] == arrowId) {
									c.usingArrows = true;
								}
							}
						}
					}

					for (int otherRangeId : c.OTHER_RANGE_WEAPONS) {
						if (c.playerEquipment[c.playerWeapon] == otherRangeId) {
							c.usingOtherRangeWeapons = true;
						}
					}
				}
				if (c.autocasting) {
					c.spellId = c.autocastId;
					c.usingMagic = true;
				}
				if (c.spellId > 0) {
					c.usingMagic = true;
				}
				c.attackTimer = c.getCombat().getAttackDelay(c.getItems().getItemName(c.playerEquipment[c.playerWeapon]).toLowerCase());

				if (c.duelRule[9]) {
					boolean canUseWeapon = false;
					for (int funWeapon : MistexConfiguration.FUN_WEAPONS) {
						if (c.playerEquipment[c.playerWeapon] == funWeapon) {
							canUseWeapon = true;
						}
					}
					if (!canUseWeapon) {
						c.sendMessage("You can only use fun weapons in this duel!");
						c.getCombat().resetPlayerAttack();
						return;
					}
				}
				if (c.duelRule[2] && (c.usingBow || c.usingOtherRangeWeapons)) {
					c.sendMessage("Range has been disabled in this duel!");
					c.getCombat().resetPlayerAttack();
					return;
				}
				if (c.duelRule[3] && (!c.usingBow && !c.usingOtherRangeWeapons && !c.usingMagic)) {
					c.sendMessage("Melee has been disabled in this duel!");
					c.getCombat().resetPlayerAttack();
					return;
				}

				if (c.duelRule[4] && c.usingMagic) {
					c.sendMessage("Magic has been disabled in this duel!");
					c.getCombat().resetPlayerAttack();
					return;
				}

				if ((!c.goodDistance(c.getX(), c.getY(), World.players[i].getX(), World.players[i].getY(), 4) && (c.usingOtherRangeWeapons && !c.usingBow && !c.usingMagic))
						|| (!c.goodDistance(c.getX(), c.getY(), World.players[i].getX(), World.players[i].getY(), 2) && (!c.usingOtherRangeWeapons && c.getCombat().usingHally() && !c.usingBow && !c.usingMagic))
						|| (!c.goodDistance(c.getX(), c.getY(), World.players[i].getX(), World.players[i].getY(), c.getCombat().getRequiredDistance()) && (!c.usingOtherRangeWeapons && !c.getCombat().usingHally() && !c.usingBow && !c.usingMagic))
						|| (!c.goodDistance(c.getX(), c.getY(), World.players[i].getX(), World.players[i].getY(), 10) && (c.usingBow || c.usingMagic))) {
					c.attackTimer = 1;
					if (!c.usingBow && !c.usingMagic && !c.usingOtherRangeWeapons && c.freezeTimer > 0)
						c.getCombat().resetPlayerAttack();
					return;
				}

				if (!c.usingCross && !c.usingArrows && c.usingBow && (c.playerEquipment[c.playerWeapon] < 4212 || c.playerEquipment[c.playerWeapon] > 4223) && !c.usingMagic) {
					c.sendMessage("You have run out of arrows!");
					c.stopMovement();
					c.getCombat().resetPlayerAttack();
					return;
				}
				if (c.getCombat().correctBowAndArrows() < c.playerEquipment[c.playerArrows] && PlayerConfiguration.CORRECT_ARROWS && c.usingBow && !c.getCombat().usingCrystalBow() && c.playerEquipment[c.playerWeapon] != 9185 && c.playerEquipment[c.playerWeapon] != 18357 && !c.usingMagic) {
					c.sendMessage("You can't use " + c.getItems().getItemName(c.playerEquipment[c.playerArrows]).toLowerCase() + "s with a " + c.getItems().getItemName(c.playerEquipment[c.playerWeapon]).toLowerCase() + ".");
					c.stopMovement();
					c.getCombat().resetPlayerAttack();
					return;
				}
				if (c.playerEquipment[c.playerWeapon] == 9185 && !c.getCombat().properBolts() && !c.usingMagic) {
					c.sendMessage("You must use bolts with a crossbow.");
					c.stopMovement();
					c.getCombat().resetPlayerAttack();
					return;
				}

				if (c.usingBow || c.usingMagic || c.usingOtherRangeWeapons || c.getCombat().usingHally()) {
					c.stopMovement();
				}

				if (!c.getCombat().checkMagicReqs(c.spellId)) {
					c.stopMovement();
					c.getCombat().resetPlayerAttack();
					return;
				}

				c.faceUpdate(i + 32768);
				if (c.duelStatus != 5) {
					if (!c.attackedPlayers.contains(c.playerIndex) && !World.players[c.playerIndex].attackedPlayers.contains(c.playerId)) {
						c.attackedPlayers.add(c.playerIndex);
						c.isSkulled = true;
						c.skullTimer = PlayerConfiguration.SKULL_TIMER;
						c.headIconPk = 0;
						c.getPA().requestUpdates();
					}
				}

				c.specAccuracy = 1.0;
				c.specDamage = 1.0;
				c.delayedDamage = c.delayedDamage2 = 0;
				if (c.usingSpecial && !c.usingMagic) {
					if (c.duelRule[10] && c.duelStatus == 5) {
						c.sendMessage("Special attacks have been disabled during this duel!");
						c.usingSpecial = false;
						c.getItems().updateSpecialBar();
						c.getCombat().resetPlayerAttack();
						return;
					}
					if (c.getCombat().checkSpecAmount(c.playerEquipment[c.playerWeapon])) {
						c.lastArrowUsed = c.playerEquipment[c.playerArrows];
						c.getCombat().activateSpecial(c.playerEquipment[c.playerWeapon], i);
						c.followId = c.playerIndex;
						return;
					} else {
						c.sendMessage("You don't have the required special energy to use this attack.");
						c.usingSpecial = false;
						c.getItems().updateSpecialBar();
						c.playerIndex = 0;
						return;
					}
				}
				if (c.playerLevel[3] > 0 && !c.isDead && World.players[i].playerLevel[3] > 0) {
					if (!c.usingMagic) {
						c.startAnimation(c.getCombat().getWepAnim(c.getItems().getItemName(c.playerEquipment[c.playerWeapon]).toLowerCase()));
						c.mageFollow = false;
					} else {
						c.startAnimation(c.MAGIC_SPELLS[c.spellId][2]);
						c.mageFollow = true;
						c.followId = c.playerIndex;
					}
				}
				World.players[i].underAttackBy = c.playerId;
				World.players[i].logoutDelay = System.currentTimeMillis();
				World.players[i].singleCombatDelay = System.currentTimeMillis();
				World.players[i].killerId = c.playerId;
				c.lastArrowUsed = 0;
				c.rangeItemUsed = 0;
				if (!c.usingBow && !c.usingMagic && !c.usingOtherRangeWeapons) { // melee
																					// hit
																					// delay
					c.followId = World.players[c.playerIndex].playerId;
					c.getPA().followPlayer();
					c.hitDelay = c.getCombat().getHitDelay(i, c.getItems().getItemName(c.playerEquipment[c.playerWeapon]).toLowerCase());
					c.delayedDamage = MistexUtility.random(c.getCombat().calculateMeleeMaxHit());
					c.projectileStage = 0;
					c.oldPlayerIndex = i;
				}

				if (c.usingBow && !c.usingOtherRangeWeapons && !c.usingMagic || c.usingCross) { // range
																								// hit
																								// delay
					if (c.playerEquipment[c.playerWeapon] >= 4212 && c.playerEquipment[c.playerWeapon] <= 4223) {
						c.rangeItemUsed = c.playerEquipment[c.playerWeapon];
						c.crystalBowArrowCount++;
					} else {
						c.rangeItemUsed = c.playerEquipment[c.playerArrows];
						c.getItems().deleteArrow();
					}
					if (c.fightMode == 2)
						c.attackTimer--;
					if (c.usingCross)
						c.usingBow = true;
					c.usingBow = true;
					c.followId = World.players[c.playerIndex].playerId;
					c.getPA().followPlayer();
					c.lastWeaponUsed = c.playerEquipment[c.playerWeapon];
					c.lastArrowUsed = c.playerEquipment[c.playerArrows];
					c.gfx100(c.getCombat().getRangeStartGFX());
					c.hitDelay = c.getCombat().getHitDelay(i, c.getItems().getItemName(c.playerEquipment[c.playerWeapon]).toLowerCase());
					c.projectileStage = 1;
					c.oldPlayerIndex = i;
					c.getCombat().fireProjectilePlayer();
				}

				if (c.usingOtherRangeWeapons) { // knives, darts, etc hit delay
					c.rangeItemUsed = c.playerEquipment[c.playerWeapon];
					c.getItems().deleteEquipment();
					c.usingRangeWeapon = true;
					c.followId = World.players[c.playerIndex].playerId;
					c.getPA().followPlayer();
					c.gfx100(c.getCombat().getRangeStartGFX());
					if (c.fightMode == 2)
						c.attackTimer--;
					c.hitDelay = c.getCombat().getHitDelay(i, c.getItems().getItemName(c.playerEquipment[c.playerWeapon]).toLowerCase());
					c.projectileStage = 1;
					c.oldPlayerIndex = i;
					c.getCombat().fireProjectilePlayer();
				}

				if (c.usingMagic) { // magic hit delay
					int pX = c.getX();
					int pY = c.getY();
					int nX = World.players[i].getX();
					int nY = World.players[i].getY();
					int offX = (pY - nY) * -1;
					int offY = (pX - nX) * -1;
					c.castingMagic = true;
					c.projectileStage = 2;
					if (c.MAGIC_SPELLS[c.spellId][3] > 0) {
						if (c.getCombat().getStartGfxHeight() == 100) {
							c.gfx100(c.MAGIC_SPELLS[c.spellId][3]);
						} else {
							c.gfx0(c.MAGIC_SPELLS[c.spellId][3]);
						}
					}
					if (c.MAGIC_SPELLS[c.spellId][4] > 0) {
						c.getPA().createPlayersProjectile(pX, pY, offX, offY, 50, 78, c.MAGIC_SPELLS[c.spellId][4], c.getCombat().getStartHeight(), c.getCombat().getEndHeight(), -i - 1, c.getCombat().getStartDelay());
					}
					if (c.autocastId > 0) {
						c.followId = c.playerIndex;
						c.followDistance = 5;
					}
					c.hitDelay = c.getCombat().getHitDelay(i, c.getItems().getItemName(c.playerEquipment[c.playerWeapon]).toLowerCase());
					c.oldPlayerIndex = i;
					c.oldSpellId = c.spellId;
					c.spellId = 0;
					Client o = (Client) World.players[i];
					if (c.MAGIC_SPELLS[c.oldSpellId][0] == 12891 && o.isMoving) {
						// c.sendMessage("Barrage projectile..");
						c.getPA().createPlayersProjectile(pX, pY, offX, offY, 50, 85, 368, 25, 25, -i - 1, c.getCombat().getStartDelay());
					}
					if (MistexUtility.random(o.getCombat().mageDef()) > MistexUtility.random(c.getCombat().mageAtk())) {
						// if(MistexUtility.random(o.getCombat().mageAtk()) >
						// MistexUtility.random(o.getCombat().mageDef())) {
						c.magicFailed = true;
					} else {
						c.magicFailed = false;
					}

					// if(MistexUtility.random(o.getCombat().mageAtk()) >
					// MistexUtility.random(o.getCombat().mageDef())) {
					// c.magicFailed = false;
					// } else if(MistexUtility.random(o.getCombat().mageAtk()) <
					// MistexUtility.random(o.getCombat().mageDef())) {
					// c.magicFailed = true;
					// }

					int freezeDelay = c.getCombat().getFreezeTime();// freeze
																	// time
					if (freezeDelay > 0 && World.players[i].freezeTimer <= -3 && !c.magicFailed) {
						World.players[i].freezeTimer = freezeDelay;
						o.resetWalkingQueue();
						o.sendMessage("You have been frozen.");
						o.frozenBy = c.playerId;
					}
					if (!c.autocasting && c.spellId <= 0)
						c.playerIndex = 0;
				}
				c.getCombat().applyLeeches(i);
				if (c.soulSplitDelay <= 1) {
					c.getCombat().applySoulSplit(i, 1);
				}

				if (c.usingBow && MistexConfiguration.CRYSTAL_BOW_DEGRADES) {
					if (c.playerEquipment[c.playerWeapon] == 4212) {
						c.getItems().wearItem(4214, 1, 3);
					}
					if (c.crystalBowArrowCount >= 250) {
						switch (c.playerEquipment[c.playerWeapon]) {

						case 4223: // 1/10 bow
							c.getItems().wearItem(-1, 1, 3);
							c.sendMessage("Your crystal bow has fully degraded.");
							if (!c.getItems().addItem(4207, 1)) {
								Mistex.itemHandler.createGroundItem(c, 4207, c.getX(), c.getY(), 1, c.getId());
							}
							c.crystalBowArrowCount = 0;
							break;

						default:
							c.getItems().wearItem(++c.playerEquipment[c.playerWeapon], 1, 3);
							c.sendMessage("Your crystal bow degrades.");
							c.crystalBowArrowCount = 0;
							break;
						}
					}
				}
			}
		}
	}
}