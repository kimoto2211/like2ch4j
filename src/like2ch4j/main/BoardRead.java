package like2ch4j.main;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class BoardRead extends BoardConnection {
	public BoardRead() {
		super();
		this.dir("subject.txt");
	}

	public void read() throws Exception {
		URL url = new URL(this.protocol, this.host, "/"+this.board + "/" + this.dir);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setDoOutput(true);
		conn.setUseCaches(false);
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Referer", protocol + "://" + host + "/"
				+ board + "/");
		if (this.cookie != null) {
			conn.setRequestProperty("Cookie", this.cookie);
		}

		BufferedReader br = new BufferedReader(new InputStreamReader(
				conn.getInputStream(), "JISAutoDetect"));
		StringBuilder httpSource = new StringBuilder();
		String str;
		while (null != (str = br.readLine())) {
			httpSource.append(str);
		}
		br.close();
		conn.disconnect();

		System.out.println(httpSource);

	}

	public static void shitarabaTest() {
		try {
			((BoardRead) new BoardRead().board("internet/21265").host(
					"jbbs.shitaraba.net")).read();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
