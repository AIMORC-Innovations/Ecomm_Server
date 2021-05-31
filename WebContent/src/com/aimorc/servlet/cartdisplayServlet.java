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
//import com.google.gson.Gson;

@WebServlet("/cartdisplayServlet")
public class cartdisplayServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public cartdisplayServlet() {
		super();

	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession(false);
		if (session != null) {
			System.out.println("cartdispServlet Session Id GET METHOD:" + session.getId());

			String sessionusername = (String) session.getAttribute("username");
			
			//session.setAttribute("category_id", 0);
			
			PostgressDBOperations operations = new PostgressDBOperations();

			try {

				Map<String, Object> wearsMap = new HashMap<String, Object>();
//				Map<String, Object> westernWearMap = operations.displayOrdersbasedoncategory(sessionusername, 2);
//				Map<String, Object> indianWearMap = operations.displayOrdersbasedoncategory(sessionusername, 1);
				
//				wearsMap.put("westernWear", westernWearMap);
//				wearsMap.put("indianWear", indianWearMap);
				
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

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String jsonString = "";
		try {
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(request.getInputStream()));

			if (bufferedReader != null) {
				jsonString = bufferedReader.readLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		HttpSession session = request.getSession(false);
		if (session != null) {
			System.out.println("cartDispDecreQuanRemItemServlet Session Id of POST METHOD:" + session.getId());
			String sessionusername = (String) session.getAttribute("username");

			PostgressDBOperations operationDb = new PostgressDBOperations();

			try {
				Object paresedJSONObject = new JSONParser().parse(jsonString);
				JSONObject jsonObject = (JSONObject) paresedJSONObject;
				System.out.println("cartDispDecreQuanRemItemServlet jsonObject:" + jsonObject);

				try {
					if (jsonString.contains("quantity") == true) {

						/*----------------------------------cartDispDecreQuanRemItemServlet------------------------*/

						int parsedproductId = Integer.parseInt((String) jsonObject.get("product_id"));
						int parsedquantity = Integer.parseInt((String) jsonObject.get("quantity"));

						System.out.println("cartDispDecreQuanRemItemServlet product_id:" + parsedproductId);

						if (operationDb.decreaseQuantity(sessionusername, parsedproductId, parsedquantity) == 1) {
							response.setStatus(200);
							System.out.println(
									"\n cartDispDecreQuanRemItemServlet : Product Decremented and updated Successfully");

						} else {
							response.sendError(409, "Product not Decremented ");
							System.out.println("\n cartDispDecreQuanRemItemServlet : Product not Decremented");
						}
					} else {

						/*----------------------------------RemoveItemServlet------------------------*/

						int parsedproductId = Integer.parseInt((String) jsonObject.get("product_id"));
						System.out.println("RemoveItemServlet product_id :" + parsedproductId);

						if (operationDb.removeProductOrder(parsedproductId) == 1) {

							response.setStatus(200);
							System.out.println("Removed successfully");

							RequestDispatcher dispatcher = request.getRequestDispatcher("cartdisplay.jsp");
							dispatcher.forward(request, response);
						} else {
							response.sendError(409, "Product not Removed ");
							System.out.println("\n RemoveItemServlet : Product not Removed");
						}
					}
				} catch (NumberFormatException e) {
					e.printStackTrace();
				}
			} catch (ClassNotFoundException | SQLException e) {

				e.printStackTrace();
			} catch (ParseException e1) {

				e1.printStackTrace();
			}

		}

	}
}