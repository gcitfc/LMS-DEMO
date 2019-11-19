package com.gcit.lms.service;

import java.sql.Connection;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import com.gcit.lms.dao.BookCopiesDAO;
import com.gcit.lms.dao.BookDAO;
import com.gcit.lms.dao.BookLoansDAO;
import com.gcit.lms.dao.BorrowerDAO;
import com.gcit.lms.dao.BranchDAO;
import com.gcit.lms.entity.Book;
import com.gcit.lms.entity.BookCopies;
import com.gcit.lms.entity.BookLoans;
import com.gcit.lms.entity.Branch;
import com.gcit.lms.entity.Borrower;

public class BranchOperations {
	
	ConnectionUtil connUtil = new ConnectionUtil();

	public BranchOperations() {
		// TODO Auto-generated constructor stub
	}
	
	
	
	public List<Branch> readAllBranch() throws SQLException {
		Connection conn = null;
		try {
			int index = 0;
			conn = connUtil.getConnection();
			BranchDAO bdao = new BranchDAO(conn);
			List<Branch> branches = bdao.readAllBranches();
			
			index = 1;
			for(Branch branch: branches) {
				System.out.println("" + index + ". " + branch.getBranchName());
				index++;
			}
			
			return branches;
			
			// conn.commit();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			conn.rollback();
		} finally{
			conn.close();
		}
		return null;
	}
	
	public void addOrUpdateBranch(int flag, Scanner sc) throws SQLException {
		Connection conn = null;
		try {
			int index = 0;
			int c = 0;
			conn = connUtil.getConnection();
			Branch b = new Branch();
			if(flag == 1) {
				// Add Branch
				System.out.println("Branch name: ");
				String name = sc.nextLine();
				b.setBranchName(name);
			}
			else {
				// Update Branch
				System.out.println("Choose a Branch to update: ");
				BranchDAO bdao = new BranchDAO(conn);
				List<Branch> branches = bdao.readAllBranches();
				index = 1;
				for(Branch branch: branches) {
					System.out.println("" + index + ". " + branch.getBranchName());
					index++;
				}
				c = Integer.parseInt(sc.nextLine());
				b.setBranchId(branches.get(c-1).getBranchId());
				
				System.out.println("Type a new Branch name: ");
				String name = sc.nextLine();
				b.setBranchName(name);
				
			}
			
			// Address
			System.out.println("Branch address: ");
			String address = sc.nextLine();
			b.setAddress(address);
			
			BranchDAO bdao = new BranchDAO(conn);
			if(flag == 1)
				bdao.saveBranch(b);
			else
				bdao.updateBranch(b);
			
			conn.commit();
			
			System.out.println("Branch Added/Updated!");
			
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			conn.rollback();
		} finally{
			conn.close();
		}
	}
	
	public void deleteBranch(int flag, Scanner sc) throws SQLException {
		Connection conn = null;
		try {
			int index = 0;
			int c = 0;
			conn = connUtil.getConnection();
			Branch b = new Branch();
			System.out.println("Choose an Branch to delete: ");
			BranchDAO adao = new BranchDAO(conn);
			List<Branch> branches = adao.readAllBranches();
			index = 1;
			for(Branch branch: branches) {
				System.out.println("" + index + ". " + branch.getBranchName());
				index++;
			}
			c = Integer.parseInt(sc.nextLine());
			b.setBranchId(branches.get(c-1).getBranchId());
			
			adao.deleteBranch(b);
			
			conn.commit();
			
			System.out.println("Branch deleted!");
			
			
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			conn.rollback();
		} finally{
			conn.close();
		}
	}
	
