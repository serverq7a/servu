package org.mistex.game.world.content.highscores;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.mistex.game.MistexConfiguration;
import org.mistex.game.MistexUtility;
import org.mistex.game.world.World;
import org.mistex.game.world.player.Client;
import org.mistex.game.world.player.Player;
import org.mistex.system.RS2Stream;

public class HighScoresBoard {

    private static HighScoresBoard leaderBoard = new HighScoresBoard();

    public static HighScoresBoard getLeaderBoard() {
            return leaderBoard;
    }

    private final int MAX_LEADERS = 10;
    private final int START_LINE = 6402;

    private HighScores[] mostKills;
    private HighScores[] highestKillDeath;
    private HighScores[] mostDeaths;

    public HighScores[] getLeaders(int type) {
            if (type == 0) {
                    return mostKills;
            } else if (type == 1) {
                    return highestKillDeath;
            } else {
                    return mostDeaths;
            }
    }

    public HighScoresBoard() {
            mostKills = new HighScores[MAX_LEADERS];
            highestKillDeath = new HighScores[MAX_LEADERS];
            mostDeaths = new HighScores[MAX_LEADERS];
            for (int i = 0; i < MAX_LEADERS; i++) {
                    mostKills[i] = new HighScores();
                    highestKillDeath[i] = new HighScores();
                    mostDeaths[i] = new HighScores();
            }
            loadLeaders();
    }

    public String DATA_LOCATION = "./Data";

    public void loadLeaders() {
            try {
                    File file = new File("./Data/highscores/Leaders.dat");
                    if (!file.exists()) {
                            return;
                    }
                    FileInputStream input = new FileInputStream(file);
                    if (input.available() == 0) {
                            input.close();
                            return;
                    }
                    RS2Stream stream = new RS2Stream(new byte[input.available()]);
                    input.read(stream.buffer, 0, stream.buffer.length);
                    int length = stream.readUnsignedByte();
                    for (int i = 0; i < length; i++) {
                            String username = stream.readString();
                            int playerKills = stream.readDWord();
                            int playerDeaths = stream.readDWord();

                            double ratio = (double) highestKillDeath[i].getPlayerKills() / (double) highestKillDeath[i].getPlayerDeaths();
                            mostKills[i] = new HighScores(username, playerKills, playerDeaths, (int) ratio);
                    }
                    length = stream.readUnsignedByte();
                    for (int i = 0; i < length; i++) {
                            String username = stream.readString();
                            int playerKills = stream.readDWord();
                            int playerDeaths = stream.readDWord();

                            double ratio = (double) highestKillDeath[i].getPlayerKills() / (double) highestKillDeath[i].getPlayerDeaths();
                            highestKillDeath[i] = new HighScores(username, playerKills, playerDeaths, (int) ratio);
                    }
                    length = stream.readUnsignedByte();
                    for (int i = 0; i < length; i++) {
                            String username = stream.readString();
                            int playerKills = stream.readDWord();
                            int playerDeaths = stream.readDWord();

                            double ratio = (double) highestKillDeath[i].getPlayerKills() / (double) highestKillDeath[i].getPlayerDeaths();
                            mostDeaths[i] = new HighScores(username, playerKills, playerDeaths, (int) ratio);
                    }
                    input.close();
            } catch (Exception ex) {
                    ex.printStackTrace();
            }
    }

    public void saveLeaders() {
            try {
                    FileOutputStream output = new FileOutputStream("./Data/highscores/Leaders.dat");
                    RS2Stream stream = new RS2Stream(new byte[MAX_LEADERS * (4 + 13)]);
                    stream.writeByte(mostKills.length);
                    for (int i = 0; i < mostKills.length; i++) {
                            stream.writeString(mostKills[i].getUsername());
                            stream.writeDWord(mostKills[i].getPlayerKills());
                            stream.writeDWord(mostKills[i].getPlayerDeaths());
                    }
                    stream.writeByte(highestKillDeath.length);
                    for (int i = 0; i < highestKillDeath.length; i++) {
                            stream.writeString(highestKillDeath[i].getUsername());
                            stream.writeDWord(highestKillDeath[i].getPlayerKills());
                            stream.writeDWord(highestKillDeath[i].getPlayerDeaths());
                    }
                    stream.writeByte(mostDeaths.length);
                    for (int i = 0; i < mostDeaths.length; i++) {
                            stream.writeString(mostDeaths[i].getUsername());
                            stream.writeDWord(mostDeaths[i].getPlayerKills());
                            stream.writeDWord(mostDeaths[i].getPlayerDeaths());
                    }
                    output.write(stream.buffer, 0, stream.currentOffset);
                    output.close();
            } catch (Exception ex) {
                    ex.printStackTrace();
            }
    }

