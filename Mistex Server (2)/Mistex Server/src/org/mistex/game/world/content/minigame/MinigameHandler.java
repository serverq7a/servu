package org.mistex.game.world.content.minigame;

import org.mistex.game.world.player.Client;

public class MinigameHandler {
	
	public static void getCorrectCoordinates(Client player) {
		if (player.inPcGame() || player.inPcBoat()) {
			player.getPA().movePlayer(3087, 3499, 0);
		} else if (player.inWeaponLobby() || player.inWeaponGame()) {
			player.getPA().movePlayer(3087, 3499, 0); 
		} else if (player.inFightPits()) {
			player.getPA().movePlayer(3087, 3499, 0);
    	}
	}


}
