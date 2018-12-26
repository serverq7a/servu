package org.mistex.game.world.content.skill;

import java.util.ArrayList;

import org.mistex.game.world.content.achievement.AchievementHandler;
import org.mistex.game.world.content.achievement.AchievementList;
import org.mistex.game.world.content.skill.SkillingTasks.TaskData;
import org.mistex.game.world.player.Client;
import org.mistex.game.world.player.item.Item;

public class Skill extends SkillHandler {
	
	public static ArrayList<Integer> unavailableObjects = new ArrayList<Integer>();
	
	private Skills skill;
	
	public Skill(Skills skill) {
		this.skill = skill;
	}

	public static enum Skills {
		COOKING(7, 40),
		WOODCUTTING(8, 30),
		FLETCHING(9, 30),
		FISHING(10, 40),
		FIREMAKING(11, 30),
		CRAFTING(12, 40),
		SMITHING(13, 50),
		MINING(14, 50),
		HERBLORE(15, 20),
		AGILITY(16, 35),
		THIEVING(17, 20),
		FARMING(19, 20),
		RUNECRAFTING(20, 45),
		HUNTER(22, 25);
		
		private int id, multiplier;
		
		private Skills(int id, int multiplier) {
			this.id = id;
			this.multiplier = multiplier;
		}
		
		public int getId() {
			return id;
		}
		
		public int getMultiplier() {
			return multiplier;
		}
	}
	
	public void addExp(Client c, double exp) {
		c.getPA().addSkillXP((int) (exp * skill.getMultiplier()), skill.getId());
	}
	
	public void giveItem(Client c, int item, int amount, String message) {
		
		c.getItems().addItem(item, amount);
		
		if (message != null)
			c.sendMessage(message);
		
		if (c.skillTaskName != null) {
			TaskData task = TaskData.forName(c.skillTaskName);
			c.sendMessage(item + ", " + task.getItem());
			int itemShift = 0;
			
			if (Item.itemIsNote[task.getItem()] && !Item.itemIsNote[task.getItem() - 1]) {
				itemShift--;
			}
			
			if (task.getItem() + itemShift == item) {
				c.skillTaskAmount += 1;
			}
		}
		
		if (item == 1515) {
			c.yewsCut += 1;
			if (c.yewsCut == 1)
				c.sendMessage("@war@You have started the achievement: Nazi ");
			if (c.yewsCut == 100)
				AchievementHandler.activateAchievement(c, AchievementList.NAZI);
		}
	}

	public int skillId() {
		return skill.getId();
	}
}