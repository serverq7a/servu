package org.mistex.game.world.content.skill.crafting;


public enum AmuletData {
	
		GOLD_AMULET(-1, 1673, 1692, 8, 30),
		SAPPHIRE_AMULET(1607, 1675, 1694, 24, 65),
		EMERALD_AMULET(1605, 1677, 1696, 31, 70),
		RUBY_AMULET(1603, 1679, 1698, 50, 85),
		DIAMOND_AMULET(1601, 1681, 1700, 70, 100),
		DRAGONSTONE_AMULET(1615, 1683, 1702, 80, 150),
		ONYX_AMULET(6573, 6585, 6585, 90, 165);

		int gem, product, stringedProduct, reqLevel, exp;

		private AmuletData(int gem, int product, int stringedProduct, int reqLevel, int exp) {
			this.gem = gem;
			this.product = product;
			this.stringedProduct = stringedProduct;
			this.reqLevel = reqLevel;
			this.exp = exp;
		}

		public int getItemReq() {
			return gem;
		}

		public int getProduct() {
			return product;
		}
		
		public int getStringedProduct() {
			return stringedProduct;
		}

		public int getReqLevel() {
			return reqLevel;
		}

		public int getExp() {
			return exp;
		}

		public static AmuletData forId(int item) {
			for (AmuletData data : values()) {
				if (data.getProduct() == item) {
					return data;
				}
			}
			return null;
		}
	}