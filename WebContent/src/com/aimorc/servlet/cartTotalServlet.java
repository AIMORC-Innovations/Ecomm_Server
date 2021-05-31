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


@WebServlet("/cartTotalServlet")
public class cartTotalServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public cartTotalServlet() {
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
				Map<String, Object> wearsMap = new HashMap<String, Object>();
				Map<String, Object> ordersInfoBasedOnCategories = operations.getAllOrdersBasedOnCatgories(sessionusername);
				ObjectMapper objectMapper = new ObjectMapper();
				String jsonstring = objectMapper.writeValueAsString(ordersInfoBasedOnCategories);
				System.out.println(" output jsonstring is" + jsonstring);
				response.getWriter().write(jsonstring);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
	}


}