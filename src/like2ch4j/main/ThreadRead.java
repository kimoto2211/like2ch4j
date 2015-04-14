package like2ch4j.main;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ThreadRead extends ThreadConnection {
	public ThreadRead() {
		super();
		this.dir("/bbs/read.cgi/");
	}

	public void read() throws Exception {
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
				conn.getInputStream()));
		StringBuilder httpSource=new StringBuilder();
		String buff;
		while (null != (buff = br.readLine())) {
			httpSource.append(buff);
		}
		br.close();
		conn.disconnect();

		System.out.println(httpSource);

	}

	public static void shitarabaTest() {
		try {
			((ThreadRead) new ThreadRead().threadId("1428843272")
					.board("computer/44282").host("jbbs.shitaraba.net")).read();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
