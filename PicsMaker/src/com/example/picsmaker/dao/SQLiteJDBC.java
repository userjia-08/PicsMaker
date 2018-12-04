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
		     //创建账户表
		      String sql = "CREATE TABLE ACCOUNT " +
		                   "(Acc_num   TEXT PRIMARY KEY CHECK(LENGTH(Acc_num)=4), " +	//卡号，4位，不重复
		                   " Acc_id   TEXT CHECK(LENGTH(Acc_id)>=12), " + //id长度至少为12位，唯一
		                   " Acc_name         TEXT    NOT NULL, " + 
		                   " Acc_passwd       TEXT    NOT NULL CHECK(LENGTH(Acc_passwd)>=6), " +  //密码长度大于6
		                   " Acc_deposit      REAL DEFAULT 0.00, " + 
		                   " Acc_cale         TEXT ) "; 
		      stmt.executeUpdate(sql);
		      //创建普通管理员表
		      sql = "CREATE TABLE MANAGER " +
	                "(Gene_num   TEXT PRIMARY KEY CHECK(LENGTH(Gene_num)=6), " +	//卡号，6位，不重复
	                " Gene_name  TEXT, " + 
	                " Gene_passwd    TEXT NOT NULL CHECK(LENGTH(Gene_passwd)>=6) ) ";//密码长度大于6
	                        
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
