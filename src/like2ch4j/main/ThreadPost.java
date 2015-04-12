package like2ch4j.main;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class ThreadPost extends BoardConnection {

	private String dir = "/test/bbs.cgi";
	private String cookie;
	private String hanamogera;
	private String subject;
	private String name;
	private String mail;
	private String mess;
	private String charCode = "SJIS";

	public Connection dir(String dir) {
		this.dir = dir;
		return this;
	}

	public Connection cookie(String cookie) {
		this.cookie = cookie;
		return this;
	}

	public Connection hanamogera(String hanamogera) {
		this.hanamogera = hanamogera;
		return this;
	}

	public Connection subject(String subject) {
		this.subject = subject;
		return this;
	}

	public Connection name(String name) {
		this.name = name;
		return this;
	}

	public Connection mail(String mail) {
		this.mail = mail;
		return this;
	}

	public Connection mess(String mess) {
		this.mess = mess;
		return this;
	}

	public Connection charCode(String charCode) {
		this.charCode = charCode;
		return this;
	}

	private ThreadPost() {
	}

	private String post() throws Exception {
		URL url = new URL(this.protocol, this.host, this.dir);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setDoOutput(true);
		conn.setUseCaches(false);
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Referer", protocol + "://" + host + "/"
				+ board + "/");
		if (this.cookie != null) {
			conn.setRequestProperty("Cookie", this.cookie);
		}
		StringBuilder paramStr = new StringBuilder("&time="
				+ System.currentTimeMillis() + hanamogera);

		if (url.getHost().equals("jbbs.shitaraba.net")) {
			this.charCode("EUC-JP");
		}

		if (url.getHost().equals("jbbs.shitaraba.net")) {
			paramStr.append("&DIR=" + board.split("/")[0] + "&BBS="
					+ board.split("/")[1]);
		} else {
			paramStr.append("&bbs=" + board);
		}

		paramStr.append("&SUBJECT=" + URLEncoder.encode(subject, charCode));
		paramStr.append("&submit=" + URLEncoder.encode("書き込む", charCode));
		paramStr.append("&FROM=" + URLEncoder.encode(name, charCode));
		paramStr.append("&mail=" + URLEncoder.encode(mail, charCode));
		paramStr.append("&MESSAGE=" + URLEncoder.encode(mess, charCode));

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
			this.hanamogera(this.getHanamogera(httpSource.toString()));
			this.cookie(conn.getHeaderField("Set-Cookie").split("; ")[0]);
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
			((ThreadPost) (new ThreadPost().host("ex14.vip2ch.com"))
					.mail("sage").board("zikken").name("").mess("テスト")
					.mail("sage").subject("テストテストTest")).post();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void shitarabaNewThreadTestPost() {
		try {
			((ThreadPost) (new ThreadPost().host("jbbs.shitaraba.net"))
					.mail("sage").board("computer/44282").name("").mess("テスト")
					.mail("sage").subject("テストテストTest").dir("/bbs/write.cgi"))
					.post();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
