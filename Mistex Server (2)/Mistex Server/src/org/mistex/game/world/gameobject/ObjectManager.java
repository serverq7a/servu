package org.mistex.game.world.gameobject;

import java.util.ArrayList;

import org.mistex.game.Mistex;
import org.mistex.game.MistexUtility;
import org.mistex.game.world.player.Client;

@SuppressWarnings("static-access")
public class ObjectManager {

	public ArrayList<Object> objects = new ArrayList<Object>();
	private ArrayList<Object> toRemove = new ArrayList<Object>();

	public void process() {
		for (Object o : objects) {
			if (o.tick > 0)
				o.tick--;
			else {
				updateObject(o);
				toRemove.add(o);
			}
		}
		for (Object o : toRemove) {
			if (isObelisk(o.newId)) {
				int index = getObeliskIndex(o.newId);
				if (activated[index]) {
					activated[index] = false;
					teleportObelisk(index);
				}
			}
			objects.remove(o);
		}
		toRemove.clear();
	}

	public void removeObject(int x, int y) {
		for (int j = 0; j < Mistex.playerHandler.players.length; j++) {
			if (Mistex.playerHandler.players[j] != null) {
				Client c = (Client) Mistex.playerHandler.players[j];
				c.getPA().object(-1, x, y, 0, 10);
			}
		}
	}

	public void updateObject(Object o) {
		for (int j = 0; j < Mistex.playerHandler.players.length; j++) {
			if (Mistex.playerHandler.players[j] != null) {
				Client c = (Client) Mistex.playerHandler.players[j];
				c.getPA().object(o.newId, o.objectX, o.objectY, o.face, o.type);
			}
		}
	}

	public void placeObject(Object o) {
		for (int j = 0; j < Mistex.playerHandler.players.length; j++) {
			if (Mistex.playerHandler.players[j] != null) {
				Client c = (Client) Mistex.playerHandler.players[j];
				if (c.distanceToPoint(o.objectX, o.objectY) <= 60)
					c.getPA().object(o.objectId, o.objectX, o.objectY, o.face, o.type);
			}
		}
	}

	public Object getObject(int x, int y, int height) {
		for (Object o : objects) {
			if (o.objectX == x && o.objectY == y && o.height == height)
				return o;
		}
		return null;
	}

	public void loadObjects(Client c) {
		if (c == null)
			return;
		for (Object o : objects) {
			if (loadForPlayer(o, c))
				c.getPA().object(o.objectId, o.objectX, o.objectY, o.face, o.type);
		}
		for (Objects o : Mistex.objectHandler.globalObjects) {
			if (loadForPlayer(o, c))
				c.getPA().object(o.objectId, o.objectX, o.objectY, o.objectFace, o.objectType);
		}
		removeObjects(c);
		removeSkillingObjects(c);
		loadCustomSpawns(c);
		skillingSpawns(c);
		weaponGameObjects(c);
		removeDonatorObjects(c);
	}

	public static void loadCustomSpawns(Client c) {
		/* Home Objects */
		c.getPA().checkObjectSpawn(2006, 3085, 3513, 3, 10); // HIGHSCORE
		c.getPA().checkObjectSpawn(563, 3090, 3492, -3, 10); // PK STATUE
		c.getPA().checkObjectSpawn(6552, 3090, 3509, 3, 10); // Ancients Altar
		c.getPA().checkObjectSpawn(410, 3078, 3484, 6, 10); // Lunar Altar
		c.getPA().checkObjectSpawn(409, 3088, 3483, 0, 10); // Prayer Altar
		c.getPA().checkObjectSpawn(411, 3085, 3510, -3, 10); // Curses Altar

		c.getPA().checkObjectSpawn(4874, 3094, 3500, 0, 10); // Thieving Stall
																// -FOOD
		c.getPA().checkObjectSpawn(4875, 3095, 3500, 0, 10); // Thieving Stall
																// -FOOD
		c.getPA().checkObjectSpawn(4876, 3096, 3500, 0, 10); // Thieving Stall -
																// CRAFT
		c.getPA().checkObjectSpawn(4877, 3097, 3500, 0, 10); // Thieving Stall -
																// MAGIC
		c.getPA().checkObjectSpawn(4878, 3098, 3500, 0, 10); // Thieving Stall -
																// SCIMITAR

		c.getPA().checkObjectSpawn(172, 3080, 3497, 0, 10); // Crystal Chest
		c.getPA().checkObjectSpawn(170, 2975, 3336, 1, 10); // Quest Chest
		c.getPA().checkObjectSpawn(3515, 3090, 3498, 1, 10); // Completionist
																// Cape Stand
		c.getPA().checkObjectSpawn(4019, 3126, 3513, 1, 10); // Summoning Crap
		c.getPA().checkObjectSpawn(4389, 1788, 5336, 0, 10);// Frost teleport
		c.getPA().checkObjectSpawn(14503, 1784, 5336, 1, 10);// Frost Wildy sign
	}

