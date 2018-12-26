package org.mistex.system.packet.packets;

import java.text.DecimalFormat;

import org.mistex.game.Mistex;
import org.mistex.game.MistexConfiguration;
import org.mistex.game.MistexUtility;
import org.mistex.game.world.World;
import org.mistex.game.world.content.Miscellaneous;
import org.mistex.game.world.content.Resting;
import org.mistex.game.world.content.Skillcapes;
import org.mistex.game.world.content.TeleportingTabHandler;
import org.mistex.game.world.content.achievement.AchievementButtons;
import org.mistex.game.world.content.achievement.AchievementHandler;
import org.mistex.game.world.content.achievement.AchievementList;
import org.mistex.game.world.content.clanchat.ClanButtons;
import org.mistex.game.world.content.dialogue.DialogueButtons;
import org.mistex.game.world.content.interfaces.InterfaceText;
import org.mistex.game.world.content.interfaces.impl.AchievementTab;
import org.mistex.game.world.content.interfaces.impl.CleanedPlayerProfilerTab;
import org.mistex.game.world.content.interfaces.impl.PlayerProfileTab;
import org.mistex.game.world.content.interfaces.impl.SlayerPanel;
import org.mistex.game.world.content.prayers.QuickPrayers;
import org.mistex.game.world.content.quests.QuestHandler;
import org.mistex.game.world.content.skill.SkillGuides;
import org.mistex.game.world.content.skill.SkillingTasks;
import org.mistex.game.world.content.skill.cooking.Cooking;
import org.mistex.game.world.content.skill.crafting.LeatherMaking;
import org.mistex.game.world.content.skill.crafting.Tanning;
import org.mistex.game.world.content.skill.fletching.BowHandler;
import org.mistex.game.world.content.skill.fletching.StringingHandler;
import org.mistex.game.world.content.skill.herblore.Herblore;
import org.mistex.game.world.content.skill.prayer.Altar;
import org.mistex.game.world.content.skill.prayer.Constants;
import org.mistex.game.world.content.skill.smithing.Smelting;
import org.mistex.game.world.content.skill.summoning.SummoningButtons;
import org.mistex.game.world.event.CycleEvent;
import org.mistex.game.world.event.CycleEventContainer;
import org.mistex.game.world.event.CycleEventHandler;
import org.mistex.game.world.player.Client;
import org.mistex.game.world.player.PlayerConfiguration;
import org.mistex.game.world.player.bank.BankTab;
import org.mistex.game.world.player.item.GameItem;
import org.mistex.game.world.shop.ShopExecutor;
import org.mistex.system.packet.PacketType;

public class ClickingButtons implements PacketType {

