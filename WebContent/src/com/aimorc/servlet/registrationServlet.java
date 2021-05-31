package com.aimorc.servlet;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import com.aimorc.postgreSQL.PostgressDBOperations;
import com.sun.net.httpserver.HttpExchange;

@WebServlet("/registrationServlet")
public class registrationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public registrationServlet() {
		super();
	}
    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
           response.getWriter().write("Welcome to DoGet of Registration Servlet");

	}
    @Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(request.getInputStream()));
		String jsonString = "";
		if (bufferedReader != null) {
			jsonString = bufferedReader.readLine();
			}
		System.out.println("Received JSON :"  +  jsonString );
		try {
			Object paresedJSONObject = new JSONParser().parse(jsonString);
			JSONObject jsonObject = (JSONObject) paresedJSONObject;
            String username = (String) jsonObject.get("username");
			String parsedpassword = (String) jsonObject.get("password");
		   PostgressDBOperations operations = new PostgressDBOperations();
			String statusMessage = "";
                if (operations.validateAccountWithUsernamePassword(username, parsedpassword)) {
				 response.sendError(409, "Resource already exists");
					 System.out.println("  User already Exists");
					 }
			
		else {
				operations.loginUserAccount(jsonObject);
				 operations.registerUserAccount(jsonObject);
				 statusMessage = "User Succecssfully registered!";
				 String message = "Welcome from AIMORC Innovations, You are Successfully registered for our Portal. Thank you!";
					String subject= "Confirm Registration";
					String from = "aimorc.ecomm@gmail.com";
					sendEmail(message,subject,username,from);
				response.getWriter().write(statusMessage);
				 response.setStatus(200);
				 System.out.println("  User Succecssfully registered!");
			}
			 } catch (Exception e) {
			e.printStackTrace();
		}
	}
    private static void sendEmail(String message, String subject, String username, String from)
    {

		//variable for gmail
		String host="smtp.gmail.com";
       //get system properties
		Properties properties = System.getProperties();
		System.out.println("Properties" +properties);
        //host set
		properties.put("mail.smtp.host",host);
		properties.put("mail.smtp.port","465");
		properties.put("mail.smtp.ssl.enable","true");// for security
		properties.put("mail.smtp.auth","true");// for aunthenication
        //getting sesssion object
		Session session = Session.getInstance(properties,new Authenticator()
		{
                protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication ("aimorc.ecomm@gmail.com", "AIMORC@15");
			}

				
		});
		session.setDebug(true);
        //compose message
        MimeMessage mimemessage = new MimeMessage(session);
         try {
			//from email
			mimemessage.setFrom(from);
            //recepient email
			mimemessage.addRecipient(Message.RecipientType.TO, new InternetAddress(username));
            //adding subject to message
			mimemessage.setSubject(subject);
            //adding textmessage
			mimemessage.setText(message);
			//send
			Transport.send(mimemessage);
			System.out.println("Sent successfully...........");
} catch (MessagingException e)
         {
	e.printStackTrace();
		}
}
}
