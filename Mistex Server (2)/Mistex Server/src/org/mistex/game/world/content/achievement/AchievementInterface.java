package org.mistex.game.world.content.achievement;

import org.mistex.game.world.event.CycleEvent;
import org.mistex.game.world.event.CycleEventContainer;
import org.mistex.game.world.event.CycleEventHandler;
import org.mistex.game.world.player.Client;

/**
 * handles the interfaces for achievements
 *
 * @author Daniel | Eclipse
 */

public class AchievementInterface {

    public static void sendInterfaceForAchievement(final Client player, Achievement achievement) {
        player.getPA().sendFrame126(achievement.getClass().getSimpleName(), 8144);
        player.getPA().sendFrame126("", 8145);
        player.getPA().sendFrame126("Achievement difficulty: " + achievement.getAcheivementType(), 8147);
        player.getPA().sendFrame126("", 8148);
        player.getPA().sendFrame126("To complete this task, you'll need to:", 8149);
        int line = 8150;
        for (int i = 0; i < achievement.getDescription().length; i++) {
            player.getPA().sendFrame126(achievement.getDescription()[i], line++);
        }
        player.getPA().sendFrame126("", line++);
        player.getPA().sendFrame126("Reward: " + achievement.getRewardString(), line++);
        for (int i = line; i < 8247; i++) {
            player.getPA().sendFrame126("", i);
        }
        player.getPA().showInterface(8134);
        player.flushOutStream();
    }

    public static void sendCompleteInterface(final Client player,
        final Achievement achievement) {
        player.showingInterface = true;
        CycleEventHandler.getSingleton().addEvent(player, new CycleEvent() {
        	@Override
            public void execute(CycleEventContainer event) {
                player.getPA().walkableInterface(23133);
                player.getPA().sendFrame126("     ACHIEVEMENT",
                    23135);
                player.getPA().sendFrame126("' " + achievement.getClass().getSimpleName().replaceAll("_", " ") + " '", 23136);
                event.stop();
            }
            @Override
            public void stop() {
                // TODO Auto-generated method stub

            }
        }, 1);
        player.showingInterface = true;
        CycleEventHandler.getSingleton().addEvent(player, new CycleEvent() {@
            Override
            public void execute(CycleEventContainer event) {
                player.showingInterface = false;
                player.getPA().checkIfComepletedAll();
                event.stop();
            }
            @Override
            public void stop() {
                // TODO Auto-generated method stub

            }
        }, 10);
        //TODO firework
    }

}