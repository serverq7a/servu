package org.mistex.game.world.content.pvp;

import org.mistex.game.world.player.Client;

public class PvPArtifacts {

			//Item Id, Value
	private static int[][] artifacts = { 	
						{14876, 5000000}, {14877, 1000000}, {14878, 750000}, 
						{14879, 500000}, {14880, 400000}, {14881, 300000}, 
						{14882, 250000}, {14883, 200000}, {14884, 150000},
						{14885, 100000}, {14886, 75000}, {14887, 50000}, {14888, 40000}, 
						{14889, 30000}, {14890, 20000}, {14891, 10000}, 
						{14892, 5000} 
			   		    };

	public static void handleExchange(Client c) {
		for (int j = 0; j < artifacts.length; j++) {
			if (c.getItems().playerHasItem(artifacts[j][0])) {
				deleteAllArtifacts(c, artifacts[j][0]);
				c.getItems().addItem(995, artifacts[j][1]);
			}
		}
		c.getPA().removeAllWindows();
	}

	public static boolean hasArtifact(Client c) {
		for (int j = 0; j < artifacts.length; j++)
			if (c.getItems().playerHasItem(artifacts[j][0]))
				return true;
		return false;
	}

	private static void deleteAllArtifacts(Client c, int artifact) {
		while (c.getItems().playerHasItem(artifact))
			c.getItems().deleteItem(artifact, 1);
	}
}
