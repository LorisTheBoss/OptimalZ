package source.view;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Created by LorisGrether on 30.04.2017.
 */
public class OptimalZstatisticsView {

    Stage statisticStage;
    Scene scene;


    public OptimalZstatisticsView(String version) {

        statisticStage = new Stage();
        statisticStage.setTitle("OptimalZ - Statistics - " + version);

        BorderPane root = new BorderPane();
        root.setTop(createTop());
        root.setCenter(createCenter());
        root.setBottom(createBottom());

        scene = new Scene(root, 800, 600);


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
