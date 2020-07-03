package de.vitbund.vitmaze.players;

import de.vitbund.vitmaze.players.Vector2.Direction;

public class Waypoint {
	
	
	enum WaypointType
	{
	   FLOOR, WALL, FINISH, FORM;
	}
	
	public WaypointType waypointType;
	public Vector2 position;
	public Vector2 explorerPos;
	public Direction exploredByLooking;
	
	public Waypoint(WaypointType waypointType, Vector2 pos) {
		this.waypointType = waypointType;
		this.position = pos;
	}
	
	public static WaypointType GetType(String type) {
		type = type.split(" ")[0];
		switch(type) {
		case "FLOOR": return WaypointType.FLOOR;
		case "WALL": return WaypointType.WALL;
		case "FINISH": return WaypointType.FINISH;
		case "FORM": return WaypointType.FORM;
		default: return null;
		}
	}

}
