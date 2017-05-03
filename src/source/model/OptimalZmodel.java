package source.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import source.Assignment;
import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * Created by LorisGrether on 10.04.2017.
 */
public class OptimalZmodel {

    private ArrayList<Assignment> listAssignmnet;
    private ObservableList<ArrayList<Assignment>> listVersions;
    private boolean isExported = false;

    private SimpleStringProperty priorityFileName;
    private SimpleStringProperty projectsFileName;

    private boolean areFilesReadIn = false;

    public OptimalZmodel() {

        this.listAssignmnet = new ArrayList<>();
        this.listVersions = FXCollections.observableArrayList();

        this.priorityFileName = new SimpleStringProperty();
        this.projectsFileName = new SimpleStringProperty();
    }

    public ObservableList<LinkedHashMap<String,String>> getTableData(){

        ObservableList<LinkedHashMap<String,String>> tableData = FXCollections.observableArrayList();

        for (Assignment assignment: listAssignmnet) {
            LinkedHashMap<String, String> rowData = new LinkedHashMap<>();

            rowData.put("ID", String.valueOf(assignment.getId()));
            rowData.put("Student Names", assignment.getName());
            rowData.put("Assigned Project", assignment.getAssignedProject());

            for (int i = 1; i <= assignment.getChosenProjects().size(); i++){
                rowData.put("Priority " + i, assignment.getChosenProjects().get(i));
            }

            rowData.put("Cost", String.valueOf(assignment.getId()));
            rowData.put("Priority", String.valueOf(assignment.getId()));

            tableData.add(rowData);
        }
        return tableData;
    }


    // ----- getters and setters -----

    public ArrayList<Assignment> getListAssignmnet() {
        return listAssignmnet;
    }

    public ObservableList<ArrayList<Assignment>> getListVersions() {
        return listVersions;
    }

    public boolean getIsExported() {
        return isExported;
    }

    public SimpleStringProperty getPriorityFileName() {
        return priorityFileName;
    }

    public void setPriorityFileName(String priorityFileName) {
        this.priorityFileName.set(priorityFileName);
    }

    public SimpleStringProperty getProjectsFileName() {
        return projectsFileName;
    }

    public void setProjectsFileName(String projectsFileName) {
        this.projectsFileName.set(projectsFileName);
    }

    public boolean getAreFilesReadIn() {
        return areFilesReadIn;
    }

    public void setAreFilesReadIn(boolean areFilesReadIn) {
        this.areFilesReadIn = areFilesReadIn;
    }

    public boolean isExported() {
        return isExported;
    }

    public void setIsExported(boolean exported) {
        isExported = exported;
    }

}
