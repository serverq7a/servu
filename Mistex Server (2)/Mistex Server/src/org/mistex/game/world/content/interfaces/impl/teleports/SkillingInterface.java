package org.mistex.game.world.content.interfaces.impl.teleports;

import org.mistex.game.world.content.interfaces.InterfaceText;
import org.mistex.game.world.player.Client;

public class SkillingInterface extends InterfaceText {

    public SkillingInterface(Client player) {
        super(player);
    }

    private final String[] text = {
            "Skilling Zone",
            "",
            "Farming",
            "",
            "Woodcutting",
            "",
            "Fishing",
            "",
            "Mining",
            "",
            "Hunter",
            "",
            "Thieving",
            "",
            "Slayer Tower",
            "",
            "Agility",
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