	public static void removeDonatorObjects(Client c) {
		c.getPA().checkObjectSpawn(-1, 2523, 4777, -1, 10);
		c.getPA().checkObjectSpawn(-1, 2843, 5093, -1, 10);
		c.getPA().checkObjectSpawn(-1, 2844, 5095, -1, 10);
		c.getPA().checkObjectSpawn(-1, 2846, 5094, -1, 10);
		c.getPA().checkObjectSpawn(-1, 2849, 5094, -1, 10);
		c.getPA().checkObjectSpawn(-1, 2851, 5095, -1, 10);
		c.getPA().checkObjectSpawn(-1, 2852, 5093, -1, 10);
		c.getPA().checkObjectSpawn(-1, 2855, 5089, -1, 10);
		c.getPA().checkObjectSpawn(-1, 2853, 5097, -1, 10);
		c.getPA().checkObjectSpawn(-1, 2856, 5097, -1, 10);
		c.getPA().checkObjectSpawn(-1, 2856, 5098, -1, 10);
		c.getPA().checkObjectSpawn(-1, 2856, 5099, -1, 10);
		c.getPA().checkObjectSpawn(-1, 2850, 5104, -1, 10);
		c.getPA().checkObjectSpawn(-1, 2845, 5104, -1, 10);
		c.getPA().checkObjectSpawn(-1, 2845, 5101, -1, 10);
		c.getPA().checkObjectSpawn(-1, 2845, 5100, -1, 10);
		c.getPA().checkObjectSpawn(-1, 2850, 5096, -1, 10);
		c.getPA().checkObjectSpawn(-1, 2848, 5095, -1, 10);
		c.getPA().checkObjectSpawn(-1, 2847, 5095, -1, 10);
		c.getPA().checkObjectSpawn(-1, 2845, 5096, -1, 10);
		c.getPA().checkObjectSpawn(-1, 2842, 5097, -1, 10);
		c.getPA().checkObjectSpawn(-1, 2839, 5097, -1, 10);
		c.getPA().checkObjectSpawn(-1, 2839, 5099, -1, 10);
		c.getPA().checkObjectSpawn(-1, 2846, 5102, -1, 10);
		c.getPA().checkObjectSpawn(-1, 2847, 5102, -1, 10);
		c.getPA().checkObjectSpawn(-1, 2848, 5102, -1, 10);
		c.getPA().checkObjectSpawn(-1, 2849, 5102, -1, 10);
		c.getPA().checkObjectSpawn(-1, 2850, 5100, -1, 10);
		c.getPA().checkObjectSpawn(-1, 2850, 5101, -1, 10);
		c.getPA().checkObjectSpawn(-1, 2849, 5099, -1, 10);
		c.getPA().checkObjectSpawn(-1, 2848, 5099, -1, 10);
		c.getPA().checkObjectSpawn(-1, 2847, 5099, -1, 10);
		c.getPA().checkObjectSpawn(-1, 2846, 5099, -1, 10);
		c.getPA().checkObjectSpawn(-1, 2851, 5104, -1, 10);
		c.getPA().checkObjectSpawn(-1, 2852, 5104, -1, 10);
		c.getPA().checkObjectSpawn(-1, 2853, 5104, -1, 10);
		c.getPA().checkObjectSpawn(-1, 2854, 5104, -1, 10);
		c.getPA().checkObjectSpawn(-1, 2844, 5104, -1, 10);
		c.getPA().checkObjectSpawn(-1, 2843, 5104, -1, 10);
		c.getPA().checkObjectSpawn(-1, 2842, 5104, -1, 10);
		c.getPA().checkObjectSpawn(-1, 2841, 5104, -1, 10);
		c.getPA().checkObjectSpawn(-1, 2831, 5095, -1, 10);
		c.getPA().checkObjectSpawn(-1, 2831, 5097, -1, 10);
		c.getPA().checkObjectSpawn(-1, 2831, 5099, -1, 10);
		c.getPA().checkObjectSpawn(-1, 2833, 5101, -1, 10);
		c.getPA().checkObjectSpawn(-1, 2835, 5101, -1, 10);
		c.getPA().checkObjectSpawn(-1, 2837, 5101, -1, 10);
		c.getPA().checkObjectSpawn(-1, 2839, 5096, -1, 10);
		c.getPA().checkObjectSpawn(-1, 2839, 5095, -1, 10);
		c.getPA().checkObjectSpawn(-1, 2839, 5094, -1, 10);
		c.getPA().checkObjectSpawn(-1, 2857, 5099, -1, 10);
		c.getPA().checkObjectSpawn(-1, 2858, 5094, -1, 10);
		c.getPA().checkObjectSpawn(-1, 2859, 5094, -1, 10);
		c.getPA().checkObjectSpawn(-1, 2861, 5094, -1, 10);
		c.getPA().checkObjectSpawn(-1, 2862, 5094, -1, 10);
		c.getPA().checkObjectSpawn(-1, 2863, 5094, -1, 10);
		c.getPA().checkObjectSpawn(-1, 2864, 5097, -1, 10);
		c.getPA().checkObjectSpawn(-1, 2864, 5098, -1, 10);
		c.getPA().checkObjectSpawn(-1, 2862, 5101, -1, 10);
		c.getPA().checkObjectSpawn(-1, 2861, 5101, -1, 10);
		c.getPA().checkObjectSpawn(-1, 2861, 5100, -1, 10);
		c.getPA().checkObjectSpawn(-1, 2861, 5099, -1, 10);
		c.getPA().checkObjectSpawn(-1, 2861, 5098, -1, 10);
		c.getPA().checkObjectSpawn(-1, 2860, 5098, -1, 10);
		c.getPA().checkObjectSpawn(-1, 2860, 5099, -1, 10);
		c.getPA().checkObjectSpawn(-1, 2860, 5097, -1, 10);

		c.getPA().checkObjectSpawn(4019, 2853, 5086, 1, 10); // Summoning Crap
		c.getPA().checkObjectSpawn(4874, 2851, 5104, 0, 10); // Thieving Stall
																// -CRAFT
		c.getPA().checkObjectSpawn(4875, 2852, 5104, 0, 10); // Thieving Stall
																// -FOOD
		c.getPA().checkObjectSpawn(4876, 2853, 5104, 0, 10); // Thieving Stall -
																// RUNES
		c.getPA().checkObjectSpawn(4877, 2854, 5104, 0, 10); // Thieving Stall -
																// MAGIC
		c.getPA().checkObjectSpawn(4878, 2855, 5104, 0, 10); // Thieving Stall -
																// SCIMITAR
		c.getPA().checkObjectSpawn(4874, 2844, 5104, 0, 10); // Thieving Stall
																// -CRAFT
		c.getPA().checkObjectSpawn(4875, 2843, 5104, 0, 10); // Thieving Stall
																// -FOOD
		c.getPA().checkObjectSpawn(4876, 2842, 5104, 0, 10); // Thieving Stall -
																// RUNES
		c.getPA().checkObjectSpawn(4877, 2841, 5104, 0, 10); // Thieving Stall -
																// MAGIC
		c.getPA().checkObjectSpawn(4878, 2840, 5104, 0, 10); // Thieving Stall -
																// SCIMITAR
		c.getPA().checkObjectSpawn(2213, 2846, 5095, 0, 10); // Bank Booth
		c.getPA().checkObjectSpawn(2213, 2847, 5095, 0, 10); // Bank Booth
		c.getPA().checkObjectSpawn(2213, 2848, 5095, 0, 10); // Bank Booth
		c.getPA().checkObjectSpawn(2213, 2849, 5095, 0, 10); // Bank Booth
		c.getPA().checkObjectSpawn(2213, 2839, 5096, 1, 10); // Bank Booth
		c.getPA().checkObjectSpawn(2213, 2839, 5095, 1, 10); // Bank Booth
		c.getPA().checkObjectSpawn(2213, 2839, 5094, 1, 10); // Bank Booth

		c.getPA().checkObjectSpawn(12351, 2857, 5101, 1, 10);// Boxing
		c.getPA().checkObjectSpawn(12351, 2858, 5101, 1, 10);// Boxing
		c.getPA().checkObjectSpawn(12351, 2859, 5101, 1, 10);// Boxing
		c.getPA().checkObjectSpawn(12351, 2860, 5101, 1, 10);// Boxing
		c.getPA().checkObjectSpawn(12351, 2861, 5101, 1, 10);// Boxing

		c.getPA().checkObjectSpawn(12351, 2862, 5101, 1, 10);// Boxing
		c.getPA().checkObjectSpawn(12351, 2863, 5101, 1, 10);// Boxing

		c.getPA().checkObjectSpawn(12351, 2864, 5100, 0, 10);// Boxing
		c.getPA().checkObjectSpawn(12351, 2864, 5099, 0, 10);// Boxing
		c.getPA().checkObjectSpawn(12351, 2864, 5098, 0, 10);// Boxing
		c.getPA().checkObjectSpawn(12351, 2864, 5097, 0, 10);// Boxing
		c.getPA().checkObjectSpawn(12351, 2864, 5096, 0, 10);// Boxing
		c.getPA().checkObjectSpawn(12351, 2864, 5095, 0, 10);// Boxing

		// c.getPA().checkObjectSpawn(12351, 2863, 5094, 1, 10);//Boxing
		c.getPA().checkObjectSpawn(12351, 2862, 5094, 1, 10);// Boxing
		c.getPA().checkObjectSpawn(12351, 2861, 5094, 1, 10);// Boxing
		c.getPA().checkObjectSpawn(12351, 2860, 5094, 1, 10);// Boxing
		c.getPA().checkObjectSpawn(12351, 2859, 5094, 1, 10);// Boxing
		c.getPA().checkObjectSpawn(12351, 2858, 5094, 1, 10);// Boxing
		c.getPA().checkObjectSpawn(12351, 2857, 5094, 1, 10);// Boxing

		c.getPA().checkObjectSpawn(12351, 2857, 5095, 0, 10);// Boxing
		c.getPA().checkObjectSpawn(12351, 2857, 5096, 0, 10);// Boxing
		c.getPA().checkObjectSpawn(12351, 2857, 5097, 0, 10);// Boxing
		c.getPA().checkObjectSpawn(12351, 2857, 5098, 0, 10);// Boxing
		c.getPA().checkObjectSpawn(12351, 2857, 5099, 0, 10);// Boxing
		c.getPA().checkObjectSpawn(12351, 2857, 5100, 0, 10);// Boxing

	}

