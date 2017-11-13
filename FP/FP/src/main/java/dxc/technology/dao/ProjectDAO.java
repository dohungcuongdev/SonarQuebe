package dxc.technology.dao;

import java.util.ArrayList;

import dxc.technology.entity.Project;

public interface ProjectDAO {

	public void insert(Project project);
	
	public void update(Project project);

	public ArrayList<Project> getAll();
	
	public ArrayList<Integer> getIdProject();
}
