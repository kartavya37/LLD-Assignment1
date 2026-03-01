public class Main {
	public static void main(String[] args) {
		System.out.println("=== Evaluation Pipeline ===");
		Submission sub = new Submission("23BCS1007", "public class A{}", "A.java");
		Rubric rubric = new Rubric();
		PlagiarismChecker plagiarismChecker = new PlagiarismChecker();
		CodeGrader codeGrader = new CodeGrader();
		ReportWriter reportWriter = new ReportWriter();

		EvaluationPipeline evaluationPipeline = new EvaluationPipeline(rubric, plagiarismChecker, codeGrader, reportWriter);

		evaluationPipeline.evaluate(sub);
	}
}
