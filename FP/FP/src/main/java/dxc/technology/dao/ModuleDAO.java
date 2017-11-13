package dxc.technology.dao;

import java.util.ArrayList;

import dxc.technology.entity.Module;

public interface ModuleDAO {

	public void insert(Module module);
	
	public ArrayList<String> getListKey();

	public ArrayList<Integer> getIdModule();

	public ArrayList<Integer> getIdModule(String key);
	
	public int findKey(String key);
}
