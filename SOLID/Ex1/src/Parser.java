import java.util.LinkedHashMap;
import java.util.Map;

public class Parser {
    
    public static final String KV_SEPARATOR = "=";
    public static final String ITEM_SEPARATOR = ";";

     /*
     * why this static 
     * - They belong to the class, not to individual objects.
     * - The parse() method is static and can directly access only static members.
     */
    /*
     * The parse method is static because:
     * - It does not depend on any instance variables.
     * - No object creation is required to perform parsing.
     */

    public static Map<String , String> parse(String raw) {

        Map<String,String> kv = new LinkedHashMap<>();
        String[] parts = raw.split(ITEM_SEPARATOR);
        for (String p : parts) {
            String[] t = p.split(KV_SEPARATOR, 2);
            if (t.length == 2) kv.put(t[0].trim(), t[1].trim());
        }
        
        return kv;

    }
}
