package source.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import source.view.IntegerStringConverter;
import source.Assignment;
import source.Project;
import source.ProjectAssigner;
import source.controller.OptimalZstatisticsController;
import source.model.OptimalZmodel;
import java.util.ArrayList;

/**
 * Created on 30.04.2017.
 */
public class OptimalZstatisticsView {

    //Table
    private TableView statisticsTable;

    //private TableColumn colID;
    private TableColumn colProjects;
    private TableColumn colProjectPrio1;
    private TableColumn colProjectPrio2;
    private TableColumn colProjectPrio3;
    private TableColumn colProjectPrio4;
    private TableColumn colProjectPrio5;
    private TableColumn colTotal;

    private ArrayList<Integer> projectPickList;

    //Button
    private Button btnSave = new Button();

    //Tabs
    private TabPane tabPane;
    private Tab tab1;
    private Tab tab2;
    private Tab tab3;

    private Rectangle rectPrio1;
    private Rectangle rectPrio2;
    private Rectangle rectPrio3;
    private Rectangle rectPrio4;
    private Rectangle rectPrio5;
    private Rectangle rectOwn;


    //Charts
    private BarChart prioDistQual;
    private CategoryAxis xAxis;
    private NumberAxis yAxis;

    final static String prio1 = "Priority 1";
    final static String prio2 = "Priority 2";
    final static String prio3 = "Priority 3";
    final static String prio4 = "Priority 4";
    final static String prio5 = "Priority 5";
    final static String own = "Own";

    private VBox bottomBox;
    private HBox statusBox;

    Stage statisticStage;
    Scene scene;

    private OptimalZmodel model;
    private OptimalZstatisticsController statisticsController;


    public OptimalZstatisticsView(OptimalZmodel model, ProjectAssigner assigner, Assignment assignment) {

        statisticStage = new Stage();
        this.model = model;
        this.statisticsController = new OptimalZstatisticsController(model, assigner, assignment, this);

        statisticStage.setTitle("OptimalZ - Statistics");

        BorderPane root = new BorderPane();
        root.setTop(createTop());
        root.setCenter(createCenter());
        root.setBottom(createBottom());

        scene = new Scene(root, 850, 600);

        this.setControlIDs();
        this.setToolTipText();
        this.setTabImages();

        String css = this.getClass().getResource("/css/OptimalZStatistics.css").toExternalForm();
        scene.getStylesheets().add(css);

        statisticStage.setScene(scene);
        statisticStage.show();

    }



    /**
     * Creates the Bar Chart and fills it with data from the statistics controller.
     */
    public Parent createContentPrioBarChart() {

        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        final BarChart<String, Number> bc = new BarChart<String, Number>(xAxis, yAxis);
        bc.setTitle("Priority Distribution");

        xAxis.setLabel("Priorities");
        yAxis.setLabel("Number of Assignments");

        XYChart.Series series1 = new XYChart.Series();
        final XYChart.Data<String, Number> dataPrio1 = new XYChart.Data(prio1, statisticsController.priorityOne());
        final XYChart.Data<String, Number> dataPrio2 = new XYChart.Data(prio2, statisticsController.priorityTwo());
        final XYChart.Data<String, Number> dataPrio3 = new XYChart.Data(prio3, statisticsController.priorityThree());
        final XYChart.Data<String, Number> dataPrio4 = new XYChart.Data(prio4, statisticsController.priorityFour());
        final XYChart.Data<String, Number> dataPrio5 = new XYChart.Data(prio5, statisticsController.priorityFive());
        final XYChart.Data<String, Number> dataOwn = new XYChart.Data(own, statisticsController.ownProjects());

        series1.getData().add(dataPrio1);
        series1.getData().add(dataPrio2);
        series1.getData().add(dataPrio3);
        series1.getData().add(dataPrio4);
        series1.getData().add(dataPrio5);
        series1.getData().add(dataOwn);

        bc.getData().addAll(series1);

        dataPrio1.getNode().setStyle("-fx-bar-fill: rgba(10, 194, 2, 1);");
        dataPrio2.getNode().setStyle("-fx-bar-fill: rgba(155, 221, 0, 1);");
        dataPrio3.getNode().setStyle("-fx-bar-fill: rgba(249, 174, 0, 1);");
        dataPrio4.getNode().setStyle("-fx-bar-fill: rgba(217, 113, 11, 1);");
        dataPrio5.getNode().setStyle("-fx-bar-fill: rgba(207, 25, 3, 1);");
        dataOwn.getNode().setStyle("-fx-bar-fill: rgba(151, 65, 198, 1);");

        bc.setLegendVisible(false);
//        yAxis.setTickLabelFormatter(IntegerStringConverter());

        return bc;
    }

