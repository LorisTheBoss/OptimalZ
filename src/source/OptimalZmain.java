package source;


import javafx.application.Application;
import javafx.stage.Stage;
import source.controller.OptimalZcontroller;
import source.model.OptimalZmodel;
import source.view.OptimalZview;

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
		
		
		//New Stuff
		
		OptimalZmodel model = new OptimalZmodel();


// test jonas

        //String priorityFileName = "res/BIT_ChoiceAdjusted.csv";
        //String priorityFileName ="C:/Users/LorisGrether/Desktop/FHNW/Semester4/PracticalProject/Source/TestData/BIT_ChoiceAdjusted.csv";

        String priorityFileName ="C:/Users/LorisGrether/Documents/Workspace/OptimalZ/res/project priority test-6-records-20170414_1124-comma_separated.csv";


        // *** 2 ***
        // a single column file with all project numbers available (also with
        // one row header)
        // String projectsFileName = "/Users/rza/Desktop/BOK_Projects.csv";
        //String projectsFileName = "res/BIT_Projects.csv";
        //String projectsFileName ="C:/Users/LorisGrether/Desktop/FHNW/Semester4/PracticalProject/Source/TestData/BIT_Projects.csv";

        String projectsFileName ="C:/Users/LorisGrether/Documents/Workspace/OptimalZ/res/ProjectListMoodle.csv";

        ProjectAssigner assigner = new ProjectAssigner(model);
        assigner.readCSV(priorityFileName, projectsFileName);
        assigner.computeCostMatrix(priorityFileName);
        assigner.computeAssignment();


		OptimalZview view = new OptimalZview(primaryStage, model);
		new OptimalZcontroller(model, view);
		
		view.start();


		/*

		//String priorityFileName = "res/BIT_ChoiceAdjusted.csv";
		String priorityFileName ="C:/Users/LorisGrether/Desktop/FHNW/Semester4/PracticalProject/Source/TestData/BIT_ChoiceAdjusted.csv";

		// *** 2 ***
		// a single column file with all project numbers available (also with
		// one row header)
		// String projectsFileName = "/Users/rza/Desktop/BOK_Projects.csv";
		

		//String projectsFileName = "res/BIT_Projects.csv";
		String projectsFileName ="C:/Users/LorisGrether/Desktop/FHNW/Semester4/PracticalProject/Source/TestData/BIT_Projects.csv";
		
		ProjectAssigner assigner = new ProjectAssigner(model);
		assigner.readCSV(priorityFileName, projectsFileName);
		assigner.computeCostMatrix(priorityFileName);
		assigner.computeAssignment();
		
		
		*/
		
		
	}
}