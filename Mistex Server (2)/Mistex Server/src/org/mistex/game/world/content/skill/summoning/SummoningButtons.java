package org.mistex.game.world.content.skill.summoning;

import org.mistex.game.Mistex;
import org.mistex.game.world.player.Client;

public class SummoningButtons {

    @SuppressWarnings("static-access")
    public static void handleActionButtons(Client c, int actionButtonId) {
        switch (actionButtonId) {
        
        case 151045:
            c.getPA().removeAllWindows();
            c.getPA().sendFrame126("Summoning Pouch Creation", 39707);
            c.getPA().showInterface(39700);
            break;
            
        case 155026:
            if (c.playerRights >= 3) {
                c.getPA().removeAllWindows();
                c.getPA().sendFrame126("Summoning Scroll Creation", 39707);
                c.getPA().showInterface(38700);
            } else {
                c.sendMessage("<col=8345667>This feature is currently unavailable");
            }
            break;
            
        case 70115: 
        case 19144:
           if (c.lastsummon <= 0) {
               c.sendMessage("<col=8345667>You don't have a familiar to call!");
               return;
           }
           int npc = c.lastsummon;
           Mistex.npcHandler.npcs[c.summoningnpcid].isDead = true;
           Mistex.npcHandler.npcs[c.summoningnpcid].applyDead = true;
           c.lastsummon = -1;
           c.totalstored = 0;
           //c.summoningnpcid = 0;
           c.summoningslot = 0;
           c.Summoning().SummonNewNPC(npc, false);
           c.sendMessage("<col=8345667>You call your familiar!");
           break;
            
        case 19142:
        case 70109:
            if (c.lastsummon > 0) {
                c.sendMessage("<col=8345667>You you withdraw some items inside your pet");
                c.getBOB().withdrawBoB();
            } else {
                c.sendMessage("<col=8345667>You do not have a npc currently spawned");
            }
            break;
            
        case 70118:
        case 19143:
            c.getPA().sendFrame75(4000, 23027);
            if (c.lastsummon > 0) {
                c.faceUpdate(c.summoningnpcid);
                c.lastsummon = -1;
                c.totalstored = 0;
                c.summoningnpcid = 0;
                c.summoningslot = 0;
                c.getBOB().dropItems();
                c.sendMessage("<col=8345667>Your BoB items have drop on the floor");        
                c.setSidebarInterface(15, -1); // Summoning
                c.sendMessage(":summoningOff:");
            } else {
                c.sendMessage("<col=8345667>You do not have a npc currently spawned");
            }
            break;
            
        case 70098:
        	c.getSpecials().determineNPCSpecial();
            break;
            
		case 155031:
		case 155034:
		case 155037:
		case 155040:
		case 155043:
		case 155046:
		case 155049:
		case 155052: // Spirit scorpion
		case 155055: // spirit tz-kih
		case 155058: // albino rat
		case 155061: // spirit kalphite
		case 155064: // compost mound
		case 155067: // giant chinchompa
		case 155070: // vampire bat
		case 155073: // honey badger
		case 155076: // beaver
		case 155079: // void ravager
		case 155082: // void spinner
		case 155085: // void torcher
		case 155088: // void shifter
		case 155091: // bronze minotaur
		case 155094: // bull ant
		case 155097: // macaw
		case 155100: // evil turnip
		case 155103: // Spirit Cockatrice
		case 155106: // Spirit Guthatrice
		case 155109: // Spirit Saratrice
		case 155112: // Spirit Zamatrice
		case 155115: // Spirit Pengatrice
		case 155118: // Spirit Coraxatrice
		case 155121: // Spirit Vulatrice
		case 155124: // Iron Minotaur
		case 155127: // pyrelord
		case 155130: // magpie
		case 155133: // bloated leech
		case 155136: // spirit terrorbird
		case 155139: // abyssal parasite
		case 155142: // spirit jelly
		case 155145: // steel minotaur
		case 155148: // ibis
		case 155151: // spirit kyatt
		case 155154: // spirit larupia
		case 155157: // spirit graahk
		case 155160: // karamthulhu overlord
		case 155163: // smoke devil
		case 155166: // abyssal lurker
		case 155169: // spirit cobra
		case 155172: // stranger plant
		case 155175: // mithril minotaur
		case 155178: // barker toad
		case 155181: // war tortoise
		case 155184: // bunyip
		case 155187: // fruit bat
		case 155190: // ravenous locust
		case 155193: // arctic bear
		case 155196: // phoenix
		case 155199: // obby golem
		case 155202: // granite crab
		case 155205: // praying mantis
		case 155208: // forge regent		
		case 155211: // addy minotaur
		case 155214: // talon beast
		case 155217: // giant ent	
		case 155220: // fire titan
		case 155223: // moss titan
		case 155226: // ice titan
		case 155229: // hydra
		case 155232: // spirit dagannoth
		case 155235: // lava titan
		case 155238: // swamp titan
		case 155241: // rune minotaur
		case 155244: // unicorn stallion
		case 155247: // geyser titan
		case 155250: // wolpertinger
		case 155253: // abyssal titan
		case 156000: // iron titan	
		case 156003: // pack yak
		case 156006: // steel titan
			c.getPouchCreation().makeSummoningPouch(c, actionButtonId);
		break;
     

        }
    }

}