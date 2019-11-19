package com.gcit.lms.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.gcit.lms.dao.BookLoansDAO;
import com.gcit.lms.dao.BorrowerDAO;
import com.gcit.lms.dao.BranchDAO;
import com.gcit.lms.entity.BookLoans;
import com.gcit.lms.entity.Borrower;
import com.gcit.lms.entity.Branch;

public class OverrideOperations {
	
	ConnectionUtil connUtil = new ConnectionUtil();

	public OverrideOperations() {
		// TODO Auto-generated constructor stub
	}
	
	public void override(Scanner sc) throws SQLException {
		Connection conn = null;
		try {
			int index = 0;
			int c = 0;
			conn = connUtil.getConnection();
			Borrower borr = new Borrower();
			Branch bran = new Branch();
			
			System.out.println("Choose a Card Owner: ");
			BorrowerDAO bdao = new BorrowerDAO(conn);
			List<Borrower> borrowers = bdao.readAllBorrowers();
			index = 1;
			for(Borrower borrower: borrowers) {
				System.out.println("" + index + ". " + borrower.getName());
				index++;
			}
			c = Integer.parseInt(sc.nextLine());
			borr = borrowers.get(c-1);
			
			System.out.println("Choose a Branch: ");
			
			BranchDAO brdao = new BranchDAO(conn);
			List<Branch> branches = brdao.readAllBranches();
			index = 1;
			for(Branch Branch: branches) {
				System.out.println("" + index + ". " + Branch.getBranchName());
				index++;
			}
			c = Integer.parseInt(sc.nextLine());
			bran = branches.get(c-1);
			
			
			BookLoansDAO bldao = new BookLoansDAO(conn);
			List<BookLoans> loans = new ArrayList<>();
			loans = bldao.readLoansByBB(borr, bran);
			System.out.println("\n" + loans.size() + " records found:\n");
			index = 1;
			for(BookLoans bl: loans) {
				System.out.println("" + index + ". " + bl.getBook().getTitle() + "\t" + bl.getDateOut() + "\t" + bl.getDueDate() + "\n");
				index++;
			}
			
			System.out.println("Choose a record to override:");
			c = Integer.parseInt(sc.nextLine());
			BookLoans currbl = loans.get(c-1);
			System.out.println("Type the new due date: (yyyy-mm-dd)");
			
			String newdate = sc.nextLine();
			currbl.setDueDate(newdate);
			
			bldao.updateLoanDue(currbl);
			
			conn.commit();
						
			System.out.println("\nDue Date Updated!");
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
