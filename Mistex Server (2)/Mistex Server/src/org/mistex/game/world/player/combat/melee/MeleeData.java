package org.mistex.game.world.player.combat.melee;

import org.mistex.game.MistexConfiguration;
import org.mistex.game.world.World;
import org.mistex.game.world.npc.animation.BlockAnimation;
import org.mistex.game.world.player.Client;

public class MeleeData {
	


	public static int getKillerId(Client c, int playerId) {
		int oldDamage = 0;
		int killerId = 0;
		for (int i = 1; i < MistexConfiguration.MAX_PLAYERS; i++) {
			if (World.players[i] != null) {
				if (World.players[i].killedBy == playerId) {
					if (World.players[i].withinDistance(World.players[playerId])) {
						if (World.players[i].totalPlayerDamageDealt > oldDamage) {
							oldDamage = World.players[i].totalPlayerDamageDealt;
							killerId = i;
						}
					}
					World.players[i].totalPlayerDamageDealt = 0;
					World.players[i].killedBy = 0;
				}
			}
		}
		return killerId;
	}

	public static void resetPlayerAttack(Client c) {
		c.usingMagic = false;
		c.npcIndex = 0;
		c.faceUpdate(0);
		c.playerIndex = 0;
		c.getPA().resetFollow();
	}

	public static boolean usingHally(Client c) {
		switch (c.playerEquipment[c.playerWeapon]) {
		case 3190:
		case 3192:
		case 3194:
		case 3196:
		case 3198:
		case 3200:
		case 3202:
		case 3204:
			return true;

		default:
			return false;
		}
	}

	public static void getPlayerAnimIndex(Client c, String weaponName) {
        c.playerStandIndex = 0x328;
        c.playerTurnIndex = 0x338;
        c.playerWalkIndex = 0x333;
        c.playerTurn180Index = 0x338;
        c.playerTurn90CWIndex = 0x338;
        c.playerTurn90CCWIndex = 0x338;
        c.playerRunIndex = 0x338;

        if (weaponName.contains("halberd") || weaponName.contains("guthan")) {
            c.playerStandIndex = 809;
            c.playerWalkIndex = 1146;
            c.playerRunIndex = 1210;
            return;
        }
        if (weaponName.contains("dharok")) {
            c.playerStandIndex = 0x811;
            c.playerWalkIndex = 0x67F;
            c.playerRunIndex = 0x680;
            return;
        }
        if (weaponName.contains("chaotic maul")) {
            c.playerStandIndex = 1662;
            c.playerWalkIndex = 1663;
            c.playerRunIndex = 1664;
            return;
        }
        if (weaponName.contains("ahrim")) {
            c.playerStandIndex = 809;
            c.playerWalkIndex = 1146;
            c.playerRunIndex = 1210;
            return;
        }
        if (weaponName.contains("verac")) {
            c.playerStandIndex = 0x328;
            c.playerWalkIndex = 0x333;
            c.playerRunIndex = 824;
            return;
        }
        if (weaponName.contains("chaotic staff")) {
            c.playerStandIndex = 808;
            c.playerRunIndex = 1210;
            c.playerWalkIndex = 1146;
            return;
        }
        if (weaponName.contains("wand") || weaponName.contains("staff") || weaponName.contains("staff") || weaponName.contains("spear")) {
            c.playerStandIndex = 8980;
            c.playerRunIndex = 1210;
            c.playerWalkIndex = 1146;
            return;
        }
        if (weaponName.contains("karil")) {
            c.playerStandIndex = 2074;
            c.playerWalkIndex = 2076;
            c.playerRunIndex = 2077;
            return;
        }
        if (weaponName.contains("2h sword") || weaponName.contains("godsword") || weaponName.contains("saradomin sw")) {
            c.playerStandIndex = 7047;
            c.playerWalkIndex = 7046;
            c.playerRunIndex = 7039;
            return;
        }
        if (weaponName.contains("bow")) {
            c.playerStandIndex = 808;
            c.playerWalkIndex = 819;
            c.playerRunIndex = 824;
            return;
        }

        switch (c.playerEquipment[c.playerWeapon]) {
        case 7673:
        case 7671:
        	c.playerStandIndex = 3677;
        	c.playerWalkIndex = 3680;
        	break;
            case 4151:
                c.playerStandIndex = 11973;
                c.playerWalkIndex = 11975;
                c.playerRunIndex = 1661;
                break;
            case 15403:

                c.playerStandIndex = 0x811;
                c.playerWalkIndex = 0x67F;
                c.playerRunIndex = 0x680;
                break;

            case 15441:
                c.playerStandIndex = 11973;
                c.playerWalkIndex = 11975;
                c.playerRunIndex = 1661;
                break;
            case 15241:
                c.playerStandIndex = 12155;
                c.playerWalkIndex = 12154;
                c.playerRunIndex = 12154;
                break;
            case 15442:
                c.playerStandIndex = 11973;
                c.playerWalkIndex = 11975;
                c.playerRunIndex = 1661;
                break;
            case 15443:
                c.playerStandIndex = 11973;
                c.playerWalkIndex = 11975;
                c.playerRunIndex = 1661;
                break;
            case 15444:
                c.playerStandIndex = 11973;
                c.playerWalkIndex = 11975;
                c.playerRunIndex = 1661;
                break;
            case 10887:
                c.playerStandIndex = 5869;
                c.playerWalkIndex = 5867;
                c.playerRunIndex = 5868;
                break;
            case 6528:
                c.playerStandIndex = 0x811;
                c.playerWalkIndex = 2064;
                c.playerRunIndex = 1664;
                break;
            case 4153:
                c.playerStandIndex = 1662;
                c.playerWalkIndex = 1663;
                c.playerRunIndex = 1664;
                break;
            case 13022:
                c.playerStandIndex = 12155;
                c.playerWalkIndex = 12155;
                c.playerRunIndex = 12154;
                break;
            case 11694:
            case 11696:
            case 11730:
            case 11698:
            case 11700:
                c.playerStandIndex = 4300;
                c.playerWalkIndex = 4306;
                c.playerRunIndex = 4305;
                break;
            case 1305:
                c.playerStandIndex = 809;
                break;
        }
    }

