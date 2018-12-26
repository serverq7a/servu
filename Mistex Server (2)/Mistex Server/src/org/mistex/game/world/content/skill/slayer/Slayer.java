package org.mistex.game.world.content.skill.slayer;

import java.util.ArrayList;

import org.mistex.game.MistexUtility;
import org.mistex.game.world.content.interfaces.InterfaceText;
import org.mistex.game.world.content.interfaces.impl.InformationTab;
import org.mistex.game.world.player.Client;

public class Slayer {

    public static final int EASY_TASK = 1, MEDIUM_TASK = 2, HARD_TASK = 3;

    public static ArrayList<Integer> easyTask = new ArrayList<Integer>();
    public static ArrayList<Integer> mediumTask = new ArrayList<Integer>();
    public static ArrayList<Integer> hardTask = new ArrayList<Integer>();

    private Client c;


    public Slayer(Client c) {
        this.c = c;
    }

    public enum Task {
        ABYSSAL_DEMON(1615, 85,3, "Slayer Tower", 3500),
        BANSHEE(1612, 15, 2, "Slayer Tower", 200),
        GIANT_BAT(78, 1, 1, "TAverly Dungeon", 150),
        BLACK_DEMON(84, 50, 2, "Taverly Dungeon", 1200),
        BLOODVELD(1618, 50, 2, "Slayer Tower", 1700),
        BLUE_DRAGON(55, 35, 2, "Taverly Dungeon", 1300),
        BRONZE_DRAGON(1590, 70, 3, "Brimhaven Dungeon", 2500),
        CRAWLING_HAND(1648, 5, 1,"Slayer Tower", 100),
        DARK_BEAST(2783, 90, 3, "Slayer Tower", 3000),
        DUST_DEVIL(1624, 65, 2, "Slayer Tower", 1700),
        EARTH_WARRIOR(124, 1, 1, "Edgeville Dungeon", 600),
        GARGOYLE(1610, 75, 3, "Slayer Tower", 1200),
        GHOST(103, 1, 1, "Taverly Dungeon", 200),
        GREATER_DEMON(83, 55, 2, "Brimhaven Dungeon", 1000),
        GREATER_DEMON2(83, 55, 3, "Brimhaven Dungeon", 1000),
        GREEN_DRAGON(941, 45, 2, "The Wilderness", 850),
        GREEN_DRAGON2(941, 45, 3, "The Wilderness", 850),
        HELLHOUND(49, 1, 3, "Taverly Dungeon", 250),
        HILL_GIANT(117, 1, 2, "Edgeville Dungeon", 450),
        INFERNAL_MAGE(1643, 45, 2, "Slayer Tower", 650),
        IRON_DRAGON(1591, 1, 3, "Brimhaven Dungeon", 1200),
        LESSER_DEMON(82, 1, 2, "Karamja Dungeon", 650),
        MOSS_GIANT(112, 1, 2, "Brimhaven Dungeon", 550),
        NECHRYAELS(1613, 80, 3, "Slayer Tower", 1500),
        RED_DRAGON(53, 60, 2, "Brimhaven Dungeon", 1250),
        RED_DRAGON2(53, 60, 3, "Brimhaven Dungeon", 1250),
        SKELETON(92, 1, 1, "Taverly Dungeon", 250),
        STEEL_DRAGON(1591, 80, 3, "Brimhaven Dungeon", 1300),
        STEEL_DRAGON2(1591, 80, 2, "Brimhaven Dungeon", 1300),
        FROST_DRAGONS(10775, 90, 3, "Frost Cave", 4000),
        TZ_TOK_JAD(2745, 95, 3, "Tzhaar Cave", 8000),
        CORPOREAL_BEAST(8133, 95, 3, "Corp Arena", 15000);

        private int npcId, levelReq, diff, experience;
        private String location;

        private Task(int npcId, int levelReq, int difficulty, String location, int experience) {
            this.npcId = npcId;
            this.levelReq = levelReq;
            this.location = location;
            this.diff = difficulty;
            this.experience = experience;
        }

        public int getNpcId() {
            return npcId;
        }


        public int getLevelReq() {
            return levelReq;
        }


        public int getDifficulty() {
            return diff;
        }

        public String getLocation1() {
            return location;
        }
        
