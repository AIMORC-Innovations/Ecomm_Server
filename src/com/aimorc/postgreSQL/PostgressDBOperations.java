package com.aimorc.postgreSQL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class PostgressDBOperations {

Connection connection = null;
PreparedStatement preparedStatement = null;
ResultSet resultSet = null;
int userid = 0;
int x;
//<!--------------------- ----------------closing resources------------------------------ -->
public void closeDb() {
System.out.println("Inside Close DB");

try {

if (null != resultSet) {
resultSet.close();
}

if (null != preparedStatement) {

preparedStatement.close();
}

if (null != connection) { 
connection.close();
}
} catch (SQLException sqlex) {
sqlex.printStackTrace();
}
}
// <!--------------------- ----------------user and password validation check------------------------------ -->
public boolean validateAccountWithUsernamePassword(String parsedusername, String parsedpassword)
throws ClassNotFoundException, SQLException {
try {
connection = JDBCPostgreSQLConnect.getConnection();
preparedStatement = connection.prepareStatement("select * from login where username = ? and password = ?");
preparedStatement.setString(1, parsedusername);
preparedStatement.setString(2, parsedpassword);
resultSet = preparedStatement.executeQuery();
System.out.print("username " + parsedusername);
while (resultSet.next()) {
return true;
}
} catch (SQLException sqlex) {
sqlex.printStackTrace();
} finally {

closeDb();
}
return false;
}

//<!--------------------- ----------------display date---------------------------------------- -->

@SuppressWarnings("null")
public Map fetchdate(String username) throws ClassNotFoundException, SQLException {
try {
connection = JDBCPostgreSQLConnect.getConnection();
userid = fetchUserId(username);
preparedStatement = connection.prepareStatement(
"select date from date where userid=?");
preparedStatement.setInt(1, userid);
resultSet = preparedStatement.executeQuery();

while (resultSet.next()) {
String date = resultSet.getString(1);
Object objdate = date ;
Map<Object, Object> individualdate = new HashMap<Object, Object>();
individualdate .put("date", objdate);
System.out.println("The date is: " + individualdate);
return individualdate;
}
} catch (SQLException sqlex) {
	sqlex.printStackTrace();
	}
finally {
	
	closeDb();
	}
	return null;// fail
	}

//<!--------------------- ----------------update date------------------------------ -->
@SuppressWarnings("null")
public int updatedatetime(String date, String username)
throws ClassNotFoundException, SQLException {
try {
connection = JDBCPostgreSQLConnect.getConnection();
userid = fetchUserId(username);
resultSet = preparedStatement.executeQuery();
int userid = 0;
while (resultSet.next()) {
userid = resultSet.getInt(1);
}
preparedStatement = connection.prepareStatement("update date set date=? where userid = ?");
preparedStatement.setString(1, date);
preparedStatement.setInt(2, userid);
return preparedStatement.executeUpdate();
} catch (SQLException sqlex) {
sqlex.printStackTrace();
}
finally {
closeDb();
}
return -1; 
}

// <!--------------------- ----------------update user profile------------------------------ -->
@SuppressWarnings("null")
public int updateUserProfile(String parsedfirstname, String parsedlastname, String parseddob,
String parsedaddress, String parsedphonenum, String parsedgender, String username)
throws ClassNotFoundException, SQLException {
try {
connection = JDBCPostgreSQLConnect.getConnection();
userid = fetchUserId(username);
resultSet = preparedStatement.executeQuery();
int userid = 0;
while (resultSet.next()) {
userid = resultSet.getInt(1);
}
preparedStatement = connection.prepareStatement("update registration set firstname=?, lastname=?,"
+ " dob=? , address=?, phonenum=?, gender=? where userid = ?");
preparedStatement.setString(1, parsedfirstname);
preparedStatement.setString(2, parsedlastname);
preparedStatement.setString(3, parseddob);
preparedStatement.setString(4, parsedaddress);
preparedStatement.setString(5, parsedphonenum);
preparedStatement.setString(6, parsedgender);
preparedStatement.setInt(7, userid);
return preparedStatement.executeUpdate();
} catch (SQLException sqlex) {
sqlex.printStackTrace();
}
finally {
closeDb();
}
return -1; 
}

// <!--------------------- ----------------forgot password login security question------------------------------ -->
@SuppressWarnings("null")
public boolean forgotpassword(String sessionusername, int parsedsecurity_id, String parsedsecurityAnswer)
throws ClassNotFoundException, SQLException {
try {
connection = JDBCPostgreSQLConnect.getConnection();
userid = fetchUserId(sessionusername);
preparedStatement = connection.prepareStatement(
"select * from registration where security_id = ? and security_answer = ? and userid = ?");
preparedStatement.setInt(1, parsedsecurity_id);
preparedStatement.setString(2, parsedsecurityAnswer);
preparedStatement.setInt(3, userid);
resultSet = preparedStatement.executeQuery();
System.out.println("Securityquestion for forgot password is : " + parsedsecurity_id
+ " has been recieved successfully!");
while (resultSet.next()) {
return true;
}
} catch (SQLException sqlex) {
sqlex.printStackTrace();
}
finally {
closeDb();
}
return false; 
}

//<!--------------------- ----------------home page category display-------------------------------->
public Map<Object, Map<Object, Object>> CategoryPageDisplay(int category_id)throws ClassNotFoundException, SQLException {
try {
connection = JDBCPostgreSQLConnect.getConnection();
String query = " select p.product_id, p.product_name , p.product_description , p.product_price, c.category_name, c.category_id from \r\n"
+ " product p join category c on c.category_id = p.category_id where p.category_id = ? ";
preparedStatement = connection.prepareStatement(query);
preparedStatement.setInt(1, category_id);
resultSet = preparedStatement.executeQuery();
Map<Object, Map<Object, Object>> categoryProducts = new HashMap<Object, Map<Object, Object>>();
while (resultSet.next()) {
String productId = Integer.toString(resultSet.getInt(1));
Object objId = productId;
String productName = resultSet.getString(2);
Object objName = productName ;
String productDescription = resultSet.getString(3);
Object objDes = productDescription;
float productPrice = resultSet.getFloat(4);
Object objPrice = productPrice;
String category_name = resultSet.getString(5);
Object objCategory_Name = category_name ;
String categoryid = Integer.toString(resultSet.getInt(6));
Object objCategory_id =  categoryid ;
Map<Object, Object> individualProduct = new HashMap<Object, Object>();
individualProduct.put("product_id", objId);
individualProduct.put("product_name", objName);
individualProduct.put("product_description", objDes);
individualProduct.put("product_price", objPrice );
individualProduct.put("category_name", objCategory_Name);
individualProduct.put("category_id", objCategory_id);
categoryProducts.put(productId, individualProduct);
}

System.out.println("The categoryProducts is: " + categoryProducts);
return categoryProducts;
} catch (SQLException sqlex) {
sqlex.printStackTrace();
}
finally {
closeDb();
}
return null;
}


//<!--------------------- ----------------home page to view category -------------------------------->
public Map<Object, Map<Object, Object>> homeCategory( String sessionusername, int category_id)throws ClassNotFoundException, SQLException {

try {
connection = JDBCPostgreSQLConnect.getConnection();
String query = "select product_id, product_name , product_description , product_price, category_id from product"
+ " where category_id = ?";
preparedStatement = connection.prepareStatement(query);
preparedStatement.setInt(1, category_id);
resultSet = preparedStatement.executeQuery();
Map<Object, Map<Object, Object>> categoryProducts = new HashMap<Object, Map<Object, Object>>();
while (resultSet.next()) {
String productId = Integer.toString(resultSet.getInt(1));
Object objId = productId;
String productName = resultSet.getString(2);
Object objName = productName ;
String productDescription = resultSet.getString(3);
Object objDes = productDescription;
float productPrice = resultSet.getFloat(4);
Object objPrice = productPrice;
int cateogryid = resultSet.getInt(5);
Object  objcid  = cateogryid;
Map<Object, Object> individualProduct = new HashMap<Object, Object>();
individualProduct.put("product_id", objId);
individualProduct.put("product_name", objName);
individualProduct.put("product_description", objDes);
individualProduct.put("product_price", objPrice );
individualProduct.put("cateogry_id", objcid  );
categoryProducts.put(productId, individualProduct);
}
System.out.println("The categoryProducts is: " + categoryProducts);
return categoryProducts;
} catch (SQLException sqlex) {
sqlex.printStackTrace();
}
finally {
closeDb();
}
return null;
}

//<!--------------------- ----------------home page category display-------------------------------->
public Map<Object, Map<Object, Object>> homeCategoryDisplay( String sessionusername)throws ClassNotFoundException, SQLException {

try {
connection = JDBCPostgreSQLConnect.getConnection();
String displayCategory = "select * from category";
preparedStatement = connection.prepareStatement(displayCategory);
resultSet = preparedStatement.executeQuery();
Map<Object, Map<Object, Object>> entireCatogery = new HashMap<Object, Map<Object, Object>>();
while (resultSet.next()) {
String categoryId = Integer.toString(resultSet.getInt(1));
Object objId = categoryId ;
String categoryName = resultSet.getString(2);
Object objName = categoryName;
String categoryDescription = resultSet.getString(3);
Object objDes = categoryDescription;
Map<Object, Object> individualCatogery = new HashMap<Object, Object>();
individualCatogery.put("category_id", objId);
individualCatogery.put("category_name", objName);
individualCatogery.put("category_description", objDes);
entireCatogery.put(categoryId, individualCatogery);
}
System.out.println("The entireCatogery is: " + entireCatogery);
return entireCatogery;
} catch (SQLException sqlex) {
sqlex.printStackTrace();
}
finally {
closeDb();
}
return null;
}


// <!--------------------- ----------------Retrieve Product information from DB------------------------------ -->
@SuppressWarnings("null")
public Map<Object, Map<Object, Object>> productinformation() throws ClassNotFoundException, ClassCastException, SQLException {

try {
connection = JDBCPostgreSQLConnect.getConnection();
preparedStatement = connection.prepareStatement(
"select product_id, product_name , product_description , product_price from product");
resultSet = preparedStatement.executeQuery();
Map<Object, Map<Object, Object>> registerProducts = new HashMap<Object, Map<Object, Object>>();
while (resultSet.next()) {
String productId = Integer.toString(resultSet.getInt(1));
Object objId = productId;
String productName = resultSet.getString(2);
Object objName = productName ;
String productDescription = resultSet.getString(3);
Object objDes = productDescription;
float productPrice = resultSet.getFloat(4);
Object objPrice = productPrice;
Map<Object, Object> individualProduct = new HashMap<Object, Object>();
individualProduct.put("product_id", objId);
individualProduct.put("product_name", objName);
individualProduct.put("product_description", objDes);
individualProduct.put("product_price", objPrice );
registerProducts.put(productId, individualProduct);
}
System.out.println("The registerProducts is: " + registerProducts);
return registerProducts;
} catch (SQLException sqlex) {
sqlex.printStackTrace();
}
finally {
closeDb();
}
return null;
}

// <!--------------------- ----------------update new password in login table------------------------------ -->
public int updatepassword(String parsedpassword, String sessionusername)
throws ClassNotFoundException, SQLException {

try {
connection = JDBCPostgreSQLConnect.getConnection();
String updatelastlogin = "UPDATE login SET password = ? WHERE username = ?";
preparedStatement = connection.prepareStatement(updatelastlogin);
preparedStatement.setString(1, parsedpassword);
preparedStatement.setString(2, sessionusername);
return preparedStatement.executeUpdate();
} catch (SQLException sqlex) {
sqlex.printStackTrace();
}
finally {
closeDb();
}
return 1;
}

// <!--------------------- ----------------password update with old password check------------------------------ -->
public boolean loginPasswordCheck(String parsedoldpassword, String sessionusername)
throws ClassNotFoundException, SQLException {
try {
connection = JDBCPostgreSQLConnect.getConnection();
preparedStatement = connection.prepareStatement("select * from login where password=? and username =?");
preparedStatement.setString(1, parsedoldpassword);
preparedStatement.setString(2, sessionusername);
resultSet = preparedStatement.executeQuery();
while (resultSet.next()) {
return true;
}
} catch (SQLException sqlex) {
sqlex.printStackTrace();
}
finally {
closeDb();
}
return false;
}

// <!--------------------- ----------------fetch user Id ------------------------------ -->
@SuppressWarnings("null")
public int fetchUserId(String sessionusername) throws ClassNotFoundException, SQLException {
try {
connection = JDBCPostgreSQLConnect.getConnection();
preparedStatement = connection.prepareStatement("select userid from login where username = ?");
preparedStatement.setString(1, sessionusername);
resultSet = preparedStatement.executeQuery();
while (resultSet.next()) {
userid = resultSet.getInt(1);
}
return userid;
} catch (SQLException sqlex) {
sqlex.printStackTrace();
}
finally {
}
return 0;
}

// <!--------------------- ----------------update last login time in login table------------------------------ -->
public int updateLastlogin(String parsedusername, String lastlogin) throws ClassNotFoundException, SQLException {

try {
connection = JDBCPostgreSQLConnect.getConnection();
String updatelastlogin = "UPDATE login SET lastlogin = ? WHERE username = ?";
preparedStatement = connection.prepareStatement(updatelastlogin);
preparedStatement.setString(1, lastlogin);
preparedStatement.setString(2, parsedusername);
return preparedStatement.executeUpdate();
} catch (SQLException sqlex) {
sqlex.printStackTrace();
}
finally {
closeDb();
}
return 1;
}

//<!--------------------- ----------------For selecting date----------------------------------------- -->
@SuppressWarnings("null")
public boolean Datepicker(String date, String sessionusername ) throws ClassNotFoundException, SQLException {
try {
connection = JDBCPostgreSQLConnect.getConnection();
userid = fetchUserId(sessionusername);
preparedStatement = connection.prepareStatement("insert into date(date,userid) values(?, ?)");
preparedStatement.setString(1, date);
preparedStatement.setInt(2, userid);
preparedStatement.executeUpdate();
return true;
} catch (SQLException sqlex) {
sqlex.printStackTrace();
}
finally {
closeDb();
}
return false; 
}

// <!--------------------- ----------------register new user----------------------------------------- -->
public boolean registerUserAccount(JSONObject jsonObject)
throws  ClassNotFoundException, SQLException {
try {
connection = JDBCPostgreSQLConnect.getConnection();
String sessionusername = (String) jsonObject.get("username");
userid = fetchUserId(sessionusername);
String query = "insert into registration (userid,firstname, lastname,dob,gender,phonenum,address,created_on,security_id,security_answer) values (?,?, ?, ?, ?, ?, ?,?,?,?)";
preparedStatement = connection.prepareStatement(query);
preparedStatement.setInt(1, userid);
preparedStatement.setString(2, (String) jsonObject.get("firstname"));
preparedStatement.setString(3, (String) jsonObject.get("lastname"));
preparedStatement.setString(4, (String) jsonObject.get("dob"));
preparedStatement.setString(5, (String) jsonObject.get("gender"));
preparedStatement.setString(6, (String) jsonObject.get("phonenum"));
preparedStatement.setString(7, (String) jsonObject.get("address"));
preparedStatement.setString(8, (String) jsonObject.get("created_on"));
preparedStatement.setInt(9, Integer.parseInt((String) jsonObject.get("security_id")));
preparedStatement.setString(10, (String) jsonObject.get("security_answer"));
preparedStatement.executeUpdate();
} catch (SQLException sqlex) {
sqlex.printStackTrace();
} finally {
closeDb();
}
return false;
}

//<!-------------------------------register new username and password in login table----------------------------------------- -->
public boolean loginUserAccount(JSONObject jsonObject) throws  ClassNotFoundException, SQLException {
try {
String lastlogin = (String) jsonObject.get("created_on");
connection = JDBCPostgreSQLConnect.getConnection();
String query = "insert into login (username, password, lastlogin) values (?, ?, ?)";
preparedStatement = connection.prepareStatement(query);
preparedStatement.setString(1, (String) jsonObject.get("username"));
preparedStatement.setString(2, (String) jsonObject.get("password"));
preparedStatement.setString(3, lastlogin);
preparedStatement.executeUpdate();
return true;
} catch (SQLException sqlex) {
sqlex.printStackTrace();
} finally {
closeDb();
}
return false;
}

// <!--------------------- ----------------display user profile----------------------------------------- -->
@SuppressWarnings("null")
public Map displayProfile(String username) throws ClassNotFoundException, SQLException {
try {
connection = JDBCPostgreSQLConnect.getConnection();
preparedStatement = connection.prepareStatement(
"select r.firstname, r.lastname, r.dob, r.gender, r.phonenum, r.address" + " from registration r "
+ "join login l on l.userid = r.userid where l.username = ?");
preparedStatement.setString(1, username);
resultSet = preparedStatement.executeQuery();
Map<String, String> individualUser = new HashMap<String, String>();
while (resultSet.next()) {
individualUser.put("firstName", resultSet.getString(1));
individualUser.put("lastName", resultSet.getString(2));
individualUser.put("dob", resultSet.getString(3));
individualUser.put("gender", resultSet.getString(4));
individualUser.put("phonenum", resultSet.getString(5));
individualUser.put("address", resultSet.getString(6));
}
return individualUser;
} catch (SQLException sqlex) {
sqlex.printStackTrace();
} finally {
closeDb();
}
return null;
}

// <!--------------------- ----------------Function for Product_order------------------------------ -->
@SuppressWarnings("null")
public boolean productOrder(String sessionusername, int parsedproductId, int quantity) throws ClassNotFoundException, SQLException {
try {
connection = JDBCPostgreSQLConnect.getConnection();
userid = fetchUserId(sessionusername);
preparedStatement = connection.prepareStatement("insert into orders(product_id ,userid, quantity) values(?, ?, ?)");
preparedStatement.setInt(1, parsedproductId);
preparedStatement.setInt(2, userid);
preparedStatement.setInt(3, quantity);
preparedStatement.executeUpdate();
return true;
} catch (SQLException sqlex) {
sqlex.printStackTrace();
}
finally {
closeDb();
}
return false; 
}

//<!--------------------- ----------------cancel pickup------------------------------ -->
public int cancelpickup(String username)
throws ClassNotFoundException, SQLException {

try {
connection = JDBCPostgreSQLConnect.getConnection();
userid = fetchUserId(username);
String removepickup = "DELETE from date WHERE userid= ?";
preparedStatement = connection.prepareStatement(removepickup);
preparedStatement.setInt(1, userid );
return preparedStatement.executeUpdate();
} catch (SQLException sqlex) {
sqlex.printStackTrace();
}
finally {
try {
if (null != resultSet) {
resultSet.close();
}
if (null != preparedStatement) {
preparedStatement.close();
}
if (null != connection) { 
connection.close();
}
} catch (SQLException sqlex) {
sqlex.printStackTrace();
}
}
return -1;// fail
}

// <!--------------------- ----------------delete order row------------------------------ -->
public int removeProductOrder(int parsedproductId)
throws ClassNotFoundException, SQLException {
try {
connection = JDBCPostgreSQLConnect.getConnection();
String updateOrders = "DELETE from orders WHERE product_id = ?";
preparedStatement = connection.prepareStatement(updateOrders);
preparedStatement.setInt(1, parsedproductId);
return preparedStatement.executeUpdate();
} catch (SQLException sqlex) {
sqlex.printStackTrace();
}
finally {
try {
if (null != resultSet) {
resultSet.close();
}
if (null != preparedStatement) {
preparedStatement.close();
}
if (null != connection) { // 
connection.close();
}
} catch (SQLException sqlex) {
sqlex.printStackTrace();
}
}
return -1;// fail
}

// <!--------------------- ----------------display orders----------------------------------------- -->
@SuppressWarnings({ "null", "resource" })
public Map<Object, Map<Object, Object>> displayOrders(String sessionusername) throws ClassNotFoundException, SQLException {
try {
connection = JDBCPostgreSQLConnect.getConnection();
userid = fetchUserId(sessionusername);
System.out.println(userid);
preparedStatement = connection.prepareStatement(
"select c.category_id, c.category_name, p.product_id , p.product_name , p.product_description , p.product_price , O.quantity from product p join orders O on O.product_id = p.product_id  join category c on p.category_id = c.category_id where O.userid = ? group by c.category_id, p.product_id, O.quantity");
preparedStatement.setInt(1, userid);
resultSet = preparedStatement.executeQuery();
Map<Object, Map<Object, Object>> entireOrder = new HashMap<Object, Map<Object, Object>>();
while (resultSet.next()) {
String categoryid = Integer.toString(resultSet.getInt(1));
Object objCId = categoryid ;
String categoryname =resultSet.getString(2);
Object objname = categoryname;
String productId = Integer.toString(resultSet.getInt(3));
Object objId = productId ;
String productName = resultSet.getString(4);
Object objName =  productName;
String productDescription = resultSet.getString(5);
Object objDes = productDescription;
float productPrice = resultSet.getFloat(6);
Object objPrice = productPrice;
String quantity = Integer.toString(resultSet.getInt(7));
Object objQuantity = quantity;

Map<Object, Object> individualOrder = new HashMap<Object, Object>();
individualOrder.put("category_id", objCId );
individualOrder.put("category_name", objname);
individualOrder.put("product_id", objId);
individualOrder.put("product_name", objName);
individualOrder.put("product_description", objDes);
individualOrder.put("product_price", objPrice );
individualOrder.put("quantity",objQuantity );

entireOrder.put(productId, individualOrder);
}
System.out.println("The entireOrder is: " + entireOrder);
return entireOrder;
} catch (SQLException sqlex) {
sqlex.printStackTrace();
} finally {
closeDb();
}
return null;
}

private List<Map<String, Object>> getAllCategoriesFromOrders(String sessionusername)
throws SQLException, ClassNotFoundException {

List<Map<String, Object>> ordersCategoriesList = new ArrayList<Map<String, Object>>();
try {
connection = JDBCPostgreSQLConnect.getConnection();
String query = "select distinct(c.category_id), c.category_name " + "from orders o "
+ "join product p on p.product_id = o.product_id "
+ "join category c on c.category_id = p.category_id " + "join login l on l.userid = o.userid "
+ "where l.username= ?";
preparedStatement = connection.prepareStatement(query);
preparedStatement.setString(1, sessionusername);
resultSet = preparedStatement.executeQuery();
while (resultSet.next()) {
String categoryId = resultSet.getString(1);
String categoryName = resultSet.getString(2);

Map<String, Object> ordersCategories = new HashMap<String, Object>();
ordersCategories.put("categoryId", categoryId);
ordersCategories.put("categoryName", categoryName);

ordersCategoriesList.add(ordersCategories);
}
} catch (Exception e) {
e.printStackTrace();
} finally {
closeDb();
}
return ordersCategoriesList;
}

private List<Map<String, Object>> getAllOrderedProductsForEachCategory(String sessionusername, String categoryId)
throws SQLException, ClassNotFoundException {
List<Map<String, Object>> ordersCategoriesList = new ArrayList<Map<String, Object>>();
try {
connection = JDBCPostgreSQLConnect.getConnection();
String query = "select p.product_id, p.product_description, p.product_name, p.product_price, o.quantity "
+ "from orders o join product p on p.product_id = o.product_id "
+ "join login l on l.userid = o.userid " + "where p.category_id= ? and l.username= ?";
preparedStatement = connection.prepareStatement(query);
preparedStatement.setInt(1, Integer.parseInt(categoryId));
preparedStatement.setString(2, sessionusername);
resultSet = preparedStatement.executeQuery();
while (resultSet.next()) {
String product_id = resultSet.getString(1);
String product_description = resultSet.getString(2);
String product_name = resultSet.getString(3);
String product_price = resultSet.getString(4);
String quantity = resultSet.getString(5);

Map<String, Object> orderedProductsForEachCategory = new HashMap<String, Object>();

orderedProductsForEachCategory.put("product_id", product_id);
orderedProductsForEachCategory.put("product_description", product_description);
orderedProductsForEachCategory.put("product_name", product_name);
orderedProductsForEachCategory.put("product_price", product_price);
orderedProductsForEachCategory.put("quantity", quantity);

ordersCategoriesList.add(orderedProductsForEachCategory);
}
} catch (Exception e) {
e.printStackTrace();
} finally {
closeDb();
}
return ordersCategoriesList;
}

public Map<String, Object> getAllOrdersBasedOnCatgories(String sessionUserName) {
Map<String, Object> ordersInfoBasedOnCategories = new HashMap<String, Object>();
try {
List<Map<String, Object>> ordersCategoriesList = getAllCategoriesFromOrders(sessionUserName);

for (Map<String, Object> eacgCategoryMap : ordersCategoriesList) {
String categoryId = (String) eacgCategoryMap.get("categoryId");
String categoryName = (String) eacgCategoryMap.get("categoryName");
List<Map<String, Object>> allProductsBasedOnCategory = getAllOrderedProductsForEachCategory(sessionUserName, categoryId);
Map<String, Object> eachInfoMap = new HashMap<String, Object>();
eachInfoMap.put("categoryId", categoryId);
eachInfoMap.put("category_name", categoryName);
eachInfoMap.put("products", allProductsBasedOnCategory);
ordersInfoBasedOnCategories.put(categoryId, eachInfoMap);
}
System.out.println("\n\n ===============> Based on Categories:: " + ordersInfoBasedOnCategories);
} catch (Exception e) {
e.printStackTrace();
}
return ordersInfoBasedOnCategories;
}

//<!--------------------- ----------------display orders----------------------------------------- -->
@SuppressWarnings({ "null", "resource" })
public Map<Object, Map<Object, Object>> displayOrdersoncategory(String sessionusername) throws ClassNotFoundException, SQLException {

try {
connection = JDBCPostgreSQLConnect.getConnection();
userid = fetchUserId(sessionusername);
System.out.println("userid is " +userid);
 preparedStatement = connection.
  prepareStatement("SELECT DISTINCT category_name \r\n"
  		+ "FROM category\r\n"
  		+ "WHERE category_id IN(SELECT category_id\r\n"
  		+ "FROM product\r\n"
  		+ "WHERE product_id IN (select  product_id from orders where userid=?))");
  preparedStatement.setInt(1, userid); 
 resultSet = preparedStatement.executeQuery();
Map<Object, Map<Object, Object>> entireOrder = new HashMap<Object, Map<Object, Object>>();
while (resultSet.next()) {

String categoryid = Integer.toString(resultSet.getInt(1));
Object objCId = categoryid ;
String categoryname =resultSet.getString(2);
Object objname = categoryname;
String productId = Integer.toString(resultSet.getInt(3));
Object objId = productId ;
String productName = resultSet.getString(4);
Object objName =  productName;
String productDescription = resultSet.getString(5);
Object objDes = productDescription;
float productPrice = resultSet.getFloat(6);
Object objPrice = productPrice;
String quantity = Integer.toString(resultSet.getInt(7));
Object objQuantity = quantity;

Map<Object, Object> individualOrder = new HashMap<Object, Object>();

individualOrder.put("category_id", objCId );
individualOrder.put("category_name", objname);
individualOrder.put("product_id", objId);
individualOrder.put("product_name", objName);
individualOrder.put("product_description", objDes);
individualOrder.put("product_price", objPrice );
individualOrder.put("quantity",objQuantity );

entireOrder.put(productId, individualOrder);
}
System.out.println("The entireOrder is: " + entireOrder);
return entireOrder;
} catch (SQLException sqlex) {
sqlex.printStackTrace();
} finally {

closeDb();
}
return null;
}

//<!--------------------- ----------------Check Existing Product Id----------------------------------------- -->
public boolean checkExistingProductid(String sessionusername, int parsedproduct_id)
throws ClassNotFoundException, SQLException {
try {
connection = JDBCPostgreSQLConnect.getConnection();
String checkPID = "select l.userid, o.product_id from login l join orders o "
+ "on l.userid= o.userid where cast(product_id as int) in (?) and username = ?";
preparedStatement = connection.prepareStatement(checkPID);
preparedStatement.setInt(1, parsedproduct_id);
preparedStatement.setString(2, sessionusername);
System.out.println("Product check with productid: "+parsedproduct_id +" exist!");
resultSet = preparedStatement.executeQuery();
while (resultSet.next()) {
return true;
}
} catch (SQLException sqlex) {
sqlex.printStackTrace();
}
finally {
closeDb();
}
return false;
}

// <!--------------------- ----------------Increase Quantity in orders table------------------------------ -->
public int increaseQuantity( String sessionusername, int parsedproductId, int parsedquantity)throws ClassNotFoundException, SQLException {
try {
connection = JDBCPostgreSQLConnect.getConnection();
userid = fetchUserId(sessionusername);
//-------------------------------------sum of quantity--------------------------------------------
preparedStatement = connection.prepareStatement("select quantity from orders where product_id = ?");
preparedStatement.setInt(1, parsedproductId);
resultSet = preparedStatement.executeQuery();
int quantity1 = 0;
while (resultSet.next()) {
quantity1 = resultSet.getInt(1);
System.out.println("quantity1 is : " + quantity1 + " of resultset from db!");
}
int quantity2 = parsedquantity;
System.out.println("quantity2 is : " + quantity2 + " of resultset from jsp!");
int quantity = quantity1 + quantity2;
System.out.println("sum of quantity is : " + quantity + " sum!");
String updateQuantity = "UPDATE orders SET quantity = ? WHERE userid = ? and product_id = ?";
preparedStatement = connection.prepareStatement(updateQuantity);
preparedStatement.setInt(1, quantity);
preparedStatement.setInt(2, userid);
preparedStatement.setInt(3, parsedproductId);
return preparedStatement.executeUpdate();
} catch (SQLException sqlex) {
sqlex.printStackTrace();
}
finally {
closeDb();
}
return 1;
}

// <!--------------------- ----------------update Quantity in orders table------------------------------ -->
public int decreaseQuantity( String sessionusername, int parsedproductId, int parsedquantity)throws ClassNotFoundException, SQLException {
try {
connection = JDBCPostgreSQLConnect.getConnection();
userid = fetchUserId(sessionusername);
//-------------------------------------update of quantity--------------------------------------------
String updateQuantity = "UPDATE orders SET quantity = ? WHERE userid = ? and product_id = ?";
preparedStatement = connection.prepareStatement(updateQuantity);
preparedStatement.setInt(1, parsedquantity);
preparedStatement.setInt(2, userid);
preparedStatement.setInt(3, parsedproductId);
int result = preparedStatement.executeUpdate();
System.out.println(result);
return result;
} catch (SQLException sqlex) {
sqlex.printStackTrace();
}
finally {
closeDb();
}
return 0;
}
}
