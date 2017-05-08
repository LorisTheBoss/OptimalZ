package source;


import javafx.application.Application;
import javafx.stage.Stage;
import source.controller.OptimalZcontroller;
import source.model.OptimalZmodel;
import source.view.OptimalZview;

/**
 * Created by LorisGrether on 10.04.2017.
 */
public class OptimalZmain extends Application {

	public static void main(String[] args) {
		launch();
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		// we have to read two files:
		// *** 1 ***
		// the first file contains one header row
		// then each row consists of 6 columns
		// col 0 = studentNr
		// col 1 - col 5 = projectnumbers with priority 1 to 5
		//
		// own projects are marked with "Eig" or "eig"
		// !!! empty cells contain a space character !!!
		// String priorityFileName = "/Users/rza/Desktop/BOK_Choice.csv";

        // *** 2 ***
        // a single column file with all project numbers available (also with
        // one row header)

		OptimalZmodel model = new OptimalZmodel();
        OptimalZview view = new OptimalZview(primaryStage, model);
		new OptimalZcontroller(model, view);
		
		view.start();
	}
}