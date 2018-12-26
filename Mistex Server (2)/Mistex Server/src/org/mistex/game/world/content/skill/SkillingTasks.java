package org.mistex.game.world.content.skill;

import java.util.ArrayList;
import java.util.List;

import org.mistex.game.MistexUtility;
import org.mistex.game.world.content.interfaces.InterfaceText;
import org.mistex.game.world.content.interfaces.impl.QuestInterface;
import org.mistex.game.world.player.Client;
import org.mistex.game.world.player.Player;
import org.mistex.game.world.player.PlayerSave;
import org.mistex.game.world.player.item.ItemAssistant;

/**
 * Skilling Tasks
 * @author Omar | Play Boy
 *
 */
public class SkillingTasks {
	
	/**
	 * Coin reward for task completion
	 */
	public static final int Easy_REWARD = 250000, MEDIUM_REWARD = 500000, Hard_REWARD = 750000;
	
	/**
	 * XP given for task completion
	 */
	
	public static final int Easy_XP = 50000, MEDIUM_XP = 125000, Hard_XP = 200000;
	
	/**
	 * Easy task list
	 */
	public static final List<TaskData> EasyTasks = new ArrayList<TaskData>();
	
	/**
	 * Medium task list
	 */
	public static final List<TaskData> mediumTasks = new ArrayList<TaskData>();
	
	/**
	 * Hard task list
	 */
	public static final List<TaskData> HardTasks = new ArrayList<TaskData>();
	
	static {
		for (TaskData data : TaskData.values()) {
			switch (data.getDifficulty()) {
			case Medium:
				mediumTasks.add(data);
				break;
			case Hard:
				HardTasks.add(data);
				break;
			default:
				EasyTasks.add(data);
				break;
			}
		}
	}
	
	public static enum TaskType {
		Easy,
		Medium,
		Hard;
	}
	
	public static enum TaskData {
		  SHRIMPS_ONE(TaskType.Easy, "Fishing", 318, 100, Player.playerFishing),
		  ANCHOVIES_ONE(TaskType.Easy, "Fishing", 320, 100, Player.playerFishing),
		  LOBSTERS_ONE(TaskType.Easy, "Fishing", 378, 100, Player.playerFishing),
		  TUNA_ONE(TaskType.Easy, "Fishing", 360, 100, Player.playerFishing),
		  SWORDFISH_ONE(TaskType.Easy, "Fishing", 372, 100, Player.playerFishing),
		  SHARKS_ONE(TaskType.Easy, "Fishing", 384, 100, Player.playerFishing),
		  SEA_TURTLES_ONE(TaskType.Easy, "Fishing", 396, 100, Player.playerFishing),
		  MANTA_RAYS_ONE(TaskType.Easy, "Fishing", 390, 100, Player.playerFishing),
		  AIR_RUNES_ONE(TaskType.Easy, "Runecrafting", 556, 200, Player.playerRunecrafting),
		  MIND_RUNES_ONE(TaskType.Easy, "Runecrafting", 558, 200, Player.playerRunecrafting),
		  WATER_RUNES_ONE(TaskType.Easy, "Runecrafting", 555, 200, Player.playerRunecrafting),
		  EARTH_RUNES_ONE(TaskType.Easy, "Runecrafting", 557, 200, Player.playerRunecrafting),
		  FIRE_RUNES_ONE(TaskType.Easy, "Runecrafting", 554, 200, Player.playerRunecrafting),
		  BODY_RUNES_ONE(TaskType.Easy, "Runecrafting", 559, 200, Player.playerRunecrafting),
		  CHAOS_RUNES_ONE(TaskType.Easy, "Runecrafting", 562, 200, Player.playerRunecrafting),
		  LAW_RUNES_ONE(TaskType.Easy, "Runecrafting", 563, 200, Player.playerRunecrafting),
		  NATURE_RUNES_ONE(TaskType.Easy, "Runecrafting", 561, 200, Player.playerRunecrafting),
		  DEATH_RUNES_ONE(TaskType.Easy, "Runecrafting", 560, 200, Player.playerRunecrafting), 
		  LOGS_ONE(TaskType.Easy, "Woodcutting", 1512, 100, Player.playerWoodcutting),
		  OAK_LOGS_ONE(TaskType.Easy, "Woodcutting", 1522, 150, Player.playerWoodcutting),
		  WILLOW_LOGS_ONE(TaskType.Easy, "Woodcutting", 1520, 150, Player.playerWoodcutting),
		  MAPLE_LOGS_ONE(TaskType.Easy, "Woodcutting", 1518, 150, Player.playerWoodcutting),
		  YEW_LOGS_ONE(TaskType.Easy, "Woodcutting", 1516, 200, Player.playerWoodcutting),
		  MAGIC_LOGS_ONE(TaskType.Easy, "Woodcutting", 1514, 200, Player.playerWoodcutting), 
		  COPPER_ORE_ONE(TaskType.Easy, "Mining", 437, 250, Player.playerMining),
		  TIN_ORE_ONE(TaskType.Easy, "Mining", 439, 250, Player.playerMining),
		  IRON_ORE_ONE(TaskType.Easy, "Mining", 441, 150, Player.playerMining),
		  COAL_ONE(TaskType.Easy, "Mining", 454, 200, Player.playerMining),
		  GOLD_ORE_ONE(TaskType.Easy, "Mining", 445, 150, Player.playerMining),
		  MITHRIL_ORE_ONE(TaskType.Easy, "Mining", 448, 100, Player.playerMining),
		  ADAMANTITE_ORE_ONE(TaskType.Easy, "Mining", 450, 100, Player.playerMining),
		  RUNITE_ORE_ONE(TaskType.Easy, "Mining", 452, 50, Player.playerMining),

