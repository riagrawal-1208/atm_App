package com.atm.connect;

import java.sql.Connection; 
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException; 
import java.sql.Statement;

public class connection {
	 
	   // JDBC driver name and database URL 
	   static final String JDBC_DRIVER = "org.h2.Driver";   
	   static final String DB_URL = "jdbc:h2:~/test";  
	   
	   //  Database credentials 
	   static final String USER = "sa"; 
	   static final String PASS = "sa"; 
	   Connection conn = null; 
	      Statement stmt = null;
	      ResultSet rs = null;
	  
	   public  ResultSet executeQuery(String query) { 
	      Connection conn = null; 
	      Statement stmt = null;
	      ResultSet rs = null;
	      try { 
	         // STEP 1: Register JDBC driver 
	         Class.forName(JDBC_DRIVER); 
	             
	         //STEP 2: Open a connection 
	         System.out.println("Connecting to database..."); 
	         conn = DriverManager.getConnection(DB_URL,USER,PASS);  
	         
	         //STEP 3: Execute a query 
	         stmt = conn.createStatement(); 
	           
	          rs =stmt.executeQuery(query);
	         System.out.println("Created table in given database..."); 
	          
	      } catch(SQLException se) { 
	         //Handle errors for JDBC 
	         se.printStackTrace(); 
	      } catch(Exception e) { 
	         //Handle errors for Class.forName 
	         e.printStackTrace(); 
	      } 
	      return rs;
	   } 
	   public  int executeUpdate(String query) { 
		      Connection conn = null; 
		      Statement stmt = null;
		      int rows=0;
		      try { 
		         // STEP 1: Register JDBC driver 
		         Class.forName(JDBC_DRIVER); 
		             
		         //STEP 2: Open a connection 
		         System.out.println("Connecting to database..."); 
		         conn = DriverManager.getConnection(DB_URL,USER,PASS);  
		         
		         //STEP 3: Execute a query 
		         stmt = conn.createStatement(); 
		           
		           rows =stmt.executeUpdate(query);
		         System.out.println("Created table in given database..."); 
		         
		      } catch(SQLException se) { 
		         //Handle errors for JDBC 
		         se.printStackTrace(); 
		      } catch(Exception e) { 
		         //Handle errors for Class.forName 
		         e.printStackTrace(); 
		      } //end try 
		      return rows;
		   }
	   public  ResultSet createAccount(String query) { 
		      Connection conn = null; 
		      PreparedStatement statement = null;
		      int rows=0;
		      try { 
		         Class.forName(JDBC_DRIVER); 
		         System.out.println("Connecting to database..."); 
		         conn = DriverManager.getConnection(DB_URL,USER,PASS);  
		          statement = conn.prepareStatement(query,
                         Statement.RETURN_GENERATED_KEYS);
		           
		           rows =statement.executeUpdate();
		           if(rows==0)
		           {
		        	   return rs=null;
		           }else{
		        	   ResultSet rs = statement.getGeneratedKeys();
		           }
		         System.out.println("Created table in given database..."); 
		      } catch(SQLException se) { 
		         //Handle errors for JDBC 
		         se.printStackTrace(); 
		      } catch(Exception e) { 
		         //Handle errors for Class.forName 
		         e.printStackTrace(); 
		      } //end try 
		      return rs;
		   }
	   public void closeConnection(ResultSet rs){
		   try { 
	            if(rs!=null) rs.close(); 
	         } catch(SQLException se){ 
	            se.printStackTrace(); 
	         } 
		   try{ 
	            if(stmt!=null) stmt.close(); 
	         } catch(SQLException se2) { 
	         } // nothing we can do 
	         try { 
	            if(conn!=null) conn.close(); 
	         } catch(SQLException se){ 
	            se.printStackTrace(); 
	         } //en
	   }
}
