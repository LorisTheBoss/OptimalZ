package source;

import javafx.collections.ObservableList;
import source.controller.OptimalZstatisticsController;
import source.model.OptimalZmodel;
import source.view.OptimalZview;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;


public class ProjectAssigner {

    public ProjectAssigner() {

    }

    //Loris Grether
    private OptimalZmodel model;
    OptimalZview view;

    // MAX_PRIO is used to define a "non-select" of a group-project
    public final int MAX_PRIO = 100;

    // we store both the project codes and the
    // student groups two seperate linked lists
    private LinkedList<String> projectNumbers;
    private LinkedList<String> studentList;

    // this is the cost matrix on which the assignment is compute
    private double[][] costMatrix;
    private double[][] copyOfCostMatrix;

    /**
     * sets up the empty lists for all project numbers available
     * and the list of all student groups
     *
     * @param model
     */
    public ProjectAssigner(OptimalZmodel model, OptimalZview view) {
        this.model = model;
        this.view = view;
        this.projectNumbers = new LinkedList<String>();
        this.studentList = new LinkedList<String>();
    }

    private void buildDeepCopy() {

        for (int i = 0; i < copyOfCostMatrix.length; i++) {
            for (int j = 0; j < copyOfCostMatrix[i].length; j++) {
                copyOfCostMatrix[i][j] = costMatrix[i][j];
            }
        }
    }

    public void valueChange(String studentName, String projectCode) {
        int i = this.studentList.indexOf(studentName);
        int j = this.projectNumbers.indexOf(projectCode);
        for (int k = 0; k < copyOfCostMatrix[i].length; k++) {
            if (k != j) {
                this.copyOfCostMatrix[i][k] = MAX_PRIO;
            } else {
                this.copyOfCostMatrix[i][k] = 0;
            }
        }
    }

    public void recomputeAssignment(HashMap<String, String> map) {

        this.buildDeepCopy();

        Iterator<String> iter = map.keySet().iterator();

        while (iter.hasNext()) {
            String studentName = iter.next();
            String projectCode = map.get(studentName);
            this.valueChange(studentName, projectCode);
        }

        this.computeAssignment();
    }

    public void readCSV(String priorityFileName, String projectNumberListFileName) {
        try {
            Scanner scan = new Scanner(new File(projectNumberListFileName));
            scan.nextLine(); // header is not used
            while (scan.hasNext()) {
                String projectNumber = scan.next();
                this.projectNumbers.add(projectNumber);

                //LorisGrether
                System.out.println(projectNumber);
            }
            scan.close();
            scan = new Scanner(new File(priorityFileName));
            scan.nextLine();  // header is not used
            Scanner lineScanner;

            while (scan.hasNext()) {
                String studentLine = scan.nextLine();

                studentLine = fillEmptyParameters(studentLine);

                //LorisGrether
                System.out.println(studentLine);

                lineScanner = new Scanner(studentLine);
                lineScanner.useDelimiter(":");
                String students = lineScanner.next();
                this.studentList.add(students);

                String[] split = studentLine.split(":");

                if (checkName(split[0])) { //checks if the group name is unique

                    Assignment assignment = new Assignment();

                    assignment.setName(split[0]);
                    assignment.setProjectPrio1(split[1]);
                    assignment.setProjectPrio2(split[2]);
                    assignment.setProjectPrio3(split[3]);
                    assignment.setProjectPrio4(split[4]);
                    assignment.setProjectPrio5(split[5]);

                    for (int i = 1; i < split.length; i++) {

                        assignment.getChosenProjects().put(i, split[i]);
                    }

                    model.getListAssignmnet().add(assignment);
                }
            }
            scan.close();
            System.out.println();
            System.out.println("CONTROL INFO: Number of Groups = " + studentList.size() + " Number of Projects = " + projectNumbers.size());
            this.view.getLblStatus().setText("CONTROL INFO: Number of Groups = " + studentList.size() + " Number of Projects = " + projectNumbers.size());
            if (studentList.size() > projectNumbers.size()) {
                System.err.println("\nMore groups than projects - complete assignment is not possible!\n");
                this.view.getLblStatus().setText("\nMore groups than projects - complete assignment is not possible!\n");
            }

            model.setAreFilesReadIn(true);

        } catch (FileNotFoundException e) {
            //TODO you have to catch this error in the front end
            view.getLblStatus().setText("ERROR: Invalid filename");
            System.err.println("invalid filename");
            e.printStackTrace();
        }
    }

