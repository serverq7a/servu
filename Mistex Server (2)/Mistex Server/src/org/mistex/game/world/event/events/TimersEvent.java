package org.mistex.game.world.event.events;

import org.mistex.game.world.event.CycleEvent;
import org.mistex.game.world.event.CycleEventContainer;
import org.mistex.game.world.player.Client;

public class TimersEvent extends CycleEvent {
	
	private final Client player;

	public TimersEvent(final Client player) {
		this.player = player;
	}

	@Override
	public void execute(CycleEventContainer container) {
		/**
		 * Handles special attack timer for summoning
		 */
		if (player.specTimer > 0) {
			player.specTimer--;
			player.getPA().sendFrame126(""+player.specTimer, 18024);
		}
	
	}

	@Override
	public void stop() {}

}
