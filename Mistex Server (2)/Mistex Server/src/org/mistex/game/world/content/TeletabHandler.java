package org.mistex.game.world.content;

import java.util.HashMap;

import org.mistex.game.Mistex;
import org.mistex.game.world.content.minigame.fightpits.FightPits;
import org.mistex.game.world.content.minigame.weapongame.WeaponGame;
import org.mistex.game.world.player.Client;
import org.mistex.game.world.player.PlayerConfiguration;
import org.mistex.game.world.task.Task;

public class TeletabHandler {

	final static int ANIMATION = 4731, GRAPHIC = 1680;

	enum Teletab {

		VARROCK(8007, 3210, 3424, 0),
		LUMBRIDGE(8008, 3222, 3218, 0),
		FALADOR(8009, 2945, 3371, 0),
		CAMELOT(8010, 2726, 3492, 0),
		ARDOUGNE(8011, 2661, 3307, 0),
		WATCHTOWER(8012, 2702, 3399, 0),
		HOUSE(8013, 3086, 3490, 0);

		private Teletab(int itemId, int posX, int posY, int height) {
			this.itemId = itemId;
			this.posX = posX;
			this.posY = posY;
			this.height = height;
		}

		public int itemId, posX, posY, height;

		public static HashMap<Integer, Teletab> tabs = new HashMap<Integer, Teletab>();

		static {
			for (Teletab i : Teletab.values())
				Teletab.tabs.put(i.itemId, i);
		}
	}
	
	public static void handleItem(Client c, int itemId) {
		for (Teletab tab : Teletab.values()) {
			if (itemId == tab.itemId)
				breakTab(c, tab);
		}
	}

	public static boolean canTeleport(Client c) {
		if (!c.inFunPk() && c.inWild() && c.wildLevel > PlayerConfiguration.NO_TELEPORT_WILD_LEVEL) {
			c.sendMessage("You can't teleport above level " + PlayerConfiguration.NO_TELEPORT_WILD_LEVEL + " in the wilderness.");
			return false;
		}
		if (c.inBoxingArena) {
			c.sendMessage("You may not teleport while in the boxing arena");
			return false;
		}
		if (c.isDoingTutorial) {
			c.sendMessage("Please talk to the guide first.");
			return false;
		}
		if (c.doingDungeoneering) {
			c.sendMessage("<col=8650ac>You can not teleport out of dungeoneering!");
			return false;
		}
		if (c.cantTeleport) {
			return false;
		}
		if (System.currentTimeMillis() - c.teleBlockDelay < c.teleBlockLength) {
			c.sendMessage("You are teleblocked and can't teleport.");
			return false;
		}
		if (c.duelStatus == 5) {
			c.sendMessage("You can't teleport during a duel!");
			return false;
		}
		if (c.inWeaponGame()) {
			c.sendMessage("You can not teleport while in this minigame!");
			return false;
		}
		if (c.inWeaponLobby()) {
			WeaponGame.removePlayer(c, true);
		}
		if (c.inFightPits()) {
			FightPits.removePlayer(c, true);
		}
		return true;
	}
	
	private static void breakTab(final Client player, final Teletab teletab) {
		if (!canTeleport(player)) {
			return;
		}
		player.isTeleporting = true;
		player.startAnimation(ANIMATION);
		player.getItems().deleteItem(teletab.itemId, 1);
		
		Mistex.getTaskScheduler().schedule(new Task(4) {
			@Override
			protected void execute() {
				player.isTeleporting = false;
				player.getPA().movePlayer(teletab.posX, teletab.posY, teletab.height);
				stop();
			}
		});
	}
}