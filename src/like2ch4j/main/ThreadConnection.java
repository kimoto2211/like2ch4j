package like2ch4j.main;

public abstract class ThreadConnection extends BoardConnection {
	String threadId;

	public ThreadConnection threadId(String threadId) {
		this.threadId = threadId;
		return this;
	}
}
