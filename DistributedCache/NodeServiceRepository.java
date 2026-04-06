
// A mock class, contains mappings between node urls and actual NodeServer objects
// In a real system, this shouldn't exist and our DistributedCache's job must just be to send a TCP/UDP request to the node server's URL and then get the response.
// But this is just for mocking/demonstration purposes.

import java.util.HashMap;
import java.util.Map;

public class NodeServiceRepository {
	public static Map<String, NodeService> nodes = new HashMap<>();
}
