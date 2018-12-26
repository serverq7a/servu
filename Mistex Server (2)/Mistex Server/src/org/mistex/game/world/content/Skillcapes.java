package org.mistex.game.world.content;

import org.mistex.game.world.player.Client;
import org.mistex.game.world.player.PlayerConfiguration;


public class Skillcapes {

	/**
	 * Checks what cape the player is wearing
	 * @param cape
	 * @return
	 */
	public static boolean wearingCape(int cape) {
		int capes[] = { 9747, 9748, 9750, 9751, 9753, 9754, 9756, 9757, 9759,
				9760, 9762, 9763, 9765, 9766, 9768, 9769, 9771, 9772, 9774,
				9775, 9777, 9778, 9780, 9781, 9783, 9784, 9786, 9787, 9789,
				9790, 9792, 9793, 9795, 9796, 9798, 9799, 9801, 9802, 9804,
				9805, 9807, 9808, 9810, 9811, 10662 };
		for (int i = 0; i < capes.length; i++) {
			if (capes[i] == cape) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Handles the skillcape GFX
	 * @param cape
	 * @return
	 */
	public static int skillcapeGfx(int cape) {
		int capeGfx[][] = { { 9747, 823 }, { 9748, 823 }, { 9750, 828 },
				{ 9751, 828 }, { 9753, 824 }, { 9754, 824 }, { 9756, 832 },
				{ 9757, 832 }, { 9759, 829 }, { 9760, 829 }, { 9762, 813 },
				{ 9763, 813 }, { 9765, 817 }, { 9766, 817 }, { 9768, 833 },
				{ 9769, 833 }, { 9771, 830 }, { 9772, 830 }, { 9774, 835 },
				{ 9775, 835 }, { 9777, 826 }, { 9778, 826 }, { 9780, 818 },
				{ 9781, 818 }, { 9783, 812 }, { 9784, 812 }, { 9786, 827 },
				{ 9787, 827 }, { 9789, 820 }, { 9790, 820 }, { 9792, 814 },
				{ 9793, 814 }, { 9795, 815 }, { 9796, 815 }, { 9798, 819 },
				{ 9799, 819 }, { 9801, 821 }, { 9802, 821 }, { 9804, 831 },
				{ 9805, 831 }, { 9807, 822 }, { 9808, 822 }, { 9810, 825 },
				{ 9811, 825 }, { 10662, 816 } };
		for (int i = 0; i < capeGfx.length; i++) {
			if (capeGfx[i][0] == cape) {
				return capeGfx[i][1];
			}
		}
		return -1;
	}

	/**
	 * Handles the skillcape emote
	 * @param cape
	 * @return
	 */
	public static int skillcapeEmote(int cape) {
		int capeEmote[][] = { { 9747, 4959 }, { 9748, 4959 }, { 9750, 4981 },
				{ 9751, 4981 }, { 9753, 4961 }, { 9754, 4961 }, { 9756, 4973 },
				{ 9757, 4973 }, { 9759, 4979 }, { 9760, 4979 }, { 9762, 4939 },
				{ 9763, 4939 }, { 9765, 4947 }, { 9766, 4947 }, { 9768, 4971 },
				{ 9769, 4971 }, { 9771, 4977 }, { 9772, 4977 }, { 9774, 4969 },
				{ 9775, 4969 }, { 9777, 4965 }, { 9778, 4965 }, { 9780, 4949 },
				{ 9781, 4949 }, { 9783, 4937 }, { 9784, 4937 }, { 9786, 4967 },
				{ 9787, 4967 }, { 9789, 4953 }, { 9790, 4953 }, { 9792, 4941 },
				{ 9793, 4941 }, { 9795, 4943 }, { 9796, 4943 }, { 9798, 4951 },
				{ 9799, 4951 }, { 9801, 4955 }, { 9802, 4955 }, { 9804, 4975 },
				{ 9805, 4975 }, { 9807, 4957 }, { 9808, 4957 }, { 9810, 4963 },
				{ 9811, 4963 }, { 10662, 4945 } };
		for (int i = 0; i < capeEmote.length; i++) {
			if (capeEmote[i][0] == cape) {
				return capeEmote[i][1];
			}
		}
		return -1;
	}

	/**
	 * Does the skillcape emote
	 * @param player
	 */
	public static void doEmote(Client player) {
		if (System.currentTimeMillis() - player.skillcapeDelay <= 3000) {
			player.sendMessage("Please wait a couple of seconds before doing this!");
			return;
		}
		if (player.inWild()) {
			player.sendMessage("You cannot do skillcape emotes in the wilderness!");
			return;
		}
		if (System.currentTimeMillis() - player.logoutDelay < 8000) {
			player.sendMessage("You cannot do skillcape emotes in combat!");
			return;
		}
		if (wearingCape(player.playerEquipment[PlayerConfiguration.EQUIPMENT_CAPE])) {
			player.stopMovement();
			player.gfx0(skillcapeGfx(player.playerEquipment[PlayerConfiguration.EQUIPMENT_CAPE]));
			player.startAnimation(skillcapeEmote(player.playerEquipment[PlayerConfiguration.EQUIPMENT_CAPE]));
			player.skillcapeDelay = System.currentTimeMillis();
		} else {
			player.sendMessage("You must be wearing a skillcape to do this emote!");
		}
	}
	
}