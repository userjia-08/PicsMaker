package com.example.picsmaker.dao;

import java.sql.*;
import java.util.*;

class SQLiteJDBC {
	 public static Connection c = null;
	 public static Statement stmt = null;
	 public static String table_name = "ID_tag";
	//连接数据库
	 public static Connection connectSQLiteDB()
	  {
	    try {
	      Class.forName("org.sqlite.JDBC");
	      c = DriverManager.getConnection("jdbc:sqlite:"+table_name+".db");
	      //System.out.println("Opened database successfully");
	      return c;
	    } catch ( Exception e ) {
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      System.exit(0);
	    }
	    
		return null;
	  }
	 
	 //在数据库中创建表
	  public static void createTable()
	  {
		    try {
		    	c = connectSQLiteDB();
		       stmt = c.createStatement();
		       
		     //创建图片表
		      String sql = "CREATE TABLE IMAGE " +
		                   "(Image_id INT(8) PRIMARY KEY AUTO_INCREMENT, " +	//卡号，4位，不重复
		                   " Image_path TEXT NOT NULL) "; 
		      stmt.executeUpdate(sql);
		      
		      //创建标签表
		      sql = "CREATE TABLE TAG " +
	                "(Tag_id   INT(8) PRIMARY KEY, " +	//标签Id
	                " Tag_name  TEXT NOT NULL) ";
	                       
		      stmt.executeUpdate(sql);    
		      
		      //创建图片-标签关系表
		      sql = "CREATE TABLE RELATIONSHIP " +
	                "(Image_id  TEXT NOT NULL , " +	//图片 绝对路径
	                " Tag_id  INT NOT NULL , "+	//标签
	                "PRIMARY KEY(Image_id, Tag_id) )";	//作联合主键
	                       
		      stmt.executeUpdate(sql);
		      
		      stmt.close();
		      c.close();
		    } catch ( Exception e ) {
		      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		      //System.exit(0);
		    }
		   // System.out.println("Table created successfully");
	  }
	 
	  //插入数据（新建账户/管理员）
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
	  
	  //更新数据库（存款，取款--修改余额，修改密码）
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
	  ///将结果集ResultSet转换成list
	  public static List<Map<String, Object>> convertList(ResultSet rs) throws SQLException {
	        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
	        ResultSetMetaData md = rs.getMetaData();
	        int columnCount = md.getColumnCount();
	        while (rs.next()) {
	            Map<String, Object> rowData = new HashMap<String, Object>();
	            for (int i = 1; i <= columnCount; i++) {
	                rowData.put(md.getColumnName(i), rs.getObject(i));
	            }
	            list.add(rowData);
	        }
	        return list;
	}
	  
	  //查询操作（查余额，登录，改密码时，查所有账户，转账）
	  public static List<Map<String,Object>> select(String sql)
	  {
	    try {
	      c = connectSQLiteDB();
	      c.setAutoCommit(false);
	     // System.out.println("Opened database successfully");
	      stmt = c.createStatement();
	      ResultSet rs = stmt.executeQuery(sql);
	      List<Map<String, Object>> resultlist = convertList(rs);
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
