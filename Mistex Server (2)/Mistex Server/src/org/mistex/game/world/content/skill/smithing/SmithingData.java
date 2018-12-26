package org.mistex.game.world.content.skill.smithing;


/**
 * @author Chex
 */
public class SmithingData {
	public enum Bar {
		BRONZE(2349),
		IRON(2351),
		STEEL(2353),
		MITHRIL(2359),
		ADAMANT(2361),
		RUNE(2363);
		
		private int bar;
		
		private Bar(int bar) {
			this.bar = bar;
		}

		public int getBar() {
			return bar;
		}

		public static Bar forId(int bar) {
			for (Bar data: Bar.values())
				if (data.getBar() == bar)
					return data;
			return null;
		}
	}
	
	public enum BarExp {
		BRONZE(12.5),
		IRON(25),
		STEEL(37.5),
		MITHRIL(50),
		ADAMANT(62.5),
		RUNE(75);
		
		private double exp;
		
		private BarExp(double exp) {
			this.exp = exp;
		}

		private double getExp() {
			return exp;
		}
		
		public static double forBar(int bar) {
			switch (Bar.forId(bar)) {
			case BRONZE:
				return BRONZE.getExp();
			case IRON:
				return IRON.getExp();
			case STEEL:
				return STEEL.getExp();
			case MITHRIL:
				return MITHRIL.getExp();
			case ADAMANT:
				return ADAMANT.getExp();
			case RUNE:
				return RUNE.getExp();
			default:
				return 0;
			}
		}
	}
	
	public enum SmithingProducts {
		AXE(new int[] { 1, 16, 31, 51, 71, 86 }, new int[] { 1351, 1349, 1353, 1355, 1357, 1359 }, 1),
		DAGGER(new int[] { 1, 15, 30, 50, 70, 85 }, new int[] { 1205, 1203, 1207, 1209, 1211, 1213 }, 1),
		MACE(new int[] { 2, 17, 32, 52, 72, 87 }, new int[] { 1422, 1420, 1424, 1428, 1430, 1432 }, 1),
		MED_HELM(new int[] { 3, 18, 33, 51, 73, 88 }, new int[] { 1139, 1137, 1141, 1355, 1145, 1147 }, 1),
		BOLTS(new int[] { 3, 18, 33, 53, 73, 88 }, new int[] { 9375, 9377, 9378, 9379, 9380, 9381 }, 1),
		SWORD(new int[] { 4, 19, 34, 54, 74, 89 }, new int[] { 1277, 1279, 1281, 1355, 1287, 1289 }, 1),
		NAILS(new int[] { 4, 19, 34, 54, 74, 89 }, new int[] { 4819, 4820, 1539, 1355, 4823, 4824 }, 1),
		ARROWTIPS(new int[] { 5, 20, 35, 55, 75, 90 }, new int[] { 39, 40, 41, 42, 43, 44 }, 1),
		SCIMITAR(new int[] { 5, 20, 35, 55, 75, 90 }, new int[] { 1321, 1323, 1325, 1329, 1331, 1333 }, 2),
		LONGSWORD(new int[] { 6, 21, 36, 56, 76, 91 }, new int[] { 1291, 1293, 1295, 1299, 1301, 1303 }, 2),
		KNIVES(new int[] { 7, 22, 37, 57, 77, 92 }, new int[] { 864, 863, 865, 866, 867, 868 }, 1),
		FULL_HELM(new int[] { 7, 22, 37, 57, 77, 92 }, new int[] { 1155, 1153, 1157, 1159, 1161, 1163 }, 2),
		SQ_SHIELD(new int[] { 8, 23, 38, 58, 78, 93 }, new int[] { 1173, 1175, 1177, 1181, 1183, 1185 }, 2),
		WARHAMMER(new int[] { 9, 24, 39, 59, 79, 94 }, new int[] { 1337, 1335, 1339, 1343, 1345, 1347 }, 3),
		BATTLEAXE(new int[] { 10, 25, 40, 60, 80, 95 }, new int[] { 1375, 1363, 1365, 1369, 1371, 1373 }, 3),
		CHAINBODY(new int[] { 11, 26, 41, 61, 81, 96 }, new int[] { 1103, 1101, 1105, 1109, 1111, 1113 }, 3),
		KITESHIELD(new int[] { 12, 27, 42, 62, 82, 97 }, new int[] { 1189, 1191, 1193, 1197, 1199, 1201 }, 3),
		TWO_HAND_SWORD(new int[] { 14, 29, 44, 64, 84, 99 }, new int[] { 1307, 1309, 1311, 1315, 1317, 1319 }, 3),
		PLATELEGS(new int[] { 16, 31, 46, 66, 86, 99 }, new int[] { 1075, 1067, 1069, 1071, 1073, 1079 }, 3),
		PLATESKIRT(new int[] { 16, 31, 46, 66, 86, 99 }, new int[] { 1087, 1081, 1083, 1085, 1091, 1093 }, 3),
		PLATEBODY(new int[] { 18, 33, 48, 68, 88, 99 }, new int[] { 1117, 1115, 1119, 1121, 1123, 1127 }, 5);
		
		private int barsRequired;
		private int[] levelRequirements, products;
		
		private SmithingProducts(int[] levelRequirements, int[] products, int barsRequired) {
			this.levelRequirements = levelRequirements;
			this.products = products;
			this.barsRequired = barsRequired;
		}
		
		public int getExp(Bar bar) {
			return (int) (barsRequired * BarExp.forBar(bar.getBar()));
		}
		
		public int barsRequired() {
			return barsRequired;
		}
		
		public int getLevelRequirement(Bar bar) {
			return levelRequirements[bar.ordinal()];
		}
		
		public int getProduct(Bar bar) {
			return products[bar.ordinal()];
		}
		
		public Bar getBar(int productType) {
			return Bar.values()[productOrdinal(productType)];
		}
		
		public int productAmount() {
			switch (this) {
			case BOLTS:
				return 10;
			case NAILS:
			case ARROWTIPS:
				return 15;
			case KNIVES:
				return 5;
			default:
				return 1;
			}
		}
		
		public static SmithingProducts forProduct(int product) {
			for (SmithingProducts data : values()) {
				for (int productId : data.products) {
					if (productId == product) {
						return data;
					}
				}
			}
			return null;
		}

		
		public static int productOrdinal(int product) {
			for (SmithingProducts data : values()) {
				for (int index = 0; index < data.products.length; index++) {
					if (data.products[index] == product) {
						return index;
					}
				}
			}
			return 0;
		}
	}
}