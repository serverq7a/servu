package org.mistex.game.world.content.skill.mining;

import org.mistex.game.world.player.Client;
import org.mistex.game.world.player.Player;

public enum Pickaxe {
	DRAGON(15259, 12188, 61),
	INFERNO_ADZ(13661, 10249, 41),
	RUNE(1275, 6746, 41),
	ADAMANT(1271, 6750, 31),
	MITHRIL(1273, 6751, 21),
	STEEL(1269, 6749, 6),
	IRON(1267, 6748, 1),
	BRONZE(1265, 6747, 1);

	int item, animation, levelReq;

	private Pickaxe(int item, int animation, int levelReq) {
		this.item = item;
		this.animation = animation;
		this.levelReq = levelReq;
	}

	public int getAnimation() {
		return animation;
	}

	public int getItem() {
		return item;
	}

	public int getLevel() {
		return levelReq;
	}

	public static Pickaxe getPickaxe(Client c) {
		Pickaxe unable = null;
		for (Pickaxe data : values()) {
			if (c.getItems().playerHasItem(data.getItem()) || c.getItems().isWearingItem(data.getItem())) {
				if (data.getLevel() > c.playerLevel[Player.playerMining]) {
					unable = data;
					continue;
				}
				return data;
			}
		}
		if (unable != null)
			c.sendMessage("You need a mining level of " + unable.getLevel() + " to use this pickaxe.");
		else
			c.sendMessage("You need a pickaxe to mine this rock.");
		return null;
	}
}