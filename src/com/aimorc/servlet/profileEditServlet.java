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
import com.aimorc.postgreSQL.PostgressDBOperations;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebServlet("/profileEditServlet")
public class profileEditServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public profileEditServlet() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out = response.getWriter();
        HttpSession session = request.getSession(false);
		if (session != null) {
			System.out.println("Profile Session Id of Get Method:" + session.getId());
			String username = (String) session.getAttribute("username");
            PostgressDBOperations db = new PostgressDBOperations();
try {
				Map output = db.displayProfile(username);
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
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)	throws ServletException, IOException {
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(request.getInputStream()));
		String jsonString = "";
        if (bufferedReader != null) {
			jsonString = bufferedReader.readLine();
		}
		System.out.println("Recieved JSON :" + jsonString);
		PrintWriter out = response.getWriter();
	    HttpSession session = request.getSession(false);
		if (session != null) {
			System.out.println("Profile Session Id of Post Method:" + session.getId());
			String username = (String) session.getAttribute("username");
			PostgressDBOperations db = new PostgressDBOperations();
try {
				Object paresedJSONObject = new JSONParser().parse(jsonString);
				JSONObject jsonObject = (JSONObject) paresedJSONObject;
                String parsedfirstname = (String) jsonObject.get("firstname");
				String parsedlastname = (String) jsonObject.get("lastname");
				String parseddob = (String) jsonObject.get("dob");
				String parsedaddress = (String) jsonObject.get("address");
				String parsedphonenum = (String) jsonObject.get("phonenum");
				String parsedgender = (String) jsonObject.get("gender");
if (db.updateUserProfile(parsedfirstname, parsedlastname, parseddob, parsedaddress, parsedphonenum, parsedgender, username)==1) {
				System.out.println("User Profile Updated successfully!");
					response.setStatus(200);
					System.out.println("Success");
				}
				else {
					response.sendError(304, "Failure");
					System.out.println("Failure");
				}

			} catch (Exception e) {

				e.printStackTrace();
			}
	}
	}
}