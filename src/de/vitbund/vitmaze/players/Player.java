package de.vitbund.vitmaze.players;

import de.vitbund.vitmaze.players.Vector2.Direction;

public class Player {

	// Data
	private int id;
	private Vector2 startPos;
	private Vector2 position;

	public void move(Direction moveDirection) {
		switch (moveDirection) {
		case NORTH:
			System.out.println("go north");
			break;
		case EAST:
			System.out.println("go east");
			break;
		case SOUTH:
			System.out.println("go south");
			break;
		case WEST:
			System.out.println("go west");
			break;
		default:
			break;
		}
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

}
