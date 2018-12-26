package org.mistex.game.world.shop;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.mistex.game.Mistex;
import org.mistex.game.world.World;
import org.mistex.game.world.player.Client;
import org.mistex.game.world.player.Player;

/**
 * Executes shops
 * @author Omar | Play Boy & Michael | Chex
 */
public class Shops {

	/**
	 * Path to shops
	 */
	private static final String PATH = "./data/cfg/shops.txt";
	
	/**
	 * Maxium stock
	 */
	protected static final int MAX_STOCKS = 36;
	
	/**
	 * Restorations time
	 */
	private static final int MILLIS_BETWEEN_RESTORATIONS = 7500;
			
	/**
	 * Last restoration
	 */
	private static long lastRestoration = 0;
	
	/**
	 * Map for shops
	 */
	private static Map<Integer, Shop> shops;
	
	/**
	 * Logger for shops
	 */
	private static final Logger logger = Logger.getLogger(Mistex.class.getName());

	/**
	 * Loads the shops
	 */
	public static void loadShops() {
		logger.info("Initialized shops.");
		String data = null;
		try {
			byte[] encoded = Files.readAllBytes(Paths.get(PATH));
			data = StandardCharsets.UTF_8.decode(ByteBuffer.wrap(encoded)).toString();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (data == null) {
			System.out.println("Error!");
			return;
		}
		shops = new HashMap<Integer, Shop>();
		String[] components = data.replaceAll("\\r\\n|\\r|\\n|\\t", "").split("\\}");
		for (int i = 0; i < components.length; i++) {
			String[] s = components[i].split("\\{");
			String[] main = s[0].split("\"|\'");
			shops.put(Integer.parseInt(main[0].replaceAll(" ", "")), new Shop(main[1],
					s.length > 1 ? s[1] : null));
		}
	}
	
	/**
	 * Process shops
	 */
	public static void process() {
		long millis = System.currentTimeMillis();
		if (millis - lastRestoration > MILLIS_BETWEEN_RESTORATIONS) {
			for (Map.Entry<Integer, Shop> entry : shops.entrySet()) {
				if (entry.getValue().restore()) {
					int shop = entry.getKey();
					for (Player p : World.players) {
						if (p != null && p.shopIndex == shop) {
							ShopExecutor.update((Client)p, Shops.get(shop));
						}
					}
				}
			}
			lastRestoration = millis;
		}
	}
	
	/**
	 * Gets shops
	 * @param index
	 * @return
	 */
	public static Shop get(int index) {
		return shops.get(index);
	}
}