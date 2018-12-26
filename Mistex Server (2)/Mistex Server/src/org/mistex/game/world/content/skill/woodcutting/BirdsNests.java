package org.mistex.game.world.content.skill.woodcutting;

import org.mistex.game.MistexUtility;
import org.mistex.game.world.player.Client;


public class BirdsNests {

    public static final int[] BIRD_NEST_IDS = {
        5070, 5071, 5072, 5073, 5074
    };
    public static final int[] SEED_REWARDS = {
        5312, 5313, 5314, 5315, 5316, 5283, 5284, 5285, 5286, 5287, 5288, 5289, 5290, 5317
    };
    public static final int[] RING_REWARDS = {
        1635, 1637, 1639, 1641, 1643
    };
    public static final int EMPTY = 5075;
    public static final int RED = 5076;
    public static final int BLUE = 5077;
    public static final int GREEN = 5078;
    public static final int AMOUNT = 1;

    public static boolean isNest(final int itemId) {
        for (int nest: BIRD_NEST_IDS) {
            if (nest == itemId) {
                return true;
            }
        }
        return false;
    }

    public static void dropNest(Client c) {
        if (MistexUtility.random(70) == 1) {
            int r = MistexUtility.random(1000);
            if (r >= 0 && r <= 640) {
                c.getItems().addOrDropItem(5073, 1); //seed
            } else if (r >= 641 && r <= 960) {
                c.getItems().addOrDropItem(5074, 1); //ring
            } else if (r >= 961) {
                int random = MistexUtility.random(2);
                if (random == 1) {
                    c.getItems().addOrDropItem(5072, 1);
                } else if (random == 2) {
                    c.getItems().addOrDropItem(5071, 1);
                } else {
                    c.getItems().addOrDropItem(5070, 1);
                }
            }
        }
    }

    public static final void searchNest(Client c, int itemId) {
        eggNest(c, itemId);
        seedNest(c, itemId);
        ringNest(c, itemId);
        c.getItems().deleteItem(itemId, AMOUNT);
        c.getItems().addItem(EMPTY, AMOUNT);
    }

    public static final void ringNest(Client c, int itemId) {
        if (itemId == 5074) {
            int random = MistexUtility.random(1000);
            if (random >= 0 && random <= 340) {
                c.getItems().addItem(RING_REWARDS[0], AMOUNT);
            } else if (random >= 341 && random <= 750) {
                c.getItems().addItem(RING_REWARDS[1], AMOUNT);
            } else if (random >= 751 && random <= 910) {
                c.getItems().addItem(RING_REWARDS[2], AMOUNT);
            } else if (random >= 911 && random <= 989) {
                c.getItems().addItem(RING_REWARDS[3], AMOUNT);
            } else if (random >= 990) {
                c.getItems().addItem(RING_REWARDS[4], AMOUNT);
            }
        }
    }

    public static final void seedNest(Client c, int itemId) {
        if (itemId == 5073) {
            int random = MistexUtility.random(1000);
           // c.sendMessage("Random = " + random);
            if (random >= 0 && random <= 220) {
                c.getItems().addItem(SEED_REWARDS[0], AMOUNT);
            } else if (random >= 221 && random <= 350) {
                c.getItems().addItem(SEED_REWARDS[1], AMOUNT);
            } else if (random >= 351 && random <= 400) {
                c.getItems().addItem(SEED_REWARDS[2], AMOUNT);
            } else if (random >= 401 && random <= 430) {
                c.getItems().addItem(SEED_REWARDS[3], AMOUNT);
            } else if (random >= 431 && random <= 440) {
                c.getItems().addItem(SEED_REWARDS[4], AMOUNT);
            } else if (random >= 441 && random <= 600) {
                c.getItems().addItem(SEED_REWARDS[5], AMOUNT);
            } else if (random >= 601 && random <= 700) {
                c.getItems().addItem(SEED_REWARDS[6], AMOUNT);
            } else if (random >= 701 && random <= 790) {
                c.getItems().addItem(SEED_REWARDS[7], AMOUNT);
            } else if (random >= 791 && random <= 850) {
                c.getItems().addItem(SEED_REWARDS[8], AMOUNT);
            } else if (random >= 851 && random <= 900) {
                c.getItems().addItem(SEED_REWARDS[9], AMOUNT);
            } else if (random >= 901 && random <= 930) {
                c.getItems().addItem(SEED_REWARDS[10], AMOUNT);
            } else if (random >= 931 && random <= 950) {
                c.getItems().addItem(SEED_REWARDS[11], AMOUNT);
            } else if (random >= 951 && random <= 970) {
                c.getItems().addItem(SEED_REWARDS[12], AMOUNT);
            } else if (random >= 971 && random <= 980) {
                c.getItems().addItem(SEED_REWARDS[13], AMOUNT);
            } else {
                c.getItems().addItem(SEED_REWARDS[0], AMOUNT);
            }
        }
    }

    public static final void eggNest(Client c, int itemId) {
        if (itemId == 5070) {
            c.getItems().addItem(RED, AMOUNT);
        }
        if (itemId == 5071) {
            c.getItems().addItem(GREEN, AMOUNT);
        }
        if (itemId == 5072) {
            c.getItems().addItem(BLUE, AMOUNT);
        }
    }

}