package org.mistex.system.util;

import org.mistex.game.world.World;
import org.mistex.game.world.player.Client;
import org.mistex.game.world.player.Player;
import org.mistex.game.world.player.PlayerSave;


public class ShutdownHook extends Thread {

	/**
	 * Our shutdownhook, this is the code that will execute before the vm is
	 * going to exit
	 */
	@SuppressWarnings("unused")
	@Override
	public void run() {
		System.out.println("The shutdown hook has been executed");

		/**
		 * Loop through all players
		 */
		for (Player p : World.players) {

			/**
			 * Check if we are saving an ACTUAL player
			 */
			if (p != null) {

				/**
				 * Create a new client player
				 */
				Client players = (Client) p;

				/**
				 * Actually saves the player, try-catch statement for any errors
				 * we may come across
				 */
				try {
					PlayerSave.saveGame((Client)p);
					System.out.println("Players sucessfully saved, exiting...");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}