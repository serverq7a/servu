package org.mistex.game.world.content.skill.slayer;

import org.mistex.game.MistexUtility;
import org.mistex.game.world.npc.NPCHandler;
import org.mistex.game.world.player.Client;
import org.mistex.game.world.player.Player;

public class DuoSlayer {
	
	/**
	 * Instances the DuoSlayer class
	 */
	public static DuoSlayer INSTANCE = new DuoSlayer();

	/**
	 * This array holds the values of possible assignments.
	 */
	private static int[] tasks = { 
			2783, 1615, 1591, 10775,
			1592, 10775, 4705, 54, 1618, 55, 1610, 83,
			49, 2607, 941, 3073, 8349, 50, 1341, 5666, 82, 83, 84, 112, 110, 111,53,1610,1613,1615,55,84
	};

	public static void cancelTask(Client c) {
		if (c.getDuoPartner() == null ) {
			c.sendMessage("<col=D93030>You don't have a duo partner therefore cannot do this.");
			c.getPA().closeAllWindows();
			return;
		}
		if (c.duoPoints < 10) {
			c.sendMessage("<col=D93030>You have insufficient Duo Slayer Points to do this.");
			c.getPA().closeAllWindows();
			return;
		}
		c.duoPoints -= 10;
		c.duoTaskAmount = -1;
		c.duoTask = -1;
		final Player partner = c.getDuoPartner();
		Client other = (Client) partner;
		if (other != null) {
			other.duoTaskAmount = -1;
			other.duoTask = -1;
			other.sendMessage("<col=D93030>Your partner canceled your duo task (-10 Duo Points), you are free to get a new one.");
		}
		c.sendMessage("<col=D93030>Your task has been cancelled (-10 Duo Points), you are free to get a new one.");
	}

	/**
	 * Gets the DuoSlayer instance
	 * 
	 * @return
	 */
	public static DuoSlayer getInstance() {
		return INSTANCE;
	}

	/**
	 * Accept DuoSlayer request.
	 * 
	 * @param client
	 * @param partner
	 */
	public void accept(Client c, Player partner) {
		Client other = (Client) partner;
		c.setDuoPartner(partner);
		partner.setDuoPartner(c);
		c.sendMessage("<col=D93030>You are now doing a slayer duo with : "+ partner.playerName + ".");
		c.sendMessage("<col=D93030>Please visit Vannaka in Edgeville to start your task.");
		if (other != null) {
			other.sendMessage("<col=D93030>"+MistexUtility.capitalize(c.playerName) + " has accepted your request.");
			other.sendMessage("<col=D93030>Please visit Vannaka in Edgeville to start your task.");
		}
		c.getPA().closeAllWindows();

	}

	/**
	 * Assigns the DuoSlayer task.
	 * 
	 * @param client
	 */
	public void assignDuo(Client client) {
		if (client.duoTask > 0) {
			((Client) client).sendMessage("<col=D93030>You already have a duo slayer task.");
			((Client) client).getPA().closeAllWindows();
			return;
		}
		if (client.duoPartner == null) {
			((Client) client).sendMessage("<col=D93030>You don't have a duo partner, use an enchanted gem on someone to invite them.");
			((Client) client).getPA().closeAllWindows();
			return;
		}
		final int randomTask = tasks[MistexUtility.random(tasks.length - 1)];
		String npcDef = NPCHandler.getNpcListName(randomTask).replaceAll("_", " ");
		if (npcDef == null) {
			assignDuo(client);
			return;
		}
		final Player partna = client.getDuoPartner();
		for (int i = 0; i < NPCHandler.slayerReqs.length; i = i + 2)
			if (NPCHandler.slayerReqs[i] == randomTask) {
				if (client.playerLevel[18] < NPCHandler.slayerReqs[i + 1]) {
					assignDuo(client);
					return;
				}
				if (partna != null) {
					if (partna.playerLevel[18] < NPCHandler.slayerReqs[i + 1])
						assignDuo(client);
					return;
				}
			}
		client.duoTask = randomTask;
		client.duoTaskAmount = 50 + MistexUtility.random(30);
		System.out.println("client duotask: " +client.duoTask+ "duotaskamount: " +client.duoTaskAmount);
		if (partna != null) {
			client.getDuoPartner().duoTaskAmount = client.duoTaskAmount;
			client.getDuoPartner().duoTask = randomTask;
			System.out.println("partner duotask: " +client.getDuoPartner().duoTask+ "partner duotaskamount: " +client.getDuoPartner().duoTaskAmount);
		}
		client.getDH().sendNpcChat2("Your dual slayer task is to kill " + client.duoTaskAmount,"" + npcDef + ". Good luck to you and your partner.",1597, "Vannaka");
		if (partna != null)
			((Client) partna).getDH().sendNpcChat2("Your dual slayer task is to kill "+ client.getDuoPartner().duoTaskAmount,"" + npcDef + ". Good luck to you and your partner.",1597, "Vannaka");
	}

	/**
	 * Complete a duo slayer task.
	 * 
	 * @param Player
	 * @param partner
	 */
	public void complete(Client client, Player partner) {
		int rewardTokens = client.playerLevel[18];
		if (partner != null)
			rewardTokens += partner.playerLevel[18];
		rewardTokens = rewardTokens / 40;
		rewardTokens = rewardTokens + 3;
		client.duoTaskAmount = -1;
		client.duoTask = -1;
		client.slayerPoints = client.slayerPoints + rewardTokens;
		client.tasksCompleted++;
		((Client) client).sendMessage("<col=D93030>Congratulations, you've completed a duo slayer task!");
		((Client) client).sendMessage("<col=D93030>You now have been awarded " + rewardTokens + " slayer points and you now have" + client.slayerPoints + " slayer points.");
		client.duoPartner = null;
		client.potentialPartner = null;
		if (partner != null) {
			partner.duoTaskAmount = -1;
			partner.duoTask = -1;
			partner.slayerPoints = partner.duoPoints + rewardTokens;
			((Client) partner).sendMessage("<col=D93030>Congratulations, you've completed a duo slayer task!");
			((Client) partner).sendMessage("<col=D93030>You now have been awarded " + rewardTokens + " slayer points and you now have"+ partner.slayerPoints + " slayer points.");
			partner.duoPartner = null;
			partner.potentialPartner = null;
		}

	}

	/**
	 * Declined duo slayer Request.
	 * 
	 * @param client
	 * @param partner
	 */
	public void decline(Client c, Player partner) {
		Client other = (Client) partner;
		c.potentialPartner = null;
		if (other != null)
		c.sendMessage("<col=D93030>You decline the request of: " + other.playerName + ".");
		c.getPA().closeAllWindows();
		if (partner != null)
		((Client) partner).sendMessage("<col=D93030>"+c.playerName + " has declined your Duo Slayer Request.");
		((Client) partner).getPA().closeAllWindows();
	}

	/**
	 * Handles inviting
	 * 
	 * @param client
	 * @param partner
	 */

	public void invite(Client client, Player partner) {
		if (client == null || partner == null)
			return;
		final Player tempPartner = client.getDuoPartner();
		if (tempPartner != null) {
			((Client) client).sendMessage("<col=D93030>You already have a duo partner; "+ client.getDuoPartner().playerName + ".");
			return;
		}
		((Client) client).sendMessage("<col=D93030>Sending slayer task request...");
		((Client) partner).getDH().sendSpecialOption2("Yes, I would like to do a slayer duo with "+ client.playerName + ".", "No Thanks.", "Duo Partner?");
		partner.dialogueAction = 879;
		partner.setPotentialPartner(client);
	}

}
