package source.view;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToolBar;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import source.Assignment;
import source.model.OptimalZmodel;

public class OptimalZview {

	//Button
	Button btnOpenProjectFile = new Button("Project File");
	Button btnOpenChoiceFile = new Button("Choice File");

	private Button btnSave = new Button();
	private Button btnStatistik = new Button();
	private Button btnUndo = new Button();
	private Button brnRedo = new Button();
	private Button btnStartAssignment = new Button();
	private Button btnPrint = new Button();
	
	//Label
	private Label lblProjectFileName = new Label("Project-File");
	private Label lblChoiceFileName = new Label("Choice-File");
	private Label lblStatus = new Label("Status");


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
		root.setCenter(createCenter());
		root.setBottom(createBottom());
		
		scene = new Scene(root, 800, 600);
		
		this.setControlIDs();
		
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
                this.brnRedo,
                new Separator(),
                this.btnStatistik,
                this.btnSave,
                this.btnPrint);

		ComboBox<Object> comboBoxVersions = new ComboBox<>();
		
		comboBoxVersions.getItems().addAll("Version 1","Version 2","Version 3");
		comboBoxVersions.getSelectionModel().selectFirst();
		
		bar.getItems().add(comboBoxVersions);
		
		bar.setMinWidth(Double.MAX_VALUE);
		
		topBox.getChildren().add(bar);
		
		return topBox;
	}


	private Node createCenter() {

        TableView<Assignment> table = new TableView<Assignment>();

        TableColumn<Assignment, Integer> colID = new TableColumn<Assignment, Integer>("ID");
        TableColumn<Assignment, String> colName = new TableColumn<Assignment, String>("Name");

        TableColumn<Assignment, String> colAssignedProject = new TableColumn<Assignment, String>("Assigned Project");

        TableColumn<Assignment, String> colProjectPrio1 = new TableColumn<Assignment, String>("Prio 1");
        TableColumn<Assignment, String> colProjectPrio2 = new TableColumn<Assignment, String>("Prio 2");
        TableColumn<Assignment, String> colProjectPrio3 = new TableColumn<Assignment, String>("Prio 3");
        TableColumn<Assignment, String> colProjectPrio4 = new TableColumn<Assignment, String>("Prio 4");
        TableColumn<Assignment, String> colProjectPrio5 = new TableColumn<Assignment, String>("Prio 5");

        //TableColumn<Assignment, String> colCost = new TableColumn<Assignment, String>("Cost");
        //TableColumn<Assignment, String> colPriority = new TableColumn<Assignment, String>("Priority");
        TableColumn<Assignment, Boolean> colLock = new TableColumn<Assignment, Boolean>("Lock");

//		colName.prefWidthProperty().bind(stage.widthProperty().divide(4));
//		colProject.prefWidthProperty().bind(stage.widthProperty().divide(4));
//		colCost.prefWidthProperty().bind(stage.widthProperty().divide(4));
//		colPriority.prefWidthProperty().bind(stage.widthProperty().divide(4));


        colLock.setCellFactory(new Callback<TableColumn<Assignment, Boolean>, //
                TableCell<Assignment, Boolean>>() {
            @Override
            public TableCell<Assignment, Boolean> call(TableColumn<Assignment, Boolean> p) {
                CheckBoxTableCell<Assignment, Boolean> cell = new CheckBoxTableCell<Assignment, Boolean>();
                cell.setAlignment(Pos.CENTER);
                return cell;
            }
        });


        table.getColumns().addAll(
                colID,
                colName,
                colAssignedProject,
                colProjectPrio1,
                colProjectPrio2,
                colProjectPrio3,
                colProjectPrio4,
                colProjectPrio5,
                //colCost,
                //colPriority,
                colLock);

        ObservableList<Assignment> tableValues = FXCollections.observableArrayList();

        tableValues.addAll(model.getListAssignmnet());

        colID.setCellValueFactory(new PropertyValueFactory<Assignment, Integer>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<Assignment, String>("name"));
        colAssignedProject.setCellValueFactory(new PropertyValueFactory<Assignment, String>("assignedProject"));
        //colCost.setCellValueFactory(new PropertyValueFactory<Assignment, String>("Cost"));
        //colPriority.setCellValueFactory(new PropertyValueFactory<String, String>("Priority"));

        table.setItems(tableValues);

        return table;
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
		this.brnRedo.setId("btnRedo");
		this.btnUndo.setId("btnUndo");
		this.btnStartAssignment.setId("btnStartAssignment");
		this.btnPrint.setId("btnPrint");

		//Label ID's
        this.lblStatus.setId("lblStatus");

		//Pane ID's
		this.bottomBox.setId("bottomBox");
		this.statusBox.setId("statusBox");
				
	}
	
	// ----- getters and setters -----
	
	public Button getBtnOpenProjectFile(){
		return this.btnOpenProjectFile;
	}

    public Button getBtnOpenChoiceFile(){
        return this.btnOpenChoiceFile;
    }

    public Button getBtnStartAssignment() {return this.btnStartAssignment;}

    public Button getBtnPrint() {return this.btnPrint;}

    public Stage getStage() {return this.stage;
	}
}



