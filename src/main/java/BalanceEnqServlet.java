import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/balanceEnq")
public class BalanceEnqServlet extends HttpServlet {

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException
	{
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			
			HttpSession session = req.getSession();
			System.out.println("Executing changeNumber ()");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank","root","Moonahmed028");
			
			PreparedStatement ps = con.prepareStatement("select * from customers where acNo = ?");
			 
			String ac = (String) session.getAttribute("AccountNumber");
			
			ps.setLong(1, Long.parseLong(ac));
			
			ResultSet rs = ps.executeQuery();
			
			
			rs.next();
			Double sal = rs.getDouble(3);
			
			res.getWriter().write( String.valueOf(sal));

			
			
			
			
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		
		
	}
}
