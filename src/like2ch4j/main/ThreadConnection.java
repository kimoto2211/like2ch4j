package like2ch4j.main;

public abstract class ThreadConnection extends BoardConnection {
	String threadId;

	public Connection threadId(String threadId) {
		this.threadId = threadId;
		return this;
	}
}
