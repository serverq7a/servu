package org.mistex.game.world.npc;

import org.mistex.game.Mistex;
import org.mistex.game.MistexUtility;
import org.mistex.system.RS2Stream;

public class NPC {

	/** 
	 * Npc id variable
	 */
	public int npcId;
	
	/**
	 * Attack npc variable
	 */
	public int attacknpc;
	
	/**
	 * npc type variable
	 */
	public int npcType;
	
	/**
	 * Old index npc variable
	 */
	public int oldIndexNPC;
	
	/**
	 * Npc coordinates variables
	 */
	public int absX, absY;
	
	
	public int index, followPlayer, npcslot, type;
	public boolean summon;
	public int lastsummon;
	public int attackingnpc;
	public int underAttackBy2;
	public boolean needsToRespawn;
	public boolean summonedNPC;
	public int lastSummon;
	public int heightLevel;
	public int makeX, makeY, maxHit, defence, attack, moveX, moveY, direction, walkingType, size;
	public int spawnX, spawnY;
	public int viewX, viewY;
	public boolean multiAttack;
	public int hp, maxHP;
	public int lastX, lastY;
	public boolean noDeathEmote;
	public long delay;
	public long singleCombatDelay = 0;
	public int diliHits = 30, nexStage = 0, cooldown = 0, mustDie = 0;
	public int killNpc = 0;
	public boolean summoner = false;
	public boolean spec = false, extraHit = false;
	public boolean removeNpc;
	public boolean IsAttackingPerson = false;
	public boolean breatingFire = false;
	public int prayerUsed = 0;
	public int FocusPointX = -1, FocusPointY = -1;
	public int face = 0;
	public int mask80var1 = 0;
	public int mask80var2 = 0;
	protected boolean mask80update = false;
	public int extraHitDelay = 0, kbdAttack, moleStage, troll, chaosEle,
			kQueen, mejKot, jad, kree, dragon, cHorror, zilyana, graardor,
			tsutsaroth, tarn, glod, deadCycle = 0, jadRange = -1;
	public int hitIcon, hitIcon2, hitMask, hitMask2;
	public int attackType, projectileId, endGfx, spawnedBy, hitDelayTimer, HP,MaxHP, hitDiff, animNumber, actionTimer, enemyX, enemyY;
	public boolean applyDead, isDead, needRespawn, respawns;
	public boolean walkingHome, underAttack;
	public int freezeTimer, attackTimer, killerId, killedBy, oldIndex, underAttackBy;
	public long lastDamageTaken;
	public boolean randomWalk;
	public boolean dirUpdateRequired;
	public boolean animUpdateRequired;
	public boolean hitUpdateRequired;
	public boolean updateRequired;
	public boolean forcedChatRequired;
	public boolean faceToUpdateRequired;
	public int firstAttacker;
	public String forcedText;
	public int hitDiff2 = 0;
	public boolean hitUpdateRequired2 = false;
	public int summonedBy;

	
	
	/**
	 * Gets the npc combat level
	 * @return
	 */
	@SuppressWarnings("static-access")
	public int getCombatLevel() {
		return Mistex.npcHandler.npcCombat[npcType];
	}
	
	/**
	 * Gets the x coordinates of npc
	 * @return
	 */
	public int getX() {
		return absX;
	}

	/**
	 * Gets the y coordinates of npc
	 * @return
	 */
	public int getY() {
		return absY;
	}
	
	/**
	 * Faces the npc
	 * @param npc
	 */
	public void facenpc(int npc) {
		face = npc;
		dirUpdateRequired = true;
		updateRequired = true;
	}
	
	/**
	 * Moves the npc a certain x coordinate
	 * @param absX
	 */
	public void setAbsX(int absX) {
		this.lastX = this.absX;
		this.absX = absX;
	}

	/**
	 * Moves the npc a certain x coordinate
	 * @param absX
	 */
	public void setAbsY(int absY) {
		this.lastY = this.absY;
		this.absY = absY;
	}
	
	/**
	 * Forces npc animation
	 * @param number
	 */
	public void forceAnim(int number) {
		animNumber = number;
		animUpdateRequired = true;
		updateRequired = true;
	}

	/**
	 * Gets npc
	 * @param _npcId
	 * @param _npcType
	 */
	public NPC(int _npcId, int _npcType) {
		npcId = _npcId;
		npcType = _npcType;
		direction = -1;
		isDead = false;
		applyDead = false;
		actionTimer = 0;
		randomWalk = true;
	}

