package org.mistex.game.world.content.interfaces.impl.teleports;

import org.mistex.game.world.content.interfaces.InterfaceText;
import org.mistex.game.world.player.Client;

public class PlayerKillingInterface extends InterfaceText {

    public PlayerKillingInterface(Client player) {
        super(player);
    }

    private final String[] text = {
            "Mage Bank",
            "",
            "Easts",
            "",
            "West",
            "N/A",
            "FunPk",
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