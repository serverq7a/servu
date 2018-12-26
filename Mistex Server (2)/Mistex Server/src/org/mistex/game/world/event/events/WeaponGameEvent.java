package org.mistex.game.world.event.events;

import org.mistex.game.world.content.minigame.weapongame.WeaponGame;
import org.mistex.game.world.event.CycleEvent;
import org.mistex.game.world.event.CycleEventContainer;
import org.mistex.game.world.player.Client;

public class WeaponGameEvent extends CycleEvent {

    private final Client c;

    public WeaponGameEvent(final Client c) {
        this.c = c;
    }


    @
    Override
    public void execute(CycleEventContainer container) {
        WeaponGame.update();
        if (!WeaponGame.gameStarted) {
            if (WeaponGame.gameStartTimer > 0) {
                WeaponGame.gameStartTimer--;
            } else if (WeaponGame.gameStartTimer == 0) {
                    if (WeaponGame.getListCount(WeaponGame.WAITING) > 1) {
                        if (WeaponGame.getListCount(WeaponGame.PLAYING) == 0 && WeaponGame.getListCount(WeaponGame.WAITING) > 1) {
                            WeaponGame.beginGame();
                        } else {
                            WeaponGame.cantStart();
                        }
                    }
                    WeaponGame.gameStartTimer = 500;
                }
            }
        if (c.hasWeaponGameWeapon && !c.inWeaponGame() && c.getPA().hasItem()) {
            c.getItems().deleteAllItems();
            c.hasWeaponGameWeapon = false;
        }

    }

    @
    Override
    public void stop() {}

}