    private Assignment createAssignment(String[] split) {
        Assignment assignment = new Assignment();

        assignment.setName(split[0]);
        assignment.setProjectPrio1(split[1]);
        assignment.setProjectPrio2(split[2]);
        assignment.setProjectPrio3(split[3]);
        assignment.setProjectPrio4(split[4]);
        assignment.setProjectPrio5(split[5]);

        for (int i = 1; i < split.length; i++) {

            assignment.getChosenProjects().put(i, split[i]);
        }
        return assignment;
    }

    private String fillEmptyParameters(String studentLine) {

        String newStudentLine = "";

        for (int i = 0; i < studentLine.length() - 1; i++) {
            char c1 = studentLine.charAt(i);
            char c2 = studentLine.charAt(i + 1);

            newStudentLine += c1;

            if (c1 == ':' && c1 == c2) {
                newStudentLine += " ";
            }
        }

        if (studentLine.charAt(studentLine.length() - 1) == ':') {
            newStudentLine += studentLine.charAt(studentLine.length() - 1) + " ";
        } else {
            newStudentLine += studentLine.charAt(studentLine.length() - 1);
        }
        return newStudentLine;
    }

    //checks if the names chosen by the students are unique
    private boolean checkName(String name) {

        for (int i = 0; i < model.getListAssignmnet().size(); i++) {

            if (model.getListAssignmnet().get(i).getName().equals(name)) {

                view.getLblStatus().setText("Group name exists twice!");
                return false; // the name of the group already exists and the application should escalate (error)
            }
        }

        return true;
    }

    /**
     * prepares the cost matrix on which the
     * assignment is evetuall computed. e.g.
     * p1	p2	p3	p4	p5	p6	p7	p8	d1	d2	d3
     * group1	10	10	0	3	4	2	10	1 	10	10	10
     * group2	10	0	10	2	4	3	10	1 	10	10	10
     * group3	10	10	10	10	10	10	10	10	10	10	0 <<-- encoding for an own project!
     *
     * @param priorityFileName
     * @throws FileNotFoundException
     */
    public void computeCostMatrix(String priorityFileName) throws FileNotFoundException {
        this.costMatrix = new double[studentList.size()][projectNumbers.size() + studentList.size()];
        this.copyOfCostMatrix = new double[studentList.size()][projectNumbers.size() + studentList.size()];
        this.fillMatrix(); // fills the matrix with entries MAX_PRIO
        Scanner scan = new Scanner(new File(priorityFileName));
        scan.nextLine();
        Scanner lineScanner;
        int i = 0;
        while (scan.hasNext()) {
            String studentLine = scan.nextLine();
            studentLine = fillEmptyParameters(studentLine);
            lineScanner = new Scanner(studentLine);

            lineScanner.useDelimiter(":");
            lineScanner.next(); // the student group


            int[] linear = {0, 1, 2, 3, 4};
            int[] quadratic = {0, 1, 4, 9, 16};

            int[] selected = model.getCostArray();

            for (int q = 0; q < 5; q++) {

                String projectCode = lineScanner.next();
                int index = this.searchProjectIndex(projectCode); // returns the index of the project

                if (index >= 0) {
                    this.costMatrix[i][index] = selected[q];
                    //this.costMatrix[i][index] = q;
                } else {
                    if (index == -1) {
                        this.costMatrix[i][projectNumbers.size() + i] = 0; // own project
                    }
                }
            }
            i++;
        }
        scan.close();
        buildDeepCopy();

        printMatrix();
    }


    /**
     * computes the optimal assignment on the basis
     * of the prepared cost matrix (eventual the assignment is printed on the console)
     */
    public void computeAssignment() {
        int[][] assignment = new int[this.studentList.size()][2];
        HungarianAlgorithm ha = new HungarianAlgorithm();
        assignment = ha.hgAlgorithm(copyOfCostMatrix);
        this.printAssignment(assignment);
        list.add(assignment);
    }

    public ArrayList<int[][]> getList() {
        return list;
    }

    private ArrayList<int[][]> list = new ArrayList<int[][]>();


