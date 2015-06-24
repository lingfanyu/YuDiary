package LocalPanel;

import java.awt.Event;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import Ground.Client;
import GroundUI.Dairy;

import Listener.JNDelBtnActionListener;
import Listener.JNNewDayNoteBtnActionListener;
import Listener.JNPassBtnActionListener;
import Listener.JNTodoBtnActionListener;
import TuringRobot.JNTuringPanel;

public class JNToolBar {
	private JToolBar toolbar = null;
	 Socket socket;
	 
	public JNToolBar() throws UnknownHostException, IOException {
		
	      socket = new Socket("localhost", 8000);
	      // Socket socket = new Socket("130.254.204.36", 8000);
	      // Socket socket = new Socket("drake.Armstrong.edu", 8000);
	      
	      //new主界面
	     ;
		
		toolbar = new JToolBar();
		toolbar.setFloatable(false);

		JButton newGround = new JButton("广场");
		newGround.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UIManager.put("RootPane.setupButtonVisible", false);
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						/*JNGroundPanel GroundFrame = new JNGroundPanel(
								GraphicsEnvironment
										.getLocalGraphicsEnvironment()
										.getDefaultScreenDevice()
										.getDefaultConfiguration());*/
						new Client();
					}
				});

			}
		});
		
		/*newNoteBtn.registerKeyboardAction(new JNNewBtnActionListener(),
				KeyStroke.getKeyStroke('N', Event.CTRL_MASK),
				JComponent.WHEN_IN_FOCUSED_WINDOW);
		newNoteBtn.addActionListener(new JNNewBtnActionListener());
		 */
		
		JButton newDayNoteBtn = new JButton("新建日记");
		newDayNoteBtn.addActionListener(new JNNewDayNoteBtnActionListener());

		JButton deleteNoteBtn = new JButton(" 删除日记 ");
		deleteNoteBtn.addActionListener(new JNDelBtnActionListener());

		JButton todoBtn = new JButton("词典");
		todoBtn.addActionListener(new JNTodoBtnActionListener());

		
		JButton passBtn = new JButton("修改密码");
		passBtn.addActionListener(new JNPassBtnActionListener());
		
		
		JButton optionBtn = new JButton(" Turing Robot ");
		optionBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UIManager.put("RootPane.setupButtonVisible", false);
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						JNTuringPanel GroundFrame = new JNTuringPanel(
								GraphicsEnvironment
										.getLocalGraphicsEnvironment()
										.getDefaultScreenDevice()
										.getDefaultConfiguration());
					}
				});

			}
		});
		
		JButton aboutBtn = new JButton(" 关于 ");
		aboutBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UIManager.put("RootPane.setupButtonVisible", false);
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						JNAboutPanel aboutFrame = new JNAboutPanel(
								GraphicsEnvironment
										.getLocalGraphicsEnvironment()
										.getDefaultScreenDevice()
										.getDefaultConfiguration());
					}
				});

			}
		});
		
		
		toolbar.add(new JLabel("  "));
		toolbar.add(newDayNoteBtn);
		toolbar.add(new JLabel("  "));
		//toolbar.add(newGround);
		toolbar.add(new JLabel("  "));
		toolbar.add(deleteNoteBtn);
		toolbar.add(new JLabel("  "));
		//toolbar.add(todoBtn);
		toolbar.add(passBtn);
		
		toolbar.add(new JLabel("  "));
		toolbar.add(optionBtn);
		toolbar.add(new JLabel("  "));
		toolbar.add(aboutBtn);
		toolbar.add(new JLabel("  "));
		toolbar.add(new Dairy(socket));
		
		
	}

	public JToolBar getToolbar() {
		return toolbar;
	}

}
