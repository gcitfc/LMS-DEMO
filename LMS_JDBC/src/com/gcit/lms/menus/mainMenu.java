package com.gcit.lms.menus;

import java.sql.SQLException;
import java.util.Scanner;

import com.gcit.lms.service.*;

public class mainMenu {

	public static void main(String[] args) throws SQLException {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		int flag = 1;

		while(flag == 1 || flag == 2 || flag == 3) {
			System.out.println("Welcome to the GCIT Library Management System. Which category of a user are you\n\n1. Librarian\n2. Administrator\n3. Borrower\n\n0. Quit");
			flag = Integer.parseInt(sc.nextLine());
			if(flag == 1) {
				Librarian librarian = new Librarian();
				librarian.mainMenu(sc);
			}
			else if(flag == 2) {
				Administrator admin = new Administrator(sc);
				//admin.mainMenu(sc);
			}
			else if(flag == 3) {
				Borrower borrower = new Borrower();
				borrower.mainMenu(sc);
			}
			else
				break;
		}
		
		sc.close();
		System.out.println("Thanks for using LMS!");
	}

}
