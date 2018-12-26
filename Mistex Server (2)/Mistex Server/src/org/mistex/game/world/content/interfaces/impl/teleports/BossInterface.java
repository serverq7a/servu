package org.mistex.game.world.content.interfaces.impl.teleports;

import org.mistex.game.world.content.interfaces.InterfaceText;
import org.mistex.game.world.player.Client;

public class BossInterface extends InterfaceText {

    public BossInterface(Client player) {
        super(player);
    }

    private final String[] text = {
            "Godwars",
            "",
            "KBD",
            "",
            "Corporeal",
            "Beast",
            "Nomad",
            "",
            "Tormented",
            "Demons",
            "",
            "",
            "",
            "",
            "",
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