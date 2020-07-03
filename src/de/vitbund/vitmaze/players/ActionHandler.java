package de.vitbund.vitmaze.players;

import de.vitbund.vitmaze.players.Vector2.Direction;

public class ActionHandler {

	public static boolean spawnSuccess() {
		String lar = PathfinderBot.inputHandler.getLastActionResult();
		if (lar.equals("OK")) {
			return true;
		}
		return false;
	}

	public static boolean moveSuccess(Direction direction) {
		String lar = PathfinderBot.inputHandler.getLastActionResult();
		String check = "OK " + direction.toString().toUpperCase();
		if (lar.equals(check)) {
			return true;
		}
		return false;
	}
	
	public static boolean isBlocked() {
		String lar = PathfinderBot.inputHandler.getLastActionResult();
		if(lar.equals("NOK BLOCKED")) {
			return true;
		}
		return false;
	}

}