	public static int getWepAnim(Client c, String weaponName) {
		   if (c.playerEquipment[c.playerWeapon] <= 0) {
	            switch (c.fightMode) {
	                case 0://att
	                    return 422;
	                case 2://kick
	                    return 423;
	                case 1://block
	                    return 451;
	            }
	        }
	        if (weaponName.contains("morrigan's throwing axe") || weaponName.contains("knife") || weaponName.contains("dart") || weaponName.contains("javelin") || weaponName.contains("thrownaxe")) {
	            return 806;
	        }
	        if (weaponName.contains("chinchompa")) {
	            return 2779;
	        }
	        if (weaponName.startsWith("boxing")) {
	        	return 3678;
	        }
	        if (weaponName.contains("halberd")) {
	            return 440;
	        }
	        if (weaponName.startsWith("dragon dagger")) {
	            return 402;
	        }
	        if (weaponName.endsWith("dagger")) {
	            return 412;
	        }
	        if (weaponName.contains("crossbow")) {
	            return 4230;
	        }
	        if (weaponName.contains("chaotic rapier")) {
	            return 386;
	        }
	        if (weaponName.contains("2h sword") || weaponName.contains("godsword") || weaponName.contains("saradomin sword") || weaponName.contains("Dragon 2h sword")) {
	            switch (c.fightMode) {
	                case 0:
	                    return 7041;
	                case 2:
	                    return 7041;
	                case 1:
	                    return 7048;
	            }
	        }
	        if (weaponName.contains("scimitar") || weaponName.contains("longsword")) {
	            switch (c.fightMode) {
	                case 0:
	                    return 12029;
	                case 1:
	                    return 12029;
	                case 2:
	                    return 12029;
	                case 3:
	                    return 12028;
	            }
	        }
	        if (weaponName.contains("rapier")) {
	            switch (c.fightMode) {
	                case 0:
	                    return 390;
	                case 1:
	                    return 390;
	                case 2:
	                    return 390;
	                case 3:
	                    return 386;
	            }
	        }
	        if (weaponName.contains("dharok") || weaponName.contains("balmung")) {
	            switch (c.fightMode) {
	                case 0:
	                    return 2066;
	                case 1:
	                    return 2066;
	                case 2:
	                    return 2066;
	                case 3:
	                    return 2067;
	            }
	        }
	        if (weaponName.contains("sword")) {
	            return 451;
	        }
	        if (weaponName.contains("karil")) {
	            return 2075;
	        }
	        //if(weaponName.contains("bow") && !weaponName.contains("'bow")) {
	        //return 426;
	        //}
	        if (weaponName.contains("'bow")) return 4230;
	        if (weaponName.contains("Hand cannon")) return 4230;
	        switch (c.playerEquipment[c.playerWeapon]) { // if you don't want to use strings
	            case 841:
	            case 843:
	            case 845:
	            case 847:
	            case 849:
	            case 851:
	            case 853:
	            case 855:
	            case 857:
	            case 859:
	            case 861:
	            case 4212:
	            case 4215:
	            case 4216:
	            case 4217:
	            case 4218:
	            case 4219:
	            case 4220:
	            case 4221:
	            case 4222:
	            case 4223:
	            case 4214:

	            case 11235:
	            case 15701:
	            case 15702:
	            case 15703:
	            case 15704:

	                return 426;
	            case 18357:
	                return 4230;
	            case 6522:
	                return 2614;
	            case 13905:
	                return 2080;
	            case 4153:
	                // granite maul
	                return 1665;
	            case 18349:
	                // Item ID 
	                return 386; //Animation ID Chaotic rapier
	            case 18351:
	                //Item ID 
	                return 451; //Animation Id Chaotic longsword
	            case 4726:
	                // guthan 
	                return 2080;
	            case 15015:
	            case 15016:
	                return 806;
	            case 14484:
	                //  Dclaw
	                return 393;
	            case 18353:
	                //  Chaotic maul
	                return 2661;
	            case 13022:
	                return 12153;
	            case 4747:
	                // torag
	                return 0x814;
	            case 4710:
	                // ahrim
	                return 406;
	            case 4755:
	                // verac
	                return 2062;
	            case 4734:
	                // karil
	                return 2075;
	            case 15241:
	                return 12153;
	            case 10887:
	                return 5865;
	            case 4151:
	            case 15441:
	            case 15442:
	            case 15443:
	            case 15444:
	                return 1658;
	            case 6528:
	                return 2661;
	            default:
	                return 451;
	        }
	    }


