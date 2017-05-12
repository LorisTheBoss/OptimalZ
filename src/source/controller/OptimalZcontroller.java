package source.controller;

import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.print.Printer;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import source.Assignment;
import source.ProjectAssigner;
import source.model.OptimalZmodel;
import source.view.OptimalZstatisticsView;
import source.view.OptimalZview;

/**
 * Created by LorisGrether on 10.04.2017.
 */
public class OptimalZcontroller {

    OptimalZmodel model;
    OptimalZview view;

    ProjectAssigner assigner;

    public OptimalZcontroller(OptimalZmodel model, OptimalZview view) {

        this.model = model;
        this.view = view;
        assigner = new ProjectAssigner(model, view);

        Assignment assignment = new Assignment();
        eventhandler();


    }

    private void eventhandler() {

//		this.view.getBtnOpenFile().setOnAction(evnet -> {
//
//		});


        model.getListVersions().addListener(new ListChangeListener<ArrayList<Assignment>>() {
            @Override
            public void onChanged(Change<? extends ArrayList<Assignment>> c) {

                view.getComboBoxVersions().getItems().add("Version " + model.getListVersions().size());
                view.getComboBoxVersions().getSelectionModel().selectLast();
                model.setActualVersion(model.getListVersions().size());
            }
        });

        view.getComboBoxVersions().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                System.out.println("other version was selected");

                //ObservableList<Assignment> tableValues = FXCollections.observableArrayList();
                //tableValues.addAll(model.getListVersions().get(Integer.parseInt(split[1])-1));
                //view.getTableView().setItems(tableValues);

                String[] split = view.getComboBoxVersions().getSelectionModel().getSelectedItem().toString().split(" ");

                model.setActualVersion(Integer.parseInt(split[1]));

                //view.getTable().getColumns().clear();
                //view.getTable().getItems().clear();

                //view.getTableData().setAll(model.getTableData());
                //fillTableView();

                System.out.println("The shown version is : " + (Integer.parseInt(split[1])));
            }
        });


        this.view.getBtnStatistik().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                //OptimalZstatisticsView optimalZstatisticsView = new OptimalZstatisticsView(view.getComboBoxVersions().getSelectionModel().getSelectedItem().toString());
                OptimalZstatisticsView optimalZstatisticsView = new OptimalZstatisticsView();


