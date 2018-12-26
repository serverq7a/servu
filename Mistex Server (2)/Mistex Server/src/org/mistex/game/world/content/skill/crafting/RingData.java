package org.mistex.game.world.content.skill.crafting;


public enum RingData {
		GOLD_RING(-1, 1635, 5, 15),
		SAPPHIRE_RING(1607, 1637, 20, 40),
		EMERALD_RING(1605, 1639, 27, 55),
		RUBY_RING(1603, 1641, 34, 70),
		DIAMOND_RING(1601, 1643, 43, 85),
		DRAGONSTONE_RING(1615, 1645, 75, 100),
		ONYX_RING(6573, 6583, 85, 115);

		int itemReq, product, reqLevel, exp;

		private RingData(int itemReq, int product, int reqLevel, int exp) {
			this.itemReq = itemReq;
			this.product = product;
			this.reqLevel = reqLevel;
			this.exp = exp;
		}

		public int getItemReq() {
			return itemReq;
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

		public static RingData forId(int item) {
			for (RingData data : values()) {
				if (data.getProduct() == item) {
					return data;
				}
			}
			return null;
		}
	}