	@SuppressWarnings({ "static-access", "null" })
	@Override
	public void processPacket(final Client c, int packetType, int packetSize) {
		int actionButtonId = MistexUtility.hexToInt(c.getInStream().buffer, 0, packetSize);
		if (c.isDead)
			return;
		if (c.playerRights == 3)
			c.sendMessage("@whi@Actionbutton: " + actionButtonId + " | Fight mode: " + c.fightMode + " | Dialogue action: " + c.dialogueAction);
		ClanButtons.handleClanButton(c, actionButtonId);
		DialogueButtons.handleClickButton(c, actionButtonId);
		AchievementButtons.handleClickButton(c, actionButtonId);
		TeleportingTabHandler.handleActionsButtons(c, actionButtonId);
		Smelting.getBar(c, actionButtonId);
		Cooking.handleButtons(c, actionButtonId);
		BowHandler.handleFletchingButtons(c, actionButtonId);
		QuestHandler.QuestButtons(c, actionButtonId);
		SummoningButtons.handleActionButtons(c, actionButtonId);
		if (c.getPA().handleGenieLamp(actionButtonId))
			return;
		if (actionButtonId >= 57200 && actionButtonId <= 57300) {
			Tanning.tanHide(c, actionButtonId);
		} else if (actionButtonId >= 33185 && actionButtonId <= 35010) {
			LeatherMaking.craftLeather(c, actionButtonId);
		}
		if (actionButtonId >= 195114 && actionButtonId <= 195130) {
			if (c.getBank().openBank((actionButtonId - 195114) / 2)) {
				return;
			}
		}
		if (actionButtonId >= 196060 && actionButtonId <= 196074) {
			BankTab tab = c.getBank().getTab((actionButtonId - 196060) / 2 + 1);
			if (tab.getItems().isEmpty()) //
				return;
			tab.collapse();
			c.getBank().openBank(0);
			return;
		}
		if (actionButtonId >= 67050 && actionButtonId <= 67075) {
			if (c.playerPrayerBook == false)
				QuickPrayers.clickPray(c, actionButtonId);
		}
		switch (actionButtonId) {
		case 19146:
			// Resting.startResting(c);
			Resting.beginRest(c);
			break;

		case 15062:
			ShopExecutor.close(c);
			break;
		case 19136:
			for (int index = 0; index < c.quickPrayers.length; index++) {
				if (c.quickPrayers[index])
					c.getCombat().activatePrayer(index);
			}
			break;
		case 67089:
			c.setSidebarInterface(5, 5608);
			break;

		case 136240:
			SkillingTasks.handlePanel(c);
			break;

		case 136239:
			InterfaceText.writeText(new SlayerPanel(c));
			c.getPA().sendFrame126("Slayer Panel", 8144);
			c.getPA().showInterface(8134);
			break;

		case 19137:
			QuickPrayers.loadCheckMarks(c);
			c.setSidebarInterface(5, 20000);
			c.getPA().sendFrame106(5);
			break;

		case 108154:
		case 184163:
			c.getPA().closeAllWindows();
			break;

		case 195090:
			if (c.enterdBankpin) {
				c.getDH().sendDialogues(1270, 494);
				return;
			}
			c.getBankPins().openPin();
			break;

		case 58074:
			c.getBankPins().closeBankPin();
			break;

		case 58073:
			if (c.hasBankpin && !c.requestPinDelete) {
				c.requestPinDelete = true;
				c.getBankPins().dateRequested();
				c.getBankPins().dateExpired();
				c.getDH().sendDialogues(1209, 1);
				c.sendMessage("@red@[Notice] A PIN delete has been requested. Your PIN will be deleted in " + c.getBankPins().recovery_Delay + " days.");
				c.sendMessage("@red@To cancel this change just type in the correct PIN.");
			} else {
				c.sendMessage("@red@[Notice] Your PIN is already pending deletion. Please wait the entire 2 days.");
				c.getPA().closeAllWindows();
			}
			break;

		case 58025:
		case 58026:
		case 58027:
		case 58028:
		case 58029:
		case 58030:
		case 58031:
		case 58032:
		case 58033:
		case 58034:
			c.getBankPins().bankPinEnter(actionButtonId);
			break;

		/** Specials **/
		case 29188:
			c.specBarId = 7636;
			c.usingSpecial = !c.usingSpecial;
			c.getItems().updateSpecialBar();
			break;
		case 29163:
			c.specBarId = 7611;
			c.usingSpecial = !c.usingSpecial;
			c.getItems().updateSpecialBar();
			break;
		case 33033:
			c.specBarId = 8505;
			c.usingSpecial = !c.usingSpecial;
			c.getItems().updateSpecialBar();
			break;
		case 29038:
			if (c.playerEquipment[c.playerWeapon] == 4153) {
				c.specBarId = 7486;
				c.getCombat().handleGmaulPlayer();
			} else {
				c.specBarId = 7486;
				c.usingSpecial = !c.usingSpecial;
				c.getItems().updateSpecialBar();
			}
			break;

		case 29063:
			if (c.getCombat().checkSpecAmount(c.playerEquipment[c.playerWeapon])) {
				c.gfx0(246);
				c.forcedChat("Raarrrrrgggggghhhhhhh!");
				c.startAnimation(1056);
				c.playerLevel[2] = c.getLevelForXP(c.playerXP[2]) + c.getLevelForXP(c.playerXP[2]) * 15 / 100;
				c.getPA().refreshSkill(2);
				c.getItems().updateSpecialBar();
			} else {
				c.sendMessage("You don't have the required special energy to use this attack.");
			}
			break;

		case 48023:
			c.specBarId = 12335;
			c.usingSpecial = !c.usingSpecial;
			c.getItems().updateSpecialBar();
			break;
		case 29138:
			c.specBarId = 7586;
			c.usingSpecial = !c.usingSpecial;
			c.getItems().updateSpecialBar();
			break;
		case 29113:
			c.specBarId = 7561;
			c.usingSpecial = !c.usingSpecial;
			c.getItems().updateSpecialBar();
			break;
		case 29238:
			c.specBarId = 7686;
			c.usingSpecial = !c.usingSpecial;
			c.getItems().updateSpecialBar();
			break;

		case 152:
			if (c.runEnergy < 1) {
				c.isRunning = false;
				c.getPA().setConfig(173, 0);
				return;
			}
			c.isRunning = !c.isRunning;
			c.getPA().setConfig(173, c.isRunning ? 0 : 1);
			break;
		case 10238:
			if (c.isStringing)
				StringingHandler.stringBow(c, c.stringu, 5);
			break;
		case 10239:
			if (c.isStringing)
				StringingHandler.stringBow(c, c.stringu, 1);
			break;
		case 118098:
			c.getPA().castVeng();
			break;

		case 6212:
			if (Constants.playerBones(c, c.boneId)) {
				Altar.sacrificeBone(c, 10);
				return;
			} else if (c.secondHerb) {
				Herblore.finishPotion(c, c.getItems().getItemAmount(c.newItem));
			} else {
				Herblore.finishUnfinished(c, c.getItems().getItemAmount(c.doingHerb));
			}
			break;

		case 6211:
			if (Constants.playerBones(c, c.boneId)) {
				Altar.sacrificeBone(c, 100);
				return;
			} else if (c.isStringing) {
				StringingHandler.stringBow(c, c.stringu, c.getItems().getItemAmount(c.stringu));
			} else if (c.secondHerb) {
				Herblore.finishPotion(c, c.getItems().getItemAmount(c.newItem));
			} else {
				Herblore.finishUnfinished(c, c.getItems().getItemAmount(c.doingHerb));
			}
			break;

		case 150:
		case 89061:
			if (c.autoRet == 1) {
				c.autoRet = 0;
			} else if (c.autoRet == 0) {
				c.autoRet = 1;
			}
			break;

		// case 118098:
		// c.getMagics().castVeng();
		// break;

		case 195145:
			c.getBank().openBank();
			c.getPA().sendFrame171(1, 50064);
			break;
		case 195110:
			c.getPA().sendFrame171(0, 50064);
			c.getPA().showInterface(15106);
			c.getItems().writeBonus();
			break;
		case 59097:
			c.getPA().sendFrame171(1, 50064);
			c.getPA().showInterface(15106);
			c.getItems().writeBonus();
			break;
		case 195093:
			c.getBank().setNoteWithdrawal(!c.getBank().withdrawAsNote());
			break;
		case 195084:
			c.getBank().setItemInserting(!c.getBank().insertItems());
			break;
		case 195101:
			c.getBank().depositInventory();
			break;
		case 195104:
			c.getBank().depositWornEquipment();
			c.getItems().setEquipment(c.playerEquipment[c.playerWeapon], c.playerEquipmentN[c.playerWeapon], c.playerWeapon);
			c.getCombat().getPlayerAnimIndex(c.getItems().getItemName(c.playerEquipment[c.playerWeapon]).toLowerCase());
			break;

		// case 154:
		case 74108:
			Skillcapes.doEmote(c);
			break;

		/** Toggles Tab **/
		case 177190:
			c.sendMessage(":HPBarToggle:");
			break;
		case 177193:
			c.sendMessage(":togglex10:");
			break;
		case 177196:
			c.sendMessage(":toggleCursors:");
			break;
		case 177199:
			c.sendMessage(":toggleMenus:");
			break;
		case 177202:
			c.sendMessage(":toggleCustomHD:");
			break;
		case 177205:
			c.sendMessage(":toggleHoverBox:");
			break;
		case 177208:
			c.sendMessage(":toggleHPOn:");
			break;
		case 185151:
			c.sendMessage(":toggleSkillOrbs:");
			break;
		case 185154:
			c.sendMessage(":toggleBuffering:");
			break;
		case 177211:
			Miscellaneous.togglesTab(c);
			c.setSidebarInterface(16, 47500);
			break;
		case 185163:
			c.setSidebarInterface(16, 45500);
			c.sendMessage(":setTexts:");
			break;
		case 185142:
			// c.wantTriviaBot = !c.wantTriviaBot;
			Miscellaneous.togglesTab(c);
			c.sendMessage("Still have to do this.");
			break;
		case 185145:
			c.sendMessage(":toggleTweening:");
			break;
		case 185148:
			c.wantYellChannel = !c.wantYellChannel;
			Miscellaneous.togglesTab(c);
			c.sendMessage(c.wantYellChannel == true ? "You have toggled the yell channel: @gre@ON" : "You have toggled the yell channel: @red@OFF");
			break;

		case 1093:
		case 1094:
		case 1097:
			if (c.autocastId > 0) {
				c.getPA().resetAutocast();
			} else {
				if (c.playerMagicBook == 1) {
					if (c.playerEquipment[c.playerWeapon] == 4675 || c.playerEquipment[c.playerWeapon] == 18355)
						c.setSidebarInterface(0, 1689);
					else
						c.sendMessage("You can't autocast ancients without an ancient staff.");
				} else if (c.playerMagicBook == 0) {
					if (c.playerEquipment[c.playerWeapon] == 4170) {
						c.setSidebarInterface(0, 12050);
					} else {
						c.setSidebarInterface(0, 1829);
					}
				}

			}
			break;

		case 14067:
			if (System.currentTimeMillis() - c.appearanceDelay >= 1000) {
				c.appearanceDelay = System.currentTimeMillis();
				c.getPA().removeAllWindows();
				c.sendMessage("You have succesfully changed your appearance. Aren't you sexy?");
				c.appearancesChanged += 1;
				if (c.appearancesChanged == 1)
					c.sendMessage("@war@You have started the achievement: Self-Conscious ");
				InterfaceText.writeText(new AchievementTab(c));
				if (c.appearancesChanged == 100)
					AchievementHandler.activateAchievement(c, AchievementList.SELF_CONSCIOUS);
			} else {
				c.sendMessage("@blu@Please wait a couple of seconds before doing this again!");
			}

			break;

		case 55096:
			c.getPA().removeAllWindows();
			// c.droppedItem = -1;
			break;

		case 55095:
			// DestroyItem.destroyItem(c, c.droppedItem);
			// c.droppedItem = -1;
			break;

		case 4171:
		case 50056:
		case 117048:
		case 75010:
		case 84237:
			if (System.currentTimeMillis() - c.teleportingDelay >= 7500) {
				String type = c.playerMagicBook == 0 ? "modern" : "ancient";
				c.getPA().startTeleport(PlayerConfiguration.START_LOCATION_X, PlayerConfiguration.START_LOCATION_Y, 0, type);
				c.teleportedHome += 1;
				c.teleportingDelay = System.currentTimeMillis();
				if (c.teleportedHome == 1)
					c.sendMessage("@war@You have started the achievement: Home Sick ");
				InterfaceText.writeText(new AchievementTab(c));
				if (c.teleportedHome == 500)
					AchievementHandler.activateAchievement(c, AchievementList.HOME_SICK);
			} else {
				c.sendMessage("Please wait a couple of seconds before doing this again!");
			}
			break;

		case 22230: // kick (unarmed
			c.fightMode = 0; // stregnth
			if (c.autocasting)
				c.getPA().resetAutocast();
			break;

		case 22228: // punch (unarmed
			c.fightMode = 1; // Defencive
			if (c.autocasting)
				c.getPA().resetAutocast();
			break;

		case 22229: // block (unarmed)
			c.fightMode = 2; // "Attack xp"
			if (c.autocasting)
				c.getPA().resetAutocast();
			break;

		// Attack
		case 9125: // Accurate
		case 48010: // flick (Abyssal Whip)
		case 14218: // pound (Mace)
		case 33020: // jab (Halberd)
		case 21200: // spike (Pickaxe)
		case 6168: // chop (Hatchet)
		case 8234: // stab (Daggers)
		case 17102: // accurate (All Darts)
		case 6236: // accurate (Long/Short-bow)
		case 1080: // bash (Staffs/Battlestaffs)
		case 6221: // range accurate
		case 30088: // claws (Chop)
		case 1177: // hammer (Pound)
			c.fightMode = 0;
			if (c.autocasting)
				c.getPA().resetAutocast();
			break;

		// Defence
		case 9126: // Defensive
		case 48008: // deflect (Abyssal Whip)
		case 1175: // block (Hammer)
		case 21201: // block (Pickaxe)
		case 14219: // block (Mace)
		case 1078: // focus - block (Staffs/Battlestaffs)
		case 33018: // fend (Hally)
		case 6169: // block (Hatechet)
		case 8235: // block (Daggers)
		case 18078: // block (Spear)
		case 30089: // block (Claws)
			c.fightMode = 1;
			if (c.autocasting)
				c.getPA().resetAutocast();
			break;
		// All
		case 9127: // Controlled
		case 14220: // Spike (Mace)
		case 6234: // longrange (Long/Short-bow)
		case 6219: // longrange
		case 18077: // lunge (Spear)
		case 18080: // swipe (Spear)
		case 18079: // pound (Spear)
		case 17100: // longrange (Darts)
			c.fightMode = 3;
			if (c.autocasting)
				c.getPA().resetAutocast();
			break;
		// Strength
		case 9128: // Aggressive
		case 14221: // Pummel(Mace)
		case 33019: // Swipe(Halberd)
		case 21203: // impale (Pickaxe)
		case 21202: // smash (Pickaxe)
		case 6171: // hack (Hatchet)
		case 6170: // smash (Axe)
		case 6220: // range rapid
		case 8236: // slash (Daggers)
		case 8237: // lunge (Daggers)
		case 30090: // claws (Lunge)
		case 30091: // claws (Slash)
		case 1176: // stat hammer
		case 1079: // pound (Staffs/Battlestaffs)
		case 6235: // rapid (Long/Short-bow)
		case 17101: // repid (Darts)
			c.fightMode = 2;
			if (c.autocasting)
				c.getPA().resetAutocast();
			break;

		/* Player Profiler */
		case 113076:
			if (c.canViewProfile == true) {
				c.canViewProfile = false;
				c.sendMessage("@ceo@Players may not view your profile!");
				c.getPA().sendFrame126("Enable Profile", 29004);
			} else if (c.canViewProfile == false) {
				c.canViewProfile = true;
				c.sendMessage("@ceo@Players may view your profile!");
				c.getPA().sendFrame126("Disable Profile", 29004);
			}
			break;

		case 113077:
			c.sendMessage("@ceo@::setage");
			c.sendMessage("@ceo@::setbplace");
			c.sendMessage("@ceo@::setaboutme");
			c.sendMessage("@ceo@::setname");
			break;

		case 113078:
			if (c.isViewingProfile == true) {
				InterfaceText.writeText(new CleanedPlayerProfilerTab(c));
				c.isViewingProfile = false;
				c.sendMessage("@ceo@Player profiler panel has been cleared!");
			} else {
				InterfaceText.writeText(new PlayerProfileTab(c));
				c.isViewingProfile = true;
			}
			break;

		case 113079:
			c.getDH().sendDialogues(50, -1);
			break;

		case 59103:
			// PriceChecker.open(c);
			c.sendMessage("Price checker will be coming soon!");
			break;

		case 9154:
			c.logout();
			break;
		case 82016:
			if (!c.takeAsNote)
				c.takeAsNote = true;
			else
				c.takeAsNote = false;
			break;

		case 97168:
			// thick skin
			c.getCombat().activatePrayer(0);
			break;
		case 97170:
			// burst of str
			c.getCombat().activatePrayer(1);
			break;
		case 97172:
			// charity of thought
			c.getCombat().activatePrayer(2);
			break;
		case 97174:
			// range
			c.getCombat().activatePrayer(3);
			break;
		case 97176:
			// mage
			c.getCombat().activatePrayer(4);
			break;
		case 97178:
			// rockskin
			c.getCombat().activatePrayer(5);
			break;
		case 97180:
			// super human
			c.getCombat().activatePrayer(6);
			break;
		case 97182:
			// improved reflexes
			c.getCombat().activatePrayer(7);
			break;
		case 97184:
			// hawk eye
			c.getCombat().activatePrayer(8);
			break;
		case 97186:
			c.getCombat().activatePrayer(9);
			break;
		case 97188:
			// protect Item
			c.getCombat().activatePrayer(10);
			break;
		case 97190:
			// 26 range
			c.getCombat().activatePrayer(11);
			break;
		case 97192:
			// 27 mage
			c.getCombat().activatePrayer(12);
			break;
		case 97194:
			// steel skin
			c.getCombat().activatePrayer(13);
			break;
		case 97196:
			// ultimate str
			c.getCombat().activatePrayer(14);
			break;
		case 97198:
			// incredible reflex
			c.getCombat().activatePrayer(15);
			break;
		case 97200:
			// protect from magic
			c.getCombat().activatePrayer(16);
			break;
		case 97202:
			// protect from range
			c.getCombat().activatePrayer(17);
			break;
		case 97204:
			// protect from melee
			c.getCombat().activatePrayer(18);
			break;
		case 97206:
			// 44 range
			c.getCombat().activatePrayer(19);
			break;
		case 97208:
			// 45 mystic
			c.getCombat().activatePrayer(20);
			break;
		case 97210:
			// retrui
			c.getCombat().activatePrayer(21);
			break;
		case 97212:
			// redem
			c.getCombat().activatePrayer(22);
			break;
		case 97214:
			// smite
			c.getCombat().activatePrayer(23);
			break;
		case 97216:
			// chiv
			c.getCombat().activatePrayer(24);
			break;
		case 97218:
			// piety
			c.getCombat().activatePrayer(25);
			break;

		/** End of curse prayers **/
		case 74176:
			if (!c.mouseButton) {
				c.mouseButton = true;
				c.getPA().sendFrame36(500, 1);
				c.getPA().sendFrame36(170, 1);
			} else if (c.mouseButton) {
				c.mouseButton = false;
				c.getPA().sendFrame36(500, 0);
				c.getPA().sendFrame36(170, 0);
			}
			break;
		case 74184:
			if (!c.splitChat) {
				c.splitChat = true;
				c.getPA().sendFrame36(502, 1);
				c.getPA().sendFrame36(287, 1);
			} else {
				c.splitChat = false;
				c.getPA().sendFrame36(502, 0);
				c.getPA().sendFrame36(287, 0);
			}
			break;
		case 74180:
			if (!c.chatEffects) {
				c.chatEffects = true;
				c.getPA().sendFrame36(501, 1);
				c.getPA().sendFrame36(171, 0);
			} else {
				c.chatEffects = false;
				c.getPA().sendFrame36(501, 0);
				c.getPA().sendFrame36(171, 1);
			}
			break;
		case 74188:
			if (!c.acceptAid) {
				c.acceptAid = true;
				c.getPA().sendFrame36(503, 1);
				c.getPA().sendFrame36(427, 1);
			} else {
				c.acceptAid = false;
				c.getPA().sendFrame36(503, 0);
				c.getPA().sendFrame36(427, 0);
			}
			break;
		case 74192:
			if (!c.isRunning2) {
				c.isRunning2 = true;
				c.getPA().sendFrame36(504, 1);
				c.getPA().sendFrame36(173, 1);
			} else {
				c.isRunning2 = false;
				c.getPA().sendFrame36(504, 0);
				c.getPA().sendFrame36(173, 0);
			}
			break;
		case 74201: // brightness1
			c.getPA().sendFrame36(505, 1);
			c.getPA().sendFrame36(506, 0);
			c.getPA().sendFrame36(507, 0);
			c.getPA().sendFrame36(508, 0);
			c.getPA().sendFrame36(166, 1);
			break;
		case 74203: // brightness2
			c.getPA().sendFrame36(505, 0);
			c.getPA().sendFrame36(506, 1);
			c.getPA().sendFrame36(507, 0);
			c.getPA().sendFrame36(508, 0);
			c.getPA().sendFrame36(166, 2);
			break;

		case 74204: // brightness3
			c.getPA().sendFrame36(505, 0);
			c.getPA().sendFrame36(506, 0);
			c.getPA().sendFrame36(507, 1);
			c.getPA().sendFrame36(508, 0);
			c.getPA().sendFrame36(166, 3);
			break;

		case 74205: // brightness4
			c.getPA().sendFrame36(505, 0);
			c.getPA().sendFrame36(506, 0);
			c.getPA().sendFrame36(507, 0);
			c.getPA().sendFrame36(508, 1);
			c.getPA().sendFrame36(166, 4);
			break;
		case 74206: // area1
			c.getPA().sendFrame36(509, 1);
			c.getPA().sendFrame36(510, 0);
			c.getPA().sendFrame36(511, 0);
			c.getPA().sendFrame36(512, 0);
			break;
		case 74207: // area2
			c.getPA().sendFrame36(509, 0);
			c.getPA().sendFrame36(510, 1);
			c.getPA().sendFrame36(511, 0);
			c.getPA().sendFrame36(512, 0);
			break;
		case 74208: // area3
			c.getPA().sendFrame36(509, 0);
			c.getPA().sendFrame36(510, 0);
			c.getPA().sendFrame36(511, 1);
			c.getPA().sendFrame36(512, 0);
			break;
		case 74209: // area4
			c.getPA().sendFrame36(509, 0);
			c.getPA().sendFrame36(510, 0);
			c.getPA().sendFrame36(511, 0);
			c.getPA().sendFrame36(512, 1);
			break;

		case 168:
			c.startAnimation(855);
			break;
		case 169:
			c.startAnimation(856);
			break;
		case 162:
			c.startAnimation(857);
			break;
		case 164:
			c.startAnimation(858);
			break;
		case 165:
			c.startAnimation(859);
			break;
		case 161:
			c.startAnimation(860);
			break;
		case 170:
			c.startAnimation(861);
			break;
		case 171:
			c.startAnimation(862);
			break;
		case 163:
			c.startAnimation(863);
			break;
		case 167:
			c.startAnimation(864);
			break;
		case 172:
			c.startAnimation(865);
			break;
		case 166:
			if (c.playerEquipment[c.playerLegs] == 10394) {
				c.startAnimation(5316);
			} else {
				c.startAnimation(866);
			}
			break;
		case 52050:
			c.startAnimation(2105);
			break;
		case 52051:
			c.startAnimation(2106);
			break;
		case 52052:
			c.startAnimation(2107);
			break;
		case 52053:
			c.startAnimation(2108);
			break;
		case 52054:
			c.startAnimation(2109);
			break;
		case 52055:
			c.startAnimation(2110);
			break;
		case 52056:
			c.startAnimation(2111);
			break;
		case 52057:
			c.startAnimation(2112);
			break;
		case 52058:
			c.startAnimation(2113);
			break;
		case 43092:
			c.startAnimation(0x558);
			c.stopMovement();
			c.gfx0(574);
			break;
		case 2155:
			c.startAnimation(0x46B);
			break;
		case 25103:
			c.startAnimation(0x46A);
			break;
		case 25106:
			c.startAnimation(0x469);
			break;
		case 2154:
			c.startAnimation(0x468);
			break;
		case 52071:
			c.startAnimation(0x84F);
			break;
		case 52072:
			c.startAnimation(0x850);
			break;
		case 59062:
			c.startAnimation(2836);
			break;
		case 72032:
			c.startAnimation(3544);
			break;
		case 72033:
			c.startAnimation(3543);
			break;
		case 72254:
			c.startAnimation(6111);
			break;
		case 72255:
			c.startAnimation(6111);
			c.stopMovement();
			break;
		case 88058:
			c.startAnimation(7531);
			c.stopMovement();
			break;
		case 88062:
			c.startAnimation(10530);
			c.stopMovement();
			c.gfx0(1864);
			break;
		case 88063:
			c.startAnimation(11044);
			c.stopMovement();
			c.gfx0(1973);
			break;
		case 88060:
			c.startAnimation(8770);
			c.stopMovement();
			c.gfx0(1553);
			break;
		case 88061:
			c.startAnimation(9990);
			c.stopMovement();
			c.gfx0(1734);
			break;
		case 73004:
			c.startAnimation(7272);
			c.stopMovement();
			c.gfx0(1244);
			break;
		case 88059:
			if (System.currentTimeMillis() - c.logoutDelay < 8000) {
				c.sendMessage("You cannot do this emote in combat!");
				return;
			}
			c.startAnimation(2414);
			c.stopMovement();
			c.gfx0(1537);
			break;
		case 73003:
			c.startAnimation(2836);
			c.stopMovement();
			break;
		case 73000:
			if (System.currentTimeMillis() - c.logoutDelay < 8000) {
				c.sendMessage("You cannot do skillcape emotes in combat!");
				return;
			}
			c.startAnimation(3543);
			c.stopMovement();
			break;
		case 73001:
			c.startAnimation(3544);
			c.stopMovement();
			break;
		case 88065:
			c.startAnimation(11542);
			c.stopMovement();
			break;
		case 88066:
			c.startAnimation(12658);
			c.stopMovement();
			c.gfx0(780);
			break;

		case 87231: // thick skin
			c.getCurse().activateCurse(0);
			return;
		case 87233: // burst of str
			c.getCurse().activateCurse(1);
			break;
		case 87235: // charity of thought
			c.getCurse().activateCurse(2);
			break;
		case 87237: // range
			c.getCurse().activateCurse(3);
			break;
		case 87239: // mage
			c.getCurse().activateCurse(4);
			break;
		case 87241: // rockskin
			c.getCurse().activateCurse(5);
			break;
		case 87243: // super human
			c.getCurse().activateCurse(6);
			break;
		case 87245: // defmage
			if (c.curseActive[7]) {
				c.curseActive[7] = false;
				c.getPA().sendFrame36(88, 0);
				c.headIcon = -1;
				c.getPA().requestUpdates();
			} else {
				c.getCurse().activateCurse(7);
				c.getPA().sendFrame36(90, 0); // defmellee
				c.getPA().sendFrame36(89, 0);// defrang
				c.getPA().sendFrame36(97, 0);// soulsplit
				c.getPA().sendFrame36(96, 0);// warth
				c.getPA().sendFrame36(88, 1);// deflmag
			}
			break;
		case 87247: // defrng
			if (c.curseActive[8]) {
				c.getPA().sendFrame36(89, 0);
				c.curseActive[8] = false;
				c.headIcon = -1;
				c.getPA().requestUpdates();
			} else {
				c.getCurse().activateCurse(8);
				c.getPA().sendFrame36(90, 0); // defmellee
				c.getPA().sendFrame36(89, 1);// defrang
				c.getPA().sendFrame36(88, 0);// deflmag
				c.getPA().sendFrame36(97, 0);// soulsplit
				c.getPA().sendFrame36(96, 0);// warth
			}
			break;
		case 87249:// protect melee
			if (c.curseActive[9]) {
				c.getPA().sendFrame36(90, 0);
				c.curseActive[9] = false;
				c.headIcon = -1;
				c.getPA().requestUpdates();
			} else {
				c.getCurse().activateCurse(9);
				c.getPA().sendFrame36(90, 1); // defmellee
				c.getPA().sendFrame36(89, 0);// defrang
				c.getPA().sendFrame36(88, 0);// deflmag
				c.getPA().sendFrame36(97, 0);// soulsplit
				c.getPA().sendFrame36(96, 0);// warth
			}
			break;

		case 87251: // leeech attack
			if (c.curseActive[10]) {
				c.getPA().sendFrame36(91, 0); // str
				c.curseActive[10] = false;
			} else {
				c.getCurse().activateCurse(10);
				c.curseActive[19] = false;
				c.getPA().sendFrame36(91, 1); // attack leech
				c.getPA().sendFrame36(105, 0);// turmoil
			}
			break;
		case 87253: // leech range
			if (c.curseActive[11]) {
				c.getPA().sendFrame36(103, 0); // str
				c.curseActive[11] = false;
			} else {
				c.getCurse().activateCurse(11);
				c.curseActive[19] = false;
				c.getPA().sendFrame36(105, 0);// turmoil
				c.getPA().sendFrame36(103, 1); // range
			}
			break;
		case 87255: // leech magic
			if (c.curseActive[12]) {
				c.getPA().sendFrame36(104, 0); // str
				c.curseActive[12] = false;
			} else {
				c.getCurse().activateCurse(12);
				c.curseActive[19] = false;
				c.getPA().sendFrame36(105, 0);// turmoil
				c.getPA().sendFrame36(104, 1); // mage
			}
			break;
		case 88001: // leech def
			if (c.curseActive[13]) {
				c.getPA().sendFrame36(92, 0); // str
				c.curseActive[13] = false;
			} else {
				c.getCurse().activateCurse(13);
				c.curseActive[19] = false;
				c.getPA().sendFrame36(105, 0);// turmoil
				c.getPA().sendFrame36(92, 1); // def
			}
			break;
		case 88003: // leech str
			if (c.curseActive[14]) {
				c.getPA().sendFrame36(93, 0); // str
				c.curseActive[14] = false;
			} else {
				c.getCurse().activateCurse(14);
				c.curseActive[19] = false;
				c.getPA().sendFrame36(105, 0);// turmoil
				c.getPA().sendFrame36(93, 1); // str
			}
			break;
		/*
		 * .getCurse().activateCurse(15); c.sendMessage("Doesn't work yet");
		 * return;
		 */
		case 88007: // protect from magic
			if (c.curseActive[16]) {
				c.getPA().sendFrame36(95, 0); // str
				c.curseActive[16] = false;
			} else {
				c.getCurse().activateCurse(16);
				c.curseActive[19] = false;
				c.getPA().sendFrame36(105, 0);// turmoil
				c.getPA().sendFrame36(95, 1); // def
			}
			return;
		case 88009: // protect from range
			if (c.curseActive[17]) {
				c.getPA().sendFrame36(96, 0);
				c.curseActive[17] = false;
				c.headIcon = -1;
				c.getPA().requestUpdates();
			} else {
				c.getCurse().activateCurse(17);
				c.getPA().sendFrame36(90, 0); // defmellee
				c.getPA().sendFrame36(89, 0);// defrang
				c.getPA().sendFrame36(88, 0);// deflmag
				c.getPA().sendFrame36(97, 0);// soulsplit
				c.getPA().sendFrame36(96, 1);// warth
			}
			break;
		case 88011: // protect from melee
			if (c.curseActive[18]) {
				c.getPA().sendFrame36(97, 0);
				c.curseActive[18] = false;
				c.headIcon = -1;
				c.getPA().requestUpdates();
			} else {
				c.getCurse().activateCurse(18);
				c.getPA().sendFrame36(90, 0); // defmellee
				c.getPA().sendFrame36(89, 0);// defrang
				c.getPA().sendFrame36(88, 0);// deflmag
				c.getPA().sendFrame36(97, 1);// soulsplit
				c.getPA().sendFrame36(96, 0);// warth
			}
			break;
		case 88013: // turmoil
			if (c.curseActive[19]) {
				c.getPA().sendFrame36(105, 0); // str
				c.curseActive[19] = false;
			} else {
				c.getCurse().activateCurse(19);
				c.curseActive[10] = false;
				c.curseActive[11] = false;
				c.curseActive[12] = false;
				c.curseActive[13] = false;
				c.curseActive[14] = false;
				c.getPA().sendFrame36(91, 0); // attack leech
				c.getPA().sendFrame36(105, 1);// turmoil
				c.getPA().sendFrame36(93, 0); // str
				c.getPA().sendFrame36(92, 0); // def
				c.getPA().sendFrame36(104, 0); // mage
				c.getPA().sendFrame36(103, 0); // range
				c.getPA().sendFrame36(95, 0);// spec
				c.getPA().sendFrame36(96, 0);// run
			}
			break;

		case 29049:
			if (c.playerEquipment[c.playerWeapon] == 4153) {
				c.specBarId = 7486;
				c.getCombat().handleGmaulPlayer();
				c.getItems().updateSpecialBar();
			} else {
				c.specBarId = 7486;
				c.usingSpecial = !c.usingSpecial;
				c.getItems().updateSpecialBar();
			}
			break;

		case 30108:
		case 29124:
		case 48034:
			c.specBarId = 7812;
			c.usingSpecial = !c.usingSpecial;
			c.getItems().updateSpecialBar();
			break;

		case 17200:
			if (c.randomBarrows == 0 || c.randomBarrows == 2) {
				c.getPA().removeAllWindows();
				c.sendMessage("You got the riddle wrong, and it locks back up.");
				// Dungeon.wrongPuzzle = true;
				break;
			} else {
				if (c.absY == 9683) {
					c.sendMessage("You hear the doors locking mechanism grind open.");
					c.getPA().object(6727, c.objectX, c.objectY, -2, 0);
					c.getPA().removeAllWindows();
					c.getPA().walkTo(0, 1);
					CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {
						public void execute(CycleEventContainer p) {
							c.getPA().object(6727, c.objectX, c.objectY, -1, 0);
							p.stop();
						}

						@Override
						public void stop() {
						}
					}, 1);
				} else if (c.absY == 9706) {
					c.sendMessage("You hear the doors locking mechanism grind open.");
					c.getPA().object(6727, c.objectX, c.objectY, 2, 0);
					c.getPA().removeAllWindows();
					c.getPA().walkTo(0, -1);
					CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {
						public void execute(CycleEventContainer p) {
							c.getPA().object(6727, c.objectX, c.objectY, 1, 0);
							p.stop();
						}

						@Override
						public void stop() {
							// TODO Auto-generated method stub

						}
					}, 1);
				} else if (c.absX == 3540) {
					c.sendMessage("You hear the doors locking mechanism grind open.");
					c.getPA().object(6727, c.objectX, c.objectY, 1, 0);
					c.getPA().removeAllWindows();
					c.getPA().walkTo(1, 0);
					CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {
						public void execute(CycleEventContainer p) {
							c.getPA().object(6743, c.objectX, c.objectY, 0, 0);
							p.stop();
						}

						@Override
						public void stop() {
							// TODO Auto-generated method stub

						}
					}, 1);
				} else {
					c.sendMessage("You hear the doors locking mechanism grind open.");
					c.getPA().object(6727, c.objectX, c.objectY, -1, 0);
					c.getPA().removeAllWindows();
					c.getPA().walkTo(-1, 0);
					CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {
						public void execute(CycleEventContainer p) {
							c.getPA().object(6743, c.objectX, c.objectY, 2, 0);
							p.stop();
						}

						@Override
						public void stop() {
							// TODO Auto-generated method stub

						}
					}, 1);
				}
			}
			break;

		case 17199:
			if (c.randomBarrows == 1 || c.randomBarrows == 2) {
				c.getPA().removeAllWindows();
				c.sendMessage("You got the riddle wrong, and it locks back up.");
				// Dungeon.wrongPuzzle = true;
				break;
			} else {
				if (c.absY == 9683) {
					c.sendMessage("You hear the doors locking mechanism grind open.");
					c.getPA().object(6727, c.objectX, c.objectY, -2, 0);
					c.getPA().removeAllWindows();
					c.getPA().walkTo(0, 1);
					CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {
						public void execute(CycleEventContainer p) {
							c.getPA().object(6727, c.objectX, c.objectY, -1, 0);
							p.stop();
						}

						@Override
						public void stop() {
							// TODO Auto-generated method stub

						}
					}, 1);
				} else if (c.absY == 9706) {
					c.sendMessage("You hear the doors locking mechanism grind open.");
					c.getPA().object(6727, c.objectX, c.objectY, 2, 0);
					c.getPA().removeAllWindows();
					c.getPA().walkTo(0, -1);
					CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {
						public void execute(CycleEventContainer p) {
							c.getPA().object(6727, c.objectX, c.objectY, 1, 0);
							p.stop();
						}

						@Override
						public void stop() {
							// TODO Auto-generated method stub

						}
					}, 1);
				} else if (c.absX == 3540) {
					c.sendMessage("You hear the doors locking mechanism grind open.");
					c.getPA().object(6727, c.objectX, c.objectY, 1, 0);
					c.getPA().removeAllWindows();
					c.getPA().walkTo(1, 0);
					CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {
						public void execute(CycleEventContainer p) {
							c.getPA().object(6743, c.objectX, c.objectY, 0, 0);
							p.stop();
						}

						@Override
						public void stop() {
							// TODO Auto-generated method stub

						}
					}, 1);
				} else {
					c.sendMessage("You hear the doors locking mechanism grind open.");
					c.getPA().object(6727, c.objectX, c.objectY, -1, 0);
					c.getPA().removeAllWindows();
					c.getPA().walkTo(-1, 0);
					CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {
						public void execute(CycleEventContainer p) {
							c.getPA().object(6743, c.objectX, c.objectY, 2, 0);
							p.stop();
						}

						@Override
						public void stop() {
							// TODO Auto-generated method stub

						}
					}, 1);
				}
			}
			break;
		case 17198:
			if (c.randomBarrows == 0 || c.randomBarrows == 1) {
				c.getPA().removeAllWindows();
				c.sendMessage("You got the riddle wrong, and it locks back up.");
				// Dungeon.wrongPuzzle = true;
				break;
			} else {
				if (c.absY == 9683) {
					c.sendMessage("You hear the doors locking mechanism grind open.");
					c.getPA().object(6727, c.objectX, c.objectY, -2, 0);
					c.getPA().removeAllWindows();
					c.getPA().walkTo(0, 1);
					CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {
						public void execute(CycleEventContainer p) {
							c.getPA().object(6727, c.objectX, c.objectY, -1, 0);
							p.stop();
						}

						@Override
						public void stop() {
							// TODO Auto-generated method stub

						}
					}, 1);
				} else if (c.absY == 9706) {
					c.sendMessage("You hear the doors locking mechanism grind open.");
					c.getPA().object(6727, c.objectX, c.objectY, 2, 0);
					c.getPA().removeAllWindows();
					c.getPA().walkTo(0, -1);
					CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {
						public void execute(CycleEventContainer p) {
							c.getPA().object(6727, c.objectX, c.objectY, 1, 0);
							p.stop();
						}

						@Override
						public void stop() {
							// TODO Auto-generated method stub

						}
					}, 1);
				} else if (c.absX == 3540) {
					c.sendMessage("You hear the doors locking mechanism grind open.");
					c.getPA().object(6727, c.objectX, c.objectY, 1, 0);
					c.getPA().removeAllWindows();
					c.getPA().walkTo(1, 0);
					CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {
						public void execute(CycleEventContainer p) {
							c.getPA().object(6743, c.objectX, c.objectY, 0, 0);
							p.stop();
						}

						@Override
						public void stop() {
							// TODO Auto-generated method stub

						}
					}, 1);
				} else {
					c.sendMessage("You hear the doors locking mechanism grind open.");
					c.getPA().object(6727, c.objectX, c.objectY, -1, 0);
					c.getPA().removeAllWindows();
					c.getPA().walkTo(-1, 0);
					CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {
						public void execute(CycleEventContainer p) {
							c.getPA().object(6743, c.objectX, c.objectY, 2, 0);
							p.stop();
						}

						@Override
						public void stop() {
							// TODO Auto-generated method stub

						}
					}, 1);
				}
			}
			break;

		/* END OF EMOTES */

		case 59100: // items kept on death?
			if (c.inTrade) {
				return;
			}
			c.StartBestItemScan();
			c.EquipStatus = 0;
			for (int k = 0; k < 4; k++)
				c.getPA().sendFrame34a(10494, -1, k, 1);
			for (int k = 0; k < 39; k++)
				c.getPA().sendFrame34a(10600, -1, k, 1);
			if (c.WillKeepItem1 > 0)
				c.getPA().sendFrame34a(10494, c.WillKeepItem1, 0, c.WillKeepAmt1);
			if (c.WillKeepItem2 > 0)
				c.getPA().sendFrame34a(10494, c.WillKeepItem2, 1, c.WillKeepAmt2);
			if (c.WillKeepItem3 > 0)
				c.getPA().sendFrame34a(10494, c.WillKeepItem3, 2, c.WillKeepAmt3);
			if (c.WillKeepItem4 > 0)
				c.getPA().sendFrame34a(10494, c.WillKeepItem4, 3, 1);
			for (int ITEM = 0; ITEM < 28; ITEM++) {
				if (c.playerItems[ITEM] - 1 > 0 && !(c.playerItems[ITEM] - 1 == c.WillKeepItem1 && ITEM == c.WillKeepItem1Slot) && !(c.playerItems[ITEM] - 1 == c.WillKeepItem2 && ITEM == c.WillKeepItem2Slot) && !(c.playerItems[ITEM] - 1 == c.WillKeepItem3 && ITEM == c.WillKeepItem3Slot)
						&& !(c.playerItems[ITEM] - 1 == c.WillKeepItem4 && ITEM == c.WillKeepItem4Slot)) {
					c.getPA().sendFrame34a(10600, c.playerItems[ITEM] - 1, c.EquipStatus, c.playerItemsN[ITEM]);
					c.EquipStatus += 1;
				} else if (c.playerItems[ITEM] - 1 > 0 && (c.playerItems[ITEM] - 1 == c.WillKeepItem1 && ITEM == c.WillKeepItem1Slot) && c.playerItemsN[ITEM] > c.WillKeepAmt1) {
					c.getPA().sendFrame34a(10600, c.playerItems[ITEM] - 1, c.EquipStatus, c.playerItemsN[ITEM] - c.WillKeepAmt1);
					c.EquipStatus += 1;
				} else if (c.playerItems[ITEM] - 1 > 0 && (c.playerItems[ITEM] - 1 == c.WillKeepItem2 && ITEM == c.WillKeepItem2Slot) && c.playerItemsN[ITEM] > c.WillKeepAmt2) {
					c.getPA().sendFrame34a(10600, c.playerItems[ITEM] - 1, c.EquipStatus, c.playerItemsN[ITEM] - c.WillKeepAmt2);
					c.EquipStatus += 1;
				} else if (c.playerItems[ITEM] - 1 > 0 && (c.playerItems[ITEM] - 1 == c.WillKeepItem3 && ITEM == c.WillKeepItem3Slot) && c.playerItemsN[ITEM] > c.WillKeepAmt3) {
					c.getPA().sendFrame34a(10600, c.playerItems[ITEM] - 1, c.EquipStatus, c.playerItemsN[ITEM] - c.WillKeepAmt3);
					c.EquipStatus += 1;
				} else if (c.playerItems[ITEM] - 1 > 0 && (c.playerItems[ITEM] - 1 == c.WillKeepItem4 && ITEM == c.WillKeepItem4Slot) && c.playerItemsN[ITEM] > 1) {
					c.getPA().sendFrame34a(10600, c.playerItems[ITEM] - 1, c.EquipStatus, c.playerItemsN[ITEM] - 1);
					c.EquipStatus += 1;
				}
			}
			for (int EQUIP = 0; EQUIP < 14; EQUIP++) {
				if (c.playerEquipment[EQUIP] > 0 && !(c.playerEquipment[EQUIP] == c.WillKeepItem1 && EQUIP + 28 == c.WillKeepItem1Slot) && !(c.playerEquipment[EQUIP] == c.WillKeepItem2 && EQUIP + 28 == c.WillKeepItem2Slot) && !(c.playerEquipment[EQUIP] == c.WillKeepItem3 && EQUIP + 28 == c.WillKeepItem3Slot)
						&& !(c.playerEquipment[EQUIP] == c.WillKeepItem4 && EQUIP + 28 == c.WillKeepItem4Slot)) {
					c.getPA().sendFrame34a(10600, c.playerEquipment[EQUIP], c.EquipStatus, c.playerEquipmentN[EQUIP]);
					c.EquipStatus += 1;
				} else if (c.playerEquipment[EQUIP] > 0 && (c.playerEquipment[EQUIP] == c.WillKeepItem1 && EQUIP + 28 == c.WillKeepItem1Slot) && c.playerEquipmentN[EQUIP] > 1 && c.playerEquipmentN[EQUIP] - c.WillKeepAmt1 > 0) {
					c.getPA().sendFrame34a(10600, c.playerEquipment[EQUIP], c.EquipStatus, c.playerEquipmentN[EQUIP] - c.WillKeepAmt1);
					c.EquipStatus += 1;
				} else if (c.playerEquipment[EQUIP] > 0 && (c.playerEquipment[EQUIP] == c.WillKeepItem2 && EQUIP + 28 == c.WillKeepItem2Slot) && c.playerEquipmentN[EQUIP] > 1 && c.playerEquipmentN[EQUIP] - c.WillKeepAmt2 > 0) {
					c.getPA().sendFrame34a(10600, c.playerEquipment[EQUIP], c.EquipStatus, c.playerEquipmentN[EQUIP] - c.WillKeepAmt2);
					c.EquipStatus += 1;
				} else if (c.playerEquipment[EQUIP] > 0 && (c.playerEquipment[EQUIP] == c.WillKeepItem3 && EQUIP + 28 == c.WillKeepItem3Slot) && c.playerEquipmentN[EQUIP] > 1 && c.playerEquipmentN[EQUIP] - c.WillKeepAmt3 > 0) {
					c.getPA().sendFrame34a(10600, c.playerEquipment[EQUIP], c.EquipStatus, c.playerEquipmentN[EQUIP] - c.WillKeepAmt3);
					c.EquipStatus += 1;
				} else if (c.playerEquipment[EQUIP] > 0 && (c.playerEquipment[EQUIP] == c.WillKeepItem4 && EQUIP + 28 == c.WillKeepItem4Slot) && c.playerEquipmentN[EQUIP] > 1 && c.playerEquipmentN[EQUIP] - 1 > 0) {
					c.getPA().sendFrame34a(10600, c.playerEquipment[EQUIP], c.EquipStatus, c.playerEquipmentN[EQUIP] - 1);
					c.EquipStatus += 1;
				}
			}
			c.ResetKeepItems();
			c.getPA().showInterface(17100);
			break;
		case 26065: // no forfeit
		case 26040:
			c.duelSlot = -1;
			c.getDuel().selectRule(0);
			break;

		case 26066: // no movement
		case 26048:
			c.duelSlot = -1;
			c.getDuel().selectRule(1);
			break;

		case 26069: // no range
		case 26042:
			c.duelSlot = -1;
			c.getDuel().selectRule(2);
			break;

		case 26070: // no melee
		case 26043:
			c.duelSlot = -1;
			c.getDuel().selectRule(3);
			break;

		case 26071: // no mage
		case 26041:
			c.duelSlot = -1;
			c.getDuel().selectRule(4);
			break;

		case 26072: // no drinks
		case 26045:
			c.duelSlot = -1;
			c.getDuel().selectRule(5);
			break;

		case 26073: // no food
		case 26046:
			c.duelSlot = -1;
			c.getDuel().selectRule(6);
			break;

		case 26074: // no prayer
		case 26047:
			c.duelSlot = -1;
			c.getDuel().selectRule(7);
			break;

		case 26076: // obsticals
		case 26075:
			c.duelSlot = -1;
			c.getDuel().selectRule(8);
			break;

		case 2158: // fun weapons
		case 2157:
			c.duelSlot = -1;
			c.getDuel().selectRule(9);
			break;

		case 30136: // sp attack
		case 30137:
			c.duelSlot = -1;
			c.getDuel().selectRule(10);
			break;

		case 53245: // no helm
			c.duelSlot = 0;
			c.getDuel().selectRule(11);
			break;

		case 53246: // no cape
			c.duelSlot = 1;
			c.getDuel().selectRule(12);
			break;

		case 53247: // no ammy
			c.duelSlot = 2;
			c.getDuel().selectRule(13);
			break;

		case 53249: // no weapon.
			c.duelSlot = 3;
			c.getDuel().selectRule(14);
			break;

		case 53250: // no body
			c.duelSlot = 4;
			c.getDuel().selectRule(15);
			break;

		case 53251: // no shield
			c.duelSlot = 5;
			c.getDuel().selectRule(16);
			break;

		case 53252: // no legs
			c.duelSlot = 7;
			c.getDuel().selectRule(17);
			break;

		case 53255: // no gloves
			c.duelSlot = 9;
			c.getDuel().selectRule(18);
			break;

		case 53254: // no boots
			c.duelSlot = 10;
			c.getDuel().selectRule(19);
			break;

		case 53253: // no rings
			c.duelSlot = 12;
			c.getDuel().selectRule(20);
			break;

		case 53248: // no arrows
			c.duelSlot = 13;
			c.getDuel().selectRule(21);
			break;

		case 33206: // Attack button
		case 34142:
			SkillGuides.atkInterface(c);
			break;
		case 33209: // str button
		case 34119:
			SkillGuides.strInterface(c);
			break;
		case 33212: // Defence
		case 34120:
			SkillGuides.defInterface(c);
			break;
		case 34133:
		case 33215: // Range
			SkillGuides.rangeInterface(c);
			break;
		case 34123:
		case 33207: // Hitpoints
			// SkillGuides.hpInterface(c);
			break;
		case 34139:
		case 33218: // Prayer
			SkillGuides.prayInterface(c);
			break;
		case 34136:
		case 33221: // Magic

			SkillGuides.mageInterface(c);
			break;
		case 34155:
		case 33224: // Runecrafting
			SkillGuides.rcInterface(c);
			break;
		case 34158:
		case 33210: // Agility
			SkillGuides.agilityInterface(c);
			break;
		case 34161:
		case 33213: // Herblore
			SkillGuides.herbloreInterface(c);
			break;
		case 59199:
		case 33216: // Theiving
			SkillGuides.thievingInterface(c);
			break;
		case 59202:
		case 33219: // craft
			SkillGuides.craftingInterface(c);
			break;
		case 33222: // Fletching
			SkillGuides.fletchingInterface(c);
			break;
		case 59205:
		case 47130: // Slayer
			SkillGuides.slayerInterface(c);
			break;
		case 33208: // Mining
			SkillGuides.miningInterface(c);
			break;
		case 33211: // Smithing
			SkillGuides.smithingInterface(c);
			break;
		case 33214: // Fishing
			SkillGuides.fishingInterface(c);
			break;
		case 33217: // Cooking
			SkillGuides.cookingInterface(c);
			break;
		case 33220: // Firemaking
			SkillGuides.firemakingInterface(c);
			break;
		case 33223: // Woodcutting
			SkillGuides.woodcuttingInterface(c);
			break;
		case 54104: // Farming
			SkillGuides.farmingInterface(c);
			break;

		case 26018:
			if (c.duelStatus == 5) {
				// c.sendMessage("You're funny sir.");
				return;
			}
			if (c.inDuelArena()) {
				Client o = (Client) World.players[c.duelingWith];
				if (o == null) {
					c.getDuel().declineDuel();
					o.getDuel().declineDuel();
					return;
				}

				if (c.duelRule[2] && c.duelRule[3] && c.duelRule[4]) {
					c.sendMessage("You won't be able to attack the player with the rules you have set.");
					break;
				}
				c.duelStatus = 2;
				if (c.duelStatus == 2) {
					c.getPA().sendFrame126("Waiting for other player...", 6684);
					o.getPA().sendFrame126("Other player has accepted.", 6684);
				}
				if (o.duelStatus == 2) {
					o.getPA().sendFrame126("Waiting for other player...", 6684);
					c.getPA().sendFrame126("Other player has accepted.", 6684);
				}

				if (c.duelStatus == 2 && o.duelStatus == 2) {
					c.canOffer = false;
					o.canOffer = false;
					c.duelStatus = 3;
					o.duelStatus = 3;
					c.getDuel().confirmDuel();
					o.getDuel().confirmDuel();
				}
			} else {
				Client o = (Client) World.players[c.duelingWith];
				c.getDuel().declineDuel();
				o.getDuel().declineDuel();
				c.sendMessage("You can't stake out of Duel Arena.");
			}
			break;

		case 25120:
			if (c.duelStatus == 5) {
				// c.sendMessage("You're funny sir.");
				return;
			}
			if (c.inDuelArena()) {
				if (c.duelStatus == 5) {
					break;
				}
				Client o1 = (Client) World.players[c.duelingWith];
				if (o1 == null) {
					c.getDuel().declineDuel();
					return;
				}

				c.duelStatus = 4;
				if (o1.duelStatus == 4 && c.duelStatus == 4) {
					c.getDuel().startDuel();
					o1.getDuel().startDuel();
					o1.duelCount = 4;
					c.duelCount = 4;
					c.duelDelay = System.currentTimeMillis();
					o1.duelDelay = System.currentTimeMillis();
				} else {
					c.getPA().sendFrame126("Waiting for other player...", 6571);
					o1.getPA().sendFrame126("Other player has accepted", 6571);
				}
			} else {
				Client o = (Client) World.players[c.duelingWith];
				c.getDuel().declineDuel();
				o.getDuel().declineDuel();
				c.sendMessage("You can't stake out of Duel Arena.");
			}
			break;

		case 13092:
			if (System.currentTimeMillis() - c.lastButton < 400) {
				c.lastButton = System.currentTimeMillis();
				break;
			} else {
				c.lastButton = System.currentTimeMillis();
			}
			Client ot = (Client) World.players[c.tradeWith];
			if (ot == null) {
				c.getTrade().declineTrade(false);
				c.sendMessage("Other player has declined the trade.");
				break;
			}
			if (!c.tradeConfirmed)
				ot.getPA().sendFrame126("Other player has accepted.", 3431);
			if (!ot.tradeConfirmed)
				c.getPA().sendFrame126("Waiting for other player...", 3431);
			c.goodTrade = true;
			ot.goodTrade = true;
			for (GameItem item : c.getTrade().offeredItems) {
				if (item.id > 0) {
					if (ot.getItems().freeSlots() < c.getTrade().offeredItems.size()) {
						c.sendMessage(ot.playerName + " only has " + ot.getItems().freeSlots() + " free slots, please remove " + (c.getTrade().offeredItems.size() - ot.getItems().freeSlots()) + " items.");
						ot.sendMessage(c.playerName + " has to remove " + (c.getTrade().offeredItems.size() - ot.getItems().freeSlots()) + " items or you could offer them " + (c.getTrade().offeredItems.size() - ot.getItems().freeSlots()) + " items.");
						c.goodTrade = false;
						ot.goodTrade = false;
						c.getPA().sendFrame126("Not enough inventory space...", 3431);
						ot.getPA().sendFrame126("Not enough inventory space...", 3431);
						break;
					} else {
						c.getPA().sendFrame126("Waiting for other player...", 3431);
						ot.getPA().sendFrame126("Other player has accepted", 3431);
						c.goodTrade = true;
						ot.goodTrade = true;
					}
				}
			}
			if (c.inTrade && !c.tradeConfirmed && ot.goodTrade && c.goodTrade) {
				c.tradeConfirmed = true;
				if (ot.tradeConfirmed) {
					c.getTrade().confirmScreen();
					ot.getTrade().confirmScreen();
					break;
				}

			}
			break;

		case 13218:
			final Client ot1 = (Client) World.players[c.tradeWith];
			if (System.currentTimeMillis() - c.lastButton < 400) {
				c.lastButton = System.currentTimeMillis();
				break;
			} else {
				c.lastButton = System.currentTimeMillis();
			}
			if (c.getTrade().twoTraders(c, ot1)) {
				c.tradeAccepted = true;
				if (ot1 == null) {
					c.getTrade().declineTrade(false);
					c.sendMessage("Other player has declined the trade.");
					break;
				}

				if (c.inTrade && c.tradeConfirmed && ot1.tradeConfirmed && !c.tradeConfirmed2 && ot1.inTrade) {
					c.tradeConfirmed2 = true;
					if (ot1.tradeConfirmed2 && c.tradeConfirmed2) {
						c.acceptedTrade = true;
						ot1.acceptedTrade = true;
						c.sendMessage("Accepted trade.");
						ot1.sendMessage("Accepted trade.");
						c.getTrade().giveItems();
						ot1.getTrade().giveItems();
						c.getTrade().resetTrade();
						ot1.getTrade().resetTrade();
						c.getPA().removeAllWindows();
						ot1.getPA().removeAllWindows();
						break;
					}
					ot1.getPA().sendFrame126("Other player has accepted.", 3535);
					if (!ot1.tradeConfirmed2)
						c.getPA().sendFrame126("Waiting for other player...", 3535);
				}
			} else {
				c.getTrade().declineTrade(true);
				c.sendMessage("You can't trade two people at once!");
				ot1.sendMessage("You can't trade two people at once!");
			}
			break;

		case 136212:
			if (Mistex.LastVoter == "") {
				c.sendMessage("There is no last voter!");
				return;
			}
			for (int i = 0; i < MistexConfiguration.MAX_PLAYERS; i++) {
				if (World.players[i] != null) {
					if (World.players[i].playerName.equalsIgnoreCase(Mistex.LastVoter)) {
						Client c2 = (Client) World.players[i];
						if (Mistex.LastVoter.equalsIgnoreCase(c.playerName)) {
							c.sendMessage("You can't view your own profile!");
							break;
						}
						if (c.isViewingProfile) {
							c.sendMessage("@ceo@Please clear your viewer first!");
							break;
						}
						if (c2.canViewProfile == false) {
							c.sendMessage("@ceo@" + MistexUtility.capitalize(c2.playerName) + " has disabled profile viewing!");
						} else {
							c.getPA().changeToSidebar(13);
							c2.sendMessage("@ceo@" + MistexUtility.capitalize(c.playerName) + " </col>is viewing your profile!");
							c.isViewingProfile = true;
							c.profileViews += 1;
							c.sendMessage("You are now viewing @ceo@" + MistexUtility.capitalize(c2.playerName) + " </col>'s profile!");
							DecimalFormat df = new DecimalFormat("#.##");
							double ratio = ((double) c.KC) / ((double) c.DC);
							c.getPA().sendFrame126("Enable Profile", 29004);
							c.getPA().sendFrame126("Profile Settings", 29005);
							c.getPA().sendFrame126("Clear", 29006);
							c.getPA().sendFrame126("", 29007);
							c.getPA().sendFrame126("@lre@Profile: @" + c2.profileColour + "@" + MistexUtility.capitalize(c2.playerName) + "", 29008);
							c.getPA().sendFrame126("@lre@Date Joined: @gre@" + c2.joinDate, 29009);
							c.getPA().sendFrame126("<currentY>@or2@Statistics:", 29010);
							c.getPA().sendFrame126("@lre@Pk Points: @" + c2.profileColour + "@" + MistexUtility.format(c2.pkPoints) + "", 29011);
							c.getPA().sendFrame126("@lre@Kills: @" + c2.profileColour + "@" + MistexUtility.format(c2.KC) + "", 29012);
							c.getPA().sendFrame126("@lre@Deaths: @" + c2.profileColour + "@" + MistexUtility.format(c2.DC) + "", 29013);
							c.getPA().sendFrame126("@lre@KDR: @" + c2.profileColour + "@" + df.format(ratio), 29014);
							c.getPA().sendFrame126("@lre@Killstreak: @" + c2.profileColour + "@" + MistexUtility.format(c2.killStreak) + "", 29015);
							c.getPA().sendFrame126("@lre@Voting Points: @" + c2.profileColour + "@" + MistexUtility.format(c2.votingPoints) + "", 29016);
							c.getPA().sendFrame126("@lre@Donation Points: @" + c2.profileColour + "@" + MistexUtility.format(c2.donatorPoints) + "", 29017);
							c.getPA().sendFrame126("@lre@Achievements Completed: @" + c2.profileColour + "@" + MistexUtility.format(c2.achievementsCompleted) + "", 29018);
							c.getPA().sendFrame126("@lre@Achievement Points: @" + c2.profileColour + "@" + MistexUtility.format(c2.achievementPoints) + "", 29019);
							c.getPA().sendFrame126("", 29020);
							c.getPA().sendFrame126("<currentY>@or2@Details:", 29021);

							if (c2.playerName.contains("Play Boy")) {
								c.getPA().sendFrame126("@lre@Name: @red@" + MistexUtility.capitalize(c.playerName) + " ( +  Y  + )" + c2.name, 29022);
							} else {
								c.getPA().sendFrame126("@lre@Name: @" + c2.profileColour + "@" + c2.name, 29022);
							}
							c.getPA().sendFrame126("@lre@Age: @" + c2.profileColour + "@" + c2.ageProfile, 29023);
							c.getPA().sendFrame126("@lre@Birthplace: @" + c2.profileColour + "@" + c2.bplaceProfile + "", 29024);
							c.getPA().sendFrame126("@lre@About Me:", 29025);
							c.getPA().sendFrame126("@" + c2.profileColour + "@" + c2.aboutMe + "", 29026);
							c.getPA().sendFrame126("", 29027);
							c.getPA().sendFrame126("<currentY>@or2@Other:", 29028);
							c.getPA().sendFrame126("@lre@Food Eaten: @" + c2.profileColour + "@" + MistexUtility.format(c2.foodEaten) + "", 29029);
							c.getPA().sendFrame126("@lre@Potions Drank: @" + c2.profileColour + "@" + MistexUtility.format(c2.potionsDrank) + "", 29030);
							c.getPA().sendFrame126("@lre@Duels Won: @" + c2.profileColour + "@" + MistexUtility.format(c2.duelsWon) + "", 29031);
							c.getPA().sendFrame126("@lre@Home Teleports: @" + c2.profileColour + "@" + MistexUtility.format(c2.teleportedHome) + "", 29032);
							c.getPA().sendFrame126("@lre@Trivia Won: @" + c2.profileColour + "@" + MistexUtility.format(c2.triviaWon) + "", 29033);
							c.getPA().sendFrame126("@lre@Altar Prays: @" + c2.profileColour + "@" + MistexUtility.format(c2.altarPrayers) + "", 29034);
							c.getPA().sendFrame126("@lre@Profile Views: @" + c2.profileColour + "@" + MistexUtility.format(c2.profileViews) + "", 29035);
							c.getPA().sendFrame126("@lre@Statue Plays: @" + c2.profileColour + "@" + MistexUtility.format(c2.pkStatuePlays) + "", 29036);
							c.getPA().sendFrame126("@lre@Trades: @" + c2.profileColour + "@" + MistexUtility.format(c2.tradesCompleted) + "", 29037);
							c.getPA().sendFrame126("@lre@Appearance Change: @" + c2.profileColour + "@" + MistexUtility.format(c2.appearancesChanged) + "", 29038);
							c.getPA().sendFrame126("@lre@Corp Kills: @" + c2.profileColour + "@" + MistexUtility.format(c2.corpKills) + "", 29039);
							c.getPA().sendFrame126("@lre@Crab Kills: @" + c2.profileColour + "@" + MistexUtility.format(c2.rockCrabKills) + "", 29039);
							c.getPA().sendFrame126("@lre@Tztok Jad Kills: @" + c2.profileColour + "@" + MistexUtility.format(c2.jadKills) + "", 29040);
							c.getPA().sendFrame126("@lre@Food Cooked: @" + c2.profileColour + "@" + MistexUtility.format(c2.foodCooked) + "", 29041);
							c.getPA().sendFrame126("@lre@Logs Burned: @" + c2.profileColour + "@" + MistexUtility.format(c2.logsBurned) + "", 29042);
							c.getPA().sendFrame126("@lre@Ores Mined: @" + c2.profileColour + "@" + MistexUtility.format(c2.oresRecieved) + "", 29043);
							c.getPA().sendFrame126("@lre@Total Prestiges: @" + c2.profileColour + "@" + MistexUtility.format(c2.totalPrestiges) + "", 29044);
							if (c.profileViews == 1)
								c.sendMessage("@war@You have started the achievement: The Viewer ");
							InterfaceText.writeText(new AchievementTab(c));
							if (c.profileViews == 250)
								AchievementHandler.activateAchievement(c, AchievementList.THE_VIEWER);
						}
						break;
					}
				}
			}
			break;

		}
		if (c.isAutoButton(actionButtonId))
			c.assignAutocast(actionButtonId);
	}

	public boolean handleClickButton(int buttonId) {
		switch (buttonId) {
		case 3145:
			mouseButtons = !mouseButtons;
			return true;
		case 3147:
			chatEffects = !chatEffects;
			return true;
		case 3189:
			privateChat = !privateChat;
			return true;
		case 48176:
			acceptAid = !acceptAid;
			return true;
		case 3138:
			brightness = 1;
			return true;
		case 3140:
			brightness = 2;
			return true;
		case 3142:
			brightness = 3;
			return true;
		case 3144:
			brightness = 4;
			return true;
		}

		return false;
	}

	private void sendConfig(Client client, int id, int value) {
		if (value < Byte.MIN_VALUE || value > Byte.MAX_VALUE) {
			client.getOutStream().createFrame(86);
			client.getOutStream().writeWordBigEndian(id);
			client.getOutStream().writeDWord(value);
		} else {
			client.getOutStream().createFrame(36);
			client.getOutStream().writeWordBigEndian(id);
			client.getOutStream().writeByte(value);
		}
	}

	public void updateSettings(Client client) {
		sendConfig(client, MOUSE_BUTTONS, mouseButtons ? 1 : 0);
		sendConfig(client, CHAT_EFFECTS, chatEffects ? 1 : 0);
		sendConfig(client, PRIVATE_CHAT, privateChat ? 1 : 0);
		sendConfig(client, ACCEPT_AID, acceptAid ? 1 : 0);
		sendConfig(client, BRIGHTNESS, brightness);
	}

	public static final int ACCEPT_AID = 427, PRIVATE_CHAT = 287, CHAT_EFFECTS = 171, MOUSE_BUTTONS = 170, BRIGHTNESS = 166, DEFAULT_BRIGHTNESS = 3;
	public boolean acceptAid, privateChat, chatEffects, mouseButtons;
	public int brightness = DEFAULT_BRIGHTNESS;

}