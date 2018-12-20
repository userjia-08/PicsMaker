
package com.example.picsmaker.dao;

import java.sql.*;
import java.util.*;

class SQLiteJDBC {
	 public static Connection c = null;
	 public static Statement stmt = null;
	//�������ݿ�
	 public static Connection connectSQLiteDB()
	  {
	    try {
	      Class.forName("org.sqlite.JDBC");
	      c = DriverManager.getConnection("jdbc:sqlite:picsmaker.db");
	      //System.out.println("Opened database successfully");
	      return c;
	    } catch ( Exception e ) {
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      System.exit(0);
	    }
	    
		return null;
	  }
	 
	 //�����ݿ��д�����
	  public static void createTable()
	  {
		    try {
		    	c = connectSQLiteDB();
		       stmt = c.createStatement();
		       
		     //����ͼƬ��
		      String sql = "CREATE TABLE IF NOT EXISTS PICTURE" +
		                   "(Picture_id INTEGER PRIMARY KEY, " +	//ͼƬid
		                   " Picture_path TEXT UNIQUE ) "; 
		      
		      stmt.executeUpdate(sql);
		      
		      //������ǩ��
		      sql = "CREATE TABLE IF NOT EXISTS TAG" +
	                "(Tag_id INTEGER PRIMARY KEY, " +	//��ǩId
	                " Tag_name TEXT UNIQUE ) ";
	                       
		      stmt.executeUpdate(sql);    
		      
		      //����ͼƬ-��ǩ��ϵ��
		      sql = "CREATE TABLE IF NOT EXISTS RELATIONSHIP" +
	                "(Picture_id  INT NOT NULL , " +	//ͼƬID
	                " Tag_id  INT NOT NULL , "+	//��ǩID
	                "PRIMARY KEY(Picture_id, Tag_id) )";	//����������
	                       
		      stmt.executeUpdate(sql);
			 /*     
			  //�����ǩ��:ϵͳ�̶��ı�ǩ
			  String sql2 = "insert into TAG(Tag_name)VALUES('People')";
			  stmt.executeUpdate(sql2);
			  sql2 = "insert into TAG(Tag_name)VALUES('Animals')";
			  stmt.executeUpdate(sql2);
			  sql2 = "insert into TAG(Tag_name)VALUES('Not Recognize')";
			  stmt.executeUpdate(sql2);
			  */
		      stmt.close();
		      c.close();
		    } catch ( Exception e ) {
		      //System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		      //System.exit(0);
		    }
		   // System.out.println("Table created successfully");
	  }
	 
	  //��������
	  public static boolean insertDB(String sql)
	  {
	    try {
	      c = connectSQLiteDB();
	      c.setAutoCommit(false);
	    //  System.out.println("Opened database successfully");
	      stmt = c.createStatement();
	      stmt.executeUpdate(sql);
	      stmt.close();
	      c.commit();
	      c.close();
	      return true;
	    } catch ( Exception e ) {
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	    //  System.exit(0);
	      return false;
	    }
	  //  System.out.println("Records created successfully");
	  }
	  
	  //�������ݿ�
	  public static boolean updateDB(String sql)
	  {
	    try {
	      c = connectSQLiteDB();
	      c.setAutoCommit(false);
	    //  System.out.println("Opened database successfully");
	      stmt = c.createStatement();
	      stmt.executeUpdate(sql);
	      c.commit();
	      stmt.close();
	      c.close();
	      return true;
	    } catch ( Exception e ) {
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	     // System.exit(0);
	      return false;
	    }
	   // System.out.println("Operation done successfully");
	  }
	  ///�������ResultSetת����list��ÿ������������ֵ��ɵ�hashmap
	  @SuppressWarnings("unchecked")
	public static List<Map<Object, Object>> convertList(ResultSet rs) throws SQLException {
	        List<Map<Object, Object>> list = new ArrayList<Map<Object, Object>>();
	        ResultSetMetaData md = rs.getMetaData();
	        //����
	        int columnCount = md.getColumnCount();
	        while (rs.next()) {
	            Map rowData = new HashMap();
	            for (int i = 1; i <= columnCount; i++) {
	                rowData.put(md.getColumnName(i), rs.getObject(i));
	            	//list.add((String) rs.getObject(i));
	            }
	            list.add(rowData);
	        }
	        return list;
	}
	  
	  //��ѯ����
	  public static List<Map<Object, Object>> select(String sql)
	  {
	    try {
	      c = connectSQLiteDB();
	      c.setAutoCommit(false);
	     // System.out.println("Opened database successfully");
	      stmt = c.createStatement();
	      ResultSet rs = stmt.executeQuery(sql);
	      List<Map<Object, Object>> resultlist = convertList(rs);
	      rs.close();
	      stmt.close();
	      c.close();
	      return resultlist;
	    } catch ( Exception e ) {
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      System.exit(0);
	    }
	    //System.out.println("Operation done successfully");
		return null;
	  }

	  
}
