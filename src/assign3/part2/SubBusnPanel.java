package assign3.part2;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.LineBorder;

import assign3.part1.ConnectDB;

public class SubBusnPanel extends JPanel {
	
	public int red,blue,green;
	public Color yelpRed;
	public ArrayList<String> resultList;
	
	public MainBusnPanel mainBusnPanel;
	public ConnectDB conn;
	public Connection connection;
	public StringBuilder str;
	public List<JCheckBox> listOfcheckBoxes = new ArrayList<JCheckBox>();
	public List<JLabel> listOfLables = new ArrayList<JLabel>();
	public static ArrayList<String> subList = new ArrayList<String>();
	public JCheckBox[] checkBox;
	public int i;
	public GridBagConstraints textFieldConstraints;
	public GridBagConstraints labelConstraints;
	
	
	public SubBusnPanel(){
		this.setOpaque(true);
		this.setAlignmentX(LEFT_ALIGNMENT);
		this.setBorder(BorderFactory.createLineBorder(Color.blue));
		this.setLayout(new GridLayout(0,1,0,0));
		
		red = 196;
		green = 18;
		blue = 0;
		yelpRed = new Color(red, green, blue);
		this.setBorder(BorderFactory.createStrokeBorder(new BasicStroke(0.75f),
				yelpRed));
		this.setBackground(Color.WHITE);

	}
	
	public void fetchSubCategories(ArrayList<String> selectedList){
	
		if(selectedList!=null){
			String selectQuery = "select distinct sub_category from business_categories where ";
			String whereClause = "main_category=";
			String condition = "or ";
			String query = selectQuery + whereClause;
			
			for(int i =0;i<selectedList.size();i++){
				 if(i==selectedList.size()-1){
					 query = query +"'"+selectedList.get(i)+"'";
				 }else{
					 query = query + "'" + selectedList.get(i) + "'" + condition + whereClause;
				 }
			}
		
			try {
				if(!selectedList.isEmpty()){
				runQuery(query);
			}
			else{
				this.removeAll();
				this.repaint();
				this.revalidate();
			}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			this.removeAll();
			
		}
		//subList.addAll(selectedList);
	}
	public void runQuery(String query) throws SQLException{
	
			resultList = new ArrayList<String>();
			ConnectDB conn = new ConnectDB();
			connection = conn.openConnection();
			Statement stmt = connection.createStatement(); 
			ResultSet result = stmt.executeQuery(query); 
				
			ResultSetMetaData meta = result.getMetaData();
			int tupleCount = 1;
			while (result.next()) {
				
				for (int col = 1; col <= meta.getColumnCount(); col++) {
					
					resultList.add(result.getString(col));
					
				}
				
			}
			conn.closeConnection();
		
			displaysubCategories(resultList);			
		
	}

	public void displaysubCategories(ArrayList<String> resResult){
		
		this.removeAll();
		
		checkBox = new JCheckBox[resResult.size()];
		this.setLayout(new GridLayout(resResult.size(),1,0,0));
		if(resResult.size()>1){
		for (int i = 0; i < resResult.size(); i++) {			
			
			
			checkBox[i] = new JCheckBox(resResult.get(i));
			this.add(checkBox[i]);
		
			
		}
		for(int i =1;i<resResult.size();i++){
			checkBox[i].addItemListener(new ItemListener(){

				@Override
				public void itemStateChanged(ItemEvent e) {
					JCheckBox cbox = (JCheckBox) e.getSource();
					if(cbox.isSelected()){
						cbox.getText();
						
						subList.add(cbox.getText());
						YelpGUI.busnAttrPanel.fetchAttributes(subList);
						
					}if(!cbox.isSelected()){
						subList.remove(cbox.getText());
						YelpGUI.busnAttrPanel.fetchAttributes(subList);
						
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
