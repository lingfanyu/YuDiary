package TextArea;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.StyledDocument;

import Database.JNNoteSaveService;
import Ground.JNTextArea;
import LocalPanel.JNConstans;
import YuNote.CategoryDao;
import YuNote.Note;
import jodd.io.FileUtil;
import jodd.util.SystemUtil;



public class Menubar extends JMenuBar{
	
	private Window window;
	private Text textPane;
	private Note note;
	
	
	//private File currentFile = null;
	//int first = 0;
	String name;
	//文件
	private final JMenu menuFile = new JMenu("文件(F)");
	private final JMenuItem menuItemNew = new JMenuItem("新建");
	private final JMenuItem menuItemOpen = new JMenuItem("打开");
	private final JMenuItem menuItemSave = new JMenuItem("保存");
	private final JMenuItem menuItemSaveAs = new JMenuItem("另存");
	private final JMenuItem menuItemExit = new JMenuItem("退出");
	
	//编辑
	private final JMenu menuEdit = new JMenu("编辑(E)");	
	private final JMenuItem menuItemCut = new JMenuItem("剪切");
	private final JMenuItem menuItemCopy = new JMenuItem("复制");
	private final JMenuItem menuItemPaste = new JMenuItem("粘贴");
	private final JMenuItem menuItemFind = new JMenuItem("查找");
	private final JMenuItem menuItemReplace = new JMenuItem("替换");
	private final JMenuItem menuItemSelectAll = new JMenuItem("全选");
	private final JMenuItem menuItemUndo = new JMenuItem("撤销");
	private final JMenuItem menuItemRedo = new JMenuItem("恢复");
		
	//插入
	private final JMenu menuInsert = new JMenu("插入(I)");	
	private final JMenuItem menuItemPicture = new JMenuItem("图片");
	private final JMenuItem menuItemDate = new JMenuItem("日期");	
	
	//格式
	private final JMenu menuForm = new JMenu("格式(M)");
	//private final JMenuItem menuItemTheme = new JMenuItem("主题");
	private final JMenuItem menuItemFont = new JMenuItem("字体设置");
	private final JMenuItem menuItemColor = new JMenuItem("字体颜色");
	private final JMenuItem menuItemBackground = new JMenuItem("背景颜色");
	
	//工具
	private final JMenu menuTool = new JMenu("工具(T)");
	private final JMenuItem menuItemCount = new JMenuItem("字数统计");
	private final JMenuItem menuItemCatalog = new JMenuItem("目录显示");
	private final JMenuItem menuItemSquare = new JMenuItem("日记广场");
	private final JMenuItem menuItemFriend = new JMenuItem("玩伴小鱼");
	private final JMenuItem menuItemHelp = new JMenuItem("查看帮助");
		
	public Menubar(Window window) {
		this.window=window;
		this.textPane= window.textPane;
		
		
		
		this.note = window.note;
		
		//if(note.)
		
		// 保存日志到文件
		String fileName = note.getUuid() + ".jn";
		String categoryPath = CategoryDao.getCategoryPath(note.getCategoryId());
		String dataPath = SystemUtil.getWorkingFolder() + JNConstans.DATA_FILE_DIR + categoryPath;
		try {
			FileUtil.mkdirs(dataPath);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String filePath = dataPath + fileName;
		
		System.out.println("Filepath:"+filePath);
		ObjectInputStream input = null;
    	try {
    		input=new ObjectInputStream(new FileInputStream(new File(filePath)));
    		//StyledDocument doc = (StyledDocument) input.readObject();
    		//textPane.setStyledDocument(doc);
    		TextStyle style=(TextStyle)input.readObject();
    		style.get(textPane);
    		validate();
    		
	    	//window.setTitle(currentFile.getName());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
					e.printStackTrace();
				}
            }
		}
		
		
    	name = note.getTitle();
		
