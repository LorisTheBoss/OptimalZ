package source.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import source.Assignment;
import source.Project;
import source.ProjectAssigner;
import source.controller.OptimalZstatisticsController;
import source.model.OptimalZmodel;

import java.util.ArrayList;

/**
 * Created by JonasStucki on 30.04.2017.
 */
public class OptimalZstatisticsView {

    //Table
    private TableView statisticsTable;

    private TableColumn colID;
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
    private Tab tab4;

    //Charts
    private BarChart prioDistQual;
    private CategoryAxis xAxis;
    private NumberAxis yAxis;

    private PieChart chart;

    private VBox bottomBox;
    private HBox statusBox;

    Stage statisticStage;
    Scene scene;

    private OptimalZmodel model;
    private OptimalZstatisticsController statisticsController;


    //public OptimalZstatisticsView(String version) {
    public OptimalZstatisticsView(OptimalZmodel model, ProjectAssigner assigner, Assignment assignment) {

        statisticStage = new Stage();
        this.model = model;
        this.statisticsController = new OptimalZstatisticsController(model, assigner, assignment);

        //statisticStage.setTitle("OptimalZ - Statistics - " + version);
        statisticStage.setTitle("OptimalZ - Statistics");

        BorderPane root = new BorderPane();
        root.setTop(createTop());
        root.setCenter(createCenter());
        root.setBottom(createBottom());

        scene = new Scene(root, 800, 600);

        this.setControlIDs();
        this.setToolTipText();

        String css = this.getClass().getResource("/css/OptimalZStatistics.css").toExternalForm();
        scene.getStylesheets().add(css);

        statisticStage.setScene(scene);
        statisticStage.show();

    }

    public Parent createContentPrioBarChart() {
        String[] priorities = {"Prio 1", "Prio 2", "Prio 3", "Prio 4", "Prio 5", "Own"};
        xAxis = new CategoryAxis();
        xAxis.setCategories(FXCollections.<String>observableArrayList(priorities));
        double number = model.getListAssignmnet().size();
        yAxis = new NumberAxis("Number of Assignements", 0.0d, number/2, number);

        //TODO delete this, its only for debugging
        statisticsController.getProjectPickList();


        ObservableList<BarChart.Series> barChartData = FXCollections.observableArrayList(
                new BarChart.Series("Number of Assignments", FXCollections.observableArrayList(
                        new BarChart.Data(priorities[0], statisticsController.priorityOne()),
                        new BarChart.Data(priorities[1], statisticsController.priorityTwo()),
                        new BarChart.Data(priorities[2], statisticsController.priorityThree()),
                        new BarChart.Data(priorities[3], statisticsController.priorityFour()),
                        new BarChart.Data(priorities[4], statisticsController.priorityFive()),
                        new BarChart.Data(priorities[5], statisticsController.ownProjects())
                ))
//                new BarChart.Series("Lemons", FXCollections.observableArrayList(
//                        new BarChart.Data(years[0], 956),
//                        new BarChart.Data(years[1], 1665),
//                        new BarChart.Data(years[2], 2559)
//                )),
//                new BarChart.Series("Oranges", FXCollections.observableArrayList(
//                        new BarChart.Data(years[0], 1154),
//                        new BarChart.Data(years[1], 1927),
//                        new BarChart.Data(years[2], 2774)
//                ))
        );
        prioDistQual = new BarChart(xAxis, yAxis, barChartData, 25.0d);
        return prioDistQual;
    }

    public static ObservableList<PieChart.Data> generateData() {
        return FXCollections.observableArrayList(
                new PieChart.Data("Optimal Z", 5),
                new PieChart.Data("Project 2", 45),
                new PieChart.Data("Project 3", 25),
                new PieChart.Data("Project 4", 15),
                new PieChart.Data("Project 5", 10));
    }

    public Parent createContentPieChart() {
        chart = new PieChart(generateData());
        chart.setClockwise(false);
        return chart;
    }

    private Node createTop() {
        HBox topBox = new HBox();

        return topBox;
    }

