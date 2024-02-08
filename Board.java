package application;

import java.util.ArrayList;
import java.util.Random;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.text.Font;

public class Board extends Label {// this class represents the board of the tic tac toe game that will represents
									// the cells of the each compinent on the board
	Font font = new Font(50);// font for the x and o

	public Board() {
		this.setStyle("-fx-border-color: yellow; -fx-border-width: 10;");// the tic tac toe board borders color and
																			// width
		this.setPrefHeight(100);// cell height
		this.setPrefWidth(100);// cell width
		this.setAlignment(Pos.CENTER);// the aligment to the center position for the elemnt x and y
		this.setFont(font);// font for the x and o
	}

	public boolean isEmpty() {// theis metod is empty is check if the cell is empty or not
		if (getText().trim().equals("") == true) {// if equals nth then return is empty
			return true;
		} // if empty 
		return false;// i fnot empty then return false
	}

	private static boolean isEmptyCell(Board cell) {// check if the cell is empty or not by enterring the cell and see
													// if its null or not
		if (cell.isEmpty() == true) {// if cell is empty then return true
			return true;
		}
		return false;// otherwise return to me false that is not empty
	}

	public boolean equals(String c) {// see if the characters x or o
		if (this.getText().trim().equals(c)) {// if the character is equales to spacific srting thet i will choose
			return true;
		}
		return false;// not equals the character that i was choosen
	}

	public static int[][] legalMoves(Board[][] cells) {// method to determine the legal moves on the cells in the board
														// that take specific celll and return the legal moves
		int[][] legalMove; // the 2dimantional array thet save in it the legal move
		int count = 0;// counter for the each cell that i will seee if its empty cell
		// here to count the number of legal moves
		for (int i = 0; i < cells.length; i++) { // walk through the game board rows
			for (int j = 0; j < cells[i].length; j++) { // walk through the game board columns
				if (isEmptyCell(cells[i][j])) { // check if the cell is empty
					count++; // then increment count for each empty cell found it will be the legal moves
				}
			}
		}
		// here i will create the array of legal moves
		legalMove = new int[count][2]; // initialize the array with the size of the board
		count = 0; // reset the counter to re use it
		// here i will calculat the array with legal moves
		for (int i = 0; i < cells.length; i++) { // walk through the game board rows
			for (int j = 0; j < cells[i].length; j++) { // walk through the game board columns
				if (isEmptyCell(cells[i][j]) == true) { // here to check if the cell is empty
					legalMove[count][0] = i; // here to save the row index of the legal move
					legalMove[count][1] = j; // here to save the column index of the legal move
					count++; // move to the next index in the array
				}
			}
		}

		return legalMove; // return the 2D array that containing the legal moves
	}

	public static int[] random_option(Board[][] cells, String currentPlayer) {// this method is to get the random legal
																				// moves from the board that i play in
		int[][] legalMoves = legalMoves(cells); // get the array 2D of the legal move and save it in 2D of int array
		Random random = new Random();// new object of tge randim

		if (legalMoves.length > 0) { // check ifther is legal moves available
			return legalMoves[random.nextInt(legalMoves.length)]; // then if trie return the random valu eof the legal
																	// moves
		} else {
			return null; // other wise if thre arent any legal moves then return null
		}
	}

}
