package source;

import javafx.collections.ObservableList;
import source.controller.OptimalZstatisticsController;
import source.model.OptimalZmodel;
import source.view.OptimalZview;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Scanner;


public class ProjectAssigner {

    //Loris Grether
    private OptimalZmodel model;
    OptimalZview view;

    // MAX_PRIO is used to define a "non-select" of a group-project
    private final int MAX_PRIO = 10;

    // we store both the project codes and the
    // student groups two seperate linked lists
    private LinkedList<String> projectNumbers;
    private LinkedList<String> studentList;

    // this is the cost matrix on which the assignment is compute
    private double[][] costMatrix;


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

    /**
     * reads the two .csv files @param priorityFileName and
     *
     * @param projectNumberListFileName that actually store the student's selection
     *                                  and the available projects in this turn
     */
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

                //LorisGrether
                System.out.println(studentLine);

                lineScanner = new Scanner(studentLine);
                lineScanner.useDelimiter(":");
                String students = lineScanner.next();
                this.studentList.add(students);

                //String[] split = studentLine.split(":");
                //if (checkLineSyntax(split[1])) {

                if (checkLineSyntax(lineScanner.next())) {

                    for (int i = 0; i < studentLine.length(); i++) {

                        if (studentLine.charAt(i) == ':') {

                            studentLine = fillEmptyParameters(studentLine, i);
                        }
                    }

                    String[] split = studentLine.split(":");

                    if (checkName(split[0])) { //checks if the group name is unique

                        //creates a new Assignment
                        model.getListAssignmnet().add(createAssignment(split));
                    }
                }
            }
            scan.close();
            System.out.println();
            System.out.println("CONTROL INFO: Number of Groups = " + studentList.size() + " Number of Projects = " + projectNumbers.size());
            if (studentList.size() > projectNumbers.size()) {
                System.err.println("\nMore groups than projects - complete assignment is not possible!\n");
            }

            model.setAreFilesReadIn(true);

        } catch (FileNotFoundException e) {
            //TODO you have to catch this error in the front end
            view.getLblStatus().setText("ERROR: Invalid filename");
            System.err.println("invalid filename");
            e.printStackTrace();
        }

        //TODO at the moment auskommentiert because it is not needed yet
        //TODO bruche mer das überhaupt no tobi?
        //addValuesToEmptyLists();
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

    private String fillEmptyParameters(String studentLine, int i) {

        if (i == studentLine.length() - 1) {

            studentLine = studentLine + "0";

        } else if (studentLine.charAt(i) == studentLine.charAt(i + 1)) {

            studentLine = studentLine.substring(0, i + 1) + "0" + studentLine.substring(i + 1, studentLine.length());
        }
        return studentLine;
    }

    //This method checks if the used delimiter is only used to split the csv values or if it is used as a character in the name value
    private boolean checkLineSyntax(String project) {

        for (int i = 0; i < this.getProjectNumbers().size(); i++) {

            if (this.getProjectNumbers().get(i).equals(project)) {
                return true;
            }
        }
        return false;
    }

    //checks if the names chosen by the students are unique
    private boolean checkName(String name) {

        for (int i = 0; i < model.getListAssignmnet().size(); i++) {

            if (model.getListAssignmnet().get(i).getName().equals(name)) {

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
        this.fillMatrix(); // fills the matrix with entries MAX_PRIO
        Scanner scan = new Scanner(new File(priorityFileName));
        scan.nextLine();
        Scanner lineScanner;
        int i = 0;
        while (scan.hasNext()) {

            String studentLine = scan.nextLine();

            for (int s = 0; s < studentLine.length(); s++) {

                if (studentLine.charAt(s) == ':') {

                    studentLine = fillEmptyParameters(studentLine, s);
                }
            }

            lineScanner = new Scanner(studentLine);
            lineScanner.useDelimiter(":");

            String[] split = studentLine.split(":");

            if (checkLineSyntax(split[1])) {

                lineScanner.next(); // the student group

                //for (int q = 0; q < 5; q++) {
                for (int q = 1; q < 6; q++) {

                    //String projectCode = lineScanner.next();
                    String projectCode = split[q];

                    System.out.println("prio " + q + ": " + split[q]);

                    int index = this.searchProjectIndex(projectCode); // returns the index of the projected

                    if (index >= 0) {
                        this.costMatrix[i][index] = q;
                    } else {
                        if (index == -1) {
                            this.costMatrix[i][projectNumbers.size() + i] = 0; // own project
                        }
                        if (index == -2) {
                            // empty cell found:
                            // currently we do nothing in this case?!
                        }
                    }
                }
                i++;
            }
        }
        scan.close();
    }


    /**
     * computes the optimal assignment on the basis
     * of the prepared cost matrix (eventual the assignment is printed on the console)
     */
    public void computeAssignment() {
        int[][] assignment = new int[this.studentList.size()][2];
        HungarianAlgorithm ha = new HungarianAlgorithm();
        assignment = ha.hgAlgorithm(costMatrix);
        this.printAssignment(assignment);
    }


    /**
     * prints the @param assignment
     * on the console
     */
    private void printAssignment(int[][] assignment) {

        this.model.getListAssignmnet();

        for (int i = 0; i < assignment.length; i++) {

            //Loris Grether
            for (int j = 0; j < model.getListAssignmnet().size(); j++) {

                if (this.studentList.get(i).equals(model.getListAssignmnet().get(j).getName())) {

                    if (assignment[i][1] < this.projectNumbers.size()) {
                        System.out.println(this.studentList.get(i) + "\t-->\t" + this.projectNumbers.get(assignment[i][1]) + " (Cost = " + this.costMatrix[i][assignment[i][1]] + ")");

                        this.model.getListAssignmnet().get(j).setAssignedProject(this.projectNumbers.get(assignment[i][1]));
                        this.model.getListAssignmnet().get(j).setCost(this.costMatrix[i][assignment[i][1]]);

                    } else {

                        //Loris Grether

                        if (this.costMatrix[i][assignment[i][1]] == 0.0) {
                            this.model.getListAssignmnet().get(j).setAssignedProject("Eigenes");
                        } else if (this.costMatrix[i][assignment[i][1]] == 10.0) {
                            this.model.getListAssignmnet().get(j).setAssignedProject("Nicht Zugewiesen");
                        }

                        //this.model.getListAssignmnet().get(j).setAssignedProject("Eigenes?");
                        System.out.println(this.studentList.get(i) + "\t-->\t Eigenes? " + " (Cost = " + this.costMatrix[i][assignment[i][1]] + ")");
                    }
                }
            }
        }

        //ArrayList<Assignment> test = new ArrayList<>(model.getListAssignmnet());

        this.model.getListVersions().add(new ArrayList<>(model.getListAssignmnet()));

        //this.model.getListAssignmnet().clear();

        this.model.getListVersions();
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
        if (projectCode.contains("eig") || projectCode.contains("Eig")) {
            return -1;
        } else {
            return -2;
        }
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

    /**
     * @author Tobias Gerhard
     * Method that fills empty values with space (" ")
     */
    private void addValuesToEmptyLists() {
        //Some toby stuff
        int p = 0;

        while (p <= model.getListAssignmnet().size() - 1) {
            if (model.getListAssignmnet().get(p).getChosenProjects().size() < 5) {

                int numberOfValues = model.getListAssignmnet().get(p).getChosenProjects().size();
                int inCreasingNumber = numberOfValues + 1; //this number is necessary to count to 5, so that the keys can be filled

                //dann, adde so viele values
                while (inCreasingNumber <= 5) {
                    model.getListAssignmnet().get(p).getChosenProjects().put(inCreasingNumber, "10");
                    inCreasingNumber++;
                    System.out.println("done: " + inCreasingNumber);
                }

            }
            p++;
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
            for (int j = 0; j < this.costMatrix.length; j++) {
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
