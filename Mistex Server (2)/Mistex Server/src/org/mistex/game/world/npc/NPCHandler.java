package org.mistex.game.world.npc;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.mistex.game.Mistex;
import org.mistex.game.MistexConfiguration;
import org.mistex.game.MistexUtility;
import org.mistex.game.world.World;
import org.mistex.game.world.clip.region.Region;
import org.mistex.game.world.content.achievement.AchievementHandler;
import org.mistex.game.world.content.achievement.AchievementList;
import org.mistex.game.world.content.interfaces.InterfaceText;
import org.mistex.game.world.content.interfaces.impl.AchievementTab;
import org.mistex.game.world.content.skill.slayer.DuoSlayer;
import org.mistex.game.world.content.skill.slayer.Slayer;
import org.mistex.game.world.event.CycleEvent;
import org.mistex.game.world.event.CycleEventContainer;
import org.mistex.game.world.event.CycleEventHandler;
import org.mistex.game.world.npc.animation.AttackAnimation;
import org.mistex.game.world.npc.animation.DeathAnimation;
import org.mistex.game.world.player.Client;
import org.mistex.game.world.player.Player;
import org.mistex.game.world.player.PlayerConfiguration;

//Max Npc Id 15976
@SuppressWarnings({ "resource", "unused", "static-access" })
public class NPCHandler {

	public static int maxNPCs = 10777;
	public static int maxListedNPCs = 10000;
	public static int maxNPCDrops = 10000;
	public static int npcCombat[] = new int[maxNPCs];
	public static NPC npcs[] = new NPC[maxNPCs];

	public static int lastImpDeathX = 0;

	public static int lastImpDeathY = 0;
	public static boolean dontdrop = false;

	/**
	 * All slayer requirements.
	 */
	public static final int[] slayerReqs = { 1648, 5, 1600, 10, 1612, 15, 1631, 20, 1620, 25, 1633, 30, 3153, 33, 1616, 40, 1643, 45, 1618, 50, 1637, 52, 1623, 55, 1604, 60, 6220, 63, 1608, 70, 3068, 72, 9467, 73, 1610, 75, 9465, 77, 9172, 78, 1613, 80, 1615, 85, 2783, 90, 9463, 93, 1624, 65 };

	public NPCHandler() {
		for (int i = 0; i < maxNPCs; i++) {
			npcs[i] = null;
			NPCDefinitions.getDefinitions()[i] = null;
		}

		loadNPCList("./Data/cfg/npc.cfg");
		loadAutoSpawn("./Data/cfg/spawn-config.cfg");
	}

	public void removeNpc(NPC npc) {
		npc.setAbsX(0);
		npc.setAbsY(0);
		npcs[npc.npcId] = null;
	}

	public void SendPRMessage(String chatToForce, int id) {//
		int ic = 0;
		for (int x = 0; x < npcs.length; x++) {
			if (npcs[x] != null && npcs[x].npcType == id) {
				if (ic == 3) {
					npcs[x].forceChat(chatToForce);
					return;
				} else if (id == 660) {
					ic++;
					continue;
				} else
					npcs[x].forceChat(chatToForce);
			}
		}
	}

	public static void killNpc(int Npc) {
		for (int x = 0; x < npcs.length; x++) {
			if (npcs[x] != null && npcs[x].npcType == Npc) {
				npcs[x].isDead = true;
				npcs[x].applyDead = true;
				npcs[x].updateRequired = true;
				npcs[x].dirUpdateRequired = true;
				npcs[x].getNextWalkingDirection();
			}
		}
	}

	public static void killNPC(NPC n, int type, int x, int y) {
		if (n == null)
			return;
		if (n.npcType == type && n.absX == x && n.absY == y) {
			lastImpDeathX = n.absX;
			lastImpDeathX = n.absY;
			n.isDead = true;
			n.applyDead = true;
			n.updateRequired = true;
			dontdrop = true;
		}
	}

	// public Npc replaceNpcID(NPC npc, int id) {
	// Npc newNpc = (Npc) spawnNpc(id, npc.getAbsX(), npc.getAbsY(),
	// npc.heightLevel);
	// removeNpc(npc);
	// return newNpc;
	// }

	public boolean checkSlayerHelm(Client c, int i) {
		if (c.slayerTask == npcs[i].npcType && c.playerEquipment[c.playerHat] == 13263) {
			return true;
		}
		return false;
	}

	public NPC spawnNpc(int id, int x, int y, int heightLevel) {
		int slot = -1;
		for (int i = 1; i < maxNPCs; i++) {
			if (npcs[i] == null) {
				slot = i;
				break;
			}
		}
		if (slot == -1) {
			return null;
		}
		NPC newNPC = new NPC(slot, id);
		newNPC.setAbsX(x);
		newNPC.setAbsY(y);
		newNPC.makeX = x;
		newNPC.makeY = y;
		newNPC.heightLevel = heightLevel;
		newNPC.walkingType = 0;
		newNPC.HP = 50;
		newNPC.MaxHP = 50;
		newNPC.maxHit = 0;
		newNPC.attack = 0;
		newNPC.defence = 0;
		npcs[slot] = newNPC;
		return newNPC;
	}

	private void spawnSecondForm(final int i) {
		npcs[i].gfx0(1055);
		CycleEventHandler.getSingleton().addEvent(this, new CycleEvent() {
			public void execute(CycleEventContainer p) {
				spawnNpc2(1160, npcs[i].absX, npcs[i].absY, 0, 1, 230, 45, 500, 300);
				p.stop();
			}

			@Override
			public void stop() {
				// CloseInterface();
			}
		}, 1);
	}

	private void spawnFirstForm(final int i) {
		CycleEventHandler.getSingleton().addEvent(this, new CycleEvent() {
			public void execute(CycleEventContainer p) {
				spawnNpc2(1158, npcs[i].absX, npcs[i].absY, 0, 1, 230, 45, 500, 300);
				p.stop();
			}

			@Override
			public void stop() {
				// CloseInterface();
			}
		}, 1);
	}

	public void multiAttackGfx(int i, int gfx) {
		if (npcs[i].projectileId < 0)
			return;
		for (int j = 0; j < World.players.length; j++) {
			if (World.players[j] != null) {
				Client c = (Client) World.players[j];
				if (c.heightLevel != npcs[i].heightLevel)
					continue;
				if (World.players[j].goodDistance(c.absX, c.absY, npcs[i].absX, npcs[i].absY, 15)) {
					int nX = NPCHandler.npcs[i].getX() + offset(i);
					int nY = NPCHandler.npcs[i].getY() + offset(i);
					int pX = c.getX();
					int pY = c.getY();
					int offX = (nY - pY) * -1;
					int offY = (nX - pX) * -1;
					c.getPA().createPlayersProjectile(nX, nY, offX, offY, 50, getProjectileSpeed(i), npcs[i].projectileId, 43, 31, -c.getId() - 1, 65);
				}
			}
		}
	}

	public boolean switchesAttackers(int i) {
		switch (npcs[i].npcType) {
		case 2551:
		case 2552:
		case 2553:
		case 2559:
		case 2560:
		case 2561:
		case 2563:
		case 2564:
		case 2565:
		case 2892:
		case 2894:
		case 8133: // Corp
		case 8127: // Dark Core
		case 10770:
		case 10771:
		case 10772:
		case 10773:
		case 10774:
		case 10775:
			return true;

		}

		return false;
	}

	public void spawnNpc3(Client c, int npcType, int x, int y, int heightLevel, int WalkingType, int HP, int maxHit, int attack, int defence, boolean attackPlayer, boolean headIcon, boolean summonFollow) {
		// first, search for a free slot
		int slot = -1;
		for (int i = 1; i < maxNPCs; i++) {
			if (npcs[i] == null) {
				slot = i;
				break;
			}
		}
		if (slot == -1) {
			// Misc.println("No Free Slot");
			return; // no free slot found
		}
		NPC newNPC = new NPC(slot, npcType);
		newNPC.absX = x;
		newNPC.absY = y;
		newNPC.makeX = x;
		newNPC.makeY = y;
		newNPC.heightLevel = heightLevel;
		newNPC.walkingType = WalkingType;
		newNPC.HP = HP;
		newNPC.MaxHP = HP;
		newNPC.maxHit = maxHit;
		newNPC.attack = attack;
		newNPC.defence = defence;
		newNPC.spawnedBy = c.getId();
		newNPC.underAttack = true;
		newNPC.facePlayer(c.playerId);
		if (headIcon)
			c.getPA().drawHeadicon(1, slot, 0, 0);
		if (summonFollow) {
			newNPC.summoner = true;
			newNPC.summonedBy = c.playerId;
			c.hasNpc = true;
		}
		if (attackPlayer) {
			newNPC.underAttack = true;
			if (c != null) {
				newNPC.killerId = c.playerId;
			}
		}
		npcs[slot] = newNPC;
	}

	public void stepAway(int i) {
		if (Region.getClipping(npcs[i].getX() - 1, npcs[i].getY(), npcs[i].heightLevel, -1, 0)) {
			npcs[i].moveX = -1;
			npcs[i].moveY = 0;
		} else if (Region.getClipping(npcs[i].getX() + 1, npcs[i].getY(), npcs[i].heightLevel, 1, 0)) {
			npcs[i].moveX = 1;
			npcs[i].moveY = 0;
		} else if (Region.getClipping(npcs[i].getX(), npcs[i].getY() - 1, npcs[i].heightLevel, 0, -1)) {
			npcs[i].moveX = 0;
			npcs[i].moveY = -1;
		} else if (Region.getClipping(npcs[i].getX(), npcs[i].getY() + 1, npcs[i].heightLevel, 0, 1)) {
			npcs[i].moveX = 0;
			npcs[i].moveY = 1;
		}
		npcs[i].getNextNPCMovement(i);
	}

	public void multiAttackDamage(int i) {
		int max = getMaxHit(i);
		for (int j = 0; j < World.players.length; j++) {
			if (World.players[j] != null) {
				Client c = (Client) World.players[j];
				if (c.isDead || c.heightLevel != npcs[i].heightLevel)
					continue;
				if (World.players[j].goodDistance(c.absX, c.absY, npcs[i].absX, npcs[i].absY, 15)) {
					if (npcs[i].attackType == 2) {
						if (!c.prayerActive[16]) {
							if (MistexUtility.random(500) + 200 > MistexUtility.random(c.getCombat().mageDef())) {
								int dam = MistexUtility.random(max);
								c.dealDamage(dam);
								c.handleHitMask(dam);
							} else {
								c.dealDamage(0);
								c.handleHitMask(0);
							}
						} else {
							c.dealDamage(0);
							c.handleHitMask(0);
						}
					} else if (npcs[i].attackType == 1) {
						if (!c.prayerActive[17]) {
							int dam = MistexUtility.random(max);
							if (MistexUtility.random(500) + 200 > MistexUtility.random(c.getCombat().calculateRangeDefence())) {
								c.dealDamage(dam);
								c.handleHitMask(dam);
							} else {
								c.dealDamage(0);
								c.handleHitMask(0);
							}
						} else {
							c.dealDamage(0);
							c.handleHitMask(0);
						}
					}
					if (npcs[i].endGfx > 0) {
						c.gfx0(npcs[i].endGfx);
					}
				}
				c.getPA().refreshSkill(3);
			}
		}
	}

	public int getClosePlayer(int i) {
		for (int j = 0; j < World.players.length; j++) {
			if (World.players[j] != null) {
				if (j == npcs[i].spawnedBy)
					return j;
				if (goodDistance(World.players[j].absX, World.players[j].absY, npcs[i].absX, npcs[i].absY, 2 + distanceRequired(i) + followDistance(i)) || isFightCaveNpc(i) || isChampionBattleNPC(i)) {
					if ((World.players[j].underAttackBy <= 0 && World.players[j].underAttackBy2 <= 0) || World.players[j].inMulti())
						if (World.players[j].heightLevel == npcs[i].heightLevel)
							return j;
				}
			}
		}
		return 0;
	}

	public int getCloseRandomPlayer(int i) {
		ArrayList<Integer> players = new ArrayList<Integer>();
		for (int j = 0; j < World.players.length; j++) {
			if (World.players[j] != null) {
				if (goodDistance(World.players[j].absX, World.players[j].absY, npcs[i].absX, npcs[i].absY, 2 + distanceRequired(i) + followDistance(i)) || isFightCaveNpc(i) || isGWDNpc(i) || isChampionBattleNPC(i)) {
					if ((World.players[j].underAttackBy <= 0 && World.players[j].underAttackBy2 <= 0) || World.players[j].inMulti())
						if (World.players[j].heightLevel == npcs[i].heightLevel)
							players.add(j);
				}
			}
		}
		if (players.size() > 0)
			return players.get(MistexUtility.random(players.size() - 1));
		else
			return 0;
	}

	public int npcSize(int i) {
		switch (npcs[i].npcType) {
		case 2883:
		case 2882:
		case 2881:
			return 3;
		}
		return 0;
	}

