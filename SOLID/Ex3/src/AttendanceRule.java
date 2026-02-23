public class AttendanceRule implements EligibilityRule {
    private final int minAttendance = 75;

    @Override
    public boolean isEligible(StudentProfile s) {
        return s.attendancePct >= minAttendance;
    }

    @Override
    public String getFailureReason() {
        return "attendance below 75";
    }
}