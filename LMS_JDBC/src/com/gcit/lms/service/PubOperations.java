/**
 * 
 */
package com.gcit.lms.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import com.gcit.lms.dao.PubDAO;
import com.gcit.lms.entity.Publisher;

/**
 * @author square
 *
 */
public class PubOperations {

	/**
	 * 
	 */
	ConnectionUtil connUtil = new ConnectionUtil();
	
	public PubOperations() {
		// TODO Auto-generated constructor stub
	}

	public void readAllPublisher(int flag, Scanner sc) throws SQLException {
		Connection conn = null;
		try {
			int index = 0;
			conn = connUtil.getConnection();
			PubDAO pdao = new PubDAO(conn);
			List<Publisher> pubs = pdao.readAllPublishers();
			
			index = 1;
			for(Publisher publisher: pubs) {
				System.out.println("" + index + ". " + publisher.getPubName());
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
	
	public void addOrUpdatePublisher(int flag, Scanner sc) throws SQLException {
		Connection conn = null;
		try {
			int index = 0;
			int c = 0;
			conn = connUtil.getConnection();
			Publisher p = new Publisher();
			if(flag == 1) {
				// Add Publisher
				System.out.println("Publisher name: ");
				String name = sc.nextLine();
				p.setPubName(name);
			}
			else {
				// Update Publisher
				System.out.println("Choose a Publisher to update: ");
				PubDAO pdao = new PubDAO(conn);
				List<Publisher> pubs = pdao.readAllPublishers();
				index = 1;
				for(Publisher publisher: pubs) {
					System.out.println("" + index + ". " + publisher.getPubName());
					index++;
				}
				c = Integer.parseInt(sc.nextLine());
				p.setPubId(pubs.get(c-1).getPubId());
				
				System.out.println("Type a new Publisher name: ");
				String name = sc.nextLine();
				p.setPubName(name);
				
			}
			
			// Address
			System.out.println("Type Publisher's Address: ");
			String address = sc.nextLine();
			p.setPubAddress(address);
			
			// Phone
			System.out.println("Type Publisher's Phone: ");
			String phone = sc.nextLine();
			p.setPubPhone(phone);
			
			PubDAO pdao = new PubDAO(conn);
			
			if(flag == 1) {
				pdao.savePub(p);
			}
			else {
				pdao.updatePub(p);
			}
			
			conn.commit();
			
			System.out.println("Publisher Added/Updated!");
			System.out.println("\nPress [Enter] to continue...");
			sc.nextLine();
			
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			conn.rollback();
		} finally{
			conn.close();
		}
	}
	
	public void deletePublisher(int flag, Scanner sc) throws SQLException {
		Connection conn = null;
		try {
			int index = 0;
			int c = 0;
			conn = connUtil.getConnection();
			Publisher g = new Publisher();
			System.out.println("Choose an Publisher to delete: ");
			PubDAO adao = new PubDAO(conn);
			List<Publisher> pubs = adao.readAllPublishers();
			index = 1;
			for(Publisher Publisher: pubs) {
				System.out.println("" + index + ". " + Publisher.getPubName());
				index++;
			}
			c = Integer.parseInt(sc.nextLine());
			g.setPubId(pubs.get(c-1).getPubId());
			
			adao.deletePub(g);
			
			conn.commit();
			
			System.out.println("Publisher deleted!");
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
