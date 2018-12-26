package org.mistex.game.world.content;

import org.mistex.game.MistexUtility;
import org.mistex.game.world.event.CycleEvent;
import org.mistex.game.world.event.CycleEventContainer;
import org.mistex.game.world.event.CycleEventHandler;
import org.mistex.game.world.gameobject.ObjectHandler;
import org.mistex.game.world.player.Client;


public class WebsHandler {
	
	/**
	 * Web object ID
	 */
	private static int WEBS_ID = 733;
	
	/**
	 * Web respawn timer
	 */
	private static int WEB_TIMER = 30;
	
	/**
	 * Checks if player has correct equipment
	 * @param item
	 * @return
	 */
	private static boolean canSlashWeb(String item) {
		return item.contains("rapier") || item.contains("dagger") || item.contains("scimitar") || item.contains("sword") || item.contains("whip") || item.contains("axe") || item.contains("claws");
	}
	
	/**
	 * Handles the web cutting
	 * @param player
	 * @param objectID
	 * @param x
	 * @param y
	 * @param h
	 * @param face
	 */
	@SuppressWarnings("static-access")
	public static void handleWebs(final Client player, final int objectID, final int x, final int y, final int h, final int face) {
		if (!canSlashWeb(player.getItems().getItemName(player.playerEquipment[3]))) {
			player.sendMessage("You need a sharp weapon to slash the web!");
			return;
		}
		if (MistexUtility.random(1) == 0) {
			player.startAnimation(player.getCombat().getWepAnim(player.getItems().getItemName(player.playerEquipment[3])));
			player.sendMessage("You failed to slash the web!");
			return;
		}
		player.startAnimation(player.getCombat().getWepAnim(player.getItems().getItemName(player.playerEquipment[3])));
		player.sendMessage("You slash the web apart!");
        ObjectHandler.createAnObject(player, -1, x, y);
		respawnWeb(player, WEBS_ID, x, y, face, 0);
	}
	
	/**
	 * Respawns web
	 * @param player
	 * @param objectID
	 * @param x
	 * @param y
	 * @param h
	 * @param face
	 */
	public static void respawnWeb(final Client player, final int objectID, final int x, final int y, final int h, final int face) {
		CycleEventHandler.getSingleton().addEvent(player, new CycleEvent() {
			public void execute(CycleEventContainer p) {
                ObjectHandler.createAnObject(player, WEBS_ID, x, y);
				player.sendMessage("Web has respawned.");
				p.stop();
			}
			
			@Override
			public void stop() {
			}
		}, WEB_TIMER);
		
	}
	
}
