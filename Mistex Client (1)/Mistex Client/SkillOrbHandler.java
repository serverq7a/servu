public class SkillOrbHandler {

    public static boolean DRAW_SkillOrbs = true;
    private static int ORB_DISPLAY_TIME = 384;
    private static int LEVEL_UP_DISPLAY_TIME = 340;
    private static int drawingLevelUp = -1;
    private static int[] savedExp = new int[24];
    private static SkillOrbHandler[] orbs = new SkillOrbHandler[24];
    public static boolean[] updated = new boolean[24];
    private static Sprite[] levelUpSkillIcons = new Sprite[24];
    private static Sprite[] skillIcons = new Sprite[24];
    private static Sprite background;
    private static Sprite fill;
    private static Sprite levelUp;
    private static Sprite[] number = new Sprite[10];
    private int levelUpTo;
    private int levelUpCycle;
    private float progress;
    private int position;
    private int lastUpdateCycle;
    private int appearCycle;

    public static void checkForUpdates() {
        for (int i = 0; i < orbs.length; i++) {
            if (savedExp[i] != Client.instance.currentExp[i]) {
                if ((orbs[i] != null) &&
                    (getLevelForExp(Client.instance.currentExp[i]) > getLevelForExp(savedExp[i]))) {
                    orbs[i].levelUp(getLevelForExp(Client.instance.currentExp[i]));
                }
                savedExp[i] = Client.instance.currentExp[i];
                updated[i] = DRAW_SkillOrbs;
            }
            if (orbs[i] != null) {
                if ((int) getProgress(i) > (int) orbs[i].progress) {
                    orbs[i].progress += 1.0F;
                } else {
                    orbs[i].progress = getProgress(i);
                }
            }
        }
    }

    private static void drawLevelUpOrb(int skill) {
        if ((drawingLevelUp != -1) && (drawingLevelUp != skill)) {
            if (orbs[skill].levelUpTo > 0) {
                orbs[skill].levelUpCycle = Client.loopCycle;
            }
            return;
        }
        if (orbs[skill].levelUpTo > 0) {
            drawingLevelUp = skill;
            int xPosition = DrawingArea.centerY - 30;
            int alpha = 256;
            if (Client.loopCycle - orbs[skill].levelUpCycle < 32) {
                alpha = 8 * (Client.loopCycle - orbs[skill].levelUpCycle);
            }
            if (Client.loopCycle - orbs[skill].levelUpCycle > LEVEL_UP_DISPLAY_TIME - 32) {
                alpha = 256 - 8 * (Client.loopCycle - (orbs[skill].levelUpCycle + LEVEL_UP_DISPLAY_TIME - 32));
            }
            levelUp.drawARGBSprite(xPosition - 44, -2, alpha);
            int alpha2 = alpha;
            if ((alpha2 == 256) && (Client.loopCycle - orbs[skill].levelUpCycle > LEVEL_UP_DISPLAY_TIME / 2)) {
                alpha2 = 8 * (Client.loopCycle - orbs[skill].levelUpCycle - LEVEL_UP_DISPLAY_TIME / 2);
            }
            if (alpha2 > 256) {
                alpha2 = 256;
            }
            levelUpSkillIcons[skill].drawARGBSprite(xPosition + 30 - levelUpSkillIcons[skill].myWidth / 2, 63 - levelUpSkillIcons[skill].myHeight / 2, Client.loopCycle - orbs[skill].levelUpCycle > LEVEL_UP_DISPLAY_TIME / 2 ? alpha - alpha2 / 3 * 2 : alpha);
            if (Client.loopCycle - orbs[skill].levelUpCycle > LEVEL_UP_DISPLAY_TIME / 2) {
                if (orbs[skill].levelUpTo < 10) {
                    number[orbs[skill].levelUpTo].drawARGBSprite(xPosition + 30 - number[orbs[skill].levelUpTo].myWidth / 2, 63 - number[orbs[skill].levelUpTo].myHeight / 2, alpha2);
                } else {
                    number[(orbs[skill].levelUpTo % 10)].drawARGBSprite(xPosition + 26, 63 - number[(orbs[skill].levelUpTo % 10)].myHeight / 2, alpha2);
                    number[((orbs[skill].levelUpTo - orbs[skill].levelUpTo % 10) / 10)].drawARGBSprite(xPosition + 31 - number[((orbs[skill].levelUpTo - orbs[skill].levelUpTo % 10) / 10)].myWidth, 63 - number[((orbs[skill].levelUpTo - orbs[skill].levelUpTo % 10) / 10)].myHeight / 2, alpha2);
                }
            }
            if (Client.loopCycle - orbs[skill].levelUpCycle > LEVEL_UP_DISPLAY_TIME) {
                orbs[skill].levelUpTo = -1;
                drawingLevelUp = -1;
            }
        }
    }

    private static void drawOrb(int skill) {
        if (Client.showSkillOrbs) {
            if (orbs[skill] == null) {
                orbs[skill] = new SkillOrbHandler(skill);
            }
            if (updated[skill]) {
                orbs[skill].lastUpdateCycle = Client.loopCycle;
                updated[skill] = false;
            }
            if (Client.loopCycle - orbs[skill].lastUpdateCycle > ORB_DISPLAY_TIME) {
                orbs[skill] = null;
                return;
            }
            if ((orbs[skill].levelUpTo > 0) && (orbs[skill].levelUpCycle > 32)) {
                for (int i = 0; i < orbs.length; i++) {
                    if (orbs[i] != null) {
                        orbs[i].appearCycle = Client.loopCycle;
                        updated[i] = true;
                    }
                }
            }
            int alpha = 256;
            if (Client.loopCycle - orbs[skill].appearCycle < 32) {
                alpha = 8 * (Client.loopCycle - orbs[skill].appearCycle);
            }
            if (Client.loopCycle - orbs[skill].lastUpdateCycle > ORB_DISPLAY_TIME - 32) {
                alpha = 256 - 8 * (Client.loopCycle - (orbs[skill].lastUpdateCycle + ORB_DISPLAY_TIME - 32));
            }
            if (orbs[skill].position == -1) {
                orbs[skill].position = getAvailablePosition();
            }
            int xPosition = (DrawingArea.clipEndX - DrawingArea.clipStartX) / 2 - 27;
            if (orbs[skill].position > 0) {
                if (orbs[skill].position % 2 != 0) {
                    xPosition += orbs[skill].position / 2 * 54;
                } else {
                    xPosition -= orbs[skill].position / 2 * 54;
                }
            }
            background.drawARGBSprite(xPosition, -4, alpha);
            DrawingArea.setDrawingArea(56, xPosition + 7, xPosition + 30, (int)(45.0F - orbs[skill].progress) + 5 + -4);
            fill.drawARGBSprite(xPosition + 7, 3, alpha);
            DrawingArea.setDrawingArea((int)(orbs[skill].progress - 38.0F) + -4, xPosition + 30, xPosition + 52, 3);
            fill.drawARGBSprite(xPosition + 7, 3, alpha);
            DrawingArea.defaultDrawingAreaSize();
            skillIcons[skill].drawARGBSprite(xPosition + 30 - skillIcons[skill].myWidth / 2, 28 - skillIcons[skill].myHeight / 2 + -4, alpha);
        }
    }

    public static void drawOrbs() {
        if (!Client.showSkillOrbs) {
            return;
        }
        if (!DRAW_SkillOrbs) {
            return;
        }
        checkForUpdates();
        for (int i = 0; i < orbs.length; i++) {
            if (orbs[i] != null || updated[i]) {
                drawOrb(i);
            }
        }
        for (int i = 0; i < orbs.length; i++) {
            if (orbs[i] != null) {
                drawLevelUpOrb(i);
            }
        }
    }

    private static int getAvailablePosition() {
        for (int i = 1; i < orbs.length + 1; i++) {
            boolean used = false;
            for (SkillOrbHandler orb: orbs) {
                if (orb != null) {
                    if (orb.position == i) {
                        used = true;
                    }
                }
            }
            if (!used) {
                return i;
            }
        }
        return 0;
    }

    public static int getExpForLevel(int level) {
        int points = 0;
        int output = 0;
        for (int lvl = 1; lvl <= level; lvl++) {
            points = (int)(points + Math.floor(lvl + 300.0D * Math.pow(2.0D, lvl / 7.0D)));
            if (lvl >= level) {
                return output;
            }
            output = (int) Math.floor(points / 4);
        }
        return 0;
    }

    public static int getLevelForExp(int exp) {
        int points = 0;
        int output = 0;
        if (exp > 13034430) {
            return 99;
        }
        for (int lvl = 1; lvl <= 99; lvl++) {
            points = (int)(points + Math.floor(lvl + 300.0D * Math.pow(2.0D, lvl / 7.0D)));
            output = (int) Math.floor(points / 4);
            if (output >= exp) {
                return lvl;
            }
        }
        return 0;
    }

    private static float getProgress(int i) {
        int currentLevel = getLevelForExp(savedExp[i]);
        float expDifference = getExpForLevel(currentLevel + 1) - getExpForLevel(currentLevel);
        float expEarnedAlready = savedExp[i] - getExpForLevel(currentLevel);
        return expEarnedAlready / expDifference * 90.0F;
    }

    public static void loadImages() {
        for (int i = 0; i < skillIcons.length; i++) {
            skillIcons[i] = new Sprite("SkillOrbs/skill " + i);
        }
        for (int i = 0; i < levelUpSkillIcons.length; i++) {
            levelUpSkillIcons[i] = new Sprite("SkillOrbs/level_up_skill " + i);
        }
        background = new Sprite("SkillOrbs/background");
        fill = new Sprite("SkillOrbs/fill");
        levelUp = new Sprite("SkillOrbs/level_up");
        for (int i = 0; i < number.length; i++) {
            number[i] = new Sprite("SkillOrbs/number " + i);
        }
    }

    private SkillOrbHandler(int skill) {
        this.position = -1;
        this.appearCycle = Client.loopCycle;
        this.progress = getProgress(skill);
        this.levelUpTo = -1;
    }

    private void levelUp(int level) {
        this.levelUpCycle = Client.loopCycle;
        this.levelUpTo = level;
    }

    public static void reset() {
        for (int i = 0; i < orbs.length; i++) {
            updated[i] = false;
        }
    }
}