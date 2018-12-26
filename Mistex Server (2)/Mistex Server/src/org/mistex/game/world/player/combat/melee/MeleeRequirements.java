package org.mistex.game.world.player.combat.melee;

import org.mistex.game.Mistex;
import org.mistex.game.MistexConfiguration;
import org.mistex.game.world.World;
import org.mistex.game.world.player.Client;
import org.mistex.game.world.player.PlayerConfiguration;

@SuppressWarnings("static-access")
public class MeleeRequirements {

	public static int getKillerId(Client c, int playerId) {
		int oldDamage = 0;
		int killerId = 0;
		for (int i = 1; i < MistexConfiguration.MAX_PLAYERS; i++) {
			if (World.players[i] != null) {
				if (World.players[i].killedBy == playerId) {
					if (World.players[i].withinDistance(World.players[playerId])) {
						if (World.players[i].totalPlayerDamageDealt > oldDamage) {
							oldDamage = World.players[i].totalPlayerDamageDealt;
							killerId = i;
						}
					}
					World.players[i].totalPlayerDamageDealt = 0;
					World.players[i].killedBy = 0;
				}
			}
		}
		return killerId;
	}

	public static int getCombatDifference(int combat1, int combat2) {
		if (combat1 > combat2) {
			return (combat1 - combat2);
		}
		if (combat2 > combat1) {
			return (combat2 - combat1);
		}
		return 0;
	}

