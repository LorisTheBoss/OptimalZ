package source.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import source.model.OptimalZmodel;

/**
 * Created by LorisGrether on 02.06.2017.
 */
public class CostManipuationView {

    private Stage settingStage;
    private OptimalZmodel model;

    private Label lblInfoText;
    private Label lblErrorText = new Label("Cost Value Error!");

    private TextField txtCost1;
    private TextField txtCost2;
    private TextField txtCost3;
    private TextField txtCost4;
    private TextField txtCost5;
    private Button btnOkay = new Button("OK");

    public CostManipuationView(OptimalZmodel model) {

        settingStage = new Stage();
        this.model = model;

        BorderPane root = new BorderPane();

        root.setTop(createTop());
        root.setCenter(createCenter());

        Scene scene = new Scene(root, 500, 250);

        settingStage.setTitle("Cost Manipulation");

        settingStage.setScene(scene);

        settingStage.setResizable(false);
        settingStage.requestFocus();
        settingStage.setAlwaysOnTop(true);
        settingStage.initModality(Modality.APPLICATION_MODAL);

        eventhandler();

        settingStage.show();
    }

    private void eventhandler() {

        this.btnOkay.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                if (checkCostValues()) {

                    int[] newCostArray = {
                            Integer.valueOf(txtCost1.getText()),
                            Integer.valueOf(txtCost2.getText()),
                            Integer.valueOf(txtCost3.getText()),
                            Integer.valueOf(txtCost4.getText()),
                            Integer.valueOf(txtCost5.getText())};

                    model.setCostArray(newCostArray);

                    settingStage.close();
                }
            }
        });
    }

    //Check if the values that are typed in from the user are integers in a range from 0-50 with some simple regular expressions
    private boolean checkCostValues() {

        if (txtCost1.getText().matches("^(?:[0-9]|[1-4][0-9]|50)$")) {
            if (txtCost2.getText().matches("^(?:[0-9]|[1-4][0-9]|50)$")) {
                if (txtCost3.getText().matches("^(?:[0-9]|[1-4][0-9]|50)$")) {
                    if (txtCost4.getText().matches("^(?:[0-9]|[1-4][0-9]|50)$")) {
                        if (txtCost5.getText().matches("^(?:[0-9]|[1-4][0-9]|50)$")) {
                            return true;
                        }
                    }
                }
            }
        }

        lblErrorText.setVisible(true);
        return false;
    }

    private Node createTop() {

        VBox box = new VBox(1);

        lblInfoText = new Label("Here you can adjust the cost values used for calculating the assignment. \n" +
                "Allowed are numbers (integer) in a range from 0-50. \n" +
                "Default the cost values are set from 0-4 (linear).\n" +
                "The costs can just be changed once per session and it must be before the first calculation.\n \n");

        lblInfoText.setStyle("-fx-font-weight: bolder");

        box.getChildren().add(lblInfoText);

        return box;
    }

    private Node createCenter() {

        GridPane centerGrid = new GridPane();
        btnOkay.setMaxWidth(60);

        txtCost1 = new TextField("0");
        txtCost2 = new TextField("1");
        txtCost3 = new TextField("2");
        txtCost4 = new TextField("3");
        txtCost5 = new TextField("4");

        txtCost1.setMaxWidth(50);
        txtCost2.setMaxWidth(50);
        txtCost3.setMaxWidth(50);
        txtCost4.setMaxWidth(50);
        txtCost5.setMaxWidth(50);

        centerGrid.add(new Label("Cost for priority 1:   "), 0, 0);
        centerGrid.add(new Label("Cost for priority 2:   "), 0, 1);
        centerGrid.add(new Label("Cost for priority 3:   "), 0, 2);
        centerGrid.add(new Label("Cost for priority 4:   "), 0, 3);
        centerGrid.add(new Label("Cost for priority 5:   "), 0, 4);

        centerGrid.add(txtCost1, 1, 0);
        centerGrid.add(txtCost2, 1, 1);
        centerGrid.add(txtCost3, 1, 2);
        centerGrid.add(txtCost4, 1, 3);
        centerGrid.add(txtCost5, 1, 4);

        centerGrid.add(new Label(""), 0, 5);
        centerGrid.add(btnOkay, 0, 6);

        lblErrorText.setStyle("-fx-text-fill: red;" +
                "-fx-font-weight: bolder;" +
                "-fx-font-size: 15px");

        lblErrorText.setVisible(false);

        centerGrid.add(lblErrorText, 1, 6);

        return centerGrid;
    }
}
