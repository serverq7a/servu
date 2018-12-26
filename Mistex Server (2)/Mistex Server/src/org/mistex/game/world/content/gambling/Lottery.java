package org.mistex.game.world.content.gambling;

import java.util.ArrayList;
import java.util.List;

import org.mistex.game.Mistex;
import org.mistex.game.world.player.Client;
import org.mistex.game.world.player.PlayerAssistant;
import org.mistex.game.world.task.Task;

public class Lottery {

	private static int totalPot;
	private static int time = 5;

	private static List<Client> contestants = new ArrayList<Client>();

	public Lottery() {
		Mistex.getTaskScheduler().schedule(new Task(100) {
			
			@Override
			protected void execute() {
				PlayerAssistant.yell("[ <col=C42BAD>Mistex</col> ] The lottery is at <col=C42BAD>" + totalPot + "</col> gp! The next winner will be picked in " + time + " minute" + (time > 1 ? "s" : ""));
			}
		});
	}
	
	public static void enterLottery(Client c, int amount) {
		if (!contestants.contains(c)) {
			contestants.add(c);
			totalPot += amount;
		}
	}
}