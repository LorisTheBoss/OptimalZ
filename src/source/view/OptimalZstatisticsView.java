package source.view;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Created by LorisGrether on 30.04.2017.
 */
public class OptimalZstatisticsView {

    private VBox bottomBox;
    private HBox statusBox;

    Stage statisticStage;
    Scene scene;


    //public OptimalZstatisticsView(String version) {
    public OptimalZstatisticsView() {

        statisticStage = new Stage();

        //statisticStage.setTitle("OptimalZ - Statistics - " + version);
        statisticStage.setTitle("OptimalZ - Statistics");

        BorderPane root = new BorderPane();
        root.setTop(createTop());
        root.setCenter(createCenter());
        root.setBottom(createBottom());

        scene = new Scene(root, 800, 600);

        //String css = this.getClass().getResource("/css/OptimalZ.css").toExternalForm();
        //scene.getStylesheets().add(css);


        statisticStage.setScene(scene);
        statisticStage.show();

    }

    private Node createTop() {
        HBox topBox = new HBox();

        return topBox;
    }

    private Node createCenter() {
        HBox centerBox = new HBox();

        return centerBox;
    }

    private Node createBottom() {
        HBox bottomBox = new HBox();

        return bottomBox;
    }
}
