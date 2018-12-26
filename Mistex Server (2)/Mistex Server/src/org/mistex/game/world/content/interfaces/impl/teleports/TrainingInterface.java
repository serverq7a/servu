package org.mistex.game.world.content.interfaces.impl.teleports;

import org.mistex.game.world.content.interfaces.InterfaceText;
import org.mistex.game.world.player.Client;

public class TrainingInterface extends InterfaceText {

    public TrainingInterface(Client player) {
        super(player);
    }

    private final String[] text = {
            "Rock Crabs",
            "",
            "Bandits",
            "",
            "Al-kharid",
            "",
            "Hill Giants",
            "",
            "Gorillas",
            "",
            "Slayer Tower",
            "",
            "Brimhaven",
            "Dungeon",
            "Taverly",
            "Dungeon",
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