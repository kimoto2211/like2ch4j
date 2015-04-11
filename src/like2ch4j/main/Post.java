package like2ch4j.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Date;

import org.junit.Test;

public class Post {

	private String protocol;
	private String host;
	private String board;
	private String threadId;
	private int port;
	private String name;
	private String mail;
	private String mess;
	private String dir;

	@SuppressWarnings("unused")
	@Deprecated
	private Post() {
	}

	public Post(String protocol, String host, String dir, String board,
			String threadId, int port, String name, String mail, String mess) {
		super();
		this.protocol = protocol;
		this.host = host;
		this.dir = dir;
		this.board = board;
		this.threadId = threadId;
		this.port = port;
		this.name = name;
		this.mail = mail;
		this.mess = mess;
	}

	public String post() throws Exception {
		URL url = new URL(this.protocol, this.host, this.dir);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setDoOutput(true);
		conn.setUseCaches(false);
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Referer", protocol + "://" + host + "/"
				+ board + "/" + threadId);
		String paramStr = "DIR=" + board.split("/")[0] + "&BBS="
				+ board.split("/")[1] + "&KEY=" + threadId + "&time="
				+ System.currentTimeMillis() + "&submit="
				+ URLEncoder.encode("書き込む", "SJIS") + "%FROM"
				+ URLEncoder.encode(name, "SJIS") + "&mail="
				+ URLEncoder.encode(mail, "SJIS") + "&MESSAGE="
				+ URLEncoder.encode(mess, "SJIS");
		PrintWriter pw = new PrintWriter(conn.getOutputStream());
		pw.print(paramStr);
		pw.close();
		BufferedReader br = new BufferedReader(new InputStreamReader(
				conn.getInputStream(), "JISAutoDetect"));
		StringBuilder httpSource = new StringBuilder();
		String str;
		while (null != (str = br.readLine())) {
			httpSource.append(str);
		}
		br.close();
		conn.disconnect();

		System.out.println(board.split("/")[0] + " " + board.split("/")[1]);
		System.out.println(httpSource.toString());
		return httpSource.toString();
	}

	@Test
	public static void testpost() {
		try {
			new Post("http", "jbbs.shitaraba.net", "/bbs/write.cgi",
					"game/52760", "1373947939", 80, "", "sage", "test")
					.post();
		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

	}
}
