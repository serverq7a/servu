package org.mistex.game.world.event.events;

import org.mistex.game.world.content.minigame.fightpits.FightPits;
import org.mistex.game.world.event.CycleEvent;
import org.mistex.game.world.event.CycleEventContainer;

public class FightPitsEvent extends CycleEvent {

	@Override
	public void execute(CycleEventContainer container) {
		FightPits.update();
		if (!FightPits.gameStarted) {
			if (FightPits.gameStartTimer > 0) {
				FightPits.gameStartTimer--;
			} else if (FightPits.gameStartTimer == 0) {
				if (FightPits.getListCount(FightPits.PLAYING) > 0) {

				} else {
					if (FightPits.getListCount(FightPits.WAITING) > 1) {
						FightPits.beginGame();
					} else {
						FightPits.cantStart();
					}
				}
				FightPits.gameStartTimer = 180;
			}
		}
	}

	@Override
	public void stop() {}

}
