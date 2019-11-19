package com.gcit.lms.dao;

import java.sql.Connection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.gcit.lms.entity.Genre;

public class GenreDAO extends BaseDAO<Genre> {

	public GenreDAO(Connection conn) {
		// TODO Auto-generated constructor stub
		super(conn);
	}
	
	public void saveGenre(Genre genre) throws ClassNotFoundException, SQLException {
		save("INSERT INTO tbl_genre (genre_name) values (?)", new Object[] { genre.getGenreName() });
	}

	public void updateGenre(Genre genre) throws SQLException, ClassNotFoundException {
		save("UPDATE tbl_genre SET genre_name = ? where genre_id = ?",
				new Object[] { genre.getGenreName(), genre.getGenreId() });

	}

	public void deleteGenre(Genre genre) throws ClassNotFoundException, SQLException {
		save("delete from tbl_genre where genre_id = ?", new Object[] { genre.getGenreId() });
	}

	public List<Genre> readAllGenre() throws ClassNotFoundException, SQLException {
		return read("SELECT * FROM tbl_genre", null);
	}
	
	public List<Genre> readAllGenreByName(String genreName) throws ClassNotFoundException, SQLException {
		genreName = "%"+genreName+"%";
		return read("SELECT * FROM tbl_genre where authorName like ?", new Object[]{genreName});
	}

	@Override
	public List<Genre> extractData(ResultSet rs) throws SQLException {
		List<Genre> genres = new ArrayList<>();
		while (rs.next()) {
			Genre g = new Genre();
			g.setGenreId(rs.getInt("genre_id"));
			g.setGenreName(rs.getString("genre_name"));
			genres.add(g);
		}
		return genres;
	}

}
