package de.vitbund.vitmaze.players;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Queue;

import de.vitbund.vitmaze.players.InputHandler.InputType;
import de.vitbund.vitmaze.players.Vector2.Direction;
import de.vitbund.vitmaze.players.Waypoint.WaypointType;


public class Explorer {
	
	private Player player;
	private InputHandler inputHandler;
	private World world;
	private Pathfinder pathfinder;
	private List<Waypoint> path; 
	private boolean pathLeadsToFinish = false;
	private boolean pathLeadsToForm = false;
	private Queue<Waypoint> waypointQ = Collections.asLifoQueue(new ArrayDeque<Waypoint>());
	
	public Explorer() {
		this.player = PathfinderBot.player;
		this.inputHandler = PathfinderBot.inputHandler;
		this.world = PathfinderBot.world;
		this.pathfinder = PathfinderBot.pathfinder;
		this.path = new ArrayList<>();
	}
	
	public void update() {
		explore();
		
		Direction finishDirection = null;
		for(Direction direction: Direction.values()) {
			WaypointType waypoint = inputHandler.getInputOf(inputHandler.directionToInput(direction));
			if(waypoint == WaypointType.FINISH) {
				finishDirection = direction;
				break;
			}
		}
		
		if(player.getFormsPickedUp() == world.formCountMin && player.finishVisited) {
			player.conditionsFinish = true;
		}
		
		if(player.conditionsFinish && inputHandler.getInputOf(InputType.currentCellStatus) != WaypointType.FINISH) {
			System.err.println("Conditions Finished");
			if(!pathLeadsToFinish) {
				Waypoint finishWaypoint = null;
				for(Waypoint waypoint : world.worldMap.values()) {
					if(waypoint.waypointType == WaypointType.FINISH) {
						finishWaypoint = waypoint;
					}
				}
				path = pathfinder.findPath(player.getPosition(), finishWaypoint.position);
				pathLeadsToFinish = true;
			}
			move();
		} else if(finishDirection != null && !player.finishVisited) {
			player.move(finishDirection);
			player.finishVisited = true;
		} else if(inputHandler.getInputOf(InputType.currentCellStatus) == WaypointType.FINISH && player.getFormsPickedUp() == world.formCountMin) {
			player.finishGame();
		} else if(inputHandler.getInputOf(InputType.currentCellStatus) == WaypointType.FORM && inputHandler.getFormHere().formID == player.getFormsPickedUp()+1) {
			player.pickUpForm();
			if(pathLeadsToForm) {
				pathLeadsToForm = false;
			}
		} else {
			move();
		}
			
	}

	public void explore() {
		for (Direction direction : Direction.values()) {
			Vector2 pos = Vector2.addUp(player.getPosition(), Vector2.directionToVector(direction));
			Waypoint cell = new Waypoint(inputHandler.getInputOf(InputHandler.directionToInput(direction)), pos);
			cell.explorerPos = player.getPosition();
			cell.exploredByLooking = direction;
			
			if (!world.containsKey(pos)) {
				world.addWaypoint(pos, cell);
				
				if (!cell.waypointType.equals(WaypointType.WALL)) {
					if(cell.waypointType.equals(WaypointType.FORM)) {
						Form form = inputHandler.getForm(direction);
						world.addForm(form.formID, cell);
					}
					waypointQ.add(cell);
				}
			}
		}
	}
	
	public void move() {
		
		if(path.isEmpty()) {
			System.err.println("WaypointQ Size: " + waypointQ.size());
			path = pathfinder.findPath(player.getPosition(), waypointQ.poll().position);
		}
		
		for (Waypoint w : path) {
			System.err.println(w.exploredByLooking);
		}
		
		Direction moveDirection = path.get(0).exploredByLooking;
		//path.remove(0);
		player.move(moveDirection);
	}
	
	public void removeFirstWaypointFromPath() {
		path.remove(0);
	}
	
	public boolean pathIsEmpty() {
		return path.isEmpty();
	}
}
