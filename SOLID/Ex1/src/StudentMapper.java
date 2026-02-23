import java.util.Map;
public class StudentMapper {
    
    private final StudentRepo db;

    StudentMapper(StudentRepo db) {
        this.db =db;
    }
    public StudentRecord record(Map<String, String> kv) {
        String id = IdUtil.nextStudentId(db.count());

        String name = kv.getOrDefault("name", "");
        String email = kv.getOrDefault("email", "");
        String phone = kv.getOrDefault("phone", "");
        String program = kv.getOrDefault("program", "");

        StudentRecord rec = new StudentRecord(id, name, email, phone, program);

        return rec;
    }
}
