package de.vitbund.vitmaze.players;

import java.util.*;

public class InputHandler {

	private Scanner input;
	private Map<InputType, String> inputMap;

	public int sizeX, sizeY, level, playerId, startX, startY;

	enum InputType {
		lastActionsResult, currentCellStatus, northCellStatus, eastCellStatus, southCellStatus, westCellStatus;
	}

	public InputHandler() {
		input = new Scanner(System.in);

		// INIT - Auslesen der Initialdaten
		// 1. Zeile: Maze Infos
		sizeX = input.nextInt(); // X-Groesse des Spielfeldes (Breite)
		sizeY = input.nextInt(); // Y-Groesse des Spielfeldes (Hoehe)
		level = input.nextInt(); // Level des Matches
		input.nextLine(); // Beenden der ersten Zeile
		// 2. Zeile: Player Infos
		playerId = input.nextInt(); // id dieses Players / Bots
		startX = input.nextInt(); // X-Koordinate der Startposition dieses Player
		startY = input.nextInt(); // Y-Koordinate der Startposition dieses Players
		input.nextLine(); // Beenden der zweiten Zeile
	}

	public boolean update() {
		if (input.hasNext()) {
			inputMap = new HashMap<>();
			inputMap.put(InputType.lastActionsResult, input.nextLine());
			inputMap.put(InputType.currentCellStatus, input.nextLine());
			inputMap.put(InputType.northCellStatus, input.nextLine());
			inputMap.put(InputType.eastCellStatus, input.nextLine());
			inputMap.put(InputType.southCellStatus, input.nextLine());
			inputMap.put(InputType.westCellStatus, input.nextLine());
			return true;
		} else {
			return false;
		}
	}

	public String getInputOf(InputType inputType) {
		return inputMap.get(inputType);
	}
	
	public void close() {
		input.close();
	}

}