    public boolean isLeader(String name) {
          for (int i = 0; i < mostKills.length; i++) {
               if (mostKills[i].getUsername().equalsIgnoreCase(name)) {
                            return true;
                    }
            }
            return false;
    }

    public boolean update = false;

    private void checkLeaders() {
            for (int i = 0; i < MistexConfiguration.MAX_PLAYERS; i++) {
                    Player player = World.players[i];
                    if (player == null || player.playerRights == 2 || player.playerRights == 3) {
                            continue;
                    }
                    for (int x = 0; x < mostKills.length; x++){
                            if (player.KC > mostKills[x].getPlayerKills() && mostKills[x].getUsername().equalsIgnoreCase(player.playerName) || player.DC > mostKills[x].getPlayerDeaths() && mostKills[x].getUsername().equalsIgnoreCase(player.playerName)){



                                    mostKills[x].setPlayerKills(player.KC);
                                    mostKills[x].setPlayerDeaths(player.DC);

                                    break;
                            }
                            else if (!isLeader(player.playerName)) {
                                    if (player.KC > mostKills[0].getPlayerKills() || player.DC > mostKills[0].getPlayerDeaths()) {

                                            mostKills[0] = new HighScores(MistexUtility.formatPlayerName(player.playerName), player.KC, player.DC, player.KDR);
                                            break;
                                    }
                            }
                    }
                    bubbleSort();
            }
    }

    public void displayLeaderBoard(Client player) {
            setOption(player);
            player.getPA().showInterface(6308);
    }

    public void setOption(Client player) {
            checkLeaders();
            int id = START_LINE;
            player.getPA().sendFrame126("@lre@In-Game Highscores", 6399);
            for (int i = mostKills.length - 1; i >= 0; i--) {
                    if (mostKills[i].getPlayerKills() == -1) {
                            player.getPA().sendFrame126("", id++);
                            continue;
                    }
                    double ratio = 0.0;
                    if (mostKills[i].getPlayerDeaths() == 0) {
                            ratio = mostKills[i].getPlayerKills();
                    } else {
                            ratio = (double) mostKills[i].getPlayerKills() / (double) mostKills[i].getPlayerDeaths();
                    }

                    player.getPA().sendFrame126(String.format("@lre@%s) @whi@User: @lre@%s@whi@  |  Kills: @lre@%s@whi@  |  Deaths: @lre@%s@whi@  |  KDR: @lre@%s", (mostKills.length - i), MistexUtility.formatPlayerName(mostKills[i].getUsername()), mostKills[i].getPlayerKills(), mostKills[i].getPlayerDeaths(), player.getPA().round(ratio, 1)), id++);
            }
    }

    private void bubbleSort() {
            boolean changed = false;
            for (int counter = 0; counter < mostKills.length - 1; counter++) {
                    for (int counter2 = 0; counter2 < mostDeaths.length - 1; counter2++) {

                            for (int index = 0; index < mostKills.length - 1 - counter; index++) {
                                    for (int index2 = 0; index2 < mostDeaths.length - 1 - counter2; index2++) {
                                            if (mostKills[index].getPlayerKills() > mostKills[index + 1].getPlayerKills() || mostDeaths[index2].getPlayerDeaths() > mostDeaths[index2 + 1].getPlayerDeaths()) {
                                                    HighScores temp = mostKills[index];
                                                    mostKills[index] = mostKills[index + 1];
                                                    mostKills[index + 1] = temp;

                                                    HighScores temp2 = mostDeaths[index2];
                                                    mostDeaths[index] = mostDeaths[index2 + 1];
                                                    mostDeaths[index + 1] = temp2;
                                                    changed = true;
                                            }
                                    }
                            }
                    }
            }
            if (changed) {
                    saveLeaders();
            }
    }

    public void removeLeader(String username) {
            if (!isLeader(username)) {
                    return;
            }
            boolean changed = false;
            for (HighScores leader: mostKills)
            {
                    if (leader.getUsername().equalsIgnoreCase(username))
                    {
                            leader.resetValues();
                            bubbleSort();
                            changed = true;
                            break;
                    }
            }
            if (changed) {
                    saveLeaders();
            }
    }
}