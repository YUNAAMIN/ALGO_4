package application;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
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

public class TicTacToe2vs extends Application {// this class represents the tic tac toe game that two player play in it
												// for X and O
	Board[][] Boards = new Board[3][3];// size of the board that is 3*3
	Label currplyr = new Label("");// label to represent the current player
	Label rondlbl = new Label("");// lable to represent the round lable
	Label reslbl = new Label("Score:" + "\n" + "You 0 - Computer 0   ");// lable that will represent the result as score
	Button reset = new Button("Reset");// button to reset the current tgame
	int rounds = 0; // initialize the rounds number as 0
	int curplyrscr = 0;// initializethe current player score ans 0
	int securrscr = 0;// initialize the second player score as 0
	int roundCount = 0;// the rounds counter
	boolean gameovr = false;// initialize the boolean value of the game over as false at the begining of the
							// game
	boolean plyrturn = true;// initialize the boolean value of the player turn that it will be at the
							// begining of the gaem
	String playerXName = "Player X";// the player X name initiallize value 
	String playerOName = "Player O";// the player O name initiallize value
	BorderPane bp;// border pane to put all of the component on them
	Label turnLabel = new Label("Turn: " + playerXName + "'s Turn"); // Initialize turnLabel here

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
				c.setOnMouseClicked(e -> clickBoard(c));// handle when i want to cick on each cell
			}
		}
		reset.setStyle("-fx-background-color: red; -fx-text-fill: white; -fx-font-weight: bold;");// reset button style
		reset.setOnAction(e -> resetGame());// set on action to the reset button

		HBox rore = new HBox(10);// so save in it the round lable and the reset button
		rore.setAlignment(Pos.CENTER);
		rore.getChildren().addAll(rondlbl, reset);

		String playerName;/// teh player name
		if (plyrturn == true) {// see the match with name will be its turn
			playerName = playerXName;// it will be the player x turn
		} else {
			playerName = playerOName;// it will be the player o turn
		}
		turnLabel = new Label("Turn: " + playerName + "'s Turn");
		turnLabel.setTextFill(Color.WHITE);
		turnLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		VBox turnBox = new VBox(turnLabel);
		turnBox.setAlignment(Pos.CENTER);
		bp.setTop(turnBox);
		updatecurrplyr();// update the current player
		updateResults("");// update the result to ""
		reset.setOnAction(e -> resetGame());// set on action on the reset button in the game
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
		reslbl.setFont(Font.font("Arial", FontWeight.BOLD, 16));
		reslbl.setLineSpacing(10);
		rondlbl.setText("Round: 1 / " + rounds); // set the initial round number value
		rondlbl.setTextFill(Color.WHITE);
		rondlbl.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		Scene scene = new Scene(bp, 600, 400);
		stage.setScene(scene);
		stage.setTitle("Tic Tac Toe");
		stage.show();
	}
