package org.mistex.game.world.player.combat.melee;

import org.mistex.game.Mistex;
import org.mistex.game.MistexUtility;
import org.mistex.game.world.World;
import org.mistex.game.world.npc.NPC;
import org.mistex.game.world.npc.NPCHandler;
import org.mistex.game.world.player.Client;


public class MeleeSpecial {

	public static boolean checkSpecAmount(Client c, int weapon) {
		switch(weapon) {
			case 1249:
			case 1215:
			case 1231:
			case 5680:
			case 5698:
			case 1305:
			case 1434:
			if(c.specAmount >= 2.5) {
				c.specAmount -= 2.5;
				c.getItems().addSpecialBar(weapon);
				return true;
			}
			return false;
			case 19784:
				if(c.specAmount >= 6) {
					c.specAmount -= 6;
					c.getItems().addSpecialBar(weapon);
					return true;
				}
				return false;
				
			case 4151:
			case 11694:
			case 11698:
			case 4153:
			case 14484:
			case 10887:
			if(c.specAmount >= 5) {
				c.specAmount -= 5;
				c.getItems().addSpecialBar(weapon);
				return true;
			}
			return false;
			
			case 3204:
			if(c.specAmount >= 3) {
				c.specAmount -= 3;
				c.getItems().addSpecialBar(weapon);
				return true;
			}
			return false;
			
			case 1377:
			case 11696:
			case 11730:
			if(c.specAmount >= 10) {
				c.specAmount -= 10;
				c.getItems().addSpecialBar(weapon);
				return true;
			}
			return false;
			
			case 4587:
			case 859:
			case 861:
			case 11235:
			case 11700:
			if(c.specAmount >= 5.5) {
				c.specAmount -= 5.5;
				c.getItems().addSpecialBar(weapon);
				return true;
			}
			return false;

			
			default:
			return true; // incase u want to test a weapon
		}
	}

