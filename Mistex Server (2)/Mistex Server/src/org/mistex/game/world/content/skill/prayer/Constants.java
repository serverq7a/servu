package org.mistex.game.world.content.skill.prayer;

import org.mistex.game.world.player.Client;
import org.mistex.game.world.player.PlayerConfiguration;

public class Constants {

	protected static final int PRAYER = 5;

	protected static final int PRAYER_XP = PlayerConfiguration.PRAYER_EXPERIENCE;

	public static boolean playerBones(final Client c, final int item) {
		for (int i = 0; i < BONES.length; i++) {
			if (item == BONES[i][0])
				return item == BONES[i][0];
		}
		return false;
	}

	public static int[][] BONES = { { 526, 5 }, // NPC BONES
			{ 528, 5 }, // BURNT BONES
			{ 530, 5 }, // BAT BONES
			{ 2859, 5 }, // WOLF BONES
			{ 3179, 5 }, // MONKEY BONES
			{ 3180, 5 }, // MONKEY BONES
			{ 3181, 5 }, // MONKEY BONES
			{ 3182, 5 }, // MONKEY BONES
			{ 3183, 5 }, // MONKEY BONES
			{ 3185, 5 }, // MONKEY BONES
			{ 3186, 5 }, // MONKEY BONES
			{ 3187, 5 }, // MONKEY BONES
			{ 532, 15 }, // BIG BONES
			{ 534, 30 }, // BABY DRAGON BONES
			{ 536, 72 }, // DRAGON BONES
			{ 2530, 5 }, // PLAYER BONES
			{ 3123, 25 }, // SHAIKAHAN BONES
			{ 3125, 23 }, // JOGRE BONES
			{ 3127, 25 }, // BURNT JOGRE BONES
			{ 4812, 82 }, // ZOGRE BONES
			{ 4830, 84 }, // FAYGR BONES
			{ 4832, 96 }, // RAURG BONES
			{ 4834, 140 }, // OURG BONES
			{ 6729, 125 }, // DAGANNOTH BONES
			{ 6812, 50 }, // WYVERN BONES
			{ 18830, 180 }, // FROST BONES
	};

}
