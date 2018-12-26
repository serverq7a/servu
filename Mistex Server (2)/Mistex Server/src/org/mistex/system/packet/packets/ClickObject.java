package org.mistex.system.packet.packets;

import org.mistex.game.MistexUtility;
import org.mistex.game.world.Position;
import org.mistex.game.world.content.skill.runecrafting.Runecrafting;
import org.mistex.game.world.content.skill.thieving.Thieving;
import org.mistex.game.world.content.skill.thieving.WallSafes;
import org.mistex.game.world.player.Client;
import org.mistex.system.packet.PacketType;

public class ClickObject implements PacketType {

	public static final int FIRST_CLICK = 132, SECOND_CLICK = 252,
			THIRD_CLICK = 70;

	@Override
	public void processPacket(Client c, int packetType, int packetSize) {
		c.clickObjectType = c.objectX = c.objectId = c.objectY = 0;
		c.objectYOffset = c.objectXOffset = 0;
		c.getPA().resetFollow();
		switch (packetType) {

		case FIRST_CLICK:
			c.objectX = c.getInStream().readSignedWordBigEndianA();
			c.objectId = c.getInStream().readUnsignedWord();
			c.objectY = c.getInStream().readUnsignedWordA();
//			if (!Region.objectExists(c.objectId, c.objectX, c.objectY, c.heightLevel)) {
//				c.sendMessage("Object does not exist.");
//				return;
//			}
			c.objectDistance = 1;
			c.agX = c.getX() - c.objectX;
			c.agY = c.getY() - c.objectY;
			 if (c.playerRights == 3) {
				c.sendMessage("objectId: " + c.objectId + " objectX: "+ c.objectX + " objectY: " + c.objectY);
			}
			c.turnPlayerTo(c.objectX, c.objectY);
			Runecrafting.craftRunes(c, c.objectId);
			if (Math.abs(c.getX() - c.objectX) > 25 || Math.abs(c.getY() - c.objectY) > 25) {
				c.resetWalkingQueue();
				break;
			}
			
			switch (c.objectId) {
			case 6:
                c.getCannon().shoot(new Position(c.objectX, c.objectY, c.heightLevel));
                break;
			case 4019:
				c.getPA().showInterface(39700);
				break;
			case 3515:
				if (!c.getPA().isMaxed()) {
					c.getPA().sendStatement("You must have all stats 99 to do this!");
					return;
				}
				if (!(c.achievementsCompleted == c.totalAchievements)) {
					c.getPA().sendStatement("You must have completed all the achievements!");
					return;
				}
				if (!c.getItems().playerHasItem(995, 10000000)) {
					c.getPA().sendStatement("You must have 10 million coins to do this!");
					return;
				}
				if (c.getItems().playerHasItem(995, 10000000)) {
					c.getItems().deleteItem(995, 10000000);
					c.getItems().addItem(14001, 1);
					c.getPA().sendStatement("You have been rewarded with a Completionist Cape!");
					c.gotCompletionist = true;
					//if (c.gotCompletionist)
						//AchievementHandler.activateAchievement(c, AchievementList.MYSTIC);
				} else {
					c.sendMessage("Something went wrong!");
				}
				break;
			case WallSafes.WALL_SAFE:
				WallSafes.checkEnabled();
				if (c.playerLevel[17] >= WallSafes.LEVEL_REQUIRED) {
					if (System.currentTimeMillis() - c.lastThieve < 2500)
						return;
					c.lastThieve = System.currentTimeMillis();
					c.turnPlayerTo(c.objectX, c.objectY);
					WallSafes.getRandomReward(c);
				} else {
					c.sendMessage(WallSafes.AT_LEAST);
				}
				break;
		
			case 3192:
				c.objectDistance = 2;
				break;
			case 1733:
				c.objectYOffset = 2;
				break;

			case 3044:
				c.objectDistance = 3;
				break;

			case 272:
				c.objectYOffset = 1;
				c.objectDistance = 0;
				break;

			case 273:
				c.objectYOffset = 1;
				c.objectDistance = 0;
				break;

			case 246:
				c.objectYOffset = 1;
				c.objectDistance = 0;
				break;

			case 4493:
			case 4494:
			case 4496:
			case 4495:
				c.objectDistance = 5;
				break;
			case 10229:
			case 6522:
				c.objectDistance = 2;
				break;
			case 8959:
				c.objectYOffset = 1;
				break;
			case 4417:
				if (c.objectX == 2425 && c.objectY == 3074)
					c.objectYOffset = 2;
				break;
			case 4420:
				if (c.getX() >= 2383 && c.getX() <= 2385) {
					c.objectYOffset = 1;
				} else {
					c.objectYOffset = -2;
				}
			case 6552:
			case 409:
				c.objectDistance = 2;
				break;
			case 2879:
			case 2878:
				c.objectDistance = 3;
				break;
			case 2557:
				c.objectDistance = 0;
				if (c.absX > c.objectX && c.objectX == 3044)
					c.objectXOffset = 1;
				if (c.absY > c.objectY)
					c.objectYOffset = 1;
				if (c.absX < c.objectX && c.objectX == 3038)
					c.objectXOffset = -1;
				break;
			case 9356:
				c.objectDistance = 2;
				break;
			case 5959:
			case 1815:
			case 5960:
			case 1816:
				c.objectDistance = 0;
				break;

			case 9293:
				c.objectDistance = 2;
				break;
			case 4418:
				if (c.objectX == 2374 && c.objectY == 3131)
					c.objectYOffset = -2;
				else if (c.objectX == 2369 && c.objectY == 3126)
					c.objectXOffset = 2;
				else if (c.objectX == 2380 && c.objectY == 3127)
					c.objectYOffset = 2;
				else if (c.objectX == 2369 && c.objectY == 3126)
					c.objectXOffset = 2;
				else if (c.objectX == 2374 && c.objectY == 3131)
					c.objectYOffset = -2;
				break;
			case 9706:
				c.objectDistance = 0;
				c.objectXOffset = 1;
				break;
			case 9707:
				c.objectDistance = 0;
				c.objectYOffset = -1;
				break;

			case 13999:
				c.getPA().startTeleport(3087, 3498, 0, "modern");
				c.teleportToX = 3093;
				c.teleportToY = 3487;

				break;
			case 4419:
			case 6707: // verac
				c.objectYOffset = 3;
				break;
			case 6823:
				c.objectDistance = 2;
				c.objectYOffset = 1;
				break;

			case 6706: // torag
				c.objectXOffset = 2;
				break;
			case 6772:
				c.objectDistance = 2;
				c.objectYOffset = 1;
				break;

			case 6705: // karils
				c.objectYOffset = -1;
				break;
			case 6822:
				c.objectDistance = 2;
				c.objectYOffset = 1;
				break;

			case 6704: // guthan stairs
				c.objectYOffset = -1;
				break;
			case 6773:
				c.objectDistance = 2;
				c.objectXOffset = 1;
				c.objectYOffset = 1;
				break;

			case 6703: // dharok stairs
				c.objectXOffset = -1;
				break;
			case 6771:
				c.objectDistance = 2;
				c.objectXOffset = 1;
				c.objectYOffset = 1;
				break;

			case 6702: // ahrim stairs
				c.objectXOffset = -1;
				break;
			case 6821:
				c.objectDistance = 2;
				c.objectXOffset = 1;
				c.objectYOffset = 1;
				break;
			case 1276:
			case 1278:// trees
			case 1281: // oak
			case 1308: // willow
			case 1307: // maple
			case 1309: // yew
			case 1306: // yew
				c.objectDistance = 3;
				break;
			default:
				c.objectDistance = 1;
				c.objectXOffset = 0;
				c.objectYOffset = 0;
				break;
			}
			if (c.goodDistance(c.objectX + c.objectXOffset, c.objectY+ c.objectYOffset, c.getX(), c.getY(), c.objectDistance)) {
				c.getActions().firstClickObject(c.objectId, c.objectX,c.objectY);
			} else {
				c.clickObjectType = 1;
			}
			break;

		case SECOND_CLICK:
			c.objectId = c.getInStream().readUnsignedWordBigEndianA();
			c.objectY = c.getInStream().readSignedWordBigEndian();
			c.objectX = c.getInStream().readUnsignedWordA();
			c.objectDistance = 1;
//			if (!Region.objectExists(c.objectId, c.objectX, c.objectY, c.heightLevel)) {
//				c.sendMessage("Object does not exist.");
//				return;
//			}
			c.turnPlayerTo(c.objectX, c.objectY);
			if (c.playerRights == 3) {
				MistexUtility.println("objectId: " + c.objectId + "  ObjectX: "+ c.objectX + "  objectY: " + c.objectY + " Xoff: "+ (c.getX() - c.objectX) + " Yoff: "+ (c.getY() - c.objectY));
			}
			Thieving.getActionButtons(c, c.objectId);

			switch (c.objectId) {
			case 4019:
				c.getPA().showInterface(38700);
				break;
            case 6:
                c.getCannon().pickup(new Position(c.objectX, c.objectY, c.heightLevel));
                break;
			case 6163:
			case 6165:
			case 6166:
			case 6164:
			case 6162:
				c.objectDistance = 2;
				break;
			default:
				c.objectDistance = 1;
				c.objectXOffset = 0;
				c.objectYOffset = 0;
				break;

			}
			if (c.goodDistance(c.objectX + c.objectXOffset, c.objectY
					+ c.objectYOffset, c.getX(), c.getY(), c.objectDistance)) {
				c.getActions().secondClickObject(c.objectId, c.objectX,
						c.objectY);
			} else {
				c.clickObjectType = 2;
			}
			break;

		case THIRD_CLICK:
			c.objectX = c.getInStream().readSignedWordBigEndian();
			c.objectY = c.getInStream().readUnsignedWord();
			c.objectId = c.getInStream().readUnsignedWordBigEndianA();

//			if (!Region.objectExists(c.objectId, c.objectX, c.objectY, c.heightLevel)) {
//				c.sendMessage("Object does not exist.");
//				return;
//			}
			
			if (c.playerRights == 3) {
				MistexUtility.println("objectId: " + c.objectId + "  ObjectX: "
						+ c.objectX + "  objectY: " + c.objectY + " Xoff: "
						+ (c.getX() - c.objectX) + " Yoff: "
						+ (c.getY() - c.objectY));
			}
			switch (c.objectId) {
			default:
				c.objectDistance = 1;
				c.objectXOffset = 0;
				c.objectYOffset = 0;
				break;
			}
			if (c.goodDistance(c.objectX + c.objectXOffset, c.objectY
					+ c.objectYOffset, c.getX(), c.getY(), c.objectDistance)) {
				c.getActions().secondClickObject(c.objectId, c.objectX,
						c.objectY);
			} else {
				c.clickObjectType = 3;
			}
			break;
		}

	}

	public void handleSpecialCase(Client c, int id, int x, int y) {

	}

}
