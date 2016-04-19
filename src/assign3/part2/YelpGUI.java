package assign3.part2;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.LineBorder;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;



public class YelpGUI {
	private Font font;
	public static JPanel mainPanel;
	public static JFrame frame; 
	public JPanel searchOptionsPanel, titlePanel;
	public int red,blue,green,r,g,b;
	public Color yelpRed,yellow;
	public static SubBusnPanel subBusnPanel;
	public static JPanel centerPanel;
	public static BusnAttrPanel busnAttrPanel ;
	public static SearchBusnPanel searchBusnPanel;
	public static CardLayout cardLayout;
	public static JPanel cardContainerPanel; 
	public static ReviewListPanel reviewListPanel;
	public static MainBusnPanel mainBusnPanel;

	
	public static void main(String[] args) {
		YelpGUI yelpApp = new YelpGUI();
	}
	
	public YelpGUI(){

		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager
							.getSystemLookAndFeelClassName());
				} catch (ClassNotFoundException | InstantiationException
						| IllegalAccessException
						| UnsupportedLookAndFeelException ex) {
				}

				frame = new JFrame("Yelp Tool");
				mainPanel = new JPanel();
				red = 196;
				green = 18;
				blue = 0;
//				red = 203;
//				green = 48;
//				blue = 7;
				r=254;
				g=245;
				b=214;
				yellow=new Color(r,g,b);
				yelpRed = new Color(red, green, blue);

				
				centerPanel = new JPanel();
				Dimension centerPanelSize = new Dimension(1100, 600);
				centerPanel.setMaximumSize(centerPanelSize);
				centerPanel.setPreferredSize(centerPanelSize);
				centerPanel.setMinimumSize(centerPanelSize);
				centerPanel.setBackground(Color.WHITE);
				centerPanel.setLayout(new GridLayout(0,4,0,0));
				displayTitle();
				
				Dimension size = new Dimension(1200, 750);
				mainPanel.setMaximumSize(size);
				mainPanel.setPreferredSize(size);
				mainPanel.setMinimumSize(size);
				mainPanel.setBorder(BorderFactory.createLineBorder(Color.red));
				mainPanel.setBackground(yellow);
				mainPanel.setForeground(yelpRed);
				mainPanel.add(centerPanel,BorderLayout.CENTER);

				
				mainBusnPanel = new MainBusnPanel();			
				centerPanel.add(mainBusnPanel,BorderLayout.CENTER);

				JScrollPane scroll = new JScrollPane();
				scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
				centerPanel.add(scroll);
				
				subBusnPanel = new SubBusnPanel();
				scroll.setViewportView(subBusnPanel);
				subBusnPanel.setLayout(new GridLayout(0,1,0,0));
				
				JScrollPane scroll2 = new JScrollPane();
				scroll2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
				centerPanel.add(scroll2);
				
				busnAttrPanel = new BusnAttrPanel();
				scroll2.setViewportView(busnAttrPanel);
				busnAttrPanel.setLayout(new GridLayout(28,1,0,0));

				
				JScrollPane scroll3 = new JScrollPane();
				scroll3.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
				scroll3.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
				centerPanel.add(scroll3);
				
				searchBusnPanel = new SearchBusnPanel();
				scroll3.setViewportView(searchBusnPanel);
				//centerPanel.add(searchBusnPanel, BorderLayout.CENTER);
										
				searchBusnPanel.busnSearchMenu();
				
				JScrollPane scroll4 = new JScrollPane();
				scroll4.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
				scroll4.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
				
				reviewListPanel = new ReviewListPanel();
				reviewListPanel.add(scroll4);
				
				frame.setLayout(new GridLayout());
				frame.setBackground(Color.WHITE);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
				//mainPanel is added to the main frame
				frame.add(mainPanel);
				frame.pack();
				frame.setLocationRelativeTo(null);
				frame.setVisible(true);
		
			}
		
		});
	}
	public void displayTitle(){
		
		titlePanel = new JPanel();
		titlePanel.setBackground(yellow);
		

		JLabel titleLabel = new JLabel("YELP DATA SEARCH APP");
		font = new Font("Verdana", Font.PLAIN, 15);
		titleLabel.setFont(font);
		titleLabel.setForeground(yelpRed);
		titlePanel.add(titleLabel);
		titlePanel.setForeground(yelpRed);
		mainPanel.add(titlePanel,BorderLayout.NORTH);
		
	}

}