    private Node createTop() {
        HBox topBox = new HBox();

        return topBox;
    }

    /**
     * Creates all the Tabs of the Statistics View.
     */
    private Node createCenter() {

        tabPane = new TabPane();
        tab1 = new Tab();
        tab2 = new Tab();
        tab3 = new Tab();

        tabPane.setRotateGraphic(false);
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        tabPane.setSide(Side.TOP);
        final VBox vbox = new VBox();
        vbox.setSpacing(10);
        vbox.setTranslateX(10);
        vbox.setTranslateY(10);


        // Initial "Overview" tab presenting overall assignment statistic numbers
        tab1.setText("Overview");
        final VBox vboxOverview = new VBox();
        vboxOverview.setSpacing(10);
        vboxOverview.setTranslateX(10);
        vboxOverview.setTranslateY(10);

        Label lblVersion = new Label("Statistics to the Version: " + "\t" +  "\t" + "\t" + "\t" + model.getActualVersion());
        Label lblTotCost = new Label("The total Cost of this Assignment is: " + "\t" + statisticsController.totalCost());
        Label lblNoGroups = new Label("The total number of groups is: " + "\t" + "\t" + statisticsController.numberOfGroups());
        Label lblNoProjects = new Label("The total number of projects is: " + "\t" + "\t" + statisticsController.numberOfProjects());

        vboxOverview.getChildren().addAll(lblVersion, lblTotCost, lblNoGroups, lblNoProjects);

        tab1.setContent(vboxOverview);
        tabPane.getTabs().add(tab1);


        // Tab2 presenting the Prpject priority Distribution Quality Bar Chart
        tab2.setText("Priority Distribution Quality");
        final VBox vBoxBarChart= new VBox();
        final HBox hBoxLabels = new HBox();
        final BorderPane barChart = new BorderPane();

        barChart.setMaxWidth(600);

        vBoxBarChart.setSpacing(10);
        vBoxBarChart.setTranslateX(10);
        vBoxBarChart.setTranslateY(10);

        Label explainRadios = new Label("Histogram showing the Distribution of the assignments to the different priorities:");
        vBoxBarChart.getChildren().add(explainRadios);

        Label lblSpacer = new Label("\t" + "\t");
        Label lblNoPrio1 = new Label(" Priority 1:  " + statisticsController.priorityOne() + "\t");
        Label lblNoPrio2 = new Label(" Priority 2:  " + statisticsController.priorityTwo() + "\t");
        Label lblNoPrio3 = new Label(" Priority 3:  " + statisticsController.priorityThree() + "\t");
        Label lblNoPrio4 = new Label(" Priority 4:  " + statisticsController.priorityFour() + "\t");
        Label lblNoPrio5 = new Label(" Priority 5:  " + statisticsController.priorityFive() + "\t");
        Label lblNoOwn = new Label(" Own:  " + statisticsController.ownProjects() + "\t");

        rectPrio1 = new Rectangle(16,16);
        rectPrio2 = new Rectangle(16,16);
        rectPrio3 = new Rectangle(16,16);
        rectPrio4 = new Rectangle(16,16);
        rectPrio5 = new Rectangle(16,16);
        rectOwn = new Rectangle(16,16);

        rectPrio1.setStyle("-fx-fill: #0AC202; -fx-arc-height: 7; -fx-arc-width: 7;");
        rectPrio2.setStyle("-fx-fill: #9BDD00; -fx-arc-height: 7; -fx-arc-width: 7;");
        rectPrio3.setStyle("-fx-fill: #F9AE00; -fx-arc-height: 7; -fx-arc-width: 7;");
        rectPrio4.setStyle("-fx-fill: #D9710B; -fx-arc-height: 7; -fx-arc-width: 7;");
        rectPrio5.setStyle("-fx-fill: #CF1903; -fx-arc-height: 7; -fx-arc-width: 7;");
        rectOwn.setStyle("-fx-fill: #9741C6; -fx-arc-height: 7; -fx-arc-width: 7;");

        hBoxLabels.getChildren().addAll(lblSpacer, rectPrio1, lblNoPrio1, rectPrio2, lblNoPrio2, rectPrio3, lblNoPrio3, rectPrio4, lblNoPrio4, rectPrio5, lblNoPrio5, rectOwn, lblNoOwn);

        barChart.setCenter(this.createContentPrioBarChart());
        vBoxBarChart.getChildren().addAll(barChart, hBoxLabels);

        tab2.setContent(vBoxBarChart);
        tabPane.getTabs().add(tab2);

        // Tab3 presenting the Prpject Statistics Table presenting all Projects and the times it was choosen at which priority
        tab3.setText("Project Statistics Table");
        final BorderPane content = new BorderPane();
        content.setTop(createTableToolBar());
        content.setCenter(createStatisticsTable());

        tab3.setContent(content);
        tabPane.getTabs().add(tab3);
        return tabPane;

    }

