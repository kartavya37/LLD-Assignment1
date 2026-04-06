
import java.util.HashMap;
import java.util.Map;

// Singleton
public class NodeRegistry {
	private static volatile NodeRegistry INSTANCE;
	private Map<Integer, String> nodes;
	private int id;

	private NodeRegistry() {
		nodes = new HashMap<>();
		id = 0;
	}

	public static NodeRegistry getInstance() {
		if (INSTANCE == null) {
			synchronized (NodeRegistry.class) {
				if (INSTANCE == null) {
					INSTANCE = new NodeRegistry();
				}
			}
		}

		return INSTANCE;
	}

	public String getNodeUrlFromId(int id) {
		if (nodes.containsKey(id)) {
			return nodes.get(id);
		} else {
			return null;
		}
	}

	public void addNode(String url) {
		nodes.put(this.id++, url);
	}

	public int getSize() {
		return this.nodes.size();
	}

}
