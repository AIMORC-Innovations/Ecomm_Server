package com.aimorc.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
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


@WebServlet("/schedulerServlet")
public class schedulerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;


	public schedulerServlet() {
		super();
	}


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if (session != null) {
			System.out.println("CategoryServlet Session Id of Get METHOD:" + session.getId());
			String username = (String) session.getAttribute("username");
			PostgressDBOperations dateselector = new PostgressDBOperations();
			try {
				Map output = dateselector.fetchdate(username);
				System.out.println(output);
				ObjectMapper mapperObj = new ObjectMapper();

				String jsonString = mapperObj.writeValueAsString(output);
				System.out.println(jsonString);

				response.getWriter().write(jsonString);

			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}

		}
	}


	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(request.getInputStream()));
		String jsonString = "";

		if (bufferedReader != null) {
			jsonString = bufferedReader.readLine();
		}

		System.out.println("Received JSON :" + jsonString );

		HttpSession session = request.getSession(false);
		if (session != null) {
			System.out.println("SchedulerServlet Session Id of POST METHOD:" + session.getId());

			String sessionusername = (String) session.getAttribute("username");
			try {
				Object paresedJSONObject = new JSONParser().parse(jsonString);
				JSONObject jsonObject = (JSONObject) paresedJSONObject;
				String date = (String) jsonObject.get("date");

				PostgressDBOperations dateselector = new PostgressDBOperations();
				if(jsonString.contains("date")) {
					try {
						String parseddate = (String) jsonObject.get("date");

						if(dateselector.updatedatetime(date,sessionusername)==1) {

							System.out.println("SchedulerServlet POST date and userid updated Successfully");
							response.setStatus(200);
						}else {

							dateselector.Datepicker(date, sessionusername);
							System.out.println("SchedulerServlet POST date and userid insereted Successfully");
						}

					}catch (NumberFormatException e) {

						e.printStackTrace();
					}

				}
				else
				{
					dateselector.cancelpickup(sessionusername);
				}

			}catch (Exception e) {

				e.printStackTrace();
			}
		}
	}
}