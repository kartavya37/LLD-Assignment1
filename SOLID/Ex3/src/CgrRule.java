public class CgrRule implements EligibilityRule {
    private final double threshold = 8.0;

    @Override
    public boolean isEligible(StudentProfile s) {
        return s.cgr >= threshold;
    }

    @Override
    public String getFailureReason() {
        return "CGR below 8.0";
    }
}