package org.mistex.game.world.content.pvp;

import org.mistex.game.MistexUtility;
import org.mistex.game.world.World;
import org.mistex.game.world.player.Client;
import org.mistex.game.world.player.PlayerAssistant;

public class Killstreak {
	private Client c;
	public static int[] points = { 5, 10, 15, 20, 25, 35, 40, 45, 50, 55, 60, 65, 70, 75, 80, 85, 90, 95, 100 };

	public Killstreak(Client c) {
		this.c = c;
	}

	public void Rewards() {
		Client o = (Client) World.players[c.killerId];
		for (int index : points) {
			if ((o.killStreak & points.length - 1) == points[index]) {
				c.pkPoints += points[index];
			}
		}
	}

	public void checkKillStreak() {
		for (int index : points) {
			if ((c.killStreak & points.length - 1) == index) {
				PlayerAssistant.yell("[ <col=E03F3F>Killstreak</col> ] <col=E03F3F>" + MistexUtility.capitalize(c.playerName) + " </col>is on a killing streak of <col=E03F3F>" + points[index] + " </col>! Bounty: <col=E03F3F>" + points[index] + " </col>PkPoints!");
			}
		}
	}

	public void killedPlayer() {
		Client o = (Client) World.players[c.killerId];
		if (o != null && (o.killStreak >= 2 || c.killerId != o.playerId)) {
			PlayerAssistant.yell("[ <col=E03F3F>Killstreak</col> ] <col=E03F3F>" + MistexUtility.capitalize(c.playerName) + " </col>has ended <col=E03F3F>" + MistexUtility.capitalize(o.playerName) + "</col>'s killing streak of </col>" + o.killStreak + "</col>!");
			Rewards();
		}
	}
}
