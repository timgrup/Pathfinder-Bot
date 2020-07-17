package de.vitbund.vitmaze.players;

public class Form {
	
	public int formID;
	public int playerID;
	public Waypoint waypoint; //null wenn später nicht gesetzt wird
	
	public Form(int playerID, int formID) {
		this.formID = formID;
		this.playerID = playerID;
	}
	
	
}
