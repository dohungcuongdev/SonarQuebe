package dxc.technology.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.ParseException;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dxc.technology.dao.ModuleDAO;
import dxc.technology.entity.Module;
import dxc.technology.entity.Project;

@Service
public class ModuleServiceImpl implements ModuleService {

	@Autowired
	ModuleDAO moduleDAO;

	@Autowired
	APIService apiService;

	@Autowired
	ProjectService projectService;

	@Override
	public void getModule(List<String> listKey) throws ParseException, IOException, JSONException {

		// get name module
		ArrayList<Project> listProject = getProjectService().getAll();
		int size = listKey.size();

		for (int i = 0; i < size; i++) {
			String[] strList = listKey.get(i).split(":");
			if (strList.length == 3 && getModuleDAO().findKey(listKey.get(i)) == 1) {
				Module module = new Module();
				module.setKey(listKey.get(i)); // set key

				for (int j = 0; j < listProject.size(); j++) {
					String temp = listProject.get(j).getName().replace(" ", "-");
					if (module.getKey().contains(temp)) {
						module.setProjectId(listProject.get(j).getId()); // set id project
					}
				}

				strList[1] = strList[1].replace("-", " ");
				module.setName(strList[1]); // set name

				getModuleDAO().insert(module);
			}

		}
	}

	@Override
	public ArrayList<String> getListKey() {
		return getModuleDAO().getListKey();
	}

	@Override
	public int findId(String key) {
		return getModuleDAO().getIdModule(key).get(0);
	}

	@Override
	public ArrayList<Integer> getIdModule() {
		return getModuleDAO().getIdModule();
	}

	public ModuleDAO getModuleDAO() {
		return moduleDAO;
	}

	public void setModuleDAO(ModuleDAO moduleDAO) {
		this.moduleDAO = moduleDAO;
	}

	public APIService getApiService() {
		return apiService;
	}

	public void setApiService(APIService apiService) {
		this.apiService = apiService;
	}

	public ProjectService getProjectService() {
		return projectService;
	}

	public void setProjectService(ProjectService projectService) {
		this.projectService = projectService;
	}
}
