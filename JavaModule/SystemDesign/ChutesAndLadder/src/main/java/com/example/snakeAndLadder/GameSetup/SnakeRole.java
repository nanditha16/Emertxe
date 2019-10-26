package com.example.snakeAndLadder.GameSetup;

import com.example.snakeAndLadder.Exceptions.InitialSetupException;

public final class SnakeRole extends SquareRole {
 	private int transport ;

 	public SnakeRole( Square s, int t) {
 		super (s);
 		assert t <0 : "A snake shift must be negative " ;
 		transport = t;
	}

 	@Override
	 public Square landHereOrGoHome () throws InitialSetupException {
 		System . out. print ("--> "+ (square.getPosition ()+1) + "--SNAKE ");
	 return destination().landHereOrGoHome ();
	 }

	 private Square destination () throws InitialSetupException {
		 return square.findRelativeSquare ( transport );
	 }
}
