package dxc.technology.model;

public class DailyData {

	private int id;
	private String name;
	private double coverage;
	private int techDebt;
	private int vulnerability;
	private int codeSmell;
	private int testcase;

	public DailyData() {

	}

	public DailyData(int id, String name, double coverage, int techDebt, int vulnerability, int codeSmell,
			int testcase) {
		super();
		this.id = id;
		this.name = name;
		this.coverage = coverage;
		this.techDebt = techDebt;
		this.vulnerability = vulnerability;
		this.codeSmell = codeSmell;
		this.testcase = testcase;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	@Override
	public String toString() {
		return "DailyData [id=" + id + ", name=" + name + ", coverage=" + coverage + ", techDebt=" + techDebt
				+ ", vulnerability=" + vulnerability + ", codeSmell=" + codeSmell + ", testcase=" + testcase + "]";
	}

}
