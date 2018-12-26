package org.mistex.game.world.content.skill.summoning;

import org.mistex.game.Mistex;
import org.mistex.game.world.player.Client;

@SuppressWarnings("static-access")
public class Summoning {
	
	/**
	 * Todo:
	 * 
	 * Add Obelisk
	 * Make pouch creating/scroll working
	 * Make all global npcs drop charms
	 * Make the bob work
	 * 
	 */

    Client c;

    public Summoning(Client c) {
        this.c = c;
    }

    public int pouchreq;

    public void store() {
        c.storing = true;
        c.getPA().sendFrame126("Summoning BoB", 7421);
        for (int k = 0; k < 29; k++) {
            if (c.storeditems[k] > 0) {
                c.getPA().Frame34(7423, c.storeditems[k], k, c.amount[k]);
            }
            if (c.storeditems[k] <= 0) {
                c.getPA().Frame34(7423, -1, k, c.amount[k]);
            }

        }
       // c.isBanking = true;
        c.storing = true;
        c.getItems().resetItems(5064);
        //c.getItems().rearrangeBank();
        //c.getItems().resetBank();
        c.getItems().resetTempItems();
        c.getOutStream().createFrame(248);
        c.getOutStream().writeWordA(4465);
        c.getOutStream().writeWord(5063);
        //c.getOutStream().writeWord(10600);
        c.getPA().sendFrame87(286, 0);
        c.flushOutStream();
        c.ResetKeepItems();
        //c.getPA().showInterface(17100);
    }
    
