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

    //A list that contains all assignments
    private ArrayList<Assignment> listAssignmnet;

    // the listVersions contains all listAssignments and is used for the version control
    private ObservableList<ArrayList<Assignment>> listVersions;

    private boolean isExported = false;

    private int[] costArray = {0, 1, 2, 3, 4};

    private SimpleStringProperty priorityFileName;
    private SimpleStringProperty projectsFileName;

    private boolean areFilesReadIn = false;
    private int actualVersion = 1;

    public OptimalZmodel() {

        this.listAssignmnet = new ArrayList<>();
        this.listVersions = FXCollections.observableArrayList();

        this.priorityFileName = new SimpleStringProperty();
        this.projectsFileName = new SimpleStringProperty();
    }

    // ----- getters and setters ------

    public void setListAssignmnet(ArrayList<Assignment> listAssignmnet) {
        this.listAssignmnet = listAssignmnet;
    }

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

    public int getActualVersion() {
        return actualVersion;
    }

    public void setActualVersion(int actualVersion) {
        this.actualVersion = actualVersion;
    }

    public int[] getCostArray() {
        return costArray;
    }

    public void setCostArray(int[] costArray) {
        this.costArray = costArray;
    }
}