package assign3.part2;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.io.BufferedReader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import assign3.part1.ConnectDB;

public class ReviewListPanel extends JPanel {

	public ConnectDB conn;
	public Connection connection;
	public JTable table;
	public int red, green, blue;
	public Color yelpRed;
	public static DefaultTableModel model;

	public ReviewListPanel() {

		this.setLayout(new BorderLayout());
		this.setBackground(Color.WHITE);
		Dimension size = new Dimension(1000, 700);
		this.setMaximumSize(size);
		this.setPreferredSize(size);
		this.setMinimumSize(size);
		red = 196;
		green = 18;
		blue = 0;
		yelpRed = new Color(red, green, blue);
	}

	public void fectchBusinessID(String busnName) {

		
		if (busnName.contains("'")) {
			busnName = busnName.replace("'", "%");
		}
		if(busnName.contains("&")){
			busnName = busnName.replace("&", "%");
		}
		String selectQuery = "select TO_CHAR(r.rev_date,'mm/dd/yyyy') as Review_Date,r.stars as Stars,r.rev_text as Review ,u.user_name as UserName ,r.votes_use as Useful_Votes from yelp_review r,yelp_business b,yelp_user u where b.business_id = r.business_id and r.user_id = u.user_id and b.busn_name LIKE "
				+ "'" + "%"  + busnName +  "%" + "'" ;
		

		try {
			this.removeAll();
			runQuery(selectQuery);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void runQuery(String selectQuery) throws SQLException {

		ConnectDB conn = new ConnectDB();
		connection = conn.openConnection();
		Statement stmt = connection.createStatement();
		ResultSet result = stmt.executeQuery(selectQuery);

		try {
			table = new JTable(buildTableModel(result));
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		TableColumn column = null;
		for (int i = 0; i < 5; i++) {
			column = table.getColumnModel().getColumn(i);
			if (i == 0) 
				column.setMaxWidth(100);
            if (i == 1)
            	column.setMaxWidth(50);
            if(i==2)
            	column.setMaxWidth(660);
            if(i==3)
            	column.setMaxWidth(90);
            if(i==4)
            	column.setMaxWidth(100);
			// column.setPreferredWidth(50);
		}
		// table.setModel(DbUtils.resultSetToTableModel(result));
		table.setBorder(BorderFactory.createLineBorder(yelpRed));
		table.setGridColor(yelpRed);
		JScrollPane tableSP = new JScrollPane(table);
		tableSP.setPreferredSize(new Dimension(1000, 700));

		this.add(tableSP);

		this.repaint();
		this.validate();
		this.updateUI();

		conn.closeConnection();

	}

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
				StringBuffer strOut = new StringBuffer();
				String aux;
				try {
					BufferedReader br = new BufferedReader(result.getClob("Review").getCharacterStream());
					while ((aux = br.readLine()) != null) {
						strOut.append(aux);
						strOut.append(System.getProperty("line.separator"));
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				String clobStr = strOut.toString();
		
			
				vector.add(result.getObject(1));
				vector.add(result.getObject(2));
				vector.add(clobStr);
				vector.add(result.getObject(4));
				vector.add(result.getObject(5));
				
			}
			data.add(vector);

		}
		model = new DefaultTableModel(data, columnNames);
		return model;
	}

}
