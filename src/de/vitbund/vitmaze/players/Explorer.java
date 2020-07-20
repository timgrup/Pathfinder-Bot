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
	private boolean lostForm = false;
	private Queue<Waypoint> waypointQ = Collections.asLifoQueue(new ArrayDeque<Waypoint>());
	
	public Explorer() {
		this.player = PathfinderBot.player;
		this.inputHandler = PathfinderBot.inputHandler;
		this.world = PathfinderBot.world;
		this.pathfinder = PathfinderBot.pathfinder;
		this.path = new ArrayList<>();
	}
	
	public void update() {
		/*
		if(!PathfinderBot.actionHandler.isTakeable()) {
			world.worldMap.get(player.getPosition()).waypointType = inputHandler.getInputOf(InputType.currentCellStatus);
			world.forms.remove(player.getFormsPickedUp());
			player.undoPickUp();
			lostForm = true;
		}
		*/
		
		updateSurroundingWayPointTypes();
		
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
		
		if(player.conditionsFinish && inputHandler.getInputOf(InputType.currentCellStatus) != WaypointType.FINISH) { //Erstellt und läuft zum Finish, wenn alle Siegeskonditionen erfüllt werden
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
		} else if(inputHandler.getInputOf(InputType.currentCellStatus) == WaypointType.FORMENEMY) {
			Direction directionFloor = null;
			for(Direction direction : Direction.values()) {
				if(world.worldMap.get(Vector2.addUp(player.getPosition(), Vector2.directionToVector(direction))).waypointType == WaypointType.FLOOR) {
					directionFloor = direction;
					break;
				}
			}
			if(directionFloor != null) {
				player.kick(directionFloor);				
			} else {
				move();
			}
		} else if(finishDirection != null && !player.finishVisited) { //Spieler versucht, wenn in Sichtweite und Finish noch unbekannt, dieses zu entdecken
			player.move(finishDirection);
			waypointQ.remove(world.worldMap.get(Vector2.addUp(player.getPosition(), Vector2.directionToVector(finishDirection))));
			player.finishVisited = true;
		} else if(player.getFormsPickedUp() < world.formCountMin && player.finishVisited && !pathLeadsToForm && world.forms.size() == world.formCountMin) { //Spieler erzeugt weg zum nächsten Formular
			Waypoint nextForm = world.forms.get(player.getFormsPickedUp()+1); 
			path = pathfinder.findPath(player.getPosition(), nextForm.position);
			pathLeadsToForm = true;
			move();
		} else if(inputHandler.getInputOf(InputType.currentCellStatus) == WaypointType.FINISH && player.getFormsPickedUp() == world.formCountMin) { //Spieler versucht, Spiel zu beenden, wenn alle Forms eingesammelt wurden und dieser sich auf Finish befindet
			player.finishGame();
		} else if(inputHandler.getInputOf(InputType.currentCellStatus) == WaypointType.FORM && inputHandler.getFormHere().formID == player.getFormsPickedUp()+1) { //Spieler versucht, wenn er auf dem nächsten einsammelbaren Formular steht, dieses einzusammeln
			player.pickUpForm();
			world.worldMap.get(player.getPosition()).waypointType = WaypointType.FLOOR; //Wenn FORM aufgehoben wird, ist Spielzelle vom EnumType FLOOR
			if(pathLeadsToForm) {
				pathLeadsToForm = false;
			}
		}
		else { //Spieler bewegt sich, falls er keine bessere Alternative hat
			move();
		}
			
	}

	private void updateSurroundingWayPointTypes() {
		Waypoint savedWaypoint = world.worldMap.get(player.getPosition());
		WaypointType currentType = inputHandler.getInputOf(InputType.currentCellStatus);
		
		if(savedWaypoint.waypointType != currentType && savedWaypoint.waypointType != WaypointType.WALL) {
			savedWaypoint.waypointType = currentType;
			
			if(savedWaypoint.waypointType == WaypointType.FORM) {
				Form form = inputHandler.getFormHere();
				world.forms.remove(form.formID);
				world.addForm(form.formID, savedWaypoint);
			}
		}
		
		
		for(Direction direction: Direction.values()) {
			savedWaypoint = world.worldMap.get(Vector2.addUp(player.getPosition(), Vector2.directionToVector(direction)));
			currentType = inputHandler.getInputOf(InputHandler.directionToInput(direction));
			
			if(world.worldMap.containsKey(Vector2.addUp(player.getPosition(), Vector2.directionToVector(direction))) && savedWaypoint.waypointType != WaypointType.WALL) {
				if(savedWaypoint.waypointType != currentType) {
					savedWaypoint.waypointType = currentType;
				}
				
				if(savedWaypoint.waypointType == WaypointType.FORM) {
					Form form = inputHandler.getForm(direction);
					world.forms.remove(form.formID);
					world.addForm(form.formID, savedWaypoint);
				}
			}
		}
		//&& (savedWaypoint.waypointType == WaypointType.FORM || savedWaypoint.waypointType == WaypointType.FORMENEMY)
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
			Waypoint nextWaypoint = waypointQ.poll();
			System.err.println("Next Waypoint!: " + nextWaypoint.position.toString());
			path = pathfinder.findPath(player.getPosition(), nextWaypoint.position);
		}
		
		for (Waypoint w : path) {
			System.err.println(w.exploredByLooking);
		}

		Waypoint moveWaypoint = path.get(0);
		Direction moveDirection = moveWaypoint.exploredByLooking;
		player.move(moveDirection);
		System.err.println("My next Target is: " + moveWaypoint.waypointType);
	}
	
	public void removeFirstWaypointFromPath() {
		path.remove(0);
	}
	
	public boolean pathIsEmpty() {
		return path.isEmpty();
	}
}