		  SHRIMPS_TWO(TaskType.Medium, "Fishing", 318, 300, Player.playerFishing),
		  ANCHOVIES_TWO(TaskType.Medium, "Fishing", 320, 300, Player.playerFishing),
		  LOBSTERS_TWO(TaskType.Medium, "Fishing", 378, 300, Player.playerFishing),
		  TUNA_TWO(TaskType.Medium, "Fishing", 360, 300, Player.playerFishing),
		  SWORDFISH_TWO(TaskType.Medium, "Fishing", 372, 300, Player.playerFishing),
		  SHARKS_TWO(TaskType.Medium, "Fishing", 384, 300, Player.playerFishing),
		  SEA_TURTLES_TWO(TaskType.Medium, "Fishing", 396, 300, Player.playerFishing),
		  MANTA_RAYS_TWO(TaskType.Medium, "Fishing", 390, 300, Player.playerFishing),
		  AIR_RUNES_TWO(TaskType.Medium, "Runecrafting", 556, 500, Player.playerRunecrafting),
		  MIND_RUNES_TWO(TaskType.Medium, "Runecrafting", 558, 500, Player.playerRunecrafting),
		  WATER_RUNES_TWO(TaskType.Medium, "Runecrafting", 555, 500, Player.playerRunecrafting),
		  EARTH_RUNES_TWO(TaskType.Medium, "Runecrafting", 557, 500, Player.playerRunecrafting),
		  FIRE_RUNES_TWO(TaskType.Medium, "Runecrafting", 554, 500, Player.playerRunecrafting),
		  BODY_RUNES_TWO(TaskType.Medium, "Runecrafting", 559, 500, Player.playerRunecrafting),
		  CHAOS_RUNES_TWO(TaskType.Medium, "Runecrafting", 562, 500, Player.playerRunecrafting),
		  LAW_RUNES_TWO(TaskType.Medium, "Runecrafting", 563, 500, Player.playerRunecrafting),
		  NATURE_RUNES_TWO(TaskType.Medium, "Runecrafting", 561, 500, Player.playerRunecrafting),
		  DEATH_RUNES_TWO(TaskType.Medium, "Runecrafting", 560, 500, Player.playerRunecrafting), 
		  LOGS_TWO(TaskType.Medium, "Woodcutting", 1512, 250, Player.playerWoodcutting),
		  OAK_LOGS_TWO(TaskType.Medium, "Woodcutting", 1522, 300, Player.playerWoodcutting),
		  WILLOW_LOGS_TWO(TaskType.Medium, "Woodcutting", 1520, 300, Player.playerWoodcutting),
		  MAPLE_LOGS_TWO(TaskType.Medium, "Woodcutting", 1518, 300, Player.playerWoodcutting),
		  YEW_LOGS_TWO(TaskType.Medium, "Woodcutting", 1516, 300, Player.playerWoodcutting),
		  MAGIC_LOGS_TWO(TaskType.Medium, "Woodcutting", 1514, 300, Player.playerWoodcutting), 
		  COPPER_ORE_TWO(TaskType.Medium, "Mining", 437, 250, Player.playerMining),
		  TIN_ORE_TWO(TaskType.Medium, "Mining", 439, 250, Player.playerMining),
		  IRON_ORE_TWO(TaskType.Medium, "Mining", 441, 250, Player.playerMining),
		  COAL_TWO(TaskType.Medium, "Mining", 454, 250, Player.playerMining),
		  GOLD_ORE_TWO(TaskType.Medium, "Mining", 445, 250, Player.playerMining),
		  MITHRIL_ORE_TWO(TaskType.Medium, "Mining", 448, 200, Player.playerMining),
		  ADAMANTITE_ORE_TWO(TaskType.Medium, "Mining", 450, 200, Player.playerMining),
		  RUNITE_ORE_TWO(TaskType.Medium, "Mining", 452, 150, Player.playerMining),
		  

