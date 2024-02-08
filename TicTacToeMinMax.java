package application;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import java.util.Optional;

public class TicTacToeMinMax extends Application {
	Board[][] Boards = new Board[3][3];// size of the board that is 3*3
	Label currplyr = new Label("");// label to represent the current player
	Label rondlbl = new Label("");// lable to represent the round lable
	Label reslbl = new Label("Score:" + "\n" + "You 0 - Computer 0   ");// lable that will represent the result as score
	Button reset = new Button("Reset");// button to reset the current tgame
	int rounds = 0; // initialize the rounds number as 0
	int curplyrscr = 0;// initializethe current player score ans 0
	int compscr = 0;// initialize the current computer score as 0 at first
	int rondcount = 0;// round counter to make it reach the rounds number that i choose
	boolean gameOver = false;// initialize the boolean value of the game over as false at the begining of the
								// game 
	boolean plyrturn = true;// initialize the boolean value of the player turn that it will be at the
							// begining of the gaem
	BorderPane bp;// border pane to put all of the component on them
	private String plyrname = "You";
	private TextArea a = new TextArea();// text area to save th eprobability ofthe plaing
	final int win = 1;// winning for the computer and losing for the playen
	final int lose = -1;// lose for the computer and winning for the player
	final int draw = 0;// draw between the player and the compuet
	int bestscrn = Integer.MAX_VALUE;
          
	@Override
	public void start(Stage stage) throws Exception {
		bp = new BorderPane();
		roundsNum();// here to set the number of the round that the player will play at the
					// beginning
		GridPane gp = new GridPane();// grid pane for the tic tac toe board to represent the cell on ut
		for (int i = 0; i < Boards.length; i++) {// create board of 3*3 size
			for (int j = 0; j < Boards[i].length; j++) {
				Boards[i][j] = new Board();// each place i will create the new cell object to create the board
				gp.add(Boards[i][j], j, i);// add it on the specific place on the grid pane
				Board c = Boards[i][j];// save each cell in var called c
				c.setOnMouseClicked(e -> clickboard(c));// handle when i want to cick on each cell
			}
		}
		reset.setStyle("-fx-background-color: red; -fx-text-fill: white; -fx-font-weight: bold;");// reset button style
		reset.setOnAction(e -> resetGame());// set on action to the reset button
		HBox rore = new HBox(10);// so save in it the round lable and the reset button
		rore.setAlignment(Pos.CENTER);
		rore.getChildren().addAll(rondlbl, reset);
		gp.setAlignment(Pos.CENTER);
		BorderPane.setAlignment(currplyr, Pos.CENTER);
		BorderPane.setAlignment(reslbl, Pos.CENTER);
		VBox vb = new VBox();
		vb.getChildren().addAll(reslbl, rore);
		vb.setAlignment(Pos.CENTER);
		bp.setCenter(gp);
		bp.setRight(vb);
		BorderPane.setAlignment(reslbl, Pos.CENTER);
		bp.setStyle("-fx-background-color: green;");
		reslbl.setTextFill(Color.WHITE);
		reslbl.setFont(Font.font("Arial", FontWeight.BOLD, 16)); // You can adjust the font size (16 in this case)
		reslbl.setLineSpacing(10);
		rondlbl.setText("Round: 1 / " + rounds); // Set initial round number
		rondlbl.setTextFill(Color.WHITE);
		rondlbl.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		Label turnLabel = new Label("Turn: Your Turn");
		turnLabel.setTextFill(Color.WHITE);
		turnLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		VBox turnBox = new VBox(turnLabel);
		turnBox.setAlignment(Pos.CENTER);
		bp.setTop(turnBox);
		a.setStyle("-fx-control-inner-background: green; -fx-text-fill: white;");
		a.setPrefSize(200, 400);
		VBox moveprob = new VBox();
		Label l = new Label("The Move Probabilities:");
		l.setStyle("-fx-background-color: red; -fx-text-fill: white; -fx-font-weight: bold;");
		moveprob.getChildren().addAll(l, a);
		bp.setLeft(moveprob);
		Scene scene = new Scene(bp, 800, 600);
		stage.setScene(scene);
		stage.setTitle("Tic Tac Toe");
		stage.show();
		// playComputer(); // make the computer play
	}
//***********************************methods **************************************************//

