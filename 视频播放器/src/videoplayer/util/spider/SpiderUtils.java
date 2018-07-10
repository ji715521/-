package videoplayer.util.spider;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class SpiderUtils {

	public static String getHtmlFromNet(String urlString) throws IOException {

		URL url = new URL(urlString);

		StringBuffer buffer = null;
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();

		conn.setRequestMethod("GET");
		conn.setConnectTimeout(1000 * 10);
		conn.setReadTimeout(1000 * 10);

		int responseCode = conn.getResponseCode();
		if (responseCode == 200) {
			long length = conn.getContentLength();
			System.out.println("content length = " + length);

			BufferedReader bReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

			buffer = new StringBuffer();
			String line = null;
			while ((line = bReader.readLine()) != null) {
				// System.out.println(line);
				buffer.append(line + "\r\n");
			}
			bReader.close();

		} else {
			System.out.println("访问网络失败: " + responseCode);
		}

		conn.disconnect();

		String html = buffer.toString();
		return html;
	}

	public static String getHtmlFromNet(URL url) throws IOException {

		StringBuffer buffer = null;
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();

		conn.setRequestMethod("GET");
		conn.setConnectTimeout(1000 * 10);
		conn.setReadTimeout(1000 * 10);

		int responseCode = conn.getResponseCode();
		if (responseCode == 200) {
			long length = conn.getContentLength();
			System.out.println("content length = " + length);

			BufferedReader bReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

			buffer = new StringBuffer();
			String line = null;
			while ((line = bReader.readLine()) != null) {
				// System.out.println(line);
				buffer.append(line + "\r\n");
			}
			bReader.close();

		} else {
			System.out.println("访问网络失败: " + responseCode);
		}

		conn.disconnect();

		String html = buffer.toString();
		return html;
	}
}
