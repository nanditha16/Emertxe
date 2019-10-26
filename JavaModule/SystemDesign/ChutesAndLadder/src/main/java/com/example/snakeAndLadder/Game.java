package com.example.snakeAndLadder;

import java.util.LinkedList;
import java.util.Random;

import com.example.snakeAndLadder.Exceptions.InitialSetupException;
import com.example.snakeAndLadder.GameSetup.Board;

public final class Game {
	private LinkedList<Player> players = new LinkedList<Player>();
	// this is a queue : elements are removed from the beginning
	// with players . remove () and added to the end by players . add ()
	private Board board = null;
	private Player winner;

	private final class Dice {
		private static final int MINVALUE = 1;
		private static final int MAXVALUE = 6;

		public int roll() {
			return random(MINVALUE, MAXVALUE);
		}

		private int random(int min, int max) {
			assert min < max;
			return  ( new Random().ints(min, max+1).findFirst().getAsInt());
		}
	}

	public Game(String[] playerNames, int numSquares, int[][] snakes, int[][] ladders) {
		// Initial Setup
		makeBoard(numSquares, ladders, snakes);
		makePlayers(playerNames);
	}

	private void makeBoard(int numSquares, int[][] ladders, int[][] snakes) {
		try {
			board = new Board(numSquares, ladders, snakes);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void play() throws InitialSetupException {
		assert !players.isEmpty() : "No players to play ";
		assert board != null : "No scoreboard to play ";

		Dice dice = new Dice();
		startGame();

		while (notOver()) {
			int roll = dice.roll();
			System.out.print( currentPlayer() + ": ");
			//System.out.println(" Current player is " + currentPlayer() + " and rolls " + roll);
			movePlayer(roll);
			//System.out.println(" Current Position of all players: \n" + this);
		}
		System.out.println(winner + " has won.");
	}

	private void movePlayer(int roll) throws InitialSetupException {
		Player currentPlayer = players.remove(); // from the head
		currentPlayer.moveForward(roll);
		players.add(currentPlayer); // to the tail - Adds element to end
		if (currentPlayer.wins()) {
			winner = currentPlayer;
		}
	}
	private void makePlayers(String[] playerNames) {
		assert playerNames.length > 0 : " There must be some player ";
		System.out.println("<<INITAL SETUP - PLAYERS >>"+" Players are : ");
		int i = 1;
		for (String str : playerNames) {
			Player player = new Player(str);
			players.add(player); // adds to the end
			System.out.println(i + ". " + str);
			i++;
		}
		System.out.println();
	}
	
	private void startGame() {
		placePlayersAtFirstSquare();
		winner = null;
	}

	private void placePlayersAtFirstSquare() {
		for (Player player : players) {
			board.firstSquare().enter(player);
		}
	}

	private boolean notOver() {
		return winner == null;
	}

	@Override
	public String toString() {
		String str = new String();
		for (Player player : players) {
			str += player.getName() + " is at square " + (player.position() + 1) + "\n";
		}
		return str;
	}

	Player currentPlayer() {
		assert players.size() > 0;
		return players.peek();

	}
}