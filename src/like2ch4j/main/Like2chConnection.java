package like2ch4j.main;

public abstract class Like2chConnection extends Connection{
	String protocol="http";
	String host;
	int port = 80;

	public Like2chConnection proto(String protocol) {
		this.protocol = protocol;
		return this;
	}

	public Like2chConnection host(String host) {
		this.host = host;
		return this;
	}

	public Like2chConnection port(int port) {
		this.port = port;
		return this;
	}

}
