package Economy;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class ShopPriceFixer {

	private static File file = new File("./Data/cfg/prices.txt");

	public static void main(String[] args) {
		try {
			String data;
			File tempPriceFile = new File("./Data/cfg/PRICES$TEMP");
			BufferedWriter out = new BufferedWriter(new FileWriter(tempPriceFile));
			BufferedReader in = new BufferedReader(new FileReader(file));
			while ((data = in.readLine()) != null) {
				data = data.trim();
				if (data.startsWith("1038 ")) {
					data = data.split(" ")[0] + " " + 1000000000;
					System.out.println(data);
				}
				out.write(data);
				out.newLine();
			}
			in.close();
			out.close();
			tempPriceFile.renameTo(file);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}