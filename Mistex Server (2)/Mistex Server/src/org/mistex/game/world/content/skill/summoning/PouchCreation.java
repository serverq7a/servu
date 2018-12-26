package org.mistex.game.world.content.skill.summoning;

import org.mistex.game.world.player.Client;
import org.mistex.game.world.player.PlayerConfiguration;

public class PouchCreation {

    Client c;

    public PouchCreation(Client c) {
        this.c = c;
    }

    /**
     * Handles Summoning Data
     **/
    private static final String[][] summoningPouchData = {
        {
            "Spirit wolf pouch", "Gold Charm", "Wolf bones", "7", "1", "Howl scroll"
        }, {
            "Dreadfowl pouch", "Gold Charm", "Raw chicken", "8", "4", "Dreadfowl strike scroll"
        }, {
            "Spirit spider pouch", "Gold Charm", "Spider carcass", "8", "10", "Egg spawn scroll"
        }, {
            "Thorny Snail pouch", "Gold Charm", "Thin snail", "9", "13","Slime spray scroll"
        }, {
            "Granite Crab pouch", "Gold Charm", "Iron ore", "7", "16", "Stony shell scroll"
        }, {
            "Mosquito pouch", "Gold Charm", "Proboscis", "1", "17","Pester scroll"
        }, {
            "Desert wyrm pouch", "Green Charm", "Bucket of sand", "45", "18",
                "Electric lash scroll"
        }, {
            "Spirit Scorpion pouch", "Crimson Charm", "Bronze claws", "57",
                "19", "Venom shot scroll"
        }, {
            "Spirit tz-kih pouch", "crimson charm", "Obsidian charm", "64",
                "22", "Fireball assault scroll"
        }, {
            "Albino rat pouch", "Blue Charm", "Raw rat meat", "75", "23",
                "Cheese feast scroll"
        }, {
            "Spirit kalphite pouch", "blue Charm", "potato cactus", "51",
                "25", "Sandstorm scroll"
        }, {
            "Compost mound pouch", "Green charm", "compost", "47", "28",
                "Generate compost scroll"
        }, {
            "Giant chinchompa pouch", "Blue Charm", "Chinchompa", "84", "29",
                "Explode scroll"
        }, {
            "Vampire bat pouch", "Crimson Charm", "Vampire dust", "81", "31",
                "Vampire touch scroll"
        }, {
            "Honey badger pouch", "Crimson Charm", "Honeycomb", "84", "32",
                "Insane ferocity scroll"
        }, {
            "Beaver pouch", "Green Charm", "Willow logs", "72", "33",
                "Multichop scroll"
        }, {
            "Void ravager pouch", "green Charm", "Ravager Charm", "74", "34",
                "Call to arms scroll"
        }, {
            "Void shifter pouch", "blue charm", "Shifter charm", "74", "34",
                "Call to arms scroll"
        }, {
            "void spinner pouch", "blue Charm", "spinner Charm", "74", "34",
                "Call to arms scroll"
        }, {
            "Void Torcher pouch", "blue Charm", "Torcher Charm", "74", "34",
                "Call to arms scroll"
        }, {
            "Bronze minotaur pouch", "Blue Charm", "Bronze bar", "102", "36",
                "Bronze bull rush scroll"
        }, {
            "Bull ant pouch", "gold Charm", "Marigolds", "11", "40",
                "Unburden scroll"
        }, {
            "Macaw pouch", "green Charm", "Clean guam", "78", "41",
                "Herbcall scroll"
        }, {
            "Evil turnip pouch", "crimson Charm", "Carved turnip", "104",
                "42", "Evil flames scroll"
        }, {
            "Iron minotaur pouch", "Blue Charm", "Iron bar", "125", "46",
                "Iron bull rush scroll"
        }, {
            "Pyrelord pouch", "Crimson Charm", "Tinderbox", "111", "46",
                "Immense heat scroll"
        }, {
            "Magpie pouch", "green Charm", "Gold ring", "88", "47",
                "Thieving fingers scroll"
        }, {
            "Bloated leech pouch", "Crimson Charm", "Raw beef", "117", "49",
                "Blood drain scroll"
        }, {
            "Spirit terrorbird pouch", "Gold Charm", "Raw bird meat", "12",
                "52", "Tireless run scroll"
        }, {
            "Abyssal parasite pouch", "green Charm", "Abyssal charm", "106",
                "54", "Abyssal drain scroll"
        }, {
            "Spirit jelly pouch", "blue Charm", "Jug of water", "151", "55",
                "Dissolve scroll"
        }, {
            "Steel minotaur pouch", "blue Charm", "steel bar", "141", "56",
                "Fish rain scroll"
        }, {
            "Ibis pouch", "green Charm", "Harpoon", "109", "56",
                "Steel bull rush scroll"
        }, {
            "Spirit Graahk pouch", "blue Charm", "graahk fur", "154", "57",
                "Ambush scroll"
        }, {
            "Spirit Kyatt pouch", "blue Charm", "Kyatt fur", "153", "57",
                "Rending scroll"
        }, {
            "Spirit larupia pouch", "blue Charm", "larupia fur", "155", "57",
                "Goad scroll"
        }, {
            "Karamthulhu overlord pouch", "blue Charm", "Empty fishbowl",
                "144", "58", "Doomsphere scroll"
        }, {
            "Smoke devil pouch", "Crimson Charm", "Goat horn dust", "141",
                "61", "Dust cloud scroll"
        }, {
            "Abyssal lurker", "green Charm", "Abyssal charm", "119", "62",
                "Abyssal stealth scroll"
        }, {
            "Spirit cobra pouch", "Crimson Charm", "Snake hide", "116", "63",
                "Ophidian incubation scroll"
        }, {
            "Stranger plant pouch", "Crimson Charm", "Bagged plant", "128",
                "64", "Poisonous blast scroll"
        }, {
            "Mithril minotaur pouch", "Blue Charm", "Mithril bar", "152",
                "66", "Mithril bull rush scroll"
        }, {
            "Barker toad pouch", "Gold Charm", "Swamp toad", "11", "66",
                "Toad bark scroll"
        }, {
            "War tortoise pouch", "Gold Charm", "Tortoise shell", "1", "67",
                "Testudo scroll"
        }, {
            "Bunyip pouch", "Green Charm", "Raw shark", "110", "68",
                "Swallow whole scroll"
        }, {
            "Fruit bat pouch", "Green Charm", "Banana", "130", "69",
                "Fruitfall scroll"
        }, {
            "Ravenous Locust pouch", "Crimson Charm", "Pot of Flour", "79",
                "70", "Famine scroll"
        }, {
            "Arctic bear pouch", "Gold Charm", "Polar kebbit fur", "14",
                "71", "Arctic blast scroll"
        }, {
            "Phoenix pouch", "Crimson Charm", "Phoenix Quill", "165", "72",
                ""
        }, {
            "Obsidian Golem pouch", "Blue Charm", "Obsidian Charm", "195",
                "73", "Volcanic strength scroll"
        }, {
            "Granite lobster pouch", "Crimson Charm", "Granite (500g)",
                "166", "74", "Crushing claw scroll"
        }, {
            "Praying mantis pouch", "Crimson Charm", "Flowers", "168", "75",
                "Mantis strike scroll"
        }, {
            "Adamant minotaur pouch", "Blue Charm", "Adamant Bar", "144",
                "76", "Inferno scroll"
        }, {
            "Forge Regent pouch", "Green Charm", "Ruby harvest", "141", "76",
                "Adamant bull rush scroll"
        }, {
            "Talon Beast pouch", "Crimson Charm", "Talon Beast charm", "174",
                "77", "Deadly claw scroll"
        }, {
            "Giant ent pouch", "Green Charm", "Willow branch", "124", "78",
                "Acorn missile scroll"
        }, {
            "Fire titan pouch", "Blue Charm", "Fire talisman", "198", "79",
                "Titan's constitution scroll"
        }, {
            "Ice titan pouch", "Blue Charm", "Water talisman", "198", "79",
                "Titan's constitution scroll"
        }, {
            "Moss titan pouch", "Blue Charm", "Earth talisman", "202", "79",
                "Titan's constitution scroll"
        }, {
            "Hydra pouch", "Green Charm", "Water orb", "128", "80",
                "Regrowth scroll"
        }, {
            "Spirit dagannoth", "Crimson Charm", "Dagannoth hide", "1", "83",
                "Spike shot scroll"
        }, {
            "Lava titan pouch", "Blue Charm", "Obsidian Charm", "219", "83",
                "Ebon thunder scroll"
        }, {
            "Swamp titan pouch", "Swamp lizard", "Swamp lizard", "150", "85",
                "Swamp plague scroll"
        }, {
            "Rune minotaur pouch", "Blue Charm", "Rune bar", "1", "86",
                "Rune bull rush scroll"
        }, {
            "Unicorn stallion pouch", "green Charm", "Unicorn Horn", "140",
                "88", "Healing aura scroll"
        }, {
            "Geyser titan pouch", "blue Charm", "Water talisman", "222",
                "89", "Boil scroll"
        }, {
            "Wolpertinger pouch", "crimson Charm", "Raw rabbit", "203", "92",
                "Magic focus scroll"
        }, {
            "Abyssal titan pouch", "green Charm", "Abyssal charm", "113",
                "93", "Essence shipment scroll"
        }, {
            "Iron titna pouch", "crimson Charm", "Iron platebody", "198",
                "95", "Iron within scroll"
        }, {
            "Pack yak pouch", "Crimson Charm", "Yak hide", "211", "96",
                "Winter storage scroll"
        }, {
            "Steel titan pouch", "Blue Charm", "Steel platebody", "178",
                "99", "Steel of legends scroll"
        },

    };

