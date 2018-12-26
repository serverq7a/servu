package org.mistex.game.world.content;

import org.mistex.game.world.player.Client;
import org.mistex.game.world.player.item.ItemAssistant;

/**
 * Contains data for using monkey greegrees.
 * @author Chex
 *
 */
public class Greegree {
	
	public static enum MonkeyData {
		SMALL_NINJA(4024, 1480, 1386, 1380, 1381, 1383, -1),
		LARGE_NINJA(4025, 1481, 1386, 1380, 1381, 1383, -1),
		MONKEY_GUARD(4026, 1482, 1401, 1399, 1400, 1402, 1403),
		BEARDED_MONKEY_GUARD(4027, 1483, 1401, 1399, 1400, 1402, 1403),
		BLUE_FACE_MONKEY_GUARD(4028, 1484, 1401, 1399, 1400, 1402, 1403),
		SMALL_ZOMBIE(4029, 1485, 1386, 1382, 1381, 1383,-1),
		LARGE_MONKEY(4030, 1486, 1386, 1382, 1381, 1383, -1),
		KARAMAJA_MONKEY(4031, 1487, 222, 219, 220, 220, 221);
		
		int greegreeID, npcID, standAnim, walkAnim, runAnim, attackAnim, blockAnim;
		
		private MonkeyData(int greegreeID, int npcID, int standAnim, int walkAnim, int runAnim, int attackAnim, int blockAnim) {
			this.greegreeID = greegreeID;
			this.npcID = npcID;
			this.standAnim = standAnim;
			this.walkAnim = walkAnim;
			this.runAnim = runAnim;
			this.attackAnim = attackAnim;
			this.blockAnim = blockAnim;
		}

		public int getGreegreeID() {
			return greegreeID;
		}
		
		public int getNpcID() {
			return npcID;
		}
		
		public int getStandAnim() {
			return standAnim;
		}
		
		public int getWalkAnim() {
			return walkAnim;
		}
		
		public int getRunAnim() {
			return runAnim;
		}
		
		public int getBlockAnim() {
			return blockAnim;
		}

		public int getAttackAnim() {
			return attackAnim;
		}

		public static MonkeyData forId(int id) {
			for (MonkeyData data: MonkeyData.values())
				if (data.greegreeID == id)
					return data;
			return null;
		}
		
		public static boolean isWearingGreegree(Client p) {
			return MonkeyData.forId(p.playerEquipment[p.playerWeapon]) != null;
		}

		public static boolean isAnim(int animId) {
			for (MonkeyData data: MonkeyData.values())
				if (data.attackAnim == animId || data.blockAnim == animId)
					return true;
			return false;
		}
	}
	
	public static boolean attemptGreegree(Client p, int weaponID) {
		int targetSlot = p.getItems().targetSlot(ItemAssistant.getItemName(weaponID).toLowerCase());
		if (MonkeyData.forId(weaponID) == null && targetSlot != p.playerWeapon && MonkeyData.isWearingGreegree(p)) {
			p.sendMessage("You can't equipt that while wearing a greegree.");
			return false;
		}
		MonkeyData data = MonkeyData.forId(weaponID);
		if (MonkeyData.isWearingGreegree(p) || data != null) {
			if (data != null) {
				setAnimations(p, data);
			} else {
				resetAnimations(p);
			}
			p.gfx100(359);
			return true;
		}
		return true;
	}
	
	public static void setAnimations(Client p, MonkeyData data) {
//		p.npcId2 = data.getNpcID();
//		p.isNpc = true;
		p.playerStandIndex = data.getStandAnim();
		p.playerWalkIndex = data.getWalkAnim();
		p.playerRunIndex = data.getRunAnim();
		p.playerTurnIndex = data.getWalkAnim();
		p.playerTurn180Index = data.getWalkAnim();
		p.playerTurn90CWIndex = data.getWalkAnim();
		p.playerTurn90CCWIndex = data.getWalkAnim();
		p.getPA().requestUpdates();
	}
	
	public static void resetAnimations(Client p) {
//		p.npcId2 = -1;
//		p.isNpc = false;
		p.getPA().resetAnimation();
		p.gfx100(359);
	}

	public static boolean attemptRemove(Client p, int slot) {
		if (slot == p.playerWeapon && MonkeyData.isWearingGreegree(p)) {
			resetAnimations(p);
		} else if (slot != p.playerWeapon && MonkeyData.isWearingGreegree(p)) {
			p.sendMessage("You can't remove items while wearing a greegree.");
			return false;
		}
		return true;
	}
}