package assign3.part2;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;
import java.util.zip.ZipFile;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import assign3.part1.ConnectDB;

public class SearchBusnPanel extends JPanel {

	public int red, blue, green;
	public Color yelpRed;
	public ArrayList<String> MainCatg = new ArrayList<String>();
	public ArrayList<String> SubCatg = new ArrayList<String>();
	public ArrayList<String> Attr = new ArrayList<String>();
	public JPanel searchOptionsPanel;
	public Color yellow;
	public JComboBox attrComboBox;
	public ArrayList<String> resultList;
	public ConnectDB conn;
	public Connection connection;
	public JTable table;
	public String selectedCondition, cityField, stateField, zipCode;
	public JComboBox<String> daysCombo, statesCombo, citiesCombo;
	public JComboBox daysComboBox, statesComboBox, citiesComboBox;
	public String daySelected, conditionSelected, fromTime, toTime, zipField, stateSelected, citySelected, filterQuery;
	public JSpinner fromTimeSpinner;
	public JSpinner toTimeSpinner;
	public JTextField fromTimeField, toTimeField;
	public JButton searchButton, clearButton;
	public static DefaultTableModel model;
	public JTextField cityTextField, stateTextField, zipTextField;
	public static String selectedID;
	public JButton popularButton,clearAllButton;
	// public static ResultSetMetaData metaData;

	public SearchBusnPanel() {
		this.setOpaque(true);
		this.setAlignmentX(LEFT_ALIGNMENT);
		red = 196;
		green = 18;
		blue = 0;
		yelpRed = new Color(red, green, blue);
		this.setBorder(BorderFactory.createStrokeBorder(new BasicStroke(0.75f), yelpRed));
		this.setBackground(Color.WHITE);

	}

