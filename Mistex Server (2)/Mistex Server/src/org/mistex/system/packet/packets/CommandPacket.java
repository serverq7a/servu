package org.mistex.system.packet.packets;

import org.mistex.game.world.player.Client;
import org.mistex.game.world.player.command.AdministratorCommands;
import org.mistex.game.world.player.command.DeveloperCommands;
import org.mistex.game.world.player.command.DonatorCommands;
import org.mistex.game.world.player.command.ModeratorCommands;
import org.mistex.game.world.player.command.PlayerCommands;
import org.mistex.system.packet.PacketType;


public class CommandPacket implements PacketType {

	public static Client c;

	public CommandPacket(Client c) {
		CommandPacket.c = c;
	}

	public static String playerCommand;

	@Override
	public void processPacket(Client c, int packetType, int packetSize) {
		/*playerCommand = c.getInStream().readString();
		if (RegularPlayer.is(c)) {
			RegularPlayer.readCommands(c);
		} else if (Moderator.is(c)) {
			Moderator.readCommands(c);
		} else if (Administrator.is(c)) {
			Administrator.readCommands(c);
		} else if (Developer.is(c)) {
			Developer.readCommands(c);
		} else if (Donator.is(c)) {
			Donator.readCommands(c);
		} else if (SuperDonator.is(c)) {
			SuperDonator.readCommands(c);
		} else if (ExtremeDonator.is(c)) {
			ExtremeDonator.readCommands(c);
		} else if (MistexSupporter.is(c)) {
			MistexSupporter.readCommands(c);
		}*/
		  String playerCommand = c.getInStream().readString();
		  //if (!playerCommand.startsWith("/") && !playerCommand.startsWith("yell")) {
	         //   c.getPA().writeCommandLog(playerCommand);
	       // }
	        
	    if (c.playerRights == 0 || c.playerRights == 1 || c.playerRights == 2 || c.playerRights == 3 || c.playerRights == 4 || c.playerRights == 5 || c.playerRights == 6 || c.playerRights == 7 || c.playerRights == 8|| c.playerRights == 9|| c.playerRights == 20)
	    	PlayerCommands.commands(c, playerCommand);
	    if (c.playerRights == 1 || c.playerRights == 2 || c.playerRights == 3)
	        ModeratorCommands.moderatorCommands(c, playerCommand);
	    if (c.playerRights == 2 || c.playerRights == 3)
	    	AdministratorCommands.administratorCommands(c, playerCommand);
	    if (c.playerRights == 3)
	        DeveloperCommands.ownerCommands(c, playerCommand);
	    if (c.playerRights == 1 || c.playerRights == 2 || c.playerRights == 3 || c.playerRights == 4 || c.playerRights == 5 || c.playerRights == 6 || c.playerRights == 7)
	    	 DonatorCommands.donatorCommands(c, playerCommand);
	}
}