    private Node createCenter() {
//        HBox centerBox = new HBox();
//
//        return centerBox;

        tabPane = new TabPane();
        tab1 = new Tab();
        tab2 = new Tab();
        tab3 = new Tab();
        tab4 = new Tab();

        tabPane.setRotateGraphic(false);
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        tabPane.setSide(Side.TOP);
        final VBox vbox = new VBox();
        vbox.setSpacing(10);
        vbox.setTranslateX(10);
        vbox.setTranslateY(10);
        // Initial tab with buttons for experimenting
        tab1.setText("Overview");
        final VBox vboxOverview = new VBox();
        vboxOverview.setSpacing(10);
        vboxOverview.setTranslateX(10);
        vboxOverview.setTranslateY(10);
        // Set Image to tap
//        final Image image = new Image(getClass().getResourceAsStream("tab_16.png"));
//        final ImageView imageView = new ImageView();
//        imageView.setImage(image);
//        tab1.setGraphic(imageView);
        Label lblVersion = new Label("Statistics to the Version: " + "\t" +  "\t" + "\t" + "\t" + model.getActualVersion());
//        Label lblAvCost = new Label("The Average Cost is: " + statisticsController.averageCost());
        Label lblTotCost = new Label("The total Cost of this Assignment is: " + "\t" + statisticsController.totalCost());
        Label lblNoGroups = new Label("The total number of groups is: " + "\t" + "\t" + statisticsController.numberOfGroups());
//        Label lblNoChoosenProjects = new Label("The total number of choosen projects is: " + statisticsController.numberOfChosenProjects());
        Label lblNoProjects = new Label("The total number of projects is: " + "\t" + "\t" + "XY");

        //Label lblChosenProjects = new Label("Groups/students have totally chosen: " + statisticsController.numberOfChosenProjects() + " projects");


        /*TODO lblChosenProjects hinzufügen*/
        vboxOverview.getChildren().addAll(lblVersion, lblTotCost, lblNoGroups, lblNoProjects);

        setUpControlButtons(vbox);
        tab1.setContent(vboxOverview);
        //tab1.setContent(vbox);
        tabPane.getTabs().add(tab1);

        // Tab2 has longer label and toggles tab closing
        tab2.setText("Priority Distribution Quality");
        final VBox vboxLongTab = new VBox();
        vboxLongTab.setSpacing(10);
        vboxLongTab.setTranslateX(10);
        vboxLongTab.setTranslateY(10);

        Label explainRadios = new Label("Here we could put the first Statistic:");
        vboxLongTab.getChildren().add(explainRadios);

        //Add Barchart to tab 2
        vboxLongTab.getChildren().add(this.createContentPrioBarChart());

        tab2.setContent(vboxLongTab);
        tabPane.getTabs().add(tab2);


        // Tab 3 has a checkbox for showing/hiding tab labels
        tab3.setText("Project popularity distribution");
        final VBox vboxTab3 = new VBox();
        vboxTab3.setSpacing(10);
        vboxTab3.setTranslateX(10);
        vboxTab3.setTranslateY(10);

        Label thirdTab = new Label("Here we could put the second Statistic:");
        vboxTab3.getChildren().add(thirdTab);

        //Add Pie Chart to tab 3
        vboxTab3.getChildren().add(this.createContentPieChart());


        tab3.setContent(vboxTab3);
        tabPane.getTabs().add(tab3);
        //Internal Tabs
        tab4.setText("Project Statistics Table");
        BorderPane content = new BorderPane();
        content.setTop(createTableToolBar());
        content.setCenter(createStatisticsTable());

        tab4.setContent(content);
        tabPane.getTabs().add(tab4);
        return tabPane;

    }

    private Node createBottom() {
        HBox bottomBox = new HBox();

        return bottomBox;
    }

    private void setControlIDs() {



        //Button ID's
        this.btnSave.setId("btnSave");

//
       //Tab ID's
        this.tab1.setId("tab1");
        this.tab2.setId("tab2");
        this.tab3.setId("tab3");
        this.tab4.setId("tab4");

//
//        //Label ID's
//        this.lblStatus.setId("lblStatus");
//
       //Pane ID's
        this.tabPane.setId("tabPane");


    }

    private void setToolTipText() {

        this.tab1.setTooltip(new Tooltip("This Tab shows you an Overview of the Statistics Data!"));
        this.tab2.setTooltip(new Tooltip("Tooltip 2"));
        this.tab3.setTooltip(new Tooltip("Tooltip 3"));
        this.tab4.setTooltip(new Tooltip("Tooltip 4"));
        this.btnSave.setTooltip(new Tooltip("Save this Project Statistics Table"));

    }

