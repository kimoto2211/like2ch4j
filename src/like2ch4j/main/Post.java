package like2ch4j.main;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class Post {

	private String protocol;
	private String host;
	private String board;
	private String threadId;
	@SuppressWarnings("unused")
	private int port;
	private String name;
	private String mail;
	private String mess;
	private String dir;
	private boolean newThread;
	private String cookie;
	private String hanamogera;
	@SuppressWarnings("unused")
	private boolean isRequiredHM;

	@SuppressWarnings("unused")
	@Deprecated
	private Post() {
	}

	public Post(String protocol, String host, String dir, String board,
			String threadId, int port, String name, String mail, String mess,
			boolean newThread, boolean isRequiredHM) {
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
		this.newThread = newThread;
		this.isRequiredHM = isRequiredHM;
	}

	private String post() throws Exception {
		URL url = new URL(this.protocol, this.host, this.dir);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setDoOutput(true);
		conn.setUseCaches(false);
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Referer", protocol + "://" + host + "/"
				+ board + "/");
		StringBuilder paramStr = new StringBuilder("&time="
				+ System.currentTimeMillis() + hanamogera);

		if (newThread) {
			if (url.getHost().equals("jbbs.shitaraba.net")) {
				paramStr.append("&SUBJECT="
						+ URLEncoder.encode(threadId, "EUC-JP"));
			} else {
				paramStr.append("&subject="
						+ URLEncoder.encode(threadId, "SJIS"));
			}
		} else {
			paramStr.append("&KEY=" + threadId);
		}
		if (this.cookie != null) {
			conn.setRequestProperty("Cookie", this.cookie);
		}

		if (url.getHost().equals("jbbs.shitaraba.net")) {
			paramStr.append("&DIR=" + board.split("/")[0] + "&BBS="
					+ board.split("/")[1]);
			paramStr.append("&submit=" + URLEncoder.encode("書き込む", "EUC-JP"));
			paramStr.append("&FROM=" + URLEncoder.encode(name, "EUC-JP"));
			paramStr.append("&mail=" + URLEncoder.encode(mail, "EUC-JP"));
			paramStr.append("&MESSAGE=" + URLEncoder.encode(mess, "EUC-JP"));
		} else {
			paramStr.append("&bbs=" + board);
			paramStr.append("&submit=" + URLEncoder.encode("書き込む", "SJIS"));
			paramStr.append("&FROM=" + URLEncoder.encode(name, "SJIS"));
			paramStr.append("&mail=" + URLEncoder.encode(mail, "SJIS"));
			paramStr.append("&MESSAGE=" + URLEncoder.encode(mess, "SJIS"));
		}
		PrintWriter pw = new PrintWriter(conn.getOutputStream());
		pw.print(paramStr.toString());
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

		System.out.println(cookie);
		System.out.println(conn.getHeaderFields());
		System.out.println(httpSource.toString());

		if (httpSource.toString().matches(".*<!-- 2ch_X:cookie -->.*")) {
			this.hanamogera = this.getHanamogera(httpSource.toString());
			this.cookie = conn.getHeaderField("Set-Cookie").split("; ")[0];
			this.post();
		}

		return httpSource.toString();
	}

	private String getHanamogera(String hs) {
		// <input type=hidden name="suka" value="pontan">
		int start = hs.indexOf("name=\"");
		int end = start;
		while (hs.charAt(end) != '>') {
			end++;
		}
		return "&" + hs.substring(start, end).split(" |\"|=")[2] + "="
				+ hs.substring(start, end).split(" |\"|=")[6];
	}

	public static void vip2chNewThreadTestPost() {
		try {
			new Post("http", "ex14.vip2ch.com", "/test/bbs.cgi?guid=ON",
					"zikken", "Testテスト", 80, "", "sage", "自作ツールのテスト", true,
					true).post();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void shitarabaNewThreadTestPost() {
		try {
			new Post("http", "jbbs.shitaraba.net", "/bbs/write.cgi",
					"computer/44282", "Testテスト", 80, "", "sage", "自作ツールのテスト",
					true, false).post();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
