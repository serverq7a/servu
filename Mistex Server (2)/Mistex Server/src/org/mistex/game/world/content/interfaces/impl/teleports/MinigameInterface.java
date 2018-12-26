package org.mistex.game.world.content.interfaces.impl.teleports;

import org.mistex.game.world.content.interfaces.InterfaceText;
import org.mistex.game.world.player.Client;

public class MinigameInterface extends InterfaceText {

    public MinigameInterface(Client player) {
        super(player);
    }

    private final String[] text = {
            "Duel Arena",
            "",
            "Fight Pits",
            "",
            "Champions",
            "Battle",
            "Barrows",
            "",
            "Fight Caves",
            "",
            "Pest Control",
            "",
            "",
            "",
            "Weapon Game",
            "",
            "",
            "",
    };

    @Override
    protected String[] text() {
        return text;
    }

    @Override
    protected int startingLine() {
        return 60662;
    }


}