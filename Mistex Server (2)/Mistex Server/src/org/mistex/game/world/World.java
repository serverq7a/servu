package org.mistex.game.world;

import java.net.InetSocketAddress;
import java.util.ArrayList;

import org.mistex.game.Mistex;
import org.mistex.game.MistexConfiguration;
import org.mistex.game.MistexUtility;
import org.mistex.game.world.npc.NPCHandler;
import org.mistex.game.world.player.Client;
import org.mistex.game.world.player.Player;
import org.mistex.game.world.player.PlayerSave;
import org.mistex.system.RS2Stream;

public class World {
	
    public static Player players[] = new Player[MistexConfiguration.MAX_PLAYERS];
    public static ArrayList<Player> lightweights = new ArrayList<Player>();
    private RS2Stream updateBlock = new RS2Stream(new byte[MistexConfiguration.BUFFER_SIZE]);
    public static String messageToAll = "";
    public static int playerCount = 0;
    public static String playersCurrentlyOn[] = new String[MistexConfiguration.MAX_PLAYERS];
    public static boolean updateAnnounced;
    public static boolean updateRunning;
    public static int updateSeconds;
    public static long updateStartTime;
    private boolean kickAllPlayers = false;

    static {
        for (int i = 0; i < MistexConfiguration.MAX_PLAYERS; i++)
            players[i] = null;
    }

    public static Object lock = new Object();

    private static World playerHandler = new World();

    public static World getPlayerHandler() {
        return playerHandler;
    }
    
	public static final Player getPlayerByName(String username) {
		for (Player player : World.players) {
			if (player == null)
				continue;
			if (player.playerName.equalsIgnoreCase(username))
				return player;
		}
		return null;
	}

    public static Player getPlayer(String name) {
        for (int d = 0; d < MistexConfiguration.MAX_PLAYERS; d++) {
            if (World.players[d] != null) {
                Player p = World.players[d];
                if (p.playerName.equalsIgnoreCase(name)) {
                    return p;
                }
            }
        }
        return null;
    }

    public boolean newPlayerClient(Client client1) {
        int slot = -1;
        for (int i = 1; i < MistexConfiguration.MAX_PLAYERS; i++) {
            if ((players[i] == null) || players[i].disconnected) {
                slot = i;
                break;
            }
        }
        if (slot == -1)
            return false;
        client1.handler = this;
        client1.playerId = slot;
        players[slot] = client1;
        players[slot].isActive = true;
        players[slot].connectedFrom = ((InetSocketAddress) client1.getSession().getRemoteAddress()).getAddress().getHostAddress();
        if (MistexConfiguration.SERVER_DEBUG)
            MistexUtility.println("Player Slot " + slot + " slot 0 " + players[0] + " Player Hit " + players[slot]);
        return true;
    }

    public void destruct() {
        for (int i = 0; i < MistexConfiguration.MAX_PLAYERS; i++) {
            if (players[i] == null)
                continue;
            players[i].destruct();
            players[i] = null;
        }
    }

    public static int getPlayerCount() {
        return playerCount;
    }

    public void updatePlayerNames() {
        playerCount = 0;
        for (int i = 0; i < MistexConfiguration.MAX_PLAYERS; i++) {
            if (players[i] != null) {
                playersCurrentlyOn[i] = players[i].playerName;
                playerCount++;
            } else {
                playersCurrentlyOn[i] = "";
            }
        }
    }

    public static boolean isPlayerOn(String playerName) {
        synchronized(World.players) {
            for (int i = 0; i < MistexConfiguration.MAX_PLAYERS; i++) {
                if (playersCurrentlyOn[i] != null) {
                    if (playersCurrentlyOn[i].equalsIgnoreCase(playerName)) {
                        return true;
                    }
                }
            }
            return false;
        }
    }

