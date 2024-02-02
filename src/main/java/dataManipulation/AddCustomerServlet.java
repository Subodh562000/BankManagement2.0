package dataManipulation;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;

import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;


@WebServlet("/addCustomerURL")
public class AddCustomerServlet extends GenericServlet{

	@Override
	public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
		
		String name = req.getParameter("name");
		String phno = req.getParameter("phone");
		
		
		if(name.trim().equals("") || phno.length()<10)
		{
				return;
		}
		try {
			addCustomer(name,Long.parseLong(phno));
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		req.setAttribute("operationSuccessful", true);

		req.getRequestDispatcher("adminHome.html").forward(req, res);
		
	}
	
	
	
	
public static void addCustomer(String name, long phone) throws SQLException, ClassNotFoundException {
		
		
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank","root","Moonahmed028");

		PreparedStatement ps = con.prepareStatement("insert into customers values (?,?,?,?,?,?)");
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery("select max(acNo)as max from customers");
		
		Random r = new Random();
	    int pin = r.nextInt(8999)+1000;
	    
	    
	    
		rs.next();
		long acc = rs.getLong("max");
		if( acc ==0)
		{
			ps.setLong(1, 11200000);
		}
		else
		{
			ps.setLong(1,++acc);
		}

		ps.setString(2, name);
		ps.setDouble(3, 300.00d);
		ps.setBoolean(4, true);
		ps.setLong(5, phone);
		ps.setInt(6, pin);
		
		ps.execute();
		
		
		
	}
	

}
