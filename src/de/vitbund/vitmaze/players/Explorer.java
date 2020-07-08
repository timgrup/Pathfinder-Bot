package de.vitbund.vitmaze.players;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Queue;

import de.vitbund.vitmaze.players.Vector2.Direction;
import de.vitbund.vitmaze.players.Waypoint.WaypointType;


public class Explorer {
	
	private Player player;
	private InputHandler inputHandler;
	private World world;
	private Pathfinder pathfinder;
	private List<Waypoint> path; 
	private Queue<Waypoint> waypointQ = Collections.asLifoQueue(new ArrayDeque<Waypoint>());
	
	public Explorer() {
		this.player = PathfinderBot.player;
		this.inputHandler = PathfinderBot.inputHandler;
		this.world = PathfinderBot.world;
		this.pathfinder = PathfinderBot.pathfinder;
		this.path = new ArrayList<>();
	}

	public void explore() {
		for (Direction direction : Direction.values()) {
			Vector2 pos = Vector2.AddUp(player.getPosition(), Vector2.directionToVector(direction));
			Waypoint cell = new Waypoint(inputHandler.getInputOf(InputHandler.directionToInput(direction)), pos);
			cell.explorerPos = player.getPosition();
			cell.exploredByLooking = direction;
			
			if (!world.containsKey(pos)) {
				world.addWaypoint(pos, cell);
				
				if(cell.waypointType.equals(WaypointType.FORM)) {
					// TODO:world.addForm(inputHandler., w);
				}
				if (!cell.waypointType.equals(WaypointType.WALL)) {
					waypointQ.add(cell);
				}
			}
		}
	}
	
	public void move() {
		
		if(path.isEmpty()) {
			path = pathfinder.findPath(player.getPosition(), waypointQ.poll().position);
		}
		
		for (Waypoint w : path) {
			System.err.println(w.exploredByLooking);
		}
		
		Direction moveDirection = path.get(0).exploredByLooking;
		path.remove(0);
		player.move(moveDirection);
	}
}
