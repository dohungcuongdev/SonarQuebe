package dxc.technology.dao;

import java.util.ArrayList;
import java.util.Date;

import dxc.technology.entity.DailyReport;
import dxc.technology.model.DailyData;
import dxc.technology.model.ShortData;

public interface DailyReportDAO {

	public void insert(DailyReport dailyReport);

	public void update(DailyReport dailyReport);

	public ArrayList<DailyReport> getAll();

	public ArrayList<Date> getDateReport(Date key);

	public DailyData getDailyData(int idModule, Date date);

	public Date getLastestDate();

	public ArrayList<ShortData> getMonthlyData(int projectId, Date date, String nameColumn);
}
