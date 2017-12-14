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
import java.util.*;  
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class FP extends HttpServlet {  // JDK 1.6 and above only

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
	//String from,String password,String to,String sub,String msg
	public static void send(String tomail,String msg){  
		// Recipient's email ID needs to be mentioned.
      //String to = "rpillai1997@gmail.com";
	  String to = tomail;

      // Sender's email ID needs to be mentioned
      String from = "kenadam2505@gmail.com";//change accordingly
      final String username = "kenadam2505@gmail.com";//change accordingly
      final String password = "rohanaudia8";//change accordingly

      // Assuming you are sending email through relay.jangosmtp.net
      String host = "smtp.gmail.com";

      Properties props = new Properties();
      props.put("mail.smtp.auth", "true");
      props.put("mail.smtp.starttls.enable", "true");
      props.put("mail.smtp.host", host);
      props.put("mail.smtp.port", "587");

      // Get the Session object.
      Session session = Session.getInstance(props,
      new javax.mail.Authenticator() {
         protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(username, password);
         }
      });

      try {
         // Create a default MimeMessage object.
         Message message = new MimeMessage(session);

         // Set From: header field of the header.
         message.setFrom(new InternetAddress(from));

         // Set To: header field of the header.
         message.setRecipients(Message.RecipientType.TO,
         InternetAddress.parse(to));

         // Set Subject: header field
         message.setSubject("Login Credentials");

         // Now set the actual message
         message.setText("" + msg);

         // Send message
         Transport.send(message);

         System.out.println("Sent message successfully....");

      } catch (MessagingException e) {
            throw new RuntimeException(e);
      }
	}  

   // The doGet() runs once per HTTP GET request to this servlet.
   @Override
   public void doGet(HttpServletRequest request, HttpServletResponse response)
               throws ServletException, IOException {
      // Set the MIME type for the response message
      response.setContentType("text/html");
      // Get a output writer to write the response message into the network socket
      PrintWriter out = response.getWriter();
	  
	  
		String RegNo1 = request.getParameter("rollno");
		int RegNo = Integer.parseInt(RegNo1);		
		int r = 0;
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
				

				if(RegNo2==RegNo){
					StringBuffer passwordD = encrypt(password2,rd,td);
					String PasswordD = passwordD.toString();
					StringBuffer pixelD = encrypt(pixel,rd,td);
					String PixelD = pixelD.toString();
					PixelD = PixelD.replace('H','.');
					PixelD = PixelD.replace('F',',');
					PixelD = PixelD.replace('G','-');
					String email = rs.getString("UserEmail");
					send(email,"Your details are as follows: \n"+"\nUsername: "+username2+"\nPassword: "+PasswordD+"\nImage: "+img2+"\nPixels: "+PixelD);
					
					out.println("<html><head><style>p{font-size: 30px;color:white;width: 405px;height: 105px;top: 50%;left: 50%;margin: -50px 0 0 -200px;	position: absolute;	background: rgba(0,0,0,0.8);}</style><script type=\"text/javascript\">function Redirect(){window.location=\"https://localhost:8443/password/Login.html\";}setTimeout('Redirect()', 5000);</script></head><body><center><p id=\"demo\">Email has been sent! You will be redirected to the login page in 5 sec.</p></center></body></html>");
					r = 1;
					break;//
				}
			}
			if(r==0){
			out.println("<html><head><style>p{font-size: 30px;color:white;width: 405px;height: 105px;top: 50%;left: 50%;margin: -50px 0 0 -200px;	position: absolute;	background: rgba(0,0,0,0.8);}</style><script type=\"text/javascript\">function Redirect(){window.location=\"https://localhost:8443/password/SignUp.html\";}setTimeout('Redirect()', 5000);</script></head><body><center><p id=\"demo\">Welcome! You have been registered. You will be redirected to the sign up page in 5 sec.</p></center></body></html>");}
			stmt.close();
			c.close();
		}catch(Exception e){
			out.println(e.getClass().getName() + ": " + e.getMessage());
			out.println("<html><head><style>p{font-size: 30px;color:white;width: 405px;height: 105px;top: 50%;left: 50%;margin: -50px 0 0 -200px;	position: absolute;	background: rgba(0,0,0,0.8);}</style><script type=\"text/javascript\">function Redirect(){window.location=\"https://localhost:8443/password/Login.html\";}setTimeout('Redirect()', 5000);</script></head><body><center><p id=\"demo\">"+e.getClass().getName() + ": " + e.getMessage()+"</p></center></body></html>");
		}
	}
}