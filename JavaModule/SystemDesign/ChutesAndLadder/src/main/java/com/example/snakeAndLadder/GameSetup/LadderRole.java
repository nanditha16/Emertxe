package com.example.snakeAndLadder.GameSetup;

import com.example.snakeAndLadder.Exceptions.InitialSetupException;

/**
 * 
 * LadderRole : will find the square position it should jump to
 * 	i.e  To find a valid square position to move upon ladder shift/transport value
 * 	 and if the square is occupied or not.
 * 
 * @author nanditha
 *
 */
public final class LadderRole extends SquareRole {
	 private int transport ;

	 public LadderRole ( Square s, int t) {
		 super (s);
		 assert t >0 : "A ladder shift must be positive ";
		 transport = t;
	 }

	 @Override
	 public Square landHereOrGoHome () throws InitialSetupException {
		 System . out. print ( "--> "+ (square.getPosition ()+1) + " --LADDER");
		 return destination().landHereOrGoHome ();
	 }

	 private Square destination () throws InitialSetupException {
		 return square.findRelativeSquare ( transport );
	 }
 }
