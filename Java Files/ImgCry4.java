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


public class ImgCry4 extends HttpServlet {  // JDK 1.6 and above only

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
	  
		try{
			String username1 = request.getParameter("username");
			String RegNo1 = request.getParameter("rollno");
			int RegNo = Integer.parseInt(RegNo1);
			String pixelU = request.getParameter("pixel1").toString();
			String [] a1 = pixelU.split(",");				
			int lengthPixel1 = a1.length;
			double arrOfPixel1[]=new double[lengthPixel1];
			for(int j=0;j<lengthPixel1;j++){
				arrOfPixel1[j]=Double.parseDouble(a1[j]);
			}
			int r = 0;
			/*out.println("<html><head><title>Sign Up</title><style>.pic{width:400px;height:300px;border-style:dotted;top: 50%;left: 50%;margin: -150px 0 0 -200px;position: absolute;}</style>");
			out.println("<body bgcolor=\"#60799f\" style=\"color:white;\">");
			//out.println(""+RegNo);
			out.println("<div class=\"pic\"><center>");
			try{
				Class.forName("org.sqlite.JDBC");
				Connection c = DriverManager.getConnection("jdbc:sqlite:C:/sqlite/PasswordL.db");
				Statement stmt = c.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT * FROM Passwords;");
				
				while(rs.next()){
					String username2 = rs.getString("username");
					String password2 = rs.getString("password");
					int RegNo2 = rs.getInt("RegNo");
					String img2 = rs.getString("pic");
					
					if(username2.equals(username1) && RegNo2==RegNo){
						out.println("Welcome No. " + RegNo2);
						out.println("<br>");
						StringBuffer passwordD = encrypt(password2,rd,td);
						String PasswordD = passwordD.toString();
						out.println("Password: " + PasswordD);			
						img2 = img2.replace('\\','/');
						out.println("<br>");
						out.println(""+img2);
						out.println("<br>");
						out.println("<img src=\"file:///"+img2+"\">");					
						r = 1;
						break;
					}
				}
				if(r==0){
				out.println("Wrong Password or Username");}
			}catch(Exception e){
				out.println(e.getClass().getName() + ": " + e.getMessage());
			}finally{
				out.println("</center></div>");
				out.println("</body></html>");
				out.close();
			}
			*/
			try{
				Class.forName("org.sqlite.JDBC");
				Connection c = DriverManager.getConnection("jdbc:sqlite:C:/sqlite/PasswordL.db");
				Statement stmt = c.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT * FROM Passwords;");
				
				while(rs.next()){
					String username2 = rs.getString("username");
					String password2 = rs.getString("password");					
					int RegNo2 = rs.getInt("RegNo");
					String img2 = rs.getString("pic");			
					String pixel = rs.getString("Pixel");
					
					if(username2.equals(username1) && RegNo2==RegNo){					
						StringBuffer passwordD = encrypt(password2,rd,td);
						String PasswordD = passwordD.toString();
						StringBuffer pixelD = encrypt(pixel,rd,td);
						String PixelD = pixelD.toString();
						PixelD = PixelD.replace('H','.');
						PixelD = PixelD.replace('F',',');
						PixelD = PixelD.replace('G','-');
						String [] a = PixelD.split(",");
						int lengthPixel = a.length;
						double arrOfPixel[]=new double[lengthPixel];
						for(int j=0;j<lengthPixel;j++){
							arrOfPixel[j]=Double.parseDouble(a[j]);
						}

						int i = 0;
						int validate = 0;
						double diffX = 100;
						double diffY = 100;
						for(i=0;i<lengthPixel/2;i++){
							diffX = Math.abs(arrOfPixel[i]-arrOfPixel1[i]);							
							diffY = Math.abs(arrOfPixel[(lengthPixel/2)+i]-arrOfPixel1[(lengthPixel1/2)+i]);							
							if(diffX<15 && diffY<15){
								validate++;
							}else{
								out.println(diffX);
								out.println(diffY);
							}
						}
						if(validate==lengthPixel/2){
							out.println("<html><head><title>Time</title><style>p{font-size: 40px;color:#fff;width: 400px;height: 100px;top: 50%;left: 50%;margin: -50px 0 0 -200px;	position: absolute;	background: rgba(0,0,0,0.8);}</style></head><body><center><p id=\"demo\"></p></center><script>var d = new Date();var t = d.getHours();var b = '';if(t<12 && t>6){b = 'Good Morning';}else if(t>=12 && t<=17){b = 'Good Afternoon';}else if(t>18 && t<22){b = 'Good Evening';}else{b = 'Good Night';}if(t>=12){ampm = 'PM';var Hour = t-12;var a = d.getDate() + '/' + d.getMonth() + '/' + d.getFullYear() + \" \" + Hour + ':' + d.getMinutes() + ':' + d.getSeconds() + \" \" + ampm;}else{ampm = 'AM';var a = d.getDate() + '/' + d.getMonth() + '/' + d.getFullYear() + \" \" + t + ':' + d.getMinutes() + ':' + d.getSeconds() + \" \" + ampm;}document.getElementById(\"demo\").innerHTML = b + \", \" + a;</script></body></html>");
						}else{
							out.println("<html><head><title>Time</title><style>p{font-size: 40px;color:#fff;width: 400px;height: 100px;top: 50%;left: 50%;margin: -50px 0 0 -200px;	position: absolute;	background: rgba(0,0,0,0.8);}</style></head><body><center><p id=\"demo\"></p></center><script>var d = new Date();var t = d.getHours();var b = '';if(t<12 && t>6){b = 'Failed to authenticate';}else if(t>=12 && t<=17){b = 'Failed to authenticate';}else if(t>18 && t<22){b = 'Failed to authenticate';}else{b = 'Failed to authenticate';}if(t>=12){ampm = 'PM';var Hour = t-12;var a = d.getDate() + '/' + d.getMonth() + '/' + d.getFullYear() + \" \" + Hour + ':' + d.getMinutes() + ':' + d.getSeconds() + \" \" + ampm;}else{ampm = 'AM';var a = d.getDate() + '/' + d.getMonth() + '/' + d.getFullYear() + \" \" + t + ':' + d.getMinutes() + ':' + d.getSeconds() + \" \" + ampm;}document.getElementById(\"demo\").innerHTML = b + \", \" + a;</script></body></html>");
						}
						
						r = 1;
						break;
					}
				}
				if(r==0){
				out.println("Wrong Password or Username");}
				stmt.close();
				c.close();
			}catch(Exception e){
				out.println(e.getClass().getName() + ": " + e.getMessage());
			}
		}catch(Exception e){
			out.println(e.getClass().getName() + ": " + e.getMessage());
		}
	}
}