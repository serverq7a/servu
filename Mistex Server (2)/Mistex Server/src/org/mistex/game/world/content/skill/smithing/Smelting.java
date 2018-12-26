package org.mistex.game.world.content.skill.smithing;

import org.mistex.game.Mistex;
import org.mistex.game.world.content.skill.Skill;
import org.mistex.game.world.content.skill.SkillHandler;
import org.mistex.game.world.player.Client;
import org.mistex.game.world.player.Player;
import org.mistex.game.world.player.item.ItemAssistant;
import org.mistex.game.world.task.Task;

/**
 * @author Chex
 */
public class Smelting extends Skill {
	
	public Smelting() {
		super(Skills.SMITHING);
	}

	private static final int COPPER = 436,
						     TIN =    438,
						     IRON =   440,
						     COAL =   453,
						     MITH =   447,
						     ADDY =   449,
						     RUNE =   451,
						     GOLD =   444,
						     SILVER = 442;

	/**
	 * Contains data to make a bar.
	 */
	public static enum BarData {
		BRONZE_BAR(1, 6, COPPER, TIN, 1, 2349),
		IRON_BAR(15, 12, IRON, -1, -1, 2351),
		SILVER_BAR(20, 13, SILVER, -1, -1, 2355),
		STEEL_BAR(20, 17, IRON, COAL, 1, 2353),
		GOLD_BAR(40, 22, GOLD, -1, -1, 2357),
		MITHRIL_BAR(50, 30, MITH, COAL, 2, 2359),
		ADAMANANT_BAR(70, 37, ADDY, COAL, 3, 2361),
		RUNE_BAR(85, 50, RUNE, COAL, 4, 2363);

		private int level, xp, itemRequired1, itemRequired2, secondItemAmount, product;

		private BarData(int level, int xp, int itemRequired1, int itemRequired2, int secondItemAmount, int product) {
			this.level = level;
			this.xp = xp;
			this.itemRequired1 = itemRequired1;
			this.itemRequired2 = itemRequired2;
			this.secondItemAmount = secondItemAmount;
			this.product = product;
		}

		public static BarData forId(int id) {
			for (BarData bar : BarData.values())
				if (bar.getItemRequired1() == id)
					return bar;
			return null;
		}
		
		public int getLevel() {
			return level;
		}
		
		public int getXp() {
			return xp;
		}
		
		public int getItemRequired1() {
			return itemRequired1;
		}
		
		public int getItemRequired2() {
			return itemRequired2;
		}
		
		public int getProduct() {
			return product;
		}
		
		public int getSecondItemAmount() {
			return secondItemAmount;
		}
	}

	/**
	 * Sends the interface
	 * 
	 * @param c
	 */
	public static void startSmelting(Client c) {
		int[] SMELT_FRAME = { 2405, 2406, 2407, 2409, 2410, 2411, 2412, 2413 };
		int[] SMELT_BARS = { 2349, 2351, 2355, 2353, 2357, 2359, 2361, 2363 };

		for (int j = 0; j < SMELT_FRAME.length; j++) {
			c.getPA().sendFrame246(SMELT_FRAME[j], 150, SMELT_BARS[j]);
		}
		c.getPA().sendFrame126(" ", 7441);
		c.getPA().sendFrame164(2400);
		c.isSkilling = true;
	}
	
	private static String fixName(String name) {
		String prefix = "a ";
		if (name.startsWith("a") || name.startsWith("i"))
			prefix = "an ";
		prefix += name;
		return prefix;
	}

	/**
	 * Sets the amount of bars that can be smelted. (EG. 5,10,28 times)
	 * 
	 * @param c
	 * @param amount
	 */
	public static void doAmount(Client c, int amount, BarData bar) {
		smeltBar(c, bar, amount);
	}

	private static boolean hasItems(Client c, BarData bar) {
		if (bar.getItemRequired1() > 0 && bar.getItemRequired2() > 0) {
			if (!c.getItems().playerHasItem(bar.getItemRequired1()) || !c.getItems().playerHasItem(bar.getItemRequired2())) {
				String name = ItemAssistant.getItemName(bar.getItemRequired1()).toLowerCase();
				String item2name = ItemAssistant.getItemName(bar.getItemRequired2()).toLowerCase();
				if (bar.getSecondItemAmount() == -1)
					c.sendMessage("You need " + name + " and " + fixName(item2name) + " to make this bar.");
				else
					c.sendMessage("You need " + name + " and " + Math.abs(bar.getSecondItemAmount()) + " " + item2name + " to make this bar.");
				c.getPA().removeAllWindows();
				return false;
			}
		}
		return true;
	}

	private static boolean hasItem(Client c, BarData bar) {
		if (bar.getItemRequired2() < 0) {
			String name = ItemAssistant.getItemName(bar.getItemRequired1()).toLowerCase();
			if (!c.getItems().playerHasItem(bar.getItemRequired1())) {
				c.sendMessage("You need " + name + " to make this bar.");
				c.getPA().removeAllWindows();
				return false;
			}
		}
		return true;
	}

