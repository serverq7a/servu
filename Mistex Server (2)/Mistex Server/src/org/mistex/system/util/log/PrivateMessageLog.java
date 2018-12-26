package org.mistex.system.util.log;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.mistex.game.MistexUtility;
import org.mistex.game.world.player.Client;

public class PrivateMessageLog {

	private Client c;

	public PrivateMessageLog(Client Client) {
		this.c = Client;
	}

	public String PATH = "./Data/logs/PMLog/";

	public void Logger(String ChatType, String ChatText) {
		Calendar Cal = new GregorianCalendar();
		int MONTH = Cal.get(Calendar.MONTH);
		int DAY = Cal.get(Calendar.DAY_OF_MONTH);
		int HOUR = Cal.get(Calendar.HOUR_OF_DAY);
		int MIN = Cal.get(Calendar.MINUTE);
		BufferedWriter chat = null;
		try {
			chat = new BufferedWriter(new FileWriter(PATH + "" + MistexUtility.capitalize(c.playerName) + ".txt", true));
			chat.write("Day " + DAY + "   Month: " + MONTH + "   Hour: " + HOUR + "   Min " + MIN + "	Chat Style = " + ChatType);
			chat.newLine();
			chat.write("Said: " + ChatText);
			chat.newLine();
			chat.newLine();
			chat.flush();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {
			if (chat != null)
				try {
					chat.close();
				} catch (IOException ioe2) {
				}
		}
	}

}
