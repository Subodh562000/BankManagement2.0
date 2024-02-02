package dataManipulation;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/changePin")
public class ChangePinServlet extends HttpServlet{
	@Override 
	protected void doPost(HttpServletRequest req , HttpServletResponse res) throws ServletException, IOException
	{
		HttpSession hs = req.getSession();
		
		String ac = (String) hs.getAttribute("AccountNumber");
		
		long accountNumber = Long.parseLong(ac);
		
		String pin = req.getParameter("newPin");
		
		try {
			if(changePin(Integer.parseInt(pin),accountNumber))
			{
			
				RequestDispatcher rd = req.getRequestDispatcher("changePinSuccess.html");
				rd.forward(req, res);
			}
			else
			{
				RequestDispatcher rd = req.getRequestDispatcher("changePinFail.html");
				rd.forward(req, res);
			}
		} catch (NumberFormatException | SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Inside this");
			e.printStackTrace();
		}
		
		
		
	}
	
	
	boolean changePin(int pin, long ac) throws SQLException
	{
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("Inside that");
			e.printStackTrace();
		}
		
		 Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank","root","Moonahmed028");
		 System.out.println("Executing change Pin Method");
		 PreparedStatement ps = con.prepareStatement("update Customers set pin = ? where acNo = ?");
		 ps.setInt(1, pin);
		 ps.setLong(2, ac);
		 
		 int n = ps.executeUpdate();
		 
		 return n>0;
	}
}
