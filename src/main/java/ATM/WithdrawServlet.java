package ATM;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.GenericServlet;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import login.CustomerLoginServ;

@WebServlet("/withdraw")
public class WithdrawServlet extends HttpServlet{

	public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		long accountNumber = Long.valueOf((String) req.getSession().getAttribute("AccountNumber"));
		double amount = Double.parseDouble(req.getParameter("amount"));
		
		if(withdraw(accountNumber,amount))
		{
			RequestDispatcher rd = req.getRequestDispatcher("withdrawSuccess.html");
			rd.forward(req, res);
		}
		else
		{
			RequestDispatcher rd = req.getRequestDispatcher("withdrawFail.html");
			rd.forward(req, res);
		}
		
		
	}
	
	
	
	private static boolean withdraw(long ac, double am)
	{
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			 Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank","root","Moonahmed028");
			PreparedStatement ps = con.prepareStatement("select * from customers where acNo = ?");
			ps.setLong(1, ac);
			
			ResultSet rs = ps.executeQuery();
			if(rs.next())
			{
				double amount = rs.getDouble(3);
				if(amount<am)
				{
					return false;
				}
				PreparedStatement ps1 = con.prepareStatement("update customers set bal = ? where acNo = ?");
				ps1.setDouble(1, amount-am);
				ps1.setLong(2, ac);
				
				ps1.executeUpdate();
				return true;
			}
			
			
			
	}
	catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	
		return false;
	}
	}
