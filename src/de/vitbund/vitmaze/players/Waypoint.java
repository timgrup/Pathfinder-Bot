package de.vitbund.vitmaze.players;

import de.vitbund.vitmaze.players.Vector2.Direction;

public class Waypoint {

	enum WaypointType {
		FLOOR, WALL, FINISH, FINISHENEMY, FORM, FORMENEMY;
	}

	public WaypointType waypointType;
	public Vector2 position;
	public Vector2 explorerPos;
	public Direction exploredByLooking;
	public boolean isExplored = false;

	public Waypoint(WaypointType waypointType, Vector2 pos) {
		this.waypointType = waypointType;
		this.position = pos;
	}

	public static WaypointType GetType(String type) {
		String[] typeArray = type.split(" ");
		type = typeArray[0];

		switch (type) {

		case "FLOOR":
			return WaypointType.FLOOR;

		case "WALL":
			return WaypointType.WALL;

		case "FINISH":
			if (Integer.parseInt(typeArray[1]) == PathfinderBot.player.getId()) {
				PathfinderBot.world.formCountMin = Integer.parseInt(typeArray[2]);
				return WaypointType.FINISH;
			} else {
				return WaypointType.FINISHENEMY;
			}

		case "FORM":
			if (Integer.parseInt(typeArray[1]) == PathfinderBot.player.getId()) {
				int formID = Integer.parseInt(typeArray[2]);
				if(PathfinderBot.world.formCountMin < formID) {
					PathfinderBot.world.formCountMin = formID; //FormID entspricht der min Menge an Formularen
				}
				return WaypointType.FORM;
			} else {
				return WaypointType.FORMENEMY;
			}

		default:
			return null;
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((position == null) ? 0 : position.hashCode());
		result = prime * result + ((waypointType == null) ? 0 : waypointType.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Waypoint other = (Waypoint) obj;
		if (position == null) {
			if (other.position != null)
				return false;
		} else if (!position.equals(other.position))
			return false;
		if (waypointType != other.waypointType)
			return false;
		return true;
	}

}
