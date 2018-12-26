package org.mistex.game.quests;

import org.mistex.game.world.player.Client;

/**
 *
 * @author Fuzenseth
 * @information Abstract quest interface
 */
public abstract class Quest {

    
    public abstract String getName();
    
    public abstract int getId();
    
    public abstract void onQuestInterfaceClick(Client client);

    public abstract void OnProgressChange(Client client);

    public abstract void OnFinish(Client client);
    
    
}