        public int getExperience() {
        	return experience;
        }
        
        public static Task forId(int npc) {
        	for (Task task : values()) {
        		if (task.getNpcId() == npc) {
        			return task;
        		}
        	}
        	return null;
        }
    }

    public void resizeTable(int difficulty) {
        if (easyTask.size() > 0 || hardTask.size() > 0 || mediumTask.size() > 0) {
            easyTask.clear();
            mediumTask.clear();
            hardTask.clear();
        }
        for (Task slayerTask : Task.values()) {
            if (slayerTask.getDifficulty() == EASY_TASK) {
                if (c.playerLevel[18] >= slayerTask.getLevelReq())
                    easyTask.add(slayerTask.getNpcId());
                continue;
            } else if (slayerTask.getDifficulty() == MEDIUM_TASK) {
                if (c.playerLevel[18] >= slayerTask.getLevelReq())
                    mediumTask.add(slayerTask.getNpcId());
                continue;
            } else if (slayerTask.getDifficulty() == HARD_TASK) {
                if (c.playerLevel[18] >= slayerTask.getLevelReq()) {
                    hardTask.add(slayerTask.getNpcId());
                }
                continue;
            }
        }
    }

    public int getRequiredLevel(int npcId) {
        for (Task task : Task.values())
            if (task.npcId == npcId)
                return task.levelReq;
        return -1;
    }

    public String getLocation(int npcId) {
        for (Task task : Task.values())
            if (task.npcId == npcId)
                return task.location;
        return "";
    }

    public boolean isSlayerNpc(int npcId) {
        for (Task task : Task.values()) {
            if (task.getNpcId() == npcId)
                return true;
        }
        return false;
    }


    public boolean isSlayerTask(int npcId) {
        if (isSlayerNpc(npcId)) {
            if (c.slayerTask == npcId) {
                return true;
            }
        }
        return false;
    }

    public int getDifficulty(int npcId) {
        for (Task task : Task.values())
            if (task.npcId == npcId)
                return task.getDifficulty();
        return 1;
    }

    public String getTaskName(int npcId) {
        for (Task task : Task.values())
            if (task.npcId == npcId)
                return task.name().replaceAll("_", " ").replaceAll("2", "").toLowerCase();
        return "";
    }

    public int getTaskId(String name) {
        for (Task task : Task.values())
            if (task.name() == name)
                return task.npcId;
        return -1;
    }
    
    public int getTaskExperience(int experience) {
    	Task task = Task.forId(experience);
    	if (task == null)
    		return 0;
    	return task.getExperience();
    }
   

    public boolean hasTask() {
        return c.slayerTask > 0 || c.taskAmount > 0;
    }

    public void generateTask() {
        if (hasTask() && !c.needsNewTask) {
        	c.getPA().closeAllWindows();
            c.getPA().sendStatement("You already have a task.");
            return;
        }
        if (hasTask() && c.needsNewTask) {
            int difficulty = getDifficulty(c.slayerTask);
            if (difficulty == EASY_TASK) {
                c.getDH().sendDialogues(105, 1597);
                c.needsNewTask = false;
                return;
            }
        }
        int taskLevel = getSlayerDifficulty();
        System.out.println("EASY :" + easyTask + "\nMEDIUM: " + mediumTask + "\nHARD: " + hardTask + "");
        for (Task slayerTask : Task.values()) {
            if (slayerTask.getDifficulty() == taskLevel) {
                if (c.playerLevel[18] >= slayerTask.getLevelReq()) {
                    resizeTable(taskLevel);
                    if (!c.needsNewTask) {
                        int task = getRandomTask(taskLevel);
                        for(int i = 0; i < c.removedTasks.length; i++) {
                            if(task == c.removedTasks[i]) {
                                c.sendMessage("Unavailable task: "+task);
                                generateTask();
                                return;
                            }
                        }
                        c.slayerTask = task;
                        c.taskAmount = getTaskAmount(taskLevel);
                    } else {
                        int task = getRandomTask(getDifficulty(taskLevel - 1));
                        for(int i = 0; i < c.removedTasks.length; i++) {
                            if(task == c.removedTasks[i]) {
                                c.sendMessage("Unavailable task: "+task);
                                generateTask();
                                return;
                            }
                        }
                        c.slayerTask = task;
                        c.taskAmount = getTaskAmount(getDifficulty(c.slayerTask) - 1);
                        c.needsNewTask = false;
                    }
                    updateInterfrace();
                    c.getPA().sendStatement("You have been assigned " + c.taskAmount+ " " + getTaskName(c.slayerTask) + ", Good luck "+ c.playerName + ".");
                    return;
                }
            }
        }
    }
    
