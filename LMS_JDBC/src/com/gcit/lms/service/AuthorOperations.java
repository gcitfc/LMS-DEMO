package com.gcit.lms.service;

import java.sql.Connection;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import com.gcit.lms.dao.AuthorDAO;
import com.gcit.lms.entity.Author;

public class AuthorOperations {
	
	ConnectionUtil connUtil = new ConnectionUtil();

	public AuthorOperations() {
		// TODO Auto-generated constructor stub
	}
	
	public void readAuthorByName(int flag, Scanner sc) throws SQLException {
		Connection conn = null;
		try {
			int index = 0;
			conn = connUtil.getConnection();
			
			System.out.println("Type the author's first/last name: ");
			String name = sc.nextLine();
			AuthorDAO adao = new AuthorDAO(conn);
			List<Author> authors = adao.readAllAuthorsByName(name);
			
			index = 1;
			for(Author author: authors) {
				System.out.println("" + index + ". " + author.getAuthorName());
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
	
	public void addOrUpdateAuthor(int flag, Scanner sc) throws SQLException {
		Connection conn = null;
		try {
			int index = 0;
			int c = 0;
			conn = connUtil.getConnection();
			Author a = new Author();
			if(flag == 1) {
				// Add author
				System.out.println("Author name: ");
				String name = sc.nextLine();
				a.setAuthorName(name);
				
				AuthorDAO adao = new AuthorDAO(conn);
				adao.saveAuthor(a);
			}
			else {
				// Update author
				System.out.println("Choose an author to update: ");
				AuthorDAO adao = new AuthorDAO(conn);
				List<Author> authors = adao.readAllAuthors();
				index = 1;
				for(Author author: authors) {
					System.out.println("" + index + ". " + author.getAuthorName());
					index++;
				}
				c = Integer.parseInt(sc.nextLine());
				a.setAuthorId(authors.get(c-1).getAuthorId());
				
				System.out.println("Type a new author name: ");
				String name = sc.nextLine();
				a.setAuthorName(name);
				
				adao.updateAuthor(a);
			}
			conn.commit();
			
			System.out.println("Author Added/Updated!");
			System.out.println("\nPress [Enter] to continue...");
			sc.nextLine();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			conn.rollback();
		} finally{
			conn.close();
		}
	}
	
	public void deleteAuthor(int flag, Scanner sc) throws SQLException {
		Connection conn = null;
		try {
			int index = 0;
			int c = 0;
			conn = connUtil.getConnection();
			Author a = new Author();
			System.out.println("Choose an author to delete: ");
			AuthorDAO adao = new AuthorDAO(conn);
			List<Author> authors = adao.readAllAuthors();
			index = 1;
			for(Author author: authors) {
				System.out.println("" + index + ". " + author.getAuthorName());
				index++;
			}
			c = Integer.parseInt(sc.nextLine());
			a.setAuthorId(authors.get(c-1).getAuthorId());
			
			//deleteBookOnOnlyAuthor(a, conn);
			
			adao.deleteAuthor(a);
			
			conn.commit();
			
			System.out.println("Author deleted!");
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
