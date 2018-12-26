package org.mistex.game.world.content.skill.crafting;

import org.mistex.game.world.content.skill.Skill;
import org.mistex.game.world.player.Client;
import org.mistex.game.world.player.item.ItemAssistant;

public class TipMaker extends Skill {

	public TipMaker() {
		super(Skills.FLETCHING);
	}


	private static int[][] tipArray = { 
		{ 1609, 45, 10 }, //opal
		{ 1611, 9187, 15 }, //jade
		{ 1613, 9188, 20 }, //red topaz
		{ 1607, 9189, 30 }, //sapphire
		{ 1605, 9190, 40 }, //emerald
		{ 1603, 9191, 50 }, //ruby
		{ 1601, 9192, 60 }, //diamond
		{ 1615, 9193, 70 }, //dragon
		{ 6573, 9194, 80 }, //onyx
		};
	

	public static void makeTip(final Client c, final int useWith, final int itemUsed) {
		for (int i = 0; i < tipArray.length; i++) {
			if (useWith == 1755 && itemUsed == tipArray[i][0] || useWith == tipArray[i][0] && itemUsed == 1755) {
				c.getItems().deleteItem2(tipArray[i][0], 1);
				new JewelryMaking().giveItem(c, tipArray[i][1], 10, "You create 10 " + ItemAssistant.getItemName(tipArray[i][1]) + ".");
				new JewelryMaking().addExp(c, tipArray[i][2]);
			}
		}
	}

}
