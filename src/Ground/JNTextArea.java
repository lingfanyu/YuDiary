package Ground;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;

import javax.swing.JTextArea;
import javax.swing.text.StyledDocument;


import TextArea.Text;


public class JNTextArea {
	//public static Text ta = new Text(null);	
	public static String text ;
	public static String name ;
	public static int have = 0;
	
	public static String search_name;
	
	public static void setText(Text tp){
		//StyledDocument doc = tp.getStyledDocument();
		//ta.setStyledDocument(doc);
	//	ta.setText(tp.getText());
	}
	public static String ready_to_up() throws FileNotFoundException, IOException{
	//	ObjectOutputStream output = null;
		//output = new ObjectOutputStream(new FileOutputStream("text.txt"));
		//output.writeObject(ta.getDocument());
		String line = ""; 
    	String send = "";
    	//InputStream in = new FileInputStream(new File("text.txt")); 
    	//BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("text.txt"), "GBK")); 
    	//while((line = reader.readLine()) != null){  
          // send +=line;
        //}
		
		return send;
	}
}
