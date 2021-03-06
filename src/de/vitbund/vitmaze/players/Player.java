package de.vitbund.vitmaze.players;

import de.vitbund.vitmaze.players.Vector2.Direction;

public class Player {

	// Data
	private int id;
	private Vector2 startPos;
	private Vector2 position;
	private Direction lastMove;

	public void move(Direction moveDirection) {
		switch (moveDirection) {
		case NORTH:
			goNorth();
			break;
		case EAST:
			goEast();
			break;
		case SOUTH:
			goSouth();
			break;
		case WEST:
			goWest();
			break;
		default:
			break;
		}

		System.err.println("!! " + PathfinderBot.inputHandler.getLastActionResult());
		System.err.println("Player Position: " + position.toString());
	}

	public void updatePosition() {
		if (lastMove != null) {
			boolean moved = ActionHandler.moveSuccess(lastMove);
			System.err.println(moved);
			if (moved) {
				position = position.AddUp(position, Vector2.directionToVector(lastMove));
			}
		}
	}

	private void goNorth() {
		System.out.println("go north");
		lastMove = Direction.NORTH;
	}

	private void goEast() {
		System.out.println("go east");
		lastMove = Direction.EAST;
	}

	private void goSouth() {
		System.out.println("go south");
		lastMove = Direction.SOUTH;
	}

	private void goWest() {
		System.out.println("go west");
		lastMove = Direction.WEST;
	}

	// Getter & Setter
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Vector2 getStartPos() {
		return startPos;
	}

	public void setStartPos(Vector2 startPos) {
		this.startPos = startPos;
	}

	public Vector2 getPosition() {
		return position;
	}

	public void setPosition(Vector2 position) {
		this.position = position;
	}

	public static Direction revertDirection(Direction direction) {
		switch (direction) {
		case NORTH:
			return Direction.SOUTH;
		case EAST:
			return Direction.WEST;
		case SOUTH:
			return Direction.NORTH;
		case WEST:
			return Direction.EAST;
		default:
			return null;
		}
	}

}
