package org.mistex.game.world.content.events;

import org.mistex.game.world.content.skill.SkillHandler;
import org.mistex.game.world.event.CycleEvent;
import org.mistex.game.world.event.CycleEventContainer;
import org.mistex.game.world.player.Client;

public class TeleTimer extends CycleEvent {
	
	private Client c;
	
	public TeleTimer(Client c) {
		this.c = c;
	}

	@Override
	public void execute(CycleEventContainer container) {
		if (c.teleTimer > 0) {
            c.teleTimer--;
            if (!c.isDead) {
                if (c.teleTimer == 1 && c.newLocation > 0) {
                    c.teleTimer = 0;
                    c.getPA().changeLocation();
                }
                if (c.teleTimer == 5) {
                    c.teleTimer--;
                    c.getPA().processTeleport();
                }
                if (c.teleTimer == 9 && c.teleGfx > 0) {
                    c.teleTimer--;
                    c.gfx100(c.teleGfx);
                    SkillHandler.resetPlayerVariables(c);
                }
            } else {
                c.teleTimer = 0;
            }
        }
	}

	@Override
	public void stop() {
	}
}