    /**
     * Pouch item id
     */
    private final int POUCH = 12155;
    
    /**
     * Shard item id
     */
    private final int SHARD = 18016;

    /**
     * Checks the requirements for pouch making
     * @param c
     * @param i
     * @return
     */
    public boolean NEED(Client c, int i) {
        try {
            return c.getItems().playerHasItem(POUCH) && c.getItems().playerHasItem(SHARD,Integer.parseInt(summoningPouchData[i][3])) &&
            	c.getItems().playerHasItem(c.getItems().getItemId(summoningPouchData[i][1])) && c.getItems().playerHasItem(c.getItems().getItemId(summoningPouchData[i][2]));
        } catch (ArrayIndexOutOfBoundsException e) {
            return true;
        }
    }
    
    /**
     * Makes the pouch
     * @param c
     * @param buttonId
     */
    public void makeSummoningPouch(Client c, int buttonId) {
        try {
            int i = (buttonId - 155031) / 3;
            if (NEED(c, i)) {
                if (c.playerLevel[23] >= Integer.parseInt(summoningPouchData[i][4])) {
                    c.getItems().deleteItem2(POUCH, 1);
                    c.getItems().deleteItem2(SHARD, Integer.parseInt(summoningPouchData[i][3]));
                    c.getItems().deleteItem2(c.getItems().getItemId(summoningPouchData[i][1]), 1);
                    c.getItems().deleteItem2(c.getItems().getItemId(summoningPouchData[i][2]), 1);
                    c.getItems().addItem(c.getItems().getItemId(summoningPouchData[i][0]), 1);
                    c.getPA().addSkillXP(Integer.parseInt(summoningPouchData[i][4]) * PlayerConfiguration.SUMMONING_EXPERIENCE, 23);
                    c.sendMessage("omar sent");
                } else {
                    c.sendMessage("You need a Summoning level of " + summoningPouchData[i][4] + " ");
                }
            } else {
                c.sendMessage("You need 1 " + summoningPouchData[i][1] + ", 1 " + summoningPouchData[i][2] + ", " + summoningPouchData[i][3] + " shards, and 1 pouch to make this.");
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            return;
        }
    }

    /**
     * Makes the scroll
     * @param c
     * @param pouchUsed
     */
    public void makeSummoningScroll(Client c, int pouchUsed) {
        for (int i = 0; i < summoningPouchData.length; i++) {
            if (pouchUsed == c.getItems().getItemId(summoningPouchData[i][0])) {
                if (c.getItems().playerHasItem( c.getItems().getItemId(summoningPouchData[i][0]), 1) && c.playerLevel[21] >= Integer.parseInt(summoningPouchData[i][4])) {
                    c.getItems().deleteItem2(c.getItems().getItemId( summoningPouchData[i][0]), 1);
                    c.getItems() .addItem(c.getItems().getItemId(summoningPouchData[i][5]), 1);
                } else {
                    c.sendMessage("You need a higher summoning level to make this scroll");
                }
            }
        }
    }
}