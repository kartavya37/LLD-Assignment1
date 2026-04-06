import java.util.ArrayList;
import java.util.List;

public class Main {
	public static void main(String[] args) {
		DistributedCacheFactory cacheFactory = new DistributedCacheFactory();

		// list of node URLs we have
		List<String> nodesList = new ArrayList<>();
		nodesList.add("127.0.0.1:9170");
		nodesList.add("127.0.0.1:9171");
		nodesList.add("127.0.0.1:9172");
		nodesList.add("127.0.0.1:9173");

		// Add them into our "mock" NodeServiceRepository as well
		for (String node : nodesList) {
			NodeService nodeService = new NodeService(new LRUCacheEvictionPolicy(4), 10, new SimpleRequestParsingStrategy());
			NodeServiceRepository.nodes.put(node, nodeService);
		}

		NodeSelectionStrategy selectionStrategy = new SimpleModuloSelectionStrategy();
		RequestSenderStrategy senderStrategy = new SimpleRequestSendingStrategy();

		DistributedCache cache = cacheFactory.create(nodesList, selectionStrategy, senderStrategy);

		cache.put("name", "Kartavya");
		cache.put("age", "20");
		cache.put("fav_subject", "LLD");
		cache.put("height", "5'10");

		String nameRes = cache.get("name");
		String ageRes = cache.get("age");

		System.out.println("Name: " + nameRes);
		System.out.println("Age: " + ageRes);

		cache.delete("age");

		ageRes = cache.get("age");

		assert (ageRes.isEmpty());

		// Test eviction with a small cache (capacity 3)
		System.out.println("\n--- Eviction Test ---");
		Cache localCache = new Cache(3, new LRUCacheEvictionPolicy(3));

		localCache.put("a", "1");
		localCache.put("b", "2");
		localCache.put("c", "3");
		System.out.println("a=" + localCache.get("a")); // expected: 1
		System.out.println("b=" + localCache.get("b")); // expected: 2
		System.out.println("c=" + localCache.get("c")); // expected: 3

		// Cache is full [c, b, a]. Inserting "d" should evict "a" (LRU).
		// But first, access "a" to make it most recently used -> [a, c, b]
		localCache.get("a");
		// Now insert "d" -> evicts "b" (LRU tail) -> [d, a, c]
		localCache.put("d", "4");

		System.out.println("a=" + localCache.get("a")); // expected: 1 (was accessed, not evicted)
		System.out.println("b=" + localCache.get("b")); // expected: null (evicted)
		System.out.println("c=" + localCache.get("c")); // expected: 3
		System.out.println("d=" + localCache.get("d")); // expected: 4

		// Test updating existing key doesn't cause eviction
		localCache.put("a", "100");
		System.out.println("a=" + localCache.get("a")); // expected: 100 (updated)
		System.out.println("c=" + localCache.get("c")); // expected: 3 (not evicted)
		System.out.println("d=" + localCache.get("d")); // expected: 4 (not evicted)
	}
}
