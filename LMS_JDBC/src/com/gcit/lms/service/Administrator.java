package com.gcit.lms.service;

import java.sql.SQLException;

import java.util.Scanner;

public class Administrator {
	
	private final int BOOK = 1;
	private final int AUTHOR = 2;
	private final int GENRE = 3;
	private final int PUBLISHER = 4;
	private final int BRANCH = 5;
	private final int BORROWER = 6;
	private final int OVERRIDE = 7;
	
	ConnectionUtil connUtil = new ConnectionUtil();
	
	public Administrator(Scanner sc) throws SQLException  {
		System.out.println("---- Admin Menu ----\n");
		
		System.out.println("1. Add/Update/Delete/Read Book\n"
				+ "2. Add/Update/Delete/Read Author\n"
				+ "3. Add/Update/Delete/Read Genre\n"
				+ "4. Add/Update/Delete/Read Publisher\n"
				+ "5. Add/Update/Delete/Read Library Branch\n"
				+ "6. Add/Update/Delete/Read Borrowers\n"
				+ "7. Override Due Date for a Book Loan\n"
				+ "8. Quit to previous");
		
		int c = Integer.parseInt(sc.nextLine());

		switch(c) {
			case BOOK:
				bookOp(sc);
				break;
			case AUTHOR:
				authorOp(sc);
				break;
			case GENRE:
				genreOp(sc);
				break;
			case PUBLISHER:
				pubOp(sc);
				break;
			case BRANCH:
				branchOp(sc);
				break;
			case BORROWER:
				borrowerOp(sc);
				break;
			case OVERRIDE:
				overrideOp(sc);
				break;
			default:
				return;
		}
	}
	
	public void mainMenu(Scanner sc) throws SQLException {
		System.out.println("---- Admin Menu ----\n");
		
		System.out.println("1. Add/Update/Delete/Read Book\n"
				+ "2. Add/Update/Delete/Read Author\n"
				+ "3. Add/Update/Delete/Read Genre\n"
				+ "4. Add/Update/Delete/Read Publisher\n"
				+ "5. Add/Update/Delete/Read Library Branch\n"
				+ "6. Add/Update/Delete/Read Borrowers\n"
				+ "7. Override Due Date for a Book Loan\n"
				+ "8. Quit to previous");
		
		int c = Integer.parseInt(sc.nextLine());

		switch(c) {
			case BOOK:
				bookOp(sc);
				break;
			case AUTHOR:
				authorOp(sc);
				break;
			case GENRE:
				genreOp(sc);
				break;
			case PUBLISHER:
				pubOp(sc);
				break;
			case BRANCH:
				branchOp(sc);
				break;
			case BORROWER:
				borrowerOp(sc);
				break;
			case OVERRIDE:
				overrideOp(sc);
				break;
			default:
				return;
		}
	}
	
	public void overrideOp(Scanner sc) throws SQLException {
		OverrideOperations op = new OverrideOperations();
		op.override(sc);
	}
	
	public void borrowerOp(Scanner sc) throws SQLException {
		BorrowerOperations bo = new BorrowerOperations();
		System.out.println("---- Borrower Operations ----");
		
		System.out.println("1. Add\n"
				+ "2. Update\n"
				+ "3. Delete\n"
				+ "4. Search\n");
		int c = Integer.parseInt(sc.nextLine());
		
		int flag = c;
		if(flag == 1 || flag == 2) {
			// Add/Update Borrower
			bo.addOrUpdateBorrower(flag, sc);
		}
		else if(flag == 3) {
			// Delete Borrower
			bo.deleteBorrower(flag, sc);
		}
		else if(flag == 4) {
			// Read Borrower by name
			bo.readBorrowerByName(flag, sc);
		}
	}
	
	public void branchOp(Scanner sc) throws SQLException {
		BranchOperations bo = new BranchOperations();
		System.out.println("---- Branch Operations ----");
		
		System.out.println("1. Add\n"
				+ "2. Update\n"
				+ "3. Delete\n"
				+ "4. Search\n");
		int c = Integer.parseInt(sc.nextLine());

		int flag = c;
		if(flag == 1 || flag == 2) {
			// Add/Update branch
			bo.addOrUpdateBranch(flag, sc);
		}
		else if(flag == 3) {
			// Delete branch
			bo.deleteBranch(flag, sc);
		}
		else if(flag == 4) {
			// Read all branch
			bo.readAllBranch();
		}
		System.out.println("\nPress [Enter] to continue...");
		sc.nextLine();
	}
	
	
	public void genreOp(Scanner sc) throws SQLException {
		GenreOperations go = new GenreOperations();
		System.out.println("---- Genre Operations ----");
		
		System.out.println("1. Add\n"
				+ "2. Update\n"
				+ "3. Delete\n"
				+ "4. Search\n");
		int c = Integer.parseInt(sc.nextLine());
		int flag = c;
		if(flag == 1 || flag == 2) {
			// Add/Update genre
			go.addOrUpdateGenre(flag, sc);
		}
		else if(flag == 3) {
			// Delete genre
			go.deleteGenre(flag, sc);
		}
		else if(flag == 4) {
			// Read all genres
			go.readAllGenre(flag, sc);
		}
	}
	
	public void pubOp(Scanner sc) throws SQLException {
		PubOperations po = new PubOperations();
		System.out.println("---- Publisher Operations ----");
		
		System.out.println("1. Add\n"
				+ "2. Update\n"
				+ "3. Delete\n"
				+ "4. Search\n");
		int c = Integer.parseInt(sc.nextLine());
		
		int flag = c;
		if(flag == 1 || flag == 2) {
			// Add/Update publisher
			po.addOrUpdatePublisher(flag, sc);
		}
		else if(flag == 3) {
			// Delete publisher
			po.deletePublisher(flag, sc);
		}
		else if(flag == 4) {
			// Read all Publishers
			po.readAllPublisher(flag, sc);
		}
	}
	
	public void bookOp(Scanner sc) throws SQLException {
		BookOperations bo = new BookOperations();
		System.out.println("---- Book Operations ----");
		
		System.out.println("1. Add\n"
				+ "2. Update\n"
				+ "3. Delete\n"
				+ "4. Search\n");
		int c = Integer.parseInt(sc.nextLine());
		
		int flag = c;
		if(flag == 1 || flag == 2) {
			// Add/Update Book
			bo.addOrUpdateBook(flag, sc);
		}
		else if(flag == 3) {
			// Delete Book
			bo.deleteBook(flag, sc);
		}
		else if(flag == 4) {
			// Read Book By Name
			bo.readBookByName(flag, sc);
		}
	}
	
	public void authorOp(Scanner sc) throws SQLException {
		AuthorOperations ao = new AuthorOperations();
		System.out.println("---- Author Operations ----");
		
		System.out.println("1. Add\n"
				+ "2. Update\n"
				+ "3. Delete\n"
				+ "4. Search\n");
		int c = Integer.parseInt(sc.nextLine());
		
		int flag = c;
		if(flag == 1 || flag == 2) {
			// Add/Update author
			ao.addOrUpdateAuthor(flag, sc);
		}
		else if(flag == 3) {
			// Delete author
			ao.deleteAuthor(flag, sc);
		}
		else if(flag == 4) {
			// Read author By Name
			ao.readAuthorByName(flag, sc);
		}
		
	}

}
