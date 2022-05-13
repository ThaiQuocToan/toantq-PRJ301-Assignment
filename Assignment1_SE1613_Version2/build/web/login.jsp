<%-- 
    Document   : login
    Created on : Feb 23, 2022, 9:49:24 PM
    Author     : Thai Quoc Toan <toantqse151272@fpt.edu.vn>
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login</title>
    </head>
    <body>
        <div><h1>Login Page</h1></div>
        <!-- ê -->
        <c:set var="errors" value="${requestScope.ERROR_LOGIN}"/>
        <c:if test="${not empty errors.loginError}">
            <font style="color: red;">
                ${errors.loginError}
            </font>
        </c:if>
        <form action="loginController" method="POST">
            <table>
            <tbody>
                <tr>
                    <td>Username</td>
                    <td>
                        <input type="text" name="txtUsername" 
                               value="" style="width: 150px"/>
                    </td>
                </tr>
                <tr>
                    <td>Password</td>
                    <td>
                        <input type="password" name="txtPassword" 
                               value="" style="width: 150px"/>
                    </td>
                </tr>
                <tr>
                    <td>Remember Me</td>
                    <td>
                        <input type="checkbox" name="RememberMe" value="ON"/>
                    </td>
                </tr>
            </tbody>
        </table>
            <input type="submit" value="Login" name="btnAction" />
            <input type="reset" value="Reset" name="Reset" />
        </form>
        <c:url var="url" value="registerPage"></c:url>
        Do you have an account? <a href="${url}">Register</a>
    </body>
</html>
