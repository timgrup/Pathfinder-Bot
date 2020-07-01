package de.vitbund.vitmaze.players;

import java.util.*;

import de.vitbund.vitmaze.players.InputHandler.InputType;
import de.vitbund.vitmaze.players.Vector2.Direction;

public class Pathfinder {

	private InputHandler inputHandler;
	private World world;
	private Player player;
	private Queue<Waypoint> waypoints;

	public Pathfinder() {
		this.inputHandler = PathfinderBot.inputHandler;
		this.world = PathfinderBot.world;
		this.player = PathfinderBot.player;
	}

	public void exploreNeighbours() {
		for (Direction direction : Direction.values()) {
			Waypoint cell = new Waypoint(inputHandler.getInputOf(InputHandler.directionToInput(direction)));
			Vector2 pos = Vector2.AddUp(player.getPosition(), Vector2.directionToVector(direction));
					
			if(!world.containsKey(pos)) {
				world.addWaypoint(pos, cell);
			}
		}
	}
}
