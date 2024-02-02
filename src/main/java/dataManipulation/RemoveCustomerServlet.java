package dataManipulation;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;

@WebServlet("/removeCustomerURL")
public class RemoveCustomerServlet extends GenericServlet{

	@Override
	public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("Executed remove");
		
		String acc = req.getParameter("accountNumber");
		
		long account = Long.parseLong(acc);
		
		
		try {
			int result =removeCustomer(account);
			
			
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		
	}
	
	
	static int removeCustomer(long acc) throws ClassNotFoundException, SQLException
	{
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank","root","Moonahmed028");

		PreparedStatement ps = con.prepareStatement("delete from customers where acNo = ?");
		ps.setLong(1, acc);
		
		int res = ps.executeUpdate();
		
		
		
		
		System.out.println(res);
		return res;
	}

}
