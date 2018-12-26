package org.mistex.system.util.log;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;

import org.mistex.game.MistexUtility;
import org.mistex.game.world.player.Client;

public class DonationsLogger {

	private Client c;

	public DonationsLogger(Client Client) {
		this.c = Client;
	}
	
	public String PATH = "./Data/logs/DonationsLog/";
	
	public void donationsRecieved(String itemName, int itemAmount) {
		Calendar C = Calendar.getInstance();
		try {
			BufferedWriter bItem = new BufferedWriter(new FileWriter(PATH+"" + MistexUtility.capitalize(c.playerName) + ".txt",true));
			try {
				bItem.newLine();
				bItem.write("Year : " + C.get(Calendar.YEAR) + "\tMonth : "+ C.get(Calendar.MONTH) + "\tDay : "+ C.get(Calendar.DAY_OF_MONTH));
				bItem.newLine();
				bItem.write("Bought " + MistexUtility.format(itemAmount) + " " + itemName);
				bItem.newLine();
				bItem.write("--------------------------------------------------");
			} finally {
				bItem.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


}