package org.mistex.game.world.content;

import org.mistex.game.MistexUtility;
import org.mistex.game.world.event.CycleEvent;
import org.mistex.game.world.event.CycleEventContainer;
import org.mistex.game.world.event.CycleEventHandler;
import org.mistex.game.world.player.Client;

/**
 * Handles the resting animation
 * @author Omar |Play Boy
 */
public class Resting {
	
	public enum Rest {
		ANIMATION1(5713, 5748),
		ANIMATION2(11786, 11788),
		ANIMATION3(5713, 2921);
		
		private int restAnimation;
		private int standAnimation;
		
		private Rest(int restAnimation, int standAnimation) {
			this.restAnimation = restAnimation;
			this.standAnimation = standAnimation;
		}
		
		public int restAnimation() {
			return restAnimation;
		}
		
		public int standAnimation() {
			return standAnimation;
		}
		
		public static Rest forId(int id) {
			for (Rest rest : values()) {
				if (rest.restAnimation() == id) {
					return rest;
				}
			}
			return null;
		}
	}
	
	public static void beginRest(Client c) {
		c.rest = Rest.values()[MistexUtility.random(Rest.values().length - 1)];
		c.startAnimation(c.rest.restAnimation());
	}
	
	public static void endRest(Client c) {
		c.startAnimation(c.rest.standAnimation());
		c.rest = null;
	}

    /**
     * Starts resting
     * @param c
     */
    public static void startResting(final Client c) {
    	beginRest(c);
        c.stopMovement();
        CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {
            public void execute(CycleEventContainer p) {
                if (c.runEnergy < 100) {
//                    c.runEnergy += ENERGY_INCREASE;
                    c.getPA().sendFrame126("" + c.runEnergy, 149);
                } else
                    p.stop();
            }
            @Override
            public void stop() {}
        }, 1);
    }

    /**
     * Stops resting
     * @param c
     */
    public static void stopResting(final Client c) {
        CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {
            public void execute(CycleEventContainer p) {
            	endRest(c);
            	c.stopMovement();
                p.stop();
            }
            @Override
            public void stop() {}
        }, 1);


    }
}