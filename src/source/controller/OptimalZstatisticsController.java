package source.controller;

import source.Assignment;
import source.model.OptimalZmodel;

import java.util.*;



/**
 * Created by Tobias on 05.05.2017.
 */

public class OptimalZstatisticsController {

    OptimalZmodel model = new OptimalZmodel();

    public  OptimalZstatisticsController(OptimalZmodel model) {
        this.model = model;
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


    public int mostAllocatedPriority() {
        int one = 0;
        int two = 0;
        int three = 0;
        int four = 0;
        int five = 0;
        int i = 0;
        //Integer[] arr = {one, two, three, four, five};
        List<Integer> list = Arrays.asList(one, two, three, four, five);

        int actualVersion = model.getActualVersion();
        ArrayList<Assignment> arrAssignments = model.getListVersions().get(actualVersion - 1); //arrAssignments now is the list with the current assignments

        while (i <= arrAssignments.size() - 1) {
            Assignment a = arrAssignments.get(i);
            int cost = a.getCost().intValue();

            switch (cost) {
                case 0:
                    one++;
                    break;
                case 1:
                    two++;
                    break;
                case 2:
                    three++;
                    break;
                case 3:
                    four++;
                    break;
                case 4:
                    five++;
                    break;
            }

        }

        Collections.sort(list);
        Collections.reverse(list);
        return list.get(0);

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
                s = a.getChosenProjects().get(z); //hier is das Problem

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

    /*TODO write method that counts the number of own or Eigene projects*/
    public int numberOfOwnProjects() {
        return 0;
    }






}
