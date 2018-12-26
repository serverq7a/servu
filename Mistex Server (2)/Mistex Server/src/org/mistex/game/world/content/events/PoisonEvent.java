package org.mistex.game.world.content.events;

import org.mistex.game.world.event.CycleEvent;
import org.mistex.game.world.event.CycleEventContainer;
import org.mistex.game.world.player.Client;

public class PoisonEvent extends CycleEvent {
	
	private Client c;
	
	public PoisonEvent(Client c) {
		this.c = c;
	}

	@Override
	public void execute(CycleEventContainer container) {
		if (c.poisonDamage > 0) {
			int damage = c.poisonDamage / 2;
	        if (damage > 0) {
	            c.sendMessage("The poison damages you.");
	            if (!c.getHitUpdateRequired()) {
	                c.setHitUpdateRequired(true);
	                c.setHitDiff(damage);
	                c.updateRequired = true;
	                c.poisonMask = 1;
	            } else if (!c.getHitUpdateRequired2()) {
	                c.setHitUpdateRequired2(true);
	                c.setHitDiff2(damage);
	                c.updateRequired = true;
	                c.poisonMask = 2;
	            }
	            c.poisonDamage--;
	            c.dealDamage(damage);
	        } else {
	            c.poisonDamage = -1;
	            c.sendMessage("You are no longer poisoned.");
	        }
		}
	}

	@Override
	public void stop() {
	}
}