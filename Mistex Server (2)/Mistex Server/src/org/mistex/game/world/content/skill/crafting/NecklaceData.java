package org.mistex.game.world.content.skill.crafting;


public enum NecklaceData {
	
		GOLD_NECKLACE(-1, 1635, 6, 20),
		SAPPHIRE_NECKLACE(1607, 1656, 22, 55),
		EMERALD_NECKLACE(1605, 1658, 29, 60),
		RUBY_NECKLACE(1603, 1660, 40, 75),
		DIAMOND_NECKLACE(1601, 1662, 56, 90),
		DRAGONSTONE_NECKLACE(1615, 1664, 72, 105),
		ONYX_NECKLACE(6573, 6577, 82, 120);

		int gem, product, reqLevel, exp;

		private NecklaceData(int gem, int product, int reqLevel, int exp) {
			this.gem = gem;
			this.product = product;
			this.reqLevel = reqLevel;
			this.exp = exp;
		}

		public int getItemReq() {
			return gem;
		}

		public int getProduct() {
			return product;
		}

		public int getReqLevel() {
			return reqLevel;
		}

		public int getExp() {
			return exp;
		}

		public static NecklaceData forId(int item) {
			for (NecklaceData data : values()) {
				if (data.getProduct() == item) {
					return data;
				}
			}
			return null;
		}
	}