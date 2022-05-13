<%-- 
    Document   : register
    Created on : Feb 23, 2022, 9:24:10 PM
    Author     : Thai Quoc Toan <toantqse151272@fpt.edu.vn>
--%>

<%@page import="java.nio.charset.StandardCharsets"%>
<%@page import="java.nio.ByteBuffer"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Register</title>
    </head>
    <body>
        <div><h1>Register Page</h1></div>

        <c:set var="errors" value="${requestScope.ERRORS}"/>
        <form action="registerController" method="POST">
            <c:if test="${not empty errors.usernameLengthError}">
                <font style="color: red">
                ${errors.usernameLengthError}<br />
                </font>
            </c:if>
            <c:if test="${not empty errors.usernameExistedError}">
                <font style="color: red">
                ${errors.usernameExistedError}<br />
                </font>
            </c:if>
            Username<strong style="color: red;">*</strong> <input 
                type="text" name="txtUsername" value="${param.txtUsername}" />(e.g. 6-20 character)<br />
            <c:if test="${not empty errors.passwordLengthError}">
                <font style="color: red">
                ${errors.passwordLengthError} <br />
                </font>
            </c:if>
            Password<strong style="color: red;">*</strong> <input 
                type="password" name="txtPassword" value="" />(e.g. 6-20 character)<br />
            <c:if test="${not empty errors.confirmPasswordError}">
                <font style="color: red">
                ${errors.confirmPasswordError}<br />
                </font>
            </c:if>
            Confirm Password<strong style="color: red;">*</strong> <input 
                type="password" name="txtConfirmPassword" value="" /> <br />
            <c:if test="${not empty errors.fullNameLengthError}">
                <font style="color: red">
                ${errors.fullNameLengthError} <br />
                </font>
            </c:if>
            Full Name<strong style="color: red;">*</strong> <input 
                type="text" name="txtFullname" value="${param.txtFullname}" />(e.g. 6-50 character)<br />
            <input type="submit" value="Register" name="btnAction" />
        </form> <br />
        <c:url var="url" value="loginPage"></c:url>
        you already have an account. <a href="${url}">Login</a>

    </body>
</html>
