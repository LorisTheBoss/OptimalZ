package source.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import source.Assignment;
import source.Project;
import source.ProjectAssigner;
import source.model.OptimalZmodel;
import source.view.OptimalZstatisticsView;
import source.view.OptimalZview;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;



/**
 * Created on 05.05.2017.
 * This class is basically responsible for everything that is statistics-related.
 */

public class OptimalZstatisticsController {


    OptimalZmodel model;
    Assignment assignment;
//    OptimalZview view; //just because ProjectAssigner wants it in constructor
    ProjectAssigner assigner;
    OptimalZstatisticsView statisticsView;


    /**
     * Constructor
     * @param model
     * @param assigner
     * @param assignment
     */

 //   public  OptimalZstatisticsController(OptimalZmodel model, ProjectAssigner assigner, Assignment assignment, OptimalZstatisticsView statisticsView) {
        public  OptimalZstatisticsController(OptimalZmodel model, ProjectAssigner assigner, Assignment assignment, OptimalZstatisticsView optimalZstatisticsView) {
        this.model = model;
        this.assigner = assigner;
        this.assignment = assignment;
        this.statisticsView = optimalZstatisticsView;

                eventhandler();
    }


    /**
     * Calculates how many projects have been assigned that were chosen as 1st priority
     * @return int
     */
    public int priorityOne() {

        int i = 0;
        int number = 0;

        //gets the actual version that is displayed
        int actualVersion = model.getActualVersion();
        ArrayList<Assignment> arrAssignments = model.getListVersions().get(actualVersion - 1); //arrAssignments now is the list with the current  assignments

        while (i <= arrAssignments.size() - 1) {
            if (arrAssignments.get(i).getCost() != null && arrAssignments.get(i).getCost() == 0.0) {
                number += 1;
            }
            i++;
        }

        return number;
    }

    /**
     * Calculates how many projects have been assigned that were chosen as 2nd priority
     * @return int
     */
    public int priorityTwo() {

        int i = 0;
        int number = 0;

        //gets the actual version that is displayed
        int actualVersion = model.getActualVersion();
        ArrayList<Assignment> arrAssignments = model.getListVersions().get(actualVersion - 1); //arrAssignments now is the list with the current assignments

        while (i <= arrAssignments.size() - 1) {
            if (arrAssignments.get(i).getCost() != null && arrAssignments.get(i).getCost() == 1.0) {
                number += 1;
            }
            i++;
        }

        return number;
    }

    /**
     * Calculates how many projects have been assigned that were chosen as 3rd priority
     * @return int
     */
    public int priorityThree() {

        int i = 0;
        int number = 0;

        //gets the actual version that is displayed
        int actualVersion = model.getActualVersion();
        ArrayList<Assignment> arrAssignments = model.getListVersions().get(actualVersion - 1); //arrAssignments now is the list with the current assignments

        while (i <= arrAssignments.size() - 1) {
            if (arrAssignments.get(i).getCost() != null && arrAssignments.get(i).getCost() == 2.0) {
                number += 1;
            }
            i++;
        }

        return number;
    }


    /**
     * Calculates how many projects have been assigned that were chosen as 4th priority
     * @return int
     */
    public int priorityFour() {

        int i = 0;
        int number = 0;

        //gets the actual version that is displayed
        int actualVersion = model.getActualVersion();
        ArrayList<Assignment> arrAssignments = model.getListVersions().get(actualVersion - 1); //arrAssignments now is the list with the current assignments

        while (i <= arrAssignments.size() - 1) {
            if (arrAssignments.get(i).getCost() != null && arrAssignments.get(i).getCost() == 3.0) {
                number += 1;
            }
            i++;
        }

        return number;
    }

    /**
     * Calculates how many projects have been assigned that were chosen as 5th priority
     * @return int
     */
    public int priorityFive() {

        int i = 0;
        int number = 0;

        //gets the actual version that is displayed
        int actualVersion = model.getActualVersion();
        ArrayList<Assignment> arrAssignments = model.getListVersions().get(actualVersion - 1); //arrAssignments now is the list with the current assignments

        while (i <= arrAssignments.size() - 1) {
            if (arrAssignments.get(i).getCost() != null && arrAssignments.get(i).getCost() == 4.0) {
                number += 1;
            }
            i++;
        }

        return number;
    }

    /**
     * Calculates how many own projects the calculation contains
     * @return int
     */
    public int ownProjects() {

        int i = 0;
        int number = 0;

        //gets the actual version that is displayed
        int actualVersion = model.getActualVersion();
        ArrayList<Assignment> arrAssignments = model.getListVersions().get(actualVersion - 1); //arrAssignments now is the list with the current assignments

        while (i <= arrAssignments.size() - 1) {
            String assigneProject = arrAssignments.get(i).getAssignedProject();
            if (assigneProject.equalsIgnoreCase("own") || assigneProject.equalsIgnoreCase("eigenes") ) {
                number += 1;
            }
            i++;
        }

        return number;
    }


    /**
     * Calculates the total cost of the assignment
     * @return double
     */
    public double totalCost() {

        //gets the actual version that is displayed
        int actualVersion = model.getActualVersion();
        ArrayList<Assignment> arrAssignments = model.getListVersions().get(actualVersion - 1); //arrAssignments now is the list with the current assignments
        double totalCost = 0.0;

        int i= 0;
        while (i <= arrAssignments.size() - 1) {
            if (arrAssignments.get(i).getCost() != null) {
                totalCost += arrAssignments.get(i).getCost();
            } else {
                //do nothing
            }
            i++;
        }

        return totalCost;
    }


