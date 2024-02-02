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

@WebServlet("/transfer")
public class FundTransferServ extends HttpServlet{
	
	double senderBal;
	static Connection con;
	static
	{
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			  con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank","root","Moonahmed028");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

	}
	


	@Override
	public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String acc = req.getParameter("account");
		String name = req.getParameter("name");
		String amount = req.getParameter("amount");
		
		long senderAc =  Long.parseLong((String)req.getSession().getAttribute("AccountNumber"));
		
		if(verifyAccount(Long.parseLong(acc), name))
		{
			System.out.println("Account verified");
			if(checkBalance(Double.parseDouble(amount), senderAc))
			{
				System.out.println("Balance is greater");
				
				if(transfer(acc,amount,senderAc))
				{
					RequestDispatcher rd = req.getRequestDispatcher("transferSuccess.html");
					rd.forward(req, res);
				}
				else
				{
					RequestDispatcher rd = req.getRequestDispatcher("transferFail.html");
					rd.forward(req, res);
				}
			}
		}
		else
		{
			
		}
		
	}
	
	
	private boolean transfer(String acc, String amount, long senderAc) {
		
		try {
			PreparedStatement st = con.prepareStatement("select bal as reBal from customers where acNo = ?");
			st.setLong(1, Long.parseLong(acc));
			
			ResultSet rs = st.executeQuery();
			
			if(rs.next())
			{
				double reBal = rs.getDouble("reBal");

				PreparedStatement ps = con.prepareStatement("update customers set bal = ? where acNo = ?");
				ps.setDouble(1,(reBal+ Double.parseDouble(amount)));
				ps.setLong(2, Long.parseLong(acc));
				ps.execute();
				
				ps.setDouble(1, (senderBal- Double.parseDouble(amount)));
				
				ps.setLong(2,senderAc);
				
				ps.execute();
				
				return true;
				
			
			}
		

			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}


	private boolean checkBalance(double am, long senderAc) {
		try {
			System.out.println("Sender account number :" + senderAc);
			
			PreparedStatement ps = con.prepareStatement("select * from customers where acNo = ?");
			ps.setLong(1, senderAc);
			
			ResultSet rs = ps.executeQuery();
			
			if(rs.next())
			{
				
				senderBal = rs.getDouble(3);
				System.out.println("Sender bal" + senderBal);
				System.out.println("Amount" + am);
				if(senderBal>am)
				{
					
					return true;
				}
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return false;
	}


	static boolean verifyAccount(long ac, String name)
	{
		try {
			PreparedStatement ps = con.prepareStatement("select * from customers where cname = ? and acNo = ?");
			
			ps.setString(1, name);
			ps.setLong(2, ac);
			
			
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
		
		
		
	}
}
