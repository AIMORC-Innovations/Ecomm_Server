package com.aimorc.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.aimorc.postgreSQL.PostgressDBOperations;

@WebServlet("/forgotChangeServlet")
public class forgotChangeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public forgotChangeServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().write("welcome to DoGet of loginServlet");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(request.getInputStream()));
		String jsonString = "";
		if(bufferedReader != null){
			jsonString = bufferedReader.readLine();
		}
		System.out.println("Recieved JSON :"+ jsonString);

		try {
			 System.out.println("forgotChangeServlet Entering into try :"); 

			Object paresedJSONObject = new JSONParser().parse(jsonString);
			JSONObject jsonObject = (JSONObject) paresedJSONObject;
			
			System.out.println("jsonString.contains"  + jsonString.contains("security_id") );
			
			String parsedpassword = (String) jsonObject.get("password");
			System.out.println("forgotChangeServlet parsedpassword:" + parsedpassword);
			
			
			if(jsonString.contains("security_id") == false) {
				System.out.println("forgotChangeServlet Entering into if password :"); 
				/* -------------------------------changeservlet-------------- */
				String statusMessage = "";

				HttpSession session = request.getSession(false);
				if (session != null) {
					System.out.println("forgotChangeServlet Session Id:" + session.getId());
					String sessionusername = (String) session.getAttribute("username");
					System.out.println("forgotChangeServlet:" + sessionusername);

					PostgressDBOperations changepassword = new PostgressDBOperations();
					changepassword.updatepassword(parsedpassword,sessionusername) ; 

					changepassword.fetchUserId(sessionusername);
					
					response.setStatus(200);
					System.out.println("Password Updated");
				}
				else {

					response.sendError(401, "Invalid Credentials" );
					System.out.println("Password Update Failed");
				}
				 }else 
					/* -------------------------------forgotservlet-------------- */
			 {
				 String parsedusername = (String) jsonObject.get("username");
					int parsedsecurity_id = Integer.parseInt((String) jsonObject.get("security_id"));
					String parsedsecurity_answer = (String) jsonObject.get("security_answer");
					
					String statusMessage = "";
								
					PostgressDBOperations password1 = new PostgressDBOperations();

					if(password1.forgotpassword(parsedusername, parsedsecurity_id, parsedsecurity_answer))
					{
						statusMessage = "validusername for provided question";
						
						HttpSession session = request.getSession();
						session.setAttribute("username", parsedusername);
						String sessionusername = (String) session.getAttribute("username");
						System.out.println(sessionusername);
						System.out.println("forgotChangeServlet Session Id:" + session.getId());
						
						RequestDispatcher dispatcher = request.getRequestDispatcher("changepassword.jsp");
						 dispatcher.forward(request, response);
					}
					else {
						 response.sendError(401, "Invalid Credentials" );
						 System.out.println("invalidusername for provided question");
					}
			 }
		}catch(Exception e)
		{
		// TODO Auto-generated catch block
		e.printStackTrace();
		}
	}
}