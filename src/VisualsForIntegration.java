//Comments from keshav

//Selected Archive buttons shouldnt be able to have an action on it
//When you move back to a screen clear all previous actions, uncheck boxes
//Select Archive screen needs back button
//New Archive screen information to type in should be black color not the gray filler info
//Increase size of file explorer thing yes sir yes sir

//Comments from big hammy
//Automatic trim popup not functioning properly 
// Remove Checkboxes in get modification reprot
//-buttons(find data location,archive location,etc) should pop up file explorer
//-back up error message should not pop up with manual trim
//-arch list should initially be empty(to be done during integration)

// New Comments
// Select Data location and select Archive Location not working on archive page
// Select archive location popup in Save As
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
public class VisualsForIntegration implements ActionListener{
	enum Page {START, ARCHIVE_LIST, NEW_ARCHIVE, OPEN_FILE, OLD_ARCHIVE, MODIFICATION_REPORT, 
		SETTINGS, SAVE, TRIM, BACKUP, RECOVERY;}
	private static JFrame frame;
	private static JPanel start;
	private static JPanel archiveList;
	private static JPanel newArchive;
	private static JPanel openFile;
	private static JPanel oldArchive;
	private static JPanel modificationReport;
	private static JPanel confirmation;
	private static JPanel settings;
	private static JPanel saveAs;
	private static JPanel restore;
	private static JPanel trim;
	private static JPanel trimNum;
	private static JPanel autoConfirmation;
	private static JPanel backUp;
	private static JPanel recovery;
	private static ProgressBar progressBar;
	private static Color w;
	private static Color b;
	private static Color g;
	private static ArrayList<Page> history;
	private static Page currPage;
	private static boolean valid;
	private static JTextField trimBehavior;
	private static String archive;
	private static Mainframe mainframe = new Mainframe();
	
