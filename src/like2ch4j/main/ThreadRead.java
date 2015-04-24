package like2ch4j.main;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ThreadRead extends ThreadConnection {
	public ThreadRead() {
		super();
		this.dir("/bbs/read.cgi/");
	}

	public String read() throws Exception {
		URL url = new URL(this.protocol, this.host, this.dir + board + "/"
				+ this.threadId);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setDoOutput(true);
		conn.setUseCaches(false);
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Referer", protocol + "://" + host + "/"
				+ board + "/");
		conn.setRequestProperty("Accept-Encoding", "gzip");
		if (this.cookie != null) {
			conn.setRequestProperty("Cookie", this.cookie);
		}

		BufferedReader br = new BufferedReader(new InputStreamReader(
				conn.getInputStream(), "JISAutoDetect"));
		StringBuilder httpSource = new StringBuilder();
		String buff;
		while (null != (buff = br.readLine())) {
			httpSource.append(buff);
		}
		br.close();
		conn.disconnect();

		return parse(httpSource.toString());

	}

	public static void shitarabaTest() {
		try {
			System.out.println(((ThreadRead) new ThreadRead()
					.threadId("1428764648").board("internet/21265")
					.host("jbbs.shitaraba.net")).read());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String parse(String str) {
		Pattern number = Pattern.compile("[0-9]+</a>");
		Pattern mess = Pattern.compile("<dd> .*</br>");
		String tmp = join(str, "<dt>", "\n");
		tmp = join(tmp, "<!DOCTYPE.*\n", "");
		tmp = join(tmp, "</dl>.*</html>", "");
		String[] reses = tmp.split("\n");
		StringBuilder[] formattedReses = new StringBuilder[reses.length];
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < reses.length; i++) {
			System.out.print(gr(number, reses[i]) + ":" + gr(mess, reses[i])
					+ "\n");
		}

		// tmp = join(tmp, "<a href=\"" + this.dir + this.board + "/"
		// + this.threadId + "/[0-9]*\".*[a-z]*\">", "");
		// tmp = join(tmp, "<br>", "\n");
		// tmp = join(tmp, "<dd> ", "\n");
		return "";
	}

	public String gr(Pattern p, String str) {
		Matcher m = p.matcher(str);
		if (m.find()) {
			return m.group(0);
		} else {
			return "";
		}
	}

	public String join(String str, String tar, String se) {
		StringBuilder sb = new StringBuilder();
		for (String s : str.split(tar)) {
			sb.append(s + se);
		}
		return sb.toString();
	}
}
