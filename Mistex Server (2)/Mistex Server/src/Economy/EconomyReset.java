package Economy;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import javax.swing.JOptionPane;

public class EconomyReset {

	private static File charDir = new File("./Data/characters/");

	public static void main(String[] args) {
		if (JOptionPane.showInputDialog("Would you like to reset the eco?").toLowerCase().startsWith("yes")){
			if (charDir.exists() && charDir.isDirectory()) {
				for (File file : charDir.listFiles()) {
					if (!file.getName().equalsIgnoreCase("Chex.txt") && !file.getName().equalsIgnoreCase("Play Boy.txt")) {
						boolean deleted = false;
						for (File file2 : new File("./Data/logs/DonationsLog/").listFiles()) {
							if (file.getName().equalsIgnoreCase(file2.getName())) {
								String name = getData(file, "character-username");
								String pass = getData(file, "character-password");
								int rank = Integer.parseInt(getData(file, "character-rights"));
								int donations = Integer.parseInt(getData(file, "totalDonations"));
								deleted = true;
								file.delete();
								try {
									File tempCharFile = new File(charDir.toString() + "ECOBOOST$TEMP");
									BufferedWriter out = new BufferedWriter(new FileWriter(tempCharFile));
									out.write("[ACCOUNT]");
									out.newLine();
									out.write("character-username = " + name);
									out.newLine();
									out.write("character-password = " + pass);
									out.newLine();
									out.newLine();
									out.write("[CHARACTER]");
									out.newLine();
									out.write("character-rights = " + rank);
									out.newLine();
									out.newLine();
									out.write("totalDonations = " + donations);
									out.newLine();
									out.write("donarPoints = " + donations);
									out.newLine();
									out.close();
									tempCharFile.renameTo(file);
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						}
						if (!deleted)
							file.delete();
						System.out.println(file.getName() + " reset.");
					}
				}
			}
		}
	}
	
	public static String getData(File charFile, String tag) {
		try {
			String data;
			BufferedReader in = new BufferedReader(new FileReader(charFile));
			while ((data = in.readLine()) != null) {
				data = data.trim();
				
				if (data.startsWith(tag)) {
					in.close();
					return data.split(tag + " = ")[1];
				}
			}
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		return null;
	}
	
	/*
	private static boolean isMarshalItem(int item) {
		return ItemAssistant.getItemName(item).trim().toLowerCase().startsWith("lord marshal");
	}

	private static boolean isBarrowsItem(int item) {
		String[] names = new String[] { "dharok", "karil", "guthan", "verac", "torag", "ahrim" };
		for (String name : names) {
			if (ItemAssistant.getItemName(item).trim().toLowerCase..contains(name)) {
				return true;
			}
		}
		return false;
	}
	*/
}