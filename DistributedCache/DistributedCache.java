
public class DistributedCache {
	private NodeSelectionStrategy selectionStrategy;
	private RequestSenderStrategy senderStrategy;

	public DistributedCache(NodeSelectionStrategy selectionStrategy, RequestSenderStrategy senderStrategy) {
		this.selectionStrategy = selectionStrategy;
		this.senderStrategy = senderStrategy;
	}

	public String get(String key) {
		String nodeUrl = selectionStrategy.chooseNode(key);
		return this.senderStrategy.get(key, nodeUrl);
	}

	public void put(String key, String value) {
		String nodeUrl = selectionStrategy.chooseNode(key);
		this.senderStrategy.put(key, value, nodeUrl);
	}

	public void delete(String key) {
		String nodeUrl = selectionStrategy.chooseNode(key);
		this.senderStrategy.delete(key, nodeUrl);
	}
}
