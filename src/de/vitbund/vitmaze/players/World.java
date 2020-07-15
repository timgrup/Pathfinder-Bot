package de.vitbund.vitmaze.players;

import java.util.HashMap;
import java.util.Map;

public class World {
	
	// Attribute
	int sizeX, sizeY, level, formCountMin;
	public Map<Vector2, Waypoint> worldMap;
	public Map<Integer, Waypoint> forms;
	
	// Konstruktor
	public World() {
		worldMap = new HashMap<>();
		forms = new HashMap<>();;
	}
	
	public void addWaypoint(Vector2 pos, Waypoint waypoint) {
		worldMap.put(pos, waypoint);
	}
	
	public boolean containsKey(Vector2 pos) {
		return worldMap.containsKey(pos);
	}
	
	// Füge ein Formular zu Map hinzu
	public void addForm(int i, Waypoint w) {
		forms.put(i, w);
	}
}
