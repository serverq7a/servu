package org.mistex.game.world.content.achievement;

import java.util.ArrayList;

import org.mistex.game.world.content.interfaces.InterfaceText;
import org.mistex.game.world.content.interfaces.impl.AchievementTab;
import org.mistex.game.world.player.Client;


/**
 * Handles the methods for the achievements
 *
 * @author Daniel | Eclipse
 */
public class AchievementHandler {

    /**
     * Adds each of the achievement and the frames for them to the array lists
     * so they're accessable throughout the class
     */
    public AchievementHandler() {
        for (int i = firstFrameId; i <= lastFrameId; i++)
            frameList.add(i);
    }

    /**
     * Holds the types of achievements
     *
     * @author Daniel | Eclipse
     */
    public enum AcheivementType {
        EASY, MEDIUM, DIFFICULT
    }

    /**
     * The frames for the interface are held in these two integers. The first
     * frame and the last frame, every other frame is added using the difference
     * between these
     */
    private int firstFrameId = 10, lastFrameId = 20;

    /**
     * Holds each of the frames inside and array list
     */
    private ArrayList < Integer > frameList = new ArrayList < Integer > ();

    /**
     * Activates the achievement for the individual player. adds the point and
     * then activates the method giveReward for the given achievement.
     *
     * @param player
     * @param achievement
     */

    public static void activateAchievement(Client player, AchievementList achievement) {
        if (achievement.getAchievement().canGet(player, achievement.getAchievement()) /* && !isComplete(player, achievement)*/ ) {
            AchievementInterface.sendCompleteInterface(player, achievement.getAchievement());
            player.achievements[achievement.ordinal()] = true;
            achievement.getAchievement().giveReward(player, achievement.getAchievement());
            player.achievementsCompleted += 1;
            player.sendMessage("[ @ceo@Achievements</col> ] You have completed an achievement!");     
            player.sendMessage("[ @ceo@Achievements</col> ] You have been rewarded @ceo@" + achievement.getAchievement().getPoints() + " </col>achievement points and </col>" + achievement.getAchievement().getRewardString());
            player.sendMessage("[ @ceo@Achievements</col> ] You have completed @ceo@" + player.achievementsCompleted + "</col>/16 achievements.");
        	InterfaceText.writeText(new AchievementTab(player));
            //if (!achievement.getAchievement().getRewardString().equals("") && !achievement.getAchievement().getRewardString().equals(null))     
           // player.achievementPoints += achievement.getAchievement().getPoints();
        }
    }

}