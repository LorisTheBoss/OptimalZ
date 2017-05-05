package source;

import java.util.ArrayList;
import java.util.HashMap;

public class Assignment {

    private static int counter = 1;
	private final int id;
	private String name;
	private String assignedProject;
	private HashMap<Integer, String> chosenProjects = new HashMap<>(); //Integer entspricht Projektwahl -> int 1 = 1. Prio, int 2 = 2. Prio etc.

    private Double cost;

	public Assignment(){
        this.id = counter++;
    }
	
	public Assignment(String name, String assignedProject){
		this.id = counter++;
		this.name = name;
		this.assignedProject = assignedProject;

		this.chosenProjects = chosenProjects;
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

}