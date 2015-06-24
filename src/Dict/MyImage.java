package Dict;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;


public class MyImage {
	private MyImage() {}
	public static Image getImage(String word) {
		String youdao = "http://dict.youdao.com/ugc/word/";
		URL url = null;
		Scanner input = null;
		Image image = null;
		try {
			url = new URL(youdao + word);
			input = new Scanner(url.openStream(),"utf-8");
			String temp = null;
			while (input.hasNext()) {
				temp = input.nextLine();
				if (temp.indexOf(new String("����鿴��ͼ")) != -1)
					break;
			}
			if (input.hasNext()) {
				input.close();			
				String pattern1 = "<a href=\"/(.+?)\" target=";
				Pattern r1 = Pattern.compile(pattern1);
				Matcher m1 = r1.matcher(temp);
				String result = null;
				if (m1.find())
					result = m1.group(1);
				url = new URL("http://dict.youdao.com/" + result.replaceAll("&amp;", "&"));
				input = new Scanner(url.openStream(),"utf-8");
				temp = null;
				while (input.hasNext()) {
					temp = input.nextLine();
					if (temp.indexOf(new String("<img")) != -1)
						break;
				}
				String pattern2 = "<img src=\"(.+?)\"/>";
				Pattern r2 = Pattern.compile(pattern2);
				Matcher m2 = r2.matcher(temp);
				result = null;
				if (m2.find())
					result = m2.group(1);
				
				url= new URL(result.replaceAll("&amp;", "&"));
				InputStream in = url.openStream();
				image = ImageIO.read(in);
			}
			else 
				image = ImageIO.read(new File("mean/NoImage.jpg"));
			input.close();
		} catch (MalformedURLException e) {
		//	e.printStackTrace();
		} catch (IOException e) {
		//	e.printStackTrace();
		}
		if (image == null)
			try {
				image = ImageIO.read(new File("mean/NoImage.jpg"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		return image;
	}
}
