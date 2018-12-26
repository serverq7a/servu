package org.mistex.game.world.content.minigame.plunder;

import org.mistex.game.world.event.CycleEvent;
import org.mistex.game.world.event.CycleEventContainer;
import org.mistex.game.world.player.Client;

public class DoorEvent extends CycleEvent {
	
	private Client c;
	@SuppressWarnings("unused")
	private int id, obX, obY;
	
	public DoorEvent(Client c, int id, int obX, int obY) {
		this.c = c;
		this.id = 0;
		this.obX = obX;
		this.obY = obY;
	}

	@Override
	public void execute(CycleEventContainer container) {
//		container.stop();
		c.getPA().sendConfig(820, id++);
		c.sendMessage(id + "");
	}

	@Override
	public void stop() {
		
		c.getPA().addSkillXP(PlunderData.EXP_MULTIPLIR * PlunderData.DOOR_EXP.getData()[c.plunderFloor & 7], 17);
//		ObjectHandler.createAnObject(c, PlunderData.GRAND_GOLD_CHEST.getData()[0], obX - 1, obY);
	}
}