	private void roundsNum() {// thes method is to choose the number of the round that am gonna play
		VBox vbox = new VBox();
		vbox.setAlignment(Pos.CENTER);
		vbox.setSpacing(10);
		Image image = new Image("C:\\Users\\Yuna\\eclipse-workspace\\TicTacToe2\\src\\application\\download.jpg");
		ImageView imageView = new ImageView(image);
		TextField rndtxt = new TextField();
		rndtxt.setPromptText("enter the rounds here!!!");// gthe prompt initial value
		Label rondlbl = new Label(" The Number Of Rounds:");
		rondlbl.setStyle("-fx-text-fill: white; -fx-font-weight: bold;");
		vbox.getChildren().addAll(imageView, rondlbl, rndtxt);
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);// create the new alert as a window instead of tge stages
		alert.setTitle("Number of Rounds :)");
		alert.setHeaderText(null);
		DialogPane dialogPane = alert.getDialogPane();// get the specific dialog content for the alert that use to apply
														// the java stylr code
		dialogPane.setContent(vbox);
		dialogPane.setStyle("-fx-background-color: green;");
		dialogPane.setPrefSize(400, 200);// the alert dialog size
		Button okbtn = (Button) alert.getDialogPane().lookupButton(ButtonType.OK);
		okbtn.setStyle("-fx-background-color: lightgreen; -fx-text-fill: white; -fx-font-weight: bold;");
		Button cnclbtn = (Button) alert.getDialogPane().lookupButton(ButtonType.CANCEL);
		cnclbtn.setStyle("-fx-background-color: lightgreen; -fx-text-fill: white; -fx-font-weight: bold;");
		Optional<ButtonType> result = alert.showAndWait();// optional that what i choose from cancel or ok
		if (result.isPresent() && result.get() == ButtonType.OK) {// if i choose ok then
			rounds = Integer.parseInt(rndtxt.getText());// save the content of the txtfield on rounds var
		} else {
			Platform.exit();// else to exit the platform that am in
		}
		ChoiceDialog<String> dialog = new ChoiceDialog<>(plyrname, plyrname, "Computer");// choice dialog that make
																							// choose who will start
																							// the playing at first
		updatecurrplyr();// update the current player
	}

	private void playComputer() {// method that make controls on the computer move
		if (!plyrturn && !gameOver) {// if its not player turn and not game over
			int bstmv = getBestMove(); // make the best move to go depends on the minimax
			if (bstmv != -1) { // if the best move not equals to -1 that means lose
				int row = bstmv / 3; // extract best move for the row
				int col = bstmv % 3; // extract best move for the column
				int celnum = row * 3 + col + 1;// equation to get the cell number that i will move in
				printprob();// method to print out th eprobability for the computer moves
				appendprob("Computer Move: Cell " + celnum);// append on the text area the cell num
				a.appendText("**************************");
				Boards[row][col].setText("O");// set the place that move on to o
				if (checkWining("O") == true) {// if the computer is winning
					endRound("Computer");// end the round with the computer win
				} else {
					plyrturn = true;// make the player turn
					updatecurrplyr();// update the current player
				}
			}
		}
	}

	private int getBestMove() {
		int bestscr = -2;
		int bestMove = -1;
		for (int i = 0; i < Boards.length; i++) {
			for (int j = 0; j < Boards[i].length; j++) {
				if (Boards[i][j].isEmpty()) {
					Boards[i][j].setText("O");
					int score = minimax(Boards, false);
					Boards[i][j].setText("");
					if (score > bestscr) {
						bestscr = score;
						bestMove = i * 3 + j;
					}
				}
			}
		}
		return bestMove;
	}

	private void appendprob(String text) {// this method that takes sting text helps to appentd the
											// probabilities on the text
											// area for the computer player
		a.appendText(text + "\n");
	}

	private void printprob() {// this method is used to pirnt out the probability of te computer moves
		System.out.println("The Moving Probabilities:");
		for (int i = 0; i < Boards.length; i++) {// walk throught the boead cells
			for (int j = 0; j < Boards[i].length; j++) {
				if (Boards[i][j].isEmpty() == true) {// check if the cell is empty
					Boards[i][j].setText("O");// set the initial value of the text is o
					int prob = minimax(Boards, false); // the probability is 1 or 0 or -1 depends on the mininmax and
														// the maximizing is false the its the player turn
					Boards[i][j].setText("");// undu the move that i make
					int celnum = i * 3 + j + 1;// this equation to get the cell number that the computer goung to
					a.appendText("Cell " + celnum + "(" + (i + 1) + ", " + (j + 1) + ") Probability: " + prob + "\n");
				}
			}
		}
	}

	private int minimax(Board[][] board, boolean isMax) {// this method is the minimax that get the mest move my giving
															// 0,1,-1
		if (checkWining("X") == true) {// if the player x the winner the comuetr will be lose
			return lose;// return lose as its -1
		} else if (checkWining("O") == true) {// if the playero the winner the comuetr will be winner
			return win;// return the win as its 1
		} else if (!hasEmpty()) {// if ther eis no empty place at the board none of the player win
			return draw;// drw that its 0
		}

		if (isMax == true) {// if it maximizing for the computer trn o turn
			return max(board);// return then the maximizing value
		} else {
			return min(board);// return the minimizing value x turn
		}
	}

	private int max(Board[][] board) {// this method is to get the nmaximizing value of the computer player that is o
		int bestscrx = -3;// make min value to compare and maximize with the score
		for (int i = 0; i < board.length; i++) {// walk throught the board cell
			for (int j = 0; j < board[i].length; j++) {
				if (board[i][j].isEmpty() == true) {// if the cell is empty
					board[i][j].setText("O");// setthe text to o
					int score = minimax(board, false);// get score bu using minimax and the player is turn
					board[i][j].setText("");// undo the move
					bestscrx = Math.max(score, bestscrx);// get the max score between the score from the min max and the
															// best score from the min value
				}
			}
		}
		return bestscrx;// return the best score as its the maximized value
	}

	private int min(Board[][] board) {// this method is to get the minimizing value of the computer player that is x
		int bestscrn = 3;// make max value to compare and maximize with the score
		for (int i = 0; i < board.length; i++) {// walk throught the board cell
			for (int j = 0; j < board[i].length; j++) {
				if (board[i][j].isEmpty() == true) {
					board[i][j].setText("X");// setthe text to x
					int score = minimax(board, true);// get score bu using minimax and the compuetr is turn
					board[i][j].setText("");// undo the move
					bestscrn = Math.min(score, bestscrn);// get the min score between the score from the min max and the
					// best score from the min value
				}
			}
		}
		return bestscrn;// return the best score as its the minimized value
	}

	private void clickboard(Board Board) {// this method is to handle the clicl on the board
		if (plyrturn && !gameOver && Board.isEmpty()) {// if its the player turn and not game over and the board is
														// empty
			Board.setText("X");// set text to x
			if (checkWining("X") == true) {// if the player is winning
				endRound("You");// make the round end and make the player is the winner
			} else if (!hasEmpty()) { // id there is no empty blocks in the board
				endRound("Draw");// make the round end and the result is draw
			} else {// other it will be the computer turn
				plyrturn = false;// make the player turn is false
				updatecurrplyr();// update the current player t computer
				playComputer();// make the computer play
			}
		}
	}

	private void endRound(String winner) {// this method used to handle the end of the round
		rondcount++;// increament the round counter
		updateResults(winner);//// update the result for the winner
		Alert alert = new Alert(Alert.AlertType.NONE);// create a new alert and style it with dialog
		alert.setTitle("Round Result :)");// title for the alert
		alert.setHeaderText(null);
		Label cntntlbl;// content of the text in lable style
		if (winner.equals("You") == true) {// if the player is winning
			cntntlbl = new Label("Round " + rondcount + " Result: You Win!");// print out that the player is the winner
		} else if (winner.equals("Computer") == true) {// if the computer is winning
			cntntlbl = new Label("Round " + rondcount + " Result: Computer Wins!");// print out that he is the winner
		} else {// other if non of the player or the computer wins
			cntntlbl = new Label("Round " + rondcount + " Result: It's a Draw!");// print out that its a draw between
																					// them
		}
		cntntlbl.setStyle("-fx-text-fill: white; -fx-font-weight: bold;");// styling the lable
		ButtonType oktype = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);// the ok button type
		Button okbtn = new Button("OK");// new button for the ok
		okbtn.setStyle("-fx-background-color: lightgreen; -fx-text-fill: white; -fx-font-weight: bold;");// stylr it
		okbtn.setOnAction(e -> alert.setResult(oktype));// set on action to do ok work
		HBox buttonsBox = new HBox(10, okbtn);// hbox for the ok
		buttonsBox.setAlignment(Pos.CENTER);// to center
		DialogPane dialogPane = alert.getDialogPane();// dialog pane for the styling
		dialogPane.setContent(new VBox(10, cntntlbl, buttonsBox));
		dialogPane.setStyle("-fx-background-color: green;");
		dialogPane.setPrefSize(400, 200);
		alert.setResultConverter(buttonType -> buttonType == oktype ? oktype : null);
		alert.showAndWait();
		if (rondcount < rounds) {// if the round count less than the round that i choose
			resetGame();// reset the game
		} else {// other wise
			finalResult();// show the final result
			playAgain();// play again
		}
	}

	private void finalResult() {// this method is to show the final result
		Alert alert = new Alert(Alert.AlertType.NONE);// create a new alert to displey the final result on it
		alert.setTitle("Game Over :(");
		alert.setHeaderText(null);
		Label cntntlbl = new Label("Final Results:\nYou: " + curplyrscr + " - Computer: " + compscr);
		cntntlbl.setStyle("-fx-text-fill: white; -fx-font-weight: bold;");
		ButtonType oktype = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
		Button okbtn = new Button("OK");// create a ok button
		okbtn.setStyle("-fx-background-color: lightgreen; -fx-text-fill: white; -fx-font-weight: bold;");
		okbtn.setOnAction(e -> alert.setResult(oktype));// set on action to the ok button
		HBox buttonsBox = new HBox(10, okbtn);// hbox for the ok button
		buttonsBox.setAlignment(Pos.CENTER);
		DialogPane dialogPane = alert.getDialogPane();// dialog pane to style the comntent of the alert
		dialogPane.setContent(new VBox(10, cntntlbl, buttonsBox));
		dialogPane.setStyle("-fx-background-color: green;");
		dialogPane.setPrefSize(400, 200);
		alert.setResultConverter(buttonType -> buttonType == oktype ? oktype : null);
		alert.showAndWait();
	}

	private void updateResults(String winner) {/// this method is to update the winner
		if (winner.equals("You") == true) {// if the winner is equels to you then the humen player is the winner
			curplyrscr++;// so increase the score by 1
		} else if (winner.equals("Computer") == true) {// if the winner is equels to you then the computer player is the
														// winner
			compscr++;// so increase the score by 1
		}

		reslbl.setText("Score: You " + curplyrscr + " - Computer " + compscr);// update the value of the scores on the
																				// scores lable
		rondlbl.setText("Round: " + rondcount + " / " + rounds);// put the round counter
		rondlbl.setTextFill(Color.WHITE);// set styling for th efont
		rondlbl.setFont(Font.font("Arial", FontWeight.BOLD, 16));
	}

	private void resetGame() { // this method is to reset the game for the new round
		if (rondcount < rounds) {// if the round counter is less that the rounds that i choose
			gameOver = false;// make it not game over
			plyrturn = true;// make it the player turn
			updatecurrplyr();// update the current player
			for (int i = 0; i < Boards.length; i++) {// walk throught the board
				for (int j = 0; j < Boards[i].length; j++) {
					Boards[i][j].setText("");// clear its content
				}
			}
			currplyr.setText("Your Turn");// set lable to your turn
			reslbl.setText("Score: You " + curplyrscr + " - Computer " + compscr);// set the player and the computer
																					// score to 0
			rondlbl.setText("Round: " + (rondcount + 1) + " / " + rounds); // update the number of the round here
			playComputer();// make the computer play its turn
		} else {
			playAgain();// else ask the user if he wanna play agaim
		}
	}

	private void updatecurrplyr() {
		if (plyrturn == true) {// if itd the humen player
			currplyr.setText("Turn: " + "Your Turn");// display this lable
		} else {// if its not the player turn its the computer turn then
			currplyr.setText("Turn: Computer's Turn");// display this lable
		}
	}

	private void playAgain() {// this method to ask the user if he want to play the game again
		Alert alert = new Alert(Alert.AlertType.NONE);// create new alert and style it using the dialog
		alert.setTitle("Play Again :)");
		alert.setHeaderText(null);
		Label contentLabel = new Label("Do you want to play this again?");
		contentLabel.setStyle("-fx-text-fill: white; -fx-font-weight: bold;");
		ButtonType yestype = new ButtonType("Yes", ButtonBar.ButtonData.YES);// make the yes button to type yes
		ButtonType notype = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);// make the no button to type close
		// and cancle
		Button yesBtn = new Button("Yes");// crete a new button for yes
		yesBtn.setStyle("-fx-background-color: lightgreen; -fx-text-fill: white;");
		yesBtn.setOnAction(e -> alert.setResult(yestype));// make it as type yes
		Button noBtn = new Button("No");// crete a new button for no
		noBtn.setStyle("-fx-background-color: lightgreen; -fx-text-fill: white;");
		noBtn.setOnAction(e -> alert.setResult(notype));// make it as type close and cancle
		HBox buttonsBox = new HBox(10, yesBtn, noBtn);
		buttonsBox.setAlignment(Pos.CENTER);
		DialogPane dialogPane = alert.getDialogPane();// create the dialog pane to costom the styles on it
		dialogPane.setContent(new VBox(10, contentLabel, buttonsBox));
		dialogPane.setStyle("-fx-background-color: green;");
		dialogPane.setPrefSize(400, 200);// specific size for the alert dialog pane
		alert.setResultConverter(buttonType -> {// set convertor for the butons
			if (buttonType == yestype) {// if the buttons tipe is equals to the yes type then return iit
				return yestype;
			} else {
				return notype;// other wise return the notype
			}
		});
		Optional<ButtonType> result = alert.showAndWait();// show the alert and wait fort the user action
		if (result.isPresent() && result.get() == yestype) {// if i press the yes button type
			Platform.runLater(() -> {
				Stage stage = (Stage) reslbl.getScene().getWindow();
				stage.close();// close and restart the game all over again
				try {
					new TicTacToeMinMax().start(new Stage());// display this class all over again
				} catch (Exception e) {// displeay an error message
					e.printStackTrace();
				}
			});
		} else {// if i press the no button
			Stage stage = (Stage) reslbl.getScene().getWindow();
			stage.close();// close the windows
		}
	}

	boolean checkWining(String c) {// this method to check if there is an winner or not
		for (int i = 0; i < 3; i++) {
			if (Boards[i][0].equals(c) && Boards[i][1].equals(c) && Boards[i][2].equals(c))
				return true;
			if (Boards[0][i].equals(c) && Boards[1][i].equals(c) && Boards[2][i].equals(c))
				return true;
		}
		if (Boards[0][0].equals(c) && Boards[1][1].equals(c) && Boards[2][2].equals(c))
			return true;
		if (Boards[0][2].equals(c) && Boards[1][1].equals(c) && Boards[2][0].equals(c))
			return true;
		return false;
	}

	public boolean hasEmpty() {// this method checks if the board has an empty space on it
		for (int i = 0; i < Boards.length; i++) {// her it will walk throught the board cell
			for (int j = 0; j < Boards[i].length; j++) {
				if (Boards[i][j].isEmpty()) {// check if they are empty
					return true;
				}
			}
		}
		return false;
	}

	public static void main(String[] args) {
		launch(args);
	}

}
