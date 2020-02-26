import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Visual implements ActionListener{
	JPanel currentPage;
	int currentArchive;
	int currentState;
	JFrame frame;
	JPanel start;
	JPanel newArchive;
	JPanel oldArchive;
	JPanel modificationReport;
	JPanel settings;
	JPanel save;
	JPanel trim;
	JPanel backup;
	JPanel recovery;
	int counter;
	JButton startButton;

	public static void main(String[] args){
		Visual visual = new Visual();
		visual.init();
	}
	
	public void init(){
		frame = new JFrame("Sync-Tool");
		frame.setSize(700,500);
		frame.setVisible(true);
		this.start();
		frame.setContentPane(this.start);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void start(){
		JPanel panel = new JPanel();
		panel.setBackground(new Color(239,239,239));
		JLabel label = new JLabel("Instructions");
		JLabel a = new JLabel("Once you press Next, you will be able to draw a line.");
		JLabel b = new JLabel("Once you draw the line press enter and watch the rollercoaster begin!");
		JButton button1 = new JButton("Next");
		button1.setBounds(300,575,100,30);
		panel.add(button1);
		button1.addActionListener(this);
		panel.add(label);
		panel.add(a);
		panel.add(b);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int y = (int)screenSize.getHeight();
		int x = (int)screenSize.getWidth();
		label.setBounds((x/2) - 150, 100, 300, 100);
		label.setFont(new Font("Comic Sans MS", Font.BOLD, 45));
		label.setForeground(new Color(255, 102, 102));
		a.setBounds((x/2) - 400, 200, 1000, 100);
		a.setForeground(new Color(255, 102, 102));
		b.setBounds((x/2) - 400, 300, 1000, 100);
		b.setForeground(new Color(255, 102, 102));
		button1.setBounds((x/2) - 100, 600, 100, 50);
		b.setFont(new Font("Comic Sans MS", Font.BOLD, 25));
		a.setFont(new Font("Comic Sans MS", Font.BOLD, 25));
		panel.add(a);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setContentPane(panel);
		frame.setLayout(null);
		frame.setVisible(true);
		frame.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent ke) {
				if (ke.getKeyCode() == ke.VK_ESCAPE) {
					frame.dispose();
				}

			}
		});
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}
