/**
 * 
 */
package com.gcit.lms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.gcit.lms.entity.Author;
import com.gcit.lms.entity.Book;

/**
 * @book ppradhan
 *
 */
public class BookDAO extends BaseDAO<Book>{
	
	public BookDAO(Connection conn) {
		super(conn);
	}

	public void saveBook(Book book) throws ClassNotFoundException, SQLException {
		save("INSERT INTO tbl_book (title, pubId) values (?, ?)", new Object[] { book.getTitle(), book.getPublisher().getPubId() });
	}
	
	public Integer saveBookWithID(Book book) throws ClassNotFoundException, SQLException {
		return saveWithID("INSERT INTO tbl_book (title, pubId) values (?, ?)", new Object[] { book.getTitle(),  book.getPublisher().getPubId()});
	}

	public void updateBook(Book book) throws SQLException, ClassNotFoundException {
		save("UPDATE tbl_book SET title = ? where bookId = ?",
				new Object[] { book.getTitle(), book.getBookId() });
	}
	
	public void updateBookPublisher(Book book) throws SQLException, ClassNotFoundException {
		save("UPDATE tbl_book SET pubId = ? where bookId = ?",
				new Object[] { book.getPublisher().getPubId(), book.getBookId() });
	}

	public void deleteBook(Book book) throws ClassNotFoundException, SQLException {
		save("delete from tbl_book where bookId = ?", new Object[] { book.getBookId() });
	}

	public List<Book> readAllBooks() throws ClassNotFoundException, SQLException {
		return read("SELECT * FROM tbl_book", null);
	}
	
	public List<Book> readAllBooksByName(String title) throws ClassNotFoundException, SQLException {
		title = "%"+title+"%";
		return read("SELECT * FROM tbl_book where title like ?", new Object[]{title});
	}
	
	public List<Book> readBooksByAuthor(Author author) throws ClassNotFoundException, SQLException {
		return read("SELECT b.bookId, title FROM tbl_book b INNER JOIN tbl_book_authors ba ON  b.bookId=ba.bookId INNER JOIN tbl_author a ON ba.authorId=a.authorId WHERE a.authorId=?", new Object[]{author.getAuthorId()});
	}

	@Override
	public List<Book> extractData(ResultSet rs) throws SQLException {
		List<Book> books = new ArrayList<>();
		while (rs.next()) {
			Book b = new Book();
			b.setBookId(rs.getInt("bookId"));
			b.setTitle(rs.getString("title"));
			books.add(b);
		}
		return books;
	}

}