	/**
	 * Faces npc to player
	 * @param playerId
	 */
	public void faceTo(int playerId) {
		this.dirUpdateRequired = true;
		this.updateRequired = true;
	}

	/**
	 * Updates npc movement
	 * @param str
	 */
	public void updateNPCMovement(RS2Stream str) {
		if (direction == -1) {
			if (updateRequired) {
				str.writeBits(1, 1);
				str.writeBits(2, 0);
			} else {
				str.writeBits(1, 0);
			}
		} else {
			str.writeBits(1, 1);
			str.writeBits(2, 1);
			str.writeBits(3, MistexUtility.xlateDirectionToClient[direction]);
			if (updateRequired) {
				str.writeBits(1, 1);
			} else {
				str.writeBits(1, 0);
			}
		}
	}

	/**
	 * Deals npc damage
	 * @param damage
	 */
	public void dealDamage(int damage) {
		if (damage > HP) {
			damage = HP;
		}
		HP -= damage;
	}

	/**
	 * Npc forced chat
	 * @param text
	 **/
	public void forceChat(String text) {
		forcedText = text;
		forcedChatRequired = true;
		updateRequired = true;
	}

	/**
	 * 
	 * @return the npcId
	 */
	public int getNpcId() {
		return npcId;
	}

	/**
	 * Appends npc mask 80 update
	 * @param str
	 */
	public void appendMask80Update(RS2Stream str) {
		str.writeWord(mask80var1);
		str.writeDWord(mask80var2);
	}

	/**
	 * Npc gfx100
	 * @param gfx
	 */
	public void gfx100(int gfx) {
		mask80var1 = gfx;
		mask80var2 = 6553600;
		mask80update = true;
		updateRequired = true;
	}

	/**
	 * Npc gfx0
	 * @param gfx
	 */
	public void gfx0(int gfx) {
		mask80var1 = gfx;
		mask80var2 = 65536;
		mask80update = true;
		updateRequired = true;
	}

	/**
	 * Appends npc animation update
	 * @param str
	 */
	public void appendAnimUpdate(RS2Stream str) {
		str.writeWordBigEndian(animNumber);
		str.writeByte(1);
	}

	/**
	 * Appends set focus destination
	 * @param str
	 */
	private void appendSetFocusDestination(RS2Stream str) {
		str.writeWordBigEndian(FocusPointX);
		str.writeWordBigEndian(FocusPointY);
	}

	/**
	 * Turns the npc
	 * @param i
	 * @param j
	 */
	public void turnNpc(int i, int j) {
		FocusPointX = 2 * i + 1;
		FocusPointY = 2 * j + 1;
		updateRequired = true;
	}

	/**
	 * Appends npc face entity
	 * @param str
	 */
	public void appendFaceEntity(RS2Stream str) {
		str.writeWord(face);
	}

	/**
	 * Faces the npc to player
	 * @param player
	 */
	public void facePlayer(int player) {
		face = player + 32768;
		dirUpdateRequired = true;
		updateRequired = true;
	}

	/**
	 * Appends face to update
	 * @param str
	 */
	public void appendFaceToUpdate(RS2Stream str) {
		str.writeWordBigEndian(viewX);
		str.writeWordBigEndian(viewY);
	}

	/**
	 * Appends npc update block
	 * @param str
	 */
	public void appendNPCUpdateBlock(RS2Stream str) {
		if (!updateRequired)
			return;
		int updateMask = 0;
		if (animUpdateRequired)
			updateMask |= 0x10;
		if (hitUpdateRequired2)
			updateMask |= 8;
		if (mask80update)
			updateMask |= 0x80;
		if (dirUpdateRequired)
			updateMask |= 0x20;
		if (forcedChatRequired)
			updateMask |= 1;
		if (hitUpdateRequired)
			updateMask |= 0x40;
		if (FocusPointX != -1)
			updateMask |= 4;
		str.writeByte(updateMask);
		if (animUpdateRequired)
			appendAnimUpdate(str);
		if (hitUpdateRequired2)
			appendHitUpdate2(str);
		if (mask80update)
			appendMask80Update(str);
		if (dirUpdateRequired)
			appendFaceEntity(str);
		if (forcedChatRequired) {
			str.writeString(forcedText);
		}
		if (hitUpdateRequired)
			appendHitUpdate(str);
		if (FocusPointX != -1)
			appendSetFocusDestination(str);
	}

