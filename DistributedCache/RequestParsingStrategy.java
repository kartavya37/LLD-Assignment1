public abstract class RequestParsingStrategy {
	public class Result {
		public String type;
		public String key;
		public String value;

		public Result(String type, String key, String value) {
			this.type = type;
			this.key = key;
			this.value = value;
		}
	}

	public abstract Result parse(String request);
}
