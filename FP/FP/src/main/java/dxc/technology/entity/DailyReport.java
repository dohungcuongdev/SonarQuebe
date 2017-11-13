package dxc.technology.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.Type;

@Entity(name = "daily_report")
public class DailyReport {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "date_report")
	@Type(type = "date")
	private Date date;

	@Column(name = "id_module")
	private int moduleId;

	@Column(name = "coverage")
	private double coverage;

	@Column(name = "technical_debt")
	private int techDebt;

	@Column(name = "vulnerability")
	private int vulnerability;

	@Column(name = "code_smell")
	private int codeSmell;

	@Column(name = "test_case")
	private int testcase;

	public DailyReport() {

	}

	public DailyReport(int id, Date date, int moduleid, double coverage, int techdebt, int vulnerability, int codesmell,
			int testcase) {
		this.id = id;
		this.date = date;
		this.moduleId = moduleid;
		this.coverage = coverage;
		this.techDebt = techdebt;
		this.vulnerability = vulnerability;
		this.codeSmell = codesmell;
		this.testcase = testcase;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getModuleId() {
		return moduleId;
	}

	public void setModuleId(int moduleId) {
		this.moduleId = moduleId;
	}

	public double getCoverage() {
		return coverage;
	}

	public void setCoverage(double coverage) {
		this.coverage = coverage;
	}

	public int getTechDebt() {
		return techDebt;
	}

	public void setTechDebt(int techDebt) {
		this.techDebt = techDebt;
	}

	public int getVulnerability() {
		return vulnerability;
	}

	public void setVulnerability(int vulnerability) {
		this.vulnerability = vulnerability;
	}

	public int getCodeSmell() {
		return codeSmell;
	}

	public void setCodeSmell(int codeSmell) {
		this.codeSmell = codeSmell;
	}

	public int getTestcase() {
		return testcase;
	}

	public void setTestcase(int testcase) {
		this.testcase = testcase;
	}

}