		  SHRIMPS_THREE(TaskType.Hard, "Fishing", 318, 500, Player.playerFishing),
		  ANCHOVIES_THREE(TaskType.Hard, "Fishing", 320, 500, Player.playerFishing),
		  LOBSTERS_THREE(TaskType.Hard, "Fishing", 378, 600, Player.playerFishing),
		  TUNA_THREE(TaskType.Hard, "Fishing", 360, 500, Player.playerFishing),
		  SWORDFISH_THREE(TaskType.Hard, "Fishing", 372, 500, Player.playerFishing),
		  SHARKS_THREE(TaskType.Hard, "Fishing", 384, 600, Player.playerFishing),
		  SEA_TURTLES_THREE(TaskType.Hard, "Fishing", 396, 600, Player.playerFishing),
		  MANTA_RAYS_THREE(TaskType.Hard, "Fishing", 390, 550, Player.playerFishing),
		  AIR_RUNES_THREE(TaskType.Hard, "Runecrafting", 556, 650, Player.playerRunecrafting),
		  MIND_RUNES_THREE(TaskType.Hard, "Runecrafting", 558, 650, Player.playerRunecrafting),
		  WATER_RUNES_THREE(TaskType.Hard, "Runecrafting", 555, 650, Player.playerRunecrafting),
		  EARTH_RUNES_THREE(TaskType.Hard, "Runecrafting", 557, 650, Player.playerRunecrafting),
		  FIRE_RUNES_THREE(TaskType.Hard, "Runecrafting", 554, 650, Player.playerRunecrafting),
		  BODY_RUNES_THREE(TaskType.Hard, "Runecrafting", 559, 650, Player.playerRunecrafting),
		  CHAOS_RUNES_THREE(TaskType.Hard, "Runecrafting", 562, 650, Player.playerRunecrafting),
		  LAW_RUNES_THREE(TaskType.Hard, "Runecrafting", 563, 650, Player.playerRunecrafting),
		  NATURE_RUNES_THREE(TaskType.Hard, "Runecrafting", 561, 650, Player.playerRunecrafting),
		  DEATH_RUNES_THREE(TaskType.Hard, "Runecrafting", 560, 650, Player.playerRunecrafting), 
		  LOGS_THREE(TaskType.Hard, "Woodcutting", 1512, 400, Player.playerWoodcutting),
		  OAK_LOGS_THREE(TaskType.Hard, "Woodcutting", 1522, 500, Player.playerWoodcutting),
		  WILLOW_LOGS_THREE(TaskType.Hard, "Woodcutting", 1520, 500, Player.playerWoodcutting),
		  MAPLE_LOGS_THREE(TaskType.Hard, "Woodcutting", 1518, 500, Player.playerWoodcutting),
		  YEW_LOGS_THREE(TaskType.Hard, "Woodcutting", 1516, 500, Player.playerWoodcutting),
		  MAGIC_LOGS_THREE(TaskType.Hard, "Woodcutting", 1514, 500, Player.playerWoodcutting), 
		  COPPER_ORE_THREE(TaskType.Hard, "Mining", 437, 500, Player.playerMining),
		  TIN_ORE_THREE(TaskType.Hard, "Mining", 439, 500, Player.playerMining),
		  IRON_ORE_THREE(TaskType.Hard, "Mining", 441, 500, Player.playerMining),
		  COAL_THREE(TaskType.Hard, "Mining", 454, 500, Player.playerMining),
		  GOLD_ORE_THREE(TaskType.Hard, "Mining", 445, 450, Player.playerMining),
		  MITHRIL_ORE_THREE(TaskType.Hard, "Mining", 448, 450, Player.playerMining),
		  ADAMANTITE_ORE_THREE(TaskType.Hard, "Mining", 450, 450, Player.playerMining),
		  RUNITE_ORE_THREE(TaskType.Hard, "Mining", 452, 250, Player.playerMining);
	
