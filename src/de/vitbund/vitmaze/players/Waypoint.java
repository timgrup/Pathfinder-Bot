package de.vitbund.vitmaze.players;

public class Waypoint {
	
	
	enum WaypointType
	{
	   FLOOR, WALL, FINISH;
	}
	
	public WaypointType waypointType;
	
	public Waypoint(WaypointType waypointType) {
		this.waypointType = waypointType;
	}
	
	public static WaypointType GetType(String type) {
		type = type.split(" ")[0];
		switch(type) {
		case "FLOOR": return WaypointType.FLOOR;
		case "WALL": return WaypointType.WALL;
		case "FINISH": return WaypointType.FINISH;
		default: return null;
		}
	}

}