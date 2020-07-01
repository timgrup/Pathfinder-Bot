package de.vitbund.vitmaze.players;

public class Vector2 {

	enum Direction {
		NORTH, EAST, SOUTH, WEST;
	}

	public static final Vector2 vectorNorth = new Vector2(0, 1);
	public static final Vector2 vectorEast = new Vector2(1, 0);
	public static final Vector2 vectorSouth = new Vector2(0, -1);
	public static final Vector2 vectorWest = new Vector2(-1, 0);

	public int x, y;

	public Vector2(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public String toString() {
		return this.x + ", " + this.y;
	}

	public Vector2 AddUp(Vector2 vector) {
		this.x += vector.x;
		this.y += vector.y;
		return this;
	}

	public static Vector2 directionToVector(Direction direction) {
		switch (direction) {
		case NORTH:
			return vectorNorth;
		case EAST:
			return vectorEast;
		case SOUTH:
			return vectorSouth;
		case WEST:
			return vectorWest;
		default:
			return null;
		}
	}

}
