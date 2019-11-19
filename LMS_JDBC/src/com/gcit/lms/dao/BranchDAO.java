package com.gcit.lms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.gcit.lms.entity.Branch;
import com.gcit.lms.entity.Book;

public class BranchDAO extends BaseDAO<Branch> {

	public BranchDAO(Connection conn) {
		super(conn);
		// TODO Auto-generated constructor stub
	}
	
	public void saveBranch(Branch branch) throws ClassNotFoundException, SQLException {
		save("INSERT INTO tbl_library_branch (branchName, branchAddress) values (?, ?)", new Object[] { branch.getBranchName(), branch.getAddress() });
	}
	
	public void saveBranchWithID(Branch branch) throws ClassNotFoundException, SQLException {
		saveWithID("INSERT INTO tbl_library_branch (branchName, branchAddress) values (?, ?)", new Object[] { branch.getBranchName(), branch.getAddress() });
	}
	
	public void updateBranch(Branch branch) throws SQLException, ClassNotFoundException {
		save("UPDATE tbl_library_branch SET branchName = ?, branchAddress = ? where branchId = ?",
				new Object[] { branch.getBranchName(), branch.getAddress(), branch.getBranchId() });

	}

	public void updateBranchName(Branch branch) throws SQLException, ClassNotFoundException {
		save("UPDATE tbl_library_branch SET branchName = ? where branchId = ?",
				new Object[] { branch.getBranchName(), branch.getBranchId() });

	}
	
	public void updateBranchAddress(Branch branch) throws SQLException, ClassNotFoundException {
		save("UPDATE tbl_library_branch SET branchAddress = ? where branchId = ?",
				new Object[] { branch.getAddress(), branch.getBranchId() });

	}

	public void deleteBranch(Branch branch) throws ClassNotFoundException, SQLException {
		save("delete from tbl_library_branch where branchId = ?", new Object[] { branch.getBranchId() });
	}

	public List<Branch> readAllBranches() throws ClassNotFoundException, SQLException {
		return read("SELECT * FROM tbl_library_branch", null);
	}
	
	public List<Branch> readBranchByName(String branchName) throws ClassNotFoundException, SQLException {
		return read("SELECT * FROM tbl_library_branch where branchName=?", new Object[]{branchName});
	}

	@Override
	public List<Branch> extractData(ResultSet rs) throws SQLException {
		List<Branch> branches = new ArrayList<>();
		while (rs.next()) {
			Branch b = new Branch();
			b.setBranchId(rs.getInt("branchId"));
			b.setBranchName(rs.getString("branchName"));
			b.setAddress(rs.getString("branchAddress"));
			branches.add(b);
		}
		return branches;
	}
	
	
	public void updateBookCopies(Integer branchId, Integer bookId, Integer copies) throws ClassNotFoundException, SQLException {
		save("UPDATE tbl_book_copies SET noOfCopies = ? where branchId = ? AND bookId = ?",
				new Object[] { copies, branchId, bookId });
	}
	
	
	public void addNewBook(Branch branch, Book book, Integer copies) throws ClassNotFoundException, SQLException {
		save("INSERT INTO tbl_book_copies (bookId, branchId, noOfCopies) values (?, ?, ?)", new Object[] { book.getBookId(), branch.getBranchId(), copies });
	}
	
}
