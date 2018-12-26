package org.mistex.game;

/**
 * Handles server configuration
 * 
 * @author Mistex Team
 */

public class MistexConfiguration {

	/**
	 * The Server name
	 */
	public static String SERVER_NAME = "Mistex";

	/**
	 * Sets the server on debug mode
	 */
	public static boolean SERVER_DEBUG = true;

	/**
	 * The Bank size
	 */
	public static final int BANK_SIZE = 352;

	/**
	 * Max players allowed
	 */
	public static final int MAX_PLAYERS = 1024;

	/**
	 * The maximum connections allowed
	 */
	public static final int IPS_ALLOWED = 2;

	/**
	 * The item limit
	 */
	public static final int ITEM_LIMIT = 30000;

	/**
	 * The maximum amount for item
	 */
	public static final int MAXITEM_AMOUNT = Integer.MAX_VALUE;

	/**
	 * The Max amount of NPCS
	 */
	@SuppressWarnings("static-access")
	public static int MAX_NPCS = Mistex.npcHandler.maxNPCs;

	/**
	 * Sends server packets
	 */
	public static boolean sendServerPackets = false;

	/**
	 * Experience lock
	 */
	public static boolean LOCK_EXPERIENCE = false;

	/**
	 * Allowed to play Minigames
	 */
	public static boolean MINI_GAMES = true;

	/**
	 * Logout message
	 */
	public static String LOGOUT_MESSAGE = "Click here to logout!";

	/**
	 * Death message
	 */
	public static String DEATH_MESSAGE = "Oh dear you are dead!";

	/**
	 * Double xp
	 */
	public static boolean DOUBLE_EXP = true;

	/**
	 * Illegal words
	 */
	public static String[] illegalWords = { ",www", ",c0m", ",(om", ",net", ",runescape", ",org", ",com", ",be", ",nl", ",info", "dspk", ".info", ".org", ".tk", ".net", ".com", ".co.uk", ".be", ".nl", ".dk", ".co.cz", ".co", ".us", ".biz", ".eu", ".de", ".cc", ".i n f o", ".o r g", ".t k", ".n e t", ".c o m", ".c o . u k", ".b e", ".n l", ".d k",
			".c o . c z", ".c o", ".u s", ".b i z", ".e u", ".d e", ".c c", ".soulsplit", "soulsplit.", "join the server", "i made a new server", };

	/**
	 * Spam site
	 */
	public static String SITE_SPAM = "www.google.com";

	/**
	 * Administration trades
	 */
	public static boolean ADMIN_CAN_TRADE = false;

	/**
	 * Administration selling items
	 */
	public static boolean ADMIN_CAN_SELL_ITEMS = false;

	/**
	 * Administration dropping items
	 */
	public static boolean ADMIN_DROP_ITEMS = false;

	/**
	 * Items required
	 */
	public static final boolean itemRequirements = true;

	/**
	 * Save timer
	 */
	public static final int SAVE_TIMER = 120;

	/**
	 * Npc random walking distance
	 */
	public static final int NPC_RANDOM_WALK_DISTANCE = 5;

	/**
	 * Npc following distance
	 */
	public static final int NPC_FOLLOW_DISTANCE = 10;

	/**
	 * World list
	 */
	public static final boolean WORLD_LIST_FIX = false;

	/**
	 * Buffer size
	 */
	public static final int BUFFER_SIZE = 10000;

	/**
	 * Important players of Mistex
	 */
	public static String[] isImportant = { "Play Boy", "Omar", "Evan", "Chex", };

	/**
	 * Players that can not be harmed from commands
	 */
	public static String[] CANTHARM = { "omar", "play boy", "mistex", "evan", "chex", };

	/**
	 * Yell tags that can not be used
	 */
	public static String[] BADTAGS = { "bitch", "Bitch", "BITCH", "nigger", "Nigger", "NIGGER", "fuck", "Fuck", "FUCK", "com", "Com", "COM", "fag", "Fag", "FAG", "CEO", "ceo", "Admin", "admin", "Administrator", "administrator", "Owner", "owner", "developer", "Developer", "Moderator", "moderator", "mod", "Mod" };

