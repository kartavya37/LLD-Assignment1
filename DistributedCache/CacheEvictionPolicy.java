import java.util.Map;

public abstract class CacheEvictionPolicy {
	private int capacity;
	protected Map<String, String> store;

	public CacheEvictionPolicy(int capacity) {
		this.capacity = capacity;
	}

	protected int getCapacity() {
		return capacity;
	}

	public void setStore(Map<String, String> store) {
		this.store = store;
	}

	protected abstract void evict();

	public abstract void notifyGet(String key);

	public abstract void notifyPut(String key, String value);

	public abstract void notifyDelete(String key);
}
