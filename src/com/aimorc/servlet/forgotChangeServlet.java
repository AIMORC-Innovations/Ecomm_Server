package com.aimorc.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import com.aimorc.postgreSQL.PostgressDBOperations;

@WebServlet("/forgotChangeServlet")
public class forgotChangeServlet extends HttpServlet {
private static final long serialVersionUID = 1L;

public forgotChangeServlet() {
super();
}

@Override
protected void doGet(HttpServletRequest request, HttpServletResponse response)
throws ServletException, IOException {
response.getWriter().write("Welcome to DoGet of Forgot Change Servlet");
}

@Override
protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(request.getInputStream()));
String jsonString = "";
if(bufferedReader != null){
jsonString = bufferedReader.readLine();
}
System.out.println("Recieved JSON :"+ jsonString);
try {
String value = null;
Object paresedJSONObject = new JSONParser().parse(jsonString);
JSONObject jsonObject = (JSONObject) paresedJSONObject;

String parsedpassword = (String) jsonObject.get("password");
System.out.println("Changepassword parsedpassword:" + parsedpassword);
if(jsonString.contains("security_id") == false) {

// -------------------------------changeservlet-------------- /
String statusMessage = "";
HttpSession session = request.getSession(false);
if (session != null) {
System.out.println("Changepassword Session Id:" + session.getId());
String sessionusername = (String) session.getAttribute("username");
System.out.println("Changepassword:" + sessionusername);
PostgressDBOperations changepassword = new PostgressDBOperations();
changepassword.updatepassword(parsedpassword,sessionusername) ;
response.setStatus(200);
System.out.println("Password Updated");
}
else {
response.sendError(401, "Invalid Credentials" );
System.out.println("Password Update Failed");
}
}
// -------------------------------forgotservlet-------------- /
else
{
String parsedusername = (String) jsonObject.get("username");
int parsedsecurity_id = Integer.parseInt((String) jsonObject.get("security_id"));
String parsedsecurity_answer = (String) jsonObject.get("security_answer");
PostgressDBOperations password1 = new PostgressDBOperations();
if(password1.forgotpassword(parsedusername, parsedsecurity_id, parsedsecurity_answer))
{HttpSession session = request.getSession();
session.setAttribute("username", parsedusername);
String name = (String) session.getAttribute("username");
System.out.println(name);
System.out.println("Forgot password Session Id:" + session.getId());
RequestDispatcher dispatcher = request.getRequestDispatcher("changepassword.jsp");
dispatcher.forward(request, response);
}
else {
response.sendError(401, "Invalid Credentials" );
System.out.println("invalidusername for provided question");
}
}
}catch(Exception e)
{
e.printStackTrace();
}
}
}