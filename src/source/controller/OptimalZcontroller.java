package source.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.input.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import source.Assignment;
import source.ProjectAssigner;
import source.model.OptimalZmodel;
import source.view.AssignedValueCell;
import source.view.CostManipuationView;
import source.view.OptimalZstatisticsView;
import source.view.OptimalZview;

/**
 * Created by LorisGrether on 10.04.2017.
 */
public class OptimalZcontroller {

    private OptimalZmodel model;
    private OptimalZview view;
    Assignment assignment;

    private ProjectAssigner assigner;

    private HashMap<String, String> map = new HashMap<String, String>();

    public OptimalZcontroller(OptimalZmodel model, OptimalZview view) {

        this.model = model;
        this.view = view;
        this.assigner = new ProjectAssigner(model, view);

        eventhandler();
    }

    private void eventhandler() {

        /***
         * This method catches all doubleclicks on a cell in the tableview and fills the changed values in a map
         */
        view.getTableView().setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {

                    if (view.getTableView().getSelectionModel().getSelectedCells().size() != 0) {

                        TablePosition pos = view.getTableView().getSelectionModel().getSelectedCells().get(0);

                        if (pos.getColumn() >= 3 && pos.getColumn() <= 7) {

                            int row = pos.getRow();
                            Assignment item = view.getTableView().getItems().get(row);
                            TableColumn col = pos.getTableColumn();
                            String projectCode = (String) col.getCellObservableValue(item).getValue();

                            ArrayList<Assignment> actualList = model.getListVersions().get(model.getActualVersion() - 1);

                            for (int i = 0; i < actualList.size(); i++) {

                                if (actualList.get(i).getName().equals(view.getTableView().getSelectionModel().getSelectedItem().getName())) {

                                    map.put(actualList.get(i).getName(), projectCode);
                                    model.getListVersions().get(model.getActualVersion() - 1).get(i).setLockedBoolean(true);

                                    view.getTableView().refresh();
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        });

        /***
         * If the user changes the version this event will be set on action
         */
        view.getComboBoxVersions().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                view.getTableView().refresh();

                int index = view.getComboBoxVersions().getSelectionModel().getSelectedIndex();
                System.out.println(index);

                model.setActualVersion(index + 1);

                if (assigner.getList().size() != 0) {

                    int[][] assignment = assigner.getList().get(index);

                    assigner.printAssignment3(assignment);

                    System.out.println(index + " <--> " + String.valueOf(model.getActualVersion() - 1));

                    ObservableList<Assignment> tableValues = FXCollections.observableArrayList();
                    tableValues.addAll(model.getListVersions().get(model.getActualVersion() - 1));
                    view.getTableView().setItems(tableValues);

                    view.getTableView().refresh();
                }

                view.getTableView().refresh();

                //We need this in order that the GUI has no update delays
                Event.fireEvent(view.getTableView(), new MouseEvent(MouseEvent.MOUSE_PRESSED, 200,
                        200, 200, 200, MouseButton.SECONDARY, 1, true, true, true, true,
                        true, true, true, true, true, true, null));
            }
        });

        this.view.getBtnStatistik().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                if (model.getListVersions().size() != 0) {

                    OptimalZstatisticsView optimalZstatisticsView = new OptimalZstatisticsView(model, assigner, assignment);

                } else {
                    view.getLblStatus().setText("INFO: You first have to calculate an assignment!");
                }

            }
        });

        this.view.getBtnCostManipulation().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                CostManipuationView costManipuationView = new CostManipuationView(model);

            }
        });


        this.view.getBtnOpenProjectFile().setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {

                String filePath = openFile("Project File");

                if ((model.getProjectsFileName().getValue() == null && filePath != null) || (model.getProjectsFileName() != null && filePath != null)) {
                    model.setProjectsFileName(filePath);
                    view.getBtnOpenProjectFile().setDisable(true);
                    view.getBtnOpenChoiceFile().setDisable(false);
                }
            }
        });

        view.getBtnOpenChoiceFile().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                String filePath = openFile("Choice File");

                if ((model.getPriorityFileName().getValue() == null && filePath != null) || (model.getPriorityFileName() != null && filePath != null)) {
                    model.setPriorityFileName(filePath);
                    view.getBtnOpenChoiceFile().setDisable(true);
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

                if ((assigner.getProjectNumbers().size() != 0 && assigner.getStudentList().size() != 0) && model.getAreFilesReadIn()) {

                    try {
                        assigner.computeCostMatrix(model.getPriorityFileName().getValue());
                        assigner.recomputeAssignment(map);

                        ObservableList<Assignment> tableValues = FXCollections.observableArrayList();
                        tableValues.addAll(model.getListVersions().get(model.getActualVersion() - 1));
                        view.getTableView().setItems(tableValues);

                        view.getComboBoxVersions().getItems().add("Version " + model.getListVersions().size());
                        view.getComboBoxVersions().getSelectionModel().selectLast();
                        model.setActualVersion(model.getListVersions().size());
                        view.getBtnCostManipulation().setDisable(true);

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

                        ArrayList<Assignment> actualListAssignment = model.getListVersions().get(model.getActualVersion() - 1);

                        for (int i = 0; i < actualListAssignment.size(); i++) {

                            if (actualListAssignment.get(i).equals(assignment)) {

                                assignment.setLockedBoolean(newValue);
                                System.out.println(assignment.getName() + " " + newValue);

                                if (newValue == false) {

                                    System.out.println("CheckBoxEvent Remove: " + assignment.getName() + "  " + assignment.getAssignedProject());

                                    map.remove(assignment.getName());
                                } else if (newValue == true) {

                                    System.out.println("CheckBoxEvent Add: " + assignment.getName() + "  " + assignment.getAssignedProject());

                                    map.put(assignment.getName(), assignment.getAssignedProject());
                                }
                                break;
                            }
                        }
                        System.out.println("current map size: " + map.size());
                    }
                });
                return booleanProperty;
            }
        });

        this.view.getStage().setOnCloseRequest(new EventHandler<WindowEvent>() {

            @Override
            public void handle(WindowEvent event) {

                if (model.getIsExported() == true) {
                    Platform.exit();
                } else {
                    Alert closeAlert = new Alert(Alert.AlertType.CONFIRMATION);
                    closeAlert.setTitle("Confirm Application closing");
                    closeAlert.setHeaderText("No assignment was saved yet.");
                    closeAlert.setContentText("Do you want to close the Application anyway?");
                    Optional<ButtonType> result = closeAlert.showAndWait();

                    if (result.get() == ButtonType.OK) {
                        Platform.exit();
                    } else {
                        event.consume();
                    }
                }

            }
        });

        view.getColProjectPrio1().setCellFactory(column -> new AssignedValueCell("rgba(10, 194, 2, 0.4)"));
        view.getColProjectPrio2().setCellFactory(column -> new AssignedValueCell("rgba(155, 221, 0, 0.4)"));
        view.getColProjectPrio3().setCellFactory(column -> new AssignedValueCell("rgba(249, 174, 0, 0.4)"));
        view.getColProjectPrio4().setCellFactory(column -> new AssignedValueCell("rgba(217, 113, 11, 0.4)"));
        view.getColProjectPrio5().setCellFactory(column -> new AssignedValueCell("rgba(207, 25, 3, 0.4)"));

        /**
         *
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
                        alert.setContentText("The assignment is stored on the desktop.");
                        alert.showAndWait();
                    } else view.getLblStatus().setText("Error during export!");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * @author Tobias Gerhard
     * Responsible for the export of the final list
     */
    public boolean csvWriter() throws IOException {

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        Date date = new Date();
        String today = dateFormat.format(date);
        File desktop = new File(System.getProperty("user.home"), "Desktop");
        FileWriter fileWriter = new FileWriter(desktop.getAbsolutePath() + "\\ProjektAssignment_" + today + ".csv");
        String NEW_LINE_SEPARATOR = "\n";
        String FILE_HEADER = "GROUP" + ";" + "ASSIGNED PROJECT" + ";" + "PRIO 1" + ";" + "PRIO 2" + ";" + "PRIO 3" + ";" + "PRIO 4" + ";" + "PRIO 5" + ";" + "COST";
        String FILE_TITLE = "Project Allocation From: " + today;

        try {
            fileWriter.append(FILE_TITLE);
            fileWriter.append(NEW_LINE_SEPARATOR);
            fileWriter.append(FILE_HEADER);

            //gets the actual version that is displayed
            int actualVersion = model.getActualVersion();
            ArrayList<Assignment> arrAssignments = model.getListVersions().get(actualVersion - 1); //arrAssignments now is the list with the current assignments


            for (int i = 1; i <= arrAssignments.size(); i++) {

                Assignment a = arrAssignments.get(i - 1);
                fileWriter.append(NEW_LINE_SEPARATOR);
                fileWriter.append(a.getName() + ";");
                fileWriter.append(a.getAssignedProject() + ";");
                fileWriter.append(a.getProjectPrio1() + ";");
                fileWriter.append(a.getProjectPrio2() + ";");
                fileWriter.append(a.getProjectPrio3() + ";");
                fileWriter.append(a.getProjectPrio4() + ";");
                fileWriter.append(a.getProjectPrio5() + ";");
                String cost = String.valueOf(a.getCost());
                fileWriter.append(cost + ";");
                fileWriter.flush();
            }
            this.model.setIsExported(true);

        } catch (Exception e) {
            view.getLblStatus().setText("Something went wrong during export");
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

        fileChooser.setInitialDirectory(desktop);

        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("CSV", "*.csv"));

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