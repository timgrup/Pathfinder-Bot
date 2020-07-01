package de.vitbund.vitmaze.players;

import java.util.HashMap;
import java.util.Scanner;

import de.vitbund.vitmaze.players.InputHandler.InputType;
import de.vitbund.vitmaze.players.Vector2.Direction;
import de.vitbund.vitmaze.players.Waypoint.WaypointType;

public class PathfinderBot {

	/**
	 * Hauptmethode zum Ausführen des Bots
	 * @param args
	 */
	
	public static InputHandler inputHandler;
	public static Pathfinder pathfinder;
	public static Player player;
	public static World world;
	
	public static void main(String[] args) {
		world = new World();
		player = new Player();
		inputHandler = new InputHandler(world, player);
		pathfinder = new Pathfinder();
		
		// TURN (Wiederholung je Runde notwendig)
		while(inputHandler.update()) {
			//Debug Informationen ausgeben
			inputHandler.printDebugInfo();
			
			//Add Start Pos
			if(world.worldMap.isEmpty()) {
				Waypoint waypoint = new Waypoint(inputHandler.getInputOf(InputType.currentCellStatus));
				world.addWaypoint(player.getStartPos(), waypoint);
			}
			
			pathfinder.exploreNeighbours();
			
			for (HashMap.Entry<Vector2, Waypoint> entry : world.worldMap.entrySet()) {
			    System.err.println(entry.getKey().toString() + "/" + entry.getValue().waypointType.toString());
			}
			
			// Rundenaktion ausgeben
			player.move(Direction.WEST);
		}
		
		// Eingabe schliessen (letzte Aktion)
		inputHandler.close();
	}

}
