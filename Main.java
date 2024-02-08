package application;

import java.io.IOException;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application {// main calculation in main class this screen make greeting to user and start
										// the program.
	@Override
	public void start(Stage primaryStage) {
		BorderPane bp = new BorderPane();
		StackPane pane = new StackPane(); 
		Button comp = new Button("WITH COMPUTER **easy**");// play with the computer
		comp.setFont(new Font("Comic Sans MS", 20));
		comp.setStyle( 
				"-fx-background-color: green; -fx-text-fill: white; -fx-border-color: red; -fx-border-width: 10px;");
		comp.setMaxWidth(600); 
		Button two = new Button("TWO PLAYERS **more fun**");// two humen player with eacj other
		two.setFont(new Font("Comic Sans MS", 20));
		two.setStyle(
				"-fx-background-color: green; -fx-text-fill: white; -fx-border-color: red; -fx-border-width: 10px;");
		two.setMaxWidth(600);
		Button adv = new Button("ADVANCED **hard**");// advenced unbeatable option with minimax algorithm
		adv.setFont(new Font("Comic Sans MS", 20));
		adv.setStyle( 
				"-fx-background-color: green; -fx-text-fill: white; -fx-border-color: red; -fx-border-width: 10px;");
		adv.setMaxWidth(600);

		HBox hb = new HBox();
		hb.getChildren().addAll(two, comp, adv);
		hb.setAlignment(Pos.CENTER);
		// hb.setSpacing(10);
		VBox vbox = new VBox();
		vbox.setAlignment(Pos.CENTER);
		vbox.setStyle("-fx-background-color: #89CFF0");
		Image startImage = new Image(
				"C:\\Users\\Yuna\\eclipse-workspace\\ALGO_4\\src\\application\\ce6f7ffd885e477efa2110437ab779dd.gif");
		ImageView view = new ImageView(startImage);
		view.setFitWidth(900);
		view.setFitHeight(700);
		view.setPreserveRatio(true);
		vbox.getChildren().addAll(view, hb);
		pane.getChildren().addAll(vbox);
		VBox gif = new VBox();
		gif.setAlignment(Pos.CENTER);
		gif.setStyle("-fx-background-color: #89CFF0");
		Image gifim = new Image(
				"C:\\Users\\Yuna\\eclipse-workspace\\ALGO_PRO2\\src\\application\\7e881bf46c1270f03fd580e8ce221639.gif");
		ImageView imageView = new ImageView(gifim);
		imageView.setFitWidth(900);
		imageView.setFitHeight(900);
		imageView.setPreserveRatio(true);
		gif.getChildren().addAll(imageView);

		bp.setCenter(pane);
		bp.setTop(hb);
		Scene scene = new Scene(bp, 900, 500); // set the size of the scene
		primaryStage.setScene(scene);
		primaryStage.setTitle("TIC TAC TOE :)");
		primaryStage.show();
//**************** set on actions***************//
		comp.setOnAction(e -> {// set on action on the comp button to display the comp play tic tac toe game
			try {
				new TicTacToecomp().start(new Stage());// open it in the same stage
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		});
		two.setOnAction(e -> {// set on action on the two button to display the two players play tic tac toe
								// game
			try {
				new TicTacToe2vs().start(new Stage());// open it in a new stage
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		});
		adv.setOnAction(e -> {// set on action on the two button to display the two players play tic tac toe
			// game
			try {
				new TicTacToeMinMax().start(new Stage());// open it in a new stage
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		});

	}

	public static void main(String[] args) {
		Application.launch(args);
		System.out.println(" ");
	}
}
