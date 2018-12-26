package org.mistex.game.world.player.combat;

import org.mistex.game.Mistex;
import org.mistex.game.MistexUtility;
import org.mistex.game.world.event.CycleEvent;
import org.mistex.game.world.event.CycleEventContainer;
import org.mistex.game.world.event.CycleEventHandler;
import org.mistex.game.world.npc.NPCHandler;
import org.mistex.game.world.player.Client;
import org.mistex.game.world.player.Player;
import org.mistex.game.world.player.combat.magic.MagicData;
import org.mistex.game.world.player.combat.magic.MagicExtras;
import org.mistex.game.world.player.combat.magic.MagicMaxHit;
import org.mistex.game.world.player.combat.magic.MagicRequirements;
import org.mistex.game.world.player.combat.melee.MeleeData;
import org.mistex.game.world.player.combat.melee.MeleeExtras;
import org.mistex.game.world.player.combat.melee.MeleeMaxHit;
import org.mistex.game.world.player.combat.melee.MeleeRequirements;
import org.mistex.game.world.player.combat.melee.MeleeSpecial;
import org.mistex.game.world.player.combat.prayers.CombatPrayer;
import org.mistex.game.world.player.combat.range.RangeData;
import org.mistex.game.world.player.combat.range.RangeExtras;
import org.mistex.game.world.player.combat.range.RangeMaxHit;

@SuppressWarnings("static-access")
public class CombatAssistant {

    public Client c;

    public CombatAssistant(Client Client) {
        this.c = Client;
    }

    public int[][] slayerReqs = {
        {
            1648, 5
        }, {
            1612, 15
        }, {
            1643, 45
        }, {
            1618, 50
        }, {
            1624, 65
        }, {
            1610, 75
        }, {
            1613, 80
        }, {
            1615, 85
        }, {
            2783, 90
        }
    };

    public int extraDis(Client o) {
        boolean extra = c.getX() != o.getX() || c.getY() != o.getY();
        if (c.isMoving && o.isMoving) {
            if (c.isRunning2) {
                return extra ? 3 : 2;
            } else {
                return extra ? 2 : 1;
            }
        } else {
            return 0;
        }
    }


    public boolean goodSlayer(int i) {
        for (int j = 0; j < slayerReqs.length; j++) {
            if (slayerReqs[j][0] == NPCHandler.npcs[i].npcType) {
                if (slayerReqs[j][1] > c.playerLevel[Player.playerSlayer]) {
                    c.sendMessage("You need a slayer level of " + slayerReqs[j][1] + " to harm this NPC.");
                    return false;
                }
            }
        }
        return true;
    }

    public boolean kalphite1(int i) {
        switch (NPCHandler.npcs[i].npcType) {
        case 1158:
            return true;
        }
        return false;
    }

    public boolean kalphite2(int i) {
        switch (NPCHandler.npcs[i].npcType) {
        case 1160:
            return true;
        }
        return false;
    }

    public void resetPlayerAttack() {
        MeleeData.resetPlayerAttack(c);
    }

    public int getCombatDifference(int combat1, int combat2) {
        return MeleeRequirements.getCombatDifference(combat1, combat2);
    }

    public int getKillerId(int playerId) {
        return MeleeRequirements.getKillerId(c, playerId);
    }

    public boolean checkReqs() {
        return MeleeRequirements.checkReqs(c);
    }

    public boolean checkMultiBarrageReqs(int i) {
        return MagicExtras.checkMultiBarrageReqs(c, i);
    }

    public int getRequiredDistance() {
        return MeleeRequirements.getRequiredDistance(c);
    }

    public void multiSpellEffectNPC(int npcId, int damage) {
        MagicExtras.multiSpellEffectNPC(c, npcId, damage);
    }

    public boolean checkMultiBarrageReqsNPC(int i) {
        return MagicExtras.checkMultiBarrageReqsNPC(i);
    }

    public void appendMultiBarrageNPC(int npcId, boolean splashed) {
        MagicExtras.appendMultiBarrageNPC(c, npcId, splashed);
    }

    public void attackNpc(int i) {
        AttackNPC.attackNpc(c, i);
    }

    public void delayedHit(final Client c, final int i) {
        AttackNPC.delayedHit(c, i);
    }

    public void applyNpcMeleeDamage(int i, int damageMask, int damage) {
        AttackNPC.applyNpcMeleeDamage(c, i, damageMask, damage);
    }

    public void attackPlayer(int i) {
        AttackPlayer.attackPlayer(c, i);
    }

    public void playerDelayedHit(final Client c, final int i) {
        AttackPlayer.playerDelayedHit(c, i);
    }

