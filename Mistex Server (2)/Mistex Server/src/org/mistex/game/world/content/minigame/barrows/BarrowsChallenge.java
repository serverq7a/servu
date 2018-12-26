package org.mistex.game.world.content.minigame.barrows;

import org.mistex.game.Mistex;
import org.mistex.game.MistexUtility;
import org.mistex.game.world.event.CycleEvent;
import org.mistex.game.world.event.CycleEventContainer;
import org.mistex.game.world.event.CycleEventHandler;
import org.mistex.game.world.player.Client;
import org.mistex.game.world.player.PlayerConfiguration;

@SuppressWarnings("static-access")
public class BarrowsChallenge {

	
	public BarrowsChallenge(Client c) {
		this.c = c;
	}
	
	private Client c;
	
	public boolean started;
	public boolean canGetReward;
	
	int rewardItems[] = {4708, 4710, 4712, 4714, 4716, 4718, 4720, 4722, 4724, 4726, 4728, 4730, 4732, 4734, 4736, 4738, 4745, 4747, 4749, 4751, 4753, 4755, 4757, 4759};
	int[][] barrowsCoords = {
			{2461, 4773}, {2462, 4773}, {2463, 4773}, {2464, 4773}, {2465, 4773}, {2466, 4773}};
	
	public void start() {
		c.getPA().movePlayer(2464, 4782, 0);
		c.sendMessage("The challenge will begin in 10 seconds. - Get ready!");
		CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {	
			public void execute(CycleEventContainer container) {
				Mistex.npcHandler.spawnNpc(c, 2025, barrowsCoords[0][0], barrowsCoords[0][1], 0, 0, 180, 35, 300, 300, true, true);
				Mistex.npcHandler.spawnNpc(c, 2026, barrowsCoords[1][0], barrowsCoords[1][1], 0, 0, 240, 45, 300, 300, true, true);
				Mistex.npcHandler.spawnNpc(c, 2027, barrowsCoords[2][0], barrowsCoords[2][1], 0, 0, 240, 45, 300, 300, true, true);
				Mistex.npcHandler.spawnNpc(c, 2028, barrowsCoords[3][0], barrowsCoords[3][1], 0, 0, 180, 30, 300, 300, true, true);
				Mistex.npcHandler.spawnNpc(c, 2029, barrowsCoords[4][0], barrowsCoords[4][1], 0, 0, 240, 40, 300, 300, true, true);
				Mistex.npcHandler.spawnNpc(c, 2030, barrowsCoords[5][0], barrowsCoords[5][1], 0, 0, 240, 45, 300, 300, true, true);
		container.stop();
			}
			@Override
			public void stop() {
				c.sendMessage("The challenge has started. Fight for your life!");
				started = true;
			}
	}, 15);
	}
	
	public void end() {
		started = false;
		canGetReward = true;
		c.barrowsChallengeKC = 0;
		c.getPA().movePlayer(PlayerConfiguration.START_LOCATION_X, PlayerConfiguration.START_LOCATION_Y, 0);
		c.sendMessage("Congratulations! You have completed the Barrows challenge minigame!");
		c.getDH().sendDialogues(28, 0);
		reward();
	}
	
	public void reward() {
		if (!canGetReward) {
			return;
		}
		c.getItems().addItem(barrowsReward(), 1);
		c.getItems().addItem(995, 100000 + MistexUtility.random(50000));
		canGetReward = false;
	}
	
	/**
	 * Getting random barrow peices.
	 * @return
	 */
	public int barrowsReward() {
		return rewardItems[(int)(Math.random()*rewardItems.length)];
	}

	
	
}
