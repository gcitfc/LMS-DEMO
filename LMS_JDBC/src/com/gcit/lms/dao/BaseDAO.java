package com.gcit.lms.dao;

import java.sql.Connection;
//import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

//import com.mysql.cj.api.jdbc.Statement;
import java.sql.Statement;

public abstract class BaseDAO<T> {

//	public final String url = "jdbc:mysql://localhost:3306/library?useSSL=false";
//	public final String username = "root";
//	public final String password = "00000000";
//	public final String driver = "com.mysql.cj.jdbc.Driver";
	
	public static Connection conn = null;
	
	public BaseDAO(Connection conn){
		this.conn = conn;
	}	
	public void save(String sql, Object[] vals) throws ClassNotFoundException, SQLException{
		PreparedStatement pstmt = conn.prepareStatement(sql);
		int index = 1;
		for(Object o: vals){
			pstmt.setObject(index, o);
			index++;
		}
		pstmt.executeUpdate();
	}
	
	public Integer saveWithID(String sql, Object[] vals) throws ClassNotFoundException, SQLException{
		PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		int index = 1;
		for(Object o: vals){
			pstmt.setObject(index, o);
			index++;
		}
		pstmt.executeUpdate();
		ResultSet rs = pstmt.getGeneratedKeys();
		while(rs.next()){
			return rs.getInt(1);
		}
		return null;
	}
	
	public List<T> read(String sql, Object[] vals) throws ClassNotFoundException, SQLException{
		PreparedStatement pstmt = conn.prepareStatement(sql);
		int index = 1;
		if(vals!=null) {
			for(Object o: vals){
				pstmt.setObject(index, o);
				index++;
			}
		}
		ResultSet rs = pstmt.executeQuery();
		return extractData(rs);
	}
	
	public abstract List<T> extractData(ResultSet rs) throws SQLException;

}