    private void toggleTabPosition(TabPane tabPane) {
        Side pos = tabPane.getSide();
        if (pos == Side.TOP) {
            tabPane.setSide(Side.RIGHT);
        } else if (pos == Side.RIGHT) {
            tabPane.setSide(Side.BOTTOM);
        } else if (pos == Side.BOTTOM) {
            tabPane.setSide(Side.LEFT);
        } else {
            tabPane.setSide(Side.TOP);
        }
    }

    private void toggleTabMode(TabPane tabPane) {
        if (!tabPane.getStyleClass().contains(TabPane.STYLE_CLASS_FLOATING)) {
            tabPane.getStyleClass().add(TabPane.STYLE_CLASS_FLOATING);
        } else {
            tabPane.getStyleClass().remove(TabPane.STYLE_CLASS_FLOATING);
        }
    }


    public Node createStatisticsTable() {

        statisticsTable = new TableView<Assignment>();

        statisticsTable.setEditable(false);
        statisticsTable.getSelectionModel().setCellSelectionEnabled(false);

        colID = new TableColumn<Project, Integer>("ID");
//        colID.setPrefWidth(35);

        colProjects = new TableColumn<Project, String>("Project Name");
//        colProjects.setPrefWidth(160);

        colProjectPrio1 = new TableColumn<Project, Integer>("Priority 1");
        colProjectPrio2 = new TableColumn<Project, Integer>("Priority 2");
        colProjectPrio3 = new TableColumn<Project, Integer>("Priority 3");
        colProjectPrio4 = new TableColumn<Project, Integer>("Priority 4");
        colProjectPrio5 = new TableColumn<Project, Integer>("Priority 5");
        colTotal = new TableColumn<Project, Integer>("Total");



        statisticsTable.getColumns().addAll(
                colID,
                colProjects,
                colProjectPrio1,
                colProjectPrio2,
                colProjectPrio3,
                colProjectPrio4,
                colProjectPrio5,
                colTotal);


//        ArrayList<Project> tableValues = statisticsController.getProjectPickList();

        ObservableList<Project> tableValues = FXCollections.observableArrayList();

        tableValues.addAll(statisticsController.getProjectPickList());

        colID.setCellValueFactory(new PropertyValueFactory<Project, Integer>("id"));
//        colProjectPrio1.setCellValueFactory(new PropertyValueFactory<Project, Integer>("id"));
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


//    private void setupInternalTab() {


//        StackPane internalTabContent = new StackPane();
//        final TabPane internalTabPane = new TabPane();
//        internalTabPane.getStyleClass().add(TabPane.STYLE_CLASS_FLOATING);
//        internalTabPane.setSide(Side.LEFT);
//
//        internalTabPane.setPrefSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
//        final Tab innerTab = new Tab();
//        innerTab.setText("Tab 1");
//        final VBox innerVbox = new VBox();
//        innerVbox.setSpacing(10);
//        innerVbox.setTranslateX(10);
//        innerVbox.setTranslateY(10);
//        Button innerTabPosButton = new Button("Toggle Tab Position");
//        innerTabPosButton.setOnAction((ActionEvent e) -> {
//            toggleTabPosition(internalTabPane);
//        });
//        innerVbox.getChildren().add(innerTabPosButton);
//        {
//            Button innerTabModeButton = new Button("Toggle Tab Mode");
//            innerTabModeButton.setOnAction((ActionEvent e) -> {
//                toggleTabMode(internalTabPane);
//            });
//            innerVbox.getChildren().add(innerTabModeButton);
//        }
//        innerTab.setContent(innerVbox);
//        internalTabPane.getTabs().add(innerTab);
//
//        for (int i = 1; i < 5; i++) {
//            Tab tab = new Tab();
//            tab.setText("Tab " + i);
//            tab.setContent(new Region());
//            internalTabPane.getTabs().add(tab);
//        }
//        internalTabContent.getChildren().add(internalTabPane);
//        tab4.setContent(internalTabContent);
//    }
//
    private void setUpControlButtons(VBox vbox) {
        // Toggle style class floating
        final Button tabModeButton = new Button("Toggle Tab Mode");
        tabModeButton.setOnAction((ActionEvent e) -> {
            toggleTabMode(tabPane);
        });
        vbox.getChildren().add(tabModeButton);
//        // Tab position
//        final Button tabPositionButton = new Button("Toggle Tab Position");
//        tabPositionButton.setOnAction((ActionEvent e) -> {
//            toggleTabPosition(tabPane);
//        });

    }

}
