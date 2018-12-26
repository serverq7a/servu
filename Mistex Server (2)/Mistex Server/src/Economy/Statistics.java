package Economy;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class Statistics {

	private static File charDir = new File("./Data/backups/[Fri Jul 11][12.00AM]/");
	static DecimalFormatSymbols separator = new DecimalFormatSymbols();
	static {
		separator.setGroupingSeparator(',');
	}
	static DecimalFormat formatter = new DecimalFormat("#,###,###,###", separator);
	
	public static void main(String[] args) {
		if (charDir.exists() && charDir.isDirectory()) {
			File[] charFiles = charDir.listFiles();
			for (int i = 0; i < charFiles.length; i++) {
				readStatistics(charFiles[i]);
			}
		}
		System.out.println(formatter.format(total) + "gp (~" + (total.divide(BigInteger.valueOf(1000000000))) + "B)");
		System.out.println(totalp);
		System.exit(0);
	}

	static BigInteger total = new BigInteger("0");
	static int totalp = 0;
	
	private static void readStatistics(File charFile) {
		try {
			BufferedReader in = new BufferedReader(new FileReader(charFile));
			String data;
			int to = 0;
			while ((data = in.readLine()) != null) {
				data = data.trim();
				int id = 0;
				if (data.startsWith("character-rights") && (data.contains("2") || data.contains("3"))) {
					in.close();
					return;
				}
				if (data.startsWith("skillPrestiges") && (data.contains("5") || data.contains("4") || data.contains("3") || data.contains("2") || data.contains("1"))) {
					String[] text = data.split("\t");
					for (String s : text) {
						if (s.contains("5") || s.contains("4") || s.contains("3") || s.contains("2") || s.contains("1"))
							totalp++;
					}
				}
				if (data.startsWith("character-item") || data.startsWith("character-bank") || data.startsWith("character-equip")) {
					id = Integer.parseInt(data.split("\t")[1]);
					if (id == 995 || id == 996) {
						to+=Integer.parseInt(data.split("\t")[2]);
						total = total.add(BigInteger.valueOf(Integer.parseInt(data.split("\t")[2])));
					}
				}
			}
			if (to > 20000000)
				System.out.println(formatter.format(to) + ", " + charFile.getName());
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return;
	}

	/*
	private static boolean isBarrowsItem(int item) {
		String[] names = new String[] { "dharok", "karil", "guthan", "verac", "torag", "ahrim" };
		for (String name : names) {
			if (ItemAssistant.getItemName(item).trim().toLowerCase().contains(name)) {
				return true;
			}
		}
		return false;
	}
	*/
}