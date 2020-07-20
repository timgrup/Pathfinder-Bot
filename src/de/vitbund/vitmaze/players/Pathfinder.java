package de.vitbund.vitmaze.players;

import java.util.*;
import java.util.Map.Entry;

import de.vitbund.vitmaze.players.InputHandler.InputType;
import de.vitbund.vitmaze.players.Vector2.Direction;
import de.vitbund.vitmaze.players.Waypoint.WaypointType;

public class Pathfinder {

	private World world;
	private List<Waypoint> wayToPoint = new ArrayList<Waypoint>();


	public Pathfinder() {
		this.world = PathfinderBot.world;
	}

	public List<Waypoint> findPath(Vector2 startPos, Vector2 targetPos) {
		//Erstelle exakte Kopie der World Map
		Map<Vector2, Waypoint> map = new HashMap<Vector2, Waypoint>();
		map.putAll(world.worldMap);
		
		//Setze Attribute der Kopie zurück
		for (Entry<Vector2, Waypoint> entry : map.entrySet()) {
			Waypoint waypoint = entry.getValue();
			waypoint.exploredByLooking = null;
			waypoint.explorerPos = null;
			waypoint.isExplored = false;
		}
		
		//Queue für noch zu erkundende Nachbarn
		Queue<Waypoint> queue = new LinkedList<Waypoint>();
		
		//Speichern von Start, Ende und derzeitigen Waypoint
		Waypoint start = map.get(startPos);
		Waypoint end = map.get(targetPos);
		Waypoint searchingFrom = null;
		boolean isRunning = true;
		
		//Füge Start hinzu
		queue.add(start);

        while (queue.size() > 0 && isRunning)
        {
            searchingFrom = queue.poll();
            searchingFrom.isExplored = true;

            if (searchingFrom.position.equals(end.position)) isRunning = false;
            
            //Explore Neighbours
            if (!isRunning) break;
            for (Direction direction : Direction.values())
            {
                Vector2 neighbourPos = Vector2.addUp(searchingFrom.position, Vector2.directionToVector(direction));

                if(map.containsKey(neighbourPos) && map.get(neighbourPos).waypointType != WaypointType.WALL)
                {
                	//Add neighbour to Queue
                	Waypoint neighbour = map.get(neighbourPos);
                    if (neighbour.isExplored || queue.contains(neighbour))
                    {
                        //do nothing
                    }
                    else
                    {
                        queue.add(neighbour);
                        neighbour.explorerPos = searchingFrom.position;
                        neighbour.exploredByLooking = direction;
                        neighbour.isExplored = true;
                    }
                }
            }
        }
        
        //Create Path
        wayToPoint = new ArrayList<Waypoint>();

        Waypoint previous = end;
        while (!previous.position.equals(start.position))
        {
            wayToPoint.add(previous);
            previous = map.get(previous.explorerPos);
        }
        
        //Reverse List
        Collections.reverse(wayToPoint);
        
        return wayToPoint;
	}
	
	public List<Waypoint> getPath() {
		return this.wayToPoint;
	}
}