    private Node createBottom() {
        HBox bottomBox = new HBox();

        return bottomBox;
    }

    // Set Control ID's for css styling
    private void setControlIDs() {

        //Button ID's
        this.btnSave.setId("btnSave");

       //Tab ID's
        this.tab1.setId("tab1");
        this.tab2.setId("tab2");
        this.tab3.setId("tab3");

       //Pane ID's
        this.tabPane.setId("tabPane");
    }

    // Set up Icon Images to taps
    private void setTabImages(){

        final Image img1 = new Image(getClass().getResourceAsStream("/pictures/statistics3.png"));
        final ImageView imageView1 = new ImageView();
        imageView1.setImage(img1);
        tab2.setGraphic(imageView1);

        final Image img2 = new Image(getClass().getResourceAsStream("/pictures/spreadsheet.png"));
        final ImageView imageView2 = new ImageView();
        imageView2.setImage(img2);
        tab3.setGraphic(imageView2);
    }

    // Set Tool Tip Texts to be displayed when hovering over the buttons
    private void setToolTipText() {

        this.tab1.setTooltip(new Tooltip("Shows Statistics Overview"));
        this.tab2.setTooltip(new Tooltip("Show Priority Distribution Histogram"));
        this.tab3.setTooltip(new Tooltip("Show Project Statistics Table"));
        this.btnSave.setTooltip(new Tooltip("Save this Project Statistics Table"));

    }

    // Creates the Prpject Statistics Table presenting all Projects and the times it was chosen at which priority
    public Node createStatisticsTable() {

        statisticsTable = new TableView<Assignment>();

        statisticsTable.setEditable(false);
        statisticsTable.getSelectionModel().setCellSelectionEnabled(false);

        colProjects = new TableColumn<Project, String>("Project Name");
        colProjectPrio1 = new TableColumn<Project, Integer>("Priority 1");
        colProjectPrio2 = new TableColumn<Project, Integer>("Priority 2");
        colProjectPrio3 = new TableColumn<Project, Integer>("Priority 3");
        colProjectPrio4 = new TableColumn<Project, Integer>("Priority 4");
        colProjectPrio5 = new TableColumn<Project, Integer>("Priority 5");
        colTotal = new TableColumn<Project, Integer>("Total");

        statisticsTable.getColumns().addAll(
                colProjects,
                colProjectPrio1,
                colProjectPrio2,
                colProjectPrio3,
                colProjectPrio4,
                colProjectPrio5,
                colTotal);

        ObservableList<Project> tableValues = FXCollections.observableArrayList();

        tableValues.addAll(statisticsController.getProjectPickList());

        colProjects.setCellValueFactory(new PropertyValueFactory<Project, String>("projectNumber"));

        colProjectPrio1.setCellValueFactory(new PropertyValueFactory<Project, Integer>("prio1"));
        colProjectPrio2.setCellValueFactory(new PropertyValueFactory<Project, Integer>("prio2"));
        colProjectPrio3.setCellValueFactory(new PropertyValueFactory<Project, Integer>("prio3"));
        colProjectPrio4.setCellValueFactory(new PropertyValueFactory<Project, Integer>("prio4"));
        colProjectPrio5.setCellValueFactory(new PropertyValueFactory<Project, Integer>("prio5"));

        colTotal.setCellValueFactory(new PropertyValueFactory<Project, Integer>("total"));

        statisticsTable.setItems(tableValues);

        return statisticsTable;
    }

    public Node createTableToolBar() {

        HBox topBox = new HBox();

        ToolBar bar = new ToolBar(
                this.btnSave);

        bar.setMinWidth(Double.MAX_VALUE);

        topBox.getChildren().add(bar);

        return topBox;
    }

    // ----- getters and setters -----

    public Button getBtnSave() { return this.btnSave; }

}
