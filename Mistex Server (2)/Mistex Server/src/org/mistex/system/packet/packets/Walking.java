package org.mistex.system.packet.packets;

import org.mistex.game.world.World;
import org.mistex.game.world.content.Resting;
import org.mistex.game.world.content.skill.SkillHandler;
import org.mistex.game.world.content.skill.agility.GnomeCourse;
import org.mistex.game.world.player.Client;
import org.mistex.game.world.player.PlayerConfiguration;
import org.mistex.game.world.shop.ShopExecutor;
import org.mistex.system.packet.PacketType;

public class Walking implements PacketType {

	@Override
	public void processPacket(Client c, int packetType, int packetSize) {
		/**
		 * Skilling
		 */
		if (c.isSkilling)
			SkillHandler.resetPlayerVariables(c);
		
//		if (c.isResting) {
//			Resting.stopResting(c);
//			return;
//		}
		
		if (c.rest != null) {
			Resting.endRest(c);
		}
		
		/**
		 * Bank
		 */
		if (c.isBanking) {
			c.isBanking = false;
		}

		/**
		 * Beast of Burden
		 */
		if (c.usingBoB) {
			c.usingBoB = false;
		}

		/**
		 * Overload
		 */
		if (c.hasOverloadBoost) {
			c.hasOverloadBoost = false;
			c.getPA().refreshAll();
			c.sendMessage("As you enter the wilderness the effects of the overload disappears.");
		}
		/**
		 * Can walk
		 */
		if (c.canWalk == false) {
			return;
		}

		/**
		 * Training prayer
		 */
		if (c.trainingPrayer) {
			c.trainingPrayer = false;
		}

		/**
		 * Barrows
		 */
		if (c.getBarrows().cantWalk) {
			c.getBarrows().challengeMinigame();
			return;
		}

		/**
		 * Agility
		 */
		if (c.doingAgility) {
			c.doingAgility = false;
			GnomeCourse.resetAgilityWalk(c);
		}

		/**
		 * Shop interface
		 */
		if (c.shopInterfaceOpen) {
			ShopExecutor.close(c);
		}

		/**
		 * Crafting
		 */
		if (c.craftingTeletabs) {
			return;
		}

		/**
		 * Trading
		 */
		if (c.inTrade) {
			if (packetType == 248) {
				c.getTrade().declineTrade();
			}
			return;
		}

		/**
		 * Tutorial
		 */
		if (c.isDoingTutorial || !c.canPlayerMove) {
			return;
		}

		/**
		 * Dead
		 */
		if (c.isDead) {
			return;
		}

		/**
		 * Packet
		 */
		if (packetType == 248 || packetType == 164) {
			c.faceUpdate(0);
			c.npcIndex = 0;
			c.playerIndex = 0;
			if (c.followId > 0 || c.followId2 > 0 || c.follow2 > 0)
				c.getPA().resetFollow();
		}
		/**
		 * Banking and tutorial
		 */
		if (c.isBanking || c.isDoingTutorial) {
			c.getPA().removeAllWindows();
		}

		/**
		 * Player skilling
		 */
		if (c.stopPlayerSkill) {
			c.stopPlayerSkill = false;
		}

		/**
		 * Trading
		 */
		if (c.inTrade) {
			c.sendMessage("You can\'t walk while in a trade.");
		} else {
			if (c.duelStatus > 0 && !c.inDuelArena()) {
				c.duelStatus = 0;
				c.duelingWith = 0;
			}
		}
		if (c.tradeStatus >= 0) {
			c.tradeStatus = 0;
		}

		/**
		 * Freeze timer
		 */
		if (c.freezeTimer > 0) {
			if (World.players[c.playerIndex] != null) {
				if (c.goodDistance(c.getX(), c.getY(), World.players[c.playerIndex].getX(), World.players[c.playerIndex].getY(), 1) && packetType != 98) {
					c.playerIndex = 0;
					return;
				}
			}
			if (packetType != 98) {
				c.sendMessage("A magical force stops you from moving.");
				c.playerIndex = 0;
			}
			return;
		}

		/**
		 * Spear
		 */
		if (System.currentTimeMillis() - c.lastSpear < 4000) {
			c.sendMessage("You have been stunned.");
			c.playerIndex = 0;
			return;
		}

		/**
		 * Packet 98
		 */
		if (packetType == 98) {
			c.mageAllowed = true;
		}

		/**
		 * Duel arena
		 */
		if (c.duelStatus == 6) {
			c.getDuel().claimStakedItems();
			return;
		}
		if (c.duelRule[1] && c.duelStatus == 5) {
			if (World.players[c.duelingWith] != null) {
				if (!c.goodDistance(c.getX(), c.getY(), World.players[c.duelingWith].getX(), World.players[c.duelingWith].getY(), 1) || c.attackTimer == 0) {
					c.sendMessage("Walking has been disabled in this duel!");
				}
			}
			c.playerIndex = 0;
			return;
		}
		if (c.duelStatus >= 1 && c.duelStatus <= 4) {
			Client o = (Client) World.players[c.duelingWith];
			c.duelStatus = 0;
			o.duelStatus = 0;
			o.getDuel().declineDuel();
			c.getDuel().declineDuel();
		}

		/**
		 * Respawn timer
		 */
		if (c.respawnTimer > 3) {
			return;
		}

		/**
		 * Packet 248
		 */
		if (packetType == 248) {
			packetSize -= 14;
		}
		
        if(c.openDuel && c.duelStatus != 5) {
            Client o = (Client) World.players[c.duelingWith];
            if(o != null)
                o.getDuel().declineDuel();
            c.getDuel().declineDuel();
        }
        if((c.duelStatus >= 1 && c.duelStatus <= 4) || c.duelStatus == 6) {
            if(c.duelStatus == 6) {
                c.getDuel().claimStakedItems();        
            }
            return;
        }
        if(c.isBanking || c.isShopping || c.inTrade)
            c.isBanking = c.isShopping = c.inTrade = false;
		/**
		 * Closes all the windows
		 */
		c.getPA().removeAllWindows();

		c.newWalkCmdSteps = (packetSize - 5) / 2;
		if (++c.newWalkCmdSteps > c.walkingQueueSize) {
			c.newWalkCmdSteps = 0;
			return;
		}

		c.getNewWalkCmdX()[0] = 0;
		c.getNewWalkCmdY()[0] = 0;
		int firstStepX = c.getInStream().readSignedWordBigEndianA() - c.getMapRegionX() * 8;
		for (int i = 1; i < c.newWalkCmdSteps; i++) {
			c.getNewWalkCmdX()[i] = c.getInStream().readSignedByte();
			c.getNewWalkCmdY()[i] = c.getInStream().readSignedByte();
		}
		int firstStepY = c.getInStream().readSignedWordBigEndian() - c.getMapRegionY() * 8;
		c.setNewWalkCmdIsRunning((c.getInStream().readSignedByteC() == 1));
		for (int i1 = 0; i1 < c.newWalkCmdSteps; i1++) {
			c.getNewWalkCmdX()[i1] += firstStepX; 
			c.getNewWalkCmdY()[i1] += firstStepY;
		}
	}

}