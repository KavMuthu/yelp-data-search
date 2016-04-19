package assign3.part2;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JPanel;

import assign3.part1.ConnectDB;

public class BusnAttrPanel extends JPanel {
	public int red,blue,green;
	public Color yelpRed;
	public ArrayList<String> resultList;
	public ConnectDB conn;
	public Connection connection;
	public JCheckBox[] checkBox;
	public ArrayList<String> attrList = new ArrayList<String>();
	public ArrayList<String> subList = new ArrayList<String>();

	public BusnAttrPanel(){
		this.setOpaque(true);
		this.setAlignmentX(LEFT_ALIGNMENT);
		//this.setBorder(BorderFactory.createLineBorder(Color.blue));
		red = 196;
		green = 18;
		blue = 0;
		yelpRed = new Color(red, green, blue);
		//this.setBorder(BorderFactory.createLineBorder(yelpRed));
		this.setBorder(BorderFactory.createStrokeBorder(new BasicStroke(0.75f),
				yelpRed));
		this.setBackground(Color.WHITE);
	}
	public void fetchAttributes(ArrayList<String> selectedCatgList){
		
		if(selectedCatgList!=null){
			String selectQuery = "select distinct busn_attributes from business_attributes ba, business_categories yb where yb.business_id = ba.business_id and ";
			String whereClause = "yb.sub_category=";
			String condition = "or ";
			String query = selectQuery + whereClause;
			
			for(int i =0;i<selectedCatgList.size();i++){
				 if(i==selectedCatgList.size()-1){
					 query = query +"'"+selectedCatgList.get(i)+"'";
				 }else{
					 query = query + "'" + selectedCatgList.get(i) + "'" + condition + whereClause;
				 }
			}
			

			try {
				if(!selectedCatgList.isEmpty()){
				runAttrQuery(query);
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
		subList.addAll(selectedCatgList);
	}
	
	public void runAttrQuery(String query) throws SQLException{
		
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
	
		displayAttributes(resultList);	
		
	}
	public void displayAttributes(ArrayList<String> resultList){
	
		this.removeAll();
		
		checkBox = new JCheckBox[resultList.size()];
		this.setLayout(new GridLayout(resultList.size(),1,0,0));
		if(resultList.size()>1){
		for (int i = 0; i < resultList.size(); i++) {			
		
			
			checkBox[i] = new JCheckBox(resultList.get(i));
			this.add(checkBox[i]);
		
			
		}
		for(int i =1;i<resultList.size();i++){
			checkBox[i].addItemListener(new ItemListener(){

				@Override
				public void itemStateChanged(ItemEvent e) {
					JCheckBox cbox = (JCheckBox) e.getSource();
					if(cbox.isSelected()){
						cbox.getText();
						
						attrList.add(cbox.getText());
						YelpGUI.searchBusnPanel.fetchValsForBusnList(MainBusnPanel.mainBusnList,SubBusnPanel.subList,attrList);
						
						
					}if(!cbox.isSelected()){
						attrList.remove(cbox.getText());
						
						YelpGUI.searchBusnPanel.fetchValsForBusnList(MainBusnPanel.mainBusnList,SubBusnPanel.subList,attrList);
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