	private TaskType taskDifficulty;
	private String taskType;
	private int taskItemID;
	private int taskAmount;
	private int skillID;
	
	private TaskData(TaskType taskDifficulty, String taskType, int taskItemID, int taskAmount, int skillID) {
		this.taskDifficulty = taskDifficulty;
		this.taskType = taskType;
		this.taskItemID = taskItemID;
		this.taskAmount = taskAmount;
		this.skillID = skillID;
	}
	
	public TaskType getDifficulty() {
		return taskDifficulty;
	}
	
	public String getTaskType() {
		return taskType;
	}
	
	public int getItem() {
		return taskItemID;
	}
	
	public int getAmount() {
		return taskAmount;
	}
	
	public int getSkillID() {
		return skillID;
	}
	
	public int getRewardAmount() {
		switch (taskDifficulty) {
		case Medium:
			return MEDIUM_REWARD;
		case Hard:
			return Hard_REWARD;
		default:
			return Easy_REWARD;
		}
	}
	
	public int getRewardXP() {
		switch (taskDifficulty) {
		case Medium:
			return MEDIUM_XP;
		case Hard:
			return Hard_XP;
		default:
			return Easy_XP;
		}
	}
	
	public static TaskData forName(String name) {
		for (TaskData data : values()) {
			if (data.name().equals(name)) {
				return data;
			}
		}
		return null;
	}
}
	
	/**
	 * Gets the task description
	 * @param c
	 */
	public static void getTaskDescription(Client player) {
		TaskData task = TaskData.forName(player.skillTaskName);
		if (task == null) {
			player.sendMessage("@red@You do not have a skill task assigned.");
			return;
		}
		player.getPA().sendStatement("Obtain @or3@"+MistexUtility.format(task.getAmount())+"</col> of  @or3@"+ItemAssistant.getItemName(task.getItem())+"</col> by @or3@"+task.getTaskType()+"</col>.");
		player.sendMessage("You need to obtain @or3@"+MistexUtility.format(task.getAmount())+"</col> of  @or3@"+ItemAssistant.getItemName(task.getItem())+"</col> by @or3@"+task.getTaskType()+"</col>.");
	}
	
	/**
	 * Checks if player can get task
	 * @param player
	 * @return
	 */
	public static boolean canGetTask(Client player) {
		if (player.skillTaskName != null) {
			player.getPA().sendStatement("You already have a task!");
			return false;
		}
		return true;
	}
	
	/**
	 * Gives player a task
	 * @param player
	 */
	public static void giveTask(Client player, TaskType difficulty) {
		if (!canGetTask(player)) {
			return;
		}
		TaskData data = null;
		switch (difficulty) {
		case Medium:
			data = mediumTasks.get(MistexUtility.random(mediumTasks.size() - 1));
			break;
		case Hard:
			data = HardTasks.get(MistexUtility.random(HardTasks.size() - 1));
			break;
		default:
			data = EasyTasks.get(MistexUtility.random(EasyTasks.size() - 1));
			break;
		}
		player.getPA().closeAllWindows();
		player.skillTaskName = data.name();
		player.skillTaskAmount = 0;
		getTaskDescription(player);
		PlayerSave.saveGame(player);
	}
	
