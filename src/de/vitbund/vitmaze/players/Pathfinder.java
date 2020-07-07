package de.vitbund.vitmaze.players;

import java.util.*;
import java.util.Map.Entry;

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
		if(wayToPoint.isEmpty()) {
			findPath(player.getPosition(), waypointQ.poll().position);
		}
		for (Waypoint waypoint : wayToPoint) {
			System.err.println(waypoint.exploredByLooking);
		}
		Direction moveDirection = wayToPoint.get(0).exploredByLooking;
		wayToPoint.remove(0);
		player.move(moveDirection);
	}

	public void findPath(Vector2 startPos, Vector2 targetPos) {
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
		System.err.println(start);
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
                Vector2 neighbourPos = Vector2.AddUp(searchingFrom.position, Vector2.directionToVector(direction));

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
        //wayToPoint.add(start);
        
        //Reverse List
        Collections.reverse(wayToPoint);
	}
}