    public void process() {
        synchronized(lock) {
            updatePlayerNames();
            if (kickAllPlayers) {
                for (int i = 1; i < MistexConfiguration.MAX_PLAYERS; i++) {
                    if (players[i] != null) {
                        players[i].disconnected = true;
                    }
                }
            }
            for (int i = 0; i < MistexConfiguration.MAX_PLAYERS; i++) {
                if (players[i] == null || !players[i].isActive || !players[i].initialized)
                    continue;
                try {
                    if (players[i].disconnected && (System.currentTimeMillis() - players[i].logoutDelay > 10000 || players[i].properLogout || kickAllPlayers)) {
                        if (players[i].inTrade) {
                            Client o = (Client) World.players[players[i].tradeWith];
                            if (o != null) {
                                o.getTrade().declineTrade();
                            }
                        }
                        if (players[i].duelStatus == 5) {
                            Client o = (Client) World.players[players[i].duelingWith];
                            if (o != null) {
                                o.getDuel().duelVictory();
                            }
                        } else if (players[i].duelStatus <= 4 && players[i].duelStatus >= 1) {
                            Client o = (Client) World.players[players[i].duelingWith];
                            if (o != null) {
                                o.getDuel().declineDuel();
                            }
                        }
                        Client o = (Client) World.players[i];
                        if (PlayerSave.saveGame(o)) {
                            System.out.println("Game saved for player " + players[i].playerName);
                        } else {
                            System.out.println("Could not save for " + players[i].playerName);
                        }
                        removePlayer(players[i]);
                        players[i] = null;
                        continue;
                    }
                    players[i].preProcessing();
                    players[i].processQueuedPackets();
                    players[i].process();
                    players[i].postProcessing();
                    players[i].getNextPlayerMovement();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                
            }

            for (int i = 0; i < MistexConfiguration.MAX_PLAYERS; i++) {
                if (players[i] == null || !players[i].isActive || !players[i].initialized)
                    continue;
                try {
                    players[i].update();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            for (int i = 0; i < MistexConfiguration.MAX_PLAYERS; i++) {
                if (players[i] == null || !players[i].isActive || !players[i].initialized)
                    continue;
                try {
                    players[i].clearUpdateFlags();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            
            if (updateRunning && !updateAnnounced) {
                updateAnnounced = true;
                Mistex.UpdateServer = true;
            }
            if (updateRunning && (System.currentTimeMillis() - updateStartTime > (updateSeconds * 1000))) {
                kickAllPlayers = true;
            }
        }
    }

    public void updateNPC(Player plr, RS2Stream str) {
        updateBlock.currentOffset = 0;
        str.createFrameVarSizeWord(65);
        str.initBitAccess();
        str.writeBits(8, plr.npcListSize);
        int size = plr.npcListSize;
        plr.npcListSize = 0;
        for (int i = 0; i < size; i++) {
            if (plr.RebuildNPCList == false && plr.withinDistance(plr.npcList[i]) == true) {
                plr.npcList[i].updateNPCMovement(str);
                plr.npcList[i].appendNPCUpdateBlock(updateBlock);
                plr.npcList[plr.npcListSize++] = plr.npcList[i];
            } else {
                int id = plr.npcList[i].npcId;
                plr.npcInListBitmap[id >> 3] &= ~(1 << (id & 7));
                str.writeBits(1, 1);
                str.writeBits(2, 3);
            }
        }
        int j = 0;
        for (int i = 0; i < NPCHandler.maxNPCs; i++) {
            if (NPCHandler.npcs[i] != null) {
                int id = NPCHandler.npcs[i].npcId;
                if (j > 5) {
                    break;
                }
                if (plr.RebuildNPCList == false && (plr.npcInListBitmap[id >> 3] & (1 << (id & 7))) != 0) {} else if (plr.withinDistance(NPCHandler.npcs[i]) == false) {

                } else {
                    plr.addNewNPC(NPCHandler.npcs[i], str, updateBlock);
                    j++;
                }
            }
        }
        plr.RebuildNPCList = false;
        if (updateBlock.currentOffset > 0) {
            str.writeBits(14, 16383);
            str.finishBitAccess();
            str.writeBytes(updateBlock.buffer, updateBlock.currentOffset, 0);
        } else {
            str.finishBitAccess();
        }
        str.endFrameVarSizeWord();
    }

    public void updatePlayer(Player plr, RS2Stream str) {
        updateBlock.currentOffset = 0;
        if (updateRunning && !updateAnnounced) {
            str.createFrame(114);
            str.writeWordBigEndian(updateSeconds * 50 / 30);
        }
        plr.updateThisPlayerMovement(str);
        boolean saveChatTextUpdate = plr.isChatTextUpdateRequired();
        plr.setChatTextUpdateRequired(false);
        plr.appendPlayerUpdateBlock(updateBlock);
        plr.setChatTextUpdateRequired(saveChatTextUpdate);
        str.writeBits(8, plr.playerListSize);
        int size = plr.playerListSize;
        plr.playerListSize = 0;
        for (int i = 0; i < size; i++) {
            if (!plr.didTeleport && !plr.playerList[i].didTeleport && plr.withinDistance(plr.playerList[i])) {
                plr.playerList[i].updatePlayerMovement(str);
                plr.playerList[i].appendPlayerUpdateBlock(updateBlock);
                plr.playerList[plr.playerListSize++] = plr.playerList[i];
            } else {
                int id = plr.playerList[i].playerId;
                plr.playerInListBitmap[id >> 3] &= ~(1 << (id & 7));
                str.writeBits(1, 1);
                str.writeBits(2, 3);
            }
        }
        int j = 0;
        for (int i = 0; i < MistexConfiguration.MAX_PLAYERS; i++) {
            // if(updateBlock.currentOffset >= 4000)
            // break;
            if (plr.playerListSize >= 254)
                break;
            if (updateBlock.currentOffset + str.currentOffset >= 30000)
                break;
            if (players[i] == null || !players[i].isActive || players[i] == plr)
                continue;
            int id = players[i].playerId;
            if ((plr.playerInListBitmap[id >> 3] & (1 << (id & 7))) != 0)
                continue;
            if (j > 5)
                break;
            if (!plr.withinDistance(players[i]))
                continue;
            plr.addNewPlayer(players[i], str, updateBlock);
            j++;
        }
        if (updateBlock.currentOffset > 0) {
            str.writeBits(11, 2047);
            str.finishBitAccess();
            str.writeBytes(updateBlock.buffer, updateBlock.currentOffset, 0);
        } else
            str.finishBitAccess();
        str.endFrameVarSizeWord();
    }

    @SuppressWarnings("static-access")
    private void removePlayer(Player plr) {
        if (plr.privateChat != 2) {
            for (int i = 1; i < MistexConfiguration.MAX_PLAYERS; i++) {
                if (players[i] == null || players[i].isActive == false)
                    continue;
                Client o = (Client) Mistex.playerHandler.players[i];
                if (o != null) {
                    o.getPA().updatePM(plr.playerId, 0);
                }
            }
        }
        plr.destruct();
    }

}