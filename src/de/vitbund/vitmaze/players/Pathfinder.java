package de.vitbund.vitmaze.players;

import java.util.*;

public class Pathfinder {

	public Map<Vector2, Waypoint> path = new HashMap<Vector2, Waypoint>();
	
	public Pathfinder(Vector2 startPos, Waypoint startWaypoint) {
		path.put(startPos, startWaypoint);
	}
}
