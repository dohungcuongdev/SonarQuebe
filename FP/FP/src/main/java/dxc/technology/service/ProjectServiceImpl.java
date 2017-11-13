package dxc.technology.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.http.ParseException;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dxc.technology.dao.ProjectDAO;
import dxc.technology.entity.Project;

@Service
public class ProjectServiceImpl implements ProjectService {

	@Autowired
	ProjectDAO projectDAO;

	@Autowired
	APIService apiService;

	@Override
	public void getProject(List<String> listKey) throws ParseException, IOException, JSONException {

		// get project values
		Set<String> setKeyName = new HashSet<String>();

		for (int i = 0; i < listKey.size(); i++) {
			String[] strList = listKey.get(i).split(":");
			if (strList.length == 3) {
				strList[2] = strList[2].replace("-", " ");
				setKeyName.add(strList[2]);
			}
		}

		ArrayList<Project> listProject = getProjectDAO().getAll();

		int check = 0;

		for (String tempName : setKeyName) {
			for (int i = 0; i < listProject.size(); i++) {
				if (tempName.equals(listProject.get(i).getName())) {
					check = 1;
				}
			}
			if (check == 0) {
				getProjectDAO().insert(new Project(tempName));
			}
			check = 0;
		}
	}

	@Override
	public ArrayList<Project> getAll() {
		return getProjectDAO().getAll();
	}

	public ProjectDAO getProjectDAO() {
		return projectDAO;
	}

	public void setProjectDAO(ProjectDAO projectDAO) {
		this.projectDAO = projectDAO;
	}

	public APIService getApiService() {
		return apiService;
	}

	public void setApiService(APIService apiService) {
		this.apiService = apiService;
	}

}