    public void showTask() {   	
    	c.getPA().sendStatement("You have been assigned " + c.taskAmount+ " " + getTaskName(c.slayerTask) + ".");
    }
    
	public static int getReceivedPoints(Client p, final int difficulty) {
		int points = (difficulty == 1 ? 2 : difficulty == 2 ? 4 : difficulty == 3 ? 6 : 8);
		int bonus = 0;
		for(int Rows = 0; Rows < p.totalTasks + 50; Rows++) {
			bonus = (p.totalTasks == (50 * Rows) ? 10 : p.totalTasks == (10 * Rows) ? 5 : 1);
		}
		return (points+bonus) * (int) ((p.getRights().isDonator() ? 1.5 : 1));
	}

    public int getTaskAmount(int diff) {
        switch (diff) {
        case 1:
            return 15 + MistexUtility.random(5);
        case 2:
            return 25 + MistexUtility.random(10);
        case 3:
            return 35 + MistexUtility.random(25);
        }
        return 30 + MistexUtility.random(50);
    }

    public int getRandomTask(int diff) {
        if (diff == EASY_TASK) {
            return easyTask.get(MistexUtility.random(easyTask.size() - 1));
        } else if (diff == MEDIUM_TASK) {
            return mediumTask.get(MistexUtility.random(mediumTask.size() - 1));
        } else if (diff == HARD_TASK) {
            return hardTask.get(MistexUtility.random(hardTask.size() - 1));
        }
        return easyTask.get(MistexUtility.random(easyTask.size() - 1));
    }

    public int getSlayerDifficulty() {
        if (c.combatLevel > 0 && c.combatLevel <= 45) {
            return EASY_TASK;
        } else if (c.combatLevel > 45 && c.combatLevel <= 90) {
            return MEDIUM_TASK;
        } else if (c.combatLevel > 90) {
            return HARD_TASK;
        }
        return EASY_TASK;
    }
    
    public void cancelTask() {
        if(!hasTask()) {
        	c.getPA().sendStatement("You must have a task to cancel first.");
            return;
        }
        if(c.slayerPoints <= 1) {
        	c.getPA().sendStatement("This requires atleast 1 slayer points, which you don't have.");
            return;
        }
        c.getPA().sendStatement("You have cancelled your current task of "+c.taskAmount+" "+getTaskName(c.slayerTask)+".");
        c.slayerTask = -1;
        c.taskAmount = 0;
        c.slayerPoints -= 1;
    }
    
    public void removeTask() {
        int counter = 0;
        if(!hasTask()) {
        	c.getPA().sendStatement("You must have a task to remove first.");
            return;
        }
        if(c.slayerPoints < 100) {
        	c.getPA().sendStatement("This requires atleast 100 slayer points, which you don't have.");
            return;
        }
        for(int i = 0; i < c.removedTasks.length; i++) {
            if(c.removedTasks[i] != -1) {
                counter++;
            }
            if(counter == 4) {
                c.sendMessage("You don't have any open slots left to remove tasks.");
                return;
            }
            if(c.removedTasks[i] == -1) {
                c.removedTasks[i] = c.slayerTask;
                c.slayerPoints -= 100;
                c.slayerTask = -1;
                c.taskAmount = 0;
                c.sendMessage("Your current slayer task has been removed, you can't obtain this task again.");
                updateCurrentlyRemoved();
                return;
            }
        }
    }
    
    public void updateInterfrace() {
        InterfaceText.writeText(new InformationTab(c));
    }
    
    public void updateCurrentlyRemoved() {
        int line[] = {42014, 42015, 42016, 42017};
        for(int i = 0; i < c.removedTasks.length; i++) {
            if(c.removedTasks[i] != -1) {
                c.getPA().sendFrame126(this.getTaskName(c.removedTasks[i]), line[i]);
            } else {
                c.getPA().sendFrame126("", line[i]);
            }
        }
    }

}