package com.gcit.lms.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import com.gcit.lms.entity.Branch;

public class Librarian {
	
	ConnectionUtil connUtil = new ConnectionUtil();
	Branch branch;
	BranchOperations bo;
	
	public Librarian()  {
		
	}
	
	public void mainMenu(Scanner sc) throws SQLException {
		int flag = 1;
		while(flag == 1) {
			System.out.println("---- Librarian Menu ----");
			System.out.println("1. Choose the branch you manage\n2. Quit to previous");
			int c = Integer.parseInt(sc.nextLine());
			if(c == 2)
				return;
			flag = libMenu2(sc);
		}
	}
	
	public int libMenu2(Scanner sc) throws SQLException {
		int flag = 1;
		while(flag == 1) {
			bo = new BranchOperations();
			List<Branch> branches = bo.readAllBranch();
			System.out.println("\n" + String.valueOf(branches.size()+1) + ". Quit to previous");
			
			int c = Integer.parseInt(sc.nextLine());
			
			if(c == branches.size()+1)
				return 1;		
			branch = branches.get(c-1);
			flag = libMenu3(sc);
		}
		return flag;
	}
	
	public int libMenu3(Scanner sc) throws SQLException {
		int flag = 1;
		while(flag == 1) {
			System.out.println("1. Update the details of the Library\n" + 
					"2. Add copies of Book to the Branch\n" + 
					"3. Quit to previous\n");
			
			int c = Integer.parseInt(sc.nextLine());
			if(c == 1) {
				bo.updateBranch(branch, sc);
			}
			else if(c == 2) {
				bo.addCopies(branch, sc);
			}
			else if(c == 3)
				return 1;
		}
		return flag;
	}

}
