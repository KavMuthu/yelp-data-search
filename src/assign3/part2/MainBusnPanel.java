package assign3.part2;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;
import javax.swing.border.Border;
/***
 * 
 * @author Kavitha Muthu W1114928
 *This class displays the Main business list of the Yelp Application
 *
 */
public class MainBusnPanel extends JPanel {

	public int red, blue, green,r,g,b;
	public Color yelpRed, yellow;
	public SubBusnPanel subBusnPanel;
	public ArrayList<String> main_busn;
	public static ArrayList<String> mainBusnList = new ArrayList<String>();
	public JCheckBox[] checkBox;

	/**
	 * defines the layout and background setting of the Main business list panel
	 */
	public MainBusnPanel() {

		this.setOpaque(true);
		this.setAlignmentX(LEFT_ALIGNMENT);
		red = 196;
		green = 18;
		blue = 0;
		yelpRed = new Color(red, green, blue);
		
		this.setBorder(BorderFactory.createStrokeBorder(new BasicStroke(0.75f), yelpRed));
		this.setBackground(Color.WHITE);
		
		//method to display the main businesses is invoked in the constructor
		addMainBusiness();
	
	}
	/**
	 * the main business list of 28 businesses are added to an array list
	 */
	private void addMainBusiness(){
		main_busn = new ArrayList<String>();
		main_busn.add("Active Life"); 
		main_busn.add("Arts & Entertainment"); 
		main_busn.add("Automotive"); 
		main_busn.add("Car Rental"); 
		main_busn.add("Cafes"); 
		main_busn.add("Beauty & Spas"); 
		main_busn.add("Convenience Stores"); 
		main_busn.add("Dentists"); 
		main_busn.add("Doctors"); 
		main_busn.add("Drugstores"); 
		main_busn.add("Department Stores"); 
		main_busn.add("Education"); 
		main_busn.add("Event Planning & Services"); 
		main_busn.add("Flowers & Gifts"); 
		main_busn.add("Food"); 
		main_busn.add("Health & Medical"); 
		main_busn.add("Home Services"); 
		main_busn.add("Home & Garden"); 
		main_busn.add("Hospitals"); 
		main_busn.add("Hotels & Travel"); 
		main_busn.add("Hardware Stores"); 
		main_busn.add("Grocery"); 
		main_busn.add("Medical Centers"); 
		main_busn.add("Nurseries & Gardening"); 
		main_busn.add("Nightlife"); 
		main_busn.add("Restaurants"); 
		main_busn.add("Shopping"); 
		main_busn.add("Transportation"); 
		
		//the method to display the checkboxes and main business names is invoked here
		displayMainCategories(main_busn);

	}
	/**
	 * 
	 * @param mainList - mainList is the array list to which the main businesses are added
	 */
	public void displayMainCategories(ArrayList<String> mainList){
		
		this.removeAll();
		//according to the size of the array list the check boxes are generated
		checkBox = new JCheckBox[mainList.size()];
		this.setLayout(new GridLayout(mainList.size(),1,0,0));
		if(mainList.size()>1){
		for (int i = 0; i < mainList.size(); i++) {			
			
			//to each checkbox the name of the main business is added
			checkBox[i] = new JCheckBox(mainList.get(i));
			this.add(checkBox[i]);
			
		}
		//item listener for an array of check boxes are created
		for(int i =1;i<mainList.size();i++){
			checkBox[i].addItemListener(new ItemListener(){

				@Override
				public void itemStateChanged(ItemEvent e) {
					JCheckBox cbox = (JCheckBox) e.getSource();
					//when a check box is selected, the selected item is added to another list - mainBusnList
					if(cbox.isSelected()){
						cbox.getText();
						mainBusnList.add(cbox.getText());
						YelpGUI.subBusnPanel.fetchSubCategories(mainBusnList);
					}
					//when a check box is unselected, the selected item is removed from mainBusnList
					if(!cbox.isSelected()){
						mainBusnList.remove(cbox.getText());
						YelpGUI.subBusnPanel.fetchSubCategories(mainBusnList);
					}
				}
				
			});
			
		}
		}
		this.repaint();
		this.validate();
		this.updateUI();
		
		
	}

}
