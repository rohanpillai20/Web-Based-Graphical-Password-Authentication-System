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


public class ImgCry5 extends HttpServlet {  // JDK 1.6 and above only

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
	  
	  
		String username1 = request.getParameter("username");
		String RegNo1 = request.getParameter("rollno");
		int RegNo = Integer.parseInt(RegNo1);		
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
				
				String img3 = img2.replace("C:\\Users\\SMP\\Desktop\\","");

				if(username2.equals(username1) && RegNo2==RegNo){
					out.println("<!DOCTYPE html><html><head><title>Graphical Password Authentication System</title><!-- CSS File--><link href=\"css/style.css\" rel=\"stylesheet\" type=\"text/css\" media=\"all\" /><!-- JS Files--><script src=\"js/jquery-2.2.3.min.js\"></script><script src=\"https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js\"></script><script src=\"http://code.jquery.com/jquery-2.1.0.min.js\"></script></head><body><!-- main --><div class=\"main-agileits\"><h1>Graphical Password Authentication System - Login</h1><!-- form --><div class=\"mainw3-agileinfo form\" style=\"margin-left:100px; float:left;\"><div id=\"login\"><form method=\"get\" action=\"http://localhost:9999/password/imgcry4\"><div class=\"field-wrap\"><label class=\"active\"> Username<span class=\"req\">*</span> </label><input type=\"text\" id=\"username\" value=\"\" name=\"username\" required></div><div class=\"field-wrap\"><label class=\"active\"> Roll No<span class=\"req\">*</span> </label><input type=\"number\" id=\"RegNo\" value=\"\" name=\"rollno\" min=1 max=120 required></div><div class=\"field-wrap\"><input type=\"text\" name=\"pixel1\" id=\"pixel1\" value=\"\" readonly></div><p class=\"forgot\"><a href=\"http://localhost:9999/password/ForgotPassword.html\">Forgot Password?</a></p><button class=\"button button-block\"/>Login</button></form></div></div><!-- //form --><!-- image --><div class = \"img\" style=\"float:left; padding: 70px 150px 80px 150px; width: 33%;\"><a id=\"pixel\"><img id=\"blah\" src=\""+img3+"\" alt=\"\" height=400 width=500 /></a></div><!-- //image --></div><!-- //main --><!-- copyright --><center><div class=\"w3copyright-agile\"><p>Rohan Pillai (15BIT049) | Shrey Vaghela (15BIT064)</p></div></center><!-- //copyright --><!-- JS --><script>$('.form').find('input, textarea').on('keyup blur focus', function (e) { var $this = $(this), label = $this.prev('label');if (e.type === 'keyup') {if ($this.val() === '') {label.removeClass('active highlight');} else {label.addClass('active highlight');}} else if (e.type === 'blur') {if( $this.val() === '' ) {label.removeClass('active highlight'); } else {label.removeClass('highlight');}} else if (e.type === 'focus') {if( $this.val() === '' ) {label.removeClass('highlight');}else if( $this.val() !== '' ) {label.addClass('highlight');}}});</script><script>document.getElementById('username').value ='"+username1+"';document.getElementById('RegNo').value ='"+RegNo1+"';document.getElementById('blah').draggable = false;</script><script> var a = []; $(function() {$('#pixel').click(function(e) {var offset = $(this).offset(); var relativeX = (e.pageX - offset.left); var relativeY = (e.pageY - offset.top); a.push({X: relativeX, Y: relativeY}); console.log(a); $('.position').val('afaf');var arr1 = Object.keys(a).map(function (key) { return a[key].X; });var arr2 = Object.keys(a).map(function (key) { return a[key].Y; });document.getElementById('pixel1').value = arr1+\",\"+arr2;});});</script><!-- //JS --></body></html>");
					r = 1;
					break;//
				}
			}
			if(r==0){
			out.println("<html><head><style>p{font-size: 30px;color:white;width: 405px;height: 105px;top: 50%;left: 50%;margin: -50px 0 0 -200px;	position: absolute;	background: rgba(0,0,0,0.8);}</style><script type=\"text/javascript\">function Redirect(){window.location=\"https://localhost:8443/password/Login.html\";}setTimeout('Redirect()', 5000);</script></head><body><center><p id=\"demo\">Wrong Username or Roll Number. You will be redirected to the login page in 5 sec.</p></center></body></html>");}
			stmt.close();
			c.close();
		}catch(Exception e){
			out.println(e.getClass().getName() + ": " + e.getMessage());
		}
	}
}