	public static String browseFileExplorer() {
		JFileChooser jfc = new JFileChooser();
		jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);		
		jfc.showOpenDialog(null);
		if(jfc.getSelectedFile() != null)
			return jfc.getSelectedFile().toString();
		else
			return null;
	}

	public VisualsForIntegration() {	
		start=new JPanel();
		archiveList=new JPanel();
		newArchive=new JPanel();
		openFile=new JPanel();
		oldArchive=new JPanel();
		modificationReport=new JPanel();	
		confirmation=new JPanel();
		settings=new JPanel();
		saveAs=new JPanel();
		restore=new JPanel();
		trim=new JPanel();
		trimNum=new JPanel();
		autoConfirmation=new JPanel();
		backUp=new JPanel();
		recovery=new JPanel();
		progressBar=new ProgressBar();
		w=new Color(250,250,250);
		b=new Color(100,150,200);
		g=new Color(180,180,180);
		history=new ArrayList<Page>();
		currPage=Page.START;
		valid=false;
		trimBehavior=new JTextField("1");
		archive="";
		makeStart();
		makeArchiveList();
		makeNewArchive();
		makeOpenFile();
		makeOldArchive("[Old Archive Name]");
		makeModificationReport();
		makeConfirmation();
		makeSettings();
		makeSaveAs();
		makeRestore();
		makeTrim();
		makeAutoConfirmation();
		makeBackUp();
		makeRecovery();
		makeProgressBar();
	}
	public void makeStart() {
		start.setBackground(w);
		start.setLayout(new BoxLayout(start, BoxLayout.Y_AXIS));
		start.setBorder(BorderFactory.createEmptyBorder(100,10,100,10));
		JLabel title=new JLabel("Welcome to Sync-Tool");
		title.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 100));
		title.setForeground(b);
		JButton startButton=new JButton();
		startButton.setText("   Start   ");
	    startButton.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 50));
	    startButton.setBackground(b);
	    startButton.setForeground(w);	    
	    start.add(Box.createVerticalGlue());
		start.add(title);		
		title.setAlignmentX(Component.CENTER_ALIGNMENT);
		start.add(Box.createRigidArea(new Dimension(0, 100)));
		start.add(startButton);
		startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		start.add(Box.createVerticalGlue());

		startButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				history.add(Page.START);
				currPage=Page.ARCHIVE_LIST;
				frame.setContentPane(archiveList);
				frame.revalidate();
			}
		});
	}
	public void makeArchiveList() {
		currPage=Page.ARCHIVE_LIST;
		archiveList.setBackground(w);
		archiveList.setLayout(new BoxLayout(archiveList, BoxLayout.Y_AXIS));
		archiveList.setBorder(BorderFactory.createEmptyBorder(0,0,100,0));		
		JLabel title=new JLabel("Sync-Tool");
		title.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 70));
		title.setForeground(b);
		JPanel grid=new JPanel();
		grid.setLayout(new GridLayout(2,1,0,0));
		JLabel scrollHeader=new JLabel("                                       Archives"
				+ "                                       ");
		scrollHeader.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 50));
		scrollHeader.setOpaque(true);
		scrollHeader.setForeground(w);
		scrollHeader.setBackground(b);
		JPanel container = new JPanel();
		container.setLayout(new GridLayout(0,1,0,0));
		container.setBackground(w);
		if(mainframe.getArchives().size() > 0){
			for(Archive arch: mainframe.getArchives()) {
				JButton temp=new JButton();
			    temp.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 50));
			    temp.setText(arch.getArchivePath());//to be changed
			    temp.setBackground(w);
			    temp.setForeground(b);
			    temp.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));	
			    temp.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {	
						history.add(Page.ARCHIVE_LIST);
						oldArchive.removeAll();
						archive=e.getActionCommand();
						makeOldArchive(archive);					
						currPage=Page.OLD_ARCHIVE;
						frame.setContentPane(oldArchive);	
						frame.revalidate();
					}
				});
			    temp.setHorizontalAlignment(JButton.LEFT);
				container.add(temp);
			}
		}else{
			JButton temp=new JButton();
		    temp.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 50));
		    temp.setText("No Archives Made Please Create a new Archive");//to be changed
		    temp.setBackground(w);
		    temp.setForeground(b);
		    temp.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));	
		    temp.setHorizontalAlignment(JButton.LEFT);
			container.add(temp);
		}
		JScrollPane scroll = new JScrollPane(container);    
	    scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
	    scroll.setBackground(w);
	    scroll.getVerticalScrollBar().setUnitIncrement(10);
		JButton newArchiveButton=new JButton();
		newArchiveButton.setText(" New Archive ");
	    newArchiveButton.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 50));
	    newArchiveButton.setBackground(b);
	    newArchiveButton.setForeground(w);	 
	    JButton findArchive=new JButton();
		findArchive.setText(" Find Archive ");
	    findArchive.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 50));
	    findArchive.setBackground(b);
	    findArchive.setForeground(w);	
	    JPanel p=new JPanel();
	    p.setBackground(w);
	    p.setLayout(new FlowLayout());
	    p.add(findArchive);
	    p.add(Box.createRigidArea(new Dimension(200,0)));
	    p.add(newArchiveButton);	 
	    archiveList.add(backButton());
		archiveList.add(title);
		title.setAlignmentX(Component.CENTER_ALIGNMENT);
		title.setBorder(BorderFactory.createEmptyBorder(0,0,50,0));		
		archiveList.add(scrollHeader);
		scrollHeader.setAlignmentX(Component.CENTER_ALIGNMENT);
		archiveList.add(scroll);
		scroll.setAlignmentX(Component.CENTER_ALIGNMENT);
		scroll.setBorder(BorderFactory.createEmptyBorder(0,155,100,155));	
		archiveList.add(p);
		newArchiveButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		newArchiveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				history.add(Page.ARCHIVE_LIST);
				currPage=Page.NEW_ARCHIVE;
				frame.setContentPane(newArchive);	
				frame.revalidate();
			}});
		findArchive.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				history.add(Page.ARCHIVE_LIST);
				currPage=Page.OPEN_FILE;
				frame.setContentPane(openFile);	
				frame.revalidate();
				browseFileExplorer();
			}});
	}
	public void makeNewArchive() {
		newArchive.setBackground(w);
		newArchive.setLayout(new BoxLayout(newArchive, BoxLayout.Y_AXIS));
		newArchive.setBorder(BorderFactory.createEmptyBorder(0,20,70,20));				  
		JLabel title=new JLabel("New Archive Page");
		title.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 70));
		title.setForeground(b);
		final JTextField name=new HintText("Enter archive name");
		name.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 50));
		JPanel gridPane=new JPanel();
		gridPane.setLayout(new GridLayout(2,2,30,30));
		gridPane.setBackground(w);
		final JTextField dLocation=new HintText("Location of Current Data");
		dLocation.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 50));

		JButton selectDLocation=new JButton();
		selectDLocation.setText("Select Data Location");
		selectDLocation.addActionListener(this);
	    selectDLocation.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 50));
	    selectDLocation.setBackground(b);
	    selectDLocation.setForeground(w);	
	    final JTextField aLocation=new HintText("Location Archive");
	    aLocation.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 50));

		JButton selectALocation=new JButton();
		selectALocation.setText("Select Archive Location");
		selectALocation.addActionListener(this);
	    selectALocation.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 50));
	    selectALocation.setBackground(b);
	    selectALocation.setForeground(w);
	    
	    gridPane.add(dLocation);
	    gridPane.add(selectDLocation);
	    gridPane.add(aLocation);
	    gridPane.add(selectALocation);
	    valid=!(name.getText().isEmpty()||dLocation.getText().isEmpty()||aLocation.getText().isEmpty());
	    final JButton create=new JButton();
		create.setText("   Create New Archive   ");
	    create.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 50));
	    create.setBackground(g);
	    create.setForeground(w);

	    newArchive.add(backButton());
	    newArchive.add(title,BorderLayout.NORTH);
	    title.setAlignmentX(Component.CENTER_ALIGNMENT);
	    title.setBorder(BorderFactory.createEmptyBorder(0,100,20,100));
	    newArchive.add(name);
	    name.setAlignmentX(Component.CENTER_ALIGNMENT);
	    name.setMaximumSize(new Dimension(900,300));
	    newArchive.add(gridPane);
	    gridPane.setMaximumSize(new Dimension(2000,800));
	    gridPane.setAlignmentX(Component.CENTER_ALIGNMENT);
	    gridPane.setBorder(BorderFactory.createEmptyBorder(50,100,50,100));
	    newArchive.add(create);

	    create.setAlignmentX(Component.CENTER_ALIGNMENT);   
	    name.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				valid=!(name.getText().isEmpty()||dLocation.getText().isEmpty()||aLocation.getText().isEmpty());
				if(valid)
	  				create.setBackground(b);
			}});
	    dLocation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				valid=!(name.getText().isEmpty()||dLocation.getText().isEmpty()||aLocation.getText().isEmpty());
				if(valid)
	  				create.setBackground(b);
			}});
	    selectDLocation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dLocation.setText(browseFileExplorer());
				dLocation.setForeground(Color.black);
			}});
	    selectALocation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				aLocation.setText(browseFileExplorer());
				aLocation.setForeground(Color.black);
			}});
	    aLocation.addActionListener(new ActionListener() {
	  		public void actionPerformed(ActionEvent e) {
	  			valid=!(name.getText().isEmpty()||dLocation.getText().isEmpty()||aLocation.getText().isEmpty());
	  			if(valid)
	  				create.setBackground(b);
	  		}});
	  	create.addActionListener(new ActionListener() {
	  		public void actionPerformed(ActionEvent e) {
	  			valid=!(name.getText().isEmpty()||dLocation.getText().isEmpty()||aLocation.getText().isEmpty());
	  			if(valid) {
	  				history.add(Page.NEW_ARCHIVE);
	  				currPage=Page.OLD_ARCHIVE;
	  				frame.setContentPane(oldArchive);
	  				frame.revalidate();
					Archive a = new Archive(aLocation.getText(), dLocation.getText(), true);
	  			}
	  		}});	  	
	}
	public void makeOpenFile() {
		currPage=Page.OPEN_FILE;
		openFile.setBackground(w);
		openFile.setLayout(new BoxLayout(openFile, BoxLayout.Y_AXIS));
		openFile.setBorder(BorderFactory.createEmptyBorder(0,20,70,20));			
		JLabel title=new JLabel("Open File Explorer");
		title.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 70));
		title.setForeground(b);
		openFile.add(backButton());
		openFile.add(title);
		title.setAlignmentX(Component.CENTER_ALIGNMENT);
	}
	public void makeOldArchive(String s) {
		currPage=Page.OLD_ARCHIVE;
		oldArchive.setBackground(w);
		oldArchive.setLayout(new BoxLayout(oldArchive, BoxLayout.Y_AXIS));
		oldArchive.setBorder(BorderFactory.createEmptyBorder(0,20,20,20));	
		JButton backUpButton=new JButton();
		backUpButton.setText("Back Up");		
	    backUpButton.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 50));
	    backUpButton.setBackground(b);
	    backUpButton.setForeground(w);	
	    JButton settingsButton=new JButton();
	    settingsButton.setText(" Settings ");
	    settingsButton.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 50));
	    settingsButton.setBackground(b);
	    settingsButton.setForeground(w);	
	    final JButton trimButton=new JButton();
	    trimButton.setText("   Trim   ");
	    trimButton.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 50));
	    if(!trimBehavior.isVisible())
	    	trimButton.setBackground(g);
	    else
	    	trimButton.setBackground(b);
	    trimButton.setForeground(w);	
		JLabel title=new JLabel(s);
		title.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 70));
		title.setForeground(b);
		JLabel scrollHeader=new JLabel("                     Drop Down with Archives"
				+ "                     ");
		scrollHeader.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 50));
		scrollHeader.setOpaque(true);
		scrollHeader.setForeground(w);
		scrollHeader.setBackground(b);
		JPanel container = new JPanel();
		container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
		container.setBackground(w);
		JScrollPane scroll = new JScrollPane(container);    
	    scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
	    scroll.setBackground(w);
	    scroll.getVerticalScrollBar().setUnitIncrement(10);
	    final JButton restore=new JButton();
		restore.setText("<html><center>Restore to <br>Current Location</center></html>");
	    restore.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 50));
	    restore.setBackground(g);
	    restore.setForeground(w);	
	    final JButton browse=new JButton();
		browse.setText("<html><center>Browse New<br>Location</center></html>");
	    browse.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 50));
	    browse.setBackground(g);
	    browse.setForeground(w);	
	    final JButton getMod=new JButton();
		getMod.setText("<html><center>Get Modification<br>Report</center></html>");
	    getMod.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 50));
	    getMod.setBackground(g);
	    getMod.setForeground(w);	
		ButtonGroup group = new ButtonGroup();
		valid=trimBehavior.isVisible();
		for(int i=0;i<10;i++) {
			JRadioButton temp=new JRadioButton();
			temp.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					valid=true;
					trimButton.setBackground(b);
					restore.setBackground(b);
					browse.setBackground(b);
					getMod.setBackground(b);
				}
			});
		    temp.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 50));
		    temp.setText("State "+(i+1));
		    temp.setBackground(w);
		    temp.setForeground(b);
		    temp.setBorder(BorderFactory.createEmptyBorder());
		    temp.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		    group.add(temp);
			container.add(temp);
		}		
	    oldArchive.add(backButton());
	    oldArchive.add(title);
		title.setAlignmentX(Component.CENTER_ALIGNMENT);
	    JPanel p1=new JPanel();
	    p1.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));	
	    p1.setBackground(w);
	    p1.setLayout(new FlowLayout());
	    p1.add(backUpButton);
	    p1.add(Box.createRigidArea(new Dimension(300, 0)));
	    p1.add(settingsButton);
	    p1.add(Box.createRigidArea(new Dimension(300, 0)));
	    p1.add(trimButton);	
	    oldArchive.add(p1);
	    oldArchive.add(scrollHeader);
	    scrollHeader.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));	
	    scrollHeader.setAlignmentX(Component.CENTER_ALIGNMENT);
	    oldArchive.add(scroll);
		scroll.setAlignmentX(Component.CENTER_ALIGNMENT);
		scroll.setBorder(BorderFactory.createEmptyBorder(0,195,50,195));	
	    JPanel p2=new JPanel();
	    p2.setBackground(w);
	    p2.setLayout(new FlowLayout());
	    p2.add(restore);
	    p2.add(Box.createRigidArea(new Dimension(100, 0)));
	    p2.add(browse);
	    p2.add(Box.createRigidArea(new Dimension(100, 0)));
	    p2.add(getMod);	
	    oldArchive.add(p2);
	    
	    backUpButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				history.add(Page.OLD_ARCHIVE);
				currPage=Page.BACKUP;
				if(trimBehavior.isVisible()) {
					frame.setContentPane(backUp);
					frame.revalidate();
				}
			}
		});
	    settingsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				history.add(Page.OLD_ARCHIVE);
				currPage=Page.SETTINGS;
				frame.setContentPane(settings);
				frame.revalidate();
			}
		});
		trimButton.addActionListener(this);/*new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			    if(trimBehavior>0||valid) {
					history.add(Page.OLD_ARCHIVE);
					frame.setContentPane(trim);
					frame.revalidate();
			    }
			}
		});*/
		restore.addActionListener(this);
		browse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			    if(valid) {
					history.add(Page.OLD_ARCHIVE);
					currPage=Page.SAVE;
					frame.setContentPane(saveAs);
					frame.revalidate();
			    }
			}
		});
		getMod.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			    if(valid) {
					history.add(Page.OLD_ARCHIVE);
					currPage=Page.MODIFICATION_REPORT;
					frame.setContentPane(modificationReport);
					frame.revalidate();
			    }
			}
		});
	 	
	}
	public void makeModificationReport() {
		modificationReport.setBackground(w);
		modificationReport.setLayout(new BoxLayout(modificationReport, BoxLayout.Y_AXIS));
		modificationReport.setBorder(BorderFactory.createEmptyBorder(0,0,50,0));		
		JLabel title=new JLabel("Modification Report");
		title.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 70));
		title.setForeground(b);
		JLabel scrollHeader=new JLabel("                             Files in State                             ");
		scrollHeader.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 50));
		scrollHeader.setOpaque(true);
		scrollHeader.setForeground(w);
		scrollHeader.setBackground(b);
		JPanel container = new JPanel();
		container.setLayout(new GridLayout(0,1,0,0));
		container.setBackground(w);
		for(int i=0;i<10;i++) {
			JLabel temp=new JLabel();
		    temp.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 50));
		    temp.setText("File Name "+(i+1));
		    temp.setBackground(w);
		    temp.setForeground(b);
		    temp.setBorder(BorderFactory.createEmptyBorder());
		    temp.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));	
		    temp.setHorizontalAlignment(JButton.LEFT);
			container.add(temp);
		}		
		JScrollPane scroll = new JScrollPane(container);    
	    scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
	    scroll.setBackground(w);
	    scroll.getVerticalScrollBar().setUnitIncrement(10);
		JButton save=new JButton();
		save.setText("Save Report");
	    save.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 50));
	    save.setBackground(b);
	    save.setForeground(w);	   
	    JButton print=new JButton();
		print.setText("Print");
	    print.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 50));
	    print.setBackground(b);
	    print.setForeground(w);	  
	    JButton dismiss=new JButton();
		dismiss.setText("Dismiss");
	    dismiss.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 50));
	    dismiss.setBackground(b);
	    dismiss.setForeground(w);
	    modificationReport.add(backButton());
		modificationReport.add(title);
		title.setAlignmentX(Component.CENTER_ALIGNMENT);
		title.setBorder(BorderFactory.createEmptyBorder(0,0,50,0));		
		modificationReport.add(scrollHeader);
		scrollHeader.setAlignmentX(Component.CENTER_ALIGNMENT);
		modificationReport.add(scroll);
		scroll.setAlignmentX(Component.CENTER_ALIGNMENT);
		scroll.setBorder(BorderFactory.createEmptyBorder(0,247,100,247));			
		JPanel p1=new JPanel();
	    p1.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));	
	    p1.setBackground(w);
	    p1.setLayout(new FlowLayout());
	    p1.add(save);
	    p1.add(Box.createRigidArea(new Dimension(200, 0)));
	    p1.add(print);
	    p1.add(Box.createRigidArea(new Dimension(200, 0)));
	    p1.add(dismiss);	
	    modificationReport.add(p1);
		//meena how to print?
	    print.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

			}
		});
	    //what to call to save
		save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

			}
		});
		//how to dismiss
		dismiss.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

			}
		});
	    
	}
	public void makeConfirmation() {
		confirmation.setBackground(w);
		confirmation.setLayout(new BoxLayout(confirmation, BoxLayout.Y_AXIS));
		JLabel title=new JLabel("Trim Confirmation");
		title.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 70));
		title.setForeground(b);
		JLabel message=new JLabel("<html><center>"
				+ "WARNING: You have selected to trim [backup information]. "
				+ "This and all previous backups will be permanently deleted. "
				+ "Proceed with the trim?</center></html>", 
				SwingConstants.CENTER);
		message.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 50));
		message.setForeground(b);
		confirmation.add(title);
		title.setAlignmentX(Component.CENTER_ALIGNMENT);
		title.setBorder(BorderFactory.createEmptyBorder(50,20,100,20));	
		confirmation.add(message);
		message.setAlignmentX(Component.CENTER_ALIGNMENT);
		message.setBorder(BorderFactory.createEmptyBorder(0,50,0,50));	
		confirmation.setPreferredSize(new Dimension(1000,700));
	}
	public void makeSettings() {
		settings.setBackground(w);
		settings.setLayout(new BoxLayout(settings, BoxLayout.Y_AXIS));
		settings.setBorder(BorderFactory.createEmptyBorder(0,50,70,50));				  
		JLabel title=new JLabel("Settings");
		title.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 70));
		title.setForeground(b);		
		JPanel p=new JPanel();
		p.setLayout(new GridLayout(1,2,50,10));
		p.setBackground(w);
		JButton selectDLocation=new JButton();
		selectDLocation.setText("Select Data Location");
	    selectDLocation.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 50));
	    selectDLocation.setBackground(b);
	    selectDLocation.setForeground(w);	
		JTextField dLocation=new HintText("Location of Current Data");
		dLocation.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 50));
	    dLocation.setForeground(g);	
		dLocation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {}});	
	    p.add(selectDLocation);
	    p.add(dLocation);
	    JButton trimButton=new JButton();
		trimButton.setText("Trim Behavior");
	    trimButton.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 50));
	    trimButton.setBackground(b);
	    trimButton.setForeground(w);	
	    JButton save=new JButton();
		save.setText("Save");
	    save.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 50));
	    save.setBackground(b);
	    save.setForeground(w);
	    settings.add(backButton());
	    settings.add(title);
	    title.setAlignmentX(Component.CENTER_ALIGNMENT);
	    title.setBorder(BorderFactory.createEmptyBorder(0,100,0,100));
	    settings.add(p);
	    p.setAlignmentX(Component.CENTER_ALIGNMENT);
	    p.setBorder(BorderFactory.createEmptyBorder(20,100,100,100));
	    p.setMaximumSize(new Dimension(Short.MAX_VALUE,200));
	    settings.add(trimButton);
	    trimButton.setAlignmentX(Component.CENTER_ALIGNMENT);
	    JPanel space=new JPanel();
	    space.setBackground(w);
	    space.add(Box.createHorizontalGlue());
	    space.add(save);	
	    space.setBorder(BorderFactory.createEmptyBorder(250,900,0,0));
	    settings.add(space);

	    trimButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				history.add(Page.SETTINGS);
				currPage=Page.TRIM;
				frame.setContentPane(trim);
				frame.revalidate();
				//frame.pack();
				//frame.setVisible(true);
			}
		});
	  // what to do for save
	    save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

			}
		});
	  //meena what to do for back???????
	    selectDLocation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				browseFileExplorer();
			}
		});
	}
	public void makeSaveAs() {
		saveAs.setBackground(w);
		saveAs.setLayout(new BoxLayout(saveAs, BoxLayout.Y_AXIS));
		saveAs.setBorder(BorderFactory.createEmptyBorder(0,50,70,50));				  
		JLabel title=new JLabel("Save As");
		title.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 70));
		title.setForeground(b);		
		JPanel p=new JPanel();
		p.setLayout(new GridLayout(1,2,50,10));
		p.setBackground(w);
		JButton selectALocation=new JButton();
		selectALocation.setText("Select Archive Location");
	    selectALocation.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 50));
	    selectALocation.setBackground(b);
	    selectALocation.setForeground(w);	
		JTextField aLocation=new HintText("Location of Archive");
		aLocation.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 50));
	    aLocation.setForeground(g);	
	    p.add(aLocation);
	    p.add(selectALocation);
	    JButton save=new JButton();
	    save.setText("Save Archive");
	    save.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 50));
	    save.setBackground(b);
	    save.setForeground(w);	
	    saveAs.add(backButton());
	    saveAs.add(title);
	    title.setAlignmentX(Component.CENTER_ALIGNMENT);
	    title.setBorder(BorderFactory.createEmptyBorder(0,100,50,100));
	    saveAs.add(p);
	    saveAs.add(Box.createRigidArea(new Dimension(0, 100)));
	    p.setAlignmentX(Component.CENTER_ALIGNMENT);
	    p.setBorder(BorderFactory.createEmptyBorder(20,100,100,100));
	    p.setMaximumSize(new Dimension(Short.MAX_VALUE,200));
	    saveAs.add(save);
	    save.setAlignmentX(Component.CENTER_ALIGNMENT);
	    selectALocation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				browseFileExplorer();
			}});
	    aLocation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}});
	    save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

			}});
	}
	
	public void makeRestore() {
		restore.setBackground(w);
		restore.setLayout(new BoxLayout(restore, BoxLayout.Y_AXIS));
		JLabel title=new JLabel("Restore");
		title.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 70));
		title.setForeground(b);
		JLabel message=new JLabel("<html><center>"
				+ "A file has been selected for restoration. "
				+ "Are you sure you want to override the current data to an earlier version?</center></html>", 
				SwingConstants.CENTER);
		message.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 50));
		message.setForeground(b);
		restore.add(title);
		title.setAlignmentX(Component.CENTER_ALIGNMENT);
		title.setBorder(BorderFactory.createEmptyBorder(50,20,100,20));	
		restore.add(message);
		message.setAlignmentX(Component.CENTER_ALIGNMENT);
		message.setBorder(BorderFactory.createEmptyBorder(0,50,0,50));	
		restore.setPreferredSize(new Dimension(1000,700));
	}
	public void makeTrim() {
		trim.setBackground(w);
		trim.setLayout(new BoxLayout(trim, BoxLayout.Y_AXIS));
		trim.setBorder(BorderFactory.createEmptyBorder(0,20,70,20));	
		JLabel title=new JLabel("Trim Behavior");
		title.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 100));
		title.setForeground(b);
		JLabel prompt=new JLabel();
		prompt.setText("How many versions do you want to keep?");
		prompt.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 50));
		prompt.setForeground(b);
		trimBehavior.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 50));
		trimBehavior.setForeground(g);	
		JLabel versions=new JLabel();
		versions.setText("versions");
		versions.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 30));
		versions.setForeground(b);
		trimNum.setBackground(w);
		trimNum.setLayout(new FlowLayout());
		trimNum.add(prompt);
		prompt.setBorder(BorderFactory.createEmptyBorder(50,50,50,20));		
		trimNum.add(trimBehavior);
		trimNum.add(versions);			
		JPanel gridPane=new JPanel();
		gridPane.setLayout(new GridLayout(1,2,50,50));
		gridPane.setBackground(w);
		JRadioButton radio1=new JRadioButton("Manual Trim");
		radio1.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 50));
		radio1.setForeground(b);
		radio1.setBackground(w);
		JRadioButton radio2=new JRadioButton("Automatic Trim");
		radio2.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 50));
		radio2.setForeground(b);
		radio2.setBackground(w);
		radio2.setSelected(true);
		JPanel p=new JPanel();
		p.setLayout(new GridLayout(1,2,50,10));
		p.setBackground(w);		
		ButtonGroup group = new ButtonGroup();
		group.add(radio1);
		group.add(radio2);		
		gridPane.add(radio1);
		gridPane.add(radio2);		
		trimBehavior.setMinimumSize(new Dimension(150,150));
		trim.add(backButton());
		trim.add(title);
		title.setAlignmentX(Component.CENTER_ALIGNMENT);
		trim.add(gridPane);	
		gridPane.setAlignmentX(Component.CENTER_ALIGNMENT);
		gridPane.setBorder(BorderFactory.createEmptyBorder(50,300,20,300));	
		trim.add(trimNum);
		radio1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				trimNum.setVisible(false);
				trimBehavior.setVisible(false);
				oldArchive.removeAll();
				makeOldArchive(archive);
				frame.revalidate();
			}});
		radio2.addActionListener(new ActionListener() {//show textbox
			public void actionPerformed(ActionEvent e) {
				trimNum.setVisible(true);
				trimBehavior.setVisible(true);	
				oldArchive.removeAll();
				makeOldArchive(archive);
				frame.revalidate();
			}});	
	}
	public void makeAutoConfirmation() {
		autoConfirmation.setBackground(w);
		autoConfirmation.setLayout(new BoxLayout(autoConfirmation, BoxLayout.Y_AXIS));
		JLabel title=new JLabel("Automatic Trim Confirmation");
		title.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 70));
		title.setForeground(b);
		JLabel message=new JLabel("<html><center>You have selected to keep "+trimBehavior.getText()
		+" version(s) with Automatic Trim. Do you wish to continue with this decision?</center></html>", 
				SwingConstants.CENTER);
		message.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 50));
		message.setForeground(b);
		autoConfirmation.add(title);
		title.setAlignmentX(Component.CENTER_ALIGNMENT);
		title.setBorder(BorderFactory.createEmptyBorder(50,20,100,20));	
		autoConfirmation.add(message);
		message.setAlignmentX(Component.CENTER_ALIGNMENT);
		message.setBorder(BorderFactory.createEmptyBorder(0,50,0,50));	
		autoConfirmation.setPreferredSize(new Dimension(1200,700));
	}
	public void makeBackUp() {
		currPage=Page.BACKUP;
		backUp.setBackground(w);
		backUp.setLayout(new BoxLayout(backUp, BoxLayout.Y_AXIS));
		backUp.setBorder(BorderFactory.createEmptyBorder(0,20,70,20));	
		JLabel title=new JLabel("Back Up");
		title.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 70));
		title.setForeground(b);
		JLabel message=new JLabel("<html><center>"
				+ "WARNING: You have automatic trim behavior on. "
				+ "If you back up now, you will lose [state information]</center></html>", 
				SwingConstants.CENTER);
		message.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 50));
		message.setForeground(b);
		JButton bBackUp=new JButton();
		bBackUp.setText("Select Archive Location");
	    bBackUp.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 50));
	    bBackUp.setBackground(b);
	    bBackUp.setForeground(w);	
	    backUp.add(backButton());
		backUp.add(title);
		title.setAlignmentX(Component.CENTER_ALIGNMENT);
		title.setBorder(BorderFactory.createEmptyBorder(50,20,50,20));	
		backUp.add(message);
		message.setAlignmentX(Component.CENTER_ALIGNMENT);
		message.setBorder(BorderFactory.createEmptyBorder(50,50,100,50));	
		backUp.add(bBackUp);
		bBackUp.setAlignmentX(Component.CENTER_ALIGNMENT);
		//alternatives
		bBackUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}});
	}
	public void makeRecovery() {
		recovery.setBackground(w);
		recovery.setLayout(new BoxLayout(recovery, BoxLayout.Y_AXIS));
		recovery.setBorder(BorderFactory.createEmptyBorder(0,50,70,50));	
		JLabel title=new JLabel("Recovery");
		title.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 70));
		title.setForeground(b);
		JLabel message=new JLabel("<html><center>There has been a unexpected shutdown of the "
				+ "system or an error. What would you like to do?</center></html>", 
				SwingConstants.CENTER);
		message.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 50));
		message.setForeground(b);
		JButton replace=new JButton();
		replace.setText("<html><center>Replace incomplete<br>version with<br>new backup</center></html>");
		replace.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 50));
		replace.setBackground(b);
		replace.setForeground(w);	
	    JButton delete=new JButton();
	    delete.setText("<html><center>Delete all<br>partial versions</html></center>");
	    delete.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 50));
	    delete.setBackground(b);
	    delete.setForeground(w);	
	    JButton close=new JButton();
	    close.setText("Close Archive");
	    close.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 50));
	    close.setBackground(b);
	    close.setForeground(w);	
	    JButton exit=new JButton();
	    exit.setText("Exit Sync Tool");
	    exit.addActionListener(this);
	    exit.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 50));
	    exit.setBackground(b);
	    exit.setForeground(w);	
	    JPanel container = new JPanel();
		container.setLayout(new GridLayout(1,3,100,100));
		container.setBackground(w);
		container.add(replace);
		container.add(delete);
		container.add(close);
		container.setBorder(BorderFactory.createEmptyBorder(150,50,150,50));	
		recovery.add(title);
		title.setAlignmentX(Component.CENTER_ALIGNMENT);
		title.setBorder(BorderFactory.createEmptyBorder(0,50,50,50));	
		recovery.add(message);
		message.setAlignmentX(Component.CENTER_ALIGNMENT);
		recovery.add(container);
		container.setAlignmentX(Component.CENTER_ALIGNMENT);
		recovery.add(exit);
		exit.setAlignmentX(Component.CENTER_ALIGNMENT);
		exit.setBorder(BorderFactory.createEmptyBorder(30,20,30,20));	
		replace.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}});	
		delete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}});	
		close.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}});	
	}
	public void actionPerformed(ActionEvent e) {
		if(valid&&e.getActionCommand().trim().equals("Trim")) {
			JButton[] buttons= {new JButton("Cancel"),new JButton("Continue")};
			buttons[0].addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {	
					JOptionPane.getRootFrame().dispose();
				}});
			buttons[1].addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {	
					JOptionPane.getRootFrame().dispose();
					//some trim happens
				}});
		    popUp(confirmation, "Trim Confirmation",buttons);
		}else if(e.getActionCommand().trim().equals("<html><center>Restore to "
				+ "<br>Current Location</center></html>")) {
			JButton[] buttons= {new JButton("No"),new JButton("Yes")};
			buttons[0].addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {	
					JOptionPane.getRootFrame().dispose();
				}});
			buttons[1].addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {	
					JOptionPane.getRootFrame().dispose();
					//some restore happens
				}});
			if(valid)
				popUp(restore, "Restore Confirmation",buttons);		
		}
		frame.revalidate();
	}
	public void popUp(JPanel p,String title,JButton[] buttons) {//makes pop-ups
	    JOptionPane.showOptionDialog(null, p, title,
                JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, buttons, null);
	}
	public JPanel backButton() {
		JPanel backButton=new JPanel();
		final JButton back=new JButton();
		back.setText("  Back  ");
	    back.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 50));
	    back.setBackground(b);
	    back.setForeground(w);	
	    final JButton[] buttons= {new JButton("No"),new JButton("Yes")};
	    buttons[0].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.getRootFrame().dispose();
			}});
		buttons[1].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {	
				JOptionPane.getRootFrame().dispose();
				history.remove(history.size()-1);	
				currPage=Page.SETTINGS;
				frame.setContentPane(settings);
				frame.revalidate();
			}});	
		back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {	
				if(currPage.equals(Page.TRIM)&&Integer.parseInt(trimBehavior.getText())>1){
						autoConfirmation.removeAll();
						makeAutoConfirmation();						
						popUp(autoConfirmation, "Autonomic Trim Cgonfirmation",buttons);										 
				}else {
				if(history.size()>0) {
					Page last=history.remove(history.size()-1);	
					currPage=last;
					if(last.equals(Page.START))
						frame.setContentPane(start);
					else if(last.equals(Page.ARCHIVE_LIST))
						frame.setContentPane(archiveList);
					else if(last.equals(Page.NEW_ARCHIVE))
						frame.setContentPane(archiveList);
					else if(last.equals(Page.ARCHIVE_LIST))
						frame.setContentPane(archiveList);
					else if(last.equals(Page.SETTINGS))
						frame.setContentPane(settings);
					else if(last.equals(Page.OLD_ARCHIVE))
						frame.setContentPane(oldArchive);
				}
				frame.revalidate();
				}
			}
		});

	    backButton.setBorder(BorderFactory.createEmptyBorder(10,0,0,1300));	
	    backButton.add(back);
	    backButton.setMaximumSize(new Dimension(Short.MAX_VALUE,100));
	    backButton.setBackground(w);
		
	    return backButton;
	}
	public void makeProgressBar() {
		//ProgressBar b=new ProgressBar();		
	}
	public static void main(String[] args) {
		VisualsForIntegration v=new VisualsForIntegration();
		mainframe = new Mainframe();
		frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(start);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
        frame.setBackground(w);
	    frame.setVisible(true);
        UIManager.put("OptionPane.background",w);
        UIManager.put("Panel.background",w);     
       /* try {
			progressBar.iterate();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}*/
	}
}
class HintText extends JTextField implements FocusListener {
	  private final String hint;
	  private boolean showingHint;

