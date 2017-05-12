package source;

import javafx.beans.property.SimpleBooleanProperty;

import java.util.ArrayList;
import java.util.HashMap;

public class Assignment {

    private static int counter = 1;
	private final int id;
	private String name;
	private String assignedProject;

    private String projectPrio1;
	private String projectPrio2;
	private String projectPrio3;
	private String projectPrio4;
	private String projectPrio5;

	private HashMap<Integer, String> chosenProjects = new HashMap<>(); //Integer entspricht Projektwahl -> int 1 = 1. Prio, int 2 = 2. Prio etc.
    private Double cost;

    private SimpleBooleanProperty isLocked = new SimpleBooleanProperty(false);

    public Boolean getLockedBoolean() {

        if (isLockedBoolean != null){

        return isLockedBoolean;}

        else {return null;}
    }

    public void setLockedBoolean(Boolean lockedBoolean) {
        isLockedBoolean = lockedBoolean;
    }

    private Boolean isLockedBoolean = false;

	public Assignment(){
        this.id = counter++;
        this.isLocked = new SimpleBooleanProperty(false);
    }
	
	public Assignment(String name, String assignedProject){
		this.id = counter++;
		this.name = name;
		this.assignedProject = assignedProject;
		this.chosenProjects = chosenProjects;
    }

    public String getPriority() {
        if (assignedProject.equals(projectPrio1)){
             return projectPrio1;
        } else if (assignedProject.equals(projectPrio2)){
            return projectPrio2;
        } else if (assignedProject.equals(projectPrio3)){
            return projectPrio3;
        } else if (assignedProject.equals(projectPrio4)){
            return projectPrio4;
        } else if (assignedProject.equals(projectPrio5)) {
            return projectPrio5;
        }
        return null;
    }

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getAssignedProject() {
		return assignedProject;
	}

	public HashMap<Integer, String> getChosenProjects() {
		return chosenProjects;
	}

    public void setName(String name) {
        this.name = name;
    }

    public void setAssignedProject(String assignedProject) {
        this.assignedProject = assignedProject;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public void setChosenProjects(HashMap<Integer, String> chosenProjects) {
        this.chosenProjects = chosenProjects;
    }



    public String getProjectPrio1() {
        return projectPrio1;
    }

    public void setProjectPrio1(String projectPrio1) {
        this.projectPrio1 = projectPrio1;
    }

    public String getProjectPrio2() {
        return projectPrio2;
    }

    public void setProjectPrio2(String projectPrio2) {
        this.projectPrio2 = projectPrio2;
    }

    public String getProjectPrio3() {
        return projectPrio3;
    }

    public void setProjectPrio3(String projectPrio3) {
        this.projectPrio3 = projectPrio3;
    }

    public String getProjectPrio4() {
        return projectPrio4;
    }

    public void setProjectPrio4(String projectPrio4) {
        this.projectPrio4 = projectPrio4;
    }

    public String getProjectPrio5() {
        return projectPrio5;
    }

    public void setProjectPrio5(String projectPrio5) {
        this.projectPrio5 = projectPrio5;
    }


}