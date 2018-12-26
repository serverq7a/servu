// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 


final class SkillConstants
{

	public static int skillsCount = 25;
	
    public static String skillNames[] = {
        "attack", "defence", "strength", "hitpoints", "ranged", "prayer", "magic", "cooking", "woodcutting", "fletching", 
        "fishing", "firemaking", "crafting", "smithing", "mining", "herblore", "agility", "thieving", "slayer", "farming", 
        "runecraft", "construction", "hunter", "summoning", "dungeoneering"
    };
    
    public static boolean skillEnabled[] = {
        true, true, true, true, true, true, true, true, true, true, 
        true, true, true, true, true, true, true, true, true, true, 
        true, true, true, true, true, true
    };
    
	public static final int[][] goalData = new int[skillsCount][3];
	
	static {
		for (int i = 0; i < goalData.length; i++) {
			goalData[i][0] = -1;
			goalData[i][1] = -1;
			goalData[i][2] = -1;
		}
	}
	
	public static String goalType = "Target Level: ";
	public static int selectedSkillId = -1;
	
	public static int skillIdForName(String name) {
		name = name.toLowerCase().trim();
		int[] skillID = { 0, 3, 14, 2, 16, 13, 1, 15, 10, 4, 17, 7, 5, 12, 11, 6, 9, 8, 20, 18, 19, 21, 22, 23, 24 };
		String[] names = { "Attack", "Hitpoints", "Mining", "Strength",
				"Agility", "Smithing", "Defence", "Herblore", "Fishing", "Range",
				"Thieving", "Cooking", "Prayer", "Crafting", "Firemaking", "Magic",
				"Fletching", "Woodcutting", "Runecrafting", "Slayer", "Farming",
				"Construction", "Hunter", "Summoning", "Dungeoneering" };
		
		for (int i = 0; i < names.length; i++)
			if (names[i].equalsIgnoreCase(name))
				return skillID[i];
		
		return 0;
	}
	
	public static String nameForId(int id) {
		String[] names = { "Attack", "Hitpoints", "Mining", "Strength",
				"Agility", "Smithing", "Defence", "Herblore", "Fishing", "Range",
				"Thieving", "Cooking", "Prayer", "Crafting", "Firemaking", "Magic",
				"Fletching", "Woodcutting", "Runecrafting", "Slayer", "Farming",
				"Construction", "Hunter", "Summoning", "Dungeoneering" };
		for (int i = 0; i < names.length; i++)
			if (i == id) return names[i];
		return null;
	}

}
