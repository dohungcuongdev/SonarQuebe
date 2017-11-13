package dxc.technology.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import org.apache.http.ParseException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dxc.technology.dao.DailyReportDAO;
import dxc.technology.entity.DailyReport;
import dxc.technology.model.DailyData;
import dxc.technology.model.ShortData;

@Service
public class DailyReportServiceImpl implements DailyReportService {

	@Autowired
	DailyReportDAO dailyReportDAO;

	@Autowired
	ModuleService moduleService;

	@Autowired
	APIService apiService;

	@Override
	public void saveOrUpdate(Date date) throws ParseException, IOException, JSONException {

		int check = checkDate(date);
		ArrayList<String> listKey = getModuleService().getListKey();
		int size = listKey.size();

		if (check == 0) {
			for (int i = 0; i < size; i++) {
				DailyReport dailyReport = getValues(listKey.get(i));
				getDailyReportDAO().insert(dailyReport);
			}
		} else {
			for (int i = 0; i < size; i++) {
				DailyReport dailyReport = getValues(listKey.get(i));
				getDailyReportDAO().update(dailyReport);
			}
		}
	}

	@Override
	public DailyReport getValues(String key) throws ParseException, IOException, JSONException {

		DailyReport dailyReport = new DailyReport();
		JSONArray arrMeasures = getApiService().getValueAPI(key);

		// set module id
		dailyReport.setModuleId(getModuleService().findId(key));
		// set module date
		dailyReport.setDate(new Date());

		// set module values
		for (int j = 0; j < arrMeasures.length(); j++) {
			JSONObject temp = (JSONObject) arrMeasures.get(j);
			String metricValue = temp.get("metric").toString();
			if (metricValue.equals("coverage")) {
				dailyReport.setCoverage(Double.parseDouble(temp.get("value").toString()));
			}
			if (metricValue.equals("code_smells")) {
				dailyReport.setCodeSmell(Integer.parseInt(temp.get("value").toString()));
			}
			if (metricValue.equals("vulnerabilities")) {
				dailyReport.setVulnerability(Integer.parseInt(temp.get("value").toString()));
			}
			if (metricValue.equals("tests")) {
				dailyReport.setTestcase(Integer.parseInt(temp.get("value").toString()));
			}
			if (metricValue.equals("sqale_index")) {
				dailyReport.setTechDebt(Integer.parseInt(temp.get("value").toString()));
			}
		}
		return dailyReport;
	}

	@Override
	public ArrayList<DailyReport> getAll() {
		return getDailyReportDAO().getAll();
	}

	@Override
	public DailyData getDailyData(int idProject, Date date) {
		ArrayList<Date> testDate = getDailyReportDAO().getDateReport(date);
		if (testDate.size() == 0) {
			date = getDailyReportDAO().getLastestDate();
		}
		return getDailyReportDAO().getDailyData(idProject, date);
	}

	@Override
	public int checkDate(Date date) {
		ArrayList<Date> arrDate = getDailyReportDAO().getDateReport(new Date());
		return arrDate.size();
	}

	@Override
	public ArrayList<ShortData> getMonthlyData(int projectId, String nameColumn) {
		Date date = getDailyReportDAO().getLastestDate();
		return getDailyReportDAO().getMonthlyData(projectId, date, nameColumn);
	}

	public APIService getApiService() {
		return apiService;
	}

	public void setApiService(APIService apiService) {
		this.apiService = apiService;
	}

	public DailyReportDAO getDailyReportDAO() {
		return dailyReportDAO;
	}

	public void setDailyReportDAO(DailyReportDAO dailyReportDAO) {
		this.dailyReportDAO = dailyReportDAO;
	}

	public ModuleService getModuleService() {
		return moduleService;
	}

	public void setModuleService(ModuleService moduleService) {
		this.moduleService = moduleService;
	}
}
