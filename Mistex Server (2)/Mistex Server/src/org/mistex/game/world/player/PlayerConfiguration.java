package org.mistex.game.world.player;

/**
 * Handles player configurations
 * @author Omar | Play Boy
 *
 */

public class PlayerConfiguration {
	
	public static final int[] ITEMS_TO_BANK_ON_DEATH = {
		11665, 11664, 11663, 8839, 8840, 8842, // Void items.
		10548, 10547, 10550, // Barbarian assault helms .
		6570, 23639, // Fire cape.
		15349, // Ardougne cape.
		7454, 7455, 7456, 7457, 7458, 7459, 7460, 7461, 7462, // All gloves. Such as barrows gloves etc..
		3840, // Holy book. /// TODO: Add these different books to shops.
		3842, // Unholy book.
		3844, // Book of balance.
		8844, 8845, 8846, 8847, 8848, 8849, 8850, 20072, // All defenders.
		2412, 2413, 2414, // God capes.
		10499, 10498, 20068, // Ava's accumulator
	};
	
	/* Spawn Restrictions */
	public static final int[] SPAWN_RESTRICTIONS =	{	
		/** Rares **/
		1038,1039,1040,1041,1042,1043,1044,1045,1046,1047,1048,1049,1050,1051,1052,1053,1054,1055,1056,1057,1058,1059,1060,
		
		/** Nex Armours **/
		13790,13791,13792,13793,13794,13795,13796,13797,13698,13699,
		
		/** Godswords **/
		11694,11695,11696,11697,11698,11699,11700,11701,11730,11731,
		
		/** God Armour **/
		11718,11719,11720,11721,11722,11723,11724,11725,11726,11727,11702,11703,11704,11705,11706,11707,11708,11709,11690,11691,
		
		/** Spirit Shields **/
		13734,13735,13736,13737,13738,13739,13740,13741,13742,13743,13744,
		
		/** Void **/
		11663,11664,11665,11666,8839,8840,8841,8842,
		
		/** Chaotics **/
		15037,15038,15039,15040,
		
		/** PvP Armour **/
		13905,13899,13887,13893,13864,13858,13861,13896,13884,13890,13876,13870,13873,
		
		/** Barrows **/
		4708,4709,4710,4711,4712,4713,4714,4715,4716,4717,4718,4719,4720,4721,4722,4723,4724,4725,4726,4727,4728,4729,4730,
		4731,4732,4733,4734,4735,473-
		/** Third-age **/
		10340,10341,10342,10343,10344,10345,10346,10347,10348,10349,10350,10351,10352,10353,10354,10355,10356,10357,10358,10359,10360,
		
		/** Capes **/
		6570,15004,
		
		/** Soft Items **/
		4151,4152,6585,6586,7462,6731,6732,6733,6734,6735,6736,6737,6738,10551,10552,10548,10549,2581,2582,2577,2578,9005,9006,9470,9471,
		9472,9473,1419,1420,600,601,730,731,1856,1857,5520,5521,7464,7465,
		
		/** Other **/
		11283,11284,11235,11236,11237,11238,11239,11240,11236,15000,15486,15272,15273,6571,6572,11286,11287,14484,
		13758,13761,13759,13760,14604,11777,961,962,963,5376,5377,
		
		/** Sexy **/
		4566,11015,11016,11017,11018,11019,11020,11021,110212,
		9921,9922,9923,9924,9925,9926,9927,9928,10724,10725,10726,10727,10728,10729,
		6858,6859,6860,6861,8159,10836,10837,10838,10839,10840,10842,
		13537,14595,14596,14597,14598,14599,14560,14561,14562,14563,14564,14565,
		
		//TODO:
		//Donator Shop, Tokens, 
		
		/** Tokens **/
		7478
		
	}; 

	/* Starting Location */
	public static final int START_LOCATION_X = 3087;
	public static final int START_LOCATION_Y = 3499;
	
	public static final int TUTORIAL_X = 3094;
	public static final int TUTORIAL_Y = 3105;

	/* Respawn Location */
	public static final int RESPAWN_X = 3087;
	public static final int RESPAWN_Y = 3499;
	
	/* Teleport Locations*/
	public static final int VARROCK_X = 3185;
	public static final int VARROCK_Y = 3447;
	
	public static final int CAMELOT_X = 2721;
	public static final int CAMELOT_Y = 3493;
	
	public static final int FALADOR_X = 3009;
	public static final int FALADOR_Y = 3355;
	
	public static final int DONATORZONE_X = 2847;
	public static final int DONATORZONE_Y = 5070;

	/* Dueling Respawn Location */
	public static final int DUELING_RESPAWN_X = 3362;
	public static final int DUELING_RESPAWN_Y = 3263;
	public static final int RANDOM_DUELING_RESPAWN = 5;
	
	/* Wilderness teleport restriction */
	public static final int NO_TELEPORT_WILD_LEVEL = 20;
	
	/* Skull timer */
	public static final int SKULL_TIMER = 1200;
	
	/* Teleblock delay */
	public static final int TELEBLOCK_DELAY = 20000;
	
	/* Other requirements */
	public static final int INCREASE_SPECIAL_AMOUNT = 17500;
	public static final boolean PRAYER_POINTS_REQUIRED = true;
	public static final boolean PRAYER_LEVEL_REQUIRED = true;
	public static final boolean MAGIC_LEVEL_REQUIRED = true;
	
	/* Combat */
	public static final int GOD_SPELL_CHARGE = 300000;
	public static final boolean RUNES_REQUIRED = true;
	public static final boolean CORRECT_ARROWS = true;
	public static final boolean SINGLE_AND_MULTI_ZONES = true;
	public static final boolean COMBAT_LEVEL_DIFFERENCE = true;

	/* Experience Rates - DON'T FUCKING CHANGE THIS. COMBAT WILL BE FAST SKILLING WILL BE SLOW AS FUCK. END OF STORY. */ 
	public static final int MELEE_EXP_RATE = 950;
	public static final int RANGE_EXP_RATE = 850;
	public static final int MAGIC_EXP_RATE = 830;
	
	public static final double SERVER_EXP_BONUS = 1;

	public static final int PRAYER_EXPERIENCE = 40;
	public static final int SLAYER_EXPERIENCE = 120;
	public static final int SUMMONING_EXPERIENCE = 55;
	
	/** Equipments **/
	public static final int EQUIPMENT_HAT = 0;
	public static final int EQUIPMENT_CAPE = 1;
	public static final int EQUIPMENT_AMULET = 2;
	public static final int EQUIPMENT_WEAPON = 3;
	public static final int EQUIPMENT_CHEST = 4;
	public static final int EQUIPMENT_SHIELD = 5;
	public static final int EQUIPMENT_LEGS = 7;
	public static final int EQUIPMENT_HANDS = 9;
	public static final int EQUIPMENT_FEET = 10;
	public static final int EQUIPMENT_RING = 12;
	public static final int EQUIPMENT_ARROWS = 13;

}
