package org.mistex.game.world.gameobject;

import org.mistex.game.world.Position;

public class Objects {

	public int objectId;
	public static int objectX;
	public static int objectY;
	public boolean bait;
	public String belongsTo;
	public long delay, oDelay;
	public int objectHeight, objectFace, xp, item, owner, target, objectType, objectTicks;
    private Position position;

	public Objects(final int id, final int x, final int y, final int height, final int face, final int type, final int ticks) {
		objectId = id;
		objectX = x;
		objectY = y;
		objectHeight = height;
		objectFace = face;
		objectType = type;
		objectTicks = ticks;
	}
	
    @SuppressWarnings("static-access")
	public Objects(int id, Position position, int face, int type, int ticks) {
        this.objectId = id;
        this.position = position;
        this.objectX = position.getX();
        this.objectY = position.getY();
        this.objectHeight = position.getZ();
        this.objectFace = face;
        this.objectType = type;
        this.objectTicks = ticks;
    }

	public int getObjectFace() {
		return objectFace;
	}

	public int getObjectHeight() {
		return objectHeight;
	}

	public int getObjectId() {
		return objectId;
	}

	public int getObjectType() {
		return objectType;
	}

	public int getObjectX() {
		return objectX;
	}

	public int getObjectY() {
		return objectY;
	}
	
    public Position getPosition() {
        return position;
    }

}