	public boolean isAggressive(int i) {
		switch (npcs[i].npcType) {
		case 9780:
		case 10127:
		case 10106:
		case 8528: // Nomad
		case 8133: // Corp
		case 8127: // Dark Core
		case 3732:
		case 3772:
		case 3762:
		case 3742:
		case 3752:
		case 1153:
		case 6367:
		case 1154:
		case 1155:
		case 1156:
		case 1157:
		case 1168:
		case 2550:
		case 2551:
		case 2552:
		case 2553:
		case 2558:
		case 2559:
		case 2560:
		case 2561:
		case 2562:
		case 50:
		case 2563:
		case 2564:
		case 2565:
		case 2892:
		case 2894:
		case 2881:
		case 2882:
		case 2883:
		case 2035:
			// GWD
		case 6250: // Npcs That Give ArmaKC
		case 6230:
		case 6231:
		case 6229:
		case 6232:
		case 6240:
		case 6241:
		case 6242:
		case 6233:
		case 6234:
		case 6243:
		case 6244:
		case 6245:
		case 6246:
		case 6238:
		case 6239:
		case 6227:
		case 6625:
		case 6223:
		case 6222: // end of armadyl npcs
		case 122: // Npcs That Give BandosKC
		case 100: // Npcs That Give BandosKC
		case 6278:
		case 6277:
		case 6276:
		case 6283:
		case 6282:
		case 6281:
		case 6280:
		case 6279:
		case 6271:
		case 6272:
		case 6273:
		case 6274:
		case 6269:
		case 6270:
		case 6268:
		case 6265:
		case 1459:
		case 6263:
		case 6261:
		case 6260: // end of bandos npcs
		case 6221:
		case 6219:
		case 6220:
		case 6217:
		case 6216:
		case 6215:
		case 6214:
		case 6213:
		case 6212:
		case 6211:
		case 6218:
		case 6208:
		case 1926:
		case 6206:
		case 6204:
		case 6203:
		case 6275:
		case 6257: // Npcs That Give SaraKC
		case 6255:
		case 6256:
		case 6258:
		case 6259:
		case 6254:
		case 6252:
		case 6248:
		case 6247:

			return true;
		}
		if (npcs[i].inWild() && npcs[i].MaxHP > 0 && !npcs[i].summon)
			return true;
		if (isFightCaveNpc(i) || isChampionBattleNPC(i))
			return true;
		return false;
	}

	public boolean isFightCaveNpc(int i) {
		switch (npcs[i].npcType) {
		case 2627:
		case 2630:
		case 2631:
		case 2741:
		case 2743:
		case 2745:
			return true;
		}
		return false;
	}

	public boolean isBossNPC(int npc) {
		switch (npcs[npc].npcType) {
		case 2745:// Jad
		case 8133:// Corporeal Beast
		case 8528:// Nomad
		case 6203:// K'ril Tsutsaroth
		case 6260:// General Graardor
		case 6247:// Zilyana
		case 6222:// Kree'ara
		case 50:// King
			return true;
		}
		return false;
	}

	public boolean isSummoningNpc(int i) {
		switch (npcs[i].npcType) {
		case 6830:
		case 6826:
		case 6842:
		case 6807:
		case 6797:
		case 7332:
		case 6832:
		case 6838:
		case 7362:
		case 6848:
		case 6995:
		case 6872:
		case 7354:
		case 6836:
		case 6846:
		case 6808:
		case 7371:
		case 7369:
		case 7368:
		case 7370:
		case 7352:
		case 6854:
		case 6868:
		case 6852:
		case 6834:
		case 6856:
		case 7378:
		case 6824:
		case 6844:
		case 6795:
		case 6819:
		case 6993:
		case 6858:
		case 6991:
		case 7364:
		case 7366:
		case 7338:
		case 6810:
		case 6821:
		case 6803:
		case 6828:
		case 6860:
		case 6890:
		case 6816:
		case 6814:
		case 7372:
		case 7373:
		case 7374:
		case 6840:
		case 6817:
		case 8576:
		case 7346:
		case 6799:
		case 6850:
		case 6862:
		case 7336:
		case 6801:
		case 7356:
		case 7358:
		case 7360:
		case 6812:
		case 6805:
		case 7342:
		case 7330:
		case 6864:
		case 6823:
		case 7340:
		case 6870:
		case 7350:
		case 7376:
		case 6874:
		case 7344:
			return true;
		}
		return false;
	}

	public boolean isGWDNpc(int i) {
		switch (npcs[i].npcType) {
		/* Bandos Room */
		case 6260:
		case 6261:
		case 6263:
		case 6265:
			return true;

		/* Zamorak Room */
		case 3810:
		case 6204:
		case 6206:
		case 6208:
			return true;

		/* Armadyl Room */
		case 6222:
		case 6223:
		case 6225:
		case 6227:
			return true;

		/* Sara Room */
		case 6247:
		case 6248:
		case 6250:
		case 6252:
			return true;
		}
		return false;
	}

	public boolean isChampionBattleNPC(int i) {
		switch (npcs[i].npcType) {
		case 50:
		case 2745:
		case 5666:
		case 6247:
		case 6260:
		case 2:
			return true;
		}
		return false;
	}

	/**
	 * Summon npc, barrows, etc
	 **/
	public static void spawnNpc(final Client c, int npcType, int x, int y, int heightLevel, int WalkingType, int HP, int maxHit, int attack, int defence, boolean attackPlayer, boolean headIcon) {
		// first, search for a free slot
		int slot = -1;
		for (int i = 1; i < maxNPCs; i++) {
			if (npcs[i] == null) {
				slot = i;
				break;
			}
		}
		if (slot == -1) {
			// MistexUtility.println("No Free Slot");
			return; // no free slot found
		}
		final NPC newNPC = new NPC(slot, npcType);
		newNPC.absX = x;
		newNPC.absY = y;
		newNPC.makeX = x;
		newNPC.makeY = y;
		newNPC.heightLevel = heightLevel;
		newNPC.walkingType = WalkingType;
		newNPC.HP = HP;
		newNPC.MaxHP = HP;
		newNPC.maxHit = maxHit;
		newNPC.attack = attack;
		newNPC.defence = defence;
		newNPC.spawnedBy = c.getId();
		if (headIcon)
			c.getPA().drawHeadicon(1, slot, 0, 0);
		if (attackPlayer) {
			newNPC.underAttack = true;
			if (c != null) {
				newNPC.killerId = c.playerId;
			}
		}
		npcs[slot] = newNPC;
	}

	public static int getAttackEmote(int i) {
		return AttackAnimation.handleEmote(i);
	}

	public int getDeadEmote(final int i) {
		return DeathAnimation.handleEmote(i);
	}

	/**
	 * Attack delays
	 **/
	public int getNpcDelay(int i) {
		switch (npcs[i].npcType) {
		case 8133: // Corporeal beast
		case 3101: // Melee
		case 3102: // Range
		case 3103: // Mage
			return 7;
		case 2025:
		case 2028:
			return 7;
		case 8127: // Dark Core
			return 2;

		case 2745:
			return 8;

		case 2558:
		case 2559:
		case 2560:
		case 2561:
		case 6260:
			return 6;
		// saradomin gw boss
		case 2562:
			return 2;

		default:
			return 5;
		}
	}

	/**
	 * Hit delays
	 **/
	public int getHitDelay(int i) {
		switch (npcs[i].npcType) {
		case 2881:
		case 2882:
		case 3200:
		case 2892:
		case 2894:
			return 3;

		case 2743:
		case 2631:
		case 2558:
		case 2559:
		case 2560:
			return 3;
		case 2745:
			if (npcs[i].attackType == 1 || npcs[i].attackType == 2) {
				return 5;
			} else {
				return 2;
			}

		case 2025:
			return 4;
		case 2028:
			return 3;

		default:
			return 2;
		}
	}

	/**
	 * Npc respawn time
	 **/
	public int getRespawnTime(int i) {
		if (isGWDNpc(i))
			return 60;
		switch (npcs[i].npcType) {
		case 8133: // Corporeal beast
			return 140;
		case 8528:
			return 150;
		case 6998:
		case 6999:
		case 6615:
		case 6625:
		case 6691:
		case 6647:
		case 6645:
		case 6688:
		case 6611:
		case 6605:
			return 30;
		case 1158:
		case 1160:
		case 6142:
		case 6143:
		case 6144:
		case 6145:
		case 6252:
		case 6250:
		case 6248:
		case 3073:
		case 5808:
		case 7772:
		case 7135:
		case 13454:
		case 13451:
		case 5631:
		case 13452:
		case 7643:
			return -1;
		case 5085:
		case 5084:
		case 5083:
		case 5082:
		case 6225:
		case 6223:
		case 6265:
		case 6263:
		case 6261:
		case 6208:
		case 6204:
		case 6206:
		case 6227:
			return 10;
		case 2881:
		case 2882:
		case 2883:
		case 2558:
		case 2559:
		case 2560:
		case 2561:
		case 2562:
		case 2563:
		case 2564:
		case 6260:
		case 6222:
		case 6203:
		case 6247:
			return 60;
		case 3777:
		case 3778:
		case 3779:
		case 3780:
			return 500;
		case 10770:
		case 10771:
		case 10772:
		case 10773:
		case 10774:
		case 10775:
		case 10219:
		case 10220:
		case 10221:
		case 10222:
		case 10223:
		case 10224:
		case 10604:
		case 10605:
		case 10606:
		case 10607:
		case 10608:
		case 10609:
		case 10776:
		case 10777:
		case 10778:
		case 10779:
		case 10780:
		case 10781:
		case 10815:
		case 10816:
		case 10817:
		case 10818:
		case 10819:
		case 10820:
		case 4291: // Cyclops
		case 4292: // Ice cyclops
		case 53:
		case 54:
		case 55:
		case 941:
		case 1590:
		case 1591:
		case 1592:
			return 50;
		default:
			return 25;
		}
	}

	public void newNPC(int npcType, int x, int y, int heightLevel, int WalkingType, int HP, int maxHit, int attack, int defence) {
		// first, search for a free slot
		int slot = -1;
		for (int i = 1; i < maxNPCs; i++) {
			if (npcs[i] == null) {
				slot = i;
				break;
			}
		}

		if (slot == -1)
			return; // no free slot found

		NPC newNPC = new NPC(slot, npcType);
		newNPC.absX = x;
		newNPC.absY = y;
		newNPC.makeX = x;
		newNPC.makeY = y;
		newNPC.heightLevel = heightLevel;
		newNPC.walkingType = WalkingType;
		newNPC.HP = HP;
		newNPC.MaxHP = HP;
		newNPC.maxHit = maxHit;
		newNPC.attack = attack;
		newNPC.defence = defence;
		npcs[slot] = newNPC;
	}

	public void newNPCList(int npcType, String npcName, int combat, int HP) {

		NPCDefinitions newNPCList = new NPCDefinitions(npcType);
		newNPCList.setNpcName(npcName);
		newNPCList.setNpcCombat(combat);
		newNPCList.setNpcHealth(HP);
		NPCDefinitions.getDefinitions()[npcType] = newNPCList;
	}

	public void handleClipping(int i) {
		NPC npc = npcs[i];
		if (npc.moveX == 1 && npc.moveY == 1) {
			if ((Region.getClipping(npc.absX + 1, npc.absY + 1, npc.heightLevel) & 0x12801e0) != 0) {
				npc.moveX = 0;
				npc.moveY = 0;
				if ((Region.getClipping(npc.absX, npc.absY + 1, npc.heightLevel) & 0x1280120) == 0)
					npc.moveY = 1;
				else
					npc.moveX = 1;
			}
		} else if (npc.moveX == -1 && npc.moveY == -1) {
			if ((Region.getClipping(npc.absX - 1, npc.absY - 1, npc.heightLevel) & 0x128010e) != 0) {
				npc.moveX = 0;
				npc.moveY = 0;
				if ((Region.getClipping(npc.absX, npc.absY - 1, npc.heightLevel) & 0x1280102) == 0)
					npc.moveY = -1;
				else
					npc.moveX = -1;
			}
		} else if (npc.moveX == 1 && npc.moveY == -1) {
			if ((Region.getClipping(npc.absX + 1, npc.absY - 1, npc.heightLevel) & 0x1280183) != 0) {
				npc.moveX = 0;
				npc.moveY = 0;
				if ((Region.getClipping(npc.absX, npc.absY - 1, npc.heightLevel) & 0x1280102) == 0)
					npc.moveY = -1;
				else
					npc.moveX = 1;
			}
		} else if (npc.moveX == -1 && npc.moveY == 1) {
			if ((Region.getClipping(npc.absX - 1, npc.absY + 1, npc.heightLevel) & 0x128013) != 0) {
				npc.moveX = 0;
				npc.moveY = 0;
				if ((Region.getClipping(npc.absX, npc.absY + 1, npc.heightLevel) & 0x1280120) == 0)
					npc.moveY = 1;
				else
					npc.moveX = -1;
			}
		} // Checking Diagonal movement.

		if (npc.moveY == -1) {
			if ((Region.getClipping(npc.absX, npc.absY - 1, npc.heightLevel) & 0x1280102) != 0)
				npc.moveY = 0;
		} else if (npc.moveY == 1) {
			if ((Region.getClipping(npc.absX, npc.absY + 1, npc.heightLevel) & 0x1280120) != 0)
				npc.moveY = 0;
		} // Checking Y movement.
		if (npc.moveX == 1) {
			if ((Region.getClipping(npc.absX + 1, npc.absY, npc.heightLevel) & 0x1280180) != 0)
				npc.moveX = 0;
		} else if (npc.moveX == -1) {
			if ((Region.getClipping(npc.absX - 1, npc.absY, npc.heightLevel) & 0x1280108) != 0)
				npc.moveX = 0;
		} // Checking X movement.
	}