	public static void removeObjects(Client c) {
		c.getPA().checkObjectSpawn(-1, 3079, 3501, -1, 10);
		c.getPA().checkObjectSpawn(-1, 3082, 3516, -1, 10);
		c.getPA().checkObjectSpawn(-1, 3091, 3499, -1, 10);
		c.getPA().checkObjectSpawn(-1, 3091, 3495, -1, 10);
		c.getPA().checkObjectSpawn(-1, 3095, 3498, -1, 10);
		c.getPA().checkObjectSpawn(-1, 3098, 3499, -1, 10);
		c.getPA().checkObjectSpawn(-1, 3092, 3488, -1, 10);
		c.getPA().checkObjectSpawn(-1, 3093, 3488, -1, 10);
		c.getPA().checkObjectSpawn(-1, 3095, 3499, -1, 10);
		c.getPA().checkObjectSpawn(-1, 3090, 3496, -1, 10);
		c.getPA().checkObjectSpawn(-1, 3092, 3496, -1, 10);
		c.getPA().checkObjectSpawn(-1, 3090, 3494, -1, 10);
		c.getPA().checkObjectSpawn(-1, 3096, 3501, -1, 10);
	}

	public static void skillingSpawns(Client c) {
		/** SKilling Zone **/
		c.getPA().checkObjectSpawn(4874, 2332, 3685, 0, 10); // Thieving Stall
																// -FOOD
		c.getPA().checkObjectSpawn(4875, 2331, 3685, 0, 10); // Thieving Stall
																// -FOOD
		c.getPA().checkObjectSpawn(4876, 2330, 3685, 0, 10); // Thieving Stall -
																// CRAFT
		c.getPA().checkObjectSpawn(4877, 2329, 3685, 0, 10); // Thieving Stall -
																// MAGIC
		c.getPA().checkObjectSpawn(4878, 2328, 3685, 0, 10); // Thieving Stall -
																// SCIMITAR
		c.getPA().checkObjectSpawn(2782, 2342, 3678, 0, 10); // Anvil
		c.getPA().checkObjectSpawn(2782, 2342, 3673, 0, 10); // Anvil
		c.getPA().checkObjectSpawn(3044, 2345, 3675, -10, 10); // Furnace
		c.getPA().checkObjectSpawn(14367, 2327, 3686, -1, 10); // Bank Booth
		c.getPA().checkObjectSpawn(14367, 2327, 3687, -1, 10); // Bank Booth
		c.getPA().checkObjectSpawn(14367, 2327, 3688, -1, 10); // Bank Booth
		c.getPA().checkObjectSpawn(14367, 2327, 3689, -1, 10); // Bank Booth
		c.getPA().checkObjectSpawn(14367, 2327, 3690, -1, 10); // Bank Booth
		c.getPA().checkObjectSpawn(14367, 2327, 3691, -1, 10); // Bank Booth
		c.getPA().checkObjectSpawn(14367, 2327, 3692, -1, 10); // Bank Booth
		c.getPA().checkObjectSpawn(14367, 2327, 3693, -1, 10); // Bank Booth
		c.getPA().checkObjectSpawn(14367, 3064, 4973, 1, 10); // Bank Booth
		c.getPA().checkObjectSpawn(14367, 3064, 4972, 1, 10); // Bank Booth
		c.getPA().checkObjectSpawn(14367, 3064, 4971, 1, 10); // Bank Booth
		c.getPA().checkObjectSpawn(14367, 3064, 4970, 1, 10); // Bank Booth
		c.getPA().checkObjectSpawn(14367, 3064, 4969, 1, 10); // Bank Booth
		c.getPA().checkObjectSpawn(14367, 3064, 4968, 1, 10); // Bank Booth
		c.getPA().checkObjectSpawn(2213, 2661, 3374, 1, 10); // Bank Booth
		c.getPA().checkObjectSpawn(2213, 2804, 3463, 1, 10); // Bank Booth
		c.getPA().checkObjectSpawn(2213, 3596, 3523, 1, 10); // Bank Booth
		c.getPA().checkObjectSpawn(2213, 3055, 3312, 0, 10); // Bank Booth
		c.getPA().checkObjectSpawn(354, 2070, 4447, 0, 10);
		c.getPA().checkObjectSpawn(354, 2059, 4447, 0, 10);

		c.getPA().checkObjectSpawn(2213, 3045, 9776, 0, 10); // Bank Booth
		c.getPA().checkObjectSpawn(2213, 3046, 9776, 0, 10); // Bank Booth
		c.getPA().checkObjectSpawn(2213, 3044, 9776, 0, 10); // Bank Booth

		c.getPA().checkObjectSpawn(14859, 3052, 9768, 0, 10); // Rune Ore
		c.getPA().checkObjectSpawn(14859, 3052, 9767, 0, 10); // Rune Ore
		c.getPA().checkObjectSpawn(14859, 3052, 9766, 0, 10); // Rune Ore
		c.getPA().checkObjectSpawn(14859, 3051, 9765, 0, 10); // Rune Ore
	}

