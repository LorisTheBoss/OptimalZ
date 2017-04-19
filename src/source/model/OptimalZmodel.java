package source.model;

import source.Assignment;

import java.util.ArrayList;

public class OptimalZmodel {

    private ArrayList<Assignment> listAssignmnet;

    private ArrayList<ArrayList<Assignment>> listVersions;

    public OptimalZmodel(){

        this.listAssignmnet = new ArrayList<>();
        this.listVersions = new ArrayList<>();
    }


    public ArrayList<Assignment> getListAssignmnet() {
        return listAssignmnet;
    }

    public ArrayList<ArrayList<Assignment>> getListVersions() {
        return listVersions;
    }
}