	@SuppressWarnings("static-access")
	public static int getBlockEmote(Client c) {
		String shield = c.getItems().getItemName(c.playerEquipment[c.playerShield]).toLowerCase();
		String weapon = c.getItems().getItemName(c.playerEquipment[c.playerWeapon]).toLowerCase();
		if (shield.contains("defender"))
			return 4177;
		if (weapon.contains("staff"))
			return 12806;
		if (shield.contains("2h"))
			return 7050;
		if (shield.contains("chinchompa"))
			return 3176;
		if (shield.contains("book") || (weapon.contains("wand") || (weapon.contains("master"))))
			return 420;
		if (shield.contains("shield"))
			return 1156;
		switch (c.playerEquipment[c.playerWeapon]) {
		case 7673:
		case 7671:
			return 3679;
		case 10887:
			return 5866;
		case 20072:
			return 4177;
		case 4755:
			return 2063;
		case 15241:
			return 12156;
		case 13899:
			return 13042;
		case 18355:
			return 13046;
		case 14484:
			return 397;
		case 11716:
			return 12008;
		case 4153:
			return 1666;
		case 4151:
			return 1659;
		case 15486:
			return 12806;
		case 18349:
			return 12030;
		case 18353:
			return 13054;
		case 18351:
			return 13042;

		case 11694:
		case 11698:
		case 11700:
		case 11696:
		case 11730:
			return 7050;
		case -1:
			return 424;
		default:
			return 424;
		}
	}