	private static boolean hasCoalAmount(Client c, BarData bar) {
		if (bar.getSecondItemAmount() > 0) {
			if (!c.getItems().playerHasItem(bar.getItemRequired2(), bar.getSecondItemAmount())) {
				String name = ItemAssistant.getItemName(bar.getItemRequired2()).toLowerCase();
				c.sendMessage("You need " + name + " and " + bar.getSecondItemAmount() + " coal ores to make this bar.");
				c.getPA().removeAllWindows();
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Main method. Smelting
	 * 
	 * @param c
	 */
	private static void smeltBar(final Client c, final BarData bar, final int amount) {
		if (bar != null) {
			if (c.playerLevel[Player.playerSmithing] < bar.getLevel()) {
				c.sendMessage("You need a smithing level of at least " + bar.getLevel() + " in order smelt this bar.");
				SkillHandler.resetPlayerVariables(c);
				return;
			}
			
			if (!(c.isSkilling && hasItems(c, bar) && hasCoalAmount(c, bar) && hasItem(c, bar))) {
				SkillHandler.resetPlayerVariables(c);
				return;
			}
			
			c.isSkilling = true;

			c.getPA().removeAllWindows();
			c.startAnimation(899);
			
			Mistex.getTaskScheduler().schedule(new Task(5) {
				int doAmount = amount;
				
				@Override
				protected void execute() {
					if (doAmount <= 0 || !c.isSkilling) {
						SkillHandler.resetPlayerVariables(c);
						stop();
						return;
					}
					
					if (c.getItems().playerHasItem(bar.getItemRequired1()) && bar.getItemRequired2() == -1) {
						c.getItems().deleteItem(bar.getItemRequired1(), 1);
					} else if (c.getItems().playerHasItem(bar.getItemRequired1()) && c.getItems().playerHasItem(bar.getItemRequired2(), bar.getSecondItemAmount())) {
						c.getItems().deleteItem(bar.getItemRequired1(), 1);
						c.getItems().deleteItem3(bar.getItemRequired2(), bar.getSecondItemAmount());
					} else {
						SkillHandler.resetPlayerVariables(c);
						stop();
						return;
					}
					
					if (doAmount != 1) {
						c.startAnimation(899);
					}
					
					new Smelting().giveItem(c, bar.getProduct(), 1, "You smelt " + fixName(ItemAssistant.getItemName(bar.getProduct()).toLowerCase()) + ".");
					new Smelting().addExp(c, bar.getXp());
					doAmount--;
					
					if (doAmount > 1 && (!c.getItems().playerHasItem(bar.getItemRequired1()) || !c.getItems().playerHasItem(bar.getItemRequired2(), bar.getSecondItemAmount()))) {
						c.sendMessage("You don't have enough ores to continue!");
						SkillHandler.resetPlayerVariables(c);
						stop();
						return;
					}
				}
			});
		}
	}

	/**
	 * Gets the index from DATA for which bar to smelt
	 */
	public static void getBar(Client c, int i) {
		switch (i) {
		case 15147: // bronze (1)
			doAmount(c, 1, BarData.BRONZE_BAR);
			break;
		case 15146: // bronze (5)
			doAmount(c, 5, BarData.BRONZE_BAR);
			break;
		case 10247: // bronze (10)
			doAmount(c, 10, BarData.BRONZE_BAR);
			break;
		case 9110:// bronze (X)
			doAmount(c, 28, BarData.BRONZE_BAR);
			break;

		case 15151: // iron (1)
			doAmount(c, 1, BarData.IRON_BAR);
			break;
		case 15150: // iron (5)
			doAmount(c, 5, BarData.IRON_BAR);
			break;
		case 15149: // iron (10)
			doAmount(c, 10, BarData.IRON_BAR);
			break;
		case 15148:// Iron (X)
			doAmount(c, 28, BarData.IRON_BAR);
			break;

		case 15159: // Steel (1)
			doAmount(c, 1, BarData.STEEL_BAR);
			break;
		case 15158: // Steel (5)
			doAmount(c, 5, BarData.STEEL_BAR);
			break;
		case 15157: // Steel (10)
			doAmount(c, 10, BarData.STEEL_BAR);
			break;
		case 15156:// Steel (X)
			doAmount(c, 28, BarData.STEEL_BAR);
			break;

		case 29017: // mith (1)
			doAmount(c, 1, BarData.MITHRIL_BAR);
			break;
		case 29016: // mith (5)
			doAmount(c, 5, BarData.MITHRIL_BAR);
			break;
		case 24253: // mith (10)
			doAmount(c, 10, BarData.MITHRIL_BAR);
			break;
		case 16062:// Mith (X)
			doAmount(c, 28, BarData.MITHRIL_BAR);
			break;

		case 29022: // Addy (1)
			doAmount(c, 1, BarData.ADAMANANT_BAR);
			break;
		case 29020: // Addy (5)
			doAmount(c, 5, BarData.ADAMANANT_BAR);
			break;
		case 29019: // Addy (10)
			doAmount(c, 10, BarData.ADAMANANT_BAR);
			break;
		case 29018:// Addy (X)
			doAmount(c, 28, BarData.ADAMANANT_BAR);
			break;

		case 29026: // RUNE (1)
			doAmount(c, 1, BarData.RUNE_BAR);
			break;
		case 29025: // RUNE (5)
			doAmount(c, 5, BarData.RUNE_BAR);
			break;
		case 29024: // RUNE (10)
			doAmount(c, 10, BarData.RUNE_BAR);
			break;
		case 29023:// Rune (X)
			doAmount(c, 28, BarData.RUNE_BAR);
			break;

		case 15155: // SILVER (1)
			doAmount(c, 1, BarData.SILVER_BAR);
			break;
		case 15154: // SILVER (5)
			doAmount(c, 5, BarData.SILVER_BAR);
			break;
		case 15153: // SILVER (10)
			doAmount(c, 10, BarData.SILVER_BAR);
			break;
		case 15152: // SILVER (x)
			doAmount(c, 28, BarData.SILVER_BAR);
			break;

		case 15163: // Gold (1)
			doAmount(c, 1, BarData.GOLD_BAR);
			break;
		case 15162: // Gold (5)
			doAmount(c, 5, BarData.GOLD_BAR);
			break;
		case 15161: // Gold (10)
			doAmount(c, 10, BarData.GOLD_BAR);
			break;
		case 15160: // Gold (x)
			doAmount(c, 28, BarData.GOLD_BAR);
			break;
		}
	}
}