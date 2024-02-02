package dataManipulation;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;

@WebServlet("/unBlockUserURL")
public class UnblockServlet extends GenericServlet {

	@Override
	public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		
		String ac = req.getParameter("accountNumber");
		try {
			block(ac);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	static void block(String s) throws ClassNotFoundException, SQLException
	{
	
			Class.forName("com.mysql.cj.jdbc.Driver");
		
			 Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank","root","Moonahmed028");
			PreparedStatement ps = con.prepareStatement("update customers set status = true where acNo = ?");
			ps.setLong(1, Long.parseLong(s));
			
			ps.execute();
			
			System.out.println("Successfully Unblocked");
		
	}

}