	  public HintText(final String hint) {
	    super(hint);
	    this.hint = hint;
	    this.setForeground(new Color(180,180,180));
	    this.showingHint = true;
	    super.addFocusListener(this);
	  }

	  @Override
	  public void focusGained(FocusEvent e) {
	    if(this.getText().isEmpty()) {
	      super.setText("");
	      showingHint = false;
	    }
	  }
	  @Override
	  public void focusLost(FocusEvent e) {
	    if(this.getText().isEmpty()) {
	      super.setText(hint);
	      showingHint = true;
	    }
	  }

	  @Override
	  public String getText() {
	    return showingHint ? "" : super.getText();
	  }
}
class ProgressBar extends JPanel {
	private static int counter=0;
	private static int limit=1000;
	public void paintComponent(Graphics g) {
		g.setColor(new Color(250,250,250));
		g.fillRect(0, 0, 1050, 250);
		g.setColor(new Color(100,150,200));
		g.fillRect(10, 10, limit+20, 100);
		g.fillRect(70, 180, 900, 100);
		g.setColor(new Color(250,250,250));
		g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 50));
		g.drawString("Your file has been backed up "+(counter*100/limit-1)+"%.",100,250);
		g.setColor(new Color(250,250,250));
		g.fillRect(20+counter, 15, limit+5-counter, 90);
		g.setColor(new Color(100,200,150));
		g.fillRect(15, 15, counter, 90);		
	}
	public void iterate() throws InterruptedException {
		while (true &&counter<=limit){
	        Thread.sleep(100);
	        repaint();
	        counter+=10;
        }
	}
}
 
