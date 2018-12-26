package org.mistex.game.world.content.skill.summoning;

import org.mistex.game.Mistex;
import org.mistex.game.MistexUtility;
import org.mistex.game.world.npc.NPC;
import org.mistex.game.world.player.Client;

@
SuppressWarnings("static-access")
public class FamiliarSpecials {

    private Client c;

    public FamiliarSpecials(Client Client) {
        this.c = Client;
    }

    public int FRUIT_BAT_TIMER = 60;
    public int UNICORN_TIMER = 60;
    public int WOLf_TIMER = 60;
    public int WOLDPERTINGER_TIMER = 60;
    public int STEEL_TITAN_TIMER = 180;


    public void determineNPCSpecial() {
        switch (c.summoningnpcid) {

        case 6817: //Fruit Bat
            handleFruitBat();
            break;

        case 6823: //Unicorn
            handleUnicorn();
            break;

        case 6830: //Wolf
            handleWolf();
            break;

        case 6870: //Wolpertinger
            handleWolpertinger();
            break;

        case 7344: //Steel Titan
            handleSteelTitan();
            c.sendMessage("sent");
            break;


        }
    }

    public boolean canUseScroll(int scrollId) {
        if (!(c.getItems().playerHasItem(scrollId))) {
            c.sendMessage("You need a " + c.getItems().getItemName(scrollId) + " to do this!");
            return false;
        }
        if (c.specTimer > 0) {
            c.sendMessage("You have used the special recently, please wait " + c.specTimer + " seconds.");
            return false;
        }
        return true;
    }


    public void handleWolpertinger() {
        if (!canUseScroll(12437)) {
            return;
        }
        c.specTimer += WOLDPERTINGER_TIMER;
        c.gfx0(1311);
        c.startAnimation(7660);
        if (c.playerLevel[6] > c.getLevelForXP(c.playerXP[6])) {
            c.playerLevel[6] = c.getLevelForXP(c.playerXP[6]);
        } else {
            c.playerLevel[6] += (c.getLevelForXP(c.playerXP[6]) * .1);
        }
        c.getPA().refreshSkill(6);
        c.sendMessage("Your Magic bonus has increased!");

    }

    public void handleWolf() {
        if (c.specTimer <= 0) {
            if (c.npcIndex > 0 && Mistex.npcHandler.npcs[c.npcIndex] != null) {
                int damage = MistexUtility.random(15) + 5;
                c.startAnimation(7660);
                c.gfx0(1313);
                c.getPA().addSkillXP(1500, 24);
                Mistex.npcHandler.npcs[c.npcIndex].HP -= damage;
                Mistex.npcHandler.npcs[c.npcIndex].hitDiff2 = damage;
                Mistex.npcHandler.npcs[c.npcIndex].hitUpdateRequired = true;
                Mistex.npcHandler.npcs[c.npcIndex].updateRequired = true;
                c.specTimer += 60;
                c.summAmount -= 5;
            } else {
                c.sendMessage("You were unable to use the special because you do not have any scrolls.");
            }


        } else {
            c.sendMessage("You don't have enough special to use this.");
        }
    }

    public void handleFruitBat() {
        final int[] coords = new int[2];
        coords[0] = c.absX + 1;
        coords[1] = c.absY;
        switch (c.lastsummon) {
        case 6817:
            if (c.summAmount >= 5) {
                if (c.specTimer <= 0) {
                    c.startAnimation(7660);
                    c.gfx50(1316);
                    Mistex.itemHandler.createGroundItem(c, 2114, coords[0], coords[1], 1, c.getId());
                    //Mistex.itemHandler.createGroundItem(c, 5972, coordss[0], coordss[1], 1, c.getId());
                    c.specTimer += 20;
                    c.summAmount -= 5;
                } else {
                    c.sendMessage("Your familiar has just used it's special you must wait " + c.specTimer + " seconds.");
                }

            } else {
                c.sendMessage("You don't have enough special to use this.");
            }
        }
    }

    public void handleUnicorn() {
        switch (c.lastsummon) {
        case 6823:
            if (c.specTimer <= 0) {
                if (c.specTimer <= 0) {
                    if (c.playerLevel[3] > c.getLevelForXP(c.playerXP[3])) {
                        c.sendMessage("You cannot heal any higher at this moment.");
                        return;
                    }
                    if (c.inMulti()) {
                        c.startAnimation(7660);
                        c.gfx0(1298);
                        c.playerLevel[3] *= 1.15;
                        c.getPA().refreshSkill(3);
                        NPC opp = Mistex.npcHandler.npcs[c.npcIndex];
                        if (opp != null) {
                            opp.gfx0(1356);
                            opp.forceAnim(8267);
                        }
                        c.getPA().addSkillXP(56, 23);
                        c.getPA().refreshSkill(23);
                        c.specTimer += 60;
                        c.summAmount -= 5;
                    }
                } else {
                    c.sendMessage("Your familiar has just used it's special you must wait " + c.specTimer + " seconds.");
                }

            } else {
                c.sendMessage("You don't have enough special to use this.");
            }
        }
    }

    public void handleSteelTitan() {
            final int damage = MistexUtility.random(30) + 10;
            int damage2 = damage;
            if (c.npcIndex > 0) {
                if (c.summAmount >= 5) {
                    if (c.specTimer <= 0) {
                        Mistex.npcHandler.npcs[c.npcIndex].hitUpdateRequired2 = true;
                        Mistex.npcHandler.npcs[c.npcIndex].updateRequired = true;
                        Mistex.npcHandler.npcs[c.npcIndex].hitDiff2 = damage;
                        Mistex.npcHandler.npcs[c.npcIndex].HP -= damage;
                        c.sendMessage("Your Steel Titan damages your opponent for " + damage2 + " health.");
                        c.startAnimation(1914);
                        NPC opp = Mistex.npcHandler.npcs[c.npcIndex]; //handles the titans gfx on the thing it is attacking
                        if (opp != null)
                            opp.gfx0(1449);
                        c.specTimer += 60;
                        c.summAmount -= 5;
                    } else {
                        c.sendMessage("Your familiar has just used it's special you must wait " + c.specTimer + " seconds.");
                    }
                } else {
                    c.sendMessage("You don't have enough special to use this.");
                }
                if (c.summAmount >= 5) {
                    if (c.specTimer <= 0) {} else if (c.oldPlayerIndex > 0 || c.playerIndex > 0) {
                        Mistex.playerHandler.players[c.playerIndex].playerLevel[3] -= damage;
                        Mistex.playerHandler.players[c.playerIndex].hitDiff2 = damage;
                        Mistex.playerHandler.players[c.playerIndex].hitUpdateRequired2 = true;
                        Mistex.playerHandler.players[c.playerIndex].updateRequired = true;
                        c.sendMessage("Your Steel Titan damages your opponent for " + damage2 + " health.");
                        c.startAnimation(1914);
                        Client opp = (Client) Mistex.playerHandler.players[c.playerIndex]; //handles titans gfx on players
                        if (opp != null)
                            opp.gfx0(1449);
                        c.specTimer += 60;
                        c.summAmount -= 5;
                    } else {
                        c.sendMessage("Your familiar has just used it's special you must wait " + c.specTimer + " seconds.");
                    }
                } else {
                    c.sendMessage("You don't have enough special to use this.");
                }
            }        
    }
}