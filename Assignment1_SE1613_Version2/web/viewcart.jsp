<%-- 
    Document   : viewcart
    Created on : Feb 24, 2022, 4:46:06 PM
    Author     : Thai Quoc Toan <toantqse151272@fpt.edu.vn>
--%>

<%@page import="toantq.tblproduct.TblproductDAO"%>
<%@page import="toantq.tblproduct.TblproductDTO"%>
<%@page import="java.util.Map"%>
<%@page import="toantq.tblaccount.TblaccountDTO"%>
<%@page import="toantq.cart.CartObject"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/WEB-INF/tlds/mylib.tld" prefix="my" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>View Cart</title>
    </head>
    <body>
        <h1>Engineer Book Store</h1>
        <c:set var="user" value="${sessionScope.USER_ROLE}"/>
        <c:if test="${not empty user}">
            <font style="color: red">
            Welcome, ${user.lastName}
            </font>
        <form action="logoutController">
            <input type="submit" value="Logout" name="btnAction" />
        </form>
        </c:if>    
        <p>Your cart includes</p>
        <c:set var="searchValue" value="${param.txtSearchItem}"/>
        <c:url var="urlRewriting" value="searchItemController">
            <c:param name="txtSearchItem" value="${searchValue}" />
        </c:url>
        <c:if test="${not empty sessionScope}">
            <c:set var="cartObject" value="${sessionScope.CART}"/>
            <c:if test="${not empty cartObject}">
                <c:set var="cart" value="${my:getCartObject(cartObject, servletContext)}"/>
                <c:if test="${not empty cart}">
                    <form action="viewCartController" method="POST">
                        <table border="1">
                            <thead>
                                <tr>
                                    <th>Action</th>
                                    <th>No.</th>
                                    <th>Name</th>
                                    <th>Price</th>
                                    <th>Quantity</th>
                                    <th>Total</th>
                                    <th>Update</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="item" items="${cart}" varStatus="counter">
                                    <tr>
                                        <td>
                                            <input type="checkbox" name="checkItem" value="${item.idItem}" />          
                                        </td>
                                        <td>
                                            <c:set var="count" value="${counter.count}" /> 
                                            ${counter.count}
                                        </td>
                                        <td>
                                            ${item.name}
                                        </td>
                                        <td>
                                            ${item.price}
                                        </td>
                                        <td>
                                            <input type="hidden" name="txtTitle_${count}" 
                                                   value="${item.idItem}" />
                                            <input type="text" name="txtQuantity_${count}" value="${item.quantity}" /><br />
                                            <c:set var="errors" value="${requestScope.ERROR_NUMBER_FORMAT}"/>
                                            <c:if test="${not empty errors}">
                                                <c:set var="errorNumberFormat" value="${my:getErrorQuantityOfEachItem(
                                                                                    item.idItem, errors.updateQuantityItemError)}"/>
                                                <c:if test="${not empty errorNumberFormat}">
                                                    <font style="color: red">
                                                    ${errorNumberFormat}<br />
                                                    </font>
                                                </c:if>
                                            </c:if>
                                            <c:set var="errorQuantityStock" value="${requestScope.ERROR_QUANTITY_ITEM_STOCK}"/>
                                            <c:if test="${not empty errorQuantityStock}">
                                                <c:forEach var="error" items="${errorQuantityStock}">
                                                    <c:set var="sameNameItem" 
                                                           value="${my:getErrorQuantityOfEachItem(
                                                                    item.idItem, error.quantityItemError)}"/>
                                                    <c:if test="${not empty sameNameItem}">
                                                        <font style="color: red">
                                                        ${sameNameItem}<br />
                                                        </font>
                                                    </c:if>
                                                </c:forEach>
                                            </c:if>
                                            <c:set var="errorQuantity" value="${requestScope.ERROR_QUANTITY_OVER_ITEM}"/>
                                            <c:if test="${not empty errorQuantity}" >
                                                <c:set var="sameNameItemOverQuantity" 
                                                       value="${my:getErrorQuantityOfEachItem(
                                                                item.idItem, errorQuantity.updateQuantityOverItemError)}"/>
                                                <c:if test="${not empty sameNameItemOverQuantity}">
                                                    <font style="color: red">
                                                    ${sameNameItemOverQuantity}
                                                    </font>
                                                </c:if>
                                            </c:if>  
                                        </td>
                                        <td>
                                            ${item.price * item.quantity}
                                        </td>
                                        <td>
                                            <input type="submit" value="Update Item's Quantity" 
                                                   name="btnAction_${count}" />
                                        </td>
                                    </tr>
                                </c:forEach> 
                                <tr>
                                    <td colspan="3">
                                        <a href="${urlRewriting}">Add more items!!!</a>
                                    </td>
                                    <td colspan="2">
                                        <input type="hidden" name="txtSearchItem" 
                                               value="${searchValue}" />
                                        <input type="submit" value="Remove item" name="btnAction" />
                                    </td>
                                    <td colspan="2">
                                        <input type="submit" value="Check out" name="btnAction" />
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </form>
                </c:if>
            </c:if>
        </c:if>
        <c:if test="${empty sessionScope or empty cartObject or empty cart}">
            <h2>No Cart!!!</h2>
            <a href="${urlRewriting}">Back to shopping page</a>
        </c:if>
    </body>
</html>