    /**
     * prints the @param assignment
     * on the console
     */
    public void printAssignment(int[][] assignment) {

        this.model.getListAssignmnet();

        ArrayList<Assignment> newVersion = new ArrayList<>();

        for (int i = 0; i < model.getListAssignmnet().size(); i++) {
            Assignment tocopy = model.getListAssignmnet().get(i);
            Assignment copy = new Assignment();
            copy.setId(tocopy.getId());
            copy.setAssignedProject(tocopy.getAssignedProject());
            copy.setName(tocopy.getName());
            copy.setProjectPrio1(tocopy.getProjectPrio1());
            copy.setProjectPrio2(tocopy.getProjectPrio2());
            copy.setProjectPrio3(tocopy.getProjectPrio3());
            copy.setProjectPrio4(tocopy.getProjectPrio4());
            copy.setProjectPrio5(tocopy.getProjectPrio5());
            copy.setCost(tocopy.getCost());
            copy.setLockedBoolean(false);
            newVersion.add(copy);
        }

        for (int i = 0; i < assignment.length; i++) {

            //Loris Grether
            for (int j = 0; j < newVersion.size(); j++) {

                if (this.studentList.get(i).equals(newVersion.get(j).getName())) {

                    if (assignment[i][1] < this.projectNumbers.size()) {
                        System.out.println(this.studentList.get(i) + "\t-->\t" + this.projectNumbers.get(assignment[i][1]) + " (Cost = " + this.costMatrix[i][assignment[i][1]] + ")");

                        newVersion.get(j).setAssignedProject(this.projectNumbers.get(assignment[i][1]));
                        newVersion.get(j).setCost(this.costMatrix[i][assignment[i][1]]);

                    } else {

                        //Loris Grether

                        if (this.costMatrix[i][assignment[i][1]] == 0.0) {
                            newVersion.get(j).setAssignedProject("Eigenes");
                        } else if (this.costMatrix[i][assignment[i][1]] == 100.0) {
                            newVersion.get(j).setAssignedProject("Nicht Zugewiesen");
                        }

                        System.out.println(this.studentList.get(i) + "\t-->\t Eigenes? " + " (Cost = " + this.costMatrix[i][assignment[i][1]] + ")");
                    }
                }
            }
        }
        model.getListVersions().add(newVersion);
        model.setActualVersion(model.getListVersions().size());

    }

    /**
     * prints the @param assignment
     * on the console
     */
    public void printAssignment3(int[][] assignment) {

        this.model.getListAssignmnet();

        for (int i = 0; i < assignment.length; i++) {

            //Loris Grether
            for (int j = 0; j < model.getListVersions().get(model.getActualVersion() - 1).size(); j++) {

                if (this.studentList.get(i).equals(model.getListVersions().get(model.getActualVersion() - 1).get(j).getName())) {

                    if (assignment[i][1] < this.projectNumbers.size()) {
                        System.out.println(this.studentList.get(i) + "\t-->\t" + this.projectNumbers.get(assignment[i][1]) + " (Cost = " + this.costMatrix[i][assignment[i][1]] + ")");

                        this.model.getListVersions().get(model.getActualVersion() - 1).get(j).setAssignedProject(this.projectNumbers.get(assignment[i][1]));
                        this.model.getListVersions().get(model.getActualVersion() - 1).get(j).setCost(this.costMatrix[i][assignment[i][1]]);

                    } else {

                        //Loris Grether

                        if (this.costMatrix[i][assignment[i][1]] == 0.0) {
                            this.model.getListVersions().get(model.getActualVersion() - 1).get(j).setAssignedProject("Eigenes");
                        } else if (this.costMatrix[i][assignment[i][1]] == 100.0) {
                            this.model.getListVersions().get(model.getActualVersion() - 1).get(j).setAssignedProject("Nicht Zugewiesen");
                        }

                        System.out.println(this.studentList.get(i) + "\t-->\t Eigenes? " + " (Cost = " + this.costMatrix[i][assignment[i][1]] + ")");
                    }
                }
            }
        }
    }

    /**
     * @return the index of the projectCode in *this*
     * projectNumbers
     * <p>
     * If projcetCode contains Eig or eig we return -1
     * If the cell is empty or contains somethind unknwon w.r.t.
     * *this* projectNumbers we return -2
     */
    private int searchProjectIndex(String projectCode) {
        for (int i = 0; i < this.projectNumbers.size(); i++) {
            if (projectCode.contains(this.projectNumbers.get(i))) {
                return i;
            }
        }
        return -1;
    }

    /**
     * fills the matrix with MAX_PRIO entries
     */
    private void fillMatrix() {
        for (int i = 0; i < this.costMatrix.length; i++) {
            for (int j = 0; j < this.costMatrix[i].length; j++) {
                this.costMatrix[i][j] = MAX_PRIO;
            }
        }
    }


    // ***************************
    // some methods for debugging
    // ***************************
    private void printList(LinkedList list) {
        for (int i = 0; i < list.size(); i++)
            System.out.println(i + ": " + list.get(i));
    }

    private void printMatrix() {
        for (int i = 0; i < this.costMatrix.length; i++) {
            for (int j = 0; j < this.costMatrix[0].length; j++) {
                System.out.print(this.costMatrix[i][j] + "\t");
            }
            System.out.println();
        }
    }


    // ----- getters and setters -----

    public LinkedList<String> getProjectNumbers() {
        return projectNumbers;
    }

    public LinkedList<String> getStudentList() {
        return studentList;
    }

}
