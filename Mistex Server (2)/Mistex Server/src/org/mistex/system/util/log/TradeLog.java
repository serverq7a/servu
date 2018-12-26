package org.mistex.system.util.log;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;

import org.mistex.game.MistexUtility;
import org.mistex.game.world.World;
import org.mistex.game.world.player.Client;

public class TradeLog {

	private Client c;

	public TradeLog(Client Client) {
		this.c = Client;
	}
	
	public String TRADERECIEVEDPATH = "./Data/logs/TradeLog/received/";
	public String TRADEGIVENPATH = "./Data/logs/TradeLog/given/";

	/**
	 * Will write what kind of item a player has received. MONTH = 0 = January
	 * DAY OF MONTH = 30 || 31
	 */
	public void tradeReceived(String itemName, int itemAmount) {
		Client o = (Client) World.players[c.tradeWith];
		Calendar C = Calendar.getInstance();
		try {
			BufferedWriter bItem = new BufferedWriter(new FileWriter(TRADERECIEVEDPATH+"" + MistexUtility.capitalize(c.playerName) + ".txt",true));
			try {
				bItem.newLine();
				bItem.write("Year : " + C.get(Calendar.YEAR) + "\tMonth : "+ C.get(Calendar.MONTH) + "\tDay : "+ C.get(Calendar.DAY_OF_MONTH));
				bItem.newLine();
				bItem.write("Received " + MistexUtility.format(itemAmount) + " " + itemName+ " From " + MistexUtility.capitalize(o.playerName));
				bItem.newLine();
				bItem.write("--------------------------------------------------");
			} finally {
				bItem.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Will write what kind of item a player has traded with another player.
	 * MONTH = 0 = January DAY OF MONTH = 30 || 31
	 */
	public void tradeGive(String itemName, int itemAmount) {
		Client o = (Client) World.players[c.tradeWith];
		Calendar C = Calendar.getInstance();
		try {
			BufferedWriter bItem = new BufferedWriter(
			new FileWriter(TRADEGIVENPATH+"" + MistexUtility.capitalize(c.playerName)+ ".txt", true));
			try {
				bItem.newLine();
				bItem.write("Year : " + C.get(Calendar.YEAR) + "\tMonth : "+ C.get(Calendar.MONTH) + "\tDay : "+ C.get(Calendar.DAY_OF_MONTH));
				bItem.newLine();
				bItem.write("Given " + MistexUtility.format(itemAmount) + " " + itemName + " To "+ MistexUtility.capitalize(o.playerName));
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