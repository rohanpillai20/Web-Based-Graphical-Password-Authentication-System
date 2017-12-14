import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import javax.swing.JFileChooser;
import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.JOptionPane;
import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import org.json.*;


public class ImgCry3 extends HttpServlet {  // JDK 1.6 and above only

	final int re = 8;
    final int rd = 26-re;
    final int te = 4;
    final int td = 10-te;

	public StringBuffer encrypt(String text, int s, int t){
    StringBuffer result= new StringBuffer();

		for (int i=0; i<text.length(); i++){
			if (Character.isUpperCase(text.charAt(i))){
				char ch = (char)(((int)text.charAt(i) + s - 65) % 26 + 65);
					result.append(ch);
			}else if(Character.isDigit(text.charAt(i))){
				char ch = (char)(((int)text.charAt(i) + t - 48) % 10 + 48);
				result.append(ch);
			}else{
				char ch = (char)(((int)text.charAt(i) + s - 97) % 26 + 97);
				result.append(ch);
				}
		}
		return result;
	}
 
   // The doGet() runs once per HTTP GET request to this servlet.
   @Override
   public void doGet(HttpServletRequest request, HttpServletResponse response)
               throws ServletException, IOException {
      // Set the MIME type for the response message
      response.setContentType("text/html");
      // Get a output writer to write the response message into the network socket
      PrintWriter out = response.getWriter();
	  
		/*out.println("<html><head><title>Sign Up</title><style>.pic{width:400px;height:300px;border-style:dotted;top: 50%;left: 50%;margin: -150px 0 0 -200px;position: absolute;}</style>");
		out.println("<body bgcolor=\"#60799f\" style=\"color:white;\">");
		String username = request.getParameter("username");
		String RegNo1 = request.getParameter("rollno");
		String a = request.getParameter("img");
		int RegNo = Integer.parseInt(RegNo1);		
		String b = "C:\\Users\\SMP\\Desktop\\" + a;
		out.println("<div class=\"pic\"><center>");
		
		out.println("Roll No: "+RegNo);
		out.println("<br>");
		String password = request.getParameter("password");
		StringBuffer passwordE = encrypt(password,re,te);
		String PasswordE = passwordE.toString();
		out.println("Encrypted: " + PasswordE);
		out.println("<br>");
		StringBuffer passwordD = encrypt(PasswordE,rd,td);
		String PasswordD = passwordD.toString();
		out.println("\nDecrypted: " + PasswordD);
		out.println("<br>");
		
		try{
         Class.forName("org.sqlite.JDBC");
         Connection c = DriverManager.getConnection("jdbc:sqlite:C:/sqlite/PasswordL.db");
		 //Statement stmt = con.createStatement();
         
		 Statement stmt = c.createStatement();
         //String sql1 = "CREATE TABLE Passwords (username varchar(25) PRIMARY KEY, password varchar(25), RegNo int);";
         //stmt.executeUpdate(sql1);
         //String sql = "INSERT INTO Passwords values('abc','abc1',123);";
         String sql = "INSERT INTO Passwords (username, password, RegNo, pic) " + "VALUES('"+username+"','"+passwordE+"',"+RegNo+",'"+b+"');";
         //System.out.println(""+sql);
         stmt.executeUpdate(sql);
         out.println("Successfully Entered Record");
		 out.println("<br>");
		 //out.println("<button type=\"submit\" formaction=\"https://localhost:8443/password/ImgCry(login).html\">Login</button>");
		 out.println("<br>");
		
		}catch(Exception e){
        out.println(e.getClass().getName() + ": " + e.getMessage());
		}finally{
			out.close();
			out.println("</center></div>");
			out.println("</body>");
			out.println("</html>");
		}	
		*/
		String username = request.getParameter("username");
		String RegNo1 = request.getParameter("rollno");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String a1 = request.getParameter("img");
		int RegNo = Integer.parseInt(RegNo1);	
		String pixel = request.getParameter("pixel1").toString();
		/*String [] arrOfPixel = pixel.split(",");
		int lengthPixel = arrOfPixel.length;
		for (String a : arrOfPixel)
			out.println(a);
		*/
		/*try {
			JSONObject jsonObj = new JSONObject(objarray);          

			String xmlString= XML.toString(jsonObj);
			out.println("JSON to XML: " + xmlString);
		} catch (JSONException e1) {
			e1.printStackTrace();
		}*/
		String b = "C:\\Users\\SMP\\Desktop\\" + a1;
		
		StringBuffer passwordE = encrypt(password,re,te);
		String PasswordE = passwordE.toString();
		StringBuffer passwordD = encrypt(PasswordE,rd,td);
		String PasswordD = passwordD.toString();
		
		StringBuffer pixelE = encrypt(pixel,re,te);
		String PixelE = pixelE.toString();
		/*StringBuffer pixelD = encrypt(PixelE,rd,td);
		String PixelD = pixelD.toString();
		PixelD = PixelD.replace('H','.');
		PixelD = PixelD.replace('F',',');
		PixelD = PixelD.replace('G','-');
		out.println(" "+PixelD);*/
			
		try{
         Class.forName("org.sqlite.JDBC");
         Connection c = DriverManager.getConnection("jdbc:sqlite:C:/sqlite/PasswordL.db");
         
		 Statement stmt = c.createStatement();
         String sql = "INSERT INTO Passwords (username, password, RegNo, pic, Pixel, UserEmail) " + "VALUES('"+username+"','"+passwordE+"',"+RegNo+",'"+b+"','"+PixelE+"','"+email+"');";
         stmt.executeUpdate(sql);
         out.println("<html><head><style>p{font-size: 30px;color:white;width: 405px;height: 105px;top: 50%;left: 50%;margin: -50px 0 0 -200px;	position: absolute;	background: rgba(0,0,0,0.8);}</style><script type=\"text/javascript\">function Redirect(){window.location=\"https://localhost:8443/password/Login.html\";}setTimeout('Redirect()', 5000);</script></head><body><center><p id=\"demo\">Welcome! You have been registered. You will be redirected to the login page in 5 sec.</p></center></body></html>");
		 stmt.close();
		 c.close();
		}catch(Exception e){
        out.println(e.getClass().getName() + ": " + e.getMessage());
		}
	}
}