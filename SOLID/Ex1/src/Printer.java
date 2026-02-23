public class Printer {
    
    public final StudentRepo db;
    Printer(StudentRepo db) {
        this.db =db;
    }

    public void print(StudentRecord rec) {
        System.out.println("OK: created student " + rec.id);
        System.out.println("Saved. Total students: " + db.count());
        System.out.println("CONFIRMATION:");
        System.out.println(rec);
    }
}