	public void process() {
		for (int i = 0; i < maxNPCs; i++) {
			if (npcs[i] == null)
				continue;
			npcs[i].clearUpdateFlags();

		}

		for (int i = 0; i < maxNPCs; i++) {
			if (npcs[i] != null) {
				if (npcs[i].actionTimer > 0) {
					npcs[i].actionTimer--;
				}

				if (npcs[i].freezeTimer > 0) {
					npcs[i].freezeTimer--;
				}

				if (npcs[i].hitDelayTimer > 0) {
					npcs[i].hitDelayTimer--;
				}

				if (npcs[i].hitDelayTimer == 1) {
					npcs[i].hitDelayTimer = 0;
					applyDamage(i);
				}

				if (npcs[i].attackTimer > 0) {
					npcs[i].attackTimer--;
				}

				if (npcs[i].spawnedBy > 0) { // delete summons npc
					if (World.players[npcs[i].spawnedBy] == null || World.players[npcs[i].spawnedBy].heightLevel != npcs[i].heightLevel || World.players[npcs[i].spawnedBy].respawnTimer > 0
							|| !World.players[npcs[i].spawnedBy].goodDistance(npcs[i].getX(), npcs[i].getY(), World.players[npcs[i].spawnedBy].getX(), World.players[npcs[i].spawnedBy].getY(), 20)) {

						if (World.players[npcs[i].spawnedBy] != null) {
							for (int o = 0; o < World.players[npcs[i].spawnedBy].barrowsNpcs.length; o++) {
								if (npcs[i].npcType == World.players[npcs[i].spawnedBy].barrowsNpcs[o][0]) {
									if (World.players[npcs[i].spawnedBy].barrowsNpcs[o][1] == 1)
										World.players[npcs[i].spawnedBy].barrowsNpcs[o][1] = 0;
								}
							}
						}
						npcs[i] = null;
					}
				}
				if (npcs[i] == null)
					continue;
				if (npcs[i].lastX != npcs[i].getX() || npcs[i].lastY != npcs[i].getY()) {
					npcs[i].lastX = npcs[i].getX();
					npcs[i].lastY = npcs[i].getY();
				}
				if (npcs[i].summon == true) {
					Client c = (Client) Mistex.playerHandler.players[npcs[i].spawnedBy];

					if (c != null && c.playerIndex < 1 && npcs[i].summon == true) {
						if (!npcs[i].underAttack) {
							if (!Mistex.playerHandler.players[npcs[i].spawnedBy].goodDistance(npcs[i].getX(), npcs[i].getY(), Mistex.playerHandler.players[npcs[i].spawnedBy].getX(), Mistex.playerHandler.players[npcs[i].spawnedBy].getY(), 2) && c.npcIndex < 1)
								followPlayer(i, c.playerId);
						}
					} else {
						if (c != null && npcs[i].summon == true) {
							if (!Mistex.playerHandler.players[npcs[i].spawnedBy].goodDistance(npcs[i].getX(), npcs[i].getY(), Mistex.playerHandler.players[npcs[i].spawnedBy].getX(), Mistex.playerHandler.players[npcs[i].spawnedBy].getY(), 5) && c.playerIndex < 1 || c.npcIndex < 1) {
								followPlayer(i, c.playerId);
							}
						}

					}

					if (c != null && c.lastsummon > 0 && !Mistex.playerHandler.players[npcs[i].spawnedBy].goodDistance(npcs[i].getX(), npcs[i].getY(), Mistex.playerHandler.players[npcs[i].spawnedBy].getX(), Mistex.playerHandler.players[npcs[i].spawnedBy].getY(), 10) && npcs[i].summon == true && !npcs[i].isDead) {
						npcs[i].isDead = true;
						npcs[i].applyDead = true;
						c.Summoning.SummonNewNPC(c.lastsummon, false);
						npcs[i].gfx0(1315);
						npcs[i].underAttackBy2 = -1;
						npcs[i].updateRequired = true;
						npcs[i].dirUpdateRequired = true;
						npcs[i].getNextWalkingDirection();
					}

					if (c != null && c.lastsummon < 0 || c == null) {
						npcs[i].isDead = true;
						npcs[i].applyDead = true;
						npcs[i].summon = false;
						npcs[i].underAttackBy2 = -1;
					}

					if (c != null && npcs[i].actionTimer < 1 && npcs[i].summon == true) {
						if (c.playerIndex > 0) {
							Client o = (Client) Mistex.playerHandler.players[c.playerIndex];
							if (o != null) {
								if (npcs[i].IsAttackingPerson = true && o.inMulti()) {
									followPlayer(i, o.playerId);
									attackPlayer(o, i);
									npcs[i].index = o.playerId;
									npcs[i].actionTimer = 7;
								}
							}
						}
					}
				}

				/**
				 * Attacking player
				 **/
				if (isAggressive(i) && !npcs[i].underAttack && !npcs[i].isDead && !switchesAttackers(i)) {
					npcs[i].killerId = getCloseRandomPlayer(i);
				} else if (isAggressive(i) && !npcs[i].underAttack && !npcs[i].isDead && switchesAttackers(i)) {
					npcs[i].killerId = getCloseRandomPlayer(i);
				}

				if (System.currentTimeMillis() - npcs[i].lastDamageTaken > 5000)
					npcs[i].underAttackBy = 0;

				if ((npcs[i].killerId > 0 || npcs[i].underAttack) && !npcs[i].walkingHome && retaliates(npcs[i].npcType)) {
					if (!npcs[i].isDead) {
						int p = npcs[i].killerId;
						if (World.players[p] != null) {
							Client c = (Client) World.players[p];
							followPlayer(i, c.playerId);
							if (npcs[i] == null)
								continue;
							if (npcs[i].attackTimer == 0) {
								attackPlayer(c, i);
							}
						} else {
							npcs[i].killerId = 0;
							npcs[i].underAttack = false;
							npcs[i].facePlayer(0);
						}
					}
				}

				/**
				 * Random walking and walking home
				 **/
				if (npcs[i] == null)
					continue;
				if ((!npcs[i].underAttack || npcs[i].walkingHome) && npcs[i].randomWalk && !npcs[i].isDead) {
					npcs[i].facePlayer(0);
					npcs[i].killerId = 0;
					if (npcs[i].spawnedBy == 0) {
						if ((npcs[i].absX > npcs[i].makeX + MistexConfiguration.NPC_RANDOM_WALK_DISTANCE) || (npcs[i].absX < npcs[i].makeX - MistexConfiguration.NPC_RANDOM_WALK_DISTANCE) || (npcs[i].absY > npcs[i].makeY + MistexConfiguration.NPC_RANDOM_WALK_DISTANCE)
								|| (npcs[i].absY < npcs[i].makeY - MistexConfiguration.NPC_RANDOM_WALK_DISTANCE)) {
							npcs[i].walkingHome = true;
						}
					}

					if (npcs[i].walkingHome && npcs[i].absX == npcs[i].makeX && npcs[i].absY == npcs[i].makeY) {
						npcs[i].walkingHome = false;
					} else if (npcs[i].walkingHome) {
						npcs[i].moveX = GetMove(npcs[i].absX, npcs[i].makeX);
						npcs[i].moveY = GetMove(npcs[i].absY, npcs[i].makeY);
						npcs[i].getNextNPCMovement(i);
						// handleClipping(i);
						npcs[i].updateRequired = true;
					}
					if (npcs[i].walkingType == 1) {
						if (MistexUtility.random(3) == 1 && !npcs[i].walkingHome) {
							int MoveX = 0;
							int MoveY = 0;
							int Rnd = MistexUtility.random(9);
							if (Rnd == 1) {
								MoveX = 1;
								MoveY = 1;
							} else if (Rnd == 2) {
								MoveX = -1;
							} else if (Rnd == 3) {
								MoveY = -1;
							} else if (Rnd == 4) {
								MoveX = 1;
							} else if (Rnd == 5) {
								MoveY = 1;
							} else if (Rnd == 6) {
								MoveX = -1;
								MoveY = -1;
							} else if (Rnd == 7) {
								MoveX = -1;
								MoveY = 1;
							} else if (Rnd == 8) {
								MoveX = 1;
								MoveY = -1;
							}

							if (MoveX == 1) {
								if (npcs[i].absX + MoveX < npcs[i].makeX + 1) {
									npcs[i].moveX = MoveX;
								} else {
									npcs[i].moveX = 0;
								}
							}

							if (MoveX == -1) {
								if (npcs[i].absX - MoveX > npcs[i].makeX - 1) {
									npcs[i].moveX = MoveX;
								} else {
									npcs[i].moveX = 0;
								}
							}

							if (MoveY == 1) {
								if (npcs[i].absY + MoveY < npcs[i].makeY + 1) {
									npcs[i].moveY = MoveY;
								} else {
									npcs[i].moveY = 0;
								}
							}

							if (MoveY == -1) {
								if (npcs[i].absY - MoveY > npcs[i].makeY - 1) {
									npcs[i].moveY = MoveY;
								} else {
									npcs[i].moveY = 0;
								}
							}

							int x = (npcs[i].absX + npcs[i].moveX);
							int y = (npcs[i].absY + npcs[i].moveY);
							npcs[i].getNextNPCMovement(i);
							// handleClipping(i);
							npcs[i].moveX = 0;
							npcs[i].moveY = 0;
						}
						npcs[i].updateRequired = true;

					}
				}

				if (npcs[i].isDead == true) {
					if (npcs[i].actionTimer == 0 && npcs[i].applyDead == false && npcs[i].needRespawn == false) {
						npcs[i].updateRequired = true;
						npcs[i].facePlayer(0);
						npcs[i].killedBy = getNpcKillerId(i);
						npcs[i].animNumber = getDeadEmote(i); // dead emote
						npcs[i].animUpdateRequired = true;
						npcs[i].freezeTimer = 0;
						npcs[i].applyDead = true;
						if (isFightCaveNpc(i))
							killedTzhaar(i);
						if (isChampionBattleNPC(i))
							killedChampionsNPC(i);
						killedBarrow(i);
						killedCrypt(i);
						npcs[i].actionTimer = 4; // delete time
						resetPlayersInCombat(i);
					} else if (npcs[i].actionTimer == 0 && npcs[i].applyDead == true && npcs[i].needRespawn == false) {
						npcs[i].needRespawn = true;
						npcs[i].actionTimer = getRespawnTime(i); // respawn time
						dropItems(i);
						appendSlayerExperience(i);
						appendDuoSlayerExperience(i);
						appendKillCount(i);
						// appendJailKc(i);
						npcs[i].absX = npcs[i].makeX;
						npcs[i].absY = npcs[i].makeY;
						npcs[i].HP = npcs[i].MaxHP;
						npcs[i].animNumber = 0x328;
						npcs[i].updateRequired = true;
						npcs[i].animUpdateRequired = true;
						if (npcs[i].npcType == 2745) {
							handleJadDeath(i);
						}

						if (npcs[i].npcType == 8127) {
							npcs[i].removeNpc = true;
							continue;
						}
						if (npcs[i].npcType == 2) {
							handleChampionsBattleBoss(i);
						}
					} else if (npcs[i].actionTimer == 0 && npcs[i].needRespawn == true && npcs[i].npcType != 1158) {
						Client player = (Client) World.players[npcs[i].spawnedBy];
						if (player != null) {
							npcs[i] = null;
						} else {
							int old1 = npcs[i].npcType;
							int old2 = npcs[i].makeX;
							int old3 = npcs[i].makeY;
							int old4 = npcs[i].heightLevel;
							int old5 = npcs[i].walkingType;
							int old6 = npcs[i].MaxHP;
							int old7 = npcs[i].maxHit;
							int old8 = npcs[i].attack;
							int old9 = npcs[i].defence;

							npcs[i] = null;
							newNPC(old1, old2, old3, old4, old5, old6, old7, old8, old9);
						}
					}
				}
			}
		}
	}

	/**
	 * Duo Slayer Experience
	 **/
	public static void appendDuoSlayerExperience(int i) {
		Client c = (Client) World.players[npcs[i].killedBy];
		boolean k = false;
		int slayerXP = PlayerConfiguration.SLAYER_EXPERIENCE * 8;
		int partnerSlayerXP = slayerXP / 2;
		if (c != null) {
			if (c.duoTask == npcs[i].npcType) {
				k = true;
			}
			if (k) {
				c.duoTaskAmount--;
				c.getPA().addSkillXP(slayerXP, 18);
				if (c.getDuoPartner() != null)
					c.getDuoPartner().duoTaskAmount--;
				// if (c.getDuoPartner() != null &&
				// c.getDuoPartner().connectedFrom != c.connectedFrom) {
				// ((Client) partna).getPA().addSkillXP(partnerSlayerXP, 18);
				// }
			}
			if (c.duoTaskAmount == 0) {
				final Player partner = c.getDuoPartner();
				DuoSlayer.getInstance().complete(c, partner);
			}
		}
	}

	public void appendSlayerExperience(int i) {
		int npc = 0;
		Client c = (Client) World.players[npcs[i].killedBy];
		if (c != null) {
			if (c.getSlayer().isSlayerTask(npcs[i].npcType)) {
				c.taskAmount--;
				c.getPA().addSkillXP(c.getSlayer().getTaskExperience(npcs[i].npcType) * 2, 18);
				if (c.taskAmount <= 0) {
					c.getPA().addSkillXP(c.getSlayer().getTaskExperience(npcs[i].npcType) * 5, 18);
					c.slayerTask = -1;
					c.totalTasks += 1;
					int points = Slayer.getReceivedPoints(c, c.getSlayer().getDifficulty(npcs[i].npcType));
					c.slayerPoints += points;
					c.sendMessage("You've completed " + c.totalTasks + " task in a row and received " + points + " points.");
					if (c.getRights().isDonator()) {
						int donatorPoints = MistexUtility.random(10);
						c.slayerPoints += donatorPoints;
						c.sendMessage("Because you are a donator you recieved an additional " + donatorPoints + " points.");

					}
				}
			}
		}
	}

	public boolean getsPulled(int i) {
		switch (npcs[i].npcType) {
		case 6260:
			if (npcs[i].firstAttacker > 0)
				return false;
			break;
		}
		return true;
	}

	public boolean multiAttacks(int i) {
		switch (npcs[i].npcType) {
		case 2558:
			return true;
		case 8133: // Corporeal beast
			if (npcs[i].attackType == 2)
				return true;
		case 1158: // kq
			if (npcs[i].attackType == 2)
				return true;
		case 1160: // kq
			if (npcs[i].attackType == 1)
				return true;
		case 2562:
			if (npcs[i].attackType == 2)
				return true;
		case 6260:
			if (npcs[i].attackType == 1)
				return true;
		default:
			return false;
		}

	}

	/**
	 * Npc killer id?
	 **/

	public int getNpcKillerId(int npcId) {
		int oldDamage = 0;
		int count = 0;
		int killerId = 0;
		for (int p = 1; p < MistexConfiguration.MAX_PLAYERS; p++) {
			if (World.players[p] != null) {
				if (World.players[p].lastNpcAttacked == npcId) {
					if (World.players[p].totalDamageDealt > oldDamage) {
						oldDamage = World.players[p].totalDamageDealt;
						killerId = p;
					}
					World.players[p].totalDamageDealt = 0;
				}
			}
		}
		return killerId;
	}