	public static void removeSkillingObjects(Client c) {
		/** Shit deleted - Omar / Play Boy **/
		c.getPA().checkObjectSpawn(-1, 2320, 3688, 0, 10); // BEDS
		c.getPA().checkObjectSpawn(-1, 2320, 3686, 0, 10); // BEDS
		c.getPA().checkObjectSpawn(-1, 2320, 3684, 0, 10); // BEDS
		c.getPA().checkObjectSpawn(-1, 2320, 3682, 0, 10); // BEDS
		c.getPA().checkObjectSpawn(-1, 2320, 3680, 0, 10); // BEDS
		c.getPA().checkObjectSpawn(-1, 2316, 3688, 0, 10); // BEDS
		c.getPA().checkObjectSpawn(-1, 2316, 3686, 0, 10); // BEDS
		c.getPA().checkObjectSpawn(-1, 2316, 3684, 0, 10); // BEDS
		c.getPA().checkObjectSpawn(-1, 2316, 3682, 0, 10); // BEDS
		c.getPA().checkObjectSpawn(-1, 2316, 3680, 0, 10); // BEDS
		c.getPA().checkObjectSpawn(-1, 2321, 3689, 0, 10); // Minning Space
		c.getPA().checkObjectSpawn(-1, 2316, 3689, 0, 10); // Minning Space
		c.getPA().checkObjectSpawn(-1, 2316, 3678, 0, 10); // Minning Space
		c.getPA().checkObjectSpawn(-1, 2321, 3678, 0, 10); // Minning Space
		c.getPA().checkObjectSpawn(-1, 2329, 3686, 0, 10); // Bank Space
		c.getPA().checkObjectSpawn(-1, 2332, 3686, 0, 10); // Bank Space
		c.getPA().checkObjectSpawn(-1, 2332, 3687, 0, 10); // Bank Space
		c.getPA().checkObjectSpawn(-1, 2328, 3693, 0, 10); // Bank Space
		c.getPA().checkObjectSpawn(-1, 2329, 3693, 0, 10); // Bank Space
		c.getPA().checkObjectSpawn(-1, 2330, 3693, 0, 10); // Bank Space
		c.getPA().checkObjectSpawn(-1, 2331, 3693, 0, 10); // Bank Space
		c.getPA().checkObjectSpawn(-1, 2332, 3693, 0, 10); // Bank Space
		c.getPA().checkObjectSpawn(-1, 2332, 3692, 0, 10); // Bank Space
		c.getPA().checkObjectSpawn(-1, 2331, 3692, 0, 10); // Bank Space
		c.getPA().checkObjectSpawn(-1, 2330, 3692, 0, 10); // Bank Space
		c.getPA().checkObjectSpawn(-1, 2332, 3691, 0, 10); // Bank Space
		c.getPA().checkObjectSpawn(-1, 2319, 3677, 0, 0); // door
		c.getPA().checkObjectSpawn(-1, 2319, 3690, 0, 0); // door
		c.getPA().checkObjectSpawn(-1, 3084, 3502, 0, 10);
		c.getPA().checkObjectSpawn(-1, 2732, 3369, 0, 10);
		c.getPA().checkObjectSpawn(-1, 2611, 4776, 0, 10);
		c.getPA().checkObjectSpawn(-1, 3077, 3496, -1, 10);
		c.getPA().checkObjectSpawn(-1, 3077, 3495, -1, 10);
		c.getPA().checkObjectSpawn(-1, 2732, 3365, 0, 10);
		c.getPA().checkObjectSpawn(-1, 2735, 3367, 0, 10);
		c.getPA().checkObjectSpawn(-1, 2729, 3373, 0, 0);
		c.getPA().checkObjectSpawn(-1, 2728, 3373, 0, 0);
		c.getPA().checkObjectSpawn(-1, 2729, 3378, 0, 0);
		c.getPA().checkObjectSpawn(-1, 2725, 3378, 0, 0);
		c.getPA().checkObjectSpawn(-1, 2726, 3368, 0, 0);
		c.getPA().checkObjectSpawn(-1, 3088, 3509, 0, 10);
		c.getPA().checkObjectSpawn(-1, 3080, 3507, 0, 10);
		c.getPA().checkObjectSpawn(-1, 3083, 3507, 0, 10);
		c.getPA().checkObjectSpawn(-1, 3079, 3507, 0, 10);
		c.getPA().checkObjectSpawn(-1, 3077, 3507, 0, 10);
		c.getPA().checkObjectSpawn(-1, 3084, 3509, 0, 10);
		c.getPA().checkObjectSpawn(-1, 3084, 3510, 0, 10);
		c.getPA().checkObjectSpawn(-1, 3084, 3512, 0, 10);
		c.getPA().checkObjectSpawn(-1, 3083, 3513, 0, 10);
		c.getPA().checkObjectSpawn(-1, 3080, 3513, 0, 10);
		c.getPA().checkObjectSpawn(-1, 3079, 3513, 0, 10);
		c.getPA().checkObjectSpawn(-1, 3078, 3513, 0, 10);
		c.getPA().checkObjectSpawn(-1, 3077, 3513, 0, 10);
		c.getPA().checkObjectSpawn(-1, 3077, 3512, 0, 10);
		c.getPA().checkObjectSpawn(-1, 3076, 3509, 0, 10);
		c.getPA().checkObjectSpawn(-1, 3076, 3510, 0, 10);
		c.getPA().checkObjectSpawn(-1, 3076, 3511, 0, 10);
		c.getPA().checkObjectSpawn(-1, 3076, 3512, 0, 10);
		c.getPA().checkObjectSpawn(-1, 3078, 3510, 0, 10);
		c.getPA().checkObjectSpawn(-1, 3080, 3510, 0, 10);
		c.getPA().checkObjectSpawn(-1, 3081, 3510, 0, 10);
		c.getPA().checkObjectSpawn(-1, 3095, 3480, 0, 10);
		c.getPA().checkObjectSpawn(-1, 3096, 3479, 0, 10);
		c.getPA().checkObjectSpawn(-1, 3097, 3481, 0, 10);
		c.getPA().checkObjectSpawn(-1, 3095, 3477, 0, 10);
		c.getPA().checkObjectSpawn(-1, 3096, 3479, 0, 10);
		c.getPA().checkObjectSpawn(-1, 3097, 3474, 0, 10);
		c.getPA().checkObjectSpawn(-1, 3096, 3476, 0, 10);
		c.getPA().checkObjectSpawn(-1, 3092, 3477, 0, 10);
		c.getPA().checkObjectSpawn(-1, 3090, 3476, 0, 10);
		c.getPA().checkObjectSpawn(-1, 3090, 3474, 0, 10);
		c.getPA().checkObjectSpawn(-1, 3091, 3478, 0, 10);
		c.getPA().checkObjectSpawn(-1, 3092, 3480, 0, 10);
		c.getPA().checkObjectSpawn(-1, 3090, 3479, 0, 10);
		c.getPA().checkObjectSpawn(-1, 3096, 3469, 0, 10);
		c.getPA().checkObjectSpawn(-1, 3048, 3494, 0, 10);
		c.getPA().checkObjectSpawn(-1, 3054, 3494, 0, 10);
		c.getPA().checkObjectSpawn(-1, 3103, 9909, 0, 0);
		c.getPA().checkObjectSpawn(-1, 3101, 9910, 0, 0);
		c.getPA().checkObjectSpawn(-1, 2543, 10143, 0, 10);
		c.getPA().checkObjectSpawn(-1, 2541, 10141, 0, 10);
		c.getPA().checkObjectSpawn(-1, 2545, 10145, 0, 10);
		c.getPA().checkObjectSpawn(-1, 3090, 3503, 0, 10);
		c.getPA().checkObjectSpawn(-1, 3090, 3494, 0, 10);
		c.getPA().checkObjectSpawn(-1, 3091, 3495, 0, 10);
		c.getPA().checkObjectSpawn(-1, 3090, 3496, 0, 10);
		c.getPA().checkObjectSpawn(-1, 3092, 3496, 0, 10);
		c.getPA().checkObjectSpawn(-1, 2728, 3373, 0, 10);
		c.getPA().checkObjectSpawn(-1, 2729, 3373, 0, 10);
		c.getPA().checkObjectSpawn(-1, 2726, 3368, 0, 10);
		c.getPA().checkObjectSpawn(-1, 2323, 3675, 0, 10);
		c.getPA().checkObjectSpawn(-1, 2322, 3675, 0, 10);
		c.getPA().checkObjectSpawn(-1, 2321, 3675, 0, 10);
		c.getPA().checkObjectSpawn(-1, 2346, 3677, 0, 10);
		c.getPA().checkObjectSpawn(-1, 2338, 3678, 0, 10);
		c.getPA().checkObjectSpawn(-1, 2339, 3678, 0, 10);
		c.getPA().checkObjectSpawn(-1, 2342, 3678, 0, 10);
		c.getPA().checkObjectSpawn(-1, 2345, 3678, 0, 10);
		c.getPA().checkObjectSpawn(-1, 2346, 3678, 0, 10);
		c.getPA().checkObjectSpawn(-1, 2346, 3668, 0, 10);
		c.getPA().checkObjectSpawn(-1, 2346, 3674, 0, 10);
		c.getPA().checkObjectSpawn(-1, 2346, 3673, 0, 10);
		c.getPA().checkObjectSpawn(-1, 2345, 3673, 0, 10);
		c.getPA().checkObjectSpawn(-1, 2343, 3673, 0, 10);
		c.getPA().checkObjectSpawn(-1, 2342, 3673, 0, 10);
		c.getPA().checkObjectSpawn(-1, 2341, 3673, 0, 10);
		c.getPA().checkObjectSpawn(-1, 2340, 3673, 0, 10);
		c.getPA().checkObjectSpawn(-1, 2339, 3673, 0, 10);
		c.getPA().checkObjectSpawn(-1, 2338, 3673, 0, 10);
		c.getPA().checkObjectSpawn(-1, 2338, 3674, 0, 10);
		c.getPA().checkObjectSpawn(-1, 2339, 3674, 0, 10);
		c.getPA().checkObjectSpawn(-1, 2341, 3675, 0, 10);
		c.getPA().checkObjectSpawn(-1, 2343, 3675, 0, 10);
	}

