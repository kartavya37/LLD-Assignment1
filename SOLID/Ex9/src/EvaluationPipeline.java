public class EvaluationPipeline {
	private final Rubric rubric;
	private final PlagiarismChecker plagiarismChecker;
	private final CodeGrader grader;
	private final ReportWriter reportWriter;

	public EvaluationPipeline(Rubric rubric, PlagiarismChecker plagiarismChecker, CodeGrader grader,
			ReportWriter reportWriter) {
		this.rubric = rubric;
		this.plagiarismChecker = plagiarismChecker;
		this.grader = grader;
		this.reportWriter = reportWriter;
	}

	public void evaluate(Submission sub) {
		int plag = plagiarismChecker.check(sub);
		System.out.println("PlagiarismScore=" + plag);

		int code = grader.grade(sub, rubric);
		System.out.println("CodeScore=" + code);

		String reportName = reportWriter.write(sub, plag, code);
		System.out.println("Report written: " + reportName);

		int total = plag + code;
		String result = (total >= 90) ? "PASS" : "FAIL";
		System.out.println("FINAL: " + result + " (total=" + total + ")");
	}
}
