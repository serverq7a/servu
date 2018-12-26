package org.mistex.game.world.content.minigame.championsbattle;

import org.mistex.game.world.npc.NPCHandler;
import org.mistex.game.world.player.Client;

/**
 * Champion's battle minigame
 * @author Omar | Play Boy
 */
public class ChampionsBattle {

	/**
	 * The npc ids
	 */
    public static final int KING_BLACK_DRAGON = 50,
    TZTOK_JAD = 2745,
    BARREL_CHEST = 5666,
    COMMANDER_ZILYANA = 6247,
    GENERAL_GRAADOR = 6260,
    MAN = 2;

    /**
     * The npc waves
     */
    private final int[][] WAVES = {
        { KING_BLACK_DRAGON }, 
        { TZTOK_JAD },
        { COMMANDER_ZILYANA }, 
        { GENERAL_GRAADOR },
        { BARREL_CHEST }, 
        { MAN }
    };

    /**
     * The coordinates of npc
     */
    private final static int[][] COORDINATES = {
        { 1891, 5355 },
        { 1899, 5364 }, 
        { 1908, 5356 }, 
        { 1900, 5347 },       
    };
    
    /**
     * The next wave
     * @param c
     */
    public void nextWave(Client c) {
        if (c != null) {
            if (c.championsWave >= WAVES.length) {
                c.championsWave = 0;
                return;
            }
            if (c.championsWave < 0) {
                return;
            }
            int npcAmount = WAVES[c.championsWave].length;
            for (int j = 0; j < npcAmount; j++) {
                int npc = WAVES[c.championsWave][j];
                int X = COORDINATES[j][0];
                int Y = COORDINATES[j][1];
                int H = 2;
                int hp = getHp(npc);
                int max = getMax(npc);
                int atk = getAtk(npc);
                int def = getDef(npc);
                NPCHandler.spawnNpc(c, npc, X, Y, H, 0, hp, max, atk, def, true, true);
            }
            c.championsToKill = npcAmount;
            c.championsBattleKills = 0;
            c.championsRounds += 1;
            c.getPA().sendStatement("You are now on round "+c.championsRounds+"!");
        }
    }

    /**
     * The hp of npc
     * @param npc
     * @return
     */
    public static int getHp(int npc) {
        switch (npc) {
        case KING_BLACK_DRAGON:
            return 100;

        case TZTOK_JAD:
            return 100;

        case COMMANDER_ZILYANA:
            return 150;

        case GENERAL_GRAADOR:
            return 200;

        case BARREL_CHEST:
            return 300;

        case MAN:
            return 99;
        }
        return 1;
    }

    /**
     * The max of npc
     * @param npc
     * @return
     */
    public static int getMax(int npc) {
        switch (npc) {
        case KING_BLACK_DRAGON:
            return 20;

        case TZTOK_JAD:
            return 35;

        case COMMANDER_ZILYANA:
            return 35;

        case GENERAL_GRAADOR:
            return 40;

        case BARREL_CHEST:
            return 65;

        case MAN:
            return 25;
        }
        return 5;
    }

    /**
     * The attack of npc
     * @param npc
     * @return
     */
    public static int getAtk(int npc) {
        switch (npc) {
        case KING_BLACK_DRAGON:
            return 200;

        case TZTOK_JAD:
            return 300;

        case COMMANDER_ZILYANA:
            return 400;

        case GENERAL_GRAADOR:
            return 450;

        case BARREL_CHEST:
            return 500;

        case MAN:
            return 200;
        }
        return 100;
    }

    /**
     * The defence of npc
     * @param npc
     * @return
     */
    public static int getDef(int npc) {
        switch (npc) {
        case KING_BLACK_DRAGON:
            return 200;

        case TZTOK_JAD:
            return 300;

        case COMMANDER_ZILYANA:
            return 400;

        case GENERAL_GRAADOR:
            return 450;

        case BARREL_CHEST:
            return 800;

        case MAN:
            return 800;
        }
        return 100;
    }

}