
public class SimpleModuloSelectionStrategy extends NodeSelectionStrategy {
	@Override
	public String chooseNode(String key) {
		NodeRegistry nodeRegistry = NodeRegistry.getInstance();

		int asciiSum = 0;
		for (int i = 0; i < key.length(); i++) {
			asciiSum += ((int) key.charAt(i));
		}

		int nodeId = asciiSum % nodeRegistry.getSize();
		String nodeUrl = nodeRegistry.getNodeUrlFromId(nodeId);

		return nodeUrl;
	}
}