		System.out.print("!!!!!!!!!!!!!"+note.getUuid());
		initMenubar();
		initShortcut();
		initListener();
	}
	
	public void setWindow(Window window){
		this.window=window;
		this.textPane=window.textPane;
		
		initMenubar();
		initShortcut();
		initListener();
		
	}
	
	
	
	//初始化菜单栏
	private void initMenubar() {
		add(menuFile);
		menuFile.setMnemonic('F');
		menuFile.add(menuItemNew);
		menuFile.add(menuItemOpen);
		menuFile.add(menuItemSave);
		menuFile.add(menuItemSaveAs);
		menuFile.add(menuItemExit);
		
		add(menuEdit);
		menuEdit.setMnemonic('E');
		menuEdit.add(menuItemCut);
		menuEdit.add(menuItemCopy);
		menuEdit.add(menuItemPaste);
		menuEdit.add(menuItemFind);
		menuEdit.add(menuItemReplace);
		menuEdit.add(menuItemSelectAll);
		menuEdit.add(menuItemUndo);
		menuEdit.add(menuItemRedo);
		
		add(menuInsert);
		menuInsert.setMnemonic('I');
		menuInsert.add(menuItemPicture);
		menuInsert.add(menuItemDate);
		
		add(menuForm);
		menuForm.setMnemonic('M');
		//menuForm.add(menuItemTheme);
		menuForm.add(menuItemFont);
		menuForm.add(menuItemColor);		
		menuForm.add(menuItemBackground);
		
		add(menuTool);
		menuTool.setMnemonic('T');
		menuTool.add(menuItemCount);
		menuTool.add(menuItemCatalog);
		menuTool.add(menuItemSquare);
		menuTool.add(menuItemFriend);
		menuTool.add(menuItemHelp);
	}
	
	//初始化快捷键
	private void initShortcut() {
		menuItemNew.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_MASK));
		menuItemOpen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_MASK));
		menuItemSave.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
		menuItemExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, InputEvent.CTRL_MASK));
		
		menuItemCut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.CTRL_MASK));
		menuItemCopy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_MASK));
		menuItemPaste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.CTRL_MASK));
		menuItemFind.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, InputEvent.CTRL_MASK));
		menuItemReplace.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, InputEvent.CTRL_MASK));
		menuItemSelectAll.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_MASK));
		menuItemUndo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_MASK));
		menuItemRedo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y, InputEvent.CTRL_MASK));
		
		menuItemPicture.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, InputEvent.CTRL_MASK));
		menuItemDate.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, InputEvent.CTRL_MASK));
	}
	
	//初始化监听器
	private void initListener() {
		//文件
		menuItemNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				fileNew();
			}
		});		
		menuItemOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fileOpen();
			}
		});	
		menuItemSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fileSave();
			}
		});	
		menuItemSaveAs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fileSaveAs();
			}
		});		
		menuItemExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(fileChange() >= 0) System.exit(0);
			}
		});

		//编辑
		menuItemCut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textPane.cut();
			}
		});	
		menuItemCopy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textPane.copy();
			}
		});	
		menuItemPaste.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textPane.paste();
			}
		});
		menuItemFind.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				select();
			}
		});
		menuItemReplace.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				replace();
			}
		});
		menuItemSelectAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textPane.selectAll();
			}
		});
		menuItemUndo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					textPane.um.undo();
				}catch(Exception e1){
					JOptionPane.showMessageDialog(null, "没有可撤销的操作！");
				}
			}
		});	
		menuItemRedo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					textPane.um.redo();
				}catch(Exception e1){
					JOptionPane.showMessageDialog(null, "没有可恢复的操作！");
				}
			}
		});
		
		//插入
		menuItemPicture.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				JFileChooser chooser = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter("图片", "gif","png","jpg");
				chooser.setFileFilter(filter);
				
				int returnVal = chooser.showOpenDialog(null);
			    if(returnVal == JFileChooser.APPROVE_OPTION) {
				    String path=chooser.getSelectedFile().getAbsolutePath();
				    ImageIcon icon=new ImageIcon(path);
				    textPane.insertIcon(icon);
				    /*
				    int pos=textPane.getCaretPosition();
				    try {
				    	int insert=Integer.parseInt(textPane.getText());
				    	textPane.setCaretPosition(insert);
				    }
				    */
			    }
			}
		});
		menuItemDate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Document document=textPane.getDocument();
				try {
					document.insertString(textPane.getCaretPosition(), new Date().toString(), null);
				} catch(BadLocationException ex) {
					ex.printStackTrace();
				}
				
			}
		});

		//格式
		menuItemFont.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FontSelect();
			}
		});
		menuItemColor.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				Color color = JColorChooser.showDialog(null, "请选择字体颜色", textPane.getSelectedTextColor());
				if (color == null) {
					color = Color.BLACK;
					System.out.println("Color is null");
				}
				textPane.setForeground(color);
			}
		});
		menuItemBackground.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				Color color = JColorChooser.showDialog(null, "请选择背景颜色", textPane.getBackground());
				if (color == null) {
					color = textPane.getBackground();
				}
				textPane.setBackground(color);
			}
		});

		//工具
		menuItemCount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String[] strings=getCharNum().split(";");
				JOptionPane.showMessageDialog(null, "字数（含空格）："+strings[1]+"\n字数（不含空格）："+strings[2],
						"字数统计", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		
		menuItemHelp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "该软件由group24编写，如有bug，请多多包涵",
						"帮助", JOptionPane.PLAIN_MESSAGE);
			}
		});
	}

	//文本改变了提示保存文本
	int fileChange() {
		if(textPane.changed) {
			int option = JOptionPane.showOptionDialog(null, "是否保存当前文本",
					"提示", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE, 
					null, null, null);
			switch (option) {
			case JOptionPane.OK_OPTION:fileSave();return 1;
			case JOptionPane.NO_OPTION:return 0;
			default:return -1;
			}
		}	
		return 0;
	}
	
	//新建文件
	void fileNew() {
		if(fileChange() < 0) return;
		textPane.setText("");
		//window.setTitle("新建文本");
	}
	
	//打开文件
	void fileOpen() {
		/*if(fileChange() < 0) return;
		
		JFileChooser chooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("文本", "txt", "doc");
		chooser.setFileFilter(filter);

	    int returnVal = chooser.showOpenDialog(null);
	    if(returnVal == JFileChooser.APPROVE_OPTION) {
		    currentFile = chooser.getSelectedFile();
		    if(currentFile != null)
		    {	    
			    ObjectInputStream input = null;
		    	try {
		    		input=new ObjectInputStream(new FileInputStream(currentFile));
		    		StyledDocument doc = (StyledDocument) input.readObject();
		    		textPane.setStyledDocument(doc);
		    		validate();
		    		
			    	//window.setTitle(currentFile.getName());
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
		            if (input != null) {
		                try {
		                    input.close();
		                } catch (IOException e) {
							e.printStackTrace();
						}
		            }
				}
		    }
	    }*/
	}

	
	private Note _buildNote() {
		//note = new Note();
		//String uuid = note.getTitle();
		//String content = (String) textPane.getDocument().toString();
		//StyledDocument doc = (StyledDocument) textPane.getDocument();
		TextStyle style=new TextStyle();
		style.set(textPane);
		note.setstyle(style);
		//Integer categoryId = Integer.parseInt((String) parameters[2]);
		//String editorType = (String) parameters[3];
		String title = name;
		//note.setUuid(uuid);
		//note.setCategoryId(categoryId);
		//note.setdoc(doc);
		note.setTitle(title);
		//note.setdoc(doc);
		
		return note;
	}
	
	
	
	
	//保存文件
	void fileSave() {

		
		JNTextArea.text = (textPane.getText());
		JNTextArea.name = note.getTitle();
		JNTextArea.have = 1;
   		//if(currentFile != null){
   			//ObjectOutputStream output=null;
	    	//try {	
	    		//StyledDocument doc = (StyledDocument) textPane.getDocument();
	    		//output = new ObjectOutputStream(new FileOutputStream(currentFile));
	    		
	    		//output.writeObject(doc);
	    		//output.flush();
	    		
	    		//JOptionPane.showMessageDialog(null, "保存成功！");
	    		//textPane.changed=false;
			//} catch (IOException e) {
				//e.printStackTrace();
			//} finally {
	          //  if (output != null) {
	            //    try {
	                //	output.close();
	              //  } catch (IOException e) {
						//e.printStackTrace();
					//}
	            //}
			//}
   		//}
		//if(note.first == 0){
   		//	fileSaveAs();
   		//}
   		
		//note.first = 1;
   			
   			
   			
   			
		this.note = this._buildNote();
		//JNNoteSaveService.saveNote(note, this._getAttList());
		try {
			JNNoteSaveService.saveNote(note, null);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	//另存文件
	void  fileSaveAs() {
		JNTextArea.text = (textPane.getText());
		JNTextArea.name = note.getTitle();
		JNTextArea.have = 1;
		
		
		/*JFileChooser chooser = new JFileChooser();
	    int returnVal = chooser.showSaveDialog(null);
	    if(returnVal == JFileChooser.APPROVE_OPTION) {
	    	currentFile = chooser.getSelectedFile();
	    	ObjectOutputStream output=null;
	    	try {
	    		if(currentFile.exists()){
	    			int option = JOptionPane.showOptionDialog(null ,"该文本文件已存在，确定要覆盖吗？", 
	    					"警告",	JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE,
	    					null,null,null);
	    			if(option != JOptionPane.OK_OPTION) return;
	    		}else
	    			currentFile.createNewFile();
	    		
		    	StyledDocument doc = (StyledDocument) textPane.getDocument();
		    	output = new ObjectOutputStream(new FileOutputStream(currentFile));
		    		
		    	output.writeObject(doc);
		    	output.flush();
		    		
		    	JOptionPane.showMessageDialog(null, "保存成功！");
		    	//window.setTitle(currentFile.getName());
				textPane.changed=false;
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (output != null) {
		        	try {
		            	output.close();
		        	} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
	    }*/
		name = JOptionPane.showInputDialog("要改名字吗:");
		if(name.equals("null")) ;
		else note.setTitle(name);
		System.out.println("name"+name);
		
		
	this.note = this._buildNote();
	//JNNoteSaveService.saveNote(note, this._getAttList());
	try {
		JNNoteSaveService.saveNote(note, null);
	} catch (IOException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
		
		
	}
	
	void select() {
		Select.launch(window);
	}
	
	private void replace() {
		Replace.launch(window);
	}
	
	public void FontSelect() {
		FontSelect.launch(window);
	}
	
	private String getCharNum() {
		int sum=textPane.getText().length();
		int sum0=textPane.getText().replaceAll("[\t\n]", "").length();
		int sum1=textPane.getText().replaceAll("[ \t\n]", "").length();
		return sum+";"+sum0+";"+sum1;
	}
	
}
