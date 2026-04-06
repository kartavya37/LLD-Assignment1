import java.util.List;

public class DistributedCacheFactory {
	public DistributedCache create(List<String> nodesList, NodeSelectionStrategy selectionStrategy,
			RequestSenderStrategy senderStrategy) {

		// Populate node registry
		NodeRegistry nodeRegistry = NodeRegistry.getInstance();
		for (String nodeUrl : nodesList) {
			nodeRegistry.addNode(nodeUrl);
		}

		DistributedCache cache = new DistributedCache(selectionStrategy, senderStrategy);

		return cache;
	}
}
