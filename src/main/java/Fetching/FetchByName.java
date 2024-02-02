package Fetching;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;

import com.google.gson.Gson;

import login.Customer;

@WebServlet("/fetchByName")
public class FetchByName extends GenericServlet{

	@Override
	public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
		
		String name = req.getParameter("name");

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			 Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank","root","Moonahmed028");
			PreparedStatement ps = con.prepareStatement("select * from customers where cname LIKE ?");
            ps.setString(1, "%" + name + "%");
			
			
			ResultSet rs = ps.executeQuery();
			
			
			List <Customer> li = new ArrayList<>();
			
			while(rs.next())
			{
				String n = rs.getString(2);
				long ac = rs.getLong(1);
				double b = rs.getDouble(3);
					
				long m = rs.getLong(5);
				boolean s = rs.getBoolean(4);
				String status = s?"Active":"Inactive";
				li.add(new Customer(n,m,ac,b,status));
			}
			
			String jsonData = new Gson().toJson(li);
		
			res.setContentType("application/json");
			res.setCharacterEncoding("UTF-8");
			res.getWriter().write(jsonData);
			

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
