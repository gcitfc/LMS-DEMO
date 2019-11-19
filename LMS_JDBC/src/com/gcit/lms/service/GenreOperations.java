/**
 * 
 */
package com.gcit.lms.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import com.gcit.lms.dao.GenreDAO;
import com.gcit.lms.entity.Genre;

/**
 * @genre square
 *
 */
public class GenreOperations {

	/**
	 * 
	 */
	
	ConnectionUtil connUtil = new ConnectionUtil();
	
	public GenreOperations() {
		// TODO Auto-generated constructor stub
	}
	
	public void readAllGenre(int flag, Scanner sc) throws SQLException {
		Connection conn = null;
		try {
			int index = 0;
			conn = connUtil.getConnection();
			GenreDAO gdao = new GenreDAO(conn);
			List<Genre> genres = gdao.readAllGenre();
			
			index = 1;
			for(Genre genre: genres) {
				System.out.println("" + index + ". " + genre.getGenreName());
				index++;
			}
			
			System.out.println("\nPress [Enter] to continue...");
			sc.nextLine();
			
			// conn.commit();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			conn.rollback();
		} finally{
			conn.close();
		}
	}
	
	public void addOrUpdateGenre(int flag, Scanner sc) throws SQLException {
		Connection conn = null;
		try {
			int index = 0;
			int c = 0;
			conn = connUtil.getConnection();
			Genre g = new Genre();
			if(flag == 1) {
				// Add genre
				System.out.println("Genre name: ");
				String name = sc.nextLine();
				g.setGenreName(name);
				
				GenreDAO gdao = new GenreDAO(conn);
				gdao.saveGenre(g);
			}
			else {
				// Update genre
				System.out.println("Choose a genre to update: ");
				GenreDAO gdao = new GenreDAO(conn);
				List<Genre> genres = gdao.readAllGenre();
				index = 1;
				for(Genre genre: genres) {
					System.out.println("" + index + ". " + genre.getGenreName());
					index++;
				}
				c = Integer.parseInt(sc.nextLine());
				g.setGenreId(genres.get(c-1).getGenreId());
				
				System.out.println("Type a new genre name: ");
				String name = sc.nextLine();
				g.setGenreName(name);
				
				gdao.updateGenre(g);
			}
			conn.commit();
			
			System.out.println("Genre Added/Updated!");
			System.out.println("\nPress [Enter] to continue...");
			sc.nextLine();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			conn.rollback();
		} finally{
			conn.close();
		}
	}
	
	public void deleteGenre(int flag, Scanner sc) throws SQLException {
		Connection conn = null;
		try {
			int index = 0;
			int c = 0;
			conn = connUtil.getConnection();
			Genre g = new Genre();
			System.out.println("Choose an genre to delete: ");
			GenreDAO adao = new GenreDAO(conn);
			List<Genre> genres = adao.readAllGenre();
			index = 1;
			for(Genre genre: genres) {
				System.out.println("" + index + ". " + genre.getGenreName());
				index++;
			}
			c = Integer.parseInt(sc.nextLine());
			g.setGenreId(genres.get(c-1).getGenreId());
			
			adao.deleteGenre(g);
			
			conn.commit();
			
			System.out.println("genre deleted!");
			System.out.println("\nPress [Enter] to continue...");
			sc.nextLine();
			
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			conn.rollback();
		} finally{
			conn.close();
		}
	}

}
