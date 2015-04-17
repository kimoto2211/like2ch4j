package like2ch4j.main;

public abstract class ThreadConnection extends BoardConnection {
	String threadId;

	public ThreadConnection threadId(String threadId) {
		this.threadId = threadId;
		return this;
	}
	
	public ThreadConnection board(String board) {
		this.board = board;
		return this;
	}

	public ThreadConnection hanamogera(String hanamogera) {
		this.hanamogera = hanamogera;
		return this;
	}

	public ThreadConnection charCode(String charCode) {
		this.charCode = charCode;
		return this;
	}

	public ThreadConnection cookie(String cookie) {
		this.cookie = cookie;
		return this;
	}

	public ThreadConnection subject(String subject) {
		this.subject = subject;
		return this;
	}

	public ThreadConnection name(String name) {
		this.name = name;
		return this;
	}

	public ThreadConnection mail(String mail) {
		this.mail = mail;
		return this;
	}

	public ThreadConnection mess(String mess) {
		this.mess = mess;
		return this;
	}
	
	public ThreadConnection proto(String protocol) {
		this.protocol = protocol;
		return this;
	}

	public ThreadConnection host(String host) {
		this.host = host;
		return this;
	}

	public ThreadConnection port(int port) {
		this.port = port;
		return this;
	}

	public ThreadConnection dir(String dir) {
		this.dir = dir;
		return this;
	}
}
