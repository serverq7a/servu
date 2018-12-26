package org.mistex.game.world.content.gambling;

import org.mistex.game.Mistex;
import org.mistex.game.world.clip.region.Region;
import org.mistex.game.world.event.CycleEvent;
import org.mistex.game.world.event.CycleEventContainer;
import org.mistex.game.world.event.CycleEventHandler;
import org.mistex.game.world.player.Client;

@SuppressWarnings("static-access")
public class MithrilSeeds {

	/**
	 * Holds all the flower ids
	 */
    public static int rFlower[] = {
        2460, 2462, 2464, 2466, 2468, 2470, 2472, 2474, 2476
    };
    
    /**
     * Picks a random flower
     * @return
     */
    public static int flower() {
        return rFlower[(int)(Math.random() * rFlower.length)];
    }

    /**
     * Plants the actual seeds
     * @param c
     */
    public static void plantMithrilSeed(Client c) {
        final int[] coords = new int[2];
        coords[0] = c.absX;
        coords[1] = c.absY;

        if (c.getItems().playerHasItem(299, 1)) {
            c.getItems().deleteItem2(299, 1);
            c.getPA().addSkillXP(2500, 19);
            c.startAnimation(827);
            c.canWalk = false;
            Mistex.objectHandler.createAnObject(c, c.randomFlower(), coords[0], coords[1]);
            c.getDH().sendDialogues(22, -1);
        }
    }
    
    /**
     * Processes what happens after seed is planted
     * @param c
     */
    public static void processFlower(Client c) {
        final int[] coords = new int[2];
        coords[0] = c.absX;
        coords[1] = c.absY;

        Mistex.objectHandler.createAnObject(c, -1, coords[0], coords[1]);
        Mistex.objectHandler.createAnObject(c, c.randomFlower(), coords[0], coords[1]);
        c.canWalk = true;
        if (Region.getClipping(c.getX() - 1, c.getY(), c.heightLevel, -1, 0)) {
            c.getPA().walkTo(-1, 0);
        } else if (Region.getClipping(c.getX() + 1, c.getY(), c.heightLevel, 1, 0)) {
            c.getPA().walkTo(1, 0);
        } else if (Region.getClipping(c.getX(), c.getY() - 1, c.heightLevel, 0, -1)) {
            c.getPA().walkTo(0, -1);
        } else if (Region.getClipping(c.getX(), c.getY() + 1, c.heightLevel, 0, 1)) {
            c.getPA().walkTo(0, 1);
        }
        c.turnPlayerTo(coords[0] + 1, coords[1]);
        c.sendMessage("You plant a flower!");

        CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {@
            Override
            public void execute(CycleEventContainer container) {
                for (int j = 0; j < Mistex.playerHandler.players.length; j++) {
                    if (Mistex.playerHandler.players[j] != null) {
                        Client c = (Client) Mistex.playerHandler.players[j];
                        Mistex.objectHandler.createAnObject(c, -1, coords[0], coords[1]);
                        container.stop();
                    }
                }
            }
        	@Override
            public void stop() {

            }
        }, 100);
    }

    /**
     * Pickups the flowers after it is planted
     * @param c
     */
	public static void pickupFlowers(Client c) {
        final int[] coords = new int[2];
        coords[0] = c.absX;
        coords[1] = c.absY;

        c.canWalk = true;
        Mistex.objectHandler.createAnObject(c, -1, coords[0], coords[1]);
        c.getItems().addItem(flower(), 1);
    }
}