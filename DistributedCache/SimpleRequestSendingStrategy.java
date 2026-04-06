import java.util.Optional;
import java.util.Map;

public class SimpleRequestSendingStrategy extends RequestSenderStrategy {
	@Override
	public String get(String key, String nodeUrl) {
		String request = "GET " + key;
		NodeService nodeService = NodeServiceRepository.nodes.get(nodeUrl);
		String res = nodeService.processRequest(request);
		return res;
	}

	@Override
	public void put(String key, String value, String nodeUrl) {
		String request = "PUT " + key + " " + value;
		NodeService nodeService = NodeServiceRepository.nodes.get(nodeUrl);

		String res = nodeService.processRequest(request);
		System.out.println("RequestSender PUT response: " + res);
	}

	@Override
	public void delete(String key, String nodeUrl) {
		String request = "DELETE " + key;
		NodeService nodeService = NodeServiceRepository.nodes.get(nodeUrl);

		String res = nodeService.processRequest(request);
		System.out.println("RequestSender DELETE response: " + res);
	}
}
