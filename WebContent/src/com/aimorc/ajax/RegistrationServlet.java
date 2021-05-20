package com.aimorc.ajax;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class RegistrationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public RegistrationServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.getWriter().write("welcome to DoGet of LoginServlet");

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(request.getInputStream()));
		String jsonString = "";
		if (bufferedReader != null) {
			jsonString = bufferedReader.readLine();
		}
		System.out.println("Received JSON :" + jsonString);
		try {

			// JSON Parsing..
			Object paresedJSONObject = new JSONParser().parse(jsonString);
			JSONObject jsonObject = (JSONObject) paresedJSONObject;

			String parsedusername = (String) jsonObject.get("username");
			String parsedpassword = (String) jsonObject.get("password");
			String parsedfirstname = (String) jsonObject.get("firstname");
			String parsedlastname = (String) jsonObject.get("lastname");
			String parseddob = (String) jsonObject.get("dob");
			String parsedgender = (String) jsonObject.get("gender");
			String parsedphonenumber = (String) jsonObject.get("phonenumber");
			String parsedaddress = (String) jsonObject.get("address");

			PostgressDBOperations operations = new PostgressDBOperations();
			String statusMessage = "";

			if (operations.checkIfUsernameExist(parsedusername)) {
				if (operations.validateAccountWithUsernamePassword(parsedusername, parsedpassword)) {
					
					response.sendError(409, "Resource already exists");
					System.out.println("User already Exists");
				} 
			} else {
				operations.loginUserAccount(jsonObject);
				operations.registerUserAccount(jsonObject);
				
				statusMessage = "User Succecssfully registered!";
				response.getWriter().write(statusMessage);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