	public void updateBranch(Branch branch, Scanner sc) throws SQLException {
		Connection conn = null;
		try {
			conn = connUtil.getConnection();
			
			System.out.println("Please enter new branch name or enter N/A for no change: ");
			String name = sc.nextLine();
			if("quit".equals(name))
				return;
			if(!"N/A".equals(name))
				branch.setBranchName(name);

			System.out.println("Please enter new branch address or enter N/A for no change: ");
			String address = sc.nextLine();
			if("quit".equals(address))
				return;
			if(!"N/A".equals(address))
				branch.setAddress(address);
					
			BranchDAO bdao = new BranchDAO(conn);
			bdao.updateBranch(branch);
			
			conn.commit();
			
			System.out.println("Branch Updated!");
			
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			conn.rollback();
		} finally{
			conn.close();
		}
	}
	
	
	public void addCopies(Branch branch, Scanner sc) throws SQLException {
		Connection conn = null;
		try {
			conn = connUtil.getConnection();
			System.out.println("Pick the Book you want to add copies of, to your branch:");
			
			BookDAO bdao = new BookDAO(conn);
			Book thisBook = new Book();
			List<Book> books = bdao.readAllBooks();
			int index = 1;
			for(Book book : books) {
				System.out.println("" + index + ". " + book.getTitle());
				index++;
			}
			System.out.println("" + index + ". Quit to cancel operation");
			int c = Integer.parseInt(sc.nextLine());
			if(c == index)
				return;
			
			thisBook = books.get(c-1);
			
			
			BookCopiesDAO bcdao = new BookCopiesDAO(conn);
			BookCopies newCopy = null;
			List<BookCopies> copies = bcdao.readCopiesByBranch(branch);
			index = 1;
			int found = 0;
			for(BookCopies bc: copies) {
				if(bc.getBook().getBookId() == thisBook.getBookId()) {
					found = 1;
					newCopy = bc;
					break;
				}
				index++;
			}
			
			if(found == 0) {
				newCopy = new BookCopies();
				newCopy.setBook(thisBook);
				newCopy.setBranch(branch);
				newCopy.setNoOfCopies(0);
			}
			
			System.out.println("Existing number of copies: " + newCopy.getNoOfCopies());
			System.out.println("Enter new number of copies: ");
			c = Integer.parseInt(sc.nextLine());
			newCopy.setNoOfCopies(c);
			
			if(found == 1)
				bcdao.updateBookCopies(newCopy);
			else if (found == 0 ) 
				bcdao.saveBookCopies(newCopy);
			
			conn.commit();
			
			System.out.println("Number Of Copies Updated!");
			
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			conn.rollback();
		} finally{
			conn.close();
		}
	}
	
	public int checkOutBookFromBranch(Branch branch, Borrower borr, Scanner sc) throws SQLException {
		Connection conn = null;
		try {
			conn = connUtil.getConnection();
//			BookDAO bdao = new BookDAO(conn);
//			Book thisBook = new Book();
//			List<Book> books = bdao.readAllBooks();
			System.out.println("Pick a Book you want to check out:");
			int index = 1;
//			for(Book book : books) {
//				System.out.println("" + index + ". " + book.getTitle());
//				index++;
//			}
//			System.out.println("" + index + ". Quit to cancel operation");
//			int c = Integer.parseInt(sc.nextLine());
//			if(c == index)
//				return;
//			
//			thisBook = books.get(c-1);
			
			BookCopiesDAO bcdao = new BookCopiesDAO(conn);
			List<BookCopies> copies = bcdao.readCopiesByBranch(branch);
			index = 1;
			for(BookCopies bc: copies) {
				System.out.println("" + index + ". " + bc.getBook().getTitle());
				index++;
			}
			System.out.println("" + index + ". Quit to cancel operation");
			int c = Integer.parseInt(sc.nextLine());
			if(c == index)
				return 1;
			BookCopies bc = copies.get(c-1);
			BookLoans loan = new BookLoans();
			loan.setBook(bc.getBook());
			loan.setBranch(branch);
			loan.setBorrower(borr);
			
			BookLoansDAO bldao = new BookLoansDAO(conn);
			bldao.saveLoan(loan);
			
			bcdao.decBookCopiesBy1(bc);
			
			conn.commit();
			
			System.out.println("Succesfully checked out!\n\nLoan Information Updated! Number Of Copies Updated!\n");
			
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			conn.rollback();
		} finally{
			conn.close();
		}
		return 1;
	}
	
	public int returnBookToBranch(Branch branch, Borrower borr, Scanner sc) throws SQLException {
		Connection conn = null;
		try {
			conn = connUtil.getConnection();
			
			int index = 1;
			
			BorrowerDAO bdao = new BorrowerDAO(conn);
			borr.setBooks(bdao.getLoansAtBranch(borr, branch.getBranchId()).getBooks());
			List<Book> books = borr.getBooks();
			
			System.out.println("" + books.size() + " records found\nPick a Book you want to return:");
			index = 1;
			for(Book b: books) {
				System.out.println("" + index + ". " + b.getTitle());
				index++;
			}
			System.out.println("" + index + ". Quit to cancel operation");
			int c = Integer.parseInt(sc.nextLine());
			if(c == index)
				return 1;
			Book book2Return = books.get(c-1);
			
			BookLoans loan = new BookLoans();
			loan.setBook(book2Return);
			loan.setBranch(branch);
			loan.setBorrower(borr);
			
			BookLoansDAO bldao = new BookLoansDAO(conn);
			bldao.updateReturnDate(loan);
			
			BookCopiesDAO bcdao = new BookCopiesDAO(conn);
			BookCopies bc = new BookCopies();
			bc.setBook(book2Return);
			bc.setBranch(branch);
			bcdao.incBookCopiesBy1(bc);
			
			conn.commit();
			
			System.out.println("Succesfully returned!\n\n! Number Of Copies Updated!\n");
			
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			conn.rollback();
		} finally{
			conn.close();
		}
		return 1;
	}

}