	/*
	 * public static boolean checkReqs(Client c) {
	 * if(World.players[c.playerIndex] == null) { return false; } if
	 * (c.playerIndex == c.playerId) return false; Client opponent = (Client)
	 * World.players[c.playerIndex]; if(!World.players[c.playerIndex].inWild()
	 * && !CastOnOther.castOnOtherSpells(c)) { c.sendMessage(
	 * "Your opponent is not in the wilderness!"); c.stopMovement();
	 * c.getCombat().resetPlayerAttack(); return false; } if(!c.inWild() &&
	 * !CastOnOther.castOnOtherSpells(c)) { c.sendMessage(
	 * "You are not in the wilderness."); c.stopMovement();
	 * c.getCombat().resetPlayerAttack(); return false; }
	 * if(Config.COMBAT_LEVEL_DIFFERENCE) { if(c.inWild()) { int combatDif1 =
	 * getCombatDifference(c.combatLevel,
	 * World.players[c.playerIndex].combatLevel); if((combatDif1 > c.wildLevel
	 * || combatDif1 > World.players[c.playerIndex].wildLevel)) { c.sendMessage(
	 * "Your combat level difference is too great to attack that player here.");
	 * c.stopMovement(); c.getCombat().resetPlayerAttack(); return false; } }
	 * else { int myCB = c.combatLevel; int pCB =
	 * World.players[c.playerIndex].combatLevel; if((myCB > pCB + 12) || (myCB <
	 * pCB - 12)) { c.sendMessage(
	 * "You can only fight players in your combat range!"); c.stopMovement();
	 * c.getCombat().resetPlayerAttack(); return false; } } }
	 * if(Config.SINGLE_AND_MULTI_ZONES) {
	 * if(!World.players[c.playerIndex].inMulti()) { // single combat zones
	 * if(World.players[c.playerIndex].underAttackBy != c.playerId &&
	 * World.players[c.playerIndex].underAttackBy != 0) { c.sendMessage(
	 * "That player is already in combat."); c.stopMovement();
	 * c.getCombat().resetPlayerAttack(); return false; }
	 * if(World.players[c.playerIndex].playerId != c.underAttackBy &&
	 * c.underAttackBy != 0 || c.underAttackBy2 > 0) { c.sendMessage(
	 * "You are already in combat."); c.stopMovement();
	 * c.getCombat().resetPlayerAttack(); return false; } } } return true; }
	 */
	public static boolean checkReqs(Client c) {
		if (Mistex.playerHandler.players[c.playerIndex] == null) {
			return false;
		}
		if (c.playerIndex == c.playerId)
			return false;
		if (c.inPits && Mistex.playerHandler.players[c.playerIndex].inPits)
			return true;
		if (Mistex.playerHandler.players[c.playerIndex].inDuelArena() && c.duelStatus != 5 && !c.usingMagic) {
			if (c.arenas() || c.duelStatus == 5) {
				c.sendMessage("You can't challenge inside the arena!");
				return false;
			}
			c.getDuel().requestDuel(c.playerIndex);
			return false;
		}
		if (c.duelStatus == 5 && Mistex.playerHandler.players[c.playerIndex].duelStatus == 5) {
			if (Mistex.playerHandler.players[c.playerIndex].duelingWith == c.getId()) {
				return true;
			} else {
				c.sendMessage("This isn't your opponent!");
				return false;
			}
		}
		if (!Mistex.playerHandler.players[c.playerIndex].inFunPk() && !Mistex.playerHandler.players[c.playerIndex].inWild() && !Mistex.playerHandler.players[c.playerIndex].inWeaponGame()) {
			if (!c.canBanClick) {
				c.sendMessage("That person is not in the wilderness.");
				c.stopMovement();
				c.getCombat().resetPlayerAttack();
			} else if (c.canBanClick && !c.usingMagic) {
				c.getDH().sendDialogues(10000, -1);
				c.stopMovement();
			} else {
				c.stopMovement();
				c.getCombat().resetPlayerAttack();
			}
			return false;
		}
		if (!Mistex.playerHandler.players[c.playerIndex].inFunPk() && !c.inWild() && !Mistex.playerHandler.players[c.playerIndex].inWeaponGame()) {
			c.sendMessage("You are not in the wilderness.");
			c.stopMovement();
			c.getCombat().resetPlayerAttack();
			return false;
		}
		if (PlayerConfiguration.COMBAT_LEVEL_DIFFERENCE && !Mistex.playerHandler.players[c.playerIndex].inFunPk() && !Mistex.playerHandler.players[c.playerIndex].inWeaponGame()) {
			if (c.inWild()) {
				int combatDif1 = getCombatDifference(c.combatLevel, World.players[c.playerIndex].combatLevel);
				if ((combatDif1 > c.wildLevel || combatDif1 > World.players[c.playerIndex].wildLevel)) {
					c.sendMessage("Your combat level difference is too great to attack that player here.");
					c.stopMovement();
					c.getCombat().resetPlayerAttack();
					return false;
				}
			} else {
				int myCB = c.combatLevel;
				int pCB = World.players[c.playerIndex].combatLevel;
				if ((myCB > pCB + 12) || (myCB < pCB - 12)) {
					c.sendMessage("You can only fight players in your combat range!");
					c.stopMovement();
					c.getCombat().resetPlayerAttack();
					return false;
				}
			}
		}

		if (PlayerConfiguration.SINGLE_AND_MULTI_ZONES && !Mistex.playerHandler.players[c.playerIndex].inFunPk() && !Mistex.playerHandler.players[c.playerIndex].inWeaponGame()) {
			if (!Mistex.playerHandler.players[c.playerIndex].inMulti()) { // single
																			// combat
																			// zones
				if (Mistex.playerHandler.players[c.playerIndex].underAttackBy != c.playerId && Mistex.playerHandler.players[c.playerIndex].underAttackBy != 0) {
					c.sendMessage("That player is already in combat.");
					c.stopMovement();
					c.getCombat().resetPlayerAttack();
					return false;
				}
				if (Mistex.playerHandler.players[c.playerIndex].playerId != c.underAttackBy && c.underAttackBy != 0 || c.underAttackBy2 > 0) {
					c.sendMessage("You are already in combat.");
					c.stopMovement();
					c.getCombat().resetPlayerAttack();
					return false;
				}
			}
		}
		return true;
	}

	public static int getRequiredDistance(Client c) {
		if (c.followId > 0 && c.freezeTimer <= 0 && !c.isMoving)
			return 2;
		else if (c.followId > 0 && c.freezeTimer <= 0 && c.isMoving) {
			return 3;
		} else {
			return 1;
		}
	}
}