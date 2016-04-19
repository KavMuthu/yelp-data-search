package assign3.part1;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import oracle.jdbc.OraclePreparedStatement;

public class Populate {
	
	public Connection con;
	public Statement stmt;
	public PreparedStatement ps;
	public String user_id, yelping_since,user_name,open_hours,close_hours;
	public int votes_use, votes_fun, votes_cool, review_count;
	public int fans_count;
	public double avg_stars;
	public String obj_type, business_id, full_address, mon_Bopen, mon_Bclose, tues_Bopen, tues_Bclose, wed_Bopen, wed_Bclose, thrus_Bopen,
	thrus_Bclose, fri_Bopen, fri_Bclose, sat_Bopen, sat_Bclose, sun_Bopen, sun_Bclose, city, busn_state,busn_name,busn_type;
	public double latitude, longitude,stars;
	public int busnReview_count;	
	public JSONObject hoursObject, monHoursObject, tuesHoursObject, wedHoursObject, thursHoursObject, friHoursObject,
	satHoursObject, sunHoursObject;
	public String attrBusnId, userBusnId, attr_key;
	public Object value, inValue, attr_val;
	public String[] friendsArray, valuesArray;
	public JSONObject valObject, innerValue,innerValObject;
	public int i, j;
	public JSONObject jsonobject;
	public String key, innerKey, appendedKey;
	public HashMap<String, Object> outer_map;
	public String catgBusn_id;
	public JSONArray bcatjsonArray;
	public int k, l;
	public Set<String> main_busn,list;
	public ArrayList<String> subCategory_list,mainCategory_list;
	public int revVotes_use, revVotes_fun, revVotes_cool,revStars;
	public String revUser_id, review_id, revDate, revText,revType, revBusiness_id;
	//public  ConnectDB connect = new ConnectDB();
	public String daysBusiness_id;
	public JSONObject daysObject;
	
	
	public static void main(String[] args) throws SQLException {
		Populate dataSet = new Populate();
		dataSet.openConnection();
		
//		dataSet.deleteBusinessDaysTuples();
//		dataSet.deleteCategoriesTuples();
//		dataSet.deleteAttributesTuples();
//		dataSet.deleteReviewTuples();
//		dataSet.deleteUserTuples();
//		dataSet.deleteBusinessTuples();
//				
//		dataSet.parseYelpUser(args[2]);
//		dataSet.parseYelpBusiness(args[0]);
//		dataSet.parseYelpReview(args[1]);
//		dataSet.parseBusnAttributes(args[0]);
//		dataSet.parseBusnCategories(args[0]);
//		dataSet.parseBusinessDays(args[0]);
//		dataSet.closeConnection();
		
	}
	
	public void openConnection() throws SQLException{
		
		  // Load the Oracle database driver 
        DriverManager.registerDriver(new oracle.jdbc.OracleDriver()); 
        //Establish the connection using the connection string
        con= DriverManager.getConnection("jdbc:oracle:thin:@//localhost:1521/orcl","system","oracle" );
        //System.out.println("Connected to the database");
	}
	public void deleteUserTuples() throws SQLException{
			 try{
			  	stmt = con.createStatement(); 
	         	System.out.println("Deleting previous tuples from yelp_user ..."); 
			         
	         	stmt.executeUpdate("delete from yelp_user"); 
		        System.out.println("Values deleted from yelp_user table");       

			} catch (SQLException e) {
				e.printStackTrace();
			} 
			//System.out.println("old values deleted "); 	
	}
	
