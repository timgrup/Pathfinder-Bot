package de.vitbund.vitmaze.players;

import java.util.Scanner;

import de.vitbund.vitmaze.players.InputHandler.InputType;
import de.vitbund.vitmaze.players.Waypoint.WaypointType;

/**
 * Klasse eines minimalen Bots für das VITMaze
 * @author Patrick.Stalljohann
 * @version 1.0
 *
 */
public class MinimalBot {

	/**
	 * Hauptmethode zum Ausführen des Bots
	 * @param args
	 */
	
	private static InputHandler inputHandler;
	private static Pathfinder pathfinder;
	
	public static void main(String[] args) {
		inputHandler = new InputHandler();

		System.err.println("PlayerID: " + inputHandler.playerId);
		
		// TURN (Wiederholung je Runde notwendig)
		while(inputHandler.update()) {
			// Debug Information ausgeben (optional möglich)
			System.err.println("Ergebnis Vorrunde: " + inputHandler.getInputOf(InputType.lastActionsResult));
			System.err.println("currentCellStatus: " + inputHandler.getInputOf(InputType.currentCellStatus));
			System.err.println("northCellStatus: " + inputHandler.getInputOf(InputType.northCellStatus));
			System.err.println("eastCellStatus: " + inputHandler.getInputOf(InputType.eastCellStatus));
			System.err.println("southCellStatus: " + inputHandler.getInputOf(InputType.southCellStatus));
			System.err.println("westCellStatus: " + inputHandler.getInputOf(InputType.westCellStatus));
			
			// Rundenaktion ausgeben
			System.out.println("go west");					
		}
		
		// Eingabe schliessen (letzte Aktion)
		inputHandler.close();
	}

}