	public void busnSearchMenu() {

		searchOptionsPanel = new JPanel();
		Dimension searchMenuSize = new Dimension(1190, 70);
		searchOptionsPanel.setMaximumSize(searchMenuSize);
		searchOptionsPanel.setPreferredSize(searchMenuSize);
		searchOptionsPanel.setMinimumSize(searchMenuSize);
		searchOptionsPanel.setBackground(yellow);
		searchOptionsPanel.setForeground(yelpRed);
		// searchOptionsPanel.setBorder(BorderFactory.createLineBorder(Color.black));

		JLabel attrLabel = new JLabel();
		attrLabel.setText("Search For:");
		searchOptionsPanel.add(attrLabel);
		attrLabel.setForeground(yelpRed);

		String[] attrSearch = new String[] { " ", "No condition", "All Attributes", "Any Attributes" };
		attrComboBox = new JComboBox<>(attrSearch);
		attrComboBox.setForeground(Color.BLACK);
		searchOptionsPanel.add(attrComboBox);

		JLabel dayLabel = new JLabel();
		dayLabel.setText("Days:");
		searchOptionsPanel.add(dayLabel);
		dayLabel.setForeground(yelpRed);

		String[] days = new String[] { " ", "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday",
				"Saturday" };
		daysComboBox = new JComboBox<>(days);
		daysComboBox.setForeground(Color.BLACK);
		searchOptionsPanel.add(daysComboBox);

		JLabel fromLabel = new JLabel();
		fromLabel.setText("From:");
		searchOptionsPanel.add(fromLabel);
		fromLabel.setForeground(yelpRed);

		// fromTimeSpinner = new JSpinner( new SpinnerDateModel() );
		// JSpinner.DateEditor fromTimeEditor = new
		// JSpinner.DateEditor(fromTimeSpinner, "HH:mm:ss");
		// fromTimeSpinner.setEditor(fromTimeEditor);
		// fromTimeSpinner.setValue(new Date());
		fromTimeField = new JTextField();
		fromTimeField.setPreferredSize(new Dimension(80, 24));
		fromTimeField.setToolTipText(
				"<html><b><font color=red>" + "Please enter in the hh:mm:ss format" + "</font></b></html>");
		searchOptionsPanel.add(fromTimeField);

		JLabel toLabel = new JLabel();
		toLabel.setText("To:");
		searchOptionsPanel.add(toLabel);
		toLabel.setForeground(yelpRed);

		// toTimeSpinner = new JSpinner( new SpinnerDateModel() );
		// JSpinner.DateEditor toTimeEditor = new
		// JSpinner.DateEditor(toTimeSpinner, "HH:mm:ss");
		// toTimeSpinner.setEditor(toTimeEditor);
		// toTimeSpinner.setValue(new Date());

		toTimeField = new JTextField();
		toTimeField.setPreferredSize(new Dimension(80, 24));
		toTimeField.setToolTipText(
				"<html><b><font color=red>" + "Please enter in the hh:mm:ss format" + "</font></b></html>");
		searchOptionsPanel.add(toTimeField);

		JLabel stateLabel = new JLabel();
		stateLabel.setText("State:");
		searchOptionsPanel.add(stateLabel);
		stateLabel.setForeground(yelpRed);

		// stateTextField = new JTextField();
		// stateTextField.setPreferredSize(new Dimension(100, 24));
		// searchOptionsPanel.add(stateTextField);

		String[] states = new String[] { " ", "SCB", "MLN", "CA", "EDH", "WI", "KHL", "FIF", "ELN", "XGL", "ON", "GA",
				"NV", "AZ" };
		statesComboBox = new JComboBox<>(states);
		statesComboBox.setForeground(Color.BLACK);
		searchOptionsPanel.add(statesComboBox);

		JLabel cityLabel = new JLabel();
		cityLabel.setText("City:");
		searchOptionsPanel.add(cityLabel);
		cityLabel.setForeground(yelpRed);

		String[] cities = new String[] { " ", "Goodyear", "Central City Village", "Middleton", "Surprise",
				"Spring Valley", "Ahwatukee", "Sun Lakes", "Guadalupe", "Tortilla Flat", "Queensferry", "Phoenix",
				"Bonnyrigg", "Gilbert", "Avondale", "Monona", "Enterprise", "Stoughton", "South Queensferry",
				"Coolidge", "Clark County", "Higley", "Ratho", "Fort Mcdowell", "De Forest", "Morristown", "Las Vegas",
				"Mesa", "DeForest", "Verona", "Tonopah", "Sunrise", "Atlanta", "W Henderson", "Central Henderson",
				"Chandler", "Edinburgh", "Glendale", "Apache Junction", "Gila Bend", "Gold Canyon", "Deforest",
				"NELLIS AFB", "Tonto Basin", "N E Las Vegas", "Nellis AFB", "Youngtown", "Sedona", "North Scottsdale",
				"Cambridge", "Woolwich", "Heidelberg", "North Las Vegas", "Henderson", "Fitchburg", "Laveen",
				"Wickenburg", "Paradise Valley", "Dane", "Boulder City", "Musselburgh", "McFarland", "Summerlin",
				"Goldfield", "Mc Farland", "Stanfield", "Newberry Springs", "Maricopa", "Sun City", "Juniper Green ",
				"Kitchener", "Arcadia", "Florence", "Carefree", "El Mirage", "Waunakee", "Tolleson", "N Las Vegas",
				"Dalkeith", "Windsor", "Rio Verde", "Nellis Afb", "W Spring Valley", "St Jacobs", "Centennial Hills",
				"Waddell", "Phoenix Sky Harbor Center", "Stockbridge", "Henderson (Stephanie)", "Madison", "Scottsdale",
				"Buckeye", "Cave Creek", "San Tan Valley", "Litchfield Park", "Las Vegas", "Anthem", "Lasswade",
				"Paradise", "Green Valley", "Sun City West", "C Las Vegas", "Henderson (Green  Valley)",
				"N W Las Vegas", "Summerlin South", "Saint Jacobs", "South Gyle", "Black Canyon City", "Trempealeau",
				"Fountain Hills ", "Tempe", "Queen Creek", "Casa Grande", "Sun Prairie", "Waterloo", "Cottage Grove",
				"Fort McDowell", "Glendale Az", "Loanhead", "W Summerlin", "New River", "New Town", "Old Town",
				"N. Las Vegas", "Inverkeithing", "City of Edinburgh", "North Queensferry", "St Clements" };
		citiesComboBox = new JComboBox<>(cities);
		citiesComboBox.setForeground(Color.BLACK);
		searchOptionsPanel.add(citiesComboBox);

		// cityTextField = new JTextField();
		// cityTextField.setPreferredSize(new Dimension(100, 24));
		// searchOptionsPanel.add(cityTextField);

		JLabel zipCode = new JLabel();
		zipCode.setText("Zip:");
		zipCode.setForeground(yelpRed);
		searchOptionsPanel.add(zipCode);

		zipTextField = new JTextField();
		zipTextField.setPreferredSize(new Dimension(100, 24));
		searchOptionsPanel.add(zipTextField);

		searchButton = new JButton("Search");
		searchButton.setForeground(yelpRed);
		searchOptionsPanel.add(searchButton);

		clearButton = new JButton("Clear Table");
		clearButton.setForeground(yelpRed);
		searchOptionsPanel.add(clearButton);
		
		clearAllButton = new JButton("Reset");
		clearAllButton.setForeground(yelpRed);
		searchOptionsPanel.add(clearAllButton);
		
		JLabel timeFormat = new JLabel();
		timeFormat.setText("Please enter time in the hh:mm:ss format");
		timeFormat.setForeground(yelpRed);
		searchOptionsPanel.add(timeFormat);
		
		YelpGUI.mainPanel.add(searchOptionsPanel, BorderLayout.SOUTH);

	}