	@SuppressWarnings("static-access")
	public static void activateSpecial(Client c, int weapon, int i){
		if(NPCHandler.npcs[i] == null && c.npcIndex > 0) {
			return;
		}
		/*if(PlayerHandler.players[i] == null && c.playerIndex > 0) {
			return;
		}*/
		c.doubleHit = false;
		c.specEffect = 0;
		c.projectileStage = 0;
		c.specMaxHitIncrease = 2;
		if(c.npcIndex > 0) {
			c.oldNpcIndex = i;
		} else if (c.playerIndex > 0){
			c.oldPlayerIndex = i;
			World.players[i].underAttackBy = c.playerId;
			World.players[i].logoutDelay = System.currentTimeMillis();
			World.players[i].singleCombatDelay = System.currentTimeMillis();
			World.players[i].killerId = c.playerId;
		}
		if(c.playerIndex > 0) {
			c.getPA().followPlayer();
		} else if(c.npcIndex > 0) {
			c.getPA().followNpc();
		}
		switch(weapon) {
			case 10887:
			c.gfx0(1027);
			c.startAnimation(5870);
			c.hitDelay = c.getCombat().getHitDelay(i, c.getItems().getItemName(c.playerEquipment[c.playerWeapon]).toLowerCase());
			c.specDamage = 1.20;
			c.specAccuracy = 1.85;
			break;
			case 19784:
            case 19780: // Korasi's Sword
            	Client o1 = (Client) Mistex.playerHandler.players[c.playerIndex];
                if (c.playerIndex > 0) {
                        c.startAnimation(4000);
                        c.gfx100(1247);
                        o1.gfx100(1248);
                        c.specAccuracy = 8.00;
                        c.getCombat().getKorasiSpecial((int)(MistexUtility.random(c.getCombat().calculateMeleeMaxHit()) * 1) + (c.getCombat().calculateMeleeMaxHit() / 2));
                }
                if (NPCHandler.npcs[i] != null && c.npcIndex > 0) {
                        c.gfx100(1247);
                        Mistex.npcHandler.npcs[i].gfx0(1248);
                        c.startAnimation(4000);
                        c.specDamage = 2.00;
                        c.specAccuracy = 5.00;
                        c.hitDelay = c.getCombat().getHitDelay(i, c.getItems().getItemName(c.playerEquipment[c.playerWeapon]).toLowerCase());
                }
                break;

			case 1305: // dragon long
			c.gfx100(248);
			c.startAnimation(1058);
			c.hitDelay = c.getCombat().getHitDelay(i, c.getItems().getItemName(c.playerEquipment[c.playerWeapon]).toLowerCase());
			c.specAccuracy = 1.10;
			c.specDamage = 1.20;
			break;
			
			case 1215: // dragon daggers
			case 1231:
			case 5680:
			case 5698:
			c.gfx100(252);
			c.startAnimation(1062);
			c.hitDelay = c.getCombat().getHitDelay(i, c.getItems().getItemName(c.playerEquipment[c.playerWeapon]).toLowerCase());
			c.doubleHit = true;
			c.specAccuracy = 1.30;
			c.specDamage = 1.05;
			break;
			
			case 11730:
			c.gfx100(1224);
			c.startAnimation(7072);
			c.hitDelay = c.getCombat().getHitDelay(i, c.getItems().getItemName(c.playerEquipment[c.playerWeapon]).toLowerCase());
			c.doubleHit = true;
			c.ssSpec = true;
			c.specAccuracy = 1.30;
			break;
			  case 13905:
	                c.startAnimation(10499);
	                c.hitDelay = c.getCombat().getHitDelay(i, c.getItems().getItemName(c.playerEquipment[c.playerWeapon]).toLowerCase() + 1);
	                c.specDamage = 1.35;
	                c.gfx100(1835);
	                c.specAccuracy = 1.65;
	                break;
	            case 13902:
	                c.startAnimation(10505);
	                c.hitDelay = c.getCombat().getHitDelay(i, c.getItems().getItemName(c.playerEquipment[c.playerWeapon]).toLowerCase() + 1);
	                c.specDamage = 1.35;
	                c.gfx100(1840);
	                c.specAccuracy = 1.65;
	                break;
			  case 13899:
	                // VLS
	                c.startAnimation(10502);
	                c.hitDelay = c.getCombat().getHitDelay(i, c.getItems().getItemName(c.playerEquipment[c.playerWeapon]).toLowerCase() + 1);
	                c.specDamage = 1.35;
	                c.specAccuracy = 1.45;
	                break;

			case 14484: // Dragon Claws
			c.gfx0(1950);
			c.startAnimation(10961);
			c.specAccuracy = 100.0;
			c.clawDamage = 0;
			c.specDamage = 0.90; 
	        if (c.playerIndex > 0) {
	        	Client o = (Client) Mistex.playerHandler.players[c.playerIndex];
	        	if (MistexUtility.random(c.getCombat().calculateMeleeAttack()) > MistexUtility.random(o.getCombat().calculateMeleeDefence())) {
	        		c.clawDamage = MistexUtility.random(c.getCombat().calculateMeleeMaxHit() + MistexUtility.random(3));//4
	        	}
	        	c.clawIndex = c.playerIndex;
	        	c.clawType = 1;
	        } else if (c.npcIndex > 0) {
	        	NPC n = Mistex.npcHandler.npcs[c.npcIndex];
	        	if (MistexUtility.random(c.getCombat().calculateMeleeAttack()) > MistexUtility.random(n.defence)) {
	        		c.clawDamage = MistexUtility.random(c.getCombat().calculateMeleeMaxHit() + MistexUtility.random(1));//2
	        	}
	        	c.clawIndex = c.npcIndex;
	        	c.clawType = 2;
	        }
			c.doubleHit = true;
			c.usingClaws = false;
			c.specEffect = 5;
			c.hitDelay = c.getCombat().getHitDelay(i, c.getItems().getItemName(c.playerEquipment[c.playerWeapon]).toLowerCase());
			break;
			case 4151: // whip
			if(NPCHandler.npcs[i] != null) {
				NPCHandler.npcs[i].gfx100(341);
			}
			c.specAccuracy = 1.10;
			c.startAnimation(1658);
			c.hitDelay = c.getCombat().getHitDelay(i, c.getItems().getItemName(c.playerEquipment[c.playerWeapon]).toLowerCase());
			break;
			
			case 11694: // ags
			c.startAnimation(7074);
			c.specDamage = 1.25;
			c.specAccuracy = 1.85;
			c.gfx0(1222);
			c.hitDelay = c.getCombat().getHitDelay(i, c.getItems().getItemName(c.playerEquipment[c.playerWeapon]).toLowerCase());
			break;
			
			case 11700:
				c.startAnimation(7070);		
				c.gfx0(1221);
				c.specAccuracy = 1.25;
				c.hitDelay = c.getCombat().getHitDelay(i, c.getItems().getItemName(c.playerEquipment[c.playerWeapon]).toLowerCase());
				c.specEffect = 2;
			break;
			
			case 11696:
				c.startAnimation(7073);
				c.gfx0(1223);
				c.specDamage = 1.10;
				c.specAccuracy = 1.5;
				c.hitDelay = c.getCombat().getHitDelay(i, c.getItems().getItemName(c.playerEquipment[c.playerWeapon]).toLowerCase());
				c.specEffect = 3;
			break;
			
			case 11698:
				c.startAnimation(7071);
				c.gfx0(1220);
				c.specAccuracy = 1.25;
				c.specEffect = 4;
				c.hitDelay = c.getCombat().getHitDelay(i, c.getItems().getItemName(c.playerEquipment[c.playerWeapon]).toLowerCase());
			break;
			
			case 1249:
				c.startAnimation(405);
				c.gfx100(253);
				if (c.playerIndex > 0) {
					Client o = (Client)World.players[i];
					o.getPA().getSpeared(c.absX, c.absY);
				}	
			break;
			
			case 3204: // d hally
			c.gfx100(282);
			c.startAnimation(1203);
			c.hitDelay = c.getCombat().getHitDelay(i, c.getItems().getItemName(c.playerEquipment[c.playerWeapon]).toLowerCase());
			if(NPCHandler.npcs[i] != null && c.npcIndex > 0) {
				if(!c.goodDistance(c.getX(), c.getY(), NPCHandler.npcs[i].getX(), NPCHandler.npcs[i].getY(), 1)){
					c.doubleHit = true;
				}
			}
			if(World.players[i] != null && c.playerIndex > 0) {
				if(!c.goodDistance(c.getX(), c.getY(), World.players[i].getX(),World.players[i].getY(), 1)){
					c.doubleHit = true;
					c.delayedDamage2 = MistexUtility.random(c.getCombat().calculateMeleeMaxHit());
				}
			}
			break;
			
			case 4153: // maul
			c.startAnimation(1667);
			c.hitDelay = c.getCombat().getHitDelay(i, c.getItems().getItemName(c.playerEquipment[c.playerWeapon]).toLowerCase());
			/*if (c.playerIndex > 0)
				gmaulPlayer(i);
			else
				gmaulNpc(i);*/
			c.gfx100(337);
			break;
			
			case 4587: // dscimmy
			c.gfx100(347);
			c.specEffect = 1;
			c.startAnimation(1872);
			c.hitDelay = c.getCombat().getHitDelay(i, c.getItems().getItemName(c.playerEquipment[c.playerWeapon]).toLowerCase());
			break;
			
			case 1434: // mace
			c.startAnimation(1060);
			c.gfx100(251);
			c.specMaxHitIncrease = 3;
			c.hitDelay = c.getCombat().getHitDelay(i, c.getItems().getItemName(c.playerEquipment[c.playerWeapon]).toLowerCase())+1;
			c.specDamage = 1.35;
			c.specAccuracy = 1.15;
			break;
			
			case 859: // magic long
			c.usingBow = true;
			c.bowSpecShot = 3;
			c.rangeItemUsed = c.playerEquipment[c.playerArrows];
			c.getItems().deleteArrow();	
			c.lastWeaponUsed = weapon;
			c.startAnimation(426);
			c.gfx100(250);	
			c.hitDelay = c.getCombat().getHitDelay(i, c.getItems().getItemName(c.playerEquipment[c.playerWeapon]).toLowerCase());
			c.projectileStage = 1;
			if (c.fightMode == 2)
				c.attackTimer--;
			break;
			
			case 861: // magic short	
			c.usingBow = true;			
			c.bowSpecShot = 1;
			c.rangeItemUsed = c.playerEquipment[c.playerArrows];
			c.getItems().deleteArrow();
			c.getItems().deleteArrow();
			c.lastWeaponUsed = weapon;
			c.startAnimation(1074);
			c.hitDelay = 3;
			c.projectileStage = 1;
			c.hitDelay = c.getCombat().getHitDelay(i, c.getItems().getItemName(c.playerEquipment[c.playerWeapon]).toLowerCase());
			if (c.fightMode == 2)
				c.attackTimer--;
			if (c.playerIndex > 0)
				c.getCombat().fireProjectilePlayer();
			else if (c.npcIndex > 0)
				c.getCombat().fireProjectileNpc();	
			break;
			
		case 11235: // dark bow	
			c.usingBow = true;
			c.dbowSpec = true;
			c.rangeItemUsed = c.playerEquipment[c.playerArrows];
			c.getItems().deleteArrow();
			c.getItems().deleteArrow();
			if (c.playerIndex > 0) {
				c.getItems().dropArrowPlayer();
			} else if(c.npcIndex > 0) {
				c.getItems().dropArrowNpc();
			}
			c.lastWeaponUsed = weapon;
			c.hitDelay = 3;
			c.startAnimation(426);
			c.projectileStage = 1;
			c.gfx100(c.getCombat().getRangeStartGFX());
			c.hitDelay = c.getCombat().getHitDelay(i, c.getItems().getItemName(c.playerEquipment[c.playerWeapon]).toLowerCase());
			if (c.fightMode == 2)
				c.attackTimer--;
			if (c.playerIndex > 0)
				c.getCombat().fireProjectilePlayer();
			else if (c.npcIndex > 0)
				c.getCombat().fireProjectileNpc();
			c.specAccuracy = 1.85;
			c.specDamage = 1.65;
		break;
		}
		c.delayedDamage = MistexUtility.random(c.getCombat().calculateMeleeMaxHit());
		c.delayedDamage2 = MistexUtility.random(c.getCombat().calculateMeleeMaxHit());
		c.usingSpecial = false;
		c.getItems().updateSpecialBar();
	}
}