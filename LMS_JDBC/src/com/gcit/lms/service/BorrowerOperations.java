package com.gcit.lms.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import com.gcit.lms.dao.BorrowerDAO;
import com.gcit.lms.entity.Borrower;

public class BorrowerOperations {

	ConnectionUtil connUtil = new ConnectionUtil();
	
	public BorrowerOperations() {
		// TODO Auto-generated constructor stub
	}
	
	public boolean validBorrower(Integer cardNo) throws SQLException {
		Connection conn = null;
		boolean valid = false;
		try {
			conn = connUtil.getConnection();
			BorrowerDAO bdao = new BorrowerDAO(conn);
			valid = bdao.validBorrower(cardNo);
			return valid;
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			conn.rollback();
		} finally{
			conn.close();
		}
		return false;
	}
	
	public void readBorrowerByName(int flag, Scanner sc) throws SQLException {
		Connection conn = null;
		try {
			int index = 0;
			conn = connUtil.getConnection();
			
			System.out.println("Type the Borrower's first/last Name: ");
			String name = sc.nextLine();
			BorrowerDAO bdao = new BorrowerDAO(conn);
			List<Borrower> Borrowers = bdao.readAllBorrowersByName(name);
			
			index = 1;
			for(Borrower borrower: Borrowers) {
				System.out.println("" + index + ". " + borrower.getName());
				index++;
			}
			
			System.out.println("\nPress [Enter] to continue...");
			sc.nextLine();
			
			// conn.commit();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			conn.rollback();
		} finally{
			conn.close();
		}
	}
	
	public void addOrUpdateBorrower(int flag, Scanner sc) throws SQLException {
		Connection conn = null;
		try {
			int index = 0;
			int c = 0;
			conn = connUtil.getConnection();
			Borrower b = new Borrower();
			if(flag == 1) {
				// Add Borrower
				System.out.println("Borrower Name: ");
				String name = sc.nextLine();
				b.setName(name);
			}
			else {
				// Update Borrower
				System.out.println("Choose an Borrower to update: ");
				BorrowerDAO bdao = new BorrowerDAO(conn);
				List<Borrower> Borrowers = bdao.readAllBorrowers();
				index = 1;
				for(Borrower Borrower: Borrowers) {
					System.out.println("" + index + ". " + Borrower.getName());
					index++;
				}
				c = Integer.parseInt(sc.nextLine());
				b.setCardNo(Borrowers.get(c-1).getCardNo());
				
				System.out.println("Type b new Borrower Name: ");
				String name = sc.nextLine();
				b.setName(name);
			}
			// Address
			System.out.println("Borrower's Address: ");
			String address = sc.nextLine();
			b.setAddress(address);
			// Phone
			System.out.println("Borrower's phone num: ");
			String phone = sc.nextLine();
			b.setPhone(phone);
			
			BorrowerDAO bdao = new BorrowerDAO(conn);
			int bId = -1;
			if(flag == 1)
				//bdao.saveBorrower(b);
				bId = bdao.saveBorrowerWithId(b);
			else
				bdao.updateBorrower(b);
			
			conn.commit();
			
			System.out.println("Borrower Added/Updated!\n Borrwoer's Card# is " + bId);
			System.out.println("\nPress [Enter] to continue...");
			sc.nextLine();
			
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			conn.rollback();
		} finally{
			conn.close();
		}
	}
	
	public void deleteBorrower(int flag, Scanner sc) throws SQLException {
		Connection conn = null;
		try {
			int index = 0;
			int c = 0;
			conn = connUtil.getConnection();
			Borrower b = new Borrower();
			System.out.println("Choose a Borrower to delete: ");
			BorrowerDAO bdao = new BorrowerDAO(conn);
			List<Borrower> Borrowers = bdao.readAllBorrowers();
			index = 1;
			for(Borrower Borrower: Borrowers) {
				System.out.println("" + index + ". " + Borrower.getName());
				index++;
			}
			c = Integer.parseInt(sc.nextLine());
			b.setCardNo(Borrowers.get(c-1).getCardNo());
			
			bdao.deleteBorrower(b);
			
			conn.commit();
			
			System.out.println("Borrower deleted!");
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