	public static int getAttackDelay(Client c, String s) {
		if(c.usingMagic) {
			switch(c.MAGIC_SPELLS[c.spellId][0]) {
				case 12871: // ice blitz
				case 13023: // shadow barrage
				case 12891: // ice barrage
				return 5;
				
				default:
				return 5;
			}
		}
		if(c.playerEquipment[c.playerWeapon] == -1)
			return 4;//unarmed
		switch (c.playerEquipment[c.playerWeapon]) {
			case 11235:
			return 9;
			case 15039:
				return 7;
			case 11730:
			return 4;
			case 6528:
			return 7;
			case 10033:
			case 10034:
			return 5;
		}
		if(s.endsWith("greataxe")) 
			return 7;
		else if(s.equals("torags hammers"))
			return 5;
		else if(s.equals("barrelchest anchor"))
			return 7;
		else if(s.equals("guthans warspear"))
			return 5;
		else if(s.equals("veracs flail"))
			return 5;
		else if(s.equals("ahrims staff"))
			return 6;
		else if(s.contains("staff")){
			if(s.contains("zamarok") || s.contains("guthix") || s.contains("saradomian") || s.contains("slayer") || s.contains("ancient"))
				return 4;
			else
				return 5;
		} else if(s.contains("bow")){
			if(s.contains("composite") || s.equals("seercull"))
				return 5;
			else if (s.contains("aril"))
				return 4;
			else if(s.contains("Ogre"))
				return 8;
			else if(s.contains("short") || s.contains("hunt") || s.contains("sword"))
				return 4;
			else if(s.contains("long") || s.contains("crystal"))
				return 6;
			else if(s.contains("'bow"))
				return 7;
			
			return 5;
		}
		else if(s.contains("dagger"))
			return 4;
		else if(s.contains("godsword") || s.contains("2h"))
			return 6;
		else if(s.contains("longsword"))
			return 5;
		else if(s.contains("sword"))
			return 4;
		else if(s.contains("scimitar"))
			return 4;
		else if(s.contains("mace"))
			return 5;
		else if(s.contains("battleaxe"))
			return 6;
		else if(s.contains("pickaxe"))
			return 5;
		else if(s.contains("thrownaxe"))
			return 5;
		else if(s.contains("axe"))
			return 5;
		else if(s.contains("warhammer"))
			return 6;
		else if(s.contains("2h"))
			return 7;
		else if(s.contains("spear"))
			return 5;
		else if(s.contains("claw"))
			return 4;
		else if(s.contains("halberd"))
			return 7;
		else if(s.equals("granite maul"))
			return 7;
		else if(s.equals("toktz-xil-ak"))//sword
			return 4;
		else if(s.equals("tzhaar-ket-em"))//mace
			return 5;
		else if(s.equals("tzhaar-ket-om"))//maul
			return 7;
		else if(s.equals("toktz-xil-ek"))//knife
			return 4;
		else if(s.equals("toktz-xil-ul"))//rings
			return 4;
		else if(s.equals("toktz-mej-tal"))//staff
			return 6;
		else if(s.contains("whip"))
			return 4;
		else if(s.contains("dart"))
			return 3;
		else if(s.contains("knife"))
			return 3;
		else if(s.contains("javelin"))
			return 6;
		return 5;
	}

	public static int getHitDelay(Client c, int i, String weaponName) {
		if (c.usingMagic) {
			switch (c.MAGIC_SPELLS[c.spellId][0]) {
			case 12891:
				return 4;
			case 12871:
				return 6;
			default:
				return 4;
			}
		} else {
			if (weaponName.contains("dart")) {
				return 3;
			}
			if (weaponName.contains("knife") || weaponName.contains("javelin")
					|| weaponName.contains("thrownaxe")) {
				return 3;
			}
			if (weaponName.contains("cross") || weaponName.contains("c'bow")) {
				return 4;
			}
			if (weaponName.contains("bow") && !c.dbowSpec) {
				return 4;
			} else if (c.dbowSpec) {
				return 4;
			}

			switch (c.playerEquipment[c.playerWeapon]) {
			case 6522: // Toktz-xil-ul
				return 3;
			case 10887:
				return 3;
			case 10034:
			case 10033:
				return 3;
			default:
				return 2;
			}
		}
	}

	public static int npcDefenceAnim(int i) {
		return BlockAnimation.handleEmote(i);
	}

}