	public static void weaponGameObjects(Client c) {
		c.getPA().checkObjectSpawn(354, 3370, 3389, 0, 10);
		c.getPA().checkObjectSpawn(354, 3371, 3389, 0, 10);
		c.getPA().checkObjectSpawn(354, 3372, 3389, 0, 10);
		c.getPA().checkObjectSpawn(354, 3373, 3389, 0, 10);
		c.getPA().checkObjectSpawn(354, 3374, 3389, 0, 10);
		c.getPA().checkObjectSpawn(354, 3375, 3389, 0, 10);
		c.getPA().checkObjectSpawn(354, 3375, 3390, 0, 10);
		c.getPA().checkObjectSpawn(354, 3375, 3391, 0, 10);
		c.getPA().checkObjectSpawn(354, 3346, 3396, 0, 10);
		c.getPA().checkObjectSpawn(354, 3346, 3395, 0, 10);
		c.getPA().checkObjectSpawn(354, 3346, 3394, 0, 10);
		c.getPA().checkObjectSpawn(354, 3346, 3393, 0, 10);
		c.getPA().checkObjectSpawn(354, 3378, 3393, 0, 10);
		c.getPA().checkObjectSpawn(354, 3377, 3393, 0, 10);
		c.getPA().checkObjectSpawn(354, 3343, 3348, 0, 10);
		c.getPA().checkObjectSpawn(354, 3343, 3347, 0, 10);
	}

