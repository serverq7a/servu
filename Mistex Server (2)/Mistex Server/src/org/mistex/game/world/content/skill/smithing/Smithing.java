package org.mistex.game.world.content.skill.smithing;

import org.mistex.game.world.content.skill.Skill;
import org.mistex.game.world.content.skill.SkillHandler;
import org.mistex.game.world.content.skill.smithing.SmithingData.Bar;
import org.mistex.game.world.content.skill.smithing.SmithingData.SmithingProducts;
import org.mistex.game.world.event.CycleEvent;
import org.mistex.game.world.event.CycleEventContainer;
import org.mistex.game.world.event.CycleEventHandler;
import org.mistex.game.world.player.Client;
import org.mistex.game.world.player.Player;
import org.mistex.game.world.player.item.ItemAssistant;

/**
 * @author Chex
 */
public class Smithing extends Skill {
	
	public Smithing() {
		super(Skills.SMITHING);
	}

	private static final int HAMMER = 2347;

	private static void refineOre(final Client c, final SmithingProducts data, final Bar bar, final int timestomake) {
		c.getPA().closeAllWindows();
		if (c.isSkilling || !hasRequirements(c, data, bar)) {
			return;
		}
		c.startAnimation(898);
		c.isSkilling = true;
		CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {
			int doAmount = timestomake;
			@Override
			public void execute(CycleEventContainer container) {
				if (!c.isSkilling || doAmount <= 0 || !hasRequirements(c, data, bar)) {
					container.stop();
					return;
				}
				
				if (doAmount != 1) {
					c.startAnimation(898);
				}
				
				c.getItems().deleteItem3(bar.getBar(), data.barsRequired());
				new Smithing().addExp(c, data.getExp(bar));
				new Smithing().giveItem(c, data.getProduct(bar), data.productAmount(), "You make " + fixName(ItemAssistant.getItemName(data.getProduct(bar)).toLowerCase()) + ".");
				c.getPA().refreshSkill(Player.playerSmithing);
				c.turnPlayerTo(c.objectX, c.objectY);
				doAmount--;
			}

			@Override
			public void stop() {
				SkillHandler.resetPlayerVariables(c);
			}
		}, 3);
	}
	
	private static String fixName(String name) {
		String prefix = "a ";
		if (name.startsWith("a") || name.startsWith("i"))
			prefix = "an ";
		prefix += name;
		return prefix;
	}
	
	private static boolean hasRequirements(Client c, SmithingProducts data, Bar bar) {
		if (!c.getItems().playerHasItem(HAMMER, 1)) {
			c.sendMessage("You don't have a hammer with you!");
			c.getPA().closeAllWindows();
			return false;
		}
		
		if (!c.getItems().playerHasItem(bar.getBar(), data.barsRequired())) {
			c.sendMessage("You don't have enough bars to make this item!");
			c.getPA().closeAllWindows();
			return false;
		}

		if (c.playerLevel[Player.playerSmithing] < data.getLevelRequirement(bar)) {
			c.sendMessage("You need a smithing level of " + data.getLevelRequirement(bar) + " to smith that.");
			c.getPA().closeAllWindows();
			return false;
		}
		return true;
	}

	public static void readInput(final Client c, final int product, final int amount) {
		SmithingProducts data = SmithingProducts.forProduct(product);
		if (data == null) {
			c.sendMessage("That item cannot be smithed.");
			return;
		}

		Bar bar = data.getBar(product);
		
		if (hasRequirements(c, data, bar))
			refineOre(c, data, bar, amount);
	}
}
