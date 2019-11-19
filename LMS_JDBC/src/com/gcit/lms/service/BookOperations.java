package com.gcit.lms.service;

import java.sql.Connection;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.gcit.lms.dao.AuthorDAO;
import com.gcit.lms.dao.BookAuthorDAO;
import com.gcit.lms.dao.BookDAO;
import com.gcit.lms.dao.BookGenreDAO;
import com.gcit.lms.dao.GenreDAO;
import com.gcit.lms.dao.PubDAO;
import com.gcit.lms.entity.Author;
import com.gcit.lms.entity.Book;
import com.gcit.lms.entity.Genre;
import com.gcit.lms.entity.Publisher;

public class BookOperations {
	
	ConnectionUtil connUtil = new ConnectionUtil();
	
	public BookOperations() {
		
	}
	

	public void readBookByName(int flag, Scanner sc) throws SQLException {
		Connection conn = null;
		try {
			int index = 0;
			conn = connUtil.getConnection();
			
			System.out.println("Type the keyword in the title: ");
			BookDAO bdao = new BookDAO(conn);
			String title = sc.nextLine();
			List<Book> books = bdao.readAllBooksByName(title);
			index = 1;
			if(books.size() == 0) {
					System.out.println("No such book!");
					return;
			}
			for(Book book : books) {
				System.out.println("" + index + ". " + book.getTitle());
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
	
	public void deleteBook(int flag, Scanner sc) throws SQLException {
		Connection conn = null;
		try {
			int index = 0;
			int c = 0;
			conn = connUtil.getConnection();
			Book b = new Book();
			
			System.out.println("Choose a book to delete: ");
			BookDAO bdao = new BookDAO(conn);
			List<Book> books = bdao.readAllBooks();
			index = 1;
			for(Book book : books) {
				System.out.println("" + index + ". " + book.getTitle());
				index++;
			}
			c = Integer.parseInt(sc.nextLine());
			b.setBookId(books.get(c-1).getBookId());
			
			bdao.deleteBook(b);
			
			conn.commit();
			System.out.println("Book deleted!");
			System.out.println("\nPress [Enter] to continue...");
			sc.nextLine();
			
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			conn.rollback();
		} finally{
			conn.close();
		}
	}
	
	
	public void addOrUpdateBook(int flag, Scanner sc) throws SQLException {
		Connection conn = null;
		try {
			int index = 0;
			char yon = 'y';
			int c = 0;
			conn = connUtil.getConnection();
			Book b = new Book();
			if(flag == 1) {
				System.out.println("Book title: ");
				String title = sc.nextLine();
				b.setTitle(title);
			}
			else {
				System.out.println("Choose a book to update: ");
				BookDAO bdao = new BookDAO(conn);
				List<Book> books = bdao.readAllBooks();
				index = 1;
				for(Book book : books) {
					System.out.println("" + index + ". " + book.getTitle());
					index++;
				}
				c = Integer.parseInt(sc.nextLine());
				b.setBookId(books.get(c-1).getBookId());
				b.setTitle(books.get(c-1).getTitle());
				
				System.out.println("Need a new title? (y/n)");
				yon = sc.nextLine().charAt(0);
				
				if(yon == 'y') {
					System.out.println("New book title: ");
					String title = sc.nextLine();
					b.setTitle(title);
				}
			}
			
			// Set Publisher
			PubDAO pdao = new PubDAO(conn);
			List<Publisher> pubs = pdao.readAllPublishers();
			System.out.println("Choose a publisher:");
			index = 1;
			for(Publisher p : pubs) {
				System.out.println("" + index + ". " + p.getPubName());
				index++;
			}
			c = Integer.parseInt(sc.nextLine());
			
			b.setPublisher(pubs.get(c-1));
			
			// Set Authors
			yon = 'y';
			AuthorDAO adao = new AuthorDAO(conn);
			List<Author> authors = adao.readAllAuthors();
			List<Author> tmpAuthors = new ArrayList<>();
			while(yon == 'y') {
				System.out.println("Choose an author: ");
				index = 1;
				for(Author a : authors) {
					System.out.println("" + index + ". " + a.getAuthorName());
					index++;
				}
				c = Integer.parseInt(sc.nextLine());
				tmpAuthors.add(authors.get(c-1));
				
				System.out.println("Add another author? (y/n)");
				yon = sc.nextLine().charAt(0);
			}

			b.setAuthors(tmpAuthors);
			
			// Set Genre
			yon = 'y';
			GenreDAO gdao = new GenreDAO(conn);
			List<Genre> genres = gdao.readAllGenre();
			List<Genre> tmpGenres = new ArrayList<>();
			while(yon == 'y') {
				System.out.println("Choose a genre: ");
				index = 1;
				for(Genre a : genres) {
					System.out.println("" + index + ". " + a.getGenreName());
					index++;
				}
				c = Integer.parseInt(sc.nextLine());
				tmpGenres.add(genres.get(c-1));
				
				System.out.println("Add another genre? (y/n)");
				yon = sc.nextLine().charAt(0);
			}

			b.setGenres(tmpGenres);
			
			// Save/Update Book
			BookDAO bdao = new BookDAO(conn);
			if(flag == 1) {
				// Save Book
				int bookId = bdao.saveBookWithID(b);
				b.setBookId(bookId);
				for(Author a : b.getAuthors()) {
					BookAuthorDAO badao = new BookAuthorDAO(conn);
					badao.saveBookAuthor(a, b);
				}
				for(Genre g : b.getGenres()) {
					BookGenreDAO bgdao = new BookGenreDAO(conn);
					bgdao.saveBookGenre(g, b);
				}
			}
			else {
				// Update Book
				bdao.updateBook(b);
				BookAuthorDAO badao = new BookAuthorDAO(conn);
				badao.deleteBookAuthor(b);
				for(Author a : b.getAuthors()) {
					badao.saveBookAuthor(a, b);
				}
				BookGenreDAO bgdao = new BookGenreDAO(conn);
				bgdao.deleteBookGenre(b);
				for(Genre g : b.getGenres()) {
					bgdao.saveBookGenre(g, b);
				}
			}
			conn.commit();
			System.out.println("Book Added/Updated!");
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
