package org.mistex.game.world.npc.animation;

import org.mistex.game.MistexUtility;
import org.mistex.game.world.npc.NPCHandler;

public class AttackAnimation extends NPCHandler {

	public static int handleEmote(int i) {
		if (npcs[i].npcType >= 3732 && npcs[i].npcType <= 3741) {
			return 3901;
		}
		if (npcs[i].npcType >= 3742 && npcs[i].npcType <= 3746) {
			return 3915;
		}
		if (npcs[i].npcType >= 3747 && npcs[i].npcType <= 3751) {
			return 3908;
		}
		if (npcs[i].npcType >= 3752 && npcs[i].npcType <= 3761) {
			return 3880;
		}
		if (npcs[i].npcType >= 3762 && npcs[i].npcType <= 3771) {
			return 3920;
		}
		if (npcs[i].npcType >= 3772 && npcs[i].npcType <= 3776) {
			return 3896;
		}
		switch (NPCHandler.npcs[i].npcType) {


		
		case 10127://Unholy Cursebearer
			int random1 = MistexUtility.random(2);
			if (random1 == 0)
				return 13176;
			else if (random1 == 1)	
				return 13175;
			else if (random1 == 2)	
				return 13169;
		
		case 9780://Rammernaut
			int random = MistexUtility.random(1);
				if (random == 0)
					return 13703;
				else if (random == 1)	
					return 13705;
		
		case 10106://Bulwark beast(lvl-198)
			return 13001;
		
		case 1618:
			return 9436;
		
		case 5993://Experiment No.2
		return 6513;
		
		case 6212://Werewolf
		case 6213://Werewolf
		return 6536;
		
		case 6271://Ork
		case 6272://Ork
		case 6273://Ork
		case 6274://Ork
		return 4320;
		
		case 6285://Warped Terrorbird
		case 6293://Warped Terrorbird
		return 7108;
		
		case 6296://Warped Tortoise
		case 6297://Warped Tortoise
		return 7093;
		
		case 2745://Jad
			if (npcs[i].attackType == 2)
				return 9300;
			else if (npcs[i].attackType == 1)
				return 9276;
			else if (npcs[i].attackType == 0)
				return 9277;

	
		case 5229://Penance ranger 
		case 5230://Penance ranger 
		case 5231://Penance ranger 
		case 5232://Penance ranger 
		case 5233://Penance ranger 
		case 5234://Penance ranger 
		case 5235://Penance ranger
		case 5236://Penance ranger
		case 5237://Penance ranger
		return 5396;		

		case 5247://Penance Queen
		return 5411;		
				
		case 75://Zombie
		case 6763://Dried Zombie
		return 5578;

		case 5248://Queen Spawn
		return 5092;

		case 5452://Icelord 
		case 5453://Icelord
		case 5454://Icelord
		case 5455://Icelord
		return 5725;

		case 5627://Sorebones
		case 5628://Sorebones
		return 5647;

		case 5691://Undead Lumberjack
		case 5699://Undead Lumberjack
		case 5707://Undead Lumberjack 
		case 5715://Undead Lumberjack 
		case 5723://Undead Lumberjack 
		case 5731://Undead Lumberjack 
		case 5739://Undead Lumberjack 
		case 5747://Undead Lumberjack
		return 5970;		
				
		case 5750://Cave Bug
		return 6079;

		case 5906://A doubt
		return 6310;		

		case 3066://Zombie Champion
		return 5581;
		
		case 3313://Tanglefoot
		return 3262;
		
		case 4397://Catablepon
		case 4398://Catablepon
		case 4399://Catablepon
		return 4273;
		
		case 4418://Gorak
		case 6218://Gorak
		return 4300;
		
		case 4463://Vampire Juvenate
		case 4464://Vampire Juvenate
		case 4465://Vampire Juvenate
		return 7183;
		
		case 4527://Suqah
		return 4387;
		
		case 4893://Giant Lobster
		return 6261;
		
		case 4971://Baby Roc
		return 5031;
		
		case 4972://Giant Roc
		return 5024;
		
		case 5176://Ogre Shaman
		case 5181://Ogre Shaman
		case 5184://Ogre Shaman 
		case 5187://Ogre Shaman 
		case 5190://Ogre Shaman 
		case 5193://Ogre Shaman 
		return 359;
		
		case 5214://Penance Fighter 
		case 5215://Penance Fighter 
		case 5216://Penance Fighter  
		case 5217://Penance Fighter  
		case 5218://Penance Fighter  
		case 5219://Penance Fighter 
		return 5097;
		
		
		case 1831://Cave Slime
		return 1793;

		case 907://Kolodion
		case 910://Kolodion
		case 2497://Tribesman
		return 729;

		case 1676://Experiment
		return 1626;

		case 10100://Bulwark Beast
		return 13001;

		case 1677://Experiment
		return 1616;

		case 1678://Experiment
		return 1612;

		case 2361://Elf Warrior
		case 2362://Elf Warrior
		case 1183://Elf Warrior
		return 426;

		case 1605://Abberant Spectre
		return 1507;

		case 1612://Banshee
		return 9449;

		case 1620://Cockatrice
		case 1621://Cockatrice
		return 1562;
		
		
		case 3835://Kalphite Queen
			if (npcs[i].attackType == 0)
		return 6241;
			else
		return 6240;
		
		case 3495://Kalphite Queen 2
			if (npcs[i].attackType == 0)
		return 2075;
			else
		return 1979;	
	
		case 2881://Dagannoth Supreme
		return 2855;
		
		case 2882://Dagannoth Prime
		return 2854;
		
		case 2883://Dagannoth Rex
		return 2851;
		
		case 3200://Chaos Elemental
		return 3146;
		
		case 6261://Sergeant Strongstack
		case 6263://Sergeant Steelwill
		case 6265://Sergeant Grimspike
		return 6154;

		case 6222://Kree'arra
		return 6976;
		
		case 6225://Flockleader Geerin
		return 6953;
		
		case 6223://Wingman Skree
		return 6952;
		
		case 6227://Flight Kilisa
		return 6954;

		case 6247://Commander Zilyana
		return 6964;
		
		case 6248://Starlight
		return 6376;
		
		case 6250://Growler
		return 7018;
		
		case 6252://Bree
		return 7009;
		
		case 8281://Ballance Elemental
			return 10680;

		case 8282://Ballance Elemental
		return 10669;

		case 8283://Ballance Elemental
		return 10681;

                    case 8597://Avatar Of Creation                    
        		case 9437://Decaying Avatar
			return 11202;
		
		case 8596://Avatar Of Destruction
		return 11197;	
		
		case 3497://Gelatinnoth Mother
		case 3498://Gelatinnoth Mother
		case 3499://Gelatinnoth Mother
		case 3500://Gelatinnoth Mother
		case 3501://Gelatinnoth Mother
		case 3502://Gelatinnoth Mother
		return 1341;

        	case 10126://Unholy Cursebearer
		return 13169; 
		
		case 6604://Revenant Imp
		return 7407;
				
		case 6605://Revenant Goblin
		return 7449;
					
		case 6606://Revenant Icefiend
		return 7397;
					
		case 6615://Revenant Ork
		return 7411;
					
		case 6611://Revenant Knight
		return 7441;	
					
		case 6623://Revenant Vampire
		return 7441;
					
		case 6622://Revenant Icefiend
		case 6621://Revenant Pyrefiend
		return 7481;
						
		case 6645://Revenant Cyclops
		return 7453;
					
		case 6688://Revenant Hellhound
		return 7460;
					
		case 6647://Revenant Demon
		return 7474;
					
		case 6691://Revenant Dark Beast
		return 7476;
					
		case 6998://Revenant Dragon
		return 8589;

		 case 8528://Nomad
                     return 12697;
                    
		 case 10775://Frost Dragon
                     return 13155;
               
		 case 1158://Kalphite Queen
		 return 6231;

		 case 50://King Black Dragon
		 return 81;
		
		 case 7133://Bork
		 return 8754;
		
		 case 6260://General Graddor
			if (npcs[i].attackType == 0)
				return 7060;
			else
				return 7063;

		 case 5666://Barrelchest
			 if (npcs[i].attackType == 0)
		 return 5894;
			 else
		 return 5895;
		
		 case 3847://Sea Troll Queen
		 return 3992;
				
		 case 3340://Giant Mole
			 if(npcs[i].attackType == 7)
		 return 3311;
			 else if (npcs[i].attackType == 0) //melee
		 return 3312;
		
		 case 8349://Tormented Demon
			 if (npcs[i].attackType == 5) //mage
		 return 10917; 
			 else if (npcs[i].attackType == 6)//range
		 return 10918;
			 else if (npcs[i].attackType == 0)//melee
		 return 10922;
		
		 case 8133://Corpreal Beast
		   	if (npcs[i].attackType == 2) //mage
		 return 10066;
			else if (npcs[i].attackType == 1) //range
		 return 10053;
			else if(npcs[i].attackType == 0) //melee
		 return 10057; //melee
			else
		 return 10058;

		//Summoning 
		case 7342://Lava Titan
		case 7340://Geyser Titan
		return 7879;

		case 7344://Steel Titan
		return 8190;

		case 7346://Obsidian Golem
		return 8048;

		case 7348://Talon Beast
		return 5989;

		case 7350://Abyssal Titan
		return 7693;

		case 6795://Spirit Terrorbird
		return 1010;
									
		case 7336://Forge Regent
		return 7871;
		
		case 7354://Giant Chinchompa
		return 7755;

		case 7355://Fire Titan
		return 7834;

		case 7358://Moss Titan
		return 7844;

		case 7359://Ice Titan
		return 8183;

		case 7362://Spirit Tz-Kih
		return 8257;

		case 7364://Spirit Graahk
		case 7366://Spirit Kyatt
		return 5228;

		case 7374://Ravenous Locust
		return 7994;

		case 7376://Iron Titan
		return 7946;
		
		case 7330://Swamp Titan
		return 8223;

		case 7332://Spirit Mosquito
		return 8032;

		case 7338://Spirit Larupia
		return 5228;
		
		case 6797://Granite Crab
		return 8104;

		case 6799://Praying Mantis
		return 8069;

		case 6801://Giant Ent
		return 7853;

		case 6803://Spirit Cobra
		return 8159;

		case 6805://Spirit Dagannoth
		return 7786;

		case 6807://Thorny Snail
		return 8148;

		case 6810://Kuramthulu Overlord
		return 7970;

		case 6812://Hydra
		return 7935;

		case 6814://Bunyip
		return 7741;

		case 6816://War Tortoise
		return 8288;

		case 6819://Abyssal Parasite
		return 7667;

		case 6821://Abyssal Lurker
		return 7680;

		case 6823://Unicorn Stallion
		return 6376;

		case 6826://Dreadfowl
		return 5387;

		case 6828://Stranger Plant
		return 8208;

		case 6830://Spirit Wolf
		return 8292;

		case 6832://Desert Wyrm
		return 7795;

		case 6834://Evil Turnip
		return 8248;

		case 6836://Vampire Bat
		return 8275;

		case 6838://Spirit Scorpion
		return 6254;

		case 6856://Iron Minotaur
		return 4921;

		case 6858://Steel Minotaur
		return 5327;

		case 6860://Mithril Minotaur
		case 6862://Adamant Minotaur
		case 6864://Rune Minotaur
		return 7656;

		case 6868://Bull Ant
		return 7896;

		case 6870://Wolpertinger
		return 8303;
		
		case 6872://Compost Mound
		return 7769;

		case 6874://Pack Yak
		return 5782;

		case 6890://Barker Toad
		return 7260;

		
		//Minigames
		case 2627://Tz-Kih
		return 2621;
				
		case 2630://Tz-Kek
		return 2625;
				
		case 2631://Tok-Xil
		return 2633;
				
		case 2741://Yt-MejKot
		return 2637;
		
		case 2746://Yt-Hurkot
		return 2637;
		
		case 2743://Ket-Zek
		return 9264;
	
		case 7368://Void Shifter
		case 7369://Void Shifter
		return 8130;

		case 7371://Void Ravager
		return 8093;
		
		case 7352://Void Torcher
		return 8234;
		
		case 7334://Void Spinner
		return 8172;

		case 2028://Karil
		return 2075;
				
		case 2025://Ahrim
		return 729;
		
		case 2026://Dharok
		return 2067;
		
		case 2027://Guthan
		return 2080;
	
		case 2029://Torag
		return 0x814;
		
		case 2030://Verac
		return 2062;
		
		case 5228://Penance Runner
		return 5228;
		
		case 2031://Bloodworm
		return 2070;
		
		//Training & Misc
		case 1640://Jelly
        		return 8575;
				
       		 case 8321://Elite Dark Mage
       		 return 10516;
				  
       		 case 1250://Fiyr Shade
       		 return 1284;

		case 9172://Aquanite
		return 12042;

        		case 10815://New Red Dragon
        		case 10607://New Green Dragon
        		case 10224://New Black Dragon
		return 13151;

		case 8777://Chaos Dwarf Hancannoeer
		return 12141;

		case 8324://Elite Black Knight
		return 13053;

		case 7797://Kurask Overlord
		return 9439;

		case 6753://Mummy
		return 5554;

		case 5250://Scarab Mage
		return 7621;
				
		case 7808://Mummy Warrior
		return 5554;

		case 7135://Ork Legion
		return 8760;
				
		case 2892://Spinolyp
		case 2894://Spinolyp
		return 2868;
				
		case 123://Hobgoblin
		case 122://Hobgoblin
		return 164;

		case 2037://Skeleton
		return 5485;
			
		case 2457://Wallaski
		return 2365;

		case 6270://Cyclops
		case 6269://Ice cyclops
		case 4291://Cyclops
		case 4292://Ice cyclops
		return 4652;
				
		case 6219:// Spiritual Warrior
		case 6255:// Spiritual Warrior
		return 451;

		case 13://Wizard
		return 711;
		
		case 103://Ghost
		case 655://Tree Spirit
		return 123;
		
		case 1624://Dust Devil
		return 1557;
		
		case 1648://Crawling Hand
		return 9125;
		
		case 2783://Dark Beast
		return 2731;
		
		case 1615://Abyssal Demon
		return 1537;
		
		case 1613://Nechryael
		return 9487;
		
		case 1610://Gargoyle
		case 1611://Gargoyle
		return 9454;
		
		case 1643://Infernal Mage
		return 7186;
		case 1459:
			return 1402;
		
		case 1616://Basilisk
		return 1546;
		
		case 2605:
		case 2606:
		case 2607:
		case 2608:
		case 2609:
		case 2598:
		case 2599:
		case 2600:
		case 2610:
		case 2601:
		case 2602:
		case 2603:
		case 2591:
		case 2604:// tzhar-hur
			return 9286;
		
		case 90://Skeleton
		return 5485;

		case 53://Red Dragon
		case 54://Black Dragon
		case 55://Blue Dragon
		case 941://Green Dragon
		case 1590://Bronze Dragon
		case 1591://Iron Dragon
		case 1592://Steel Dragon
		case 5363://Mithril Dragon
		return 80;
		
		case 124://Earth Warrior
		return 390;
		
		case 803://Monk
		return 422;

		case 51://Baby Dragon
		case 52://Baby Blue Dragon
		case 1589://Baby Red Dragon
		return 25;			

		case 58://Shadow Spider
		case 59://Giant Spider
		case 60://Giant Spider
		case 61://Spider
		case 62://Jungle Spider
		case 63://Deadly Red Spider
		case 64://Ice Spider
		case 134://Poison Spider
		return 143;	
		
		case 105://Bear
		case 106://Bear
		return 41;
		
		case 412://Bat
		case 78://Giant Bat
		return 4915;
		
		case 127:
			return 185;
			
		case 6210:
			return 6573;
		
		case 2033://Giant Rat
		return 138;		
		
		case 102://Goblin
		case 100://Goblin
		case 101://Goblin
		return 6184;	
		
		case 81://Cow
		return 0x03B;
		
		case 21://Hero
		return 451;	
		
		case 41://Chicken
		return 55;	
		
		case 9://Guard
		case 32://Guard
		case 20://Paladin
		return 451;	
		
		case 1338://Dagannoth
		case 1340://Dagannoth
		case 1342://Dagannoth
		return 1341;
	
		case 19://White Knight
		return 451;
		
		case 110://Fire Giant
		case 111://Ice Giant
		case 112://Moss Giant
		case 117://Hill Giant
		return 4652;
		
		case 2452://Giant Rock Crab
		return 1312;
		
		case 2889://Rock Lobster
		return 2859;
		
		case 118://Dwarf
		case 119://Chaos Dwarf
		return 99;
		
		case 82://Lesser Demon
		case 83://Greater Demon
		case 84://Black Demon
		case 1472://Jungle Demon
		return 64;
		
		case 1267://Rock Crab
		case 1265://Rock Crab
		return 1312;
		
		case 125://Ice Warrior
		return 451;

		case 1153://Kalphite Worker
		case 1154://Kalphite Soldier
		case 1155://Kalphite Guardian
		case 1156://Kalphite Worker
		case 1157://Kalphite Guardian
		return 1184;
		default:
			return 433;
		}
	}

}