    /**
     * Calculates the average cost of the assignment
     * @return double
     */
    public double averageCost() {

        //gets the actual version that is displayed
        int actualVersion = model.getActualVersion();
        ArrayList<Assignment> arrAssignments = model.getListVersions().get(actualVersion - 1); //arrAssignments now is the list with the current assignments
        double totalCost = 0.0;

        int i = 0;
        while (i <= arrAssignments.size() - 1) {
            totalCost += arrAssignments.get(i).getCost();
            i++;
        }

        return totalCost / arrAssignments.size();


    }

    /**
     * Calculates the number of groups
     * @return int
     */
    public int numberOfGroups() {
        int groups = model.getListVersions().get(0).size();
        return groups;

    }

    /**
     * Calculates the list with each project and how often it has been chosen with which priority
     * @return ArrayList<Project>
     */
    public ArrayList<Project> getProjectPickList() {

        int listLength = assigner.getProjectNumbers().size(); //debugging
        ArrayList<Project> projectWithNumberOfPicks = new ArrayList<>(listLength); //define capacity first for performance reasons
        int projectAtIndex = 0;

        //Hier holen wir die Anzahl der Projekte und weisen sie einem String zu
        while (projectAtIndex < assigner.getProjectNumbers().size()) { //if assigner.getProjectNumbers().size() -1, Own in list will not be considered as a project
            String projectNumber = assigner.getProjectNumbers().get(projectAtIndex);
            Project project = new Project(projectNumber);
            int assignmentAtIndex = 0;

            //Hier holen wir ein Assignment aus der Liste aller Assignments und speichern sie in assignment
            while (assignmentAtIndex < model.getListAssignmnet().size() - 1) {
                assignment = model.getListAssignmnet().get(assignmentAtIndex);

                int valueAtKey = 1;
                while (valueAtKey <= 5) {
                    String chosenProject = assignment.getChosenProjects().get(valueAtKey);


                    if (projectNumber.equals(chosenProject)) {
                        switch (Integer.valueOf(valueAtKey)) {
                            case 1:
                                project.increasePrio1();
                                project.increaseTotal();
                                break;
                            case 2:
                                project.increasePrio2();
                                project.increaseTotal();
                                break;
                            case 3:
                                project.increasePrio3();
                                project.increaseTotal();
                                break;
                            case 4:
                                project.increasePrio4();
                                project.increaseTotal();
                                break;
                            case 5:
                                project.increasePrio5();
                                project.increaseTotal();
                                break;
                        }
                    }
                    valueAtKey++;
                }
                assignmentAtIndex++;
            }
            projectWithNumberOfPicks.add(project);
            projectAtIndex++;
        }
        return projectWithNumberOfPicks;
    }

    /**
     * Creates a CSV that contains the the information from the getProjectPickList() method
     * @throws IOException
     */
    public boolean writeProjectListToCSV() throws IOException {
        boolean bool = false;
        File desktop = new File(System.getProperty("user.home"), "Desktop");
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        Date date = new Date();
        String today = dateFormat.format(date);
        FileWriter fileWriter = new FileWriter(desktop.getAbsolutePath() + "\\Projektstatistik_" + today + ".csv");
        String FILE_HEADER = "Project" + ";" + "PRIO 1" + ";" + "PRIO 2" + ";" + "PRIO 3" + ";" + "PRIO 4" + ";" + "PRIO 5" + ";" + "Total";
        String NEW_LINE_SEPARATOR = "\n";
        ArrayList<Project> projectWithNumberOfPicks = getProjectPickList();
        int projectAtIndex  = 0;

        try {
            fileWriter.append(FILE_HEADER);
            fileWriter.append(NEW_LINE_SEPARATOR);

            while (projectAtIndex < projectWithNumberOfPicks.size()) {
                Project p = projectWithNumberOfPicks.get(projectAtIndex);
                fileWriter.append(p.getProjectNumber() + ";");
                fileWriter.append(p.getPrio1() + ";");
                fileWriter.append(p.getPrio2() + ";");
                fileWriter.append(p.getPrio3() + ";");
                fileWriter.append(p.getPrio4() + ";");
                fileWriter.append(p.getPrio5() + ";");
                fileWriter.append(p.getTotal() + NEW_LINE_SEPARATOR);

                fileWriter.flush();
                projectAtIndex++;
                bool = true;
            }

        } catch (Exception e) {
            //TODO catch this in the frontend
            System.err.println("Something went wrong during export");
        } finally {
            fileWriter.flush();
            fileWriter.close();
        }

        return bool;


    }

    /**
     * Returns the number of projects that are in the list of projects
     * @return int
     */
    public int numberOfProjects(){
        int projects = 0;

        projects = assigner.getProjectNumbers().size() - 1; //-1 becase of own project

        return projects;
    }

    private void eventhandler() {

        this.statisticsView.getBtnSave().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    if (writeProjectListToCSV()) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Good News!");
                        alert.setHeaderText(null);
                        alert.setContentText("The statistics is stored on the desktop. ");
                        alert.showAndWait();
                    } else {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Bad News!");
                        alert.setHeaderText(null);
                        alert.setContentText("Something went wrong during export. Try again.");
                        alert.showAndWait();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }



}