    public void applyPlayerMeleeDamage(int i, int damageMask, int damage) {
        AttackPlayer.applyPlayerMeleeDamage(c, i, damageMask, damage);
    }

    public void addNPCHit(int i, Client c) {
        AttackNPC.addNPCHit(i, c);
    }

    public void applyPlayerHit(Client c, final int i) {
        AttackPlayer.applyPlayerHit(c, i);
    }

    public void fireProjectileNpc() {
        RangeData.fireProjectileNpc(c);
    }

    public void fireProjectilePlayer() {
        RangeData.fireProjectilePlayer(c);
    }

    public boolean usingCrystalBow() {
        return c.playerEquipment[c.playerWeapon] >= 4212 && c.playerEquipment[c.playerWeapon] <= 4223;
    }

    public boolean multis() {
        return MagicData.multiSpells(c);
    }

    public void appendMultiBarrage(int playerId, boolean splashed) {
        MagicExtras.appendMultiBarrage(c, playerId, splashed);
    }

    public void multiSpellEffect(int playerId, int damage) {
        MagicExtras.multiSpellEffect(c, playerId, damage);
    }

    public void applySmite(int index, int damage) {
        MeleeExtras.applySmite(c, index, damage);
    }

    public boolean usingDbow() {
        return c.playerEquipment[c.playerWeapon] == 11235;
    }

    public boolean usingHally() {
        return MeleeData.usingHally(c);
    }
 

    public void getPlayerAnimIndex(String weaponName) {
        MeleeData.getPlayerAnimIndex(c, weaponName);
    }

    public int getWepAnim(String weaponName) {
        return MeleeData.getWepAnim(c, weaponName);
    }

    public int getBlockEmote() {
        return MeleeData.getBlockEmote(c);
    }

    public int getAttackDelay(String s) {
        return MeleeData.getAttackDelay(c, s);
    }

    public int getHitDelay(int i, String weaponName) {
        return MeleeData.getHitDelay(c, i, weaponName);
    }

    public int npcDefenceAnim(int i) {
        return MeleeData.npcDefenceAnim(i);
    }

    public int calculateMeleeAttack() {
        return MeleeMaxHit.calculateMeleeAttack(c);
    }

    public int bestMeleeAtk() {
        return MeleeMaxHit.bestMeleeAtk(c);
    }

    public int calculateMeleeMaxHit() {
        return (int) MeleeMaxHit.calculateBaseDamage(c, c.usingSpecial);
    }

    public int calculateMeleeDefence() {
        return MeleeMaxHit.calculateMeleeDefence(c);
    }

    public int bestMeleeDef() {
        return MeleeMaxHit.bestMeleeDef(c);
    }

    public void addCharge() {
        MeleeExtras.addCharge(c);
    }

    public void handleDfs(final Client c) {
        MeleeExtras.handleDragonFireShield(c);
    }

    public void handleDfsNPC(final Client c) {
        MeleeExtras.handleDragonFireShieldNPC(c);
    }

    public void appendVengeanceNPC(int otherPlayer, int damage) {
        MeleeExtras.appendVengeanceNPC(c, otherPlayer, damage);
    }

    public void appendVengeance(int otherPlayer, int damage) {
        MeleeExtras.appendVengeance(c, otherPlayer, damage);
    }

    public void applyRecoilNPC(int damage, int i) {
        MeleeExtras.applyRecoilNPC(c, damage, i);
    }

    public void applyRecoil(int damage, int i) {
        MeleeExtras.applyRecoil(c, damage, i);
    }

    public void removeRecoil(Client c) {
        MeleeExtras.removeRecoil(c);
    }

    public void handleGmaulPlayer() {
        MeleeExtras.graniteMaulSpecial(c);
    }

    public void activateSpecial(int weapon, int i) {
        MeleeSpecial.activateSpecial(c, weapon, i);
    }

    public boolean checkSpecAmount(int weapon) {
        return MeleeSpecial.checkSpecAmount(c, weapon);
    }

    public int calculateRangeAttack() {
        return RangeMaxHit.calculateRangeAttack(c);
    }

    public int calculateRangeDefence() {
        return RangeMaxHit.calculateRangeDefence(c);
    }

    public int rangeMaxHit() {
        return RangeMaxHit.maxHit(c);
    }

    public int getRangeStr(int i) {
        return RangeData.getRangeStr(i);
    }

    public int getRangeStartGFX() {
        return RangeData.getRangeStartGFX(c);
    }

    public int getRangeProjectileGFX() {
        return RangeData.getRangeProjectileGFX(c);
    }

    public int correctBowAndArrows() {
        return RangeData.correctBowAndArrows(c);
    }

