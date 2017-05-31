package source;

import java.util.ArrayList;

/**
 * Created by Tobias on 19.05.2017.
 */
public class Project {

    private static int counter = 1;
    private final int id;
    private int prio1;
    private int prio2;
    private int prio3;
    private int prio4;
    private int prio5;
    private int total;
    private String projectNumber;

    private ArrayList<Project> projectWithNumberOfPicks = new ArrayList<>();

    /**
     * Priorities indicate how often the project has been chosen as the respective priority
     * @param projectNumber
     */
    public Project(String projectNumber) {
        id = counter++;
        this.projectNumber = projectNumber;
        prio1 = 0;
        prio2 = 0;
        prio3 = 0;
        prio4 = 0;
        prio5 = 0;
        total = 0;
    }

    /**
     * Increases the project's prio1 attribute if the project is detected as the student's 1st priority
     */
    public void increasePrio1() {
        this.prio1++;
    }

    /**
     * Increases the project's prio2 attribute if the project is detected as the student's 2nd priority
     */
    public void increasePrio2() {
        this.prio2++;
    }

    /**
     * Increases the project's prio3 attribute if the project is detected as the student's 3rd priority
     */
    public void increasePrio3() {
        this.prio3++;
    }

    /**
     * Increases the project's prio4 attribute if the project is detected as the student's 4th priority
     */
    public void increasePrio4() {
        this.prio4++;
    }

    /**
     * Increases the project's prio5 attribute if the project is detected as the student's 5th priority
     */
    public void increasePrio5() {
        this.prio5++;
    }

    /**
     * Increases the project's total attribute
     */
    public void increaseTotal() {
        this.total++;
    }


    //Getters & setters

    public int getID() {
        return id;
    }

    public String getProjectNumber() {
        return projectNumber;
    }

    public int getPrio1() {
        return prio1;
    }

    public int getPrio2() {
        return prio2;
    }

    public int getPrio3() {
        return prio3;
    }

    public int getPrio4() {
        return prio4;
    }

    public int getPrio5() {
        return prio5;
    }

    public int getTotal() {
        return total;
    }

    public ArrayList<Project> getProjectWithNumberOfPicks() {
        return projectWithNumberOfPicks;
    }

    public void setPrio1(int prio1) {
        this.prio1 = prio1;
    }

    public void setPrio2(int prio2) {
        this.prio2 = prio2;
    }

    public void setPrio3(int prio3) {
        this.prio3 = prio3;
    }

    public void setPrio4(int prio4) {
        this.prio4 = prio4;
    }

    public void setPrio5(int prio5) {
        this.prio5 = prio5;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public void setProjectNumber(String projectNumber) {
        this.projectNumber = projectNumber;
    }
}
