package Ground;

import GroundUI.Dairy;
import GroundUI.square2;

import java.net.*;
import java.io.IOException;

public class Client{
  Socket socket;
  
  public Client() {
    try {
      // Create a socket to connect to the server
      socket = new Socket("localhost", 8000);
      // Socket socket = new Socket("130.254.204.36", 8000);
      // Socket socket = new Socket("drake.Armstrong.edu", 8000);
      
      //new主界面
        new Dairy(socket);
       // new square2(socket,1);
    }
    catch (IOException ex) {
    	System.out.println(ex.toString());
    }
  }
  
  public static void main(String[] args){
	new Client();
  }
}


