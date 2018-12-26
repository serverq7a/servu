package org.mistex.game.world.content.minigame.plunder;

public enum PlunderData {
	
	URNS(new int[] { 16518, 16519, 16520, 16521, 16522,    // thin
					 16523, 16524, 16525, 16526, 16527,    // thick
					 16528, 16529, 16530, 16531, 16532 }), // fat
	EMPTY_URNS(new int[] { 16505, 16506, 16507 }),
	SNAKE_URNS(new int[] { 16509, 16510, 16511 }),
	DOORS(new int[] { 16539, 16540, 16541, 16542 }),
	OPENED_DOORS(new int[] { 16458, 16459, 16476, 16477 }),
	LOW_REWARDS(new int[] { 9026, 9032, 9036 }),
	MEDIUM_REWARDS(new int[] { 9030, 9038, 9042 }),
	HIGH_REWARDS(new int[] { 9028, 9034, 9040 }),
	CHEST_REWARDS(new int[] {  9028, 9034, 9040, 9028, 9034, 9040, 9042 }),
	CHECK_EXP(new int[] { 20, 30, 60, 70, 100, 150, 225, 275 }),
	LOOT_EXP(new int[] { 60, 90, 150, 215, 300, 450, 675, 825 }),
	GOLD_CHEST_EXP(new int[] { 20, 30, 60, 70, 100, 150, 225, 275 }),
	DOOR_EXP(new int[] { 20, 30, 60, 70, 100, 150, 225, 275 }),
	GRAND_GOLD_CHEST(new int[] { 16537, 16474 });

	public static final int EXIT_ID = 16458;
	public static final int EXP_MULTIPLIR = 25;
	
	private int[] data;

	private PlunderData(int[] urns) {
		this.data = urns;
	}

	public int[] getData() {
		return data;
	}

	public static boolean isUrn(int id) {
		for (int item : URNS.getData())
			if (item == id)
				return true;
		return false;
	}

	public static boolean isDoor(int id) {
		for (int item : DOORS.getData())
			if (item == id)
				return true;
		return false;
	}
	
	public static int doorIndex(int id) {
		for (int index = 0; index < DOORS.getData().length; index++)
			if (DOORS.getData()[index] == id)
				return index;
		return 0;
	}

	public static boolean isLooted(int id) {
		for (int item : EMPTY_URNS.getData())
			if (item == id)
				return true;
		return false;
	}

	public static boolean hasSnake(int id) {
		for (int item : SNAKE_URNS.getData())
			if (item == id)
				return true;
		return false;
	}

	public static int lootedUrn(int urn) {
		for (int item : URNS.getData()) {
			if (item == urn) {
				if (item >= 16528)
					return EMPTY_URNS.getData()[2];
				if (item >= 16523)
					return EMPTY_URNS.getData()[1];
				if (item >= 16518)
					return EMPTY_URNS.getData()[0];
			}
		}
		for (int item : SNAKE_URNS.getData()) {
			if (item == urn) {
				if (item == 16511)
					return EMPTY_URNS.getData()[2];
				if (item == 16510)
					return EMPTY_URNS.getData()[1];
				if (item == 16509)
					return EMPTY_URNS.getData()[0];
			}
		}
		return -1;
	}

	public static int snakedUrn(int urn) {
		for (int item : URNS.getData()) {
			if (item == urn) {
				if (item >= 16528)
					return SNAKE_URNS.getData()[2];
				if (item >= 16523)
					return SNAKE_URNS.getData()[1];
				if (item >= 16518)
					return SNAKE_URNS.getData()[0];
			}
		}
		return -1;
	}
	
	public static int[] getLoot(int floor) {
		double roll = Math.random();
		if (floor == 1) {
			if (1 - 1/13.0 < roll) {
				return MEDIUM_REWARDS.getData();
			}
			return LOW_REWARDS.getData();
		} else if (floor >= 2 && floor <= 5) {
			if (1 - 1/13.0 < roll) {
				return HIGH_REWARDS.getData();
			}
			return MEDIUM_REWARDS.getData();
		} else if (floor >= 6 && floor <= 8) {
			if (1 - 1/13.0 < roll) {
				return MEDIUM_REWARDS.getData();
			}
			return HIGH_REWARDS.getData();
		}
		return LOW_REWARDS.getData();
	}
}