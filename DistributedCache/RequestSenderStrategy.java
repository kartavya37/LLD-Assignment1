import java.util.Optional;

public abstract class RequestSenderStrategy {
	public abstract String get(String key, String nodeUrl);

	public abstract void put(String key, String value, String nodeUrl);

	public abstract void delete(String key, String nodeUrl);
}