//***********************************methods **************************************************//

	private void roundsNum() {// method to get the number of the rounds that i want to play
		VBox vbox = new VBox();
		vbox.setAlignment(Pos.CENTER);
		vbox.setSpacing(10);
		Image image = new Image("C:\\Users\\Yuna\\eclipse-workspace\\TicTacToe2\\src\\application\\download.jpg");
		ImageView imageView = new ImageView(image);
		TextField rndtxt = new TextField();// text field to write the number of the rounds that i wnat to play init
		rndtxt.setPromptText("enter the rounds here!!!");// the initial value in the text field
		Label rondlbl = new Label(" The Number Of Rounds:");// lable tha show wht should i enter
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
		playersName();// here to get the players name that will play
	}

	private void playersName() {// this method is to get the players x and o names
		VBox vbox = new VBox();
		vbox.setAlignment(Pos.CENTER);
		vbox.setSpacing(10);
		Image image = new Image("C:\\Users\\Yuna\\eclipse-workspace\\ALGO_4\\src\\application\\download.png");
		ImageView imageView = new ImageView(image);
		vbox.getChildren().add(imageView);
		TextField plyrx = new TextField();// text field for the player x
		plyrx.setPromptText("Enter Player X's name");// prompt text on the text field
		TextField plyro = new TextField();// text field for the player o
		plyro.setPromptText("Enter Player O's name");// prompt text on the text field
		vbox.getChildren().addAll(new Label("Player X Name:"), plyrx, new Label("Player O Name:"), plyro);
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);// alert to display content that i need
		alert.setTitle("Confirmation");
		alert.setHeaderText(null);
		DialogPane dialogPane = alert.getDialogPane();// use the dialog pane to style the window and its content
		dialogPane.setContent(vbox);
		dialogPane.setStyle("-fx-background-color: green;");
		dialogPane.setPrefSize(400, 200);
		Button okButton = (Button) alert.getDialogPane().lookupButton(ButtonType.OK);// ok button type
		okButton.setStyle("-fx-background-color: lightgreen; -fx-text-fill: white; -fx-font-weight: bold;");
		Button cancelButton = (Button) alert.getDialogPane().lookupButton(ButtonType.CANCEL);// cancel button type
		cancelButton.setStyle("-fx-background-color: lightgreen; -fx-text-fill: white; -fx-font-weight: bold;");
		Optional<ButtonType> result = alert.showAndWait();// optional that what i choose from cancel or ok
		if (result.isPresent() && result.get() == ButtonType.OK) {// if i choos the ok button
			String playerXName;
			if (plyrx.getText().isEmpty() == true) {// if the text field is empty then put the initial value
				playerXName = "Player X";// make the player turn to player x turn
			} else {
				playerXName = plyrx.getText();// other wise if its not empty then takethe content of the text field
			}
			String playerOName;
			if (plyro.getText().isEmpty() == true) {// if the text field is empty then put the initial value
				playerOName = "Player O";// mak ethe olayer turn to o
			} else {
				playerOName = plyro.getText();// other wise if its not empty then takethe content of the text field
			}
			startPlayer(playerXName, playerOName);// the one who i click who will start the game
		} else {
			Platform.exit();// other wise close the plat form if cancel or finished what i want
		}
	}

	private void startPlayer(String playerXName, String playerOName) {// this method choose which player will start at
																		// the first
		if (playerXName.isEmpty() == true) {// if the text field is empty
			this.playerXName = "Player X";// set the player x as the default value
		} else {
			this.playerXName = playerXName;// other wise take the vlau to it
		}
		if (playerOName.isEmpty()) {// if the text field is empty
			this.playerOName = "Player O";// set the player o as the default value
		} else {
			this.playerOName = playerOName;// other wise take the vlau to it
		}

		plyrturn = (playerXName.equals(this.playerXName));// here to choose whether true or false for matching the
															// players name
		updatecurrplyr();// update the current player
		ChoiceDialog<String> dialog = new ChoiceDialog<>(playerXName, playerXName, playerOName);// choice dialog to
																								// choose between the
																								// players who will
																								// start at first .
		dialog.setTitle("Choose Starting Player");
		dialog.setHeaderText(null);
		dialog.setContentText("Select who will start the game:");
		DialogPane dialogPane = dialog.getDialogPane();// dialog pane to set the window styling
		dialogPane.setStyle("-fx-background-color: green;");
		ButtonBar buttonBar = (ButtonBar) dialogPane.lookup(".button-bar");
		if (buttonBar != null) {
			buttonBar.setStyle("-fx-background-color: green;");
		}
		for (ButtonType buttonType : dialogPane.getButtonTypes()) {
			Button button = (Button) dialogPane.lookupButton(buttonType);
			if (button != null) {
				button.setStyle("-fx-background-color: lightgreen; -fx-text-fill: white;");
			}
		}
		dialog.showAndWait().ifPresent(choice -> {
			if (choice.equals(playerXName) == true) {
				plyrturn = true;// the player x start
			} else {
				plyrturn = false;// the player o starts
			}
			updatecurrplyr();// update the current player
		});
	}

	private void clickBoard(Board Board) {// this method for rhe click board
		if (!gameovr && Board.isEmpty()) {// if yhe board is empty and not game over
			if (plyrturn == true) {// mak thh eplayer x turn
				Board.setText("X");// set the text to x
			} else {
				Board.setText("O");// se6 the text to o
			}
			if (checkWining(plyrturn ? "X" : "O") == true) {// if the player o or x is the inning
				currplyr.setText("Player " + (plyrturn ? 'X' : 'O') + " Wins!");// make the player that win to winning
				updateResults(plyrturn ? "X" : "O");// update the result for the player turn
				roundResult();// give the round result
				if ((curplyrscr >= rounds / 2 + 1) || (securrscr >= rounds / 2 + 1)) {// see if one of the players is
																						// close to win
					boolean continuePlaying = askToContinue();// ask if want to continue
					if (!continuePlaying) {// not continue playing
						gameovr = true;// make game over
						finalResults();// give the final result
						playAgain();// ask to plaing again
						return;// return to continue all the in the method e
					}
				}

				if (roundCount < rounds) {// if the roundcounter is less than the round number that i choose
					newRound();// open a new round
				} else {// else
					finalResults();// give the final result
					playAgain();// ask the player if want to play again
				}
			} else {// the other player o
				plyrturn = !plyrturn;// make the playe lo turn
				updatecurrplyr();// update the layer
				if (!hasEmpty()) {// if the board full and dont have an empty
					currplyr.setText("Game Over - It's a Draw!");// print out that draw
					roundResult();// give the round resut
					if (roundCount < rounds) {// if the roundcounter is less than the round number that i choose
						newRound();// open newound
					} else {
						finalResults();// give the final result
						playAgain();// ask the player if want to play again
					}
				}
			}
		}
	}

	private boolean askToContinue() {// method that ask to continue playing the game or not
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);// alert to display ythe comtent
		alert.setTitle("Continue Playing:)");
		alert.setHeaderText(null);
		alert.setContentText("One of the players is close to winning. Do you want to continue playing this game ?");
		ButtonType yesbtn = new ButtonType("Yes");// yes button
		ButtonType nobtn = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
		alert.getButtonTypes().setAll(yesbtn, nobtn);// no button
		DialogPane dialogPane = alert.getDialogPane();// dialog pane to ser styling
		dialogPane.setStyle("-fx-background-color: green;");
		Button yesButtonNode = (Button) alert.getDialogPane().lookupButton(yesbtn);
		yesButtonNode.setStyle("-fx-background-color: lightgreen; -fx-text-fill: white; -fx-font-weight: bold;");
		Button noButtonNode = (Button) alert.getDialogPane().lookupButton(nobtn);
		noButtonNode.setStyle("-fx-background-color: lightgreen; -fx-text-fill: white; -fx-font-weight: bold;");
		Optional<ButtonType> result = alert.showAndWait();
		return result.isPresent() && result.get() == yesbtn;
	}

	private void resetGame() {// this method is to reset the game
		if (!gameovr && roundCount <= rounds) {// if its not game over and the round count is less than or equals the
												// rounds that i choose
			plyrturn = true;// if its the first player turn
			updatecurrplyr();// update the current player
			for (int i = 0; i < Boards.length; i++) {// work throught the boards cells to clear its content
				for (int j = 0; j < Boards[i].length; j++) {
					Boards[i][j].setText("");// clear the coontent
				}
			}
			currplyr.setText(playerXName + "'s Turn");// make as the initial value as the player x turn
			roundCount++;// increament the round counter

			if (roundCount <= rounds) {// if the round counter is less than or equals to thee round that i choose
				rondlbl.setText("Round: " + roundCount + " / " + rounds);// set the round text to
			}
		}
		if (roundCount > rounds) {// if the round counter is greater than to thee round that i choose
			finalResults();// show the final result
			playAgain();// ask the player if he want to play again
		}
	}

	private void updateResults(String winner) {// this method to update the
		if (!winner.equals("")) {
			if (winner.equals("X")) {
				securrscr++; // increment the player x score
			} else if (winner.equals("O")) {
				curplyrscr++; // increment the player o score
			}
		}
		reslbl.setText("Score: " + playerXName + " " + securrscr + " - " + playerOName + " " + curplyrscr);
	}

	private void roundResult() {// method to show the round result
		Alert alert = new Alert(Alert.AlertType.INFORMATION);// create a new alert to show the components of the round
																// result
		alert.setTitle("Round Result :)");
		alert.setHeaderText(null);
		String winner = (plyrturn) ? "Player X" : "Player O";// save the winner if the player
		alert.setContentText("Round " + roundCount + " Result: " + winner + " Wins!");
		DialogPane dialogPane = alert.getDialogPane();// dialog pian to styling the window
		dialogPane.setStyle("-fx-background-color: green;");
		Label contentText = (Label) alert.getDialogPane().lookup(".content");
		contentText.setStyle("-fx-text-fill: white; -fx-font-weight: bold;");
		alert.showAndWait();// show the content
	}

	private void finalResults() {// this method is to show the final result
		Alert alert = new Alert(Alert.AlertType.INFORMATION);// create new alert to put the content on it
		alert.setTitle("Game Over :(");
		alert.setHeaderText(null);
		alert.setContentText("Final Results:\nPlayer X: " + securrscr + " - Player O: " + curplyrscr);// the content of
																										// the alert
		DialogPane dialogPane = alert.getDialogPane();// dialog pane to styling the window
		dialogPane.setStyle("-fx-background-color: green;");
		Label contentText = (Label) alert.getDialogPane().lookup(".content");
		contentText.setStyle("-fx-text-fill: white; -fx-font-weight: bold;");
		alert.showAndWait();// show the content of the alert
	}

	private void updatecurrplyr() {// method to update the current playerturn
		turnLabel.setText("Turn: " + (plyrturn ? playerXName : playerOName) + "'s Turn");
		currplyr.setText((plyrturn) ? playerXName + "'s Turn" : playerOName + "'s Turn");
	}

	private void newRound() {// this method is to start a new round
		roundCount++;// increamnt the round counter
		resetGame();// erset the game
		updatecurrplyr(); // update the current player name to put the correct name on the lable
	}

	private void playAgain() {// this method used to ask the player if want to play again
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);// create a new alert to put the content on it
		alert.setTitle("Play Again :)");
		alert.setHeaderText(null);
		alert.setContentText("Do you want to play again?");// the content of the alert
		ButtonType yesButton = new ButtonType("Yes");// yes button
		ButtonType noButton = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);// no button and its type is
																						// cancel or close
		alert.getButtonTypes().setAll(yesButton, noButton);// put the buttons
		DialogPane dialogPane = alert.getDialogPane();// dialog pane to styling the window and its content
		dialogPane.setStyle("-fx-background-color: green;");
		Button yesButtonNode = (Button) alert.getDialogPane().lookupButton(yesButton);// button yes with type ok
		yesButtonNode.setStyle("-fx-background-color: lightgreen; -fx-text-fill: white; -fx-font-weight: bold;");
		Button noButtonNode = (Button) alert.getDialogPane().lookupButton(noButton);// btton no with type no
		noButtonNode.setStyle("-fx-background-color: lightgreen; -fx-text-fill: white; -fx-font-weight: bold;");
		Optional<ButtonType> result = alert.showAndWait();// relut what we choose from the ok or cancel
		if (result.isPresent() && result.get() == yesButton) {// if i choosethe yes button
			Platform.runLater(() -> {// se what i choose as a result for pressing button
				Stage stage = (Stage) reslbl.getScene().getWindow();
				stage.close();
				try {// restart the game
					new TicTacToe2vs().start(new Stage());// open the window of the game agen
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
		} else {
			Stage stage = (Stage) reslbl.getScene().getWindow();
			stage.close();// close the windoew
		}
	}

	boolean checkWining(String c) {// this method is to check the whose the winnner from x or o
		for (int i = 0; i < 3; i++) {// for three element
			if (Boards[i][0].getText().equals(c) && Boards[i][1].getText().equals(c)
					&& Boards[i][2].getText().equals(c))
				return true;
			if (Boards[0][i].getText().equals(c) && Boards[1][i].getText().equals(c)
					&& Boards[2][i].getText().equals(c))
				return true;
		}
		if (Boards[0][0].getText().equals(c) && Boards[1][1].getText().equals(c) && Boards[2][2].getText().equals(c))
			return true;
		if (Boards[0][2].getText().equals(c) && Boards[1][1].getText().equals(c) && Boards[2][0].getText().equals(c))
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

	private void showErrorDialog(String message) {// this is to show an errer dialog
		Alert errorAlert = new Alert(Alert.AlertType.ERROR);
		errorAlert.setTitle("Error");
		errorAlert.setHeaderText(null);
		errorAlert.setContentText(message);// display the the error message
		errorAlert.showAndWait();
	}

	public static void main(String[] args) {
		launch(args);
	}
}