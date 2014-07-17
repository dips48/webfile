package com.dips.db;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.Statement;

public class DBconnect {
	private static Connection con;
	public static void init(){
	try{
		Class.forName("com.mysql.jdbc.Driver");
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("Œﬁ∑®◊∞‘ÿ«˝∂Ø");
		}
	try{
	String url="jdbc:mysql://localhost:3306/webconnect?user=root&password=csu*#netlab";
	 con = DriverManager.getConnection(url);
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("≈‰÷√≤Œ ˝¥ÌŒÛ");
		}
}	
	public static ResultSet query(String str){
		if(con==null){
			init();
		}
		try{
		Statement stat=con.createStatement();
		ResultSet rs=stat.executeQuery(str);
		return rs;}catch(Exception e){
			System.out.println("≤È—Ø”Ôæ‰¥ÌŒÛ");
			return null;
		}
	}
	public static void exec(String str){
		if(con==null){
			init();
		}
		try{
		Statement stat=con.createStatement();
		stat.execute(str);
		}catch(Exception e){
			System.out.println("≤È—Ø”Ôæ‰¥ÌŒÛ");
			e.printStackTrace();
		}
	}
	public static boolean CheckUser(String username,String password){
		String str="SELECT * FROM USER WHERE username='"+username+"' and password='"+password+"'";
		ResultSet rs=query(str);
		try{
		if(rs.next()){
			return true;
		}}catch(Exception e){
			e.printStackTrace();
		}
		return false;
		
	}
	public static boolean InsertUser(String username,String password){
		String str="SELECT * FROM USER WHERE username='"+username+"'";
		System.out.println(str);
		ResultSet rs=query(str);
		try{
		if(rs.next()){
			return false;
		}else{
			str="INSERT INTO user (`username`, `password`) VALUES ('"+username+"', '"+password+"')";
			System.out.println(str);
			DBconnect.exec(str);
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		return true;
	}
	
}
