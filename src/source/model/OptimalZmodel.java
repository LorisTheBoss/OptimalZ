package source.model;


import source.Assignment;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class OptimalZmodel {

    private ArrayList<Assignment> listAssignmnet;

    private ArrayList<ArrayList<Assignment>> listVersions;

    public OptimalZmodel(){

        this.listAssignmnet = new ArrayList<>();
        this.listVersions = new ArrayList<>();
    }

    /**
     * @author Tobias Gerhard
     * Responsible for the export of the final list
     */
    public void csvWriter() throws IOException {

        String COMMA_DELIMITER = ",";
        String NEW_LINE_SEPARATOR = "\n";
        String FILE_HEADER = "GROUP,PROJECT";
        //FileWriter fileWriter = new FileWriter("C:\\Users\\Tobias\\Desktop\\AssignmentList.csv");
        File desktop = new File(System.getProperty("user.home"), "Desktop");
        FileWriter fileWriter = new FileWriter(desktop.getAbsolutePath() + "\\ProjektAssignment.csv");
        try {
            fileWriter.append(FILE_HEADER);
            for (int i = 1; i <= listAssignmnet.size(); i++) {
                Assignment a = listAssignmnet.get(i - 1);
                fileWriter.append(NEW_LINE_SEPARATOR);
                fileWriter.append(a.getName());
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(a.getAssignedProject());
                fileWriter.flush();
            }
        } catch (Exception e) {
            System.err.println("Something went wrong during export");
        } finally {
            fileWriter.flush();
            fileWriter.close();
        }
    }


    public ArrayList<Assignment> getListAssignmnet() {
        return listAssignmnet;
    }

    public ArrayList<ArrayList<Assignment>> getListVersions() {
        return listVersions;
    }

}
