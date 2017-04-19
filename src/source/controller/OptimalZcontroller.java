package source.controller;

import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.print.Printer;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
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

                //String priorityFileName = openFile();

			}
		});

		view.getBtnOpenChoiceFile().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                //String projectsFileName = openFile();

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
				Platform.exit();
			}
		});
		
		
		
		
	}

	private String openFile(){

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("OptimalZ - Open File");
        fileChooser.setInitialDirectory(new File("C:\\Users\\LorisGrether\\Documents\\Workspace\\OptimalZ\\res"));

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