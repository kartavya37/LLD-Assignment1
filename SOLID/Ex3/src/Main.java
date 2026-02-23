import java.util.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Placement Eligibility ===");
        
        
        StudentProfile s = new StudentProfile("23BCS1001", "Ayaan", 8.10, 72, 18, LegacyFlags.NONE);
        
        
        List<EligibilityRule> placementRules = List.of(
            new DisciplinaryRule(),
            new CgrRule(),
            new AttendanceRule(),
            new CreditsRule()
        );

        EligibilityEngine engine = new EligibilityEngine(new FakeEligibilityStore(), placementRules);
        engine.runAndPrint(s);
    }
}