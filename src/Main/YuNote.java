package Main;

import java.awt.GraphicsEnvironment;
import java.awt.HeadlessException;
import java.io.IOException;
import java.net.UnknownHostException;

import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;

import Database.JPassDialog;
import LocalPanel.JNMainPanel;

import chrriis.dj.nativeswing.swtimpl.NativeInterface;



/*public class YuNote {
	
	public static void main(String[] args) {
		try {
			NativeInterface.open();
			BeautyEyeLNFHelper.frameBorderStyle = BeautyEyeLNFHelper.FrameBorderStyle.generalNoTranslucencyShadow;
           //BeautyEyeLNFHelper.frameBorderStyle = BeautyEyeLNFHelper.FrameBorderStyle.osLookAndFeelDecorated;
           BeautyEyeLNFHelper.launchBeautyEyeLNF();
			
			UIManager.put("RootPane.setupButtonVisible", false);
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					try {
						if(JPassDialog.test_pass()==1)
						{
						JNMainPanel mainPanel = new JNMainPanel(GraphicsEnvironment.getLocalGraphicsEnvironment()
								.getDefaultScreenDevice().getDefaultConfiguration());
						}
					} catch (HeadlessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (UnknownHostException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
			NativeInterface.runEventPump();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}*/

