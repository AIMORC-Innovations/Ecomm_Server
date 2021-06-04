package com.aimorc.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.Base64;


import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;


import com.aimorc.postgreSQL.PostgressDBOperations;

@WebServlet("/loginServlet")
public class loginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;


	public loginServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.getWriter().write("Welcome to DoGet of Login Servlet");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(request.getInputStream()));
		String jsonString = "";
		if (bufferedReader != null) {
			jsonString = bufferedReader.readLine();
		}
		System.out.println("Recieved JSON :" + jsonString);
		PrintWriter out = response.getWriter();
		try {
			Object paresedJSONObject = new JSONParser().parse(jsonString);
			JSONObject jsonObject = (JSONObject) paresedJSONObject;
			String username = (String) jsonObject.get("username");
			String password = (String) jsonObject.get("password");
			String lastlogin = (String) jsonObject.get("lastlogin");
			String statusMessage = "";




		String encryptedpassword = getEncodedString(password);
		System.out.println("encryptedpassword = " + encryptedpassword);

			PostgressDBOperations operations = new PostgressDBOperations();
			if (operations.validateAccountWithUsernamePassword(username,encryptedpassword)) {
				statusMessage = "User Succesfully Logged in";
				HttpSession session = request.getSession();
				session.setAttribute("username", username);
				String name = (String) session.getAttribute("username");
				System.out.println(name);
				System.out.println("LoginSession Id:" + session.getId());
				response.setStatus(200);
			} else {
				response.sendError(401, "Invalid Credentials");
				System.out.println("Invalid");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String getEncodedString(String password)
	{
	String encrytedpassword = Base64.getEncoder().encodeToString(password.getBytes());
	return encrytedpassword;
	}
}