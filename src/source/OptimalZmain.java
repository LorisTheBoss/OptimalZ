package source;


import au.com.bytecode.opencsv.CSVWriter;
import javafx.application.Application;
import javafx.stage.Stage;
import source.controller.OptimalZcontroller;
import source.model.OptimalZmodel;
import source.view.OptimalZview;

import java.io.FileWriter;

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




        //String priorityFileName = "res/BIT_ChoiceAdjusted.csv";
        //String priorityFileName ="C:/Users/LorisGrether/Desktop/FHNW/Semester4/PracticalProject/Source/TestData/BIT_ChoiceAdjusted.csv";

        String priorityFileName ="C:/Users/Tobias/Dropbox/Practical Project (OptimalZ)/Development/csv files/project priority test-9-records-20170423_1419-comma_separated_Number Format_testTobias.csv";


        // *** 2 ***
        // a single column file with all project numbers available (also with
        // one row header)
        // String projectsFileName = "/Users/rza/Desktop/BOK_Projects.csv";
        //String projectsFileName = "res/BIT_Projects.csv";
        //String projectsFileName ="C:/Users/LorisGrether/Desktop/FHNW/Semester4/PracticalProject/Source/TestData/BIT_Projects.csv";

        String projectsFileName ="C:/Users/Tobias/Dropbox/Practical Project (OptimalZ)/Development/csv files/Project List number format_testTobias.csv";

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



		/************************************************
		 *	Export Test Tobi
		 ************************************************/

		/**
		 * @author Tobias Gerhard
		 * Responsible for the export of the final list

		String COMMA_DELIMITER = ",";
		String NEW_LINE_SEPARATOR = "\n";
		String FILE_HEADER = "GROUP, PROJECT";
		FileWriter fileWriter = new FileWriter("C:\\Users\\Tobias\\Desktop\\AssignmentList.csv");
		try {
			fileWriter.append(FILE_HEADER);
			for (int i = 1 ; i <= model.getListAssignmnet().size() ; i++) {
				Assignment a = model.getListAssignmnet().get(i-1);
				fileWriter.append(NEW_LINE_SEPARATOR);
				fileWriter.append(a.getName());
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append(a.getAssignedProject());
				fileWriter.flush();
			}
		} catch (Exception e) {
			System.err.println("Something went wrong during export");
		} finally {
			fileWriter.flush();
			fileWriter.close();
		}
		*/

		model.csvWriter();



	}
}