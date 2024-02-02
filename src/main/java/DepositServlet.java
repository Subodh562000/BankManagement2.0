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
import javax.servlet.http.HttpSession;

import login.CustomerLoginServ;

@WebServlet("/deposit")
public class DepositServlet extends HttpServlet{

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		
		HttpSession session = req.getSession();
		String ac = (String) session.getAttribute("AccountNumber");
		
		
		
		System.out.println(ac);
		double am = Double.parseDouble(req.getParameter("amount"));
		
		if(deposit(Long.parseLong(ac),am))
		{
			
			RequestDispatcher rd = req.getRequestDispatcher("depositSuccess.html");
			rd.forward(req, res);
		}
		else
		{
			System.out.println(req.getSession().getId());
			RequestDispatcher rd = req.getRequestDispatcher("depostiFail.html");
			rd.forward(req, res);
		}
		
		
	}
	private static boolean deposit(long ac, double am)
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
				ps1.setDouble(1, amount+am);
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
