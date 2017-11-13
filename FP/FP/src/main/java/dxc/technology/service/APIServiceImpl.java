package dxc.technology.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import dxc.technology.entity.Project;
import dxc.technology.model.DailyData;

@Service
@Configuration
@PropertySource("classpath:api.properties")
public class APIServiceImpl implements APIService {
	@Bean
	public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}

	@Autowired
	ProjectService projectService;

	@Autowired
	ModuleService moduleService;

	@Autowired
	DailyReportService dailyReportService;

	// injecting properties
	@Autowired
	private Environment env;

	@Override
	public List<String> getKeyAPI(String head, String foot) throws IOException, ParseException, JSONException {

		// initialier
		List<String> list = new ArrayList<String>();

		// get keys from API
		for (int i = 1;; i++) {
			HttpGet httpGetKeyAndId = new HttpGet(head + i + foot);
			try (CloseableHttpClient httpClient = HttpClients.createDefault();
					CloseableHttpResponse response = httpClient.execute(httpGetKeyAndId);) {
				HttpEntity entity = response.getEntity();

				JSONObject decoded = new JSONObject(EntityUtils.toString(entity));

				JSONArray arrComponents = (JSONArray) decoded.get("components");

				if (arrComponents.length() != 0) {
					for (int j = 0; j < arrComponents.length(); j++) {
						JSONObject listComponent = (JSONObject) arrComponents.get(j);
						String Key = listComponent.get("key").toString();
						list.add(Key);
					}
				} else {
					break;
				}
			}
		}
		return list;
	}

	@Override
	public JSONArray getValueAPI(String key) throws IOException, ParseException, JSONException {

		// initialier
		JSONArray arrMeasures = null;

		HttpGet httpGetValues = new HttpGet(env.getProperty("api.keyhead") + key + env.getProperty("api.keyfoot"));
		try (CloseableHttpClient httpClient = HttpClients.createDefault();
				CloseableHttpResponse response = httpClient.execute(httpGetValues);) {
			HttpEntity entity = response.getEntity();
			JSONObject jsonObject = new JSONObject(EntityUtils.toString(entity));

			JSONObject jsonComponent = (JSONObject) jsonObject.get("component");
			arrMeasures = (JSONArray) jsonComponent.get("measures");
		}

		return arrMeasures;
	}

	@Override
	public ArrayList<DailyData> getDailyData(Date date) {
		ArrayList<DailyData> listDailyData = new ArrayList<DailyData>();
		ArrayList<Project> listProject = getProjectService().getAll();
		for (int i = 0; i < listProject.size(); i++) {
			DailyData dailyData = getDailyReportService().getDailyData(listProject.get(i).getId(), date);
			dailyData.setId(listProject.get(i).getId());
			dailyData.setName(listProject.get(i).getName());
			listDailyData.add(dailyData);
		}
		return listDailyData;
	}

	public ProjectService getProjectService() {
		return projectService;
	}

	public void setProjectService(ProjectService projectService) {
		this.projectService = projectService;
	}

	public ModuleService getModuleService() {
		return moduleService;
	}

	public void setModuleService(ModuleService moduleService) {
		this.moduleService = moduleService;
	}

	public DailyReportService getDailyReportService() {
		return dailyReportService;
	}

	public void setDailyReportService(DailyReportService dailyReportService) {
		this.dailyReportService = dailyReportService;
	}

}
