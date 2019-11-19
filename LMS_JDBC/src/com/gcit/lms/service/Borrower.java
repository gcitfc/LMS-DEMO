package com.gcit.lms.service;

import java.sql.SQLException;

import java.util.List;
import java.util.Scanner;

import com.gcit.lms.entity.Branch;

public class Borrower {
	
	ConnectionUtil connUtil = new ConnectionUtil();
	Branch branch;
	BranchOperations bo;
	com.gcit.lms.entity.Borrower borr;
	
	public Borrower() {
		
	}
	
	public void mainMenu(Scanner sc) throws SQLException {
		System.out.println("---- Borrower Menu ----\n");
		System.out.println("Enter your Card Number: ");
		int cardNo = Integer.parseInt(sc.nextLine());
		BorrowerOperations bo = new BorrowerOperations();
		boolean valid = bo.validBorrower(cardNo);
				
		while(!valid) {
			System.out.println("Invalid Card Number! Please try again...\n\nEnter your Card Number: ");
			cardNo = Integer.parseInt(sc.nextLine());
			valid = bo.validBorrower(cardNo);
		}
		
		borr = new com.gcit.lms.entity.Borrower();
		borr.setCardNo(cardNo);
		
		int flag = 1;
		while(flag == 1) {
			System.out.println("Welcome! (Card#" + cardNo + ")\n");
			System.out.println("1. Checkout a book\n2. Return a book\n3. Returns to Main Menu");
			int c = Integer.parseInt(sc.nextLine());
			if(c == 3)
				return;
			
			if(c == 1)
				flag = borrMenu21(sc);
			else if(c == 2) {
				flag = borrMenu22(sc);
			}
		}		
	}
	
	public int borrMenu21(Scanner sc) throws SQLException {
		int flag = 1;
		System.out.println("Choose a Branch you want to check out from:");
		bo = new BranchOperations();
		List<Branch> branches = bo.readAllBranch();
		System.out.println("\n" + String.valueOf(branches.size()+1) + ". Quit to previous");
		
		int c = Integer.parseInt(sc.nextLine());
		
		if(c == branches.size()+1)
			return 1;
		branch = branches.get(c-1);
		flag = bo.checkOutBookFromBranch(branch, borr, sc);
		return flag;
	}
	
	public int borrMenu22(Scanner sc) throws SQLException {
		int flag = 1;
		System.out.println("Choose a Branch you checked out from:");
		bo = new BranchOperations();
		List<Branch> branches = bo.readAllBranch();
		System.out.println("\n" + String.valueOf(branches.size()+1) + ". Quit to previous");
		
		int c = Integer.parseInt(sc.nextLine());
		
		if(c == branches.size()+1)
			return 1;
		branch = branches.get(c-1);
		flag = bo.returnBookToBranch(branch, borr, sc);
		
		return flag;
	}

}
