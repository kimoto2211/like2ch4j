package like2ch4j.main;

public abstract class BoardConnection extends Like2chConnection {
	String board;
	String hanamogera;
	String charCode = "SJIS";
	String cookie;
	String subject;
	String name;
	String mail;
	String mess;


	public BoardConnection board(String board) {
		this.board = board;
		return this;
	}

	public BoardConnection hanamogera(String hanamogera) {
		this.hanamogera = hanamogera;
		return this;
	}

	public BoardConnection charCode(String charCode) {
		this.charCode = charCode;
		return this;
	}

	public BoardConnection cookie(String cookie) {
		this.cookie = cookie;
		return this;
	}

	public BoardConnection subject(String subject) {
		this.subject = subject;
		return this;
	}

	public BoardConnection name(String name) {
		this.name = name;
		return this;
	}

	public BoardConnection mail(String mail) {
		this.mail = mail;
		return this;
	}

	public BoardConnection mess(String mess) {
		this.mess = mess;
		return this;
	}
}
