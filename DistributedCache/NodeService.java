
public class NodeService {
	private String url;
	private Cache cache;
	private RequestParsingStrategy parsingStrategy;

	public NodeService(CacheEvictionPolicy evictionPolicy, int cacheCapacity, RequestParsingStrategy parsingStrategy) {
		// create cache
		this.cache = new Cache(cacheCapacity, evictionPolicy);

		this.parsingStrategy = parsingStrategy;
	}

	String processRequest(String request) {
		RequestParsingStrategy.Result result = this.parsingStrategy.parse(request);

		if (result.type.equals("GET")) {
			return this.cache.get(result.key);
		} else if (result.type.equals("PUT")) {
			this.cache.put(result.key, result.value);
			return "OK";
		} else if (result.type.equals("DELETE")) {
			this.cache.delete(result.key);
			return "OK";
		} else {
			return "ERROR: UNSUPPORTED OPERATION";
		}
	}
}