	/**
	 * Clears npc update flags
	 */
	public void clearUpdateFlags() {
		updateRequired = false;
		forcedChatRequired = false;
		hitUpdateRequired = false;
		hitUpdateRequired2 = false;
		animUpdateRequired = false;
		dirUpdateRequired = false;
		mask80update = false;
		forcedText = null;
		moveX = 0;
		moveY = 0;
		direction = -1;
		FocusPointX = -1;
		FocusPointY = -1;
	}

	/**
	 * Gets the next npc walking direction
	 * @return
	 */
	public int getNextWalkingDirection() {
		int dir;
		dir = MistexUtility.direction(absX, absY, (absX + moveX), (absY + moveY));
		if (dir == -1)
			return -1;
		dir >>= 1;
		absX += moveX;
		absY += moveY;
		return dir;
	}

	/**
	 * Gets the next npc movement
	 * @param i
	 */
	public void getNextNPCMovement(int i) {
		direction = -1;
		if (NPCHandler.npcs[i].freezeTimer == 0) {
			direction = getNextWalkingDirection();
		}
	}

	/**
	 * Handles npc append hit update 
	 * @param str
	 */
	public void appendHitUpdate(RS2Stream str) {
		if (HP <= 0) {
			isDead = true;
		}
		str.writeByteC(hitDiff);
		if (hitDiff > 0) {
			str.writeByteS(1);
		} else {
			str.writeByteS(0);
		}
		/*
		 * str.writeByteS(HP); str.writeByteC(MaxHP);
		 */
		str.writeByteS(MistexUtility.getCurrentHP(HP, MaxHP, 100));
		str.writeByteC(100);
	}

	/**
	 * Handles npc append hit update 2
	 * @param str
	 */
	public void appendHitUpdate2(RS2Stream str) {
		if (HP <= 0) {
			isDead = true;
		}
		str.writeByteA(hitDiff2);
		if (hitDiff2 > 0) {
			str.writeByteC(1);
		} else {
			str.writeByteC(0);
		}
		str.writeByteA(HP);
		str.writeByte(MaxHP);
	}

	/**
	 * Handles npc hitmask
	 * @param damage
	 */
	public void handleHitMask(int damage) {
		if (!hitUpdateRequired) {
			hitUpdateRequired = true;
			hitDiff = damage;
		} else if (!hitUpdateRequired2) {
			hitUpdateRequired2 = true;
			hitDiff2 = damage;
		}
		updateRequired = true;
	}
	
	/**
	 * General graador shouts
	 * @return
	 */
	public String Graardor() {
		int quote = MistexUtility.random(9);
		switch (quote) {
		case 0:
			return "Death to our enemies!";
		case 1:
			return "Brargh!";
		case 2:
			return "Break their bones!";
		case 3:
			return "For the glory of the Big High War God!";
		case 4:
			return "Split their skulls!";
		case 5:
			return "We feast on the bones of our enemies tonight!";
		case 6:
			return "CHAAARGE!";
		case 7:
			return "Crush them underfoot!";
		case 8:
			return "All glory to Bandos!";
		case 9:
			return "GRAAAAAAAAAR!";
		}
		return "";
	}

	/**
	 * Tsutsaroth shouts
	 * @return
	 */
	public String Tsutsaroth() {
		int quote = MistexUtility.random(8);
		switch (quote) {
		case 0:
			return "Attack them, you dogs!";
		case 1:
			return "Forward!";
		case 2:
			return "Death to Saradomin's dogs!";
		case 3:
			return "Kill them you cowards!";
		case 4:
			return "The Dark One will have their souls!";
		case 5:
			return "Zamorak curse them!";
		case 6:
			return "Rend them limb from limb!";
		case 7:
			return "No retreat!";
		case 8:
			return "Flay them all!!";
		}
		return "";
	}

	/**
	 * Commander zilyana shouts
	 * @return
	 */
	public String Zilyana() {
		int quote = MistexUtility.random(9);
		switch (quote) {
		case 0:
			return "Death to the enemies of the light!";
		case 1:
			return "Slay the evil ones!";
		case 2:
			return "Saradomin lend me strength!";
		case 3:
			return "By the power of Saradomin!";
		case 4:
			return "May Saradomin be my sword!";
		case 5:
			return "Good will always triumph!";
		case 6:
			return "Forward! Our allies are with us!";
		case 7:
			return "Saradomin is with us!";
		case 8:
			return "In the name of Saradomin!";
		case 9:
			return "Attack! Find the Godsword!";
		}
		return "";
	}
	
