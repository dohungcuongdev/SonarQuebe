package dxc.technology.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.ParseException;
import org.json.JSONException;

import dxc.technology.entity.Project;

public interface ProjectService {

	public void getProject(List<String> listKey) throws ParseException, IOException, JSONException;

	public ArrayList<Project> getAll();
}
