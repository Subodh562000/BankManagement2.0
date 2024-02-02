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

@WebServlet("/changePhone")
public class ChangePhoneServlet extends HttpServlet {

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException
	{
		String mb = req.getParameter("newMobileNumber");
		HttpSession session = req.getSession();
		
		String ac = (String)session.getAttribute("AccountNumber");
		System.out.println("Account number is " + ac);
		if(changeNumber(Long.parseLong(mb), Long.parseLong(ac)))
		{

					RequestDispatcher rd = req.getRequestDispatcher("changePhoneSuccess.html");
					rd.forward(req, res);
			
		}
		else	
		{
			RequestDispatcher rd = req.getRequestDispatcher("changePinFail.html");
			rd.forward(req, res);

		}
	}

	private boolean changeNumber(long mb, long ac) {

		 try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			System.out.println("Executing changeNumber ()");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank","root","Moonahmed028");
			
			PreparedStatement ps = con.prepareStatement("update customers set phoneno = ? where acNo = ?");
			 
			ps.setLong(1, mb);
			ps.setLong(2, ac);
			
			int effected = ps.executeUpdate();
			System.out.println("Effected is " + effected);
			return effected>0;
			
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		 return false;
	}
}