	private void killedBarrow(int i) {
		Client c = (Client) Mistex.playerHandler.players[npcs[i].killedBy];
		if (c != null) {
			if (c.getBarrowsChallenge().started) {
				challengeBarrow(i);
				return;
			}
			for (int o = 0; o < c.barrowsNpcs.length; o++) {
				if (npcs[i].npcType == c.barrowsNpcs[o][0]) {
					c.barrowsNpcs[o][1] = 2; // 2 for dead
					c.barrowsKillCount++;
				}
			}
		}
	}

	private void killedCrypt(int i) {
		Client c = (Client) Mistex.playerHandler.players[npcs[i].killedBy];
		if (c != null) {
			for (int o = 0; o < c.barrowCrypt.length; o++) {
				if (npcs[i].npcType == c.barrowCrypt[o][0]) {
					c.barrowsKillCount++;
					c.getPA().sendFrame126("" + c.barrowsKillCount, 16137);
				}
			}
		}
	}

	public void challengeBarrow(int i) {
		Client c = (Client) Mistex.playerHandler.players[npcs[i].killedBy];
		if (c != null) {
			for (int o = 0; o < c.barrowsNpcs.length; o++) {
				if (c.barrowsChallengeKC < 6) {
					if (npcs[i].npcType == c.barrowsNpcs[o][0]) {
						c.barrowsChallengeKC++;
					}
				} else if (c.barrowsChallengeKC == 6) {
					c.getBarrowsChallenge().end();
				}
			}
		}
	}

	private void killedTzhaar(int i) {
		final Client c2 = (Client) World.players[npcs[i].spawnedBy];
		c2.tzhaarKilled++;
		if (c2.tzhaarKilled == c2.tzhaarToKill) {
			c2.waveId++;
			if (c2 != null) {
				Mistex.fightCaves.spawnNextWave(c2);
			}

		}
	}

	public void handleJadDeath(int i) {
		Client c = (Client) World.players[npcs[i].spawnedBy];
		if (!(c.inChampionsBattle) && !(c.inChampionsBattle())) {
			c.getItems().addItem(6570, 1);
			c.getItems().addItem(6529, MistexUtility.random(500));
			c.isKillingJad = false;
			c.jadKills += 1;
			c.getPA().yell("[ <col=C42BAD>Mistex</col> ] " + c.getRights().determineIcon() + "<col=C42BAD>" + MistexUtility.capitalize(c.playerName) + "</col> has killed Tztok Jad!");
			c.sendMessage("Congratulations on completing the fight caves minigame!");
			c.getPA().resetTzhaar();
			c.waveId = 300;
			if (c.jadKills == 1)
				c.sendMessage("@war@You have started the achievement: Jad Killer");
			InterfaceText.writeText(new AchievementTab(c));
			if (c.jadKills == 15)
				AchievementHandler.activateAchievement(c, AchievementList.JAD_KILLER);
		}
	}

	public void dropItems(int i) {
		if (dontdrop) {
			return;
		}
		int npc = 0;
		Client c = (Client) World.players[npcs[i].killedBy];
		if (c != null) {

			/*
			 * if(npcs[i].npcType == 4291 && c.inCyclops) { int random2 =
			 * MistexUtility.random(4); if(random2 == 1) {
			 * Mistex.itemHandler.createGroundItem(c,
			 * Mistex.getWarriorsGuild().getCyclopsDrop(c), npcs[i].absX,
			 * npcs[i].absY, 1, c.playerId); } } int[] animatedArmor = {4278,
			 * 4279, 4280, 4281, 4282, 4283, 4284}; int[] tokens = {10, 20, 30,
			 * 40, 50, 60, 70}; for(int f = 0; f < animatedArmor.length; f++) {
			 * if (npcs[i].npcType == animatedArmor[f]) {
			 * Mistex.getWarriorsGuild().setSpawned(c, false);
			 * Mistex.itemHandler.createGroundItem(c, 8851, npcs[i].absX,
			 * npcs[i].absY, tokens[f], c.playerId); } }
			 */

			if (npcs[i].npcType == 6367 && c.AcQuest == 8) {
				Mistex.itemHandler.createGroundItem(c, 291, npcs[i].absX, npcs[i].absY, 1, c.playerId);
				c.AcQuest = 9;
			}

			if (npcs[i].npcType == 1265) {
				c.rockCrabKills += 1;
				if (c.rockCrabKills == 1)
					c.sendMessage("@war@You have started the achievement: Crabs");
				InterfaceText.writeText(new AchievementTab(c));
				if (c.rockCrabKills == 100)
					AchievementHandler.activateAchievement(c, AchievementList.CRABS);
			}

			if (npcs[i].npcType == 912 || npcs[i].npcType == 913 || npcs[i].npcType == 914) {
				c.magePoints += 1;
			}

			if (npcs[i].npcType == 10422 || npcs[i].npcType == 10430 || npcs[i].npcType == 10415 || npcs[i].npcType == 10410 || npcs[i].npcType == 10335 || npcs[i].npcType == 10330) {
				c.dungeoneeringKills += 1;
				c.sendMessage("DK = " + c.dungeoneeringKills);
			}

			if (!(c.inChampionsBattle) && !(c.inChampionsBattle())) {
				double special = Math.random();
				int[] specials = new int[] { 2677, 2678, 2679, 985, 987 };
				if (special > 1 - (1 / 75.0)) {
					Mistex.itemHandler.createGroundItem(c, specials[MistexUtility.random(specials.length - 1)], npcs[i].absX, npcs[i].absY, 1, c.playerId);
				}

				double charm = Math.random();
				int[] charms = new int[] { 12158, 12159, 12160, 12161, 12163, };
				if (charm > 1 - (1 / 3.0)) {

					Mistex.itemHandler.createGroundItem(c, charms[MistexUtility.random(charms.length - 1)], npcs[i].absX, npcs[i].absY, 1, c.playerId);

				}

				NpcDrop.dropItemForNpc(c, npcs[i].npcType, npcs[i].absX, npcs[i].absY, npcs[i].heightLevel);
			}
		}
	}

	// id of bones dropped by npcs
	public int boneDrop(int type) {
		switch (type) {
		case 1: // normal bones
		case 9:
		case 100:
		case 12:
		case 17:
		case 803:
		case 18:
		case 81:
		case 101:
		case 41:
		case 19:
		case 90:
		case 75:
		case 86:
		case 78:
		case 912:
		case 913:
		case 914:
		case 1648:
		case 1643:
		case 1618:
		case 1624:
		case 181:
		case 119:
		case 49:
		case 26:
		case 1341:
			return 526;
		case 117:
			return 532; // big bones
		case 50: // drags
		case 53:
		case 54:
		case 55:
		case 941:
		case 1590:
		case 1591:
		case 1592:
			return 536;
		case 84:
		case 1615:
		case 1613:
		case 82:
		case 3200:
			return 592;
		case 2881:
		case 2882:
		case 2883:
			return 6729;
		case 10775:
			return 18830;
		default:
			return -1;
		}
	}

	public void appendKillCount(int i) {
		Client c = (Client) World.players[npcs[i].killedBy];
		if (c != null) {
			int[] kcMonsters = { 122, 49, 2558, 2559, 2560, 2561, 2550, 2551, 2552, 2553, 2562, 2563, 2564, 2565 };
			for (int j : kcMonsters) {
				if (npcs[i].npcType == j) {
					if (c.killCount < 20) {
						// c.killCount++;
						// c.sendMessage("Killcount: " + c.killCount);
					} else {
						// c.sendMessage("You already have 20 kill count");
					}
					break;
				}
			}
		}
	}

	public int getStackedDropAmount(int itemId, int npcId) {
		switch (itemId) {
		case 995:
			switch (npcId) {
			case 1:
				return 50 + MistexUtility.random(50);
			case 9:
				return 133 + MistexUtility.random(100);
			case 1624:
				return 1000 + MistexUtility.random(300);
			case 1618:
				return 1000 + MistexUtility.random(300);
			case 1643:
				return 1000 + MistexUtility.random(300);
			case 1610:
				return 1000 + MistexUtility.random(1000);
			case 1613:
				return 1500 + MistexUtility.random(1250);
			case 1615:
				return 3000;
			case 18:
				return 500;
			case 101:
				return 60;
			case 913:
			case 912:
			case 914:
				return 750 + MistexUtility.random(500);
			case 1612:
				return 250 + MistexUtility.random(500);
			case 1648:
				return 250 + MistexUtility.random(250);
			case 90:
				return 200;
			case 82:
				return 1000 + MistexUtility.random(455);
			case 52:
				return 400 + MistexUtility.random(200);
			case 49:
				return 1500 + MistexUtility.random(2000);
			case 1341:
				return 1500 + MistexUtility.random(500);
			case 26:
				return 500 + MistexUtility.random(100);
			case 20:
				return 750 + MistexUtility.random(100);
			case 21:
				return 890 + MistexUtility.random(125);
			case 117:
				return 500 + MistexUtility.random(250);
			case 2607:
				return 500 + MistexUtility.random(350);
			}
			break;
		case 11212:
			return 10 + MistexUtility.random(4);
		case 565:
		case 561:
			return 10;
		case 560:
		case 563:
		case 562:
			return 15;
		case 555:
		case 554:
		case 556:
		case 557:
			return 20;
		case 892:
			return 40;
		case 886:
			return 100;
		case 6522:
			return 6 + MistexUtility.random(5);

		}

		return 1;
	}

	public void resetPlayersInCombat(int i) {
		for (int j = 0; j < World.players.length; j++) {
			if (World.players[j] != null)
				if (World.players[j].underAttackBy2 == i)
					World.players[j].underAttackBy2 = 0;
		}
	}

	/**
	 * Npc names
	 **/

	public static String getNpcName(int npcId) {
		if (npcId <= -1) {
			return "None";
		}
		if (NPCDefinitions.getDefinitions()[npcId] == null) {
			return "None";
		}
		return NPCDefinitions.getDefinitions()[npcId].getNpcName();
	}

	/**
	 * Npc Follow Player
	 **/

	public static int GetMove(int Place1, int Place2) {
		if ((Place1 - Place2) == 0) {
			return 0;
		} else if ((Place1 - Place2) < 0) {
			return 1;
		} else if ((Place1 - Place2) > 0) {
			return -1;
		}
		return 0;
	}

	public boolean followPlayer(int i) {
		switch (npcs[i].npcType) {
		case 2892:
		case 2894:
			/*
			 * case 2030: case 2029: case 2028: case 2027: case 2026: case 2025:
			 */
			return false;
		}
		return true;
	}

	public void followPlayer(int i, int playerId) {
		if (World.players[playerId] == null) {
			return;
		}
		int[] noMove = { 7770, 6142, 6143, 6144, 6145, 2894, 2896, 6006, 6007, 6008, 1944, 1946, 13454, 13451, 5631, 13452 };
		for (int no = 0; no < noMove.length; no++) {
			if (npcs[i].npcType == noMove[no]) {
				return;
			}
		}
		if (World.players[playerId].respawnTimer > 0) {
			npcs[i].facePlayer(0);
			npcs[i].randomWalk = true;
			npcs[i].underAttack = false;
			return;
		}
		if (!followPlayer(i)) {
			npcs[i].facePlayer(playerId);
			return;
		}

		int playerX = World.players[playerId].absX;
		int playerY = World.players[playerId].absY;
		npcs[i].randomWalk = false;
		if (goodDistance(npcs[i].getX(), npcs[i].getY(), playerX, playerY, distanceRequired(i)))
			return;
		if ((npcs[i].spawnedBy > 0) || ((npcs[i].absX < npcs[i].makeX + MistexConfiguration.NPC_FOLLOW_DISTANCE) && (npcs[i].absX > npcs[i].makeX - MistexConfiguration.NPC_FOLLOW_DISTANCE) && (npcs[i].absY < npcs[i].makeY + MistexConfiguration.NPC_FOLLOW_DISTANCE) && (npcs[i].absY > npcs[i].makeY - MistexConfiguration.NPC_FOLLOW_DISTANCE))) {
			if (npcs[i].heightLevel == World.players[playerId].heightLevel) {
				if (World.players[playerId] != null && npcs[i] != null) {
					if (playerY < npcs[i].absY) {
						npcs[i].moveX = GetMove(npcs[i].absX, playerX);
						npcs[i].moveY = GetMove(npcs[i].absY, playerY);
					} else if (playerY > npcs[i].absY) {
						npcs[i].moveX = GetMove(npcs[i].absX, playerX);
						npcs[i].moveY = GetMove(npcs[i].absY, playerY);
					} else if (playerX < npcs[i].absX) {
						npcs[i].moveX = GetMove(npcs[i].absX, playerX);
						npcs[i].moveY = GetMove(npcs[i].absY, playerY);
					} else if (playerX > npcs[i].absX) {
						npcs[i].moveX = GetMove(npcs[i].absX, playerX);
						npcs[i].moveY = GetMove(npcs[i].absY, playerY);
					} else if (playerX == npcs[i].absX || playerY == npcs[i].absY) {
						int o = MistexUtility.random(3);
						switch (o) {
						case 0:
							npcs[i].moveX = GetMove(npcs[i].absX, playerX);
							npcs[i].moveY = GetMove(npcs[i].absY, playerY + 1);
							break;

						case 1:
							npcs[i].moveX = GetMove(npcs[i].absX, playerX);
							npcs[i].moveY = GetMove(npcs[i].absY, playerY - 1);
							break;

						case 2:
							npcs[i].moveX = GetMove(npcs[i].absX, playerX + 1);
							npcs[i].moveY = GetMove(npcs[i].absY, playerY);
							break;

						case 3:
							npcs[i].moveX = GetMove(npcs[i].absX, playerX - 1);
							npcs[i].moveY = GetMove(npcs[i].absY, playerY);
							break;
						}
					}
					int x = (npcs[i].absX + npcs[i].moveX);
					int y = (npcs[i].absY + npcs[i].moveY);
					npcs[i].facePlayer(playerId);
					// if (checkClipping(i))
					// handleClipping(i);
					npcs[i].getNextNPCMovement(i);
					/*
					 * else { npcs[i].moveX = 0; npcs[i].moveY = 0; }
					 */
					npcs[i].facePlayer(playerId);
					npcs[i].updateRequired = true;
				}
			}
		} else {
			npcs[i].facePlayer(0);
			npcs[i].randomWalk = true;
			npcs[i].underAttack = false;
		}
	}

