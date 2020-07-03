package de.vitbund.vitmaze.players;

import java.util.*;

import de.vitbund.vitmaze.players.InputHandler.InputType;
import de.vitbund.vitmaze.players.Vector2.Direction;
import de.vitbund.vitmaze.players.Waypoint.WaypointType;

public class Pathfinder {

	private InputHandler inputHandler;
	private World world;
	private Player player;
	private Queue<Waypoint> waypointQ = new LinkedList<Waypoint>();

	public Pathfinder() {
		this.inputHandler = PathfinderBot.inputHandler;
		this.world = PathfinderBot.world;
		this.player = PathfinderBot.player;
	}

	public void exploreNeighbours() {
		for (Direction direction : Direction.values()) {
			// Store cell, is FLOOR WALL etc..
			Vector2 pos = Vector2.AddUp(player.getPosition(), Vector2.directionToVector(direction));
			Waypoint cell = new Waypoint(inputHandler.getInputOf(InputHandler.directionToInput(direction)), pos);
			cell.explorerPos = player.getPosition();
			cell.exploredByLooking = direction;

			if (!world.containsKey(pos)) {
				world.addWaypoint(pos, cell);

				if (cell.waypointType.equals(WaypointType.FLOOR)) {
					waypointQ.add(cell);
				}
			}
		}
	}

	public void move() {
		boolean isStartPos = player.getPosition().equals(player.getStartPos());
		System.err.println("Player is startpos: " + isStartPos);
		if (!isStartPos) {
			player.move(Player.revertDirection(world.worldMap.get(player.getPosition()).exploredByLooking));
		} else {
			Waypoint waypoint = waypointQ.poll();
			if (waypoint != null) {
				player.move(waypoint.exploredByLooking);
			}
		}
	}
}
