
public class SimpleRequestParsingStrategy extends RequestParsingStrategy {
	@Override
	public RequestParsingStrategy.Result parse(String request) {
		String[] parts = request.split(" ");

		String method;
		String key;
		String value = null;

		method = parts[0];
		key = parts[1];

		if (parts.length >= 3) {
			value = "";
			for (int i = 2; i < parts.length; i++) {
				value += (parts[i] + " ");
			}
		}

		return new Result(method, key, value);
	}
}
