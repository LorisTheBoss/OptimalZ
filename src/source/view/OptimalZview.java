package source.view;


import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import source.Assignment;
import source.model.OptimalZmodel;

import javax.swing.table.*;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by LorisGrether on 10.04.2017.
 */
public class OptimalZview {

    //Button
    Button btnOpenProjectFile = new Button("Project File");
    Button btnOpenChoiceFile = new Button("Choice File");

    private Button btnSave = new Button();
    private Button btnStatistik = new Button();

    private Button btnUndo = new Button();
    private Button btnRedo = new Button();
    private Button btnStartAssignment = new Button();

    //Label
    private Label lblStatus = new Label("Status");

    public TableView<Assignment> getTableView() {
        return tableView;
    }

    private TableColumn<Assignment, Boolean> colLock;

    private TableColumn<Assignment, String> colProjectPrio1;
    private TableColumn<Assignment, String> colProjectPrio2;
    private TableColumn<Assignment, String> colProjectPrio3;
    private TableColumn<Assignment, String> colProjectPrio4;
    private TableColumn<Assignment, String> colProjectPrio5;

    TableView<Assignment> tableView;
    private TableView table;
    private ComboBox<Object> comboBoxVersions;

    public ObservableList<LinkedHashMap<String, String>> getTableData() {
        return tableData;
    }

    private ObservableList<LinkedHashMap<String, String>> tableData;

    //Pane
    private VBox bottomBox;
    private HBox statusBox;

    private Scene scene;

    private OptimalZmodel model;
    private Stage stage;

    public OptimalZview(Stage primaryStage, OptimalZmodel model) {

        this.model = model;
        this.stage = primaryStage;

        stage.setTitle("OptimalZ");

        BorderPane root = new BorderPane();

        root.setTop(createTop());
        root.setCenter(createCenter2());
        root.setBottom(createBottom());

        scene = new Scene(root, 850, 600);

        this.setControlIDs();
        this.setToolTipText();

        String css = this.getClass().getResource("/css/OptimalZ.css").toExternalForm();
        scene.getStylesheets().add(css);

        stage.setScene(scene);
    }

    public void start() {
        stage.show();
        this.lblStatus.setText("Ready");
    }

    private Node createTop() {

        HBox topBox = new HBox();

        ToolBar bar = new ToolBar(
                this.btnOpenProjectFile,
                this.btnOpenChoiceFile,
                new Separator(),
                this.btnStartAssignment,
                this.btnUndo,
                this.btnRedo,
                new Separator(),
                this.btnStatistik,
                this.btnSave);

        comboBoxVersions = new ComboBox<>();
        comboBoxVersions.getSelectionModel().selectFirst();

        bar.getItems().add(comboBoxVersions);

        bar.setMinWidth(Double.MAX_VALUE);

        topBox.getChildren().add(bar);

        return topBox;
    }

    public Node createCenter() {

        tableData = FXCollections.observableArrayList();

        table = new TableView<>(tableData);

        table.setEditable(true);
        table.getSelectionModel().setCellSelectionEnabled(true);

        return table;
    }

    public Node createCenter2() {

        tableView = new TableView<Assignment>();

        tableView.setEditable(true);
        tableView.getSelectionModel().setCellSelectionEnabled(true);

        TableColumn<Assignment, Integer> colID = new TableColumn<Assignment, Integer>("ID");
        colID.setPrefWidth(35);
        TableColumn<Assignment, String> colName = new TableColumn<Assignment, String>("Name");
        colName.setPrefWidth(120);
        TableColumn<Assignment, String> colAssignedProject = new TableColumn<Assignment, String>("Assigned Project");
        colAssignedProject.setPrefWidth(105);

        colProjectPrio1 = new TableColumn<Assignment, String>("Priority 1");
        colProjectPrio2 = new TableColumn<Assignment, String>("Priority 2");
        colProjectPrio3 = new TableColumn<Assignment, String>("Priority 3");
        colProjectPrio4 = new TableColumn<Assignment, String>("Priority 4");
        colProjectPrio5 = new TableColumn<Assignment, String>("Priority 5");

        TableColumn<Assignment, String> colCost = new TableColumn<Assignment, String>("Cost");
        colLock = new TableColumn<Assignment, Boolean>("Lock");

        colLock.setCellFactory(new Callback<TableColumn<Assignment, Boolean>,
                TableCell<Assignment, Boolean>>() {
            @Override
            public TableCell<Assignment, Boolean> call(TableColumn<Assignment, Boolean> p) {
                CheckBoxTableCell<Assignment, Boolean> cell = new CheckBoxTableCell<Assignment, Boolean>();
                cell.setAlignment(Pos.CENTER);
                return cell;
            }
        });

        tableView.getColumns().addAll(
                colID,
                colName,
                colAssignedProject,
                colProjectPrio1,
                colProjectPrio2,
                colProjectPrio3,
                colProjectPrio4,
                colProjectPrio5,
                colCost,
                colLock);

        ObservableList<Assignment> tableValues = FXCollections.observableArrayList();

        tableValues.addAll(model.getListAssignmnet());

        colID.setCellValueFactory(new PropertyValueFactory<Assignment, Integer>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<Assignment, String>("name"));
        colAssignedProject.setCellValueFactory(new PropertyValueFactory<Assignment, String>("assignedProject"));
        colCost.setCellValueFactory(new PropertyValueFactory<Assignment, String>("Cost"));

        colProjectPrio1.setCellValueFactory(new PropertyValueFactory<Assignment, String>("projectPrio1"));
        colProjectPrio2.setCellValueFactory(new PropertyValueFactory<Assignment, String>("projectPrio2"));
        colProjectPrio3.setCellValueFactory(new PropertyValueFactory<Assignment, String>("projectPrio3"));
        colProjectPrio4.setCellValueFactory(new PropertyValueFactory<Assignment, String>("projectPrio4"));
        colProjectPrio5.setCellValueFactory(new PropertyValueFactory<Assignment, String>("projectPrio5"));

        tableView.setItems(tableValues);

        return tableView;
    }