	/**
	 * Items tradeable
	 */
	public static final int[] ITEM_TRADEABLE = { 12745, 15004, 8850, 10551, 8839, 8840, 8842, 11663, 11664, 11665, 3842, 3844, 3840, 8844, 8845, 8846, 8847, 8848, 8849, 8850, 10551, 6570, 7462, 7461, 7460, 7459, 7458, 7457, 7456, 7455, 7454, 8839, 8840, 8842, 11663, 11664, 11665, 10499, 9748, 9754, 9751, 9769, 9757, 9760, 9763, 9802, 9808, 9784,
			9799, 9805, 9781, 9796, 9793, 9775, 9772, 9778, 9787, 9811, 9766, 9749, 9755, 9752, 9770, 9758, 9761, 9764, 9803, 9809, 9785, 9800, 9806, 9782, 9797, 9794, 9776, 9773, 9779, 9788, 9812, 9767, 9747, 9753, 9750, 9768, 9756, 9759, 9762, 9801, 9807, 9783, 9798, 9804, 9780, 9795, 9792, 9774, 9771, 9777, 9786, 9810, 9765, 26359, 12158, 12159,
			12160, 12161, 12163, 18937, 18938, 18939, 18940, 18941, 18942, 18943, 18944, 18945, 18946, 6539 };

	/**
	 * Undropable items
	 */
	public static final int[] UNDROPPABLE_ITEMS = { 20763, 20764, 3515, 151, 152, 153, 154, 155, 156, 6570 };

	/**
	 * Fun weapons
	 */
	public static final int[] FUN_WEAPONS = { 2460, 2461, 2462, 2463, 2464, 2465, 2466, 2467, 2468, 2469, 2470, 2471, 2471, 2473, 2474, 2475, 2476, 2477 };

	/**
	 * NPCS that can not be attacked.
	 */
	public static final int[] NON_ATTAKABLE_NPCS = new int[] { 8923, 599, 2286, 555, 3021, 700, 541, 545, 557, 461, 6971, 550, 556, 1597, 2282, 211, 246, 494, 2566, 3789, 1696, 495, 700, 2258, 6971, 1035, 5082, 5083, 6063, 1028, 5084, 5085 };

	/**
	 * NPCS that are undead.
	 */
	public static final int[] UNDEAD_NPCS = { 90, 91, 92, 93, 94, 103, 104, 73, 74, 75, 76, 77 };

	/**
	 * How fast the special attack bar refills.
	 */
	public static final int INCREASE_SPECIAL_AMOUNT = 17500;

	/**
	 * If you need more than one prayer point to use prayer.
	 */
	public static final boolean PRAYER_POINTS_REQUIRED = true;

	/**
	 * If you need a certain prayer level to use a certain prayer.
	 */
	public static final boolean PRAYER_LEVEL_REQUIRED = true;

	/**
	 * If you need a certain magic level to use a certain spell.
	 */
	public static final boolean MAGIC_LEVEL_REQUIRED = true;

	/**
	 * How long the god charge spell lasts.
	 */
	public static final int GOD_SPELL_CHARGE = 300000;

	/**
	 * If you need runes to use magic spells.
	 */
	public static final boolean RUNES_REQUIRED = true;

	/**
	 * If you need correct arrows to use with bows.
	 */
	public static final boolean CORRECT_ARROWS = true;

	/**
	 * If the crystal bow degrades.
	 */
	public static final boolean CRYSTAL_BOW_DEGRADES = true;

	/**
	 * Double xp weekend.
	 */
	public static final boolean WEEKEND_DOUBLE_EXP = false;

	/**
	 * Max clans
	 */
	public static final int MAX_CLANS = 100;

	/**
	 * Good distances
	 * 
	 * @param objectX
	 * @param objectY
	 * @param playerX
	 * @param playerY
	 * @param distance
	 * @return
	 */
	public static boolean goodDistance(int objectX, int objectY, int playerX, int playerY, int distance) {
		return ((objectX - playerX <= distance && objectX - playerX >= -distance) && (objectY - playerY <= distance && objectY - playerY >= -distance));
	}

}