	public String getFilterQuery() {

		String filterQuery = "";

		// from time
		if (fromTimeField != null) {
			fromTime = fromTimeField.getText();

		}
		// to time
		if (toTimeField != null) {
			toTime = toTimeField.getText();
		
		}
		// zip code
		if (zipTextField != null) {
			zipCode = zipTextField.getText();
		
		}

		return filterQuery;

	}

	public void fetchValsForBusnList(ArrayList<String> mainBusn, ArrayList<String> subBusn, ArrayList<String> attr) {

		// days
		daysComboBox.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				daysCombo = (JComboBox<String>) e.getSource();
				String selectedDay = (String) daysCombo.getSelectedItem();
				daySelected = selectedDay;
			

			}

		});
		// state
		statesComboBox.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent event2) {
				statesCombo = (JComboBox<String>) event2.getSource();
				String selectedState = (String) statesCombo.getSelectedItem();
				stateSelected = selectedState;
			
			}

		});
		// city
		citiesComboBox.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent event3) {
				citiesCombo = (JComboBox<String>) event3.getSource();
				String selectedCity = (String) citiesCombo.getSelectedItem();
				citySelected = selectedCity;
		
			}

		});

		if (mainBusn != null && subBusn != null && attr != null) {
		

			attrComboBox.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent event) {
					JComboBox<String> combo = (JComboBox<String>) event.getSource();
					selectedCondition = (String) combo.getSelectedItem();
					conditionSelected = selectedCondition;

				

				}
			});

			searchButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent event) {

					if (conditionSelected == null && event.getActionCommand().equals("Search")) {

						JOptionPane.showMessageDialog(null,
								"Please select a condition in the Search For drop down menu");

					}

					
					String filterQuery = " ";
					String dayQuery= " ";
					String fromTimeQuery =" ";
					String toTimeQuery= " ";
					String stateQuery =" ";
					String cityQuery = " ";
					String zipQuery = " ";
					
					
					getFilterQuery();

					 if(daySelected!=null){
					 
					 dayQuery = " and bd.open_days= " + "'" + daySelected + "'";
					 }else{
						dayQuery=" ";
					 }
					 if(!fromTime.isEmpty()){
					 
					 fromTimeQuery = " and "+"'" + "0" +" "+ fromTime + "'" + " "  +" between bd.open_time and bd.close_time";
					 }else{
						 fromTimeQuery = " ";
					 }
			
					 if(!toTime.isEmpty()){
					
					 toTimeQuery += " and " +"'" + "0" +" "+ toTime + "'" + " " + "<=" + " "+ "bd.close_time";
					 }else{
						toTimeQuery = " "; 
					 }
					
					 if(stateSelected!=null){
					
					 stateQuery += " and b.busn_state= " + "'" +
					 stateSelected + "'";
					 }
					 else{
						 stateQuery = " ";
					 }
					 if(citySelected!=null){
					
					 cityQuery += " and b.city= " + "'" + citySelected +
					 "'";
					 }else{
						 cityQuery= " ";
					 }
					 if(!zipCode.isEmpty()){
					
					 zipQuery += " and b.full_address LIKE '%" + zipCode +
					 "%'";
					 }else{
						 zipQuery = " ";
					 }

					 filterQuery = dayQuery + fromTimeQuery + toTimeQuery + stateQuery + cityQuery + zipQuery;
					 

					

					if (conditionSelected.equals("No condition")) {
						String selectQuery = "select distinct b.busn_name,b.city,b.busn_state, b.stars from business_attributes ba, yelp_business b, business_categories bc, business_days bd where b.business_id = ba.business_id  and b.business_id = bc.business_id and b.business_id = bd.business_id and ";
						String attrValue = "ba.busn_attributes = ";
						String openBraces = "(";
						String and = "and";
						String mainBusnVal = "bc.main_category = ";
						String mainCat = "bc.main_category = ";
						String subBusnVal = "bc.sub_category = ";
						String subCat = "bc.sub_category = ";
						String orCondition = "or ";
						String closeBraces = ")";
						String query = null;

						for (int m = 0; m < mainBusn.size(); m++) {
							if (m == mainBusn.size() - 1) {
								mainBusnVal = mainBusnVal + "'" + mainBusn.get(m) + "'";
							} else {
								mainBusnVal = mainBusnVal + "'" + mainBusn.get(m) + "'" + " " + orCondition + mainCat;
							}
						}

						for (int s = 0; s < subBusn.size(); s++) {
							if (s == subBusn.size() - 1) {
								subBusnVal = subBusnVal + "'" + subBusn.get(s) + "'";
							} else {
								subBusnVal = subBusnVal + "'" + subBusn.get(s) + "'" + " " + orCondition + subCat;
							}
						}
						query = selectQuery + attrValue;

						for (int j = 0; j < attr.size(); j++) {

							if (j == attr.size() - 1) {
								query = query + "'" + attr.get(j) + "'" + " " + and + " " + openBraces + mainBusnVal
										+ closeBraces + " " + and + " " + openBraces + subBusnVal + closeBraces + " "
										+ filterQuery;
							}
						}

						
						try {
							if (mainBusn != null && subBusn != null && attr != null) {
								YelpGUI.searchBusnPanel.removeAll();
								runQuery(query);
							} else {
								YelpGUI.searchBusnPanel.removeAll();
								YelpGUI.searchBusnPanel.revalidate();
								YelpGUI.searchBusnPanel.repaint();
							}
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					if (conditionSelected.equals("Any Attributes")) {

						
						String selectQuery = "select distinct b.busn_name,b.city,b.busn_state, b.stars from business_attributes ba, yelp_business b, business_categories bc, business_days bd where b.business_id = ba.business_id  and b.business_id = bc.business_id and b.business_id = bd.business_id and ";
						String attrValue = "ba.busn_attributes = ";
						String openBraces = "(";
						String and = "and";
						String mainBusnVal = "bc.main_category = ";
						String mainCat = "bc.main_category = ";
						String subBusnVal = "bc.sub_category = ";
						String subCat = "bc.sub_category = ";
						String orCondition = "or ";
						String closeBraces = ")";
						String query1 = "";
						String query2 = "";

						for (int m = 0; m < mainBusn.size(); m++) {
							if (m == mainBusn.size() - 1) {
								mainBusnVal = mainBusnVal + "'" + mainBusn.get(m) + "'";
							} else {
								mainBusnVal = mainBusnVal + "'" + mainBusn.get(m) + "'" + " " + orCondition + mainCat;
							}
						}

						for (int s = 0; s < subBusn.size(); s++) {
							if (s == subBusn.size() - 1) {
								subBusnVal = subBusnVal + "'" + subBusn.get(s) + "'";
							} else {
								subBusnVal = subBusnVal + "'" + subBusn.get(s) + "'" + " " + orCondition + subCat;
							}
						}
						String INTERSECT = "INTERSECT ";
						query1 = selectQuery + attrValue;

						for (int j = 0; j < attr.size(); j++) {

							query2 += query1 + "'" + attr.get(j) + "'" + " " + and + " " + openBraces + mainBusnVal
									+ closeBraces + and + " " + openBraces + subBusnVal + closeBraces + " "
									+ filterQuery;

							if (j < attr.size() - 1) {
								query2 += INTERSECT;
							}

						}

						
						try {
							if (mainBusn != null && subBusn != null && attr != null) {
								YelpGUI.searchBusnPanel.removeAll();
								runQuery(query2);
							} else {
								YelpGUI.searchBusnPanel.removeAll();
								YelpGUI.searchBusnPanel.revalidate();
								YelpGUI.searchBusnPanel.repaint();
							}
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}

					if (conditionSelected.equals("All Attributes")) {

						String selectQuery = "select distinct b.busn_name,b.city,b.busn_state, b.stars from business_attributes ba, yelp_business b, business_categories bc, business_days bd where b.business_id = ba.business_id  and b.business_id = bc.business_id and b.business_id = bd.business_id and ";
						String attrValue = "ba.busn_attributes = ";
						String openBraces = "(";
						String and = "and";
						String mainBusnVal = "bc.main_category = ";
						String mainCat = "bc.main_category = ";
						String subBusnVal = "bc.sub_category = ";
						String subCat = "bc.sub_category = ";
						String orCondition = "or ";
						String closeBraces = ")";
						String mainVal = null;
						String query1 = "";
						String query2 = "";

						for (int m = 0; m < mainBusn.size(); m++) {
							if (m == mainBusn.size() - 1) {
								mainBusnVal = mainBusnVal + "'" + mainBusn.get(m) + "'";
							} else {
								mainBusnVal = mainBusnVal + "'" + mainBusn.get(m) + "'" + " " + orCondition + mainCat;
							}
						}

						for (int s = 0; s < subBusn.size(); s++) {
							if (s == subBusn.size() - 1) {
								subBusnVal = subBusnVal + "'" + subBusn.get(s) + "'";
							} else {
								subBusnVal = subBusnVal + "'" + subBusn.get(s) + "'" + " " + orCondition + subCat;
							}
						}
						String UNION = " UNION ";
						query1 = selectQuery + attrValue;

						for (int j = 0; j < attr.size(); j++) {

							query2 += query1 + "'" + attr.get(j) + "'" + " " + and + " " + openBraces + mainBusnVal
									+ closeBraces + and + " " + openBraces + subBusnVal + closeBraces + " "
									+ filterQuery;

							if (j < attr.size() - 1) {
								query2 += UNION;
							}
						}
						
						try {
							if (mainBusn != null && subBusn != null && attr != null) {
								YelpGUI.searchBusnPanel.removeAll();
								runQuery(query2);
							} else {
								YelpGUI.searchBusnPanel.removeAll();
								YelpGUI.searchBusnPanel.revalidate();
								YelpGUI.searchBusnPanel.repaint();
							}
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
				}
			});

			clearButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent event) {
					
					YelpGUI.searchBusnPanel.removeAll();
					YelpGUI.searchBusnPanel.repaint();
					YelpGUI.searchBusnPanel.revalidate();
					YelpGUI.searchBusnPanel.updateUI();
				}
			});
			
			clearAllButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent event) {
					
					attrComboBox.setSelectedItem(" ");
					daysComboBox.setSelectedItem(" ");
					fromTimeField.setText(" ");
					toTimeField.setText(" ");
					statesComboBox.setSelectedItem(" ");
					citiesComboBox.setSelectedItem(" ");
					zipTextField.setText(" ");
				}
			});
		}
	}

	public void runQuery(String query) throws SQLException {

	
		resultList = new ArrayList<String>();
		ConnectDB conn = new ConnectDB();
		connection = conn.openConnection();
		Statement stmt = connection.createStatement();
		ResultSet result = stmt.executeQuery(query);


		try {
			table = new JTable(buildTableModel(result));
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		TableColumn column = null;
		for (int i = 0; i < 4; i++) {
			column = table.getColumnModel().getColumn(i);
			
		}

		// Set styling for table
		table.setBorder(BorderFactory.createLineBorder(yelpRed));
		table.setGridColor(Color.LIGHT_GRAY);
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if (e.getValueIsAdjusting())
					return;
				int selectedRow = table.convertRowIndexToModel(table.getSelectedRow());
				int selectedColumn = table.convertColumnIndexToModel(0);

				selectedID = (String) model.getValueAt(selectedRow, selectedColumn);
				
				if (selectedID != null) {

					YelpGUI.reviewListPanel.fectchBusinessID(selectedID);

					JFrame reviewFrame = new JFrame();
					reviewFrame.add(YelpGUI.reviewListPanel);
					Dimension size = new Dimension(1000, 700);
					reviewFrame.setMaximumSize(size);
					reviewFrame.setPreferredSize(size);
					reviewFrame.setMinimumSize(size);
					reviewFrame.pack();
					reviewFrame.setLocationRelativeTo(null);
					reviewFrame.setVisible(true);
				}
			}
		});
		JScrollPane tableSP = new JScrollPane(table);
		tableSP.setPreferredSize(new Dimension(500, 675));

		this.add(tableSP);

		this.repaint();
		this.validate();
		this.updateUI();

		YelpGUI.searchBusnPanel.revalidate();
		YelpGUI.searchBusnPanel.repaint();
		
		conn.closeConnection();

	}

	/**
	 * 
	 * @param rs
	 *            ResultSet of the queried statement
	 * @return Returns table model with header set for the table and the row
	 *         data
	 * @throws SQLException
	 */
	public static DefaultTableModel buildTableModel(ResultSet result) throws SQLException {
		


		ResultSetMetaData metaData = result.getMetaData();
		


		// names of columns
		Vector<String> columnNames = new Vector<String>();
		int columnCount = metaData.getColumnCount();
		for (int column = 1; column <= columnCount; column++) {
			columnNames.add(metaData.getColumnName(column));
		}

		// data of the table
		Vector<Vector<Object>> data = new Vector<Vector<Object>>();
		while (result.next()) {
			Vector<Object> vector = new Vector<Object>();
			for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
				vector.add(result.getObject(columnIndex));
			}
			data.add(vector);

		}
		model = new DefaultTableModel(data, columnNames);
		return model;
	}

}