	public final int IN_USE_ID = 14825;

	public boolean isObelisk(int id) {
		for (int j = 0; j < obeliskIds.length; j++) {
			if (obeliskIds[j] == id)
				return true;
		}
		return false;
	}

	public int[] obeliskIds = { 14829, 14830, 14827, 14828, 14826, 14831 };
	public int[][] obeliskCoords = { { 3154, 3618 }, { 3225, 3665 }, { 3033, 3730 }, { 3104, 3792 }, { 2978, 3864 }, { 3305, 3914 } };
	public boolean[] activated = { false, false, false, false, false, false };

	public void startObelisk(int obeliskId) {
		int index = getObeliskIndex(obeliskId);
		if (index >= 0) {
			if (!activated[index]) {
				activated[index] = true;
				addObject(new Object(14825, obeliskCoords[index][0], obeliskCoords[index][1], 0, -1, 10, obeliskId, 16));
				addObject(new Object(14825, obeliskCoords[index][0] + 4, obeliskCoords[index][1], 0, -1, 10, obeliskId, 16));
				addObject(new Object(14825, obeliskCoords[index][0], obeliskCoords[index][1] + 4, 0, -1, 10, obeliskId, 16));
				addObject(new Object(14825, obeliskCoords[index][0] + 4, obeliskCoords[index][1] + 4, 0, -1, 10, obeliskId, 16));
			}
		}
	}

