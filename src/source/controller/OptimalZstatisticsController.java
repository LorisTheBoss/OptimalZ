package source.controller;

import source.Assignment;
import source.Project;
import source.ProjectAssigner;
import source.model.OptimalZmodel;
import source.view.OptimalZstatisticsView;
import source.view.OptimalZview;

import java.util.*;



/**
 * Created by Tobias on 05.05.2017.
 */

public class OptimalZstatisticsController {


    OptimalZmodel model;
    Assignment assignment;
    OptimalZview view; //just because ProjectAssigner wants it in constructor
    ProjectAssigner assigner;


    public  OptimalZstatisticsController(OptimalZmodel model, ProjectAssigner assigner, Assignment assignment) {
        this.model = model;
        this.assigner = assigner;
        this.assignment = assignment;
    }

    public int priorityOne() {

        int i = 0;
        int number = 0;

        //gets the actual version that is displayed
        int actualVersion = model.getActualVersion();
        ArrayList<Assignment> arrAssignments = model.getListVersions().get(actualVersion - 1); //arrAssignments now is the list with the current  assignments

        while (i <= arrAssignments.size() - 1) {
            if (arrAssignments.get(i).getCost() == 0.0) {
                number += 1;
            }
            i++;
        }

        return number;
    }


    public int priorityTwo() {

        int i = 0;
        int number = 0;

        //gets the actual version that is displayed
        int actualVersion = model.getActualVersion();
        ArrayList<Assignment> arrAssignments = model.getListVersions().get(actualVersion - 1); //arrAssignments now is the list with the current assignments

        while (i <= arrAssignments.size() - 1) {
            if (arrAssignments.get(i).getCost() == 1.0) {
                number += 1;
            }
            i++;
        }

        return number;
    }


    public int priorityThree() {

        int i = 0;
        int number = 0;

        //gets the actual version that is displayed
        int actualVersion = model.getActualVersion();
        ArrayList<Assignment> arrAssignments = model.getListVersions().get(actualVersion - 1); //arrAssignments now is the list with the current assignments

        while (i <= arrAssignments.size() - 1) {
            if (arrAssignments.get(i).getCost() == 2.0) {
                number += 1;
            }
            i++;
        }

        return number;
    }

    public int priorityFour() {

        int i = 0;
        int number = 0;

        //gets the actual version that is displayed
        int actualVersion = model.getActualVersion();
        ArrayList<Assignment> arrAssignments = model.getListVersions().get(actualVersion - 1); //arrAssignments now is the list with the current assignments

        while (i <= arrAssignments.size() - 1) {
            if (arrAssignments.get(i).getCost() == 3.0) {
                number += 1;
            }
            i++;
        }

        return number;
    }

    public int priorityFive() {

        int i = 0;
        int number = 0;

        //gets the actual version that is displayed
        int actualVersion = model.getActualVersion();
        ArrayList<Assignment> arrAssignments = model.getListVersions().get(actualVersion - 1); //arrAssignments now is the list with the current assignments

        while (i <= arrAssignments.size() - 1) {
            if (arrAssignments.get(i).getCost() == 4.0) {
                number += 1;
            }
            i++;
        }

        return number;
    }

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


    public double totalCost() {

        //gets the actual version that is displayed
        int actualVersion = model.getActualVersion();
        ArrayList<Assignment> arrAssignments = model.getListVersions().get(actualVersion - 1); //arrAssignments now is the list with the current assignments
        double totalCost = 0.0;

        int i= 0;
        while (i <= arrAssignments.size() - 1) {
            totalCost += arrAssignments.get(i).getCost();
            i++;
        }

        return totalCost;
    }


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

    public int bestPriority() {

        //gets the actual version that is displayed
        int actualVersion = model.getActualVersion();
        ArrayList<Assignment> arrAssignments = model.getListVersions().get(actualVersion - 1); //arrAssignments now is the list with the current assignments
        int priority = 5;

        int i = 0;
        while (i <= arrAssignments.size() - 1) {
            if (arrAssignments.get(i).getCost() < priority) {
                priority = arrAssignments.get(i).getCost().intValue();
            }
        }

        return priority;
    }

    /*TODO funktioniert noch nicht*/
    public int numberOfChosenProjects() {

        ArrayList<Assignment> arrAssignments = model.getListVersions().get(0);
        ArrayList<String> allChosenProjects = new ArrayList<>();
        int i = 0;
        Assignment a;
        String s;

        while (i <= arrAssignments.size() - 1) {
            a = arrAssignments.get(i);
            int z = 1;

            while (z <= 5) {
                s = a.getChosenProjects().get(z); //TODO hier is das Problem

                if (!allChosenProjects.contains(s)) {
                    allChosenProjects.add(s);
                }
                z++;
            }
        }

        return allChosenProjects.size();
    }

    public int numberOfGroups() {
        int groups = model.getListVersions().get(0).size();
        return groups;

    }

    /*TODO write method that counts the number of projects*/
    public int numberOfProjects() {

        return 0;
    }

    public ArrayList<Project> listOfProjectsWithNumberOfPicks() {

        int listLength = assigner.getProjectNumbers().size(); //debugging
        ArrayList<Project> projectWithNumberOfPicks = new ArrayList<>(listLength); //define capacity first for performance reasons
        int projectAtIndex = 0;

        //Hier holen wir die Anzahl der Projekte und weisen sie einem String zu
        while (projectAtIndex < assigner.getProjectNumbers().size() - 1) { //if not -1, Own in list will also be calculated as a project
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

        //TODO Löschen (it's for debugging)
        int y = 0;
        while (y < projectWithNumberOfPicks.size()) {
            System.out.print("We have so many projects: " + projectWithNumberOfPicks.size() + "\t");
            Project p = projectWithNumberOfPicks.get(y);
            System.out.println("Project Number: " + p.getProjectNumber() + "\tID = " + p.getID() + "\tPrio 1 = " + p.getPrio1() +
                "\tPrio 2 = " + p.getPrio2() + "\tPrio 3 = " + p.getPrio3() + "\tPrio 4 = " + p.getPrio4() + "\tPrio 5 = " + p.getPrio5() + "\tTotal = " + p.getTotal());
            y++;
        }


        return projectWithNumberOfPicks;
    }







}