	/**
	 * load spell
	 **/
	public void loadSpell2(int i) {
		npcs[i].attackType = 3;
		int random = MistexUtility.random(3);
		if (random == 0) {
			npcs[i].projectileId = 393; // red
			npcs[i].endGfx = 430;
		} else if (random == 1) {
			npcs[i].projectileId = 394; // green
			npcs[i].endGfx = 429;
		} else if (random == 2) {
			npcs[i].projectileId = 395; // white
			npcs[i].endGfx = 431;
		} else if (random == 3) {
			npcs[i].projectileId = 396; // blue
			npcs[i].endGfx = 428;
		}
	}

	public void loadSpell(Client c, int i) {
		int random;
		switch (npcs[i].npcType) {
		case 8528:
			int n5 = MistexUtility.random(7);

			if (n5 == 0) {
				if (npcs[i].HP <= 1500 && npcs[i].HP >= 1200) { // Ice Barrage
					npcs[i].forceChat("Die now, in a prison of ice!");
					npcs[i].forceAnim(6986); // barrage?
					npcs[i].attackType = 2;
					if (c.freezeTimer <= 0) {
						c.freezeTimer = 15;
						c.gfx0(369);
						c.sendMessage("You have been frozen.");
					}
				}
			} else if (n5 == 1) { // Smoke Barrage
				if (npcs[i].HP <= 2500 && npcs[i].HP >= 2200) {
					npcs[i].forceChat("Fill My Soul With Smoke!");
					c.getPA().appendPoison(160);
					c.gfx0(391);
					npcs[i].attackType = 1;
					c.sendMessage("Nomad fills your lungs with a deathly smoke.");
				}
			} else if (n5 == 2) { // Shadow Barrage
				if (npcs[i].HP <= 2000 && npcs[i].HP >= 1700) {
					npcs[i].forceChat("Embrace Darkness!");
					c.gfx0(382);
					npcs[i].attackType = 1;
					c.getPA().walkableInterface(12418);
					c.sendMessage("You're encased in a unholy shadow.");
				}
			} else if (n5 == 3) { // Blood Barrage
				if (npcs[i].HP <= 1000 && npcs[i].HP >= 7500) {
					npcs[i].HP += 185;
					npcs[i].attackType = 2;
					c.gfx0(377);
					c.sendMessage("Nomad Saps your health and increases his own.");
				}
			} else if (n5 == 4) { // Turmoil
				if (npcs[i].HP <= 5000 && npcs[i].HP >= 1000) {
					npcs[i].forceAnim(6326); // turm
					npcs[i].forceChat("There is...NO ESCAPE!");
					npcs[i].hitDelayTimer += 2;
				}
			} else if (n5 == 5) { // Wrath
				if (npcs[i].HP <= 2000 && npcs[i].HP >= 0) {
					npcs[i].projectileId = 0; // melee
					npcs[i].forceChat("Feel The Wrath Of ZAROS!!");
					npcs[i].attackType = 1;
					npcs[i].forceAnim(2259); // Wrath
					c.gfx0(2260);
					c.playerLevel[3] -= 10;
					c.handleHitMask(10);
					c.getPA().refreshSkill(3);
				}
			} else if (n5 == 6) { // Normal Attack
				npcs[i].projectileId = 0; // melee
				npcs[i].attackType = 0;
			} else if (n5 == 7) { // First!
				if (npcs[i].HP <= 3000 && npcs[i].HP >= 2900) {
					npcs[i].projectileId = 0; // melee
					npcs[i].forceChat("AT LAST!!!!");
					npcs[i].attackType = 1;
				}
			}
			break;
		case 3340:
			if (npcs[i].HP <= npcs[i].MaxHP / 2) {
				if (MistexUtility.random(5) >= 4) {
					npcs[i].moleStage = 1;
					return;
				}
			}
			npcs[i].moleStage = 0;
			break;
		case 1158: // kq first form
			int kqRandom = MistexUtility.random(3);
			if (kqRandom == 2) {
				npcs[i].projectileId = 280; // gfx
				npcs[i].attackType = 2;
				npcs[i].endGfx = 279;
			} else {
				npcs[i].attackType = 0;
			}
			break;
		case 1160: // kq secondform
			int kqRandom2 = MistexUtility.random(3);
			if (kqRandom2 == 2) {
				npcs[i].projectileId = 279; // gfx
				npcs[i].attackType = 1 + MistexUtility.random(1);
				npcs[i].endGfx = 278;
			} else {
				npcs[i].attackType = 0;
			}
			break;
		case 6225:
		case 6230:
		case 6233:
		case 6234:
		case 6235:
		case 6236:
		case 6238:
		case 6241:
		case 6242:
		case 6243:
		case 6244:
		case 6245:
		case 2184:
			npcs[i].attackType = 1;
			npcs[i].projectileId = 1190;
			break;

		case 6229:
		case 6232:
		case 6239:
		case 6237:
		case 6240:
		case 6246:
			npcs[i].attackType = 1;
			npcs[i].projectileId = 35;
			break;

		case 6221:
			npcs[i].attackType = 2;
			npcs[i].endGfx = 78;
			break;

		case 6257:
			npcs[i].attackType = 2;
			npcs[i].endGfx = 76;
			break;

		case 6256:
		case 6220:
			npcs[i].attackType = 1;
			npcs[i].projectileId = 326;
			break;

		case 7463:
			npcs[i].attackType = 2;
			npcs[i].projectileId = 374;
			npcs[i].endGfx = 375;
			break;
		case 13479:
		case 13480:
			int b = MistexUtility.random(4);
			if (b > 2) {
				npcs[i].attackType = 1;
			} else {
				npcs[i].attackType = 2;
			}
			npcs[i].projectileId = 1980;
			npcs[i].endGfx = 86;
			break;
		case 2606:
			npcs[i].attackType = 1;
			npcs[i].projectileId = 442;
			npcs[i].endGfx = -1;
			break;
		case 2592:
			npcs[i].attackType = 2;
			npcs[i].projectileId = 448;
			npcs[i].endGfx = 346;
			break;

		case 907:
		case 908:
		case 909:
		case 910:
		case 911:
			npcs[i].attackType = 2;
			npcs[i].endGfx = 76;
			break;

		case 2743:
			npcs[i].attackType = 2;
			npcs[i].projectileId = 445;
			npcs[i].endGfx = 446;
			break;

		case 2741:
			if (npcs[i].HP <= npcs[i].MaxHP / 2) {
				if (MistexUtility.random(5) >= 4) {
					npcs[i].mejKot = 0;
					return;
				}
			}
			npcs[i].mejKot = 1;
			break;

		case 5346:
		case 5347:
		case 5348:
			npcs[i].attackType = 2;
			npcs[i].projectileId = 133;
			break;

		case 5297:
		case 5298:
		case 5299:
		case 5300:
			npcs[i].attackType = 1;
			npcs[i].projectileId = 35;
			break;

		case 2631:
			npcs[i].attackType = 1;
			npcs[i].projectileId = 443;
			break;

		case 2188:
			npcs[i].attackType = 1;
			npcs[i].projectileId = 1195;
			break;

		case 3071:
			npcs[i].attackType = 3;
			npcs[i].gfx100(499);
			break;

		case 1643:
			npcs[i].attackType = 2;
			npcs[i].gfx100(155);
			npcs[i].projectileId = 156;
			npcs[i].endGfx = 157;
			break;

		case 3072:
			npcs[i].endGfx = -1;
			if (npcs[i].dragon < 2) {
				npcs[i].attackType = 0;
				npcs[i].projectileId = -1;
			} else if (npcs[i].dragon == 2) {
				npcs[i].attackType = 3;
				npcs[i].projectileId = 393;
				npcs[i].multiAttack = true;
			} else if (npcs[i].dragon == 3) {
				npcs[i].attackType = 1;
				npcs[i].projectileId = 1340;
				npcs[i].multiAttack = true;
			} else if (npcs[i].dragon == 4) {
				npcs[i].attackType = 2;
				npcs[i].projectileId = 1406;
				npcs[i].multiAttack = true;
			} else if (npcs[i].dragon == 5) {
				npcs[i].gfx0(255);
				npcs[i].attackType = 2;
				npcs[i].projectileId = 434;
				npcs[i].endGfx = 435;
				npcs[i].multiAttack = true;
			} else if (npcs[i].dragon == 6) {
				npcs[i].attackType = 0;
				npcs[i].projectileId = -1;
			} else if (npcs[i].dragon == 7) {
				npcs[i].attackType = 0;
				npcs[i].projectileId = -1;
				npcs[i].multiAttack = true;
			}
			break;

		case 3073:
			if (npcs[i].dragon < 2) {
				npcs[i].attackType = 0;
				npcs[i].projectileId = -1;
			} else if (npcs[i].dragon == 2) {
				npcs[i].attackType = 3;
				npcs[i].projectileId = 393;
			} else if (npcs[i].dragon == 3) {
				npcs[i].attackType = 1;
				npcs[i].projectileId = 1340;
			} else if (npcs[i].dragon == 4) {
				npcs[i].attackType = 2;
				npcs[i].projectileId = 1288;
			}
			break;

		case 13447: // nex
			if (npcs[i].nexStage == 1 || npcs[i].nexStage == 2) {
				if (npcs[i].glod == 1) {
					npcs[i].multiAttack = true;
					npcs[i].attackType = 2;
					npcs[i].projectileId = 386;
					npcs[i].endGfx = 390;
				} else if (npcs[i].glod == 2) {
					// c.getPA().sendMp3("virus");
					npcs[i].forceChat("Let the virus flow through you!");
					npcs[i].multiAttack = true;
					npcs[i].attackType = 4;
					npcs[i].projectileId = -1;
					npcs[i].endGfx = -1;
					npcs[i].cooldown = 30;
				} else {
					npcs[i].attackType = 0;
					npcs[i].projectileId = -1;
					npcs[i].endGfx = -1;
				}
			} else if (npcs[i].nexStage == 3 || npcs[i].nexStage == 4) {
				if (npcs[i].glod == 1) {
					npcs[i].multiAttack = true;
					npcs[i].attackType = 1;
					npcs[i].projectileId = 378;
					npcs[i].endGfx = -1;
				} else if (npcs[i].glod == 2) {
					// c.getPA().sendMp3("darkenmy");
					npcs[i].forceChat("Darken my shadow!");
					npcs[i].multiAttack = true;
					npcs[i].attackType = 4;
					npcs[i].projectileId = -1;
					npcs[i].endGfx = -1;
					npcs[i].cooldown = 30;
				} else if (npcs[i].glod == 3) {
					// c.getPA().sendMp3("fearthe");
					npcs[i].forceChat("Fear the shadow!");
					npcs[i].multiAttack = true;
					npcs[i].attackType = 4;
					npcs[i].projectileId = -1;
					npcs[i].endGfx = 382;
				} else {
					npcs[i].attackType = 0;
					npcs[i].projectileId = -1;
					npcs[i].endGfx = -1;
				}
			} else if (npcs[i].nexStage == 5 || npcs[i].nexStage == 6) {
				if (npcs[i].glod == 1) {
					npcs[i].multiAttack = true;
					npcs[i].attackType = 2;
					npcs[i].projectileId = 374;
					npcs[i].endGfx = 376;
				} else if (npcs[i].glod == 2) {
					// c.getPA().sendMp3("idemand");
					npcs[i].forceChat("I demand a blood sacrifice!");
					npcs[i].multiAttack = true;
					npcs[i].attackType = 2;
					npcs[i].projectileId = -1;
					npcs[i].endGfx = 377;
					npcs[i].cooldown = 20;
				} else if (npcs[i].glod == 3) {
					npcs[i].forceChat("A siphon will solve this!");
					npcs[i].attackType = 2;
					npcs[i].projectileId = -1;
					npcs[i].endGfx = -1;
				} else {
					npcs[i].attackType = 0;
					npcs[i].projectileId = -1;
					npcs[i].endGfx = -1;
				}
			} else if (npcs[i].nexStage == 7 || npcs[i].nexStage == 8) {
				if (npcs[i].glod == 1) {
					npcs[i].multiAttack = true;
					npcs[i].attackType = 2;
					npcs[i].projectileId = 362;
					npcs[i].endGfx = 369;
				} else if (npcs[i].glod == 2) {
					npcs[i].forceChat("Contain this!");
					// npcs[i].multiAttack = true;
					npcs[i].attackType = 2;
					npcs[i].projectileId = -1;
					npcs[i].endGfx = -1;
					npcs[i].cooldown = 16;
				} else if (npcs[i].glod == 3) {
					npcs[i].forceChat("Die now, in a prison of ice!");
					npcs[i].attackType = 2;
					npcs[i].projectileId = -1;
					npcs[i].endGfx = -1;
					npcs[i].cooldown = 16;
				} else {
					npcs[i].attackType = 0;
					npcs[i].projectileId = -1;
					npcs[i].endGfx = -1;
				}
			} else if (npcs[i].nexStage == 9) {
				if (npcs[i].glod == 1) {
					npcs[i].multiAttack = true;
					npcs[i].attackType = 2;
					npcs[i].projectileId = 386;
					npcs[i].endGfx = 390;
				} else {
					npcs[i].attackType = 0;
					npcs[i].projectileId = (npcs[i].prayerUsed == 1 ? 2263 : -1);
					npcs[i].endGfx = (npcs[i].prayerUsed == 1 ? 2264 : -1);
				}
			}
			break;

		case 8133:
			switch (npcs[i].glod) {
			case 2:
				npcs[i].attackType = 1;
				npcs[i].projectileId = 1824;
				break;
			case 3:
				npcs[i].attackType = 2;
				npcs[i].projectileId = 1825;
				break;
			case 4:
				npcs[i].attackType = 2;
				npcs[i].projectileId = 1823;
				break;
			default:
				npcs[i].attackType = 0;
				npcs[i].projectileId = -1;
				break;
			}
			break;

		case 8349:
		case 8350:
		case 8351:
			switch (npcs[i].glod) {
			case 2:
				npcs[i].attackType = 1;
				npcs[i].projectileId = 1887;
				break;
			case 1:
				npcs[i].attackType = 1;
				npcs[i].projectileId = 1887;
				break;
			case 3:
				npcs[i].attackType = 2;
				npcs[i].projectileId = 1884;
				npcs[i].gfx0(1885);
				break;
			case 4:
				npcs[i].attackType = 2;
				npcs[i].projectileId = 1884;
				npcs[i].gfx0(1885);
				break;
			default:
				npcs[i].attackType = 0;
				npcs[i].projectileId = -1;
				npcs[i].gfx0(1886);
				break;
			}
			break;

		case 5810:
			switch (npcs[i].glod) {
			case 1:
				npcs[i].attackType = 1;
				npcs[i].projectileId = 1406;
				break;
			default:
				npcs[i].attackType = 0;
				npcs[i].projectileId = -1;
				break;
			}
			break;

		case 53:// Red Dragon
		case 54:// Black dragon
		case 941:// Green Dragon
		case 55:// Blue Dragon
		case 1590:// Bronze Dragon
		case 1591:// Iron Dragon
		case 1592:// Steal Dragon
		case 5362: // Brutal green
		case 10775:// Frost
			if (goodDistance(npcs[i].absX, npcs[i].absY, Mistex.playerHandler.players[npcs[i].killerId].absX, Mistex.playerHandler.players[npcs[i].killerId].absY, 2)) {
				random = MistexUtility.random(2);// If the NPC is in a distance
													// of 2 or
				// less, use melee and range
			} else {
				random = (0);// else use range attack only.
			}
			if (random == 0) {
				npcs[i].attackType = 2;
				npcs[i].projectileId = 393;
				npcs[i].endGfx = 430;
			} else if (random == 1) {
				npcs[i].projectileId = -1; // melee
				npcs[i].endGfx = -1;
				npcs[i].attackType = 0;
			}
			break;

		case 2745:
		case 5637:
			npcs[i].jad = MistexUtility.random(1);
			switch (npcs[i].jad) {
			case 0:
				npcs[i].gfx0(1626);
				npcs[i].attackType = 2;
				npcs[i].projectileId = 1627;
				break;
			case 1:
				npcs[i].gfx0(1625);
				npcs[i].attackType = 1;
				npcs[i].projectileId = -1;
				break;
			}
			npcs[i].endGfx = 157;
			break;

		case 6222:
			npcs[i].kree = MistexUtility.random(2);
			switch (npcs[i].kree) {
			case 0:
				npcs[i].attackType = 2;
				npcs[i].projectileId = 1196;
				break;
			case 1:
			case 2:
				npcs[i].attackType = 1;
				npcs[i].projectileId = 1197;
				break;
			}
			if (MistexUtility.random(5) >= 3) {
				npcs[i].forceChat(npcs[i].Kree());
			}
			break;

		case 6203:
			if (MistexUtility.random(5) >= 3) {
				npcs[i].forceChat(npcs[i].Tsutsaroth());
			}
			switch (npcs[i].tsutsaroth) {
			case 0:
			case 1:
			case 2:
			case 3:
				npcs[i].attackType = 0;
				npcs[i].projectileId = -1;
				break;
			case 4:
				npcs[i].attackType = 2;
				npcs[i].multiAttack = true;
				npcs[i].gfx0(1210);
				npcs[i].projectileId = 1211;
				break;
			}
			break;

		case 6260:
			switch (npcs[i].graardor) {
			case 0:
			case 1:
			case 2:
			case 3:
			case 4:
				if (MistexUtility.random(5) >= 4) {
					npcs[i].forceChat(npcs[i].Graardor());
				}
				npcs[i].attackType = 0;
				npcs[i].projectileId = -1;
				break;
			case 5:
				if (MistexUtility.random(5) >= 3) {
					npcs[i].forceChat(npcs[i].Graardor());
				}
				npcs[i].attackType = 1;
				npcs[i].multiAttack = true;
				npcs[i].gfx0(1219);
				npcs[i].projectileId = 1200;
				break;
			}
			break;

		case 6247:
			switch (npcs[i].zilyana) {
			case 0:
			case 1:
			case 2:
			case 3:
				npcs[i].attackType = 0;
				break;
			case 4:
			case 5:
			case 6:
				npcs[i].attackType = 2;
				break;
			}
			break;

		case 3761:
			npcs[i].attackType = 2;
			npcs[i].projectileId = 100;
			npcs[i].endGfx = 101;
			break;

		case 2190:
			npcs[i].attackType = 2;
			npcs[i].endGfx = 1187;
			break;

		case 3200:
			npcs[i].attackType = 2;
			npcs[i].chaosEle = MistexUtility.random(2);
			switch (npcs[i].chaosEle) {
			case 0:
				npcs[i].projectileId = 551;
				npcs[i].endGfx = 552;
				break;
			case 1:
				npcs[i].projectileId = 554;
				npcs[i].endGfx = 555;
				break;
			case 2:
				npcs[i].projectileId = 557;
				npcs[i].endGfx = 558;
				break;
			}
			break;

		case 2059:
			switch (npcs[i].tarn) {
			case 0:
				npcs[i].attackType = 0;
				npcs[i].projectileId = -1;
				npcs[i].endGfx = -1;
				break;
			case 1:
				npcs[i].attackType = 2;
				npcs[i].multiAttack = true;
				npcs[i].projectileId = 554;
				npcs[i].endGfx = 555;
				break;
			case 2:
				npcs[i].attackType = 1;
				npcs[i].multiAttack = true;
				npcs[i].projectileId = 475;
				npcs[i].endGfx = -1;
				break;
			}
			break;

		case 5666:
			switch (npcs[i].glod) {
			case 0:
				npcs[i].attackType = 0;
				npcs[i].endGfx = -1;
				break;
			case 1:
				npcs[i].attackType = 1;
				npcs[i].multiAttack = true;
				npcs[i].endGfx = 405;
				break;
			}
			c.playerLevel[5] -= 5;
			if (c.playerLevel[5] < 0) {
				c.playerLevel[5] = 0;
			}
			c.getPA().refreshSkill(5);
			for (int pr = 12; pr < 15; pr++) {
				if (c.prayerActive[pr]) {
					c.sendMessage("The Barrelchest keeps you from praying!");
					c.prayerActive[pr] = false;
					c.getPA().sendFrame36(c.PRAYER_GLOW[pr], 0);
					c.headIcon = -1;
					c.getPA().requestUpdates();
				}
			}
			break;

		case 7770:
			switch (npcs[i].glod) {
			case 0:
				npcs[i].attackType = 0;
				npcs[i].endGfx = -1;
				break;
			case 1:
				npcs[i].attackType = 1;
				npcs[i].multiAttack = true;
				npcs[i].endGfx = 405;
				break;
			}
			break;

		case 6021:
		case 6006:
		case 6007:
		case 6008:
			switch (npcs[i].glod) {
			case 0:
				npcs[i].attackType = 0;
				npcs[i].projectileId = -1;
				break;
			case 1:
				npcs[i].attackType = 1;
				npcs[i].multiAttack = true;
				npcs[i].projectileId = 1406;
				break;
			case 2:
				npcs[i].attackType = 2;
				npcs[i].projectileId = 159;
				break;
			}
			break;

		case 6017:
		case 6018:
		case 6019:
		case 6020:
			switch (npcs[i].glod) {
			case 0:
				npcs[i].attackType = 0;
				npcs[i].projectileId = -1;
				npcs[i].endGfx = -1;
				break;
			case 1:
			case 2:
			case 3:
				npcs[i].attackType = 2;
				// npcs[i].multiAttack = true;
				npcs[i].projectileId = 124;
				npcs[i].endGfx = -1;
				break;
			case 4:
				npcs[i].attackType = 2;
				npcs[i].projectileId = 178;
				npcs[i].endGfx = 179;
				break;
			}
			break;

		case 2057:
			switch (npcs[i].glod) {
			case 0:
				npcs[i].attackType = 0;
				npcs[i].endGfx = -1;
				break;
			case 1:
				npcs[i].attackType = 1;
				npcs[i].multiAttack = true;
				npcs[i].endGfx = 405;
				break;
			}
			if (MistexUtility.random(9) >= 8) {
				for (int pr = 12; pr < 15; pr++) {
					if (c.prayerActive[pr]) {
						c.sendMessage("Glod breaks through your prayer!");
						c.prayerActive[pr] = false;
						c.getPA().sendFrame36(c.PRAYER_GLOW[pr], 0);
						c.headIcon = -1;
						c.getPA().requestUpdates();
					}
				}
			}
			break;

		case 50:
			if (goodDistance(npcs[i].absX, npcs[i].absY, Mistex.playerHandler.players[npcs[i].killerId].absX, Mistex.playerHandler.players[npcs[i].killerId].absY, 2)) {
				random = MistexUtility.random(4);// If the NPC is in a distance
													// of 2 or
				// less, use melee and range
			} else {
				random = MistexUtility.random(3);// else use range attack only.
			}
			if (random == 0) {
				npcs[i].projectileId = 393; // red
				npcs[i].endGfx = 430;
				npcs[i].attackType = 3;
			} else if (random == 1) {
				npcs[i].projectileId = 394; // green
				npcs[i].endGfx = 429;
				npcs[i].attackType = 3;
			} else if (random == 2) {
				npcs[i].projectileId = 395; // white
				npcs[i].endGfx = 431;
				npcs[i].attackType = 3;
			} else if (random == 3) {
				npcs[i].projectileId = 396; // blue
				npcs[i].endGfx = 428;
				npcs[i].attackType = 3;
				if (c.freezeTimer <= 0) {
					c.freezeTimer = 30;
					c.frozenBy = c.playerId;
					c.stopMovement();
					c.sendMessage("You have been frozen.");
				}
			} else if (random == 4) {
				npcs[i].projectileId = -1; // melee
				npcs[i].endGfx = -1;
				npcs[i].attackType = 0;
			}
			break;

		case 2025:
			npcs[i].attackType = 2;
			npcs[i].gfx100(155);
			npcs[i].projectileId = 156;
			break;

		case 6206:
			npcs[i].attackType = 1;
			npcs[i].gfx0(1208);
			npcs[i].projectileId = 1209;
			break;

		case 6208:
			npcs[i].attackType = 2;
			npcs[i].gfx0(1212);
			npcs[i].projectileId = 1213;
			break;

		case 1472:
			npcs[i].attackType = 2;
			int r = MistexUtility.random(2);
			switch (r) {
			case 0:
				npcs[i].projectileId = 162;
				npcs[i].endGfx = 163;
				break;
			case 1:
				npcs[i].projectileId = 165;
				npcs[i].endGfx = 166;
				break;
			case 2:
				npcs[i].projectileId = 156;
				npcs[i].endGfx = 157;
				break;
			}
			break;

		case 6250:
			npcs[i].attackType = 2;
			npcs[i].gfx0(1184);
			npcs[i].projectileId = 1185;
			npcs[i].endGfx = 1186;
			break;

		case 6263:
			npcs[i].attackType = 2;
			npcs[i].gfx0(1202);
			npcs[i].projectileId = 1203;
			npcs[i].endGfx = 1204;
			break;

		case 2882:
		case 2457:
			npcs[i].attackType = 2;
			npcs[i].projectileId = 162;
			npcs[i].endGfx = 163;
			break;

		case 2894:
			npcs[i].attackType = 2;
			npcs[i].projectileId = 500;
			npcs[i].endGfx = 502;
			break;

		case 6223:
		case 6231:
			npcs[i].attackType = 2;
			npcs[i].projectileId = 1199;
			break;

		case 2028:
			npcs[i].attackType = 1;
			npcs[i].projectileId = 27;
			break;

		case 6252:
			npcs[i].attackType = 1;
			npcs[i].gfx100(1187);
			npcs[i].projectileId = 1188;
			break;

		case 1343:
		case 1351:
		case 3771:
		case 2896:
			npcs[i].attackType = 1;
			npcs[i].projectileId = 300;
			break;

		case 2881:
			npcs[i].attackType = 1;
			npcs[i].projectileId = 475;
			break;

		case 6265:
			npcs[i].attackType = 1;
			npcs[i].projectileId = 1206;
			break;

		case 2183:
			npcs[i].attackType = 1;
			npcs[i].projectileId = 1191;
			break;
		}
	}

