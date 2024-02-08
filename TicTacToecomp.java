package application;

import java.util.Optional;

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

public class TicTacToecomp extends Application {
	Board[][] Boards = new Board[3][3];// size of the board that is 3*3
	Label currplyr = new Label("");// label to represent the current player
	Label rondlbl = new Label("");// lable to represent the round lable
	Label reslbl = new Label("Score:" + "\n" + "You 0 - Computer 0   ");// lable that will represent the result as score
	Button reset = new Button("Reset");// button to reset the current tgame
	int rounds = 0; // initialize the rounds number as 0
	int curplyrscr = 0;// initializethe current player score ans 0
	int compscr = 0;// initialize the current computer score as 0 at first
	int rondcount = 0;// round counter to make it reach the rounds number that i choose
	boolean gameovr = false;// initialize the boolean value of the game over as false at the begining of the
							// game
	boolean plyrturn = true;// initialize the boolean value of the player turn that it will be at the
							// begining of the gaem
	BorderPane bp;// border pane to put all of the component on them
	private String plyrname = "You";// the player name

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
		VBox vb = new VBox();// to save the result lable and the rore hbox in it
		vb.getChildren().addAll(reslbl, rore);
		vb.setAlignment(Pos.CENTER);
		bp.setCenter(gp);
		bp.setRight(vb);
		bp.setStyle("-fx-background-color: green;");
		reslbl.setTextFill(Color.WHITE);
		reslbl.setFont(Font.font("Arial", FontWeight.BOLD, 16));
		reslbl.setLineSpacing(10);
		rondlbl.setText("Round: 1 / " + rounds); // here is to set the initial lnumber of rands then when playing keep
													// tracking
		rondlbl.setTextFill(Color.WHITE);
		rondlbl.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		VBox turnBox = new VBox(currplyr);
		turnBox.setAlignment(Pos.CENTER);
		currplyr.setTextFill(Color.WHITE);
		currplyr.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		bp.setTop(turnBox);
		Scene scene = new Scene(bp, 600, 400);
		stage.setScene(scene);
		stage.setTitle("Tic Tac Toe :)");
		stage.show();
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
		plyrname();// call the plauer name to update the current player name
		ChoiceDialog<String> dialog = new ChoiceDialog<>(plyrname, plyrname, "Computer");// choice dialog that make
																							// choose who will start
																							// the playing at first
																							// .
		dialog.setTitle("Choose Starting Player :)");
		dialog.setHeaderText(null);
		dialog.setContentText("Select who will start the game:");
		DialogPane dialogPan = dialog.getDialogPane();// dialog pane to set styling on the choice dialog
		dialogPan.setStyle("-fx-background-color: green;");
		dialogPan.setPrefSize(400, 200);
		Button okbotn = (Button) dialog.getDialogPane().lookupButton(ButtonType.OK);// ok button typ e
		okbotn.setStyle("-fx-background-color: lightgreen; -fx-text-fill: white; -fx-font-weight: bold;");
		Button cncelbtn = (Button) dialog.getDialogPane().lookupButton(ButtonType.CANCEL);// cancel button type
		cncelbtn.setStyle("-fx-background-color: lightgreen; -fx-text-fill: white; -fx-font-weight: bold;");
		dialog.showAndWait().ifPresent(choice -> {
			if (choice.equals(plyrname) == true) {// if the choice equals to the player name
				plyrturn = true;// then it will be the player turn
			} else {
				plyrturn = false;// other wise the player turn will be false
				computerPlayer();// the computer player turn
			}
			updatecurrplyr();// update the current player
		});
	}

	private void plyrname() {// method to get the player name
		TextInputDialog dialog = new TextInputDialog();// text field dialog to write the name in it
		dialog.setTitle("Player Name :)");
		dialog.setHeaderText(null);
		dialog.setContentText("Enter your name:");// content of the text
		DialogPane dialogPane = dialog.getDialogPane();// dialog panee to set styling
		dialogPane.setStyle("-fx-background-color: green;");
		Button okbtn = (Button) dialogPane.lookupButton(ButtonType.OK);// ok button type
		okbtn.setStyle("-fx-background-color: lightgreen; -fx-text-fill: white; -fx-font-weight: bold;");
		Button cnclbtn = (Button) dialogPane.lookupButton(ButtonType.CANCEL);// cancel button type
		cnclbtn.setStyle("-fx-background-color: lightgreen; -fx-text-fill: white; -fx-font-weight: bold;");
		Optional<String> result = dialog.showAndWait();
		plyrname = result.orElse("You"); // put the defolt value if i didnt choose name to you
		updatecurrplyr(); // update the current player name
	}

	private void clickboard(Board Board) {// handl eth eclicking actions on the board
		if (plyrturn && !gameovr && Board.isEmpty()) {// if its the player turn and still in the round not the game over
														// and the board is emty
			Board.setText("X");// set the cell that i will press on to X
			if (checkWining("X") == true) {// check if when add the X i win or not
				endRound(plyrname);// if win then handle to make the round be ended and i ll be the winner
			} else if (!hasEmpty()) { // if ther e is no place to move that represent the draw betwee the players
				endRound("Draw");// if not empty then handle to make draw between them
			} else {
				plyrturn = false;// disable the player turn to false to make the compyter play its turn
				updatecurrplyr();// update the current player to be the next player
				computerPlayer();// make the computer player turn
			}
		}
	}

	private void computerPlayer() {// it will make the computer turn moves
		if (!plyrturn && !gameovr) {// if its not the humen player turn and not game over then
			new Thread(() -> {// crete new thread that delay 2 seconds
				try {
					Thread.sleep(2000); // delay and sleeep for 2 seconds
				} catch (InterruptedException e) {
					e.printStackTrace();// display an error message
				}

				Platform.runLater(() -> computerMove());// sfter the 2 second meke the computer turn move
			}).start();// start the threads
		}
	}

	private void computerMove() {// this method that make the computer moves
		int[] move = Board.random_option(Boards, "O");// make legal random move for the O as a computer move
		if (move != null) {// if there is move choosen
			int i = move[0];// move for the row
			int j = move[1];// move for the colomn
			Boards[i][j].setText("O");// set in that position the value of the O
			if (checkWining("O") == true) {// if the O is wining
				endRound("Computer");// then handl eto end th eround that am in
			} else {
				plyrturn = true;// else make the player turn
				updatecurrplyr();// update the current player
			}
		}

	}

	private void updatecurrplyr() {// method to update the current player
		if (plyrturn == true) {// if itd the humen player
			currplyr.setText("Turn: " + plyrname + " Turn");// display this lable
		} else {// if its not the player turn its the computer turn then
			currplyr.setText("Turn: Computer's Turn");// display this lable
		}
	}

	private void endRound(String winner) {// method to ent thr round
		rondcount++;// increment te round counter
		updateResult(winner);// update the result
		if ((curplyrscr >= rounds / 2 + 1) || (compscr >= rounds / 2 + 1)) {// check if the player is close to win
			// Ask players if they want to continue playing
			boolean continuePlaying = askToContinue();//ask to playing   
			if (!continuePlaying) {
				gameovr = true;
				finalResult();
				playAgain();
				return;
			}
		}

		Alert alert = new Alert(Alert.AlertType.NONE);
		alert.setTitle("Round Result :)");
		alert.setHeaderText(null);
		Label contentLbl;

		if (winner.equals(plyrname)) {
			contentLbl = new Label("Round " + rondcount + " Result: You Win!");
		} else if (winner.equals("Computer")) {
			contentLbl = new Label("Round " + rondcount + " Result: Computer Wins!");
		} else {
			contentLbl = new Label("Round " + rondcount + " Result: It's a Draw!");
		}

		contentLbl.setStyle("-fx-text-fill: white; -fx-font-weight: bold;");
		ButtonType oktype = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
		Button okbtn = new Button("OK");
		okbtn.setStyle("-fx-background-color: lightgreen; -fx-text-fill: white; -fx-font-weight: bold;");
		okbtn.setOnAction(e -> alert.setResult(oktype));
		HBox buttonsBox = new HBox(10, okbtn);
		buttonsBox.setAlignment(Pos.CENTER);
		DialogPane dialogPane = alert.getDialogPane();
		dialogPane.setContent(new VBox(10, contentLbl, buttonsBox));
		dialogPane.setStyle("-fx-background-color: green;");
		dialogPane.setPrefSize(400, 200);
		alert.setResultConverter(buttonType -> {
			if (buttonType == oktype) {
				return oktype;
			} else {
				return null;
			}
		});

		alert.showAndWait();

		if (rondcount < rounds) {
			resetGame();
		} else {
			finalResult();
			playAgain();
		}
	}

	private boolean askToContinue() {
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle("Continue Playing?");
		alert.setHeaderText(null);
		alert.setContentText("One of the players is close to winning. Do you want to continue playing?");

		ButtonType yesButton = new ButtonType("Yes");
		ButtonType noButton = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
		alert.getButtonTypes().setAll(yesButton, noButton);

		DialogPane dialogPane = alert.getDialogPane();
		dialogPane.setStyle("-fx-background-color: green;");
		Button yesButtonNode = (Button) alert.getDialogPane().lookupButton(yesButton);
		yesButtonNode.setStyle("-fx-background-color: lightgreen; -fx-text-fill: white; -fx-font-weight: bold;");
		Button noButtonNode = (Button) alert.getDialogPane().lookupButton(noButton);
		noButtonNode.setStyle("-fx-background-color: lightgreen; -fx-text-fill: white; -fx-font-weight: bold;");

		Optional<ButtonType> result = alert.showAndWait();
		return result.isPresent() && result.get() == yesButton;
	}

	private void finalResult() {// method that display the final result for the game
		Alert alert = new Alert(Alert.AlertType.NONE);// create a new alert to display the final result in it
		alert.setTitle("The FInal Result :)");
		alert.setHeaderText(null);
		Label contentLbl = new Label("Final Results:\nYou: " + curplyrscr + " - Computer: " + compscr);// lable to
																										// display the
																										// final result
		contentLbl.setStyle("-fx-text-fill: white; -fx-font-weight: bold;");
		ButtonType oktype = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);// creat ea custom ok button
		Button okbtn = new Button("OK");
		okbtn.setStyle("-fx-background-color: lightgreen; -fx-text-fill: white; -fx-font-weight: bold;");
		okbtn.setOnAction(e -> alert.setResult(oktype));// ok to make sure or permetion for ur dissision
		HBox buttonsBox = new HBox(10, okbtn);
		buttonsBox.setAlignment(Pos.CENTER);
		DialogPane dialogPane = alert.getDialogPane();// create a new dialog pane to make custom changes on the alert
		dialogPane.setContent(new VBox(10, contentLbl, buttonsBox));// put the content on them
		dialogPane.setStyle("-fx-background-color: green;");
		dialogPane.setPrefSize(400, 200);// pref size for the dialog
		alert.setResultConverter(buttonType -> {// make the ok button as the alert result
			if (buttonType == oktype) {// if the button type equals to the ok type then return the ok type
				return oktype;
			} else {
				return null;// other wise return null
			}
		});
		alert.showAndWait();// and ask the player if he want to play again
	}

	private void updateResult(String winner) {// this method use to update the results and display them
		if (winner.equals(plyrname) == true) {// if the winner is the humen player then
			curplyrscr++;// increamnt the humen player score
		} else if (winner.equals("Computer") == true) {// else if the winner is the compyter
			compscr++;// increase the computer score
		}

		reslbl.setText("Score: " + plyrname + curplyrscr + " - Computer " + compscr);// update the result for the
																						// score
		rondlbl.setText("Round: " + rondcount + " / " + rounds);// update the round s
		rondlbl.setTextFill(Color.WHITE);
		rondlbl.setFont(Font.font("Arial", FontWeight.BOLD, 16));
	}

	private void resetGame() {// method to reset the game for the new round
		if (rondcount < rounds) {// if the round counter is less than the number of round that i choose
			gameovr = false;// start the game that its not game over
			plyrturn = true;// set the player humen turn to true
			updatecurrplyr();// update the current player

			for (int i = 0; i < Boards.length; i++) {// wlak throught all the cells to clear the content that are in it
				for (int j = 0; j < Boards[i].length; j++) {
					Boards[i][j].setText("");// clear the content
				}
			}

			currplyr.setText(plyrname + "Turn");// set as its the humen turn
			reslbl.setText("Score: " + plyrname + curplyrscr + " - Computer " + compscr);
			rondlbl.setText("Round: " + (rondcount + 1) + " / " + rounds); // update the number of the round

			computerPlayer();// then it comes the computer turn after the humen turn
		} else {
			playAgain();// her e too ask the user if he want to play again
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
					new TicTacToecomp().start(new Stage());// display this class all over again
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