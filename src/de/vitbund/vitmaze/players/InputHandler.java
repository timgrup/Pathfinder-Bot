package de.vitbund.vitmaze.players;

import java.util.*;

import de.vitbund.vitmaze.players.InputHandler.InputType;
import de.vitbund.vitmaze.players.Vector2.Direction;
import de.vitbund.vitmaze.players.Waypoint.WaypointType;

public class InputHandler {

	private World world;
	private Player player;
	private Scanner input;
	private Map<InputType, WaypointType> inputMap;
	private String lastActionResult;

	enum InputType {
		currentCellStatus, northCellStatus, eastCellStatus, southCellStatus, westCellStatus;
	}

	public InputHandler(World world, Player player) {
		this.world = world;
		this.player = player;
		input = new Scanner(System.in);

		initStart();
	}

	private void initStart() {
		// 1. Zeile: Maze Infos
		int sizeX = input.nextInt(); // X-Groesse des Spielfeldes (Breite)
		int sizeY = input.nextInt(); // Y-Groesse des Spielfeldes (Hoehe)
		int level = input.nextInt(); // Level des Matches
		input.nextLine(); // Beenden der ersten Zeile
		// 2. Zeile: Player Infos
		int playerId = input.nextInt(); // id dieses Players / Bots
		int startX = input.nextInt(); // X-Koordinate der Startposition dieses Player
		int startY = input.nextInt(); // Y-Koordinate der Startposition dieses Players
		input.nextLine(); // Beenden der zweiten Zeile

		this.world.sizeX = sizeX;
		this.world.sizeY = sizeY;

		this.player.setId(playerId);
		this.player.setStartPos(new Vector2(startX, startY));
		this.player.setPosition(player.getStartPos());
	}

	public boolean update() {
		if (input.hasNext()) {
			inputMap = new HashMap<>();
			lastActionResult = input.nextLine();
			inputMap.put(InputType.currentCellStatus, Waypoint.GetType(input.nextLine()));
			inputMap.put(InputType.northCellStatus, Waypoint.GetType(input.nextLine()));
			inputMap.put(InputType.eastCellStatus, Waypoint.GetType(input.nextLine()));
			inputMap.put(InputType.southCellStatus, Waypoint.GetType(input.nextLine()));
			inputMap.put(InputType.westCellStatus, Waypoint.GetType(input.nextLine()));
			return true;
		} else {
			return false;
		}
	}

	public WaypointType getInputOf(InputType inputType) {
		return inputMap.get(inputType);
	}
	
	public String getLastActionResult() {
		return this.lastActionResult;
	}

	public void close() {
		input.close();
	}

	public void printDebugInfo() {
		// Print Debug Information
		System.err.println("lastActionResult: " + this.lastActionResult);
		System.err.println("currentCellStatus: " + this.getInputOf(InputType.currentCellStatus));
		System.err.println("northCellStatus: " + this.getInputOf(InputType.northCellStatus));
		System.err.println("eastCellStatus: " + this.getInputOf(InputType.eastCellStatus));
		System.err.println("southCellStatus: " + this.getInputOf(InputType.southCellStatus));
		System.err.println("westCellStatus: " + this.getInputOf(InputType.westCellStatus));
	}

	public static InputType directionToInput(Direction direction) {
		switch (direction) {
		case NORTH:
			return InputType.northCellStatus;
		case EAST:
			return InputType.eastCellStatus;
		case SOUTH:
			return InputType.southCellStatus;
		case WEST:
			return InputType.westCellStatus;
		default:
			return null;
		}
	}

}
