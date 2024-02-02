package login;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/customerVerify")
public class CustomerLoginServ extends HttpServlet{

	
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException
	{
		System.out.println("Executing");
		String username=  req.getParameter("accountNumber");
		String password = req.getParameter("pin");
		
		HttpSession session = req.getSession();
		
		
		session.setAttribute("AccountNumber", username);
		session.setAttribute("password", password);
		
		
		
			
			if(validateCredential(username,password))
			{
				System.out.println(session.getId());
			RequestDispatcher rd = req.getRequestDispatcher("atmHomepage.html");
			rd.forward(req, res);
			}
			else
			{
				
				PrintWriter p = res.getWriter();
				p.print("<script> alert('Invalid input'); </script>");
				RequestDispatcher rd = req.getRequestDispatcher("atmLogin.html");
				rd.include(req, res);
			}
			
		
	}
	
	
//	@Override
//	public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
//		// TODO Auto-generated method stub
//		
//		
//		
//	}
	
	
	private boolean validateCredential(String username, String password) {
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			System.out.println("Executing");
			 Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank","root","Moonahmed028");
			PreparedStatement ps = con.prepareStatement("select * from customers where acNo = ? and pin = ?");
			ps.setLong(1, Long.parseLong( username));
			ps.setInt(2, Integer.parseInt(password));
			
			ResultSet rs = ps.executeQuery();
			if(rs.next())
			{
				return true;
			}
		
			

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;

	}}
