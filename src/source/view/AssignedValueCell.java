package source.view;

import javafx.scene.control.TableCell;
import javafx.scene.paint.Color;
import source.Assignment;

/**
 * Created by Jonas on 10.05.2017.
 */
public class AssignedValueCell extends TableCell<Assignment, String> {

    private final String color;

    public AssignedValueCell(String color){
        super();
        this.color = "-fx-background-color: " + color;
    }


    @Override
    protected void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);

        if (item == null
                || empty
                || getIndex()>getTableView().getItems().size()
                || getIndex()<0) {
            setText(null);
            setStyle("");
        } else {
            Assignment assignment = getTableView().getItems().get(getIndex());
            setText(item);
            if (item.equals(assignment.getAssignedProject())) {
                setTextFill(Color.BLACK);
                setStyle(color);
            } else {
                setTextFill(Color.BLACK);
                setStyle("");
                //setStyle("-fx-background-color: #ffd2f8");
            }
        }
        getTableView().refresh();
    }
}
