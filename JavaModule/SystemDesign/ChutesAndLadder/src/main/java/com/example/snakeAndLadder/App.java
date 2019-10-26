package com.example.snakeAndLadder;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) {
		// TODO these parameters have to be read and checked for
		// validity from the console , that is , the command line .
		String[] playerNames = { " Monica ", " Albert ", " Noemi ", " Jaume " };
		int numSquares = 12;
		// for the user first square is at position 1 but
		// internally is at 0
		int[][] snakesFromTo = { { 11, 5 } };
		int[][] laddersFromTo = { { 2, 6 }, { 7, 9 } };

		try {
			// initial player and board setup
			Game game = new Game(playerNames, numSquares, snakesFromTo, laddersFromTo);
			// Start the play
			game.play();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
}
