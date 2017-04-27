package source.controller;

import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.IOException;
import java.util.Optional;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.print.Printer;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import source.ProjectAssigner;
import source.model.OptimalZmodel;
import source.view.OptimalZview;

public class OptimalZcontroller {
	
	OptimalZmodel model;
	OptimalZview view;

	public OptimalZcontroller(OptimalZmodel model, OptimalZview view) {

		this.model = model;
		this.view = view;
	
		eventhandler();
		
	
	}

	private void eventhandler() {
		
//		this.view.getBtnOpenFile().setOnAction(evnet -> {
//
//		});
		
		this.view.getBtnOpenProjectFile().setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {

                String priorityFileName = openFile();

			}
		});

		view.getBtnOpenChoiceFile().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                String projectsFileName = openFile();

            }
        });

        /**
         * @author Tobias Gerhard
         * Button ruft die csvWriter()-Methode auf um das File zu exortieren
         */
		view.getBtnSave().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    model.csvWriter();
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Good News!");
                    alert.setHeaderText(null);
                    alert.setContentText("Die Projektzuteilung wurde auf dem Desktop gespeichert. ");
                    alert.showAndWait();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

		view.getBtnPrint().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Printer printer = Printer.getDefaultPrinter();
                PrinterJob printerJob = PrinterJob.getPrinterJob();
                printerJob.printDialog();


            }
        });

		view.getBtnStartAssignment().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("start calculation!!");

            }




        });


		view.getStage().setOnCloseRequest(new EventHandler<WindowEvent>() {

			@Override
			public void handle(WindowEvent event) {

                if (model.getIsExported() == true) {
                    Platform.exit();
                } else {
                    Alert closeAlert = new Alert(Alert.AlertType.CONFIRMATION);
                    closeAlert.setTitle("Wir benötigen Ihre Besätigung");
                    closeAlert.setHeaderText("Es wurde noch keine Projektzuteilung exportiert.");
                    closeAlert.setContentText("Soll das Programm wirklich geschlossen werden?");
                    Optional<ButtonType> result = closeAlert.showAndWait();

                    if (result.get() == ButtonType.OK){
                        Platform.exit();
                    } else {
                        closeAlert.close();
                    }
                }
            }
		});
		
		
		
		
	}

	private String openFile(){

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("OptimalZ - Open File");
        File desktop = new File(System.getProperty("user.home"), "Desktop");
        fileChooser.setInitialDirectory(desktop);

        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("CSV", "*.csv"),
                new FileChooser.ExtensionFilter("XLS", "*.xls"),
                new FileChooser.ExtensionFilter("XLSX", "*.xlsx"));

        File selectedFile = fileChooser.showOpenDialog(new Stage());

        if(selectedFile != null){
            selectedFile.getAbsolutePath();
            System.out.println(selectedFile.getAbsolutePath());
            return selectedFile.getAbsolutePath();
        }


        return null;
    }


}