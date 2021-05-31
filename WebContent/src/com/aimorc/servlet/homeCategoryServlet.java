package com.aimorc.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import java.sql.SQLException;
import java.util.HashMap;
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
import org.json.simple.parser.ParseException;

import com.aimorc.postgreSQL.PostgressDBOperations;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebServlet("/homeCategoryServlet")
public class homeCategoryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public homeCategoryServlet() {
		super();

	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession(false);
		
		if (session != null) {
			System.out.println("homeCategoryServlet Session Id GET METHOD:" + session.getId());

			String sessionusername = (String) session.getAttribute("username");
			session.setAttribute("category_id", 0);
			PostgressDBOperations operations = new PostgressDBOperations();

			try {
				Map<Object, Map<Object, Object>> output = new HashMap();

				output = operations.homeCategoryDisplay(sessionusername);
				ObjectMapper objectMapper = new ObjectMapper();

				String jsonstring = objectMapper.writeValueAsString(output.values());
				System.out.println(jsonstring);

				response.getWriter().write(jsonstring);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String jsonString = "";
		try
		{
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(request.getInputStream()));

			if (bufferedReader != null) {
				jsonString = bufferedReader.readLine();
			}
		}catch(IOException e) {
			e.printStackTrace();
		}

		HttpSession session = request.getSession(false);
		if (session != null) {
			System.out.println("homeCategoryServlet Session Id of POST METHOD:" + session.getId());
			
			String sessionusername = (String) session.getAttribute("username");

			PostgressDBOperations operationDb = new PostgressDBOperations();

			try {
				Object paresedJSONObject = new JSONParser().parse(jsonString);
				JSONObject jsonObject = (JSONObject) paresedJSONObject;
				System.out.println("homeCategoryServlet jsonObject:" + jsonObject);

						/*----------------------------------home servlet------------------------*/

						int parsedcategory_id = Integer.parseInt((String) jsonObject.get("category_id"));
						session.setAttribute("category_id", parsedcategory_id);

						System.out.println("homeCategoryServlet product_id:" + parsedcategory_id);
						
						Map<Object, Map<Object, Object>> output = new HashMap();

						output = operationDb.homeCategory(sessionusername, parsedcategory_id);
						ObjectMapper objectMapper = new ObjectMapper();

						String jsonstring = objectMapper.writeValueAsString(output.values());
						System.out.println(jsonstring);

						response.getWriter().write(jsonstring);
					
			} catch (ParseException e) {

				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}
	}
}