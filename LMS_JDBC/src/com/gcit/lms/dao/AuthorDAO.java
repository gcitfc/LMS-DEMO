package com.gcit.lms.dao;

import java.sql.Connection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.gcit.lms.entity.Author;
import com.gcit.lms.entity.Book;

/**
 * @author ppradhan
 *
 */
public class AuthorDAO extends BaseDAO<Author> {

	public AuthorDAO(Connection conn) {
		super(conn);
	}

	public void saveAuthor(Author author) throws ClassNotFoundException, SQLException {
		save("INSERT INTO tbl_author (authorName) values (?)", new Object[] { author.getAuthorName() });
	}

	public void updateAuthor(Author author) throws SQLException, ClassNotFoundException {
		save("UPDATE tbl_author SET authorName = ? where authorId = ?",
				new Object[] { author.getAuthorName(), author.getAuthorId() });
	}

	public void deleteAuthor(Author author) throws ClassNotFoundException, SQLException {
		save("delete from tbl_author where authorId = ?", new Object[] { author.getAuthorId() });
	}

	public List<Author> readAllAuthors() throws ClassNotFoundException, SQLException {
		return read("SELECT * FROM tbl_author", null);
	}
	
	public List<Author> readAllAuthorsByName(String authorName) throws ClassNotFoundException, SQLException {
		authorName = "%"+authorName+"%";
		return read("SELECT * FROM tbl_author where authorName like ?", new Object[]{authorName});
	}
	
	public List<Author> readAuthorsByBook(Book book) throws ClassNotFoundException, SQLException {
		return read("SELECT a.authorId, a.authorName FROM tbl_book b INNER JOIN tbl_book_authors ba ON  b.bookId=ba.bookId INNER JOIN tbl_author a ON ba.authorId=a.authorId WHERE b.bookId=?", new Object[]{book.getBookId()});
	}

	@Override
	public List<Author> extractData(ResultSet rs) throws SQLException {
		List<Author> authors = new ArrayList<>();
		while (rs.next()) {
			Author a = new Author();
			a.setAuthorId(rs.getInt("authorId"));
			a.setAuthorName(rs.getString("authorName"));
			authors.add(a);
		}
		return authors;
	}

}
