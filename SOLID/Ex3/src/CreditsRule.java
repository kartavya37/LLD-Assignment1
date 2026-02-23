public class CreditsRule implements EligibilityRule {
    private final int minCredits = 20;

    @Override
    public boolean isEligible(StudentProfile s) {
        return s.earnedCredits >= minCredits;
    }

    @Override
    public String getFailureReason() {
        return "credits below 20";
    }
}