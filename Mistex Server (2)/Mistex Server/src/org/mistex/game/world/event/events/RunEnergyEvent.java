package org.mistex.game.world.event.events;

import org.mistex.game.world.event.CycleEvent;
import org.mistex.game.world.event.CycleEventContainer;
import org.mistex.game.world.player.Client;

public class RunEnergyEvent extends CycleEvent {
	
	private final Client player;

	public RunEnergyEvent(final Client player) {
		this.player = player;
	}

	@Override
	public void execute(CycleEventContainer container) {
		if (player.runEnergy < 100) {
			if (System.currentTimeMillis() > player.getPA().getAgilityRunRestore() + player.lastRunRecovery) {
				player.runEnergy++;
				player.lastRunRecovery = System.currentTimeMillis();
				player.getPA().sendFrame126(player.runEnergy+"%", 149);
			}
		}
		
	}

	@Override
	public void stop() {}

}
