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
	public static ActionHandler actionHandler;
	public static Pathfinder pathfinder;
	public static Player player;
	public static World world;
	public static Explorer explorer;
	
	public static void main(String[] args) {
		world = new World();
		player = new Player();
		inputHandler = new InputHandler(world, player);
		actionHandler = new ActionHandler(inputHandler);
		pathfinder = new Pathfinder();
		explorer = new Explorer();
		
		// TURN (Wiederholung je Runde notwendig)
		while(inputHandler.update()) {
			//Debug Informationen ausgeben
			inputHandler.printDebugInfo();
			
			//Add Start Pos
			if(world.worldMap.isEmpty()) {
				Waypoint waypoint = new Waypoint(inputHandler.getInputOf(InputType.currentCellStatus), player.getStartPos());
				world.addWaypoint(player.getStartPos(), waypoint);
			}
			player.updatePosition();
			
			explorer.explore();
			if(inputHandler.getInputOf(InputType.currentCellStatus) == WaypointType.FINISH)
				System.out.println("finish");
			explorer.move();
			
			
			System.err.println(world.worldMap.size());
			
			/*
			for (HashMap.Entry<Vector2, Waypoint> entry : world.worldMap.entrySet()) {
			    System.err.println(entry.getKey().toString() + "/" + entry.getValue().waypointType.toString());
			}
			*/
		}
		
		// Eingabe schliessen (letzte Aktion)
		inputHandler.close();
	}

}