    private Node createBottom() {

        bottomBox = new VBox(30);

        statusBox = new HBox(20);
        statusBox.setPadding(new Insets(5));
        statusBox.getChildren().add(lblStatus);

        bottomBox.getChildren().addAll(statusBox);
        return bottomBox;
    }

    private void setControlIDs() {

        //Button ID's
        this.btnOpenProjectFile.setId("btnOpenProjectFile");
        this.btnOpenChoiceFile.setId("btnOpenChoiceFile");
        this.btnSave.setId("btnSave");
        this.btnStatistik.setId("btnStatistik");
        this.btnRedo.setId("btnRedo");
        this.btnUndo.setId("btnUndo");
        this.btnStartAssignment.setId("btnStartAssignment");

        //ComboBox
        this.comboBoxVersions.setId("comboBoxVersions");

        //Label ID's
        this.lblStatus.setId("lblStatus");

        //Pane ID's
        this.bottomBox.setId("bottomBox");
        this.statusBox.setId("statusBox");

    }

    private void setToolTipText() {

        this.btnOpenProjectFile.setTooltip(new Tooltip("Open the file with the available projects"));
        this.btnOpenChoiceFile.setTooltip(new Tooltip("Open the file with all the choices"));
        this.btnStartAssignment.setTooltip(new Tooltip("Calculate Assignment"));
        this.btnSave.setTooltip(new Tooltip("Save the actual version"));
        this.btnStatistik.setTooltip(new Tooltip("Show the statistics of the actual version"));
        this.btnRedo.setTooltip(new Tooltip("Redo your changes"));
        this.btnUndo.setTooltip(new Tooltip("Undo your changes"));
        this.comboBoxVersions.setTooltip(new Tooltip("Version History"));
    }


    // ----- getters and setters -----

    public TableColumn<Assignment, String> getColProjectPrio1() {
        return colProjectPrio1;
    }

    public TableColumn<Assignment, String> getColProjectPrio2() {
        return colProjectPrio2;
    }

    public TableColumn<Assignment, String> getColProjectPrio3() {
        return colProjectPrio3;
    }

    public TableColumn<Assignment, String> getColProjectPrio4() {
        return colProjectPrio4;
    }

    public TableColumn<Assignment, String> getColProjectPrio5() { return colProjectPrio5; }

    public Button getBtnOpenProjectFile() {
        return this.btnOpenProjectFile;
    }

    public Button getBtnOpenChoiceFile() {
        return this.btnOpenChoiceFile;
    }

    public Button getBtnStartAssignment() {
        return this.btnStartAssignment;
    }

    public Button getBtnSave() {
        return this.btnSave;
    }

    public Stage getStage() {
        return this.stage;
    }

    public Label getLblStatus() {
        return this.lblStatus;
    }

    public Button getBtnStatistik() {
        return btnStatistik;
    }

    public ComboBox<Object> getComboBoxVersions() {
        return comboBoxVersions;
    }

    public TableView getTable() {
        return table;
    }

    public TableColumn<Assignment, Boolean> getColLock() {
        return colLock;
    }

    public Button getBtnUndo() {
        return btnUndo;
    }

    public Button getBtnRedo() {
        return btnRedo;
    }
}



