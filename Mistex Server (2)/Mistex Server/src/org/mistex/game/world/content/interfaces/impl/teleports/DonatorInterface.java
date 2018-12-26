package org.mistex.game.world.content.interfaces.impl.teleports;

import org.mistex.game.world.content.interfaces.InterfaceText;
import org.mistex.game.world.player.Client;

public class DonatorInterface extends InterfaceText {

    public DonatorInterface(Client player) {
        super(player);
    }

    private final String[] text = {
            "Donator Zone",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
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