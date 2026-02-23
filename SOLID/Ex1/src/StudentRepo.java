import java.util.List;

public interface StudentRepo {
    
    public void save(StudentRecord r);
    public int count();
    public List<StudentRecord> all() ;

}
