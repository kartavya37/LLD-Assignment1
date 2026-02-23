public class DisciplinaryRule implements EligibilityRule {
    @Override
    public boolean isEligible(StudentProfile s) {
        return s.disciplinaryFlag == LegacyFlags.NONE;
    }

    @Override
    public String getFailureReason() {
        return "disciplinary flag present";
    }
}