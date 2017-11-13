package dxc.technology.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import dxc.technology.entity.DailyReport;
import dxc.technology.entity.Module;
import dxc.technology.entity.Project;
import dxc.technology.model.DailyData;
import dxc.technology.model.ShortData;

@Repository
@Transactional
public class DailyReportDAOImpl implements DailyReportDAO {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void insert(DailyReport dailyReport) {
		getSessionFactory().getCurrentSession().saveOrUpdate(dailyReport);
	}

	@Override
	public void update(DailyReport dailyReport) {
		Query q = getSessionFactory().getCurrentSession().createQuery("update " + DailyReport.class.getName()
				+ " set codeSmell = :code_smell , coverage = :coverage , techDebt = :technical_debt , testcase = :test_case , vulnerability = :vulnerability where moduleId = :id_module and date = :date_report");
		q.setParameter("code_smell", dailyReport.getCodeSmell());
		q.setParameter("coverage", dailyReport.getCoverage());
		q.setParameter("technical_debt", dailyReport.getTechDebt());
		q.setParameter("test_case", dailyReport.getTestcase());
		q.setParameter("vulnerability", dailyReport.getVulnerability());
		q.setParameter("id_module", dailyReport.getModuleId());
		q.setParameter("date_report", dailyReport.getDate());
		q.executeUpdate();
	}

	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<DailyReport> getAll() {
		return (ArrayList<DailyReport>) getSessionFactory().getCurrentSession().createQuery("from daily_report").list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<Date> getDateReport(Date date) {
		Query q = getSessionFactory().getCurrentSession()
				.createQuery("select date from " + DailyReport.class.getName() + " where date = :date_report");
		q.setParameter("date_report", date);
		ArrayList<Date> arrList = (ArrayList<Date>) q.list();
		return arrList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public DailyData getDailyData(int idProject, Date date) {
		DailyData dailyData = new DailyData();
		Query q = getSessionFactory().getCurrentSession().createQuery(
				"select avg(d.coverage), sum(d.codeSmell), sum(d.techDebt), sum(d.testcase), sum(d.vulnerability) from "
						+ DailyReport.class.getName() + " d, " + Project.class.getName() + " p, "
						+ Module.class.getName() + " m "
						+ "where p.id = :id and d.date = :date_report and d.moduleId = m.id and p.id = m.projectId");
		q.setParameter("id", idProject);
		q.setParameter("date_report", date);

		List<Object[]> datas = q.list();

		for (Object[] d : datas) {
			dailyData.setCoverage(Double.valueOf(d[0].toString()));
			dailyData.setCodeSmell(Integer.parseInt(d[1].toString()));
			dailyData.setTechDebt(Integer.parseInt(d[2].toString()));
			dailyData.setTestcase(Integer.parseInt(d[3].toString()));
			dailyData.setVulnerability(Integer.parseInt(d[4].toString()));
		}
		return dailyData;
	}

	@Override
	public Date getLastestDate() {

		Query q = getSessionFactory().getCurrentSession()
				.createQuery("select date from " + DailyReport.class.getName() + " order by date desc")
				.setMaxResults(1);

		return (Date) q.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<ShortData> getMonthlyData(int projectId, Date lastestDate, String nameColumn) {

		Calendar cal = Calendar.getInstance();
		cal.setTime(lastestDate);
		cal.add(Calendar.DATE, -28);
		Date dateBefore28Days = cal.getTime();

		Query q = getSessionFactory().getCurrentSession().createSQLQuery("select sum(" + nameColumn
				+ "), date_report from daily_report d, module m, project p where date_report between :date2 and :date1 and d.id_module = m.id and m.project_id = p.id and p.id = :id group by d.date_report");
		q.setParameter("date1", new SimpleDateFormat("yyyy-MM-dd").format(lastestDate));
		q.setParameter("date2", new SimpleDateFormat("yyyy-MM-dd").format(dateBefore28Days));
		q.setParameter("id", projectId);

		List<Object[]> datas = q.list();
		ArrayList<ShortData> listShortData = new ArrayList<>();

		for (Object[] d : datas) {
			ShortData shortData = new ShortData();
			shortData.setValue(String.valueOf(d[0]));
			shortData.setDate(new SimpleDateFormat("yyyy-MM-dd").format(d[1]));
			listShortData.add(shortData);
		}

		return listShortData;
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

}
