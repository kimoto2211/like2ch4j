package like2ch4j.main;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ThreadRead extends ThreadConnection {
	public ThreadRead() {
		super();
		this.dir("/bbs/rawmode.cgi/");
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

		StringBuilder tmp = new StringBuilder();
		for (String b : parse(httpSource.toString())) {
			tmp.append(b + "\n");
		}

		return tmp.toString();

	}

	public static void shitarabaTest() {
		try {
			System.out.println(((ThreadRead) new ThreadRead()
					.threadId("1426157209").board("internet/21265")
					.host("jbbs.shitaraba.net")).read());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String[] parse(String str) {
		String[] strs = str.split("<>");
		return strs;

	}
}
