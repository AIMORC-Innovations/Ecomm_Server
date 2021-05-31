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
import com.fasterxml.jackson.databind.ObjectMapper;

@WebServlet("/securityQuestionLoginChangeServlet")
public class securityQuestionLoginChangeServlet extends HttpServlet {
private static final long serialVersionUID = 1L;
public securityQuestionLoginChangeServlet() {
super();
}

protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
response.getWriter().write("welcome to DoGet of securityQuestionLoginChangeServlet");
}

protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(request.getInputStream()));
String jsonString = "";
if(bufferedReader != null){
jsonString = bufferedReader.readLine();
}
System.out.println("Recieved JSON :"+ jsonString);
HttpSession session = request.getSession(false);
if (session != null) {
System.out.println("Changepassword Session Id:" + session.getId());
String sessionusername = (String) session.getAttribute("username");
try {
Object paresedJSONObject = new JSONParser().parse(jsonString);
JSONObject jsonObject = (JSONObject) paresedJSONObject;
String parsedoldpassword = (String) jsonObject.get("oldpassword");
String parsedpassword = (String) jsonObject.get("newpassword");
if(jsonString.contains("security_id") == false) {
/* / -------------------------------loginchangeservlet-------------- / */
String statusMessage = "";
PostgressDBOperations changepassword = new PostgressDBOperations();
if(changepassword.loginPasswordCheck(parsedoldpassword, sessionusername)) {
System.out.println("Password Matched");
if( changepassword.loginPasswordCheck(parsedpassword, sessionusername))
{
System.out.println("New password is same as old password, please give another new password");
response.sendError(409, "New Password is same is old Password" );
}
else {
changepassword.updatepassword(parsedpassword,sessionusername) ;
System.out.println("Password Updated");
response.setStatus(200);
}
}
else 
{
	System.out.println("Password Not Found"); response.sendError(401, "Invalid Password" );
	}
 }
else {
/*/ -------------------------------loginsecurityquestion-------------- /*/
String parsedusername = (String) session.getAttribute("username");
int parsedsecurity_id = Integer.parseInt((String) jsonObject.get("security_id"));
String parsedsecurity_answer = (String) jsonObject.get("security_answer");
String statusMessage = "";
PostgressDBOperations password = new PostgressDBOperations();
if(password.forgotpassword(parsedusername, parsedsecurity_id, parsedsecurity_answer))
{
statusMessage = "validUsername For Provided Question";
System.out.println("ValidUsername For Provided Question");
RequestDispatcher dispatcher = request.getRequestDispatcher("loginchangepassword.jsp");
dispatcher.forward(request, response);
}
else {
response.sendError(401, "Invalid Credentials" );
System.out.println("Invalid Answer For Provided Question");
}
}
}
catch (Exception e) {
e.printStackTrace();
}
}
}
}