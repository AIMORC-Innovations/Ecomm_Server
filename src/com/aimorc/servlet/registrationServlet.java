package com.aimorc.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import com.aimorc.postgreSQL.PostgressDBOperations;

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
            String parsedusername = (String) jsonObject.get("username");
			String parsedpassword = (String) jsonObject.get("password");
		   PostgressDBOperations operations = new PostgressDBOperations();
			String statusMessage = "";
                if (operations.validateAccountWithUsernamePassword(parsedusername, parsedpassword)) {
				 response.sendError(409, "Resource already exists");
					 System.out.println("  User already Exists");
					 }
			
		else {
				operations.loginUserAccount(jsonObject);
				 operations.registerUserAccount(jsonObject);
				 statusMessage = "User Succecssfully registered!";
				response.getWriter().write(statusMessage);
				 response.setStatus(200);
				 System.out.println("  User Succecssfully registered!");
			}
			 } catch (Exception e) {
			e.printStackTrace();
		}
	}
}
