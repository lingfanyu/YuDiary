package TuringRobot;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GraphicsConfiguration;
import java.awt.GridLayout;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import org.jb2011.lnf.beautyeye.ch3_button.BEButtonUI;
import org.json.JSONException;
import org.json.JSONObject;

import YuNote.OptionDao;

import LocalPanel.JNConstans;
import LocalUI.GUIUtil;




public class JNTuringPanel extends JPanel {
	private JFrame frame = null;
	private JRadioButton simpleEditorRadioButton = new JRadioButton("选项一");
	private JRadioButton advancedEditorRadioButton = new JRadioButton("选项二");
	private JRadioButton markdownEditorRadioButton = new JRadioButton("选项三");

	JTextField MyQues = new JTextField();
	JTextArea TuringAnswer = new JTextArea();
	
	// 默认窗体大小
	private static final int DEFAULT_PANEL_WIDTH = 700;
	private static final int DEFAULT_PANE_HEIGHT = 700;

	public JNTuringPanel(GraphicsConfiguration gc) {
		try {
			_initFrame(gc);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void _initFrame(GraphicsConfiguration gc) throws IOException{
		frame = new JFrame(gc);
		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(DEFAULT_PANEL_WIDTH, DEFAULT_PANE_HEIGHT));
		frame.setTitle("Turing Robot");

		JPanel mainPanel = new JPanel(new BorderLayout());

		JPanel optionPanel = new JPanel(new GridLayout(2, 0));
		optionPanel.setPreferredSize(new Dimension(DEFAULT_PANEL_WIDTH/2, DEFAULT_PANE_HEIGHT/4));
		//optionPanel.add(this.getEditorOptionPanel());
		
		
		optionPanel.add(this.getTuringPanel());
		
		optionPanel.add(this.getDatapathOptionPanel());

		mainPanel.add(optionPanel, BorderLayout.CENTER);
		//mainPanel.add(this.getDatapathOptionPanel(), BorderLayout.NORTH);
		

		JPanel btnPanel = new JPanel();

		JButton cancelBtn = new JButton("取消");

		cancelBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});

		JButton confirmBtn = new JButton("发送");
		confirmBtn.setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.green));
		confirmBtn.setForeground(Color.white);

		confirmBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String APIKEY = "ff5fc5db6abc00f0951586a2bd80b61f"; 
			    String INFO = "";
				try {
					INFO = URLEncoder.encode(MyQues.getText(), "utf-8");
				} catch (UnsupportedEncodingException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} 
			    String getURL = "http://www.tuling123.com/openapi/api?key=" + APIKEY + "&info=" + INFO; 
			    URL getUrl = null;
				try {
					getUrl = new URL(getURL);
				} catch (MalformedURLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} 
			    HttpURLConnection connection = null ;
				try {
					connection = (HttpURLConnection) getUrl.openConnection();
				} catch (IOException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				} 
			    try {
					connection.connect();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} 

			    // 取得输入流，并使用Reader读取 
			    BufferedReader reader = null;
				try {
					reader = new BufferedReader(new InputStreamReader( connection.getInputStream(), "utf-8"));
				} catch (UnsupportedEncodingException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			    StringBuffer sb = new StringBuffer(); 
			    String line = ""; 
			    try {
					while ((line = reader.readLine()) != null) { 
					    sb.append(line); 
					}
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} 
			    try {
					reader.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} 
			    // 断开连接 
			    connection.disconnect(); 
			    JSONObject jo = null;
				try {
					jo = new JSONObject(sb.toString());
				} catch (JSONException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}    
			    //TuringAnswer.setText(TuringAnswer.getText()+sb) ;
			    try {
					TuringAnswer.setText(jo.getString("text")) ;
				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			    MyQues.setText("");
			    
			    System.out.println(sb); 
			}
		});

		btnPanel.add(cancelBtn);
		btnPanel.add(confirmBtn);

		mainPanel.add(btnPanel, BorderLayout.SOUTH);

		frame.getContentPane().add(mainPanel);
		frame.pack();
		GUIUtil.setFrameCenter(frame);

		frame.setVisible(true);

	}

	public JComponent getEditorOptionPanel() {
		JPanel editorPanel = new JPanel(new BorderLayout());

		editorPanel.setBorder(new TitledBorder("选项"));
		editorPanel.setLayout(new BorderLayout());

		ButtonGroup buttonGroup = new ButtonGroup();
		buttonGroup.add(simpleEditorRadioButton);
		buttonGroup.add(advancedEditorRadioButton);
		buttonGroup.add(markdownEditorRadioButton);

		editorPanel.add(simpleEditorRadioButton, BorderLayout.WEST);
		editorPanel.add(advancedEditorRadioButton, BorderLayout.CENTER);
		editorPanel.add(markdownEditorRadioButton, BorderLayout.EAST);

		OptionDao dao = new OptionDao();
		int editorType = dao.get().getEditorType();
		if (JNConstans.EDITORY_ADVANCED == editorType) {
			advancedEditorRadioButton.setSelected(true);
		} else if (JNConstans.EDITORY_MARKDOWN == editorType) {
			markdownEditorRadioButton.setSelected(true);
		} else {
			simpleEditorRadioButton.setSelected(true);
		}
		return editorPanel;
	}

	public JComponent getDatapathOptionPanel() {
		final JPanel datapathPanel = new JPanel(new BorderLayout());

		datapathPanel.setBorder(new TitledBorder("我："));
		datapathPanel.setLayout(new BorderLayout());
		datapathPanel.setPreferredSize(new Dimension(200,200));
		MyQues.setPreferredSize(new Dimension(200,200));
		/*JButton btnSelect = new JButton("选择");
		btnSelect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser();
				fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int intRetVal = fc.showOpenDialog(datapathPanel);
				if (intRetVal == JFileChooser.APPROVE_OPTION) {
					datapath.setText(fc.getSelectedFile().getPath());
				}

			}
		});*/
		datapathPanel.add(MyQues, BorderLayout.CENTER);
		//datapathPanel.add(btnSelect, BorderLayout.EAST);
		datapathPanel.add(new JLabel("*请输入聊天的内容"), BorderLayout.SOUTH);
		return datapathPanel;
	}
	
	public JComponent getTuringPanel() {
		final JPanel Turing = new JPanel(new BorderLayout());
		Turing.setSize(750, 550);
		
		Turing.setBorder(new TitledBorder("Turing："));
		Turing.setLayout(new BorderLayout());
		
		JScrollPane TuringSP = new JScrollPane();
		TuringSP.setViewportView(TuringAnswer);
		
		
		//TuringAnswer.setSize(700, 500);
		TuringAnswer.setLineWrap(true);
		TuringAnswer.setEditable(false);
		/*JButton btnSelect = new JButton("选择");
		btnSelect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser();
				fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int intRetVal = fc.showOpenDialog(datapathPanel);
				if (intRetVal == JFileChooser.APPROVE_OPTION) {
					datapath.setText(fc.getSelectedFile().getPath());
				}

			}
		});*/
		Turing.add(TuringSP, BorderLayout.CENTER);
		//datapathPanel.add(btnSelect, BorderLayout.EAST);
		//datapathPanel.add(new JLabel("*请输入聊天的内容"), BorderLayout.SOUTH);
		return Turing;
	}
	

}
