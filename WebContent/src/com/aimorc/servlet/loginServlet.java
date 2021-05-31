package com.aimorc.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.aimorc.postgreSQL.PostgressDBOperations;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebServlet("/loginServlet")
public class loginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	ServletOutputStream sos = null;

	public loginServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession(false);
		if (session != null) {
			System.out.println("get of category Session Id of Login servlet get METHOD:" + session.getId());
			String sessionusername = (String) session.getAttribute("username");

			PostgressDBOperations operationDb = new PostgressDBOperations();

			Map<Object, Map<Object, Object>> output = new HashMap();

			//output = operationDb.homeCategory( );
			
			ObjectMapper objectMapper = new ObjectMapper();

			String jsonstring = objectMapper.writeValueAsString(output.values());
			System.out.println(jsonstring);

			response.getWriter().write(jsonstring); 
		}
	}

	protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(request.getInputStream()));
		String jsonString = "";
		
		if(bufferedReader != null){
			jsonString = bufferedReader.readLine();
		}
		System.out.println("Recieved JSON :"+ jsonString);

		try {
			Object paresedJSONObject = new JSONParser().parse(jsonString);
			JSONObject jsonObject = (JSONObject) paresedJSONObject;
			
			String parsedusername = (String) jsonObject.get("username");
			  
			String parsedpassword = (String) jsonObject.get("password");
			String lastlogin = (String) jsonObject.get("lastlogin");
			
			String 	statusMessage = "";
			
			PostgressDBOperations operations = new PostgressDBOperations();

				if (operations.validateAccountWithUsernamePassword(parsedusername, parsedpassword)) {
					statusMessage = "validusernameandpassword";
					
					operations.updateLastlogin(parsedusername, lastlogin);

					HttpSession session = request.getSession();
					session.setAttribute("username", parsedusername);
					session.setAttribute("password", parsedpassword);
					String sessionusername = (String) session.getAttribute("username");
					String password = (String) session.getAttribute("password");
					System.out.println(sessionusername);
					System.out.println(password);
					System.out.println("Login Session Id:" + session.getId());
					
					try {
						response.setStatus(200);

					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {
					 response.sendError(401, "Invalid Credentials" );
					 System.out.println("Invalid");
				}
									
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
