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
	private List<Waypoint> wayToPoint = new ArrayList<Waypoint>();

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

				if (!cell.waypointType.equals(WaypointType.WALL)) {
					waypointQ.add(cell);
				}
			}
		}
	}

	public void move() {
		boolean isStartPos = player.getPosition().equals(player.getStartPos());
		System.err.println("Player is startpos: " + isStartPos);
		if (!isStartPos && wayToPoint.isEmpty()) {
			player.move(Player.revertDirection(world.worldMap.get(player.getPosition()).exploredByLooking));
		} else {
			if(wayToPoint.isEmpty()) {
				wayToPoint = new ArrayList<Waypoint>();
				Waypoint waypoint = waypointQ.poll();
				if (waypoint != null) {
					wayToPoint.add(waypoint);
					while(!waypoint.explorerPos.equals(player.getStartPos())) {
						waypoint = world.worldMap.get(waypoint.explorerPos);
						wayToPoint.add(waypoint);
					}
					Direction moveDir = wayToPoint.get(wayToPoint.size() - 1).exploredByLooking;
					wayToPoint.remove(wayToPoint.size() - 1);
					player.move(moveDir);
				}
			} else {
				Direction moveDir = wayToPoint.get(wayToPoint.size() - 1).exploredByLooking;
				wayToPoint.remove(wayToPoint.size() - 1);
				player.move(moveDir);
			}
		}
	}
}