//                if (model.getListVersions().size() != 0) {
//                } else {
//                    view.getLblStatus().setText("INFO: You first have to select a version");
//                }

            }
        });


        this.view.getBtnOpenProjectFile().setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {

                String filePath = openFile("Project File");

                if ((model.getProjectsFileName().getValue() == null && filePath != null) || (model.getProjectsFileName() != null && filePath != null)) {
                    model.setProjectsFileName(filePath);
                }
            }
        });

        view.getBtnOpenChoiceFile().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                //model.setPriorityFileName(openFile("Priority File"));

                String filePath = openFile("Choice File");

                if ((model.getPriorityFileName().getValue() == null && filePath != null) || (model.getPriorityFileName() != null && filePath != null)) {
                    model.setPriorityFileName(filePath);
                }
            }
        });

        this.model.getProjectsFileName().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

                view.getLblStatus().setText("INFO: " + newValue);

                if (model.getPriorityFileName().getValue() != null && model.getProjectsFileName() != null) {

                    assigner.readCSV(model.getPriorityFileName().getValue(), model.getProjectsFileName().getValue());
                }
            }
        });

        this.model.getPriorityFileName().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

                if (model.getPriorityFileName().getValue() != null && model.getProjectsFileName() != null) {

                    assigner.readCSV(model.getPriorityFileName().getValue(), model.getProjectsFileName().getValue());
                }
            }
        });


        this.view.getBtnStartAssignment().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                System.out.println("start calculation!!!");

                //checkForLocks();

                if ((assigner.getProjectNumbers().size() != 0 && assigner.getStudentList().size() != 0) && model.getAreFilesReadIn()) {

                    try {
                        assigner.computeCostMatrix(model.getPriorityFileName().getValue());
                        assigner.computeAssignment();

                        ObservableList<Assignment> tableValues = FXCollections.observableArrayList();
                        tableValues.addAll(model.getListAssignmnet());
                        view.getTableView().setItems(tableValues);

                        //view.getTable().getColumns().clear();
                        //view.getTable().getItems().clear();

                        //view.getTableData().setAll(model.getTableData());
                        //fillTableView();

                        view.getLblStatus().setText("INFO: Assignment was successfully computed");

                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                } else {
                    view.getLblStatus().setText("INFO: You first have to select a valid project and choice file");
                }
            }
        });

        view.getColLock().setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Assignment, Boolean>, ObservableValue<Boolean>>() {
            @Override
            public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<Assignment, Boolean> param) {

                Assignment assignment = param.getValue();

                SimpleBooleanProperty booleanProperty = new SimpleBooleanProperty(assignment.getLockedBoolean());

                booleanProperty.addListener(new ChangeListener<Boolean>() {
                    @Override
                    public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                        assignment.setLockedBoolean(newValue);
                    }
                });
                return booleanProperty;
            }
        });

        this.view.getStage().setOnCloseRequest(new EventHandler<WindowEvent>() {

            @Override
            public void handle(WindowEvent event) {


                //TODO This code is okay but comment because it is bothersome!! --> Don't forget to uncomment this stuff before the hand in
                /*

                if (model.getIsExported() == true) {
                    Platform.exit();
                } else {
                    Alert closeAlert = new Alert(Alert.AlertType.CONFIRMATION);
                    closeAlert.setTitle("Wir benötigen Ihre Besätigung");
                    closeAlert.setHeaderText("Es wurde noch keine Projektzuteilung exportiert.");
                    closeAlert.setContentText("Soll das Programm wirklich geschlossen werden?");
                    Optional<ButtonType> result = closeAlert.showAndWait();

                    if (result.get() == ButtonType.OK) {
                        Platform.exit();
                    } else {
                        event.consume();
                    }
                }

                */

            }
        });

        /**
         * @author Tobias Gerhard
         * Button ruft die csvWriter()-Methode auf um das File zu exortieren
         */
        this.view.getBtnSave().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    if (csvWriter()) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Good News!");
                        alert.setHeaderText(null);
                        alert.setContentText("Die Projektzuteilung wurde auf dem Desktop gespeichert. ");
                        alert.showAndWait();
                    } else view.getLblStatus().setText("Error during export!");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });


    }

    public void fillTableView() {

        if (view.getTableData().size() != 0) {

            for (Map.Entry entry : view.getTableData().get(0).entrySet()) {

                TableColumn column = new TableColumn(entry.getKey().toString());
                column.setCellValueFactory(new MapValueFactory<String>(entry.getKey().toString()));
                view.getTable().getColumns().add(column);

            }

            TableColumn<Assignment, Boolean> colLock = new TableColumn("Lock");
            //colLock.setCellValueFactory(new PropertyValueFactory<String, String>("lock"));
            view.getTable().getColumns().add(colLock);


            colLock.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Assignment, Boolean>, ObservableValue<Boolean>>() {
                @Override
                public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<Assignment, Boolean> param) {

                    SimpleBooleanProperty isLocked = new SimpleBooleanProperty();
                    isLocked.addListener(new ChangeListener<Boolean>() {
                        @Override
                        public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                            System.out.println("--------->    Locked    !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");


                        }
                    });
                    return isLocked;
                }
            });

            //
            colLock.setCellFactory(p -> {
                CheckBoxTableCell<Assignment, Boolean> cell = new CheckBoxTableCell<>();
                cell.setAlignment(Pos.CENTER);
                return cell;
            });
        }
    }


    /**
     * @author Tobias Gerhard
     * Responsible for the export of the final list
     */
    public boolean csvWriter() throws IOException {

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String today = dateFormat.format(date);
        File desktop = new File(System.getProperty("user.home"), "Desktop");
        FileWriter fileWriter = new FileWriter(desktop.getAbsolutePath() + "\\ProjektAssignment_" + today + "_v" + model.getActualVersion() + ".csv");
        String NEW_LINE_SEPARATOR = "\n";
        String FILE_HEADER = "GROUP" + ";" + "ASSIGNED PROJECT" + ";" + "PRIO 1" + ";" + "PRIO 2" + ";" + "PRIO 3" + ";" + "PRIO 4" + ";" + "PRIO 5" + ";" + "COST";
        String FILE_TITLE = "Projektzuteilung vom " + today;

        try {
            fileWriter.append(FILE_TITLE);
            fileWriter.append(NEW_LINE_SEPARATOR);
            fileWriter.append(FILE_HEADER);

            //gets the actual version that is displayed
            int actualVersion = model.getActualVersion();
            ArrayList<Assignment> arrAssignments = model.getListVersions().get(actualVersion - 1); //arrAssignments now is the list with the current assignments



            for (int i = 1; i <= arrAssignments.size(); i++) {

                int z = 1;
                Assignment a = arrAssignments.get(i - 1);
                fileWriter.append(NEW_LINE_SEPARATOR);
                fileWriter.append(a.getName() + ";");
                fileWriter.append(a.getAssignedProject() + ";");

                while (z <= 6) {
                    fileWriter.append(a.getChosenProjects().get(z)  + ";");
                    fileWriter.flush();
                    z++;

                    if (z == 6) {
                        String cost = String.valueOf(a.getCost());
                        fileWriter.append(cost + ";");
                    }
                }


                this.model.setIsExported(true);
            }
        } catch (Exception e) {
            //TODO catch this in the frontend
            System.err.println("Something went wrong during export");
            this.model.setIsExported(false);
        } finally {
            fileWriter.flush();
            fileWriter.close();
            return this.model.getIsExported();
        }
    }


    private String openFile(String fileType) {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("OptimalZ - Open " + fileType);
        File desktop = new File(System.getProperty("user.home"), "Desktop");

        //Loris
        //File desktop = new File("C:/Users/LorisGrether/Desktop/FHNW/Semester4/PracticalProject/Source/TestData");
        fileChooser.setInitialDirectory(desktop);

        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("CSV", "*.csv"));
        //new FileChooser.ExtensionFilter("XLS", "*.xls"),
        //new FileChooser.ExtensionFilter("XLSX", "*.xlsx"));

        File selectedFile = fileChooser.showOpenDialog(new Stage());

        if (selectedFile != null && selectedFile.isFile() && selectedFile.exists()) {
            selectedFile.getAbsolutePath();
            System.out.println(selectedFile.getAbsolutePath());
            return selectedFile.getAbsolutePath();
        }

        this.view.getLblStatus().setText("Error while selecting the " + fileType);
        return null;
    }


}