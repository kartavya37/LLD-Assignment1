import java.util.*;

public class OnboardingService {
    private final StudentRepo db;
    private final StudentMapper mapper;
    private final Printer printer;

    public OnboardingService(StudentRepo db ,StudentMapper mapper , Printer printer ) { 
        this.db = db; 
        this.mapper = mapper;
        this.printer = printer;
    }

    // Intentionally violates SRP: parses + validates + creates ID + saves + prints.
    public void registerFromRawInput(String raw) {
        System.out.println("INPUT: " + raw);

        Map<String, String> kv = Parser.parse(raw);
        List<String> errors = Validater.validate(kv);
        
        if (!errors.isEmpty()) {
            System.out.println("ERROR: cannot register");
            for (String e : errors) System.out.println("- " + e);
            return;
        }

        StudentRecord rec = mapper.record(kv);
        db.save(rec);
        printer.print(rec);

    }
}
