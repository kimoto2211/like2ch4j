package like2ch4j.main;

public abstract class BoardConnection extends Like2chConnection {
	String board;

	public Connection board(String board) {
		this.board = board;
		return this;
	}
}
