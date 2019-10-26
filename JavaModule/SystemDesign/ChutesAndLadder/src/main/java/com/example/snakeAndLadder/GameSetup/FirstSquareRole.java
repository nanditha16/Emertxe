package com.example.snakeAndLadder.GameSetup;

import java.util.ArrayList;

import com.example.snakeAndLadder.Player;

/**
 * FirstSquareRole (can contain ArrayList of players)
 * 	It is a FirstSquare
 * 	player to enter and leave the square
 * 	check if It can be occupied, if player is present or not 
 * 		i.e if the players list is empty or not
 */
public final class FirstSquareRole extends SquareRole {

 private ArrayList <Player > players = new ArrayList<Player>();

 	public FirstSquareRole ( Square s) {
 		super (s);
 	}

	 @Override
	 public boolean isFirstSquare() {
		 return true ;
	 }

	 @Override
	 public void enter( Player player ) {
		 players.add( player );
		 player.setSquare ( square );
	 }

	 @Override
	 public void leave( Player player ) {
		 players.remove ( player );
	 }

	 @Override
	 public boolean isOccupied() {
		 return ! players.isEmpty ();
	}
 }