 package com.aimorc.ajax;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;

@WebServlet("/ProfileServlet")
public class ProfileServlet extends HttpServlet {
private static final long serialVersionUID = 1L;

public ProfileServlet() {
super();

}
protected void doGet(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {

	response.getWriter().write("welcome to DoGet of ProfileServlet");

}
protected void doPost(HttpServletRequest request, HttpServletResponse response)
throws ServletException, IOException {

PrintWriter out = response.getWriter();

HttpSession session = request.getSession(false);
if (session != null) {
System.out.println(" Profile Session Id:" + session.getId());
String username = (String) session.getAttribute("username");
// out.print("<h1> Welcome" + username + "</h1>");
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
}

//response.setContentType("application/json"); // Set content type of the response so that jQuery knows
//what it can expect.
//response.setCharacterEncoding("UTF-8"); // You want world domination, huh?
//response.getWriter().write("RETurn DATA");
//response.sendRedirect("profile.jsp");
//response.getWriter().append("Served at: ").append(request.getContextPath());

//System.out.println(jsonString);
//RequestDispatcher rd=request.getRequestDispatcher("profile.jsp");
//rd.forward(request, response);
//HttpSession session = request.getSession();

//RequestDispatcher dispatcher = request.getRequestDispatcher("home.jsp");
//dispatcher.forward(request, response);
