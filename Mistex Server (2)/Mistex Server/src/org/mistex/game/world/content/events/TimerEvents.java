package org.mistex.game.world.content.events;

import org.mistex.game.world.World;
import org.mistex.game.world.event.CycleEvent;
import org.mistex.game.world.event.CycleEventContainer;
import org.mistex.game.world.player.Client;

public class TimerEvents extends CycleEvent {

	private Client c;

	public TimerEvents(Client c) {
		this.c = c;
	}

	@Override
	public void execute(CycleEventContainer container) {
		if (c.clawDelay > 0) {
            c.clawDelay--;
        }
        if (c.clawDelay == 1) {
            c.delayedDamage = (c.clawDamage / 4);
            c.delayedDamage2 = (c.clawDamage / 4) + 1;
            if (c.clawType == 2) {
            	c.getCombat().applyNpcMeleeDamage(c.clawIndex, 1, c.clawDamage / 4);
            }
            if (c.clawType == 1) {
            	c.getCombat().applyPlayerMeleeDamage(c.clawIndex, 1, c.clawDamage / 4);
            }
            if (c.clawType == 2) {
            	c.getCombat().applyNpcMeleeDamage(c.clawIndex, 2, (c.clawDamage / 4) + 1);
            }
            if (c.clawType == 1) {
            	c.getCombat().applyPlayerMeleeDamage(c.clawIndex, 2, (c.clawDamage / 4) + 1);
            }
            c.clawDelay = 0;
            c.specEffect = 0;
            c.previousDamage = 0;
            c.usingClaws = false;
            c.clawType = 0;
        }
        
		if (c.skullTimer > 0) {
			c.skullTimer--;
			if (c.skullTimer == 1) {
				c.isSkulled = false;
				c.attackedPlayers.clear();
				c.headIconPk = -1;
				c.skullTimer = -1;
				c.getPA().requestUpdates();
			}
		}

		if (c.isDead && c.respawnTimer == -6) {
			c.getPA().applyDead();
		}

		if (c.respawnTimer == 7) {
			c.respawnTimer = -6;
			c.getPA().giveLife();
		} else if (c.respawnTimer == 12) {
			c.respawnTimer--;
			c.startAnimation(7185);
			c.poisonDamage = -1;
		}

		if (c.respawnTimer > -6) {
			c.respawnTimer--;
		}

		if (c.freezeTimer > -6) {
			c.freezeTimer--;
			if (c.frozenBy > 0) {
				if (World.players[c.frozenBy] == null) {
					c.freezeTimer = -1;
					c.frozenBy = -1;
				} else if (!c.goodDistance(c.absX, c.absY, World.players[c.frozenBy].absX, World.players[c.frozenBy].absY, 20)) {
					c.freezeTimer = -1;
					c.frozenBy = -1;
				}
			}
		}

		if (c.hitDelay > 0) {
			c.hitDelay--;
		}

		if (c.hitDelay == 1) {
			if (c.oldNpcIndex > 0) {
				c.getCombat().delayedHit(c, c.oldNpcIndex);
			}
			if (c.oldPlayerIndex > 0) {
				c.getCombat().playerDelayedHit(c, c.oldPlayerIndex);
			}
		}

		if (c.attackTimer > 0) {
			c.attackTimer--;
		}

		if (c.attackTimer == 1) {
			if (c.npcIndex > 0 && c.clickNpcType == 0) {
				c.getCombat().attackNpc(c.npcIndex);
			}
			if (c.playerIndex > 0) {
				c.getCombat().attackPlayer(c.playerIndex);
			}
		} else if (c.attackTimer <= 0 && (c.npcIndex > 0 || c.playerIndex > 0)) {
			if (c.npcIndex > 0) {
				c.attackTimer = 0;
				c.getCombat().attackNpc(c.npcIndex);
			} else if (c.playerIndex > 0) {
				c.attackTimer = 0;
				c.getCombat().attackPlayer(c.playerIndex);
			}
		}
	}

	@Override
	public void stop() {
	}
}