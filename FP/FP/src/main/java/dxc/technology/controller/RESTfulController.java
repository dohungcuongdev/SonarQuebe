package dxc.technology.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.ParseException;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import dxc.technology.model.DailyData;
import dxc.technology.model.ShortData;
import dxc.technology.service.APIService;
import dxc.technology.service.DailyReportService;
import dxc.technology.service.ModuleService;
import dxc.technology.service.ProjectService;

@RestController
@RequestMapping("/api")
public class RESTfulController {

	@Autowired
	ProjectService projectService;

	@Autowired
	ModuleService moduleService;

	@Autowired
	DailyReportService dailyReportService;

	@Autowired
	APIService apiService;

	@Autowired
	private Environment env;

	@CrossOrigin
	@RequestMapping(value = "/allprojects", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public ArrayList<DailyData> showAllProjects() {
		Date date = new Date();
		return apiService.getDailyData(date);
	}

	@CrossOrigin
	@RequestMapping(value = "/testSonarQube", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public void testSonarQube() {
		String apiProjectHead = env.getProperty("api.projecthead");
		String apiProjectFoot = env.getProperty("api.projectfoot");
		List<String> listKeyAndId;
		try {
			listKeyAndId = apiService.getKeyAPI(apiProjectHead, apiProjectFoot);
			projectService.getProject(listKeyAndId);
			moduleService.getModule(listKeyAndId);
			dailyReportService.saveOrUpdate(new Date());
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	@CrossOrigin
	@RequestMapping(value = "/updateSonarQube", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	public void updateSonarQube() {
		String apiProjectHead = env.getProperty("api.projecthead");
		String apiProjectFoot = env.getProperty("api.projectfoot");
		List<String> listKeyAndId;
		try {
			listKeyAndId = apiService.getKeyAPI(apiProjectHead, apiProjectFoot);
			projectService.getProject(listKeyAndId);
			moduleService.getModule(listKeyAndId);
			dailyReportService.saveOrUpdate(new Date());
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	@CrossOrigin
	@RequestMapping(value = "/{columnName}/{projectId}", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public ArrayList<ShortData> showAllTectDebtinMonth(@PathVariable("projectId") int projectId,
			@PathVariable("columnName") String columnName) {
		return dailyReportService.getMonthlyData(projectId, columnName);
	}

}