	public void parseYelpUser(String yelpUserPath){
		System.out.println("User path: " + yelpUserPath);
		try{
		InputStream inputStream = new FileInputStream(yelpUserPath);
		InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
		BufferedReader br = new BufferedReader(inputStreamReader);

		String line="";
		
		while((line = br.readLine())!=null){          
			          

		JSONTokener jsonParser = new JSONTokener(line);  
		JSONObject  jsonobject=new JSONObject(jsonParser);      
			
		 	user_id = jsonobject.getString("user_id");
	       // System.out.println("user_id: "+ user_id);
	        yelping_since = jsonobject.getString("yelping_since");
	        //System.out.println("yelping_since: " + yelping_since);
	       
	        JSONObject VotejsonObject = jsonobject.getJSONObject("votes");  
		 	votes_use = VotejsonObject.getInt("useful");
		  	//System.out.println("useful: " + votes_use); 
		  	votes_fun = VotejsonObject.getInt("funny");
		 	//System.out.println("funny: "+ votes_fun); 
		 	votes_cool = VotejsonObject.getInt("cool");
	        //System.out.println("cool: "+ votes_cool);   
	       
	        review_count = jsonobject.getInt("review_count");
	        //System.out.println("review_count: "+ review_count );
	        user_name = jsonobject.getString("name");
	        //System.out.println("name: " + user_name);
	        fans_count = jsonobject.getInt("fans");
	        //System.out.println("fans_count: " + fans_count);
	        avg_stars = jsonobject.getDouble("average_stars");
	        //System.out.println("avg_stars: " + avg_stars);
	        obj_type = jsonobject.getString("type");
	        
	        loadYelpUser(user_id,yelping_since,votes_use,votes_fun,votes_cool,review_count,user_name,fans_count,avg_stars,obj_type);
		}
	}catch (FileNotFoundException ex) {
		ex.printStackTrace();
	} catch (IOException ex) {
		ex.printStackTrace();
	} catch (NullPointerException ex) {
		ex.printStackTrace();
	} catch (JSONException e) {
		e.printStackTrace();
	} 
		catch (SQLException e) {
		e.printStackTrace();
	}
		
	}
	 public void loadYelpUser(String user_id,String yelping_since,int votes_use,int votes_fun,int votes_cool,int review_count,String user_name,int fans_count,double avg_stars,String obj_type) throws SQLException { 
         try{
       	  stmt = con.createStatement(); 
         System.out.println("Inserting Data ..."); 
  
         ps = con.prepareStatement("INSERT INTO yelp_user VALUES(?,?,?,?,?,?,?,?,?,?)"); 
         ps.setString(1, user_id);       
         ps.setString(2, yelping_since);
         ps.setInt(3, votes_use);
         ps.setInt(4, votes_fun);
         ps.setInt(5, votes_cool);
         ps.setInt(6, review_count);
         ps.setString(7, user_name);
         ps.setInt(8, fans_count);
         ps.setDouble(9, avg_stars);
         ps.setString(10, obj_type);
         ps.addBatch();
			
         ps.executeBatch();
         con.commit();
   
         }catch (SQLException e) {
				e.printStackTrace();
			} finally{
			    ps.close();
			}

     } 
	public void deleteBusinessTuples() throws SQLException{
		 try{
			  	stmt = con.createStatement(); 
	         	System.out.println("Deleting previous tuples from yelp_business ..."); 
			         
	         	stmt.executeUpdate("delete from yelp_business"); 
		        System.out.println("Values deleted from yelp_business table");       

			} catch (SQLException e) {
				e.printStackTrace();
			} 
			//System.out.println("old values deleted "); 	
	} 
	public void parseYelpBusiness(String yelp_businessPath){
		//System.out.println("Busn Path: "+ yelp_businessPath);
		try {

			InputStream inputStream = new FileInputStream(yelp_businessPath);
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
			BufferedReader br = new BufferedReader(inputStreamReader);
			String line = "";

			while ((line = br.readLine()) != null) {

				JSONTokener jsonParser = new JSONTokener(line);
				JSONObject jsonobject = new JSONObject(jsonParser);

				business_id = jsonobject.getString("business_id");
//				System.out.println("business_id: " + business_id);
				full_address = jsonobject.getString("full_address");
//				System.out.println("full_add: " + full_address);
				city = jsonobject.getString("city");
//				System.out.println("city: " + city);
				busn_state = jsonobject.getString("state");
//				System.out.println("busn_state: " + busn_state);
				latitude = jsonobject.getDouble("latitude");
//				System.out.println("latitude: " + latitude);
				longitude = jsonobject.getDouble("longitude");
//				System.out.println("longitude: " + longitude);
				busnReview_count = jsonobject.getInt("review_count");
//				System.out.println("busnReview_count: " + busnReview_count);
				busn_name = jsonobject.getString("name");
//				System.out.println("busn_name: " + busn_name);
				stars = jsonobject.getDouble("stars");
//				System.out.println("stars: " + stars);
				busn_type = jsonobject.getString("type");
//				System.out.println("busn_type: " + busn_type);
//				System.out.println(
//						"--------------------------------------------------------------------------------------------------------------");

				loadYelpBusiness(business_id, full_address,city, busn_state, latitude, longitude, busnReview_count, busn_name, stars, busn_type);
			}
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		} catch (NullPointerException ex) {
			ex.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}

		
	}
	public void loadYelpBusiness(String business_id, String full_address, String city, String busn_state, double latitude, double longitude,
			int busnReview_count, String busn_name, double stars, String busn_type) throws SQLException {
		try {
			stmt = con.createStatement();

			System.out.println("Inserting Data ...");
			PreparedStatement ps = con.prepareStatement(
					"INSERT INTO yelp_business VALUES(?,?,?,?,?,?,?,?,?,?)");
			ps.setString(1, business_id);
			ps.setString(2, full_address);
			ps.setString(3, city);
			ps.setString(4, busn_state);
			ps.setDouble(5, latitude);
			ps.setDouble(6, longitude);
			ps.setInt(7, review_count);
			ps.setString(8, busn_name);
			ps.setDouble(9, stars);
			ps.setString(10, busn_type);
			ps.executeUpdate();
			con.commit();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public void deleteReviewTuples() throws SQLException{
			stmt = con.createStatement();
			System.out.println("Deleting values from yelp_review...");
	      	stmt.executeUpdate("delete from yelp_review");
	        System.out.println("Values deleted from yelp_review table");
	}
	public void parseYelpReview(String reviewPath) throws SQLException{
		//System.out.println("Review Path: " +reviewPath);
		try{
			
			InputStream inputStream = new FileInputStream(reviewPath);
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
			BufferedReader br = new BufferedReader(inputStreamReader);
			String line="";
			
			while((line = br.readLine())!=null){          
				          
			JSONTokener jsonParser = new JSONTokener(line);  
			JSONObject  jsonobject=new JSONObject(jsonParser);      

			JSONObject VotejsonObject = jsonobject.getJSONObject("votes"); 

			 	revVotes_use = VotejsonObject.getInt("useful");
			  	//System.out.println("revVotes_use: " + revVotes_use); 
			  	revVotes_use = VotejsonObject.getInt("funny");
			 	//System.out.println("revVotes_fun: "+ revVotes_fun); 
			 	revVotes_cool = VotejsonObject.getInt("cool");
		        //System.out.println("revVotes_cool: "+ revVotes_cool);   
		        revUser_id = jsonobject.getString("user_id");
		        //System.out.println("revUser_id: "+ revUser_id);
		        review_id = jsonobject.getString("review_id");
		        //System.out.println("review_id: "+review_id);
		        revStars = jsonobject.getInt("stars");
		        //System.out.println("revStars: "+revStars);
		        revDate = jsonobject.getString("date");
		        //System.out.println("revDate: "+ revDate);
		        revText = jsonobject.getString("text");
		        //System.out.println("revText: "+revText);
		        revType = jsonobject.getString("type");
		        //System.out.println("revType: "+revType);
		        revBusiness_id = jsonobject.getString("business_id");
		        //System.out.println("revBusiness_id: "+ revBusiness_id);
		       // System.out.println("--------------------------------------------------------------------------------------------------------------");
	        
		        loadYelpReview(revVotes_use,revVotes_use,revVotes_cool,revUser_id,review_id,revStars,revDate,revText,revType,revBusiness_id);
			}
		}catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		} catch (NullPointerException ex) {
			ex.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		} 	
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		
	}
	  public void loadYelpReview(int revVotes_use,int revVotes_fun,int revVotes_cool,String revUser_id,String review_id,int revStars,String revDate,String revText,String revType,String revBusiness_id) throws SQLException { 
          try{
   
          System.out.println("Inserting Data ..."); 
          ps = con.prepareStatement("INSERT INTO yelp_review VALUES(?,?,?,?,?,?,?,?,?,?)"); 
          ((OraclePreparedStatement)ps).setExecuteBatch(70);
          
          ps.setInt(1, revVotes_use); 
          ps.setInt(2, revVotes_fun);
          ps.setInt(3, revVotes_cool);
          ps.setString(4, revUser_id);
          ps.setString(5, review_id);
          ps.setInt(6, revStars);
          ps.setDate(7, java.sql.Date.valueOf(revDate));
          ps.setString(8, revText);
          ps.setString(9, revType);
          ps.setString(10, revBusiness_id);
          ps.executeUpdate();
         
          ((OraclePreparedStatement)ps).sendBatch();
          
          con.commit();
        // System.out.println("data inserted");
          }catch (SQLException e) {
				e.printStackTrace();
			} 
          finally {
		        ps.close(); 
		    }
      } 
	  public void deleteAttributesTuples() throws SQLException{
		  	stmt = con.createStatement();
			System.out.println("Deleting values from business_attributes...");
	        stmt.executeUpdate("delete from business_attributes");
	        System.out.println("Values deleted from busn_attr table");
	  }
	public void parseBusnAttributes(String attrPath){
		//System.out.println("Attr PAth: " + attrPath);
		try {

			InputStream inputStream = new FileInputStream(attrPath);
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
			BufferedReader br = new BufferedReader(inputStreamReader);
			String line = "";
		
			while ((line = br.readLine()) != null) {
		
				JSONTokener jsonParser = new JSONTokener(line);
				jsonobject = new JSONObject(jsonParser);

				//System.out.println();
				business_id = jsonobject.getString("business_id");
				//System.out.println("business_id: " + business_id);

				JSONObject attrJsonObject = (JSONObject) jsonobject.get("attributes");
				//System.out.println("ATTRIBUTES: " + attrJsonObject);

				outer_map = new HashMap<String, Object>();

				Iterator<String> iter = attrJsonObject.keys();
				while (iter.hasNext()) {
					key = (String) iter.next();
					value = attrJsonObject.get(key);

					if (value instanceof JSONObject) {
						valObject = (JSONObject) value;
						Iterator<String> it = valObject.keys();
						while (it.hasNext()) {
							innerKey = (String) it.next();
						
								appendedKey = key + "-" + innerKey;
								outer_map.put(appendedKey, (Object) valObject.get(innerKey));
								loadBusnAttributes(business_id, appendedKey, (Object) valObject.get(innerKey));

						}
					} else {
						if (!key.isEmpty() && value != null) {

							outer_map.put(key, value);
							loadBusnAttributes(business_id,key,value);

						}
					}
				}

				outer_map.clear();

			}
			
		//	System.out.println(
		//			"------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	public void loadBusnAttributes(String attrBusnId, String attr_key, Object attr_value) throws SQLException {
		try {
			stmt = con.createStatement();
			ps = con.prepareStatement("INSERT INTO business_attributes VALUES(?,?)");
			((OraclePreparedStatement)ps).setExecuteBatch(70);
	          
			ps.setString(1, attrBusnId);
			ps.setString(2, attr_key + "_" + attr_value);
			ps.executeUpdate();
			
			((OraclePreparedStatement)ps).sendBatch();
			con.commit();
			
		} catch (SQLException ex) {
			ex.printStackTrace();
		}finally{
			ps.close();
		}

	}
	private void addMainBusiness(){
		main_busn = new HashSet<String>();
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

	}
	public void deleteCategoriesTuples() throws SQLException{
		
			stmt = con.createStatement();
			System.out.println("Deleting values from yelp_categories...");
			stmt.executeUpdate("delete from business_categories");
	        System.out.println("Values deleted from yelp_busnCategories table");
	}
	public void parseBusnCategories(String catgPath){
		//System.out.println("Catg path: " + catgPath);
		try {

			InputStream inputStream = new FileInputStream("res/yelp_business.json");
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
			BufferedReader br = new BufferedReader(inputStreamReader);
			String line = "";

			mainCategory_list = new ArrayList<String>();
			subCategory_list = new ArrayList<String>();
			addMainBusiness();
			list = new HashSet<String>(); 

			while ((line = br.readLine()) != null) {

				JSONTokener jsonParser = new JSONTokener(line);
				jsonobject = new JSONObject(jsonParser);

				catgBusn_id = jsonobject.getString("business_id");
				//System.out.println("catgBusn_id: " + catgBusn_id);

				bcatjsonArray = jsonobject.getJSONArray("categories");
			
				list.clear();     
	
				if (bcatjsonArray != null) { 
					int len = bcatjsonArray.length();
					for (int i=0;i<len;i++){ 
						list.add(bcatjsonArray.get(i).toString());
					} 
				} 

				subCategory_list.clear();
				mainCategory_list.clear();

				Iterator<String> it = list.iterator();
				while(it.hasNext()){
					String main_busn_it = it.next();
					if(main_busn.contains(main_busn_it)){     
						mainCategory_list.add(main_busn_it);

					}else{
						subCategory_list.add(main_busn_it);

					}
				}


				loadBusnCategories(catgBusn_id);
			}
			//System.out.println("Main busn: " + mainCategory_list);
			//System.out.println("Sub busn: " + subCategory_list);
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		} catch (NullPointerException ex) {
			ex.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	public void loadBusnCategories(String catgBusn_id) throws SQLException {
		try {
			stmt = con.createStatement();

			System.out.println("Inserting Data ...");

			ps = con.prepareStatement("INSERT INTO business_categories VALUES(?,?,?)"); 

			for (i = 0; i < mainCategory_list.size(); i++) {
				ps.setString(1, catgBusn_id);
				ps.setString(2, mainCategory_list.get(i));
				for(j=0;j<subCategory_list.size();j++){
					ps.setString(3, subCategory_list.get(j));
					ps.executeUpdate();
				}
			}	
			ps.close();
			con.commit();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	public void deleteBusinessDaysTuples() {
		try {
			stmt = con.createStatement();
			System.out.println("Deleting previous tuples ...");
			stmt.executeUpdate("delete from business_days");

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//System.out.println("old values deleted ");

	}
	public void parseBusinessDays(String daysPath) {
		//System.out.println("Days Path: " + daysPath);
		try {
			InputStream inputStream = new FileInputStream(daysPath);
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
			BufferedReader br = new BufferedReader(inputStreamReader);
			String line = "";

			while ((line = br.readLine()) != null) {
				JSONTokener jsonParser = new JSONTokener(line);
				JSONObject jsonobject = new JSONObject(jsonParser);

				

				JSONObject daysObject = (JSONObject) jsonobject.get("hours");
	
				if (daysObject!=null) {
	
					String daysBusiness_id = jsonobject.getString("business_id");
					//System.out.println("business_id: " + daysBusiness_id);
					
						Iterator<String> iter = daysObject.keys();
						while (iter.hasNext()) {
							key = (String) iter.next();
							value = daysObject.get(key);
							System.out.println("KEY: " + key + " " + "VALUE: " + value);

							if (value instanceof JSONObject) {
								innerValObject = (JSONObject) value;

								String closeTime = innerValObject.getString("close");
								//System.out.println("close_time: " + closeTime);

								String openTime = innerValObject.getString("open");
								//System.out.println("open_time: " + openTime);
								
								loadBusinessDays(daysBusiness_id, key, closeTime, openTime);

						}

					}

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void loadBusinessDays(String business_id, String days, String closeTime,String openTime) {

		try {
			//stmt = con.createStatement();
			System.out.println("Inserting Data ...");

			//System.out.println("IN load----->" + "ID: " + business_id + " " +"Days: " + days + " " + "OTime: " + openTime
			//+" " + "CTime: " + closeTime);
			ps = con.prepareStatement("INSERT INTO business_days VALUES(?,?,?,?)");
			ps.setString(1, business_id);
			ps.setString(2, days);
			String open_hours = "0 " + openTime + ":00";
			ps.setString(3, open_hours);

			String close_hours = "0 " + closeTime + ":00";
			ps.setString(4, close_hours);
			ps.executeUpdate();
			//ps.addBatch();

			//ps.executeBatch();

			con.commit();

			//System.out.println("Data inserted");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				ps.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public void closeConnection(){
		 try { 
	           con.close();           
	           } catch (SQLException e) { 
	          System.err.println("Cannot close connection: " + e.getMessage()); 
	       } 
		
	}
	

}
