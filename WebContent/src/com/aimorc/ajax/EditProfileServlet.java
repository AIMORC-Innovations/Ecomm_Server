package com.aimorc.ajax;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

@WebServlet("/EditServlet")
public class EditProfileServlet extends HttpServlet {
private static final long serialVersionUID = 1L;

public EditProfileServlet() {
super();

}

protected void doGet(HttpServletRequest request, HttpServletResponse response)
throws ServletException, IOException {

response.getWriter().write("welcome to DoGet of EditServlet");
}

protected void doPost(HttpServletRequest request, HttpServletResponse response)
throws ServletException, IOException {
BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(request.getInputStream()));
String jsonString = "";

if (bufferedReader != null) {
jsonString = bufferedReader.readLine();
}
System.out.println("Recieved JSON :" + jsonString);
PrintWriter out = response.getWriter();
HttpSession session = request.getSession(false);

if (session != null) {
System.out.println("Edit Profile Session Id:" + session.getId());
String username = (String) session.getAttribute("username");

PostgressDBOperations db = new PostgressDBOperations();

try {
Object paresedJSONObject = new JSONParser().parse(jsonString);
JSONObject jsonObject = (JSONObject) paresedJSONObject;

String parsedusername = (String) jsonObject.get("username");
String parsedpassword = (String) jsonObject.get("password");
String parsedfirstname = (String) jsonObject.get("firstname");
String parsedlastname = (String) jsonObject.get("lastname");
String parseddob = (String) jsonObject.get("dob");
String parsedaddress = (String) jsonObject.get("address");
String parsedphonenum = (String) jsonObject.get("phonenum");
String parsedgender = (String) jsonObject.get("gender");

if (db.updateUserProfile(parsedfirstname, parsedlastname, parseddob, parsedaddress,parsedphonenum,parsedgender,username)) {
String statusMessage = "User Profile Updated Successfully";


response.sendError(200, "Success");
System.out.println("Success");
}
} catch (Exception e) {

e.printStackTrace();
}

}
}
}