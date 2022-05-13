<%-- 
    Document   : search
    Created on : Feb 23, 2022, 10:42:59 PM
    Author     : Thai Quoc Toan <toantqse151272@fpt.edu.vn>
--%>

<%@page import="java.util.List"%>
<%@page import="toantq.tblaccount.TblaccountDTO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="/WEB-INF/tlds/mylib.tld" prefix="my"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Search</title>
    </head>
    <body>
        <div><h1>Search Page</h1></div>
        <c:set var="admin" value="${sessionScope.ADMIN_ROLE}"/>
        <c:if test="${not empty admin}">
        <font style="color: red;">
        Welcome, ${admin.lastName}
        </font>
        <form action="logoutController">
            <input type="submit" value="Logout" name="btnAction" />
        </form><br />
        </c:if>
        <form action="searchController">
            Search Value <input type="text" name="txtSearchValue" 
                                value="${param.txtSearchValue}" />
            <input type="submit" value="Search" name="btnAction" />
        </form>
        <c:set var="searchValue" value="${param.txtSearchValue}" scope="page" />
        <c:if test="${not empty searchValue}" var="search" scope="page">
            <c:set var="resultSearchLastName" value="${requestScope.RESULT_SEARCH_LASTNAME}" scope="page" />
            <c:if test="${not empty resultSearchLastName}" var="result" scope="page">
                <table border="1">
                    <thead>
                        <tr>
                            <th>No.</th>
                            <th>Username</th>
                            <th>Password</th>
                            <th>Full Name</th>
                            <th>Role</th>
                            <th>Delete</th>
                            <th>Update</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="dto" items="${resultSearchLastName}" varStatus="counter">
                            <c:if test="${dto.role eq false}">
                            <form action="updateController" method="POST">
                                <tr>
                                    <td>
                                        ${counter.count}
                                    </td>
                                    <td>
                                        <input type="hidden" name="txtUsername" 
                                               value="${dto.username}" />
                                        ${dto.username}
                                    </td>
                                    <td>
                                        <c:set var="errorUpdatePassword" value="${requestScope.ERROR_UPDATE_PASSWORD}"/>
                                        <c:if test="${not empty errorUpdatePassword.passwordLengthError}">
                                            <c:set var="errorPasswordLength" value="${my:getErrorQuantityOfEachItem(dto.username,
                                                                                      errorUpdatePassword.passwordLengthError)}" />
                                            <c:if test="${not empty errorPasswordLength}" >
                                                <font style="color: red">
                                                ${errorPasswordLength}<br />
                                                </font>
                                            </c:if>
                                        </c:if>  
                                        <input type="password" name="txtPassword" 
                                               value="${dto.password}" />
                                    </td>
                                    <td>
                                        ${dto.lastName}
                                    </td>
                                    <td>
                                        <input type="checkbox" name="txtIsAdmin" value="ON"
                                               <c:if test="${dto.role eq true}">
                                                   checked="checked"
                                               </c:if> />
                                    </td>
                                    <td>
                                        <c:url var="urlRewriting" value="deleteController">
                                            <c:param name="txtUsername" value="${dto.username}"/>
                                            <c:param name="lastSearchValue" value="${param.txtSearchValue}"/>
                                        </c:url>
                                        <a href="${urlRewriting}">Delete</a>
                                    </td>
                                    <td>
                                        <input type="hidden" name="lastSearchValue" 
                                               value="${searchValue}" />
                                        <input type="submit" value="Update" name="btnAction" />
                                    </td>
                                </tr>
                            </form>
                        </c:if>
                    </c:forEach>
                    </tbody>
                </table>
            </c:if>
            <c:if test="${empty resultSearchLastName}">
                <h2>No record for your search!!!</h2>
            </c:if>
        </c:if>
    </body>
</html>
