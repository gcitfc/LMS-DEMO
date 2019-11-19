package com.gcit.lms.dao;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.gcit.lms.entity.Book;
import com.gcit.lms.entity.Borrower;

public class BorrowerDAO extends BaseDAO<Borrower> {
	
	public BorrowerDAO(Connection conn) {
		super(conn);
	}
	
	public boolean validBorrower(Integer cardNo) throws SQLException {
		String sql = "SELECT cardNo FROM tbl_borrower WHERE cardNo=?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setObject(1, cardNo);
		ResultSet rs = pstmt.executeQuery();
		while (rs.next())
			return cardNo == rs.getInt("cardNo");
		return false;
	}
	
	public void updateBorrower(Borrower borrower) throws SQLException, ClassNotFoundException {
		save("UPDATE tbl_Borrower SET name = ?, address = ?, phone = ? where cardNo = ?",
				new Object[] { borrower.getName(), borrower.getAddress(), borrower.getPhone(), borrower.getCardNo() });
	}
	
	public void saveBorrower(Borrower borrower) throws ClassNotFoundException, SQLException {
		save("INSERT INTO tbl_Borrower (name, address, phone) values (?,?,?)", new Object[] { borrower.getName(), borrower.getAddress(), borrower.getPhone() });
	}
	
	public int saveBorrowerWithId(Borrower borrower) throws ClassNotFoundException, SQLException {
		return saveWithID("INSERT INTO tbl_Borrower (name, address, phone) values (?,?,?)", new Object[] { borrower.getName(), borrower.getAddress(), borrower.getPhone() });
	}

	public void updateBorrowerName(Borrower borrower) throws SQLException, ClassNotFoundException {
		save("UPDATE tbl_Borrower SET name = ? where cardNo = ?",
				new Object[] { borrower.getName(), borrower.getCardNo() });

	}
	
	public void updateBorrowerAddress(Borrower borrower) throws SQLException, ClassNotFoundException {
		save("UPDATE tbl_Borrower SET address = ? where cardNo = ?",
				new Object[] { borrower.getAddress(), borrower.getCardNo() });
	}
	
	public void updateBorrowerPhone(Borrower borrower) throws SQLException, ClassNotFoundException {
		save("UPDATE tbl_Borrower SET phone = ? where cardNo = ?",
				new Object[] { borrower.getPhone(), borrower.getCardNo() });
	}
		

	public void deleteBorrower(Borrower borrower) throws ClassNotFoundException, SQLException {
		save("delete from tbl_Borrower where cardNo = ?", new Object[] { borrower.getCardNo() });
	}

	public List<Borrower> readAllBorrowers() throws ClassNotFoundException, SQLException {
		return read("SELECT * FROM tbl_Borrower", null);
	}
	
	public List<Borrower> readAllBorrowersByName(String borrowerName) throws ClassNotFoundException, SQLException {
		borrowerName = "%"+borrowerName+"%";
		return read("SELECT * FROM tbl_Borrower where name like ?", new Object[]{borrowerName});
	}

	public Borrower getLoansAtBranch(Borrower b, Integer branchId) throws SQLException {
		Borrower ret = new Borrower();
		Integer cardNo = b.getCardNo();
		ret.setCardNo(cardNo);
		String sql = "SELECT b.title, b.bookId FROM tbl_book_loans bl INNER JOIN tbl_book b ON bl.bookId=b.bookId WHERE cardNo=? AND branchId=? AND dateIn IS NULL;";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setObject(1, cardNo);
		pstmt.setObject(2, branchId);
		ResultSet rs = pstmt.executeQuery();
		
		List<Book> books = new ArrayList<>();
		while (rs.next()) {
			Book book = new Book();
			book.setBookId(rs.getInt("bookId"));
			book.setTitle(rs.getString("title"));
			books.add(book);
		}
		ret.setBooks(books);
		
//		int index = 0;
//		for(Book b: ret.getBooks()) {
//			System.out.print(b.getTitle() + " ");
//			System.out.print(ret.getCopies().get(index));
//			System.out.println();
//			index++;
//		}
		
		return ret;
	}
	
	
	@Override
	public List<Borrower> extractData(ResultSet rs) throws SQLException {
		List<Borrower> borrowers = new ArrayList<>();
		while (rs.next()) {
			Borrower a = new Borrower();
			a.setCardNo(rs.getInt("cardNo"));
			a.setName(rs.getString("name"));
			a.setAddress(rs.getString("address"));
			a.setPhone(rs.getString("phone"));
			borrowers.add(a);
		}
		return borrowers;
	}
}
