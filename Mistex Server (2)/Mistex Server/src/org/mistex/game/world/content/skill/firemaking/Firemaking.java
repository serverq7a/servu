package org.mistex.game.world.content.skill.firemaking;

import org.mistex.game.Mistex;
import org.mistex.game.world.Tile;
import org.mistex.game.world.clip.region.Region;
import org.mistex.game.world.content.achievement.AchievementHandler;
import org.mistex.game.world.content.achievement.AchievementList;
import org.mistex.game.world.content.interfaces.InterfaceText;
import org.mistex.game.world.content.interfaces.impl.AchievementTab;
import org.mistex.game.world.content.skill.Skill;
import org.mistex.game.world.event.CycleEvent;
import org.mistex.game.world.event.CycleEventContainer;
import org.mistex.game.world.event.CycleEventHandler;
import org.mistex.game.world.gameobject.ObjectHandler;
import org.mistex.game.world.gameobject.ObjectManager;
import org.mistex.game.world.player.Client;

public class Firemaking extends Skill {

    public Firemaking() {
		super(Skills.FIREMAKING);
	}

	public static enum LogData {
    	NORMAL(1511, 40),
    	OAK(1521, 60),
    	WILLOW(1519, 105),
    	MAPLE(1517, 135),
    	YEW(1515, 203),
    	MAGIC(1513, 304);
    	
    	private int log, exp;
    	
    	private LogData(int log, int exp) {
    		this.exp = exp;
    		this.log = log;
		}
    	
    	public int getExp() {
			return exp;
		}
    	
    	public int getLog() {
			return log;
		}
    	
    	public static LogData forId(int id) {
    		for (LogData data : values()) {
    			if (data.getLog() == id) {
    				return data;
    			}
    		}
    		return NORMAL;
    	}
    }

    private static int[][] logData = {
        {
            1511, 1, 40, 2732
        }, {
            2862, 1, 40, 2732
        }, {
            1521, 15, 60, 2732
        }, {
            1519, 30, 105, 2732
        }, {
            6333, 35, 105, 2732
        }, {
            1517, 45, 135, 2732
        }, {
            10810, 45, 135, 2732
        }, {
            6332, 50, 158, 2732
        }, {
            1515, 60, 203, 2732
        }, {
            1513, 75, 304, 2732
        },
    };

    public static boolean playerLogs(Client c, int i, int l) {
        boolean flag = false;
        for (int kl = 0; kl < logData.length; kl++) {
            if ((i == logData[kl][0] && requiredItem(c, l)) || (requiredItem(c, i) && l == logData[kl][0])) {
                flag = true;
            }
        }
        return flag;
    }

    private static int getAnimation(Client c, int item, int item1) {
        int[][] data = {
            {
                841, 6714
            }, {
                843, 6715
            }, {
                849, 6716
            }, {
                853, 6717
            }, {
                857, 6718
            }, {
                861, 6719
            }
        };
        for (int i = 0; i < data.length; i++) {
            if (item == data[i][0] || item1 == data[i][0]) {
                return data[i][1];
            }
        }
        return 733;
    }

    private static boolean requiredItem(Client c, int i) {
        int[] data = {
            841, 843, 849, 853, 857, 861, 590
        };
        for (int l = 0; l < data.length; l++) {
            if (i == data[l]) {
                return true;
            }
        }
        return false;
    }

    public static void grabData(final Client c, final int useWith,
        final int withUse) {
        final int[] coords = new int[2];
        coords[0] = c.absX;
        coords[1] = c.absY;
        if (c.playerIsWoodcutting) {
            c.sendMessage("Please finish what you are doing!");
            return;
        }

        if (ObjectHandler.objectExists(coords[0], coords[1], c.heightLevel) != null || Mistex.objectManager.getObject(coords[0], coords[1], c.heightLevel) != null) {
            c.sendMessage("You can't light a fire here!");
            return;
        }

        //if (c.inBankZone() && !c.getPA().isDonator()) {
          //  c.sendMessage("You can't light a fire here!");
          //  return;   	
       //}
        
        for (int i = 0; i < logData.length; i++) {
            if ((requiredItem(c, useWith) && withUse == logData[i][0] || useWith == logData[i][0] && requiredItem(c, withUse))) {
                if (c.playerLevel[11] < logData[i][1]) {
                    c.sendMessage("You need a higher firemaking level to light this log!");
                    return;
                }
                if (System.currentTimeMillis() - c.lastFire > 1200) {
                    if (c.playerIsFiremaking) {
                        return;
                    }
                    final int[] time = new int[3];
                    final int log = logData[i][0];
                    final int fire = logData[i][3];
                    if (System.currentTimeMillis() - c.lastFire > 3000) {
                        c.startAnimation(getAnimation(c, useWith, withUse));
                        time[0] = 4;
                        time[1] = 3;
                    } else {
                        time[0] = 1;
                        time[1] = 2;
                    }
                    c.playerIsFiremaking = true;

                    Mistex.itemHandler.createGroundItem(c, log, coords[0], coords[1], 1, c.getId());

                    CycleEventHandler.getSingleton().addEvent(c,new CycleEvent() {
                    	@Override
                            public void execute(CycleEventContainer container) {
                                ObjectHandler.createAnObject(c, fire, coords[0], coords[1]);
                                Mistex.itemHandler.removeGroundItem(c, log, coords[0], coords[1], false);
                                c.playerIsFiremaking = false;
                                container.stop();
                            }

                            @Override
                            public void stop() {

                            }
                        }, time[0]);
                    if (Region.getClipping(c.getX() - 1, c.getY(), c.heightLevel, -1, 0)) {
                        c.getPA().walkTo(-1, 0);
                    } else if (Region.getClipping(c.getX() + 1, c.getY(), c.heightLevel, 1, 0)) {
                        c.getPA().walkTo(1, 0);
                    } else if (Region.getClipping(c.getX(), c.getY() - 1, c.heightLevel, 0, -1)) {
                        c.getPA().walkTo(0, -1);
                    } else if (Region.getClipping(c.getX(), c.getY() + 1, c.heightLevel, 0, 1)) {
                        c.getPA().walkTo(0, 1);
                    }
                    new Tile(c.absX - 1, c.absY, c.heightLevel);
                    CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {
                    	@Override
                        public void execute(CycleEventContainer container) {
                            c.startAnimation(65535);
                            c.sendMessage("You light the logs.");
                            c.logsBurned += 1;
                            if (c.logsBurned == 1) {
                    			c.sendMessage("@war@You have started the achievement: Blazed");
                            	InterfaceText.writeText(new AchievementTab(c));
                            }
                    		if (c.logsBurned == 4200)
                    			AchievementHandler.activateAchievement(c, AchievementList.BLAZED);
                            
                            container.stop();
                        }

                        @Override
                        public void stop() {}
                    }, time[1]);
                    CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {
                    	@Override
                        public void execute(CycleEventContainer container) {
                            ObjectHandler.createAnObject(c, -1, coords[0], coords[1]);
                            Mistex.itemHandler.createGroundItem(c, 592, coords[0], coords[1], 1, c.getId());
                            c.sendMessage("Your fire has been extinguished.");
                            container.stop();
                        }
                        @Override
                        public void stop() {

                        }
                    }, 120);
                    new Firemaking().addExp(c, logData[i][2]);
                    c.getItems().deleteItem(logData[i][0], c.getItems().getItemSlot(logData[i][0]), 1);
                    c.turnPlayerTo(c.absX + 1, c.absY);
                    c.lastFire = System.currentTimeMillis();
                }
            }
        }
        ObjectManager.loadCustomSpawns(c);
    }
}