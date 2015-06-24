package Main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import jodd.jerry.Jerry;
import jodd.jerry.JerryFunction;

/*
public class NetUtil {

	/**
	 * 默认编码UTF-8
	 * 
	 * @param url
	 * @return
	 * @throws IOException
	 */
/*	public static String downloadHtml(String url) throws IOException {
		return getPageHTML(url, "UTF-8");
	}

	public static String downloadHtml(String url, String encoding)
			throws IOException {
		return getPageHTML(url, encoding);
	}

	private static String getPageHTML(String url, String encoding) {
		StringBuilder pageHTML = new StringBuilder();
		try {
			URL pageUrl = new URL(url);
			HttpURLConnection connection = (HttpURLConnection) pageUrl
					.openConnection();
			connection.setRequestProperty("User-Agent", "MSIE 7.0");
			BufferedReader br = new BufferedReader(new InputStreamReader(
					connection.getInputStream(), encoding));
			String line = null;
			while ((line = br.readLine()) != null) {
				pageHTML.append(line);
				pageHTML.append("\r\n");
			}
			connection.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pageHTML.toString();
	}

	
	public static List<String> fetchImagesSrc(String html){
		System.out.println("================================");
		Jerry doc = Jerry.jerry(html);
		doc.$("img").each(new JerryFunction() {
			public boolean onNode(Jerry $this, int index) {
				String src = $this.attr("src");
				System.out.println(src);
				return true;
			}
		});
		return null;
	}
}*/
