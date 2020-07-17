package de.vitbund.vitmaze.players;

import java.util.*;

import de.vitbund.vitmaze.players.InputHandler.InputType;
import de.vitbund.vitmaze.players.Vector2.Direction;
import de.vitbund.vitmaze.players.Waypoint.WaypointType;

public class InputHandler {

	private World world;
	private Player player;
	private Scanner input;
	private String lastActionResult;
	private Map<InputType, WaypointType> inputMap;
	private List<String> rawInput = new ArrayList<>();

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
		// Listeneintrag f�r jeden InputType 
		if (input.hasNext()) {
			lastActionResult = input.nextLine();
			// Map f�r ENUMS
			inputMap = new HashMap<>();
			// Liste f�r unbearbeitete Strings
			rawInput = new ArrayList<>();
			
			// Zeile einlesen f�r jeden inputType/ENUM
			for (InputType inputType : InputType.values()) {
				String inputString = input.nextLine();
				inputMap.put(inputType, Waypoint.GetType(inputString));
				rawInput.add(inputString);
			}
			
			for(String s : rawInput) {
				System.err.println(s);
			}
			
			return true; 	// Update erfolgreich
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
		for(InputType inputType : InputType.values()) {
			System.err.println(inputType.toString() + ": " + this.getInputOf(inputType));
		}
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
	
	public Form getForm(Direction d) {
		WaypointType waypoint = getInputOf(directionToInput(d));
		if(waypoint == WaypointType.FORM || waypoint == WaypointType.FORMENEMY) {
			String rawString = rawInput.get(InputHandler.directionToInput(d).ordinal());
			String[] rawStringArray = rawString.split(" ");
			Form form = new Form(Integer.parseInt(rawStringArray[1]), Integer.parseInt(rawStringArray[2]));
			return form;
		}
		return null;
	}
}