    public int getProjectileShowDelay() {
        return RangeData.getProjectileShowDelay(c);
    }

    public int getProjectileSpeed() {
        return RangeData.getProjectileSpeed(c);
    }

    public void crossbowSpecial(Client c, int i) {
        RangeExtras.crossbowSpecial(c, i);
    }

    public void appendMutliChinchompa(int npcId) {
        RangeExtras.appendMutliChinchompa(c, npcId);
    }

    public boolean properBolts() {
        return usingBolts(c.playerEquipment[c.playerArrows]);
    }

    public boolean usingBolts(int i) {
        return (i >= 9140 && i <= 9145) || (i >= 9236 && i <= 9245);
    }

    public int mageAtk() {
        return MagicMaxHit.mageAttack(c);
    }

    public int mageDef() {
        return MagicMaxHit.mageDefefence(c);
    }

    public int magicMaxHit() {
        return MagicMaxHit.magiMaxHit(c);
    }

    public boolean wearingStaff(int runeId) {
        return MagicRequirements.wearingStaff(c, runeId);
    }

    public boolean checkMagicReqs(int spell) {
        return MagicRequirements.checkMagicReqs(c, spell);
    }

    public int getMagicGraphic(Client c, int i) {
        return MagicData.getMagicGraphic(c, i);
    }

    public int getFreezeTime() {
        return MagicData.getFreezeTime(c);
    }

    public int getStartHeight() {
        return MagicData.getStartHeight(c);
    }

    public int getEndHeight() {
        return MagicData.getEndHeight(c);
    }

    public int getStartDelay() {
        return MagicData.getStartDelay(c);
    }

    public int getStaffNeeded() {
        return MagicData.getStaffNeeded(c);
    }

    public boolean godSpells() {
        return MagicData.godSpells(c);
    }

    public int getEndGfxHeight() {
        return MagicData.getEndGfxHeight(c);
    }

    public int getStartGfxHeight() {
        return MagicData.getStartGfxHeight(c);
    }

    public void handlePrayerDrain() {
        CombatPrayer.handlePrayerDrain(c);
    }

    public void reducePrayerLevel() {
        CombatPrayer.reducePrayerLevel(c);
    }

    public void resetPrayers() {
        CombatPrayer.resetPrayers(c);
    }

    public void activatePrayer(int i) {
        CombatPrayer.activatePrayer(c, i);
    }
    
    public void getKorasiSpecial(int i) {
    	CombatFormulas.korasiSpecial(c, i);
    }
    