	/**
	 * Kree shouts
	 * @return
	 */
	public String Kree() {
		int quote = MistexUtility.random(6);
		switch (quote) {
		case 0:
			return "Attack with your talons!";
		case 1:
			return "Face the wratch of Armadyl";
		case 2:
			return "SCCCRREEEEEEEEEECHHHHH";
		case 3:
			return "KA KAW KA KAW";
		case 4:
			return "Fight my minions!";
		case 5:
			return "Good will always triumph!";
		case 6:
			return "Attack! Find the Godsword!";
		}
		return "";
	}

	/**
	 * Glod shouts
	 * @return
	 */
	public String Glod() {
		int talk = MistexUtility.random(2);
		switch (talk) {
		case 1:
			return "Glod Angry!";
		case 2:
			return "Glod Bash!";
		}
		return "Glod Smash!";
	}


	/**
	 * Checks if npc is in multi zone
	 * @return
	 */
	public boolean inMulti() {
		if ((absX >= 3136 && absX <= 3327 && absY >= 3519 && absY <= 3607) || 
	        	(absX >= 3190 && absX <= 3327 && absY >= 3648 && absY <= 3839) || 
	        	(absX >= 3200 && absX <= 3390 && absY >= 3840 && absY <= 3967) || 
	        	(absX >= 2992 && absX <= 3007 && absY >= 3912 && absY <= 3967) || 
	        	(absX >= 2946 && absX <= 2959 && absY >= 3816 && absY <= 3831) || 
	        	(absX >= 3008 && absX <= 3199 && absY >= 3856 && absY <= 3903) || 
	        	(absX >= 3008 && absX <= 3071 && absY >= 3600 && absY <= 3711) || 
	        	(absX >= 3072 && absX <= 3327 && absY >= 3608 && absY <= 3647) || 
	        	(absX >= 2624 && absX <= 2690 && absY >= 2550 && absY <= 2619) || 
	        	(absX >= 2371 && absX <= 2422 && absY >= 5062 && absY <= 5117) || 
	        	(absX >= 2896 && absX <= 2927 && absY >= 3595 && absY <= 3630) || 
	        	(absX >= 2892 && absX <= 2932 && absY >= 4435 && absY <= 4464) || 
	        	(absX >= 2654 && absX <= 2721 && absY >= 3705 && absY <= 3737) || 
	        	(absX >= 2508 && absX <= 2533 && absY >= 4630 && absY <= 4661) || 
	        	(absX >= 2138 && absX <= 2168 && absY >= 5084 && absY <= 5113) ||
	            (absX >= 2499 && absX <= 2514 && absY >= 4751 && absY <= 4769) || 
	            (absX >= 1741 && absX <= 1765 && absY >= 5222 && absY <= 5245) || 
	            (absX >= 2368 && absX <= 2427 && absY >= 5121 && absY <= 5168) ||
	            (gwdCoords()) || inNomadZone() ||
	            (absX > 3342 && absX < 3384 && absY > 3381 && absY < 3447)	||
	            (absX >= 2256 && absX <= 2287 && absY >= 4680 && absY <= 4711)) {
			return true;
		}
		return false;
	}
	
	/**
	 * Checks if npc is in nomad zone
	 * @return
	 */
    public boolean inNomadZone() {
        return (absX > 3488 && absX < 3516 && absY > 3564 && absY < 3586);
    }
    
    /**
     * Checks if npc is in godwars
     * @return
     */
	public boolean gwdCoords() {
		if (heightLevel == 2 || heightLevel == 0 || heightLevel == 1) {
		if (absX >= 2800 && absX <= 2950 && absY >= 5200 && absY <= 5400) {
				return true;
			}
		}
		return false;
	}

    /**
     * Checks if npc is in wilderness
     * @return
     */
	public boolean inWild() {
		if (absX > 2941 && absX < 3392 && absY > 3518 && absY < 3966
				|| absX > 2941 && absX < 3392 && absY > 9918 && absY < 10366) {
			return true;
		}
		return false;
	}
	

}
