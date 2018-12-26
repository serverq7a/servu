package org.mistex.game.world.content.cannon;

import org.mistex.game.world.Position;
import org.mistex.game.world.gameobject.Objects;
import org.mistex.game.world.player.Player;

/**
 * Represents a dwarf multicannon that will be placed in the rs2 world. We
 * extend {@link Objects} so that we can inherit all the traits of a regular
 * Object, plus add our own traits.
 *
 * @author Arham 4
 * @author lare96
 */
public class MultiCannonObject extends Objects {

    /**
     * The player in control of this dwarf multicannon. This field is very
     * important because it ensures that no one else can fire, or pickup this
     * cannon.
     */
    private Player owner;

    /**
     * Construct a new dwarf multicannon
     */
    public MultiCannonObject(int id, Player owner) {
        super(id, new Position(owner.absX, owner.absY, owner.heightLevel), 0, 10, 0);
        this.setOwner(owner);
    }

    /**
     * @return the owner
     */
    public Player getOwner() {
        return owner;
    }

    /**
     * @param owner the owner to set
     */
    public void setOwner(Player owner) {
        this.owner = owner;
    }
}