    public void applynpcSoulSplit(int index, int damage) {
        if (!c.curseActive[18])
            return;
        if (c.oldNpcIndex > 0) {
            if (NPCHandler.npcs[c.oldNpcIndex] != null) {
                if (damage == 0) {
                    c.sendMessage("test");
                    return;
                }
                if (c.curseActive[18] && !c.prayerActive[23] && (c.playerLevel[3] <= 99)) {
                    int heal = 2;
                    if (c.playerLevel[3] + heal >= c.getPA().getLevelForXP(c.playerXP[3])) {
                        c.playerLevel[3] = c.getPA().getLevelForXP(c.playerXP[3]);
                    } else {
                        c.playerLevel[3] += heal;
                    }
                    c.getPA().refreshSkill(3);
                }
                final int pX = c.getX();
                final int pY = c.getY();
                final int nX = NPCHandler.npcs[c.oldNpcIndex].getX();
                final int nY = NPCHandler.npcs[c.oldNpcIndex].getY();
                final int offX = (pY - nY) * -1;
                final int offY = (pX - nX) * -1;
                c.getPA().createPlayersProjectile2(pX, pY, offX, offY, 50, 50, 2263, 9, 9, c.oldNpcIndex + 1, 24, 0);
                c.soulSplitDelay = 4;
                CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {
					public void execute(CycleEventContainer s) {
                        if (c.soulSplitDelay > 0) {
                            c.soulSplitDelay--;
                        }
                        //if (c.soulSplitDelay == 3) {
                           // NPCHandler.npcs[c.oldNpcIndex].gfx0(2264); // 1738
                      //  }
                        if (c.soulSplitDelay == 2) {
                            int offX2 = (nY - pY) * -1;
                            int offY2 = (nX - pX) * -1;
                            c.getPA().createPlayersProjectile(nX, nY, offX2, offY2, 50, 45, 2263, 31, 31, -c.playerId - 1, 0);
                        }
                        if (c.soulSplitDelay == 0) {
                            s.stop();
                        }
                    }

					@Override
					public void stop() {
						// TODO Auto-generated method stub
						
					}
                }, 1);
            }
        }
    }
	public void applySoulSplit(int index, int damage) {
        if (!c.curseActive[18])
            return;
        if (Mistex.playerHandler.players[index] != null) {
            final Client c2 = (Client) Mistex.playerHandler.players[index];
            final int pX = c.getX();
            final int pY = c.getY();
            final int oX = c2.getX();
            final int oY = c2.getY();
            int offX = (pY - oY) * -1;
            int offY = (pX - oX) * -1;
            if (c.curseActive[18] && !c.prayerActive[23] && (c.playerLevel[3] <= 99)) {
                int heal = 2;
                if (c2.playerLevel[5] <= 0) {
                    c2.playerLevel[5] = 0;
                    c2.getCombat().resetPrayers();
                }
                if (c.playerLevel[3] + heal >= c.getPA().getLevelForXP(c.playerXP[3])) {
                    c.playerLevel[3] = c.getPA().getLevelForXP(c.playerXP[3]);
                } else {
                    c.playerLevel[3] += heal;
                    c2.playerLevel[5] -= 1;
                }
                c.getPA().refreshSkill(3);
                c2.getPA().refreshSkill(5);
            }
            c.getPA().createPlayersProjectile(pX, pY, offX, offY, 50, 45, 2263, 31, 31, -c.oldPlayerIndex - 1, 0);
            c.soulSplitDelay = 4;
            CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {
				public void execute(CycleEventContainer s) {
                    if (c.soulSplitDelay > 0) {
                        c.soulSplitDelay--;
                    }
                    if (c.soulSplitDelay == 3) {
                        c2.gfx0(2264);
                    }
                    if (c.soulSplitDelay == 2) {
                        int offX2 = (oY - pY) * -1;
                        int offY2 = (oX - pX) * -1;
                        c.getPA().createPlayersProjectile(oX, oY, offX2, offY2, 50, 45, 2263, 31, 31, -c.playerId - 1, 0);
                    }
                    if (c.soulSplitDelay == 0) {
                        s.stop();
                    }
                }

				@Override
				public void stop() {
					// TODO Auto-generated method stub
					
				}
            }, 1);
        }
    }
    public void deflectDamage(int damage) {
        int damage2 = 0;
        if (damage < 10)
            damage2 = 0;
        else
            damage2 = damage / 10;
        c.dealDamage(damage2);
    }



    public void applyLeeches(int index) {
        if (MistexUtility.random(20) == 0) {
            leechAttack(index);
        }
        if (MistexUtility.random(20) == 0) {
            leechDefence(index);
        }
        if (MistexUtility.random(20) == 0) {
            leechStrength(index);
        }
        if (MistexUtility.random(20) == 0) {
            leechSpecial(index);
        }
        if (MistexUtility.random(20) == 0) {
            leechRanged(index);
        }
        if (MistexUtility.random(20) == 0) {
            leechMagic(index);
        }
        if (MistexUtility.random(20) == 0) {
            leechEnergy(index);
        }
    }

    public void applynpcLeeches(int index) {
        if (MistexUtility.random(20) == 0) {
            npcleechAttack(index);
        }
        if (MistexUtility.random(20) == 0) {
            npcleechDefence(index);
        }
        if (MistexUtility.random(20) == 0) {
            npcleechStrength(index);
        }
        if (MistexUtility.random(20) == 0) {
            npcleechSpecial(index);
        }
        if (MistexUtility.random(20) == 0) {
            npcleechRanged(index);
        }
        if (MistexUtility.random(20) == 0) {
            npcleechMagic(index);
        }
        if (MistexUtility.random(20) == 0) {
            npcleechEnergy(index);
        }
    }

    public void leechAttack(int index) {
        if (!c.curseActive[10])
            return;
        if (Mistex.playerHandler.players[index] != null) {
            final Client c2 = (Client) Mistex.playerHandler.players[index];
            final int pX = c.getX();
            final int pY = c.getY();
            final int oX = c2.getX();
            final int oY = c2.getY();
            int offX = (pY - oY) * -1;
            int offY = (pX - oX) * -1;
            c.sendMessage("You leech your opponent's attack.");
            c.startAnimation(12575);
            c.getPA().createPlayersProjectile(pX, pY, offX, offY, 50, 45, 2231, 43, 31, -c.oldPlayerIndex - 1, 1);
            c.leechAttackDelay = 2;
            CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {
					public void execute(CycleEventContainer s) {
                    if (c.leechAttackDelay > 0) {
                        c.leechAttackDelay--;
                    }
                    if (c.leechAttackDelay == 1) {
                        c2.gfx0(2232);
                        if (c.attackMultiplier < 1.10) {
                            c.attackMultiplier += 0.01;
                        }
                        if (c2.attackMultiplier > 0.80) {
                            c2.attackMultiplier -= 0.01;
                        }
                    }
                    if (c.leechAttackDelay == 0) {
                        s.stop();
                    }
                }

					@Override
					public void stop() {
						// TODO Auto-generated method stub
						
					}
            }, 1);
        }
    }

    public void leechRanged(int index) {
        if (!c.curseActive[11])
            return;
        if (Mistex.playerHandler.players[index] != null) {
            final Client c2 = (Client) Mistex.playerHandler.players[index];
            final int pX = c.getX();
            final int pY = c.getY();
            final int oX = c2.getX();
            final int oY = c2.getY();
            int offX = (pY - oY) * -1;
            int offY = (pX - oX) * -1;
            c.sendMessage("You leech your opponent's range.");
            c.startAnimation(12575);
            c.getPA().createPlayersProjectile(pX, pY, offX, offY, 50, 45, 2236, 43, 31, -c.oldPlayerIndex - 1, 0);
            c.leechRangedDelay = 2;
            CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {
					public void execute(CycleEventContainer s) {
                    if (c.leechRangedDelay > 0) {
                        c.leechRangedDelay--;
                    }
                    if (c.leechRangedDelay == 1) {
                        c2.gfx0(2238);
                        if (c.rangedMultiplier < 1.10) {
                            c.rangedMultiplier += 0.01;
                        }
                        if (c2.rangedMultiplier > 0.80) {
                            c2.rangedMultiplier -= 0.01;
                        }
                    }
                    if (c.leechRangedDelay == 0) {
                        s.stop();
                    }
                }

					@Override
					public void stop() {
						// TODO Auto-generated method stub
						
					}
            }, 500);
        }
    }

    public void leechMagic(int index) {
        if (!c.curseActive[12])
            return;
        if (Mistex.playerHandler.players[index] != null) {
            final Client c2 = (Client) Mistex.playerHandler.players[index];
            final int pX = c.getX();
            final int pY = c.getY();
            final int oX = c2.getX();
            final int oY = c2.getY();
            int offX = (pY - oY) * -1;
            int offY = (pX - oX) * -1;
            c.sendMessage("You leech your opponent's magic.");
            c.startAnimation(12575);
            c.getPA().createPlayersProjectile(pX, pY, offX, offY, 50, 45, 2240, 43, 31, -c.oldPlayerIndex - 1, 2);
            c.leechMagicDelay = 2;
            CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {
					public void execute(CycleEventContainer s) {
                    if (c.leechMagicDelay > 0) {
                        c.leechMagicDelay--;
                    }
                    if (c.leechMagicDelay == 1) {
                        c2.gfx0(2242);
                        if (c.magicMultiplier < 1.10) {
                            c.magicMultiplier += 0.01;
                        }
                        if (c2.magicMultiplier > 0.80) {
                            c2.magicMultiplier -= 0.01;
                        }
                    }
                    if (c.leechMagicDelay == 0) {
                        s.stop();
                    }
                }

					@Override
					public void stop() {
						// TODO Auto-generated method stub
						
					}
            }, 1);
        }
    }

    public void leechDefence(int index) {
        if (!c.curseActive[13])
            return;
        if (Mistex.playerHandler.players[index] != null) {
            final Client c2 = (Client) Mistex.playerHandler.players[index];
            final int pX = c.getX();
            final int pY = c.getY();
            final int oX = c2.getX();
            final int oY = c2.getY();
            int offX = (pY - oY) * -1;
            int offY = (pX - oX) * -1;
            c.sendMessage("You leech your opponent's defence.");
            c.startAnimation(12575);
            c.getPA().createPlayersProjectile(pX, pY, offX, offY, 50, 45, 2244, 43, 31, -c.oldPlayerIndex - 1, 3);
            c.leechDefenceDelay = 2;
            CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {
					public void execute(CycleEventContainer s) {
                    if (c.leechDefenceDelay > 0) {
                        c.leechDefenceDelay--;
                    }
                    if (c.leechDefenceDelay == 1) {
                        c2.gfx0(2246);
                        if (c.defenceMultiplier < 1.10) {
                            c.defenceMultiplier += 0.01;
                        }
                        if (c2.defenceMultiplier > 0.80) {
                            c2.defenceMultiplier -= 0.01;
                        }
                    }
                    if (c.leechDefenceDelay == 0) {
                        s.stop();
                    }
                }

					@Override
					public void stop() {
						// TODO Auto-generated method stub
						
					}
            }, 1);
        }
    }

    public void leechStrength(int index) {
        if (!c.curseActive[14])
            return;
        if (Mistex.playerHandler.players[index] != null) {
            final Client c2 = (Client) Mistex.playerHandler.players[index];
            final int pX = c.getX();
            final int pY = c.getY();
            final int oX = c2.getX();
            final int oY = c2.getY();
            int offX = (pY - oY) * -1;
            int offY = (pX - oX) * -1;
            c.sendMessage("You leech your opponent's strength.");
            c.startAnimation(12575);
            c.getPA().createPlayersProjectile(pX, pY, offX, offY, 50, 45, 2248, 43, 31, -c.oldPlayerIndex - 1, 4);
            c.leechStrengthDelay = 2;
            CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {
					public void execute(CycleEventContainer s) {
                    if (c.leechStrengthDelay > 0) {
                        c.leechStrengthDelay--;
                    }
                    if (c.leechStrengthDelay == 1) {
                        c2.gfx0(2250);
                        if (c.strengthMultiplier < 1.10) {
                            c.strengthMultiplier += 0.01;
                        }
                        if (c2.strengthMultiplier > 0.80) {
                            c2.strengthMultiplier -= 0.01;
                        }
                    }
                    if (c.leechStrengthDelay == 0) {
                        s.stop();
                    }
                }

					@Override
					public void stop() {
						// TODO Auto-generated method stub
						
					}
            }, 1);
        }
    }

    public void leechEnergy(int index) {
        if (!c.curseActive[15])
            return;
        if (Mistex.playerHandler.players[index] != null) {
            final Client c2 = (Client) Mistex.playerHandler.players[index];
            final int pX = c.getX();
            final int pY = c.getY();
            final int oX = c2.getX();
            final int oY = c2.getY();
            int offX = (pY - oY) * -1;
            int offY = (pX - oX) * -1;
            c.sendMessage("You leech your opponent's run energy.");
            c.startAnimation(12575);
            c.getPA().createPlayersProjectile(pX, pY, offX, offY, 50, 45, 2252, 43, 31, -c.oldPlayerIndex - 1, 5);
            c.leechEnergyDelay = 2;
            CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {
					public void execute(CycleEventContainer s) {
                    if (c.leechEnergyDelay > 0) {
                        c.leechEnergyDelay--;
                    }
                    if (c.leechEnergyDelay == 1) {
                        c2.gfx0(2254);
                    }
                    if (c.leechEnergyDelay == 0) {
                        s.stop();
                    }
                }

					@Override
					public void stop() {
						// TODO Auto-generated method stub
						
					}
            }, 1);
        }
    }

    public void leechSpecial(int index) {
        if (!c.curseActive[16])
            return;
        if (Mistex.playerHandler.players[index] != null) {
            final Client c2 = (Client) Mistex.playerHandler.players[index];
            final int pX = c.getX();
            final int pY = c.getY();
            final int oX = c2.getX();
            final int oY = c2.getY();
            int offX = (pY - oY) * -1;
            int offY = (pX - oX) * -1;
            c.sendMessage("You leech your opponent's special attack.");
            c.startAnimation(12575);
            c.getPA().createPlayersProjectile(pX, pY, offX, offY, 50, 45, 2256, 43, 31, -c.oldPlayerIndex - 1, 6);
            c.leechSpecialDelay = 2;
            CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {
					public void execute(CycleEventContainer s) {
                    if (c.leechSpecialDelay > 0) {
                        c.leechSpecialDelay--;
                    }
                    if (c.leechSpecialDelay == 1) {
                        c2.gfx0(2258);
                        if (c.specAmount >= 10)
                            return;
                        if (c2.specAmount <= 0)
                            return;
                        c.specAmount += 1;
                        c2.specAmount -= 1;
                        c2.sendMessage("Your special attack has been drained.");
                    }
                    if (c.leechSpecialDelay == 0) {
                        s.stop();
                    }
                }

					@Override
					public void stop() {
						// TODO Auto-generated method stub
						
					}
            }, 1);
        }
    }

    public void npcleechAttack(int index) {
        if (!c.curseActive[10])
            return;
        if (c.oldNpcIndex > 0) {
            if (NPCHandler.npcs[c.oldNpcIndex] != null) {
                final int pX = c.getX();
                final int pY = c.getY();
                final int nX = NPCHandler.npcs[c.oldNpcIndex].getX();
                final int nY = NPCHandler.npcs[c.oldNpcIndex].getY();
                final int offX = (pY - nY) * -1;
                final int offY = (pX - nX) * -1;
                c.sendMessage("You leech your opponent's attack.");
                c.startAnimation(12575);
                c.getPA().createPlayersProjectile(pX, pY, offX, offY, 50, 45, 2231, 43, 31, -c.oldNpcIndex - 1, 1);
                c.leechAttackDelay = 2;
                CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {
 					public void execute(CycleEventContainer s) {
                        if (c.leechAttackDelay > 0) {
                            c.leechAttackDelay--;
                        }
                        if (c.leechAttackDelay == 1) {
                            //NPCHandler.npcs[c.oldNpcIndex].gfx0(2232);
                            if (c.attackMultiplier < 1.10) {
                                c.attackMultiplier += 0.01;
                            }
                        }
                        if (c.leechAttackDelay == 0) {
                            s.stop();
                        }
                    }

					@Override
					public void stop() {
						// TODO Auto-generated method stub
						
					}
                }, 1);
            }
        }
    }

    public void npcleechRanged(int index) {
        if (!c.curseActive[11])
            return;
        if (c.oldNpcIndex > 0) {
            if (NPCHandler.npcs[c.oldNpcIndex] != null) {
                final int pX = c.getX();
                final int pY = c.getY();
                final int nX = NPCHandler.npcs[c.oldNpcIndex].getX();
                final int nY = NPCHandler.npcs[c.oldNpcIndex].getY();
                final int offX = (pY - nY) * -1;
                final int offY = (pX - nX) * -1;
                c.sendMessage("You leech your opponent's range.");
                c.startAnimation(12575);
                c.getPA().createPlayersProjectile(pX, pY, offX, offY, 50, 45, 2236, 43, 31, -c.oldNpcIndex - 1, 0);
                c.leechRangedDelay = 2;
                CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {
 					public void execute(CycleEventContainer s) {
                        if (c.leechRangedDelay > 0) {
                            c.leechRangedDelay--;
                        }
                        if (c.leechRangedDelay == 1) {
                            //NPCHandler.npcs[c.oldNpcIndex].gfx0(2238);
                            if (c.rangedMultiplier < 1.10) {
                                c.rangedMultiplier += 0.01;
                            }
                        }
                        if (c.leechRangedDelay == 0) {
                            s.stop();
                        }
                    }

					@Override
					public void stop() {
						// TODO Auto-generated method stub
						
					}
                }, 1);
            }
        }
    }

    public void npcleechMagic(int index) {
        if (!c.curseActive[12])
            return;
        if (c.oldNpcIndex > 0) {
            if (NPCHandler.npcs[c.oldNpcIndex] != null) {
                final int pX = c.getX();
                final int pY = c.getY();
                final int nX = NPCHandler.npcs[c.oldNpcIndex].getX();
                final int nY = NPCHandler.npcs[c.oldNpcIndex].getY();
                final int offX = (pY - nY) * -1;
                final int offY = (pX - nX) * -1;
                c.sendMessage("You leech your opponent's magic.");
                c.startAnimation(12575);
                c.getPA().createPlayersProjectile(pX, pY, offX, offY, 50, 45, 2240, 43, 31, -c.oldNpcIndex - 1, 2);
                c.leechMagicDelay = 2;
                CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {
 					public void execute(CycleEventContainer s) {
                        if (c.leechMagicDelay > 0) {
                            c.leechMagicDelay--;
                        }
                        if (c.leechMagicDelay == 1) {
                            //NPCHandler.npcs[c.oldNpcIndex].gfx0(2242);
                            if (c.magicMultiplier < 1.10) {
                                c.magicMultiplier += 0.01;
                            }
                        }
                        if (c.leechMagicDelay == 0) {
                            s.stop();
                        }
                    }

					@Override
					public void stop() {
						// TODO Auto-generated method stub
						
					}
                }, 1);
            }
        }
    }

    public void npcleechDefence(int index) {
        if (!c.curseActive[13])
            return;
        if (c.oldNpcIndex > 0) {
            if (NPCHandler.npcs[c.oldNpcIndex] != null) {
                final int pX = c.getX();
                final int pY = c.getY();
                final int nX = NPCHandler.npcs[c.oldNpcIndex].getX();
                final int nY = NPCHandler.npcs[c.oldNpcIndex].getY();
                final int offX = (pY - nY) * -1;
                final int offY = (pX - nX) * -1;
                c.sendMessage("You leech your opponent's defence.");
                c.startAnimation(12575);
                c.getPA().createPlayersProjectile(pX, pY, offX, offY, 50, 45, 2244, 43, 31, -c.oldNpcIndex - 1, 3);
                c.leechDefenceDelay = 2;
                CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {
 					public void execute(CycleEventContainer s) {
                        if (c.leechDefenceDelay > 0) {
                            c.leechDefenceDelay--;
                        }
                        if (c.leechDefenceDelay == 1) {
                            //NPCHandler.npcs[c.oldNpcIndex].gfx0(2246);
                            if (c.defenceMultiplier < 1.10) {
                                c.defenceMultiplier += 0.01;
                            }
                        }
                        if (c.leechDefenceDelay == 0) {
                            s.stop();
                        }
                    }

					@Override
					public void stop() {
						// TODO Auto-generated method stub
						
					}
                }, 1);
            }
        }
    }

    public void npcleechStrength(int index) {
        if (!c.curseActive[14])
            return;
        if (c.oldNpcIndex > 0) {
            if (NPCHandler.npcs[c.oldNpcIndex] != null) {
                final int pX = c.getX();
                final int pY = c.getY();
                final int nX = NPCHandler.npcs[c.oldNpcIndex].getX();
                final int nY = NPCHandler.npcs[c.oldNpcIndex].getY();
                final int offX = (pY - nY) * -1;
                final int offY = (pX - nX) * -1;
                c.sendMessage("You leech your opponent's strength.");
                c.startAnimation(12575);
                c.getPA().createPlayersProjectile(pX, pY, offX, offY, 50, 45, 2248, 43, 31, -c.oldNpcIndex - 1, 4);
                c.leechStrengthDelay = 2;
                CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {
 					public void execute(CycleEventContainer s) {
                        if (c.leechStrengthDelay > 0) {
                            c.leechStrengthDelay--;
                        }
                        if (c.leechStrengthDelay == 1) {
                            //NPCHandler.npcs[c.oldNpcIndex].gfx0(2250);
                            if (c.strengthMultiplier < 1.10) {
                                c.strengthMultiplier += 0.01;
                            }
                        }
                        if (c.leechStrengthDelay == 0) {
                            s.stop();
                        }
                    }

					@Override
					public void stop() {
						// TODO Auto-generated method stub
						
					}
                }, 1);
            }
        }
    }

    public void npcleechEnergy(int index) {
        if (!c.curseActive[15])
            return;
        if (c.oldNpcIndex > 0) {
            if (NPCHandler.npcs[c.oldNpcIndex] != null) {
                final int pX = c.getX();
                final int pY = c.getY();
                final int nX = NPCHandler.npcs[c.oldNpcIndex].getX();
                final int nY = NPCHandler.npcs[c.oldNpcIndex].getY();
                final int offX = (pY - nY) * -1;
                final int offY = (pX - nX) * -1;
                c.sendMessage("You leech your opponent's run energy.");
                c.startAnimation(12575);
                c.getPA().createPlayersProjectile(pX, pY, offX, offY, 50, 45, 2252, 43, 31, -c.oldNpcIndex - 1, 5);
                c.leechEnergyDelay = 2;
                CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {
 					public void execute(CycleEventContainer s) {
                        if (c.leechEnergyDelay > 0) {
                            c.leechEnergyDelay--;
                        }
                        if (c.leechEnergyDelay == 1) {
                            //NPCHandler.npcs[c.oldNpcIndex].gfx0(2254);
                        }
                        if (c.leechEnergyDelay == 0) {
                            s.stop();
                        }
                    }

					@Override
					public void stop() {
						// TODO Auto-generated method stub
						
					}
                }, 1);
            }
        }
    }

    public void npcleechSpecial(int index) {
        if (!c.curseActive[16])
            return;
        if (c.oldNpcIndex > 0) {
            if (NPCHandler.npcs[c.oldNpcIndex] != null) {
                final int pX = c.getX();
                final int pY = c.getY();
                final int nX = NPCHandler.npcs[c.oldNpcIndex].getX();
                final int nY = NPCHandler.npcs[c.oldNpcIndex].getY();
                final int offX = (pY - nY) * -1;
                final int offY = (pX - nX) * -1;
                c.sendMessage("You leech your opponent's special attack.");
                c.startAnimation(12575);
                c.getPA().createPlayersProjectile(pX, pY, offX, offY, 50, 45, 2256, 43, 31, -c.oldNpcIndex - 1, 6);
                c.leechSpecialDelay = 2;
                CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {
 					public void execute(CycleEventContainer s) {
                        if (c.leechSpecialDelay > 0) {
                            c.leechSpecialDelay--;
                        }
                        if (c.leechSpecialDelay == 1) {
                            //NPCHandler.npcs[c.oldNpcIndex].gfx0(2258);
                            if (c.specAmount >= 10)
                                return;
                            c.specAmount += 1;
                        }
                        if (c.leechSpecialDelay == 0) {
                            s.stop();
                        }
                    }

					@Override
					public void stop() {
						// TODO Auto-generated method stub
						
					}
                }, 1);
            }
        }
    }
 
}