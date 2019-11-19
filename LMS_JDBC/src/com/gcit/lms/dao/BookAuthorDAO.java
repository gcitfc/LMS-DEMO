package com.gcit.lms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.gcit.lms.entity.Author;
import com.gcit.lms.entity.Book;
import com.gcit.lms.entity.BookAuthor;

public class BookAuthorDAO extends BaseDAO<BookAuthor> {

	public BookAuthorDAO(Connection conn) {
		// TODO Auto-generated constructor stub
		super(conn);
	}
	
	public void saveBookAuthor(Author author, Book book) throws ClassNotFoundException, SQLException {
		save("INSERT INTO tbl_book_authors (authorId, bookId) values (?, ?)", new Object[] { author.getAuthorId(), book.getBookId() });
	}

	public void deleteBookAuthor(Book book) throws ClassNotFoundException, SQLException {
		save("delete from tbl_book_authors where bookId = ?", new Object[] { book.getBookId() });
	}
	
	@Override
	public List<BookAuthor> extractData(ResultSet rs) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

}
