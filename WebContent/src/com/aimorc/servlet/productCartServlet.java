package com.aimorc.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
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
import org.json.simple.parser.ParseException;

import com.aimorc.postgreSQL.PostgressDBOperations;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebServlet("/productCartServlet")
public class productCartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public productCartServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	@SuppressWarnings("unlikely-arg-type")
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, NullPointerException {
		// TODO Auto-generated method stub
		//boolean flag = true;
		HttpSession session = request.getSession(false);
		//if (session != null) {
			System.out.println("productCartServlet Session Id GET METHOD:" + session.getId());
			String username = (String) session.getAttribute("username");
			
			 int parsedcategory_id = (int) session.getAttribute("category_id"); 
			 System.out.println("productCartServlet parsedcategory_id of GET method "+ parsedcategory_id);

			PostgressDBOperations operations = new PostgressDBOperations();

			if(parsedcategory_id == 0) {
			try {
				Map<Object, Map<Object, Object>> output_Info = operations.productinformation();
				//Map<Object, Map<Object, Object>> output_Info = operations.CategoryPageDisplay(parsedcategory_id);
				System.out.println("Map output of info at servlet" + output_Info);
	 
				ObjectMapper mapperObj = new ObjectMapper();
				String jsonString = mapperObj.writeValueAsString(output_Info.values()); 
	
				System.out.println("output_Info get values"  + output_Info.values());
				System.out.println("jsonString  values"  + jsonString);		
				 response.getWriter(). write(jsonString);

			} catch (Exception e ) {
				e.printStackTrace();
			}
		} 
			  else { 
				  try { 
					 
			  //Map<Object, Map<Object, Object>>  output_Info = operations.productinformation(); 
			Map<Object, Map<Object, Object>> output_Info = operations.CategoryPageDisplay(parsedcategory_id);
			 System.out.println("Map output of info at servlet" + output_Info);
			 
			 ObjectMapper mapperObj = new ObjectMapper(); String jsonString =
			 mapperObj.writeValueAsString(output_Info.values());
			 
			 System.out.println("output_Info get values" + output_Info.values());
			  System.out.println("jsonString  values" + jsonString); response.getWriter().
			  write(jsonString);
			  
			 } 
				  catch (Exception e) { e.printStackTrace(); 
			 } 
		}
			 
		//}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(request.getInputStream()));
		String jsonString = "";
		
		if(bufferedReader != null){
			jsonString = bufferedReader.readLine();
		}
		System.out.println("Recieved JSON of productCartServlet POST METHOD :"+ jsonString);
		
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession(false);
		if (session != null) {
			System.out.println("Cart Session Id of POST METHOD:" + session.getId());
			String sessionusername = (String) session.getAttribute("username");
			
			PostgressDBOperations Operation = new PostgressDBOperations();
			
		try { 
			  Object paresedJSONObject = new JSONParser().parse(jsonString);
			  JSONObject jsonObject = (JSONObject) paresedJSONObject;
			  System.out.println("Cart jsonObject inside POST:" + jsonObject);
			  
			  //If for productid
			  if(jsonString.contains("product_id")) {
			try {
			 int parsedproduct_id = Integer.parseInt((String) jsonObject.get("product_id"));
			 int parsedquantity = Integer.parseInt((String) jsonObject.get("quantity"));
			 
			  System.out.println("productCartServlet product_id POST METHOD:" + parsedproduct_id);
			  
			  if(Operation.checkExistingProductid(sessionusername, parsedproduct_id) == true) {
			  
				  Operation.increaseQuantity(sessionusername, parsedproduct_id, parsedquantity);
				  System.out.println("productCartServlet POST qunatity updated Successfully");
					
						response.setStatus(200);
			  }else
			  {
				  Operation.productOrder(sessionusername, parsedproduct_id, parsedquantity);
				  System.out.println("productCartServlet POST quantity and productid inserted Successfully");
				  response.setStatus(200);
			  }
			  
			  } catch ( NumberFormatException e)  {
					 //TODO Auto-generated catch block
				}
		}//if conatins p_ID else for category-id
		else {
			 int parsedcategory_id = Integer.parseInt((String) jsonObject.get("category_id"));
			 
			 session.setAttribute("category_id", parsedcategory_id);
			 System.out.println("parsedcategory_id of POST method"+parsedcategory_id);
			 
			  System.out.println("productCartServlet category_id POST METHOD:" + parsedcategory_id);
		}
			  
			  } catch (ClassNotFoundException | SQLException e)  {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
}