	public void SummonNewNPC(int npcID, boolean message) {
        int maxhit = 0;
        int attack = 0;
        int defence = 0;
        String summonName = "No Familiar";
        c.getPA().sendFrame75(npcID, 23027);
        switch (npcID) {
        
        case 6830:
            maxhit = 4;
            attack = 10;
            defence = 80;
            summonName = "Spirit Wolf";
            break;

        case 6826:
            maxhit = 6;
            attack = 10;
            defence = 80;
            summonName = "Dread Fowl";
            break;

        case 6842:
            maxhit = 6;
            attack = 10;
            defence = 80;
            summonName = "Spirit Spider";
            break;

        case 6807:
            maxhit = 5;
            attack = 20;
            defence = 80;
            c.maxstore = 3;
            summonName = "Thorny Snail";
            break;

        case 6797:
            maxhit = 8;
            attack = 20;
            defence = 80;
            summonName = "Granite Crab";
            break;

        case 7332:
            maxhit = 8;
            attack = 20;
            defence = 80;
            summonName = "Spirit Mosquito";
            break;

        case 6832:
            maxhit = 8;
            attack = 20;
            defence = 80;
            summonName = "Desert Wyrm";
            break;

        case 6838:
            maxhit = 8;
            attack = 20;
            defence = 80;
            summonName = "Spirit Scorpion";
            break;

        case 7362:
            maxhit = 8;
            attack = 20;
            defence = 80;
            summonName = "Spirit Tz-Kih";
            break;

        case 6848:
            maxhit = 8;
            attack = 20;
            defence = 80;
            summonName = "Albino Rat";
            break;

        case 6995:
            maxhit = 10;
            attack = 20;
            defence = 80;
            summonName = "Spirit Kalphite";
            break;

        case 6872:
            maxhit = 10;
            attack = 20;
            defence = 80;
            summonName = "Compost Mound";
            break;

        case 7354:
            maxhit = 11;
            attack = 20;
            defence = 80;
            summonName = "Giant Chinchompa";
            break;

        case 6836:
            maxhit = 12;
            attack = 20;
            defence = 80;
            summonName = "Vampire Bat";
            break;

        case 6846:
            maxhit = 14;
            attack = 40;
            defence = 80;
            summonName = "Honey Badger";
            break;

        case 6808:
            maxhit = 12;
            attack = 40;
            defence = 80;;
            summonName = "Beaver";
            break;

        case 7371:
            maxhit = 11;
            attack = 40;
            defence = 80;
            summonName = "Void Ravager";
            break;
        case 7369:
            maxhit = 11;
            attack = 40;
            defence = 80;
            summonName = "Void Shifter";
            break;
        case 7368:
            maxhit = 11;
            attack = 40;
            defence = 80;
            summonName = "Void Shifter";
            break;
        case 7370:
            maxhit = 11;
            attack = 40;
            defence = 80;
            summonName = "Void Ravager";
            break;
        case 7352:
            maxhit = 11;
            attack = 40;
            defence = 80;
            summonName = "Void Torcher";
            break;

        case 6854:
            maxhit = 12;
            attack = 40;
            defence = 80;
            summonName = "Bronze Minotaur";
            break;

        case 6868:
            maxhit = 12;
            attack = 40;
            defence = 80;
            c.maxstore = 6;
            summonName = "Bull Ant";
            break;

        case 6852:
            maxhit = 8;
            attack = 40;
            defence = 80;
            summonName = "Macaw";
            break;
            
        case 6834:
            maxhit = 14;
            attack = 40;
            defence = 80;
            summonName = "Evil Turnip";
            break;

        case 6856:
            maxhit = 15;
            attack = 40;
            defence = 80;
            summonName = "Iron Minotaur";
            break;

        case 7378:
            maxhit = 14;
            attack = 40;
            defence = 80;
            summonName = "Pyrelord";
            break;

        case 6824:
            maxhit = 13;
            attack = 40;
            defence = 80;
            summonName = "Magpie";
            break;

        case 6844:
            maxhit = 12;
            attack = 40;
            defence = 80;
            summonName = "Bloated Leech";
            break;

        case 6795:
            c.maxstore = 12;
            maxhit = 11;
            attack = 60;
            defence = 80;
            summonName = "Spirit Terrorbird";
            break;

        case 6819:
            maxhit = 13;
            attack = 60;
            defence = 80;
            summonName = "Abyssal Parasite";
            break;
            
        case 6993:
            maxhit = 15;
            attack = 60;
            defence = 80;
            summonName = "Spirit Jelly";
            break;

        case 6858:
            maxhit = 11;
            attack = 60;
            defence = 80;
            summonName = "Steel Minotaur";
            break;

        case 6991:
            maxhit = 11;
            attack = 60;
            defence = 80;
            summonName = "Ibis";
            break;

        case 7364:
            maxhit = 20;
            attack = 60;
            defence = 80;
            summonName = "Spirit Graahk";
            break;
            
        case 7366:
            maxhit = 20;
            attack = 60;
            defence = 80;
            summonName = "Spirit Kyatt";
            break;
            
        case 7338:
            maxhit = 20;
            attack = 60;
            defence = 80;
            summonName = "Spirit Larupia";
            break;

        case 6810:
            maxhit = 11;
            attack = 60;
            defence = 80;
            summonName = "Karamthulhu Overlord";
            break;

        case 6821:
            maxhit = 11;
            attack = 60;
            defence = 80;
            summonName = "Abbysal Lurker";
            break;

        case 6803:
            maxhit = 14;
            attack = 60;
            defence = 80;
            summonName = "Spirit Cobra";
            break;

        case 6828:
            maxhit = 18;
            attack = 60;
            defence = 80;
            summonName = "Stranger Plant";
            break;

        case 6860:
            maxhit = 20;
            attack = 60;
            defence = 80;
            summonName = "Mithril Minotaur";
            break;

        case 6890:
            maxhit = 20;
            attack = 60;
            defence = 80;
            summonName = "Barker Toad";
            break;

        case 6816:
            c.maxstore = 18;
            maxhit = 21;
            attack = 60;
            defence = 80;
            summonName = "War Tortoise";
            break;

        case 6814:
            maxhit = 17;
            attack = 60;
            defence = 80;
            summonName = "Bunyip";
            break;

        case 7372:
            maxhit = 11;
            attack = 60;
            defence = 80;
            summonName = "Ravenous Locust";
            break;
            
        case 7373:
            maxhit = 11;
            attack = 60;
            defence = 80;
            summonName = "Ravenous Locust";
            break;
            
        case 7374:
            maxhit = 11;
            attack = 60;
            defence = 80;
            summonName = "Ravenous Locust";
            break;

        case 6840:
            pouchreq = 71;
            break;

        case 6817:
            maxhit = 11;
            attack = 60;
            defence = 80;
            summonName = "Fruit Bat";
            break;
            
        case 8576:
            pouchreq = 99;
            break;

        case 7346:
            maxhit = 25;
            attack = 80;
            defence = 80;
            summonName = "Obsidian Golem";
            break;

        case 6799:
            maxhit = 11;
            attack = 60;
            defence = 80;
            summonName = "Praying Mantis";
            break;

        case 6850:
            maxhit = 11;
            attack = 60;
            defence = 80;
            summonName = "Granite Lobster";
            break;

        case 6862:
            maxhit = 22;
            attack = 60;
            defence = 80;
            summonName = "Adamant Minotaur";
            break;

        case 7336:
            maxhit = 24;
            attack = 60;
            defence = 80;
            summonName = "Forge Regent";
            break;

        case 6801:
            maxhit = 11;
            attack = 60;
            defence = 80;
            summonName = "Giant Ant";
            break;

        case 7356:
            maxhit = 26;
            attack = 60;
            defence = 80;
            summonName = "Fire Titan";
            break;
            
        case 7358:
            maxhit = 26;
            attack = 60;
            defence = 80;
            summonName = "Moss Titan";
            break;
            
        case 7360:
            maxhit = 26;
            attack = 60;
            defence = 80;
            summonName = "Ice Titan";
            break;

        case 6812:
            maxhit = 28;
            attack = 60;
            defence = 80;
            summonName = "Hydra";
            break;

        case 6805:
            maxhit = 30;
            attack = 60;
            defence = 80;
            summonName = "Spirit Dagannoth";
            break;

        case 7342:
            maxhit = 30;
            attack = 60;
            defence = 80;
            summonName = "Lava Titan";
            break;

        case 7330:
            maxhit = 31;
            attack = 60;
            defence = 80;
            summonName = "Swamp Titan";
            break;
        case 6864:
            maxhit = 32;
            attack = 60;
            defence = 80;
            summonName = "Rune Minotaur";
            break;
            
        case 6823:
            maxhit = 33;
            attack = 60;
            defence = 80;
            summonName = "Unicorn Stallion";
            break;

        case 7340:
            maxhit = 34;
            attack = 60;
            defence = 80;
            summonName = "Geyser Titan";
            break;

        case 6870:
            maxhit = 35;
            attack = 60;
            defence = 80;
            summonName = "Wolpertinger";
            break;

        case 7350:
            maxhit = 36;
            attack = 60;
            defence = 80;
            summonName = "Abyssal Titan";
            break;

        case 7376:
            maxhit = 37;
            attack = 60;
            defence = 80;
            summonName = "Iron Titan";
            break;

        case 6874:
            c.maxstore = 27;
            maxhit = 38;
            attack = 60;
            defence = 80;
            summonName = "Pack Yak";
            break;

        case 7344:
            maxhit = 39;
            attack = 90;
            defence = 80;
            summonName = "Steel Titan";
            break;
        }
        switch (npcID) {
        case 6830:
            pouchreq = 1;
            break;

        case 6826:
            pouchreq = 4;
            break;

        case 6842:
            pouchreq = 10;
            break;

        case 6807:
            pouchreq = 13;
            break;

        case 6797:
            pouchreq = 16;
            break;


        case 7332:
            pouchreq = 17;
            break;

        case 6832:
            pouchreq = 18;
            break;


        case 6838:
            pouchreq = 19;
            break;

        case 7362:
            pouchreq = 22;
            break;


        case 6848:
            pouchreq = 23;
            break;

        case 6995:
            pouchreq = 25;
            break;

        case 6872:
            pouchreq = 28;
            break;

        case 7354:
            pouchreq = 29;
            break;

        case 6836:
            pouchreq = 31;
            break;

        case 6846:
            pouchreq = 32;
            break;

        case 6808:
            pouchreq = 33;
            break;

        case 7371:
        case 7369:
        case 7368:
        case 7370:
        case 7352:
            pouchreq = 34;
            break;

        case 6854:
        case 68:
            pouchreq = 36;
            break;

        case 6868:
            pouchreq = 40;
            break;

        case 6852:
            pouchreq = 41;
            break;
        case 6834:
            pouchreq = 42;
            break;

        case 6856:
            pouchreq = 46;
            break;

        case 7378:
            pouchreq = 46;
            break;

        case 6824:
            pouchreq = 47;
            break;

        case 6844:
            pouchreq = 49;
            break;

        case 6795:
            pouchreq = 52;
            break;

        case 6819:
            pouchreq = 54;
            break;
        case 6993:
            pouchreq = 55;
            break;

        case 6858:
            pouchreq = 56;
            break;

        case 6991:
            pouchreq = 56;
            break;


        case 7364:
        case 7366:
        case 7338:
            pouchreq = 57;
            break;

        case 6810:
            pouchreq = 58;
            break;

        case 6866:
            pouchreq = 99;
            break;

        case 6821:
            pouchreq = 62;
            break;


        case 6803:
            pouchreq = 63;
            break;

        case 6828:
            pouchreq = 64;
            break;

        case 6860:
            pouchreq = 66;
            break;


        case 6890:
            pouchreq = 66;
            break;

        case 6816:
            pouchreq = 67;
            break;

        case 6814:
            pouchreq = 68;
            break;


        case 7372:
        case 7373:
        case 7374:
            pouchreq = 70;
            break;

        case 6840:
            pouchreq = 71;
            break;

        case 6817:
            pouchreq = 69;
            break;
        case 8576:
            pouchreq = 99;
            break;

        case 7346:
            pouchreq = 73;
            break;

        case 6799:
            pouchreq = 75;
            break;

        case 6850:
            pouchreq = 74;
            break;

        case 6862:
            pouchreq = 76;
            break;

        case 7336:
            pouchreq = 76;
            break;

        case 6801:
            pouchreq = 78;
            break;

        case 7356:
        case 7358:
        case 7360:
            pouchreq = 79;
            break;

        case 6812:
            pouchreq = 80;
            break;


        case 6805:
        case 7342:
            pouchreq = 83;
            break;

        case 7330:
            pouchreq = 85;
            break;
        case 6864:
            pouchreq = 86;
            break;
        case 6823:
            pouchreq = 88;
            break;
        case 7340:
            pouchreq = 89;
            break;

        case 6870:
            pouchreq = 92;
            break;

        case 7350:
            pouchreq = 93;
            break;

        case 7376:
            pouchreq = 95;
            break;
        case 6874:
            pouchreq = 96;
            break;
        case 7344:
            pouchreq = 99;
            break;
        }
        if (c.playerLevel[23] >= pouchreq) {
        	Mistex.npcHandler.Summon(c, npcID, summonName, c.absX, c.absY - 1, c.heightLevel, 0, 100, maxhit, false, attack, defence);
            c.getItems().deleteItem(c.s, 1);
            c.setSidebarInterface(15, 18017); // Summonin
            c.sendMessage(":summoningOn:");
            if (message == true) {
            	c.sendMessage("<col=8345667>You have spawned the "+summonName+"!");
            	c.faceUpdate(c.summoningnpcid);
            }
            c.getPA().sendFrame126(summonName, 18028);
            c.getPA().sendFrame126("@inf@", 18043);
            c.getPA().sendFrame126(""+c.summAmount, 18045);
            for (int i = 0; i < Mistex.npcHandler.maxNPCs; i++) {
                if (Mistex.npcHandler.npcs[i] != null) {
                    c.npcslot = Mistex.npcHandler.npcs[i].npcId;
                }
            }
        } else {
            c.sendMessage("<col=8345667>You need " + pouchreq + " Summoning to summon this monster");
        }
    }
}