	/**
	 * Distanced required to attack
	 **/
	public int distanceRequired(int i) {
		switch (npcs[i].npcType) {
		case 8133:
			return 5;
		case 8528:
			return 3;
		case 2025:
		case 2028:
			return 6;
		case 50:
		case 2562:
			return 2;
		case 2881: // dag kings
		case 2882:
		case 3200: // chaos ele
		case 2743:
		case 2631:
		case 2745:
			return 8;
		case 2883: // rex
			return 1;
		case 2552:
		case 2553:
		case 2556:
		case 2557:
		case 2558:
		case 2559:
		case 2560:
		case 2564:
		case 2565:
			return 9;
		// things around dags
		case 2892:
		case 2894:
			return 10;
		default:
			return 1;
		}
	}

	public int followDistance(int i) {
		switch (npcs[i].npcType) {
		case 6260:
		case 6261:
		case 6247:
		case 6248:
		case 6223:
		case 6225:
		case 6227:
		case 6203:
		case 6204:
		case 6206:
		case 6208:
		case 6250:
		case 6252:
		case 6263:
		case 8133:
		case 6265:
			return 15;
		case 3247:
		case 6270:
		case 6219:
		case 6255:
		case 6229:
		case 6277:
		case 6233:
		case 6232:
		case 6218:
		case 6269:
		case 3248:
		case 6212:
		case 6220:
		case 6276:
		case 6256:
		case 6230:
		case 6239:
		case 6221:
		case 6231:
		case 6257:
		case 6278:
		case 6272:
		case 6274:
		case 6254:
		case 4291: // Cyclops
		case 4292: // Ice cyclops
		case 6258:
		case 8349:
		case 8350:
		case 8351:
		case 10141:
			return 7;
		case 50:
		case 10770:
		case 10771:
		case 10772:
		case 10773:
		case 10774:
		case 10775:
		case 10604:
		case 10776:
		case 10777:
		case 10778:
		case 10779:
		case 10780:
		case 10781:
		case 10815:
		case 10816:
		case 10817:
		case 10818:
		case 10819:
		case 10820:
		case 10219:
		case 10220:
		case 10221:
		case 10222:
		case 10223:
		case 10224:
		case 10605:
		case 10606:
		case 10607:
		case 10608:
		case 10609:
			return 18;
		case 2883:
			return 4;
		case 2881:
		case 2882:
			return 1;

		}
		return 0;

	}

