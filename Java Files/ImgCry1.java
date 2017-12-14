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

public class ImgCry1 extends HttpServlet {  // JDK 1.6 and above only
 
   // The doGet() runs once per HTTP GET request to this servlet.
   @Override
   public void doGet(HttpServletRequest request, HttpServletResponse response)
               throws ServletException, IOException {
      // Set the MIME type for the response message
      response.setContentType("text/html");
      // Get a output writer to write the response message into the network socket
      PrintWriter out = response.getWriter();
	  
	  
	  
	  
	  String a = request.getParameter("img");
	  String b = "C:\\Users\\SMP\\Desktop\\" + a;
	  try {
         out.println("<html>");
         out.println("<head><title>Image Encryption</title></head>");
         out.println("<body>");
         out.println("<h1>Image Encryption</h1>");
         // Echo client's request information
         out.println("<p>Request URL: " + a + "</p>");
         // Generate a random number upon each request
         out.println("<p>Random Number: <strong>" + Math.random() + "</strong></p>");    

	  
	  
		  try{
				FileInputStream file = new FileInputStream(b);
				FileOutputStream outStream = new FileOutputStream("Encrypt.jpg");
				byte k[]="CooL2116NiTh5252".getBytes();
				SecretKeySpec key = new SecretKeySpec(k, "AES");
				Cipher enc = Cipher.getInstance("AES");
				enc.init(Cipher.ENCRYPT_MODE, key);
				CipherOutputStream cos = new CipherOutputStream(outStream, enc);
				byte[] buf = new byte[1024];
				int read;
				while((read=file.read(buf))!=-1){
					cos.write(buf,0,read);
				}
				file.close();
				outStream.flush();
				cos.close();
				out.println("The file encrypted successfully in bin folder");
				out.println("<form method='get' action='http://localhost:9999/password/imgcry2'><br><input type='submit' value='Decrypt'></form>");
			}catch(Exception e){
				out.println(""+ e);
			}
			out.println("</body></html>");
		}finally {
         out.close();  // Always close the output writer
		}
	}
}
