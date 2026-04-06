
public class LRUCacheEvictionPolicy extends CacheEvictionPolicy {

	private DoublyLinkedList list;

	public LRUCacheEvictionPolicy(int capacity) {
		super(capacity);
		list = new DoublyLinkedList();
	}

	@Override
	protected void evict() {
		String evictedKey = this.list.getTailValue();
		if (evictedKey != null) {
			this.store.remove(evictedKey);
			this.list.deleteAtTail();
		}
	}

	@Override
	public void notifyGet(String key) {
		this.list.moveToHead(key);
	}

	@Override
	public void notifyPut(String key, String value) {
		if (this.store.containsKey(key)) {
			this.list.moveToHead(key);
			return;
		}
		if (this.list.getSize() >= getCapacity()) {
			evict();
		}
		this.list.insertAtHead(key);
	}

	@Override
	public void notifyDelete(String key) {
		this.list.delete(key);
	}
}