	/**
	 * Checks if player is complete task
	 * @param player
	 */
	public static void checkComplete(Client player) {
		if (player.skillTaskName == null) {
			player.sendMessage("@or3@You do not have a task!");
			return;
		}
		TaskData task = TaskData.forName(player.skillTaskName);
		if (player.skillTaskAmount < task.getAmount()) {
			String name = ItemAssistant.getItemName(task.getItem()).toLowerCase();
			player.getPA().sendStatement("You still need " + (task.getAmount() - player.skillTaskAmount) + " " + name + (name.endsWith("s") ? "" : "s") + " left to collect.");
			return;
		}
		if (!player.getItems().playerHasItem(task.getItem(), task.getAmount())) {
			player.getPA().sendStatement("You need all the items you have collected in order to complete the task.");
			return;
		}
		player.getItems().deleteItem(task.getItem(), task.getAmount());
		player.getItems().addItem(995, task.getRewardAmount());
		player.skillingPoints += task.getDifficulty().equals(TaskType.Hard) ? 40 : task.getDifficulty().equals(TaskType.Medium) ? 20 : 10;
		player.skillingPoints += MistexUtility.random(10) + 1;
		player.getPA().sendStatement("You have completed your skilling task!");
		player.sendMessage("@or3@You were rewarded with "+MistexUtility.format(task.getRewardAmount())+" coins and "+MistexUtility.format(task.getRewardXP())+" XP in "+task.getTaskType()+".");
		player.getPA().addSkillXP(task.getRewardXP(), task.getSkillID());
		endTask(player);
	}
	
	/**
	 * Ends the player's task
	 * @param player
	 */
	public static void endTask(Client player) {
		player.skillTaskName = null;
		player.skillTaskAmount = -1;
		PlayerSave.saveGame(player);
		player.sendMessage("@or3@Your task has been ended. Please talk to Ed Wood at Edgeville for a new one.");
	}
	
	/**
	 * Cancels the player task
	 * @param player
	 */
	public static void cancelTask(Client player) {
		TaskData task = TaskData.forName(player.skillTaskName);
		if (player.skillTaskName == null && player.skillTaskAmount == -1) {
			player.getDH().sendDialogues(356, 5964);
		} else if (task.getDifficulty().equals(TaskType.Easy)) {
			if (player.getItems().playerHasItem(995, 100000)) {
				player.getItems().deleteItem(995, 100000);
				endTask(player);
			} else {
				player.getDH().sendDialogues(357, 5964);
			}
		} else if (task.getDifficulty().equals(TaskType.Medium)) {
			if (player.getItems().playerHasItem(995, 250000)) {
				player.getItems().deleteItem(995, 250000);
				endTask(player);
			} else {
				player.getDH().sendDialogues(358, 5964);
			}
		} else if (task.getDifficulty().equals(TaskType.Hard)) {
			if (player.getItems().playerHasItem(995, 500000)) {
				player.getItems().deleteItem(995, 500000);
				endTask(player);
			} else {
				player.getDH().sendDialogues(369, 5964);
			}
		}
	}
	
	/**
	 * Handles the task panel
	 * @param player
	 */
	public static void handlePanel(Client player) {
		TaskData task = TaskData.forName(player.skillTaskName);
	 	player.getPA().showInterface(8134);
	 	InterfaceText.writeText(new QuestInterface(player));
		player.getPA().sendFrame126("Skilling Task Panel", 8144);
		if (player.skillTaskName == null) {
			player.getPA().sendFrame126("You do not have a task! ", 8145);
			player.getPA().sendFrame126("", 8146);
			player.getPA().sendFrame126("Speak to Ed Wood located at Edgeville.", 8147);
		} else {
			player.getPA().sendFrame126("@bla@Name: @or3@"+MistexUtility.capitalize(task.name().toLowerCase().replaceAll("_", " ")), 8145);
			player.getPA().sendFrame126("@bla@Difficulty: @or3@"+task.getDifficulty(), 8146);
			player.getPA().sendFrame126("@bla@Item: @or3@"+ItemAssistant.getItemName(task.getItem())+"s", 8147);
			player.getPA().sendFrame126("@bla@Amount: @or3@"+MistexUtility.format(task.getAmount()), 8148);
			player.getPA().sendFrame126("@bla@Completed: @or3@"+player.skillTaskAmount, 8149);
			player.getPA().sendFrame126("@bla@Skill: @or3@"+task.getTaskType(), 8150);
		}
	}
}