	public int getProjectileSpeed(int i) {
		switch (npcs[i].npcType) {
		case 2881:
		case 2882:
		case 3200:
			return 85;

		case 2745:
			return 135;

		case 50:
			return 90;

		case 2025:
			return 85;

		case 2028:
			return 80;

		default:
			return 85;
		}
	}

	/**
	 * NPC Attacking Player
	 **/

	public void attackPlayer(Client c, int i) {
		if (npcs[i].lastX != npcs[i].getX() || npcs[i].lastY != npcs[i].getY()) {
			return;
		}

		if (npcs[i] != null) {
			if (npcs[i].isDead)
				return;
			if (!npcs[i].inMulti() && npcs[i].underAttackBy > 0 && npcs[i].underAttackBy != c.playerId) {
				npcs[i].killerId = 0;
				return;
			}
			if (!npcs[i].inMulti() && (c.underAttackBy > 0 || (c.underAttackBy2 > 0 && c.underAttackBy2 != i))) {
				npcs[i].killerId = 0;
				return;
			}
			if (npcs[i].heightLevel != c.heightLevel) {
				npcs[i].killerId = 0;
				return;
			}
			npcs[i].facePlayer(c.playerId);
			boolean special = false; // specialCase(c,i);
			if (goodDistance(npcs[i].getX(), npcs[i].getY(), c.getX(), c.getY(), distanceRequired(i)) || special) {
				if (c.respawnTimer <= 0) {
					if (npcs[i].npcType == 2862 || npcs[i].npcType == 6142 || npcs[i].npcType == 6143 || npcs[i].npcType == 6144 || npcs[i].npcType == 6145 || npcs[i].npcType == 1944 || npcs[i].npcType == 1946) {
						return;
					}
					npcs[i].facePlayer(c.playerId);
					npcs[i].attackTimer = getNpcDelay(i);
					npcs[i].hitDelayTimer = getHitDelay(i);
					npcs[i].attackType = 0;
					if (special)
						loadSpell2(i);
					else
						loadSpell(c, i);
					if (npcs[i].attackType == 3)
						npcs[i].hitDelayTimer += 2;
					if (multiAttacks(i)) {
						multiAttackGfx(i, npcs[i].projectileId);
						startAnimation(getAttackEmote(i), i);
						npcs[i].oldIndex = c.playerId;
						return;
					}
					if (npcs[i].projectileId > 0) {
						int nX = NPCHandler.npcs[i].getX() + offset(i);
						int nY = NPCHandler.npcs[i].getY() + offset(i);
						int pX = c.getX();
						int pY = c.getY();
						int offX = (nY - pY) * -1;
						int offY = (nX - pX) * -1;
						c.getPA().createPlayersProjectile(nX, nY, offX, offY, 50, getProjectileSpeed(i), npcs[i].projectileId, 43, 31, -c.getId() - 1, 65);
					}
					c.underAttackBy2 = i;
					c.singleCombatDelay2 = System.currentTimeMillis();
					npcs[i].oldIndex = c.playerId;
					startAnimation(getAttackEmote(i), i);
					c.getPA().removeAllWindows();
					if (c.isTeleporting) {
						c.startAnimation(65535);
						c.isTeleporting = false;
						c.isRunning = false;
						c.gfx0(-1);
						c.startAnimation(-1);
					}
				}
			}
		}
	}

	public int offset(int i) {
		switch (npcs[i].npcType) {
		case 50:
			return 2;
		case 2881:
		case 2882:
			return 1;
		case 2745:
		case 2743:
			return 1;
		/* Dung Boss */
		case 9780:
		case 10127:
			return 2;
		case 10106:
			return 3;
		}
		return 0;
	}

	public boolean specialCase(Client c, int i) { // responsible for npcs that
		// much
		if (goodDistance(npcs[i].getX(), npcs[i].getY(), c.getX(), c.getY(), 8) && !goodDistance(npcs[i].getX(), npcs[i].getY(), c.getX(), c.getY(), distanceRequired(i)))
			return true;
		return false;
	}

	public boolean retaliates(int npcType) {
		return npcType >= 6142 && npcType <= 6145 || npcType < 3777 || npcType > 3780 && !(npcType >= 2440 && npcType <= 2446);
	}

	public void applyDamage(int i) {
		if (npcs[i] != null) {
			if (World.players[npcs[i].oldIndex] == null) {
				return;
			}
			if (npcs[i].isDead)
				return;
			Client c = (Client) World.players[npcs[i].oldIndex];
			if (multiAttacks(i)) {
				multiAttackDamage(i);
				return;
			}
			if (c.playerIndex <= 0 && c.npcIndex <= 0)
				if (c.autoRet == 1)
					c.npcIndex = i;
			if (c.attackTimer <= 3 || c.attackTimer == 0 && c.npcIndex == 0 && c.oldNpcIndex == 0) {
				c.startAnimation(c.getCombat().getBlockEmote());
			}
			if (c.respawnTimer <= 0) {
				int damage = 0;
				if (npcs[i].attackType == 0) {
					damage = MistexUtility.random(npcs[i].maxHit);
					if (10 + MistexUtility.random(c.getCombat().calculateMeleeDefence()) > MistexUtility.random(NPCHandler.npcs[i].attack)) {
						damage = 0;
					}
					if (c.prayerActive[18] || c.curseActive[9]) { // protect
																	// from
																	// melee
						// if (npcs[i].npcType == 8528 || npcs[i].npcType ==
						// 8133 || npcs[i].npcType == 5666 || npcs[i].npcType ==
						// 6222 || npcs[i].npcType == 6247 || npcs[i].npcType ==
						// 6260 || npcs[i].npcType == 6203 || npcs[i].npcType ==
						// 1158 || npcs[i].npcType == 1160)
						if (npcs[i].npcType != 2030)
							damage = (int) (damage * (c.curseActive[9] ? 0.5 : c.prayerActive[18] ? 0.4 : 1));
						if (npcs[i].npcType == 2745)
							damage = 0;
						if (c.playerLevel[3] - damage < 0) {
							damage = c.playerLevel[3];
						}
					}
				}
				if (npcs[i].attackType == 1) { // range
					damage = MistexUtility.random(npcs[i].maxHit);
					if (10 + MistexUtility.random(c.getCombat().calculateRangeDefence()) > MistexUtility.random(NPCHandler.npcs[i].attack)) {
						damage = 0;
					}
					if (c.prayerActive[17] || c.curseActive[8]) { // protect
																	// from
																	// range
						// if (npcs[i].npcType == 8133)
						damage = (int) (damage * (c.curseActive[8] ? 0.5 : c.prayerActive[17] ? 0.4 : 1));
						if (npcs[i].npcType == 2745)
							damage = 0;
					}
					if (c.playerLevel[3] - damage < 0) {
						damage = c.playerLevel[3];
					}
				}

				if (npcs[i].attackType == 2) { // magic
					damage = MistexUtility.random(npcs[i].maxHit);
					boolean magicFailed = false;
					if (10 + MistexUtility.random(c.getCombat().mageDef()) > MistexUtility.random(NPCHandler.npcs[i].attack)) {
						damage = 0;
						magicFailed = true;
					}
					if (c.prayerActive[16] || c.curseActive[7]) { // protect
																	// from
																	// magic
						// if (npcs[i].npcType == 1158)
						damage = (int) (damage * (c.curseActive[7] ? 0.5 : c.prayerActive[16] ? 0.4 : 1));
						if (npcs[i].npcType == 2745)
							damage = 0;
						magicFailed = true;
						if (c.playerLevel[3] - damage < 0) {
							damage = c.playerLevel[3];
						}
						if (npcs[i].endGfx > 0 && (!magicFailed || isFightCaveNpc(i) || isChampionBattleNPC(i))) {
							c.gfx100(npcs[i].endGfx);
						} else {
							c.gfx100(85);
						}
					}
					if (c.playerLevel[3] - damage < 0) {
						damage = c.playerLevel[3];
					}
					if (npcs[i].endGfx > 0 && (!magicFailed || isFightCaveNpc(i) || isChampionBattleNPC(i))) {
						c.gfx100(npcs[i].endGfx);
					} else {
						c.gfx100(85);
					}
				}

				if (npcs[i].attackType == 3) { // fire breath
					int anti = c.getPA().antiFire();
					if (anti == 0) {
						damage = MistexUtility.random(30) + 10;
						c.sendMessage("You are badly burnt by the dragon fire!");
					} else if (anti == 1)
						damage = MistexUtility.random(20);
					else if (anti == 2)
						damage = MistexUtility.random(5);
					else if (anti == 3)
						damage = 0;
					if (c.playerLevel[3] - damage < 0)
						damage = c.playerLevel[3];
					c.gfx100(npcs[i].endGfx);
				}
				handleSpecialEffects(c, i, damage);
				c.logoutDelay = System.currentTimeMillis(); // logout delay
				// c.setHitDiff(damage);
				c.handleHitMask(damage);
				c.playerLevel[3] -= damage;
				c.getPA().refreshSkill(3);
				c.updateRequired = true;
				// c.setHitUpdateRequired(true);
			}
		}
	}

	public boolean isDragon(int i) {
		switch (npcs[i].npcType) {
		case 50:
		case 53:
		case 54:
		case 55:
		case 941:
		case 1590:
		case 1591:
		case 1592:
			return true;
		}
		return false;
	}

	public void handleSpecialEffects(Client c, int i, int damage) {
		if (npcs[i].npcType == 1158 || npcs[i].npcType == 1160 || npcs[i].npcType == 2892 || npcs[i].npcType == 2894) {
			if (damage > 0) {
				if (c != null) {
					if (c.playerLevel[5] > 0) {
						c.playerLevel[5]--;
						c.getPA().refreshSkill(5);
						c.getPA().appendPoison(12);
					}
				}
			}
		}

	}

	public static void startAnimation(int animId, int i) {
		npcs[i].animNumber = animId;
		npcs[i].animUpdateRequired = true;
		npcs[i].updateRequired = true;
	}

	public NPC[] getNPCs() {
		return npcs;
	}

	public boolean goodDistance(int objectX, int objectY, int playerX, int playerY, int distance) {
		return ((objectX - playerX <= distance && objectX - playerX >= -distance) && (objectY - playerY <= distance && objectY - playerY >= -distance));
	}

	public int getMaxHit(int i) {
		switch (npcs[i].npcType) {
		case 1158:
			if (npcs[i].attackType == 2)
				return 30;
			else
				return 30;
		case 2558:
			if (npcs[i].attackType == 2)
				return 28;
			else
				return 68;
		case 2562:
			return 31;
		case 6260:
			return 36;
		case 8133:
			return 70;
		}
		return 1;
	}

	public boolean loadAutoSpawn(String FileName) {
		String line = "";
		String token = "";
		String token2 = "";
		String token2_2 = "";
		String[] token3 = new String[10];
		boolean EndOfFile = false;
		int ReadMode = 0;
		BufferedReader characterfile = null;
		try {
			characterfile = new BufferedReader(new FileReader("./" + FileName));
		} catch (FileNotFoundException fileex) {
			MistexUtility.println(FileName + ": file not found.");
			return false;
		}
		try {
			line = characterfile.readLine();
		} catch (IOException ioexception) {
			MistexUtility.println(FileName + ": error loading file.");
			return false;
		}
		while (EndOfFile == false && line != null) {
			line = line.trim();
			int spot = line.indexOf("=");
			if (spot > -1) {
				token = line.substring(0, spot);
				token = token.trim();
				token2 = line.substring(spot + 1);
				token2 = token2.trim();
				token2_2 = token2.replaceAll("\t\t", "\t");
				token2_2 = token2_2.replaceAll("\t\t", "\t");
				token2_2 = token2_2.replaceAll("\t\t", "\t");
				token2_2 = token2_2.replaceAll("\t\t", "\t");
				token2_2 = token2_2.replaceAll("\t\t", "\t");
				token3 = token2_2.split("\t");
				if (token.equals("spawn")) {
					newNPC(Integer.parseInt(token3[0]), Integer.parseInt(token3[1]), Integer.parseInt(token3[2]), Integer.parseInt(token3[3]), Integer.parseInt(token3[4]), getNpcListHP(Integer.parseInt(token3[0])), Integer.parseInt(token3[5]), Integer.parseInt(token3[6]), Integer.parseInt(token3[7]));

				}
			} else {
				if (line.equals("[ENDOFSPAWNLIST]")) {
					try {
						characterfile.close();
					} catch (IOException ioexception) {
					}
					return true;
				}
			}
			try {
				line = characterfile.readLine();
			} catch (IOException ioexception1) {
				EndOfFile = true;
			}
		}
		try {
			characterfile.close();
		} catch (IOException ioexception) {
		}
		return false;
	}

