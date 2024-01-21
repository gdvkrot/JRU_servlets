<%@ page import="java.util.List" %>
<%@ page import="ua.javarush.models.User" %>
<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Users Page</title>
</head>
<body>
<h1>Add new User</h1>
<form action="/users" method="post">
    <label for="name">Name:</label>
    <input id="name" type="text" name="name"><br>
    <label for="password">Password:</label>
    <input id="password" type="text" name="password"><br>
    <label for="email">Email:</label>
    <input id="email" type="text" name="email"><br>
    <label for="birthDate">BirthDate:</label>
    <input id="birthDate" type="date" name="birthDate"><br>
    <input type="submit" value="Add">
</form>

<h3>All Users:</h3>
<%
    List<User> users = new ArrayList<>();
    try {
        users = (List<User>) request.getAttribute("users");
    }
    catch (Exception ex) {
        throw new RuntimeException(ex);
    }
    for (User u : users) { %>
<%= u %>
<br>
<% } %>
</body>
</html>
