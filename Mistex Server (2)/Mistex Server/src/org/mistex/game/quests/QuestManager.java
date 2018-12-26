package org.mistex.game.quests;

import org.mistex.game.world.player.Client;

import java.util.ArrayList;
import java.util.List;

/***
 * @Quest manager
 * @information Deals with the quests globally.
 */
public class QuestManager {


    private ArrayList<Quest> quests = new ArrayList<Quest>();

    private Client client;

    public QuestManager(Client client) {

        this.client = client;
    }


    public void UpdateProgress(int id, int stage) {
        for (Quest quest : quests) {
            if (quest.getId() == id) {
                client.questProgress[id] = stage;
            }
        }
    }

    public void ShowInterface(int id) {
      for (Quest quest : quests) {
          if (quest.getId() == id) {
              quest.onQuestInterfaceClick(client);
          }
      }
    }


}
