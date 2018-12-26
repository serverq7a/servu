package org.mistex.game.quests.impl;

import org.mistex.game.quests.Quest;
import org.mistex.game.world.player.Client;

public class RecipeForDisaster extends Quest {



    @Override
    public String getName() {
       return "Recipe for Disaster";
    }

    @Override
    public int getId() {
        return 0;
    }

    @Override
    public void onQuestInterfaceClick(Client client) {

    }

    @Override
    public void OnProgressChange(Client client) {
        switch (client.questProgress[getId()]) {

            case 0:

                break;

                case 1:

                    break;
            default:
                //complete
                break;
        }
    }

    @Override
    public void OnFinish(Client client) {

    }
}
