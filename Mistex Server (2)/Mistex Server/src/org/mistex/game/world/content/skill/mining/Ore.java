package org.mistex.game.world.content.skill.mining;

public enum Ore {
	COPPER(new int[] { 15505, 15503, 11936, 11937, 11938, 2091, 2090 }, 436, 1, 18, 8),
	TIN(new int[] { 2311, 11933, 2094, 2095, 11959, 11958, 11957 }, 438, 1, 18, 8),
	IRON(new int[] { 31073, 2093, 2092, 11954, 11955, 11956 }, 440, 15, 35, 10),
	COAL(new int[] { 11930, 11932, 2097, 2096 }, 453, 30, 50, 15),
	GOLD(new int[] { 31065, 2098, 2099 }, 444, 40, 65, 20),
	SILVER(new int[] { 2100, 2101 }, 442, 20, 40, 5),
	CLAY(new int[] { 15504, 15503, 15505, 11950, 11949, 11948 }, 434, 40, 65, 20),
	MITHRIL(new int[] { 11944, 11942, 2103, 2102 }, 447, 55, 80, 25),
	ADAMANT(new int[] { 11939, 11941, 2104, 2105 }, 449, 70, 95, 50),
	RUNITE(new int[] { 14859, 14860 }, 451, 85, 125, 100);

	private int[] ores;
	private int product, levelReq, exp, respawn;

	private Ore(int[] ores, int product, int levelReq, int exp, int respawn) {
		this.ores = ores;
		this.product = product;
		this.levelReq = levelReq;
		this.exp = exp;
		this.respawn = respawn;
	}

	public int getExp() {
		return exp;
	}

	public int getLevel() {
		return levelReq;
	}

	public int[] getOres() {
		return ores;
	}

	public int getProduct() {
		return product;
	}

	public int getRespawn() {
		return respawn;
	}

	public static Ore forId(int object) {
		for (Ore data : values())
			for (int ore : data.getOres())
				if (ore == object)
					return data;
		return null;
	}
}