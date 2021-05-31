package com.aimorc.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.aimorc.postgreSQL.PostgressDBOperations;
import com.fasterxml.jackson.databind.ObjectMapper;
//import com.google.gson.Gson;

@WebServlet("/categoryServlet")
public class categoryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public categoryServlet() {
		super();

	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession(false);
		if (session != null) {
			System.out.println("cartdispServlet Session Id GET METHOD:" + session.getId());

			String sessionusername = (String) session.getAttribute("username");
			PostgressDBOperations operations = new PostgressDBOperations();

			try {

				Map<String, Object> output = new HashMap();

				output = operations.displayOrdersbasedoncategory(sessionusername, 1);

				ObjectMapper objectMapper = new ObjectMapper();
				System.out.println(" output is" + output);

				String jsonstring = objectMapper.writeValueAsString(output.values());

				response.getWriter().write(jsonstring);
			}
			catch (Exception e) {

				e.printStackTrace();
			}
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(request.getInputStream()));
		String jsonString = "";

		if (bufferedReader != null) {
			jsonString = bufferedReader.readLine();
		}

		System.out.println("Received JSON :" + jsonString);

		HttpSession session = request.getSession(false);
		if (session != null) {
			System.out.println("CategoryServlet Session Id of POST METHOD:" + session.getId());

			String sessionusername = (String) session.getAttribute("username");
			try {
				Object paresedJSONObject = new JSONParser().parse(jsonString);
				JSONObject jsonObject = (JSONObject) paresedJSONObject;
				
				String date = (String) jsonObject.get("date");
				System.out.println("entered");
				
				PostgressDBOperations dateselector = new PostgressDBOperations();
				
				if (dateselector.Datepicker(date, sessionusername)) {

					System.out.println("CategoryServlet POST date and userid inserted Successfully");
					response.setStatus(200);
				}

				else {
					response.sendError(304, "Failure");
					System.out.println("date cannot be inserted");
				}

			} catch (Exception e) {

				e.printStackTrace();
			}
		}
	}
}
