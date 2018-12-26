package org.mistex.game.world.content.achievement;

import org.mistex.game.world.content.achievement.impl.*;

public enum AchievementList {

	MYSTIC(new Mystic()),
	LAW_ABIDING_CITIZEN(new Law_Abiding_Citizen()),
	MUNCHIES(new Munchies()),
	MR_THIRSTY(new Mr_Thirsty()),
	OVER_900(new Over_900()),
	HOME_SICK(new Home_Sick()),
	BRANIAC(new Brainiac()),
	HOLY_MONK(new Holy_Monk()),
	THE_VIEWER(new The_Viewer()),
	MASTER_BAITER(new Master_Baiter()),
	BEAST(new Beast()),
	WEAKLING(new Weakling()),
	THE_GAMER(new The_Gamer()),
	THE_TRADER(new The_Trader()),
	SELF_CONSCIOUS(new Self_Conscious()),
	CORPOREAL_HUNTER(new Corporeal_Hunter()),
	JAD_KILLER(new Jad_killer()),
	CRABS(new Crabs()),
	NAZI(new Nazi()),
	IRON_CHEF(new Iron_Chef()),
	BLAZED(new Blazed()),
	OREO(new Oreo()),
	WOWZA(new Wowza()),
	THE_DUELIST(new The_Duelist());
	
	private Achievement achievement;
	
	AchievementList(Achievement achievement) {
		this.achievement = achievement;
	}
	
	public Achievement getAchievement() {
		return achievement;
	}
	
	public static int getSize() {
		return AchievementList.values().length;
	}
}
