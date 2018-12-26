package org.mistex.game.world.content.events;

import org.mistex.game.world.event.CycleEvent;
import org.mistex.game.world.event.CycleEventContainer;
import org.mistex.game.world.player.Client;

public class RestoreStatsEvent extends CycleEvent {
	
	private Client c;
	
	public RestoreStatsEvent(Client c) {
		this.c = c;
	}
	
	@Override
	public void execute(CycleEventContainer container) {
		container.stop();
	}

	@Override
	public void stop() {
        for (int level = 0; level < c.playerLevel.length; level++) {
            if (c.playerLevel[level] < c.getLevelForXP(c.playerXP[level])) {
                if (level != 5) { // prayer doesn't restore
                    c.playerLevel[level] += 1;
                    c.getPA().setSkillLevel(level, c.playerLevel[level], c.playerXP[level]);
                    c.getPA().refreshSkill(level);
                }
            } else if (c.playerLevel[level] > c.getLevelForXP(c.playerXP[level])) {
                c.playerLevel[level] -= 1;
                c.getPA().setSkillLevel(level, c.playerLevel[level], c.playerXP[level]);
                c.getPA().refreshSkill(level);
            }
        }
	}
}