package de.vitbund.vitmaze.players;

import java.util.HashMap;
import java.util.Map;

public class World {
	
	int sizeX, sizeY, level, formCount;
	public Map<Vector2, Waypoint> worldMap;
	
	public World() {
		worldMap = new HashMap<Vector2, Waypoint>();
	}
	
	public void addWaypoint(Vector2 pos, Waypoint waypoint) {
		worldMap.put(pos, waypoint);
	}
	
	public boolean containsKey(Vector2 pos) {
		return worldMap.containsKey(pos);
	}

}
