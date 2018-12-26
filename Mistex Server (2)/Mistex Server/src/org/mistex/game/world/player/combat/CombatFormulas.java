package org.mistex.game.world.player.combat;

import org.mistex.game.MistexUtility;
import org.mistex.game.world.World;
import org.mistex.game.world.player.Client;
import org.mistex.game.world.player.PlayerConfiguration;

public class CombatFormulas {
	
	public final static boolean isInDiagonalBlock(Client attacked, Client attacker) {
		return attacked.absX - 1 == attacker.absX
				&& attacked.absY + 1 == attacker.absY
				|| attacker.absX - 1 == attacked.absX
				&& attacker.absY + 1 == attacked.absY
				|| attacked.absX + 1 == attacker.absX
				&& attacked.absY - 1 == attacker.absY
				|| attacker.absX + 1 == attacked.absX
				&& attacker.absY - 1 == attacked.absY
				|| attacked.absX + 1 == attacker.absX
				&& attacked.absY + 1 == attacker.absY
				|| attacker.absX + 1 == attacked.absX
				&& attacker.absY + 1 == attacked.absY;
	}
	
	public static void stopDiagonalAttack(Client attacked, Client attacker) {
		if (attacker.freezeTimer > 0) {
			attacker.sendMessage("A magical force stops you from moving.");
			attacker.getCombat().resetPlayerAttack();
		} else {
			int xMove = attacked.getX() - attacker.getX();
			int yMove = 0;
			if (xMove == 0)
				yMove = attacked.getY() - attacker.getY();
			int k = attacker.getX() + xMove;
			k -= attacker.mapRegionX * 8;
			attacker.getNewWalkCmdX()[0] = attacker.getNewWalkCmdY()[0] = 0;
			int l = attacker.getY() + yMove;
			l -= attacker.mapRegionY * 8;
			for (int n = 0; n < attacker.newWalkCmdSteps; n++) {
				attacker.getNewWalkCmdX()[n] += k;
				attacker.getNewWalkCmdY()[n] += l;
			}
		}
	}
	

	@SuppressWarnings("static-access")
	public static void korasiSpecial(Client c, int damage) {
                Client o = (Client) World.players[c.playerIndex];
                int i = c.playerIndex;
                if (damage > 70)
                    damage = MistexUtility.random(50) + 20;
                if (o.prayerActive[16]) {
                        damage /= 2;
                }
                if (o.playerLevel[o.playerHitpoints] - damage < 0) {
                        damage = o.playerLevel[o.playerHitpoints];
                }
                if (o.vengOn && damage > 0)
                c.getCombat().appendVengeance(i, damage);
                c.getPA().addSkillXP((damage * PlayerConfiguration.MAGIC_EXP_RATE), 6);
                c.getPA().addSkillXP((damage * PlayerConfiguration.MAGIC_EXP_RATE / 3), 3);
                c.getPA().refreshSkill(3);
                c.getPA().refreshSkill(6);
                o.handleHitMask(damage);
                o.dealDamage(damage);
                o.damageTaken[c.playerId] += damage;
                c.totalPlayerDamageDealt += damage;
                o.updateRequired = true;
                c.doubleHit = false;
                o.getPA().refreshSkill(3);
        }
}