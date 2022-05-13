<%-- 
    Document   : Shopping
    Created on : Feb 24, 2022, 1:20:02 PM
    Author     : Thai Quoc Toan <toantqse151272@fpt.edu.vn>
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="toantq.tblproduct.TblproductDTO"%>
<%@page import="java.util.List"%>
<%@page import="toantq.tblaccount.TblaccountDTO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/WEB-INF/tlds/mylib.tld" prefix="my"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Shopping</title>
    </head>
    <body>
        <h1>Engineer Book Store</h1>
        <c:set var="user" value="${sessionScope.USER_ROLE}" />
        <c:if test="${not empty user}" >
            <font style="color: red;">
            Welcome, ${user.getLastName()}
            </font>
        <form action="logoutController">
            <input type="submit" value="Logout" name="btnAction" />
        </form>
        </c:if>
        <form action="searchItemController">
            Search Item <input type="text" name="txtSearchItem" 
                               value="${param.txtSearchItem}" />
            <input type="submit" value="Search Item" name="btnAction" />
        </form>
        <c:set var="searchValue" value="${param.txtSearchItem}" />
        <c:if test="${not empty searchValue}" >
            <c:set var="result" value="${requestScope.RESULT_SEARCH_ITEM}" />
            <c:if test="${not empty result}" >
                <form action="cartController" method="POST">

                    <table border="1">
                        <thead>
                            <tr>
                                <th>Action</th>
                                <th>No.</th>
                                <th>Name</th>
                                <th>Price</th>
                                <th>Description</th>
                                <th>Quantity</th>
                                <th>Status</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="dto" items="${result}" varStatus="counter" >
                                <tr>
                                    <td>
                                        <input type="checkbox" name="chooseItem" 
                                               value="${dto.idItem}" readonly=""/><br />
                                        <c:set var="errors" value="${requestScope.ERROR_ITEM_STATUS}" />
                                        <c:if test="${not empty errors}">
                                            <c:forEach var="error" items="${errors}">
                                                <c:set var="errorItemStatus" 
                                                       value="${my:getErrorQuantityOfEachItem(
                                                                dto.idItem, error.statusItemError)}"/>
                                                <c:if test="${not empty errorItemStatus}">
                                                    <font style="color: red">
                                                    ${errorItemStatus}<br />
                                                    </font>
                                                </c:if>
                                            </c:forEach>
                                        </c:if>
                                        <c:set var="quantityErrors" value="${requestScope.ERROR_ITEM_QUANTITY}"/>
                                        <c:if test="${not empty quantityErrors}">
                                            <c:forEach var="quantityError" items="${quantityErrors}">
                                                <c:set var="quantityErrorEachItem" 
                                                       value="${my:getErrorQuantityOfEachItem(
                                                                dto.idItem, quantityError.quantityItemError)}"/>
                                                <c:if test="${not empty quantityErrorEachItem}">
                                                    <font style="color: red">
                                                    ${quantityErrorEachItem}<br />
                                                    </font>
                                                </c:if>
                                            </c:forEach>
                                        </c:if>
                                    </td>
                                    <td>
                                        ${counter.count}
                                    </td>
                                    <td>
                                        ${dto.name}
                                    </td>
                                    <td>
                                        ${dto.price}
                                    </td>
                                    <td>
                                        ${dto.description}
                                    </td>
                                    <td>
                                        ${dto.quantity}
                                    </td>
                                    <td>
                                        <c:if test="${dto.status}">
                                            <p style="color: green">Active</p>
                                        </c:if>
                                        <c:if test="${dto.status eq false}">
                                            <p style="color: red">None Active</p>
                                        </c:if>
                                    </td>
                                </tr>
                            </c:forEach>
                            <tr>
                                <td colspan="3">
                                    <input type="hidden" name="txtSearchItem" value="${searchValue}" />
                                    <input type="submit" value="Add item to Cart" name="btnAction" />
                                </td>
                                <td colspan="4">
                                    <input type="submit" value="View your cart" name="btnAction" />
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </form>

            </c:if>
            <c:if test="${empty result}">
                <h2>
                    No records about this item!!!
                </h2>
            </c:if>
        </c:if>
    </body>
</html>
