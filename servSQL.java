import java.io.IOException;
import java.io.PrintWriter;
import java.sql.DriverManager;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;

/**
 * Servlet implementation class Stud
 */
@WebServlet("/Stud")
public class Stud extends HttpServlet {
	private static final long serialVersionUID = 1L;
	String name,year,email,dob,op,id;
	PreparedStatement ps;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Stud() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		response.setContentType("text/html");
		PrintWriter pr=response.getWriter();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con=(Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/PCCOE", "root","mysql");
			id=request.getParameter("id");
			fname=request.getParameter("fname");
			lname=request.getParameter("lname");
			pass1=request.getParameter("pass1");
			dob=request.getParameter("dob");
			email=request.getParameter("email");
			op=request.getParameter("op");

		if(op.equals("submit")){
			Statement stmt=(Statement) con.createStatement();
			int i=stmt.executeUpdate("insert into stud values('"+id+"','"+fname+"','"+lname+"','"+pass1+"','"+dob+"','"+email"');");
			if(i<0)
			{
				pr.println("Failed to insert");
			}
			else
			{
				pr.println("Inserted into database successfully...");
			}
		}
		else if(op.equals("update")){
			ps=(PreparedStatement) con.prepareStatement("update stud set fname=?,lname=?,pass1=?,dob=?,email=? where id=?;");  
			ps.setString(1,fname);  
			ps.setString(2,lname);  
			ps.setString(3,pass1);
			ps.setString(4,dob);
			ps.setString(5,email);  
			ps.setString(6,id);  

			int i=ps.executeUpdate();				
			if(i<0)
			{
				pr.println("Unsuccessful");
			}
			else
			{
				pr.println("Record updated successfully...");
			}
		}
		
		else{
			ResultSet rs=null;
			String sql="select * from stud where id='"+id+"';";
			ps=(PreparedStatement) con.prepareStatement(sql);
			rs=ps.executeQuery();
			  	
			while(rs.next()){
				pr.println("ID: "+rs.getString(1)+"<br>");
				pr.println("Name: "+rs.getString(2)+"<br>");
				pr.println("Email id: "+rs.getString(3)+"<br>");
				pr.println("Year: "+rs.getString(4)+"<br>");
				pr.println("Date Of Birth: "+rs.getString(5)+"<br>");
			}					
		}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

