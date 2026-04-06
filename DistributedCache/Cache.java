import java.util.HashMap;
import java.util.Map;

public class Cache {
	private int capacity;
	private int size;
	private CacheEvictionPolicy evictionPolicy;
	private Map<String, String> map;

	public Cache(int capacity, CacheEvictionPolicy evictionPolicy) {
		this.capacity = capacity;
		this.size = 0;
		this.evictionPolicy = evictionPolicy;
		this.map = new HashMap<>();
		this.evictionPolicy.setStore(this.map);
	}

	private void notifyGet(String key) {
		this.evictionPolicy.notifyGet(key);
	}

	private void notifyPut(String key, String value) {
		this.evictionPolicy.notifyPut(key, value);
	}

	private void notifyDelete(String key) {
		this.evictionPolicy.notifyDelete(key);
	}

	public String get(String key) {
		notifyGet(key);

		return this.map.get(key);
	}

	public void put(String key, String value) {
		notifyPut(key, value);

		this.map.put(key, value);
		this.size++;
	}

	public void delete(String key) {
		notifyDelete(key);

		this.map.remove(key);
		this.size--;
	}
}