	public int getObeliskIndex(int id) {
		for (int j = 0; j < obeliskIds.length; j++) {
			if (obeliskIds[j] == id)
				return j;
		}
		return -1;
	}

	public void teleportObelisk(int port) {
		int random = MistexUtility.random(5);
		while (random == port) {
			random = MistexUtility.random(5);
		}
		for (int j = 0; j < Mistex.playerHandler.players.length; j++) {
			if (Mistex.playerHandler.players[j] != null) {
				Client c = (Client) Mistex.playerHandler.players[j];
				int xOffset = c.absX - obeliskCoords[port][0];
				int yOffset = c.absY - obeliskCoords[port][1];
				if (c.goodDistance(c.getX(), c.getY(), obeliskCoords[port][0] + 2, obeliskCoords[port][1] + 2, 1)) {
					c.getPA().startTeleport2(obeliskCoords[random][0] + xOffset, obeliskCoords[random][1] + yOffset, 0);
				}
			}
		}
	}

	public boolean loadForPlayer(Object o, Client c) {
		if (o == null || c == null)
			return false;
		return c.distanceToPoint(o.objectX, o.objectY) <= 60 && c.heightLevel == o.height;
	}

	public boolean loadForPlayer(Objects o, Client c) {
		if (o == null || c == null)
			return false;
		return c.distanceToPoint(o.objectX, o.objectY) <= 60 && c.heightLevel == o.objectHeight;
	}

	public void addObject(Object o) {
		if (getObject(o.objectX, o.objectY, o.height) == null) {
			objects.add(o);
			placeObject(o);
		}
	}

}