	public int getNpcListHP(int npcId) {
		if (npcId <= -1) {
			return 0;
		}
		if (NPCDefinitions.getDefinitions()[npcId] == null) {
			return 0;
		}
		return NPCDefinitions.getDefinitions()[npcId].getNpcHealth();

	}

	public static String getNpcListName(int npcId) {
		if (npcId <= -1) {
			return "None";
		}
		if (NPCDefinitions.getDefinitions()[npcId] == null) {
			return "None";
		}
		return NPCDefinitions.getDefinitions()[npcId].getNpcName();
	}

	public boolean loadNPCList(String FileName) {
		String line = "";
		String token = "";
		String token2 = "";
		String token2_2 = "";
		String[] token3 = new String[10];
		boolean EndOfFile = false;
		int npcs = 0;
		BufferedReader characterfile = null;
		try {
			characterfile = new BufferedReader(new FileReader("./" + FileName));
		} catch (FileNotFoundException fileex) {
			MistexUtility.println(FileName + ": file not found.");
			return false;
		}
		try {
			line = characterfile.readLine();
		} catch (IOException ioexception) {
			MistexUtility.println(FileName + ": error loading file.");
			try {
				characterfile.close();
			} catch (IOException e) {
			}
		}
		while (EndOfFile == false && line != null) {
			line = line.trim();
			int spot = line.indexOf("=");
			if (spot > -1) {
				token = line.substring(0, spot);
				token = token.trim();
				token2 = line.substring(spot + 1);
				token2 = token2.trim();
				token2_2 = token2.replaceAll("\t\t", "\t");
				token2_2 = token2_2.replaceAll("\t\t", "\t");
				token2_2 = token2_2.replaceAll("\t\t", "\t");
				token2_2 = token2_2.replaceAll("\t\t", "\t");
				token2_2 = token2_2.replaceAll("\t\t", "\t");
				token3 = token2_2.split("\t");
				if (token.equals("npc")) {
					newNPCList(Integer.parseInt(token3[0]), token3[1], Integer.parseInt(token3[2]), Integer.parseInt(token3[3]));
					// npcCombat[Integer.parseInt(token3[0])] =
					// Integer.parseInt(token3[2]);
					npcs++;
				}
			} else {
				if (line.equals("[ENDOFNPCLIST]")) {
					System.out.println("Loaded " + npcs + " npcs.");
					try {
						characterfile.close();
					} catch (IOException ioexception) {
					}
					return true;
				}
			}
			try {
				line = characterfile.readLine();
			} catch (IOException ioexception1) {
				EndOfFile = true;
			}
		}
		try {
			characterfile.close();
		} catch (IOException ioexception) {
		}
		System.out.println("Loaded " + npcs + " npcs.");
		return false;
	}

	public static void spawnNpc2(int npcType, int x, int y, int heightLevel, int WalkingType, int HP, int maxHit, int attack, int defence) {
		// first, search for a free slot
		int slot = -1;
		for (int i = 1; i < maxNPCs; i++) {
			if (npcs[i] == null) {
				slot = i;
				break;
			}
		}
		if (slot == -1) {
			// MistexUtility.println("No Free Slot");
			return; // no free slot found
		}
		NPC newNPC = new NPC(slot, npcType);
		newNPC.absX = x;
		newNPC.absY = y;
		newNPC.makeX = x;
		newNPC.makeY = y;
		newNPC.heightLevel = heightLevel;
		newNPC.walkingType = WalkingType;
		newNPC.HP = HP;
		newNPC.MaxHP = HP;
		newNPC.maxHit = maxHit;
		newNPC.attack = attack;
		newNPC.defence = defence;
		npcs[slot] = newNPC;
	}

	private void killedChampionsNPC(int i) {
		final Client c2 = (Client) World.players[npcs[i].spawnedBy];
		if (c2 == null)
			return;
		if (c2.inChampionsBattle() && c2.inChampionsBattle) {
			c2.championsBattleKills++;
			if (c2.championsBattleKills == c2.championsToKill) {
				c2.championsWave++;
				Mistex.championsBattle.nextWave(c2);
			}
		}
	}

	public void handleChampionsBattleBoss(int i) {
		Client c = (Client) World.players[npcs[i].spawnedBy];
		if (c.inChampionsBattle() && c.inChampionsBattle) {
			c.getItems().addItem(995, 5000000);
			c.inChampionsBattle = false;
			c.getPA().sendStatement("Amazing! You have actually killed the champions.");
			c.getPA().yell("[ <col=C42BAD>Mistex</col> ] " + c.getRights().determineIcon() + "<col=C42BAD>" + MistexUtility.capitalize(c.playerName) + "</col> has beaten the Champions!");
			c.getPA().resetChampionsBattle();
		}
	}

	public void Summon(Client c, int npcType, String summonName, int x, int y, int heightLevel, int WalkingType, int HP, int maxHit, boolean attackPlayer, int attack, int defence) {
		int slot = -1;
		for (int i = 1; i < maxNPCs; i++) {
			if (npcs[i] == null) {
				slot = i;
				break;
			}
		}
		if (slot == -1) {
			// Misc.println("No Free Slot");
			return; // no free slot found
		}
		if (c.canSummon()) {
			NPC newNPC = new NPC(slot, npcType);
			newNPC.absX = x;
			newNPC.absY = y;
			newNPC.makeX = x;
			newNPC.makeY = y;
			newNPC.heightLevel = heightLevel;
			newNPC.walkingType = WalkingType;
			newNPC.HP = HP;
			newNPC.MaxHP = HP;
			newNPC.maxHit = maxHit;
			newNPC.attack = attack;
			newNPC.defence = defence;
			newNPC.spawnedBy = c.getId();
			newNPC.followPlayer = c.getId();
			newNPC.summon = true;
			newNPC.npcslot = slot;
			newNPC.gfx0(1315);
			npcs[slot] = newNPC;
			c.getPA().sendFrame126(summonName, 25614);
			c.getPA().sendFrame75(newNPC.npcType, 23027);
			c.lastsummon = npcType;
			c.summon = true;
			c.summoningnpcid = slot;
		} else {
			c.sendMessage("Your npc will spawn when your out of the home area");
			c.needsToSpawn = true;
		}
	}

	public boolean AttackNPC(int NPCID) {
		if (Mistex.npcHandler.npcs[npcs[NPCID].attacknpc] != null) {
			int EnemyX = Mistex.npcHandler.npcs[npcs[NPCID].attacknpc].absX;
			int EnemyY = Mistex.npcHandler.npcs[npcs[NPCID].attacknpc].absY;
			int EnemyHP = Mistex.npcHandler.npcs[npcs[NPCID].attacknpc].HP;
			int hitDiff = 0;

			hitDiff = MistexUtility.random(npcs[NPCID].maxHit);
			if (goodDistance(EnemyX, EnemyY, npcs[NPCID].absX, npcs[NPCID].absY, 1) == true) {
				if (Mistex.npcHandler.npcs[npcs[NPCID].attacknpc].isDead == true) {
					npcs[NPCID].animNumber = 2103;
					npcs[NPCID].animUpdateRequired = true;
					npcs[NPCID].updateRequired = true;
				} else {
					if ((EnemyHP - hitDiff) < 0) {
						hitDiff = EnemyHP;
					}
					if (npcs[NPCID].npcType == 9)
						npcs[NPCID].animNumber = 386;
					if (npcs[NPCID].npcType == 3200)
						npcs[NPCID].animNumber = 0x326; // drags: chaos ele
					// emote ( YESSS )
					if ((npcs[NPCID].npcType == 1605) || (npcs[NPCID].npcType == 1472)) {
						npcs[NPCID].animNumber = 386; // drags: abberant
						// spector death ( YAY )
					}

					npcs[NPCID].animUpdateRequired = true;
					npcs[NPCID].updateRequired = true;
					Mistex.npcHandler.npcs[npcs[NPCID].attacknpc].hitDiff = hitDiff;
					Mistex.npcHandler.npcs[npcs[NPCID].attacknpc].attacknpc = NPCID;
					Mistex.npcHandler.npcs[npcs[NPCID].attacknpc].updateRequired = true;
					Mistex.npcHandler.npcs[npcs[NPCID].attacknpc].hitUpdateRequired = true;
					npcs[NPCID].actionTimer = 7;
					return true;
				}
			}
		}
		return false;
	}

	public void attackNPC(Client player, int c, int i) {
		if (npcs[i] != null) {
			if (npcs[i].isDead)
				return;
			if (!npcs[i].inMulti() && npcs[i].underAttackBy > 0 && npcs[i].underAttackBy != npcs[c].npcId) {
				return;
			}
			if (!npcs[i].inMulti() && (npcs[c].underAttackBy > 0 || (npcs[c].underAttackBy2 > 0 && npcs[c].underAttackBy2 != i))) {
				npcs[i].killerId = 0;
				return;
			}
			if (npcs[i].heightLevel != npcs[c].heightLevel) {
				npcs[i].killerId = 0;
				return;
			}
			follownpc(i, c);
			npcs[i].facePlayer(npcs[c].npcId);
			npcs[i].facenpc(npcs[c].npcId);
			boolean special = false;// specialCase(c,i);
			if (goodDistance(npcs[i].getX(), npcs[i].getY(), npcs[c].getX(), npcs[c].getY(), distanceRequired(i)) || special) {
				if (npcs[c].actionTimer <= 0) {
					npcs[i].facePlayer(npcs[c].npcId);
					npcs[i].attackTimer = getNpcDelay(i);
					npcs[i].hitDelayTimer = getHitDelay(i);
					npcs[i].attackType = 0;

					if (special)
						loadSpell2(i);
					else
						loadSpell(player, i);

					npcs[c].underAttackBy2 = i;
					npcs[c].actionTimer = 7;
					npcs[i].actionTimer = 5;
					int damg = MistexUtility.random(npcs[i].maxHit);
					npcs[c].handleHitMask(damg);
					npcs[c].HP -= damg;
					npcs[c].hitUpdateRequired2 = true;
					npcs[c].hitUpdateRequired = true;

					npcs[i].oldIndexNPC = npcs[c].npcId;
					startAnimation(getAttackEmote(i), i);
					// c.getPA().removeAllWindows();
				}
			}
		}
	}

	public void follownpc(int i, int playerId) {
		if (npcs[playerId] == null) {
			return;
		}

		if (!followPlayer(i)) {
			npcs[i].facePlayer(playerId);
			return;
		}

		if (!goodDistance(npcs[i].getX(), npcs[i].getY(), npcs[playerId].getX(), npcs[playerId].getY(), 1) && npcs[i].npcType == 10127 && npcs[i].attackType == 0) {
			npcs[i].attackType = 2;
			return;
		}

		npcs[i].randomWalk = false;

		if (goodDistance(npcs[i].getX(), npcs[i].getY(), npcs[playerId].absX, npcs[playerId].absY, distanceRequired(i)))
			return;

		if ((npcs[i].spawnedBy > 0) || ((npcs[i].absX < npcs[i].makeX + MistexConfiguration.NPC_FOLLOW_DISTANCE) && (npcs[i].absX > npcs[i].makeX - MistexConfiguration.NPC_FOLLOW_DISTANCE) && (npcs[i].absY < npcs[i].makeY + MistexConfiguration.NPC_FOLLOW_DISTANCE) && (npcs[i].absY > npcs[i].makeY - MistexConfiguration.NPC_FOLLOW_DISTANCE))) {
			if (npcs[i].heightLevel == npcs[playerId].heightLevel) {
				if (npcs[playerId] != null && npcs[i] != null) {
					if (npcs[playerId].absY < npcs[i].absY) {
						npcs[i].moveX = GetMove(npcs[i].absX, npcs[playerId].absX);
						npcs[i].moveY = GetMove(npcs[i].absY, npcs[playerId].absY);
					} else if (npcs[playerId].absY > npcs[i].absY) {
						npcs[i].moveX = GetMove(npcs[i].absX, npcs[playerId].absX);
						npcs[i].moveY = GetMove(npcs[i].absY, npcs[playerId].absY);
					} else if (npcs[playerId].absX < npcs[i].absX) {
						npcs[i].moveX = GetMove(npcs[i].absX, npcs[playerId].absX);
						npcs[i].moveY = GetMove(npcs[i].absY, npcs[playerId].absY);
					} else if (npcs[playerId].absX > npcs[i].absX) {
						npcs[i].moveX = GetMove(npcs[i].absX, npcs[playerId].absX);
						npcs[i].moveY = GetMove(npcs[i].absY, npcs[playerId].absY);
					} else if (npcs[playerId].absX == npcs[i].absX || npcs[playerId].absY == npcs[i].absY) {
						int o = MistexUtility.random(3);
						switch (o) {
						case 0:
							npcs[i].moveX = GetMove(npcs[i].absX, npcs[playerId].absX);
							npcs[i].moveY = GetMove(npcs[i].absY, npcs[playerId].absY + 1);
							break;

						case 1:
							npcs[i].moveX = GetMove(npcs[i].absX, npcs[playerId].absX);
							npcs[i].moveY = GetMove(npcs[i].absY, npcs[playerId].absY - 1);
							break;

						case 2:
							npcs[i].moveX = GetMove(npcs[i].absX, npcs[playerId].absX + 1);
							npcs[i].moveY = GetMove(npcs[i].absY, npcs[playerId].absY);
							break;

						case 3:
							npcs[i].moveX = GetMove(npcs[i].absX, npcs[playerId].absX - 1);
							npcs[i].moveY = GetMove(npcs[i].absY, npcs[playerId].absY);
							break;
						}
					}
					int x = (npcs[i].absX + npcs[i].moveX);
					int y = (npcs[i].absY + npcs[i].moveY);
					// npcs[i].facePlayer(playerId);
					// handleClipping(i);
					npcs[i].getNextNPCMovement(i);
					// handleClipping(i);
					npcs[i].facePlayer(playerId);
					npcs[i].updateRequired = true;
				}
			}
		} else {
			npcs[i].facePlayer(0);
			npcs[i].randomWalk = true;
			npcs[i].underAttack = false;
		}
	}

}